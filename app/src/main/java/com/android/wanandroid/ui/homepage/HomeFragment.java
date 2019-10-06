package com.android.wanandroid.ui.homepage;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.libloadingview.LoadingView;
import com.android.wanandroid.R;
import com.android.wanandroid.app.Constants;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.entity.BannerInfo;
import com.android.wanandroid.data.entity.Tags;
import com.android.wanandroid.detail.DetailActivity;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.MvpBaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/30 20:23
 */
public class HomeFragment extends MvpBaseFragment<Contract.IHomePresenter> implements Contract.IHomeView {

    private RecyclerView mRecyclerView;
    private int page = 0;
    private RlvHomeAdapter mAdapter;
    private SmartRefreshLayout mSmartRefresh;
    private List<ArticleInfo<Tags>> mArticleInfos;
    private List<BannerInfo> mBannerInfos;


    @Override
    protected void initData() {
        super.initData();
        mPresenter.getBanner();
        mPresenter.getArticle(page);
        //showLoading(LoadingView.LOADING_MODE_WHITE_BG);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClick((title, link) -> DetailActivity.actionStart(mBaseActivity, title, link));
        mBaseActivity.overridePendingTransition(R.anim.activity_open, R.anim.activity_close);

        mSmartRefresh.setOnRefreshListener(refreshLayout -> {
            page = 0;
            mArticleInfos.clear();
            initData();
        });
        mSmartRefresh.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mPresenter.getArticle(page);
        });
    }

    @Override
    protected void initView(View root) {
        mSmartRefresh = onViewCreatedBind(R.id.homeSmart, null);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = onViewCreatedBind(R.id.homeRecyclerView, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));


        mBannerInfos = new ArrayList<>();
        mArticleInfos = new ArrayList<>();
        mAdapter = new RlvHomeAdapter(mBaseActivity, mBannerInfos, mArticleInfos);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    public static HomeFragment getInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void cancelRequest() {

    }


    @Override
    public void onBannerSuccess(List<BannerInfo> banners) {
        mBannerInfos.clear();
        mBannerInfos.addAll(banners);
        mAdapter.setBannerInfos(mBannerInfos);
        closeLoading();
        mSmartRefresh.finishRefresh(true);
        mSmartRefresh.finishLoadMore(true);
    }

    @Override
    public void onArticleSuccess(List<ArticleInfo<Tags>> articles) {
        mArticleInfos.addAll(articles);
        mAdapter.setArticleInfos(mArticleInfos);
        if (page == 0) {
            mSmartRefresh.finishRefresh();
        } else {
            mSmartRefresh.finishLoadMore();
            if (articles.size() < 20) {
                showToast("数据全部加载完毕");
            }
        }
        closeLoading();
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
        onError(msg, new LoadingView.RetryCallBack() {
            @Override
            public void onRetry() {
                initData();
            }
        });
        if (page == 0) {
            mSmartRefresh.finishRefresh(false);
            showToast("数据刷新失败");
        } else {
            mSmartRefresh.finishLoadMore(false);
            showToast("数据加载失败");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            getActivity().findViewById(R.id.tab).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.main_floating_action_btn).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.main_toolbar).setVisibility(View.VISIBLE);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public Contract.IHomePresenter createPresenter() {
        return new HomePresenter();
    }
}
