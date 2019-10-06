package com.kkk.mvp.base;

import io.reactivex.disposables.Disposable;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 18:31
 */
public interface ICancelBaseCallBack<T> extends IBaseCallBack<T>{

    void onStart(Disposable disposable);
}
