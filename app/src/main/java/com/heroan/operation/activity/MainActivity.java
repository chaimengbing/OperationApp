package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.heroan.operation.R;
import com.heroan.operation.utils.BleUtils;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.SettingUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate {


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
    protected void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.CONNCT_OK);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.CONNCT_FAIL);
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.CONNCT_OK);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.CONNCT_FAIL);
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
                toActivity(WebViewActivity.createIntent(context, "运维云", "http://47.104.107.184"));
                break;
            case R.id.main_2:
                connectDevice();
                break;
            case R.id.main_3:
                break;
            case R.id.main_4:
                break;
            case R.id.main_5:
                break;
            case R.id.main_6:
                toActivity(new Intent(getApplicationContext(), SystemSetActivity.class));
                break;
            default:
                break;

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
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    private void connectDevice() {
//        toActivity(new Intent(getApplicationContext(), BasicSettingActivity.class));
        if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_BLE) {
            toActivity(new Intent(getApplicationContext(), BleDeviceListActivity.class));
        } else {
            SocketUtil.getSocketUtil().connectRTU(ConfigParams.IP, ConfigParams.PORT);
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.CONNCT_OK) {
            showShortToast(getString(R.string.Device_connected_successfully));
            toActivity(new Intent(getApplicationContext(), BasicSettingActivity.class));
        } else if (id == UiEventEntry.CONNCT_FAIL) {
            showShortToast(R.string.Device_wifi_settings);
        }
    }
}
