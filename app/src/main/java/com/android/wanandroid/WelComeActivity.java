package com.android.wanandroid;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.kkk.mvp.base.BaseActivity;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 20:07
 */
public class WelComeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().postDelayed(() -> {
            MainActivity.actionStart(WelComeActivity.this);
            overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
            finish();
        },3000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
    }
}
