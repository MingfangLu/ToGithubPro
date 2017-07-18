package com.xianjiu.www.togithubproject.activity.base;

import android.app.Application;
import android.content.Context;
import com.xianjiu.www.togithubproject.activity.utile.LogUtil;

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
    }

    // 用作全局的 用来判断施工任务中是否已经有施工任务的拍照完成
    private int    photoYesOrNotFlage;
    private String id;

    public int getPhotoYesOrNotFlage(String id) {
        LogUtil.d("getPhotoYesOrNotFlage",id+"====id");
//        LogUtil.d("getPhotoYesOrNotFlage",idStr+"====idStr");

//        if (this.id == idStr) {
        //
        //        } else {
        //            return 0;
        //        }
        if (id!=null){

            return photoYesOrNotFlage;
        }else {
            return 0;
        }
    }

    public void setPhotoYesOrNotFlage(String id, int photoYesOrNotFlage) {
        this.id = id;
        this.photoYesOrNotFlage = photoYesOrNotFlage;
    }
}
