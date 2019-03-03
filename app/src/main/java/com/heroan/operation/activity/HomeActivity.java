package com.heroan.operation.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;
import com.heroan.operation.adapter.CustomAdapter;
import com.heroan.operation.adapter.CustomToAdapter;
import com.heroan.operation.fragment.ADFragment;
import com.heroan.operation.fragment.AmmeterFragment;
import com.heroan.operation.fragment.AmmeterSearchFragment;
import com.heroan.operation.fragment.AnalogQuantityFragment;
import com.heroan.operation.fragment.AtherPamarsFragment;
import com.heroan.operation.fragment.CameraFragment;
import com.heroan.operation.fragment.ChannelBEIFragment;
import com.heroan.operation.fragment.ChannelCENTERFragment;
import com.heroan.operation.fragment.ChannelFragment;
import com.heroan.operation.fragment.ChannelGMSFragment;
import com.heroan.operation.fragment.ChannelGPRSFragment;
import com.heroan.operation.fragment.ChannelSelectFragment;
import com.heroan.operation.fragment.CollectFragment;
import com.heroan.operation.fragment.CommParamsFragment;
import com.heroan.operation.fragment.FlowFragment;
import com.heroan.operation.fragment.LSearchFragment;
import com.heroan.operation.fragment.PressFragment;
import com.heroan.operation.fragment.RTUVersionFragment;
import com.heroan.operation.fragment.RainPamarsFragment;
import com.heroan.operation.fragment.SQFragment;
import com.heroan.operation.fragment.SearchDataFragment;
import com.heroan.operation.fragment.SearchFragment;
import com.heroan.operation.fragment.SoilSearchFragment;
import com.heroan.operation.fragment.SystemPamarsFragment;
import com.heroan.operation.fragment.ValveControlRelayFragment;
import com.heroan.operation.fragment.WQualitySearchFragment;
import com.heroan.operation.fragment.WaterPamarsFragment;
import com.heroan.operation.fragment.WaterPlanFragment;
import com.heroan.operation.fragment.WaterQualityFragment;
import com.heroan.operation.fragment.WeatherParamFragment;
import com.heroan.operation.fragment.YPTFragment;
import com.heroan.operation.fragment.ZWFragment;
import com.heroan.operation.utils.BleUtils;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.util.ArrayList;
import java.util.List;

import cn.com.heaton.blelibrary.ble.BleDevice;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.ScreenUtil;
import zuo.biao.library.util.SettingUtil;

import static com.heroan.operation.utils.ServiceUtils.sendData;


/**
 * Created by Vcontrol on 2016/11/24.
 */

public class HomeActivity extends BaseActivity implements PopupWindow.OnDismissListener, EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private LinearLayout titleLayout;
    private LinearLayout bottomLayout;
    private LinearLayout bottomenuLayout;
    private TextView rtuSetting;
    private TextView rtuSearch;
    private TextView rtuVersion;

    private TextView titleRight;
    private TextView title;
    private ImageView backImageView;
    private int currentTab = 44;


    private LinearLayout setLayout;
    private TextView setTextView;
    private ImageView iconImageView;

    private ListView setListView;
    private ListView setToListView;
    private TextView sensorTextView;
    private LinearLayout sensorLayout;

    private CustomToAdapter setToAdapter;
    private CustomAdapter setAdapter;
    private String tabName = "";

    private PopupWindow popupWindow;
    private List<String> searchList;
    private List<String> setList;
    private List<String> setToList;
    private List<String> setToChannelList;


    private SearchFragment gprsFragment = null;
    private ADFragment adFragment = null;
    private ChannelFragment channelFragment = null;
    private LSearchFragment lSearchFragment = null;


    private BleDevice bleDevice;

    //当前选项
    private int currentSel = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rtu_basic);
        initView();
        initData();
        initEvent();
    }

    public void setCurrentSel(int currentSel) {
        this.currentSel = currentSel;
    }

    private void initSetToData() {

        setToList.add(getString(R.string.rain_pamars));
        setToList.add(getString(R.string.water_pamars));
        setToList.add(getString(R.string.water_plan));
        setToList.add(getString(R.string.camera));
        setToList.add(getString(R.string.shangqing));
        setToList.add(getString(R.string.gprs_plan));
        setToList.add(getString(R.string.zawei_plan));
        setToList.add(getString(R.string.ather_pamars));
        setToList.add(getString(R.string.Water_quality_parameters));
        setToList.add(getString(R.string.Meteorological_parameters));
        setToList.add(getString(R.string.Analog_settings));
        setToList.add(getString(R.string.Meter_settings));
        setToList.add("温度计");
        setToList.add(getString(R.string.Manometer));

    }

    private void initSetChannelData() {

        setToChannelList.add(getString(R.string.channel_select));
        setToChannelList.add(getString(R.string.gprs_pamars));
        setToChannelList.add(getString(R.string.gms_pamars));
        setToChannelList.add(getString(R.string.bei_pamars));
        setToChannelList.add(getString(R.string.Central_station_address_setting));
        setToChannelList.add(getString(R.string.Channel_demonstration_settings));

    }


    private void initSetData() {
        setList.add(getString(R.string.system_params_setting));
        setList.add(getString(R.string.collect_setting));
        setList.add(getString(R.string.comm_params_setting));
        setList.add(getString(R.string.channel_setting));
        setList.add(getString(R.string.sensor_setting));
        setList.add(getString(R.string.ad_setting));
        setList.add(getString(R.string.Valve_control_relay_settings));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy::");
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_RESULT_OK);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_RESULT_ERROR);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.CONNCT_AGAIN);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.CONNCT_OK);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.CONNCT_FAIL);
    }

    private void selRtu(TextView textView, int id) {

        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.bottomblack));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(id), null, null);
        }
    }

    private void nolRtu(TextView textView, int id) {
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(id), null, null);
        }
    }

    @Override
    public void onClick(View view) {
        String rtuDetail = "";
        Bundle bundle = new Bundle();
        bundle.putInt(UiEventEntry.CURRENT_SEARCH, UiEventEntry.TAB_SEARCH_BASIC);
        switch (view.getId()) {
            case R.id.rtu_setting:
                if (SearchFragment.createInstance().isVisible()) {
                    SearchFragment.createInstance().stopUpdate();
                }

                rtuDetail = rtuSetting.getText().toString().trim();
                setTitleName(rtuDetail);
                selRtu(rtuSetting, R.mipmap.rtu_setting_sel);
                nolRtu(rtuSearch, R.mipmap.rtu_search);
                nolRtu(rtuVersion, R.mipmap.rtu_version);

                turnToFragmentStack(R.id.detail_layout, SystemPamarsFragment.createInstance());
                titleLayout.setVisibility(View.VISIBLE);
                if (setList.size() > 0) {
                    setTextView.setText(setList.get(0));
                }
                currentTab = UiEventEntry.TAB_SETTING;

                currentSel = UiEventEntry.TAB_SETTING_SYS;

                updateRight();
                setTitleRightVisible(View.VISIBLE);

                if (setList.size() == 1) {
                    iconImageView.setVisibility(View.GONE);
                } else {
                    iconImageView.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rtu_search:
                rtuDetail = rtuSearch.getText().toString().trim();
                setTitleName(rtuDetail);

                selRtu(rtuSearch, R.mipmap.rtu_search_sel);
                nolRtu(rtuSetting, R.mipmap.rtu_setting);
                nolRtu(rtuVersion, R.mipmap.rtu_version);


                titleLayout.setVisibility(View.VISIBLE);
                if (searchList.size() > 0) {
                    setTextView.setText(searchList.get(0));
                }
                currentTab = UiEventEntry.TAB_SEARCH;


                currentSel = UiEventEntry.TAB_SEARCH_BASIC;
                if (!SearchFragment.createInstance().isVisible()) {
                    turnToFragmentStack(R.id.detail_layout, SearchFragment.createInstance(), bundle);
                }

                updateRight();
                setTitleRightVisible(View.VISIBLE);

                if (searchList.size() == 1) {
                    iconImageView.setVisibility(View.GONE);
                } else {
                    iconImageView.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rtu_version:
                rtuDetail = rtuVersion.getText().toString().trim();
                setTitleName(rtuDetail);

                selRtu(rtuVersion, R.mipmap.rtu_version_sel);
                nolRtu(rtuSetting, R.mipmap.rtu_setting);
                nolRtu(rtuSearch, R.mipmap.rtu_search);

                titleLayout.setVisibility(View.GONE);

                currentTab = UiEventEntry.TAB_VERSION;
                currentSel = UiEventEntry.TAB_SETTING_VERSION;
                bundle.putBoolean("isBleDevice", true);
                turnToFragmentStack(R.id.detail_layout, RTUVersionFragment.createInstance(), bundle);

                setTitleRightVisible(View.GONE);
                break;

            case R.id.title_back:
                backMain();
                break;
            case R.id.title_right:
                //刷新或重新连接Socket
                if (getString(R.string.re_connect).equals(titleRight.getText().toString().trim())) {
                    if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_BLE) {
                        BleUtils.getInstance().connectDevice(bleDevice);
                    } else {
                        SocketUtil.getSocketUtil().connectRTU(ConfigParams.IP, ConfigParams.PORT);
                    }
                } else {
                    updateData();
                }
                break;
            case R.id.ll_setting:
                iconImageView.setImageResource(R.mipmap.icon_sel);
                showPopupWindow(setLayout);
                break;
            case R.id.sensor_textview:
                sensorLayout.setVisibility(View.GONE);
                setListView.setVisibility(View.VISIBLE);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backMain();
    }

    private void backMain() {
        if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_BLE) {
            BleUtils.getInstance().disConnectDevice();
        } else {
            SocketUtil.getSocketUtil().closeSocketClient();
        }
        this.finish();
    }


    private void updateData() {
        if (currentTab == UiEventEntry.TAB_SETTING) {
            setingData();
        } else if (currentTab == UiEventEntry.TAB_SEARCH) {
            searchData();
        } else if (currentTab == UiEventEntry.TAB_VERSION) {

        }
    }

    private void setingData() {
        switch (currentSel) {
            case UiEventEntry.TAB_CHANNEL_SELECT:
                sendData(ConfigParams.ReadCommPara1);
                break;
            case UiEventEntry.TAB_CHANNEL_GPRS:
            case UiEventEntry.TAB_CHANNEL_GMS:
                sendData(ConfigParams.ReadCommPara2);
                break;
            case UiEventEntry.TAB_CHANNEL_BEI:
                sendData(ConfigParams.ReadCommPara3);
                break;
            case UiEventEntry.TAB_CHANNEL_CENTER:
                sendData(ConfigParams.ReadCommPara4);
                break;
            case UiEventEntry.TAB_CHANNEL_YPT:
                sendData(ConfigParams.READYUN);
                break;
            case UiEventEntry.TAB_SETTING_SYS:
                sendData(ConfigParams.ReadSystemPara);
                break;
            case UiEventEntry.TAB_SETTING_VALVA:
                sendData(ConfigParams.ReadSystemPara6);
                break;
            case UiEventEntry.TAB_SETTING_COLLECT:
                sendData(ConfigParams.ReadSystemPara);
                break;
            case UiEventEntry.TAB_SETTING_COMM:
                sendData(ConfigParams.ReadCommPara1);
                break;
            //温度计
            case UiEventEntry.TAB_SENSOR_TEMP:
                sendData(ConfigParams.ReadTemp_SensorPara);
                break;
            case UiEventEntry.TAB_SETTING_CHANNEL:
                ChannelFragment.createInstance().refreshData();
                break;
            case UiEventEntry.TAB_SENSOR_RAIN:
            case UiEventEntry.TAB_SENSOR_WATER_PARAMS:
                sendData(ConfigParams.ReadSensorPara1);
                break;
            case UiEventEntry.TAB_SENSOR_Water_Quality:
                sendData(ConfigParams.ReadWaterQuality);
                break;
            case UiEventEntry.TAB_SENSOR_Ameeter:
                sendData(ConfigParams.ReadDIANBIAO_SensorPara);
                break;
            case UiEventEntry.TAB_SENSOR_Weather_Param:
                sendData(ConfigParams.ReadWeatherParam);
                break;
            case UiEventEntry.TAB_SENSOR_WATER_PLAN:
            case UiEventEntry.TAB_SENSOR_CAMERA:
            case UiEventEntry.TAB_SENSOR_ATHER:
            case UiEventEntry.TAB_SENSOR_PRESS:
            case UiEventEntry.TAB_SETTING_VERSION:
                sendData(ConfigParams.ReadSensorPara2);
                break;

            case UiEventEntry.TAB_SENSOR_SQ:
                sendData(ConfigParams.ReadMoisture_SensorPara);
                break;
            case UiEventEntry.TAB_SENSOR_AQ:
                sendData(ConfigParams.ReadAna_SensorPara);
                break;
            case UiEventEntry.TAB_SENSOR_FLOW:
                sendData(ConfigParams.ReadSensorPara2);
                break;

            case UiEventEntry.TAB_SETTING_AD:
                if (ADFragment.createInstance().isVisible()) {
                    ADFragment.createInstance().setData();
                }
                break;
            default:
                break;
        }
    }

    private void searchData() {
        switch (currentSel) {
            case UiEventEntry.TAB_SEARCH_BASIC:
                if (SearchFragment.createInstance().isVisible()) {
                    SearchFragment.createInstance().setData();
                }
                break;
            case UiEventEntry.TAB_SEARCH_GPRS:
            case UiEventEntry.TAB_SEARCH_CAMERA:
                if (currentSel == UiEventEntry.TAB_SEARCH_CAMERA) {
                    sendData(ConfigParams.SENDPIC);
                    sendData(ConfigParams.SEND2PIC);

                }
                if (SearchFragment.createInstance().isVisible()) {
                    SearchFragment.createInstance().updateData();
                }
                break;

            case UiEventEntry.TAB_SEARCH_SENSOR:
                if (SearchFragment.createInstance().isVisible()) {
                    SearchFragment.createInstance().setData();
                }
                break;
            case UiEventEntry.TAB_SEARCH_SIOL:
                if (SoilSearchFragment.createInstance().isVisible()) {
                    SoilSearchFragment.createInstance().setData();
                }
                break;
            case UiEventEntry.TAB_SEARCH_WQ:
                if (WQualitySearchFragment.createInstance().isVisible()) {
                    WQualitySearchFragment.createInstance().setData();
                }
                break;
            case UiEventEntry.TAB_SEARCH_Ammeter:
                if (WQualitySearchFragment.createInstance().isVisible()) {
                    WQualitySearchFragment.createInstance().setData();
                }
                break;
            case UiEventEntry.TAB_SEARCH_READ_IMAGE:
                SocketUtil.getSocketUtil().startReceImage();
                sendData(ConfigParams.ReadImage + "000");
                break;
            case UiEventEntry.TAB_SEARCH_RADATA:
                break;
        }
    }


    private void initSearchData() {
        searchList.add(getString(R.string.basic_search));
        searchList.add(getString(R.string.comm_status_search));
        searchList.add(getString(R.string.camera_search));
        searchList.add(getString(R.string.sensor_status_search));
        searchList.add(getString(R.string.read_image));
        searchList.add(getString(R.string.historical_data));
        searchList.add(getString(R.string.Soil_inquiry));
        searchList.add(getString(R.string.Water_quality_inquiry));
        searchList.add(getString(R.string.Electricity_meter_inquiry));

    }

    View contentView = null;

    public void showPopupWindow(View anchor) {

        if (popupWindow == null) {
            contentView = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.windows_popupwindow, null);
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            setListView = contentView.findViewById(R.id.set_listview);
            setToListView = contentView.findViewById(R.id.set_to_listview);
            sensorTextView = contentView.findViewById(R.id.sensor_textview);
            sensorLayout = contentView.findViewById(R.id.sensor_layout);
            sensorTextView.setOnClickListener(this);

            popupWindow.setOnDismissListener(this);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new PaintDrawable());
        }

        if (currentTab == UiEventEntry.TAB_SEARCH) {
            setAdapter = new CustomAdapter(getApplicationContext(), searchList);
            sensorLayout.setVisibility(View.GONE);
            setListView.setVisibility(View.VISIBLE);
        } else {
            setAdapter = new CustomAdapter(getApplicationContext(), setList);
        }
        setListView.setAdapter(setAdapter);

        handRTU(setListView);
        int[] location = calculatePopWindowPos(anchor, contentView);
        showAsDropDown(popupWindow, anchor, 0, 0);

    }

    private void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (ScreenUtil.getScreenHeight(OperationApplication.getInstance()) - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = ScreenUtil.getScreenWidth(OperationApplication.getInstance()) - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = ScreenUtil.getScreenWidth(OperationApplication.getInstance()) - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }


    private void handSensor() {
        sensorTextView.setText(getString(R.string.sensor_setting));
        sensorLayout.setVisibility(View.VISIBLE);
        setListView.setVisibility(View.GONE);
        setToAdapter = new CustomToAdapter(getApplicationContext(),
                setToList);
        setToListView.setAdapter(setToAdapter);
        setToListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                if (position == UiEventEntry.NOTIFY_SENSOR_RAIN) {
                    turnToFragmentStack(R.id.detail_layout, RainPamarsFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_SENSOR_RAIN);
                } else if (position == UiEventEntry.NOTIFY_SENSOR_WATER_PARAMS) {
                    setCurrentSel(UiEventEntry.TAB_SENSOR_WATER_PARAMS);
                    turnToFragmentStack(R.id.detail_layout, WaterPamarsFragment.createInstance());
                } else if (position == UiEventEntry.NOTIFY_SENSOR_WATER_PLAN) {
                    setCurrentSel(UiEventEntry.TAB_SENSOR_WATER_PLAN);
                    turnToFragmentStack(R.id.detail_layout, WaterPlanFragment.createInstance());
                } else if (position == UiEventEntry.NOTIFY_SENSOR_CAMERA) {
                    turnToFragmentStack(R.id.detail_layout, CameraFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_SENSOR_CAMERA);
                } else if (position == UiEventEntry.NOTIFY_SENSOR_SQ) {
                    turnToFragmentStack(R.id.detail_layout, SQFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_SENSOR_SQ);
                } else if (position == UiEventEntry.NOTIFY_SENSOR_FLOW) {
                    turnToFragmentStack(R.id.detail_layout, FlowFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_SENSOR_FLOW);
                } else if (position == UiEventEntry.NOTIFY_SENSOR_ZW) {
                    turnToFragmentStack(R.id.detail_layout, ZWFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_SENSOR_ZW);
                } else if (position == UiEventEntry.NOTIFY_SENSOR_ATHER) {
                    setCurrentSel(UiEventEntry.TAB_SENSOR_ATHER);
                    turnToFragmentStack(R.id.detail_layout, AtherPamarsFragment.createInstance());
                } else if (position == UiEventEntry.NOTIFY_SENSOR_Water_Quality) {
                    setCurrentSel(UiEventEntry.TAB_SENSOR_Water_Quality);
                    turnToFragmentStack(R.id.detail_layout, WaterQualityFragment.createInstance());
                } else if (position == UiEventEntry.NOTIFY_SENSOR_Weather_Param) {
                    setCurrentSel(UiEventEntry.TAB_SENSOR_Weather_Param);
                    turnToFragmentStack(R.id.detail_layout, WeatherParamFragment.createInstance());
                } else if (position == UiEventEntry.NOTIFY_SENSOR_AQ) {
                    turnToFragmentStack(R.id.detail_layout, AnalogQuantityFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_SENSOR_AQ);
                } else if (position == UiEventEntry.NOTIFY_SENSOR_Ammeter) {
                    turnToFragmentStack(R.id.detail_layout, AmmeterFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_SENSOR_Ameeter);
                } else if (position == UiEventEntry.NOTIFY_SENSOR_PRESS) {
                    setCurrentSel(UiEventEntry.TAB_SENSOR_PRESS);
                    turnToFragmentStack(R.id.detail_layout, PressFragment.createInstance());
                }

                popupWindow.dismiss();
                tabName = setToList.get(position);
            }
        });
    }

    private void handChannel() {
        sensorTextView.setText(getString(R.string.channel_setting));
        sensorLayout.setVisibility(View.VISIBLE);
        setListView.setVisibility(View.GONE);
        setToAdapter = new CustomToAdapter(getApplicationContext(),
                setToChannelList);
        setToListView.setAdapter(setToAdapter);
        setToListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                if (position == UiEventEntry.NOTIFY_CHANNEL_SELECT) {
                    turnToFragmentStack(R.id.detail_layout, ChannelSelectFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_CHANNEL_SELECT);
                } else if (position == UiEventEntry.NOTIFY_CHANNEL_GPRS) {
                    setCurrentSel(UiEventEntry.TAB_CHANNEL_GPRS);
                    turnToFragmentStack(R.id.detail_layout, ChannelGPRSFragment.createInstance());
                } else if (position == UiEventEntry.NOTIFY_CHANNEL_GMS) {
                    setCurrentSel(UiEventEntry.TAB_CHANNEL_GMS);
                    turnToFragmentStack(R.id.detail_layout, ChannelGMSFragment.createInstance());
                } else if (position == UiEventEntry.NOTIFY_CHANNEL_BEI) {
                    turnToFragmentStack(R.id.detail_layout, ChannelBEIFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_CHANNEL_BEI);
                } else if (position == UiEventEntry.NOTIFY_CHANNEL_CENTER) {
                    turnToFragmentStack(R.id.detail_layout, ChannelCENTERFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_CHANNEL_CENTER);
                } else if (position == UiEventEntry.NOTIFY_CHANNEL_YPT) {
                    sensorTextView.setText(getString(R.string.channel_setting));
                    sensorLayout.setVisibility(View.VISIBLE);
                    setListView.setVisibility(View.GONE);
                    setToAdapter = new CustomToAdapter(getApplicationContext(),
                            setToChannelList);
                    setToListView.setAdapter(setToAdapter);
                    dialogEditText();
                }
                popupWindow.dismiss();
                tabName = setToChannelList.get(position);
                setTextView.setText(tabName);
            }
        });
    }

    private void handRTU(final ListView setListView) {
        if (setListView != null) {
            setListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    if (currentTab == UiEventEntry.TAB_SEARCH) {
                        tabName = searchList.get(position);

                        if (position == UiEventEntry.NOTIFY_SEARCH_RDDATA) {
                            Bundle bundle1 = new Bundle();
                            setCurrentSel(UiEventEntry.TAB_SEARCH_RADATA);
                            turnToFragmentStack(R.id.detail_layout, SearchDataFragment.createInstance(), bundle1);
                        } else {
                            Bundle bundle = new Bundle();
                            if (SearchFragment.createInstance().isVisible()) {
                                SearchFragment.createInstance().stopUpdate();
                            }

                            if (position == UiEventEntry.NOTIFY_SEARCH_BASIC) {
                                setCurrentSel(UiEventEntry.TAB_SEARCH_BASIC);
                                bundle.putInt(UiEventEntry.CURRENT_SEARCH, UiEventEntry.TAB_SEARCH_BASIC);
                                if (SearchFragment.createInstance().isVisible()) {
                                    SearchFragment.createInstance().setSearch(UiEventEntry.TAB_SEARCH_BASIC);
                                    SearchFragment.createInstance().setData();
                                } else {
                                    turnToFragmentStack(R.id.detail_layout, SearchFragment.createInstance(), bundle);
                                }
                            } else if (position == UiEventEntry.NOTIFY_SEARCH_GPRS) {
                                setCurrentSel(UiEventEntry.TAB_SEARCH_GPRS);
                                bundle.putInt(UiEventEntry.CURRENT_SEARCH, UiEventEntry.TAB_SEARCH_GPRS);
                                if (SearchFragment.createInstance().isVisible()) {
                                    SearchFragment.createInstance().setSearch(UiEventEntry.TAB_SEARCH_GPRS);
                                    SearchFragment.createInstance().setData();
                                } else {
                                    turnToFragmentStack(R.id.detail_layout, SearchFragment.createInstance(), bundle);
                                }
                            } else if (position == UiEventEntry.NOTIFY_SEARCH_CAMERA) {
                                bundle.putInt(UiEventEntry.CURRENT_SEARCH, UiEventEntry.TAB_SEARCH_CAMERA);
                                setCurrentSel(UiEventEntry.TAB_SEARCH_CAMERA);
                                if (SearchFragment.createInstance().isVisible()) {
                                    SearchFragment.createInstance().setSearch(UiEventEntry.TAB_SEARCH_CAMERA);
                                    SearchFragment.createInstance().setData();
                                } else {
                                    turnToFragmentStack(R.id.detail_layout, SearchFragment.createInstance(), bundle);
                                }
                            } else if (position == UiEventEntry.NOTIFY_SEARCH_SENSOR) {
                                bundle.putInt(UiEventEntry.CURRENT_SEARCH, UiEventEntry.TAB_SEARCH_SENSOR);
                                setCurrentSel(UiEventEntry.TAB_SEARCH_SENSOR);
                                if (SearchFragment.createInstance().isVisible()) {
                                    SearchFragment.createInstance().setSearch(UiEventEntry.TAB_SEARCH_SENSOR);
                                    SearchFragment.createInstance().setData();
                                } else {
                                    turnToFragmentStack(R.id.detail_layout, SearchFragment.createInstance(), bundle);
                                }
                            } else if (position == UiEventEntry.NOTIFY_SEARCH_READ_IMAGE) {
                                bundle.putInt(UiEventEntry.CURRENT_SEARCH, UiEventEntry.TAB_SEARCH_READ_IMAGE);
                                setCurrentSel(UiEventEntry.TAB_SEARCH_READ_IMAGE);
                                if (SearchFragment.createInstance().isVisible()) {
                                    SearchFragment.createInstance().setSearch(UiEventEntry.TAB_SEARCH_READ_IMAGE);
                                    SearchFragment.createInstance().setData();
                                } else {
                                    turnToFragmentStack(R.id.detail_layout, SearchFragment.createInstance(), bundle);
                                }
                            } else if (position == UiEventEntry.NOTIFY_SEARCH_BASIC_SOIL) {
                                setCurrentSel(UiEventEntry.TAB_SEARCH_SIOL);
                                turnToFragmentStack(R.id.detail_layout, SoilSearchFragment.createInstance(), bundle);
                            } else if (position == UiEventEntry.NOTIFY_SEARCH_BASIC_WQUALITY) {
                                setCurrentSel(UiEventEntry.TAB_SEARCH_WQ);
                                turnToFragmentStack(R.id.detail_layout, WQualitySearchFragment.createInstance(), bundle);
                            } else if (position == UiEventEntry.NOTIFY_SEARCH_BASIC_AMMETER) {
                                setCurrentSel(UiEventEntry.TAB_SEARCH_Ammeter);
                                turnToFragmentStack(R.id.detail_layout, AmmeterSearchFragment.createInstance(), bundle);
                            }
                            updateRight();
                        }

                        setTextView.setText(tabName);
                        popupWindow.dismiss();
                    } else {
                        tabName = setList.get(position);
                        if (parent.getAdapter() instanceof CustomAdapter) {
                            setTitleRightVisible(View.VISIBLE);
                            if (sensorLayout.getVisibility() == View.GONE && position == 4) {//传感器设置
                                handSensor();
                            } else if ((sensorLayout.getVisibility() == View.GONE && position == 3)) {//信道设置
                                handChannel();
                            } else {
                                Bundle bundle = new Bundle();
                                setTitleRight(getString(R.string.update));
                                if (position == UiEventEntry.NOTIFY_SYSTEM_PAMARS) {
                                    setCurrentSel(UiEventEntry.TAB_SETTING_SYS);
                                    turnToFragmentStack(R.id.detail_layout, SystemPamarsFragment.createInstance(), bundle);
                                } else if (position == UiEventEntry.NOTIFY_COLLECT) {
                                    turnToFragmentStack(R.id.detail_layout, CollectFragment.createInstance(), bundle);
                                    setCurrentSel(UiEventEntry.TAB_SETTING_COLLECT);
                                } else if (position == UiEventEntry.NOTIFY_COMM_PAMARS) {
                                    turnToFragmentStack(R.id.detail_layout, CommParamsFragment.createInstance(), bundle);
                                    setCurrentSel(UiEventEntry.TAB_SETTING_COMM);
                                } else if (position == UiEventEntry.NOTIFY_CHANNEL_PAMARS) {

                                    setCurrentSel(UiEventEntry.TAB_SETTING_CHANNEL);
                                    turnToFragmentStack(R.id.detail_layout, ChannelFragment.createInstance(), bundle);
                                } else if (position == UiEventEntry.NOTIFY_AD_PAMARS) {
                                    setCurrentSel(UiEventEntry.TAB_SETTING_AD);
                                    turnToFragmentStack(R.id.detail_layout, ADFragment.createInstance(), bundle);
                                } else if (position == UiEventEntry.NOTIFY_VR_PAMARS) {
                                    setCurrentSel(UiEventEntry.TAB_SETTING_VALVA);
                                    turnToFragmentStack(R.id.detail_layout, ValveControlRelayFragment.createInstance(), bundle);
                                }
                                popupWindow.dismiss();
                            }
                        }
                        setTextView.setText(tabName);
                    }

                }
            });
        }
    }

    public void turnToFragmentStack(int containerViewId, Fragment toFragmentClass, Bundle args) {
        toFragmentClass.setArguments(args);
        fragmentManager.beginTransaction().replace(containerViewId, toFragmentClass).commitAllowingStateLoss();
    }


    @Override
    public void onDismiss() {
        iconImageView.setImageResource(R.mipmap.icon_default);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_RESULT_OK) {
            String result = "";
            if (args[1] != null) {
                result = (String) args[1];
            }
            if (!TextUtils.isEmpty(result)) {
                if (result.equals(ConfigParams.RESETALL)) {
                    ToastUtil.showToastLong(getString(R.string.device_returned_factory) + getString(R.string.Please_click) + "'" + getString(R.string.Save_settings_and_restart) + "'" + getString(R.string.Button_restart_device));
                } else if (result.equals(ConfigParams.ResetUnit) || result.equals(ConfigParams.RESETALL) || result.equals(ConfigParams.RESETUNIT)) {
                    ToastUtil.showToastLong(getString(R.string.device_is_restarting));
                } else if (result.equals(ConfigParams.RESETALL10)) {
                    ToastUtil.showToastLong(getString(R.string.five_minutes));
                } else {
                    ToastUtil.showToastLong(getString(R.string.Set_successfully));
                }
            }

        } else if (id == UiEventEntry.CONNCT_AGAIN || id == UiEventEntry.CONNCT_FAIL) {
            setTitleRight(getString(R.string.re_connect));
            if (gprsFragment != null && gprsFragment.isVisible()) {
                gprsFragment.stopUpdate();
            }
            if (lSearchFragment != null && lSearchFragment.isVisible()) {
                lSearchFragment.stopUpdate();
            }
        } else if (id == UiEventEntry.CONNCT_OK) {
            updateRight();
        } else if (id == UiEventEntry.READ_RESULT_ERROR) {
            ToastUtil.showToastLong(getString(R.string.Set_error));
        }
    }

    void setTitleRight(String text) {
        titleRight.setText(text);
    }

    void setTitleRightVisible(int visible) {
        titleRight.setVisibility(visible);
    }

    private void updateRight() {
        setTitleRightVisible(View.VISIBLE);
        if (currentSel == UiEventEntry.TAB_SEARCH_GPRS) {
            setTitleRight(getString(R.string.comm_test));

        } else if (currentSel == UiEventEntry.TAB_SEARCH_CAMERA) {
            setTitleRight(getString(R.string.send_img));
        } else if (currentSel == UiEventEntry.TAB_GROUND_WATER_AD) {
            setTitleRight(getString(R.string.collect_ad_lv));
        } else if (currentSel == UiEventEntry.TAB_SEARCH_READ_IMAGE) {
            setTitleRight(getString(R.string.read_image));
        } else if (currentSel == UiEventEntry.TAB_SEARCH_RADATA) {
            setTitleRight(getString(R.string.Receive_historical_data));
            setTitleRightVisible(View.GONE);
        } else {
            boolean isConnect = false;
            if (SettingUtil.getSetMode() == SettingUtil.KEY_SET_MODE_BLE) {
                isConnect = bleDevice != null && bleDevice.isConnected() ? true : false;
            } else {
                isConnect = SocketUtil.getSocketUtil().isConnected();
            }

            if (isConnect) {
                setTitleRight(getString(R.string.update));
            } else {
                setTitleRight(getString(R.string.re_connect));
            }
        }
    }

    private void dialogEditText() {
        final EditText editText = new EditText(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        builder.setTitle(getString(R.string.password1));

        builder.setView(editText);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("9527")) {
                    turnToFragmentStack(R.id.detail_layout, YPTFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_CHANNEL_YPT);
                } else {
                    ToastUtil.showToastLong(getString(R.string.Password_error));
                    turnToFragmentStack(R.id.detail_layout, ChannelSelectFragment.createInstance());
                    setCurrentSel(UiEventEntry.TAB_CHANNEL_SELECT);
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                turnToFragmentStack(R.id.detail_layout, ChannelSelectFragment.createInstance());
                setCurrentSel(UiEventEntry.TAB_CHANNEL_SELECT);
            }
        });
        builder.create().show();
    }


    private void turnToFragmentStack(int containerViewId, Fragment toFragmentClass) {
        turnToFragmentStack(containerViewId, toFragmentClass, null);
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_RESULT_ERROR);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_RESULT_OK);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.CONNCT_AGAIN);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.CONNCT_OK);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.CONNCT_FAIL);


        rtuSetting = findViewById(R.id.rtu_setting);
        rtuSearch = findViewById(R.id.rtu_search);
        rtuVersion = findViewById(R.id.rtu_version);
        backImageView = findViewById(R.id.title_back);
        titleRight = findViewById(R.id.title_right);
        title = findViewById(R.id.title);
        backImageView = findViewById(R.id.title_back);
        rtuVersion = findViewById(R.id.rtu_version);

        titleLayout = findViewById(R.id.ll_layout);
        bottomenuLayout = findViewById(R.id.bottom_menu_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        setLayout = findViewById(R.id.ll_setting);
        setTextView = findViewById(R.id.set_textview);
        iconImageView = findViewById(R.id.icon_default);

        setList = new ArrayList<>();
        setToList = new ArrayList<>();
        setToChannelList = new ArrayList<>();
        searchList = new ArrayList<>();
    }


    private void setTitleName(String text) {
        title.setText(text);
    }

    @Override
    public void initData() {
        //显示toolbar
        setTitleName(rtuSetting.getText().toString().trim());

        rtuSetting.setOnClickListener(this);
        rtuSearch.setOnClickListener(this);
        rtuVersion.setOnClickListener(this);

        if (getIntent() != null) {
            bleDevice = (BleDevice) getIntent().getSerializableExtra(UiEventEntry.NOTIFY_BASIC_DEVICE);
        }

        Bundle bundle = new Bundle();
        currentSel = UiEventEntry.TAB_SETTING_SYS;
        turnToFragmentStack(R.id.detail_layout, SystemPamarsFragment.createInstance(), bundle);
        currentSel = UiEventEntry.TAB_SETTING_SYS;

        setTitleRightVisible(View.VISIBLE);
        setTitleRight(getString(R.string.update));
        initSetData();
        initSetToData();
        initSetChannelData();
        initSearchData();


        if (setList.size() > 0) {
            setTextView.setText(setList.get(0));
        }
        setLayout.setOnClickListener(this);
    }

    @Override
    public void initEvent() {
        backImageView.setOnClickListener(this);
    }
}
