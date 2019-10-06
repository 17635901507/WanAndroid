package com.android.wanandroid.ui.homepage;

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
import com.android.wanandroid.data.entity.BannerInfo;
import com.android.wanandroid.data.entity.Tags;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 10:13
 */
public class RlvHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<BannerInfo> mBannerInfos;
    private List<ArticleInfo<Tags>> mArticleInfos;
    private int TYPE_BANNER = 0;
    private int TYPE_ARTICLE = 1;
    private final LayoutInflater mInflater;

    public RlvHomeAdapter(Context context, List<BannerInfo> bannerInfos, List<ArticleInfo<Tags>> articleInfos) {
        this.mContext = context;
        this.mBannerInfos = bannerInfos;
        this.mArticleInfos = articleInfos;
        mInflater = LayoutInflater.from(context);
    }

    public void setBannerInfos(List<BannerInfo> mBannerInfos) {
        this.mBannerInfos = mBannerInfos;
        notifyDataSetChanged();
    }

    public void setArticleInfos(List<ArticleInfo<Tags>> mArticleInfos) {
        this.mArticleInfos = mArticleInfos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            return new BannerViewHolder(mInflater.inflate(R.layout.item_banner, parent, false));
        }
        return new ArticleViewHolder(mInflater.inflate(R.layout.item_home_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            ArrayList<String> titles = new ArrayList<>();
            for (BannerInfo bannerInfo : mBannerInfos) {
                titles.add(bannerInfo.title);
            }
            ((BannerViewHolder) holder).mBanner.setImages(mBannerInfos)
                    .setBannerTitles(titles)
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)//设置banner样式
                    .setBannerAnimation(Transformer.DepthPage)//设置banner动画效果
                    .setDelayTime(mBannerInfos.size() * 400)//设置轮播时间
                    .setIndicatorGravity(BannerConfig.CENTER)//设置指示器位置（当banner模式中有指示器时）
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            BannerInfo bannerInfo = (BannerInfo) path;
                            Glide.with(context).load(bannerInfo.imagePath).into(imageView);
                            //Banner的接口回调
                            imageView.setOnClickListener(v -> {
                                if(onItemClick != null){
                                    onItemClick.setOnItemClick(bannerInfo.title,bannerInfo.url);
                                }
                            });
                        }
                    }).start();
        } else if (holder instanceof ArticleViewHolder) {
            if (mBannerInfos.size() > 0) {
                position = position - 1;
            }
            if (position == 0) {
                ((ArticleViewHolder) holder).item_search_pager_tag_red_tv.setVisibility(View.VISIBLE);
                ((ArticleViewHolder) holder).item_search_pager_tag_red_tv.setText("项目");
            } else {
                ((ArticleViewHolder) holder).item_search_pager_tag_red_tv.setVisibility(View.GONE);
            }
            ArticleInfo<Tags> articleInfo = mArticleInfos.get(position);
            ((ArticleViewHolder) holder).tv_author.setText(articleInfo.author);
            ((ArticleViewHolder) holder).tv_chapterName.setText(articleInfo.superChapterName + "/" + articleInfo.chapterName);
            ((ArticleViewHolder) holder).tv_niceDate.setText(articleInfo.niceDate);
            ((ArticleViewHolder) holder).tv_title.setText(articleInfo.title);
            ((ArticleViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClick != null){
                        onItemClick.setOnItemClick(articleInfo.title,articleInfo.link);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mBannerInfos != null && mBannerInfos.size() > 0) {
            return mArticleInfos.size() + 1;
        } else {
            return mArticleInfos.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mBannerInfos.size() > 0) {
            return TYPE_BANNER;
        } else {
            return TYPE_ARTICLE;
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        Banner mBanner;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            mBanner = itemView.findViewById(R.id.home_banner);
        }
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView tv_author, tv_chapterName, tv_title, tv_niceDate, item_search_pager_tag_red_tv;
        public ArticleViewHolder(@NonNull View itemView) {
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
