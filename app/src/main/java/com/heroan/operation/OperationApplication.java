package com.heroan.operation;

import android.os.Handler;

import cn.jpush.android.api.JPushInterface;
import zuo.biao.library.base.BaseApplication;

public class OperationApplication extends BaseApplication {

    public static volatile Handler applicationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.init(this);
        applicationHandler = new Handler(this.getMainLooper());

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }
}
