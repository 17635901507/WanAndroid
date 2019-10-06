package com.android.wanandroid.ui.nv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.nv.NvArticles;
import com.android.wanandroid.data.entity.nv.NvData;
import com.android.wanandroid.detail.DetailActivity;
import com.kkk.mvp.utils.SystemFacade;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/5 13:08
 */
public class RlvNvAdapter extends RecyclerView.Adapter<RlvNvAdapter.ViewHolder> {
    private Context mContext;
    private List<NvData<NvArticles>> mList = new ArrayList<>();
    private final LayoutInflater mInflater;

    public RlvNvAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<NvData<NvArticles>> list) {
        if(this.mList != null){
            this.mList.clear();
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_nv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NvData<NvArticles> nvArticlesNvData = mList.get(position);
        holder.title.setText(nvArticlesNvData.name);

        List<NvArticles> articles = nvArticlesNvData.articles;
        holder.item_nv_flow_layout.setAdapter(new TagAdapter<NvArticles>(articles) {
            @Override
            public View getView(FlowLayout parent, int position, NvArticles feedArticleData) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                        holder.item_nv_flow_layout, false);
                if (feedArticleData == null) {
                    return null;
                }
                String name = feedArticleData.title;
                tv.setPadding(SystemFacade.dp2px(mContext,10), SystemFacade.dp2px(mContext,10),
                        SystemFacade.dp2px(mContext,10), SystemFacade.dp2px(mContext,10));
                tv.setText(name);
                tv.setTextColor(SystemFacade.randomColor());
                tv.setBackground(parent.getResources().getDrawable(R.drawable.bg_node_shape));
                holder.item_nv_flow_layout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position1, FlowLayout parent1) {
                        DetailActivity.actionStart(parent.getContext(),articles.get(position1).title,articles.get(position1).link);
                        return true;
                    }
                });
                return tv;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TagFlowLayout item_nv_flow_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            item_nv_flow_layout = itemView.findViewById(R.id.item_nv_flow_layout);
        }
    }
}
