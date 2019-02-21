package com.heroan.operation.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class GroundWaterBasicFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate, AdapterView.OnItemSelectedListener
{

    private static final String DEFAULT_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final String TAG = GroundWaterBasicFragment.class.getSimpleName();
    private EditText siteTestEditText;
    private EditText depthEditText;

    private EditText gateMeterEditText;

    private Spinner sensorTypeSpinner;
    private Spinner timeSpinner;
    private Button siteTestSetButton;
    private Button depthSetButton;

    private Button collectionButton;
    private CheckBox checkBox1;
    private CheckBox checkBox2;



    private Button gateMeterSetButton;

    private SimpleSpinnerAdapter sensorTypeAdapter;
    private SimpleSpinnerAdapter timeTypeAdapter;
    private String[] sensorTypeItems;
    private String[] timeItems;
    private RadioGroup runStatusRadioGroup;
    private RadioGroup outputRadioGroup;
    private RadioGroup humRadioGroup;
    private RadioButton alwaysRadioButton;
    private RadioButton lowRadioButton;

    private TextView currentTime;
    private TextView rtuTimeTextView;
    private Runnable mTimeUpdateThread;
    private Button timeButton;
    private Button resetButton;
    private Button sam_gate_now_Button;
    private Button restartButton;
    private SimpleDateFormat sendTimeFormat;
    private SimpleDateFormat timeFormat;
    private Switch elecrelaySwitch;
    private RelativeLayout elecrelayLayout;

    private EditText apnEdittext;
    private Button apnButton;

    public static String[] yearContent = null;
    public static String[] monthContent = null;
    public static String[] dayContent = null;
    public static String[] hourContent = null;
    public static String[] minuteContent = null;
    public static String[] secondContent = null;

    private String setTime = "";

    private Spinner siteTypeSpinner;
    private SimpleSpinnerAdapter siteTypeAdapter;
    private String[] siteTypeItems;


    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_setting_system_pamars;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initComponentViews(View view)
    {

        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        siteTestSetButton = (Button) view.findViewById(R.id.site_test_setting_button);

        gateMeterSetButton = (Button) view.findViewById(R.id.gate_meter_setting_button);
        gateMeterEditText = (EditText) view.findViewById(R.id.gate_meter_add_number);

        depthSetButton = (Button) view.findViewById(R.id.ground_setting_button);
        siteTestEditText = (EditText) view.findViewById(R.id.site_test_add_number);
        depthEditText = (EditText) view.findViewById(R.id.ground_setting_edittext);
        sensorTypeSpinner = (Spinner) view.findViewById(R.id.sensor_type_spinner);
        timeSpinner = (Spinner) view.findViewById(R.id.time_interval_spinner);

        runStatusRadioGroup = (RadioGroup) view.findViewById(R.id.run_status);
        outputRadioGroup = (RadioGroup) view.findViewById(R.id.output_status);
        humRadioGroup = (RadioGroup) view.findViewById(R.id.hum_status);
        lowRadioButton = (RadioButton) view.findViewById(R.id.low_power_radiobtton);
        alwaysRadioButton = (RadioButton) view.findViewById(R.id.always_online_radiobtton);
        timeButton = (Button) view.findViewById(R.id.time_button);
        resetButton = (Button) view.findViewById(R.id.reset_button);
        sam_gate_now_Button= (Button) view.findViewById(R.id.sam_gate_now_button);
        restartButton = (Button) view.findViewById(R.id.restart_button);
        currentTime = (TextView) view.findViewById(R.id.current_time);
        rtuTimeTextView = (TextView) view.findViewById(R.id.rtu_time);

        /*collectionButton = (Button) view.findViewById(R.id.collection_button);
        checkBox1 = (CheckBox) view.findViewById(R.id.ground_checkbox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.ground_checkbox2);*/

        elecrelaySwitch = (Switch) view.findViewById(R.id.elecrelay_switch);
        elecrelayLayout = (RelativeLayout) view.findViewById(R.id.elecrelay_layout);

        apnEdittext = (EditText) view.findViewById(R.id.apn_edittext);
        apnButton = (Button) view.findViewById(R.id.apn_set_button);

        siteTypeSpinner = (Spinner) view.findViewById(R.id.site_type_spinner);
        initView(view);


    }

    @Override
    public void initData()
    {
        initContent();
        sensorTypeItems = getResources().getStringArray(R.array.sensor_type);
        timeItems = getResources().getStringArray(R.array.ground_collect_time);
        sensorTypeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, sensorTypeItems);
        timeTypeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, timeItems);
        sensorTypeSpinner.setAdapter(sensorTypeAdapter);
        timeSpinner.setAdapter(timeTypeAdapter);

        siteTypeItems = getResources().getStringArray(R.array.site_type);
        siteTypeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, siteTypeItems);
        siteTypeSpinner.setAdapter(siteTypeAdapter);


        timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);
        mTimeUpdateThread = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    OperationApplication.applicationHandler.postDelayed(mTimeUpdateThread, 1000);
                    currentTime.setText(timeFormat.format(System.currentTimeMillis()));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        OperationApplication.applicationHandler.post(mTimeUpdateThread);

        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadData);
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

        humRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetXIUZHENG;
                switch (i)
                {
                    case R.id.hum_update_radiobtton:
                        String low = content + "1";
                        SocketUtil.getSocketUtil().sendContent(low);

                        break;
                    case R.id.hum_noupdate_radiobtton:
                        String always = content + "2";
                        SocketUtil.getSocketUtil().sendContent(always);

                        break;

                    default:
                        break;
                }
            }
        });

        outputRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetOutPut;
                switch (i)
                {
                    case R.id.send_radiobtton:
                        String low = content + "1";
                        SocketUtil.getSocketUtil().sendContent(low);

                        break;
                    case R.id.no_send_radiobtton:
                        String always = content + "0";
                        SocketUtil.getSocketUtil().sendContent(always);

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
        siteTestSetButton.setOnClickListener(this);
        depthSetButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        sam_gate_now_Button.setOnClickListener(this);
        apnButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        /*collectionButton.setOnClickListener(this);*/
        rtuTimeTextView.setOnClickListener(this);
        gateMeterSetButton.setOnClickListener(this);
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
        switch (view.getId())
        {
            case R.id.site_test_setting_button:
                String number = siteTestEditText.getText().toString();
                if (TextUtils.isEmpty(number))
                {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
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
                // siteTestEditText.setText(ss + number);
                String content = ConfigParams.SetAddr + ss + number;

                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.gate_meter_setting_button:
                String number1 = gateMeterEditText.getText().toString();
                if (TextUtils.isEmpty(number1))
                {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
                    return;
                }
                String ss1 = "";
                //if (number1.length() < 5)
                //{
                    //for (int i = 0; i < 5 - number1.length(); i++)
                    //{
                     //   ss1 += "0";
                   // }
               // }

                String content2 = ConfigParams.Setlora_gate_addr + ss1 + number1;

                SocketUtil.getSocketUtil().sendContent(content2);
                break;
            case R.id.apn_set_button:
                String centerAdd = "";
                centerAdd = apnEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.APN_cannot_be_empty));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAPN + centerAdd);

                break;
            case R.id.ground_setting_button:
                String ground = depthEditText.getText().toString();
                if (TextUtils.isEmpty(ground))
                {
                    ToastUtil.showToastLong(getString(R.string.Groundwater_depth));
                    return;
                }

                if (ground.length() > 5)
                {
                    ToastUtil.showToastLong(getString(R.string.Buried_depth_5));
                    return;
                }
                // siteTestEditText.setText(ss + number);
                String content1 = ConfigParams.SetDep + ground;

                SocketUtil.getSocketUtil().sendContent(content1);
                break;


            /*case R.id.collection_button:
                String check1 = checkBox1 != null && checkBox1.isChecked() ? "1" : "0";
                String check2 = checkBox2 != null && checkBox2.isChecked() ? "1" : "0";
                String content3 = ConfigParams.SetScadaFactor + "0"+check2+"0" + check1;
                SocketUtil.getSocketUtil().sendContent(content3);
                break;*/


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
            case R.id.reset_button:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.ResetUnit);
                break;
            case R.id.sam_gate_now_button:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.Sam_Gate_now);
                break;
            case R.id.restart_button:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.RESETALL);
                break;
            case R.id.rtu_time:
//                seletDate();
                break;

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
    public void onNothingSelected(AdapterView<?> parent)
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



        }

    }

    private void setData(String result)
    {
        if (result.contains(ConfigParams.GROUND_RTUid.trim()))
        {// 遥测站地址：
            siteTestEditText.setText(result.replaceAll(ConfigParams.GROUND_RTUid.trim(), "").trim());
            siteTestEditText.setSelection(siteTestEditText.getText().toString().trim().length());
        }

        if (result.contains(ConfigParams.Setlora_gate_addr.trim()))
        {// 闸位计地址：
            gateMeterEditText.setText(result.replaceAll(ConfigParams.Setlora_gate_addr.trim(), "").trim());
            gateMeterEditText.setSelection(gateMeterEditText.getText().toString().trim().length());
        }

        if (result.contains(ConfigParams.GROUND_maishen.trim()))
        {// 埋深地址：
            depthEditText.setText(result.replaceAll(ConfigParams.GROUND_maishen.trim(), "").trim());
            depthEditText.setSelection(depthEditText.getText().toString().trim().length());
        }
        else if (result.contains(ConfigParams.SetAPN.trim()))
        {
            apnEdittext.setText(result.replaceAll(ConfigParams.SetAPN.trim(), "").trim());
            apnEdittext.setSelection(apnEdittext.getText().toString().trim().length());
        }
        else if (result.contains(ConfigParams.SetStationMode.trim()))
        {// 站点类型：
            String site = result.replaceAll(ConfigParams.SetStationMode.trim(), "").trim();
            int siteNum = Integer.parseInt(site);
            if (siteNum <= 9)
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
        else if (result.contains(ConfigParams.GROUND_Water485Type.trim()))
        {// 传感器类型：
            String site = result.replaceAll(ConfigParams.GROUND_Water485Type.trim(), "").trim();
            int siteNum = Integer.parseInt(site);
            sensorTypeSpinner.setSelection(siteNum, false);
            sensorTypeAdapter.setSelectedItem(siteNum);

            sensorTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    sensorTypeAdapter.setSelectedItem(i);

                    String content = ConfigParams.SetWater485Type + i;
                    SocketUtil.getSocketUtil().sendContent(content);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

                }
            });
        }
        else if (result.contains(ConfigParams.GROUND_SetPacketInterval.trim()))
        {// 采集时间间隔：
            String site = result.replaceAll(ConfigParams.GROUND_SetPacketInterval.trim(), "").trim();
            int siteNum = Integer.parseInt(site);
            if (siteNum <= timeItems.length){
                timeSpinner.setSelection(siteNum, false);
                timeTypeAdapter.setSelectedItem(siteNum);
            }
            else {
                ToastUtil.showToastLong(getString(R.string.Groundwater_value));
            }


            timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    timeTypeAdapter.setSelectedItem(i);

                    String content = ConfigParams.SetPacketInterval + i;
                    SocketUtil.getSocketUtil().sendContent(content);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

                }
            });
        }


        /*else if (result.contains(ConfigParams.SetScadaFactor.trim()) && (!result.equals("OK")))
        {// 获取采集要素设置
            String collect = result.replaceAll(ConfigParams.SetScadaFactor.trim(), "").trim();
            checkBox1.setChecked((collect.charAt(3)) == '1');
            checkBox2.setChecked((collect.charAt(1)) == '1');

        }*/

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
        else if (result.contains(ConfigParams.GROUND_RunMode.trim()))
        {
            String s = result.replaceAll(ConfigParams.GROUND_RunMode.trim(), "").trim();
            if (TextUtils.isEmpty(s))
            {
                return;
            }
            if (s.equals("0"))
            {
                lowRadioButton.setChecked(true);
            }
            else
            {
                alwaysRadioButton.setChecked(true);
            }
        }
        else if (result.contains(ConfigParams.GROUND_XIUZHENG.trim()))
        {
            String s = result.replaceAll(ConfigParams.GROUND_XIUZHENG.trim(), "").trim();
            if (TextUtils.isEmpty(s))
            {
                return;
            }
            if (s.equals("1"))
            {
                humRadioGroup.check(R.id.hum_update_radiobtton);
            }
            else if(s.equals("2"))
            {
                humRadioGroup.check(R.id.hum_noupdate_radiobtton);
            }
        }
        else if (result.contains(ConfigParams.GROUND_PrintMode.trim()))
        {
            String s = result.replaceAll(ConfigParams.GROUND_PrintMode.trim(), "").trim();
            if (TextUtils.isEmpty(s))
            {
                return;
            }
            if (s.equals("0"))
            {
                outputRadioGroup.check(R.id.no_send_radiobtton);
            }
            else
            {
                outputRadioGroup.check(R.id.send_radiobtton);
            }
        }
        else if (result.contains(ConfigParams.GROUND_tm_year.trim()))
        {
            String timeTemp = result.replaceAll(ConfigParams.GROUND_tm_year.trim(), "").trim();
            String time = timeTemp.replaceAll(" ", "0").trim();
            Log.i(TAG, "time:" + time);
            String month = time.substring(4,6);
            int intMonth = Integer.valueOf(month);
            if (intMonth > 12 || intMonth <1){

                ToastUtil.showToastLong("月份错误");
                return;
            }
            int day = Integer.valueOf(time.substring(6,8));

            if (day > 31 || intMonth < 1){

                ToastUtil.showToastLong("日期错误");
                return;
            }

            int hour = Integer.valueOf(time.substring(8,10));
            int min = Integer.valueOf(time.substring(10,12));
            int s= Integer.valueOf(time.substring(12,14));
            if (hour > 24 || hour < 0 || min > 60 || min < 0 || s > 60 || s < 0 ){

                ToastUtil.showToastLong("时间错误");
                return;
            }

            String rtuTime = getTime(time);
            if (rtuTimeTextView != null && rtuTime != null)
            {
                rtuTimeTextView.setText(rtuTime);
                setTime = time;
            }
        }
    }

    public void initContent()
    {
        yearContent = new String[66];
        for (int i = 0; i < 65; i++)
            yearContent[i] = String.valueOf(i + 1970);

        monthContent = new String[12];
        for (int i = 0; i < 12; i++)
        {
            monthContent[i] = String.valueOf(i + 1);
            if (monthContent[i].length() < 2)
            {
                monthContent[i] = "0" + monthContent[i];
            }
        }

        dayContent = new String[31];
        for (int i = 0; i < 31; i++)
        {
            dayContent[i] = String.valueOf(i + 1);
            if (dayContent[i].length() < 2)
            {
                dayContent[i] = "0" + dayContent[i];
            }
        }
        hourContent = new String[24];
        for (int i = 0; i < 24; i++)
        {
            hourContent[i] = String.valueOf(i);
            if (hourContent[i].length() < 2)
            {
                hourContent[i] = "0" + hourContent[i];
            }
        }

        minuteContent = new String[60];
        for (int i = 0; i < 60; i++)
        {
            minuteContent[i] = String.valueOf(i);
            if (minuteContent[i].length() < 2)
            {
                minuteContent[i] = "0" + minuteContent[i];
            }
        }
        secondContent = new String[60];
        for (int i = 0; i < 60; i++)
        {
            secondContent[i] = String.valueOf(i);
            if (secondContent[i].length() < 2)
            {
                secondContent[i] = "0" + secondContent[i];
            }
        }
    }


    /**
     * 根据年月算出这个月多少天
     *
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month)
    {
        int day = 30;
        boolean flag = false;
        switch (year % 4)
        {// 计算是否是闰年
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }




    /**
     * 选择设置的时间
     */
//    private void seletDate()
//    {
//        View view = ((LayoutInflater) parentActivity.getSystemService(parentActivity.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);
//
//        Calendar calendar = Calendar.getInstance();
//        int curYear = calendar.get(Calendar.YEAR);
//        int curMonth = calendar.get(Calendar.MONTH) + 1;
//        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
//        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
//        int curMinute = calendar.get(Calendar.MINUTE);
//        int curSecond = calendar.get(Calendar.SECOND);
//
//        yearWheel = (WheelView) view.findViewById(R.id.yearwheel);
//        monthWheel = (WheelView) view.findViewById(R.id.monthwheel);
//        dayWheel = (WheelView) view.findViewById(R.id.daywheel);
//        hourWheel = (WheelView) view.findViewById(R.id.hourwheel);
//        minuteWheel = (WheelView) view.findViewById(R.id.minutewheel);
//        secondWheel = (WheelView) view.findViewById(R.id.secondwheel);
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(view);
//
//        yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
//        yearWheel.setCurrentItem(curYear - 1970);
//        yearWheel.setCyclic(true);
//        yearWheel.setInterpolator(new AnticipateOvershootInterpolator());
//        yearWheel.addScrollingListener(scrollListener);
//
//
//        monthWheel.setAdapter(new StrericWheelAdapter(monthContent));
//        monthWheel.setCurrentItem(curMonth - 1);
//        monthWheel.setCyclic(true);
//        monthWheel.setInterpolator(new AnticipateOvershootInterpolator());
//        monthWheel.addScrollingListener(scrollListener);
//
//        dayWheel.setAdapter(new NumericWheelAdapter(1, getDay(Integer.parseInt(yearWheel.getCurrentItemValue()), Integer.parseInt(monthWheel.getCurrentItemValue())), "%02d"));
//        dayWheel.setCurrentItem(curDay - 1);
//        dayWheel.setCyclic(true);
//        dayWheel.setInterpolator(new AnticipateOvershootInterpolator());
//        dayWheel.addScrollingListener(scrollListener);
//
//
//        hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
//        hourWheel.setCurrentItem(curHour);
//        hourWheel.setCyclic(true);
//        hourWheel.setInterpolator(new AnticipateOvershootInterpolator());
//
//        minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
//        minuteWheel.setCurrentItem(curMinute);
//        minuteWheel.setCyclic(true);
//        minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());
//
//        secondWheel.setAdapter(new StrericWheelAdapter(secondContent));
//        secondWheel.setCurrentItem(curSecond);
//        secondWheel.setCyclic(true);
//        secondWheel.setInterpolator(new AnticipateOvershootInterpolator());
//
//        builder.setTitle(getString(R.string.Select_time));
//        builder.setPositiveButton(getString(R.string.Determine), new DialogInterface.OnClickListener()
//        {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//
//                setTime = "";
//                int dayq = dayWheel.getCurrentItem() + 1;
//                String year = yearWheel.getCurrentItemValue();
//                String month = monthWheel.getCurrentItemValue();
//                String day = dayq < 10 ? 0 + "" + dayq : dayq + "";
//                String hour = hourWheel.getCurrentItemValue();
//                String min = minuteWheel.getCurrentItemValue();
//                String second = secondWheel.getCurrentItemValue();
//                StringBuffer sb = new StringBuffer();
//                sb.append(year).append(getString(R.string.year))
//                        .append(month).append(getString(R.string.month))
//                        .append(day).append(getString(R.string.day)).append(hour)
//                        .append(getString(R.string.hour)).append(min)
//                        .append(getString(R.string.minute)).append(second).append(getString(R.string.second));
//
//
//                Log.info(TAG, "date::sb:" + sb.toString());
//
//                StringBuffer sb1 = new StringBuffer();
//                sb1.append(year)
//                        .append(month)
//                        .append(day).append(hour).append(min)
//                        .append(second);
//
//                setTime = sb1.toString();
//
//                rtuTimeTextView.setText(sb.toString());
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//
//    }

    private String getTime(String strDate)
    {
        // 准备第一个模板，从字符串中提取出日期数字
        String pat1 = "yyyyMMddHHmmss";
        // 准备第二个模板，将提取后的日期数字变为指定的格式
        String pat2 = "yyyy"+getString(R.string.year)+"MM"+getString(R.string.month)+"dd"+getString(R.string.day)+"HH"+getString(R.string.hour)+"mm"+getString(R.string.minute)+"ss"+getString(R.string.second);
        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(pat2);        // 实例化模板对象
        Date d = null;
        try
        {
            d = sdf1.parse(strDate);   // 将给定的字符串中的日期提取出来
        }
        catch (Exception e)
        {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }



        String time = sdf2.format(d);
        if (TextUtils.isEmpty(time))
        {
            return null;
        }
        return time;
    }

}
