package com.hug.takerest.shots.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by HStan on 2017/4/14.
 */

public class UserPageFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles = {"Shots","Projects"};
    private List<Fragment> mlist;

    public UserPageFragmentAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public Fragment getItem(int position) {
            return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
