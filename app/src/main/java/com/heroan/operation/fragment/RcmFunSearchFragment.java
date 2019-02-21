package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class RcmFunSearchFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate
{
    private static final String TAG = RcmFunSearchFragment.class.getSimpleName();

    private TextView resultTextView;

    private String addrNum ;
    private String workMode ;
    private String timeInterval ;
    private String Customer ;
    private String BatteryVolts ;
    private String Temperature ;
//    private String Collection_elements = "采集要素：";



    private ScrollView resultScroll;
    private StringBuffer currentSB = new StringBuffer();


    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_search;
    }

    @Override
    public void initComponentViews(View view)
    {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BUNDLE);

        resultTextView = (TextView) view.findViewById(R.id.result_data_textview);
        resultScroll = (ScrollView) view.findViewById(R.id.result_scroll);


        setData();

    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initData()
    {

    }


    @Override
    public void setListener()
    {
    }

    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        if (id == UiEventEntry.NOTIFY_BUNDLE)
        {
            setData();
        }
        else if (id == UiEventEntry.READ_DATA)
        {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content))
            {
                return;
            }
            readData(result, content);
        }
    }

    private void readData(String result, String content)
    {

        String data = "";

       if (result.contains(ConfigParams.SetAddr))
        {
            data = result.replaceAll(ConfigParams.SetAddr, "").trim();
            currentSB.insert(currentSB.indexOf(addrNum) + addrNum.length(),data.trim());
        }
        else if (result.contains(ConfigParams.SetWorkMode))
        {
            data = result.replaceAll(ConfigParams.SetWorkMode, "").trim();
            currentSB.insert(currentSB.indexOf(workMode) + workMode.length(),data.trim().equals("1") ? getString(R.string.always_online) : getString(R.string.low_power));
        }
        else if (result.contains(ConfigParams.BatteryVolts))
        {
            data = result.replaceAll(ConfigParams.BatteryVolts, "").trim();
            currentSB.insert(currentSB.indexOf(BatteryVolts) + BatteryVolts.length(),data.trim());
        }
        else if (result.contains(ConfigParams.Temperature))
        {
            data = result.replaceAll(ConfigParams.Temperature, "").trim();
            currentSB.insert(currentSB.indexOf(Temperature) + Temperature.length(), data);
        }
        else if (result.contains(ConfigParams.SetPacketInterval))
        {
            data = result.replaceAll(ConfigParams.SetPacketInterval, "").trim();
            int time = Integer.parseInt(data);
            currentSB.insert(currentSB.indexOf(timeInterval) + timeInterval.length(), ServiceUtils.getFunTimePos(time));
        }
        else if (result.contains(ConfigParams.CustomerSelect))
        {
            data = result.replaceAll(ConfigParams.CustomerSelect, "").trim();
            currentSB.insert(currentSB.indexOf(Customer) + Customer.length(), data);
        }
//        else if (result.contains(ConfigParams.SetScadaFactor))
//        {
//            data = result.replaceAll(ConfigParams.SetScadaFactor,"").trim();
//            currentSB.insert(currentSB.indexOf(Collection_elements) + Collection_elements.length(),data.trim());
//        }



        resultTextView.setText(currentSB.toString());
    }


    public void setData()
    {

        addrNum = getString(R.string.site_test_address)+":";
        workMode = getString(R.string.operation_status)+":";
        timeInterval = getString(R.string.dingshibao_jiange)+":";
        Customer = getString(R.string.client);
        BatteryVolts= getString(R.string.Battery_voltage);
        Temperature = getString(R.string.temperature);

        String content = ConfigParams.ReadFunctionData;
        SocketUtil.getSocketUtil().sendContent(content);
        currentSB.delete(0, currentSB.length());
        resultScroll.setVisibility(View.VISIBLE);
        currentSB.append(addrNum);
        currentSB.append("\n");
        currentSB.append(workMode);
        currentSB.append("\n");
        currentSB.append(timeInterval);
        currentSB.append("\n");
        currentSB.append(Customer);
        currentSB.append("\n");
        currentSB.append(BatteryVolts);
        currentSB.append("\n");
        currentSB.append(Temperature);
        currentSB.append("\n");

        if (resultTextView != null && currentSB.length() > 0)
        {
            resultTextView.setText(currentSB.toString());
        }
    }
}
