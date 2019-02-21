package com.heroan.operation.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;

import zuo.biao.library.util.ScreenUtil;

public class ToastUtil {
    private static Toast mToast;
    private static Toast shortToast = null;


    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showCustomize(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    public static void showToastLong(final String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        OperationApplication.applicationHandler.post(new Runnable() {

            @Override
            public void run() {
                cancelToast();
                LayoutInflater inflater = LayoutInflater.from(OperationApplication.getInstance());
                View layout = inflater.inflate(R.layout.toast, null);
                ((TextView) layout.findViewById(R.id.toast_textview)).setText(content);
                mToast = Toast.makeText(OperationApplication.getInstance(), "", Toast.LENGTH_LONG);
                //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
                mToast.setGravity(Gravity.TOP, 0, ScreenUtil.getScreenHeight(OperationApplication.getInstance()) / 2);
                mToast.setView(layout);
                mToast.show();
            }
        });

    }

    public static void showToastShort(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        cancelToast();
        mToast = Toast.makeText(OperationApplication.getInstance(), content, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void makeShortText(String msg, Context context) {
        if (context == null) {
            return;
        }

        if (shortToast == null) {
            shortToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            shortToast.setText(msg);
        }
        shortToast.show();
    }


    private static Toast longToast = null;

    public static void makeLongText(String msg, Context context) {
        if (context == null) {
            return;
        }

        if (longToast == null) {
            longToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            longToast.setText(msg);
        }
        longToast.show();
    }


    public static void showLong(Context context, String msg) {
        makeLongText(msg, context);
    }

    public static void showLong(Context context, int id) {
        makeLongText(context.getResources().getString(id), context);
    }

    public static void showShort(Context context, String msg) {
        makeShortText(msg, context);
    }

    public static void showShort(Context context, int id) {
        makeShortText(context.getResources().getString(id), context);
    }


    public static void showCenterToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
