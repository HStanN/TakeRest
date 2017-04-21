package com.hug.takerest.shots.ui;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.WebActivity;
import com.hug.takerest.base.BaseActivity;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.adapter.UserPageFragmentAdapter;
import com.hug.takerest.shots.model.User;
import com.hug.takerest.shots.presenter.UserContract;
import com.hug.takerest.shots.presenter.UserPresenter;
import com.hug.takerest.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends BaseActivity implements UserContract.View {
    @BindView(R.id.avator)
    SimpleDraweeView mAvator;
    @BindView(R.id.user_background)
    SimpleDraweeView mBackground;
    @BindView(R.id.user_bio)
    TextView mBio;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.follows)
    TextView mFollows;
    @BindView(R.id.user_homepage)
    ImageView mHomepage;
    @BindView(R.id.user_twitter)
    ImageView mTwitter;
    @BindView(R.id.tab_layout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private String FOLLOWERS = "followers:";
    private String FOLLOWINGS = "followings:";
    private String DIVIDER_STRING = "\n";
    private String BLANK = "\b";

    private String web_url;
    private String twitter_url;

    private UserPresenter mPresenter;
    private UserShotFragment shotsFragment;
    private UserShotFragment projectFragment;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_user);
    }

    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
        initToolbar();
        setBackEnable(true);
        getToolbar().setTitle("");
        mBackground.setImageURI("res://" + getPackageName() + "/" + R.mipmap.user_bg);
        retrofit.initRetrofit(this, RetrofitManager.SHOT_TYPE);
        mPresenter = new UserPresenter(this);
        mPresenter.start(getIntent().getIntExtra("id", 0));
    }

    private void initTab(int id) {
        shotsFragment = new UserShotFragment().newInstance(id, UserShotFragment.SHOT);
        projectFragment = new UserShotFragment().newInstance(id, UserShotFragment.PROJECT);
        fragments.add(shotsFragment);
        fragments.add(projectFragment);
        UserPageFragmentAdapter adapter = new UserPageFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void bind() {
        super.bind();
        mHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, WebActivity.class);
                intent.putExtra("url", web_url);
                startActivity(intent);
            }
        });
        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, WebActivity.class);
                intent.putExtra("url", twitter_url);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccess(User user) {
        initTab(user.getId());
        mAvator.setImageURI(user.getAvatar_url());
        getToolbar().setTitle(user.getName());
        StringBuilder sb = new StringBuilder();
        sb.append(FOLLOWERS);
        sb.append(BLANK);
        sb.append(user.getFollowers_count());
        sb.append(BLANK);
        sb.append(DIVIDER_STRING);
        sb.append(BLANK);
        sb.append(FOLLOWINGS);
        sb.append(BLANK);
        sb.append(user.getFollowings_count());
        mFollows.setText(sb.toString());
        if (user.getLocation() != null) {
            mLocation.setText(user.getLocation());
        }
        if (user.getBio() != null) {
            mBio.setText(Html.fromHtml(user.getBio()));
        } else {
            mBio.setText(getString(R.string.no_description));
        }
        if (user.getLinks() != null) {
            if (user.getLinks().getWeb() != null) {
                web_url = user.getLinks().getWeb();
                mHomepage.setVisibility(View.VISIBLE);
            } else {
                mHomepage.setVisibility(View.GONE);
            }
            if (user.getLinks().getTwitter() != null) {
                twitter_url = user.getLinks().getTwitter();
                mTwitter.setVisibility(View.VISIBLE);
            } else {
                mTwitter.setVisibility(View.GONE);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onError(String t) {
        ToastUtil.makeTextShort(this, t);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dispose();
    }
}
