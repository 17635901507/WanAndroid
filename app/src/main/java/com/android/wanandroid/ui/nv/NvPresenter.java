package com.android.wanandroid.ui.nv;

import com.android.wanandroid.data.entity.nv.NvArticles;
import com.android.wanandroid.data.entity.nv.NvData;
import com.android.wanandroid.data.repositories.MainRepository;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BasePresenter;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 21:34
 */
public class NvPresenter extends BasePresenter<Contract.INvView> implements Contract.INvPresenter {
    private MainRepository mModel;
    public NvPresenter(){
        mModel = MainRepository.getInstance();
    }
    @Override
    public void getNvData() {
        if(mModel != null){
            mModel.getNvData(getLifecycleProvider(), new IBaseCallBack<List<NvData<NvArticles>>>() {
                @Override
                public void onSuccess(List<NvData<NvArticles>> data) {
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
