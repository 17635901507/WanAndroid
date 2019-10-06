package com.android.wanandroid.data.entity;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/1 10:57
 */
public class BannerInfo {
    public String desc;
    public int id;
    public String imagePath;
    public int isVisible;
    public int order;
    public String title;
    public int type;
    public String url;

    @Override
    public String toString() {
        return "BannerInfo{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", isVisible=" + isVisible +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }
}
