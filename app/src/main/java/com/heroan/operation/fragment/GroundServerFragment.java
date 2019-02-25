package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;


/**
 * Created by Vcontrol on 2016/11/23.
 */

public class GroundServerFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener {
    private static final String TAG = GroundServerFragment.class.getSimpleName();

    private RadioGroup serverRadioGroup;
    private String currentServer = "0";
    private TextView gprsTextView;
    private TextView serverTextView;
    private EditText ipEditText, portEditText;
    private Button gprsButton;

    private StringBuffer currentSB = new StringBuffer();

    private String server1;
    private String server2;
    private String server3;
    private String server4;
    private String port1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_ground_server);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void initView() {

        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        serverRadioGroup = (RadioGroup) view.findViewById(R.id.server_group);

        gprsButton = (Button) view.findViewById(R.id.gprs_button);
        gprsTextView = (TextView) view.findViewById(R.id.gprs_textview);
        serverTextView = (TextView) view.findViewById(R.id.service_info);
        ipEditText = (EditText) view.findViewById(R.id.ip_edittext);
        portEditText = (EditText) view.findViewById(R.id.port_edittext);

        gprsTextView.setText(getString(R.string.center));

        initView(view);
    }

    @Override
    public void initData() {

        server1 = getString(R.string.The_server) + "1\n" + getString(R.string.ip_);
        server2 = getString(R.string.The_server) + "2\n" + getString(R.string.ip_);
        server3 = getString(R.string.The_server) + "3\n" + getString(R.string.ip_);
        server4 = getString(R.string.The_server) + "4\n" + getString(R.string.ip_);
        port1 = getString(R.string.port);
        currentSB.delete(0, currentSB.length());

        currentSB.append(server1);
        currentSB.append("  ");
        currentSB.append(port1);
        currentSB.append("\n");
        currentSB.append(server2);
        currentSB.append("  ");
        currentSB.append(port1);
        currentSB.append("\n");
        currentSB.append(server3);
        currentSB.append("  ");
        currentSB.append(port1);
        currentSB.append("\n");
        currentSB.append(server4);
        currentSB.append("  ");
        currentSB.append(port1);
        currentSB.append("\n");

        serverTextView.setText(currentSB.toString());

        ServiceUtils.sendData(ConfigParams.ReadCommPara);

    }

    @Override
    public void initEvent() {
        gprsButton.setOnClickListener(this);
    }


    private void initView(final View view) {
        serverRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                if (checkedId == R.id.server_1) {
                    currentServer = "1";
                    gprsTextView.setText(getString(R.string.center_1_));
                } else if (checkedId == R.id.server_2) {
                    currentServer = "2";
                    gprsTextView.setText(getString(R.string.center_2));
                } else if (checkedId == R.id.server_3) {
                    currentServer = "3";
                    gprsTextView.setText(getString(R.string.center_3));
                } else if (checkedId == R.id.server_4) {
                    currentServer = "4";
                    gprsTextView.setText(getString(R.string.center_4));
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result)) {
                return;
            }
            setData(result);
        }
    }


    public void setData(String result) {
        String[] ipArray = result.split(ConfigParams.setPort.trim());
        String ip = "";
        String port = "";
        if (result.contains(ConfigParams.SetIP + 1)) {
            if (ipArray != null) {
                ip = ServiceUtils.getRemoteIp(ipArray[0].replaceAll(ConfigParams.SetIP + 1, "").trim());
                if (ipArray.length > 1) {
                    port = ipArray[1].trim();
                }
            }
            currentSB.insert(currentSB.indexOf(server1) + server1.length(), ip);
            currentSB.insert(currentSB.indexOf(port1) + port1.length(), port);
        } else if (result.contains(ConfigParams.SetIP + 2)) {
            if (ipArray != null) {
                ip = ServiceUtils.getRemoteIp(ipArray[0].replaceAll(ConfigParams.SetIP + 2, "").trim());
                if (ipArray.length > 1) {
                    port = ipArray[1].trim();
                }
            }
            currentSB.insert(currentSB.indexOf(server2) + server2.length(), ip);
            currentSB.insert(currentSB.indexOf(port1, currentSB.indexOf(server2)) + port1.length(), port);
        } else if (result.contains(ConfigParams.SetIP + 3)) {
            if (ipArray != null) {
                ip = ServiceUtils.getRemoteIp(ipArray[0].replaceAll(ConfigParams.SetIP + 3, "").trim());
                if (ipArray.length > 1) {
                    port = ipArray[1].trim();
                }
            }
            currentSB.insert(currentSB.indexOf(server3) + server3.length(), ip);
            currentSB.insert(currentSB.indexOf(port1, currentSB.indexOf(server3)) + port1.length(), port);
        } else if (result.contains(ConfigParams.SetIP + 4)) {
            if (ipArray != null) {
                ip = ServiceUtils.getRemoteIp(ipArray[0].replaceAll(ConfigParams.SetIP + 4, "").trim());
                if (ipArray.length > 1) {
                    port = ipArray[1].trim();
                }
            }
            currentSB.insert(currentSB.indexOf(server4) + server4.length(), ip);
            currentSB.insert(currentSB.lastIndexOf(port1) + port1.length(), port);
        }
        serverTextView.setText(currentSB.toString());
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.gprs_button) {
            if ("0".equals(currentServer)) {
                ToastUtil.showToastLong(getString(R.string.Please_select_the_server_number));
                return;
            }
            String ip = ipEditText.getText().toString().trim();
            String port = portEditText.getText().toString().trim();


            if (TextUtils.isEmpty(ip)) {
                ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                return;
            }
            if (TextUtils.isEmpty(port)) {
                ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                return;
            }
            Log.i(TAG, "ip:" + ip + ",port" + port);

            String content =
                    ConfigParams.SetIP + currentServer + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
            ServiceUtils.sendData(content);
        }
    }
}
