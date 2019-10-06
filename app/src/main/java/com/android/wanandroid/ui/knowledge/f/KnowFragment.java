package com.android.wanandroid.ui.knowledge.f;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.app.Constants;
import com.android.wanandroid.data.entity.know.Children;
import com.android.wanandroid.data.entity.know.Knowledge;
import com.android.wanandroid.ui.Contract;
import com.android.wanandroid.ui.knowledge.a.RlvKnowledgeAdapter;
import com.android.wanandroid.ui.knowledge.p.KnowPresenter;
import com.kkk.mvp.base.MvpBaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/30 20:32
 */
public class KnowFragment extends MvpBaseFragment<Contract.IKnowledgePresenter> implements Contract.IKnowledgeView {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefresh;
    private RlvKnowledgeAdapter mAdapter;
    private List<Knowledge<Children>> mKnowledges;

    @Override
    protected void initView(View root) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = onViewCreatedBind(R.id.know_ledge_recyclerview, null);
        mSmartRefresh = onViewCreatedBind(R.id.know_ledge_smart_refresh, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));

        mSmartRefresh.setOnRefreshListener(refreshLayout -> initData());
        mSmartRefresh.setOnLoadMoreListener(refreshLayout -> {
            showToast(R.string.text_nomore);
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getKnowledge();
        mKnowledges = new ArrayList<>();
    }

    @Override
    protected void initListener() {
        super.initListener();

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know;
    }

    @Override
    protected void cancelRequest() {

    }

    @Override
    public void onSuccess(List<Knowledge<Children>> knowledges) {
        mKnowledges = knowledges;
        mAdapter = new RlvKnowledgeAdapter(mBaseActivity);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setList(knowledges);

        mSmartRefresh.finishRefresh();

        mAdapter.setOnItemClick(new RlvKnowledgeAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int i,int childPosition) {
                Bundle bundle = new Bundle();

                Knowledge<Children> childrenKnowledge = mKnowledges.get(i);

                bundle.putString(Constants.ARG_PARAM1,childrenKnowledge.name);
                bundle.putSerializable(Constants.ARG_PARAM2, (Serializable) childrenKnowledge.children);
                addFragment(getFragmentManager(), KnowTabFragment.class,R.id.main_fragment_container,bundle);


                //隐藏主布局的 ToolBar 和TabLayout
                getActivity().findViewById(R.id.tab).setVisibility(View.GONE);
                getActivity().findViewById(R.id.main_floating_action_btn).setVisibility(View.GONE);
                getActivity().findViewById(R.id.main_toolbar).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
        mSmartRefresh.finishRefresh();
    }

    @Override
    public Contract.IKnowledgePresenter createPresenter() {
        return new KnowPresenter();
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
}
