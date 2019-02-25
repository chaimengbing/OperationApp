package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.UpdateBinAdapter;
import com.heroan.operation.utils.BleUtils;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import cn.com.heaton.blelibrary.ble.BleDevice;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class RTUVersionFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private static final String TAG = RTUVersionFragment.class.getSimpleName();
    private TextView appVers;
    private TextView rtuVers;
    private Button updateButton;
    private Button updateButton1;

    private UpdateBinAdapter adapter;
    private UpdateBinAdapter adapter1;

    private ListView updatelistView;
    private ListView updatelistView1;
    private int currentType;
    private int currentType1;


    private TextView downloadTextView;
    private TextView sumTextView;
    private FileInputStream binIs;
    private FileInputStream binIs1;

    private TextView downloadTextView1;
    private TextView sumTextView1;
    /**
     * 文件当前包数
     */
    private int currentP = 1;
    private int currentP1 = 1;
    /**
     * 文件总包数
     */
    private int fileSum;
    private long fileLength;
    //bin文件总包数，十六进制，从低到高
    private String packageSum;
    //bin文件当前包数,首先发送第一包，十六进制，从低到高
    private String packageCurrent;
    private File fileBin;
    private int fileSum1;
    private long fileLength1;
    //bin文件总包数，十六进制，从低到高
    private String packageSum1;
    //bin文件当前包数,首先发送第一包，十六进制，从低到高
    private String packageCurrent1;
    private File fileBin1;
    private RelativeLayout packageLayout;
    private RelativeLayout packageLayout1;
    private TextView listTextView;
    private TextView listTextView1;
    private TextView vcm;

    private boolean isBleDevice = false;
    private BleDevice bleDevice = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_setting_version);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.DOWNLOADING);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.SetAllConfigInfo);
    }

    private String getAppVersion() {
        String versionName = null;

        try {
            versionName = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(), 0).versionName;
        } catch (Exception e) {

        }
        return versionName;
    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.DOWNLOADING);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.SetAllConfigInfo);

        if (getArguments() != null) {
            isBleDevice = getArguments().getBoolean("isBleDevice");
            bleDevice = (BleDevice) getArguments().getSerializable("device");
        }

        appVers = (TextView) view.findViewById(R.id.app_ver);
        rtuVers = (TextView) view.findViewById(R.id.rtu_ver);
        vcm = (TextView) view.findViewById(R.id.vcm);
        downloadTextView = (TextView) view.findViewById(R.id.download_textview);
        downloadTextView1 = (TextView) view.findViewById(R.id.download_textview1);
        sumTextView = (TextView) view.findViewById(R.id.sum_data_textview);
        sumTextView1 = (TextView) view.findViewById(R.id.sum_data_textview1);
        listTextView = (TextView) view.findViewById(R.id.list_title_textview);
        listTextView1 = (TextView) view.findViewById(R.id.touch_import_textview);
        updateButton = (Button) view.findViewById(R.id.update_rtu_button);
        updateButton1 = (Button) view.findViewById(R.id.touch_import_button);
        updatelistView = (ListView) view.findViewById(R.id.update_listview);
        updatelistView1 = (ListView) view.findViewById(R.id.touch_import_listview);
        packageLayout = (RelativeLayout) view.findViewById(R.id.package_layout);
        packageLayout1 = (RelativeLayout) view.findViewById(R.id.package_layout1);

        appVers.setText(getString(R.string.APP_Version) + getAppVersion());
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            currentType = getArguments().getInt(UiEventEntry.CURRENT_RTU_NAME);
            if (currentType == UiEventEntry.WRU_2800 || currentType == UiEventEntry.WRU_2801 || currentType == UiEventEntry.WRU_2100) {
                ServiceUtils.sendData(ConfigParams.ReadSensorPara2);
            } else if (currentType == UiEventEntry.WRU_1901) {
                ServiceUtils.sendData(ConfigParams.ReadData);
            } else if (currentType == UiEventEntry.RTU_2800 || currentType == UiEventEntry.WRU_2801) {
                ServiceUtils.sendData(ConfigParams.ReadParameter);
            } else if (currentType == UiEventEntry.LRU_3000) {
                ServiceUtils.sendData(ConfigParams.ReadParameters);
                vcm.setText(getString(R.string.Software_version));
            } else if (currentType == UiEventEntry.LRU_3200) {
                ServiceUtils.sendData(ConfigParams.ReadNetVer);
            } else if (currentType == UiEventEntry.RCM_2000) {
                ServiceUtils.sendData(ConfigParams.ReadFunctionData);
                vcm.setText(getString(R.string.Camera_version));
            } else if (currentType == UiEventEntry.LRU_BLE_3300) {
                vcm.setText(getString(R.string.Software_version));
                BleUtils.getInstance().sendData(ConfigParams.ReadVer.getBytes());
            }
        } else {
            ServiceUtils.sendData(ConfigParams.ReadSensorPara2);
        }

        if (currentType == UiEventEntry.WRU_2800 || currentType == UiEventEntry.WRU_2801 || currentType == UiEventEntry.WRU_2100) {
            updatelistView.setVisibility(View.VISIBLE);
            packageLayout.setVisibility(View.VISIBLE);
            listTextView.setVisibility(View.VISIBLE);

            updatelistView1.setVisibility(View.VISIBLE);
            packageLayout1.setVisibility(View.VISIBLE);
            listTextView1.setVisibility(View.VISIBLE);
        } else {
            updatelistView.setVisibility(View.GONE);
            packageLayout.setVisibility(View.GONE);
            listTextView.setVisibility(View.GONE);

            updatelistView1.setVisibility(View.GONE);
            packageLayout1.setVisibility(View.GONE);
            listTextView1.setVisibility(View.GONE);
        }


        adapter = new UpdateBinAdapter(getActivity());
        updatelistView.setAdapter(adapter);

        adapter1 = new UpdateBinAdapter(getActivity());
        updatelistView1.setAdapter(adapter1);
    }

    @Override
    public void initEvent() {
        updateButton.setOnClickListener(this);
        updateButton1.setOnClickListener(this);

        updatelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = (File) adapter.getItem(position);
                Log.i(TAG, "file:" + file.getName());

                fileBin = new File(file.getAbsolutePath());

                //文件总大小
                fileLength = fileBin.length();
                Log.i(TAG, "length:" + fileLength);
                //bin文件总包数
                fileSum = ((int) fileLength / 1024) + 1;
                sumTextView.setText(getString(R.string.Total_number_of_packages) + fileSum);
                currentP = 1;
                sendBinData();
            }
        });

        updatelistView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = (File) adapter1.getItem(position);
                Log.i(TAG, "file:" + file.getName());

                fileBin1 = new File(file.getAbsolutePath());

                //文件总大小
                fileLength1 = fileBin1.length();
                Log.i(TAG, "length:" + fileLength1);
                //bin文件总包数
                fileSum1 = ((int) fileLength1 / 1024);
                sumTextView1.setText(getString(R.string.Total_number_of_packages) + fileSum1);
                currentP1 = 1;
                sendBinData1();
            }
        });

    }

    private byte[] dataSum = null;
    private byte[] dataSum1 = null;

    private void sendBinData() {
        //需要进行 crc 校验
        byte[] buffer = new byte[1036];
        initBuffer(buffer);

        //bin文件总包数
        packageSum = ServiceUtils.getIntLowHex(fileSum);
        //bin文件当前包数,首先发送第一包
        packageCurrent = ServiceUtils.getIntLowHex(currentP);


        String p = "0F00";
        String p1 = "0100";

        byte[] b = ServiceUtils.decodeToBytes(packageSum);
        buffer[6] = b[0];
        buffer[7] = b[1];

        byte[] b1 = ServiceUtils.decodeToBytes(packageCurrent);
        buffer[8] = b1[0];
        buffer[9] = b1[1];

        try {
            if (binIs == null) {
                dataSum = new byte[(int) fileLength];
                binIs = new FileInputStream(fileBin);
                binIs.read(dataSum);

            }
//            Log.info(TAG,Arrays.toString(dataSum));
            byte[] data = new byte[1024];
            int temtLength = 1024 * (currentP - 1);
            if (currentP == fileSum) {//发送最后一包数据
                int length = (int) (fileLength - temtLength);
//                Log.info(TAG, "length:" + length);
                System.arraycopy(dataSum, temtLength, data, 0, length);

                //最后一包数据不到1024的补"0A"
                for (int i = length; i < data.length; i++) {
                    data[i] = 0x0A;
                }

            } else {
                System.arraycopy(dataSum, temtLength, data, 0, data.length);
            }

            for (int i = 0; i < 1024; i++) {
                buffer[i + 12] = data[i];
            }
            //crc 校验值
            String crcValue = ServiceUtils.getIntLowHex(ServiceUtils.calcCrc16(buffer));
//            Log.info(TAG, "crcValue:" + Arrays.toString(buffer));
            String content =
                    ServiceUtils.toHexString(ConfigParams.DOWNLOAD) + ServiceUtils.byteArrayToHexStr(buffer) + crcValue + ConfigParams.DOWNLOADEND;
            Log.i(TAG, "content:" + content);
            byte[] sendData = ServiceUtils.decodeToBytes(content);
//            Log.info(TAG, "sendData:" + Arrays.toString(sendData));
            SocketUtil.getSocketUtil().sendContent(sendData, true);
            downloadTextView.setText(getString(R.string.Sending_the_first) + currentP + getString(R.string.Packet_data));//("正在发送第" + currentP + "包数据");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void sendBinData1() {
        //需要进行 crc 校验
        byte[] buffer = new byte[1024];


        //bin文件总包数
        packageSum1 = ServiceUtils.getIntLowHex(fileSum1);
        //bin文件当前包数,首先发送第一包
        packageCurrent1 = ServiceUtils.getIntLowHex(currentP1);


        String p = "0F00";
        String p1 = "0100";

//        byte[] b = ServiceUtils.decodeToBytes(packageSum1);
//        buffer[2] = b[0];
//        buffer[3] = b[1];
//
//        byte[] b1 = ServiceUtils.decodeToBytes(packageCurrent1);
//        buffer[4] = b1[0];
//        buffer[5] = b1[1];


        try {
            if (binIs1 == null) {
                dataSum1 = new byte[(int) fileLength1];
                binIs1 = new FileInputStream(fileBin1);
                binIs1.read(dataSum1);

            }
//            Log.info(TAG,Arrays.toString(dataSum));
            byte[] data = new byte[1024];
            int temtLength = 1024 * (currentP1 - 1);
            if (currentP1 == fileSum1) {//发送最后一包数据
                int length = (int) (fileLength1 - temtLength);
//                Log.info(TAG, "length:" + length);
                System.arraycopy(dataSum1, temtLength, data, 0, length);

                //最后一包数据不到1024的补"0A"
                for (int i = length; i < data.length; i++) {
                    data[i] = 0x0A;
                }

            } else {
                System.arraycopy(dataSum1, temtLength, data, 0, data.length);
            }

            for (int i = 0; i < 1024; i++) {
                buffer[i] = data[i];
            }
            //crc 校验值

            String string = Integer.toString(currentP1) + " ";
            String end = "\r\n";


            String crcValue = ServiceUtils.getIntLowHex(ServiceUtils.calcCrc16(buffer));
//            Log.info(TAG, "crcValue:" + Arrays.toString(buffer));
            String content =
                    ServiceUtils.toHexString(ConfigParams.SetAllConfigInfo) + ServiceUtils.toHexString(string) + ServiceUtils.byteArrayToHexStr(buffer) + crcValue;
            Log.i(TAG, "content:" + content);
            byte[] sendData = ServiceUtils.decodeToBytes(content);
            byte[] bytes = new byte[sendData.length + 2];

            for (int i = 0; i < sendData.length; i++) {

                bytes[i] = sendData[i];
            }
            bytes[sendData.length] = 0x0D;
            bytes[sendData.length + 1] = 0x0A;

//            Log.info(TAG, "sendData:" + Arrays.toString(sendData));
            SocketUtil.getSocketUtil().sendContent(bytes, true);
            downloadTextView1.setText(getString(R.string.Sending_the_first) + currentP1 + getString(R.string.Packet_data));//("正在发送第" + currentP + "包数据");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 第6、7字节为总包数
     * 第8、9字节为当前
     * o
     *
     * @param buffer 进行CRC校验的buffer
     */
    private void initBuffer(byte[] buffer) {
        buffer[0] = 0x55;
        buffer[1] = 0x01;
        buffer[2] = 0x00;
        buffer[3] = 0x00;
        buffer[4] = 0x00;
        buffer[5] = 0x50;
//        buffer[6] = 0x0f;
//        buffer[7] = 0x00;
//        buffer[8] = 0x01;
//        buffer[9] = 0x00;
        buffer[10] = 0x00;
        buffer[11] = 0x04;
//        buffer[12] = 0x01;
//
//        for (int i = 13; i < 1036; i++)
//        {
//            buffer[i] = 0x0A;
//        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_rtu_button:
                ToastUtil.showToastLong(getString(R.string.update));
                break;
            default:
                break;
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result)) {
                return;
            }

            setData(result);
        } else if (id == UiEventEntry.DOWNLOADING) {
            String download = (String) args[0];
            if (TextUtils.isEmpty(download)) {
                return;
            }
            handDownLoad(download);
        } else if (id == UiEventEntry.SetAllConfigInfo) {
            String download = (String) args[0];
            if (TextUtils.isEmpty(download)) {
                return;
            }
            handDownLoad1(download);
        }
    }

    /**
     * 处理RTU返回数据
     *
     * @param download :RTU返回的数据
     */
    private void handDownLoad(String download) {
        String data = download.replaceAll(ConfigParams.DOWNLOAD, "");
        data = data.replace(" ok", "");
        String tempP = data.replaceAll(" ", "0").trim();
        Log.i(TAG, "temp:" + tempP);
        if (tempP.equals(packageSum)) {//接收包数与总包数相同，发送完成
            ToastUtil.showToastLong(getString(R.string.Sent_successfully));
            //接收完毕
            try {
                SocketUtil.getSocketUtil().setDownload(false);
                if (binIs != null) {
                    binIs.close();
                    binIs = null;
                }
                dataSum = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (tempP.equals(packageCurrent)) {
            currentP++;
            sendBinData();
        }
    }

    private void handDownLoad1(String download) {
        String data = download.replaceAll(ConfigParams.SetAllConfigInfo, "");
        data = data.replace(" OK", "");
        String tempP = data.replaceAll(" ", "0").trim();
        Log.i(TAG, "temp:" + tempP);
        if (tempP.equals(packageSum1)) {//接收包数与总包数相同，发送完成
            ToastUtil.showToastLong(getString(R.string.Successfully_send_device));
            //接收完毕
            try {
                SocketUtil.getSocketUtil().setDownload(false);
                if (binIs1 != null) {
                    binIs1.close();
                    binIs1 = null;
                }
                dataSum1 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (tempP.equals(packageCurrent1)) {

            currentP1++;
            sendBinData1();

        }

    }

    public void setData(String data) {
        if (currentType == UiEventEntry.WRU_2801 || currentType == UiEventEntry.WRU_2800 || currentType == UiEventEntry.WRU_2100) {
            if (data.contains(ConfigParams.Ver)) {
                String ver = data.replaceAll(ConfigParams.Ver, "").trim();
                rtuVers.setText(ver);
            }
        } else if (currentType == UiEventEntry.WRU_1901) {
            if (data.contains(ConfigParams.GROUND_Ver)) {

                String ver = data.replaceAll(ConfigParams.GROUND_Ver, "").trim();
                rtuVers.setText(ver);
            }
        } else if (currentType == UiEventEntry.LRU_3000) {
            if (data.contains(ConfigParams.Version)) {

                String ver = data.replaceAll(ConfigParams.Version, "").trim();
                rtuVers.setText(ver);
            }
        } else if (currentType == UiEventEntry.LRU_3200) {
            if (data.contains(ConfigParams.NetVer)) {

                String ver = data.replaceAll(ConfigParams.NetVer, "").trim();
                rtuVers.setText(ver);
            }
        } else if (currentType == UiEventEntry.RTU_2800 || currentType == UiEventEntry.RTU_2801) {
            if (data.contains(ConfigParams.SoftVer)) {
                String ver = data.replaceAll(ConfigParams.SoftVer, "").trim();
                rtuVers.setText(ver);
            }
        } else if (currentType == UiEventEntry.RCM_2000) {
            if (data.contains(ConfigParams.SoftVer)) {
                String ver = data.replaceAll(ConfigParams.SoftVer, "").trim();
                rtuVers.setText(ver);
            }
        } else if (currentType == UiEventEntry.LRU_BLE_3300) {
            if (data.contains(ConfigParams.ReadVer)) {
                String ver = data.replaceAll(ConfigParams.ReadVer, "").trim();
                rtuVers.setText(ver);
            }
        }
    }
}
