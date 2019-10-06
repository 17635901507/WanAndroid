package com.android.wanandroid.data.entity.know;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 11:06
 */
public class Knowledge<T> {
    public List<T> children;
    public int courseId;
    public int id;
    public int order;
    public int parentChapterId;
    public int visible;
    public String name;
    public boolean userControlSetTop;
}
