package com.xianjiu.www.togithubproject.activity.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.xianjiu.www.togithubproject.R;
import com.xianjiu.www.togithubproject.activity.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $USER_NAME on 2017/7/18 001823:44
 * ${TODO}
 * lumf428@163.com
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.imageview_splash)
    ImageView mImageviewSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);

        // 创建动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        // 设置动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始执行的时候
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束的时候
                 overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 动画重复的时候
            }
        });
        // 开启动画
        mImageviewSplash.startAnimation(animation);

    }
}
