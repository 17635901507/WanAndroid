package com.android.wanandroid.data.entity.nv;

import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 21:03
 */
public class NvData<T> {
    public List<T> articles;
    public int cid;
    public String name;

    @Override
    public String toString() {
        return "NvData{" +
                "articles=" + articles +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                '}';
    }
}
