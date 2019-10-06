package com.android.wanandroid.data.entity.pro;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/5 21:44
 */
public class ProTab<T> {
    public List<T> children;
    public int courseId;
    public int id;
    public String name;
    public long order;
    public int parentChapterId;
    public boolean userControlSetTop;
    public int visible;
}
