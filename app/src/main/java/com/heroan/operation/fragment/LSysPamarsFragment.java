package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linxi on 2017/12/5.
 */

public class LSysPamarsFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{
    private static final String TAG = LSysPamarsFragment.class.getSimpleName();
    private static final String DEFAULT_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    private static final String SEND_TIME_FORMAT = "yyyyMMddHHmmss";

    private EditText ip1;
    private EditText ip2;
    private EditText ip3;
    private EditText ip4;

    private EditText port1;
    private EditText port2;
    private EditText port3;
    private EditText port4;

    private Button set1;
    private Button set2;
    private Button set3;
    private Button set4;
    private Button tcp1;
    private Button tcp2;
    private Button tcp3;
    private Button tcp4;

    private TextView rtuTimeTextView;
    private Button timeButton;

    private EditText apnEdittext;
    private Button apnButton;
    private EditText loraEdittext;
    private Button loraButton;
    private String setTime = "";
    private SimpleDateFormat sendTimeFormat;
    private SimpleDateFormat timeFormat;
    private Button resetFactoryButton;
    private Button saveResetButton;

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_lsyspamars;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.SELECT_TIME);
    }
    @Override
    public void initComponentViews(View view)
    {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.SELECT_TIME);

        apnEdittext = (EditText) view.findViewById(R.id.apn_edittext);
        apnButton = (Button) view.findViewById(R.id.apn_set_button);
        loraEdittext = (EditText) view.findViewById(R.id.Lora_edittext);
        loraButton = (Button) view.findViewById(R.id.Lora_set_button);

        ip1 = (EditText) view.findViewById(R.id.ip_1_edittext);
        ip2 = (EditText) view.findViewById(R.id.ip_2_edittext);
        ip3 = (EditText) view.findViewById(R.id.ip_3_edittext);
        ip4 = (EditText) view.findViewById(R.id.ip_4_edittext);

        port1 = (EditText) view.findViewById(R.id.port_1_edittext);
        port2 = (EditText) view.findViewById(R.id.port_2_edittext);
        port3 = (EditText) view.findViewById(R.id.port_3_edittext);
        port4 = (EditText) view.findViewById(R.id.port_4_edittext);

        set1 = (Button) view.findViewById(R.id.gprs_1_button);
        set2 = (Button) view.findViewById(R.id.gprs_2_button);
        set3 = (Button) view.findViewById(R.id.gprs_3_button);
        set4 = (Button) view.findViewById(R.id.gprs_4_button);
        tcp1 = (Button) view.findViewById(R.id.tcp_1_button);
        tcp2 = (Button) view.findViewById(R.id.tcp_2_button);
        tcp3 = (Button) view.findViewById(R.id.tcp_3_button);
        tcp4 = (Button) view.findViewById(R.id.tcp_4_button);
        rtuTimeTextView = (TextView) view.findViewById(R.id.rtu_time);
        timeButton = (Button) view.findViewById(R.id.time_button);

        resetFactoryButton = (Button) view.findViewById(R.id.reset_factory);
        saveResetButton = (Button) view.findViewById(R.id.save_and_reset);
    }

    @Override
    public void initData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadNetCfg);
        timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);
    }

    @Override
    public void setListener()
    {
        set1.setOnClickListener(this);
        set2.setOnClickListener(this);
        set3.setOnClickListener(this);
        set4.setOnClickListener(this);
        tcp1.setOnClickListener(this);
        tcp2.setOnClickListener(this);
        tcp3.setOnClickListener(this);
        tcp4.setOnClickListener(this);
        apnButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        loraButton.setOnClickListener(this);
        rtuTimeTextView.setOnClickListener(this);
        resetFactoryButton.setOnClickListener(this);
        saveResetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int centerNum = -1;
        String ip,port,content="";
        switch (v.getId())
        {
            case R.id.apn_set_button:
                content = apnEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(content))
                {
                    ToastUtil.showToastLong(getString(R.string.APN_cannot_be_empty));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAPN + content);

                break;
            case R.id.Lora_set_button:
                content = loraEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(content))
                {
                    ToastUtil.showToastLong(getString(R.string.Lora_empty));
                    return;
                }
                centerNum = Integer.parseInt(content);
                if (centerNum < 1 || centerNum > 32)
                {
                    ToastUtil.showToastLong(getString(R.string.Lora_channel_range));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetLoraChannel + content);

                break;
            case R.id.reset_factory:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.RESETALL);
                break;
            case R.id.save_and_reset:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.RESETUNIT);
                break;
            case R.id.tcp_1_button:
                ip = ip1.getText().toString().trim();

//                if (isIP(ip) == false)
//                {
//                    ToastUtil.showToastLong(getString(R.string.Configured_IP_parameters) + ip + getString(R.string.illegal));
//                }
                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetIP +  1 + " " +ip;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.gprs_1_button:
                port = port1.getText().toString().trim();

                if (TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                centerNum = Integer.parseInt(port);
                if (centerNum < 0 || centerNum > 65535)
                {
                    ToastUtil.showToastLong(getString(R.string.port_range));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetPort +  1 + " " + port;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.tcp_2_button:
                ip = ip2.getText().toString().trim();

                if (isIP(ip) == false)
                {
                    ToastUtil.showToastLong(getString(R.string.Configured_IP_parameters) + ip + getString(R.string.illegal));
                }
                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetIP +  2 + " " + ip;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.gprs_2_button:
                port = port2.getText().toString().trim();

                if (TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                centerNum = Integer.parseInt(port);
                if (centerNum < 0 || centerNum > 65535)
                {
                    ToastUtil.showToastLong(getString(R.string.port_range));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetPort +  2 + " " + port;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.tcp_3_button:
                ip = ip3.getText().toString().trim();

                if (isIP(ip) == false)
                {
                    ToastUtil.showToastLong(getString(R.string.Configured_IP_parameters) + ip + getString(R.string.illegal));
                }
                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetIP +  3 + " " + ip;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.gprs_3_button:
                port = port3.getText().toString().trim();

                if (TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                centerNum = Integer.parseInt(port);
                if (centerNum < 0 || centerNum > 65535)
                {
                    ToastUtil.showToastLong(getString(R.string.port_range));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetPort +  3 + " " + port;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.tcp_4_button:
                ip = ip4.getText().toString().trim();

                if (isIP(ip) == false)
                {
                    ToastUtil.showToastLong(getString(R.string.Configured_IP_parameters) + ip + getString(R.string.illegal));
                }
                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetIP +  4 + " " + ip;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.gprs_4_button:
                port = port4.getText().toString().trim();

                if (TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                centerNum = Integer.parseInt(port);
                if (centerNum < 0 || centerNum > 65535)
                {
                    ToastUtil.showToastLong(getString(R.string.port_range));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetNetPort +  4 + " " + port;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.time_button:
                if (TextUtils.isEmpty(setTime))
                {
                    SocketUtil.getSocketUtil().sendContent(ConfigParams.SetRTC + sendTimeFormat.format(System.currentTimeMillis()));
                }
                else
                {
                    SocketUtil.getSocketUtil().sendContent(ConfigParams.SetRTC + setTime);
                }
                break;
            case R.id.rtu_time:
//                ServiceUtils.getServiceUtils().seletDate(getActivity());
                break;
            default:
                break;

        }

    }

    public boolean isIP(String addr)
    {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        //============对之前的ip判断的bug在进行判断
        if (ipAddress==true){
            String ips[] = addr.split("\\.");
            if(ips.length==4){
                try{
                    for(String ip : ips){
                        if(Integer.parseInt(ip)<0|| Integer.parseInt(ip)>255){
                            return false;
                        }
                    }
                }catch (Exception e){
                    return false;
                }
                return true;
            }else{
                return false;
            }
        }
        return ipAddress;
    }

    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        if (id == UiEventEntry.READ_DATA)
        {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content))
            {
                return;
            }
            setData(result);
        }
        else if (id == UiEventEntry.SELECT_TIME)
        {
            String result = (String) args[0];
            rtuTimeTextView.setText(result);
            setTime = (String) args[1];
        }
    }

    private void setData(String result)
    {
        String data = "";
        if (result.contains(ConfigParams.SetAPN))
        {
            data = result.replaceAll(ConfigParams.SetAPN, "").trim();
            apnEdittext.setText(data);
        }
        else if (result.contains(ConfigParams.SetLoraChannel))
        {
            data = result.replaceAll(ConfigParams.SetLoraChannel, "").trim();
            loraEdittext.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetIP + "1"))
        {
            data = result.replaceAll(ConfigParams.SetNetIP + "1", "").trim();
            ip1.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetIP + "2"))
        {
            data = result.replaceAll(ConfigParams.SetNetIP + "2", "").trim();
            ip2.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetIP + "3"))
        {
            data = result.replaceAll(ConfigParams.SetNetIP + "3", "").trim();
            ip3.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetIP + "4"))
        {
            data = result.replaceAll(ConfigParams.SetNetIP + "4", "").trim();
            ip4.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetPort + "1"))
        {
            data = result.replaceAll(ConfigParams.SetNetPort + "1", "").trim();
            port1.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetPort + "2"))
        {
            data = result.replaceAll(ConfigParams.SetNetPort + "2", "").trim();
            port2.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetPort + "3"))
        {
            data = result.replaceAll(ConfigParams.SetNetPort + "3", "").trim();
            port3.setText(data);
        }
        else if (result.contains(ConfigParams.SetNetPort + "4"))
        {
            data = result.replaceAll(ConfigParams.SetNetPort + "4", "").trim();
            port4.setText(data);
        }
        else if (result.contains(ConfigParams.SetRTC.trim()))
        {
            String timeTemp = result.replaceAll(ConfigParams.SetRTC.trim(), "").trim();
            String time = timeTemp.replaceAll(" ", "0").trim();
            Log.i(TAG, "time:" + time);
            String month = time.substring(4,6);
            int intMonth = Integer.valueOf(month);
            if (intMonth > 12 || intMonth <1){

                ToastUtil.showToastLong(getString(R.string.Month_error));
                return;
            }
            int day = Integer.valueOf(time.substring(6,8));

            if (day > 31 || intMonth < 1){

                ToastUtil.showToastLong(getString(R.string.Date_error));
                return;
            }

            int hour = Integer.valueOf(time.substring(8,10));
            int min = Integer.valueOf(time.substring(10,12));
            int s= Integer.valueOf(time.substring(12,14));
            if (hour > 24 || hour < 0 || min > 60 || min < 0 || s > 60 || s < 0 ){

                ToastUtil.showToastLong(getString(R.string.Time_error1));
                return;
            }
            String rtuTime = ServiceUtils.getTime(time,getActivity());
            if (rtuTimeTextView != null && rtuTime != null)
            {
                rtuTimeTextView.setText(rtuTime);
                setTime = time;
            }
        }

    }
}
