package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by linxi on 2018/4/19.
 */

public class AmmeterFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {
    private final String TAG = AmmeterFragment.class.getSimpleName();

    private Spinner ammeterSpinner;
    private String[] ammeterItems;
    private SimpleSpinnerAdapter ammeterAdapter;
    private boolean isFirst = true;

    private static AmmeterFragment instance;

    public static AmmeterFragment createInstance() {
        if (instance == null) {
            instance = new AmmeterFragment();
        }
        return instance;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_ammeter);
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
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        ammeterSpinner = (Spinner) view.findViewById(R.id.ammeter);
    }

    @Override
    public void initData() {
        ammeterItems = getResources().getStringArray(R.array.ammeter);
        ammeterAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item,
                ammeterItems);
        ammeterSpinner.setAdapter(ammeterAdapter);
        ServiceUtils.sendData(ConfigParams.ReadDIANBIAO_SensorPara);
    }

    @Override
    public void initEvent() {
        ammeterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (isFirst) {
                    isFirst = false;
                    return;
                }

                ammeterAdapter.setSelectedItem(i);
                i += 1;
                ServiceUtils.sendData(ConfigParams.Setdiaobian_guiyue + ServiceUtils.getStr("" + i, 2));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        if (result.contains(ConfigParams.Setdiaobian_guiyue)) {
            data = result.replaceAll(ConfigParams.Setdiaobian_guiyue, "").trim();
            if (ServiceUtils.isNumeric(data)) {
                int t = Integer.parseInt(data);
                if (t < ammeterItems.length) {
                    ammeterSpinner.setSelection(t, false);
                }
                ammeterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id) {
                        ammeterAdapter.setSelectedItem(position);
                        String water485 = ammeterItems[position];
                        if ("无".equals(water485)) {
                            return;
                        }

                        position += 1;
                        ServiceUtils.sendData(ConfigParams.Setdiaobian_guiyue + ServiceUtils.getStr("" + position, 2));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }
}
