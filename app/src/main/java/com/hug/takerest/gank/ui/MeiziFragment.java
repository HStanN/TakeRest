package com.hug.takerest.gank.ui;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hug.takerest.R;
import com.hug.takerest.base.BaseFragment;
import com.hug.takerest.constants.C;
import com.hug.takerest.constants.Transition;
import com.hug.takerest.gank.adapter.GankAdapter;
import com.hug.takerest.gank.model.Gank;
import com.hug.takerest.gank.model.GankData;
import com.hug.takerest.gank.presenter.GankContract;
import com.hug.takerest.gank.presenter.GankPresenter;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.util.GetFrescoBitmap;
import com.hug.takerest.util.ToastUtil;
import com.hug.takerest.widget.SaveMeiziPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/3/30.
 */

public class MeiziFragment extends BaseFragment implements GankContract.View {
    @BindView(R.id.meizi_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GankPresenter presenter;
    private GankAdapter mAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private String sType = "福利";
    private int PAGE = 1;
    private List<Gank> mList;
    private SaveMeiziPopWindow saveMeiziPopWindow;

    private Gank selectedGank;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meizi, container, false);
        ButterKnife.bind(this, rootView);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        retrofit.initRetrofit(getActivity(), RetrofitManager.GANK_TYPE);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 20);
        presenter = new GankPresenter(this);
        presenter.start(sType, C.COUNT, 1);
        bind();
        return rootView;
    }

    private void bind() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList = new ArrayList<>();
                presenter.start(sType, C.COUNT, 1);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
                int lastVisiblePos = getMaxElem(lastVisiblePositions);
                int totalItemCount = manager.getItemCount();
                if (lastVisiblePos == (totalItemCount - 1) && dy > 0) {
                    PAGE++;
                    presenter.add(PAGE);
                }
            }
        });
    }

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

    @Override
    public void onSuccess(GankData gankData) {
        mList = new ArrayList<>();
        mList = gankData.getResults();
        if (mAdapter != null) {
            mAdapter.dataChanged(mList);
        } else {
            mAdapter = new GankAdapter(getActivity(), mList);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setOnItemClickLitener(new GankAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, Gank gank) {
                Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                intent.putExtra("url", gank.getUrl());
                intent.putExtra("transition_name", Transition.MEIZI_TRANSITION);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), view, Transition.MEIZI_TRANSITION);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, final Gank gank, int x, int y) {
                if (saveMeiziPopWindow == null) {
                    saveMeiziPopWindow = new SaveMeiziPopWindow(getActivity());
                }
                selectedGank = gank;
                saveMeiziPopWindow.show(view, x, y - view.getHeight());
                saveMeiziPopWindow.setSaveAndShareListener(new SaveMeiziPopWindow.ISaveAndShareListener() {
                    @Override
                    public void save() {
                        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext()
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    C.SAVE_REQUEST);
                        } else {
                            saveImage(gank.getUrl());
                        }
                    }

                    @Override
                    public void share() {
                        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext()
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    C.SHARE_REQUEST);
                        } else {
                            shareImage(gank.getUrl());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void add(GankData gankData) {
        if (gankData != null && gankData.getResults().size() > 0) {
            int position = mList.size() - 1;
            mList.addAll(gankData.getResults());
            mAdapter.dataInsert(position, mList);
        }
    }

    public void saveImage(String url) {
        GetFrescoBitmap.saveImage(getActivity(), url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uri>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Uri value) {
                        if (value != null) {
                            ToastUtil.makeTextShort(getActivity(), getString(R.string.save_success) + value.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.makeTextShort(getActivity(), getString(R.string.save_error) + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void shareImage(String url) {
        GetFrescoBitmap.saveImage(getActivity(), url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uri>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Uri value) {
                        if (value != null) {
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, value);
                            shareIntent.setType("image/jpeg");
                            startActivity(Intent.createChooser(shareIntent, ""));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == C.SAVE_REQUEST){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (selectedGank != null){
                    saveImage(selectedGank.getUrl());
                }
            }else{
                ToastUtil.makeTextShort(getActivity(),getString(R.string.storage_permission_refuse));
            }
        }
        if (requestCode == C.SHARE_REQUEST){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (selectedGank != null){
                    shareImage(selectedGank.getUrl());
                }
            }else{
                ToastUtil.makeTextShort(getActivity(),getString(R.string.storage_permission_refuse));
            }
        }
    }

    @Override
    public void onShowProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onError(String t) {
        ToastUtil.makeTextShort(getActivity(), t);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
