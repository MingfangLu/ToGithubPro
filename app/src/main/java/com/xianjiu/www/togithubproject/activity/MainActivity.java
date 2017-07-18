package com.xianjiu.www.togithubproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.xianjiu.www.togithubproject.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","成功了吗??");
    }
}
