package com.android.wanandroid.ui.knowledge.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.Article;
import com.android.wanandroid.data.entity.ArticleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 15:12
 */
public class RlvKnowArticleAdapter extends RecyclerView.Adapter<RlvKnowArticleAdapter.ViewHolder> {
    private Context mContext;
    List<ArticleInfo> mArticles = new ArrayList<>();

    private final LayoutInflater mInflater;

    public RlvKnowArticleAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void clearArticle(){
        if(mArticles != null){
            mArticles.clear();
        }
    }
    public void setArticles(List<ArticleInfo> articles) {
        if(mArticles != null){
            mArticles.addAll(articles);
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
        ArticleInfo articleInfo = mArticles.get(position);
        holder.tv_author.setText(articleInfo.author);
        holder.tv_chapterName.setText(articleInfo.superChapterName + "/" + articleInfo.chapterName);
        holder.tv_niceDate.setText(articleInfo.niceDate);
        holder.tv_title.setText(articleInfo.title);
        holder.itemView.setOnClickListener(v -> {
            if(onItemClick != null){
                onItemClick.setOnItemClick(articleInfo.title,articleInfo.link);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
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
