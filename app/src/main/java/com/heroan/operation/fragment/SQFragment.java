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
import com.heroan.operation.utils.UiEventEntry;

import cn.com.heaton.blelibrary.ble.BleDevice;
import zuo.biao.library.base.BaseFragment;

import static com.heroan.operation.utils.ServiceUtils.sendData;

/**
 * 墒情
 * Created by Vcontrol on 2016/11/23.
 */

public class SQFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate {

    private RadioGroup soilModelGroup;
    private RadioGroup soil485Group;

    private EditText waterSoil1EditText;
    private EditText waterSoil2EditText;
    private EditText waterSoil3EditText;
    private EditText waterSoil4EditText;

    private Button waterSoil1Button;
    private Button waterSoil2Button;
    private Button waterSoil3Button;
    private Button waterSoil4Button;

    private Spinner soilType1Spinner;
    private Spinner soilType2Spinner;
    private Spinner soilType3Spinner;
    private Spinner soilType4Spinner;
    private Spinner soilType5Spinner;
    private Spinner soilType6Spinner;

    private Spinner AnaMoistureTypeSpinner;

    private String[] waterItems;
    private SimpleSpinnerAdapter waterAdapter;

    private String[] AnaMoistureItems;
    private SimpleSpinnerAdapter AnaMoistureAdapter;
    private boolean isFirst = true;
    private boolean isFirst1 = true;
    private boolean isFirst2 = true;
    private boolean isFirst3 = true;
    private boolean isFirst4 = true;
    private boolean isFirst5 = true;

    private boolean isFirst6 = true;

    private boolean isBleDevice = false;
    private BleDevice bleDevice = null;

    private static SQFragment instance;

    public static SQFragment createInstance() {
        if (instance == null) {
            instance = new SQFragment();
        }
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_sensor_sq);
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

        isFirst = true;
        isFirst1 = true;
        isFirst2 = true;
        isFirst3 = true;
        isFirst4 = true;
        isFirst5 = true;
        isFirst6 = true;
        soilModelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = null;
                if (checkedId == R.id.water_soil_model_button) {
                    content = ConfigParams.SetMoistureType + "1";
                } else if (checkedId == R.id.water_soil_model_button2) {
                    content = ConfigParams.SetMoistureType + "2";
                } else if (checkedId == R.id.water_soil_model_button3) {
                    content = ConfigParams.SetMoistureType + "3";
                }
                sendData(content);
            }
        });
        soil485Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = null;
                if (checkedId == R.id.water_soil_485_button) {
                    content = ConfigParams.SetMoisture485Type + "1";
                } else if (checkedId == R.id.water_soil_485_button2) {
                    content = ConfigParams.SetMoisture485Type + "2";
                } else if (checkedId == R.id.water_soil_485_button3) {
                    content = ConfigParams.SetMoisture485Type + "3";
                }
                sendData(content);

            }
        });

        soilType1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst) {
                    isFirst = false;
                    return;
                }
                waterAdapter.setSelectedItem(position);
                String content = ConfigParams.SetMoisture_Style1 + position;
                sendData(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        soilType2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst1) {
                    isFirst1 = false;
                    return;
                }
                waterAdapter.setSelectedItem(position);
                sendData(ConfigParams.SetMoisture_Style2 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        soilType3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst2) {
                    isFirst2 = false;
                    return;
                }
                waterAdapter.setSelectedItem(position);
                sendData(ConfigParams.SetMoisture_Style3 + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        soilType4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst3) {
                    isFirst3 = false;
                    return;
                }
                waterAdapter.setSelectedItem(position);
                sendData(ConfigParams.SetMoisture_Style4 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        soilType5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst4) {
                    isFirst4 = false;
                    return;
                }
                waterAdapter.setSelectedItem(position);
                sendData(ConfigParams.SetMoisture_Style5 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        soilType6Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst5) {
                    isFirst5 = false;
                    return;
                }
                waterAdapter.setSelectedItem(position);
                sendData(ConfigParams.SetMoisture_Style6 + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AnaMoistureTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst6) {
                    isFirst6 = false;
                    return;
                }
                AnaMoistureAdapter.setSelectedItem(position);
                sendData(ConfigParams.SetAnaMoistureType + position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        if (getArguments() != null) {
            isBleDevice = getArguments().getBoolean("isBleDevice");
            bleDevice = (BleDevice) getArguments().getSerializable("device");
        }


        soilModelGroup = (RadioGroup) view.findViewById(R.id.water_soil_model);
        soil485Group = (RadioGroup) view.findViewById(R.id.water_soil_485);
        waterSoil1EditText = (EditText) view.findViewById(R.id.water_soil_0);
        waterSoil2EditText = (EditText) view.findViewById(R.id.water_soil_1);
        waterSoil3EditText = (EditText) view.findViewById(R.id.water_soil_2);
        waterSoil4EditText = (EditText) view.findViewById(R.id.water_soil_3);

        waterSoil1Button = (Button) view.findViewById(R.id.water_soil_type_button);
        waterSoil2Button = (Button) view.findViewById(R.id.water_soil_type_1_button);
        waterSoil3Button = (Button) view.findViewById(R.id.water_soil_type_2_button);
        waterSoil4Button = (Button) view.findViewById(R.id.water_soil_type_3_button);

        soilType1Spinner = (Spinner) view.findViewById(R.id.water_soil_type_1);
        soilType2Spinner = (Spinner) view.findViewById(R.id.water_soil_type_2);
        soilType3Spinner = (Spinner) view.findViewById(R.id.water_soil_type_3);
        soilType4Spinner = (Spinner) view.findViewById(R.id.water_soil_type_4);
        soilType5Spinner = (Spinner) view.findViewById(R.id.water_soil_type_5);
        soilType6Spinner = (Spinner) view.findViewById(R.id.water_soil_type_6);

        AnaMoistureTypeSpinner = (Spinner) view.findViewById(R.id.AnaMoisture_Type);

        initView(view);
    }

    @Override
    public void initData() {

        waterItems = getResources().getStringArray(R.array.water_soil_type);
        waterAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, waterItems);
        soilType1Spinner.setAdapter(waterAdapter);
        soilType2Spinner.setAdapter(waterAdapter);
        soilType3Spinner.setAdapter(waterAdapter);
        soilType4Spinner.setAdapter(waterAdapter);
        soilType5Spinner.setAdapter(waterAdapter);
        soilType6Spinner.setAdapter(waterAdapter);

        AnaMoistureItems = getResources().getStringArray(R.array.water_select);
        AnaMoistureAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, AnaMoistureItems);
        AnaMoistureTypeSpinner.setAdapter(AnaMoistureAdapter);
        sendData(ConfigParams.ReadMoisture_SensorPara);
    }

    @Override
    public void initEvent() {
        waterSoil1Button.setOnClickListener(this);
        waterSoil2Button.setOnClickListener(this);
        waterSoil3Button.setOnClickListener(this);
        waterSoil4Button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String waterSoil = "";
        switch (view.getId()) {
            case R.id.water_soil_type_button:
                waterSoil = waterSoil1EditText.getText().toString().trim();
//                SocketUtil.getSocketUtil().sendContent();
                break;
            case R.id.water_soil_type_1_button:
                waterSoil = waterSoil2EditText.getText().toString().trim();

                break;
            case R.id.water_soil_type_2_button:
                waterSoil = waterSoil3EditText.getText().toString().trim();

                break;
            case R.id.water_soil_type_3_button:
                waterSoil = waterSoil4EditText.getText().toString().trim();

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
        String data;
        int pos = 0;
        String res = "";
        if (result.contains(ConfigParams.SetMoisture_Style1.trim())) {
            res = result.replaceAll(ConfigParams.SetMoisture_Style1, "");
            pos = Integer.parseInt(res);
            if (pos < waterItems.length) {
                soilType1Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetMoisture_Style2.trim())) {
            res = result.replaceAll(ConfigParams.SetMoisture_Style2, "");
            pos = Integer.parseInt(res);
            if (pos < waterItems.length) {
                soilType2Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetMoisture_Style3.trim())) {
            res = result.replaceAll(ConfigParams.SetMoisture_Style3, "");
            pos = Integer.parseInt(res);
            if (pos < waterItems.length) {
                soilType3Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetMoisture_Style4.trim())) {
            res = result.replaceAll(ConfigParams.SetMoisture_Style4, "");
            pos = Integer.parseInt(res);
            if (pos < waterItems.length) {
                soilType4Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetMoisture_Style5.trim())) {
            res = result.replaceAll(ConfigParams.SetMoisture_Style5, "");
            pos = Integer.parseInt(res);
            if (pos < waterItems.length) {
                soilType5Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetMoisture_Style6.trim())) {
            res = result.replaceAll(ConfigParams.SetMoisture_Style6, "");
            pos = Integer.parseInt(res);
            if (pos < waterItems.length) {
                soilType6Spinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetAnaMoistureType.trim())) {
            res = result.replaceAll(ConfigParams.SetAnaMoistureType, "");
            pos = Integer.parseInt(res);
            if (pos < AnaMoistureItems.length) {
                AnaMoistureTypeSpinner.setSelection(pos);
            }
        } else if (result.contains(ConfigParams.SetMoistureType)) {
            data = result.replaceAll(ConfigParams.SetMoistureType, "").trim();

            if ("1".equals(data)) {
                soilModelGroup.check(R.id.water_soil_model_button);
            } else if ("2".equals(data)) {
                soilModelGroup.check(R.id.water_soil_model_button2);
            } else {
                soilModelGroup.check(R.id.water_soil_model_button3);
            }
        } else if (result.contains(ConfigParams.SetMoisture485Type)) {
            data = result.replaceAll(ConfigParams.SetMoisture485Type, "").trim();

            if ("1".equals(data)) {
                soil485Group.check(R.id.water_soil_485_button);
            } else if ("2".equals(data)) {
                soil485Group.check(R.id.water_soil_485_button2);
            } else {
                soil485Group.check(R.id.water_soil_485_button3);
            }
        }
    }

}
