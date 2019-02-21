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

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

/**
 * 通信参数
 * Created by Vcontrol on 2016/11/23.
 */

public class CommParamsFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{

    private Spinner timeSpinner;
    private Spinner picSpinner;
    private String[] timeItems;
    private String[] pictimeItems;
    private SimpleSpinnerAdapter timeAdapter;
    private SimpleSpinnerAdapter pictimeAdapter;

    private LinearLayout time1;
    private LinearLayout time2;

    private boolean isSelectTime = true;

    private Spinner commTypeSpinner;
    private String[] commItems;
    private SimpleSpinnerAdapter commAdapter;

//    private EditText center1AddEdittext;
//    private EditText center2AddEdittext;
//    private EditText center3AddEdittext;
//    private EditText center4AddEdittext;
    private EditText dayBeginEdittext;
    private EditText timeEdittext;
    private EditText apnEdittext;
    private EditText userEdittext;
    private EditText apnPsdEdittext;
    private EditText commPsdEdittext;
    private EditText commNumEdittext;

//    private Button center1AddButton;
//    private Button center2AddButton;
//    private Button center3AddButton;
//    private Button center4AddButton;
    private Button dayBeginButton;
    private Button commNumButton;
    private Button timeButton;
    private Button apnButton;
    private Button userButton;
    private Button apnPsdButton;
    private Button commPsdButton;

    // 召测
    private RadioGroup callTestGroup;
    //卡类型、
    private RadioGroup simTypeGroup;

    private int currentType = -1;
    private LinearLayout apnLayout;
    private LinearLayout picLayout;

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_setting_comm_prmams1;
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
        timeSpinner = (Spinner) view.findViewById(R.id.time_interval_spinner);
        picSpinner = (Spinner) view.findViewById(R.id.pic_interval_spinner);
        commTypeSpinner = (Spinner) view.findViewById(R.id.comm_type_spinner);
        callTestGroup = (RadioGroup) view.findViewById(R.id.call_test_radiogroup);
        simTypeGroup = (RadioGroup) view.findViewById(R.id.sim_type_radiogroup);

//
//        center1AddEdittext = (EditText) view.findViewById(R.id.center_1_add_edittext);
//        center2AddEdittext = (EditText) view.findViewById(R.id.center_2_add_edittext);
//        center3AddEdittext = (EditText) view.findViewById(R.id.center_3_add_edittext);
//        center4AddEdittext = (EditText) view.findViewById(R.id.center_4_add_edittext);
        commNumEdittext = (EditText) view.findViewById(R.id.comm_num_edittext);
        dayBeginEdittext = (EditText) view.findViewById(R.id.day_begin_edittext);
        apnEdittext = (EditText) view.findViewById(R.id.apn_edittext);
        timeEdittext = (EditText) view.findViewById(R.id.time_interval_edittext);
        userEdittext = (EditText) view.findViewById(R.id.user_edittext);
        apnPsdEdittext = (EditText) view.findViewById(R.id.apn_psd_edittext);
        commPsdEdittext = (EditText) view.findViewById(R.id.comm_psd_edittext);

//        center1AddButton = (Button) view.findViewById(R.id.center_1_add_button);
//        center2AddButton = (Button) view.findViewById(R.id.center_2_add_button);
//        center3AddButton = (Button) view.findViewById(R.id.center_3_add_button);
//        center4AddButton = (Button) view.findViewById(R.id.center_4_add_button);
        commNumButton = (Button) view.findViewById(R.id.comm_num_button);
        timeButton = (Button) view.findViewById(R.id.time_interval_button);
        dayBeginButton = (Button) view.findViewById(R.id.day_begin_add_button);
        apnButton = (Button) view.findViewById(R.id.apn_set_button);
        userButton = (Button) view.findViewById(R.id.apn_user_button);
        apnPsdButton = (Button) view.findViewById(R.id.apn_psd_button);
        commPsdButton = (Button) view.findViewById(R.id.comm_psd_button);

        time1 = (LinearLayout) view.findViewById(R.id.time1);
        time2 = (LinearLayout) view.findViewById(R.id.time2);
        apnLayout = (LinearLayout) view.findViewById(R.id.apn_layout);
        picLayout = (LinearLayout) view.findViewById(R.id.pic_layout);

        if (isSelectTime)
        {
            time1.setVisibility(View.GONE);
            time2.setVisibility(View.VISIBLE);
        }
        else
        {
            time2.setVisibility(View.GONE);
            time1.setVisibility(View.VISIBLE);
        }

        initView(view);
        initEthView(getArguments());

    }

    /**
     * 判断是否是2801
     * @param bundle
     */
    private void initEthView(Bundle bundle)
    {
        if (bundle != null)
        {
            currentType = bundle.getInt(UiEventEntry.CURRENT_RTU_NAME);
            if (currentType == UiEventEntry.WRU_2801)
            {
                apnLayout.setVisibility(View.GONE);
                picLayout.setVisibility(View.GONE);
            }
        }
    }

    private void initView(final View v)
    {
        callTestGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = v.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetAcquisitionMode;
                if (checkedId == R.id.call_test)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.no_call_test)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");

                }

            }
        });
        simTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = v.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetSIMType;
                if (checkedId == R.id.sim_mobile)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.sim_unicom)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
                else if (checkedId == R.id.sim_telecom)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "2");
                }

            }
        });


    }

    @Override
    public void initData()
    {
        timeItems = getResources().getStringArray(R.array.time_interval);
        pictimeItems = getResources().getStringArray(R.array.pictime_interval);
        timeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, timeItems);
        pictimeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, pictimeItems);
        timeSpinner.setAdapter(timeAdapter);
        picSpinner.setAdapter(pictimeAdapter);

        commItems = getResources().getStringArray(R.array.comm_type);
        commAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, commItems);
        commTypeSpinner.setAdapter(commAdapter);
//        1369339469
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadCommPara1);
    }

    @Override
    public void setListener()
    {
//        center1AddButton.setOnClickListener(this);
//        center2AddButton.setOnClickListener(this);
//        center3AddButton.setOnClickListener(this);
//        center4AddButton.setOnClickListener(this);
        dayBeginButton.setOnClickListener(this);
        apnButton.setOnClickListener(this);
        apnPsdButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        commPsdButton.setOnClickListener(this);
        commNumButton.setOnClickListener(this);
        userButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        String centerAdd = "";
        int centerNum = -1;
        switch (view.getId())
        {

            case R.id.day_begin_add_button:
                centerAdd = dayBeginEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.Day_start_time_must_not_be_empty));
                    return;
                }

                centerNum = Integer.parseInt(centerAdd);
                if (centerNum < 0 || centerNum > 23)
                {
                    ToastUtil.showToastLong(getString(R.string.Daily_start_time_range));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetSendBeginTime + ServiceUtils.getStr(centerAdd, 2));

                break;
            case R.id.comm_num_button:
                centerAdd = commNumEdittext.getText().toString().trim();

                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.Communication_identifier_must_not_be_null));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetSMS + "5" + " " + ServiceUtils.getStr(centerAdd, 11));

                break;
            case R.id.apn_set_button:
                centerAdd = apnEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.APN_cannot_be_empty));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAPN + centerAdd);

                break;
            case R.id.apn_user_button:
                centerAdd = userEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.APN_user_account_cannot_be_empty));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAPNUser + centerAdd);

                break;
            case R.id.apn_psd_button:
                centerAdd = apnPsdEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.APN_password_cannot_be_empty));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAP_N_secret + centerAdd);
                break;
            case R.id.comm_psd_button:
                centerAdd = commPsdEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.Communication_password_cannot_be_empty));
                    return;
                }

                centerNum = Integer.parseInt(centerAdd);
                if (centerNum < 0 || centerNum > 65535)
                {
                    ToastUtil.showToastLong(getString(R.string.Communication_password_range));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetComPassword + ServiceUtils.getStr(centerAdd, 5));

                break;
            case R.id.time_interval_button:
                centerAdd = timeEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd))
                {
                    ToastUtil.showToastLong(getString(R.string.Scheduled_time_interval_cannot_be_null));
                    return;
                }

                centerNum = Integer.parseInt(centerAdd);
                if (centerNum < 0 || centerNum > 999)
                {
                    ToastUtil.showToastLong("getString(R.string.Regular_reporting_interval_range)");
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetPacketInterval + ServiceUtils.getStr(centerAdd, 3));

                break;

            default:
                break;
        }
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
        if (result.contains(ConfigParams.SetPacketInterval))
        {// 定时报间隔
            data = result.replaceAll(ConfigParams.SetPacketInterval.trim(), "").trim();
            if (isSelectTime)
            {
                if (ServiceUtils.isNumeric(data))
                {
                    int t = Integer.parseInt(data);
                    timeSpinner.setSelection(ServiceUtils.getTimePos(t), false);
                    timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                        {
                            timeAdapter.setSelectedItem(i);

                            String content = ConfigParams.SetPacketInterval + ServiceUtils.getTimeNum(timeItems[i]);
                            SocketUtil.getSocketUtil().sendContent(content);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent)
                        {

                        }
                    });
                }
            }
            else
            {
                timeEdittext.setText(data);
            }
        }
        if (result.contains(ConfigParams.PicSendInterval))
        {// 图片报间隔
            data = result.replaceAll(ConfigParams.PicSendInterval.trim(), "").trim();

            if (ServiceUtils.isNumeric(data))
            {
                int t = Integer.parseInt(data);

//                if (t < pictimeItems.length)
//                {
                    picSpinner.setSelection(ServiceUtils.getpicTimePos(t), false);
//                    picSpinner.setSelection();
//                }
//                e、lse
//                {
//                    picSpinner.setSelection(0, false);
//                }
                picSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        pictimeAdapter.setSelectedItem(position);
                        String content = ConfigParams.PicSendInterval + ServiceUtils.getTimeNum(pictimeItems[position]);
                        SocketUtil.getSocketUtil().sendContent(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

            }

        }
        else if (result.contains(ConfigParams.SetprotocolType))
        {// 通信协议
            data = result.replaceAll(ConfigParams.SetprotocolType.trim(), "").trim();
            if (ServiceUtils.isNumeric(data))
            {
                int t = Integer.parseInt(data);
                commTypeSpinner.setSelection(t, false);
                commTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        commAdapter.setSelectedItem(i);
                        if (i == 0)
                        {
                            return;
                        }

                        String content = ConfigParams.SetprotocolType + ServiceUtils.getStr(i + "", 2);
                        SocketUtil.getSocketUtil().sendContent(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView)
                    {

                    }
                });
            }
        }
        else if (result.contains(ConfigParams.SetAcquisitionMode.trim()))
        {// 召测模式
            data = result.replaceAll(ConfigParams.SetAcquisitionMode, "").trim();
            if (data.equals("0"))
            {
                callTestGroup.check(R.id.call_test);
            }
            else if (data.equals("1"))
            {
                callTestGroup.check(R.id.no_call_test);
            }
        }
        else if (result.contains(ConfigParams.SetSIMType.trim()))
        {// 召测模式
            data = result.replaceAll(ConfigParams.SetSIMType, "").trim();
            if (data.equals("1"))
            {
                simTypeGroup.check(R.id.sim_unicom);
            }
            else if (data.equals("2"))
            {
                simTypeGroup.check(R.id.sim_telecom);
            }
            else if (data.equals("0"))
            {
                simTypeGroup.check(R.id.sim_mobile);
            }
        }
        // 日起始时间
        else if (result.contains(ConfigParams.SetSendBeginTime))
        {
            data = result.replaceAll(ConfigParams.SetSendBeginTime, "").trim();
            dayBeginEdittext.setText(data);
        }
        // 通信识别码
        else if (result.contains(ConfigParams.SetSMS + "5"))
        {
            data = result.replaceAll(ConfigParams.SetSMS + "5", "").trim();
            commNumEdittext.setText(data);
        }
        // 中心站地址 1 2 3 4
//        else if (result.contains(ConfigParams.Setcenternumber + "1"))
//        {
//            data = result.replaceAll(ConfigParams.Setcenternumber + "1", "").trim();
//            center1AddEdittext.setText(data);
//        }
//        else if (result.contains(ConfigParams.Setcenternumber + "2"))
//        {
//            data = result.replaceAll(ConfigParams.Setcenternumber + "2", "").trim();
//            center2AddEdittext.setText(data);
//        }
//        else if (result.contains(ConfigParams.Setcenternumber + "3"))
//        {
//            data = result.replaceAll(ConfigParams.Setcenternumber + "3", "").trim();
//            center3AddEdittext.setText(data);
//        }
//        else if (result.contains(ConfigParams.Setcenternumber + "4"))
//        {
//            data = result.replaceAll(ConfigParams.Setcenternumber + "4", "").trim();
//            center4AddEdittext.setText(data);
//        }
        else if (result.contains(ConfigParams.SetAPN))
        {
            data = result.replaceAll(ConfigParams.SetAPN, "").trim();
            apnEdittext.setText(data);
        }
        else if (result.contains(ConfigParams.SetAPNUser))
        {
            data = result.replaceAll(ConfigParams.SetAPNUser, "").trim();
            userEdittext.setText(data);
        }
        else if (result.contains(ConfigParams.SetAP_N_secret))
        {
            data = result.replaceAll(ConfigParams.SetAP_N_secret, "").trim();
            apnPsdEdittext.setText(data);
        }
        else if (result.contains(ConfigParams.SetComPassword))
        {
            data = result.replaceAll(ConfigParams.SetComPassword, "").trim();
            commPsdEdittext.setText(data);
        }
    }

}

