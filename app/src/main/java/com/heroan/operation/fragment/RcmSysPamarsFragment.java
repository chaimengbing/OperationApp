package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;
import com.heroan.operation.adapter.SimpleSpinnerAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class RcmSysPamarsFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{

    private static final String TAG = RcmSysPamarsFragment.class.getSimpleName();

    private EditText ipChannel;
    private EditText portChannel;
    private EditText ftpAddrChannel;
    private EditText ftpPortChannel;

    private Button channelButton;
    private Button ftpAddrButton;


    private Spinner apnSpinner;
    private SimpleSpinnerAdapter apnAdapter;
    private String[] apnItems;
    private Spinner tfSpinner;
    private Spinner photoSpinner;
    private String[] timeItems;
    private SimpleSpinnerAdapter timeAdapter;
    private Spinner ratioSpinner;
    private String[] ratioItems;
    private SimpleSpinnerAdapter ratioAdapter;
    private Spinner framesSpinner;
    private String[] framesItems;
    private SimpleSpinnerAdapter framesAdapter;
    private String[] tfItems;
    private SimpleSpinnerAdapter tfAdapter;

    private EditText photoInterval;

    private Button photoIntervalButton;
    private Button startupButton;
    private Button shutDownButton;
    private Button clearSDButton;
    private Button takePhotoButton;
    private Button ftpImageButton;
    private Button Reboot_device_two;
    private Button reset_button_two;
    private Button Restart_system_board;

    private RadioGroup simConfigRadioGroup;

    private Button takePhoto;


    private boolean isFirst = true;
    private boolean isrFirst = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_rcm_system);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }



    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        ipChannel = (EditText) view.findViewById(R.id.ip_channel_edittext);
        portChannel = (EditText) view.findViewById(R.id.port_channel_edittext);
        ftpAddrChannel = (EditText) view.findViewById(R.id.ftp_addr_edittext);
        ftpPortChannel = (EditText) view.findViewById(R.id.ftp_port_edittext);
        photoInterval = (EditText) view.findViewById(R.id.photo_interval_edittext);

        channelButton = (Button) view.findViewById(R.id.ip_channel_button);
        ftpAddrButton = (Button) view.findViewById(R.id.ftp_addr_button);

        photoIntervalButton = (Button) view.findViewById(R.id.photo_interval_button);
        shutDownButton = (Button) view.findViewById(R.id.shut_down);
        startupButton = (Button) view.findViewById(R.id.start_up);
        takePhotoButton = (Button) view.findViewById(R.id.take_photo);
        clearSDButton = (Button) view.findViewById(R.id.clear_sd_card);
        ftpImageButton = (Button) view.findViewById(R.id.ftp_imagelist_button);
        reset_button_two = (Button) view.findViewById(R.id.reset_button_two);
        Reboot_device_two = (Button) view.findViewById(R.id.Reboot_device_two);
        Restart_system_board = (Button) view.findViewById(R.id.Restart_system_board);

        simConfigRadioGroup = (RadioGroup) view.findViewById(R.id.main_picture);
        tfSpinner = (Spinner) view.findViewById(R.id.tf_spinner);
        photoSpinner = (Spinner) view.findViewById(R.id.photo_spinner);
        ratioSpinner = (Spinner) view.findViewById(R.id.ratio_spinner);//摄像头分辨率
        framesSpinner = (Spinner) view.findViewById(R.id.frames_spinner);
        apnSpinner = (Spinner) view.findViewById(R.id.APN_spinner);

        takePhoto = (Button) view.findViewById(R.id.take_photo1);

        takePhoto.setOnClickListener(this);


        isFirst = true;
        isrFirst = true;
        initView(view);
    }

    @Override
    public void initData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadData);

        timeItems = getResources().getStringArray(R.array.pictime_interval);
        timeAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, timeItems);
        photoSpinner.setAdapter(timeAdapter);

        apnItems = getResources().getStringArray(R.array.apn);
        apnAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, apnItems);
        apnSpinner.setAdapter(apnAdapter);

        ratioItems = getResources().getStringArray(R.array.ratio_array);
        ratioAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, ratioItems);
        ratioSpinner.setAdapter(ratioAdapter);

        tfItems = getResources().getStringArray(R.array.tf_time2);
        tfAdapter = new SimpleSpinnerAdapter(getActivity(), R.layout.simple_spinner_item, tfItems);
        tfSpinner.setAdapter(tfAdapter);

        framesItems = getResources().getStringArray(R.array.frames_interval);
        framesAdapter = new SimpleSpinnerAdapter(getActivity(),R.layout.simple_spinner_item,framesItems);
        framesSpinner.setAdapter(framesAdapter);


    }

    @Override
    public void initEvent() {
        channelButton.setOnClickListener(this);
        ftpAddrButton.setOnClickListener(this);
        photoIntervalButton.setOnClickListener(this);
        shutDownButton.setOnClickListener(this);
        takePhotoButton.setOnClickListener(this);
        startupButton.setOnClickListener(this);
        clearSDButton.setOnClickListener(this);
        ftpImageButton.setOnClickListener(this);
        Reboot_device_two.setOnClickListener(this);
        reset_button_two.setOnClickListener(this);
        Restart_system_board.setOnClickListener(this);


        photoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (isFirst)
                {
                    isFirst = false;
                    return;
                }
                timeAdapter.setSelectedItem(position);
                String content = ConfigParams.SetTakePhotoInterval + position;
                SocketUtil.getSocketUtil().sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        apnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (isFirst)
                {
                    isFirst = false;
                    return;
                }
                apnAdapter.setSelectedItem(position);
                String content = ConfigParams.SetCameraAPN + position;
                SocketUtil.getSocketUtil().sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ratioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (isrFirst)
                {
                    isrFirst = false;
                    return;
                }
                Log.i(TAG, "wwwww");
                ratioAdapter.setSelectedItem(position);
                String content = ConfigParams.SetPIC_Resolution  + position;
                SocketUtil.getSocketUtil().sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        framesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (isrFirst)
                {
                    isrFirst = false;
                    return;
                }
                framesAdapter.setSelectedItem(position);
                String content = ConfigParams.SetFramesInterval + position;
                SocketUtil.getSocketUtil().sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        tfSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                tfAdapter.setSelectedItem(position);
                String content = ConfigParams.SetVideoSave + position;
                SocketUtil.getSocketUtil().sendContent(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void initView(final View view)
    {
        simConfigRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                View checkView = view.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = "";
                if (checkedId == R.id.main_picture_radiobtton)
                {
                    content = ConfigParams.YunFunction + "0";
                }
                else
                {
                    content = ConfigParams.YunFunction + "1";
                }
                SocketUtil.getSocketUtil().sendContent(content);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        int item = photoSpinner.getSelectedItemPosition();
        photoSpinner.setSelection(item);

        int item1 = ratioSpinner.getSelectedItemPosition();
        ratioSpinner.setSelection(item1);

        int item2 = framesSpinner.getSelectedItemPosition();
        framesSpinner.setSelection(item2);

        int item3 = apnSpinner.getSelectedItemPosition();
        apnSpinner.setSelection(item3);

    }



    @Override
    public void onClick(View view)
    {
        String data = "";
        String port = "";
        String content = "";
        switch (view.getId())
        {
            case R.id.take_photo1:
                content = ConfigParams.CameraTakePicture;
                break;
            case R.id.ip_channel_button:
                data = ipChannel.getText().toString().trim();
                port = portChannel.getText().toString().trim();
                if (TextUtils.isEmpty(data) || TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.IP_or_port_empty));
                    return;
                }
                content = ConfigParams.SetIPChannel  + data + " " + ConfigParams.Port + port;
                break;

            case R.id.ftp_addr_button:
                data = ftpAddrChannel.getText().toString().trim();
                port = ftpPortChannel.getText().toString().trim();
                if (TextUtils.isEmpty(data) || TextUtils.isEmpty(port))
                {
                    ToastUtil.showToastLong(getString(R.string.Address_or_port_empty));
                    return;
                }
                content = ConfigParams.SetFTPAddr + data + " " + ConfigParams.Port + port;

                break;
            case R.id.photo_interval_button:

                data = photoInterval.getText().toString().trim();
                if (TextUtils.isEmpty(data))
                {
                    ToastUtil.showToastLong(getString(R.string.Camera_interval_empty));
                    return;
                }

                content = ConfigParams.SetTakePhotoInterval + data;
                break;

            case R.id.shut_down:
                content = ConfigParams.ShutDown;
                break;
            case R.id.start_up:
                content = "";
                startupStatus();
                break;
            case R.id.take_photo:
                content = ConfigParams.SetTakePhoto;

                break;
            case R.id.ftp_imagelist_button:

//                Intent intent = new Intent(getActivity(), FTPImageActivity.class);
//                getActivity().startActivity(intent);


                break;
            case R.id.clear_sd_card:
                content = ConfigParams.SetClearSDCard;
                break;
            case R.id.Reboot_device_two:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.RESETALL10);
                break;
            case R.id.reset_button_two:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.RESETALL);
                break;
            case R.id.Restart_system_board:
                SocketUtil.getSocketUtil().sendContent(ConfigParams.SetRestartSystemBoard);
            default:
                content = "";
                break;
        }

        SocketUtil.getSocketUtil().sendContent(content);
    }

    private Runnable timeRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            sendData();
            OperationApplication.applicationHandler.postDelayed(timeRunnable, UiEventEntry.TIME);
        }
    };

    private void sendData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadStatus);
    }

    private void startupStatus()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.StartUp);
        OperationApplication.applicationHandler.postDelayed(timeRunnable, UiEventEntry.TIME);
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
            setData(result);

        }
    }

    private void setData(String result)
    {
        String data;
        String ip;
        if (result.contains(ConfigParams.SetFTPAddr.trim()))
        {//
            String[] portArray = result.split(ConfigParams.Port.trim());
            ftpAddrChannel.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetFTPAddr, "").trim()));
            if (portArray.length > 1)
            {
                ftpPortChannel.setText(portArray[1].trim());
            }
        }
        else if (result.contains(ConfigParams.SetIPChannel.trim()))
        {
            String[] portArray = result.split(ConfigParams.Port.trim());
            ipChannel.setText(ServiceUtils.getRemoteIp(portArray[0].replaceAll(ConfigParams.SetIPChannel, "").trim()));
            if (portArray.length > 1)
            {
                portChannel.setText(portArray[1].trim());
            }

        }
//        else if (result.contains(ConfigParams.SetTakePhotoInterval.trim()))
//        {// 拍照时间间隔
//            data = result.replaceAll(ConfigParams.SetTakePhotoInterval, "");
//            photoInterval.setText(data.trim());
//        }
        else if (result.contains(ConfigParams.YunFunction.trim()))
        {// 卡配置
            data = result.replaceAll(ConfigParams.YunFunction, "").trim();
            if ("0".equals(data))
            {
                simConfigRadioGroup.check(R.id.main_picture_radiobtton);
            }
            else if ("1".equals(data))
            {
                simConfigRadioGroup.check(R.id.main_video_radiobtton);
            }
        }
        else if (result.contains(ConfigParams.SetVideoSave.trim()))
        {// 录像时间间隔
            data = result.replaceAll(ConfigParams.SetVideoSave, "");
            int i = Integer.parseInt(data);
            if (i < tfItems.length && i > 0)
            {
                isFirst = true;
                tfSpinner.setSelection(i);
            }

        }
        else if (result.contains(ConfigParams.SetTakePhotoInterval.trim()))
        {// 拍照时间间隔
            data = result.replaceAll(ConfigParams.SetTakePhotoInterval, "");
            int i = Integer.parseInt(data);
            if (i < timeItems.length && i > 0)
            {
                isFirst = true;
                photoSpinner.setSelection(i);
            }

        }
        else if (result.contains(ConfigParams.SetCameraAPN.trim()))
        {// 拍照时间间隔
            data = result.replaceAll(ConfigParams.SetCameraAPN, "");
            int i = Integer.parseInt(data);
            if (i < apnItems.length && i > 0)
            {
                isFirst = true;
                apnSpinner.setSelection(i);
            }

        }
        else if (result.contains(ConfigParams.SetPIC_Resolution.trim()))
        {// 拍照时间间隔
            data = result.replaceAll(ConfigParams.SetPIC_Resolution, "");
            int i = Integer.parseInt(data);
            if (i < ratioItems.length && i > 0)
            {
                isrFirst = true;
                ratioSpinner.setSelection(i);
            }
            else
            {
                isrFirst = true;
                ratioSpinner.setSelection(ratioItems.length - 1);

            }

        }
        else if (result.contains("System Up"))
        {// 开机完成
            OperationApplication.applicationHandler.removeCallbacks(timeRunnable);
        }
        else if (result.contains("System Down"))
        {// 正在开机

        }

    }


}
