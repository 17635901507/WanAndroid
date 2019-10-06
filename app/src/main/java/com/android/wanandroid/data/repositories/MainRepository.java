package com.android.wanandroid.data.repositories;

import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.entity.BannerInfo;
import com.android.wanandroid.data.entity.HttpResult;
import com.android.wanandroid.data.entity.Tags;
import com.android.wanandroid.data.entity.know.Children;
import com.android.wanandroid.data.entity.know.Knowledge;
import com.android.wanandroid.data.entity.nv.NvArticles;
import com.android.wanandroid.data.entity.nv.NvData;
import com.android.wanandroid.data.entity.pro.ProTab;
import com.android.wanandroid.data.entity.search.Useful;
import com.android.wanandroid.data.okhttp.DataService;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BaseRepository;
import com.kkk.mvp.base.IBaseCallBack;
import com.kkk.mvp.exceptions.ResultException;
import com.trello.rxlifecycle2.LifecycleProvider;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 9:19
 */
public class MainRepository extends BaseRepository implements Contract.IMainModel {
    private static volatile MainRepository mInstance;
    private MainRepository(){

    }

    public static MainRepository getInstance() {
        if(mInstance == null){
            synchronized (MainRepository.class){
                if(mInstance == null){
                    mInstance = new MainRepository();
                }
            }
        }
        return mInstance;
    }
    @Override
    public void getBanner(LifecycleProvider provider, IBaseCallBack<List<BannerInfo>> callBack) {
        observer(provider, DataService.getApiService().getBanner(), (HttpResult<List<BannerInfo>> listHttpResult) -> {
            if(listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0){
                return Observable.just(listHttpResult.data);
            }
            return Observable.error(new ResultException(listHttpResult.errorCode,listHttpResult.errorMsg));
        },callBack);
    }

    @Override
    public void getArticle(LifecycleProvider provider,int page, IBaseCallBack<List<ArticleInfo<Tags>>> callBack) {
        observer(provider, DataService.getApiService().getArticle(page), articleHttpResult -> {
            if (articleHttpResult.errorCode == 0 && articleHttpResult.data != null && articleHttpResult.data.datas != null && articleHttpResult.data.datas.size() > 0) {
                return Observable.just(articleHttpResult.data.datas);
            }
            return Observable.error(new ResultException(articleHttpResult.errorCode, articleHttpResult.errorMsg));
        },callBack);
    }

    @Override
    public void getUseful(LifecycleProvider provider, IBaseCallBack<List<Useful>> callBack) {
        observer(provider, DataService.getApiService().getUsefulSites(), listHttpResult -> {
            if(listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0){
                return Observable.just(listHttpResult.data);
            }
            return Observable.error(new ResultException(listHttpResult.errorCode,listHttpResult.errorMsg));
        },callBack);
    }

    @Override
    public void getKnowledge(LifecycleProvider provider, IBaseCallBack<List<Knowledge<Children>>> callBack) {
        observer(provider, DataService.getApiService().getKnowledge(), knowledgeHttpResult -> {
            if(knowledgeHttpResult.errorCode == 0 && knowledgeHttpResult.data != null && knowledgeHttpResult.data.size() > 0){
                return Observable.just(knowledgeHttpResult.data);
            }
            return Observable.error(new ResultException(knowledgeHttpResult.errorCode,knowledgeHttpResult.errorMsg));
        },callBack);

    }

    @Override
    public void getKnowArticle(LifecycleProvider provider,int page, int cid, IBaseCallBack<Article<ArticleInfo>> callBack) {
        observer(provider, DataService.getApiService().getKnowArticle(page,cid), listHttpResult -> {
            if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.datas != null) {
                return Observable.just(listHttpResult.data);
            }
            return Observable.error(new ResultException(listHttpResult.errorCode, listHttpResult.errorMsg));

        },callBack);
    }

    @Override
    public void getAuthorArticle(LifecycleProvider provider, int page, String author, IBaseCallBack<Article<ArticleInfo>> callBack) {
        observer(provider, DataService.getApiService().getAuthorActicle(page, author), articleHttpResult -> {
            if(articleHttpResult.errorCode == 0 && articleHttpResult.data != null && articleHttpResult.data.datas != null && articleHttpResult.data.datas.size() > 0){
                return Observable.just(articleHttpResult.data);
            }
            return Observable.error(new ResultException(articleHttpResult.errorCode,articleHttpResult.errorMsg));
        },callBack);
    }

    @Override
    public void getNvData(LifecycleProvider provider, IBaseCallBack<List<NvData<NvArticles>>> callBack) {
        observer(provider, DataService.getApiService().getNvData(), listHttpResult -> {
            if(listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0){
                return Observable.just(listHttpResult.data);
            }
            return Observable.error(new ResultException(listHttpResult.errorCode,listHttpResult.errorMsg));
        },callBack);
    }

    @Override
    public void getProTab(LifecycleProvider provider, IBaseCallBack<List<ProTab>> callBack) {
        observer(provider, DataService.getApiService().getProTab(), new Function<HttpResult<List<ProTab>>, ObservableSource<List<ProTab>>>() {
            @Override
            public ObservableSource<List<ProTab>> apply(HttpResult<List<ProTab>> listHttpResult) throws Exception {
                if(listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0){
                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ResultException(listHttpResult.errorCode,listHttpResult.errorMsg));
            }
        },callBack);
    }

    @Override
    public void getProArticle(LifecycleProvider provider,int page,int cid, IBaseCallBack<Article<ArticleInfo<Tags>>> callBack) {
        observer(provider, DataService.getApiService().getProArticle(page, cid), new Function<HttpResult<Article<ArticleInfo<Tags>>>, ObservableSource<Article<ArticleInfo<Tags>>>>() {
            @Override
            public ObservableSource<Article<ArticleInfo<Tags>>> apply(HttpResult<Article<ArticleInfo<Tags>>> articleHttpResult) throws Exception {
                if(articleHttpResult.errorCode == 0 && articleHttpResult.data != null && articleHttpResult.data.datas != null && articleHttpResult.data.datas.size() > 0){
                    return Observable.just(articleHttpResult.data);
                }
                return Observable.error(new ResultException(articleHttpResult.errorCode,articleHttpResult.errorMsg));
            }
        },callBack);
    }
}
