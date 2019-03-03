package com.heroan.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.fragment.BasicQueryFragment;
import com.heroan.operation.fragment.BasicSetFragment;
import com.heroan.operation.utils.UiEventEntry;

import cn.com.heaton.blelibrary.ble.BleDevice;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.EditTextInfoWindow;
import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;

public class BasicSettingActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_TO_EDIT_TEXT_INFO = 20;


    private TextView seniorSetting;
    private TextView leftText, rightText;
    private View leftView, rightView;
    private BleDevice bleDevice;


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
        leftView = findView(R.id.left_divider);
        rightView = findView(R.id.right_divider);
        leftText = findView(R.id.left_text);
        rightText = findView(R.id.right_text);
    }

    @Override
    public void initData() {
        turnToFragment(R.id.basic_set_layout, BasicSetFragment.createInstance());
        leftText.setSelected(true);
        rightText.setSelected(false);

        if (getIntent() != null) {
            bleDevice = (BleDevice) getIntent().getSerializableExtra(UiEventEntry.NOTIFY_BASIC_DEVICE);
        }
    }

    @Override
    public void initEvent() {
        seniorSetting.setOnClickListener(this);
        leftText.setOnClickListener(this);
        rightText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.senior_setting_textview:
                toActivity(EditTextInfoWindow.createIntent(context, EditTextInfoWindow.TYPE_PASSWORD
                        , "输入密码", StringUtil.getTrimedString(getString(R.string.input_password))),
                        REQUEST_TO_EDIT_TEXT_INFO);
                break;
            case R.id.left_text:
                rightView.setVisibility(View.GONE);
                leftView.setVisibility(View.VISIBLE);
                leftText.setSelected(true);
                rightText.setSelected(false);
                turnToFragment(R.id.basic_set_layout, BasicSetFragment.createInstance());
                break;
            case R.id.right_text:
                rightView.setVisibility(View.VISIBLE);
                leftView.setVisibility(View.GONE);
                leftText.setSelected(false);
                rightText.setSelected(true);
                turnToFragment(R.id.basic_set_layout, BasicQueryFragment.createInstance());
                break;
            default:
                break;
        }
    }

    private void turnToFragment(int containerViewId, Fragment toFragmentClass) {
        fragmentManager.beginTransaction().replace(containerViewId, toFragmentClass).commitAllowingStateLoss();
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
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_BLE) {
                        intent.putExtra(UiEventEntry.NOTIFY_BASIC_DEVICE, bleDevice);
                    }
                    toActivity(intent);
                }
                break;
        }
    }
}
