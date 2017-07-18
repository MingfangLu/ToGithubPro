package com.xianjiu.www.togithubproject.activity.net;

import com.xianjiu.www.togithubproject.BuildConfig;

/**
 * Created by Shanshan on 2017/3/21.
 */

public class RequestUrl {
    public static       boolean isOnline = BuildConfig.isOnline;
    //测试网址
    //public final static String test = "http://";
    public final static String  test     = "http://";
    //线上
    public final static String  online   = "https://";
    //    private static String BASE_URL = isOnline ? online : test;
    private static      String  BASE_URL = isOnline ? online : test;

}
