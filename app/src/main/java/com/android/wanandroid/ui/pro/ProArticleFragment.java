package com.android.wanandroid.ui.pro;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.entity.Tags;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BaseFragment;
import com.kkk.mvp.base.MvpBaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProArticleFragment extends MvpBaseFragment<Contract.IProArticlePresenter> implements Contract.IProArticleView {
    private int cid;
    private int page = 0;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefresh;
    private RlvArticleAdapter mAdapter;

    public ProArticleFragment() {
        // Required empty public constructor
    }

    public ProArticleFragment(int id){
        this.cid = id;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pro_article;
    }

    @Override
    protected void initView(View root) {
        mSmartRefresh = onViewCreatedBind(R.id.pro_smartRefresh, null);
        mRecyclerView = onViewCreatedBind(R.id.pro_recyclerView, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));

        mAdapter = new RlvArticleAdapter(mBaseActivity);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getProArticle(page,cid);
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
                mAdapter.clearList();//刷新前清除集合数据
                initData();
            }
        });
    }

    @Override
    protected void cancelRequest() {

    }

    @Override
    public void onSuccess(Article<ArticleInfo<Tags>> articleInfoArticle) {
        mAdapter.setList(articleInfoArticle.datas);
        if(page >  0){
            mSmartRefresh.finishLoadMore(true);
        }else{
            mSmartRefresh.finishRefresh(true);
        }
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
        if(page >  0){
            mSmartRefresh.finishLoadMore(false);
        }else{
            mSmartRefresh.finishRefresh(false);
        }
    }

    @Override
    public Contract.IProArticlePresenter createPresenter() {
        return new ArticlePresenter();
    }
}
