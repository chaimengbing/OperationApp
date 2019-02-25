package com.heroan.operation.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.LeDeviceListAdapter;
import com.heroan.operation.utils.BleUtils;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.UiEventEntry;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.BleDevice;
import zuo.biao.library.base.BaseActivity;

public class BleDeviceListActivity extends BaseActivity implements AdapterView.OnItemClickListener, EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener {
    private static String TAG = BleDeviceListActivity.class.getName();

    private ListView mListView;
    private TextView noDevice;
    private LinearLayout loadingView;
    private Button sendButton;
    private Button readButton;
    private TextView title;
    private TextView titleRight;
    private ImageView backImageView;


    private LeDeviceListAdapter mLeDeviceListAdapter;
    private Ble<BleDevice> mBle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bledevice_list);
        initView();
        initData();
        initEvent();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this,
                UiEventEntry.NOTIFY_BLE_CONNECT_SUCCESS);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BLE_CONNECT_FAIL);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BLE_CONNECT_STOP);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BLE_SCAN_SUCCESS);
        if (mBle != null) {
            mBle.destory(getApplicationContext());
        }

        BleUtils.getInstance().disConnectDevice();
    }


    private void showEmptyView() {
        if (mLeDeviceListAdapter == null || mLeDeviceListAdapter.getCount() == 0) {
            noDevice.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            noDevice.setVisibility(View.GONE);
        }
    }


    //初始化蓝牙
    private void initBle() {
        //3、检查蓝牙是否支持及打开
        checkBluetoothStatus();
    }


    private void showLoading() {
        noDevice.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }


    //检查蓝牙是否支持及打开
    private void checkBluetoothStatus() {
        // 检查设备是否支持BLE4.0
//        if (!mBle.isSupportBle(this)) {
//            ToastUtil.showShort(getApplicationContext(), R.string.ble_not_supported);
//            finish();
//        }
        if (!BleUtils.getInstance().isBleEnable()) {
            //4、若未打开，则请求打开蓝牙
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Ble.REQUEST_ENABLE_BT);
        } else {
            //5、若已打开，则进行扫描
            BleUtils.getInstance().startScan();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                showLoading();
                BleUtils.getInstance().startScan();
                break;
            default:
                break;
        }
    }


    //请求权限
    private void requestPermission() {
        requestPermission(new String[]{Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                "请求蓝牙相关权限", new GrantedResult() {
                    @Override
                    public void onResult(boolean granted) {
                        if (granted) {
                            //2、初始化蓝牙
                            initBle();
                        } else {
                            finish();
                        }
                    }
                });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BluetoothDevice device = mLeDeviceListAdapter.getDevice(i);
        if (device == null) return;
        if (BleUtils.getInstance().isScanning()) {
            BleUtils.getInstance().stopScan();
        }
        BleUtils.getInstance().startScan();
    }


    private void toBleView() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra(UiEventEntry.NOTIFY_BASIC_NAME, getString(R.string.lru_3300));
        intent.putExtra(UiEventEntry.NOTIFY_BASIC_TYPE, UiEventEntry.LRU_BLE_3300);
        startActivity(intent);
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.NOTIFY_BLE_CONNECT_SUCCESS) {
            toBleView();
        } else if (id == UiEventEntry.NOTIFY_BLE_SCAN_SUCCESS) {
            BluetoothDevice bleDevice = (BluetoothDevice) args[0];
            if (bleDevice != null) {
                mLeDeviceListAdapter.addDevice(bleDevice);
                mLeDeviceListAdapter.notifyDataSetChanged();
            }
            hideLoading();
            showEmptyView();

        } else if (id == UiEventEntry.NOTIFY_BLE_CONNECT_FAIL) {

        } else if (id == UiEventEntry.NOTIFY_BLE_CONNECT_STOP) {
            hideLoading();
            showEmptyView();
        }
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BLE_CONNECT_SUCCESS);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BLE_CONNECT_FAIL);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BLE_CONNECT_STOP);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BLE_SCAN_SUCCESS);
        mListView = findView(R.id.device_listview);
        noDevice = findView(R.id.no_device);
        loadingView = findView(R.id.loadingview);
        sendButton = findView(R.id.send_button);
        readButton = findView(R.id.read_button);
        title = findView(R.id.title);
        backImageView = findView(R.id.title_back);
        titleRight = findView(R.id.title_right);
    }

    @Override
    public void initData() {
        title.setText("选择设备");
        titleRight.setText("扫描");
        showLoading();
        mLeDeviceListAdapter = new LeDeviceListAdapter(this);
        mListView.setAdapter(mLeDeviceListAdapter);
        mListView.setOnItemClickListener(this);
        sendButton.setOnClickListener(this);
        readButton.setOnClickListener(this);
        //1、请求蓝牙相关权限
        requestPermission();
    }

    @Override
    public void initEvent() {
        titleRight.setOnClickListener(this);
        backImageView.setOnClickListener(this);
    }
}
