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
 * Created by linxi on 2018/1/19.
 */

public class YPTFragment extends BaseFragment implements View.OnClickListener,EventNotifyHelper.NotificationCenterDelegate
{
    private static final java.lang.String TAG = YPTFragment.class.getSimpleName() ;
    private EditText deviceEditText;
    private Button deviceSetButton;
    private RadioGroup center4Group;
    private RadioGroup reserve4Group;
    private RadioGroup noeth4ChannelGroup;
    private EditText ip4;
    private EditText port4;
    private Button set4;
    private RadioGroup timingGroup;
    private RadioGroup uploadGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_ypt);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    private void initView(final View v)
    {
        center4Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = v.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetCenterType + "4 ";
                if (checkedId == R.id.no_use_4)
                {
                    ServiceUtils.sendData(content + "0");
                }
                else if (checkedId == R.id.gprs_radiobutton_4)
                {
                    ServiceUtils.sendData(content + "2");
                }
                else if (checkedId == R.id.gprs_gsm_radiobutton_4)
                {
                    ServiceUtils.sendData(content + "7");
                }
            }
        });
        reserve4Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = v.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetReserveType + "4 ";
                if (checkedId == R.id.no_use_sencond_4)
                {
                    ServiceUtils.sendData(content + "0");
                }
                else if (checkedId == R.id.sms_radiobutton_4)
                {
                    ServiceUtils.sendData(content + "1");
                }
                else if (checkedId == R.id.beidou_radiobutton_4)
                {
                    ServiceUtils.sendData(content + "3");
                }
            }
        });
        noeth4ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = v.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.Setconnect_Type4;
                if (checkedId == R.id.tcp_4)
                {
                    ServiceUtils.sendData(content + "0");
                }
                else if (checkedId == R.id.udp_4)
                {
                    ServiceUtils.sendData(content + "1");
                }
            }
        });
        timingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = v.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetYUN_SETTIME_SendMode;
                if (checkedId == R.id.timing)
                {
                    ServiceUtils.sendData(content + "1");
                }
                else if (checkedId == R.id.no_timing)
                {
                    ServiceUtils.sendData(content + "2");
                }
            }
        });
        uploadGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = v.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetYUN_ELEMENT_SendMode;
                if (checkedId == R.id.upload)
                {
                    ServiceUtils.sendData(content + "1");
                }
                else if (checkedId == R.id.no_upload)
                {
                    ServiceUtils.sendData(content + "2");
                }
            }
        });
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        deviceSetButton = (Button) view.findViewById(R.id.device_id_setting_button1);
        deviceEditText = (EditText) view.findViewById(R.id.device_id1);
        center4Group = (RadioGroup) view.findViewById(R.id.center_radiogroup_4);
        reserve4Group = (RadioGroup) view.findViewById(R.id.reserve_radiogroup_4);
        noeth4ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_4);
        timingGroup = (RadioGroup) view.findViewById(R.id.timing_group);
        uploadGroup = (RadioGroup) view.findViewById(R.id.upload_group);
        ip4 = (EditText) view.findViewById(R.id.ip_4_edittext);
        port4 = (EditText) view.findViewById(R.id.port_4_edittext);
        set4 = (Button) view.findViewById(R.id.gprs_4_button);
        initView(view);
    }

    @Override
    public void initData()
    {
        ServiceUtils.sendData(ConfigParams.READYUN);
    }

    @Override
    public void initEvent() {
        deviceSetButton.setOnClickListener(this);
        set4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String data = "";
        int dataNum = 0;
        String content = "";
        switch (v.getId())
        {
            case R.id.device_id_setting_button1:
                String deviceid = deviceEditText.getText().toString();
                if (TextUtils.isEmpty(deviceid))
                {
                    ToastUtil.showToastLong("设备ID不能为空！");
                    return;
                }

                if (deviceid.length() == 14)
                {
                    String content1 = ConfigParams.SetRTUid18 + deviceid;
                    ServiceUtils.sendData(content1);
                }
                else

                {
                    ToastUtil.showToastLong("设备ID位数不等于14位，请重新输入！");
                }
                break;
            case R.id.gprs_4_button:
                data = ip4.getText().toString().trim();
                content = port4.getText().toString().trim();

                if (TextUtils.isEmpty(data))
                {
                    ToastUtil.showToastLong("ip地址不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(content))
                {
                    ToastUtil.showToastLong("端口号不能为空！");
                    return;
                }
                // 设置状态参数
                String string = ConfigParams.SetIP +  4 + " " + ServiceUtils.getRegxIp(data) + ConfigParams.setPort + ServiceUtils.getStr(content + "", 5);
                ServiceUtils.sendData(string);
                break;
            default:
                break;
        }
    }
    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        Log.i(TAG,"1");
        String result = (String) args[0];
        String content = (String) args[1];
        if (TextUtils.isEmpty(result))
        {
            return;
        }
        setData(result);
    }

    private void setData(String result)
    {
        String data = "";
        String[] portArray = null;
        if (result.contains(ConfigParams.SetRTUid18.trim()))
        {// 设备ID
            deviceEditText.setText(result.replaceAll(ConfigParams.SetRTUid18.trim(), "").trim());
        }
        if (result.contains(ConfigParams.Setconnect_Type))
        {
            data = result.replaceAll(ConfigParams.Setconnect_Type, "").trim();
            if (TextUtils.isEmpty(data))
            {
                return;
            }
            String statu = ServiceUtils.hexString2binaryString(data);
            if (statu.length() > 0)
            {

                char type4 = statu.charAt(statu.length() - 4);
                if ('0' == type4)
                {//tcp
                    noeth4ChannelGroup.check(R.id.tcp_4);
                }
                else
                {
                    noeth4ChannelGroup.check(R.id.udp_4);
                }
            }
        }
        else if (result.contains(ConfigParams.SetYUN_SETTIME_SendMode))
        {
            data = result.replaceAll(ConfigParams.SetYUN_SETTIME_SendMode, "").trim();
            if (TextUtils.isEmpty(data))
            {
                return;
            }

                if ("1".equals(data))
                {//tcp
                    timingGroup.check(R.id.timing);
                }
                else
                {
                    timingGroup.check(R.id.no_timing);
                }
        }
        else if (result.contains(ConfigParams.SetYUN_ELEMENT_SendMode))
        {
            data = result.replaceAll(ConfigParams.SetYUN_ELEMENT_SendMode, "").trim();
            if (TextUtils.isEmpty(data))
            {
                return;
            }
                if ("1".equals(data))
                {//tcp
                    uploadGroup.check(R.id.upload);
                }
                else
                {
                    uploadGroup.check(R.id.no_upload);
                }
        }
        else if (result.contains(ConfigParams.SetCenterType + "4"))
        {
            data = result.replaceAll(ConfigParams.SetCenterType + "4", "").trim();
            if ("0".equals(data))
            {
                center4Group.check(R.id.no_use_4);
            }
            else if ("2".equals(data))
            {
                center4Group.check(R.id.gprs_radiobutton_4);
            }
            else if ("7".equals(data))
            {
                center4Group.check(R.id.gprs_gsm_radiobutton_4);
            }
        }
        else if (result.contains(ConfigParams.SetReserveType + "4"))
        {
            data = result.replaceAll(ConfigParams.SetReserveType + "4", "").trim();
            if ("0".equals(data))
            {
                reserve4Group.check(R.id.no_use_sencond_4);
            }
            else if ("1".equals(data))
            {
                reserve4Group.check(R.id.sms_radiobutton_4);
            }
            else if ("3".equals(data))
            {
                reserve4Group.check(R.id.beidou_radiobutton_4);
            }
        }
        else if (result.contains(ConfigParams.SetIP + "4"))
        {
            portArray = result.split(ConfigParams.setPort.trim());
            ip4.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 4, "").trim()));
            if (portArray.length > 1)
            {
                port4.setText(portArray[1].trim());
            }
        }
    }
}
