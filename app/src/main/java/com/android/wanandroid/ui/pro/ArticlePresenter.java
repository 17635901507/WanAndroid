package com.android.wanandroid.ui.pro;

import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.entity.Tags;
import com.android.wanandroid.data.repositories.MainRepository;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BasePresenter;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/5 22:55
 */
public class ArticlePresenter extends BasePresenter<Contract.IProArticleView> implements Contract.IProArticlePresenter {
    private MainRepository mModel;

    public ArticlePresenter() {
        mModel = MainRepository.getInstance();
    }
    @Override
    public void getProArticle(int page, int cid) {
        if(mModel != null){
            mModel.getProArticle(getLifecycleProvider(), page, cid, new IBaseCallBack<Article<ArticleInfo<Tags>>>() {
                @Override
                public void onSuccess(Article<ArticleInfo<Tags>> data) {
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
