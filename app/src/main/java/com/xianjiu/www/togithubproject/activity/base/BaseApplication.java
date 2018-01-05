package com.xianjiu.www.togithubproject.activity.base;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class BaseApplication extends Application {
    /**
     * 全局上下文,方便使用
     */
    public static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseApplication.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        // Logger 的打印数据
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
