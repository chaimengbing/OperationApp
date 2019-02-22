package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.BleUtils;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.text.SimpleDateFormat;

import cn.com.heaton.blelibrary.ble.BleDevice;
import zuo.biao.library.base.BaseFragment;

/**
 * Created by linxi on 2018/5/22.
 */

public class LNewSysPamarsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, EventNotifyHelper.NotificationCenterDelegate {
    private static final String DEFAULT_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final String TAG = LNewSysPamarsFragment.class.getSimpleName();

    private EditText siteTestEditText;
    private Button siteTestSetButton;
    private EditText xingEditText;
    private Button xingSetButton;
    private RadioGroup siteNumRadioGroup;
    private EditText apnEdittext;
    private Button apnButton;
    private Button resetFactoryButton;
    private Button saveResetButton;
    private TextView rtuTimeTextView;
    private Button timeButton;
    private SimpleDateFormat sendTimeFormat;
    private SimpleDateFormat timeFormat;
    private LinearLayout xingLinearLayout;

    private Spinner timeSpinner;
    private String[] timeItems;
    private SimpleSpinnerAdapter timeAdapter;
    //是不是8位站号
    private boolean is8Add = true;

    private String setTime = "";
    private int currentType = -1;

    private RadioGroup noeth1ChannelGroup;

    private EditText ip1;
    private EditText port1;
    private Button set1;

    private boolean isBleDevice = false;
    private BleDevice bleDevice = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_lru_new_syspamars);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.SELECT_TIME);
    }


    @Override
    public void initView() {

        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.SELECT_TIME);

        if (getArguments() != null) {
            isBleDevice = getArguments().getBoolean("isBleDevice");
            bleDevice = (BleDevice) getArguments().getSerializable("device");
        }
        xingLinearLayout = (LinearLayout) view.findViewById(R.id.ll_xingzheng);
        xingLinearLayout.setVisibility(View.GONE);

        timeSpinner = (Spinner) view.findViewById(R.id.time_interval_spinner);
        siteTestSetButton = (Button) view.findViewById(R.id.site_test_setting_button);
        xingSetButton = (Button) view.findViewById(R.id.xingzheng_setting_button);
        siteTestEditText = (EditText) view.findViewById(R.id.site_test_add_number);
        xingEditText = (EditText) view.findViewById(R.id.xingzheng_number);

        noeth1ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_1);

        siteNumRadioGroup = (RadioGroup) view.findViewById(R.id.site_num);

        siteNumRadioGroup.check(R.id.site_num_8);

        apnEdittext = (EditText) view.findViewById(R.id.apn_edittext);
        apnButton = (Button) view.findViewById(R.id.apn_set_button);
        ip1 = (EditText) view.findViewById(R.id.ip_1_edittext);
        port1 = (EditText) view.findViewById(R.id.port_1_edittext);
        set1 = (Button) view.findViewById(R.id.gprs_1_button);

        resetFactoryButton = (Button) view.findViewById(R.id.reset_factory);
        saveResetButton = (Button) view.findViewById(R.id.save_and_reset);
        timeButton = (Button) view.findViewById(R.id.time_button);
//        currentTime = (TextView) view.findViewById(R.id.current_time);
        rtuTimeTextView = (TextView) view.findViewById(R.id.rtu_time);

        initView(view);

        if (bleDevice != null && bleDevice.isConnected()){
            sendData(ConfigParams.ReadSystemPara);
        }
    }

    @Override
    public void initData() {

        timeItems = getResources().getStringArray(R.array.time_interval);
        timeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, timeItems);
        timeSpinner.setAdapter(timeAdapter);

        timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);


    }

    @Override
    public void initEvent() {
        siteTestSetButton.setOnClickListener(this);
        xingSetButton.setOnClickListener(this);
        resetFactoryButton.setOnClickListener(this);
        saveResetButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        rtuTimeTextView.setOnClickListener(this);
        apnButton.setOnClickListener(this);
        set1.setOnClickListener(this);
    }

    private void initView(final View view) {

        noeth1ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.Setconnect_Type1;
                if (checkedId == R.id.tcp_1) {
                    content = content + "0";
                } else if (checkedId == R.id.udp_1) {
                    content = content + "1";
                }
                sendData(content);
            }
        });

        siteNumRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetId_Type;
                switch (i) {
                    case R.id.site_num_8:
                        String low = content + "0";
                        sendData(low);
                        is8Add = true;
                        xingLinearLayout.setVisibility(View.GONE);

                        break;
                    case R.id.site_num_10:
                        String always = content + "1";
                        sendData(always);

                        is8Add = false;
                        xingLinearLayout.setVisibility(View.VISIBLE);


                        break;


                    default:
                        break;
                }
            }
        });
    }


    private void sendData(String content) {
        if (isBleDevice) {
            BleUtils.getInstance().sendData(bleDevice, content.getBytes());
        } else {
            SocketUtil.getSocketUtil().sendContent(content);
        }
    }

    @Override
    public void onClick(View view) {

        String data = "";
        int dataNum = 0;
        String content;
        String ip, port = "";
        switch (view.getId()) {
            case R.id.apn_set_button:
                content = apnEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToastLong(getString(R.string.APN_cannot_be_empty));
                    return;
                }
                content = ConfigParams.SetAPN + content;
                sendData(content);
                break;
            case R.id.gprs_1_button:
                ip = ip1.getText().toString().trim();
                port = port1.getText().toString().trim();

                if (TextUtils.isEmpty(ip)) {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(port)) {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetIP + 1 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
                sendData(content);
                break;
            case R.id.site_test_setting_button:

                String number = siteTestEditText.getText().toString();
                if (TextUtils.isEmpty(number)) {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
                    return;
                }
                if (number.length() > 10) {
                    return;
                }
                String ss = "";
                if (number.length() < 10) {
                    for (int i = 0; i < 10 - number.length(); i++) {
                        ss += "0";
                    }
                }
                if (is8Add) {
                    content = ConfigParams.SetAddr + ss + number;
                } else {
                    content = ConfigParams.SetRTUidyc + ServiceUtils.getStr(number, 5);
                }

                sendData(content);
                break;
            case R.id.xingzheng_setting_button:
                if (is8Add) {
                    return;
                }

                String xing = xingEditText.getText().toString();
                if (TextUtils.isEmpty(xing)) {
                    ToastUtil.showToastLong(getString(R.string.Administrative_division_code_can_not_be_empty));
                    return;
                }
                String co = ConfigParams.SetRTUidxz + ServiceUtils.getStr(xing, 6);

                sendData(co);
                break;

            case R.id.reset_factory:
                sendData(ConfigParams.RESETALL);
                break;
            case R.id.save_and_reset:
                sendData(ConfigParams.RESETUNIT);
                break;
            case R.id.time_button:
                String timeContent = null;
                if (TextUtils.isEmpty(setTime)) {
                    timeContent = ConfigParams.SETTIME + sendTimeFormat.format(System.currentTimeMillis());
                } else {
                    timeContent = ConfigParams.SETTIME + setTime;
                }
                sendData(timeContent);
                break;

            case R.id.rtu_time:
//                ServiceUtils.getServiceUtils().seletDate(getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result)) {
                return;
            }
            setData(result);

//            if (result.contains("SetId_Type")) {
//                if (result.contains("OK SetId_Type")) {//设置站号类型成功
//
//                } else {
//                    is8Add = TRUE;
//                    xingLinearLayout.setVisibility(View.GONE);
//                }
//            }

        } else if (id == UiEventEntry.SELECT_TIME) {
            String result = (String) args[0];
            rtuTimeTextView.setText(result);
            setTime = (String) args[1];
        }
    }

    private void setData(String result) {
        String data = "";
        String[] portArray = null;
        if (result.contains(ConfigParams.SetAddr.trim())) {// 遥测站地址：
            siteTestEditText.setText(result.replaceAll(ConfigParams.SetAddr.trim(), "").trim());
        } else if (result.contains(ConfigParams.Setconnect_Type)) {
            data = result.replaceAll(ConfigParams.Setconnect_Type, "").trim();
            if (TextUtils.isEmpty(data)) {
                return;
            }
            String statu = ServiceUtils.hexString2binaryString(data);

            if (statu.length() > 0) {
                char type = statu.charAt(statu.length() - 1);
                if ('0' == type) {//tcp
                    noeth1ChannelGroup.check(R.id.tcp_1);
                } else if ('1' == type) {
                    noeth1ChannelGroup.check(R.id.udp_1);
                }

            }

        } else if (result.contains(ConfigParams.SetPacketInterval)) {// 定时报间隔
            data = result.replaceAll(ConfigParams.SetPacketInterval.trim(), "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int t = Integer.parseInt(data);
                timeSpinner.setSelection(ServiceUtils.getTimePos(t), false);
                timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        timeAdapter.setSelectedItem(i);

                        String content = ConfigParams.SetPacketInterval + ServiceUtils.getTimeNum(timeItems[i]);
                        sendData(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

        } else if (result.contains(ConfigParams.SetAPN)) {
            data = result.replaceAll(ConfigParams.SetAPN, "").trim();
            apnEdittext.setText(data);
        } else if (result.contains(ConfigParams.SETTIME.trim())) {
            String timeTemp = result.replaceAll(ConfigParams.SETTIME.trim(), "").trim();
            String time = timeTemp.replaceAll(" ", "0").trim();


            String month = time.substring(4, 6);
            int intMonth = Integer.valueOf(month);
            if (intMonth > 12 || intMonth < 1) {

                ToastUtil.showToastLong(getString(R.string.Month_error));
                return;
            }
            int day = Integer.valueOf(time.substring(6, 8));

            if (day > 31 || intMonth < 1) {

                ToastUtil.showToastLong(getString(R.string.Date_error));
                return;
            }

            int hour = Integer.valueOf(time.substring(8, 10));
            int min = Integer.valueOf(time.substring(10, 12));
            int s = Integer.valueOf(time.substring(12, 14));
            if (hour > 24 || hour < 0 || min > 60 || min < 0 || s > 60 || s < 0) {

                ToastUtil.showToastLong(getString(R.string.Time_error1));
                return;
            }

            String rtuTime = ServiceUtils.getTime(time, getActivity());
            if (rtuTimeTextView != null && rtuTime != null) {
                rtuTimeTextView.setText(rtuTime);
                setTime = time;
            }
        } else if (result.contains(ConfigParams.SetIP + "1")) {
            portArray = result.split(ConfigParams.setPort.trim());
            ip1.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 1, "").trim()));
            if (portArray.length > 1) {
                port1.setText(portArray[1].trim());
            }
        } else if (result.contains(ConfigParams.SetId_Type.trim())) {//区分8位站号和十位站号
            String s = result.replaceAll(ConfigParams.SetId_Type.trim(), "").trim();
            if (TextUtils.isEmpty(s)) {
                return;
            }
            if ("0".equals(s)) {//8位站号
                is8Add = true;
                siteNumRadioGroup.check(R.id.site_num_8);
                xingLinearLayout.setVisibility(View.GONE);
            } else if ("1".equals(s)) {//10位站号
                is8Add = false;
                siteNumRadioGroup.check(R.id.site_num_10);
                xingLinearLayout.setVisibility(View.VISIBLE);
            }
        } else if (result.contains(ConfigParams.SetRTUidxz.trim())) {//10位站号行政区划码
//            if (is8Add)
//            {
//                return;
//            }
            xingEditText.setText(result.replaceAll(ConfigParams.SetRTUidxz.trim(), "").trim());
        } else if (result.contains(ConfigParams.SetRTUidyc.trim())) {//10位站号遥测站地址
            if (is8Add) {
                return;
            }
            siteTestEditText.setText(result.replaceAll(ConfigParams.SetRTUidyc.trim(), "").trim());
        }
    }
}
