package com.hug.takerest.shots.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beltaief.flowlayout.FlowLayout;
import com.hug.takerest.R;
import com.hug.takerest.base.BaseFragment;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.adapter.UserShotProjectsAdapter;
import com.hug.takerest.shots.model.Project;
import com.hug.takerest.shots.model.Shot;
import com.hug.takerest.shots.presenter.UserShotProjectsContract;
import com.hug.takerest.shots.presenter.UserShotProjectsPresenter;
import com.hug.takerest.util.DividerGridItemDecoration;
import com.hug.takerest.util.FlowLayoutManager;
import com.hug.takerest.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HStan on 2017/4/14.
 */

public class UserShotFragment extends BaseFragment implements UserShotProjectsContract.View{

    private int ID;

    public static final String SHOT = "shot";
    public static final String PROJECT = "projects";
    private String mType;

    @BindView(R.id.user_shot_projects_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.flowlayout)
    FlowLayout mFlowLayout;
    private UserShotProjectsAdapter mAdapter;
    private FlowLayoutManager flowLayoutManager;

    private UserShotProjectsPresenter mPresenter;

    public UserShotFragment newInstance(int id,String type) {
        UserShotFragment fragment = new UserShotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void parseArgs() {
        Bundle bundle = getArguments();
        ID = bundle.getInt("id");
        mType = bundle.getString("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shot_projects, container, false);
        ButterKnife.bind(this, rootView);
        flowLayoutManager = new FlowLayoutManager(mFlowLayout);
        retrofit.initRetrofit(getActivity(), RetrofitManager.SHOT_TYPE);
        mPresenter = new UserShotProjectsPresenter(this);
        initRecyclerView(mType);
        if (mType.equals(SHOT)){
            mPresenter.getShots(ID);
        }else if (mType.equals(PROJECT)){
            mPresenter.getProjects(ID);
        }
        return rootView;
    }

    private void initRecyclerView(String mType) {
        if (mType.equals(SHOT)){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
            mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        }else if (mType.equals(PROJECT)){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onShotSuccess(final List<Shot> list) {
        mAdapter = new UserShotProjectsAdapter(getActivity(),list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new UserShotProjectsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View avator, int position) {
                Intent intent= new Intent(getActivity(),ShotDetailActivity.class);
                intent.putExtra("shot_id",list.get(position).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onProjectSuccess(List<Project> list) {
        mAdapter = new UserShotProjectsAdapter(getActivity(),list);
        mRecyclerView.setAdapter(mAdapter);
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
    public void showEmpty() {
        flowLayoutManager.setEmpty();
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onError(String t) {
        flowLayoutManager.showError();
        ToastUtil.makeTextShort(getActivity(),t);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dispose();
    }

}
