package com.kkk.mvp.exceptions;

import android.content.Context;

import com.android.wanandroid.R;
import com.android.wanandroid.app.BaseApp;

import java.io.IOException;

/**
 * @author kaixinli
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/9/29 18:17
 */
public class ResultException extends Throwable{
    public static final int SERVER_ERROR = 0x1000000;

    public int code;

    public Throwable source;

    public ResultException(Throwable source) {
        this.source = source;
    }

    public ResultException(int code) {
        super("");
        this.code = code;
    }

    public ResultException(int code,String message){
        super(message);
        this.code = -1;
    }

    private String getMessage(Context context){
        if(source != null && source instanceof IOException){
            return context.getResources().getString(R.string.text_error_net_exception);
        }

        if(code == SERVER_ERROR){
            return context.getResources().getString(R.string.text_error_server_exception);
        }

        return context.getResources().getString(R.string.text_error_un_know_exception);
    }

    @Override
    public String getMessage() {
        return getMessage(BaseApp.getInstance());
    }
}
