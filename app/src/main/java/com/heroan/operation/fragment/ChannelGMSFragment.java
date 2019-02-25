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
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * 短信设置
 * Created by Vcontrol on 2017/03/27.
 */

public class ChannelGMSFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {
    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;

    private Button set1;
    private Button set2;
    private Button set3;
    private Button set4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_channel_gms);
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

        num1 = (EditText) view.findViewById(R.id.num_1_edittext);
        num2 = (EditText) view.findViewById(R.id.num_2_edittext);
        num3 = (EditText) view.findViewById(R.id.num_3_edittext);
        num4 = (EditText) view.findViewById(R.id.num_4_edittext);

        set1 = (Button) view.findViewById(R.id.gms_1_button);
        set2 = (Button) view.findViewById(R.id.gms_2_button);
        set3 = (Button) view.findViewById(R.id.gms_3_button);
        set4 = (Button) view.findViewById(R.id.gms_4_button);
    }

    @Override
    public void initData() {
        ServiceUtils.sendData(ConfigParams.ReadCommPara2);
    }

    @Override
    public void initEvent() {
        set1.setOnClickListener(this);
        set2.setOnClickListener(this);
        set3.setOnClickListener(this);
        set4.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String num;
        String content = "";
        switch (view.getId()) {
            case R.id.gms_1_button:
                num = num1.getText().toString().trim();
                if (TextUtils.isEmpty(num)) {
                    ToastUtil.showToastLong(getString(R.string.SMS_number_cannot_be_empty));
                    return;
                }
                if (num.length() != 11) {
                    ToastUtil.showToastLong(getString(R.string.correct_cell_phone_number));
                    return;
                }

                content = ConfigParams.SetSMS + "1" + " " + num;
                break;
            case R.id.gms_2_button:
                num = num2.getText().toString().trim();
                if (TextUtils.isEmpty(num)) {
                    ToastUtil.showToastLong(getString(R.string.SMS_number_cannot_be_empty));
                    return;
                }
                if (num.length() != 11) {
                    ToastUtil.showToastLong(getString(R.string.correct_cell_phone_number));
                    return;
                }

                content = ConfigParams.SetSMS + "2" + " " + num;
                break;
            case R.id.gms_3_button:
                num = num3.getText().toString().trim();
                if (TextUtils.isEmpty(num)) {
                    ToastUtil.showToastLong(getString(R.string.SMS_number_cannot_be_empty));
                    return;
                }
                if (num.length() != 11) {
                    ToastUtil.showToastLong(getString(R.string.correct_cell_phone_number));
                    return;
                }

                content = ConfigParams.SetSMS + "3" + " " + num;
                break;
            case R.id.gms_4_button:
                num = num4.getText().toString().trim();
                if (TextUtils.isEmpty(num)) {
                    ToastUtil.showToastLong(getString(R.string.SMS_number_cannot_be_empty));
                    return;
                }
                if (num.length() != 11) {
                    ToastUtil.showToastLong(getString(R.string.correct_cell_phone_number));
                    return;
                }

                content = ConfigParams.SetSMS + "4" + " " + num;
                break;
        }

        ServiceUtils.sendData(content);
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        String result = (String) args[0];
        String content = (String) args[1];
        if (TextUtils.isEmpty(result)) {
            return;
        }
        setData(result);
    }

    //解析数据

    private void setData(String result) {
        // 短信信道 1 2 3 4
        if (result.contains(ConfigParams.SetSMS + "1")) {
            num1.setText(result.replaceAll(ConfigParams.SetSMS + "1", "").trim());
        } else if (result.contains(ConfigParams.SetSMS + "2")) {
            num2.setText(result.replaceAll(ConfigParams.SetSMS + "2", "").trim());
        } else if (result.contains(ConfigParams.SetSMS + "3")) {
            num3.setText(result.replaceAll(ConfigParams.SetSMS + "3", "").trim());
        } else if (result.contains(ConfigParams.SetSMS + "4")) {
            num4.setText(result.replaceAll(ConfigParams.SetSMS + "4", "").trim());
        }
    }

}
