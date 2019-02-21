package com.heroan.operation.fragment;

import android.view.View;
import android.widget.TextView;

import com.heroan.operation.R;

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
    public int getLayoutView()
    {
        return R.layout.fragment_setting_control;
    }

    @Override
    public void initComponentViews(View view)
    {
        showTextView = (TextView) view.findViewById(R.id.show_data_textview);

        showTextView.setText(showResult);
    }

    @Override
    public void initData()
    {

    }

    @Override
    public void setListener()
    {

    }

}
