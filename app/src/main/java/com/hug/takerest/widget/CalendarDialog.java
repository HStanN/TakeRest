package com.hug.takerest.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by HStan on 2017/5/9.
 */

public class CalendarDialog {
    private Context context;
    private android.app.DatePickerDialog dialog;
    private DatePickerListener datePickerListener;

    public CalendarDialog(Context context,DatePickerListener datePickerListener){
        this.context =context;
        this.datePickerListener = datePickerListener;
    }


    public interface DatePickerListener{
        void onDateSet(int year,int month,int day);
    }

    public void show(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        dialog = new android.app.DatePickerDialog(context,mOnDateSetListener,year,monthOfYear,dayOfMonth);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            if (datePickerListener != null){
                datePickerListener.onDateSet(i,i1+1,i2);
            }
        }
    };
}
