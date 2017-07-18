package com.xianjiu.www.togithubproject.activity.net.entity;

public class BaseEntity<T> {
    // javaBean的父类,所有数据都具有这两个字段
    // 只要子类继承，子类就能调用这两个字段
    private   String message;
    private   int    status;
    private   int    version;
    protected T      data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
