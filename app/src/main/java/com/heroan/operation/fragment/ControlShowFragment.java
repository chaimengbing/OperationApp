package com.heroan.operation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heroan.operation.R;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by linxi on 2017/4/27.
 */
public class ControlShowFragment extends BaseFragment
{

    private  static final String TAG = ControlShowFragment.class.getSimpleName();

    private String showResult =getString(R.string.temperature)+ "23℃\n" +getString(R.string.humidity)+
            "55%RH\n" +getString(R.string.Wind_speed)+getString(R.string.Atmospheric_pressure)+
            "2\n" +
            "：95KPa\n" +getString(R.string.shangqing)+
            "：30%";
    private TextView showTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_control);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void initView() {
        showTextView = (TextView) view.findViewById(R.id.show_data_textview);
        showTextView.setText(showResult);
    }

    @Override
    public void initData()
    {

    }

    @Override
    public void initEvent() {

    }


}
