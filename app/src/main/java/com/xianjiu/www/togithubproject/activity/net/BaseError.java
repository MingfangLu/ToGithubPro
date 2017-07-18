package com.xianjiu.www.togithubproject.activity.net;

import okhttp3.Request;

/**
 * Created by Shanshan on 2017/3/21.
 */

public class BaseError {
    /**
     * 未知异常
     */
    public static final int STATUS_UNKNOW = -1;
    /**
     * 返回成功的状态
     */
    public static final int STATUS_SUCCESS = 0;
    /**
     * 网络请求异常
     */
    public static final int STATUS_REQUEST_ERROR =1001;
    /**
     * 解析异常
     */
    public static final int STATUS_PARSE_ERROR =1002;

    /**
     * 后台返回数据异常
     */
    public static final int STATUS_DATA_ERROR =1003;
    /**
     * 业务逻辑
     */
    public static final int STATUS_BIZ_ERROR = 2000;
    /**
     * 版本过低
     */
    public static final int STATUS_LOW_VERSION_ERROR = 2100;
    /**
     * app错误
     */
    public static final int STATUS_APP_ERROR = 3000;

    /**
     * 登录状态失效
     */
    public static final int STATUS_TOKEN_ERROR = 4000;


    private Request   request;
    private Exception exception;
    private int errorCode = STATUS_UNKNOW;
    private String message;

    public BaseError() {
    }
    public BaseError(Request request, Exception exception) {
        this.request = request;
        this.exception = exception;
    }

    public BaseError(Request request, Exception exception, int errorCode, String message) {
        this.request = request;
        this.exception = exception;
        this.errorCode = errorCode;
        this.message=message;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
