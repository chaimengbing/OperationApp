package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

/**
 * 其他参数
 * Created by Vcontrol on 2016/11/23.
 */

public class AtherPamarsFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private Button collectTimeButton;
    private Button lvValuesButton;

    private EditText collectTimeEditText;
    private EditText lvValuesEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_sensor_ather);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }


    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        collectTimeButton = view.findViewById(R.id.collect_time_button);
        lvValuesButton = view.findViewById(R.id.lv_values_button);

        collectTimeEditText = view.findViewById(R.id.collect_time_edittext);
        lvValuesEditText = view.findViewById(R.id.lv_values_edittext);
    }

    @Override
    public void initData() {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadSensorPara2);
    }

    @Override
    public void initEvent() {
        collectTimeButton.setOnClickListener(this);
        lvValuesButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String values = "";
        int num = -1;
        switch (view.getId()) {
            case R.id.collect_time_button:
                values = collectTimeEditText.getText().toString().trim();
                if (TextUtils.isEmpty(values)) {
                    ToastUtil.showToastLong(getString(R.string.Collection_waiting_time_empty));
                    return;
                }
                num = Integer.parseInt(values);
                if (num < 0 || num > 300) {
                    ToastUtil.showToastLong(getString(R.string.waiting_time_enter));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetSample_Delay_Time + ServiceUtils.getStr(values, 3));

                break;
            case R.id.lv_values_button:
                values = lvValuesEditText.getText().toString().trim();
                if (TextUtils.isEmpty(values)) {
                    ToastUtil.showToastLong(getString(R.string.low_voltage_alarm_value_null));
                    return;
                }

                double temp = Double.parseDouble(values) * 100;
                int level = (int) temp;
                if (temp < 800 || temp > 2000) {
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetBatteryVoltLow + ServiceUtils.getStr(level + "", 4));
                break;

            default:
                break;
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        String result = (String) args[0];
        String content = (String) args[1];
        if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content)) {
            return;
        }
        setData(result);
    }


    private void setData(String result) {
        String data = "";
        if (result.contains(ConfigParams.SetSample_Delay_Time)) {
            data = result.replaceAll(ConfigParams.SetSample_Delay_Time, "").trim();
            collectTimeEditText.setText(data);
        } else if (result.contains(ConfigParams.SetBatteryVoltLow.trim())) {
            data = result.replaceAll(ConfigParams.SetBatteryVoltLow.trim(), "").trim();
            if (ServiceUtils.isNumeric(data)) {
                double level = Double.parseDouble(data) / 100.0;
                Log.i("AtherPamarsFragment", "level:" + level + ",data:" + data);
                lvValuesEditText.setText(String.valueOf(level));
            }
        }

    }
}
