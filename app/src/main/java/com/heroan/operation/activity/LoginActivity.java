package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heroan.operation.R;
import com.heroan.operation.interfaces.OnHttpResponseListener;
import com.heroan.operation.manager.OnHttpResponseListenerImpl;
import com.heroan.operation.utils.HttpRequest;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.StringUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText nameEdittext;
    private EditText passEdittext;
    private Button loginButton;


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
        nameEdittext = findView(R.id.name_edit);
        passEdittext = findView(R.id.pass_edit);
        loginButton = findView(R.id.login);
    }

    @Override
    public void initData() {
        HttpRequest.translate("library", 0,
                new OnHttpResponseListenerImpl(new OnHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(int requestCode, int resultCode, String resultData) {
                        showShortToast(resultData);
                    }

                    @Override
                    public void onHttpError(int requestCode, Exception e) {

                    }
                }));


    }

    @Override
    public void initEvent() {
        loginButton.setOnClickListener(this);
        findView(R.id.register_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            login(nameEdittext.getText().toString(), passEdittext.getText().toString());
        } else if (v.getId() == R.id.register_text) {
            startActivity(RegisterActivity.createIntent(getApplicationContext()));
        }
    }

    private void login(String name, String password) {
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(password)) {
            showShortToast(getString(R.string.name_or_pass_no_null));
            return;
        }
        HttpRequest.login(name, password, 0,
                new OnHttpResponseListenerImpl(new OnHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(int requestCode, int resultCode, String resultData) {
                        showShortToast(resultData);
                        toActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    @Override
                    public void onHttpError(int requestCode, Exception e) {

                    }
                }));
    }
}
