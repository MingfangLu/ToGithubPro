package com.xianjiu.www.togithubproject.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianjiu.www.togithubproject.R;
import com.xianjiu.www.togithubproject.activity.activity.BaseActivity;
import com.xianjiu.www.togithubproject.activity.base.BaseApplication;
import com.xianjiu.www.togithubproject.activity.utile.PrefManager;

/**
 * 主要是mainactivity, fragment的基类
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    public  BaseActivity    mActivity;
    public  PrefManager     mPrefManager;
    public  BaseApplication mApp;
    private LinearLayout    layout_container;
    private View            layout_loading;
    private TextView        tv_loading_content;
    protected View layoutView = null;

    public Handler responseHandler;
    protected String            userName;//用户姓名
    protected String            uersTitle;//用户职位
    protected String            mRoleId;//角色（权限）id
    protected int               operatorId;//嘟嘟操作人id
    protected int               saId;//SA的id
    protected String            shopCode;//店铺编码
    protected int               userId;//用户id
    protected String            staffId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVeriables();
    }

    private void initVeriables(){
        mActivity = (BaseActivity) getActivity();
        mApp = (BaseApplication) mActivity.getApplication();
        responseHandler = mActivity.responseHandler;

        mPrefManager = PrefManager.getInstance(mActivity);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            startLoad();
            userVisibleHint();
        }
    }

    /**
     * 多次执行
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutViewAll = inflater.inflate(R.layout.activity_main, null);
        layoutView = inflater.inflate(getLayoutId(), null);
        layout_container.addView(layoutView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        onCreateChildView(savedInstanceState);
        return layoutViewAll;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            startLoad();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        Intent intent = new Intent(mActivity, clazz);
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
        Intent intent = new Intent(mActivity, clazz);
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
        mActivity.overridePendingTransition(R.anim.activity_open_in_from_right, R.anim.activity_open_out_from_right);
    }

    /**
     * 带动画 跳转Activity并且带返回值 动画效果为(只有从右侧进入动画,水平移动进入)
     *
     * @param intent
     */
    public void startActivityForResultWithAnimation_FromRightEnter(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.activity_open_in_from_right, R.anim.activity_open_out_from_right);
    }

    /**
     * 是否为第一次加载
     */
    private boolean isFirstInitLoad = true;

    /**
     * 重置第一次加载状态
     * <p>
     * public void resetInitLoadState() {
     * isFirstInitLoad = true;
     * }
     * <p>
     * /**
     * 开始加载
     */
    private final void startLoad() {
        if (isFirstInitLoad) {
            isFirstInitLoad = false;
            onFirstAccessToLoad();
        }
    }

    /**
     * 只有第一次加载这个界面才会调用该方法,可调用resetInitLoadState()进行重置
     */
    public void onFirstAccessToLoad() {

    }

    /**
     * 只要该界面用户可见，就会进入
     */
    public void userVisibleHint() {

    }

    public void hidnLoadingView() {
        if (layout_loading != null) {
            layout_loading.setVisibility(View.GONE);
        }
        tv_loading_content.setText(null);
    }

    public void showLoadingView() {
        showLoadingView(null);
    }


    public void showLoadingView(String message) {
        if (layout_loading != null) {
            layout_loading.setVisibility(View.VISIBLE);
        }
        tv_loading_content.setText(message);
    }

    public abstract void onCreateChildView(Bundle savedInstanceState);

    public abstract int getLayoutId();

}