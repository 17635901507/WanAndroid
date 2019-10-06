package com.android.wanandroid.ui.homepage.search;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.search.Useful;
import com.android.wanandroid.detail.DetailActivity;
import com.android.wanandroid.ui.Contract;
import com.kkk.mvp.base.MvpBaseFragment;
import com.kkk.mvp.utils.SystemFacade;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/3 15:16
 */
public class UsageFragment extends MvpBaseFragment<Contract.IUsefulPresenter> implements Contract.IUsefulView {

    private Toolbar mToolbar;
    private TextView mTitle;
    private TagFlowLayout mFlowLayout;

    @Override
    protected void initView(View root) {

        initToolBar();
        mFlowLayout = onViewCreatedBind(R.id.useful_sites_flow_layout, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getUseful();
    }

    private void initToolBar() {
        mToolbar = onViewCreatedBind(R.id.main_toolbar, null);
        mTitle = onViewCreatedBind(R.id.main_title, null);

        mToolbar.setTitle("");
        mToolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        mTitle.setText(R.string.text_usage);
        mTitle.setTextColor(Color.BLACK);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_grey_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_usage;
    }

    @Override
    protected void cancelRequest() {

    }


    @Override
    public void onSuccess(List<Useful> usefuls) {
        mFlowLayout.setAdapter(new TagAdapter<Useful>(usefuls) {
            @Override
            public View getView(FlowLayout parent, int position, Useful usefulSiteData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert usefulSiteData != null;
                String name = usefulSiteData.name;
                tv.setText(name);
                setItemBackground(tv);
                mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position1, FlowLayout parent1) {

                        Useful useful = usefuls.get(position1);
                        DetailActivity.actionStart(mBaseActivity,useful.name,useful.link);
                        return true;
                    }
                });
                return tv;
            }
        });
    }



    private void setItemBackground(TextView tv) {
        tv.setBackgroundColor(SystemFacade.randomColor());
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public Contract.IUsefulPresenter createPresenter() {
        return new UsefulPresenter();
    }
}
