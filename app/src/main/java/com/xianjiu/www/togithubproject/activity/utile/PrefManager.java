package com.xianjiu.www.togithubproject.activity.utile;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.xianjiu.www.togithubproject.BuildConfig;

/**
 * 封装常量
 */
public class PrefManager {

    private SharedPreferences pref_user     = null;
    private SharedPreferences pref_settings = null;
    private SharedPreferences pref_cache    = null;

    //登录的用户信息
    private static final String LOGIN_USER_DATA = "LOGIN_USER_DATA";

    //用户参数
    private static final String USER_NAME = "user_name";

    private static final String USER_PASSWORD = "user_password";

    private static final String CAR_NUM_PREFIX = "car_num_prefix";
    /**
     * 登录后返回的token
     */
    private static final String TOKEN_LOGIN    = "TOKEN_LOGIN";
    /**
     * 当前登录员工的id
     */
    private static final String STAFFID_LOGIN  = "STAFFID_LOGIN";

    /**
     * 员工所属店铺编码
     */
    private static final String SHOP_CODE = "SHOP_CODE";


    //设置参数
    private static final String TEST_IP = "ip";

    private static final String TEST_PROT = "port";

    private static final String TEST_USE = "is_use";

    /**
     * 是否匹配车牌前缀
     */
    private static final String IS_OPEN_CAR_PREFIX = "is_open_car_prefix";

    /**
     * 只显示 未完成的订单
     */
    private static final String IS_ONLY_SHOW_UNFINISHED_ORDER = "is_only_show_unfinished_order";

    /**
     * 订单列表是否展开
     */
    private static final String IS_SHOW_ORDER_LIST_EXPAND = "is_show_order_list_expand";

    /**
     * 当前的版本code
     */
    private static final String CURRENT_VERSION_CODE = "current_version_code";

    //订单下自定义返回字段数据
    public static final  String ORDER_CUSTOM_RETURN_FIELD_DATA = "order_custom_return_field_data";
    // 测试、线上环境前缀
    private static final String KEY_SP_SERVER_ENV              = "key_sp_server_env";


    private static PrefManager prefManager;
    private static Gson mGson;

    public static PrefManager getInstance(Context context) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager;
    }

    private Context context;

    private PrefManager(Context context) {
        this.context = context;
        pref_user = context.getSharedPreferences("pref_user", Context.MODE_PRIVATE);
        pref_settings = context.getSharedPreferences("pref_settings", Context.MODE_PRIVATE);
        pref_cache = context.getSharedPreferences("pref_cache", Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    /**
     * 保存用户信息
     *
     * @param username
     * @param password
     */
    public void setUserInfo(String username, String password, String carNumPrefix) {
        pref_user.edit().putString(USER_NAME, username).putString(USER_PASSWORD, password).putString(CAR_NUM_PREFIX,
                carNumPrefix).commit();
    }

    public String getUserName() {
        return pref_user.getString(USER_NAME, null);
    }

    public String getUserPassword() {
        return pref_user.getString(USER_PASSWORD, null);
    }

    /**
     * 车牌前缀
     *
     * @return
     */
    public String getCarNumPrefix() {
        return pref_user.getString(CAR_NUM_PREFIX, "");
    }

    /**
     * 清空账户密码
     */
    public void clearPassWord() {
        pref_user.edit().putString(USER_PASSWORD, null).commit();
    }

    /**
     * 退出账号后清空缓存
     */
    public void clearDataForLogout() {
        pref_user.edit().clear().commit();
        pref_cache.edit().clear().commit();
//        pref_settings.edit().clear().commit();
    }

    public boolean isLogin() {
        return false;
    }


    //设置

    /**
     * 设置ip 端口
     *
     * @param ip
     * @param port
     */
    public void setRequestAddress(String ip, String port, boolean isUse) {
        pref_settings.edit().putString(TEST_IP, ip).putString(TEST_PROT, port).putBoolean(TEST_USE, isUse).commit();
    }

    /**
     * 设置是否开启匹配车牌前缀
     */
    public void setIsOpenCarPrefix(boolean isOpenCarPrefix) {
        pref_settings.edit().putBoolean(IS_OPEN_CAR_PREFIX, isOpenCarPrefix).commit();
    }

    /**
     * 是否只显示未完成的订单
     *
     * @param isOnlyShow
     */
    public void setIsOnlyShowUnfinishedOrder(boolean isOnlyShow) {
        pref_settings.edit().putBoolean(IS_ONLY_SHOW_UNFINISHED_ORDER, isOnlyShow).commit();
    }

    public void setIsShowOrderListExpand(boolean isShowOrderListExpand) {
        pref_settings.edit().putBoolean(IS_SHOW_ORDER_LIST_EXPAND, isShowOrderListExpand).commit();
    }

    public void setCurrentVersionCode(int versionCode) {
        pref_settings.edit().putInt(CURRENT_VERSION_CODE, versionCode).commit();
    }

    public int getCurrentVersionCode() {
        return pref_settings.getInt(CURRENT_VERSION_CODE, -1);
    }

    /**
     * 获取 是否只显示未完成的订单
     *
     * @return
     */
    public boolean isOnlyShowUnfinishedOrder() {
        return pref_settings.getBoolean(IS_ONLY_SHOW_UNFINISHED_ORDER, false);
    }

    /**
     * 是否开启匹配车牌前缀
     *
     * @return
     */
    public boolean isOpenCarPrefix() {
        return pref_settings.getBoolean(IS_OPEN_CAR_PREFIX, true);
    }

    public String getRequestAddressIP() {
        return pref_settings.getString(TEST_IP, null);
    }

    public String getRequestAddressPort() {
        return pref_settings.getString(TEST_PROT, null);
    }

    public boolean isUseTestAddress() {
        return pref_settings.getBoolean(TEST_USE, true);
    }

    public boolean isOrderListExpand() {
        return pref_settings.getBoolean(IS_SHOW_ORDER_LIST_EXPAND, false);
    }

    //cache

    /**
     * 保存缓存数据
     *
     * @param key
     * @param value
     */
    public void setCacheValue(String key, String value) {
        SharedPreferences.Editor ed = pref_cache.edit();
        ed.putString(key, value);
        ed.commit();
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    public String getCacheValue(String key) {
        return pref_cache.getString(key, null);
    }

    /**
     * 保存登录Token信息
     *
     * @param value
     */
    public void setLoginToken(String value) {
        SharedPreferences.Editor ed = pref_user.edit();
        ed.putString(TOKEN_LOGIN, value);
        ed.commit();
    }

    /**
     * 获取登录token
     *
     * @return
     */
    public String getLoginToken() {
        if (pref_user.contains(TOKEN_LOGIN)) {
            return pref_user.getString(TOKEN_LOGIN, "");
        } else {
            return "";
        }
    }

    /**
     * 保存登录Token信息
     *
     * @param value
     */
    public void setStaffId(String value) {
        SharedPreferences.Editor ed = pref_user.edit();
        ed.putString(STAFFID_LOGIN, value);
        ed.commit();
    }

    /**
     * 获取登录token
     *
     * @return
     */
    public String getStaffId() {
        if (pref_user.contains(STAFFID_LOGIN)) {
            return pref_user.getString(STAFFID_LOGIN, "");
        } else {
            return "";
        }
    }

    /**
     * 设置服务host前缀
     *
     * @param isOnline true代表线上，false代表线下
     */
    public void setServerEnv(boolean isOnline) {
        SharedPreferences.Editor editor = pref_settings.edit();
        editor.putBoolean(KEY_SP_SERVER_ENV, isOnline);
        editor.commit();
    }

    /**
     * 获得服务host前缀
     * true代表线上，false代表线下
     *
     * @return
     */
    public boolean getServerEnv() {
        if (pref_settings.contains(KEY_SP_SERVER_ENV)) {
            return pref_settings.getBoolean(KEY_SP_SERVER_ENV, BuildConfig.isOnline);
        } else {
            return BuildConfig.isOnline;
        }
    }

    /**
     * 保存店铺编码
     *
     * @param value
     */
    public void setShopCode(String value) {
        SharedPreferences.Editor ed = pref_user.edit();
        ed.putString(SHOP_CODE, value);
        ed.commit();
    }

    /**
     * 获取店铺编码
     *
     * @return
     */
    public String getShopCode() {
        if (pref_user.contains(SHOP_CODE)) {
            return pref_user.getString(SHOP_CODE, "");
        } else {
            return "";
        }
    }

}
