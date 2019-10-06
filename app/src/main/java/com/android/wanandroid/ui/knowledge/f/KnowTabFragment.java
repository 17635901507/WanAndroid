package com.android.wanandroid.ui.knowledge.f;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.wanandroid.R;
import com.android.wanandroid.app.Constants;
import com.android.wanandroid.data.entity.know.Children;
import com.android.wanandroid.ui.knowledge.a.CommonVpAdapter;
import com.google.android.material.tabs.TabLayout;
import com.kkk.mvp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 12:05
 */
public class KnowTabFragment extends BaseFragment {

    private Toolbar mToolBar;
    private TextView mTitle;
    private String mName;
    private List<Children> mChildren;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public KnowTabFragment(){}
    @Override
    protected void initView(View root) {

        Bundle arguments = getArguments();
        if(arguments != null){
            mName = arguments.getString(Constants.ARG_PARAM1);
            mChildren = (List<Children>) arguments.getSerializable(Constants.ARG_PARAM2);
        }

        initToolBar();

        mTabLayout = onViewCreatedBind(R.id.tablayout, null);
        mViewPager = onViewCreatedBind(R.id.know_ledge_viewpager, null);

    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < mChildren.size(); i++) {
            titles.add(mChildren.get(i).name);
            fragments.add(new KnowArticleFragment(mChildren.get(i).id));
        }
        CommonVpAdapter adapter = new CommonVpAdapter(getFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initToolBar() {
        mToolBar = onViewCreatedBind(R.id.main_toolbar, null);
        mTitle = onViewCreatedBind(R.id.main_title, null);
        mTitle.setText(mName);
        mTitle.setTextColor(Color.WHITE);
        mToolBar.setTitle("");

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_child;
    }


    @Override
    protected void cancelRequest() {

    }
}
