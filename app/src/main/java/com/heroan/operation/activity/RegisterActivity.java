package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.heroan.operation.R;
import com.heroan.operation.interfaces.OnHttpResponseListener;
import com.heroan.operation.manager.OnHttpResponseListenerImpl;
import com.heroan.operation.utils.HttpRequest;
import com.heroan.operation.utils.IdentifyingCode;

import cn.jpush.android.api.JPushInterface;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdit, passEdit, infoDesEdit, checkCodeEdit;
    private Button registerButton;
    private ImageView checkCodeImage;

    private String realCode = "";


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
        infoDesEdit = findView(R.id.info_des_edit);
        registerButton = findView(R.id.register);
        checkCodeImage = findView(R.id.check_code_image);
        checkCodeEdit = findView(R.id.check_code_edit);

        checkCodeImage.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
        realCode = IdentifyingCode.getInstance().getCode().toLowerCase();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        registerButton.setOnClickListener(this);
        checkCodeImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            register(phoneEdit.getText().toString().trim(), passEdit.getText().toString().trim(),
                    infoDesEdit.getText().toString().trim(), checkCodeEdit.getText().toString());
        } else if (v.getId() == R.id.check_code_image) {
            checkCodeImage.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
            realCode = IdentifyingCode.getInstance().getCode().toLowerCase();
        }
    }

    private void register(final String phone, String pass, String infoDes,
                          String checkCode) {
        if (StringUtil.isEmpty(phone)) {
            showShortToast(getString(R.string.Phone_number_empty));
            return;
        }
        if (StringUtil.isEmpty(pass)) {
            showShortToast(getString(R.string.tip_password_can_not_be_empty));
            return;
        }
        if (StringUtil.isEmpty(infoDes)) {
            showShortToast(getString(R.string.info_des_no_empty));
            return;
        }
        if (StringUtil.isEmpty(checkCode)) {
            showShortToast(getString(R.string.check_code_no_empty));
            return;
        }
        if (!realCode.equals(checkCode.toLowerCase())) {
            showShortToast(getString(R.string.check_code_bad));
            return;
        }
        showProgressDialog(getString(R.string.loading));
        String registerId = JPushInterface.getRegistrationID(context);

        HttpRequest.register(phone, pass, infoDes, registerId, 0,
                new OnHttpResponseListenerImpl(new OnHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(int requestCode, int resultCode, String resultData) {
                        dismissProgressDialog();
                        SettingUtil.setSaveValue(SettingUtil.PHONE, phone);
                        showShortToast("激活成功");
                        startActivity(LoginActivity.createIntent(getApplicationContext()));
                        finish();
                    }

                    @Override
                    public void onHttpError(int requestCode, String resultData) {
                        dismissProgressDialog();
                        showShortToast(resultData);
                    }
                }));
    }
}
