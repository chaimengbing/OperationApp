package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

/**
 * GPRS设置界面
 * Created by Vcontrol on 2017/03/27.
 */

public class ChannelGPRSFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private static final String TAG = ChannelGPRSFragment.class.getSimpleName();
    //信道连接方式1 2 3 4
    private RadioGroup noeth1ChannelGroup;
    private RadioGroup noeth2ChannelGroup;
    private RadioGroup noeth3ChannelGroup;

    private EditText ip1;
    private EditText ip2;
    private EditText ip3;

    private EditText port1;
    private EditText port2;
    private EditText port3;

    private Button set1;
    private Button set2;
    private Button set3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_channel_gprs);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }


    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        noeth1ChannelGroup = view.findViewById(R.id.channel_group_1);
        noeth2ChannelGroup = view.findViewById(R.id.channel_group_2);
        noeth3ChannelGroup = view.findViewById(R.id.channel_group_3);

        ip1 = view.findViewById(R.id.ip_1_edittext);
        ip2 = view.findViewById(R.id.ip_2_edittext);
        ip3 = view.findViewById(R.id.ip_3_edittext);

        port1 = view.findViewById(R.id.port_1_edittext);
        port2 = view.findViewById(R.id.port_2_edittext);
        port3 = view.findViewById(R.id.port_3_edittext);

        set1 = view.findViewById(R.id.gprs_1_button);
        set2 = view.findViewById(R.id.gprs_2_button);
        set3 = view.findViewById(R.id.gprs_3_button);
    }

    @Override
    public void initData() {
        ServiceUtils.sendData(ConfigParams.ReadCommPara2);
    }

    @Override
    public void initEvent() {
        set1.setOnClickListener(this);
        set2.setOnClickListener(this);
        set3.setOnClickListener(this);

        noeth1ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.Setconnect_Type1;
                if (checkedId == R.id.tcp_1) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.udp_1) {
                    ServiceUtils.sendData(content + "1");
                }
            }
        });
        noeth2ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.Setconnect_Type2;
                if (checkedId == R.id.tcp_2) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.udp_2) {
                    ServiceUtils.sendData(content + "1");
                }
            }
        });
        noeth3ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.Setconnect_Type3;
                if (checkedId == R.id.tcp_3) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.udp_3) {
                    ServiceUtils.sendData(content + "1");
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        String ip, port, content = "";
        switch (view.getId()) {
            case R.id.gprs_1_button:
                ip = ip1.getText().toString().trim();
                port = port1.getText().toString().trim();

                if (TextUtils.isEmpty(ip)) {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(port)) {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content =
                        ConfigParams.SetIP + 1 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);

                break;
            case R.id.gprs_2_button:
                ip = ip2.getText().toString().trim();
                port = port2.getText().toString().trim();

                if (TextUtils.isEmpty(ip)) {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(port)) {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content =
                        ConfigParams.SetIP + 2 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);

                break;
            case R.id.gprs_3_button:
                ip = ip3.getText().toString().trim();
                port = port3.getText().toString().trim();

                if (TextUtils.isEmpty(ip)) {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(port)) {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content =
                        ConfigParams.SetIP + 3 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
                break;
//            case R.id.gprs_4_button:
//                ip = ip4.getText().toString().trim();
//                port = port4.getText().toString().trim();
//
//                if (TextUtils.isEmpty(ip))
//                {
//                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
//                    return;
//                }
//                if (TextUtils.isEmpty(port))
//                {
//                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
//                    return;
//                }
//                // 设置状态参数
//                content = ConfigParams.SetIP +  4 + " " + ServiceUtils.getRegxIp(ip) +
// ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
//                break;
            default:
                break;
        }

        ServiceUtils.sendData(content);
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        String result = (String) args[0];
        String content = (String) args[1];
        if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content)) {
            return;
        }
        setData(result);
    }

    private void setData(String result) {
        String data = "";
        String[] portArray = null;

        //信道连接方式 1 2 3 4
        if (result.contains(ConfigParams.Setconnect_Type)) {
            data = result.replaceAll(ConfigParams.Setconnect_Type, "").trim();
            if (TextUtils.isEmpty(data)) {
                return;
            }
            String statu = ServiceUtils.hexString2binaryString(data);
            Log.d(TAG, "status:" + statu);

            if (statu.length() > 0) {
                char type = statu.charAt(statu.length() - 1);
                if ('0' == type) {//tcp
                    noeth1ChannelGroup.check(R.id.tcp_1);
                } else if ('1' == type) {
                    noeth1ChannelGroup.check(R.id.udp_1);
                }
                char type2 = statu.charAt(statu.length() - 2);
                if ('0' == type2) {//tcp
                    noeth2ChannelGroup.check(R.id.tcp_2);
                } else if ('1' == type2) {
                    noeth2ChannelGroup.check(R.id.udp_2);
                }
                char type3 = statu.charAt(statu.length() - 3);
                if ('0' == type3) {//tcp
                    noeth3ChannelGroup.check(R.id.tcp_3);
                } else if ('1' == type3) {
                    noeth3ChannelGroup.check(R.id.udp_3);
                }
            }

        } else if (result.contains(ConfigParams.SetIP + "1")) {
            portArray = result.split(ConfigParams.setPort.trim());
            ip1.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 1,
                    "").trim()));
            if (portArray.length > 1) {
                port1.setText(portArray[1].trim());
            }
        } else if (result.contains(ConfigParams.SetIP + "2")) {
            portArray = result.split(ConfigParams.setPort.trim());
            ip2.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 2,
                    "").trim()));
            if (portArray.length > 1) {
                port2.setText(portArray[1].trim());
            }
        } else if (result.contains(ConfigParams.SetIP + "3")) {
            portArray = result.split(ConfigParams.setPort.trim());
            ip3.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 3,
                    "").trim()));
            if (portArray.length > 1) {
                port3.setText(portArray[1].trim());
            }
        }
//        else if (result.contains(ConfigParams.SetIP + "4"))
//        {
//            portArray = result.split(ConfigParams.setPort.trim());
//            ip4.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP +
// 4, "").trim()));
//            if (portArray.length > 1)
//            {
//                port4.setText(portArray[1].trim());
//            }
//        }

    }

}
