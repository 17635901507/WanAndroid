package com.kkk.mvp.base;

import com.kkk.mvp.exceptions.ResultException;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 18:15
 */
public interface IBaseCallBack<T> {
    void onSuccess(T data);

    void onFail(ResultException e);
}
