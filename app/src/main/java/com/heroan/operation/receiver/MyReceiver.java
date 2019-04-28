package com.heroan.operation.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.heroan.operation.activity.DisplayMessageActivity;
import com.heroan.operation.activity.MessageActivity;
import com.heroan.operation.manager.SQLHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private SQLHelper sqlHelper;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Log.d(TAG,
                "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));


        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG,
                    "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            processCustomMessage(context, bundle);


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知:" + bundle.getString(JPushInterface.EXTRA_ALERT));
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//            mContentBean.setN_content(bundle.getString(JPushInterface.EXTRA_ALERT));
//            Log.e("mContentBean-Content",mContentBean.getN_content());
            handNotify(bundle, false, context);

            //打开数据库弹窗，手动选择是否保存
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            handNotify(bundle, true, context);


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG,
                    "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE,
                    false);
            Log.w(TAG,
                    "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void handNotify(Bundle bundle, boolean isOpen, Context context) {
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String type;
        if (!TextUtils.isEmpty(title)) {
            if (title.startsWith("报警")) {
                type = "1";
            } else if (title.startsWith("工单")) {
                type = "2";
            } else {
                type = "3";
            }
        } else {
            title = "通知";
            type = "1";
        }
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm");
        String time = formatter.format(curDate);

        //自动保存至数据库
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.COLUMN_CONTENT, content);
        contentValues.put(SQLHelper.COLUMN_TIME, time);
        contentValues.put(SQLHelper.COLUMN_TITLE, title);
        contentValues.put(SQLHelper.COLUMN_TYPE, type);

        if (isOpen) {
            //打开自定义的Activity
            Intent i = new Intent(context, DisplayMessageActivity.class);
            i.putExtra("contentValues", contentValues);
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            if (sqlHelper == null) {
                sqlHelper = new SQLHelper(context);
            }
            sqlHelper.insert(contentValues);
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
