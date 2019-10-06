package com.android.wanandroid.data.entity;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/1 10:56
 */
public class HttpResult<T> {
    public String errorMsg;
    public int errorCode;
    public T data;

    @Override
    public String toString() {
        return "HttpResult{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                ", data=" + data +
                '}';
    }
}
