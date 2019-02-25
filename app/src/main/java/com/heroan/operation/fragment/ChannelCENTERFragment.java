package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

/**
 * Created by linxi on 2017/9/8.
 */

public class ChannelCENTERFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {
    private static final java.lang.String TAG = ChannelCENTERFragment.class.getSimpleName();
    private EditText center1AddEdittext;
    private EditText center2AddEdittext;
    private EditText center3AddEdittext;
    private EditText center4AddEdittext;
    private EditText fiveRainEdittext;
    private EditText waterPersonEdittext;
    private Button center1AddButton;
    private Button center2AddButton;
    private Button center3AddButton;
    private Button center4AddButton;
    private Button fiveRainButton;
    private Button waterPersonButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_comm_center);
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
        center1AddEdittext = (EditText) view.findViewById(R.id.center_1_add_edittext);
        center2AddEdittext = (EditText) view.findViewById(R.id.center_2_add_edittext);
        center3AddEdittext = (EditText) view.findViewById(R.id.center_3_add_edittext);
        center4AddEdittext = (EditText) view.findViewById(R.id.center_4_add_edittext);
        fiveRainEdittext = (EditText) view.findViewById(R.id.five_rain);
        waterPersonEdittext = (EditText) view.findViewById(R.id.water_person);

        center1AddButton = (Button) view.findViewById(R.id.center_1_add_button);
        center2AddButton = (Button) view.findViewById(R.id.center_2_add_button);
        center3AddButton = (Button) view.findViewById(R.id.center_3_add_button);
        center4AddButton = (Button) view.findViewById(R.id.center_4_add_button);
        fiveRainButton = (Button) view.findViewById(R.id.five_rain_button);
        waterPersonButton = (Button) view.findViewById(R.id.water_person_button);
    }

    @Override
    public void initData() {
        ServiceUtils.sendData(ConfigParams.ReadCommPara4);
    }

    @Override
    public void initEvent() {
        center1AddButton.setOnClickListener(this);
        center2AddButton.setOnClickListener(this);
        center3AddButton.setOnClickListener(this);
        center4AddButton.setOnClickListener(this);
        fiveRainButton.setOnClickListener(this);
        waterPersonButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String centerAdd = "";
        int centerNum;
        switch (view.getId()) {
            case R.id.center_1_add_button:

                centerAdd = center1AddEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd)) {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
                    return;
                }

                centerNum = Integer.parseInt(centerAdd);
                if (centerNum < 0 || centerNum > 255) {
                    ToastUtil.showToastLong(getString(R.string.Address_range));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.Setcenternumber + "1 " + ServiceUtils.getStr(centerAdd, 3));

                break;
            case R.id.center_2_add_button:
                centerAdd = center2AddEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd)) {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
                    return;
                }

                centerNum = Integer.parseInt(centerAdd);
                if (centerNum < 0 || centerNum > 255) {
                    ToastUtil.showToastLong(getString(R.string.Address_range));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.Setcenternumber + "2 " + ServiceUtils.getStr(centerAdd, 3));

                break;
            case R.id.center_3_add_button:
                centerAdd = center3AddEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd)) {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
                    return;
                }

                centerNum = Integer.parseInt(centerAdd);
                if (centerNum < 0 || centerNum > 255) {
                    ToastUtil.showToastLong(getString(R.string.Address_range));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.Setcenternumber + "3 " + ServiceUtils.getStr(centerAdd, 3));

                break;
            case R.id.center_4_add_button:
                centerAdd = center4AddEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(centerAdd)) {
                    ToastUtil.showToastLong(getString(R.string.Address_cannot_be_empty));
                    return;
                }

                centerNum = Integer.parseInt(centerAdd);
                if (centerNum < 0 || centerNum > 255) {
                    ToastUtil.showToastLong(getString(R.string.Address_range));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.Setcenternumber + "4 " + ServiceUtils.getStr(centerAdd, 3));

                break;
            case R.id.five_rain_button:
                centerAdd = fiveRainEdittext.getText().toString();
                ServiceUtils.sendData(ConfigParams.SendManualDataRain + centerAdd);
                break;
            case R.id.water_person_button:
                centerAdd = waterPersonEdittext.getText().toString();
                ServiceUtils.sendData(ConfigParams.SendManualDataWater + centerAdd);
                break;
            default:
                break;
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {

        Log.i(TAG, "1");
        String result = (String) args[0];
        String content = (String) args[1];
        if (TextUtils.isEmpty(result)) {
            return;
        }
        setData(result);

    }

    public void setData(String result) {
        String data = "";
        Log.i(TAG, data);
        if (result.contains(ConfigParams.Setcenternumber + "1")) {
            data = result.replaceAll(ConfigParams.Setcenternumber + "1", "").trim();
            center1AddEdittext.setText(data);
        } else if (result.contains(ConfigParams.Setcenternumber + "2")) {
            data = result.replaceAll(ConfigParams.Setcenternumber + "2", "").trim();
            center2AddEdittext.setText(data);
        } else if (result.contains(ConfigParams.Setcenternumber + "3")) {
            data = result.replaceAll(ConfigParams.Setcenternumber + "3", "").trim();
            center3AddEdittext.setText(data);
        } else if (result.contains(ConfigParams.Setcenternumber + "4")) {
            data = result.replaceAll(ConfigParams.Setcenternumber + "4", "").trim();
            center4AddEdittext.setText(data);
        }

    }
}
