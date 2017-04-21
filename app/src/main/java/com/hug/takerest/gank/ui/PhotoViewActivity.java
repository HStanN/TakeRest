package com.hug.takerest.gank.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.hug.takerest.R;
import com.hug.takerest.base.BaseActivity;
import com.hug.takerest.constants.C;
import com.hug.takerest.util.GetFrescoBitmap;
import com.hug.takerest.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by HStan on 2017/3/30.
 */

public class PhotoViewActivity extends BaseActivity {
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.photoview)
    PhotoDraweeView photoDraweeView;
    @BindView(R.id.iv_save)
    ImageView mSave;
    @BindView(R.id.iv_share)
    ImageView mShare;
    String url;
    private float normalScale;

    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_photoview);
    }

    @Override
    protected void init() {
        super.init();
        transparentStatusBar();
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse(url));
        controller.setAutoPlayAnimations(true);
        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || photoDraweeView == null) {
                    return;
                }
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                normalScale = photoDraweeView.getScale();
            }
        });
        photoDraweeView.setController(controller.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            String name = getIntent().getStringExtra("transition_name");
            photoDraweeView.setTransitionName(name);
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.FIT_CENTER));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.FIT_CENTER, ScalingUtils.ScaleType.CENTER_CROP));
        }
    }


    @Override
    protected void bind() {
        super.bind();
        photoDraweeView.setAllowParentInterceptOnEdge(true);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext()
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PhotoViewActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            C.SAVE_REQUEST);
                } else {
                    save();
                }
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext()
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PhotoViewActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            C.SHARE_REQUEST);
                } else {
                    share();
                }
            }
        });
    }

    private void save(){
        GetFrescoBitmap.saveImage(PhotoViewActivity.this, url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uri>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Uri value) {
                        if (value != null) {
                            ToastUtil.makeTextShort(PhotoViewActivity.this, getString(R.string.save_success) + value.toString());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.makeTextShort(PhotoViewActivity.this, getString(R.string.save_error) +
                                e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void share(){
        GetFrescoBitmap.saveImage(PhotoViewActivity.this, url)
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
                save();
            }else{
                ToastUtil.makeTextShort(this,getString(R.string.storage_permission_refuse));
            }
        }
        if (requestCode == C.SHARE_REQUEST){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                share();
            }else{
                ToastUtil.makeTextShort(this,getString(R.string.storage_permission_refuse));
            }
        }
    }

    @Override
    public void onBackPressed() {
        DraweeView<GenericDraweeHierarchy> draweeView = photoDraweeView.getAttacher().getDraweeView();
        photoDraweeView.setScale(normalScale,draweeView.getRight()/2,draweeView.getBottom()/2,true);
        super.onBackPressed();
    }
}
