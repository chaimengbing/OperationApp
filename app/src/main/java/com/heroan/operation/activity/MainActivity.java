package com.heroan.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.heroan.operation.R;
import com.heroan.operation.model.RtuItem;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.HttpRequest;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.util.List;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.JSON;
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
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.CONNCT_FAIL);
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.CONNCT_OK);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.CONNCT_FAIL);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initData() {
        HttpRequest.getDevicesList(SettingUtil.getSaveValue(SettingUtil.PHONE), 0, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (!TextUtils.isEmpty(resultJson)) {
                    SettingUtil.setSaveValue(SettingUtil.RTU_LIST, resultJson);
                }
            }
        });
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
                /**
                 * http://rtuyun.cn/
                 * 运维云的跳转链接改成这个地址。http://cloud.zjswxjs.com/
                 */
                toActivity(WebViewActivity.createIntent(context, "运维云", "http://cloud.zjswxjs.com/"));
//                toActivity(WebViewActivity.createIntent(context, "运维云", "http://cloud.zjswxjs.com/"));
                break;
            case R.id.main_2:

                connectDevice();
                break;
            case R.id.main_3:
                toActivity(new Intent(getApplicationContext(), RtuListActivity.class));
                break;
            case R.id.main_4:
                toActivity(new Intent(getApplicationContext(), MessageActivity.class));
                break;
            case R.id.main_5:
                toActivity(new Intent(getApplicationContext(), OperaSignInActivity.class));
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
                    Intent intent = new Intent(ACTION_EXIT_APP);
                    sendBroadcast(intent);
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
//
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.CONNCT_OK) {
            showShortToast("设备连接成功");
            toActivity(new Intent(getApplicationContext(), BasicSettingActivity.class));
            //暂时去掉ID验证
//            ServiceUtils.sendData(ConfigParams.READRTUID);
        } else if (id == UiEventEntry.CONNCT_FAIL) {
            showShortToast(R.string.Device_wifi_settings);
        } else if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            if (result != null) {
                String rtuListString = SettingUtil.getSaveValue(SettingUtil.RTU_LIST);
                if (TextUtils.isEmpty(rtuListString)) {
                    showShortToast("清先获取设备清单");
                    return;
                }
                String rtuId = result.replaceAll(ConfigParams.READRTUID, "").trim();
                List<RtuItem> rtuItems = JSON.parseArray(rtuListString, RtuItem.class);
                boolean isSuccess = false;
                if (rtuItems != null && rtuItems.size() > 0) {
                    for (RtuItem rtuItem : rtuItems) {
                        if (rtuId.equals(rtuItem.getStationId())) {
                            isSuccess = true;
                        }
                    }
                }
                if (isSuccess) {
                    showShortToast("设备连接成功");
                    toActivity(new Intent(getApplicationContext(), BasicSettingActivity.class));
                } else {
                    showShortToast("这不是您的设备");
                }
            } else {
                showShortToast("未获取到设备ID，请更新RTU程序");
            }
        }
    }
}
