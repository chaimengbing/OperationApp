package com.heroan.operation.utils;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import cn.com.heaton.blelibrary.ble.callback.BleWriteEntityCallback;

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
            options.uuid_service = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
            //设置主服务的uuid
            //options.uuid_services_extra = new UUID[]{UUID.fromString
            // ("0000180f-0000-1000-8000-00805f9b34fb")};//添加额外的服务（如电量服务，心跳服务等）
            options.uuid_write_cha = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");
            //设置可写特征的uuid
            options.uuid_read_cha = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");
            //设置可读特征的uuid
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

    public boolean isConnected() {
        boolean result = false;
        if (mBle != null) {
            synchronized (mBle.getLocker()) {
                List<BleDevice> list = mBle.getConnetedDevices();
                if (list != null) {
                    for (BleDevice device1 : list) {
                        if (device1.isConnected()) {
                            result = device1.isConnected();
                            break;
                        }
                    }
                }
            }
        }
        return result;
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

    public void disConnectDevice() {
        if (mBle != null) {
            synchronized (mBle.getLocker()) {
                List<BleDevice> list = mBle.getConnetedDevices();
                if (list != null) {
                    for (BleDevice device1 : list) {
                        mBle.disconnect(device1);
                    }
                }
            }
        }
    }


    /*设置通知的回调*/
    private BleNotiftCallback<BleDevice> bleNotiftCallback = new BleNotiftCallback<BleDevice>() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onChanged(BleDevice device, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            String result = new String(data);
            Log.d(TAG, "result:" + result);
            notifyData(result);
        }
    };


    /*连接的回调*/
    private BleConnCallback<BleDevice> connectCallback = new BleConnCallback<BleDevice>() {
        @Override
        public void onConnectionChanged(final BleDevice device) {
            if (device.isConnected()) {
                /*连接成功后，设置通知*/
                mBle.startNotify(device, bleNotiftCallback);
                EventNotifyHelper.getInstance().postNotification(UiEventEntry.NOTIFY_BLE_CONNECT_SUCCESS, device);
                Log.e(TAG, "onConnectionChanged: " + device.isConnected());
            }
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
    public void sendData(final byte[] data) {
        if (data == null) {
            ToastUtil.showLong(OperationApplication.getInstance(), "发送数据为空");
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
        if (data.length > 20) {
            mBle.writeEntity(device, data, 20, 50, new BleWriteEntityCallback<BleDevice>() {
                @Override
                public void onWriteSuccess() {
                    Log.e(TAG, "onWriteSuccess::");
                }

                @Override
                public void onWriteFailed() {
                    Log.e(TAG, "onWriteFailed::");
                }
            });
        } else {
            boolean result = mBle.write(device, data,
                    new BleWriteCallback<BleDevice>() {
                        @Override
                        public void onWriteSuccess(BluetoothGattCharacteristic characteristic) {
//                            ToastUtil.showLong(OperationApplication.getInstance(), "发送数据成功");
                        }
                    });
            if (!result) {
//            ToastUtil.showLong(VcontrolApplication.getCurrentContext(), "发送数据失败!");
            }
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

    private StringBuffer stringBuffer = new StringBuffer();

    private void redData(BleDevice device) {
        boolean result = mBle.read(device, new BleReadCallback<BleDevice>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onReadSuccess(BluetoothGattCharacteristic characteristic) {
                super.onReadSuccess(characteristic);
                byte[] data = characteristic.getValue();
                String result = new String(data);
                if (result.endsWith("@")) {
                    EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_DATA,
                            stringBuffer.toString());
                } else {
                    stringBuffer.append(result);
                }
            }
        });
        if (!result) {
            Log.d(TAG, "读取数据失败!");
        }
    }


//    protected Queue<DataInfo> splitPacketFor20Byte(byte[] data) {
//        LinkedList dataInfoQueue = new LinkedList();
//        if(data != null) {
//            int index = 0;
//            do {
//                byte[] surplusData = new byte[data.length - index];
//                System.arraycopy(data, index, surplusData, 0, data.length - index);
//                byte[] currentData;
//                if(surplusData.length <= 20) {
//                    currentData = new byte[surplusData.length];
//                    System.arraycopy(surplusData, 0, currentData, 0, surplusData.length);
//                    index += surplusData.length;
//                } else {
//                    currentData = new byte[20];
//                    System.arraycopy(data, index, currentData, 0, 20);
//                    index += 20;
//                }
//                DataInfo dataInfo = new DataInfo();
//                dataInfo.setDataType(1);
//                dataInfo.setData(currentData);
//                dataInfoQueue.offer(dataInfo);
//            } while(index < data.length);
//        }
//
//        return dataInfoQueue;
//    }

    private void notifyData(String data) {
        String[] res = data.split("\r\n");
        for (String result : res) {
            if (result != null && result.toUpperCase().contains("OK")) {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_RESULT_OK,
                        result);
            } else if (result != null && result.toUpperCase().contains("ERROR")) {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_RESULT_ERROR
                        , result);
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
