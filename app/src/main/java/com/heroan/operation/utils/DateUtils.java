package com.heroan.operation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    public static boolean isAfter(Date date, String endTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//年-月-日 时-分
        boolean isAfter = false;
        try {
            Date date2 = dateFormat.parse(endTime);//结束时间
            isAfter = date.after(date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  isAfter;
    }

    //两个时间戳是否是同一天 时间戳是long型的（11或者13）
    public static boolean isSameData(long currentTime, long lastTime) {
        try {
            Calendar nowCal = Calendar.getInstance();
            Calendar dataCal = Calendar.getInstance();
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            String data1 = df1.format(currentTime);
            String data2 = df2.format(lastTime);
            java.util.Date now = df1.parse(data1);
            java.util.Date date = df2.parse(data2);
            nowCal.setTime(now);
            dataCal.setTime(date);
            return isSameDay(nowCal, dataCal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } else {
            return false;
        }
    }
}
