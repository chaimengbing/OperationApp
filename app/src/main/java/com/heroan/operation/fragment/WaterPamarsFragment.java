package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;
/**
 * 水位参数
 * Created by Vcontrol on 2016/11/23.
 */

public class WaterPamarsFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{

    private RadioGroup waterGroup;
    private Button addWaterButton;
    private Button addWaterUpButton;
    private Button addWaterDownButton;
    private Button addTimeButton;
    private Button waterModifyButton;
    private Button waterBasicButton;
    private Button waterPhotoUpButton;
    private Button waterPhotoDownButton;

    private EditText addWaterEditText;
    private EditText addWaterUpEditText;
    private EditText addWaterDownEditText;
    private EditText addTimeEditText;
    private EditText waterModifyEditText;
    private EditText waterBasicEditText;
    private EditText waterPhotoUpEditText;
    private EditText waterPhotoDownEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_sensor_water_params);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }


    private void initView(final View view)
    {
        waterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetWaterMeterPara;
                if (checkedId == R.id.water_button)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.water_button2)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
                else if (checkedId == R.id.water_button3)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "2");
                }
            }
        });
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        waterGroup = (RadioGroup) view.findViewById(R.id.water_group);
        addWaterButton = (Button) view.findViewById(R.id.add_water_button);
        addWaterUpButton = (Button) view.findViewById(R.id.add_water_up_button);
        addWaterDownButton = (Button) view.findViewById(R.id.add_water_down_button);
        addTimeButton = (Button) view.findViewById(R.id.add_time_button);
        waterModifyButton = (Button) view.findViewById(R.id.water_modify_button);
        waterBasicButton = (Button) view.findViewById(R.id.water_basic_button);
        waterPhotoUpButton = (Button) view.findViewById(R.id.photo_water_up_button);
        waterPhotoDownButton = (Button) view.findViewById(R.id.photo_water_down_button);

        addWaterEditText = (EditText) view.findViewById(R.id.add_water_edittext);
        addWaterUpEditText = (EditText) view.findViewById(R.id.add_water_up_edittext);
        addWaterDownEditText = (EditText) view.findViewById(R.id.add_water_down_edittext);
        addTimeEditText = (EditText) view.findViewById(R.id.add_time_edittext);
        waterModifyEditText = (EditText) view.findViewById(R.id.water_modify_edittext);
        waterBasicEditText = (EditText) view.findViewById(R.id.water_basic_edittext);
        waterPhotoUpEditText = (EditText) view.findViewById(R.id.photo_water_up_edittext);
        waterPhotoDownEditText = (EditText) view.findViewById(R.id.photo_water_down_edittext);

        initView(view);
    }

    @Override
    public void initData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadSensorPara1);
    }

    @Override
    public void initEvent() {
        addWaterButton.setOnClickListener(this);
        addWaterUpButton.setOnClickListener(this);
        addWaterDownButton.setOnClickListener(this);
        addTimeButton.setOnClickListener(this);
        waterModifyButton.setOnClickListener(this);
        waterBasicButton.setOnClickListener(this);
        waterPhotoUpButton.setOnClickListener(this);
        waterPhotoDownButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        String water;
        String tt = "";
        String content = null;
        double temp;
        int value = 0;
        switch (view.getId())
        {
            case R.id.add_water_button:
                water = addWaterEditText.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.reported_water_level_is_not_null));
                    return;
                }

                temp = Double.parseDouble(water);
                if (temp > 99.99)
                {
                    ToastUtil.showToastLong(getString(R.string.reported_water_level_range));
                    return;
                }

                tt = String.valueOf(temp * 100);

                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetWaterLeverMax + ServiceUtils.getStr(water, 4);
                break;
            case R.id.add_water_up_button:
                water = addWaterUpEditText.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.add_up_threshold_cannot_be_empty));
                    return;
                }
                temp = Double.parseDouble(water);
                if (temp > 9.99)
                {
                    ToastUtil.showToastLong(getString(R.string.above_reported_threshold_range));
                    return;
                }
                tt = String.valueOf(temp * 100);

                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetWaterLeverChangeUP + ServiceUtils.getStr(water + "", 3);
                break;
            case R.id.add_water_down_button:
                water = addWaterDownEditText.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.down_threshold_cannot_be_empty));
                    return;
                }

                temp = Double.parseDouble(water);
                if (temp > 9.99)
                {
                    ToastUtil.showToastLong(getString(R.string.under_reported_threshold_range));
                    return;
                }
                tt = String.valueOf(temp * 100);

                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetWaterLeverChangeDW + ServiceUtils.getStr(water, 3);
                break;
            case R.id.add_time_button:
                water = addTimeEditText.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.reporting_interval_cannot_be_null));
                    return;
                }
                value = Integer.parseInt(water);
                if (value < 0 || value > 59)
                {
                    return;
                }
                content = ConfigParams.SetAddReportInterval + ServiceUtils.getStr(value + "", 2);
                break;
            case R.id.water_modify_button:
                water = waterModifyEditText.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.water_level_correction_cannot_be_null));
                    return;
                }
                temp = Double.parseDouble(water);
//                if (temp < 0.001)
//                {
//                    ToastUtil.showToastLong("水位修正值超出范围！");
//                    return;
//                }
                tt = String.valueOf(temp * 1000);

                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetAnaWaterCal + ServiceUtils.getStr(water, 7);
                break;
            case R.id.water_basic_button:
                water = waterBasicEditText.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.base_value_of_the_water_level));
                    return;
                }
                temp = Double.parseDouble(water);
//                if (temp < 0.001)
//                {
//                    ToastUtil.showToastLong("水位基值超出范围！");
//                    return;
//                }
                tt = String.valueOf(temp * 1000);

                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetAnaWaterBac + ServiceUtils.getStr(water, 7);
                break;
            case R.id.photo_water_up_button:
                water = waterPhotoUpEditText.getText().toString();
                if(TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.up_photo_threshold_cannot_be_empty));
                    return;
                }
                temp = Double.parseDouble(water);
                if(temp > 9999.999)
                {
                    ToastUtil.showToastLong(getString(R.string.up_photo_threshold_range));
                }
                tt = String.valueOf(temp*1000);
                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetWaterPicUp + ServiceUtils.getStr(water,7);
                break;
            case R.id.photo_water_down_button:
                water = waterPhotoDownEditText.getText().toString();
                if(TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.down_photo_threshold_cannot_be_empty));
                    return;
                }
                temp = Double.parseDouble(water);
                if(temp > 9999.999)
                {
                    ToastUtil.showToastLong(getString(R.string.down_photo_threshold_range));
                }
                tt = String.valueOf(temp*1000);
                water = tt.substring(0, tt.indexOf("."));
                content = ConfigParams.SetWaterPicDown + ServiceUtils.getStr(water,7);
                break;

            default:
                break;
        }
        if (!TextUtils.isEmpty(content))
        {
            SocketUtil.getSocketUtil().sendContent(content);
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
        if (result.contains(ConfigParams.SetWaterMeterPara))
        {
            data = result.replaceAll(ConfigParams.SetWaterMeterPara, "").trim();
            if ("0".equals(data))
            {
                waterGroup.check(R.id.water_button);
            }
            else if ("1".equals(data))
            {
                waterGroup.check(R.id.water_button2);
            }
            else if ("2".equals(data))
            {
                waterGroup.check(R.id.water_button3);
            }
        }
        else if (result.contains(ConfigParams.SetWaterLeverMax))
        {
            data = result.replaceAll(ConfigParams.SetWaterLeverMax, "").trim();
            addWaterEditText.setText(ServiceUtils.getDouble(data));
        }
        else if (result.contains(ConfigParams.SetWaterLeverChangeUP))
        {
            data = result.replaceAll(ConfigParams.SetWaterLeverChangeUP, "").trim();
            addWaterUpEditText.setText(ServiceUtils.getDouble(data));
        }
        else if (result.contains(ConfigParams.SetWaterLeverChangeDW))
        {
            data = result.replaceAll(ConfigParams.SetWaterLeverChangeDW, "").trim();
            addWaterDownEditText.setText(ServiceUtils.getDouble(data));
        }
        else if (result.contains(ConfigParams.SetWaterPicUp))
        {
            data = result.replaceAll(ConfigParams.SetWaterPicUp, "").trim();
            waterPhotoUpEditText.setText(ServiceUtils.getDouble_1(data));
        }
        else if (result.contains(ConfigParams.SetWaterPicDown))
        {
            data = result.replaceAll(ConfigParams.SetWaterPicDown, "").trim();
            waterPhotoDownEditText.setText(ServiceUtils.getDouble_1(data));
        }
        else if (result.contains(ConfigParams.SetAddReportInterval))
        {
            data = result.replaceAll(ConfigParams.SetAddReportInterval, "").trim();
            addTimeEditText.setText(data);
        }
        else if (result.contains(ConfigParams.SetAnaWaterCal))
        {
            data = result.replaceAll(ConfigParams.SetAnaWaterCal, "").trim();
            if (ServiceUtils.isNumeric(data))
            {
                double level = Double.parseDouble(data) / 1000.0;
                String temp = String.valueOf(level);
                waterModifyEditText.setText(temp);
            }

            SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadSensorPara2);
        }
        else if (result.contains(ConfigParams.SetAnaWaterBac))
        {
            data = result.replaceAll(ConfigParams.SetAnaWaterBac, "").trim();
            if (ServiceUtils.isNumeric(data))
            {
                double level = Double.parseDouble(data) / 1000.0;
                String temp = String.valueOf(level);
                waterBasicEditText.setText(temp);
            }
        }
    }
}
