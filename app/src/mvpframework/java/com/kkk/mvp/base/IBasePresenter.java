package com.kkk.mvp.base;

import io.reactivex.disposables.Disposable;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 18:24
 */
public interface IBasePresenter<T extends IBaseView> {
    void attachView(T view);

    void detachView();

    void cacheRequest(Disposable disposable);

    void cancelRequest();
}
