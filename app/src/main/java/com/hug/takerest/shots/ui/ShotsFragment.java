package com.hug.takerest.shots.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beltaief.flowlayout.FlowLayout;
import com.hug.takerest.R;
import com.hug.takerest.base.BaseFragment;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.adapter.ShotsAdapter;
import com.hug.takerest.shots.model.Shot;
import com.hug.takerest.shots.presenter.ShotsContract;
import com.hug.takerest.shots.presenter.ShotsPresenter;
import com.hug.takerest.util.FlowLayoutManager;
import com.hug.takerest.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HStan on 2017/4/6.
 */

public class ShotsFragment extends BaseFragment implements ShotsContract.View {
    @BindView(R.id.flowlayout)
    FlowLayout flowLayout;
    @BindView(R.id.shots_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.shot_ptr)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private FlowLayoutManager flowLayoutManager;
    private ShotsAdapter adapter;
    private ShotsPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shots, container, false);
        ButterKnife.bind(this, rootView);
        bind();
        initRecyclerView();
        flowLayoutManager = new FlowLayoutManager(flowLayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        retrofit.initRetrofit(getActivity(), RetrofitManager.SHOT_TYPE);
        mPresenter = new ShotsPresenter(this);
        mPresenter.start();
        return rootView;
    }

    private void bind() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onSuccess(final List<Shot> list) {
        adapter = new ShotsAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new ShotsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View avator, int position) {
                Shot shot = list.get(position);
                Intent intent = new Intent(getActivity(),ShotDetailActivity.class);
                intent.putExtra("shot_id",shot.getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), avator, "avator");
                    startActivity(intent, options.toBundle());
                }else {
                    startActivity(intent);
                }
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
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onError(String t) {
        hideProgress();
        flowLayoutManager.setEmpty();
        flowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.start();
                flowLayoutManager.showProgress();
            }
        });
        ToastUtil.makeTextShort(getActivity(), t);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dispose();
    }
}
