package com.android.wanandroid.ui.knowledge.p;

import com.android.wanandroid.data.entity.know.Children;
import com.android.wanandroid.data.entity.know.Knowledge;
import com.android.wanandroid.data.repositories.MainRepository;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BasePresenter;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 11:20
 */
public class KnowPresenter extends BasePresenter<Contract.IKnowledgeView> implements Contract.IKnowledgePresenter {
    private MainRepository mModel;
    public KnowPresenter(){
        mModel = MainRepository.getInstance();
    }
    @Override
    public void getKnowledge() {
        if(mModel != null){
            mModel.getKnowledge(getLifecycleProvider(), new IBaseCallBack<List<Knowledge<Children>>>() {
                @Override
                public void onSuccess(List<Knowledge<Children>> data) {
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
