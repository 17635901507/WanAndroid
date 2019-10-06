package com.kkk.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 18:41
 */
public abstract class MvpBaseFragment<P extends IBasePresenter> extends BaseFragment implements IBaseView<P> {
    protected P mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPresenter = createPresenter();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void cancelRequest() {
        mPresenter.cancelRequest();
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
    }

    @Override
    public Activity getActivityObj() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
