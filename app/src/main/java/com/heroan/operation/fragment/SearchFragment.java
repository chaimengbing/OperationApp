package com.heroan.operation.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;
import com.heroan.operation.view.ZoomImageView;

import java.io.File;
import java.io.FileInputStream;

import zuo.biao.library.base.BaseFragment;

/**
 * 查询界面
 * Created by Vcontrol on 2016/11/23.
 */

public class SearchFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener {
    private static final String TAG = SearchFragment.class.getSimpleName();
    private TextView resultTextView;
    private int search = 113;
    Context context;

    private String TotalRainVal = "累计雨量值：";
    private String PrecentRainVal;// = "当前雨量值：";
    private String WaterLevel_R;
    private String WaterLevel_A;
    private String WaterLevel_2R;
    private String WaterLevel_2A;
    private String WaterLevel_3R;
    private String WaterLevel_3A;
    private String WaterLevel_4R;
    private String WaterLevel_4A;

    private String Temperature_G;
    private String Temperature;
    private String BatteryVolts;
    private String SHIDIANBatteryVolts;
    /**
     * 河道瞬时流量：Water_Flow
     * 河道累积流量：Cumulative_Flow
     * 正累积流量（2）：Cumulative_Flow1
     * 负累积流量（3）：Cumulative_Flow2
     * 河道流速：Flow_Speed
     * 累计流量(21): Cumulative_Flow6
     * 风速：WindSpeed
     * 风向：WindDirection
     * 气温：Temperature_A
     * 湿度：humidity
     * <p>
     * 北京尚水：：：
     * 水温：Temperature_Water
     * 电导率： CDNR
     * PH值 ：  PH
     * 溶解氧 ：  DO
     * 浊度：  TRB
     * 叶绿素：  CHLA
     * 蓝绿藻：   Phycocyanin
     * 化学需氧量：  COD
     * 氨氮： NH4N
     */

    private String Water_Flow;
    private String Water_Flow2;
    private String Water_Flow3;
    private String Cumulative_Flow;
    private String Cumulative_Flow1;
    private String Cumulative_Flow2;
    private String Flow_Speed;
    private String Cumulative_Flow6;
    private String WindSpeed;
    private String WindDirection;
    private String Temperature_A;
    private String humidity;
    private String Press;


    /**
     * Moisture1  %0.1f\r\n            墒情1
     * Moisture2  %0.1f\r\n            墒情2
     * Moisture3  %0.1f\r\n            墒情3
     * M_Volt1 %0.2f\r\n               墒情1电压
     * M_Volt2 %0.2f\r\n               墒情2电压
     * M_Volt3 %0.2f\r\n               墒情3电压
     */
    private String Moisture1;
    private String Moisture2;
    private String Moisture3;
    private String Moisture4;
    private String Moisture5;
    private String Moisture6;
    private String M_Volt1;
    private String M_Volt2;
    private String M_Volt3;

    //北京尚水
    private String TRB;
    private String Temperature_Water;
    private String CDNR;
    private String PH;
    private String DO;
    private String CHLA;
    private String Phycocyanin;
    private String COD;
    private String NH4N;
    private String XZ;
    private String YZ;
    private String ZZ;

    private String valve_status;


    private String MM = " mm";
    private String M = " m";
    private String MS = " m/s";
    private String M3 = " m³";
    private String M3S = " m³/s";
    private String C = " ℃";
    private String V = " V";
    private String WMM = "W/㎡";

    private String PIC_Sta;
    private String PIC_Send_Sta1;
    private String PIC_Send_Sta2;
    private String PIC_Send_Sta3;
    private String PIC_Send_Sta4;
    private String PIC_Co1;
    private String PIC_Co2;
    private String PIC_Co3;
    private String PIC_Co4;
    private String PIC_Fr1;
    private String PIC_Fr2;
    private String PIC_Fr3;
    private String PIC_Fr4;
    private String Camerapercent;


    private String GPRS_CSQ;
    private String GPRS_Status;
    private String GPRS_SMS_Handle_Status_display;
    private String SOCKET_STATUS_1;
    private String SOCKET_STATUS_2;
    private String SOCKET_STATUS_3;
    private String SOCKET_STATUS_4;
    private String BEIDOU_CSQ;


    private String Send_informa_time_tm1;
    private String Send_informa_time_tm2;
    private String Send_informa_time_tm3;
    private String Send_informa_time_tm4;
    private String Send_inf_chanel1;
    private String Send_inf_chanel2;
    private String Send_inf_chanel3;
    private String Send_inf_chanel4;


    private String Equipment_Status;// = "设备状态：";
    // bit0--交流电充电状态，0--正常，1--停电
    public static String STATUS1;//  = "交流电充电状态：";
    // bit1--蓄电池电压状态，0--正常，1--报警
    public static String STATUS2;// = "蓄电池电压状态：";
    // bit2--水位超限报警状态，0--正常，1--报警
    public static String STATUS3;// = "水位超限报警状态：";
    // bit3--流量超限报警状态，0--正常，1--报警
    public static String STATUS4;// = "流量超限报警状态：";
    // bit4--水质超限报警状态，0--正常，1--报警
    public static String STATUS5;// = "水质超限报警状态：";
    // bit5--流量仪表状态状态，0--正常，1--故障
    public static String STATUS6;// = "流量仪表状态：";
    // bit6--水位仪表状态状态，0--正常，1--故障
    public static String STATUS7;// = "水位仪表状态：";
    // bit7--终端箱门状态，0--开启，1--关闭
    public static String STATUS8;// = "终端箱门状态：";
    // bit8--存储器状态，0--正常，1--异常
    public static String STATUS9;// = "存储器状态：";
    // bit9--IC卡功能有效状态，0--关闭，1--IC卡有效
    public static String STATUS10;// = "IC卡功能有效状态：";
    // bit10--水泵工作状态，0--水泵工作，1--水泵停机
    public static String STATUS11;// = "水泵工作状态：";
    // bit11--余水量报警，0--未超限，1--水量超限
    public static String STATUS12;// = "余水量报警：";
    // bit12--闸位仪表状态，0--正常，1--故障
    public static String STATUS13;// = "闸位仪表状态：";
    // bit13--墒情仪表状态，0--正常，1--故障
    public static String STATUS14;// = "墒情仪表状态：";
    // bit14--摄像头状态，0--正常，1--故障
    public static String STATUS15;// = "摄像头状态：";
    private String EQUIP_Reicv_count;// = "485传感器接收数据个数：";
    private String EQUIP_Reicv;// = "485传感器接收数据：";

    private String radiation;

    private ScrollView resultScroll;
    private ZoomImageView receImageView;
    private RelativeLayout receLayout;
    private StringBuffer currentSB = new StringBuffer();

    private LinearLayout control;
    private LinearLayout rainValue;
    private Button rainValueButton;
    private EditText rainValueEdittext;
    private LinearLayout cumulativeFlowValue;
    private Button cumulativeFlowButton;
    private EditText cumulativeFlowEdittext;
    private Button update;
    private Button stop;
    private Button send2pic;

    private int currentType = -1;

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            setData();
            OperationApplication.applicationHandler.postDelayed(timeRunnable, UiEventEntry.TIME);
        }
    };

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


    /**
     * 判断是否是2801
     *
     * @param bundle
     */
    private void initEthView(Bundle bundle) {
        if (bundle != null) {
            currentType = bundle.getInt(UiEventEntry.CURRENT_RTU_NAME);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_IMAGE_SUCCESS);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        OperationApplication.applicationHandler.removeCallbacks(timeRunnable);
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_IMAGE_SUCCESS);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BUNDLE);


        resultTextView = (TextView) view.findViewById(R.id.result_data_textview);
        resultScroll = (ScrollView) view.findViewById(R.id.result_scroll);
        receImageView = (ZoomImageView) view.findViewById(R.id.rece_imageview);
        receLayout = (RelativeLayout) view.findViewById(R.id.img_layout);

        control = (LinearLayout) view.findViewById(R.id.control);
        rainValue = (LinearLayout) view.findViewById(R.id.rain_value_max);
        update = (Button) view.findViewById(R.id.update_button);
        rainValueButton = (Button) view.findViewById(R.id.rainvalue_button);
        rainValueEdittext = (EditText) view.findViewById(R.id.rainvalue_edittext);
        stop = (Button) view.findViewById(R.id.stop_button);
        send2pic = (Button) view.findViewById(R.id.sens_2_pic_button);
        cumulativeFlowValue = (LinearLayout) view.findViewById(R.id.Cumulative_Flow);
        cumulativeFlowButton = (Button) view.findViewById(R.id.Cumulative_Flow_button);
        cumulativeFlowEdittext = (EditText) view.findViewById(R.id.Cumulative_Flow1);


    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            search = getArguments().getInt(UiEventEntry.CURRENT_SEARCH);
        } else {
            search = UiEventEntry.TAB_SEARCH_BASIC;
        }
        initEthView(getArguments());
        setData();

    }

    @Override
    public void initEvent() {
        update.setOnClickListener(this);
        stop.setOnClickListener(this);
        send2pic.setOnClickListener(this);
        rainValueButton.setOnClickListener(this);
        cumulativeFlowButton.setOnClickListener(this);
    }

    public void updateData() {
        stopUpdate();
        OperationApplication.applicationHandler.post(timeRunnable);
    }

    public void stopUpdate() {
        OperationApplication.applicationHandler.removeCallbacks(timeRunnable);
    }



    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.NOTIFY_BUNDLE) {
            Bundle bundle = (Bundle) args[0];
            if (bundle != null) {
                search = bundle.getInt(UiEventEntry.CURRENT_SEARCH);
            }
            setData();
        } else if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result)) {
                return;
            }
            readData(result);
        } else if (id == UiEventEntry.READ_IMAGE_SUCCESS) {
            readData("");
        }
    }

    private void readData(String result) {
        String timeR = "";
        String res = "";

        if (search == UiEventEntry.TAB_SEARCH_BASIC) {//基本查询
            if (result.contains(ConfigParams.TotalRainVal)) {
                String rain = result.replaceAll(ConfigParams.TotalRainVal, "").trim();
                rainValueEdittext.setText(rain);
//                currentSB.insert(currentSB.indexOf(TotalRainVal) + TotalRainVal.length(),
// result.replaceAll(ConfigParams.TotalRainVal, "").trim());
            } else if (result.contains(ConfigParams.Cumulative_Flowa)) {
                String cumulative = result.replaceAll(ConfigParams.Cumulative_Flowa, "").trim();
                cumulativeFlowEdittext.setText(cumulative);
            } else if (result.contains(ConfigParams.PrecentRainVal)) {
                currentSB.insert(currentSB.indexOf(PrecentRainVal) + PrecentRainVal.length(),
                        result.replaceAll(ConfigParams.PrecentRainVal, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_R)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_R) + WaterLevel_R.length(),
                        result.replaceAll(ConfigParams.WaterLevel_R, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_A)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_A) + WaterLevel_A.length(),
                        result.replaceAll(ConfigParams.WaterLevel_A, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_2R)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_2R) + WaterLevel_2R.length(),
                        result.replaceAll(ConfigParams.WaterLevel_2R, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_2A)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_2A) + WaterLevel_2A.length(),
                        result.replaceAll(ConfigParams.WaterLevel_2A, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_3R)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_3R) + WaterLevel_3R.length(),
                        result.replaceAll(ConfigParams.WaterLevel_3R, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_3A)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_3A) + WaterLevel_3A.length(),
                        result.replaceAll(ConfigParams.WaterLevel_3A, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_4R)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_4R) + WaterLevel_4R.length(),
                        result.replaceAll(ConfigParams.WaterLevel_4R, "").trim());
            } else if (result.contains(ConfigParams.WaterLevel_4A)) {
                currentSB.insert(currentSB.indexOf(WaterLevel_4A) + WaterLevel_4A.length(),
                        result.replaceAll(ConfigParams.WaterLevel_4A, "").trim());
            } else if (result.contains(ConfigParams.ANGEL_1)) {
                currentSB.insert(currentSB.indexOf(XZ) + XZ.length(),
                        result.replaceAll(ConfigParams.ANGEL_1, "").trim());
            } else if (result.contains(ConfigParams.ANGEL_2)) {
                currentSB.insert(currentSB.indexOf(YZ) + YZ.length(),
                        result.replaceAll(ConfigParams.ANGEL_2, "").trim());
            } else if (result.contains(ConfigParams.ANGEL_3)) {
                currentSB.insert(currentSB.indexOf(ZZ) + ZZ.length(),
                        result.replaceAll(ConfigParams.ANGEL_3, "").trim());
            } else if (result.contains(ConfigParams.Temperature_G)) {
                currentSB.insert(currentSB.indexOf(Temperature_G) + Temperature_G.length(),
                        result.replaceAll(ConfigParams.Temperature_G, "").trim());
            } else if (result.contains(ConfigParams.Temperature)) {
                currentSB.insert(currentSB.indexOf(Temperature) + Temperature.length(),
                        result.replaceAll(ConfigParams.Temperature, "").trim());
            } else if (result.contains(ConfigParams.BatteryVolts)) {
                if (result.contains(ConfigParams.SHIDIANBatteryVolts)) {
                    currentSB.insert(currentSB.indexOf(SHIDIANBatteryVolts) + SHIDIANBatteryVolts.length(), result.replaceAll(ConfigParams.SHIDIANBatteryVolts, "").trim());
                } else {
                    currentSB.insert(currentSB.indexOf(BatteryVolts) + BatteryVolts.length(),
                            result.replaceAll(ConfigParams.BatteryVolts, "").trim());
                }
            } else if (result.contains(ConfigParams.Water_Flow)) {
                currentSB.insert(currentSB.indexOf(Water_Flow) + Water_Flow.length(),
                        result.replaceAll(ConfigParams.Water_Flow, "").trim());
            } else if (result.contains(ConfigParams.Waterb_Flowb)) {
                currentSB.insert(currentSB.indexOf(Water_Flow2) + Water_Flow2.length(),
                        result.replaceAll(ConfigParams.Waterb_Flowb, "").trim());
            } else if (result.contains(ConfigParams.Waterc_Flowc)) {
                currentSB.insert(currentSB.indexOf(Water_Flow3) + Water_Flow3.length(),
                        result.replaceAll(ConfigParams.Waterc_Flowc, "").trim());
            } else if (result.contains(ConfigParams.Cumulative_Flowa)) {
                currentSB.insert(currentSB.indexOf(Cumulative_Flow) + Cumulative_Flow.length(),
                        result.replaceAll(ConfigParams.Cumulative_Flowa, "").trim());
            } else if (result.contains(ConfigParams.Cumulative_Flowb)) {
                currentSB.insert(currentSB.indexOf(Cumulative_Flow1) + Cumulative_Flow1.length(),
                        result.replaceAll(ConfigParams.Cumulative_Flowb, "").trim());
            } else if (result.contains(ConfigParams.Cumulative_Flowc)) {
                currentSB.insert(currentSB.indexOf(Cumulative_Flow2) + Cumulative_Flow2.length(),
                        result.replaceAll(ConfigParams.Cumulative_Flowc, "").trim());
            } else if (result.contains(ConfigParams.Flow_Speed)) {
                currentSB.insert(currentSB.indexOf(Flow_Speed) + Flow_Speed.length(),
                        result.replaceAll(ConfigParams.Flow_Speed, "").trim());
            } else if (result.contains(ConfigParams.Cumulative_Flowg)) {
                currentSB.insert(currentSB.indexOf(Cumulative_Flow6) + Cumulative_Flow6.length(),
                        result.replaceAll(ConfigParams.Cumulative_Flowg, "").trim());
            } else if (result.contains(ConfigParams.WindSpeed)) {
                currentSB.insert(currentSB.indexOf(WindSpeed) + WindSpeed.length(),
                        result.replaceAll(ConfigParams.WindSpeed, "").trim());
            } else if (result.contains(ConfigParams.WindDirection)) {
                currentSB.insert(currentSB.indexOf(WindDirection) + WindDirection.length(),
                        result.replaceAll(ConfigParams.WindDirection, "").trim());
            } else if (result.contains(ConfigParams.Temperature_A)) {
                currentSB.insert(currentSB.indexOf(Temperature_A) + Temperature_A.length(),
                        result.replaceAll(ConfigParams.Temperature_A, "").trim());
            } else if (result.contains(ConfigParams.humidity)) {
                currentSB.insert(currentSB.indexOf(humidity) + humidity.length(),
                        result.replaceAll(ConfigParams.humidity, "").trim());
            } else if (result.contains(ConfigParams.Press)) {
                currentSB.insert(currentSB.indexOf(Press) + Press.length(),
                        result.replaceAll(ConfigParams.Press, "").trim());
            } else if (result.contains(ConfigParams.radiation)) {
                currentSB.insert(currentSB.indexOf(radiation) + radiation.length(),
                        result.replaceAll(ConfigParams.radiation, "").trim());
            } else if (result.contains(ConfigParams.valve_status)) {
                String data = result.replaceAll(ConfigParams.valve_status, "").trim();
                if ("1".equals(data)) {
                    res = getString(R.string.Open);
                } else if ("3".equals(data)) {
                    res = getString(R.string.Opening);
                } else if ("2".equals(data)) {
                    res = getString(R.string.Closing);
                } else {
                    res = getString(R.string.Close);
                }
                currentSB.insert(currentSB.indexOf(valve_status) + valve_status.length(), res);
            } else if (result.contains(ConfigParams.Moisture1)) {
                currentSB.insert(currentSB.indexOf(Moisture1) + Moisture1.length(),
                        result.replaceAll(ConfigParams.Moisture1, "").trim());
            } else if (result.contains(ConfigParams.Moisture2)) {
                currentSB.insert(currentSB.indexOf(Moisture2) + Moisture2.length(),
                        result.replaceAll(ConfigParams.Moisture2, "").trim());
            } else if (result.contains(ConfigParams.Moisture3)) {
                currentSB.insert(currentSB.indexOf(Moisture3) + Moisture3.length(),
                        result.replaceAll(ConfigParams.Moisture3, "").trim());
            } else if (result.contains(ConfigParams.Moisture4)) {
                currentSB.insert(currentSB.indexOf(Moisture4) + Moisture4.length(),
                        result.replaceAll(ConfigParams.Moisture4, "").trim());
            } else if (result.contains(ConfigParams.Moisture5)) {
                currentSB.insert(currentSB.indexOf(Moisture5) + Moisture5.length(),
                        result.replaceAll(ConfigParams.Moisture5, "").trim());
            } else if (result.contains(ConfigParams.Moisture6)) {
                currentSB.insert(currentSB.indexOf(Moisture6) + Moisture6.length(),
                        result.replaceAll(ConfigParams.Moisture6, "").trim());
            } else if (result.contains(ConfigParams.M_Volt1)) {
                currentSB.insert(currentSB.indexOf(M_Volt1) + M_Volt1.length(),
                        result.replaceAll(ConfigParams.M_Volt1, "").trim());
            } else if (result.contains(ConfigParams.M_Volt2)) {
                currentSB.insert(currentSB.indexOf(M_Volt2) + M_Volt2.length(),
                        result.replaceAll(ConfigParams.M_Volt2, "").trim());
            } else if (result.contains(ConfigParams.M_Volt3)) {
                currentSB.insert(currentSB.indexOf(M_Volt3) + M_Volt3.length(),
                        result.replaceAll(ConfigParams.M_Volt3, "").trim());
            }
            if (UiEventEntry.isShangShui) {
                //北京尚水
                if (result.contains(ConfigParams.TRB)) {
                    currentSB.insert(currentSB.indexOf(TRB) + TRB.length(),
                            result.replaceAll(ConfigParams.TRB, "").trim());
                } else if (result.contains(ConfigParams.Temperature_Water)) {
                    currentSB.insert(currentSB.indexOf(Temperature_Water) + Temperature_Water.length(), result.replaceAll(ConfigParams.Temperature_Water, "").trim());
                } else if (result.contains(ConfigParams.CDNR)) {
                    currentSB.insert(currentSB.indexOf(CDNR) + CDNR.length(),
                            result.replaceAll(ConfigParams.CDNR, "").trim());
                } else if (result.contains(ConfigParams.PH)) {
                    currentSB.insert(currentSB.indexOf(PH) + PH.length(),
                            result.replaceAll(ConfigParams.PH, "").trim());
                } else if (result.contains(ConfigParams.DO)) {
                    currentSB.insert(currentSB.indexOf(DO) + DO.length(),
                            result.replaceAll(ConfigParams.DO, "").trim());
                } else if (result.contains(ConfigParams.CHLA)) {
                    currentSB.insert(currentSB.indexOf(CHLA) + CHLA.length(),
                            result.replaceAll(ConfigParams.CHLA, "").trim());
                } else if (result.contains(ConfigParams.Phycocyanin)) {
                    currentSB.insert(currentSB.indexOf(Phycocyanin) + Phycocyanin.length(),
                            result.replaceAll(ConfigParams.Phycocyanin, "").trim());
                } else if (result.contains(ConfigParams.COD)) {
                    currentSB.insert(currentSB.indexOf(COD) + COD.length(),
                            result.replaceAll(ConfigParams.COD, "").trim());
                } else if (result.contains(ConfigParams.NH4N)) {
                    currentSB.insert(currentSB.indexOf(NH4N) + NH4N.length(),
                            result.replaceAll(ConfigParams.NH4N, "").trim());
                }
            }

            resultTextView.setText(currentSB.toString());
        } else if (search == UiEventEntry.TAB_SEARCH_GPRS) {//通信状态查询
            if (result.contains(ConfigParams.GPRS_CSQ)) {
                currentSB.insert(currentSB.indexOf(GPRS_CSQ) + GPRS_CSQ.length(),
                        result.replaceAll(ConfigParams.GPRS_CSQ, "").trim());
            } else if (result.contains(ConfigParams.GPRS_Status)) {
                currentSB.insert(currentSB.indexOf(GPRS_Status) + GPRS_Status.length(),
                        ServiceUtils.getGPRSStatus(result.replaceAll(ConfigParams.GPRS_Status,
                                "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.GPRS_SMS_Handle_Status_display)) {
                currentSB.insert(currentSB.indexOf(GPRS_SMS_Handle_Status_display) + GPRS_SMS_Handle_Status_display.length(), ServiceUtils.getGSMStatus(result.replaceAll(ConfigParams.GPRS_SMS_Handle_Status_display, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.SOCKET_STATUS_1)) {
                currentSB.insert(currentSB.indexOf(SOCKET_STATUS_1) + SOCKET_STATUS_1.length(),
                        ServiceUtils.getSocketStatus(result.replaceAll(ConfigParams.SOCKET_STATUS_1, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.SOCKET_STATUS_2)) {
                currentSB.insert(currentSB.indexOf(SOCKET_STATUS_2) + SOCKET_STATUS_2.length(),
                        ServiceUtils.getSocketStatus(result.replaceAll(ConfigParams.SOCKET_STATUS_2, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.SOCKET_STATUS_3)) {
                currentSB.insert(currentSB.indexOf(SOCKET_STATUS_3) + SOCKET_STATUS_3.length(),
                        ServiceUtils.getSocketStatus(result.replaceAll(ConfigParams.SOCKET_STATUS_3, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.SOCKET_STATUS_4)) {
                currentSB.insert(currentSB.indexOf(SOCKET_STATUS_4) + SOCKET_STATUS_4.length(),
                        ServiceUtils.getSocketStatus(result.replaceAll(ConfigParams.SOCKET_STATUS_4, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.BEIDOU_CSQ)) {
                currentSB.insert(currentSB.indexOf(BEIDOU_CSQ) + BEIDOU_CSQ.length(),
                        result.replaceAll(ConfigParams.BEIDOU_CSQ, "").trim());
            } else if (result.contains(ConfigParams.Send_informa_time_tm1)) {
                currentSB.insert(currentSB.indexOf(Send_informa_time_tm1) + Send_informa_time_tm1.length(), result.replaceAll(ConfigParams.Send_informa_time_tm1, "").trim());
            } else if (result.contains(ConfigParams.Send_inf_chanel1)) {
                res = result.replaceAll(ConfigParams.Send_inf_chanel1, "").trim();
                if ("3".equals(res)) {
                    timeR = getString(R.string.beidou);
                } else if ("2".equals(res)) {
                    timeR = getString(R.string.sms);
                } else if ("1".equals(res)) {
                    timeR = "GPRS";
                } else {
                    timeR = getString(R.string.no);
                }
                currentSB.insert(currentSB.indexOf(Send_inf_chanel1) + Send_inf_chanel1.length(),
                        timeR);
            } else if (result.contains(ConfigParams.Send_informa_time_tm2)) {
                currentSB.insert(currentSB.indexOf(Send_informa_time_tm2) + Send_informa_time_tm2.length(), result.replaceAll(ConfigParams.Send_informa_time_tm2, "").trim());
            } else if (result.contains(ConfigParams.Send_inf_chanel2)) {
                res = result.replaceAll(ConfigParams.Send_inf_chanel2, "").trim();
                if ("3".equals(res)) {
                    timeR = getString(R.string.beidou);
                } else if ("2".equals(res)) {
                    timeR = getString(R.string.sms);
                } else if ("1".equals(res)) {
                    timeR = "GPRS";
                } else {
                    timeR = getString(R.string.no);
                }
                currentSB.insert(currentSB.indexOf(Send_inf_chanel2) + Send_inf_chanel2.length(),
                        timeR);
            } else if (result.contains(ConfigParams.Send_informa_time_tm3)) {
                currentSB.insert(currentSB.indexOf(Send_informa_time_tm3) + Send_informa_time_tm3.length(), result.replaceAll(ConfigParams.Send_informa_time_tm3, "").trim());
            } else if (result.contains(ConfigParams.Send_inf_chanel3)) {
                res = result.replaceAll(ConfigParams.Send_inf_chanel3, "").trim();
                if ("3".equals(res)) {
                    timeR = getString(R.string.beidou);
                } else if ("2".equals(res)) {
                    timeR = getString(R.string.sms);
                } else if ("1".equals(res)) {
                    timeR = "GPRS";
                } else {
                    timeR = getString(R.string.no);
                }
                currentSB.insert(currentSB.indexOf(Send_inf_chanel3) + Send_inf_chanel3.length(),
                        timeR);
            } else if (result.contains(ConfigParams.Send_informa_time_tm4)) {
                currentSB.insert(currentSB.indexOf(Send_informa_time_tm4) + Send_informa_time_tm4.length(), result.replaceAll(ConfigParams.Send_informa_time_tm4, "").trim());
            } else if (result.contains(ConfigParams.Send_inf_chanel4)) {
                res = result.replaceAll(ConfigParams.Send_inf_chanel4, "").trim();
                if ("3".equals(res)) {
                    timeR = getString(R.string.beidou);
                } else if ("2".equals(res)) {
                    timeR = getString(R.string.sms);
                } else if ("1".equals(res)) {
                    timeR = "GPRS";
                } else {
                    timeR = getString(R.string.no);
                }
                currentSB.insert(currentSB.indexOf(Send_inf_chanel4) + Send_inf_chanel4.length(),
                        timeR);
            }

            resultTextView.setText(currentSB.toString());
        } else if (search == UiEventEntry.TAB_SEARCH_CAMERA) {//摄像头状态查询
            if (result.contains(ConfigParams.PIC_Sta)) {
                currentSB.insert(currentSB.indexOf(PIC_Sta) + PIC_Sta.length(),
                        ServiceUtils.getCameraReadStatus(result.replaceAll(ConfigParams.PIC_Sta,
                                "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.PIC_Send_Sta1)) {
                currentSB.insert(currentSB.indexOf(PIC_Send_Sta1) + PIC_Send_Sta1.length(),
                        ServiceUtils.getImgSendStatus(result.replaceAll(ConfigParams.PIC_Send_Sta1, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.PIC_Send_Sta2)) {
                currentSB.insert(currentSB.indexOf(PIC_Send_Sta2) + PIC_Send_Sta2.length(),
                        ServiceUtils.getImgSendStatus(result.replaceAll(ConfigParams.PIC_Send_Sta2, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.PIC_Send_Sta3)) {
                currentSB.insert(currentSB.indexOf(PIC_Send_Sta3) + PIC_Send_Sta3.length(),
                        ServiceUtils.getImgSendStatus(result.replaceAll(ConfigParams.PIC_Send_Sta3, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.PIC_Send_Sta4)) {
                currentSB.insert(currentSB.indexOf(PIC_Send_Sta4) + PIC_Send_Sta4.length(),
                        ServiceUtils.getImgSendStatus(result.replaceAll(ConfigParams.PIC_Send_Sta4, "").trim(), getActivity()));
            } else if (result.contains(ConfigParams.PIC_Co1)) {
                currentSB.insert(currentSB.indexOf(PIC_Co1) + PIC_Co1.length(),
                        result.replaceAll(ConfigParams.PIC_Co1, "").trim());
            } else if (result.contains(ConfigParams.PIC_Co2)) {
                currentSB.insert(currentSB.indexOf(PIC_Co2) + PIC_Co2.length(),
                        result.replaceAll(ConfigParams.PIC_Co2, "").trim());
            } else if (result.contains(ConfigParams.PIC_Co3)) {
                currentSB.insert(currentSB.indexOf(PIC_Co3) + PIC_Co3.length(),
                        result.replaceAll(ConfigParams.PIC_Co3, "").trim());
            } else if (result.contains(ConfigParams.PIC_Co4)) {
                currentSB.insert(currentSB.indexOf(PIC_Co4) + PIC_Co4.length(),
                        result.replaceAll(ConfigParams.PIC_Co4, "").trim());
            } else if (result.contains(ConfigParams.PIC_Fr1)) {
                currentSB.insert(currentSB.indexOf(PIC_Fr1) + PIC_Fr1.length(),
                        result.replaceAll(ConfigParams.PIC_Fr1, "").trim());
            } else if (result.contains(ConfigParams.PIC_Fr2)) {
                currentSB.insert(currentSB.indexOf(PIC_Fr2) + PIC_Fr2.length(),
                        result.replaceAll(ConfigParams.PIC_Fr2, "").trim());
            } else if (result.contains(ConfigParams.PIC_Fr3)) {
                currentSB.insert(currentSB.indexOf(PIC_Fr3) + PIC_Fr3.length(),
                        result.replaceAll(ConfigParams.PIC_Fr3, "").trim());
            } else if (result.contains(ConfigParams.PIC_Fr4)) {
                currentSB.insert(currentSB.indexOf(PIC_Fr4) + PIC_Fr4.length(),
                        result.replaceAll(ConfigParams.PIC_Fr4, "").trim());
            } else if (result.contains(ConfigParams.Camerapercent)) {
                currentSB.insert(currentSB.indexOf(Camerapercent) + Camerapercent.length(),
                        result.replaceAll(ConfigParams.Camerapercent, "").trim());
            }

            resultTextView.setText(currentSB.toString());
        } else if (search == UiEventEntry.TAB_SEARCH_SENSOR) {//传感器状态查询
            if (result.contains(ConfigParams.Equipment_Status)) {
                String statusValues =
                        ServiceUtils.getStr(ServiceUtils.hexString2binaryString(result.replaceAll(ConfigParams.Equipment_Status, "").trim()), 32);
                if (TextUtils.isEmpty(statusValues)) {
                    return;
                }

                for (int i = statusValues.length() - 1; i > 16; i--) {
                    ServiceUtils.getSingleStatus(currentSB, i, statusValues.charAt(i),
                            getActivity());
                }
            } else if (result.contains(ConfigParams.EQUIP_Reicv_count)) {
                currentSB.insert(currentSB.indexOf(EQUIP_Reicv_count) + EQUIP_Reicv_count.length(), result.replaceAll(ConfigParams.EQUIP_Reicv_count, "").trim());
            } else if (result.contains(ConfigParams.EQUIP_Reicv)) {
                currentSB.insert(currentSB.indexOf(EQUIP_Reicv) + EQUIP_Reicv.length(),
                        "\n" + ServiceUtils.get485Data(result.replaceAll(ConfigParams.EQUIP_Reicv
                                , "")));
            }

            resultTextView.setText(currentSB.toString());
        } else if (search == UiEventEntry.TAB_SEARCH_READ_IMAGE) {//图片回放
            ToastUtil.showToastLong(getString(R.string.Get_picture_success));
            showImageView();
        }
    }

    private void showImageView() {
        if (!(new File(ConfigParams.ImagePath, ConfigParams.ImageName).exists())) {
            Log.i(TAG, "image is empty!!!");
            return;
        }

        if (receImageView != null) {

            Bitmap bm = ServiceUtils.getPhotoFromSDCard(ConfigParams.ImagePath,
                    ConfigParams.ImageName);
            if (bm != null) {
                receImageView.setImageBitmap(bm);
                receImageView.invalidate();
            }

        }
    }

    private boolean readImage() {
        try {
            Log.i(TAG, "readImage::");
            FileInputStream fis =
                    new FileInputStream(new File(ConfigParams.ImagePath + ConfigParams.ImageName));
            // FileOutputStream fos = new FileOutputStream(new
            // File(ConfigParams.ImagePath + "/rtu1.jpg"));

            byte[] bytes = new byte[1024];
            if (fis.read(bytes) < 0) {
                return false;
            }
            // while ((next = fis.read(bytes)) > 0)
            // {
            // // fos.write(bytes, 0, next);
            // // Log.i(TAG, "fis.read(bytes)" + fis.read(bytes));
            // Log.i(TAG, "bytes" + Arrays.toString(bytes));
            //
            // }

            // fos.flush();
            fis.close();
            // fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public void setData() {

        PrecentRainVal = getString(R.string.Current_hyetal_value) + ":" + " ";
        WaterLevel_R = getString(R.string.Relative_water_level_on_reservoir);
        WaterLevel_A = getString(R.string.Absolute_water_level_on_reservoir);
        WaterLevel_2R = getString(R.string.Relative_water_level_on_reservoir_2);
        WaterLevel_2A = getString(R.string.Absolute_water_level_on_reservoir_2);
        WaterLevel_3R = getString(R.string.Relative_water_level_on_reservoir_3);
        WaterLevel_3A = getString(R.string.Absolute_water_level_on_reservoir_3);
        WaterLevel_4R = getString(R.string.Relative_water_level_on_reservoir_4);
        WaterLevel_4A = getString(R.string.Absolute_water_level_on_reservoir_4);

        Temperature_G = getString(R.string.ground_temperature);
        Temperature = getString(R.string.RTU_Temperature);
        BatteryVolts = getString(R.string.Battery_voltage);
        SHIDIANBatteryVolts = getString(R.string.Commercial_Power);

        Water_Flow = getString(R.string.River_instantaneous_flow);
        Water_Flow2 = getString(R.string.River_instantaneous_flow_2);
        Water_Flow3 = getString(R.string.River_instantaneous_flow_3);
        Cumulative_Flow = getString(R.string.Cumulative_flow_of_river_channel);
        Cumulative_Flow1 = getString(R.string.Positive_cumulative_flow);
        Cumulative_Flow2 = getString(R.string.Negative_cumulative_flow);
        Flow_Speed = getString(R.string.Channel_velocity);
        Cumulative_Flow6 = getString(R.string.Cumulative_flow);
        WindSpeed = getString(R.string.Wind_speed);
        WindDirection = getString(R.string.WindDirection);
        Temperature_A = getString(R.string.Temperature);
        humidity = getString(R.string.humidity);
        Press = getString(R.string.Press);

        Moisture1 = getString(R.string.shangqing) + "1：";
        Moisture2 = getString(R.string.shangqing) + "2：";
        Moisture3 = getString(R.string.shangqing) + "3：";
        Moisture4 = getString(R.string.shangqing) + "4：";
        Moisture5 = getString(R.string.shangqing) + "5：";
        Moisture6 = getString(R.string.shangqing) + "6：";
        M_Volt1 = getString(R.string.shangqing) + "1" + getString(R.string.Voltage) + "：";
        M_Volt2 = getString(R.string.shangqing) + "2" + getString(R.string.Voltage) + "：";
        M_Volt3 = getString(R.string.shangqing) + "3" + getString(R.string.Voltage) + "：";

        //北京尚水
        TRB = getString(R.string.TRB) + "：";
        Temperature_Water = getString(R.string.Water_Temperature);
        CDNR = getString(R.string.Electrical_conductivity);
        PH = getString(R.string.PH_value);
        DO = getString(R.string.DO);
        CHLA = getString(R.string.Chlorophyll);
        Phycocyanin = getString(R.string.Blue_green_algae);
        COD = getString(R.string.Chemical_oxygen_demand);
        NH4N = getString(R.string.NH4N);
        XZ = getString(R.string.X_acceleration);
        YZ = getString(R.string.Y_acceleration);
        ZZ = getString(R.string.Z_acceleration);

        valve_status = getString(R.string.Valve_status);

        PIC_Sta = getString(R.string.Picture_read_status);
        PIC_Send_Sta1 = getString(R.string.Channel_1_picture);
        PIC_Send_Sta2 = getString(R.string.Channel_2_picture);
        PIC_Send_Sta3 = getString(R.string.Channel_3_picture);
        PIC_Send_Sta4 = getString(R.string.Channel_4_picture);
        PIC_Co1 = getString(R.string.PIC_Co1);
        PIC_Co2 = getString(R.string.PIC_Co2);
        PIC_Co3 = getString(R.string.PIC_Co3);
        PIC_Co4 = getString(R.string.PIC_Co4);
        PIC_Fr1 = getString(R.string.PIC_Fr1);
        PIC_Fr2 = getString(R.string.PIC_Fr2);
        PIC_Fr3 = getString(R.string.PIC_Fr3);
        PIC_Fr4 = getString(R.string.PIC_Fr4);
        Camerapercent = getString(R.string.Camerapercent);

        GPRS_CSQ = getString(R.string.Signal_strength);
        GPRS_Status = getString(R.string.GPRS_Status);
        GPRS_SMS_Handle_Status_display = getString(R.string.GSM_state);
        SOCKET_STATUS_1 = getString(R.string.SOCKET_STATUS_1);
        SOCKET_STATUS_2 = getString(R.string.SOCKET_STATUS_2);
        SOCKET_STATUS_3 = getString(R.string.SOCKET_STATUS_3);
        SOCKET_STATUS_4 = getString(R.string.SOCKET_STATUS_4);
        BEIDOU_CSQ = getString(R.string.BEIDOU_CSQ);


        Send_informa_time_tm1 = getString(R.string.Send_informa_time_tm1);
        Send_informa_time_tm2 = getString(R.string.Send_informa_time_tm2);
        Send_informa_time_tm3 = getString(R.string.Send_informa_time_tm3);
        Send_informa_time_tm4 = getString(R.string.Send_informa_time_tm4);
        Send_inf_chanel1 = getString(R.string.Send_inf_chanel1);
        Send_inf_chanel2 = getString(R.string.Send_inf_chanel2);
        Send_inf_chanel3 = getString(R.string.Send_inf_chanel3);
        String Send_inf_chanel4 = getString(R.string.Send_inf_chanel4);

        Equipment_Status = getString(R.string.Equipment_Status);
        STATUS1 = getString(R.string.STATUS1);
        STATUS2 = getString(R.string.STATUS2);
        STATUS3 = getString(R.string.STATUS3);
        STATUS4 = getString(R.string.STATUS4);
        STATUS5 = getString(R.string.STATUS5);
        STATUS6 = getString(R.string.STATUS6);
        STATUS7 = getString(R.string.STATUS7);
        STATUS8 = getString(R.string.STATUS8);
        STATUS9 = getString(R.string.STATUS9);
        STATUS10 = getString(R.string.STATUS10);
        STATUS11 = getString(R.string.STATUS11);
        STATUS12 = getString(R.string.STATUS12);
        STATUS13 = getString(R.string.STATUS13);
        STATUS14 = getString(R.string.STATUS14);
        STATUS15 = getString(R.string.STATUS15);
        EQUIP_Reicv_count = getString(R.string.EQUIP_Reicv_count);
        EQUIP_Reicv = getString(R.string.EQUIP_Reicv);
        radiation = getString(R.string.Sun_radiation);

        currentSB.delete(0, currentSB.length());
        rainValueEdittext.setText("");
        cumulativeFlowEdittext.setText("");
        receLayout.setVisibility(View.GONE);
        control.setVisibility(View.GONE);
        rainValue.setVisibility(View.GONE);
        cumulativeFlowValue.setVisibility(View.GONE);
        resultScroll.setVisibility(View.VISIBLE);
        if (search == UiEventEntry.TAB_SEARCH_BASIC) {
            rainValue.setVisibility(View.VISIBLE);
            cumulativeFlowValue.setVisibility(View.VISIBLE);
            SocketUtil.getSocketUtil().sendContent(ConfigParams.Readdata);
//            currentSB.append(TotalRainVal);
//            currentSB.append(MM);
//            currentSB.append("\n");
            currentSB.append(PrecentRainVal);
            currentSB.append(MM);
            currentSB.append("\n");
            currentSB.append(WaterLevel_R);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(WaterLevel_A);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(WaterLevel_2R);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(WaterLevel_2A);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(WaterLevel_3R);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(WaterLevel_3A);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(WaterLevel_4R);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(WaterLevel_4A);
            currentSB.append(M);
            currentSB.append("\n");
            currentSB.append(Temperature_G);
            currentSB.append(C);
            currentSB.append("\n");
            currentSB.append(Temperature);
            currentSB.append(C);
            currentSB.append("\n");
            currentSB.append(BatteryVolts);
            currentSB.append(V);
            currentSB.append("\n");
            currentSB.append(SHIDIANBatteryVolts);
            currentSB.append(V);
            currentSB.append("\n");

            currentSB.append(Water_Flow);
            currentSB.append(M3S);
            currentSB.append("\n");
            currentSB.append(Water_Flow2);
            currentSB.append(M3S);
            currentSB.append("\n");
            currentSB.append(Water_Flow3);
            currentSB.append(M3S);
            currentSB.append("\n");
//            currentSB.append(Cumulative_Flow);
//            currentSB.append(M3);
//            currentSB.append("\n");
            currentSB.append(Cumulative_Flow1);
            currentSB.append(M3);
            currentSB.append("\n");
            currentSB.append(Cumulative_Flow2);
            currentSB.append(M3);
            currentSB.append("\n");
            currentSB.append(Flow_Speed);
            currentSB.append(MS);
            currentSB.append("\n");
            currentSB.append(Cumulative_Flow6);
            currentSB.append(M3);
            currentSB.append("\n");
            currentSB.append(WindSpeed);
            currentSB.append(MS);
            currentSB.append("\n");
            currentSB.append(WindDirection);
            currentSB.append("°");
            currentSB.append("\n");
            currentSB.append(Temperature_A);
            currentSB.append(C);
            currentSB.append("\n");
            currentSB.append(humidity);
            currentSB.append("%RH");
            currentSB.append("\n");
            currentSB.append(Press);
            currentSB.append("MPa");
            currentSB.append("\n");
            currentSB.append(Moisture1);
            currentSB.append("\n");
            currentSB.append(Moisture2);
            currentSB.append("\n");
            currentSB.append(Moisture3);
            currentSB.append("\n");
            currentSB.append(Moisture4);
            currentSB.append("\n");
            currentSB.append(Moisture5);
            currentSB.append("\n");
            currentSB.append(Moisture6);
            currentSB.append("\n");
            currentSB.append(M_Volt1);
            currentSB.append("\n");
            currentSB.append(M_Volt2);
            currentSB.append("\n");
            currentSB.append(M_Volt3);
            currentSB.append("\n");
            currentSB.append(XZ);
            currentSB.append("\n");
            currentSB.append(YZ);
            currentSB.append("\n");
            currentSB.append(ZZ);
            currentSB.append("\n");
            currentSB.append(radiation);
            currentSB.append(WMM);
            currentSB.append("\n");


            if (currentType != UiEventEntry.WRU_2801) {
                currentSB.append(valve_status);
                currentSB.append("\n");
                if (UiEventEntry.isShangShui) {
                    //北京尚水
                    currentSB.append(TRB);
                    currentSB.append("NTU");
                    currentSB.append("\n");
                    currentSB.append(Temperature_Water);
                    currentSB.append(C);
                    currentSB.append("\n");
                    currentSB.append(CDNR);
                    currentSB.append("μS/cm");
                    currentSB.append("\n");
                    currentSB.append(PH);
                    currentSB.append("pH");
                    currentSB.append("\n");
                    currentSB.append(DO);
                    currentSB.append("mg/L");
                    currentSB.append("\n");
                    currentSB.append(CHLA);
                    currentSB.append("ug/L");
                    currentSB.append("\n");
                    currentSB.append(Phycocyanin);
                    currentSB.append("cells/mL");
                    currentSB.append("\n");
                    currentSB.append(COD);
                    currentSB.append("mg/L");
                    currentSB.append("\n");
                    currentSB.append(NH4N);
                    currentSB.append("mg/L");
                    currentSB.append("\n");
                }
            }


        } else if (search == UiEventEntry.TAB_SEARCH_GPRS) {
            control.setVisibility(View.VISIBLE);
            SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadRunStatus1);
            currentSB.append(GPRS_CSQ);
            currentSB.append("\n");
            currentSB.append(GPRS_Status);
            currentSB.append("\n");
            currentSB.append(GPRS_SMS_Handle_Status_display);
            currentSB.append("\n");
            currentSB.append(SOCKET_STATUS_1);
            currentSB.append("\n");
            currentSB.append(SOCKET_STATUS_2);
            currentSB.append("\n");
            currentSB.append(SOCKET_STATUS_3);
            currentSB.append("\n");
            currentSB.append(SOCKET_STATUS_4);
            currentSB.append("\n");
            currentSB.append(BEIDOU_CSQ);
            currentSB.append("\n");
            currentSB.append("\n");
            currentSB.append("\n");
            currentSB.append("\n");
            currentSB.append(Send_informa_time_tm1);
            currentSB.append("\n");
            currentSB.append(Send_inf_chanel1);
            currentSB.append("\n");
            currentSB.append(Send_informa_time_tm2);
            currentSB.append("\n");
            currentSB.append(Send_inf_chanel2);
            currentSB.append("\n");
            currentSB.append(Send_informa_time_tm3);
            currentSB.append("\n");
            currentSB.append(Send_inf_chanel3);
            currentSB.append("\n");
            currentSB.append(Send_informa_time_tm4);
            currentSB.append("\n");
            currentSB.append(Send_inf_chanel4);
            currentSB.append("\n");
        } else if (search == UiEventEntry.TAB_SEARCH_CAMERA) {
            control.setVisibility(View.VISIBLE);
            SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadRunStatus2);
            currentSB.append(PIC_Sta);
            currentSB.append("\n");
            currentSB.append(PIC_Send_Sta1);
            currentSB.append("\n");
            currentSB.append(PIC_Send_Sta2);
            currentSB.append("\n");
            currentSB.append(PIC_Send_Sta3);
            currentSB.append("\n");
            currentSB.append(PIC_Send_Sta4);
            currentSB.append("\n");
            currentSB.append(PIC_Co1);
            currentSB.append("\n");
            currentSB.append(PIC_Co2);
            currentSB.append("\n");
            currentSB.append(PIC_Co3);
            currentSB.append("\n");
            currentSB.append(PIC_Co4);
            currentSB.append("\n");
            currentSB.append(PIC_Fr1);
            currentSB.append("\n");
            currentSB.append(PIC_Fr2);
            currentSB.append("\n");
            currentSB.append(PIC_Fr3);
            currentSB.append("\n");
            currentSB.append(PIC_Fr4);
            currentSB.append("\n");
            currentSB.append(Camerapercent);
            currentSB.append("\n");
        } else if (search == UiEventEntry.TAB_SEARCH_SENSOR) {
            SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadRunStatus3);
            currentSB.append(Equipment_Status);
            currentSB.append("\n");
            currentSB.append(STATUS1);
            currentSB.append("\n");
            currentSB.append(STATUS2);
            currentSB.append("\n");
            currentSB.append(STATUS3);
            currentSB.append("\n");
            currentSB.append(STATUS4);
            currentSB.append("\n");
            currentSB.append(STATUS5);
            currentSB.append("\n");
            currentSB.append(STATUS6);
            currentSB.append("\n");
            currentSB.append(STATUS7);
            currentSB.append("\n");
            currentSB.append(STATUS8);
            currentSB.append("\n");
            currentSB.append(STATUS9);
            currentSB.append("\n");
            currentSB.append(STATUS10);
            currentSB.append("\n");
            currentSB.append(STATUS11);
            currentSB.append("\n");
            currentSB.append(STATUS12);
            currentSB.append("\n");
            currentSB.append(STATUS13);
            currentSB.append("\n");
            currentSB.append(STATUS14);
            currentSB.append("\n");
            currentSB.append(STATUS15);
            currentSB.append("\n");
            currentSB.append(EQUIP_Reicv_count);
            currentSB.append("\n");
            currentSB.append(EQUIP_Reicv);
            currentSB.append("\n");
        } else if (search == UiEventEntry.TAB_SEARCH_READ_IMAGE) {//图片回放
            receLayout.setVisibility(View.VISIBLE);
            resultScroll.setVisibility(View.GONE);

//            Drawable d = getResources().getDrawable(R.mipmap.small_rtu1);
//            receImageView.setImageBitmap(ImageTools.drawableToBitmap(d));
        }
        if (resultTextView != null && currentSB.length() > 0) {
            resultTextView.setText(currentSB.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_button:
                updateData();
                break;
            case R.id.stop_button:
                stopUpdate();
                break;
            case R.id.sens_2_pic_button:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SEND2PIC);
                break;
            case R.id.rainvalue_button:
                String rain = rainValueEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(rain)) {
                    ToastUtil.showToastLong(getString(R.string.cumulative_rainfall_value_empty));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.TotalRainVal + rain);
                break;
            case R.id.Cumulative_Flow_button:
                String cumulative = cumulativeFlowEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(cumulative)) {
                    ToastUtil.showToastLong(getString(R.string.cumulative_empty));
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(ConfigParams.Cumulative_Flowa + cumulative);
                break;

            default:
                break;

        }
    }
}
