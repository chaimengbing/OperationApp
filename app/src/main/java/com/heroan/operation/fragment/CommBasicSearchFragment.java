package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class CommBasicSearchFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate {
    private static final String TAG = CommBasicSearchFragment.class.getSimpleName();
    private TextView resultTextView;

    private String SetAddr;
    private String SetWorkMode;
    private String SoftVer;
    private String SetPacketInterval;
    private String BatteryVolts;
    /**
     * SetAddr d%\r\n      站地址 10位
     * SetWorkMode d%\r\n           工作模式 0-低功耗,1-永在线
     * SoftVer %s-%s\r\n        软件版本
     * SetPacketInterval %d\r\n  通讯间隔
     * BatteryVolts %0.2f\r\n 电池电压
     */


    private ScrollView resultScroll;
    private StringBuffer currentSB = new StringBuffer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_search);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_IMAGE_SUCCESS);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_IMAGE_SUCCESS);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BUNDLE);

        resultTextView = view.findViewById(R.id.result_data_textview);
        resultScroll = view.findViewById(R.id.result_scroll);
    }

    @Override
    public void initData() {
        setData();
    }

    @Override
    public void initEvent() {

    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.NOTIFY_BUNDLE) {
            setData();
        } else if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content)) {
                return;
            }
            readData(result, content);
        }
    }

    private void readData(String result, String content) {


        String data = "";
        if (result.contains(ConfigParams.SetAddr)) {
            currentSB.insert(currentSB.indexOf(SetAddr) + SetAddr.length(),
                    result.replaceAll(ConfigParams.SetAddr, "").trim());
        } else if (result.contains(ConfigParams.SetWorkMode)) {
            data = result.replaceAll(ConfigParams.SetWorkMode, "").trim().equals("1") ?
                    getString(R.string.always_online) : getString(R.string.low_power);
            currentSB.insert(currentSB.indexOf(SetWorkMode) + SetWorkMode.length(), data);
        } else if (result.contains(ConfigParams.SoftVer)) {
            currentSB.insert(currentSB.indexOf(SoftVer) + SoftVer.length(),
                    result.replaceAll(ConfigParams.SoftVer, "").trim());
        } else if (result.contains(ConfigParams.SetPacketInterval)) {
            currentSB.insert(currentSB.indexOf(SetPacketInterval) + SetPacketInterval.length(),
                    ServiceUtils.getCommTime(result.replaceAll(ConfigParams.SetPacketInterval,
                            "").trim()));
        } else if (result.contains(ConfigParams.BatteryVolts)) {
            currentSB.insert(currentSB.indexOf(BatteryVolts) + BatteryVolts.length(),
                    result.replaceAll(ConfigParams.BatteryVolts, "").trim());
        }


        resultTextView.setText(currentSB.toString());
    }


    public void setData() {

        SetAddr = getString(R.string.SetAddr);
        SetWorkMode = getString(R.string.SetWorkMode);
        SoftVer = getString(R.string.SoftVer);
        SetPacketInterval = getString(R.string.SetPacketInterval);
        BatteryVolts = getString(R.string.Battery_voltage);
        currentSB.delete(0, currentSB.length());
        resultScroll.setVisibility(View.VISIBLE);
        ServiceUtils.sendData(ConfigParams.ReadParameter);
        currentSB.append(SetAddr);
        currentSB.append("\n");
        currentSB.append(SetWorkMode);
        currentSB.append("\n");
        currentSB.append(SoftVer);
        currentSB.append("\n");
        currentSB.append(SetPacketInterval);
        currentSB.append("\n");
        currentSB.append(BatteryVolts);
        currentSB.append("\n");

        if (resultTextView != null && currentSB.length() > 0) {
            resultTextView.setText(currentSB.toString());
        }
    }
}
