package com.android.wanandroid.ui.wx;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.wanandroid.R;
import com.android.wanandroid.ui.knowledge.a.CommonVpAdapter;
import com.google.android.material.tabs.TabLayout;
import com.kkk.mvp.base.BaseFragment;

import java.util.ArrayList;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/30 20:34
 */
public class PubNumFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public PubNumFragment(){}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pubnum;
    }
    @Override
    protected void initView(View root) {
        mTabLayout = onViewCreatedBind(R.id.wx_tablayout, null);
        mViewPager = onViewCreatedBind(R.id.wx_viewpager, null);

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        titles.add("鸿洋");
        titles.add("郭霖");
        titles.add("玉刚说");
        titles.add("承香墨影");
        titles.add("Android群英传");
        titles.add("code小生");
        titles.add("谷歌开发者");
        titles.add("齐卓社");
        titles.add("美团技术团队");
        titles.add("GcsSloop");
        titles.add("互联网侦查");
        titles.add("susion随心");
        titles.add("程序亦非猿");
        titles.add("Gityuan");
        for (int i = 0; i < titles.size(); i++) {
            fragments.add(new WxArticleFragment(titles.get(i)));
        }
        CommonVpAdapter adapter = new CommonVpAdapter(getFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }



    @Override
    protected void cancelRequest() {

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
