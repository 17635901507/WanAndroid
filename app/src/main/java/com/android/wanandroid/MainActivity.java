package com.android.wanandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.wanandroid.ui.homepage.HomeFragment;
import com.android.wanandroid.ui.knowledge.f.KnowFragment;
import com.android.wanandroid.ui.nv.NavigationFragment;
import com.android.wanandroid.ui.pro.ProjectFragment;
import com.android.wanandroid.ui.wx.PubNumFragment;
import com.android.wanandroid.ui.homepage.search.SearchFragment;
import com.android.wanandroid.ui.homepage.search.UsageFragment;
import com.android.wanandroid.widget.LIFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.kkk.mvp.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mMainToolbar;
    private NavigationView mMainNavigationview;
    private DrawerLayout mMainDrawerlayout;
    private TabLayout mTab;
    private ArrayList<Integer> mToolBarTitles;
    private TextView mTitle;
    private LIFloatingActionButton mFloatingBtn;
    private HomeFragment mHomeFragment;

    public TabLayout getTab() {
        return mTab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mTitle.setText(mToolBarTitles.get(position));
                switch (position) {
                    case 0:
                        addFragment(getSupportFragmentManager(), HomeFragment.class, R.id.main_fragment_container, null);
                        break;
                    case 1:
                        addFragment(getSupportFragmentManager(), KnowFragment.class, R.id.main_fragment_container, null);
                        break;
                    case 2:
                        addFragment(getSupportFragmentManager(), PubNumFragment.class, R.id.main_fragment_container, null);
                        break;
                    case 3:
                        addFragment(getSupportFragmentManager(), NavigationFragment.class, R.id.main_fragment_container, null);
                        break;
                    case 4:
                        addFragment(getSupportFragmentManager(), ProjectFragment.class, R.id.main_fragment_container, null);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        mTab = findViewById(R.id.tab);
        mFloatingBtn = onViewCreatedBind(R.id.main_floating_action_btn, this);
        initToolBar();
        initNavigation();

        mTab.addTab(mTab.newTab().setText(R.string.text_home).setIcon(R.drawable.tab_home_select));
        mTab.addTab(mTab.newTab().setText(R.string.text_knowledge).setIcon(R.drawable.tab_knowledge_select));
        mTab.addTab(mTab.newTab().setText(R.string.text_tencent).setIcon(R.drawable.tab_me_select));
        mTab.addTab(mTab.newTab().setText(R.string.text_navigation).setIcon(R.drawable.tab_navigation_select));
        mTab.addTab(mTab.newTab().setText(R.string.text_project).setIcon(R.drawable.tab_progect_select));

        mHomeFragment = new HomeFragment();
        addFragment(getSupportFragmentManager(), HomeFragment.class, R.id.main_fragment_container, null);


    }

    private void initNavigation() {
        mMainNavigationview = (NavigationView) findViewById(R.id.main_navigationview);
        mMainDrawerlayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mMainDrawerlayout, mMainToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = mMainDrawerlayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        mMainDrawerlayout.addDrawerListener(toggle);

    }

    private void initToolBar() {
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = onViewCreatedBind(R.id.main_title, null);
        mTitle.setText(R.string.text_home);
        mMainToolbar.setTitle("");
        setSupportActionBar(mMainToolbar);

        mToolBarTitles = new ArrayList<>();
        mToolBarTitles.add(R.string.text_home);
        mToolBarTitles.add(R.string.text_knowledge);
        mToolBarTitles.add(R.string.text_tencent);
        mToolBarTitles.add(R.string.text_navigation);
        mToolBarTitles.add(R.string.text_project);
    }


    //ToolBar  选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //选项菜单监听
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //常用网站
            case R.id.action_usage:
                addFragment(getSupportFragmentManager(), UsageFragment.class,R.id.main_fragment_container,null);
                break;
            //搜索
            case R.id.action_search:
                addFragment(getSupportFragmentManager(), SearchFragment.class,R.id.main_fragment_container,null);
                break;
        }
        //隐藏ToolBar，TabLayout 和 悬浮按钮
        mMainToolbar.setVisibility(View.GONE);
        mFloatingBtn.setVisibility(View.GONE);
        mTab.setVisibility(View.GONE);
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //悬浮按钮点击监听
            case R.id.main_floating_action_btn:
                break;
        }
    }
}
