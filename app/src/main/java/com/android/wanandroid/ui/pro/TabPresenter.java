package com.android.wanandroid.ui.pro;

import com.android.wanandroid.data.entity.pro.ProTab;
import com.android.wanandroid.data.repositories.MainRepository;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BasePresenter;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/5 21:51
 */
public class TabPresenter extends BasePresenter<Contract.IProTabView> implements Contract.IProTabPresenter {
    private MainRepository mModel;

    public TabPresenter() {
        mModel = MainRepository.getInstance();
    }

    @Override
    public void getProTab() {
        if(mModel != null){
            mModel.getProTab(getLifecycleProvider(), new IBaseCallBack<List<ProTab>>() {
                @Override
                public void onSuccess(List<ProTab> data) {
                    mView.onSuccess(data);
                }

                @Override
                public void onFail(ResultException e) {
                    mView.onFail(e.getMessage());
                }
            });
        }
    }
}
