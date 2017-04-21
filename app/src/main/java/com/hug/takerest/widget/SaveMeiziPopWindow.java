package com.hug.takerest.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hug.takerest.R;

/**
 * Created by HStan on 2017/4/5.
 */

public class SaveMeiziPopWindow {
    private PopupWindow popupWindow;
    private Context mContext;
    private ISaveAndShareListener iSaveAndShareListener;

    public SaveMeiziPopWindow(Context context){
        this.mContext = context;
        initPopupWindow();
    }

    public interface ISaveAndShareListener{
        void save();
        void share();
    }

    protected void initPopupWindow(){
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.save_img_popwindow, null);
        TextView mSave = (TextView) contentView.findViewById(R.id.save_img_to_local);
        TextView mShare = (TextView) contentView.findViewById(R.id.share_to_friends);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (iSaveAndShareListener != null){
                    iSaveAndShareListener.save();
                }
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (iSaveAndShareListener != null){
                    iSaveAndShareListener.share();
                }
            }
        });
    }

    public void setSaveAndShareListener(ISaveAndShareListener iSaveAndShareListener){
        this.iSaveAndShareListener = iSaveAndShareListener;
    }

    public void show(View view,int x, int y){
        if (popupWindow != null){
            popupWindow.showAsDropDown(view,x,y);
        }else{
            throw new NullPointerException("popupWindow is null");
        }
    }
}
