package com.xianjiu.www.togithubproject.activity.widget;

import android.app.Activity;

import com.xianjiu.www.togithubproject.activity.activity.BaseActivity;

import java.util.LinkedList;
import java.util.List;

public class BaseAppManager {

    private static BaseAppManager     instance    = null;
    private static List<BaseActivity> mActivities = new LinkedList<>();

    private BaseAppManager() {

    }

    public synchronized static BaseAppManager getInstance() {
        if (instance == null) {
            instance = new BaseAppManager();
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    public synchronized void addActivity(BaseActivity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(BaseActivity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clear(boolean isAnim) {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            BaseActivity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish(isAnim);
            i = mActivities.size();
        }
    }

    public synchronized void clearToTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            BaseActivity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }

    /**
     * 关闭指定Activity
     *
     * @param clazz
     */
    public static synchronized void finishActivity(Class clazz) {
        for (BaseActivity ac : mActivities) {
            if (ac != null && ac.getClass().getCanonicalName().equals(clazz.getCanonicalName())) {
                ac.finish();
                break;
            }
        }
    }

    /**
     * 关闭所有Activity，除了当前指定activity
     *
     * @param clazz
     */
    public static synchronized void finishActivityExceptThis(Class clazz) {
        BaseActivity targetActivity = null;
        for (BaseActivity ac : mActivities) {
            if (ac != null && ac.getClass().getCanonicalName().equals(clazz.getCanonicalName())) {
                targetActivity = ac;
                continue;
            }
            if (ac != null) {
                ac.finish();
            }
        }
        mActivities.clear();
        mActivities.add(targetActivity);
    }
}