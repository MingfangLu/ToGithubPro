package com.xianjiu.www.togithubproject.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianjiu.www.togithubproject.R;
import com.xianjiu.www.togithubproject.activity.base.BaseApplication;
import com.xianjiu.www.togithubproject.activity.fragment.BaseFragment;
import com.xianjiu.www.togithubproject.activity.utile.LogUtil;
import com.xianjiu.www.togithubproject.activity.utile.PrefManager;
import com.xianjiu.www.togithubproject.activity.utile.ToastUtil;
import com.xianjiu.www.togithubproject.activity.widget.TitleActionBar;

import java.util.Arrays;
import java.util.HashMap;


/**
 * 二级.三级界面,Activity基类
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener {

    /**
     * 请求 进入登录/详情界面code
     */
    public static final int REQUEST_USER_CODE = 101;

    BaseApplication mApp;
    PrefManager     mPrefManager;

    // 标题栏
    private TitleActionBar titleActionBar;
    //状态栏
    private View           layout_state_title;

    private   View         layout_loading;
    private   TextView     tv_loading_content;
    private   LinearLayout layout_container;
    protected View         view_ActionBar_Right;//标题栏右上角的按钮

    // 退出动画
    private boolean isFinishAnim = true;

    /**
     * 是否为第一次加载
     */
    boolean isFirstLoad = true;

    /**
     * 请求数据类
     */
    private FragmentTransaction mFragementTransaction;

    public  Handler         responseHandler;
    private BaseApplication mApplication;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_container);
        responseHandler = new ResponseHandler();
        mApp = (BaseApplication) getApplication();
        mPrefManager = PrefManager.getInstance(this);
        layout_container.addView(getLayoutInflater().inflate(getLayoutId(), null), LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        // setTranslucentStatus();
        titleActionBar.setVisibility(isActionBarVisible() ? View.VISIBLE : View.GONE);
        titleActionBar.setTitle(getTitleText());
        titleActionBar.addLeftButtonAction(new TitleActionBar.Action() {

            @Override
            public void action(View v) {
                onBackPressed();
            }
        });
        mApplication = (BaseApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFontConfig();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 更新字体模式大小
     */
    private void updateFontConfig() {
        Resources res = getResources();
        Configuration c = res.getConfiguration();
        c.fontScale = 1.0f;
        res.updateConfiguration(c, res.getDisplayMetrics());
    }

    /**
     *
     */
    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
    }

    /**
     * 界面销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //BaseAppManager.getInstance().removeActivity(this);
        // stopAsyncTask();
    }

    /**
     * 界面跳转携带过来的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public TitleActionBar getTitleActionBar() {
        return titleActionBar;
    }

    /**
     * 设置是否显示actionBar ，default:显示
     *
     * @param isShow
     */
    public void setTitleActionBarShow(boolean isShow) {
        if (titleActionBar != null) {
            titleActionBar.setVisibility(View.GONE);
        }
    }

    /**
     * 更新状态栏背景颜色
     *
     * @param color 资源文件
     */
    public void updateStatusTitleColor(int color) {
        layout_state_title.setBackgroundResource(color);
    }

    /**
     * 设置是否启用退出动画效果
     *
     * @param isAnim
     */
    public void finish(boolean isAnim) {
        isFinishAnim = isAnim;
        finish();
    }

    /**
     * 界面关闭的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        if (isFinishAnim) {
            overridePendingTransition(R.anim.activity_close_in_to_right, R.anim.activity_close_out_to_right);
        }
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 提交进度对话框
     */
    private ProgressDialog mSubmitPDialog = null;

    /**
     * 显示提交进度框
     *
     * @param message 默认显示：正在提交数据， 不加...
     */
    public void showSubmittingDialog(String message) {
        if (mSubmitPDialog == null) {
            mSubmitPDialog = new ProgressDialog(this);
            mSubmitPDialog.setCanceledOnTouchOutside(false);
        }
        mSubmitPDialog.setMessage((TextUtils.isEmpty(message) ? "正在提交数据" : message) + "...");
        mSubmitPDialog.show();
    }

    /**
     * 关闭提交进度框
     */
    public void closeSubmittingDialog() {
        if (mSubmitPDialog != null && mSubmitPDialog.isShowing()) {
            mSubmitPDialog.dismiss();
        }
    }

    /**
     * 隐藏正在加载进度框
     */
    public void hidnLoadingView() {
        if (layout_loading != null) {
            layout_loading.setVisibility(View.GONE);
        }
        if (tv_loading_content != null)
            tv_loading_content.setText("");
    }

    /**
     * 显示正在加载进度框
     */
    public void showLoadingView() {
        showLoadingView(null);
    }

    /**
     * 显示正在加载进度框
     */
    public void showLoadingView(String message) {
        if (layout_loading != null) {
            layout_loading.setVisibility(View.VISIBLE);
        }
        if (tv_loading_content != null) {
            tv_loading_content.setText(message);
        }
    }

    /**
     * Fragment跳转
     *
     * @param resId
     * @param fromFragment
     * @param toFragment
     * @param tag
     * @return
     */
    public BaseFragment turnToFragment(int resId, BaseFragment fromFragment, BaseFragment toFragment, String tag) {
        mFragementTransaction = getSupportFragmentManager().beginTransaction();
        /**
         * 如果要切换到的Fragment没有被Fragment事务添加，则隐藏被切换的Fragment，添加要切换的Fragment 否则，则隐藏被切换的Fragment，显示要切换的Fragment
         */
        if (!toFragment.isAdded()) {
            if (fromFragment != null) {
                mFragementTransaction.hide(fromFragment);
            }
            mFragementTransaction.add(resId, toFragment, tag);
        } else {
            mFragementTransaction.hide(fromFragment).show(toFragment);
        }
        mFragementTransaction.commit();

        return toFragment;
    }

    /**
     * 移除栈里所有的历史fragment
     */
    public void removeAllFragment() {
        int count = getFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            getFragmentManager().popBackStack();
        }
    }


    /**
     * 跳转
     *
     * @param clazz
     */
    public void goToWithNoData(Class clazz) {
        goToWithData(clazz, null);
    }

    /**
     * 跳转
     *
     * @param clazz
     * @param bundle
     */
    public void goToWithData(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityWithAnimation_FromRightEnter(intent);
    }

    /**
     * 带返回值的跳转
     *
     * @param clazz
     * @param requestCode
     */
    public void goToWithNoDataForResult(Class clazz, int requestCode) {
        goToWithDataForResult(clazz, null, requestCode);
    }

    /**
     * 带返回值的跳转
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void goToWithDataForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResultWithAnimation_FromRightEnter(intent, requestCode);
    }

    /**
     * 带动画 跳转Activity 动画效果为(只有从右侧进入动画,水平移动进入)
     *
     * @param intent
     */
    public void startActivityWithAnimation_FromRightEnter(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.activity_open_in_from_right, R.anim.activity_open_out_from_right);
    }

    /**
     * 带动画 跳转Activity并且带返回值 动画效果为(只有从右侧进入动画,水平移动进入)
     *
     * @param intent
     */
    public void startActivityForResultWithAnimation_FromRightEnter(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_open_in_from_right, R.anim.activity_open_out_from_right);
    }

    /**
     * ActionBar是否可见
     *
     * @return
     */
    public boolean isActionBarVisible() {
        return true;
    }

    public abstract int getLayoutId();

    /**
     * 头标题
     *
     * @return
     */
    public abstract String getTitleText();

    /**
     * 权限申请回调处理集合
     * Key  权限名
     * Value    权限申请回调处理接口
     */
    private static HashMap<String, CheckPermissionListener> permissionCallBackMap = new HashMap<>();

    /**
     * 权限申请说明，即为什么要申请此权限的权限说明集合
     * Key  权限名
     * Value    权限申请说明
     */
    private static HashMap<String, String> permissionExplanationMap = new HashMap<>();

    //初始化权限申请说明的集合
    static {
        //暂无
    }

    //权限申请的请求代码
    private static final int REQUEST_CODE_PERMISSIONS_REQUEST = 10;

    /**
     * 校验权限是否授予
     *
     * @param permissionName          具体取值参照 Manifest.permission.XXX_XX
     * @param checkPermissionListener 授权结果的回调
     */
    public void checkPermission(String permissionName, CheckPermissionListener checkPermissionListener) {
        if (TextUtils.isEmpty(permissionName)) {
            if (checkPermissionListener != null) {
                checkPermissionListener.onPermissionDenied(permissionName);
            }
            return;
        }

        //6.0以下版本直接返回授权通过
        if (Build.VERSION.SDK_INT < 23) {
            if (checkPermissionListener != null) {
                checkPermissionListener.onPermissionGranted(permissionName);
            }
            return;
        }

        if (ContextCompat.checkSelfPermission(this, permissionName) != PackageManager.PERMISSION_GRANTED) {
            if (checkPermissionListener != null) {
                checkPermissionListener.onPermissionDenied(permissionName);
            }
        } else {
            if (checkPermissionListener != null) {
                checkPermissionListener.onPermissionGranted(permissionName);
            }
        }
    }

    /**
     * 校验(并申请)权限
     *
     * @param permissionName          具体取值参照 Manifest.permission.XXX_XX
     * @param checkPermissionListener 授权结果的回调
     */
    public void checkAndRequestPermission(String permissionName, CheckPermissionListener checkPermissionListener) {
        if (TextUtils.isEmpty(permissionName)) {
            LogUtil.d("权限名称为空，授权检查失败");
            if (checkPermissionListener != null) {
                checkPermissionListener.onPermissionDenied(permissionName);
            }
            return;
        }

        //6.0以下版本直接返回授权通过
        if (Build.VERSION.SDK_INT < 23) {
            if (checkPermissionListener != null) {
                checkPermissionListener.onPermissionGranted(permissionName);
            }
            return;
        }

        //添加回调
        if (permissionCallBackMap.containsKey(permissionName)) {
            permissionCallBackMap.remove(permissionName);
        }
        permissionCallBackMap.put(permissionName, checkPermissionListener);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, permissionName) != PackageManager.PERMISSION_GRANTED) {

            LogUtil.d("授权检查(" + permissionName + ")，尚未授权");
            // 是否需要展示权限申请说明，即为什么要申请此权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                if (permissionExplanationMap.containsKey(permissionName)) {
                    ToastUtil.show(BaseApplication.context, permissionExplanationMap.get(permissionName) + "");
                }
                LogUtil.d("授权检查失败(" + permissionName + ")，给出权限申请说明");
                if (checkPermissionListener != null) {
                    checkPermissionListener.onPermissionDenied(permissionName);
                }
            } else {
                LogUtil.d("授权检查(" + permissionName + ")，发起授权申请");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{permissionName}, REQUEST_CODE_PERMISSIONS_REQUEST);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            LogUtil.d("授权检查(" + permissionName + ")，已授权。");
            if (checkPermissionListener != null) {
                checkPermissionListener.onPermissionGranted(permissionName);
            }
        }
    }

    /**
     * 申请权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        String permission = "";
        if (permissions != null && permissions.length > 0) {
            permission = permissions[0];
        }
        //如果请求的权限为空，则不再继续处理
        if (TextUtils.isEmpty(permission)) {
            return;
        }

        LogUtil.d("授权(" + Arrays.toString(permissions) + ")申请结果(0=GRANTED；-1=DENIED)：" + Arrays.toString(grantResults));
        //获得权限的回调
        CheckPermissionListener checkPermissionListener = permissionCallBackMap.get(permission);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (checkPermissionListener != null) {
                        //已授权
                        checkPermissionListener.onPermissionGranted(permission);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    if (checkPermissionListener != null) {
                        //拒绝
                        checkPermissionListener.onPermissionDenied(permission);
                    }
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public interface CheckPermissionListener {

        /**
         * 授权成功
         *
         * @param permissionName 具体取值参照 Manifest.permission.XXX_XX
         */
        void onPermissionGranted(String permissionName);

        /**
         * 授权失败
         *
         * @param permissionName 具体取值参照 Manifest.permission.XXX_XX
         */
        void onPermissionDenied(String permissionName);

    }

    public class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String msgShow = null;
            switch (msg.what) {
                default:
                    break;
            }
            if (msgShow != null) {
                ToastUtil.show(BaseActivity.this, msgShow);
            }
        }
    }

}
