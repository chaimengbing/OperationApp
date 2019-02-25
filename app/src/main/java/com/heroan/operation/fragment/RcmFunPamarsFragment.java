package com.heroan.operation.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;
import com.heroan.operation.view.wheel.NumericWheelAdapter;
import com.heroan.operation.view.wheel.OnWheelScrollListener;
import com.heroan.operation.view.wheel.StrericWheelAdapter;
import com.heroan.operation.view.wheel.WheelView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class RcmFunPamarsFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private static final String TAG = RcmFunPamarsFragment.class.getSimpleName();

    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";

    private EditText numEditText;
    private EditText customerEditText;
    private RadioGroup runStatusRadioGroup;
    private RadioGroup takeModelRadioGroup;
    private Button funAddrButton;
    private Button customerButton;
    private Button takePictureButton;

    private Spinner timeSpinner;
    private Spinner collectionSpinner;
    private String[] timeItems;
    private String[] collectionItems;
    private SimpleSpinnerAdapter timeAdapter;
    private SimpleSpinnerAdapter collectionAdapter;

    private Spinner customSpinner;
    private String[] customItems;
    private SimpleSpinnerAdapter customAdapter;


    private SimpleDateFormat sendTimeFormat;
    private WheelView yearWheel, monthWheel, dayWheel, hourWheel, minuteWheel, secondWheel;
    public static String[] yearContent = null;
    public static String[] monthContent = null;
    public static String[] dayContent = null;
    public static String[] hourContent = null;
    public static String[] minuteContent = null;
    public static String[] secondContent = null;
    private String setTime = "";
    private TextView rtuTimeTextView;
    private Button timeButton;
    private boolean isFirst = true;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private Button collectButton;

    private Spinner customLzSpinner;
    private String[] customLzItems;
    private SimpleSpinnerAdapter customLzAdapter;
    private LinearLayout lz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_rcm_fun);
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
        numEditText = (EditText) view.findViewById(R.id.site_fun_number);
        customerEditText = (EditText) view.findViewById(R.id.customer_number);
        runStatusRadioGroup = (RadioGroup) view.findViewById(R.id.run_fun_status);
        takeModelRadioGroup = (RadioGroup) view.findViewById(R.id.take_pictures_model);
        funAddrButton = (Button) view.findViewById(R.id.site_fun_button);
        takePictureButton = (Button) view.findViewById(R.id.take_pictures);
        timeSpinner = (Spinner) view.findViewById(R.id.fun_interval_spinner);
        collectionSpinner = (Spinner) view.findViewById(R.id.time_interval_spinner);
        customSpinner = (Spinner) view.findViewById(R.id.customer_spinner);
        customLzSpinner = (Spinner) view.findViewById(R.id.fun_interval_spinner1);

        lz = (LinearLayout) view.findViewById(R.id.lz);

        timeButton = (Button) view.findViewById(R.id.time_fun_button);
        customerButton = (Button) view.findViewById(R.id.customer_button);
        rtuTimeTextView = (TextView) view.findViewById(R.id.rtu_fun_time);

        collectButton = (Button) view.findViewById(R.id.collect_button_two);
        checkBox1 = (CheckBox) view.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) view.findViewById(R.id.checkbox4);

        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);
        isFirst = true;
        initView(view);
    }

    @Override
    public void initData() {
        ServiceUtils.sendData(ConfigParams.ReadFunctionData);

        timeItems = getResources().getStringArray(R.array.pictime_interval);
        timeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item,
                timeItems);
        timeSpinner.setAdapter(timeAdapter);

        collectionItems = getResources().getStringArray(R.array.comm_rtu_time);
        collectionAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item,
                collectionItems);
        collectionSpinner.setAdapter(collectionAdapter);

        customItems = getResources().getStringArray(R.array.customer_select);
        customAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item,
                customItems);
        customSpinner.setAdapter(customAdapter);

        customLzItems = getResources().getStringArray(R.array.pictime_interval);
        customLzAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item,
                customLzItems);
        customLzSpinner.setAdapter(customLzAdapter);

        initContent();
    }

    @Override
    public void initEvent() {
        funAddrButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        rtuTimeTextView.setOnClickListener(this);
        customerButton.setOnClickListener(this);
        collectButton.setOnClickListener(this);
        takePictureButton.setOnClickListener(this);


        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (isFirst) {
                    isFirst = false;
                    return;
                }
                timeAdapter.setSelectedItem(position);

                String content = ConfigParams.SetPacketInterval + position;
                ServiceUtils.sendData(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        customLzSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (isFirst) {
                    isFirst = false;
                    return;
                }
                customLzAdapter.setSelectedItem(position);

                String content = ConfigParams.SetVideoInterval + position;
                ServiceUtils.sendData(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        collectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (isFirst) {
                    isFirst = false;
                    return;
                }

                collectionAdapter.setSelectedItem(i);

                String content = ConfigParams.SetCollectionInterval + i;
                ServiceUtils.sendData(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView(final View view) {
        runStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetWorkMode;
                switch (i) {
                    case R.id.low_power_fun_radiobtton:
                        String low = content + "0";
                        ServiceUtils.sendData(low);

                        break;
                    case R.id.always_online_fun_radiobtton:
                        String always = content + "1";
                        ServiceUtils.sendData(always);

                        break;

                    default:
                        break;
                }
            }
        });

        takeModelRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed()) {
                    return;
                }

                String content = ConfigParams.SetTakePhotoMode;
                switch (i) {
                    case R.id.Take_pictures_regularly:
                        String regularly = content + "0";
                        ServiceUtils.sendData(regularly);
//                        lz.setVisibility(View.VISIBLE);
                        break;

                    case R.id.Plus_pictures_taken:
                        String plus = content + "1";
                        ServiceUtils.sendData(plus);
//                        lz.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        String data = "";
        String port = "";
        String content = "";
        switch (view.getId()) {
            case R.id.site_fun_button:
                data = numEditText.getText().toString().trim();
                if (TextUtils.isEmpty(data)) {
                    ToastUtil.showToastLong(getString(R.string.Telemetry_station_address_empty));
                    return;
                }
                if (data.length() > 10) {
                    ToastUtil.showToastLong(getString(R.string.Telemetry_station_address_exceeds_limit));
                    return;
                }
                content = ConfigParams.SetAddr + data;
                break;
            case R.id.customer_button:
                data = customerEditText.getText().toString().trim();
                if (TextUtils.isEmpty(data)) {
                    ToastUtil.showToastLong(getString(R.string.Customer_number_empty));
                    return;
                }
                content = ConfigParams.CustomerSelect + data;
                break;
            case R.id.time_fun_button:
                if (TextUtils.isEmpty(setTime)) {
                    ServiceUtils.sendData(ConfigParams.SETTIME + sendTimeFormat.format(System.currentTimeMillis()));
                } else {
                    ServiceUtils.sendData(ConfigParams.SETTIME + setTime);
                }
                break;

            case R.id.rtu_fun_time:
                seletDate();
                break;

            case R.id.collect_button_two:
                String check1 = checkBox1 != null && checkBox1.isChecked() ? "1" : "0";
                String check2 = checkBox2 != null && checkBox2.isChecked() ? "1" : "0";
                String check3 = checkBox3 != null && checkBox3.isChecked() ? "1" : "0";
                String check4 = checkBox4 != null && checkBox4.isChecked() ? "1" : "0";
                String content1 = ConfigParams.SetScadaFactor + check1 + check2 + check3 + check4;
                ServiceUtils.sendData(content1);
                break;

            case R.id.take_pictures:
                content = ConfigParams.SetTakePhoto;
                break;
            default:
                content = "";
                break;
        }

        ServiceUtils.sendData(content);
    }

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            sendData();
            OperationApplication.applicationHandler.postDelayed(timeRunnable, UiEventEntry.TIME);
        }
    };

    private void sendData() {
        ServiceUtils.sendData(ConfigParams.ReadStatus);
    }

    private void startupStatus() {
        ServiceUtils.sendData(ConfigParams.StartUp);
        OperationApplication.applicationHandler.postDelayed(timeRunnable, UiEventEntry.TIME);
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content)) {
                return;
            }
            setData(result);

        }
    }

    private void setData(String result) {
        String data;
        String ip;
        int value;
        if (result.contains(ConfigParams.SetAddr.trim())) {//
            data = result.replaceAll(ConfigParams.SetAddr, "").trim();
            numEditText.setText(data);
        } else if (result.contains(ConfigParams.SetWorkMode.trim())) {//
            data = result.replaceAll(ConfigParams.SetWorkMode, "").trim();
            if ("1".equals(data)) {
                runStatusRadioGroup.check(R.id.always_online_fun_radiobtton);
            } else {
                runStatusRadioGroup.check(R.id.low_power_fun_radiobtton);
            }
        } else if (result.contains(ConfigParams.SetTakePhotoMode.trim())) {//拍照方式
            data = result.replaceAll(ConfigParams.SetTakePhotoMode, "").trim();
            if ("1".equals(data)) {
                takeModelRadioGroup.check(R.id.Plus_pictures_taken);
            } else {
                takeModelRadioGroup.check(R.id.Take_pictures_regularly);
            }
        } else if (result.contains(ConfigParams.SetPacketInterval.trim())) {// 定时报间隔
            data = result.replaceAll(ConfigParams.SetPacketInterval, "").trim();

            value = Integer.parseInt(data);
            if (value < timeItems.length && value > 0) {
                timeSpinner.setSelection(value);
            }
        } else if (result.contains(ConfigParams.SetVideoInterval.trim())) {// 定时报间隔
            data = result.replaceAll(ConfigParams.SetVideoInterval, "").trim();

            value = Integer.parseInt(data);
            if (value < customLzItems.length && value > 0) {
                customLzSpinner.setSelection(value);
            }
        } else if (result.contains(ConfigParams.SetCollectionInterval.trim())) {//采集间隔
            data = result.replaceAll(ConfigParams.SetCollectionInterval, "").trim();
            value = Integer.parseInt(data);
            if (value < collectionItems.length && value > 0) {
                collectionSpinner.setSelection(value);
            }

        } else if (result.contains(ConfigParams.CustomerSelect.trim())) {// 客户编号
            data = result.replaceAll(ConfigParams.CustomerSelect, "").trim();
            customerEditText.setText(data);

//            value = Integer.parseInt(data);
//            if (value < customItems.length && value > 0)
//            {
//                customSpinner.setSelection(value);
//            }
//            customSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//            {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long
// id)
//                {
//                    customAdapter.setSelectedItem(position);
//                    String content = ConfigParams.CustomerSelect + position;
//                    ServiceUtils.sendData(content);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent)
//                {
//
//                }
//            });
        } else if (result.contains(ConfigParams.SETTIME.trim())) {
            String timeTemp = result.replaceAll(ConfigParams.SETTIME.trim(), "").trim();
            String time = timeTemp.replaceAll(" ", "0").trim();
            Log.i(TAG, "time:" + time);
            String month = time.substring(4, 6);
            int intMonth = Integer.valueOf(month);
            if (intMonth > 12 || intMonth < 1) {

                ToastUtil.showToastLong("月份错误");
                return;
            }
            int day = Integer.valueOf(time.substring(6, 8));

            if (day > 31 || intMonth < 1) {

                ToastUtil.showToastLong("日期错误");
                return;
            }

            int hour = Integer.valueOf(time.substring(8, 10));
            int min = Integer.valueOf(time.substring(10, 12));
            int s = Integer.valueOf(time.substring(12, 14));
            if (hour > 24 || hour < 0 || min > 60 || min < 0 || s > 60 || s < 0) {

                ToastUtil.showToastLong("时间错误");
                return;
            }
            String rtuTime = getTime(time);
            if (rtuTimeTextView != null && rtuTime != null) {
                rtuTimeTextView.setText(rtuTime);
                setTime = time;
            }
        } else if (result.contains("System Up")) {// 开机完成
        } else if (result.contains("System Down")) {// 正在开机

        }
        if (result.contains(ConfigParams.SetScadaFactor.trim()) && (!result.equals("OK"))) {//
            // 获取采集要素设置
            String collect = result.replaceAll(ConfigParams.SetScadaFactor.trim(), "").trim();
            checkBox1.setChecked((collect.charAt(0)) == '1');
            checkBox2.setChecked((collect.charAt(1)) == '1');
            checkBox3.setChecked((collect.charAt(2)) == '1');
            checkBox4.setChecked((collect.charAt(3)) == '1');
        }
    }


    public void initContent() {
        yearContent = new String[66];
        for (int i = 0; i < 65; i++)
            yearContent[i] = String.valueOf(i + 1970);

        monthContent = new String[12];
        for (int i = 0; i < 12; i++) {
            monthContent[i] = String.valueOf(i + 1);
            if (monthContent[i].length() < 2) {
                monthContent[i] = "0" + monthContent[i];
            }
        }

        dayContent = new String[31];
        for (int i = 0; i < 31; i++) {
            dayContent[i] = String.valueOf(i + 1);
            if (dayContent[i].length() < 2) {
                dayContent[i] = "0" + dayContent[i];
            }
        }
        hourContent = new String[24];
        for (int i = 0; i < 24; i++) {
            hourContent[i] = String.valueOf(i);
            if (hourContent[i].length() < 2) {
                hourContent[i] = "0" + hourContent[i];
            }
        }

        minuteContent = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteContent[i] = String.valueOf(i);
            if (minuteContent[i].length() < 2) {
                minuteContent[i] = "0" + minuteContent[i];
            }
        }
        secondContent = new String[60];
        for (int i = 0; i < 60; i++) {
            secondContent[i] = String.valueOf(i);
            if (secondContent[i].length() < 2) {
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
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {// 计算是否是闰年
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
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

    OnWheelScrollListener scrollListener = new OnWheelScrollListener()
    {

        @Override
        public void onScrollingStarted(WheelView wheel)
        {
        }

        @Override
        public void onScrollingFinished(WheelView wheel)
        {
            if (yearWheel != null && monthWheel != null && dayWheel != null)
            {
                dayWheel.setAdapter(new NumericWheelAdapter(1, getDay(Integer.parseInt(yearWheel.getCurrentItemValue()), Integer.parseInt(monthWheel.getCurrentItemValue())), "%02d"));
                dayWheel.setCyclic(true);
                dayWheel.setInterpolator(new AnticipateOvershootInterpolator());

            }
        }
    };

    /**
     * 选择设置的时间
     */
    private void seletDate() {
        View view = ((LayoutInflater) context.getSystemService(context
                .LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);

        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);
        int curSecond = calendar.get(Calendar.SECOND);

        yearWheel = (WheelView) view.findViewById(R.id.yearwheel);
        monthWheel = (WheelView) view.findViewById(R.id.monthwheel);
        dayWheel = (WheelView) view.findViewById(R.id.daywheel);
        hourWheel = (WheelView) view.findViewById(R.id.hourwheel);
        minuteWheel = (WheelView) view.findViewById(R.id.minutewheel);
        secondWheel = (WheelView) view.findViewById(R.id.secondwheel);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
        yearWheel.setCurrentItem(curYear - 1970);
        yearWheel.setCyclic(true);
        yearWheel.setInterpolator(new AnticipateOvershootInterpolator());
        yearWheel.addScrollingListener(scrollListener);


        monthWheel.setAdapter(new StrericWheelAdapter(monthContent));
        monthWheel.setCurrentItem(curMonth - 1);
        monthWheel.setCyclic(true);
        monthWheel.setInterpolator(new AnticipateOvershootInterpolator());
        monthWheel.addScrollingListener(scrollListener);

        dayWheel.setAdapter(new NumericWheelAdapter(1, getDay(Integer.parseInt(yearWheel
                .getCurrentItemValue()), Integer.parseInt(monthWheel.getCurrentItemValue())), "%02d"));
        dayWheel.setCurrentItem(curDay - 1);
        dayWheel.setCyclic(true);
        dayWheel.setInterpolator(new AnticipateOvershootInterpolator());
        dayWheel.addScrollingListener(scrollListener);


        hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
        hourWheel.setCurrentItem(curHour);
        hourWheel.setCyclic(true);
        hourWheel.setInterpolator(new AnticipateOvershootInterpolator());

        minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
        minuteWheel.setCurrentItem(curMinute);
        minuteWheel.setCyclic(true);
        minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());

        secondWheel.setAdapter(new StrericWheelAdapter(secondContent));
        secondWheel.setCurrentItem(curSecond);
        secondWheel.setCyclic(true);
        secondWheel.setInterpolator(new AnticipateOvershootInterpolator());

        builder.setTitle(getString(R.string.Select_time));
        builder.setPositiveButton(getString(R.string.Determine), new DialogInterface
                .OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                setTime = "";
                int dayq = dayWheel.getCurrentItem() + 1;
                String year = yearWheel.getCurrentItemValue();
                String month = monthWheel.getCurrentItemValue();
                String day = dayq < 10 ? 0 + "" + dayq : dayq + "";
                String hour = hourWheel.getCurrentItemValue();
                String min = minuteWheel.getCurrentItemValue();
                String second = secondWheel.getCurrentItemValue();
                StringBuffer sb = new StringBuffer();
                sb.append(year).append(getString(R.string.year))
                        .append(month).append(getString(R.string.month))
                        .append(day).append(getString(R.string.day)).append(hour)
                        .append(getString(R.string.hour)).append(min)
                        .append(getString(R.string.minute)).append(second).append(getString(R
                        .string.second));


                StringBuffer sb1 = new StringBuffer();
                sb1.append(year)
                        .append(month)
                        .append(day).append(hour).append(min)
                        .append(second);

                setTime = sb1.toString();

                rtuTimeTextView.setText(sb.toString());
                dialog.cancel();
            }
        });

        builder.show();

    }

    private String getTime(String strDate) {
        // 准备第一个模板，从字符串中提取出日期数字
        String pat1 = "yyyyMMddHHmmss";
        // 准备第二个模板，将提取后的日期数字变为指定的格式
        String pat2 =
                "yyyy" + getString(R.string.year) + "MM" + getString(R.string.month) + "dd" + getString(R.string.day) + "HH" + getString(R.string.hour) + "mm" + getString(R.string.minute) + "ss" + getString(R.string.second);
        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(pat2);        // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(strDate);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }

        String time = sdf2.format(d);
        if (TextUtils.isEmpty(time)) {
            return null;
        }
        return time;
    }


}
