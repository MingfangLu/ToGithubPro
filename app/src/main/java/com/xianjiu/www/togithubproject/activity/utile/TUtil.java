package com.xianjiu.www.togithubproject.activity.utile;

import android.text.TextUtils;
import android.widget.Toast;

import com.xianjiu.www.togithubproject.activity.base.BaseApplication;

/**
 * Create by Stack_lu
 *
 */
public class TUtil {
    //    private static EvaletToast toast;
    //
    //    public static void s(String msg) {
    //        if (!TextUtils.isEmpty(msg)) {
    //            int duration = 5;// 单位秒
    //            if (toast != null) {
    //                toast.cancel();
    //                toast = null;
    //            }
    //            toast = EvaletToast.makeText(BaseApplication.getContext(),msg,duration);
    //            toast.show();
    //        }
    //    }

    private static Toast toast = null; //用于判断是否已有Toast执行

    public static void s(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT); //正常执行
        } else {
            toast.setText(text); //用于覆盖前面未消失的提示信息
        }
        toast.show();
    }

    public static void s(int msgId) {
        s(BaseApplication.getContext().getResources().getString(msgId));
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
