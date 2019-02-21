package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;
import com.heroan.operation.adapter.ChannelAdapter;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;
import com.heroan.operation.view.CustomListView;

import zuo.biao.library.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class ChannelFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener
{

    private static final String TAG = ChannelFragment.class.getSimpleName();
    private CustomListView channelListView;
    private ChannelAdapter channelAdapter;
    private LinearLayout ethLayout;
    private LinearLayout noethLayout;
    private boolean isEth = false;

    private Button devgprsButton;
    private Button gprsButton;
    private TextView gprsTextView;
    private TextView ipEditView;
    private TextView portEditView;
    private EditText devipEditText;

    private TextView ipTextView;
    private TextView macTextView;
    private Spinner ipSpinner;

    private RadioGroup ethChannelGroup;
    private RadioGroup noeth1ChannelGroup;
    private RadioGroup noeth2ChannelGroup;
    private RadioGroup noeth3ChannelGroup;
    private RadioGroup noeth4ChannelGroup;

    private RadioGroup beiDouGroup;

    private int currentConfig = ConfigParams.IP_DHCP;

    private SimpleSpinnerAdapter ipAdapter;
    private String[] ipItems;


    private List<String> channelList = new ArrayList<>();

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_setting_channel;
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
        channelListView = (CustomListView) view.findViewById(R.id.channel_listview);
        ethLayout = (LinearLayout) view.findViewById(R.id.eth_layout);
        noethLayout = (LinearLayout) view.findViewById(R.id.noeth_layout);

        devgprsButton = (Button) view.findViewById(R.id.setip_button);
        gprsButton = (Button) view.findViewById(R.id.gprs_button);
        gprsTextView = (TextView) view.findViewById(R.id.gprs_textview);
        gprsTextView.setText(getString(R.string.center));
        devipEditText = (EditText) view.findViewById(R.id.setip_edittext);
        ipEditView = (EditText) view.findViewById(R.id.ip_edittext);
        portEditView = (EditText) view.findViewById(R.id.port_edittext);
        ipTextView = (TextView) view.findViewById(R.id.ip_add);
        macTextView = (TextView) view.findViewById(R.id.mac_add);
        ipSpinner = (Spinner) view.findViewById(R.id.ip_spinner);

        ethChannelGroup = (RadioGroup) view.findViewById(R.id.eth_channel_group);
        noeth1ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_1);
        noeth2ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_2);
        noeth3ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_3);
        noeth4ChannelGroup = (RadioGroup) view.findViewById(R.id.channel_group_4);

        beiDouGroup = (RadioGroup) view.findViewById(R.id.beidou_radiogroup);

        initView(view);


        setView(getArguments());

    }

    private void initView(final View view)
    {
        ethChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
                if (checkedId == R.id.eth_tcp)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.eth_udp)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
            }
        });
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
        noeth4ChannelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.Setconnect_Type4;
                if (checkedId == R.id.tcp_4)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.udp_4)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
            }
        });

        beiDouGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetBeidouType;
                if (checkedId == R.id.beidou_button)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "00");
                }
                else if (checkedId == R.id.beidou_button_2)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "01");

                }

            }
        });
    }

    public void refreshData()
    {
        if (isEth)
        {
            ipItems = getResources().getStringArray(R.array.ip_config);
            ipAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, ipItems);
            ipSpinner.setAdapter(ipAdapter);
            SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadStaticIP);

            OperationApplication.applicationHandler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadCommPara2);
                }
            },1500);
        }
        else
        {
            channelList.clear();
            channelAdapter = new ChannelAdapter(getActivity(), channelListView);
            channelListView.setAdapter(channelAdapter);
            // 请求数据

            SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadCommPara2);
        }

    }

    private void setView(Bundle bundle)
    {

        if (bundle != null)
        {
            int type = bundle.getInt(UiEventEntry.CURRENT_RTU_NAME);

            if (type == UiEventEntry.WRU_2801)
            {
                noethLayout.setVisibility(View.GONE);
                ethLayout.setVisibility(View.VISIBLE);
                isEth = true;
            }
            else
            {
                noethLayout.setVisibility(View.VISIBLE);
                ethLayout.setVisibility(View.GONE);
                isEth = false;
            }
        }
        else
        {
            noethLayout.setVisibility(View.VISIBLE);
            ethLayout.setVisibility(View.GONE);
            isEth = false;
        }
    }


    @Override
    public void initData()
    {
        refreshData();
    }

    @Override
    public void setListener()
    {
        devgprsButton.setOnClickListener(this);
        gprsButton.setOnClickListener(this);

        ipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ipAdapter.setSelectedItem(position);
                currentConfig = position;

                if (currentConfig == ConfigParams.IP_DHCP)
                {
                    devipEditText.setEnabled(false);
                }
                else
                {
                    devipEditText.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
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

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.setip_button:
                String devip = devipEditText.getText().toString().trim();
                if (TextUtils.isEmpty(devip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                String content = ConfigParams.SetStaticIP + "1 " + devip + " " + ConfigParams.IPFlags + currentConfig;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.gprs_button:
                String ip = ipEditView.getText().toString().trim();
                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_IP_address_cannot_be_empty));
                    return;
                }
                String port = portEditView.getText().toString().trim();
                if (TextUtils.isEmpty(ip))
                {
                    ToastUtil.showToastLong(getString(R.string.The_port_number_cannot_be_empty));
                    return;
                }

                String content1 = ConfigParams.SetIP + 1 + " " + ip + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
                // String content1 = ConfigParams.SetIP + "1 " +
                // ServiceUtils.getRegxIp(ip) + " " + ConfigParams.PORT +
                // currentConfig;
                SocketUtil.getSocketUtil().sendContent(content1);
                break;

            default:
                break;
        }

    }

    private void setData(String result)
    {
        if (isEth)
        {
            if (result.contains(ConfigParams.SetStaticIP))
            {
                devipEditText.setText(ServiceUtils.getRemoteIp(result.replaceAll(ConfigParams.SetStaticIP, "").trim()));
            }
            else if (result.contains(ConfigParams.IPFlags))
            {
                String data = result.replaceAll(ConfigParams.IPFlags, "").trim();
                if (ServiceUtils.isNumeric(data))
                {
                    int config = Integer.parseInt(data);
                    if (config >= 0 || config <= 1)
                    {
                        ipSpinner.setSelection(config);
                    }
                }
            }
            else if (result.contains(ConfigParams.Module_MAC))
            {
                String mac = result.replaceAll(ConfigParams.Module_MAC, "").replaceAll(" ", "0");
                mac = ServiceUtils.getMac(mac);
                macTextView.setText(mac);
//                SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadCommPara2);
            }
            else if (result.contains(ConfigParams.SetIP + "1"))
            {
                // 读取参数，更新适配器
                String[] ipArray = result.split(ConfigParams.setPort.trim());
                if (ipArray != null)
                {
                    if (ipArray.length > 0)
                    {
                        ipEditView.setText(ServiceUtils.getRemoteIp(ipArray[0].replaceAll(ConfigParams.SetIP + 1, "").trim()));
                    }
                    if (ipArray.length > 1)
                    {
                        portEditView.setText(ipArray[1].trim());
                    }
                }
            }
            else if (result.contains(ConfigParams.Setconnect_Type))
            {
                String data = result.replaceAll(ConfigParams.Setconnect_Type, "").trim();
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
                        ethChannelGroup.check(R.id.eth_tcp);
                    }
                    else
                    {
                        ethChannelGroup.check(R.id.eth_udp);
                    }
                }
            }
        }
        else
        {

            String data = "";
            if (!(result.contains(ConfigParams.Setconnect_Type)))
            {
                channelList.add(result);
            }
            if (result.contains(ConfigParams.Setconnect_Type))
            {
                OperationApplication.applicationHandler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadCommPara3);
                    }
                },1500);


            }
            if (result.contains(ConfigParams.Setcenternumber + "4"))
            {// 更新adapter
                channelAdapter.setData(channelList);
                channelAdapter.notifyDataSetChanged();
                int pos = channelListView.getFirstVisiblePosition();
                channelListView.setSelection(pos);
            }
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
                    else
                    {
                        noeth1ChannelGroup.check(R.id.udp_1);
                    }
                    char type2 = statu.charAt(statu.length() - 2);
                    if ('0' == type2)
                    {//tcp
                        noeth2ChannelGroup.check(R.id.tcp_2);
                    }
                    else
                    {
                        noeth2ChannelGroup.check(R.id.udp_2);
                    }
                    char type3 = statu.charAt(statu.length() - 3);
                    if ('0' == type3)
                    {//tcp
                        noeth3ChannelGroup.check(R.id.tcp_3);
                    }
                    else
                    {
                        noeth3ChannelGroup.check(R.id.udp_3);
                    }
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
            else if (result.contains(ConfigParams.SetBeidouType.trim()))
            {// 北斗厂家
                data = result.replaceAll(ConfigParams.SetBeidouType, "").trim();
                if (data.equals("0"))
                {
                    beiDouGroup.check(R.id.beidou_button);
                }
                else
                {
                    beiDouGroup.check(R.id.beidou_button_2);
                }
            }
            channelAdapter.setData(channelList);
            channelAdapter.notifyDataSetChanged();
            int pos = channelListView.getFirstVisiblePosition();
            channelListView.setSelection(pos);
        }
    }

}
