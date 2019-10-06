package com.android.wanandroid.ui.wx;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.detail.DetailActivity;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BaseFragment;
import com.kkk.mvp.base.MvpBaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 *              按照作者昵称搜索文章
 * @date : 2019/10/4 18:15
 */
public class WxArticleFragment extends MvpBaseFragment<Contract.IAuthorArticlePresenter> implements Contract.IAuthorArticleView {

    private RecyclerView mRecyclerView;
    private int page = 0;
    private RlvAuthorArticleAdapter mAdapter;
    private SmartRefreshLayout mSmartRefresh;

    public WxArticleFragment(){}
    private String author;

    public WxArticleFragment(String author){
        this.author = author;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_article;
    }

    @Override
    protected void initView(View root) {
        mSmartRefresh = onViewCreatedBind(R.id.wx_smartrefresh, null);
        mRecyclerView = onViewCreatedBind(R.id.wx_recyclerview, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));


        mAdapter = new RlvAuthorArticleAdapter(mBaseActivity);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSmartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mAdapter.clearArticles();
                initData();
            }
        });
        //跳转详情
        mAdapter.setOnItemClick((title, link) -> DetailActivity.actionStart(mBaseActivity,title,link));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getAuthorArticle(page,author);
    }

    @Override
    protected void cancelRequest() {

    }

    @Override
    public void onSuccess(Article<ArticleInfo> articles) {
        mAdapter.setArticleInfos(articles.datas);
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public Contract.IAuthorArticlePresenter createPresenter() {
        return new WxArticlePresenter();
    }
}
