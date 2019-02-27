package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by linxi on 2018/3/23.
 */

public class SoilSearchFragment extends BaseFragment implements View.OnClickListener,EventNotifyHelper.NotificationCenterDelegate
{
    private static final String TAG = SoilSearchFragment.class.getSimpleName();

    private TextView resultTextView;
    private String Temperature_G ;
    private String Temperature_2G ;
    private String Temperature_3G ;
    private String Moisture1 ;
    private String Moisture2 ;
    private String Moisture3 ;
    private String CDNR ;
    private String CDN2R ;
    private String CDN3R ;
    private String CDN4R ;

    private StringBuffer currentSB = new StringBuffer();
    private ScrollView resultScroll;


    private static SoilSearchFragment instance;

    public static SoilSearchFragment createInstance() {
        if (instance == null) {
            instance = new SoilSearchFragment();
        }
        return instance;
    }
    
    @Override
    public void onClick(View v)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_search);
        initView();
        initData();
        initEvent();
        return view;
    }




    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);

    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);


        resultTextView = (TextView) view.findViewById(R.id.result_data_textview);
        resultScroll = (ScrollView) view.findViewById(R.id.result_scroll);


    }

    @Override
    public void initData() {
        setData();
    }

    @Override
    public void initEvent() {

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

        if (result.contains(ConfigParams.Temperature_G))
        {

            currentSB.insert(currentSB.indexOf(Temperature_G) + Temperature_G.length(), result.replaceAll(ConfigParams.Temperature_G, "").trim());

        }else if (result.contains(ConfigParams.Temperature_2G))
        {
            currentSB.insert(currentSB.indexOf(Temperature_2G) + Temperature_2G.length(), result.replaceAll(ConfigParams.Temperature_2G, "").trim());
        }else if (result.contains(ConfigParams.Temperature_3G))
        {
            currentSB.insert(currentSB.indexOf(Temperature_3G) + Temperature_3G.length(), result.replaceAll(ConfigParams.Temperature_3G, "").trim());
        }
        else if (result.contains(ConfigParams.Moisture1))
        {
            currentSB.insert(currentSB.indexOf(Moisture1) + Moisture1.length(), result.replaceAll(ConfigParams.Moisture1, "").trim());
        }else if (result.contains(ConfigParams.Moisture2))
        {
            currentSB.insert(currentSB.indexOf(Moisture2) + Moisture2.length(), result.replaceAll(ConfigParams.Moisture2, "").trim());
        }
        else if (result.contains(ConfigParams.Moisture3))
        {
            currentSB.insert(currentSB.indexOf(Moisture3) + Moisture3.length(), result.replaceAll(ConfigParams.Moisture3, "").trim());
        }
        else if (result.contains(ConfigParams.CDNR))
        {
            currentSB.insert(currentSB.indexOf(CDNR) + CDNR.length(), result.replaceAll(ConfigParams.CDNR, "").trim());
        }
        else if (result.contains(ConfigParams.CDN2R))
        {
            currentSB.insert(currentSB.indexOf(CDN2R) + CDN2R.length(), result.replaceAll(ConfigParams.CDN2R, "").trim());
        }else if (result.contains(ConfigParams.CDN3R))
        {
            currentSB.insert(currentSB.indexOf(CDN3R) + CDN3R.length(), result.replaceAll(ConfigParams.CDN3R, "").trim());
        }else if (result.contains(ConfigParams.CDN4R))
        {
            currentSB.insert(currentSB.indexOf(CDN4R) + CDN4R.length(), result.replaceAll(ConfigParams.CDN4R, "").trim());
        }
        resultTextView.setText(currentSB.toString());

    }


    public void setData()
    {

        Temperature_G = getString(R.string.ground_temperature);
        Temperature_2G = getString(R.string.ground_temperature_2);
        Temperature_3G = getString(R.string.ground_temperature_3);
        Moisture1 = getString(R.string.shangqing)+"1：";
        Moisture2 = getString(R.string.shangqing)+"2：";
        Moisture3 = getString(R.string.shangqing)+"3：";
        CDNR = getString(R.string.Conductance);
        CDN2R = getString(R.string.Conductance_2);
        CDN3R = getString(R.string.Conductance_3);
        CDN4R = getString(R.string.Conductance_4);

        String content = ConfigParams.Read_turang_data;
        ServiceUtils.sendData(content);
        currentSB.delete(0, currentSB.length());
        resultScroll.setVisibility(View.VISIBLE);
        currentSB.append(Temperature_G);
        currentSB.append("℃");
        currentSB.append("\n");
        currentSB.append(Temperature_2G);
        currentSB.append("℃");
        currentSB.append("\n");
        currentSB.append(Temperature_3G);
        currentSB.append("℃");
        currentSB.append("\n");
        currentSB.append(Moisture1);
        currentSB.append("\n");
        currentSB.append(Moisture2);
        currentSB.append("\n");
        currentSB.append(Moisture3);
        currentSB.append("\n");
        currentSB.append(CDNR);
        currentSB.append("\n");
        currentSB.append(CDN2R);
        currentSB.append("\n");
        currentSB.append(CDN3R);
        currentSB.append("\n");
        currentSB.append(CDN4R);
        currentSB.append("\n");

        if (resultTextView != null && currentSB.length() > 0)
        {
            resultTextView.setText(currentSB.toString());
        }
    }
}
