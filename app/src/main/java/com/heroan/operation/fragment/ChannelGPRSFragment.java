package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

/**
 * GPRS设置界面
 * Created by Vcontrol on 2017/03/27.
 */

public class ChannelGPRSFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{

    private static final String TAG = ChannelGPRSFragment.class.getSimpleName();
    //信道连接方式1 2 3 4
    private RadioGroup noeth1ChannelGroup;
    private RadioGroup noeth2ChannelGroup;
    private RadioGroup noeth3ChannelGroup;
    private RadioGroup noeth4ChannelGroup;

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

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_setting_channel_gprs;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initComponentViews(View view)
    {

        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        noeth1ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_1);
        noeth2ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_2);
        noeth3ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_3);
//        noeth4ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_4);

        ip1 = (EditText) view.findViewById(R.id.ip_1_edittext);
        ip2 = (EditText) view.findViewById(R.id.ip_2_edittext);
        ip3 = (EditText) view.findViewById(R.id.ip_3_edittext);
//        ip4 = (EditText) view.findViewById(R.id.ip_4_edittext);

        port1 = (EditText) view.findViewById(R.id.port_1_edittext);
        port2 = (EditText) view.findViewById(R.id.port_2_edittext);
        port3 = (EditText) view.findViewById(R.id.port_3_edittext);
//        port4 = (EditText) view.findViewById(R.id.port_4_edittext);

        set1 = (Button) view.findViewById(R.id.gprs_1_button);
        set2 = (Button) view.findViewById(R.id.gprs_2_button);
        set3 = (Button) view.findViewById(R.id.gprs_3_button);
//        set4 = (Button) view.findViewById(R.id.gprs_4_button);

        initView(view);

    }

    private void initView(final View view)
    {
        noeth1ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.Setconnect_Type1;
                if (checkedId == R.id.tcp_1)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.udp_1)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
            }
        });
        noeth2ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.Setconnect_Type2;
                if (checkedId == R.id.tcp_2)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.udp_2)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
            }
        });
        noeth3ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.Setconnect_Type3;
                if (checkedId == R.id.tcp_3)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.udp_3)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
            }
        });
//        noeth4ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId)
//            {
//                View checkView = view.findViewById(checkedId);
//                if (!checkView.isPressed())
//                {
//                    return;
//                }
//                String content = ConfigParams.Setconnect_Type4;
//                if (checkedId == R.id.tcp_4)
//                {
//                    SocketUtil.getSocketUtil().sendContent(content + "0");
//                }
//                else if (checkedId == R.id.udp_4)
//                {
//                    SocketUtil.getSocketUtil().sendContent(content + "1");
//                }
//            }
//        });
    }

    @Override
    public void initData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadCommPara2);
    }

    @Override
    public void setListener()
    {
        set1.setOnClickListener(this);
        set2.setOnClickListener(this);
        set3.setOnClickListener(this);
//        set4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        String ip,port,content="";
        switch (view.getId())
        {
            case R.id.gprs_1_button:
                ip = ip1.getText().toString().trim();
                port = port1.getText().toString().trim();

                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetIP +  1 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);

                break;
            case R.id.gprs_2_button:
                ip = ip2.getText().toString().trim();
                port = port2.getText().toString().trim();

                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetIP +  2 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);

                break;
            case R.id.gprs_3_button:
                ip = ip3.getText().toString().trim();
                port = port3.getText().toString().trim();

                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }
                // 设置状态参数
                content = ConfigParams.SetIP +  3 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
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
//                content = ConfigParams.SetIP +  4 + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
//                break;
            default:
                break;
        }

        SocketUtil.getSocketUtil().sendContent(content);
    }


    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        String result = (String) args[0];
        String content = (String) args[1];
        if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content))
        {
            return;
        }
        setData(result);
    }

    private void setData(String result)
    {
        String data = "";
        String[] portArray = null;

        //信道连接方式 1 2 3 4
        if (result.contains(ConfigParams.Setconnect_Type))
        {
            data = result.replaceAll(ConfigParams.Setconnect_Type, "").trim();
            if (TextUtils.isEmpty(data))
            {
                return;
            }
            String statu = ServiceUtils.hexString2binaryString(data);
            Log.d(TAG, "status:" + statu);

            if (statu.length() > 0)
            {
                char type = statu.charAt(statu.length() - 1);
                if ('0' == type)
                {//tcp
                    noeth1ChannelGroup.check(R.id.tcp_1);
                }
                else if ('1' == type)
                {
                    noeth1ChannelGroup.check(R.id.udp_1);
                }
                char type2 = statu.charAt(statu.length() - 2);
                if ('0' == type2)
                {//tcp
                    noeth2ChannelGroup.check(R.id.tcp_2);
                }
                else if ('1' == type2)
                {
                    noeth2ChannelGroup.check(R.id.udp_2);
                }
                char type3 = statu.charAt(statu.length() - 3);
                if ('0' == type3)
                {//tcp
                    noeth3ChannelGroup.check(R.id.tcp_3);
                }
                else if ('1' == type3)
                {
                    noeth3ChannelGroup.check(R.id.udp_3);
                }
                char type4 = statu.charAt(statu.length() - 4);
                if ('0' == type4)
                {//tcp
                    noeth4ChannelGroup.check(R.id.tcp_4);
                }
                else if ('1' == type4)
                {
                    noeth4ChannelGroup.check(R.id.udp_4);
                }
            }

        }
        else if (result.contains(ConfigParams.SetIP + "1"))
        {
            portArray = result.split(ConfigParams.setPort.trim());
            ip1.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 1, "").trim()));
            if (portArray.length > 1)
            {
                port1.setText(portArray[1].trim());
            }
        }
        else if (result.contains(ConfigParams.SetIP + "2"))
        {
            portArray = result.split(ConfigParams.setPort.trim());
            ip2.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 2, "").trim()));
            if (portArray.length > 1)
            {
                port2.setText(portArray[1].trim());
            }
        }
        else if (result.contains(ConfigParams.SetIP + "3"))
        {
            portArray = result.split(ConfigParams.setPort.trim());
            ip3.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 3, "").trim()));
            if (portArray.length > 1)
            {
                port3.setText(portArray[1].trim());
            }
        }
//        else if (result.contains(ConfigParams.SetIP + "4"))
//        {
//            portArray = result.split(ConfigParams.setPort.trim());
//            ip4.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIP + 4, "").trim()));
//            if (portArray.length > 1)
//            {
//                port4.setText(portArray[1].trim());
//            }
//        }

    }

}
