package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;
/**
 * Created by linxi on 2017/12/22.
 */

public class ValveControlRelayFragment extends BaseFragment implements View.OnClickListener,EventNotifyHelper.NotificationCenterDelegate

{
    private static final java.lang.String TAG = ValveControlRelayFragment.class.getSimpleName() ;

    private Switch elecrelaySwitch1;
    private Switch elecrelaySwitch2;
    private Switch elecrelaySwitch3;
    private Switch elecrelaySwitch4;
    private RadioGroup valveTypeRadioGroup;
    private RadioButton butterflyRadiobutton;
    private RadioButton pulseRadiobutton;
    private RadioButton vaRadiobutton;
    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.valve_relay);
        initView();
        initData();
        initEvent();
        return view;
    }


    public void initView(final View view)
    {
        valveTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetvalveType;
                switch (i)
                {
                    case R.id.Butterfly_valve_radiobtton:
                        String butterfly = content + "1";
                        ServiceUtils.sendData(butterfly);

                        break;
                    case R.id.Pulse_solenoid_valveradiobtton:
                        String pulse = content + "2";
                        ServiceUtils.sendData(pulse);

                        break;
                    case R.id.valveradiobtton_485:
                        String va = content + "3";
                        ServiceUtils.sendData(va);

                        break;

                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        elecrelaySwitch1 = (Switch) view.findViewById(R.id.elecrelay_switch1);
        elecrelaySwitch2 = (Switch) view.findViewById(R.id.elecrelay_switch2);
        elecrelaySwitch3 = (Switch) view.findViewById(R.id.elecrelay_switch3);
        elecrelaySwitch4 = (Switch) view.findViewById(R.id.elecrelay_switch4);
        valveTypeRadioGroup = (RadioGroup) view.findViewById(R.id.valve_Type);
        butterflyRadiobutton = (RadioButton) view.findViewById(R.id.Butterfly_valve_radiobtton);
        pulseRadiobutton = (RadioButton) view.findViewById(R.id.Pulse_solenoid_valveradiobtton);
        vaRadiobutton = (RadioButton) view.findViewById(R.id.valveradiobtton_485);

        initView(view);
    }

    @Override
    public void initData()
    {
        ServiceUtils.sendData(ConfigParams.ReadSystemPara6);

    }

    @Override
    public void initEvent() {
        elecrelaySwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (elecrelaySwitch1.isPressed())
                {

                    if (!isChecked)
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "0" + " " + "1");
                    }
                    else
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "1" + " " + "1");

                    }
                }

            }
        });

        elecrelaySwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (elecrelaySwitch2.isPressed())
                {

                    if (!isChecked)
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "0" + " " + "2");
                    }
                    else
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "1" + " " + "2");

                    }
                }

            }
        });

        elecrelaySwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (elecrelaySwitch3.isPressed())
                {

                    if (!isChecked)
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "0" + " " + "3");
                    }
                    else
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "1" + " " + "3");

                    }
                }

            }
        });
        elecrelaySwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (elecrelaySwitch4.isPressed())
                {

                    if (!isChecked)
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "0" + " " + "4");
                    }
                    else
                    {
                        ServiceUtils.sendData(ConfigParams.elecrelay + "1" + " " + "4");

                    }
                }

            }
        });
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
        if (result.contains(ConfigParams.elecrelay.trim()))
        {
            String data = result.replaceAll(ConfigParams.elecrelay,"").trim();

            String[] content = data.split(ConfigParams.Space.trim());

            if ("1".equals(content[3]))
            {
                if ("0".equals(content[1]))
                {
                    elecrelaySwitch1.setChecked(false);
                }
                else
                {
                    elecrelaySwitch1.setChecked(true);
                }
            }
            else if ("2".equals(content[3]))
            {
                if ("0".equals(content[1]))
                {
                    elecrelaySwitch2.setChecked(false);
                }
                else
                {
                    elecrelaySwitch2.setChecked(true);
                }
            }
            else if ("3".equals(content[3]))
            {
                if ("0".equals(content[1]))
                {
                    elecrelaySwitch3.setChecked(false);
                }
                else
                {
                    elecrelaySwitch3.setChecked(true);
                }
            }
            else if ("4".equals(content[3]))
            {
                if ("0".equals(content[1]))
                {
                    elecrelaySwitch4.setChecked(false);
                }
                else
                {
                    elecrelaySwitch4.setChecked(true);
                }
            }
        }
        else if (result.contains(ConfigParams.SetvalveType.trim()))
        {
            String s = result.replaceAll(ConfigParams.SetvalveType.trim(), "").trim();
            if (TextUtils.isEmpty(s))
            {
                return;
            }
            if (s.equals("1"))
            {
                butterflyRadiobutton.setChecked(true);
            }
            else if (s.equals("2"))
            {
                pulseRadiobutton.setChecked(true);
            }
            else if (s.equals("3"))
            {
                vaRadiobutton.setChecked(true);
            }
        }
    }
}
