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
import com.heroan.operation.utils.UiEventEntry;

import java.text.SimpleDateFormat;

import zuo.biao.library.base.BaseFragment;

public class BasicSetFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate, RadioGroup.OnCheckedChangeListener {


    private static BasicSetFragment instance;
    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";

    private EditText addressEdit, waterBasicEdit;
    private TextView addressBtn, timeText, timebtn, waterSetBtn, resetBtn, saveButton;
    private RadioGroup netGroup, moniGroup, celiangGroup, rainGroup;
    private Spinner companySpinner;

    private String[] water485Items;
    private SimpleSpinnerAdapter water485Adapter;

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
    }

    @Override
    public void initData() {
        ServiceUtils.getServiceUtils().initContent();

        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);
        water485Items = getResources().getStringArray(R.array.water_select_485);
        water485Adapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, water485Items);
        companySpinner.setAdapter(water485Adapter);

        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                water485Adapter.setSelectedItem(position);
                String water485 = water485Items[position];
                if ("无".equals(water485)) {
                    return;
                }
                String content = ConfigParams.SetWater485Type + ServiceUtils.getStr("" + position, 2);
                sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
