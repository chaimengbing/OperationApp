package com.heroan.operation.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;

import com.heroan.operation.R;
import com.heroan.operation.fragment.SearchFragment;
import com.heroan.operation.view.wheel.NumericWheelAdapter;
import com.heroan.operation.view.wheel.OnWheelScrollListener;
import com.heroan.operation.view.wheel.StrericWheelAdapter;
import com.heroan.operation.view.wheel.WheelView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zuo.biao.library.util.Log;
import zuo.biao.library.util.SettingUtil;

public class ServiceUtils {

    private static final String TAG = ServiceUtils.class.getSimpleName();


    private static Map<String, String> resultMap = new HashMap<>();
    private static ExecutorService socketThreads;
    private static String years;
    private static String months;
    private static String days;
    private static String hours;
    private static String minutes;
    private static String seconds;
    private static String select_time;
    private static String determine;

    private static String normal;
    private static String power_failure;
    private static String Call_police;
    private static String malfunction;
    private static String Open;
    private static String Close;
    private static String abnormal;
    private static String IC_card_valid;
    private static String Pump_work;
    private static String Pump_down;
    private static String Not_overrun;
    private static String Water_limit;

    private static String Reset;
    private static String Device_error;
    private static String Low_signal_strength;
    private static String No_SIM_card;
    private static String Not_registered_network;
    private static String Signal_strength_normal;
    private static String Sleep;
    private static String Shut_down;
    private static String Register_network;
    private static String PPP_connected;
    private static String PPP_failed;

    private static String Set_service_type;
    private static String Set_server_address_port_number;
    private static String Set_ID_connection;
    private static String Open_connection;
    private static String Check_connection_status;
    private static String Waiting_for_data;
    private static String Read_received_data;
    private static String Send_data_head;
    private static String Send_data_content;
    private static String Close_connection;
    private static String Task_off;
    private static String status1;

    private static String Read_number_messages;
    private static String Read_text_messages;
    private static String Send_message_header;
    private static String Send_SMS_content;
    private static String Delete_text_messages;

    private static String Not_read;
    private static String Reading_picture;
    private static String Read_success;
    private static String Reading_failed;

    private static String Did_not_send;
    private static String sending;
    private static String Sent_successfully;
    private static String Failed_send;

    public static ExecutorService getSocketThreads() {
        if (socketThreads == null) {
            socketThreads = Executors.newFixedThreadPool(5);
        }
        return socketThreads;
    }


    private static ServiceUtils serviceUtils = null;

    public static ServiceUtils getServiceUtils() {

        if (serviceUtils == null) {
            serviceUtils = new ServiceUtils();
        }
        return serviceUtils;
    }

    private WheelView yearWheel, monthWheel, dayWheel, hourWheel, minuteWheel, secondWheel;
    public static String[] yearContent = null;
    public static String[] monthContent = null;
    public static String[] dayContent = null;
    public static String[] hourContent = null;
    public static String[] minuteContent = null;
    public static String[] secondContent = null;


    /**
     * 选择设置的时间
     */
    public void seletDate(final Activity context) {
        View view = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)
        ).inflate(R.layout.time_picker, null);

        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);
        int curSecond = calendar.get(Calendar.SECOND);

        yearWheel = view.findViewById(R.id.yearwheel);
        monthWheel = view.findViewById(R.id.monthwheel);
        dayWheel = view.findViewById(R.id.daywheel);
        hourWheel = view.findViewById(R.id.hourwheel);
        minuteWheel = view.findViewById(R.id.minutewheel);
        secondWheel = view.findViewById(R.id.secondwheel);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

        builder.setTitle(context.getString(R.string.Select_time));
        builder.setPositiveButton(context.getString(R.string.Determine), new DialogInterface
                .OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                int dayq = dayWheel.getCurrentItem() + 1;
                String year = yearWheel.getCurrentItemValue();
                String month = monthWheel.getCurrentItemValue();
                String day = dayq < 10 ? 0 + "" + dayq : dayq + "";
                String hour = hourWheel.getCurrentItemValue();
                String min = minuteWheel.getCurrentItemValue();
                String second = secondWheel.getCurrentItemValue();
                StringBuffer sb = new StringBuffer();
                sb.append(year).append(context.getString(R.string.year))
                        .append(month).append(context.getString(R.string.month))
                        .append(day).append(context.getString(R.string.day)).append(hour)
                        .append(context.getString(R.string.hour)).append(min)
                        .append(context.getString(R.string.minute)).append(second).append
                        (context.getString(R.string.second));


                StringBuffer sb1 = new StringBuffer();
                sb1.append(year)
                        .append(month)
                        .append(day).append(hour).append(min)
                        .append(second);

                EventNotifyHelper.getInstance().postNotification(UiEventEntry.SELECT_TIME, sb
                        .toString(), sb1.toString());
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void seletSearchDate(final Activity context, final int type) {
        View view = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)
        ).inflate(R.layout.time_search_picker, null);

        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);

        yearWheel = view.findViewById(R.id.yearwheel);
        monthWheel = view.findViewById(R.id.monthwheel);
        dayWheel = view.findViewById(R.id.daywheel);
        hourWheel = view.findViewById(R.id.hourwheel);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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


        builder.setTitle(context.getString(R.string.Select_time));
        builder.setPositiveButton(context.getString(R.string.Determine), new DialogInterface
                .OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                int dayq = dayWheel.getCurrentItem() + 1;
                String year = yearWheel.getCurrentItemValue();
                String month = monthWheel.getCurrentItemValue();
                String day = dayq < 10 ? 0 + "" + dayq : dayq + "";
                String hour = hourWheel.getCurrentItemValue();
                StringBuffer sb = new StringBuffer();
                sb.append(year).append(context.getString(R.string.year))
                        .append(month).append(context.getString(R.string.month))
                        .append(day).append(context.getString(R.string.day)).append(hour)
                        .append(context.getString(R.string.hour));
                StringBuffer sb1 = new StringBuffer();
                sb1.append(year).append(" ")
                        .append(month).append(" ")
                        .append(day).append(" ").append(hour);

                EventNotifyHelper.getInstance().postNotification(UiEventEntry.SELECT_TIME, sb
                        .toString(), sb1.toString(), type);
                dialog.cancel();
            }
        });

        builder.show();

    }

    public static String getTime(String strDate, Context context) {
        years = context.getString(R.string.year);
        months = context.getString(R.string.month);
        days = context.getString(R.string.day);
        hours = context.getString(R.string.hour);
        minutes = context.getString(R.string.minute);
        seconds = context.getString(R.string.second);
        select_time = context.getString(R.string.Select_time);
        determine = context.getString(R.string.Determine);
        // 准备第一个模板，从字符串中提取出日期数字
        String pat1 = "yyyyMMddHHmmss";
        // 准备第二个模板，将提取后的日期数字变为指定的格式
        String pat2 =
                "yyyy" + years + "MM" + months + "dd" + days + "HH" + hours + "mm" + minutes +
                        "ss" + seconds;
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


    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

        @Override
        public void onScrollingStarted(WheelView wheel) {
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            if (yearWheel != null && monthWheel != null && dayWheel != null) {
                dayWheel.setAdapter(new NumericWheelAdapter(1, getDay(Integer.parseInt
                        (yearWheel.getCurrentItemValue()), Integer.parseInt(monthWheel.getCurrentItemValue())), "%02d"));
                dayWheel.setCyclic(true);
                dayWheel.setInterpolator(new AnticipateOvershootInterpolator());

            }
        }
    };

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
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    private static String hexStr = "0123456789ABCDEF";  //全局


    //charToByte返回在指定字符的第一个发生的字符串中的索引，即返回匹配字符
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    public static int hexToInt(byte b) throws Exception {
        if (b >= '0' && b <= '9') {
            return (int) b - '0';
        }
        if (b >= 'a' && b <= 'f') {
            return (int) b + 10 - 'a';
        }
        if (b >= 'A' && b <= 'F') {
            return (int) b + 10 - 'A';
        }
        throw new Exception("invalid hex");
    }

    public static byte[] decodeToBytes(String hexString) {
        byte[] hex = hexString.getBytes();
        if ((hex.length % 2) != 0) {
            return null;
        }
        byte[] ret = new byte[hex.length / 2];
        int j = 0;
        int i = 0;
        try {
            while (i < hex.length) {
                byte hi = hex[i++];
                byte lo = hex[i++];
                ret[j++] = (byte) ((hexToInt(hi) << 4) | hexToInt(lo));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }


    public static String decodeToString(String hexString) {
        return new String(decodeToBytes(hexString));
    }

    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    /**
     * @param hexString
     * @return 将十六进制转换为字节数组
     */
    public static byte[] HexStringToBinary(String hexString) {
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位

        for (int i = 0; i < len; i++) {
            //右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);//高地位做或运算
        }
        return bytes;
    }

    public static Bitmap getPhotoFromSDCard(String path, String photoName) {
        Bitmap photoBitmap = BitmapFactory.decodeFile(path + "/" + photoName);
        if (photoBitmap == null) {
            return null;
        } else {
            return photoBitmap;
        }
    }


    public static String getIntLowHex(int value) {
        String data = ServiceUtils.getStr(Integer.toHexString(value) + "", 4);
        return getStringLowHex(data);
    }


    public static String getStringLowHex(String data) {
        List<String> list = new ArrayList<>();
        String result = "";
        byte[] bytes = data.getBytes();
        Log.i(TAG, bytes.length + "");


        for (int i = 0; i < data.length(); i += 2) {
            String s = data.substring(i, i + 2);
            list.add(s);

        }

        for (int i = (list.size() - 1); i >= 0; i--) {
            result += list.get(i);
        }
        return result;
    }


    static byte[] crc16_tab_h = {(byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01,
            (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80,
            (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01,
            (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
            (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01,
            (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80,
            (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00,
            (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80,
            (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01,
            (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
            (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
            (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
            (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
            (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
            (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
            (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
            (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
            (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40};

    static byte[] crc16_tab_l = {(byte) 0x00, (byte) 0xC0, (byte) 0xC1, (byte) 0x01, (byte) 0xC3,
            (byte) 0x03, (byte) 0x02, (byte) 0xC2, (byte) 0xC6, (byte) 0x06, (byte) 0x07,
            (byte) 0xC7, (byte) 0x05, (byte) 0xC5, (byte) 0xC4, (byte) 0x04, (byte) 0xCC,
            (byte) 0x0C, (byte) 0x0D, (byte) 0xCD, (byte) 0x0F, (byte) 0xCF, (byte) 0xCE,
            (byte) 0x0E, (byte) 0x0A, (byte) 0xCA, (byte) 0xCB, (byte) 0x0B, (byte) 0xC9,
            (byte) 0x09, (byte) 0x08, (byte) 0xC8, (byte) 0xD8, (byte) 0x18, (byte) 0x19,
            (byte) 0xD9, (byte) 0x1B, (byte) 0xDB, (byte) 0xDA, (byte) 0x1A, (byte) 0x1E,
            (byte) 0xDE, (byte) 0xDF, (byte) 0x1F, (byte) 0xDD, (byte) 0x1D, (byte) 0x1C,
            (byte) 0xDC, (byte) 0x14, (byte) 0xD4, (byte) 0xD5, (byte) 0x15, (byte) 0xD7,
            (byte) 0x17, (byte) 0x16, (byte) 0xD6, (byte) 0xD2, (byte) 0x12,
            (byte) 0x13, (byte) 0xD3, (byte) 0x11, (byte) 0xD1, (byte) 0xD0, (byte) 0x10,
            (byte) 0xF0, (byte) 0x30, (byte) 0x31, (byte) 0xF1, (byte) 0x33, (byte) 0xF3,
            (byte) 0xF2, (byte) 0x32, (byte) 0x36, (byte) 0xF6, (byte) 0xF7, (byte) 0x37,
            (byte) 0xF5, (byte) 0x35, (byte) 0x34, (byte) 0xF4, (byte) 0x3C, (byte) 0xFC,
            (byte) 0xFD, (byte) 0x3D, (byte) 0xFF, (byte) 0x3F, (byte) 0x3E, (byte) 0xFE,
            (byte) 0xFA, (byte) 0x3A, (byte) 0x3B, (byte) 0xFB, (byte) 0x39, (byte) 0xF9,
            (byte) 0xF8, (byte) 0x38, (byte) 0x28, (byte) 0xE8, (byte) 0xE9, (byte) 0x29,
            (byte) 0xEB, (byte) 0x2B, (byte) 0x2A, (byte) 0xEA, (byte) 0xEE, (byte) 0x2E,
            (byte) 0x2F, (byte) 0xEF, (byte) 0x2D, (byte) 0xED, (byte) 0xEC, (byte) 0x2C,
            (byte) 0xE4, (byte) 0x24, (byte) 0x25, (byte) 0xE5, (byte) 0x27, (byte) 0xE7,
            (byte) 0xE6, (byte) 0x26, (byte) 0x22, (byte) 0xE2, (byte) 0xE3, (byte) 0x23,
            (byte) 0xE1, (byte) 0x21, (byte) 0x20, (byte) 0xE0, (byte) 0xA0, (byte) 0x60,
            (byte) 0x61, (byte) 0xA1, (byte) 0x63, (byte) 0xA3, (byte) 0xA2, (byte) 0x62,
            (byte) 0x66, (byte) 0xA6, (byte) 0xA7, (byte) 0x67, (byte) 0xA5, (byte) 0x65,
            (byte) 0x64, (byte) 0xA4, (byte) 0x6C, (byte) 0xAC, (byte) 0xAD, (byte) 0x6D,
            (byte) 0xAF, (byte) 0x6F, (byte) 0x6E, (byte) 0xAE, (byte) 0xAA, (byte) 0x6A,
            (byte) 0x6B, (byte) 0xAB, (byte) 0x69, (byte) 0xA9, (byte) 0xA8, (byte) 0x68,
            (byte) 0x78, (byte) 0xB8, (byte) 0xB9, (byte) 0x79, (byte) 0xBB, (byte) 0x7B,
            (byte) 0x7A, (byte) 0xBA, (byte) 0xBE, (byte) 0x7E, (byte) 0x7F, (byte) 0xBF,
            (byte) 0x7D, (byte) 0xBD, (byte) 0xBC, (byte) 0x7C, (byte) 0xB4, (byte) 0x74,
            (byte) 0x75, (byte) 0xB5, (byte) 0x77, (byte) 0xB7, (byte) 0xB6, (byte) 0x76,
            (byte) 0x72, (byte) 0xB2, (byte) 0xB3, (byte) 0x73, (byte) 0xB1, (byte) 0x71,
            (byte) 0x70, (byte) 0xB0, (byte) 0x50, (byte) 0x90, (byte) 0x91, (byte) 0x51,
            (byte) 0x93, (byte) 0x53, (byte) 0x52, (byte) 0x92, (byte) 0x96, (byte) 0x56,
            (byte) 0x57, (byte) 0x97, (byte) 0x55, (byte) 0x95, (byte) 0x94, (byte) 0x54,
            (byte) 0x9C, (byte) 0x5C, (byte) 0x5D, (byte) 0x9D, (byte) 0x5F, (byte) 0x9F,
            (byte) 0x9E, (byte) 0x5E, (byte) 0x5A, (byte) 0x9A, (byte) 0x9B, (byte) 0x5B,
            (byte) 0x99, (byte) 0x59, (byte) 0x58, (byte) 0x98, (byte) 0x88, (byte) 0x48,
            (byte) 0x49, (byte) 0x89, (byte) 0x4B, (byte) 0x8B, (byte) 0x8A, (byte) 0x4A,
            (byte) 0x4E, (byte) 0x8E, (byte) 0x8F, (byte) 0x4F, (byte) 0x8D, (byte) 0x4D,
            (byte) 0x4C, (byte) 0x8C, (byte) 0x44, (byte) 0x84, (byte) 0x85, (byte) 0x45,
            (byte) 0x87, (byte) 0x47, (byte) 0x46, (byte) 0x86, (byte) 0x82, (byte) 0x42,
            (byte) 0x43, (byte) 0x83, (byte) 0x41, (byte) 0x81, (byte) 0x80, (byte) 0x40};


    //转化字符串为十六进制编码
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    // 转化十六进制编码为字符串
    public static String toStringHex1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    /**
     * 计算CRC16校验
     *
     * @param data 需要计算的数组
     * @return CRC16校验值
     */
    public static int calcCrc16(byte[] data) {
        return calcCrc16(data, 0, data.length);
    }

    /**
     * 计算CRC16校验
     *
     * @param data   需要计算的数组
     * @param offset 起始位置
     * @param len    长度
     * @return CRC16校验值
     */
    public static int calcCrc16(byte[] data, int offset, int len) {
        return calcCrc16(data, offset, len, 0xffff);
    }

    /**
     * 计算CRC16校验
     *
     * @param data   需要计算的数组
     * @param offset 起始位置
     * @param len    长度
     * @param preval 之前的校验值
     * @return CRC16校验值
     */
    public static int calcCrc16(byte[] data, int offset, int len, int preval) {
        int ucCRCHi = (preval & 0xff00) >> 8;
        int ucCRCLo = preval & 0x00ff;
        int iIndex;
        for (int i = 0; i < len; ++i) {
            iIndex = (ucCRCLo ^ data[offset + i]) & 0x00ff;
            ucCRCLo = ucCRCHi ^ crc16_tab_h[iIndex];
            ucCRCHi = crc16_tab_l[iIndex];
        }
        return ((ucCRCHi & 0x00ff) << 8) | (ucCRCLo & 0x00ff) & 0xffff;
    }

    /**
     * 判断字符串是否是乱码
     *
     * @param strName
     * @return
     */
    public static boolean isGarbledCode(String strName) {
        if (null == strName || 0 == strName.trim().length()) {
            return true;
        }

        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }

        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static String getMac(String mac) {
        if (TextUtils.isEmpty(mac)) {
            return "";
        }
        Log.d(TAG, "mac:" + mac);
        StringBuilder sb = new StringBuilder(mac);

        if (sb.length() > 10) {
            sb.insert(10, ":");
        }
        if (sb.length() > 8) {
            sb.insert(8, ":");
        }
        if (sb.length() > 6) {
            sb.insert(6, ":");
        }
        if (sb.length() > 4) {
            sb.insert(4, ":");
        }
        if (sb.length() > 2) {
            sb.insert(2, ":");
        }

        return sb.toString();
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 2进制数转为16进制
     *
     * @param bString
     * @return
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return "";
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    public static String get485Data(String data) {
        String resultData = "";
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        data = data.replaceAll(" ", "0");
        Log.i(TAG, "get485Data::data:" + data);
        for (int i = 0; i < data.length(); i += 2) {
            resultData += ((i / 2 + 1) + ":" + (String.valueOf(data.charAt(i)) + String.valueOf(data.charAt(i + 1))));
            resultData += " , ";
            if (i == 12) {
                resultData += "\n";
            }
        }
        return resultData;
    }

    /**
     * //设备状态 //bit0--交流电充电状态，0--正常，1--停电 //bit1--蓄电池电压状态，0--正常，1--报警
     * //bit2--水位超限报警状态，0--正常，1--报警 //bit3--流量超限报警状态，0--正常，1--报警
     * //bit4--水质超限报警状态，0--正常，1--报警 //bit5--流量仪表状态状态，0--正常，1--故障
     * //bit6--水位仪表状态状态，0--正常，1--故障 //bit7--终端箱门状态，0--开启，1--关闭
     * //bit8--存储器状态，0--正常，1--异常 //bit9--IC卡功能有效状态，0--关闭，1--IC卡有效
     * //bit10--水泵工作状态，0--水泵工作，1--水泵停机 //bit11--余水量报警，0--未超限，1--水量超限
     * //bit12--闸位仪表状态，0--正常，1--故障 //bit13--墒情仪表状态，0--正常，1--故障
     * //bit14--摄像头状态，0--正常，1--故障 //bit15--bit31保留
     *
     * @param i
     * @param status
     * @return
     */
    public static String getStatus(int i, char status) {
        String resultStatus = "";
        switch (i) {
            case 31:
                // bit0--交流电充电状态，0--正常，1--停电
                resultStatus += "交流电充电状态：";
                resultStatus += status == '0' ? "正常" : "停电";
                break;
            case 30:
                // bit1--蓄电池电压状态，0--正常，1--报警
                resultStatus += "蓄电池电压状态：";
                resultStatus += status == '0' ? "正常" : "报警";
                break;
            case 29:
                // bit2--水位超限报警状态，0--正常，1--报警
                resultStatus += "水位超限报警状态：";
                resultStatus += status == '0' ? "正常" : "报警";
                break;
            case 28:
                // bit3--流量超限报警状态，0--正常，1--报警
                resultStatus += "流量超限报警状态：";
                resultStatus += status == '0' ? "正常" : "报警";
                break;
            case 27:
                // bit4--水质超限报警状态，0--正常，1--报警
                resultStatus += "水质超限报警状态：";
                resultStatus += status == '0' ? "正常" : "报警";
                break;
            case 26:
                // bit5--流量仪表状态状态，0--正常，1--故障
                resultStatus += "流量仪表状态状态：";
                resultStatus += status == '0' ? "正常" : "故障";
                break;
            case 25:
                // bit6--水位仪表状态状态，0--正常，1--故障
                resultStatus += "水位仪表状态状态：";
                resultStatus += status == '0' ? "正常" : "故障";
                break;
            case 24:
                // bit7--终端箱门状态，0--开启，1--关闭
                resultStatus += "终端箱门状态：";
                resultStatus += status == '0' ? "开启" : "关闭";
                break;
            case 23:
                // bit8--存储器状态，0--正常，1--异常
                resultStatus += "存储器状态：";
                resultStatus += status == '0' ? "正常" : "异常";
                break;
            case 22:
                // bit9--IC卡功能有效状态，0--关闭，1--IC卡有效
                resultStatus += "IC卡功能有效状态：";
                resultStatus += status == '0' ? "关闭" : "IC卡有效";
                break;
            case 21:
                // bit10--水泵工作状态，0--水泵工作，1--水泵停机
                resultStatus += "水泵工作状态：";
                resultStatus += status == '0' ? "水泵工作" : "水泵停机";
                break;
            case 20:
                // bit11--余水量报警，0--未超限，1--水量超限
                resultStatus += "余水量报警：";
                resultStatus += status == '0' ? "未超限" : "水量超限";
                break;
            case 19:
                // bit12--闸位仪表状态，0--正常，1--故障
                resultStatus += "闸位仪表状态：";
                resultStatus += status == '0' ? "正常" : "故障";

                break;
            case 18:
                // bit13--墒情仪表状态，0--正常，1--故障
                resultStatus += "墒情仪表状态：";
                resultStatus += status == '0' ? "正常" : "故障";
                break;
            case 17:
                // bit14--摄像头状态，0--正常，1--故障
                resultStatus += "摄像头状态：";
                resultStatus += status == '0' ? "正常" : "故障";
                break;

            default:
                break;
        }
        resultStatus += "\n";
        return resultStatus;
    }

    public static void getSingleStatus(StringBuffer sb, int i, char status, Context context) {
        String resultStatus = "";
        String statusNum = "";
        normal = context.getString(R.string.normal);
        power_failure = context.getString(R.string.power_failure);
        Call_police = context.getString(R.string.Call_police);
        malfunction = context.getString(R.string.malfunction);
        Open = context.getString(R.string.Open);
        Close = context.getString(R.string.Close);
        abnormal = context.getString(R.string.abnormal);
        IC_card_valid = context.getString(R.string.IC_card_valid);
        Pump_work = context.getString(R.string.Pump_work);
        Pump_down = context.getString(R.string.Pump_down);
        Not_overrun = context.getString(R.string.Not_overrun);
        Water_limit = context.getString(R.string.Reset);
        switch (i) {
            case 31:
                // bit0--交流电充电状态，0--正常，1--停电
                resultStatus = status == '0' ? normal : power_failure;
                statusNum = SearchFragment.STATUS1;
                break;
            case 30:
                // bit1--蓄电池电压状态，0--正常，1--报警
                resultStatus = status == '0' ? normal : Call_police;
                statusNum = SearchFragment.STATUS2;
                break;
            case 29:
                // bit2--水位超限报警状态，0--正常，1--报警
                resultStatus = status == '0' ? normal : Call_police;
                statusNum = SearchFragment.STATUS3;
                break;
            case 28:
                // bit3--流量超限报警状态，0--正常，1--报警
                resultStatus = status == '0' ? normal : Call_police;
                statusNum = SearchFragment.STATUS4;
                break;
            case 27:
                // bit4--水质超限报警状态，0--正常，1--报警
                resultStatus = status == '0' ? normal : Call_police;
                statusNum = SearchFragment.STATUS5;
                break;
            case 26:
                // bit5--流量仪表状态状态，0--正常，1--故障
                resultStatus = status == '0' ? normal : malfunction;
                statusNum = SearchFragment.STATUS6;
                break;
            case 25:
                // bit6--水位仪表状态状态，0--正常，1--故障
                resultStatus = status == '0' ? normal : malfunction;
                statusNum = SearchFragment.STATUS7;
                break;
            case 24:
                // bit7--终端箱门状态，0--开启，1--关闭
                resultStatus = status == '0' ? Open : Close;
                statusNum = SearchFragment.STATUS8;
                break;
            case 23:
                // bit8--存储器状态，0--正常，1--异常
                resultStatus = status == '0' ? normal : abnormal;
                statusNum = SearchFragment.STATUS9;
                break;
            case 22:
                // bit9--IC卡功能有效状态，0--关闭，1--IC卡有效
                resultStatus = status == '0' ? Close : IC_card_valid;
                statusNum = SearchFragment.STATUS10;
                break;
            case 21:
                // bit10--水泵工作状态，0--水泵工作，1--水泵停机
                resultStatus = status == '0' ? Pump_work : Pump_down;
                statusNum = SearchFragment.STATUS11;
                break;
            case 20:
                // bit11--余水量报警，0--未超限，1--水量超限
                resultStatus = status == '0' ? Not_overrun : Water_limit;
                statusNum = SearchFragment.STATUS12;
                break;
            case 19:
                // bit12--闸位仪表状态，0--正常，1--故障
                resultStatus = status == '0' ? normal : malfunction;
                statusNum = SearchFragment.STATUS13;

                break;
            case 18:
                // bit13--墒情仪表状态，0--正常，1--故障
                resultStatus = status == '0' ? normal : malfunction;
                statusNum = SearchFragment.STATUS14;
                break;
            case 17:
                // bit14--摄像头状态，0--正常，1--故障
                resultStatus = status == '0' ? normal : malfunction;
                statusNum = SearchFragment.STATUS15;
                break;

            default:
                break;
        }
        sb.insert(sb.indexOf(statusNum) + statusNum.length(), resultStatus);
    }

    /**
     * 根据32位的十六进制数，获取设备状态
     *
     * @param hexStatus
     * @return
     */
    public static String getEquipmentStatus(String hexStatus) {


        Log.i(TAG, "getEquipmentStatus::hexStatus:" + hexStatus);
        String resultStatus = "";
        if (TextUtils.isEmpty(hexStatus)) {
            return resultStatus;
        }

        for (int i = hexStatus.length() - 1; i > 16; i--) {
            resultStatus += getStatus(i, hexStatus.charAt(i));
        }

        return resultStatus;
    }

    /**
     * 16进制数转为2进制
     *
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        String hex = getHex(hexString);
        if (hex == null || hex.length() % 2 != 0)
            return "";
        String bString = "", tmp;
        for (int i = 0; i < hex.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hex.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * 把远程发过来的字符转为16进制数
     *
     * @param equipmentStatus
     * @return
     */
    public static String getHex(String equipmentStatus) {
        if (equipmentStatus.length() % 2 != 0) {
            return "0" + equipmentStatus;
        } else {
            return equipmentStatus;
        }

    }

    public static void requestEdittext(final EditText edittext) {

        if (edittext != null) {
            edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        EditText etxt = (EditText) v;
                        edittext.setText(etxt.getText().toString());
                    }
                }
            });
        }
    }

    /**
     * 根据传入数字进行补零操作
     *
     * @param number
     * @param count
     * @return
     */
    public static String getStr(String number, int count) {
        String ss = "";
        if (number.length() < count) {
            for (int i = 0; i < count - number.length(); i++) {
                ss += "0";
            }
        }
        return ss + number;
    }

    /**
     * 传入的数字保留两位小数
     *
     * @param number
     * @return
     */
    public static String getDouble(String number) {
        Log.i(TAG, "number:" + number);
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        String temp = "";
        if (isNumeric(number)) {
            double level = Double.parseDouble(number) / 100.0;
            Log.i(TAG, "level:" + level + ",data:" + number);
            temp = String.valueOf(level);
//			if (number.length() <= 2)
//			{
//				temp = "0." + number;
//			}
//			else
//			{
//				StringBuilder sb = new StringBuilder(number);
//				sb.insert(number.length() - 2, ".");
//				temp = sb.toString();
//			}
        }
        return temp;
    }


    public static String getDouble_1(String number) {
        Log.i(TAG, "number:" + number);
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        String temp = "";
        if (isNumeric(number)) {
            double level = Double.parseDouble(number) / 1000.0;
            Log.i(TAG, "level:" + level + ",data:" + number);
            temp = String.valueOf(level);
//			if (number.length() <= 2)
//			{
//				temp = "0." + number;
//			}
//			else
//			{
//				StringBuilder sb = new StringBuilder(number);
//				sb.insert(number.length() - 2, ".");
//				temp = sb.toString();
//			}
        }
        return temp;
    }

    /**
     * 传入的数字保留两位小数
     *
     * @param number
     * @return
     */
    public static String getDouble2(String number) {
        Log.i(TAG, "number:" + number);
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        String temp = "";
        if (isNumeric(number)) {
            double level = Double.parseDouble(number);

            DecimalFormat df = new DecimalFormat("0.00");
//            String CNY = df.format(level);
            Log.i(TAG, "level:" + level + ",data:" + number);
//            temp = String.valueOf(level);
            temp = df.format(level);
//			if (number.length() <= 2)
//			{
//				temp =  number + ".00";
//			}
//			else
//			{
//				StringBuilder sb = new StringBuilder(number);
//				sb.insert(number.length() - 2, ".");
//				temp = sb.toString();
//			}
        }
        return temp;
    }

    /**
     * 目标字符串向后补零
     *
     * @param number 目标数字
     * @param count  总伟数
     * @return
     */
    public static String getStrAfter(String number, int count) {
        String ss = "";
        if (number.length() < count) {
            for (int i = 0; i < count - number.length(); i++) {
                ss += "0";
            }
        }
        return number + ss;
    }

    /**
     * ip地址补零操作
     *
     * @param ip
     * @return
     */
    public static String getRegxIp(String ip)// ggg.com
    {
        String ss = "";
        if (TextUtils.isEmpty(ip)) {
            return "";
        }
        String[] temp = ip.split("\\.");// ggg com
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].length() < 3 && isNumeric(temp[i])) {
                ss += getStr(temp[i], 3);
            } else {
                ss += temp[i];
            }
            ss += ".";
        }
        if (TextUtils.isEmpty(ss)) {
            return "";
        }
        return ss.substring(0, ss.length() - 1);
    }

    /**
     * IP地址去零
     *
     * @param ip
     * @return
     */
    public static String getRemoteIp(String ip)// ggg.com
    {
        String ss = "";
        if (TextUtils.isEmpty(ip)) {
            return "";
        }
        String[] temp = ip.split("\\.");// ggg com
        for (int i = 0; i < temp.length; i++) {
            if (isNumeric(temp[i])) {
                ss += Integer.parseInt(temp[i]) + "";
            } else {
                ss += temp[i];
            }
            ss += ".";
        }
        if (TextUtils.isEmpty(ss)) {
            return "";
        }
        return ss.substring(0, ss.length() - 1);
    }

    public static int getTimePos(int time) {
        int result = 0;
        switch (time) {
            case 61:
                result = 0;
                break;
            case 62:
                result = 1;
                break;
            case 5:
                result = 2;
                break;
            case 10:
                result = 3;
                break;
            case 15:
                result = 4;
                break;
            case 30:
                result = 5;
                break;
            case 1:
                result = 6;
                break;
            case 02:
                result = 7;
                break;
            case 03:
                result = 8;
                break;
            case 04:
                result = 9;
                break;
            case 06:
                result = 10;
                break;
            case 8:
                result = 11;
                break;
            case 12:
                result = 12;
                break;
            case 24:
                result = 13;
                break;

            default:
                break;
        }
        return result;
    }

    public static int getpicTimePos(int time) {
        int result = 0;
        switch (time) {
            case 5:
                result = 0;
                break;
            case 10:
                result = 1;
                break;
            case 15:
                result = 2;
                break;
            case 30:
                result = 3;
                break;
            case 1:
                result = 4;
                break;
            case 02:
                result = 5;
                break;
            case 03:
                result = 6;
                break;
            case 04:
                result = 7;
                break;
            case 06:
                result = 8;
                break;
            case 8:
                result = 9;
                break;
            case 12:
                result = 10;
                break;
            case 24:
                result = 11;
                break;

            default:
                break;
        }
        return result;
    }

    public static String getFunTimePos(int time) {
        String result = "";
        switch (time) {
            case 0:
                result = "5分钟";
                break;
            case 1:
                result = "10分钟";
                break;
            case 2:
                result = "15分钟";
                break;
            case 3:
                result = "30分钟";
                break;
            case 4:
                result = "1小时";
                break;
            case 5:
                result = "2小时";
                break;
            case 6:
                result = "3小时";
                break;
            case 7:
                result = "4小时";
                break;
            case 8:
                result = "6小时";
                break;
            case 9:
                result = "8小时";
                break;
            case 10:
                result = "12小时";
                break;
            case 11:
                result = "24小时";
                break;

            default:
                break;
        }
        return result;
    }

    public static String getFunCustomerPos(int time) {
        String result = "";
        switch (time) {
            case 0:
                result = "威控科技";
                break;

            default:
                result = "威控科技";
                break;
        }
        return result;
    }

    public static String getFunTFPos(int time) {
        String result = "";
        switch (time) {
            case 0:
                result = "5s";
                break;
            case 1:
                result = "10s";
                break;
            case 2:
                result = "15s";
                break;
            case 3:
                result = "20s";
                break;
            case 4:
                result = "25s";
                break;
            case 5:
                result = "30s";
                break;

            default:
                result = "5s";
                break;
        }
        return result;
    }

    public static String getTimeNum(String time) {
        String result = "";
        switch (time) {
            case "1分钟":
                result = "61";
                break;
            case "2分钟":
                result = "62";
                break;
            case "5分钟":
                result = "05";
                break;
            case "10分钟":
                result = "10";
                break;
            case "15分钟":
                result = "15";
                break;
            case "30分钟":
                result = "30";
                break;
            case "1小时":
                result = "01";
                break;
            case "2小时":
                result = "02";
                break;
            case "3小时":
                result = "03";
                break;
            case "4小时":
                result = "04";
                break;
            case "6小时":
                result = "06";
                break;
            case "8小时":
                result = "08";
                break;
            case "12小时":
                result = "12";
                break;
            case "24小时":
                result = "24";
                break;

            default:
                break;
        }
        return result;
    }

    /**
     * 获取GPRS状态
     * 0为复位，1为设备错误，2为信号强度低，3为无SIM卡，4为没有注册到网络，5为信号强度正常，6为休眠，7为关机，8为注册到网络，
     * 9为PPP已连接，10为PPP失败。
     *
     * @param status
     * @return
     */
    public static String getGPRSStatus(String status, Context context) {
        String result = "";
        Reset = context.getString(R.string.Reset);
        Device_error = context.getString(R.string.Device_error);
        Low_signal_strength = context.getString(R.string.Low_signal_strength);
        No_SIM_card = context.getString(R.string.No_SIM_card);
        Not_registered_network = context.getString(R.string.Not_registered_network);
        Signal_strength_normal = context.getString(R.string.Signal_strength_normal);
        Sleep = context.getString(R.string.Sleep);
        Shut_down = context.getString(R.string.Shut_down);
        Register_network = context.getString(R.string.Register_network);
        PPP_connected = context.getString(R.string.PPP_connected);
        PPP_failed = context.getString(R.string.PPP_failed);
        switch (status) {
            case "0":
                result = Reset;
                break;
            case "1":
                result = Device_error;// "设备错误";
                break;
            case "2":
                result = Low_signal_strength;// "信号强度低";
                break;
            case "3":
                result = No_SIM_card;// "无SIM卡";
                break;
            case "4":
                result = Not_registered_network;// "没有注册到网络";
                break;
            case "5":
                result = Signal_strength_normal;//"信号强度正常";
                break;
            case "6":
                result = Sleep;//"休眠";
                break;
            case "7":
                result = Shut_down;//"关机";
                break;
            case "8":
                result = Register_network;//"注册到网络";
                break;
            case "9":
                result = PPP_connected;//"PPP已连接";
                break;
            case "10":
                result = PPP_failed;//"PPP失败";
                break;

            default:
                break;
        }

        return result;

    }

    /**
     * 获取Socket状态 1为设置服务类型，2为设置服务器地址和端口号，3为设置连接的ID，4 为打开连接，5 为检查连接状态，6
     * 为等待数据，7为读取接收数据，8为发送数据头，9为发送数据内容，10为关闭连接，11为任务关闭。
     *
     * @param status
     * @return
     */
    public static String getSocketStatus(String status, Context context) {
        Log.i(TAG, "status:" + status);
        String result = "";
        Set_service_type = context.getString(R.string.Set_service_type);
        Set_server_address_port_number = context.getString(R.string.Set_server_address_port_number);
        Set_ID_connection = context.getString(R.string.Set_ID_connection);
        Open_connection = context.getString(R.string.Open_connection);
        Check_connection_status = context.getString(R.string.Check_connection_status);
        Waiting_for_data = context.getString(R.string.Waiting_for_data);
        Read_received_data = context.getString(R.string.Read_received_data);
        Send_data_head = context.getString(R.string.Send_data_head);
        Send_data_content = context.getString(R.string.Send_data_content);
        Close_connection = context.getString(R.string.Close_connection);
        Task_off = context.getString(R.string.Task_off);
        status1 = context.getString(R.string.status);
        switch (status) {
            case "1":
                result = Set_service_type;//"设置服务类型";
                break;
            case "2":
                result = Set_server_address_port_number;//"设置服务器地址和端口号";
                break;
            case "3":
                result = Set_ID_connection;//"设置连接的ID";
                break;
            case "4":
                result = Open_connection;//"打开连接";
                break;
            case "5":
                result = Check_connection_status;//"检查连接状态";
                break;
            case "6":
                result = Waiting_for_data;//"等待数据";
                break;
            case "7":
                result = Read_received_data;//"读取接收数据";
                break;
            case "8":
                result = Send_data_head;//"发送数据头";
                break;
            case "9":
                result = Send_data_content;//"发送数据内容";
                break;
            case "10":
                result = Close_connection;//"关闭连接";
                break;
            case "11":
                result = Task_off;//"任务关闭";
                break;

            default:
                result = status1 + status;//"状态：" + status;
                break;
        }

        return result;

    }

    /**
     * 获取GSM状态 0为等待数据，1为读取短信条数，2为读取短信，3为发送短信头，4为发送短信内容，5为删除短信。
     *
     * @param status
     * @return
     */
    public static String getGSMStatus(String status, Context context) {
        String result = "";
        Waiting_for_data = context.getString(R.string.Waiting_for_data);
        Read_number_messages = context.getString(R.string.Read_number_messages);
        Read_text_messages = context.getString(R.string.Read_text_messages);
        Send_message_header = context.getString(R.string.Send_message_header);
        Send_SMS_content = context.getString(R.string.Send_SMS_content);
        Delete_text_messages = context.getString(R.string.Delete_text_messages);
        status1 = context.getString(R.string.status);
        switch (status) {
            case "0":
                result = Waiting_for_data;//"等待数据";
                break;
            case "1":
                result = Read_number_messages;//"读取短信条数";
                break;
            case "2":
                result = Read_text_messages;//"读取短信";
                break;
            case "3":
                result = Send_message_header;//"发送短信头";
                break;
            case "4":
                result = Send_SMS_content;//"发送短信内容";
                break;
            case "5":
                result = Delete_text_messages;//"删除短信";
                break;

            default:
                result = status1 + status;//"状态：" + status;
                break;
        }

        return result;

    }

    /**
     * 通讯时间间隔
     *
     * @param time
     * @return
     */
    public static String getCommTime(String time) {
        String result = "";
        switch (time) {
            case "0":
                result = "5分钟";
                break;
            case "1":
                result = "10分钟";
                break;
            case "2":
                result = "30分钟";
                break;
            case "3":
                result = "1小时";
                break;
            case "4":
                result = "2小时";
                break;
            case "5":
                result = "4小时";
                break;

            default:
                result = "5分钟";
                break;
        }

        return result;

    }

    /**
     * 摄像头读取状态 0没有读取，1正在读取图片，2读取成功，3读取失败
     *
     * @param status
     * @return
     */
    public static String getCameraReadStatus(String status, Context context) {
        String result = "";
        Not_read = context.getString(R.string.Not_read);
        Reading_picture = context.getString(R.string.Reading_picture);
        Read_success = context.getString(R.string.Read_success);
        Reading_failed = context.getString(R.string.Reading_failed);
        switch (status) {
            case "0":
                result = Not_read;//"没有读取";
                break;
            case "1":
                result = Reading_picture;//"正在读取图片";
                break;
            case "2":
                result = Read_success;//"读取成功";
                break;
            case "3":
                result = Reading_failed;//"读取失败";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 服务器发送状态 0没有发送，1正在发送，2发送成功，3发送失败
     *
     * @param status
     * @return
     */
    public static String getImgSendStatus(String status, Context context) {
        String result = "";

        Did_not_send = context.getString(R.string.Did_not_send);
        sending = context.getString(R.string.sending);
        Sent_successfully = context.getString(R.string.Sent_successfully);
        Failed_send = context.getString(R.string.Failed_send);
        switch (status) {
            case "0":
                result = Did_not_send;//"没有发送";
                break;
            case "1":
                result = sending;//"正在发送";
                break;
            case "2":
                result = Sent_successfully;//"发送成功";
                break;
            case "3":
                result = Failed_send;//"发送失败";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 解析获取系统参数
     *
     * @param name
     * @return
     */
    public static String getSysType(String name) {
        String result = "";
        switch (name) {
            case ConfigParams.SetAddr:
                result = "遥测站地址：";
                break;
            case ConfigParams.SetWorkMode:
                result = "运行状态：";
                break;
            case ConfigParams.SetScadaFactor:
                result = "采集要素：";
                break;
            case ConfigParams.SetStationMode:
                result = "站点类型：";
                break;
            default:
                break;
        }

        return result;
    }

    /**
     * 解析获取系统参数
     *
     * @param name
     * @return
     */
    public static String getAddrType(String name) {
        String result = "";
        switch (name) {
            case "0":
                result = "降水站";
                break;
            case "1":
                result = "河道站";
                break;
            case "2":
                result = "水库战";
                break;
            case "3":
                result = "闸坝站";
                break;
            case "4":
                result = "泵站";
                break;
            case "5":
                result = "墒情站";
                break;
            case "6":
                result = "地下水站";
                break;
            default:
                result = "无";
                break;
        }
        return result;
    }

    /**
     * 解析获取系统参数
     *
     * @param name
     * @return
     */
    public static String getSensorType(String name) {
        String result = "";
        switch (name) {
            case ConfigParams.SetRainMeterPara:
                result = "雨量分辨力：";
                break;
            case ConfigParams.SetRainFlowChangeMax:
                result = "雨量加报阈值：";
                break;
            case ConfigParams.SetWaterMeterPara:
                result = "水位分辨力：";
                break;
            case ConfigParams.SetWaterLeverMax:
                result = "加报水位：";
                break;
            case ConfigParams.SetWaterLeverChangeUP:
                result = "加报水位上加报阈值：";
                break;
            case ConfigParams.SetWaterLeverChangeDW:
                result = "加报水位下加报阈值：";
                break;
            case ConfigParams.SetAddReportInterval:
                result = "加报时间间隔：";
                break;
            case ConfigParams.SetAnaWaterCal:
                result = "水位修正值：";
                break;
            case ConfigParams.SetAnaWaterBac:
                result = "水位基值：";
                break;
            case ConfigParams.SetAnaWaterType:
                result = "模拟量水位计选择 ：";
                break;
            case ConfigParams.SetWaterType:
                result = "水位计类型：";
                break;
            case ConfigParams.SetWater485Type:
                result = "485水位计选择：";
                break;
            case ConfigParams.SetCameraType:
                result = "摄像头类型：";
                break;
            case ConfigParams.SetPIC_Resolution:
                result = "摄像头分辨率：";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 解析通信参数
     *
     * @param name
     * @return
     */
    public static String getCommType(String name) {
        String result = "";
        switch (name) {
            case ConfigParams.SetPacketInterval:
                result = "定时报间隔：";
                break;
            case ConfigParams.SetAcquisitionMode:
                result = "召测模式：";
                break;
            case ConfigParams.SetCenterType:
                result = "中心站主信道：";
                break;
            case ConfigParams.SetReserveType:
                result = "备用信道：";
                break;
            case ConfigParams.SetIP:
                result = "IP地址：";
                break;
            case ConfigParams.setPort:
                result = "端口：";
                break;
            case ConfigParams.SetSMS:
                result = "短信号码：";
                break;
            case ConfigParams.SetBeiDou:
                result = "北斗号码：";
                break;

            default:
                break;
        }
        return result;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 发送数据的方法
     *
     * @param content
     */
    public static void sendData(String content) {
        Log.d(TAG,"sendData::content:" + content + "，mode：" + SettingUtil.getSetMode());
        if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_BLE) {
            BleUtils.getInstance().sendData(content.getBytes());
        } else {
            SocketUtil.getSocketUtil().sendContent(content);
        }
    }

}
