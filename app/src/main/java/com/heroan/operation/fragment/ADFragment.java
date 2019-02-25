package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class ADFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private EditText adTlaEdittext;

    private EditText adThaEdittext;
    private EditText adWayEdittext;


    private EditText adTlvEdittext;
    private EditText adThvEdittext;
    private Button adTlvButton;
    private Button adThvButton;

    private Button adTlaButton;
    private Button adThaButton;

    private String ADV1;// = "AD1电压:";
    private String ADV2;// = "AD2电压:";
    private String ADV3;// = "AD3电压:";
    private String ADV4;// = "AD4电压:";
    private String ADV5;// = "AD5电压:";
    private String ADV6;// = "AD6电压:";
    private String ADV7;// = "AD7电压:";
    private String ADV8;// = "AD8电压:";

    private String ADA1;// = "AD1电流:";
    private String ADA2;// = "AD2电流:";
    private String ADA3;// = "AD3电流:";
    private String ADA4;// = "AD4电流:";
    private String ADA5;// = "AD5电流:";
    private String ADA6;// = "AD6电流:";
    private String ADA7;// = "AD7电流:";
    private String ADA8;// = "AD8电流:";

    private String enter_ad_number;
    private String ad_number_range;

    private String ad_current_calibration_large_value_empty;
    private String ad_current_calibration_small_value_empty;
    private String ad_voltage_calibration_large_value_null;
    private String ad_voltage_calibration_small_value_null;

    private StringBuffer currentSB = new StringBuffer();
    private TextView resultTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_ad);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        return view;
    }


    @Override
    public void onDestroy() {
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        super.onDestroy();
    }

    int type = UiEventEntry.WRU_2800;

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        adTlaButton = (Button) view.findViewById(R.id.ad_tla_button);
        adThaButton = (Button) view.findViewById(R.id.ad_tha_button);

        adTlaEdittext = (EditText) view.findViewById(R.id.ad_tla_edittext);
        adTlvButton = (Button) view.findViewById(R.id.ad_tlv_button);
        adThvButton = (Button) view.findViewById(R.id.ad_thv_button);
        adTlvEdittext = (EditText) view.findViewById(R.id.ad_tlv_edittext);
        adThvEdittext = (EditText) view.findViewById(R.id.ad_thv_edittext);
        adThaEdittext = (EditText) view.findViewById(R.id.ad_tha_edittext);
        adWayEdittext = (EditText) view.findViewById(R.id.ad_way_edittext);

        resultTextView = (TextView) view.findViewById(R.id.result_ad_textview);
    }

    @Override
    public void initData() {
        ADV1 = "AD1" + getString(R.string.Voltage) + ":";
        ADV2 = "AD2" + getString(R.string.Voltage) + ":";
        ADV3 = "AD3" + getString(R.string.Voltage) + ":";
        ADV4 = "AD4" + getString(R.string.Voltage) + ":";
        ADV5 = "AD5" + getString(R.string.Voltage) + ":";
        ADV6 = "AD6" + getString(R.string.Voltage) + ":";
        ADV7 = "AD7" + getString(R.string.Voltage) + ":";
        ADV8 = "AD8" + getString(R.string.Voltage) + ":";

        ADA1 = "AD1" + getString(R.string.Current) + ":";
        ADA2 = "AD2" + getString(R.string.Current) + ":";
        ADA3 = "AD3" + getString(R.string.Current) + ":";
        ADA4 = "AD4" + getString(R.string.Current) + ":";
        ADA5 = "AD5" + getString(R.string.Current) + ":";
        ADA6 = "AD6" + getString(R.string.Current) + ":";
        ADA7 = "AD7" + getString(R.string.Current) + ":";
        ADA8 = "AD8" + getString(R.string.Current) + ":";

        if (getArguments() != null) {
            type = getArguments().getInt(UiEventEntry.CURRENT_RTU_NAME);
        }
        if (type != UiEventEntry.WRU_2801) {
            setData();
        }
    }

    @Override
    public void initEvent() {
        adTlaButton.setOnClickListener(this);
        adTlvButton.setOnClickListener(this);
        adThvButton.setOnClickListener(this);
        adThaButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String adH = "";
        String way = adWayEdittext.getText().toString().trim();
        if (TextUtils.isEmpty(way)) {
            ToastUtil.showToastLong(getString(R.string.enter_AD_number));
            return;
        }
        int wayNum = Integer.parseInt(way);
        if (wayNum > 8 || wayNum < 1) {
            ToastUtil.showToastLong(getString(R.string.AD_number_range));
            return;
        }

        switch (view.getId()) {
            case R.id.ad_tha_button:
                adH = adThaEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(adH)) {
                    ToastUtil.showToastLong(getString(R.string.AD_current_calibration_large_value_empty));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.SETHA + wayNum + " " + adH);
                break;
            case R.id.ad_tla_button:
                adH = adTlaEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(adH)) {
                    ToastUtil.showToastLong(getString(R.string.AD_current_calibration_small_value_empty));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.SETLA + wayNum + " " + adH);
                break;
            case R.id.ad_thv_button:
                adH = adThvEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(adH)) {
                    ToastUtil.showToastLong(getString(R.string.AD_voltage_calibration_large_value_null));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.SETHV + wayNum + " " + adH);
                break;
            case R.id.ad_tlv_button:
                adH = adTlvEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(adH)) {
                    ToastUtil.showToastLong(getString(R.string.AD_voltage_calibration_small_value_null));
                    return;
                }
                ServiceUtils.sendData(ConfigParams.SETLV + wayNum + " " + adH);

                break;

            default:
                break;
        }

    }


    public void setData() {

        currentSB.delete(0, currentSB.length());
        ServiceUtils.sendData(ConfigParams.READAD);
        currentSB.append(ADV1);
        currentSB.append("\n");
        currentSB.append(ADV2);
        currentSB.append("\n");
        currentSB.append(ADV3);
        currentSB.append("\n");
        currentSB.append(ADV4);
        currentSB.append("\n");
        currentSB.append(ADV5);
        currentSB.append("\n");
        currentSB.append(ADV6);
        currentSB.append("\n");
        currentSB.append(ADV7);
        currentSB.append("\n");
        currentSB.append(ADV8);
        currentSB.append("\n");
        currentSB.append(ADA1);
        currentSB.append("\n");
        currentSB.append(ADA2);
        currentSB.append("\n");
        currentSB.append(ADA3);
        currentSB.append("\n");
        currentSB.append(ADA4);
        currentSB.append("\n");
        currentSB.append(ADA5);
        currentSB.append("\n");
        currentSB.append(ADA6);
        currentSB.append("\n");
        currentSB.append(ADA7);
        currentSB.append("\n");
        currentSB.append(ADA8);
        currentSB.append("\n");
        if (resultTextView != null && currentSB.length() > 0) {
            resultTextView.setText(currentSB.toString());
        }
    }


    private void readData(String result, String content) {
        if (content.equals(ConfigParams.READAD)) {//AD基本查询
            if (result.contains(ConfigParams.READADV1)) {
                currentSB.insert(currentSB.indexOf(ADV1) + ADV1.length(),
                        result.replaceAll(ConfigParams.READADV1, "").trim());
            } else if (result.contains(ConfigParams.READADV2)) {
                currentSB.insert(currentSB.indexOf(ADV2) + ADV2.length(),
                        result.replaceAll(ConfigParams.READADV2, "").trim());
            } else if (result.contains(ConfigParams.READADV3)) {
                currentSB.insert(currentSB.indexOf(ADV3) + ADV3.length(),
                        result.replaceAll(ConfigParams.READADV3, "").trim());
            } else if (result.contains(ConfigParams.READADV4)) {
                currentSB.insert(currentSB.indexOf(ADV4) + ADV4.length(),
                        result.replaceAll(ConfigParams.READADV4, "").trim());
            } else if (result.contains(ConfigParams.READADV5)) {
                currentSB.insert(currentSB.indexOf(ADV5) + ADV5.length(),
                        result.replaceAll(ConfigParams.READADV5, "").trim());
            } else if (result.contains(ConfigParams.READADV6)) {
                currentSB.insert(currentSB.indexOf(ADV6) + ADV6.length(),
                        result.replaceAll(ConfigParams.READADV6, "").trim());
            } else if (result.contains(ConfigParams.READADV7)) {
                currentSB.insert(currentSB.indexOf(ADV7) + ADV7.length(),
                        result.replaceAll(ConfigParams.READADV7, "").trim());
            } else if (result.contains(ConfigParams.READADV8)) {
                currentSB.insert(currentSB.indexOf(ADV8) + ADV8.length(),
                        result.replaceAll(ConfigParams.READADV8, "").trim());
            } else if (result.contains(ConfigParams.READADA1)) {
                currentSB.insert(currentSB.indexOf(ADA1) + ADA1.length(),
                        result.replaceAll(ConfigParams.READADA1, "").trim());
            } else if (result.contains(ConfigParams.READADA2)) {
                currentSB.insert(currentSB.indexOf(ADA2) + ADA2.length(),
                        result.replaceAll(ConfigParams.READADA2, "").trim());
            } else if (result.contains(ConfigParams.READADA3)) {
                currentSB.insert(currentSB.indexOf(ADA3) + ADA3.length(),
                        result.replaceAll(ConfigParams.READADA3, "").trim());
            } else if (result.contains(ConfigParams.READADA4)) {
                currentSB.insert(currentSB.indexOf(ADA4) + ADA4.length(),
                        result.replaceAll(ConfigParams.READADA4, "").trim());
            } else if (result.contains(ConfigParams.READADA5)) {
                currentSB.insert(currentSB.indexOf(ADA5) + ADA5.length(),
                        result.replaceAll(ConfigParams.READADA5, "").trim());
            } else if (result.contains(ConfigParams.READADA6)) {
                currentSB.insert(currentSB.indexOf(ADA6) + ADA6.length(),
                        result.replaceAll(ConfigParams.READADA6, "").trim());
            } else if (result.contains(ConfigParams.READADA7)) {
                currentSB.insert(currentSB.indexOf(ADA7) + ADA7.length(),
                        result.replaceAll(ConfigParams.READADA7, "").trim());
            } else if (result.contains(ConfigParams.READADA8)) {
                currentSB.insert(currentSB.indexOf(ADA8) + ADA8.length(),
                        result.replaceAll(ConfigParams.READADA8, "").trim());
            }

            resultTextView.setText(currentSB.toString());
        }
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content)) {
                return;
            }
            readData(result, content);
        }
    }
}
