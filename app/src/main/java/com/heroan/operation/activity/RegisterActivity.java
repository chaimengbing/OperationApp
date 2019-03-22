package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heroan.operation.R;
import com.heroan.operation.utils.HttpRequest;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdit, passEdit, cofirmPassEdit, infoDesEdit;
    private Button registerButton;


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
        phoneEdit = findView(R.id.phone_edit);
        passEdit = findView(R.id.pass_edit);
        cofirmPassEdit = findView(R.id.cofirm_pass_edit);
        infoDesEdit = findView(R.id.info_des_edit);
        registerButton = findView(R.id.register);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            register(phoneEdit.getText().toString().trim(), passEdit.getText().toString().trim(),
                    cofirmPassEdit.getText().toString().trim(),
                    infoDesEdit.getText().toString().trim());
        }
    }

    private void register(String phone, String pass, String confirmPass, String infoDes) {
        if (StringUtil.isEmpty(phone)) {
            showShortToast(getString(R.string.Phone_number_empty));
            return;
        }
        if (StringUtil.isEmpty(pass) || StringUtil.isEmpty(confirmPass)) {
            showShortToast(getString(R.string.tip_password_can_not_be_empty));
            return;
        }
        if (!pass.equals(confirmPass)) {
            showShortToast(getString(R.string.Two_input_match));
            return;
        }
        if (StringUtil.isEmpty(infoDes)) {
            showShortToast(getString(R.string.info_des_no_empty));
            return;
        }

        HttpRequest.register(phone, pass, infoDes, 0, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                showShortToast(resultJson);
            }
        });
    }
}
