package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heroan.operation.R;
import com.heroan.operation.interfaces.OnHttpResponseListener;
import com.heroan.operation.manager.OnHttpResponseListenerImpl;
import com.heroan.operation.utils.HttpRequest;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.SettingUtil;
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
        String name = SettingUtil.getSaveValue(SettingUtil.PHONE);
        if (StringUtil.isNotEmpty(name, true)) {
            nameEdittext.setText(name);
            nameEdittext.setSelection(name.length());
        }
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

    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showShortToast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    Intent intent = new Intent(ACTION_EXIT_APP);
                    sendBroadcast(intent);
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }


    private void login(final String name, final String password) {
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(password)) {
            showShortToast(getString(R.string.name_or_pass_no_null));
            return;
        }
        showProgressDialog(getString(R.string.loading));
        HttpRequest.login(name, password, 0,
                new OnHttpResponseListenerImpl(new OnHttpResponseListener() {
            @Override
            public void onHttpSuccess(int requestCode, int resultCode, String resultData) {
                dismissProgressDialog();
                SettingUtil.setSaveValue(SettingUtil.PHONE, name);
                SettingUtil.setSaveValue(SettingUtil.PASSWORD, password);
                SettingUtil.setSaveLongValue(SettingUtil.LOGIN_TIME, System.currentTimeMillis());

                finish();
                startActivity(MainActivity.createIntent(getApplicationContext()));
            }

            @Override
            public void onHttpError(int requestCode, String resultData) {
                dismissProgressDialog();
                showShortToast(resultData);
            }
        }));
    }
}
