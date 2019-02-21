package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
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

public class GroundWaterSearchFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate
{
    private static final String TAG = GroundWaterSearchFragment.class.getSimpleName();
    private TextView resultTextView;

//    遥测站编号: %d\r\n
//    0号中心站IP: %s\r\n
//    端口: %d\r\n
//    埋深: %dcm\r\n
//    软件版本号: %s\r\n
//    电池电压: %0.2f\r\n
//    水位: %0.3f\r\n
//    水温: %0.1f\r\n
//    信号值: %d\r\n
//    监测站时间: %4d-%2d-%2d %2d:%2d:%2d\r\n
//    GPRS召测模式: %d\r\n
//    低功耗模式: %d\r\n

    //全部参数
    public String GROUND_RTUid ;
    public String GROUND_Server_IP ;
    public String GROUND_Socket_Port;
    public String GROUND_maishen ;
    public String GROUND_Ver  ;
    public String GROUND_BatteryVolts ;
    public String GROUND_WaterLevel ;
    public String GROUND_Water_Temperature ;
    public String GROUND_SYLL ;
    public String GROUND_SYWater ;
    public String GROUND_GPRS_CSQ ;
//    public String GROUND_tm_year = "监测站时间：";
    public String GROUND_GPRSCallMode ;
    public String GROUND_RunMode ;

    public String GROUND_WaterLevel_2A;
    public String GROUND_GatePosition_1;


    private String CM = " cm";
    private String M = " m";
    private String C = " ℃";
    private String V = " V";
    private String M3 = " m³";

    private ScrollView resultScroll;
    private RelativeLayout receLayout;
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

        resultTextView = (TextView) view.findViewById(R.id.result_data_textview);
        resultScroll = (ScrollView) view.findViewById(R.id.result_scroll);
        receLayout = (RelativeLayout) view.findViewById(R.id.img_layout);



        setData();

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadData);
    }


    @Override
    public void setListener()
    {
    }

    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        if (id == UiEventEntry.READ_DATA)
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
        if (content.equals(ConfigParams.ReadData))
        {//通信状态查询
            if (result.contains(ConfigParams.GROUND_RTUid))
            {
                currentSB.insert(currentSB.indexOf(GROUND_RTUid) + GROUND_RTUid.length(), result.replaceAll(ConfigParams.GROUND_RTUid, "").trim());
            }
            else if (result.contains(ConfigParams.GROUND_Server_IP))
            {
                currentSB.insert(currentSB.indexOf(GROUND_Server_IP) + GROUND_Server_IP.length(), ServiceUtils.getRemoteIp(result.replaceAll(ConfigParams.GROUND_Server_IP, "").trim()));
            }
            else if (result.contains(ConfigParams.GROUND_Socket_Port))
            {
                currentSB.insert(currentSB.indexOf(GROUND_Socket_Port) + GROUND_Socket_Port.length(), result.replaceAll(ConfigParams.GROUND_Socket_Port, "").trim());
            }
            else if (result.contains(ConfigParams.GROUND_maishen))
            {
                currentSB.insert(currentSB.indexOf(GROUND_maishen) + GROUND_maishen.length(), result.replaceAll(ConfigParams.GROUND_maishen, "").trim());
            }
//            else if (result.contains(ConfigParams.GROUND_Ver))
//            {
//                currentSB.insert(currentSB.indexOf(GROUND_Ver) + GROUND_Ver.length(), result.replaceAll(ConfigParams.GROUND_Ver, "").trim());
//            }
            else if (result.contains(ConfigParams.GROUND_BatteryVolts))
            {
                currentSB.insert(currentSB.indexOf(GROUND_BatteryVolts) + GROUND_BatteryVolts.length(), result.replaceAll(ConfigParams.GROUND_BatteryVolts, "").trim());
            }
            else if (result.contains(ConfigParams.GROUND_WaterLevel))
            {
                currentSB.insert(currentSB.indexOf(GROUND_WaterLevel) + GROUND_WaterLevel.length(),result.replaceAll(ConfigParams.GROUND_WaterLevel, "").trim());
            }

            //水位2
            else if (result.contains(ConfigParams.GROUND_WaterLevel_2A))
            {
                currentSB.insert(currentSB.indexOf(GROUND_WaterLevel_2A) + GROUND_WaterLevel_2A.length(),result.replaceAll(ConfigParams.GROUND_WaterLevel_2A, "").trim());
            }
            //闸位计
            else if (result.contains(ConfigParams.GROUND_GatePosition_1))
            {
                currentSB.insert(currentSB.indexOf(GROUND_GatePosition_1) + GROUND_GatePosition_1.length(),result.replaceAll(ConfigParams.GROUND_GatePosition_1, "").trim());
            }


            else if (result.contains(ConfigParams.GROUND_Water_Temperature))
            {
                currentSB.insert(currentSB.indexOf(GROUND_Water_Temperature) + GROUND_Water_Temperature.length(), result.replaceAll(ConfigParams.GROUND_Water_Temperature, "").trim());
            }
            else if (result.contains(ConfigParams.GROUND_SYLL))
            {
                currentSB.insert(currentSB.indexOf(GROUND_SYLL) + GROUND_SYLL.length(),result.replaceAll(ConfigParams.GROUND_SYLL,"").trim());
            }
            else if (result.contains(ConfigParams.GROUND_SYWater))
            {
                currentSB.insert(currentSB.indexOf(GROUND_SYWater) + GROUND_SYWater.length(),result.replaceAll(ConfigParams.GROUND_SYWater,"").trim());
            }
            else if (result.contains(ConfigParams.GROUND_GPRS_CSQ))
            {
                currentSB.insert(currentSB.indexOf(GROUND_GPRS_CSQ) + GROUND_GPRS_CSQ.length(), result.replaceAll(ConfigParams.GROUND_GPRS_CSQ, "").trim());
            }
//            else if (result.contains(ConfigParams.GROUND_tm_year))
//            {
//                currentSB.insert(currentSB.indexOf(GROUND_tm_year) + GROUND_tm_year.length(), result.replaceAll(ConfigParams.GROUND_tm_year, "").trim());
//            }
            else if (result.contains(ConfigParams.GROUND_GPRSCallMode))
            {
                String model = result.replaceAll(ConfigParams.GROUND_GPRSCallMode, "").trim();
                String res = "";
                if (!TextUtils.isEmpty(model))
                {
                    if ("0".equals(model))
                    {
                        res = getString(R.string.no_call_test);
                    }
                    else
                    {
                        res = getString(R.string.zhaoce);
                    }
                    currentSB.insert(currentSB.indexOf(GROUND_GPRSCallMode) + GROUND_GPRSCallMode.length(), res);
                }
            }
            else if (result.contains(ConfigParams.GROUND_RunMode))
            {
                String model = result.replaceAll(ConfigParams.GROUND_RunMode, "").trim();
                String res = "";
                if (!TextUtils.isEmpty(model))
                {
                    if ("0".equals(model))
                    {
                        res = getString(R.string.low_power);
                    }
                    else
                    {
                        res = getString(R.string.always_online);
                    }
                    currentSB.insert(currentSB.indexOf(GROUND_RunMode) + GROUND_RunMode.length(), res);
                }
            }

            resultTextView.setText(currentSB.toString());
        }
    }


    public void setData()
    {

        GROUND_RTUid = getString(R.string.Telemetry_station_number);
        GROUND_Server_IP = getString(R.string.No_1_central_station_IP);
        GROUND_Socket_Port = getString(R.string.port);
        GROUND_maishen = getString(R.string.Buried_depth);
        GROUND_Ver = getString(R.string.Software_version_number);
        GROUND_BatteryVolts = getString(R.string.Battery_voltage);
        GROUND_WaterLevel = getString(R.string.WaterLevel);
        GROUND_WaterLevel_2A = getString(R.string.WaterLevel_2A);
        GROUND_GatePosition_1 = getString(R.string.GatePosition_1);
        GROUND_Water_Temperature = getString(R.string.Water_Temperature);
        GROUND_SYLL = getString(R.string.Remaining_traffic_value);
        GROUND_SYWater = getString(R.string.remaining_water);
        GROUND_GPRS_CSQ = getString(R.string.GPRS_CSQ);
//    public String GROUND_tm_year = "监测站时间：";
        GROUND_GPRSCallMode = getString(R.string.GPRSCallMode);
        GROUND_RunMode = getString(R.string.RunMode);

        currentSB.delete(0, currentSB.length());
        receLayout.setVisibility(View.GONE);
        resultScroll.setVisibility(View.VISIBLE);
        currentSB.append(GROUND_RTUid);
        currentSB.append("\n");
        currentSB.append(GROUND_Server_IP);
        currentSB.append("\n");
        currentSB.append(GROUND_Socket_Port);
        currentSB.append("\n");
        currentSB.append(GROUND_maishen);
        currentSB.append(CM);
        currentSB.append("\n");
//        currentSB.append(GROUND_Ver);
//        currentSB.append("\n");
        currentSB.append(GROUND_BatteryVolts);
        currentSB.append(V);
        currentSB.append("\n");
        currentSB.append(GROUND_WaterLevel);
        currentSB.append(M);
        currentSB.append("\n");

        currentSB.append(GROUND_WaterLevel_2A);
        currentSB.append(M);
        currentSB.append("\n");
        currentSB.append(GROUND_GatePosition_1);
        currentSB.append(M);
        currentSB.append("\n");


        currentSB.append(GROUND_Water_Temperature);
        currentSB.append(C);
        currentSB.append("\n");
        currentSB.append(GROUND_SYLL);
        currentSB.append(M3);
        currentSB.append("\n");
        currentSB.append(GROUND_SYWater);
        currentSB.append(M3);
        currentSB.append("\n");
        currentSB.append(GROUND_GPRS_CSQ);
        currentSB.append("\n");
//        currentSB.append(GROUND_tm_year);
//        currentSB.append("\n");
        currentSB.append(GROUND_GPRSCallMode);
        currentSB.append("\n");
        currentSB.append(GROUND_RunMode);
        currentSB.append("\n");
        if (resultTextView != null && currentSB.length() > 0)
        {
            resultTextView.setText(currentSB.toString());
        }

    }
}
