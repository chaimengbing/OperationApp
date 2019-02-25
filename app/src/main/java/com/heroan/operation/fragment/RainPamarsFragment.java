package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * 雨量参数
 * Created by Vcontrol on 2016/11/23.
 */

public class RainPamarsFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private RadioGroup rainGroup;
    private RadioGroup rainType;
    private EditText valuesEdittext;
    private Button rainsetButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_sensor_rain);
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


    private void initView(final View view) {
        rainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetRainMeterPara;
                if (checkedId == R.id.rain_button) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.rain_button_2) {
                    ServiceUtils.sendData(content + "1");
                } else if (checkedId == R.id.rain_button_3) {
                    ServiceUtils.sendData(content + "2");
                } else if (checkedId == R.id.rain_button_4) {
                    ServiceUtils.sendData(content + "3");
                }
            }
        });
        rainType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetRainType;
                if (checkedId == R.id.rain_type_button) {
                    ServiceUtils.sendData(content + "1");
                } else if (checkedId == R.id.rain_type_button_2) {
                    ServiceUtils.sendData(content + "2");
                } else if (checkedId == R.id.rain_type_button_3) {
                    ServiceUtils.sendData(content + "3");
                }
            }
        });
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        rainGroup = (RadioGroup) view.findViewById(R.id.rain_group);
        rainType = (RadioGroup) view.findViewById(R.id.rain_type);
        valuesEdittext = (EditText) view.findViewById(R.id.values_edittext);
        rainsetButton = (Button) view.findViewById(R.id.rainset_button);

        initView(view);
    }

    @Override
    public void initData() {
        ServiceUtils.sendData(ConfigParams.ReadSensorPara1);
    }

    @Override
    public void initEvent() {
        rainsetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rainset_button:
                String s = valuesEdittext.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtil.showToastLong(getString(R.string.rainfall_threshold_cannot_be_null));
                    return;
                }

                int number = Integer.parseInt(s);
                if (number < 0 || number > 99) {
                    return;
                }
                ServiceUtils.sendData(ConfigParams.SetRainFlowChangeMax + ServiceUtils.getStr(number + "", 2));

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
        if (result.contains(ConfigParams.SetRainMeterPara)) {
            data = result.replaceAll(ConfigParams.SetRainMeterPara, "").trim();
            if ("0".equals(data)) {
                rainGroup.check(R.id.rain_button);
            } else if ("1".equals(data)) {
                rainGroup.check(R.id.rain_button_2);
            } else if ("2".equals(data)) {
                rainGroup.check(R.id.rain_button_3);
            } else if ("3".equals(data)) {
                rainGroup.check(R.id.rain_button_4);
            }
        } else if (result.contains(ConfigParams.SetRainFlowChangeMax)) {
            data = result.replaceAll(ConfigParams.SetRainFlowChangeMax, "").trim();
            valuesEdittext.setText(data);
        } else if (result.contains(ConfigParams.SetRainType)) {
            data = result.replaceAll(ConfigParams.SetRainType, "").trim();
            if ("1".equals(data)) {
                rainType.check(R.id.rain_type_button);
            } else if ("2".equals(data)) {
                rainType.check(R.id.rain_type_button_2);
            } else if ("3".equals(data)) {
                rainType.check(R.id.rain_type_button_3);
            }
        }
    }

}
