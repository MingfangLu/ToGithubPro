/**
 * BokeMecn
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.xianjiu.www.togithubproject.activity.net;


import okhttp3.Request;

/**
 * 客户端版本太低，需要强制升级的异常
 * 
 * @author wangfan
 * @version $Id: LowVersionError.java, v 0.1 2016-3-9 上午9:51:36 wangfan Exp $
 */
public class LowVersionError extends BaseError {

    /**
     * 更新的说明信息
     */
    private String releaseNote;
    
    /**
     * 目标地址，更新地址
     */
    private String url;
    
    /**
     * 更新的标题
     */
    private String title;
    
    /**
     * 
     */
    public LowVersionError() {
    }

    public LowVersionError(String releaseNote, String url, String title) {
        super();
        this.releaseNote = releaseNote;
        this.url = url;
        this.title = title;
    }
    public LowVersionError(Request request, Exception exception, int errorCode, String message, String releaseNote, String url, String title) {
        super(request,exception,errorCode,message);
        this.releaseNote = releaseNote;
        this.url = url;
        this.title = title;
    }


    public String getReleaseNote() {
        return releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
