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

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by linxi on 2018/4/11.
 */

public class AnalogQuantityFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private EditText quantitySoil1EditText;
    private EditText quantitySoil2EditText;
    private EditText quantitySoil3EditText;
    private EditText quantitySoil4EditText;
    private EditText quantitySoil5EditText;
    private EditText quantitySoil6EditText;
    private EditText quantitySoil7EditText;

    private EditText lowerLimitEditText;

    private Button quantitySoil1Button;
    private Button quantitySoil2Button;
    private Button quantitySoil3Button;
    private Button quantitySoil4Button;
    private Button quantitySoil5Button;
    private Button quantitySoil6Button;
    private Button quantitySoil7Button;
    private Button lowerLimitButton;


    private Spinner quantityType1Spinner;
    private Spinner quantityType2Spinner;
    private Spinner quantityType3Spinner;
    private Spinner quantityType4Spinner;
    private Spinner quantityType5Spinner;
    private Spinner quantityType6Spinner;
    private Spinner quantityType7Spinner;


    private String[] quantityItems;
    private SimpleSpinnerAdapter quantityAdapter;

    private boolean isFirst = true;
    private boolean isFirst1 = true;
    private boolean isFirst2 = true;
    private boolean isFirst3 = true;
    private boolean isFirst4 = true;
    private boolean isFirst5 = true;

    private boolean isFirst6 = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_sensor_aq);
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




    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        quantitySoil1EditText =  view.findViewById(R.id.ay_0);
        quantitySoil2EditText =  view.findViewById(R.id.ay_1);
        quantitySoil3EditText =  view.findViewById(R.id.ay_2);
        quantitySoil4EditText =  view.findViewById(R.id.ay_3);
        quantitySoil5EditText =  view.findViewById(R.id.ay_4);
        quantitySoil6EditText =  view.findViewById(R.id.ay_5);
        quantitySoil7EditText =  view.findViewById(R.id.ay_6);

        lowerLimitEditText =  view.findViewById(R.id.ay_14);

        quantitySoil1Button =  view.findViewById(R.id.ay0_button);
        quantitySoil2Button =  view.findViewById(R.id.ay1_button);
        quantitySoil3Button =  view.findViewById(R.id.ay2_button);
        quantitySoil4Button =  view.findViewById(R.id.ay3_button);
        quantitySoil5Button =  view.findViewById(R.id.ay4_button);
        quantitySoil6Button =  view.findViewById(R.id.ay5_button);
        quantitySoil7Button =  view.findViewById(R.id.ay6_button);

        lowerLimitButton =  view.findViewById(R.id.ay14_button);
        quantityType1Spinner =  view.findViewById(R.id.element_0);
        quantityType2Spinner =  view.findViewById(R.id.element_1);
        quantityType3Spinner =  view.findViewById(R.id.element_2);
        quantityType4Spinner =  view.findViewById(R.id.element_3);
        quantityType5Spinner =  view.findViewById(R.id.element_4);
        quantityType6Spinner =  view.findViewById(R.id.element_5);
        quantityType7Spinner =  view.findViewById(R.id.element_6);


    }

    @Override
    public void initData() {

        quantityItems = getResources().getStringArray(R.array.ay_element);
        quantityAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item,
                quantityItems);
        quantityType1Spinner.setAdapter(quantityAdapter);
        quantityType2Spinner.setAdapter(quantityAdapter);
        quantityType3Spinner.setAdapter(quantityAdapter);
        quantityType4Spinner.setAdapter(quantityAdapter);
        quantityType5Spinner.setAdapter(quantityAdapter);
        quantityType6Spinner.setAdapter(quantityAdapter);
        quantityType7Spinner.setAdapter(quantityAdapter);
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadAna_SensorPara);
    }

    @Override
    public void initEvent() {

        quantitySoil1Button.setOnClickListener(this);
        quantitySoil2Button.setOnClickListener(this);
        quantitySoil3Button.setOnClickListener(this);
        quantitySoil4Button.setOnClickListener(this);
        quantitySoil5Button.setOnClickListener(this);
        quantitySoil6Button.setOnClickListener(this);
        quantitySoil7Button.setOnClickListener(this);
        lowerLimitButton.setOnClickListener(this);

        isFirst = true;
        isFirst1 = true;
        isFirst2 = true;
        isFirst3 = true;
        isFirst4 = true;
        isFirst5 = true;
        isFirst6 = true;
        quantityType1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst) {
                    isFirst = false;
                    return;
                }
                quantityAdapter.setSelectedItem(position);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAna_element0 + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        quantityType2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst1) {
                    isFirst1 = false;
                    return;
                }
                quantityAdapter.setSelectedItem(position);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAna_element1 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantityType3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst2) {
                    isFirst2 = false;
                    return;
                }
                quantityAdapter.setSelectedItem(position);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAna_element2 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantityType4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst3) {
                    isFirst3 = false;
                    return;
                }
                quantityAdapter.setSelectedItem(position);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAna_element3 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantityType5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst4) {
                    isFirst4 = false;
                    return;
                }
                quantityAdapter.setSelectedItem(position);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAna_element4 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantityType6Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst5) {
                    isFirst5 = false;
                    return;
                }
                quantityAdapter.setSelectedItem(position);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAna_element5 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantityType7Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst6) {
                    isFirst6 = false;
                    return;
                }
                quantityAdapter.setSelectedItem(position);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAna_element6 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View v) {

        String content = "";
        switch (v.getId()) {
            case R.id.ay0_button:
                content = quantitySoil1EditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange0 + content);
                break;
            case R.id.ay1_button:
                content = quantitySoil2EditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange1 + content);
                break;
            case R.id.ay2_button:
                content = quantitySoil3EditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange2 + content);
                break;
            case R.id.ay3_button:
                content = quantitySoil4EditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange3 + content);
                break;
            case R.id.ay4_button:
                content = quantitySoil5EditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange4 + content);
                break;
            case R.id.ay5_button:
                content = quantitySoil6EditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange5 + content);
                break;
            case R.id.ay6_button:
                content = quantitySoil7EditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange6 + content);
                break;
            case R.id.ay14_button:
                content = lowerLimitEditText.getText().toString().trim();
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetAnaRange_low0 + content);
                break;
            default:
                break;
        }

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

    private void setData(String result) {
        String data;
        int pos = 0;
        String res = "";
        if (result.contains(ConfigParams.SetAna_element0.trim())) {
            res = result.replaceAll(ConfigParams.SetAna_element0, "");
            pos = Integer.parseInt(res);
            if (pos < quantityItems.length) {
                quantityType1Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAna_element1.trim())) {
            res = result.replaceAll(ConfigParams.SetAna_element1, "");
            pos = Integer.parseInt(res);
            if (pos < quantityItems.length) {
                quantityType2Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAna_element2.trim())) {
            res = result.replaceAll(ConfigParams.SetAna_element2, "");
            pos = Integer.parseInt(res);
            if (pos < quantityItems.length) {
                quantityType3Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAna_element3.trim())) {
            res = result.replaceAll(ConfigParams.SetAna_element3, "");
            pos = Integer.parseInt(res);
            if (pos < quantityItems.length) {
                quantityType4Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAna_element4.trim())) {
            res = result.replaceAll(ConfigParams.SetAna_element4, "");
            pos = Integer.parseInt(res);
            if (pos < quantityItems.length) {
                quantityType5Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAna_element5.trim())) {
            res = result.replaceAll(ConfigParams.SetAna_element5, "");
            pos = Integer.parseInt(res);
            if (pos < quantityItems.length) {
                quantityType6Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAna_element6.trim())) {
            res = result.replaceAll(ConfigParams.SetAna_element6, "");
            pos = Integer.parseInt(res);
            if (pos < quantityItems.length) {
                quantityType7Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAnaRange0)) {
            data = result.replaceAll(ConfigParams.SetAnaRange0, "").trim();
            quantitySoil1EditText.setText(data);
        } else if (result.contains(ConfigParams.SetAnaRange1)) {
            data = result.replaceAll(ConfigParams.SetAnaRange1, "").trim();
            quantitySoil2EditText.setText(data);
        } else if (result.contains(ConfigParams.SetAnaRange2)) {
            data = result.replaceAll(ConfigParams.SetAnaRange2, "").trim();
            quantitySoil3EditText.setText(data);
        } else if (result.contains(ConfigParams.SetAnaRange3)) {
            data = result.replaceAll(ConfigParams.SetAnaRange3, "").trim();
            quantitySoil4EditText.setText(data);
        } else if (result.contains(ConfigParams.SetAnaRange4)) {
            data = result.replaceAll(ConfigParams.SetAnaRange4, "").trim();
            quantitySoil5EditText.setText(data);
        } else if (result.contains(ConfigParams.SetAnaRange5)) {
            data = result.replaceAll(ConfigParams.SetAnaRange5, "").trim();
            quantitySoil6EditText.setText(data);
        } else if (result.contains(ConfigParams.SetAnaRange6)) {
            data = result.replaceAll(ConfigParams.SetAnaRange6, "").trim();
            quantitySoil7EditText.setText(data);
        } else if (result.contains(ConfigParams.SetAnaRange_low0)) {
            data = result.replaceAll(ConfigParams.SetAnaRange_low0, "").trim();
            lowerLimitEditText.setText(data);
        }
    }

}
