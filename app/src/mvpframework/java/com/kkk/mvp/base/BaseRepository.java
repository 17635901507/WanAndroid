package com.kkk.mvp.base;

import com.kkk.mvp.exceptions.ResultException;
import com.kkk.mvp.utils.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 19:14
 */
public class BaseRepository {

    protected <T,R>  void observer(LifecycleProvider provider, Observable<T> observable, Function<T, ObservableSource<R>> flatMap, final IBaseCallBack<R> callBack){

        observable.
                flatMap(flatMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(provider instanceof RxFragment ? ((RxFragment)provider).<R>bindUntilEvent(FragmentEvent.DESTROY) : ((RxAppCompatActivity)(provider)).<R>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if(callBack instanceof ICancelBaseCallBack){
                            ((ICancelBaseCallBack<R>) callBack).onStart(d);
                        }
                    }

                    @Override
                    public void onNext(R r) {
                        Logger.d("BaseRepository observer onNext %s ",r.toString());
                        callBack.onSuccess(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("BaseRepository observer onError %s",e.getMessage());
                        if(e instanceof ResultException){
                            callBack.onFail((ResultException) e);
                        }else if(e instanceof IOException){
                            callBack.onFail(new ResultException(e));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
