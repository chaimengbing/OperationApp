package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heroan.operation.R;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.WebViewActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        findView(R.id.main_1).setOnClickListener(this);
        findView(R.id.main_2).setOnClickListener(this);
        findView(R.id.main_3).setOnClickListener(this);
        findView(R.id.main_4).setOnClickListener(this);
        findView(R.id.main_5).setOnClickListener(this);
        findView(R.id.main_6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_1:
                toActivity(WebViewActivity.createIntent(context, "运维云", "http://www.baidu.com"));
                break;
            case R.id.main_2:
                toActivity(new Intent(getApplicationContext(),HomeActivity.class));
                break;
            case R.id.main_3:
                break;
            case R.id.main_4:
                break;
            case R.id.main_5:
                break;
            case R.id.main_6:
                break;
            default:
                break;

        }
    }
}
