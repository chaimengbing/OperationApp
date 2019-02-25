package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.heroan.operation.OperationApplication;
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

public class GroundADFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{


    private Button adUPButton;
    private Button adDownButton;
    private TextView currentLv;

    private boolean isHigh = true;


    private Runnable adRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (isHigh)
            {
                ServiceUtils.sendData(ConfigParams.ReadBatteryHighStatus);
            }
            else
            {
                ServiceUtils.sendData(ConfigParams.ReadBatteryLowStatus);
            }
            OperationApplication.applicationHandler.postDelayed(adRunnable, UiEventEntry.TIME);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_ground_ad);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        OperationApplication.applicationHandler.removeCallbacks(adRunnable);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_AD_LV);
    }


    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_AD_LV);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        adUPButton = (Button) view.findViewById(R.id.ad_up_button);
        adDownButton = (Button) view.findViewById(R.id.ad_down_button);
        currentLv = (TextView) view.findViewById(R.id.current_lv);
        currentLv.setText(R.string.nocollect);
    }

    @Override
    public void initData()
    {

    }

    @Override
    public void initEvent() {
        adDownButton.setOnClickListener(this);
        adUPButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {

//        if (!SocketUtil.getSocketUtil().isConnected())
//        {
//            return;
//        }

        switch (view.getId())
        {
            case R.id.ad_down_button:
                ServiceUtils.sendData(ConfigParams.SetBatteryLow);
                isHigh = false;
                break;
            case R.id.ad_up_button:
                ServiceUtils.sendData(ConfigParams.SetBatteryHigh);
                isHigh = true;
                break;

            default:
                break;
        }

        OperationApplication.applicationHandler.postDelayed(adRunnable, UiEventEntry.AD_TIME);

    }


    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        if (id == UiEventEntry.READ_AD_LV)
        {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result))
            {
                return;
            }
            if (result.contains("14V AD is measuring") || result.contains("11V AD is measuring"))
            {//正在配置

            }
            else if (result.contains("14V AD is OK") || result.contains("11V AD is OK"))
            {
                OperationApplication.applicationHandler.removeCallbacks(adRunnable);
                ToastUtil.showToastLong(getString(R.string.Acquisition_success));
            }
        }
        else if (id == UiEventEntry.READ_DATA)
        {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result))
            {
                return;
            }
            if (result.contains(ConfigParams.BatteryVolts))
            {
                String lv = result.replaceAll(ConfigParams.BatteryVolts, "").trim();

                if (currentLv != null)
                {
                    currentLv.setText(lv + getString(R.string.v));
                }
            }
        }
    }
}
