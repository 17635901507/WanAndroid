package com.android.wanandroid.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/3 22:05
 */
public class LIWebView extends WebView {

    public LIWebView(Context context, boolean b) {
        super(context, b);
    }

    public LIWebView(Context context) {
        super(context);
    }

    public LIWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LIWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public LIWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public LIWebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Context getFixedContext(Context context) {
        return context.createConfigurationContext(new Configuration());
    }
}
