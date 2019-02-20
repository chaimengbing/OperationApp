package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heroan.operation.R;

import zuo.biao.library.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        findView(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login){
            toActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}
