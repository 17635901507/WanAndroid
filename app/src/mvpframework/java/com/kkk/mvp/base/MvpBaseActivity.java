package com.kkk.mvp.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 18:34
 */
public abstract class MvpBaseActivity<P extends IBasePresenter> extends BaseActivity implements IBaseView<P>{
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();

        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }


    @Override
    public void closeLoading() {
        super.closeLoading();
    }

    @Override
    public Activity getActivityObj() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
