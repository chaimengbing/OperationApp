package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by linxi on 2017/11/30.
 */

public class WaterQualityFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {
    private final String TAG = WaterQualityFragment.class.getSimpleName();

    private Spinner waterQualitySpinner;
    private String[] waterQualityItems;
    private SimpleSpinnerAdapter waterQualityAdapter;
    private boolean isFirst = true;

    private Button collectButton;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private CheckBox checkBox9;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_water_quality);
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
        waterQualitySpinner = view.findViewById(R.id.Water_quality);
        collectButton = view.findViewById(R.id.collect_button);
        checkBox1 = view.findViewById(R.id.checkbox1);
        checkBox2 = view.findViewById(R.id.checkbox2);
        checkBox3 = view.findViewById(R.id.checkbox3);
        checkBox4 = view.findViewById(R.id.checkbox4);
        checkBox5 = view.findViewById(R.id.checkbox5);
        checkBox6 = view.findViewById(R.id.checkbox6);
        checkBox7 = view.findViewById(R.id.checkbox7);
        checkBox8 = view.findViewById(R.id.checkbox8);
        checkBox9 = view.findViewById(R.id.checkbox9);
    }

    @Override
    public void initData() {
        waterQualityItems = getResources().getStringArray(R.array.Water_quality);
        waterQualityAdapter = new SimpleSpinnerAdapter(getActivity(),
                R.layout.simple_spinner_item, waterQualityItems);
        waterQualitySpinner.setAdapter(waterQualityAdapter);
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadWaterQuality);
    }

    @Override
    public void initEvent() {
        collectButton.setOnClickListener(this);

        waterQualitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (isFirst) {
                    isFirst = false;
                    return;
                }

                waterQualityAdapter.setSelectedItem(i);

                String content = ConfigParams.SetWaterQuality + i;
                SocketUtil.getSocketUtil().sendContent(content);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_button:
                String check1 = checkBox1 != null && checkBox1.isChecked() ? "1" : "0";
                String check2 = checkBox2 != null && checkBox2.isChecked() ? "1" : "0";
                String check3 = checkBox3 != null && checkBox3.isChecked() ? "1" : "0";
                String check4 = checkBox4 != null && checkBox4.isChecked() ? "1" : "0";
                String check5 = checkBox5 != null && checkBox5.isChecked() ? "1" : "0";
                String check6 = checkBox6 != null && checkBox6.isChecked() ? "1" : "0";
                String check7 = checkBox7 != null && checkBox7.isChecked() ? "1" : "0";
                String check8 = checkBox8 != null && checkBox8.isChecked() ? "1" : "0";
                String check9 = checkBox9 != null && checkBox9.isChecked() ? "1" : "0";

                String content =
                        ConfigParams.Setsz_select + check1 + check2 + check3 + check4 + check5 + check6 + check7 + check8 + check9;
                SocketUtil.getSocketUtil().sendContent(content);
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
        String data = "";
        if (result.contains(ConfigParams.SetWaterQuality)) {
            data = result.replaceAll(ConfigParams.SetWaterQuality, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int t = Integer.parseInt(data);
                if (t < waterQualityItems.length) {
                    waterQualitySpinner.setSelection(t, false);
                }
                waterQualitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id) {
                        waterQualityAdapter.setSelectedItem(position);
                        String water485 = waterQualityItems[position];
                        if ("无".equals(water485)) {
                            return;
                        }
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.SetWaterQuality + ServiceUtils.getStr("" + position, 2));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } else if (result.contains(ConfigParams.Setsz_select.trim()) && (!result.equals("OK"))) {
            // 获取采集要素设置
            String collect = result.replaceAll(ConfigParams.Setsz_select.trim(), "").trim();
            checkBox1.setChecked((collect.charAt(0)) == '1');
            checkBox2.setChecked((collect.charAt(1)) == '1');
            checkBox3.setChecked((collect.charAt(2)) == '1');
            checkBox4.setChecked((collect.charAt(3)) == '1');
            checkBox5.setChecked((collect.charAt(4)) == '1');
            checkBox6.setChecked((collect.charAt(5)) == '1');
            checkBox7.setChecked((collect.charAt(6)) == '1');
            checkBox8.setChecked((collect.charAt(7)) == '1');
            checkBox9.setChecked((collect.charAt(8)) == '1');

        }
    }
}
