package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * 采集要素
 * Created by Vcontrol on 2016/11/23.
 */

public class CollectFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

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
    private CheckBox checkBox10;
    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;
    private CheckBox checkBox14;
    private CheckBox checkBox15;
    private CheckBox checkBox16;
    private CheckBox checkBox17;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_collect);
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
        checkBox10 = view.findViewById(R.id.checkbox10);
        checkBox11 = view.findViewById(R.id.checkbox11);
        checkBox12 = view.findViewById(R.id.checkbox12);
        checkBox13 = view.findViewById(R.id.checkbox13);
        checkBox14 = view.findViewById(R.id.checkbox14);
        checkBox15 = view.findViewById(R.id.checkbox15);
        checkBox16 = view.findViewById(R.id.checkbox16);
        checkBox17 = view.findViewById(R.id.checkbox17);
    }

    @Override
    public void initData() {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadSystemPara);
    }

    @Override
    public void initEvent() {
        collectButton.setOnClickListener(this);
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
                String check10 = checkBox10 != null && checkBox10.isChecked() ? "1" : "0";
                String check11 = checkBox11 != null && checkBox11.isChecked() ? "1" : "0";
                String check12 = checkBox12 != null && checkBox12.isChecked() ? "1" : "0";
                String check13 = checkBox13 != null && checkBox13.isChecked() ? "1" : "0";
                String check14 = checkBox14 != null && checkBox14.isChecked() ? "1" : "0";
                String check15 = checkBox15 != null && checkBox15.isChecked() ? "1" : "0";
                String check16 = checkBox16 != null && checkBox16.isChecked() ? "1" : "0";
                String check17 = checkBox17 != null && checkBox17.isChecked() ? "1" : "0";
                String content =
                        ConfigParams.SetScadaFactor + check1 + check2 + check3 + check4 + check5 + check6 + check7 + check8 + check9 + check10 + check11 + check12 + check13 + check14 + check15 + check16 + check17;
                SocketUtil.getSocketUtil().sendContent(content);
                break;
            default:
                break;
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
            setData(result);

        }
    }

    private void setData(String result) {
        if (result.contains(ConfigParams.SetScadaFactor.trim()) && (!result.equals("OK"))) {//
            // 获取采集要素设置
            String collect = result.replaceAll(ConfigParams.SetScadaFactor.trim(), "").trim();
            checkBox1.setChecked((collect.charAt(0)) == '1');
            checkBox2.setChecked((collect.charAt(1)) == '1');
            checkBox3.setChecked((collect.charAt(2)) == '1');
            checkBox4.setChecked((collect.charAt(3)) == '1');
            checkBox5.setChecked((collect.charAt(4)) == '1');
            checkBox6.setChecked((collect.charAt(5)) == '1');
            checkBox7.setChecked((collect.charAt(6)) == '1');
            checkBox8.setChecked((collect.charAt(7)) == '1');
            checkBox9.setChecked((collect.charAt(8)) == '1');
            if (collect.length() > 9) {
                checkBox10.setChecked((collect.charAt(9)) == '1');
            }
            if (collect.length() > 10) {
                checkBox11.setChecked((collect.charAt(10)) == '1');
            }
            if (collect.length() > 11) {
                checkBox12.setChecked((collect.charAt(11)) == '1');
            }
            if (collect.length() > 12) {
                checkBox13.setChecked((collect.charAt(12)) == '1');
            }
            if (collect.length() > 13) {
                checkBox14.setChecked((collect.charAt(13)) == '1');
            }
            if (collect.length() > 14) {
                checkBox15.setChecked((collect.charAt(14)) == '1');
            }
            if (collect.length() > 15) {
                checkBox16.setChecked((collect.charAt(15)) == '1');
            }
            if (collect.length() > 16) {
                checkBox17.setChecked((collect.charAt(16)) == '1');
            }
        }
    }
}
