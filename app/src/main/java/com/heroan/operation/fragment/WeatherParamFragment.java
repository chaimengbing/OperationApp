package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;
/**
 * Created by linxi on 2018/4/2.
 */

public class WeatherParamFragment extends BaseFragment implements View.OnClickListener,EventNotifyHelper.NotificationCenterDelegate
{
    private final String TAG = WeatherParamFragment.class.getSimpleName();

    private Spinner waterQualitySpinner;
    private String[] waterQualityItems;
    private SimpleSpinnerAdapter waterQualityAdapter;
    private boolean isFirst = true;
    @Override
    public void onClick(View v)
    {

    }

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_weather_param;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }
    @Override
    public void initComponentViews(View view)
    {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        waterQualitySpinner = (Spinner) view.findViewById(R.id.weather_param);
    }

    @Override
    public void initData()
    {
        waterQualityItems = getResources().getStringArray(R.array.weather_param);
        waterQualityAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, waterQualityItems);
        waterQualitySpinner.setAdapter(waterQualityAdapter);
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadWeatherParam);
    }

    @Override
    public void setListener()
    {
        waterQualitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {

                if (isFirst)
                {
                    isFirst = false;
                    return;
                }

                waterQualityAdapter.setSelectedItem(i);

                String content = ConfigParams.SetWaterQuality + i;
                SocketUtil.getSocketUtil().sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

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
        if (result.contains(ConfigParams.ReadWeatherParam))
        {
            data = result.replaceAll(ConfigParams.ReadWeatherParam, "").trim();
            if (ServiceUtils.isNumeric(data))
            {
                int t = Integer.parseInt(data);
                if (t < waterQualityItems.length)
                {
                    waterQualitySpinner.setSelection(t, false);
                }
                waterQualitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        waterQualityAdapter.setSelectedItem(position);
                        String water485 = waterQualityItems[position];
                        if ("无".equals(water485))
                        {
                            return;
                        }
                        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadWeatherParam + ServiceUtils.getStr("" + position,2));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });
            }
        }
    }
}


