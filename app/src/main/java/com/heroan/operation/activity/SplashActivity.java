package com.heroan.operation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.heroan.operation.R;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(RegisterActivity.createIntent(SplashActivity.this));
                finish();
            }
        }, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
