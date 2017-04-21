package com.hug.takerest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.hstan_avatar)
    SimpleDraweeView mAvatar;
    @BindView(R.id.version_name)
    TextView mVersionName;
    @BindView(R.id.header_bg)
    SimpleDraweeView mHeader;

    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
        initToolbar();
        setBackEnable(true);

        mAvatar.setImageURI("res://" + getPackageName() + "/" + R.mipmap.hstan_avator);
        mHeader.setImageURI("res://" + getPackageName() + "/" + R.mipmap.about_header_bg);
        mVersionName.setText(getVersion());
    }

    @Override
    protected void bind() {
        super.bind();
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,WebActivity.class);
                intent.putExtra("url",getString(R.string.my_github));
                startActivity(intent);
            }
        });
    }

    /**
     * 获取APP版本号
     * */
    public String getVersion(){
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo  packInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
