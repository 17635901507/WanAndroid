package com.android.wanandroid.data.okhttp;

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
import com.android.wanandroid.data.entity.search.SearchHot;
import com.android.wanandroid.data.entity.search.Useful;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 9:06
 */
public interface ApiService {
    //https://www.wanandroid.com/banner/json    首页banner
    @GET("banner/json")
    Observable<HttpResult<List<BannerInfo>>> getBanner();

    //https://www.wanandroid.com/article/list/0/json  首页文章列表
    @GET("article/list/{page}/json")
    Observable<HttpResult<Article<ArticleInfo<Tags>>>> getArticle(@Path("page") int page);

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     *
     * @return 常用网站数据
     */
    @GET("friend/json")
    Observable<HttpResult<List<Useful>>> getUsefulSites();

    /**
     * 热搜
     * http://www.wanandroid.com//hotkey/json
     *
     * @return 热门搜索数据
     */
    @GET("hotkey/json")
    @Headers("Cache-Control: public, max-age=36000")
    Observable<HttpResult<List<SearchHot>>> getTopSearchData();

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page page
     * @param k POST search key
     * @return 搜索数据
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<HttpResult<Article<ArticleInfo>>> getSearchList(@Path("page") int page, @Field("k") String k);

    /**
     * 知识体系
     * https://www.wanandroid.com/tree/json
     * @return 知识体系
     */
    @GET("tree/json")
    Observable<HttpResult<List<Knowledge<Children>>>> getKnowledge();

    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60
     * @return 知识体系下
     */
    @GET("article/list/{page}/json")
    Observable<HttpResult<Article<ArticleInfo>>> getKnowArticle(@Path("page") int page,@Query("cid") int cid);


    /**
     * 按照作者昵称搜索文章
     * https://wanandroid.com/article/list/0/json?author=鸿洋
     * @retrun 文章列表
     */
    @GET("article/list/{page}/json")
    Observable<HttpResult<Article<ArticleInfo>>> getAuthorActicle(@Path("page") int page,@Query("author") String author);

    /**
     * 导航数据
     * https://www.wanandroid.com/navi/json
     * @return 导航数据
     */
    @GET("navi/json")
    Observable<HttpResult<List<NvData<NvArticles>>>> getNvData();

    /**
     * 项目分类
     * https://www.wanandroid.com/project/tree/json
     *
     * 方法： GET
     * 参数： 无
     * @return 项目分类
     */
    @GET("project/tree/json")
    Observable<HttpResult<List<ProTab>>> getProTab();

    /**
     * 项目列表数据
     * https://www.wanandroid.com/project/list/1/json?cid=294
     *
     * 方法：GET
     * 参数：
     * 	cid 分类的id，上面项目分类接口
     * 	页码：拼接在链接中，从1开始。
     * @return 项目列表数据
     */
    @GET("project/list/{page}/json")
    Observable<HttpResult<Article<ArticleInfo<Tags>>>> getProArticle(@Path("page") int page,@Query("cid") int cid);

}
