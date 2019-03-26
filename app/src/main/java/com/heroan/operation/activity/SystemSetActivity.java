package com.heroan.operation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heroan.operation.R;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.ItemDialog;
import zuo.biao.library.util.SettingUtil;

public class SystemSetActivity extends BaseActivity implements View.OnClickListener, ItemDialog.OnDialogItemClickListener {

    private static final String[] MODE_NAMES = {"WIFI", "蓝牙"};
    private static final int SET_MODE_SET_TOPBAR = 100;

    private TextView title, titleRight, setModeText;
    private ImageView backImageView;
    private Button exitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_set);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        title = findView(R.id.title);
        titleRight = findView(R.id.title_right);
        titleRight.setVisibility(View.GONE);
        backImageView = findView(R.id.title_back);
        setModeText = findView(R.id.set_mode_text);
        exitLogin = findView(R.id.exit_login);
        title.setText(getString(R.string.system_setting));
    }

    @Override
    public void initData() {
        setMode();
    }

    private void setMode() {
        if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_WIFI) {
            setModeText.setText("WIFI");
        } else {
            setModeText.setText("蓝牙");
        }
    }

    @Override
    public void initEvent() {
        backImageView.setOnClickListener(this);
        exitLogin.setOnClickListener(this);
        findView(R.id.ll_set_mode).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.ll_set_mode:
                new ItemDialog(context, MODE_NAMES, "选择配置方式", SET_MODE_SET_TOPBAR, this).show();
                break;
            case R.id.exit_login:
                new AlertDialog(context, "提示", "是否退出登录", true, 0, new AlertDialog.OnDialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        if (isPositive) {
                            SettingUtil.setSaveValue(SettingUtil.PHONE, "");
                            SettingUtil.setSaveValue(SettingUtil.PASSWORD, "");
                            SettingUtil.setSaveLongValue(SettingUtil.LOGIN_TIME, 0);
                            startActivity(LoginActivity.createIntent(getApplicationContext()));
                        }
                    }
                }).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDialogItemClick(int requestCode, int position, String item) {
        switch (requestCode) {
            case SET_MODE_SET_TOPBAR:
                if (position == 0) {
                    SettingUtil.setSetMode(SettingUtil.KEY_SET_MODE_WIFI);
                } else {
                    SettingUtil.setSetMode(SettingUtil.KEY_SET_MODE_BLE);
                }
                setMode();
                break;
        }
    }
}
