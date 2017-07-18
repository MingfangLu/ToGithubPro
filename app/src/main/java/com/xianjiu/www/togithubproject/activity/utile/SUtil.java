package com.xianjiu.www.togithubproject.activity.utile;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create by Stack_lu
 *
 */
public class SUtil {
    private static SUtil sUtil;

    private SUtil() {
    }

    public static SUtil getInstance() {
        if (sUtil == null) {
            sUtil = new SUtil();
        }
        return sUtil;
    }

    // private final static String test = "parkb_sharepreference";
    private final static String test                   = "online_parkb_sharepreference";
    // private final static String test = "offline_parkb_sharepreference";
    // SharedPreferences名字
    private final static String evalet_sharepreference = test;

    // 代泊员id保存
    private final String        DESID                  = "desid";
    // token保存
    private final String        TOKEN                  = "token";
    // 个人信息保存
    private final String        PERSONINFO             = "personinfo";                  // 代泊员信息
    // 定位的经纬度及地址
    private final String        LOCATIONINFO           = "locationinfo";                // 当前位置信息
    private final String        QIANDAOINFO            = "qianinfo";                    // 签到信息
    private final String        DES_PHONE              = "des_phone";                   // 代泊员电话

    private final String        DAIBODIAN_ID           = "daibodian_id";                // 保存签到代泊点id
    private final String        DAIBODIAN_NAME           = "daibodian_name";                // 保存签到代泊点名称
    private final String        DAIBODIAN_TYPE         = "daibodian_type";              // 保存签到代泊点类型
    private final String        TASKING_PARAMS         = "tasking_params";              // 保存有任务时上传经纬度所需参数
    private final String        NOTASK_PARAMS          = "notask_params";               // 保存无任务时上传经纬度所需参数
    private final String        TASK_ORDER_INFO        = "task_order_info";             // 保存正在执行的任务信息，字段以逗号分隔
    private final String        CURLOCATION            = "curLocation";
    private final String        PREVLOCATION           = "precLocation";
    private final String        ISOPERATOR             = "isOperator";                  // 是否具有创建非代泊订单权限
    private final String        LOG_CONTROL            = "log_control";                 // 是否是否打开日志
    private final String        JSON_CUR_POSITION_INFO = "json_cur_position_info";      // 当前位置信息
    private final String        VERSIONCODE            = "version_code";
    private final String        CITY_ID              = "city_id";                   // 城市id
    private final String        ISTRANSFER             = "istransfer";                  // 签到的代泊点是否支持挪车
    private final String        SELFPARK_SONG_SWITCH   = "selfpark_song_switch";        // 自助停车单铃声提示开关状态
    private static final String KEY_SP_SERVER_ENV      = "key_sp_server_env";           // 测试、test02环境前缀
    private final String        SYSTEM_REASON_PARAMS = "system_reason_params";//延迟/爽约原因、延迟/取消原因配置参数列表
    private final String        ISHAVESHOP             = "isHaveShop";                  // 签到的代泊点是否有养护门店
    private final String        IS_RECEPTION_MODE      = "isReceptionMode";             // 接待点模式

    // 签到的代泊点是否有养护门店
    public void setHaveShop(Context mContext, boolean isHaveShop) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
                .edit();
        editor.putBoolean(ISHAVESHOP, isHaveShop);
        editor.commit();
    }

    // 获取签到的代泊点是否有养护门店
    public boolean isHaveShop(Context context) {
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(ISHAVESHOP)) {
            return settings.getBoolean(ISHAVESHOP, false);
        } else {
            return false;
        }
    }

    // 保存延迟/爽约原因、延迟/取消原因配置参数列表
    public void setSysDriverDelayOrCancelReason(Context mContext, String paramsArray) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
                .edit();
        editor.putString(SYSTEM_REASON_PARAMS, paramsArray);
        editor.commit();
    }

    // 获取延迟/爽约原因、延迟/取消原因配置参数列表
    public String getSysDriverDelayOrCancelReason(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(SYSTEM_REASON_PARAMS)) {
            return settings.getString(SYSTEM_REASON_PARAMS, "");
        } else {
            return "";
        }
    }
    /**
     * 设置自助停车单铃声提示
     * 
     * @param mContext
     * @param isOpen
     */
    public void setSelfParkSongSwitch(Context mContext, boolean isOpen) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putBoolean(SELFPARK_SONG_SWITCH, isOpen);
        editor.commit();
    }

    /**
     * 获取自助停车单铃声提示
     * 
     * @param context
     * @return
     */
    public boolean getSelfParkSongSwitch(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(SELFPARK_SONG_SWITCH)) {
            return settings.getBoolean(SELFPARK_SONG_SWITCH, false);
        } else {
            return false;
        }
    }

    /**
     * 设置服务host前缀
     * 
     * @param mContext
     * @param serverEnv
     */
    public static void setServerEnv(Context mContext, String serverEnv) {
        if (mContext == null) {
            return;
        }
        SharedPreferences.Editor editor = mContext
            .getSharedPreferences(evalet_sharepreference, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_SP_SERVER_ENV, serverEnv);
        editor.commit();
    }

    /**
     * 获得服务host前缀
     * 
     * @param context
     * @return
     */
    public static String getServerEnv(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(KEY_SP_SERVER_ENV)) {
            return settings.getString(KEY_SP_SERVER_ENV, "test");
        } else {
            return "test";
        }
    }

    // 设置是否支持挪车
    public void setTransferFlag(Context mContext, boolean isTransfer) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putBoolean(ISTRANSFER, isTransfer);
        editor.commit();
    }

    // 获取代泊点是否支持挪车标志
    public boolean isTransfer(Context context) {
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(ISTRANSFER)) {
            return settings.getBoolean(ISTRANSFER, false);
        } else {
            return false;
        }
    }

    public void setCityId(Context mContext, long cityId) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putLong(CITY_ID, cityId);
        editor.commit();
    }

    public long getCityId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(CITY_ID)) {
            return settings.getLong(CITY_ID, 0);
        } else {
            return 0;
        }
    }

    public void setCurrPosition(Context mContext, String positionInfo) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(JSON_CUR_POSITION_INFO, positionInfo);
        editor.commit();
    }

    public String getCurrPosition(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(JSON_CUR_POSITION_INFO)) {
            return settings.getString(JSON_CUR_POSITION_INFO, "{}");
        } else {
            return "{}";
        }
    }

    public void setValetType(Context mContext, String type) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(DAIBODIAN_TYPE, type);
        editor.commit();
    }

    public String getValetType(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(DAIBODIAN_TYPE)) {
            return settings.getString(DAIBODIAN_TYPE, "");
        } else {
            return "";
        }
    }

    public void setOperator(Context mContext, boolean isOperator) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putBoolean(ISOPERATOR, isOperator);
        editor.commit();
    }

    public boolean getOperator(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(ISOPERATOR)) {
            return settings.getBoolean(ISOPERATOR, false);
        } else {
            return false;
        }
    }

    public void setToken(Context mContext, String token) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(TOKEN)) {
            return settings.getString(TOKEN, "");
        } else {
            return "";
        }
    }

    public void setDesPhone(Context mContext, String desphone) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(DES_PHONE, desphone);
        editor.commit();
    }

    public String getDesPhone(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(DES_PHONE)) {
            return settings.getString(DES_PHONE, "");
        } else {
            return "";
        }
    }

    public void setPersonInfo(Context mContext, String jsonString) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(PERSONINFO, jsonString);
        editor.commit();
    }

    public String getPersonInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(PERSONINFO)) {
            return settings.getString(PERSONINFO, "");
        } else {
            return "";
        }
    }

    public void setDesId(Context mContext, long desid) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putLong(DESID, desid);
        editor.commit();
    }

    public long getDesId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(DESID)) {
            return settings.getLong(DESID, 0);
        } else {
            return 0;
        }
    }

    public void setQianDaoInfo(Context mContext, String str) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(QIANDAOINFO, str);
        editor.commit();
    }

    public String getQianDaoInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(QIANDAOINFO)) {
            return settings.getString(QIANDAOINFO, null);
        } else {
            return null;
        }
    }

    public void setLocationInfo(Context mContext, String locationString) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(LOCATIONINFO, locationString);
        editor.commit();
    }

    public String getLocationInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(LOCATIONINFO)) {
            return settings.getString(LOCATIONINFO, "");
        } else {
            return "";
        }
    }

    public void setDaiBoDianId(Context mContext, String locationString) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(DAIBODIAN_ID, locationString);
        editor.commit();
    }

    public String getDaiBoDianId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(DAIBODIAN_ID)) {
            return settings.getString(DAIBODIAN_ID, "");
        } else {
            return "";
        }
    }

    public void setDaiBoDianName(Context mContext, String locationString) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
                .edit();
        editor.putString(DAIBODIAN_NAME, locationString);
        editor.commit();
    }

    public String getDaiBoDianName(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(DAIBODIAN_NAME)) {
            return settings.getString(DAIBODIAN_NAME, "");
        } else {
            return "";
        }
    }

    // 保存有任务时上传经纬度所需的参数
    public void setTaskingParams(Context mContext, String params) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(TASKING_PARAMS, params);
        editor.commit();
    }

    // 获取有任务时上传经纬度所需的参数
    public String getTaskingParams(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(TASKING_PARAMS)) {
            return settings.getString(TASKING_PARAMS, "");
        } else {
            return "";
        }
    }

    // 保存无任务时上传经纬度所需的参数
    public void setNoTaskParams(Context mContext, String params) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(NOTASK_PARAMS, params);
        editor.commit();
    }

    // 获取无任务时上传经纬度所需的参数
    public String getNoTaskParams(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(NOTASK_PARAMS)) {
            return settings.getString(NOTASK_PARAMS, "");
        } else {
            return "";
        }
    }

    // 保存正在执行的任务信息，以逗号分隔
    public void setTaskOrderInfo(Context mContext, String info) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(TASK_ORDER_INFO, info);
        editor.commit();
    }

    // 获取正在执行的任务信息
    public String getTaskOrderInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(TASK_ORDER_INFO)) {
            return settings.getString(TASK_ORDER_INFO, "");
        } else {
            return "";
        }
    }

    // 保存当前经纬度
    public void setCurLocation(Context mContext, String location) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(CURLOCATION, location);
        editor.commit();
    }

    // 获取当前经纬度
    public String getCurLocation(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(CURLOCATION)) {
            return settings.getString(CURLOCATION, "");
        } else {
            return "";
        }
    }

    // 保存上传的经纬度
    public void setPrevLocation(Context mContext, String location) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putString(PREVLOCATION, location);
        editor.commit();
    }

    // 获取上传的经纬度
    public String getPrevLocation(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(PREVLOCATION)) {
            return settings.getString(PREVLOCATION, "");
        } else {
            return "";
        }
    }

    // 设置日志开关
    public void setLogControl(Context mContext, boolean isOpen) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
            .edit();
        editor.putBoolean(LOG_CONTROL, isOpen);
        editor.commit();
    }

    // 获取日志开关
    public boolean getLogControl(Context context) {
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(LOG_CONTROL)) {
            return settings.getBoolean(LOG_CONTROL, false);
        } else {
            return false;
        }
    }

    public void setVersionCode(Context mContext, int value) {
        setData(mContext, VERSIONCODE, value);
    }

    public int getVersionCode(Context context) {
        return getIntData(context, VERSIONCODE);
    }

    // 设置代泊点是否为接待点模式
    public void setReceptionMode(Context mContext, boolean isReceptionMode) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(evalet_sharepreference, 0)
                .edit();
        editor.putBoolean(IS_RECEPTION_MODE, isReceptionMode);
        editor.commit();
    }

    // 获取签到的代泊点是否为接待点模式
    public boolean isReceptionMode(Context context) {
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(evalet_sharepreference, 0);
        if (settings.contains(IS_RECEPTION_MODE)) {
            return settings.getBoolean(IS_RECEPTION_MODE, false);
        } else {
            return false;
        }
    }
    /**
     * 设置数据
     * 
     * @param mContext
     * @param key
     * @param value
     */
    public void setData(Context mContext, String key, int value) {
        if (mContext == null) {
            return;
        }
        SharedPreferences.Editor editor = mContext
            .getSharedPreferences(evalet_sharepreference, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取数据
     * 
     * @param mContext
     * @param key
     * @return
     */
    public int getIntData(Context mContext, String key) {
        if (mContext == null) {
            return 0;
        }
        SharedPreferences settings = mContext.getSharedPreferences(evalet_sharepreference,
            Context.MODE_PRIVATE);
        if (settings.contains(key)) {
            return settings.getInt(key, 0);
        } else {
            return 0;
        }
    }
}
