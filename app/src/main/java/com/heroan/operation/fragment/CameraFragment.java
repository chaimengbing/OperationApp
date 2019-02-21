package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;
import com.heroan.operation.view.MyRadioGroup;

import zuo.biao.library.util.Log;

/**
 * 摄像头
 * Created by Vcontrol on 2016/11/23.
 */

public class CameraFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate, View.OnClickListener
{
    private Button cameraNumButton;
    private EditText cameraNumEdittext;
    private MyRadioGroup companyGroup;
    private RadioGroup typeGroup;
    private RadioGroup cameraGroup;
    private RadioGroup sendModelGroup;


    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_sensor_camera;
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
        final View viewq = view;
        typeGroup = (RadioGroup) view.findViewById(R.id.type_group);
        companyGroup = (MyRadioGroup) view.findViewById(R.id.company_group);
        cameraGroup = (RadioGroup) view.findViewById(R.id.camera_group);
        sendModelGroup = (RadioGroup) view.findViewById(R.id.send_model_group);

        cameraNumButton = (Button) view.findViewById(R.id.camera_num_button);
        cameraNumEdittext = (EditText) view.findViewById(R.id.camera_num_edittext);

        companyGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId)
            {
                View checkView = viewq.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetCameraManuf;
                if (checkedId == R.id.company_button)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "01");
                }
                else if (checkedId == R.id.company_button2)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "02");
                }
                else if (checkedId == R.id.company_button3)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "03");
                }
                else if (checkedId == R.id.company_button4)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "04");
                }
                else if (checkedId == R.id.company_button5)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "05");
                }
                else if (checkedId == R.id.company_button6)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "06");
                }
            }
        });

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = viewq.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetCameraType;
                if (checkedId == R.id.type_button)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
                else if (checkedId == R.id.type_button2)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "2");
                }
                else if (checkedId == R.id.type_button3)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "3");
                }
            }
        });

        sendModelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = viewq.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetPicSendMode;
                if (checkedId == R.id.Single_packet_reply_button)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
                else if (checkedId == R.id.Multi_packet_reply_button)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "2");
                }
            }
        });
        cameraGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View checkView = viewq.findViewById(checkedId);
                if (!checkView.isPressed())
                {
                    return;
                }
                String content = ConfigParams.SetPIC_Resolution;
                if (checkedId == R.id.camera_button)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "0");
                }
                else if (checkedId == R.id.camera_button2)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "1");
                }
                else if (checkedId == R.id.camera_button3)
                {
                    SocketUtil.getSocketUtil().sendContent(content + "2");
                }
            }
        });


    }

    @Override
    public void initData()
    {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadSensorPara2);
    }

    @Override
    public void setListener()
    {
        cameraNumButton.setOnClickListener(this);
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
        if (result.contains(ConfigParams.SetCameraType))
        {
            data = result.replaceAll(ConfigParams.SetCameraType, "").trim();
            if ("1".equals(data))
            {
                typeGroup.check(R.id.type_button);
            }
            else if ("2".equals(data))
            {
                typeGroup.check(R.id.type_button2);
            }
            else if ("3".equals(data))
            {
                typeGroup.check(R.id.type_button3);
            }
        }
        else if (result.contains(ConfigParams.SetPIC_Resolution))
        {
            data = result.replaceAll(ConfigParams.SetPIC_Resolution, "").trim();
            if ("0".equals(data))
            {
                cameraGroup.check(R.id.camera_button);
            }
            else if ("1".equals(data))
            {
                cameraGroup.check(R.id.camera_button2);
            }
            else if ("2".equals(data))
            {
                cameraGroup.check(R.id.camera_button3);
            }
        }
        else if (result.contains(ConfigParams.SetCameraManuf))
        {
            data = result.replaceAll(ConfigParams.SetCameraManuf, "").trim();
            if ("1".equals(data))
            {
                companyGroup.check(R.id.company_button);
            }
            else if ("2".equals(data))
            {
                companyGroup.check(R.id.company_button2);
            }
            else if ("3".equals(data))
            {
                companyGroup.check(R.id.company_button3);
            }
            else if ("4".equals(data))
            {
                companyGroup.check(R.id.company_button4);
            }
            else if ("5".equals(data))
            {
                companyGroup.check(R.id.company_button5);
            }
            else if ("6".equals(data))
            {
                companyGroup.check(R.id.company_button6);
            }
        }
        else if (result.contains(ConfigParams.SetPicSendMode))
        {
            data = result.replaceAll(ConfigParams.SetPicSendMode, "").trim();
            if ("1".equals(data))
            {
                sendModelGroup.check(R.id.Single_packet_reply_button);
            }
            else
            {
                sendModelGroup.check(R.id.Multi_packet_reply_button);
            }

        }
        else if (result.contains(ConfigParams.SetCamNum))
        {
            data = result.replaceAll(ConfigParams.SetCamNum, "").trim();
            cameraNumEdittext.setText(data);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.camera_num_button:
                String water = cameraNumEdittext.getText().toString();
                if (TextUtils.isEmpty(water))
                {
                    ToastUtil.showToastLong(getString(R.string.cameras_number_empty));
                    return;
                }

                int planNum = Integer.parseInt(water);
                if (planNum < 0 || planNum > 9)
                {
                    ToastUtil.showToastLong(getString(R.string.cameras_enter));
                    return;
                }
                String content = ConfigParams.SetCamNum + planNum;
                SocketUtil.getSocketUtil().sendContent(content);


                break;

            default:

                break;
        }
    }
}
