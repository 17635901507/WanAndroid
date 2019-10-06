package com.android.wanandroid.ui.knowledge.p;

import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.repositories.MainRepository;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BasePresenter;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 12:55
 */
public class KnowArticlePresenter extends BasePresenter<Contract.IKnowArticleView> implements Contract.IKnowArticlePresenter {
    private MainRepository mModel;
    public KnowArticlePresenter(){
        mModel = MainRepository.getInstance();
    }

    @Override
    public void getKnowArticle(int page,int cid) {
        if(mModel != null){
            mModel.getKnowArticle(getLifecycleProvider(),page,cid, new IBaseCallBack<Article<ArticleInfo>>() {
                @Override
                public void onSuccess(Article<ArticleInfo> data) {
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
