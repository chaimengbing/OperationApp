package com.heroan.operation.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.heroan.operation.OperationApplication;
import com.heroan.operation.R;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketClientSendingDelegate;
import com.vilyever.socketclient.helper.SocketPacket;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import zuo.biao.library.util.Log;

public class SocketUtil
{

    private static final String TAG = SocketUtil.class.getSimpleName();

    private int search = 0 ;
    private FileOutputStream imageFos;
    private FileOutputStream historyFos;
    private boolean isReSend = true;

    /**
     * SocketClient 实例测试
     */

    private static SocketUtil socketUtil = null;
    private SocketClient socketClient;
    private boolean isDownload = false;

    private String send;
    private byte[] sendByte;

    public static SocketUtil getSocketUtil()
    {

        if (socketUtil == null)
        {
            socketUtil = new SocketUtil();
        }
        return socketUtil;
    }


    public void connectRTU(String ip, int port)
    {
        try
        {
            Log.i(TAG, "connectRTU::");
            closeSocketClient();
            socketClient = new SocketClient();
            // 远程端IP地址
            socketClient.getAddress().setRemoteIP(ip);
            // 远程端端口号
            socketClient.getAddress().setRemotePort(port);
            // 连接超时时长，单位毫
            socketClient.getAddress().setConnectionTimeout(15 * 1000);
            // 设置编码为UTF-8
            socketClient.setCharsetName(CharsetUtil.UTF_8);

            socketClient.registerSocketClientDelegate(new SocketClientDelegate()
            {
                @Override
                public void onConnected(SocketClient client)
                {
                    Log.i(TAG, "onConnected::");
                    EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.CONNCT_OK);
                }

                @Override
                public void onDisconnected(SocketClient client)
                {
                    Log.i(TAG, "onDisconnected::");
                    closeSocketClient();
                    EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.CONNCT_FAIL);
                }

                @Override
                public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket)
                {
                    handReceData(responsePacket);
                }
            });


            socketClient.registerSocketClientSendingDelegate(new SocketClientSendingDelegate()
            {
                @Override
                public void onSendPacketBegin(SocketClient client, SocketPacket packet)
                {

                }

                @Override
                public void onSendPacketCancel(SocketClient client, SocketPacket packet)
                {

                }

                @Override
                public void onSendPacketEnd(SocketClient client, SocketPacket packet)
                {
                    reSendContent();
                }

                @Override
                public void onSendPacketProgress(SocketClient client, SocketPacket packet, float progress)
                {
                }
            });
            socketClient.connect();
        } catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

    }

    private void reSendContent()
    {
        if (socketClient == null)
        {
            return;
        }

        if (send.contains(ConfigParams.RDDATA) || send.contains(ConfigParams.RDDATAEND) || send.contains(ConfigParams.ReadImage) || isDownload )
        {
            //2秒未收到重复发送数据
            OperationApplication.applicationHandler.removeCallbacks(reSendRunnable);
            OperationApplication.applicationHandler.postDelayed(reSendRunnable, UiEventEntry.TIME);
        }

    }


    private Runnable reSendRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (isReSend)
            {
                if (isDownload)
                {
                    if (sendByte == null || sendByte.length == 0)
                    {
                        return;
                    }
                    sendContent(sendByte, true);
                }

                else
                {
                    if (TextUtils.isEmpty(send))
                    {
                        return;
                    }
                    sendContent(send);
                }
            }
        }
    };


    private void handReceData(@NonNull SocketResponsePacket responsePacket)
    {
        Log.i(TAG, "handReceData::");
        String data = responsePacket.getMessage();
        String[] res = data.split("\r\n");
        byte[] data1 = responsePacket.getData();

//        Log.info(TAG, "data:" + data);

        if (isDownload)
        {
            isReSend = false;
            for (String result : res)
            {
                Log.i(TAG, result);
                EventNotifyHelper.getInstance().postNotification(UiEventEntry.DOWNLOADING, result);
                EventNotifyHelper.getInstance().postNotification(UiEventEntry.SetAllConfigInfo, result);

            }
        }
        else
        {

            if (send.contains(ConfigParams.RDDATATIME) || send.contains(ConfigParams.RDDATA))
            {// 请求历史文件
                isReSend = false;
                receiveHistory(data1);
            }
            else if (send.contains(ConfigParams.ReadImage))
            {// 请求图片
                isReSend = false;
                receiveImage(data1);
            }
            else
            {
                for (String result : res)
                {
                    Log.i(TAG, "result:" + result);
                    if (ConfigParams.SetBatteryHigh.equals(send) || ConfigParams.SetBatteryLow.equals(send) || ConfigParams.ReadBatteryHighStatus.equals(send) || ConfigParams.ReadBatteryLowStatus.equals(send))
                    {//AD电压采集单独处理
                        EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_AD_LV, result);
                    }
                    else
                    {
                        if (result != null && result.toUpperCase().contains("OK"))
                        {
                            EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_RESULT_OK, result, send);
                        }
                        else if (result != null && result.toUpperCase().contains("ERROR"))
                        {
                            EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_RESULT_ERROR, result, send);
                        }
                        else if (result != null && result.contains("Not Started"))
                        {// System Not Started
                            ToastUtil.showToastLong(OperationApplication.getInstance().getString(R.string.Device_again_later));
                        }
                        else
                        {
                            EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_DATA, result, send);
                            if (search == UiEventEntry.TAB_SEARCH_CAMERA){

                                return;
                        }else {
                                if (search == UiEventEntry.TAB_SEARCH_GPRS)
                                {
                                    return;
                                }else {
                                    ToastUtil.showToastLong(OperationApplication.getInstance().getString(R.string.Get_data_successfully));
                                }


                            }

                        }
                    }
                }
            }
        }
    }


    /**
     * 发送数据
     *
     * @param content
     */
    public void sendContent(String content)
    {
        if (TextUtils.isEmpty(content))
        {
            return;
        }
        if (socketClient == null)
        {
            return;
        }
        this.send = content;
        isReSend = true;

        socketClient.sendString(content + "\r\n");


    }



    public void setDownload(boolean isDownload)
    {
        this.isDownload = isDownload;
    }




    /**
     * 发送数据
     *
     * @param data
     */
    public void sendContent(byte[] data, boolean isDownload)
    {
        if (data == null)
        {
            return;
        }
        if (socketClient == null)
        {
            return;
        }
        Log.i(TAG, "sendContent::data:");
        this.isDownload = isDownload;
        this.sendByte = data;
        isReSend = true;
        socketClient.sendData(data );
    }




    /**
     * 关闭Socket连接
     */
    public void closeSocketClient()
    {
        if (socketClient != null)
        {
            socketClient.disconnect();
            socketClient = null;
        }
    }

    /**
     * Socket是否连接
     */
    public boolean isConnected()
    {
        if (socketClient != null)
        {
            return socketClient.isConnected();
        }
        return false;
    }


    public void startReceImage()
    {
        try
        {
            File file = new File(ConfigParams.ImagePath + ConfigParams.ImageName);
            if (!file.exists())
            {
                // 图片文件不存在 创建文件
                file.createNewFile();
            }
            imageFos = new FileOutputStream(file);

        } catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
    }


    public void startReceHostory()
    {
        try
        {
            if (historyFos != null)
            {
                return;
            }

            Log.i(TAG, "startReceHostory::");
            File file = new File(ConfigParams.ImagePath + ServiceUtils.getStringDate() + ConfigParams.HistoryName);
            if (!file.exists())
            {
                // 图片文件不存在 创建文件
                file.createNewFile();
            }
            historyFos = new FileOutputStream(file);

        } catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
    }

    public void stopReceHistory()
    {
        Log.i(TAG, "stopReceHistory::");

        try
        {
            if (historyFos != null)
            {
                historyFos.close();
                historyFos = null;
            }
        } catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

    }

    public void stopReceImages()
    {
        Log.i(TAG, "stopReceImages::");

        try
        {
            if (imageFos != null)
            {
                imageFos.close();
            }
        } catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

    }


    private void receiveImage(byte[] buffer)
    {
        try
        {
            if (imageFos == null)
            {
                return;
            }
            // 上一包
            String before = "000";
            // 当前包数
            String current = "000";
            // 总包数
            String length = "";

            int imageSize = buffer.length - 23;


            byte[] imageInfoBuffer = new byte[20];
            byte[] imageBuffer = new byte[imageSize];


            // 跳过前19个字节
            System.arraycopy(buffer, 20, imageBuffer, 0, imageSize);
            // 获取图片包信息、
            System.arraycopy(buffer, 0, imageInfoBuffer, 0, 20);

            imageFos.write(imageBuffer, 0, imageBuffer.length);
            imageFos.flush();

            byte[] lengthByte = new byte[10];
            for (int i = 0; i < 7; i++)
            {
                lengthByte[i] = imageInfoBuffer[i + 13];
            }
            String lengthS = new String(lengthByte);

            current = lengthS.substring(0, 3);
            length = lengthS.substring(4, 7);

            if (ServiceUtils.isGarbledCode(current))
            {// 如果当前包有乱码，再次获取上一包数据
                sendContent(ConfigParams.ReadImage + before);
            }
            else
            {
                before = current;
            }

            // 总包数等于当前包数
            if (!TextUtils.isEmpty(current) && !TextUtils.isEmpty(length) && current.equals(length))
            {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_IMAGE_SUCCESS);
                stopReceImages();
                return;
            }

            // 取完一包，在去获取下一包
            if (!ServiceUtils.isGarbledCode(current))
            {
                sendContent(ConfigParams.ReadImage + current);
            }
            else
            {
                sendContent(ConfigParams.ReadImage + before);
            }
        } catch (Exception e)
        {
            ToastUtil.showToastLong(OperationApplication.getInstance().getString(R.string.Image_failed));
            Log.e(TAG, e.toString());
        }

    }

    private void receiveHistory(byte[] buffer)
    {
        try
        {
            if (historyFos == null)
            {
                return;
            }

            String end = new String(buffer);
            Log.i(TAG, "end:" + end);
            // 读取文件完成
            if (!TextUtils.isEmpty(end) && end.contains(ConfigParams.RDDATAEND))
            {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_HISTORY_SUCCESS);
                stopReceHistory();
                OperationApplication.applicationHandler.removeCallbacks(reSendRunnable);
                return;
            }

            // 读取文件错误
            if (!TextUtils.isEmpty(end) && end.contains("error"))
            {
                EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_RESULT_ERROR);
                stopReceHistory();
                OperationApplication.applicationHandler.removeCallbacks(reSendRunnable);
                return;
            }

            String current = "";
            String result = "";

            byte[] infoBuffer = new byte[13];
            int historySize = buffer.length - 14;
            byte[] historyBuffer = new byte[historySize];

            // 跳过前14个字节
            System.arraycopy(buffer, 14, historyBuffer, 0, historyBuffer.length);
            // 获取文件包信息、
            System.arraycopy(buffer, 0, infoBuffer, 0, 13);


            historyFos.write(historyBuffer, 0, historyBuffer.length);
            historyFos.flush();

            String lengthS = new String(infoBuffer);

            result = lengthS.substring(0, 6);
            current = lengthS.substring(7, 13);

            Log.i(TAG, "result:" + result);
            Log.i(TAG, "current:" + current);
            EventNotifyHelper.getInstance().postUiNotification(UiEventEntry.READ_CURRENT_HISTORY, current);


            // 取完一包，在去获取下一包
            if (!ServiceUtils.isGarbledCode(current))
            {
                sendContent(ConfigParams.RDDATA + current + " OK");
            }
        } catch (Exception e)
        {
//            ToastUtil.showToastLong("历史文件获取失败！");
            Log.e(TAG, e.toString());
        }

    }
}

