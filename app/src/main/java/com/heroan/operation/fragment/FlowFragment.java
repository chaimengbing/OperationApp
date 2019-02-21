package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import zuo.biao.library.util.Log;
/**
 * 流量计
 * Created by Vcontrol on 2016/11/23.
 */

public class FlowFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate {
    private Spinner flowSpinner;
    private String[] flowItems;
    private SimpleSpinnerAdapter waterAdapter;
    private Spinner newFlowSpinner;
    private String[] newFlowItems;
    private SimpleSpinnerAdapter newFlowAdapter;
    private Spinner flow_weir_co_Spinner;
    private String[] flow_weir_coItems;
    private SimpleSpinnerAdapter flow_weir_coAdapter;
    private  static final String TAG = FlowFragment.class.getSimpleName();

    private RadioGroup flowTypeGroup;
    private EditText speedEdittext;
    private EditText valueEdittext;
    private Button speedButton;
    private Button valueButton;

    private EditText coefficientEdittext0;
    private Button coefficientButton0;
    private EditText coefficientEdittext1;
    private Button coefficientButton1;
    private EditText coefficientEdittext2;
    private Button coefficientButton2;
    private EditText coefficientEdittext3;
    private Button coefficientButton3;

    private EditText planNumEditText;
    private Button planNumButton;

    @Override
    public int getLayoutView() {
        return R.layout.fragment_sensor_flow;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initComponentViews(View view) {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        flowSpinner = (Spinner) view.findViewById(R.id.flow_485_spinner);
        flow_weir_co_Spinner = (Spinner) view.findViewById(R.id.flow_weir_co_spinner);
        flowTypeGroup = (RadioGroup) view.findViewById(R.id.flow_type);
        speedEdittext = (EditText) view.findViewById(R.id.flow_speed_add_value);
        valueEdittext = (EditText) view.findViewById(R.id.flow_add_value);
        speedButton = (Button) view.findViewById(R.id.flow_speed_add_value_button);
        valueButton = (Button) view.findViewById(R.id.flow_add_value_button);
        coefficientEdittext0 = (EditText) view.findViewById(R.id.Flow_0_coefficient);
        coefficientButton0 = (Button) view.findViewById(R.id.Flow_coefficient_button0);
        coefficientEdittext1 = (EditText) view.findViewById(R.id.Flow_1_coefficient);
        coefficientButton1 = (Button) view.findViewById(R.id.Flow_coefficient_button1);
        coefficientEdittext2 = (EditText) view.findViewById(R.id.Flow_2_coefficient);
        coefficientButton2 = (Button) view.findViewById(R.id.Flow_coefficient_button2);
        coefficientEdittext3 = (EditText) view.findViewById(R.id.Flow_3_coefficient);
        coefficientButton3 = (Button) view.findViewById(R.id.Flow_coefficient_button3);
        planNumEditText = (EditText) view.findViewById(R.id.plan_num_edittext);
        planNumButton = (Button) view.findViewById(R.id.plan_num_button);
        newFlowSpinner = (Spinner) view.findViewById(R.id.flow_485_spinner_new);


        initView(view);
    }

    private void initView(final View view) {


        flowTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = "";
                if (checkedId == R.id.flow_type_button) {
                    content = ConfigParams.SetFlowType + "1";
                } else if (checkedId == R.id.flow_type_button2) {
                    content = ConfigParams.SetFlowType + "2";
                }
                else if (checkedId == R.id.flow_type_button3)
                {
                    content = ConfigParams.SetFlowType + "3";
                }
                else if (checkedId == R.id.flow_type_button4)
                {
                    content = ConfigParams.SetFlowType + "4";
                }
                SocketUtil.getSocketUtil().sendContent(content);
            }
        });
    }

    @Override
    public void initData() {
        flowItems = getResources().getStringArray(R.array.flow_485);
        waterAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, flowItems);
        flowSpinner.setAdapter(waterAdapter);

        flow_weir_coItems = getResources().getStringArray(R.array.weir_code_number);
        flow_weir_coAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, flow_weir_coItems);
        flow_weir_co_Spinner.setAdapter(flow_weir_coAdapter);

        newFlowItems = getResources().getStringArray(R.array.flow_485_new);
        newFlowAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, newFlowItems);
        newFlowSpinner.setAdapter(newFlowAdapter);

        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadSensorPara2);



//        flowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                waterAdapter.setSelectedItem(position);
//                String content = ConfigParams.SetFlow485Type + ServiceUtils.getStr(position + 1 + "", 2);
//                SocketUtil.getSocketUtil().sendContent(content);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    @Override
    public void setListener() {
        speedButton.setOnClickListener(this);
        valueButton.setOnClickListener(this);
        coefficientButton0.setOnClickListener(this);
        coefficientButton1.setOnClickListener(this);
        coefficientButton2.setOnClickListener(this);
        coefficientButton3.setOnClickListener(this);
        planNumButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String flow = "";
        String content = "";
        double temp;
        String tt = "";
        String water = "";
        switch (view.getId()) {
            case R.id.plan_num_button:
                water = planNumEditText.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.flowmeters_cannot_be_empty));
                    return;
                }

                int planNum = Integer.parseInt(water);
                if (planNum < 0 || planNum > 9)
                {
                    ToastUtil.showToastLong(getString(R.string.Flowmeter_reenter));
                    return;
                }
                content = ConfigParams.Setliuliang_Num + planNum;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            case R.id.flow_speed_add_value_button:
                flow = speedEdittext.getText().toString().trim();

                if (TextUtils.isEmpty(flow))
                {
                    ToastUtil.showToastLong(getString(R.string.flow_threshold_cannot_be_empty));
                    return;
                }
                temp = Double.parseDouble(flow);
                if (temp > 99.99)
                {
                    ToastUtil.showToastLong(getString(R.string.Flow_rate_plus_threshold_range));
                    return;
                }
                tt = String.valueOf(temp * 1000);

                flow = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.Setliusujiabao + ServiceUtils.getStr(flow + "", 5);


                break;
            case R.id.flow_add_value_button:
                flow = valueEdittext.getText().toString().trim();

                if (TextUtils.isEmpty(flow))
                {
                    ToastUtil.showToastLong(getString(R.string.flow_plus_threshold_empty));
                    return;
                }
                temp = Double.parseDouble(flow);
                if (temp > 9.99)
                {
                    ToastUtil.showToastLong(getString(R.string.Flow_plus_threshold_range));
                    return;
                }
                tt = String.valueOf(temp * 1000);

                flow = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.Setliuliangjiabao + ServiceUtils.getStr(flow + "", 6);

                break;
            case R.id.Flow_coefficient_button0:
                flow = coefficientEdittext0.getText().toString().trim();
                content = ConfigParams.Setliuliang_Factor_0 + ServiceUtils.getStr(flow + "",2);
                break;
            case R.id.Flow_coefficient_button1:
                flow = coefficientEdittext1.getText().toString().trim();
                content = ConfigParams.Setliuliang_Factor_1 + ServiceUtils.getStr(flow + "",2);
                break;
            case R.id.Flow_coefficient_button2:
                flow = coefficientEdittext2.getText().toString().trim();
                content = ConfigParams.Setliuliang_Factor_2 + ServiceUtils.getStr(flow + "",2);
                break;
            case R.id.Flow_coefficient_button3:
                flow = coefficientEdittext3.getText().toString().trim();
                content = ConfigParams.Setliuliang_Factor_3 + ServiceUtils.getStr(flow + "",2);
                break;

            default:
                break;

        }
        SocketUtil.getSocketUtil().sendContent(content);
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

    private void setData(String result)
    {
        String data = "";
        String temp = "";
        if (result.contains(ConfigParams.SetFlow485Type))
        {
            data = result.replaceAll(ConfigParams.SetFlow485Type, "").trim();
            Log.i(TAG,"data:" + data);
            if (ServiceUtils.isNumeric(data))
            {
                int i = Integer.parseInt(data);
                if (i <= flowItems.length){
                    flowSpinner.setSelection(i - 1,false);
                    //waterAdapter.setSelectedItem(i);
                }
                //else {
                    //ToastUtil.showToastLong(getString(R.string.SetOldFlow485Type_error));
                //}

                flowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        waterAdapter.setSelectedItem(position);
                        String content = ConfigParams.SetFlow485Type + ServiceUtils.getStr(position + 1 + "", 2);
                        SocketUtil.getSocketUtil().sendContent(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                newFlowSpinner.setSelection(i - 1,false);

                newFlowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        newFlowAdapter.setSelectedItem(position);
                        String content = ConfigParams.SetFlow485Type + ServiceUtils.getStr(position + 1 + "", 2);
                        SocketUtil.getSocketUtil().sendContent(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
        if (result.contains(ConfigParams.SetWeir_Code_Number))
        {
            data = result.replaceAll(ConfigParams.SetWeir_Code_Number, "").trim();
            Log.i(TAG,"data:" + data);
            if (ServiceUtils.isNumeric(data))
            {
                int i = Integer.parseInt(data);
                flow_weir_co_Spinner.setSelection(i - 1,false);

                flow_weir_co_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        flow_weir_coAdapter.setSelectedItem(position);
                        String content = ConfigParams.SetWeir_Code_Number + ServiceUtils.getStr(position + 1 + "", 2);
                        SocketUtil.getSocketUtil().sendContent(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
//        else if (result.contains(ConfigParams.SetFlow485Type))
//        {
//            data = result.replaceAll(ConfigParams.SetFlow485Type, "").trim();
//            Log.info(TAG,"data:" + data);
//            if (ServiceUtils.isNumeric(data))
//            {
//                int i = Integer.parseInt(data);
//                newFlowSpinner.setSelection(i - 1,false);
//
//                newFlowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        newFlowAdapter.setSelectedItem(position);
//                        String content = ConfigParams.SetFlow485Type + ServiceUtils.getStr(position + 1 + "", 2);
//                        SocketUtil.getSocketUtil().sendContent(content);
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//            }
//        }
        else if (result.contains(ConfigParams.SetFlowType))
        {
            data = result.replaceAll(ConfigParams.SetFlowType, "").trim();
            if ("1".equals(data))
            {
                flowTypeGroup.check(R.id.flow_type_button);
            }
            else if ("2".equals(data))
            {
                flowTypeGroup.check(R.id.flow_type_button2);
            }
            else if ("3".equals(data))
            {
                flowTypeGroup.check(R.id.flow_type_button3);
            }
            else if ("4".equals(data))
            {
                flowTypeGroup.check(R.id.flow_type_button4);
            }
            SocketUtil.getSocketUtil().sendContent(ConfigParams.Readliuliang_SensorPara);

        }
        else if (result.contains(ConfigParams.Setliuliang_Num))
        {
            data = result.replaceAll(ConfigParams.Setliuliang_Num, "").trim();
            planNumEditText.setText(data);
        }
        else if (result.contains(ConfigParams.Setliusujiabao))
        {
            data = result.replaceAll(ConfigParams.Setliusujiabao, "").trim();
            if (ServiceUtils.isNumeric(data))
            {
                double level = Double.parseDouble(data) / 1000.0;
                temp = String.valueOf(level);
                speedEdittext.setText(temp);
            }
        }
        else if (result.contains(ConfigParams.Setliuliangjiabao))
        {
            data = result.replaceAll(ConfigParams.Setliuliangjiabao, "").trim();
            if (ServiceUtils.isNumeric(data))
            {
                double level = Double.parseDouble(data) / 1000.0;
                temp = String.valueOf(level);
                valueEdittext.setText(temp);
            }
        }
        else if (result.contains(ConfigParams.Setliuliang_Factor_0))
        {
            Log.i(TAG,"data:" + data);
            data = result.replaceAll(ConfigParams.Setliuliang_Factor_0, "").trim();
            double level = Double.parseDouble(data);
                temp = String.valueOf(level);
                coefficientEdittext0.setText(temp);
//           if (ServiceUtils.isNumeric(data))、
//            {
//                double level = Double.parseDouble(data) / 1000.0;
//                temp = String.valueOf(level);
//                coefficientEdittext0.setText(temp);
//            }
        }
        else if (result.contains(ConfigParams.Setliuliang_Factor_1))
        {
            data = result.replaceAll(ConfigParams.Setliuliang_Factor_1, "").trim();
//            if (ServiceUtils.isNumeric(data))
//            {
                double level = Double.parseDouble(data);
                temp = String.valueOf(level);
                coefficientEdittext1.setText(temp);
//            }
        }
        else if (result.contains(ConfigParams.Setliuliang_Factor_2))
        {
            data = result.replaceAll(ConfigParams.Setliuliang_Factor_2, "").trim();
//            if (ServiceUtils.isNumeric(data))
//            {
                double level = Double.parseDouble(data);
                temp = String.valueOf(level);
                coefficientEdittext2.setText(temp);
//            }
        }
        else if (result.contains(ConfigParams.Setliuliang_Factor_3))
        {
            data = result.replaceAll(ConfigParams.Setliuliang_Factor_3, "").trim();
//            if (ServiceUtils.isNumeric(data))
            {
                double level = Double.parseDouble(data);
                temp = String.valueOf(level);
                coefficientEdittext3.setText(temp);
            }
        }
    }
}
