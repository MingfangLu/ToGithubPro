package com.xianjiu.www.togithubproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.xianjiu.www.togithubproject.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickInsertData(View view) {
        // 测试logger的日志打印

        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("map1","数据一");
        dataMap.put("map2","数据二");
        dataMap.put("map3","数据三");
        dataMap.put("map4","数据四");
        Logger.d(dataMap);
    }
}
