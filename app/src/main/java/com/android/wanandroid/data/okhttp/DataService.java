package com.android.wanandroid.data.okhttp;

import com.kkk.mvp.ok.ApiDataService;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 9:09
 */
public class DataService {

    public static ApiService getApiService(){
        return (ApiService) ApiDataService.getApiService();
    }
}
