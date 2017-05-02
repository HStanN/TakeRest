package com.hug.takerest.movie.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beltaief.flowlayout.FlowLayout;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.WebActivity;
import com.hug.takerest.base.BaseActivity;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.movie.adapter.DirectorAdapter;
import com.hug.takerest.movie.model.Director;
import com.hug.takerest.movie.model.MovieBasic;
import com.hug.takerest.movie.presenter.MovieBasicContract;
import com.hug.takerest.movie.presenter.MovieBasicPresenter;
import com.hug.takerest.util.FlowLayoutManager;
import com.hug.takerest.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieBasicActivity extends BaseActivity implements MovieBasicContract.View {
    @BindView(R.id.movie_image)
    SimpleDraweeView mImage;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.movie_type)
    TextView mType;
    @BindView(R.id.watched)
    TextView mWatched;
    @BindView(R.id.flowlayout)
    FlowLayout flowLayout;
    @BindView (R.id.movie_image_blur)
    SimpleDraweeView mBlurView;
    @BindView(R.id.plot)
    TextView mPlot;
    @BindView(R.id.original_title)
    TextView mOriginalName;
    @BindView(R.id.ratingbar)
    RatingBar mRatingBar;
    @BindView(R.id.rating_number)
    TextView mRating;
    @BindView(R.id.director_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.buy_ticket)
    Button mBuyTicket;
    @BindView(R.id.share_friends)
    Button mShare;
    private FlowLayoutManager flowLayoutManager;
    private static final String ID = "id";
    private static final String URL = "url";
    private static final String ORIGINAL_TITLE = "原名：";
    private static final String WATCHED = "人看过";

    private DirectorAdapter adapter;
    private MovieBasicPresenter presenter;
    private String ticketUrl;
    private String shareUrl;

    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_movie_basic);
    }

    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
        setToolbarTitle("");
        setBackEnable(true);
        flowLayoutManager = new FlowLayoutManager(flowLayout);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerView.setHasFixedSize(true);
        retrofit.initRetrofit(this, RetrofitManager.MOVIE_TYPE);
        presenter = new MovieBasicPresenter(this,getIntent().getIntExtra(ID,0));
        presenter.start();
    }

    @Override
    protected void bind() {
        super.bind();
        flowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start();
            }
        });
        mBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ticketUrl.isEmpty()){
                    Intent intent = new Intent(MovieBasicActivity.this,WebActivity.class);
                    intent.putExtra(URL,ticketUrl);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onError(String t) {
        ToastUtil.makeTextShort(this,t);
    }

    @Override
    public void onSuccess(final MovieBasic movieBasic) {
        String url = movieBasic.getImages().getLarge();
        String des = movieBasic.getCountries().get(0);
        String date = movieBasic.getYear();
        String title = movieBasic.getTitle();
        String plot = movieBasic.getSummary();
        shareUrl = movieBasic.getShare_url();
        ticketUrl = movieBasic.getSchedule_url();
        String original_title = movieBasic.getOriginal_title();
        double rating = movieBasic.getRating().getAverage();
        int watched = movieBasic.getCollect_count();
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < movieBasic.getGenres().size();i++){
            if (i < movieBasic.getGenres().size()-1){
                sb.append(movieBasic.getGenres().get(i)+"/");
            }else{
                sb.append(movieBasic.getGenres().get(i));
            }
        }
        setToolbarTitle(title);
        mBlurView.setImageURI(Uri.parse(url));
        mImage.setImageURI(Uri.parse(url));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.FIT_XY, ScalingUtils.ScaleType.FIT_XY));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.FIT_XY, ScalingUtils.ScaleType.FIT_XY));
        }
        mDescription.setText(des+"\r\r"+date);
        mOriginalName.setText(ORIGINAL_TITLE+ original_title);
        mRatingBar.setMax(movieBasic.getRating().getMax());
        mRatingBar.setRating((float)movieBasic.getRating().getAverage()/2);
        mWatched.setText(watched+WATCHED);
        mType.setText(sb.toString());
        mPlot.setText(plot);
        mRating.setText(rating+"");
        final List<Director> list = movieBasic.getDirectors();
        list.addAll(movieBasic.getCasts());
        adapter = new DirectorAdapter(this,DirectorAdapter.NORMAL,list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new DirectorAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MovieBasicActivity.this,WebActivity.class);
                intent.putExtra(URL,list.get(position).getAlt());
                startActivity(intent);
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent action_share = new Intent(Intent.ACTION_SEND);
                action_share.setType("text/plain");
                action_share.putExtra(Intent.EXTRA_SUBJECT, movieBasic.getTitle());
                action_share.putExtra(Intent.EXTRA_TEXT, movieBasic.getTitle()+movieBasic.getMobile_url());
                action_share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(action_share, movieBasic.getTitle()));
            }
        });
    }

    @Override
    public void showProgress() {
        flowLayoutManager.showProgress();
    }

    @Override
    public void hideProgress() {
        flowLayoutManager.showContent();
    }

    @Override
    public void setPresenter(Object presenter) {
        if (presenter == null) {
            Logger.d(HTTP_TAG.toString(), "MovieBasicPresenter is null");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
