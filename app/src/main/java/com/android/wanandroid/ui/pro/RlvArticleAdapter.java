package com.android.wanandroid.ui.pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.ArticleInfo;
import com.android.wanandroid.data.entity.Tags;
import com.android.wanandroid.detail.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/5 22:59
 */
public class RlvArticleAdapter extends RecyclerView.Adapter<RlvArticleAdapter.ViewHolder> {
    private Context mContext;

    private List<ArticleInfo<Tags>> mList = new ArrayList<>();
    private final LayoutInflater mInflater;

    public RlvArticleAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void clearList() {
        if(mList != null){
            mList.clear();
        }
    }

    public void setList(List<ArticleInfo<Tags>> list) {
        if(this.mList != null){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_pro_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleInfo<Tags> articleInfo = mList.get(position);

        holder.item_pro_tv_desc.setText(articleInfo.desc);
        holder.item_pro_tv_niceDate_and_author.setText(articleInfo.niceDate+"  "+articleInfo.author);
        holder.item_pro_tv_title.setText(articleInfo.title);
        Glide.with(mContext).load(articleInfo.envelopePic).into(holder.item_pro_iv_envelopePic);

        //跳转详情
        holder.itemView.setOnClickListener(v -> DetailActivity.actionStart(mContext,articleInfo.title,articleInfo.link));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_pro_iv_envelopePic;
        TextView item_pro_tv_title,item_pro_tv_desc,item_pro_tv_niceDate_and_author;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_pro_iv_envelopePic = itemView.findViewById(R.id.item_pro_iv_envelopePic);
            item_pro_tv_title = itemView.findViewById(R.id.item_pro_tv_title);
            item_pro_tv_desc = itemView.findViewById(R.id.item_pro_tv_desc);
            item_pro_tv_niceDate_and_author = itemView.findViewById(R.id.item_pro_tv_niceDate_and_author);
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
