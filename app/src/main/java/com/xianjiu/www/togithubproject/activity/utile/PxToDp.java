package com.xianjiu.www.togithubproject.activity.utile;

import android.content.Context;

/**
 * 像素和dip的转换
 */
public class PxToDp {
    /**
     * 根据手机的分辨率dp转成px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率px(像素)转成dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param uipx 640UI图上标记的 像素值
     * @return 返回 当前手机对等比例的像素值
     * @Description: (用一句话描述该方法做什么) 根据UI图计算手机对应的像素值
     */
    public static int getRelyPx(Context context, int uipx) {
        float uiScale = ((float) context.getResources().getDisplayMetrics().widthPixels) / 640;
        return (int) (uiScale * uipx);
    }
}
