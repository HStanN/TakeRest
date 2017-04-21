package com.hug.takerest.movie.ui;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.beltaief.flowlayout.FlowLayout;
import com.hug.takerest.R;
import com.hug.takerest.base.BaseFragment;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.movie.adapter.MovieAdapter;
import com.hug.takerest.movie.model.Movie;
import com.hug.takerest.movie.presenter.OnShownMovieContract;
import com.hug.takerest.movie.presenter.OnShownMoviePresenter;
import com.hug.takerest.util.DividerItemDecoration;
import com.hug.takerest.util.FlowLayoutManager;
import com.hug.takerest.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HStan on 2017/3/28.
 */

public class OnShownMovieFragment extends BaseFragment implements OnShownMovieContract.View, AMapLocationListener {
    private MovieAdapter adapter;
    private OnShownMoviePresenter presenter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.flowlayout)
     FlowLayout flowLayout;
    private FlowLayoutManager flowLayoutManager;

    public AMapLocationClient mLocationClient = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_onshown_movie, container, false);
        ButterKnife.bind(this, rootView);
        flowLayoutManager = new FlowLayoutManager(flowLayout);
        showProgress();
        checkPermission();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        retrofit.initRetrofit(getActivity(), RetrofitManager.MOVIE_TYPE);
        return rootView;
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext()
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }else{
            initLocation();
        }
    }

    private void initLocation() {
        showProgress();
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        mLocationClient.setLocationListener(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setHttpTimeOut(20000);
        option.setOnceLocation(true);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    @Override
    public void onError(String t) {
        ToastUtil.makeTextShort(getActivity(), t);
        flowLayoutManager.showError();
        flowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start();
            }
        });
    }

    @Override
    public void onSuccess(final List<Movie> list) {
        adapter = new MovieAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new MovieAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = list.get(position).getId();
                Intent intent = new Intent(getActivity(), MovieBasicActivity.class);
                intent.putExtra("id", id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), view, "movie_image");
                    startActivity(intent, options.toBundle());
                } else {
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
    }

    @Override
    public void setPresenter(OnShownMovieContract.Presenter presenter) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                initLocation();
            }else{
                ToastUtil.makeTextShort(getActivity(),getString(R.string.location_permission_refuse));
                flowLayoutManager.setEmpty();
                flowLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPermission();
                    }
                });
            }
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                presenter = new OnShownMoviePresenter(this, aMapLocation.getCity());
                presenter.start();
            } else {
                ToastUtil.makeTextShort(getActivity(), getString(R.string.error_location) + " code:" + aMapLocation.getErrorCode()
                        + " msg:" + aMapLocation.getErrorInfo());
            }
        }
    }
}
