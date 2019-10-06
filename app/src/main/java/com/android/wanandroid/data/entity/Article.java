package com.android.wanandroid.data.entity;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 9:30
 */
public class Article<T> {
    public List<T> datas;
    public int curPage;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
}
