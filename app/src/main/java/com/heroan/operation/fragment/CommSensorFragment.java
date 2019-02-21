package com.heroan.operation.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxi on 2017/2/27.
 */

public class CommSensorFragment extends BaseFragment implements View.OnClickListener
{

    private static final String TAG = CommSensorFragment.class.getSimpleName();
    //0路传感器
    private LinearLayout sensorLayout0;
    private EditText sensor4850;
    private Spinner sensorNum0;
    private EditText sensorTime0;
    private EditText sensorModbusAddr0;
    private EditText sensorAddr0;
    private EditText sensorLen0;
    private Button saveButton0;
    private Button sensorButton0;
    private int currentNum0 = 0;
    private List<String> seneor0List = new ArrayList<>();
    private SimpleSpinnerAdapter sensor0Adpter;

    private String sque;
    private String delay;
    private String addr;
    private String start;
    private String len;

    //1路传感器
    private LinearLayout sensorLayout1;
    private EditText sensor4851;
    private Spinner sensorNum1;
    private EditText sensorTime1;
    private EditText sensorModbusAddr1;
    private EditText sensorAddr1;
    private EditText sensorLen1;
    private Button saveButton1;
    private int currentNum1 = 0;
    private List<String> seneor1List = new ArrayList<>();
    private SimpleSpinnerAdapter sensor1Adpter;
    private Button sensorButton1;

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_setting_comm_sensor;
    }

    @Override
    public void initComponentViews(View view)
    {
        sensorLayout0 = (LinearLayout) view.findViewById(R.id.sensor_layout_0);
        sensor4850 = (EditText) view.findViewById(R.id.sensor_485_0);
        sensorNum0 = (Spinner) view.findViewById(R.id.sensor_num_0);
        sensorTime0 = (EditText) view.findViewById(R.id.sensor_time_0);
        sensorModbusAddr0 = (EditText) view.findViewById(R.id.sensor_modbus_addr_0);
        sensorAddr0 = (EditText) view.findViewById(R.id.sensor_addr_0);
        sensorLen0 = (EditText) view.findViewById(R.id.sensor_len_0);
        saveButton0 = (Button) view.findViewById(R.id.save_button_0);
        sensorButton0 = (Button) view.findViewById(R.id.sensor_0_button);


        sensorLayout1 = (LinearLayout) view.findViewById(R.id.sensor_layout_1);
        sensor4851 = (EditText) view.findViewById(R.id.sensor_485_1);
        sensorNum1 = (Spinner) view.findViewById(R.id.sensor_num_1);
        sensorTime1 = (EditText) view.findViewById(R.id.sensor_time_1);
        sensorModbusAddr1 = (EditText) view.findViewById(R.id.sensor_modbus_addr_1);
        sensorAddr1 = (EditText) view.findViewById(R.id.sensor_addr_1);
        sensorLen1 = (EditText) view.findViewById(R.id.sensor_len_1);
        saveButton1 = (Button) view.findViewById(R.id.save_button_1);
        sensorButton1 = (Button) view.findViewById(R.id.sensor_1_button);

        sensorLayout0.setVisibility(View.GONE);
        sensorLayout1.setVisibility(View.GONE);

    }

    @Override
    public void initData()
    {


        sensorNum0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (sensor0Adpter == null)
                {
                    return;
                }
                sensor0Adpter.setSelectedItem(i);
                sque = i + 1 + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        sensorNum1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (sensor1Adpter == null)
                {
                    return;
                }
                sensor1Adpter.setSelectedItem(i);
                sque = i + 1 + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

    }

    @Override
    public void setListener()
    {

        saveButton0.setOnClickListener(this);
        saveButton1.setOnClickListener(this);
        sensorButton0.setOnClickListener(this);
        sensorButton1.setOnClickListener(this);

        sensor4850.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String num = s.toString();
                Log.i(TAG, "num:" + num);
                if (TextUtils.isEmpty(num))
                {
                    sensorLayout0.setVisibility(View.GONE);
                    return;
                }
                int number = Integer.parseInt(num);
                currentNum0 = number;

            }
        });

        sensor4851.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String num = s.toString();
                Log.i(TAG, "num:" + num);
                if (TextUtils.isEmpty(num))
                {
                    sensorLayout1.setVisibility(View.GONE);
                    return;
                }
                int number = Integer.parseInt(num);
                currentNum1 = number;

            }
        });

    }

    @Override
    public void onClick(View v)
    {
        String content = "";
        switch (v.getId())
        {
            case R.id.save_button_0:
                if (TextUtils.isEmpty(sque))
                {
                    return;
                }
                delay = sensorTime0.getText().toString().trim();
                if (TextUtils.isEmpty(delay))
                {
                    return;
                }
                addr = sensorModbusAddr0.getText().toString().trim();
                if (TextUtils.isEmpty(addr))
                {
                    return;
                }
                start = sensorAddr0.getText().toString().trim();
                if (TextUtils.isEmpty(start))
                {
                    return;
                }
                len = sensorLen0.getText().toString().trim();
                if (TextUtils.isEmpty(len))
                {
                    return;
                }

                content = ConfigParams.SetConfig0 + ConfigParams.Sque +
                        ServiceUtils.getStr(sque, 3) + " " +
                        ConfigParams.Delay + ServiceUtils.getStr(delay, 2) + " " +
                        ConfigParams.Addr + ServiceUtils.getStr(addr, 3) + " " +
                        ConfigParams.Start +ServiceUtils.getStr(start, 4) + " " +
                        ConfigParams.Len + ServiceUtils.getStr(len, 4);

                break;
            case R.id.save_button_1:

                if (TextUtils.isEmpty(sque))
                {
                    return;
                }
                delay = sensorTime1.getText().toString().trim();
                if (TextUtils.isEmpty(delay))
                {
                    return;
                }
                addr = sensorModbusAddr1.getText().toString().trim();
                if (TextUtils.isEmpty(addr))
                {
                    return;
                }
                start = sensorAddr1.getText().toString().trim();
                if (TextUtils.isEmpty(start))
                {
                    return;
                }
                len = sensorLen1.getText().toString().trim();
                if (TextUtils.isEmpty(len))
                {
                    return;
                }

                content = ConfigParams.SetConfig1 + ConfigParams.Sque +
                        ServiceUtils.getStr(sque, 3) + " " +
                        ConfigParams.Delay + ServiceUtils.getStr(delay, 2) + " " +
                        ConfigParams.Addr + ServiceUtils.getStr(addr, 3) + " " +
                        ConfigParams.Start + ServiceUtils.getStr(start, 4) + " " +
                        ConfigParams.Len + ServiceUtils.getStr(len, 4);

                break;

            case R.id.sensor_0_button:
                if (currentNum0 == 0)
                {
                    sensorLayout0.setVisibility(View.GONE);
                }
                else
                {
                    content = ConfigParams.Set485Num  + "0 " + ServiceUtils.getStr(currentNum0 + "",3);
                    sensorLayout0.setVisibility(View.VISIBLE);
                    seneor0List.clear();
                    for (int i = 0; i < currentNum0; i++)
                    {
                        seneor0List.add(i + 1 + "");
                    }
                    sensor0Adpter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, seneor0List);
                    sensorNum0.setAdapter(sensor0Adpter);
                    sque = 1 + "";
                }

                break;
            case R.id.sensor_1_button:
                if (currentNum1 == 0)
                {
                    sensorLayout1.setVisibility(View.GONE);
                }
                else
                {
                    content = ConfigParams.Set485Num  + "1 " + ServiceUtils.getStr(currentNum1 + "",3);
                    sensorLayout1.setVisibility(View.VISIBLE);
                    seneor1List.clear();
                    for (int i = 0; i < currentNum1; i++)
                    {
                        seneor1List.add(i + 1 + "");
                    }
                    sensor1Adpter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, seneor1List);
                    sensorNum1.setAdapter(sensor1Adpter);
                    sque = 1 + "";
                }

                break;

            default:
                break;
        }
        if (TextUtils.isEmpty(content))
        {
            return;
        }
        SocketUtil.getSocketUtil().sendContent(content);
    }


}
