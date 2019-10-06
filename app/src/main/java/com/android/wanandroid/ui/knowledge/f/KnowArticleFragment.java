package com.android.wanandroid.ui.knowledge.f;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.detail.DetailActivity;
import com.android.wanandroid.ui.Contract;
import com.android.wanandroid.ui.knowledge.a.RlvKnowArticleAdapter;
import com.android.wanandroid.ui.knowledge.p.KnowArticlePresenter;
import com.kkk.mvp.base.MvpBaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 12:41
 */
public class KnowArticleFragment extends MvpBaseFragment<Contract.IKnowArticlePresenter> implements Contract.IKnowArticleView {

    private RecyclerView mRecyclerView;
    private RlvKnowArticleAdapter mAdapter;
    private SmartRefreshLayout mSmartRefresh;
    private int cid;
    private int page = 0;

    public KnowArticleFragment() {
    }

    public KnowArticleFragment(int id) {
        cid = id;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know_article;
    }

    @Override
    protected void initView(View root) {
        mSmartRefresh = onViewCreatedBind(R.id.know_article_smartrefresh, null);
        mRecyclerView = onViewCreatedBind(R.id.know_article_recyclerview, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mAdapter = new RlvKnowArticleAdapter(mBaseActivity);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getKnowArticle(page, cid);
    }

    @Override
    protected void initListener() {
        super.initListener();
        //刷新与加载
        mSmartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                if (mAdapter != null) {
                    mAdapter.clearArticle();
                    initData();
                }
            }
        });

        //跳转详情页
        mAdapter.setOnItemClick((title, link) -> DetailActivity.actionStart(mBaseActivity,title,link));
    }

    @Override
    protected void cancelRequest() {

    }

    @Override
    public void onSuccess(Article<ArticleInfo> articles) {
        mAdapter.setArticles(articles.datas);
        if (page > 0) {
            mSmartRefresh.finishLoadMore();
        } else {
            mSmartRefresh.finishRefresh();
        }
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
        if (page > 0) {
            mSmartRefresh.finishLoadMore();
        } else {
            mSmartRefresh.finishRefresh();
        }
    }

    @Override
    public Contract.IKnowArticlePresenter createPresenter() {
        return new KnowArticlePresenter();
    }
}
