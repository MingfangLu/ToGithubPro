package com.xianjiu.www.togithubproject.activity.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import com.xianjiu.www.togithubproject.R;


/**
 * 登录界面
 */
public class ActivityLogin extends BaseActivity {


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    @Override
    public int getLayoutId() {
        return R.layout.aitivity_login;
    }

    /**
     * 头标题
     *
     * @return
     */
    @Override
    public String getTitleText() {
        return "登录";
    }


    /**
     * 隐藏ActionBar
     *
     * @return
     */
    @Override
    public boolean isActionBarVisible() {
        return false;
    }

    /**
     * 拍照权限给予
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}