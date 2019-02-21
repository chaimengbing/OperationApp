package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.BleUtils;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

import cn.com.heaton.blelibrary.ble.BleDevice;

/**
 * Created by linxi on 2018/5/23.
 */

public class BleSearchFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate {
    private static final String TAG = BleSearchFragment.class.getSimpleName();

    private TextView resultTextView;
    private ScrollView resultScroll;
    private StringBuffer currentSB = new StringBuffer();
    private String WaterLevel_R;
    private String WaterLevel_A;
    private String BatteryVolts;
    private String GatePosition;
    private String signe;
    private String Moisture;
    private String GPRS_Status;
    private String connectStatus1;
    private String connectStatus2;
    private String Send_informa_time_tm1;
    private String Send_informa_time_tm2;

    private String C = " ℃";
    private String V = " V";
    private String M = " m";
    private boolean isBleDevice = false;
    private BleDevice bleDevice = null;

    @Override
    public int getLayoutView() {
        return R.layout.fragment_search;
    }

    @Override
    public void initComponentViews(View view) {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BUNDLE);
        resultTextView = (TextView) view.findViewById(R.id.result_data_textview);
        resultScroll = (ScrollView) view.findViewById(R.id.result_scroll);


        if (getArguments() != null) {
            isBleDevice = getArguments().getBoolean("isBleDevice");
            bleDevice = (BleDevice) getArguments().getSerializable("device");
        }
        setData();
    }


    private void sendData(String content) {
        if (isBleDevice) {
            BleUtils.getInstance().sendData(bleDevice, content.getBytes());
        } else {
            SocketUtil.getSocketUtil().sendContent(content);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_IMAGE_SUCCESS);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }


    private void readData(String result, String content) {
        String timeR = "";
        String res = "";

        if (result.contains(ConfigParams.BatteryVolts)) {
            currentSB.insert(currentSB.indexOf(BatteryVolts) + BatteryVolts.length(), result.replaceAll(ConfigParams.BatteryVolts, "").trim());

        } else if (result.contains(ConfigParams.Gate_position)) {
            currentSB.insert(currentSB.indexOf(GatePosition) + GatePosition.length(), ServiceUtils.getGPRSStatus(result.replaceAll(ConfigParams.Gate_position, "").trim(), getActivity()));
        } else if (result.contains(ConfigParams.RSSI)) {
            currentSB.insert(currentSB.indexOf(signe) + signe.length(), result.replaceAll(ConfigParams.RSSI, "").trim());
        } else if (result.contains(ConfigParams.Moisture)) {
            currentSB.insert(currentSB.indexOf(Moisture) + Moisture.length(), result.replaceAll(ConfigParams.Moisture, "").trim());
        } else if (result.contains(ConfigParams.WaterLevel_R)) {
            currentSB.insert(currentSB.indexOf(WaterLevel_R) + WaterLevel_R.length(), result.replaceAll(ConfigParams.WaterLevel_R, "").trim());
        } else if (result.contains(ConfigParams.WaterLevel_A)) {
            currentSB.insert(currentSB.indexOf(WaterLevel_A) + WaterLevel_A.length(), result.replaceAll(ConfigParams.WaterLevel_A, "").trim());
        } else if (result.contains(ConfigParams.GPRS_Status)) {
            currentSB.insert(currentSB.indexOf(GPRS_Status) + GPRS_Status.length(), ServiceUtils.getGPRSStatus(result.replaceAll(ConfigParams.GPRS_Status, "").trim(), getActivity()));
        } else if (result.contains(ConfigParams.Gate_position)) {
            currentSB.insert(currentSB.indexOf(GatePosition) + GatePosition.length(), ServiceUtils.getGPRSStatus(result.replaceAll(ConfigParams.Gate_position, "").trim(), getActivity()));
        } else if (result.contains(ConfigParams.RSSI)) {
            currentSB.insert(currentSB.indexOf(signe) + signe.length(), result.replaceAll(ConfigParams.RSSI, "").trim());
        } else if (result.contains(ConfigParams.Send_informa_time_tm1)) {
            currentSB.insert(currentSB.indexOf(Send_informa_time_tm1) + Send_informa_time_tm1.length(), result.replaceAll(ConfigParams.Send_informa_time_tm1, "").trim());
        } else if (result.contains(ConfigParams.Send_informa_time_tm2)) {
            currentSB.insert(currentSB.indexOf(Send_informa_time_tm2) + Send_informa_time_tm2.length(), result.replaceAll(ConfigParams.Send_informa_time_tm2, "").trim());
        } else if (result.contains(ConfigParams.ConnectStatus1)) {
            String data = result.replaceAll(ConfigParams.ConnectStatus1, "").trim();
            if ("Finish".equals(data)) {
                currentSB.insert(currentSB.indexOf(connectStatus1) + connectStatus1.length(), getString(R.string.connected));
            } else {
                String[] connectStatus = data.split(":");
                if (connectStatus.length > 1) {
                    currentSB.insert(currentSB.indexOf(connectStatus1) + connectStatus1.length(), getString(R.string.Connecting_are) + connectStatus[0] + getString(R.string.port1) + connectStatus[1]);
                }

            }

        } else if (result.contains(ConfigParams.ConnectStatus2)) {
            String data = result.replaceAll(ConfigParams.ConnectStatus2, "").trim();
            if ("Finish".equals(data)) {
                currentSB.insert(currentSB.indexOf(connectStatus2) + connectStatus2.length(), getString(R.string.connected));
            } else {
                String[] connectStatus = data.split(":");
                if (connectStatus.length > 1) {
                    currentSB.insert(currentSB.indexOf(connectStatus2) + connectStatus2.length(), getString(R.string.Connecting_are) + connectStatus[0] + getString(R.string.port1) + connectStatus[1]);
                }

            }

        }
        resultTextView.setText(currentSB.toString());

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.NOTIFY_BUNDLE) {
            setData();
        } else if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content)) {
                return;
            }
            readData(result, content);
        }
    }

    public void setData() {

        BatteryVolts = getString(R.string.Battery_voltage);
        WaterLevel_R = getString(R.string.Relative_water_level_on_reservoir);
        WaterLevel_A = getString(R.string.Absolute_water_level_on_reservoir);
        GatePosition = getString(R.string.Gate_position);
        Moisture = getString(R.string.shangqing);
        signe = getString(R.string.Signal_strength_value);
        GPRS_Status = getString(R.string.GPRS_Status);
        connectStatus1 = getString(R.string.Channel_1_connection_status);
        connectStatus2 = getString(R.string.Channel_2_connection_status);
        Send_informa_time_tm1 = getString(R.string.Send_informa_time_tm1);
        Send_informa_time_tm2 = getString(R.string.Send_informa_time_tm2);

        currentSB.delete(0, currentSB.length());

        resultScroll.setVisibility(View.VISIBLE);


        sendData(ConfigParams.Readdata);

        currentSB.append(BatteryVolts);
        currentSB.append(V);
        currentSB.append("\n");
        currentSB.append(WaterLevel_R);
        currentSB.append(M);
        currentSB.append("\n");
        currentSB.append(WaterLevel_A);
        currentSB.append(M);
        currentSB.append("\n");
        currentSB.append(GatePosition);
        currentSB.append(M);
        currentSB.append("\n");
        currentSB.append(Moisture);
        currentSB.append("\n");
        currentSB.append(signe);
        currentSB.append("\n");
        currentSB.append(GPRS_Status);
        currentSB.append("\n");
        currentSB.append(connectStatus1);
        currentSB.append("\n");
        currentSB.append(connectStatus2);
        currentSB.append("\n");
        currentSB.append(Send_informa_time_tm1);
        currentSB.append("\n");
        currentSB.append(Send_informa_time_tm2);
        currentSB.append("\n");

        if (resultTextView != null && currentSB.length() > 0) {
            resultTextView.setText(currentSB.toString());
        }
    }


}
