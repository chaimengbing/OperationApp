package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.text.SimpleDateFormat;
import java.util.Date;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class CommRtuSysFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private static final String DEFAULT_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final String TAG = CommRtuSysFragment.class.getSimpleName();
    private EditText siteTestEditText;
    private Spinner timeSpinner;
    private Button siteTestSetButton;
    private SimpleSpinnerAdapter timeTypeAdapter;
    private String[] timeItems;
    private RadioGroup runStatusRadioGroup;
    private RadioButton alwaysRadioButton;
    private RadioButton lowRadioButton;

    private TextView currentTime;
    private TextView rtuTimeTextView;
    private Button timeButton;
    private Button resetButton;
    private SimpleDateFormat sendTimeFormat;
    private SimpleDateFormat timeFormat;

    public static String[] yearContent = null;
    public static String[] monthContent = null;
    public static String[] dayContent = null;
    public static String[] hourContent = null;
    public static String[] minuteContent = null;
    public static String[] secondContent = null;

    private String setTime = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_comm_setting_system_pamars);
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

        siteTestSetButton = (Button) view.findViewById(R.id.site_test_setting_button);
        siteTestEditText = (EditText) view.findViewById(R.id.site_test_add_number);
        timeSpinner = (Spinner) view.findViewById(R.id.time_interval_spinner);

        runStatusRadioGroup = (RadioGroup) view.findViewById(R.id.run_status);
        lowRadioButton = (RadioButton) view.findViewById(R.id.low_power_radiobtton);
        alwaysRadioButton = (RadioButton) view.findViewById(R.id.always_online_radiobtton);
        timeButton = (Button) view.findViewById(R.id.time_button);
        resetButton = (Button) view.findViewById(R.id.reset_button);
        currentTime = (TextView) view.findViewById(R.id.current_time);
        rtuTimeTextView = (TextView) view.findViewById(R.id.rtu_time);

    }

    @Override
    public void initData() {
        initContent();

        timeItems = getResources().getStringArray(R.array.comm_rtu_time);
        timeTypeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item,
                timeItems);
        timeSpinner.setAdapter(timeTypeAdapter);

        timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);

        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadParameter);
    }

    @Override
    public void initEvent() {
        siteTestSetButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        rtuTimeTextView.setOnClickListener(this);

        runStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetWorkMode;
                switch (i) {
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

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.site_test_setting_button:
                String number = siteTestEditText.getText().toString();
                if (TextUtils.isEmpty(number)) {
                    ToastUtil.showToastLong(getString(R.string.RTU_station_number_empty));
                    return;
                }
                String ss = "";
                if (number.length() < 10) {
                    for (int i = 0; i < 10 - number.length(); i++) {
                        ss += "0";
                    }
                }
                String content = ConfigParams.SetAddr + ss + number;
                SocketUtil.getSocketUtil().sendContent(content);
                break;

            case R.id.time_button:
                if (TextUtils.isEmpty(setTime)) {
                    SocketUtil.getSocketUtil().sendContent(ConfigParams.SETTIME + sendTimeFormat.format(System.currentTimeMillis()));
                } else {
                    SocketUtil.getSocketUtil().sendContent(ConfigParams.SETTIME + setTime);
                }
                break;
            case R.id.reset_button:
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
        if (result.contains(ConfigParams.SetAddr.trim())) {// 遥测站地址：
            siteTestEditText.setText(result.replaceAll(ConfigParams.SetAddr.trim(), "").trim());
            siteTestEditText.setSelection(siteTestEditText.getText().toString().trim().length());
        } else if (result.contains(ConfigParams.SetPacketInterval.trim())) {// 采集时间间隔：
            String site = result.replaceAll(ConfigParams.SetPacketInterval.trim(), "").trim();
            int siteNum = Integer.parseInt(site);
            if (siteNum >= 0 && siteNum <= 5) {
                timeSpinner.setSelection(siteNum, false);
                timeTypeAdapter.setSelectedItem(siteNum);
            }

            timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    timeTypeAdapter.setSelectedItem(i);

                    String content = ConfigParams.SetPacketInterval + i;
                    SocketUtil.getSocketUtil().sendContent(content);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else if (result.contains(ConfigParams.SetWorkMode.trim())) {
            String s = result.replaceAll(ConfigParams.SetWorkMode.trim(), "").trim();
            if (TextUtils.isEmpty(s)) {
                return;
            }
            if (s.equals("0")) {
                lowRadioButton.setChecked(true);
            } else {
                alwaysRadioButton.setChecked(true);
            }
        } else if (result.contains(ConfigParams.SETTIME.trim())) {
            String timeTemp = result.replaceAll(ConfigParams.SETTIME.trim(), "").trim();
            String time = timeTemp.replaceAll(" ", "0").trim();
            String rtuTime = getTime(time);
            if (rtuTimeTextView != null && rtuTime != null) {
                rtuTimeTextView.setText(rtuTime);
                setTime = time;
            }
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


    /**
     * 选择设置的时间
     */
//    private void seletDate()
//    {
//        View view = ((LayoutInflater) parentActivity.getSystemService(parentActivity
// .LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);
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
//        dayWheel.setAdapter(new NumericWheelAdapter(1, getDay(Integer.parseInt(yearWheel
// .getCurrentItemValue()), Integer.parseInt(monthWheel.getCurrentItemValue())), "%02d"));
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
//        builder.setPositiveButton(getString(R.string.Determine), new DialogInterface
// .OnClickListener()
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
//                        .append(getString(R.string.minute)).append(second).append(getString(R
// .string.second));
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
