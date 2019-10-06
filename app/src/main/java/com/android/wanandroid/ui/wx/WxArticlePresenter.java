package com.android.wanandroid.ui.wx;

import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.repositories.MainRepository;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BasePresenter;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 19:47
 */
public class WxArticlePresenter extends BasePresenter<Contract.IAuthorArticleView> implements Contract.IAuthorArticlePresenter {
    private MainRepository mModel;
    public WxArticlePresenter(){
        mModel = MainRepository.getInstance();
    }
    @Override
    public void getAuthorArticle(int page, String author) {
        if(mModel != null){
            mModel.getAuthorArticle(getLifecycleProvider(), page, author, new IBaseCallBack<Article<ArticleInfo>>() {
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
