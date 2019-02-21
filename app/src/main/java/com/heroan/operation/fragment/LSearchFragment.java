package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heroan.operation.OperationApplication;
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

public class LSearchFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener
{
    private static final String TAG = LSearchFragment.class.getSimpleName();
    private TextView resultTextView;
    private int search = 143;

    private String signe ;
    private String gmodel ;
    private String connectStatus1 ;
    private String connectStatus2 ;
    private String connectStatus3 ;
    private String connectStatus4 ;
    private String netID ;
    private ScrollView resultScroll;
    private StringBuffer currentSB = new StringBuffer();



    private Runnable timeRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            setData();
            OperationApplication.applicationHandler.postDelayed(timeRunnable, UiEventEntry.TIME);
        }
    };

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_search;
    }

    @Override
    public void initComponentViews(View view)
    {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_IMAGE_SUCCESS);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.NOTIFY_BUNDLE);


        resultTextView = (TextView) view.findViewById(R.id.result_data_textview);
        resultScroll = (ScrollView) view.findViewById(R.id.result_scroll);



        if (getArguments() != null)
        {
            search = getArguments().getInt(UiEventEntry.CURRENT_SEARCH);
        }
        else
        {
            search = UiEventEntry.TAB_SEARCH_LRU_BASIC;
        }
        setData();

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_IMAGE_SUCCESS);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        OperationApplication.applicationHandler.removeCallbacks(timeRunnable);
    }

    @Override
    public void initData()
    {

    }

    public void updateData()
    {
        stopUpdate();
        OperationApplication.applicationHandler.post(timeRunnable);
    }

    public void stopUpdate()
    {
        OperationApplication.applicationHandler.removeCallbacks(timeRunnable);
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
            Bundle bundle = (Bundle) args[0];
            if (bundle != null)
            {
                search = bundle.getInt(UiEventEntry.CURRENT_SEARCH);
            }
            setData();
        }
        else if (id == UiEventEntry.READ_DATA)
        {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result))
            {
                return;
            }
            readData(result);
        }
        else if (id == UiEventEntry.READ_IMAGE_SUCCESS)
        {
            readData("");
        }
    }

    private void readData(String result)
    {
        if (search == UiEventEntry.TAB_SEARCH_LRU_BASIC)
        {//基本查询
            if (result.contains(ConfigParams.RSSI))
            {
                currentSB.insert(currentSB.indexOf(signe) + signe.length(), result.replaceAll(ConfigParams.RSSI, "").trim());
            }
            else if (result.contains(ConfigParams.GModuleStatus))
            {
                String data = result.replaceAll(ConfigParams.GModuleStatus, "").trim();
                if ("0".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.Reset_state));
                }
                else if ("1".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.Testing_signal_strength));
                }
                else if ("2".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.Detecting_SIM_status));
                }
                else if ("3".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.Detecting_GSM_status));
                }
                else if ("4".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.Start_PPP_connection));
                }
                else if ("5".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.Start_PDP));
                }
                else if ("6".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.Activating_PDP));
                }
                else if ("7".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(gmodel) + gmodel.length(), getString(R.string.G_module));
                }

            }
            else if (result.contains(ConfigParams.ConnectStatus1))
            {
                String data = result.replaceAll(ConfigParams.ConnectStatus1, "").trim();
                if ("Finish".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(connectStatus1) + connectStatus1.length(), getString(R.string.connected));
                }
                else
                {
                    String[] connectStatus = data.split(":");
                    if (connectStatus.length > 1)
                    {
                        currentSB.insert(currentSB.indexOf(connectStatus1) + connectStatus1.length(), getString(R.string.Connecting_are) + connectStatus[0] + getString(R.string.port1) + connectStatus[1]);
                    }

                }

            }
            else if (result.contains(ConfigParams.ConnectStatus2))
            {
                String data = result.replaceAll(ConfigParams.ConnectStatus2, "").trim();
                if ("Finish".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(connectStatus2) + connectStatus2.length(), getString(R.string.connected));
                }
                else
                {
                    String[] connectStatus = data.split(":");
                    if (connectStatus.length > 1)
                    {
                        currentSB.insert(currentSB.indexOf(connectStatus2) + connectStatus2.length(), getString(R.string.Connecting_are) + connectStatus[0] + getString(R.string.port1) + connectStatus[1]);
                    }

                }

            }
            else if (result.contains(ConfigParams.ConnectStatus3))
            {
                String data = result.replaceAll(ConfigParams.ConnectStatus3, "").trim();
                if ("Finish".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(connectStatus3) + connectStatus3.length(), getString(R.string.connected));
                }
                else
                {
                    String[] connectStatus = data.split(":");
                    if (connectStatus.length > 1)
                    {
                        currentSB.insert(currentSB.indexOf(connectStatus3) + connectStatus3.length(), getString(R.string.Connecting_are) + connectStatus[0] + getString(R.string.port1) + connectStatus[1]);
                    }

                }

            }
            else if (result.contains(ConfigParams.ConnectStatus4))
            {
                String data = result.replaceAll(ConfigParams.ConnectStatus4, "").trim();
                if ("Finish".equals(data))
                {
                    currentSB.insert(currentSB.indexOf(connectStatus4) + connectStatus4.length(), getString(R.string.connected));
                }
                else
                {
                    String[] connectStatus = data.split(":");
                    if (connectStatus.length > 1)
                    {
                        currentSB.insert(currentSB.indexOf(connectStatus4) + connectStatus4.length(), getString(R.string.Connecting_are) + connectStatus[0] + getString(R.string.port1) + connectStatus[1]);
                    }

                }

            }
            else if (result.contains(ConfigParams.NetID))
            {
                currentSB.insert(currentSB.indexOf(netID) + netID.length(), result.replaceAll(ConfigParams.NetID, "").trim());
            }
        }

        resultTextView.setText(currentSB.toString());

    }



    public void setData()
    {

        signe = getString(R.string.Signal_strength_value);
        gmodel = getString(R.string.G_module_status);
        connectStatus1 = getString(R.string.Channel_1_connection_status);
        connectStatus2 = getString(R.string.Channel_2_connection_status);
        connectStatus3 = getString(R.string.Channel_3_connection_status);
        connectStatus4 = getString(R.string.Channel_4_connection_status);
        netID = getString(R.string.Gateway_ID);

        currentSB.delete(0, currentSB.length());

        resultScroll.setVisibility(View.VISIBLE);
        if (search == UiEventEntry.TAB_SEARCH_LRU_BASIC)
        {
            SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadNetParam);
//            currentSB.append(TotalRainVal);
//            currentSB.append(MM);
//            currentSB.append("\n");
            currentSB.append(signe);
            currentSB.append("\n");
            currentSB.append(gmodel);
            currentSB.append("\n");
            currentSB.append(connectStatus1);
            currentSB.append("\n");
            currentSB.append(connectStatus2);
            currentSB.append("\n");
            currentSB.append(connectStatus3);
            currentSB.append("\n");
            currentSB.append(connectStatus4);
            currentSB.append("\n");
            currentSB.append(netID);
            currentSB.append("\n");


        }

        if (resultTextView != null && currentSB.length() > 0)
        {
            resultTextView.setText(currentSB.toString());
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.update_button:
                updateData();
                break;
            case R.id.stop_button:
                stopUpdate();
                break;

            default:
                break;

        }
    }
}
