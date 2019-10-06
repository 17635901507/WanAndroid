package com.android.wanandroid.ui.wx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.ArticleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 19:51
 */
public class RlvAuthorArticleAdapter extends RecyclerView.Adapter<RlvAuthorArticleAdapter.ViewHolder> {
    private Context mContext;
    private List<ArticleInfo> mArticleInfos = new ArrayList<>();
    private final LayoutInflater mInflater;

    public RlvAuthorArticleAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    public void clearArticles() {
        if(mArticleInfos != null){
            mArticleInfos.clear();
        }
    }

    public void setArticleInfos(List<ArticleInfo> articleInfos) {
        if(this.mArticleInfos != null){
            this.mArticleInfos.addAll(articleInfos);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleInfo articleInfo = mArticleInfos.get(position);
        holder.tv_author.setText(articleInfo.author);
        holder.tv_chapterName.setText(articleInfo.superChapterName + "/" + articleInfo.chapterName);
        holder.tv_niceDate.setText(articleInfo.niceDate);
        holder.tv_title.setText(articleInfo.title);

        //接口回调
        holder.itemView.setOnClickListener(v -> {
            if(onItemClick != null){
                onItemClick.setOnItemClick(articleInfo.title,articleInfo.link);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticleInfos.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_author, tv_chapterName, tv_title, tv_niceDate, item_search_pager_tag_red_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_chapterName = itemView.findViewById(R.id.tv_chapterName);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_niceDate = itemView.findViewById(R.id.tv_niceDate);
            item_search_pager_tag_red_tv = itemView.findViewById(R.id.item_search_pager_tag_red_tv);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface  OnItemClick{
        void setOnItemClick(String title,String link);
    }
}
