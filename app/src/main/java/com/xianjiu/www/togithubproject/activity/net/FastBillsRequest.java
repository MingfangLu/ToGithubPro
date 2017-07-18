package com.xianjiu.www.togithubproject.activity.net;

import java.util.List;

/**
 * Created by Administrator on 2016-05-31.
 */
public class FastBillsRequest {
    private FastBillsBaseInfo        baseInfo;
    private List<String>             fileArray;
    private String                   signFile;

    public FastBillsBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(FastBillsBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public void setFileArray(List<String> fileArray) {
        this.fileArray = fileArray;
    }

    public void setSignFile(String signFile) {
        this.signFile = signFile;
    }
}
