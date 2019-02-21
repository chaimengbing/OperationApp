package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class LruSysPamarsFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{

    private static final String TAG = LruSysPamarsFragment.class.getSimpleName();

    private byte[] head = new byte[20];

    private EditText macAdd;
    private EditText standBy;
//    private EditText overTime;
//    private EditText idLogic;
//    private EditText tranAdd;
//    private EditText physicsChannel;
//    private EditText airSpeed;

    private Button macAddButton;
    private Button standByButton;
//    private Button overTimeButton;
//    private Button idLogicButton;
//    private Button tranAddButton;
//    private Button physicsChannelButton;
//    private Button airSpeedButton;
    private Button initLru;
    private RadioGroup simConfigRadioGroup;
    private RadioGroup simConfigRadioGroup1;

    private Switch valveSwitch_1;
    private Switch valveSwitch_2;


    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_setting_lru_system;
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

        macAdd = (EditText) view.findViewById(R.id.mac_add);
        standBy = (EditText) view.findViewById(R.id.standby_time);
//        overTime = (EditText) view.findViewById(R.id.over_time);
//        idLogic = (EditText) view.findViewById(R.id.id_logic);
//        tranAdd = (EditText) view.findViewById(R.id.tran_add);
//        physicsChannel = (EditText) view.findViewById(R.id.physics_channel);
//        airSpeed = (EditText) view.findViewById(R.id.air_speed);

        macAddButton = (Button) view.findViewById(R.id.mac_add_setting_button);
        standByButton = (Button) view.findViewById(R.id.standby_time_setting_button);

        valveSwitch_1 = (Switch) view.findViewById(R.id.valve_switch_1);
        valveSwitch_2 = (Switch) view.findViewById(R.id.valve_switch_2);
//        overTimeButton = (Button) view.findViewById(R.id.over_time_setting_button);
//        idLogicButton = (Button) view.findViewById(R.id.id_logic_setting_button);
//        tranAddButton = (Button) view.findViewById(R.id.tran_add_setting_button);
//        physicsChannelButton = (Button) view.findViewById(R.id.physics_channel_setting_button);
//        airSpeedButton = (Button) view.findViewById(R.id.air_speed_setting_button);

        initLru = (Button) view.findViewById(R.id.init_lru);
        simConfigRadioGroup = (RadioGroup) view.findViewById(R.id.sim_config_radiogroup);
        simConfigRadioGroup1 = (RadioGroup) view.findViewById(R.id.sim_config_radiogroup1);
        initView(view);


        initHead();


    }

    private void initHead()
    {
//        public static final String LRU_HEAD = "A5013412ffff00";
//        //设置物理地址0x55，	数据域4个字节，低字节在先
//        public static final String LRU_MAC_ADD = "554";
        head[0] = (byte) 0xA5;
        head[1] = (byte) 0x01;
        head[2] = (byte) 0x34;
        head[3] = (byte) 0x12;
        head[4] = (byte) 0xff;
        head[5] = (byte) 0xff;
        head[6] = (byte) 0x00;
    }

    @Override
    public void initData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadParameters);
    }

    private void initView(final View view)
    {
        simConfigRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = "";
                if (checkedId == R.id.sim_config_telecom)
                {
                    content = ConfigParams.PulsePolar + "1";
                }
                else
                {
                    content = ConfigParams.PulsePolar + "0";
                }
                SocketUtil.getSocketUtil().sendContent(content);
            }
        });


        simConfigRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = "";
                if (checkedId == R.id.sim_config_telecom1)
                {
                    content = ConfigParams.SensorPolar + "1";
                }
                else
                {
                    content = ConfigParams.SensorPolar + "0";
                }
                SocketUtil.getSocketUtil().sendContent(content);
            }
        });
    }

    @Override
    public void setListener()
    {
        macAddButton.setOnClickListener(this);
        standByButton.setOnClickListener(this);
        initLru.setOnClickListener(this);

        valveSwitch_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (valveSwitch_1.isPressed())
                {

                    if (!isChecked)
                    {
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.valve1ctl + "2");
                    }
                    else
                    {
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.valve1ctl + "1");

                    }
                }

            }
        });

        valveSwitch_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (valveSwitch_2.isPressed())
                {

                    if (!isChecked)
                    {
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.valve2ctl + "2");
                    }
                    else
                    {
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.valve2ctl + "1");

                    }
                }

            }
        });
    }


    private String getLowHex(String data)
    {
        List<String> list = new ArrayList<>();
        String result = "";
        byte[] bytes = data.getBytes();
        Log.i(TAG, bytes.length + "");


        for (int i = 0; i < data.length(); i += 2)
        {
            String s = data.substring(i, i + 2);
            list.add(s);

        }

        for (int i = (list.size() - 1); i >= 0; i--)
        {
            result += list.get(i);
        }
        return result;
    }

    @Override
    public void onClick(View view)
    {
        String data = "";
        String type = "";
        int count = 0;
        switch (view.getId())
        {
            case R.id.mac_add_setting_button:
                data = macAdd.getText().toString();
                if (TextUtils.isEmpty(data))
                {
                    return;
                }
                count = Integer.parseInt(data);
                if (count < 0 || count > 31)
                {
                    ToastUtil.showToastLong(getString(R.string.Wireless_channel_range));
                    return;
                }
                type = ConfigParams.Channel;
                break;
            case R.id.standby_time_setting_button:
                data = standBy.getText().toString().trim();
                if (TextUtils.isEmpty(data))
                {
                    return;
                }
                count = Integer.parseInt(data);
                if (count < 75 || count > 200)
                {
                    ToastUtil.showToastLong(getString(R.string.Pulse_duration_range));
                    return;
                }
                type = ConfigParams.PulseTime;
                break;
            case R.id.init_lru:

                data = "";
                type = ConfigParams.FactoryInitialization;
                break;

            default:
                break;
        }

        String content = type + data;
        SocketUtil.getSocketUtil().sendContent(content);
    }


    private byte[] getCheck()
    {
        byte[] check = new byte[2];
        check[0] = 0;
        check[1] = 0;

        for (int i = 0; i < head.length; i++)
        {
            check[0] += head[i];
            check[1] ^= head[i];
        }
        Log.i(TAG, "check[0]:" + check[0] + ",check[1]:" + check[1]);
        return check;
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
    }

    private void setData(String result)
    {
        String data;
        int value;
        if (result.contains(ConfigParams.Channel.trim()))
        {// 物理地址
            data = result.replaceAll(ConfigParams.Channel, "");
            macAdd.setText(data);
        }
        else if (result.contains(ConfigParams.PulseTime.trim()))
        {// 待机时间间隔
            data = result.replaceAll(ConfigParams.PulseTime, "");
            standBy.setText(data);
        }
        else if (result.contains(ConfigParams.PulsePolar.trim()))
        {// 卡配置
            data = result.replaceAll(ConfigParams.PulsePolar, "").trim();
            if ("0".equals(data))
            {
                simConfigRadioGroup.check(R.id.sim_config_mobile);
            }
            else if ("1".equals(data))
            {
                simConfigRadioGroup.check(R.id.sim_config_telecom);
            }
        }
        else if (result.contains(ConfigParams.SensorPolar.trim()))
        {// 卡配置
            data = result.replaceAll(ConfigParams.SensorPolar, "").trim();
            if ("0".equals(data))
            {
                simConfigRadioGroup1.check(R.id.sim_config_mobile1);
            }
            else if ("1".equals(data))
            {
                simConfigRadioGroup1.check(R.id.sim_config_telecom1);
            }
        }
    }

}
