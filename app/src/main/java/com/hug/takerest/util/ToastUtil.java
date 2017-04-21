package com.hug.takerest.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HStan on 2017/4/1.
 */

public class ToastUtil {

    public static void makeTextShort(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void makeTextLong(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
