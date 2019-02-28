package com.heroan.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.heroan.operation.R;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.EditTextInfoWindow;
import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;

public class BasicSettingActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_TO_EDIT_TEXT_INFO = 20;


    private TextView seniorSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_setting);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        seniorSetting = findView(R.id.senior_setting_textview);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        seniorSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.senior_setting_textview:
                toActivity(EditTextInfoWindow.createIntent(context, EditTextInfoWindow.TYPE_PASSWORD
                        , "输入密码", StringUtil.getTrimedString(R.string.input_password)),
                        REQUEST_TO_EDIT_TEXT_INFO);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_TO_EDIT_TEXT_INFO:
                if (data != null) {
                    if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_BLE) {
                        toActivity(new Intent(getApplicationContext(),BleDeviceListActivity.class));
                    } else {
                        toActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                }
                break;
        }
    }
}
