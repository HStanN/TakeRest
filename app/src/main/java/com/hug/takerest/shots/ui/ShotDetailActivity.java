package com.hug.takerest.shots.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.base.BaseActivity;
import com.hug.takerest.constants.Transition;
import com.hug.takerest.gank.ui.PhotoViewActivity;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.adapter.CommentsAdapter;
import com.hug.takerest.shots.model.Comments;
import com.hug.takerest.shots.model.Shot;
import com.hug.takerest.shots.presenter.ShotContract;
import com.hug.takerest.shots.presenter.ShotPresenter;
import com.hug.takerest.util.ToastUtil;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HStan on 2017/4/11.
 */

public class ShotDetailActivity extends BaseActivity implements ShotContract.View {
    private int shotId;

    @BindView(R.id.avator)
    SimpleDraweeView mAvator;
    @BindView(R.id.shot_image)
    SimpleDraweeView mImage;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_bio)
    TextView mUserBio;
    @BindView(R.id.comments_number)
    TextView mComments;
    @BindView(R.id.image_descrition)
    TextView mImageDescription;
    @BindView(R.id.comments_list)
    RecyclerView mRecyclerView;

    private CommentsAdapter mAdapter;
    private String url;
    private ShotPresenter mPresenter;
    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_shot);
    }

    @Override
    protected void init() {
        super.init();
        retrofit.initRetrofit(this, RetrofitManager.SHOT_TYPE);
        ButterKnife.bind(this);
        initToolbar();
        setBackEnable(true);
        initRecyclerView();
        shotId = getShotId();
        mPresenter = new ShotPresenter(shotId,this);
        mPresenter.start();
    }

    private void initUserInfo(final Shot shot) {
        getToolbar().setTitle(shot.getTitle());
        mAvator.setImageURI(shot.getUser().getAvatar_url());
        url = (shot.getImages().getHidpi() == null) ? shot.getImages().getNormal() : shot.getImages().getHidpi();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setAutoPlayAnimations(true)
                .build();
        mImage.setController(controller);
        if (shot.getDescription() != null){
            CharSequence charSequence = Html.fromHtml(shot.getDescription());
            mImageDescription.setText(charSequence);
        }else{
            mImageDescription.setText(getString(R.string.no_description));
        }
        mUserName.setText(shot.getUser().getName());
        if (shot.getUser().getBio() != null) {
            CharSequence charSequence = Html.fromHtml(shot.getUser().getBio());
            mUserBio.setText(charSequence);
        } else {
            mUserBio.setText(getString(R.string.no_description));
        }
        mComments.setText("Comments(" + shot.getComments_count() + ")");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
        }
        mAvator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShotDetailActivity.this,UserActivity.class);
                intent.putExtra("id",shot.getUser().getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(ShotDetailActivity.this, mAvator, "avator");
                    startActivity(intent, options.toBundle());
                }else {
                    startActivity(intent);
                }
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void bind() {
        super.bind();
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShotDetailActivity.this, PhotoViewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("transition_name", Transition.SHOT_TRANSITION);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(ShotDetailActivity.this, mImage, Transition.SHOT_TRANSITION);
                    startActivity(intent, options.toBundle());
                }else{
                    startActivity(intent);
                }
            }
        });
        retrofit.getComments(shotId, new RetrofitManager.OnRetrofitCallback<List<Comments>>() {
            @Override
            public void onResponse(List<Comments> response) {
                Collections.reverse(response);

                mAdapter = new CommentsAdapter(ShotDetailActivity.this,response);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }

    public int getShotId() {
        return getIntent().getIntExtra("shot_id",0);
    }

    @Override
    public void onSuccess(Shot shot) {
        initUserInfo(shot);
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onError(String t) {
        ToastUtil.makeTextShort(this,t);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dispose();
    }
}
