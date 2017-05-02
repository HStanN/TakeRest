package com.hug.takerest.gank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beltaief.flowlayout.FlowLayout;
import com.hug.takerest.R;
import com.hug.takerest.WebActivity;
import com.hug.takerest.base.BaseFragment;
import com.hug.takerest.gank.adapter.DailyAdapter;
import com.hug.takerest.gank.model.GankDaily;
import com.hug.takerest.gank.presenter.GankDailyContract;
import com.hug.takerest.gank.presenter.GankDailyPresenter;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.util.DateUtil;
import com.hug.takerest.util.FlowLayoutManager;
import com.hug.takerest.util.ToastUtil;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HStan on 2017/3/29.
 */

public class DailyFragment extends BaseFragment implements GankDailyContract.View {
    @BindView(R.id.gank_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.flowlayout)
    FlowLayout flowlayout;

    Calendar calendar;
    GankDailyPresenter presenter;
    FlowLayoutManager flowLayoutManager;
    private DailyAdapter mAdapter;
    private int day,month,year;
    private int PRE_DATE = 0;
    private static final String TODAY_TIPS = "视频：";
    private static final String URL = "url";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, rootView);
        retrofit.initRetrofit(getActivity(), RetrofitManager.GANK_TYPE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        flowLayoutManager = new FlowLayoutManager(flowlayout);
        calendar = DateUtil.getCalendar(Locale.CHINA);
        presenter = new GankDailyPresenter(this);
        year = DateUtil.getYear(calendar);
        month = DateUtil.getMonth(calendar);
        day = DateUtil.getDay(calendar);
        presenter.start(year, month, day);
        bind();
        return rootView;
    }

    private void bind() {
        flowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start(year, month, day);
            }
        });
    }

    public void getGankByDate(int year,int month,int day){
        if (presenter != null){
            presenter.start(year, month, day);
        }
    }

    @Override
    public void onSuccess(GankDaily gankDaily) {
        if (gankDaily != null &&gankDaily.getCategory().size() > 0) {
            mAdapter = new DailyAdapter(getActivity(), gankDaily.getResults(), gankDaily.getCategory());
            mRecyclerView.setAdapter(mAdapter);
            if (gankDaily.getResults().getVideo() != null){
                final String sVideoUrl = gankDaily.getResults().getVideo().get(0).getUrl();
                String video_title = gankDaily.getResults().getVideo().get(0).getDesc();

                View header = LayoutInflater.from(getActivity()).inflate(R.layout.daily_header_view, mRecyclerView, false);
                TextView videoTitle = (TextView) header.findViewById(R.id.video_title);
                ImageView play = (ImageView) header.findViewById(R.id.start_play);
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sVideoUrl != null) {
                            Intent intent = new Intent(getActivity(), WebActivity.class);
                            intent.putExtra(URL, sVideoUrl);
                            getActivity().startActivity(intent);
                        }
                    }
                });
                videoTitle.setText(TODAY_TIPS + video_title);
                mAdapter.setHeaderView(header);

            }
        }else{
            PRE_DATE--;
            String date = DateUtil.getPreviewsDay(calendar,PRE_DATE);
            day = DateUtil.getDayFromString(date);
            month = DateUtil.getMonthFromString(date);
            year = DateUtil.getYearFromString(date);
            presenter.start(year,month,day);
        }

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

    }

    @Override
    public void onError(String t) {
        ToastUtil.makeTextShort(getActivity(),t);
       flowLayoutManager.showError();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
