package com.android.wanandroid.ui.nv;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.nv.NvArticles;
import com.android.wanandroid.data.entity.nv.NvData;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.BaseFragment;
import com.kkk.mvp.base.MvpBaseFragment;
import com.kkk.mvp.utils.Logger;
import com.kkk.mvp.utils.SystemFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/30 20:35
 */
public class NavigationFragment extends MvpBaseFragment<Contract.INvPresenter> implements Contract.INvView {

    private static final String TAG = "NavigationFragment";
    private VerticalTabLayout mTabLayout;
    private RecyclerView mRecyclerView;
    private RlvNvAdapter mAdapter;
    private TextView mTitle;
    private LinearLayoutManager mLinearLayoutManager;
    private int mCurrentPosition = 0;
    private int mTitleHeight;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView(View root) {
        mTitle = onViewCreatedBind(R.id.title, null);
        mTabLayout = onViewCreatedBind(R.id.nv_verticaltablayout, null);
        mRecyclerView = onViewCreatedBind(R.id.nv_recyclerView, null);
        mLinearLayoutManager = new LinearLayoutManager(mBaseActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RlvNvAdapter(mBaseActivity);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void initListener() {
        super.initListener();
        mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                //RecyclerView 联动 TabLayout
                moveToPosition(mLinearLayoutManager,mRecyclerView,position);

            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }
    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    public static void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {


        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        if (currentPosition <= firstPosition) {
            mRecyclerView.smoothScrollToPosition(currentPosition);
        } else if (currentPosition <= lastPosition) {
            int top = mRecyclerView.getChildAt(currentPosition - firstPosition).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            mRecyclerView.smoothScrollToPosition(currentPosition);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getNvData();
    }

    @Override
    protected void cancelRequest() {

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
    public void onSuccess(List<NvData<NvArticles>> nvDataList) {
        TabAdapter adapter = new TabAdapter() {
            @Override
            public int getCount() {
                return nvDataList.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new QTabView.TabTitle.Builder()
                        .setContent(nvDataList.get(position).name)
                        .setTextColor(Color.GREEN, Color.GRAY)//第一个是选中颜色，第二个是正常颜色
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return -1;
            }
        };
        //绑定适配器
        mTabLayout.setTabAdapter(adapter);

        //刷新RecycleView数据
        mAdapter.setList(nvDataList);

        //recyclerview 滑动监听(粘性头部)
        initListener(nvDataList);

    }

    private void initListener(List<NvData<NvArticles>> nvDataList) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取固定头布局
                mTitleHeight = mTitle.getHeight();
                Logger.d(TAG + "titleHeight: s%", mTitleHeight);

                //TabLayout 绑定到 RecycleView 对应item上
                mTabLayout.setTabSelected(mCurrentPosition);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //因为第一个已经显示，所以获取下一个
                View view = mLinearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    //当item头部向上滚动，给固定title也设置向上滚动
                    if (view.getTop() <= mTitleHeight) {
                        mTitle.setY(-(mTitleHeight - view.getTop()));
                        Logger.d(TAG + "title Y: s%", -(mTitleHeight - view.getTop()));
                    }else {
                        mTitle.setY(0);
                    }
                }
                //改变头布局内容及设置Y轴内容
                if(mCurrentPosition != mLinearLayoutManager.findFirstVisibleItemPosition()){
                    mCurrentPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

                    mTitle.setY(0);
                    if(nvDataList != null){
                        //设置头部title
                        mTitle.setText(nvDataList.get(mCurrentPosition).name);
                        Logger.d(TAG + "title Name: s%", nvDataList.get(mCurrentPosition).name);

                    }
                }
            }

        });
        //设置默认头布局内容
        mTitle.setText(nvDataList.get(0).name);


    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public Contract.INvPresenter createPresenter() {
        return new NvPresenter();
    }
}
