package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.text.SimpleDateFormat;

import zuo.biao.library.util.Log;

/**
 * 系统参数界面
 * Created by Vcontrol on 2016/11/23.
 */

public class SystemPamarsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, EventNotifyHelper.NotificationCenterDelegate
{

    private static final String DEFAULT_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final String TAG = SystemPamarsFragment.class.getSimpleName();


    //存储间隔
    private EditText saveNumEditText;
    private Button saveNumSetButton;

    private EditText siteTestEditText;
    private Button siteTestSetButton;
    private EditText xingEditText;
    private EditText deviceEditText;
    private Spinner siteTypeSpinner;
    private Spinner workModelSpinner;

    private Button xingSetButton;

    private Button deviceSetButton;
    private SimpleSpinnerAdapter siteTypeAdapter;
    private String[] siteTypeItems;
    private String[] workModelItems;
    private SimpleSpinnerAdapter workModelAdapter;
    private RadioGroup runStatusRadioGroup;
    private RadioGroup siteNumRadioGroup;
    private RadioButton alwaysRadioButton;
    private RadioButton lowRadioButton;

    private RadioGroup valveTypeRadioGroup;
    private RadioButton butterflyRadiobutton;
    private RadioButton pulseRadiobutton;
    private RadioButton vaRadiobutton;

    //    private Runnable mTimeUpdateThread;
    private Button resetFactoryButton;
    private Button saveResetButton;
    //    private TextView currentTime;
    private TextView rtuTimeTextView;
    private Button timeButton;
    private SimpleDateFormat sendTimeFormat;
    private SimpleDateFormat timeFormat;
    private LinearLayout xingLinearLayout;

    private Switch elecrelaySwitch;

    //是不是8位站号
    private boolean is8Add = true;

    private RelativeLayout elecrelayLayout;
    private String setTime = "";
    private int currentType = -1;



    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_setting_system_pamars1;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.SELECT_TIME);
    }

    @Override
    public void initComponentViews(View view)
    {

        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.SELECT_TIME);

        xingLinearLayout = (LinearLayout) view.findViewById(R.id.ll_xingzheng);
        xingLinearLayout.setVisibility(View.GONE);

        siteTestSetButton = (Button) view.findViewById(R.id.site_test_setting_button);
        xingSetButton = (Button) view.findViewById(R.id.xingzheng_setting_button);
        siteTestEditText = (EditText) view.findViewById(R.id.site_test_add_number);
        xingEditText = (EditText) view.findViewById(R.id.xingzheng_number);
        deviceSetButton = (Button) view.findViewById(R.id.device_id_setting_button);
        deviceEditText = (EditText) view.findViewById(R.id.device_id);
        saveNumSetButton = (Button) view.findViewById(R.id.save_setting_button);
        saveNumEditText = (EditText) view.findViewById(R.id.save_number);
        siteTypeSpinner = (Spinner) view.findViewById(R.id.site_type_spinner);
        workModelSpinner = (Spinner) view.findViewById(R.id.work_model_spinner);

        runStatusRadioGroup = (RadioGroup) view.findViewById(R.id.run_status);
        siteNumRadioGroup = (RadioGroup) view.findViewById(R.id.site_num);
        valveTypeRadioGroup = (RadioGroup) view.findViewById(R.id.valve_Type);
        siteNumRadioGroup.check(R.id.site_num_8);
        lowRadioButton = (RadioButton) view.findViewById(R.id.low_power_radiobtton);
        alwaysRadioButton = (RadioButton) view.findViewById(R.id.always_online_radiobtton);
        butterflyRadiobutton = (RadioButton) view.findViewById(R.id.Butterfly_valve_radiobtton);
        pulseRadiobutton = (RadioButton) view.findViewById(R.id.Pulse_solenoid_valveradiobtton);
        vaRadiobutton = (RadioButton) view.findViewById(R.id.valveradiobtton_485);
        resetFactoryButton = (Button) view.findViewById(R.id.reset_factory);
        saveResetButton = (Button) view.findViewById(R.id.save_and_reset);
        timeButton = (Button) view.findViewById(R.id.time_button);
//        currentTime = (TextView) view.findViewById(R.id.current_time);
        rtuTimeTextView = (TextView) view.findViewById(R.id.rtu_time);
        elecrelaySwitch = (Switch) view.findViewById(R.id.elecrelay_switch);
        elecrelayLayout = (RelativeLayout) view.findViewById(R.id.elecrelay_layout);
        initView(view);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentType = bundle.getInt(UiEventEntry.CURRENT_RTU_NAME);
            if (currentType == UiEventEntry.WRU_2801)
            {
                elecrelayLayout.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void initData()
    {
        siteTypeItems = getResources().getStringArray(R.array.site_type);
        siteTypeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, siteTypeItems);
        siteTypeSpinner.setAdapter(siteTypeAdapter);

        workModelItems = getResources().getStringArray(R.array.Work_status);
        workModelAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, workModelItems);
        workModelSpinner.setAdapter(workModelAdapter);
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadSystemPara);

        timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);


//        mTimeUpdateThread = new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    VcontrolApplication.applicationHandler.postDelayed(mTimeUpdateThread, 1000);
////                    currentTime.setText(timeFormat.format(System.currentTimeMillis()));
//                } catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        };
//        VcontrolApplication.applicationHandler.post(mTimeUpdateThread);
    }

    private void initView(final View view)
    {
        runStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetWorkMode;
                switch (i)
                {
                    case R.id.low_power_radiobtton:
                        String low = content + "0";
                        SocketUtil.getSocketUtil().sendContent(low);

                        break;
                    case R.id.always_online_radiobtton:
                        String always = content + "1";
                        SocketUtil.getSocketUtil().sendContent(always);

                        break;

                    default:
                        break;
                }
            }
        });

        valveTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetvalveType;
                switch (i)
                {
                    case R.id.Butterfly_valve_radiobtton:
                        String butterfly = content + "1";
                        SocketUtil.getSocketUtil().sendContent(butterfly);

                        break;
                    case R.id.Pulse_solenoid_valveradiobtton:
                        String pulse = content + "2";
                        SocketUtil.getSocketUtil().sendContent(pulse);

                        break;
                    case R.id.valveradiobtton_485:
                        String va = content + "3";
                        SocketUtil.getSocketUtil().sendContent(va);

                        break;

                    default:
                        break;
                }
            }
        });

        siteNumRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetId_Type;
                switch (i)
                {
                    case R.id.site_num_8:
                        String low = content + "0";
                        SocketUtil.getSocketUtil().sendContent(low);
                        is8Add = true;
                        xingLinearLayout.setVisibility(View.GONE);

                        break;
                    case R.id.site_num_10:
                        String always = content + "1";
                        SocketUtil.getSocketUtil().sendContent(always);

                        is8Add = false;
                        xingLinearLayout.setVisibility(View.VISIBLE);


                        break;


                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void setListener()
    {
        deviceSetButton.setOnClickListener(this);
        siteTestSetButton.setOnClickListener(this);
        xingSetButton.setOnClickListener(this);
        resetFactoryButton.setOnClickListener(this);
        saveResetButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        rtuTimeTextView.setOnClickListener(this);
        saveNumSetButton.setOnClickListener(this);


        elecrelaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (elecrelaySwitch.isPressed())
                {

                    if (!isChecked)
                    {
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.elecrelay + "0");
                    }
                    else
                    {
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.elecrelay + "1");

                    }
                }

            }
        });
    }

    @Override
    public void onClick(View view)
    {

        String data = "";
        int dataNum = 0;
        String content;
        switch (view.getId())
        {
            case R.id.device_id_setting_button:

                String deviceid = deviceEditText.getText().toString();
                if (TextUtils.isEmpty(deviceid))
                {
                    ToastUtil.showToastLong(getString(R.string.Device_ID_cannot_be_empty));
                    return;
                }

                if (deviceid.length() == 14)
                {
                    String content1 = ConfigParams.SetRTUid18 + deviceid;
                    SocketUtil.getSocketUtil().sendContent(content1);
                }
                else
                {
                    ToastUtil.showToastLong(getString(R.string.Device_ID_14));
                }


                break;
            case R.id.site_test_setting_button:

                String number = siteTestEditText.getText().toString();
                if (TextUtils.isEmpty(number))
                {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
                    return;
                }
                if (number.length() > 10)
                {
                    return;
                }
                String ss = "";
                if (number.length() < 10)
                {
                    for (int i = 0; i < 10 - number.length(); i++)
                    {
                        ss += "0";
                    }
                }
                if (is8Add)
                {
                    content = ConfigParams.SetAddr + ss + number;
                }
                else
                {
                    content = ConfigParams.SetRTUidyc + ServiceUtils.getStr(number, 5);
                }


                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.xingzheng_setting_button:
                if (is8Add)
                {
                    return;
                }

                String xing = xingEditText.getText().toString();
                if (TextUtils.isEmpty(xing))
                {
                    ToastUtil.showToastLong(getString(R.string.Administrative_division_code_can_not_be_empty));
                    return;
                }
                String co = ConfigParams.SetRTUidxz + ServiceUtils.getStr(xing, 6);

                SocketUtil.getSocketUtil().sendContent(co);
                break;
            case R.id.save_setting_button:


                data = saveNumEditText.getText().toString();
                if (TextUtils.isEmpty(data))
                {
                    ToastUtil.showToastLong(getString(R.string.Storage_interval_cannot_be_empty));
                    return;
                }
                dataNum = Integer.parseInt(data);
                if (dataNum < 0 || dataNum > 1440)
                {
                    ToastUtil.showToastLong(getString(R.string.Memory_interval_out_of_range));
                    return;
                }
                content = ConfigParams.SetSaveInterval + ServiceUtils.getStr(data, 4);

                SocketUtil.getSocketUtil().sendContent(content);
                break;

            case R.id.reset_factory:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.RESETALL);
                break;
            case R.id.save_and_reset:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.RESETUNIT);
                break;
            case R.id.time_button:
                if (TextUtils.isEmpty(setTime))
                {
                    SocketUtil.getSocketUtil().sendContent(ConfigParams.SETTIME + sendTimeFormat.format(System.currentTimeMillis()));
                }
                else
                {
                    SocketUtil.getSocketUtil().sendContent(ConfigParams.SETTIME + setTime);
                }
                break;

            case R.id.rtu_time:
//                 ServiceUtils.getServiceUtils().seletDate(getActivity());
                break;
//            case R.id.elecrelay_switch:
//                boolean isChecked = true;
//                if (isChecked)
//                {
//                    SocketUtil.getSocketUtil().sendContent(ConfigParams.elecrelay + "1");
//                }
//                else
//                {
//                    SocketUtil.getSocketUtil().sendContent(ConfigParams.elecrelay + "0");
//                }
//                break;

            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        siteTypeAdapter.setSelectedItem(i);

        String content = ConfigParams.SetStationMode + i;
        SocketUtil.getSocketUtil().sendContent(content);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        if (id == UiEventEntry.READ_DATA)
        {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content))
            {
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

        }
        else if (id == UiEventEntry.SELECT_TIME)
        {
            String result = (String) args[0];
            rtuTimeTextView.setText(result);
            setTime = (String) args[1];
        }
    }

    private void setData(String result)
    {
        if (result.contains(ConfigParams.SetAddr.trim()))
        {// 遥测站地址：
            siteTestEditText.setText(result.replaceAll(ConfigParams.SetAddr.trim(), "").trim());
        }
        else if (result.contains(ConfigParams.SetRTUid18.trim()))
        {// 设备ID
            deviceEditText.setText(result.replaceAll(ConfigParams.SetRTUid18.trim(), "").trim());
        }
        else if (result.contains(ConfigParams.SetSaveInterval.trim()))
        {// 设备ID
            saveNumEditText.setText(result.replaceAll(ConfigParams.SetSaveInterval.trim(), "").trim());
        }
        else if (result.contains(ConfigParams.elecrelay.trim()))
        {// 设备ID
            String data = result.replaceAll(ConfigParams.elecrelay.trim(), "").trim();

            if ("0".equals(data))
            {
                elecrelaySwitch.setChecked(false);
            }
            else
            {
                elecrelaySwitch.setChecked(true);
            }
        }
        else if (result.contains(ConfigParams.SetStationMode.trim()))
        {// 站点类型：
            String site = result.replaceAll(ConfigParams.SetStationMode.trim(), "").trim();
            int siteNum = Integer.parseInt(site);
            if (siteNum <= 12)
            {
                siteTypeSpinner.setSelection(siteNum, false);
                siteTypeAdapter.setSelectedItem(siteNum);
            }
            else
            {
                siteTypeSpinner.setSelection(0, false);
                siteTypeAdapter.setSelectedItem(0);

            }
            siteTypeSpinner.setOnItemSelectedListener(this);
        }
        else if (result.contains(ConfigParams.SetWorkModel.trim()))
        {// 工作状态：
            String site = result.replaceAll(ConfigParams.SetWorkModel.trim(), "").trim();
            int siteNum = Integer.parseInt(site);
            workModelSpinner.setSelection(siteNum, false);
            workModelAdapter.setSelectedItem(siteNum);

            workModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    workModelAdapter.setSelectedItem(i);

                    String content = ConfigParams.SetWorkModel + i;
                    SocketUtil.getSocketUtil().sendContent(content);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

                }
            });
        }
        else if (result.contains(ConfigParams.SetWorkMode.trim()))
        {
            String s = result.replaceAll(ConfigParams.SetWorkMode.trim(), "").trim();
            if (TextUtils.isEmpty(s))
            {
                return;
            }
            if (s.equals("0"))
            {
                lowRadioButton.setChecked(true);
            }
            else if (s.equals("1"))
            {
                alwaysRadioButton.setChecked(true);
            }
        }
        else if (result.contains(ConfigParams.SetvalveType.trim()))
        {
            String s = result.replaceAll(ConfigParams.SetvalveType.trim(), "").trim();
            if (TextUtils.isEmpty(s))
            {
                return;
            }
            if (s.equals("1"))
            {
                butterflyRadiobutton.setChecked(true);
            }
            else if (s.equals("2"))
            {
                pulseRadiobutton.setChecked(true);
            }
            else if (s.equals("3"))
            {
                vaRadiobutton.setChecked(true);
            }
        }
        else if (result.contains(ConfigParams.SETTIME.trim()))
        {
            String timeTemp = result.replaceAll(ConfigParams.SETTIME.trim(), "").trim();
            String time = timeTemp.replaceAll(" ", "0").trim();
            Log.i(TAG, "time:" + time);



            String month = time.substring(4,6);
            int intMonth = Integer.valueOf(month);
            if (intMonth > 12 || intMonth <1){

                ToastUtil.showToastLong(getString(R.string.Month_error));
                return;
            }
            int day = Integer.valueOf(time.substring(6,8));

            if (day > 31 || intMonth < 1){

                ToastUtil.showToastLong(getString(R.string.Date_error));
                return;
            }

            int hour = Integer.valueOf(time.substring(8,10));
            int min = Integer.valueOf(time.substring(10,12));
            int s= Integer.valueOf(time.substring(12,14));
            if (hour > 24 || hour < 0 || min > 60 || min < 0 || s > 60 || s < 0 ){

                ToastUtil.showToastLong(getString(R.string.Time_error1));
                return;
            }

            String rtuTime = ServiceUtils.getTime(time,getActivity());
            if (rtuTimeTextView != null && rtuTime != null)
            {
                rtuTimeTextView.setText(rtuTime);
                setTime = time;
            }
        }
        else if (result.contains(ConfigParams.SetId_Type.trim()))
        {//区分8位站号和十位站号
            String s = result.replaceAll(ConfigParams.SetId_Type.trim(), "").trim();
            if (TextUtils.isEmpty(s))
            {
                return;
            }
            if ("0".equals(s))
            {//8位站号
                is8Add = true;
                siteNumRadioGroup.check(R.id.site_num_8);
                xingLinearLayout.setVisibility(View.GONE);
            }
            else if ("1".equals(s))
            {//10位站号
                is8Add = false;
                siteNumRadioGroup.check(R.id.site_num_10);
                xingLinearLayout.setVisibility(View.VISIBLE);
            }
        }
        else if (result.contains(ConfigParams.SetRTUidxz.trim()))
        {//10位站号行政区划码
//            if (is8Add)
//            {
//                return;
//            }
            xingEditText.setText(result.replaceAll(ConfigParams.SetRTUidxz.trim(), "").trim());
        }
        else if (result.contains(ConfigParams.SetRTUidyc.trim()))
        {//10位站号遥测站地址
            if (is8Add)
            {
                return;
            }
            siteTestEditText.setText(result.replaceAll(ConfigParams.SetRTUidyc.trim(), "").trim());
        }
    }
}
