package com.heroan.operation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.text.SimpleDateFormat;

import zuo.biao.library.base.BaseFragment;

public class BasicSetFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate, RadioGroup.OnCheckedChangeListener {


    private static BasicSetFragment instance;
    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";

    private EditText addressEdit, waterBasicEdit, waitTimeRdit;
    private TextView addressBtn, timeText, timebtn, waterSetBtn, resetBtn, saveButton, waitTimeButton;
    private RadioGroup netGroup, moniGroup, celiangGroup, rainGroup;
    private Spinner companySpinner, flow485Spinner;

    private String[] water485Items;
    private SimpleSpinnerAdapter water485Adapter;

    private String[] newFlowItems;
    private SimpleSpinnerAdapter newFlowAdapter;

    private SimpleDateFormat sendTimeFormat;
    private String setTime = "";


    public static BasicSetFragment createInstance() {
        if (instance == null) {
            instance = new BasicSetFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_basic_setting);
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
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.SELECT_TIME);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.SELECT_TIME);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        addressEdit = findView(R.id.address_edittext);
        addressBtn = findView(R.id.add_setting_button);
        timeText = findView(R.id.time_text);
        timebtn = findView(R.id.time_button);
        netGroup = findView(R.id.net_group);
        celiangGroup = findView(R.id.celiang_group);
        rainGroup = findView(R.id.rain_group);
        waterBasicEdit = findView(R.id.water_basic_edittext);
        waterSetBtn = findView(R.id.water_basic_button);
        companySpinner = findView(R.id.company_485_spinner);
        moniGroup = findView(R.id.moni_group);
        resetBtn = findView(R.id.reset_factory);
        saveButton = findView(R.id.save_and_reset);
        waitTimeButton = findView(R.id.wait_time_button);
        waitTimeRdit = findView(R.id.wait_time_edittext);
        flow485Spinner = findView(R.id.flow_485_spinner);
    }

    @Override
    public void initData() {
        ServiceUtils.getServiceUtils().initContent();

        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);
        water485Items = getResources().getStringArray(R.array.water_select_485);
        water485Adapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, water485Items);
        companySpinner.setAdapter(water485Adapter);

        newFlowItems = getResources().getStringArray(R.array.flow_485_new);
        newFlowAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, newFlowItems);
        flow485Spinner.setAdapter(newFlowAdapter);

        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                water485Adapter.setSelectedItem(position);
                String water485 = water485Items[position];
                if ("无".equals(water485)) {
                    return;
                }
                String content = ConfigParams.SetWater485Type + ServiceUtils.getStr("" + (position - 1), 2);
                sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        ServiceUtils.sendData(ConfigParams.ReadSystemPara);
        ServiceUtils.sendData(ConfigParams.ReadTidySystemPara);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void initEvent() {
        addressBtn.setOnClickListener(this);
        timebtn.setOnClickListener(this);
        waterSetBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        timeText.setOnClickListener(this);

        netGroup.setOnCheckedChangeListener(this);
        moniGroup.setOnCheckedChangeListener(this);
        celiangGroup.setOnCheckedChangeListener(this);
        rainGroup.setOnCheckedChangeListener(this);
        waitTimeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wait_time_button:
                String values = waitTimeRdit.getText().toString().trim();
                if (TextUtils.isEmpty(values)) {
                    ToastUtil.showToastLong(getString(R.string.Collection_waiting_time_empty));
                    return;
                }
                int num = Integer.parseInt(values);
                if (num < 0 || num > 300) {
                    ToastUtil.showToastLong(getString(R.string.waiting_time_enter));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.SetSample_Delay_Time + ServiceUtils.getStr(values, 3));
                break;
            case R.id.add_setting_button:
                addressSet();
                break;
            case R.id.time_button:
                if (TextUtils.isEmpty(setTime)) {
                    ServiceUtils.sendData(ConfigParams.SETTIME + sendTimeFormat.format(System.currentTimeMillis()));
                } else {
                    ServiceUtils.sendData(ConfigParams.SETTIME + setTime);
                }
                break;
            case R.id.time_text:
                ServiceUtils.getServiceUtils().seletDate(getActivity());
                break;
            case R.id.water_basic_button:
                waterBasicSet();
                break;
            case R.id.reset_factory:
                ServiceUtils.sendData(ConfigParams.RESETALL);
                break;
            case R.id.save_and_reset:
                ServiceUtils.sendData(ConfigParams.RESETUNIT);
                break;
            default:
                break;
        }
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.SELECT_TIME) {
            String result = (String) args[0];
            timeText.setText(result);
            setTime = (String) args[1];
        } else if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result)) {
                return;
            }
            setData(result);
        }
    }

    private void setData(String result) {

        if (result.contains(ConfigParams.SetAddr.trim())) {// 遥测站地址：
            addressEdit.setText(result.replaceAll(ConfigParams.SetAddr.trim(), "").trim());
        } else if (result.contains(ConfigParams.SetFlow485Type)) {
            String data = result.replaceAll(ConfigParams.SetFlow485Type, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int i = Integer.parseInt(data);

                flow485Spinner.setSelection(i - 1, false);

                flow485Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        newFlowAdapter.setSelectedItem(position);
                        String content = ConfigParams.SetFlow485Type + ServiceUtils.getStr(position + 1 + "", 2);
                        ServiceUtils.sendData(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } else if (result.contains(ConfigParams.SetSample_Delay_Time)) {
            String data = result.replaceAll(ConfigParams.SetSample_Delay_Time, "").trim();
            waitTimeRdit.setText(data);
        } else if (result.contains(ConfigParams.SetAnaWaterBac.trim())) {
            String data = result.replaceAll(ConfigParams.SetAnaWaterBac.trim(), "").trim();
            if (ServiceUtils.isNumeric(data)) {
                double level = Double.parseDouble(data) / 1000.0;
                String temp = String.valueOf(level);
                waterBasicEdit.setText(temp);
            }
        } else if (result.contains(ConfigParams.SetAnaWaterSignal.trim())) {// 设备ID
            String data = result.replaceAll(ConfigParams.SetAnaWaterSignal.trim(), "").trim();
            if ("1".equals(data)) {
                moniGroup.check(R.id.zheng_button);
            } else {
                moniGroup.check(R.id.fan_button);
            }
        } else if (result.contains(ConfigParams.SetAutoGSMMode.trim())) {
            String data = result.replaceAll(ConfigParams.SetAutoGSMMode.trim(), "").trim();
            if ("0".equals(data)) {
                netGroup.check(R.id.auto_button);
            } else {
                netGroup.check(R.id.g2_button);
            }
        } else if (result.contains(ConfigParams.SetWater_caiji_Type.trim())) {
            String data = result.replaceAll(ConfigParams.SetWater_caiji_Type.trim(), "").trim();
            if ("1".equals(data)) {
                celiangGroup.check(R.id.shuishen_button);
            } else {
                celiangGroup.check(R.id.konggao_button);
            }
        } else if (result.contains(ConfigParams.SetRainMeterPara.trim())) {
            String data = result.replaceAll(ConfigParams.SetRainMeterPara.trim(), "").trim();
            if ("0".equals(data)) {
                rainGroup.check(R.id.rain_1);
            } else if ("1".equals(data)) {
                rainGroup.check(R.id.rain_2);
            } else if ("2".equals(data)) {
                rainGroup.check(R.id.rain_3);
            } else {
                rainGroup.check(R.id.rain_4);
            }
        } else if (result.contains(ConfigParams.SetWater485Type)) {
            String data = result.replaceAll(ConfigParams.SetWater485Type, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int t = Integer.parseInt(data);
                try {
                    if (t < water485Items.length) {
                        companySpinner.setSelection(t + 1, false);
                    }
                } catch (Exception e) {

                }

            }
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
            timeText.setText(rtuTime);
            setTime = time;
        }
    }


    private void addressSet() {
        String number = addressEdit.getText().toString();
        if (TextUtils.isEmpty(number)) {
            showShortToast(getString(R.string.Address_cannot_be_empty));
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
        String content = ConfigParams.SetAddr + ss + number;
        sendContent(content);
    }

    private void waterBasicSet() {
        String water = waterBasicEdit.getText().toString();
        if (TextUtils.isEmpty(water)) {
            showShortToast(getString(R.string.base_value_of_the_water_level));
            return;
        }
        double temp = Double.parseDouble(water);
        String tt = String.valueOf(temp * 1000);

        water = tt.substring(0, tt.indexOf("."));
        String content = ConfigParams.SetAnaWaterBac + ServiceUtils.getStr(water, 7);
        sendContent(content);
    }

    private void sendContent(String content) {
        ServiceUtils.sendData(content);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        View checkView = view.findViewById(checkedId);
        if (!checkView.isPressed()) {
            return;
        }
        switch (group.getId()) {
            case R.id.net_group:
                GSMModeSet(checkedId);
                break;
            case R.id.moni_group:
                moniSet(checkedId);
                break;
            case R.id.celiang_group:
                celiangSet(checkedId);
                break;
            case R.id.rain_group:
                rainSet(checkedId);
                break;
            default:
                break;
        }
    }

    private void moniSet(int checkedId) {
        String content = ConfigParams.SetAnaWaterSignal;
        if (checkedId == R.id.zheng_button) {
            content = content + "1";
        } else if (checkedId == R.id.fan_button) {
            content = content + "2";
        }
        sendContent(content);
    }

    private void GSMModeSet(int checkedId) {
        String content = ConfigParams.SetAutoGSMMode;
        if (checkedId == R.id.auto_button) {
            content = content + "0";
        } else if (checkedId == R.id.g2_button) {
            content = content + "1";
        }
        sendContent(content);
    }

    private void celiangSet(int checkedId) {
        String content = ConfigParams.SetWater_caiji_Type;
        if (checkedId == R.id.shuishen_button) {
            content = content + "1";
        } else if (checkedId == R.id.konggao_button) {
            content = content + "2";
        }
        sendContent(content);
    }

    private void rainSet(int checkedId) {
        String content = ConfigParams.SetRainMeterPara;
        if (checkedId == R.id.rain_1) {
            content = content + "0";
        } else if (checkedId == R.id.rain_1) {
            content = content + "1";
        } else if (checkedId == R.id.rain_3) {
            content = content + "2";
        } else if (checkedId == R.id.rain_4) {
            content = content + "3";
        }
        sendContent(content);
    }
}
