package com.xianjiu.www.togithubproject.activity.net;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.gson.Gson;
import com.xianjiu.www.togithubproject.activity.activity.LoginActivity;
import com.xianjiu.www.togithubproject.activity.activity.BaseActivity;
import com.xianjiu.www.togithubproject.activity.net.entity.BaseEntity;
import com.xianjiu.www.togithubproject.activity.utile.AppUtils;
import com.xianjiu.www.togithubproject.activity.utile.LogUtil;
import com.xianjiu.www.togithubproject.activity.utile.PrefManager;
import com.xianjiu.www.togithubproject.activity.widget.BaseAppManager;

import java.util.HashMap;
import java.util.Map;

import static com.xianjiu.www.togithubproject.activity.base.BaseApplication.context;

public class RequestFactory {
    /**
     * 异步GET请求
     *
     * @param clickView
     * @param baseActivity
     * @param mHandler
     * @param result
     * @param api
     * @param successMsgId
     * @param failMsgId
     * @param <T>
     */
    private static <T extends BaseEntity> void doNetGetRequest(final View clickView, final BaseActivity baseActivity,
                                                               final Handler mHandler, Class<T> result, String api,
                                                               final int successMsgId, final int failMsgId) {
        // 禁用当前点击视图
        if (clickView != null) {
            clickView.setEnabled(false);
        }
        String baseUrl = PrefManager.getInstance(context).getServerEnv() ? RequestUrl.online :
                RequestUrl.test;
        String requestUrl = baseUrl + api + "?versionCode=" + AppUtils.getVersionCode(context) +
                "&token=" + PrefManager.getInstance(context).getLoginToken() + "&staffId=" +
                PrefManager.getInstance(context).getStaffId() + "&shopCode=" + PrefManager
                .getInstance(context).getShopCode();
        LogUtil.e("1", "requestUrl==" + requestUrl);
        OkHttpClientManager.getAsyn(requestUrl, new ResultCallback<T>(result) {
            @Override
            public void onError(BaseError baseError) {
                if (clickView != null) {
                    clickView.setEnabled(true);
                }
                if (baseActivity != null) {
                    baseActivity.hidnLoadingView();
                    if (baseError.getErrorCode() == BaseError.STATUS_TOKEN_ERROR) {
                        Intent intent = new Intent(baseActivity, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        baseActivity.startActivity(intent);
                        BaseAppManager.finishActivityExceptThis(LoginActivity.class);
                    }
                }
                if (mHandler != null) {
                    Message message = Message.obtain();
                    message.what = failMsgId;
                    message.obj = baseError;
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onResponse(T response) {
                if (clickView != null) {
                    clickView.setEnabled(true);
                }
                if (baseActivity != null) {
                    baseActivity.hidnLoadingView();
                }
                if (mHandler != null) {
                    Message message = Message.obtain();
                    message.what = successMsgId;
                    message.obj = response.getData();
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 异步Post请求
     *
     * @param clickView
     * @param baseActivity
     * @param mHandler
     * @param result
     * @param api
     * @param paramMap
     * @param successMsgId
     * @param failMsgId
     * @param <T>
     */
    private static <T extends BaseEntity> void doNetPostRequest(final View clickView, final BaseActivity
            baseActivity, final Handler mHandler, Class<T> result, String api, Map<String, String> paramMap, final
                                                                int successMsgId, final int failMsgId) {
        // 禁用当前点击视图
        if (clickView != null) {
            clickView.setEnabled(false);
        }
        // 添加基础参数
        if (paramMap == null) {
            paramMap = new HashMap<>();
        }
        paramMap.put("versionCode", String.valueOf(AppUtils.getVersionCode(context)));
        paramMap.put("token", PrefManager.getInstance(context).getLoginToken());
        paramMap.put("staffId", PrefManager.getInstance(context).getStaffId());
        paramMap.put("shopCode", PrefManager.getInstance(context).getShopCode());
        paramMap.put("ttid", AppUtils.getVersionCode(context) + "parkmecnsa" + AppUtils.getVersionName(context));
        LogUtil.e("1", "=============start=============");
        for (String key : paramMap.keySet()) {
            LogUtil.i("1", key + "=" + paramMap.get(key));
        }
        String baseUrl = PrefManager.getInstance(context).getServerEnv() ? RequestUrl.online :
                RequestUrl.test;
        LogUtil.e("1", "requestUrl==" + baseUrl + api);
        LogUtil.e("1", "=============end=============");
        OkHttpClientManager.postAsyn(baseUrl + api, paramMap, new ResultCallback<T>(result) {
            @Override
            public void onError(BaseError baseError) {
                if (clickView != null) {
                    clickView.setEnabled(true);
                }
                if (baseActivity != null) {
                    //baseActivity.hidnLoadingView();
                    if (baseError.getErrorCode() == BaseError.STATUS_TOKEN_ERROR) {
                        // Intent intent = new Intent(baseActivity, ActivityLogin.class);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // baseActivity.startActivity(intent);
                        // BaseAppManager.finishActivityExceptThis(ActivityLogin.class);
                    }
                }
                if (mHandler != null) {
                    Message message = Message.obtain();
                    message.what = failMsgId;
                    message.obj = baseError;
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onResponse(T response) {
                if (clickView != null) {
                    clickView.setEnabled(true);
                }
                if (baseActivity != null) {
                    // baseActivity.hidnLoadingView();
                }
                if (mHandler != null) {
                    Message message = Message.obtain();
                    message.what = successMsgId;
                    message.obj = response.getData();
                    mHandler.sendMessage(message);
                }
            }
        });
    }


    // 用于上传复杂json的
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    // 用于上传复杂json的
    public static <T> String beanToJson(FastBillsRequest request) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(request);
        return jsonStr;
    }

}
