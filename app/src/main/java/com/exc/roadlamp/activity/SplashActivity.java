/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.exc.roadlamp.activity;

import android.view.KeyEvent;

import com.exc.roadlamp.R;
import com.exc.roadlamp.utils.SSLAgent;
import com.exc.roadlamp.utils.TokenUtils;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * 启动页【无需适配屏幕大小】
 */
public class SplashActivity extends BaseSplashActivity {


    /**
     * activity启动后的初始化
     */
    @Override
    protected void onCreateActivity() {
        initSplashView(R.drawable.xui_config_bg_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SSLAgent.getInstance().trustAllHttpsCertificates();
                loginOrGoMainPage();
            }
        }, 2000);

    }


    /**
     * 启动页结束后的动作
     */
    @Override
    protected void onSplashFinished() {
    }

    private void loginOrGoMainPage() {
//        if (TokenUtils.hasToken()) {
////            ActivityUtils.startActivity(MainActivity.class);
////            ActivityUtils.startActivity(MainActivity.class);
//        } else {
        ActivityUtils.startActivity(LoginActivity.class);
//        }
        finish();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }
}
