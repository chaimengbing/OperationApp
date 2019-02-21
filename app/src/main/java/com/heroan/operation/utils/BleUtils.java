package com.heroan.operation.utils;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;


import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;

import java.util.List;
import java.util.UUID;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.BleDevice;
import cn.com.heaton.blelibrary.ble.callback.BleConnCallback;
import cn.com.heaton.blelibrary.ble.callback.BleNotiftCallback;
import cn.com.heaton.blelibrary.ble.callback.BleReadCallback;
import cn.com.heaton.blelibrary.ble.callback.BleScanCallback;
import cn.com.heaton.blelibrary.ble.callback.BleWriteCallback;

public class BleUtils {

    private static String TAG = "BleUtils";


    private static BleUtils bleUtils = null;

    private static Ble<BleDevice> mBle = null;


    private BleUtils() {
        initBle();
    }


    public static BleUtils getInstance() {
        if (bleUtils == null) {
            bleUtils = new BleUtils();
        }
        return bleUtils;
    }


    private Ble<BleDevice> initBle() {
        if (mBle == null) {
            mBle = Ble.getInstance();
            Ble.Options options = new Ble.Options();
            options.logBleExceptions = true;//设置是否输出打印蓝牙日志
            options.throwBleException = true;//设置是否抛出蓝牙异常
            options.autoConnect = false;//设置是否自动连接
            options.scanPeriod = 12 * 1000;//设置扫描时长
            options.connectTimeout = 10 * 1000;//设置连接超时时长
            options.uuid_service = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");//设置主服务的uuid
            //options.uuid_services_extra = new UUID[]{UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")};//添加额外的服务（如电量服务，心跳服务等）
            options.uuid_write_cha = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");//设置可写特征的uuid
            options.uuid_read_cha = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");//设置可读特征的uuid
            //ota相关 修改为你们自己的
        /*  options.uuid_ota_service = UUID.fromString("0000fee8-0000-1000-8000-00805f9b34fb");
            options.uuid_ota_notify_cha = UUID.fromString("003784cf-f7e3-55b4-6c4c-9fd140100a16");
            options.uuid_ota_write_cha = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");*/
            mBle.init(OperationApplication.getInstance(), options);
        }
        return mBle;
    }


    public boolean isScanning() {
        if (mBle != null) {
            return mBle.isScanning();
        }
        return false;
    }

    public void stopScan() {
        if (mBle != null) {
            mBle.stopScan();
        }
    }

    public boolean isBleEnable() {
        if (mBle != null) {
            return mBle.isBleEnable();
        }
        return false;
    }

    public void connectDevice(BleDevice device) {
        if (mBle != null) {
            mBle.connect(device, connectCallback);
        } else {
            EventNotifyHelper.getInstance().postNotification(UiEventEntry.NOTIFY_BLE_CONNECT_FAIL);
        }
    }


    /*设置通知的回调*/
    private BleNotiftCallback<BleDevice> bleNotiftCallback = new BleNotiftCallback<BleDevice>() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onChanged(BleDevice device, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            String result1 = new String(data);
            ToastUtil.showLong(OperationApplication.getInstance(), "connectNotify: " + result1);
            notifyData(result1);
        }
    };


    /*连接的回调*/
    private BleConnCallback<BleDevice> connectCallback = new BleConnCallback<BleDevice>() {
        @Override
        public void onConnectionChanged(final BleDevice device) {
            if (device.isConnected()) {
                /*连接成功后，设置通知*/
                mBle.startNotify(device, bleNotiftCallback);
            }
            EventNotifyHelper.getInstance().postNotification(UiEventEntry.NOTIFY_BLE_CONNECT_SUCCESS, device);
            Log.e(TAG, "onConnectionChanged: " + device.isConnected());
        }

        @Override
        public void onConnectException(BleDevice device, int errorCode) {
            super.onConnectException(device, errorCode);
            EventNotifyHelper.getInstance().postNotification(UiEventEntry.NOTIFY_BLE_CONNECT_FAIL);
//            ToastUtil.showLong(getApplicationContext(), "连接异常，异常状态码:" + errorCode);
        }
    };


    /**
     * 发送数据
     */
    public void sendData(final BleDevice device, final byte[] data) {
        if (data == null) {
            ToastUtil.showLong(OperationApplication.getInstance(), "发送数据为空");
            return;
        }
        if (device == null) {
            ToastUtil.showLong(OperationApplication.getInstance(), "请选择设备");
            return;
        }

        if (mBle != null) {
            synchronized (mBle.getLocker()) {
                List<BleDevice> list = mBle.getConnetedDevices();
                if (list != null) {
                    for (BleDevice device1 : list) {
                        writeData(device1, data);
                    }
                }
            }

        }

    }

    private void writeData(final BleDevice device, final byte[] data) {
        boolean result = mBle.write(device, data,
                new BleWriteCallback<BleDevice>() {
                    @Override
                    public void onWriteSuccess(BluetoothGattCharacteristic characteristic) {
                        ToastUtil.showLong(OperationApplication.getInstance(), "发送数据成功");
                    }
                });
        if (!result) {
            ToastUtil.showLong(OperationApplication.getInstance(), "发送数据失败!");
        }
    }


    /**
     * 主动读取数据
     */
    public void readData(BleDevice device) {
        if (mBle != null) {
            synchronized (mBle.getLocker()) {
                List<BleDevice> list = mBle.getConnetedDevices();
                if (list != null) {
                    for (BleDevice device1 : list) {
                        redData(device1);
                    }
                }
            }
        }

    }

    private void redData(BleDevice device) {
        boolean result = mBle.read(device, new BleReadCallback<BleDevice>() {
            @Override
            public void onReadSuccess(BluetoothGattCharacteristic characteristic) {
                super.onReadSuccess(characteristic);
                byte[] data = characteristic.getValue();
                String result1 = new String(data);
                ToastUtil.showLong(OperationApplication.getInstance(), "onReadSuccess: " + result1);
                if (TextUtils.isEmpty(result1)) {
                    return;
                }
                notifyData(result1);

            }
        });
        if (!result) {
            Log.d(TAG, "读取数据失败!");
        }
    }


    private void notifyData(String data) {
        String[] res = data.split("\r\n");
        for (String result : res) {
            if (result != null && result.toUpperCase().contains("OK")) {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_RESULT_OK, result);
            } else if (result != null && result.toUpperCase().contains("ERROR")) {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_RESULT_ERROR, result);
            } else if (result != null && result.contains("Not Started")) {// System Not Started
                ToastUtil.showToastLong(OperationApplication.getInstance().getString(R.string.Device_again_later));
            } else {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_DATA, result);
            }
        }
    }

    /**
     * 重新扫描
     */
    public void startScan() {
        if (mBle != null) {
            mBle.startScan(scanCallback);
        }
    }


    BleScanCallback<BleDevice> scanCallback = new BleScanCallback<BleDevice>() {
        @Override
        public void onLeScan(final BleDevice device, int rssi, byte[] scanRecord) {
            if (mBle != null) {
                synchronized (mBle.getLocker()) {
                    EventNotifyHelper.getInstance().postNotification(UiEventEntry.NOTIFY_BLE_SCAN_SUCCESS, device);
                }
            }
        }

        @Override
        public void onStop() {
            super.onStop();
            EventNotifyHelper.getInstance().postNotification(UiEventEntry.NOTIFY_BLE_CONNECT_STOP);
        }
    };


}
