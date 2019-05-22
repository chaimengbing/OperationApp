package com.heroan.operation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.heroan.operation.R;
import com.heroan.operation.utils.DateUtils;

import java.util.Date;
import java.util.Set;

import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                Date date = new Date(System.currentTimeMillis());
//                if (DateUtils.isAfter(date, "2019-06-15")) {
//                    finish();
//                } else {
                String name = SettingUtil.getSaveValue(SettingUtil.PHONE);
                long loginTime = SettingUtil.getSaveLongValue(SettingUtil.LOGIN_TIME);
                if (loginTime != 0 && StringUtil.isNotEmpty(name, true) && DateUtils.isSameData(loginTime, System.currentTimeMillis())) {
                    startActivity(MainActivity.createIntent(SplashActivity.this));
                } else {
                    startActivity(LoginActivity.createIntent(SplashActivity.this));
                }
                finish();
            }
//            }
        }, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
