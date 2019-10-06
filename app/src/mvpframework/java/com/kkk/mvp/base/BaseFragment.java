package com.kkk.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.android.libloadingview.LoadingView;
import com.android.wanandroid.R;
import com.android.wanandroid.app.BaseApp;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/27 9:42
 */
public abstract class BaseFragment extends RxFragment {

    private LoadingView mLoadingView;
    protected BaseActivity mBaseActivity;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity) activity;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(mBaseActivity, view);
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        initData();
        initListener();
    }

    protected void initListener() {}

    protected void initData() {}

    protected abstract int getLayoutId();

    protected abstract void initView(View root);

    protected <T extends View> T onViewCreatedBind(@IdRes int id, View.OnClickListener listener){
        T t = getView().findViewById(id);
        t.setOnClickListener(listener);
        return t;
    }
    protected BaseFragment addFragment(FragmentManager manager,Class<? extends BaseFragment> aClass,@IdRes int containerId,Bundle args){
        if(mBaseActivity != null){
            return mBaseActivity.addFragment(manager,aClass,containerId,args);
        }
        return null;
    }

    private ViewGroup getParentViewGroup(){
        return (ViewGroup) getView().getParent();
    }

    protected abstract void cancelRequest();

    protected void showLoading(@LoadingView.LoadingMode int mode){
        showLoading(mode,getParentViewGroup(),false);
    }

    protected void showLoading(@LoadingView.LoadingMode int mode,@IdRes int containerId,boolean cancelAble){
        showLoading(mode,(ViewGroup) getView().findViewById(containerId),cancelAble);
    }

    protected void showLoading(@LoadingView.LoadingMode int mode,ViewGroup group,boolean cancelAble){
        mLoadingView = LoadingView.inject(mBaseActivity, group);
        if(cancelAble){
            mLoadingView.setCloseListener(new LoadingView.OnCancelListener() {
                @Override
                public void onCancel() {
                    cancelRequest();
                }
            });
        }else{
            mLoadingView.setCloseListener(null);
        }
        mLoadingView.show(mode,cancelAble);
    }

    protected void onError(String msg, LoadingView.RetryCallBack callBack){
        if(mLoadingView != null && mLoadingView.isShow()){// 如果这个loading 页面已经被添加显示
            mLoadingView.onError(msg,callBack);
        }
    }

    protected void closeLoading(){
        if(mLoadingView != null && mLoadingView.isShow()){// 如果这个loading 页面已经被添加显示
            mLoadingView.close();
        }
    }

    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    protected void back(){
        getFragmentManager().popBackStack();
    }

    public int enter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_in;
    }

    public int exit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_out;
    }

    public int popEnter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_in;
    }

    public int popExit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_out;
    }

    public boolean isNeedAddToBackStack() {
        return true;
    }

    public boolean isNeedAnimation() {
        return true;
    }

    protected void showToast(@StringRes int id){
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@NonNull String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
