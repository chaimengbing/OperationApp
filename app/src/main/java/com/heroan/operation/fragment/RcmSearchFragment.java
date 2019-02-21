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

public class RcmSearchFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate
{
    private static final String TAG = RcmSearchFragment.class.getSimpleName();


    private byte[] head = new byte[8];


    private TextView resultTextView;

    private String channel1 ;
    private String ftpAddr ;
    private String port1 ;

    private String takePhotoInterval ;
    private String videoTF ;
    private String simConfig ;
    private String netWorkStatus ;
    private String TFStatus ;



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

        if (result.contains(ConfigParams.SetIPChannel))
        {
            String[] ipArray = result.split(ConfigParams.Port.trim());
            String ip = "";
            String port = "";
            if (result.contains(ConfigParams.SetIPChannel))
            {
                if (ipArray != null)
                {
                    ip = ipArray[0].replaceAll(ConfigParams.SetIPChannel, "").trim();
                    if (ipArray.length > 1)
                    {
                        port = ipArray[1].trim();
                    }
                }
                currentSB.insert(currentSB.indexOf(channel1) + channel1.length(), ip);
                currentSB.insert(currentSB.indexOf(port1) + port1.length(), port);
            }

        }
        else if (result.contains(ConfigParams.SetFTPAddr))
        {
            String[] ipArray = result.split(ConfigParams.Port.trim());
            String ip = "";
            String port = "";
            if (ipArray != null)
            {
                ip = ipArray[0].replaceAll(ConfigParams.SetFTPAddr, "").trim();
                if (ipArray.length > 1)
                {
                    port = ipArray[1].trim();
                }
            }
            currentSB.insert(currentSB.indexOf(ftpAddr) + ftpAddr.length(), ip);
            currentSB.insert(currentSB.indexOf(port1, currentSB.indexOf(ftpAddr)) + port1.length(), port);
        }
        else if (result.contains(ConfigParams.NetWorkCardStatus))
        {
            data = result.replaceAll(ConfigParams.NetWorkCardStatus, "").trim();
            currentSB.insert(currentSB.indexOf(netWorkStatus) + netWorkStatus.length(),data.trim().equals("1") ? getString(R.string.Not_inserted) : getString(R.string.normal));
        }
        else if (result.contains(ConfigParams.TFCardStatus))
        {
            data = result.replaceAll(ConfigParams.TFCardStatus, "").trim();
            currentSB.insert(currentSB.indexOf(TFStatus) + TFStatus.length(),data.trim().equals("1") ? getString(R.string.Not_inserted) : getString(R.string.normal));
        }
        else if (result.contains(ConfigParams.SetSIMConfig))
        {
            data = result.replaceAll(ConfigParams.SetSIMConfig, "").trim();
            currentSB.insert(currentSB.indexOf(simConfig) + simConfig.length(),data.trim().equals("1") ? getString(R.string.China_Mobile) : getString(R.string.China_Telecom));
        }
        else if (result.contains(ConfigParams.SetTakePhotoInterval))
        {
            data = result.replaceAll(ConfigParams.SetTakePhotoInterval, "").trim();
            int time = Integer.parseInt(data.trim());
            currentSB.insert(currentSB.indexOf(takePhotoInterval) + takePhotoInterval.length(), ServiceUtils.getFunTimePos(time));
        }
        else if (result.contains(ConfigParams.SetVideoSave))
        {
            data = result.replaceAll(ConfigParams.SetVideoSave, "").trim();
            int i = Integer.parseInt(data);
            currentSB.insert(currentSB.indexOf(videoTF) + videoTF.length(),ServiceUtils.getFunTFPos(i));
        }


        resultTextView.setText(currentSB.toString());
    }


    public void setData()
    {

        channel1 = getString(R.string.TCP_address)+"\n"+getString(R.string.ip_);
        ftpAddr = getString(R.string.FTP_address)+"\n"+getString(R.string.ip_);
        port1 = getString(R.string.port);

        takePhotoInterval = getString(R.string.Photo_interval) + ":";
        videoTF = getString(R.string.Video_recording_time) + ":";
        simConfig= getString(R.string.Card_configuration);
        netWorkStatus = getString(R.string.NIC_Status);
        TFStatus= getString(R.string.TF_Card_Status);

        String content = ConfigParams.ReadData;
        SocketUtil.getSocketUtil().sendContent(content);
        currentSB.delete(0, currentSB.length());
        resultScroll.setVisibility(View.VISIBLE);
        currentSB.append(netWorkStatus);
        currentSB.append("\n");
        currentSB.append(TFStatus);
        currentSB.append("\n");
        currentSB.append(simConfig);
        currentSB.append("\n");
//        currentSB.append(takePhotoInterval);
//        currentSB.append("\n");
        currentSB.append(videoTF);
        currentSB.append("\n");
        currentSB.append(channel1);
        currentSB.append("  ");
        currentSB.append(port1);
        currentSB.append("\n");
        currentSB.append(ftpAddr);
        currentSB.append("  ");
        currentSB.append(port1);
        currentSB.append("\n");

        if (resultTextView != null && currentSB.length() > 0)
        {
            resultTextView.setText(currentSB.toString());
        }
    }
}
