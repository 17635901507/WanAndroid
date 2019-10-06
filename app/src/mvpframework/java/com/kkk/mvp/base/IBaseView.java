package com.kkk.mvp.base;

import android.app.Activity;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 18:24
 */
public interface IBaseView<T extends IBasePresenter> {
    T createPresenter();

    void closeLoading();

    Activity getActivityObj();
}
