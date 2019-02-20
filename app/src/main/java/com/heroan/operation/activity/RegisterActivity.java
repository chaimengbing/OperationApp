package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heroan.operation.R;

import zuo.biao.library.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    public static Intent createIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        findView(R.id.register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register){
            toActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }
}
