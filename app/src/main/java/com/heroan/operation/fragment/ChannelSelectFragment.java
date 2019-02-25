package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * 信道选择
 * Created by Vcontrol on 2017/03/27.
 */

public class ChannelSelectFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private RadioGroup centerGroup;
    private RadioGroup center2Group;
    private RadioGroup center3Group;
    private RadioGroup center4Group;

    private RadioGroup reserveGroup;
    private RadioGroup reserve2Group;
    private RadioGroup reserve3Group;
    private RadioGroup reserve4Group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_channel_select);
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

        centerGroup = view.findViewById(R.id.center_radiogroup);
        center2Group = view.findViewById(R.id.center_radiogroup_2);
        center3Group = view.findViewById(R.id.center_radiogroup_3);
        center4Group = view.findViewById(R.id.center_radiogroup_4);

        reserveGroup = view.findViewById(R.id.reserve_radiogroup);
        reserve2Group = view.findViewById(R.id.reserve_radiogroup_2);
        reserve3Group = view.findViewById(R.id.reserve_radiogroup_3);
        reserve4Group = view.findViewById(R.id.reserve_radiogroup_4);
    }

    @Override
    public void initData() {
        ServiceUtils.sendData(ConfigParams.ReadCommPara1);

    }

    @Override
    public void initEvent() {
        centerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetCenterType + "1 ";
                if (checkedId == R.id.no_use) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.gprs_radiobutton) {
                    ServiceUtils.sendData(content + "2");
                } else if (checkedId == R.id.gprs_gsm_radiobutton) {
                    ServiceUtils.sendData(content + "7");
                }
            }
        });
        center2Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetCenterType + "2 ";
                if (checkedId == R.id.no_use_2) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.gprs_radiobutton_2) {
                    ServiceUtils.sendData(content + "2");
                } else if (checkedId == R.id.gprs_gsm_radiobutton_2) {
                    ServiceUtils.sendData(content + "7");
                }
            }
        });
        center3Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetCenterType + "3 ";
                if (checkedId == R.id.no_use_3) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.gprs_radiobutton_3) {
                    ServiceUtils.sendData(content + "2");
                } else if (checkedId == R.id.gprs_gsm_radiobutton_3) {
                    ServiceUtils.sendData(content + "7");
                }
            }
        });
        center4Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetRS232_1_ADD_Channel + "1 ";
                if (checkedId == R.id.no_use_4) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.gprs_radiobutton_4) {
                    ServiceUtils.sendData(content + "1");
                }

            }
        });

        reserveGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetReserveType + "1 ";
                if (checkedId == R.id.no_use_sencond) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.sms_radiobutton) {
                    ServiceUtils.sendData(content + "1");
                } else if (checkedId == R.id.beidou_radiobutton) {
                    ServiceUtils.sendData(content + "3");
                }
            }
        });

        reserve2Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetReserveType + "2 ";
                if (checkedId == R.id.no_use_sencond_2) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.sms_radiobutton_2) {
                    ServiceUtils.sendData(content + "1");
                } else if (checkedId == R.id.beidou_radiobutton_2) {
                    ServiceUtils.sendData(content + "3");
                }
            }
        });
        reserve3Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetReserveType + "3 ";
                if (checkedId == R.id.no_use_sencond_3) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.sms_radiobutton_3) {
                    ServiceUtils.sendData(content + "1");
                } else if (checkedId == R.id.beidou_radiobutton_3) {
                    ServiceUtils.sendData(content + "3");
                }
            }
        });

        reserve4Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.SetReserveType + "4 ";
                if (checkedId == R.id.no_use_sencond_4) {
                    ServiceUtils.sendData(content + "0");
                } else if (checkedId == R.id.sms_radiobutton_4) {
                    ServiceUtils.sendData(content + "1");
                } else if (checkedId == R.id.beidou_radiobutton_4) {
                    ServiceUtils.sendData(content + "3");
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

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

        // 中心站主信道 1 2 3 4
        if (result.contains(ConfigParams.SetCenterType + "1")) {
            data = result.replaceAll(ConfigParams.SetCenterType + "1", "").trim();
            if ("0".equals(data)) {
                centerGroup.check(R.id.no_use);
            } else if ("2".equals(data)) {
                centerGroup.check(R.id.gprs_radiobutton);
            } else if ("7".equals(data)) {
                centerGroup.check(R.id.gprs_gsm_radiobutton);
            }
        } else if (result.contains(ConfigParams.SetCenterType + "2")) {
            data = result.replaceAll(ConfigParams.SetCenterType + "2", "").trim();
            if ("0".equals(data)) {
                center2Group.check(R.id.no_use_2);
            } else if ("2".equals(data)) {
                center2Group.check(R.id.gprs_radiobutton_2);
            } else if ("7".equals(data)) {
                center2Group.check(R.id.gprs_gsm_radiobutton_2);
            }
        } else if (result.contains(ConfigParams.SetCenterType + "3")) {
            data = result.replaceAll(ConfigParams.SetCenterType + "3", "").trim();
            if ("0".equals(data)) {
                center3Group.check(R.id.no_use_3);
            } else if ("2".equals(data)) {
                center3Group.check(R.id.gprs_radiobutton_3);
            } else if ("7".equals(data)) {
                center3Group.check(R.id.gprs_gsm_radiobutton_3);
            }
        } else if (result.contains(ConfigParams.SetRS232_1_ADD_Channel + "1")) {
            data = result.replaceAll(ConfigParams.SetRS232_1_ADD_Channel + "1", "").trim();
            if ("0".equals(data)) {
                center4Group.check(R.id.no_use_4);
            } else if ("1".equals(data)) {
                center4Group.check(R.id.gprs_radiobutton_4);
            }

        }
        // 中心站备用信道 1 2 3 4
        else if (result.contains(ConfigParams.SetReserveType + "1")) {
            data = result.replaceAll(ConfigParams.SetReserveType + "1", "").trim();
            if ("0".equals(data)) {
                reserveGroup.check(R.id.no_use_sencond);
            } else if ("1".equals(data)) {
                reserveGroup.check(R.id.sms_radiobutton);
            } else if ("3".equals(data)) {
                reserveGroup.check(R.id.beidou_radiobutton);
            }
        } else if (result.contains(ConfigParams.SetReserveType + "2")) {
            data = result.replaceAll(ConfigParams.SetReserveType + "2", "").trim();
            if ("0".equals(data)) {
                reserve2Group.check(R.id.no_use_sencond_2);
            } else if ("1".equals(data)) {
                reserve2Group.check(R.id.sms_radiobutton_2);
            } else if ("3".equals(data)) {
                reserve2Group.check(R.id.beidou_radiobutton_2);
            }
        } else if (result.contains(ConfigParams.SetReserveType + "3")) {
            data = result.replaceAll(ConfigParams.SetReserveType + "3", "").trim();
            if ("0".equals(data)) {
                reserve3Group.check(R.id.no_use_sencond_3);
            } else if ("1".equals(data)) {
                reserve3Group.check(R.id.sms_radiobutton_3);
            } else if ("3".equals(data)) {
                reserve3Group.check(R.id.beidou_radiobutton_3);
            }
        } else if (result.contains(ConfigParams.SetReserveType + "4")) {
            data = result.replaceAll(ConfigParams.SetReserveType + "4", "").trim();
            if ("0".equals(data)) {
                reserve4Group.check(R.id.no_use_sencond_4);
            } else if ("1".equals(data)) {
                reserve4Group.check(R.id.sms_radiobutton_4);
            } else if ("3".equals(data)) {
                reserve4Group.check(R.id.beidou_radiobutton_4);
            }
        }
    }

}
