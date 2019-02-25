package com.heroan.operation;

import android.os.Handler;

import zuo.biao.library.base.BaseApplication;

public class OperationApplication extends BaseApplication {

    public static volatile Handler applicationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.init(this);
        applicationHandler = new Handler(this.getMainLooper());
    }
}
