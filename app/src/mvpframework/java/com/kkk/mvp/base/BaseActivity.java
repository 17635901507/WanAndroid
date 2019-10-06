package com.kkk.mvp.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.libloadingview.LoadingView;
import com.android.wanandroid.R;
import com.kkk.mvp.utils.ActivityCollector;
import com.kkk.mvp.utils.Logger;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

public class BaseActivity extends RxAppCompatActivity {

    private LoadingView mLoadingView;
    private String mLogTag;

    protected <T extends View> T onViewCreatedBind(@IdRes int id, View.OnClickListener listener) {
        T t = this.findViewById(id);
        t.setOnClickListener(listener);
        return t;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogTag = getClass().getSimpleName();
        Logger.d("BaseActivity %s", mLogTag);
        ActivityCollector.addActivity(this);

    }

    /**
     * 用于显示一个fragment到指定的ViewGroup id,如果这个fragment 没有被添加，则调用add 方法进行添加，如果已经添加，但是被隐藏了，那么直接显示。
     *
     * @param manager     : FragmentManager
     * @param aClass      : 需要显示的fragment
     * @param containerId : fragment 被添加到的ViewGroup id
     * @param args        : Bundle 用于参数传递
     * @return : 返回添加的fragment
     * <p>
     * *先查询FragmentManager所在的Activity是否已经添加了这个fragment
     * *第一步：先从一个mAdded 的ArrayList 遍历查找，如果找不到再从一个叫 mActive 的 SparseArray 的一个
     * *map 里边查找。
     * *
     * *注意：
     * *1、一个Fragment被remove后，只会从mAdded里边删除，不会从mActive里边删除，只有当这个Fragment所在的transaction
     * *从回退栈里边移除后 才会从 mActive 里边删除
     * *2、当我们add 一个Fragment 时，会把我们的Fragment添加到mAdded里边，不会添加到mActive里边。
     * *3、只有当我们把transaction 添加到回退栈的时候，才会把我们的Fragment 添加到mActive里边。
     * *
     * *
     * *
     * *所以说我们通过 findFragmentByTag 找出来的fragment 可能已经被remove了，但是由于 加入了回退栈，因此依然存在与 mActive 中。
     */

    protected BaseFragment addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, @IdRes int containerId, Bundle args) {
        Logger.d("%s add %s", mLogTag, aClass.getSimpleName());
        //step 1：从FragmentManager中通过tag查找 fragment，如果能找到就不用 new 新的fragment，找不到则new 新的。

        String tag = aClass.getName();

        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction();
        BaseFragment baseFragment = null;
        if (fragment == null) {
            try {
                baseFragment = aClass.newInstance();//通过反射new 出一个fragment 的实例
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            if (baseFragment == null) {
                throw new UnsupportedOperationException(tag + "必须提供无参构造方法");
            }

            //设置fragment 进入 进出，弹进，弹出 动画
            transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());

            transaction.add(containerId,baseFragment,tag);//add 一个fragment
            if(baseFragment.isNeedAddToBackStack()){
                transaction.addToBackStack(tag);
            }
        }else{
            baseFragment = (BaseFragment) fragment;
            if(baseFragment.isAdded()){
                if(baseFragment.isHidden()){
                    transaction.show(baseFragment);
                }
            }else{
                transaction.add(containerId,baseFragment,tag);//add 一个fragment
            }
        }

        //step 1.1:隐藏之前的fragment
        hideBeforeFragment(manager,transaction,baseFragment);
        baseFragment.setArguments(args);
        transaction.commit();
        return baseFragment;
    }

    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment curFragment) {
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment:fragments) {
            if(fragment != curFragment && !fragment.isHidden()){
                transaction.hide(fragment);
            }
        }
    }

    protected void showToast(@StringRes int id){
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@NonNull String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLoading(@LoadingView.LoadingMode int mode){
        showLoading(mode,android.R.id.content,false);
    }

    protected void showLoading(@LoadingView.LoadingMode int mode,boolean cancelAble){
        showLoading(mode,android.R.id.content,cancelAble);
    }

    protected void showLoading(@LoadingView.LoadingMode int mode,@IdRes int containerId,boolean cancelAble){
        showLoading(mode,(ViewGroup)findViewById(containerId),cancelAble);
    }

    protected void showLoading(@LoadingView.LoadingMode int mode,ViewGroup group,boolean cancelAble){
        mLoadingView = LoadingView.inject(this, group);

        if(cancelAble){
            mLoadingView.setCloseListener(() -> cancelRequest());
        }else {
            mLoadingView.setCloseListener(null);
        }
        mLoadingView.show(mode,cancelAble);
    }

    public void onError(String msg, LoadingView.RetryCallBack callBack){
        if(mLoadingView != null && mLoadingView.isShow()){
            mLoadingView.onError(msg,callBack);
        }
    }

    //取消请求
    private void cancelRequest() {
        closeLoading();
    }

    protected void closeLoading() {
        if(mLoadingView != null && mLoadingView.isShow()){// 如果这个loading 页面已经被添加显示
            mLoadingView.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
