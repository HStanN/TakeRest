package com.hug.takerest.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HStan on 2017/3/30.
 */

public class GetFrescoBitmap {

    public static Observable<Uri> saveImage(final Context context,final String url) {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {

            @Override
            public void subscribe(final ObservableEmitter<Bitmap> e) throws Exception {
                ImageRequest imageRequest = ImageRequestBuilder
                        .newBuilderWithSource(Uri.parse(url))
                        .setProgressiveRenderingEnabled(true)
                        .build();
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                DataSource<CloseableReference<CloseableImage>>
                        dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
                dataSource.subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    public void onNewResultImpl(Bitmap bitmap) {
                        if (bitmap == null) {
                            Logger.e("image:"+Uri.parse(url).toString()+"\nbitmap is null");
                            return;
                        } else {
                            e.onNext(bitmap);
                            return;
                        }
                    }

                    @Override
                    public void onFailureImpl(DataSource dataSource) {
                    }
                }, CallerThreadExecutor.getInstance());
            }
        }).flatMap(new Function<Bitmap, ObservableSource<Uri>>() {
            @Override
            public ObservableSource<Uri> apply(Bitmap bitmap) throws Exception {
                File appDir = new File(Environment.getExternalStorageDirectory(), "TakeRest_Meizi");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = url.replace("/","-");
                File file = null;
                if (url.contains(".gif")){
//                    String name = fileName.replace(".","");
//                    file = new File(appDir, fileName+ ".gif");
//                    try {
//                        FileOutputStream fos = new FileOutputStream(file);
//                        fos.write(.getData());//gif is gif image object
//                        fos.flush();
//                        fos.close();
//                    } catch (IOException ioe) {
//                        ioe.printStackTrace();
//                    }
                }else{
                    file = new File(appDir, fileName);
                    if (!file.exists()){
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Uri u = Uri.fromFile(file);
                return Observable.just(u);
            }
        }).subscribeOn(Schedulers.io());
    }
}
