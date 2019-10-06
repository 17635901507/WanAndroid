package com.android.wanandroid.ui.homepage;

import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.entity.BannerInfo;
import com.android.wanandroid.data.entity.Tags;
import com.android.wanandroid.data.repositories.MainRepository;
import com.kkk.mvp.base.BasePresenter;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 10:01
 */
public class HomePresenter extends BasePresenter<Contract.IHomeView> implements Contract.IHomePresenter {
    private MainRepository mModel;
    public HomePresenter(){
        mModel = MainRepository.getInstance();
    }
    @Override
    public void getBanner() {
        if(mModel !=  null){
            mModel.getBanner(getLifecycleProvider(), new IBaseCallBack<List<BannerInfo>>() {
                @Override
                public void onSuccess(List<BannerInfo> data) {
                    mView.onBannerSuccess(data);
                }

                @Override
                public void onFail(ResultException e) {
                    mView.onFail(e.getMessage());
                }
            });
        }
    }

    @Override
    public void getArticle(int page) {
        if(mModel != null){
            mModel.getArticle(getLifecycleProvider(), page, new IBaseCallBack<List<ArticleInfo<Tags>>>() {


                @Override
                public void onSuccess(List<ArticleInfo<Tags>> data) {
                    mView.onArticleSuccess(data);
                }

                @Override
                public void onFail(ResultException e) {
                    mView.onFail(e.getMessage());
                }
            });
        }
    }

}
