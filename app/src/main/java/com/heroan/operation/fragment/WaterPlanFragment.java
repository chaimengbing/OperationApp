package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import cn.com.heaton.blelibrary.ble.BleDevice;
import zuo.biao.library.base.BaseFragment;

/**
 * 水位计
 * Created by Vcontrol on 2016/11/23.
 */

public class WaterPlanFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate {
    private final String TAG = WaterPlanFragment.class.getSimpleName();

    private RadioGroup waterGroup;
    private RadioGroup waterCollectGroup;
    private RadioGroup singalGroup;
    private Spinner selwaterSpinner;
    private Spinner selwater485Spinner;
    private String[] waterItems;
    private SimpleSpinnerAdapter waterAdapter;
    private String[] water485Items;
    private SimpleSpinnerAdapter water485Adapter;

    private Button modelButton;
    private Button waterLevelButton;
    private EditText modelEditText;
    private EditText waterLevelEditText;

    private Button planNumButton;
    private Button plan1Button;
    private Button plan2Button;
    private Button plan3Button;
    private Button plan4Button;
    private EditText planNumEditText;
    private EditText plan1EditText;
    private EditText plan2EditText;
    private EditText plan3EditText;
    private EditText plan4EditText;


    private boolean isBleDevice = false;
    private BleDevice bleDevice = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_sensor_water_plan);
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





    private void initView(final View view) {
        waterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetWaterType;
                if (checkedId == R.id.watertype_button) {
                    content = content + "1";
                } else if (checkedId == R.id.watertype_button2) {
                    content = content + "2";
                } else if (checkedId == R.id.watertype_button3) {
                    content = content + "3";
                }
                ServiceUtils.sendData(content);
            }
        });
        waterCollectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetWater_caiji_Type;
                if (checkedId == R.id.water_collect_button) {
                    content = content + "1";
                } else if (checkedId == R.id.water_collect_button2) {
                    content = content + "2";
                }
                ServiceUtils.sendData(content);
            }
        });
        singalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetAnaWaterSignal;
                if (checkedId == R.id.water_singal_button) {
                    content = content + "1";
                } else if (checkedId == R.id.water_singal_button2) {
                    content = content + "2";
                }
                ServiceUtils.sendData(content);
            }
        });
    }


    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        waterGroup = (RadioGroup) view.findViewById(R.id.watertype_group);
        waterCollectGroup = (RadioGroup) view.findViewById(R.id.water_collect_group);
        singalGroup = (RadioGroup) view.findViewById(R.id.water_singal_group);
        selwaterSpinner = (Spinner) view.findViewById(R.id.selwater_spinner);
        selwater485Spinner = (Spinner) view.findViewById(R.id.sel485water_spinner);

        modelButton = (Button) view.findViewById(R.id.model_button);
        waterLevelButton = (Button) view.findViewById(R.id.water_level_button);
        modelEditText = (EditText) view.findViewById(R.id.model_edittext);
        waterLevelEditText = (EditText) view.findViewById(R.id.water_level_edittext);

        planNumButton = (Button) view.findViewById(R.id.plan_num_button);
        planNumEditText = (EditText) view.findViewById(R.id.plan_num_edittext);
        plan1Button = (Button) view.findViewById(R.id.plan_button_1);
        plan1EditText = (EditText) view.findViewById(R.id.plan_edittext_1);
        plan2Button = (Button) view.findViewById(R.id.plan_button_2);
        plan2EditText = (EditText) view.findViewById(R.id.plan_edittext_2);
        plan3Button = (Button) view.findViewById(R.id.plan_button_3);
        plan3EditText = (EditText) view.findViewById(R.id.plan_edittext_3);
        plan4Button = (Button) view.findViewById(R.id.plan_button_4);
        plan4EditText = (EditText) view.findViewById(R.id.plan_edittext_4);

        if (getArguments() != null) {
            isBleDevice = getArguments().getBoolean("isBleDevice");
            bleDevice = (BleDevice) getArguments().getSerializable("device");
        }

        initView(view);
    }

    @Override
    public void initData() {
        waterItems = getResources().getStringArray(R.array.water_select);
        waterAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, waterItems);
        selwaterSpinner.setAdapter(waterAdapter);

        water485Items = getResources().getStringArray(R.array.water_select_485);
        water485Adapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, water485Items);
        selwater485Spinner.setAdapter(water485Adapter);

        ServiceUtils.sendData(ConfigParams.ReadSensorPara2);
    }

    @Override
    public void initEvent() {
        modelButton.setOnClickListener(this);
        planNumButton.setOnClickListener(this);
        plan1Button.setOnClickListener(this);
        plan2Button.setOnClickListener(this);
        plan3Button.setOnClickListener(this);
        plan4Button.setOnClickListener(this);
        waterLevelButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String water;
        String content = null;
        int value;
        switch (view.getId()) {
            case R.id.plan_num_button:
                water = planNumEditText.getText().toString();
                if (TextUtils.isEmpty(water)) {
                    ToastUtil.showToastLong(getString(R.string.water_level_meters_empty));
                    return;
                }

                int planNum = Integer.parseInt(water);
                if (planNum < 0 || planNum > 9) {
                    ToastUtil.showToastLong(getString(R.string.water_enter));
                    return;
                }
                content = ConfigParams.SetWater_Num + planNum;
                ServiceUtils.sendData(content);
                break;
            case R.id.plan_button_1:
                water = plan1EditText.getText().toString();
                if (TextUtils.isEmpty(water)) {
                    ToastUtil.showToastLong(getString(R.string.Water_level_meter_address_empty));
                    return;
                }

                content = ConfigParams.SetWater_Addr1 + water;
                ServiceUtils.sendData(content);
                break;
            case R.id.plan_button_2:
                water = plan2EditText.getText().toString();
                if (TextUtils.isEmpty(water)) {
                    ToastUtil.showToastLong(getString(R.string.Water_level_meter_address_empty));
                    return;
                }

                content = ConfigParams.SetWater_Addr2 + water;
                ServiceUtils.sendData(content);
                break;
            case R.id.plan_button_3:
                water = plan3EditText.getText().toString();
                if (TextUtils.isEmpty(water)) {
                    ToastUtil.showToastLong(getString(R.string.Water_level_meter_address_empty));
                    return;
                }

                content = ConfigParams.SetWater_Addr3 + water;
                ServiceUtils.sendData(content);
                break;
            case R.id.plan_button_4:
                water = plan4EditText.getText().toString();
                if (TextUtils.isEmpty(water)) {
                    ToastUtil.showToastLong(getString(R.string.Water_level_meter_address_empty));
                    return;
                }

                content = ConfigParams.SetWater_Addr4 + water;
                ServiceUtils.sendData(content);
                break;
            case R.id.model_button:
                water = modelEditText.getText().toString();
                if (TextUtils.isEmpty(water)) {
                    ToastUtil.showToastLong(getString(R.string.analog_filter_coefficients_null));
                    return;
                }

                double temp1 = Double.parseDouble(water);
                if (temp1 > 1.0) {
                    ToastUtil.showToastLong(getString(R.string.analog_filter_coefficients_range));
                    return;
                }
                String tt = String.valueOf(temp1 * 100);

                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetWaterLvBo + ServiceUtils.getStr(water, 3);
                ServiceUtils.sendData(content);
                break;
            case R.id.water_level_button:
                water = waterLevelEditText.getText().toString();
                if (TextUtils.isEmpty(water)) {
                    ToastUtil.showToastLong(getString(R.string.analog_water_level_gauge_empty));
                    return;
                }
                double temp = Double.parseDouble(water) * 100;
                int level = (int) temp;
                if (level < 0 || level > 99999) {
                    return;
                }

                content = ConfigParams.SetAnaWaterRange + ServiceUtils.getStr(level + "", 5);
                ServiceUtils.sendData(content);
                break;

            default:
                break;
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        String result = (String) args[0];
        if (TextUtils.isEmpty(result)) {
            return;
        }
        setData(result);
    }

    private void setData(String result) {
        String data = "";
        if (result.contains(ConfigParams.SetWaterType)) {
            data = result.replaceAll(ConfigParams.SetWaterType, "").trim();
            if ("1".equals(data)) {
                waterGroup.check(R.id.watertype_button);
            } else if ("2".equals(data)) {
                waterGroup.check(R.id.watertype_button2);
            } else if ("3".equals(data)) {
                waterGroup.check(R.id.watertype_button3);
            }
        } else if (result.contains(ConfigParams.SetWater_caiji_Type)) {
            data = result.replaceAll(ConfigParams.SetWater_caiji_Type, "").trim();
            if ("1".equals(data)) {
                waterCollectGroup.check(R.id.water_collect_button);
            } else if ("2".equals(data)) {
                waterCollectGroup.check(R.id.water_collect_button2);
            }
        } else if (result.contains(ConfigParams.SetAnaWaterSignal)) {
            data = result.replaceAll(ConfigParams.SetAnaWaterSignal, "").trim();
            if ("1".equals(data)) {
                singalGroup.check(R.id.water_singal_button);
            } else if ("2".equals(data)) {
                singalGroup.check(R.id.water_singal_button2);
            }
        } else if (result.contains(ConfigParams.SetWater_Num)) {
            data = result.replaceAll(ConfigParams.SetWater_Num, "").trim();
            planNumEditText.setText(data);
        } else if (result.contains(ConfigParams.SetWater_Addr1)) {
            data = result.replaceAll(ConfigParams.SetWater_Addr1, "").trim();
            plan1EditText.setText(data);
        } else if (result.contains(ConfigParams.SetWater_Addr2)) {
            data = result.replaceAll(ConfigParams.SetWater_Addr2, "").trim();
            plan2EditText.setText(data);
        } else if (result.contains(ConfigParams.SetWater_Addr3)) {
            data = result.replaceAll(ConfigParams.SetWater_Addr3, "").trim();
            plan3EditText.setText(data);
        } else if (result.contains(ConfigParams.SetWater_Addr4)) {
            data = result.replaceAll(ConfigParams.SetWater_Addr4, "").trim();
            plan4EditText.setText(data);
        } else if (result.contains(ConfigParams.SetWaterLvBo)) {
            data = result.replaceAll(ConfigParams.SetWaterLvBo, "").trim();
            modelEditText.setText(ServiceUtils.getDouble(data));
        } else if (result.contains(ConfigParams.SetAnaWaterRange)) {
            data = result.replaceAll(ConfigParams.SetAnaWaterRange, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                double level = Double.parseDouble(data) / 100.0;
                waterLevelEditText.setText(String.valueOf(level));
            }
        } else if (result.contains(ConfigParams.SetAnaWaterType)) {
            data = result.replaceAll(ConfigParams.SetAnaWaterType, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int t = Integer.parseInt(data);
                if (t <= 4) {
                    selwaterSpinner.setSelection(t, false);
                } else {
                    selwaterSpinner.setSelection(0, false);
                }
                selwaterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        waterAdapter.setSelectedItem(position);
                        String water = waterItems[position];
                        if ("无".equals(water)) {
                            return;
                        }
                        String content = ConfigParams.SetAnaWaterType + position;
                        ServiceUtils.sendData(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } else if (result.contains(ConfigParams.SetWater485Type)) {
            data = result.replaceAll(ConfigParams.SetWater485Type, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int t = Integer.parseInt(data);
                if (t < water485Items.length) {
                    selwater485Spinner.setSelection(t, false);
                }
                selwater485Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        water485Adapter.setSelectedItem(position);
                        String water485 = water485Items[position];
                        if ("无".equals(water485)) {
                            return;
                        }
                        String content = ConfigParams.SetWater485Type + ServiceUtils.getStr("" + position, 2);
                        ServiceUtils.sendData(content);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }
}
