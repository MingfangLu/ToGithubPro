package com.xianjiu.www.togithubproject.activity.utile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.xianjiu.www.togithubproject.BuildConfig;
import com.xianjiu.www.togithubproject.activity.base.BaseApplication;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogUtil {

    public static final String         TAG   = "LogUtil";

    // public static boolean DEBUG = BuildConfig.DEBUG;
    private static boolean             DEBUG = !BuildConfig.isOnline;

    // 用来存储设备信息和异常信息
    private static Map<String, String> infos = new HashMap<String, String>();

    public static void setDebugFlag(boolean debug) {
        DEBUG = debug;
    }

    // private static final Logger logger = LoggerFactory.getLogger(Log.class);
    /**
     * 打印 info
     * 
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag,msg);
        }
    }

    public static void i(String tag, Throwable ex) {
        i(tag,prepareExceptionIfo(ex));
    }

    public static void i(String msg) {
        i(TAG,msg);
    }

    /**
     * 打印 debug信息
     * 
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag,msg);
            logToFile(msg,FileUtils.getTodayLogFilePath());
        }
    }

    public static void d(String tag, Throwable ex) {
        d(tag,prepareExceptionIfo(ex));
    }

    public static void d(String msg) {
        d(TAG,msg);
    }

    /**
     * 打印 error 信息
     * 
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag,msg);
            logToFile(msg,FileUtils.getTodayLogFilePath());
        }
    }
    /**
     * 打印 error 信息
     *
     * @param tag
     * @param msg
     * @param logToFile 是否将日志记录到本地
     */
    public static void e(String tag, String msg,boolean logToFile) {
        if (DEBUG||logToFile) {
            Log.e(tag,msg);
            logToFile(msg,FileUtils.getTodayLogFilePath());
        }
    }

    public static void e(String tag, Throwable ex) {
        e(tag,prepareExceptionIfo(ex));
    }

    public static void e(Throwable ex) {
        e(TAG,prepareExceptionIfo(ex));
    }

    public static void e(String msg) {
        e(TAG,msg);
    }

    /**
     * 将错误日志写入本地文件
     * 
     * @param msg
     * @param fileName
     */
    public static void logToFile(String msg, String fileName) {
        //检测是否具有写SD卡权限，如果没有则不再继续
        if (ContextCompat.checkSelfPermission(BaseApplication.getContext(), Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            FileUtils.writeStringToFile(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " : "
                    + msg + "\n",fileName);
        } catch (Throwable e) {
            // 无法写文件
        }
    }

    /**
     * 将异常情况打印至文件
     * 
     * @param context
     * @param ex
     * @param fileName
     */
    public static void logToFile(Context context, Throwable ex, String fileName) {
        if (context == null) {
            return;
        }
        String msg = collectDeviceInfo(context);
        msg += prepareExceptionIfo(ex);
        logToFile(msg,fileName);
    }

    /**
     * 处理异常信息，获得异常的堆栈信息
     * 
     * @param ex
     * @return
     */
    public static String prepareExceptionIfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        return result;
    }

    /**
     * 收集设备参数信息
     * 
     * @param ctx
     */
    public static String collectDeviceInfo(Context ctx) {
        if (infos.isEmpty()) {
            try {
                PackageManager pm = ctx.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),PackageManager.GET_ACTIVITIES);

                if (pi != null) {
                    String versionName = pi.versionName == null ? "null" : pi.versionName;
                    String versionCode = pi.versionCode + "";
                    infos.put("versionName",versionName);
                    infos.put("versionCode",versionCode);
                }
            } catch (NameNotFoundException e) {
                e(TAG,e);
            }

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    infos.put(field.getName(),field.get(null).toString());
                    d(TAG,field.getName() + " : " + field.get(null));
                } catch (Exception e) {
                    e(TAG,e);
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("FINGERPRINT=" + infos.get("FINGERPRINT") + "\n");
        sb.append("HOST=" + infos.get("HOST") + "\n");
        sb.append("versionCode=" + infos.get("versionCode") + "\n");
        sb.append("versionName=" + infos.get("versionName") + "\n");
        sb.append("HARDWARE=" + infos.get("HARDWARE") + "\n");
        sb.append("DISPLAY=" + infos.get("DISPLAY") + "\n");
        sb.append("CPU_ABI=" + infos.get("CPU_ABI") + "\n");
        sb.append("CPU_ABI2=" + infos.get("CPU_ABI2") + "\n");
        sb.append("IS_DEBUGGABLE=" + infos.get("IS_DEBUGGABLE") + "\n");
        sb.append("SERIAL=" + infos.get("SERIAL") + "\n");
        return sb.toString();
    }

    //可以全局控制是否打印log日志
    private static boolean isPrintLog = true;

    private static int LOG_MAXLENGTH = 2000;

    public static void LogAllCat(String msg) {
        if (isPrintLog) {

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e("LogAllCat" + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e("LogAllCat" + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void LogAllCat(String type, String msg) {

        if (isPrintLog) {

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(type + "_" + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(type + "_" + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

}
