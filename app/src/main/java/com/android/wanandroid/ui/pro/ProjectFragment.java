package com.android.wanandroid.ui.pro;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.pro.ProTab;
import com.android.wanandroid.ui.Contract;
import com.android.wanandroid.ui.knowledge.a.CommonVpAdapter;
import com.google.android.material.tabs.TabLayout;
import com.kkk.mvp.base.MvpBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/30 20:36
 */
public class ProjectFragment extends MvpBaseFragment<Contract.IProTabPresenter> implements Contract.IProTabView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void initView(View root) {
        mTabLayout = onViewCreatedBind(R.id.tablayout, null);
        mViewPager = onViewCreatedBind(R.id.pro_viewpager, null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getProTab();
    }

    @Override
    protected void cancelRequest() {

    }

    @Override
    public void onSuccess(List<ProTab> proTabs) {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < proTabs.size(); i++) {
            ProTab proTab = proTabs.get(i);
            titles.add(proTab.name);
            fragments.add(new ProArticleFragment(proTab.id));
        }
        CommonVpAdapter adapter = new CommonVpAdapter(getFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public Contract.IProTabPresenter createPresenter() {
        return new TabPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            getActivity().findViewById(R.id.tab).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.main_floating_action_btn).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.main_toolbar).setVisibility(View.VISIBLE);
        }
        super.onHiddenChanged(hidden);
    }
}
