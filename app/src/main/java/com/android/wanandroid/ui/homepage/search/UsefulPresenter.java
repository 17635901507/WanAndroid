package com.android.wanandroid.ui.homepage.search;

import com.android.wanandroid.data.entity.search.Useful;
import com.android.wanandroid.data.repositories.MainRepository;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BasePresenter;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/3 21:01
 */
public class UsefulPresenter extends BasePresenter<Contract.IUsefulView> implements Contract.IUsefulPresenter {
    private MainRepository mModel;
    public UsefulPresenter(){
        mModel = MainRepository.getInstance();
    }
    @Override
    public void getUseful() {
        if(mModel != null){
            mModel.getUseful(getLifecycleProvider(), new IBaseCallBack<List<Useful>>() {
                @Override
                public void onSuccess(List<Useful> data) {
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
