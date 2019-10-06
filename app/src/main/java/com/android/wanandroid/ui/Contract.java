package com.android.wanandroid.ui;

import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.entity.BannerInfo;
import com.android.wanandroid.data.entity.Tags;
import com.android.wanandroid.data.entity.know.Children;
import com.android.wanandroid.data.entity.know.Knowledge;
import com.android.wanandroid.data.entity.nv.NvArticles;
import com.android.wanandroid.data.entity.nv.NvData;
import com.android.wanandroid.data.entity.pro.ProTab;
import com.android.wanandroid.data.entity.search.Useful;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.base.IBasePresenter;
import com.kkk.mvp.base.IBaseView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 9:14
 *
 * 主页四个界面的契约类
 */
public interface Contract {
    //--------------------- 首页 Home -------------------------
    interface IHomeView extends IBaseView<IHomePresenter>{
        void onBannerSuccess(List<BannerInfo> banners);
        void onArticleSuccess(List<ArticleInfo<Tags>> articles);
        void onFail(String msg);
    }

    interface IHomePresenter extends IBasePresenter<IHomeView>{
        void getBanner();
        void getArticle(int page);
    }
    //--------------------- 首页 Home -------------------------


    //--------------------- 常用网站 -------------------------
    interface IUsefulView extends IBaseView<IUsefulPresenter>{
        void onSuccess(List<Useful> usefuls);
        void onFail(String msg);
    }
    interface IUsefulPresenter extends IBasePresenter<IUsefulView>{
        void getUseful();
    }
    //--------------------- 常用网站 -------------------------


    //--------------------- 知识体系 -------------------------
    interface IKnowledgeView extends IBaseView<IKnowledgePresenter>{
        void onSuccess(List<Knowledge<Children>> knowledges);
        void onFail(String msg);
    }
    interface IKnowledgePresenter extends IBasePresenter<IKnowledgeView>{
        void getKnowledge();
    }
    //--------------------- 知识体系 -------------------------


    //--------------------- 知识体系下的文章 -------------------------
    interface IKnowArticleView extends IBaseView<IKnowArticlePresenter>{
        void onSuccess(Article<ArticleInfo> articles);
        void onFail(String msg);
    }
    interface IKnowArticlePresenter extends IBasePresenter<IKnowArticleView>{
        void getKnowArticle(int page,int cid);
    }
    //--------------------- 知识体系下的文章 -------------------------


    //--------------------- 按照作者昵称搜索文章 -------------------------
    interface IAuthorArticleView extends IBaseView<IAuthorArticlePresenter>{
        void onSuccess(Article<ArticleInfo> articles);
        void onFail(String msg);
    }
    interface IAuthorArticlePresenter extends IBasePresenter<IAuthorArticleView>{
        void getAuthorArticle(int page,String author);
    }
    //--------------------- 按照作者昵称搜索文章 -------------------------


    //--------------------- 导航数据 -------------------------
    interface INvView extends IBaseView<INvPresenter>{
        void onSuccess(List<NvData<NvArticles>> nvDataList);
        void onFail(String msg);
    }
    interface INvPresenter extends IBasePresenter<INvView>{
        void getNvData();
    }
    //--------------------- 导航数据 -------------------------


    //--------------------- 项目分类 -------------------------
    interface IProTabView extends IBaseView<IProTabPresenter>{
        void onSuccess(List<ProTab> proTabs);
        void onFail(String msg);
    }
    interface IProTabPresenter extends IBasePresenter<IProTabView>{
        void getProTab();
    }
    //--------------------- 项目分类 -------------------------


    //--------------------- 项目列表 -------------------------
    interface IProArticleView extends IBaseView<IProArticlePresenter>{
        void onSuccess(Article<ArticleInfo<Tags>> articleInfoArticle);
        void onFail(String msg);
    }
    interface IProArticlePresenter extends IBasePresenter<IProArticleView>{
        void getProArticle(int page,int cid);
    }
    //--------------------- 项目列表 -------------------------


    interface IMainModel{
        void getBanner(LifecycleProvider provider, IBaseCallBack<List<BannerInfo>> callBack);
        void getArticle(LifecycleProvider provider,int page,IBaseCallBack<List<ArticleInfo<Tags>>> callBack);
        void getUseful(LifecycleProvider provider,IBaseCallBack<List<Useful>> callBack);
        void getKnowledge(LifecycleProvider provider,IBaseCallBack<List<Knowledge<Children>>> callBack);
        void getKnowArticle(LifecycleProvider provider,int page,int cid,IBaseCallBack<Article<ArticleInfo>> callBack);
        void getAuthorArticle(LifecycleProvider provider,int page,String author,IBaseCallBack<Article<ArticleInfo>> callBack);
        void getNvData(LifecycleProvider provider,IBaseCallBack<List<NvData<NvArticles>>> callBack);
        void getProTab(LifecycleProvider provider,IBaseCallBack<List<ProTab>> callBack);
        void getProArticle(LifecycleProvider provider,int page,int cid,IBaseCallBack<Article<ArticleInfo<Tags>>> callBack);
    }
}
