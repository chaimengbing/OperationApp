package com.heroan.operation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

public class BasicQueryFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener {


    private static BasicQueryFragment instance;

    private TextView dianyaText, xinhaoText, netStateText, sendstateText, sendTimeText, wenduText, shuiweiText, currentYuliangText;
    private TextView yuliangSet;
    private EditText yuliangEdit;

    public static BasicQueryFragment createInstance() {
        if (instance == null) {
            instance = new BasicQueryFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_params_query);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
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

        dianyaText = findView(R.id.dianya_text);
        xinhaoText = findView(R.id.xinhao_text);
        netStateText = findView(R.id.net_state_text);
        sendstateText = findView(R.id.send_state_text);
        sendTimeText = findView(R.id.send_time_text);
        wenduText = findView(R.id.wendu_text);
        shuiweiText = findView(R.id.shuiwei_text);
        yuliangEdit = findView(R.id.yuliang_edittext);
        yuliangSet = findView(R.id.yuliang_button);
        currentYuliangText = findView(R.id.current_yuliang_text);
    }

    @Override
    public void initData() {
//        ServiceUtils.sendData(ConfigParams.Readdata);
        ServiceUtils.sendData(ConfigParams.ReadTidydata);
    }

    @Override
    public void initEvent() {
        yuliangSet.setOnClickListener(this);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result)) {
                return;
            }
            setData(result);
        }
    }

    private void setData(String result) {
        if (result.contains(ConfigParams.BatteryVolts.trim())) {
            if (result.contains(ConfigParams.SHIDIANBatteryVolts)) {
            } else {
                dianyaText.setText(result.replaceAll(ConfigParams.BatteryVolts.trim(), "").trim());
            }
        } else if (result.contains(ConfigParams.GPRS_CSQ.trim())) {
            xinhaoText.setText(result.replaceAll(ConfigParams.GPRS_CSQ.trim(), "").trim());
        } else if (result.contains(ConfigParams.GPRS_Status.trim())) {
            String status = ServiceUtils.getGPRSStatus(result.replaceAll(ConfigParams.GPRS_Status, "").trim(), getActivity());
            netStateText.setText(status);
        } else if (result.contains(ConfigParams.Temperature.trim()) && (!result.contains(ConfigParams.Temperature_A)) && (!result.contains(ConfigParams.Temperature_G))) {
            wenduText.setText(result.replaceAll(ConfigParams.Temperature.trim(), "").trim());
        } else if (result.contains(ConfigParams.TotalRainVal.trim())) {
            String rain = result.replaceAll(ConfigParams.TotalRainVal, "").trim();
            yuliangEdit.setText(rain);
        } else if (result.contains(ConfigParams.WaterLevel_R.trim())) {
            shuiweiText.setText(result.replaceAll(ConfigParams.WaterLevel_R.trim(), "").trim());
        } else if (result.contains(ConfigParams.Send_informa_time_tm1.trim())) {
            sendTimeText.setText(result.replaceAll(ConfigParams.Send_informa_time_tm1.trim(), "").trim());
        } else if (result.contains(ConfigParams.SOCKET_STATUS_1.trim())) {
            String status = ServiceUtils.getSocketStatus(result.replaceAll(ConfigParams.SOCKET_STATUS_1, "").trim(), getActivity());
            sendstateText.setText(status);
        } else if (result.contains(ConfigParams.PrecentRainVal)) {
            currentYuliangText.setText(result.replaceAll(ConfigParams.PrecentRainVal, "").trim());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.yuliang_button) {
            String rain = yuliangEdit.getText().toString().trim();
            if (TextUtils.isEmpty(rain)) {
                ToastUtil.showToastLong(getString(R.string.cumulative_rainfall_value_empty));
                return;
            }
            ServiceUtils.sendData(ConfigParams.TotalRainVal + rain);
        }
    }
}
