package com.kkk.mvp.base;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.IdRes;

import com.kkk.mvp.utils.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.disposables.Disposable;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 19:05
 */
public class BasePresenter<V extends IBaseView> implements IBasePresenter<V>{
    private Disposable mRecentRequest;
    protected V mView;

    protected LifecycleProvider getLifecycleProvider(){
        if(mView != null){
            return (LifecycleProvider) mView;
        }
        return null;
    }

    @SuppressLint("ResourceType")
    protected String getString(@IdRes int id){
        if(mView != null){
            Activity activity = mView.getActivityObj();
            if(activity != null){
                return activity.getString(id);
            }
        }
        return "";
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void cacheRequest(Disposable disposable) {
        Logger.d("%s ++++++++",getClass().getSimpleName());
        mRecentRequest = disposable;
    }

    @Override
    public void cancelRequest() {
        if(mRecentRequest != null){
            mRecentRequest.dispose();
            Logger.d("%s ------  ", getClass().getSimpleName());
        }
    }
}
