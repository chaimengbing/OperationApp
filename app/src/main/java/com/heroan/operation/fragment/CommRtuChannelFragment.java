package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class CommRtuChannelFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener {

    private static final String TAG = CommRtuChannelFragment.class.getSimpleName();

    private Button devgprsButton;
    private Button gprsButton;
    private TextView ipEditView;
    private TextView portEditView;
    private EditText devipEditText;

    private TextView macTextView;
    private Spinner ipSpinner;

    private int currentConfig = ConfigParams.IP_DHCP;

    private SimpleSpinnerAdapter ipAdapter;
    private String[] ipItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_comm_channel_eth);
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


    public void refreshData() {
        ipItems = getResources().getStringArray(R.array.ip_config);
        ipAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, ipItems);
        ipSpinner.setAdapter(ipAdapter);
        ServiceUtils.sendData(ConfigParams.ReadStaticIP);


    }


    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        devgprsButton = view.findViewById(R.id.setip_button);
        gprsButton = view.findViewById(R.id.gprs_button);
        devipEditText = view.findViewById(R.id.setip_edittext);
        ipEditView = view.findViewById(R.id.ip_edittext);
        portEditView = view.findViewById(R.id.port_edittext);
        macTextView = view.findViewById(R.id.mac_add);
        ipSpinner = view.findViewById(R.id.ip_spinner);
    }

    @Override
    public void initData() {
        refreshData();
    }

    @Override
    public void initEvent() {
        devgprsButton.setOnClickListener(this);
        gprsButton.setOnClickListener(this);

        ipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ipAdapter.setSelectedItem(position);
                currentConfig = position;

                if (currentConfig == ConfigParams.IP_DHCP) {
                    devipEditText.setEnabled(false);
                } else {
                    devipEditText.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setip_button:
                String devip = devipEditText.getText().toString().trim();
                if (TextUtils.isEmpty(devip)) {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }

                String content =
                        ConfigParams.SetStaticIP + "1 " + devip + " " + ConfigParams.IPFlags + currentConfig;
                ServiceUtils.sendData(content);
                break;
            case R.id.gprs_button:
                String ip = ipEditView.getText().toString().trim();
                if (TextUtils.isEmpty(ip)) {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                String port = portEditView.getText().toString().trim();
                if (TextUtils.isEmpty(ip)) {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }

                String content1 =
                        ConfigParams.SetIP + 1 + " " + ip + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
                // String content1 = ConfigParams.SetIP + "1 " +
                // ServiceUtils.getRegxIp(ip) + " " + ConfigParams.PORT +
                // currentConfig;
                ServiceUtils.sendData(content1);
                break;

            default:
                break;
        }

    }

    private void setData(String result) {
        if (result.contains(ConfigParams.SetStaticIP)) {
            devipEditText.setText(ServiceUtils.getRemoteIp(result.replaceAll(ConfigParams.SetStaticIP, "").trim()));
//            VcontrolApplication.applicationHandler.postDelayed(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    ServiceUtils.sendData(ConfigParams.ReadCommPara2);
//                }
//            },1500);
        } else if (result.contains(ConfigParams.IPFlags)) {
            String data = result.replaceAll(ConfigParams.IPFlags, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int config = Integer.parseInt(data);
                if (config >= 0 || config <= 1) {
                    ipSpinner.setSelection(config);
                }
            }
        } else if (result.contains(ConfigParams.Module_MAC)) {
            String mac = result.replaceAll(ConfigParams.Module_MAC, "").replaceAll(" ", "0");
            mac = ServiceUtils.getMac(mac);
            macTextView.setText(mac);
            ServiceUtils.sendData(ConfigParams.ReadCommPara2);
        } else if (result.contains(ConfigParams.SetIP + "1")) {
            // 读取参数，更新适配器
            String[] ipArray = result.split(ConfigParams.setPort.trim());
            if (ipArray != null) {
                if (ipArray.length > 0) {
                    ipEditView.setText(ServiceUtils.getRemoteIp(ipArray[0].replaceAll(ConfigParams.SetIP + 1, "").trim()));
                }
                if (ipArray.length > 1) {
                    portEditView.setText(ipArray[1].trim());
                }
            }
        }
    }

}
