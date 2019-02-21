package com.heroan.operation.utils;

import android.os.Environment;

public class ConfigParams
{

//    public static final String IP = "192.168.1.194";
    public static final int PORT = 2800;
    public static final int GROUND_PORT = 8080;

    public static final String LRU_IP = "192.168.4.1";
    public static final int LRU_PORT = 3000;

    public static final String IP = "192.168.4.1";
//    //    public static final String IP = "192.168.4.1";
//    public static final int PORT = 2800;
//    public static final int GROUND_PORT = 8080;
//
//    public static final String LRU_IP = "192.168.4.1";
//    public static final int LRU_PORT = 3000;


    public static final String FTP_SERVER_IP = "139.129.109.197";
    public static final String FTP_SERVER_ACCOUNT = "4gcam";
    public static final String FTP_SERVER_PSD = "123456";
    public static final int FTP_SERVER_PORT = 21;

    public static final String VCLOUD_URL = "http://139.129.109.197:9089/vcloudweb/index.jsp";
    public static final String RTU_INFO_URL = "http://192.168.1.157:8080/find?id=";

//    configPara.RTUid: %d\r\n
//    configPara.Server_IP[0]: %s\r\n
//    configPara.Socket_Port[0]: %d\r\n
//    configPara.maishen: %dcm\r\n
//    Ver: %s\r\n
//    Real_time_data.BatteryVolts: %0.2f\r\n
//    Real_time_data.WaterLevel: %0.3f\r\n
//    Real_time_data.Water_Temperature: %0.1f\r\n
//    GPRS_CSQ: %d\r\n
//    rtc2set.tm_year: %4d-%2d-%2d %2d:%2d:%2d\r\n
//    configPara.GPRSCallMode: %d\r\n
//    configPara.RunMode: %d\r\n

    public static final String GROUND_RTUid = "configPara.RTUid: ";


    public static final String GROUND_Server_IP = "configPara.Server_IP: ";
    public static final String GROUND_Socket_Port = "configPara.Socket_Port: ";
    public static final String GROUND_maishen = "configPara.maishen: ";
    public static final String GROUND_Ver = "Ver: ";
    public static final String GROUND_BatteryVolts = "Real_time_data.BatteryVolts: ";

    public static final String GROUND_WaterLevel_2A = "WaterLevel_2A ";
    public static final String GROUND_GatePosition_1 = "GatePosition_1 ";

    public static final String GROUND_WaterLevel = "Real_time_data.WaterLevel: ";

    public static final String GROUND_Water_Temperature = "Real_time_data.Water_Temperature: ";
    public static final String GROUND_SYLL = "Real_time_data.Cumulative_Flow: ";
    public static final String GROUND_SYWater = "Real_time_data.Cumulative_Flow1: ";
    public static final String GROUND_GPRS_CSQ = "GPRS_CSQ: ";
    public static final String GROUND_tm_year = "rtc2set.tm_year: ";
    public static final String GROUND_GPRSCallMode = "configPara.GPRSCallMode: ";
    public static final String GROUND_RunMode = "configPara.RunMode: ";
    public static final String GROUND_Water485Type =   "configPara.Water485Type: ";
    public static final String GROUND_SetPacketInterval = "SetPacketInterval: ";
    public static final String GROUND_PrintMode = "configPara.PrintMode: ";
    public static final String GROUND_XIUZHENG = "configPara.XIUZHENG: ";


    // 图片回放
    public static final String ReadImage = "ReadImage ";
    // 接收历史数据
    public static final String RDDATATIME = "RDDATA TIME ";
    public static final String RDDATA = "RDDATA ";
    public static final String RDDATAEND = "RDDATA END";
    // 图片名字
    public static final String ImageName = "image.jpg";
    //历史记录的后缀名
    public static final String HistoryName = ".txt";

    public static final String Path = Environment.getExternalStorageDirectory() + "/";
    public static final String FtpImagePath = Path + "Vcontrol/image/";

    public static final String ResetUnit = "ResetUnit";
    public static final String Sam_Gate_now = "Sam_Gate_Now 1";
    // 图片路径
    public static final String ImagePath = Environment.getExternalStorageDirectory() + "/Vcontrol/";
    // 路径
    public static final String BinPath = Environment.getExternalStorageDirectory() + "/Vcontrol/";

    // 动静态IP地址设置
    public static final String SetStaticIP = "SetStaticIP ";
    // MAC地址、IP地址设置
    public static final String ReadStaticIP = "ReadStaticIP";
    // DHCP 静态IP
    public static final String IPFlags = "IP_Flags ";
    public static final String Module_MAC = "Module_MAC ";
    public static final int IP_DHCP = 0;
    public static final int IP_STATIC = 1;

    //设置各个信道连接方式  0，tcp；1，udp
    public static final String Setconnect_Type = "Setconnect_Type ";
    public static final String Setconnect_Type1 = "Setconnect_Type 1 ";
    public static final String Setconnect_Type2 = "Setconnect_Type 2 ";
    public static final String Setconnect_Type3 = "Setconnect_Type 3 ";
    public static final String Setconnect_Type4 = "Setconnect_Type 4 ";
    public static final String SetYUN_SETTIME_SendMode = "Set_YUNTIME_Mode ";
    public static final String SetYUN_ELEMENT_SendMode = "SetYUN_ELEMENT_SendMode ";


    // SetId_Type X，设置站号类型，0代表8位站号，1代表10位站号。(系统参数设置)
    public static final String SetId_Type = "SetId_Type ";

    //读取水质参数
    public static final String ReadWaterQuality = "ReadWaterQuality ";
    //设置水质传感器SetWaterQuality X 0-天健创新一体机 1-天健创新
    public static final String SetWaterQuality = "SetWaterQuality ";

    public static final String Setsz_select = "Setsz_select ";

    //读取电表参数
    public static final String ReadDIANBIAO_SensorPara = "ReadDIANBIAO_SensorPara";
    //设置电表协议
    public static final String Setdiaobian_guiyue = "Setdiaobian_guiyue ";

    public static final String ReadWeatherParam = "ReadWeatherParam ";

    // SetRTUid XXXXXX;设置10位站号的行政区划码，0-999999。
    public static final String SetRTUidxz = "SetRTUidxz ";
    // 存储间隔，0-1440
    public static final String SetSaveInterval = "SetSaveInterval ";

    // SetRTUid1 XXXXX;设置10位站号的遥测站地址，0-65535。
    public static final String SetRTUidyc = "SetRTUidyc ";


    public static final String SetRTUid18 = "SetRTUid18 ";
    // 遥测站地址
    public static final String SetAddr = "SetAddr ";
    public static final String SetDep = "SetDep ";

    //闸位计地址
    public static final String Setlora_gate_addr = "Setlora_gate_addr ";

    // 站点类型 0-降水站 1-河道站 2-水库站 3-闸坝站 4-泵站 5-墒情站 6-地下
    public static final String SetStationMode = "SetStationMode ";

    public static final String SetWorkModel = "SetWorkModel ";


    // 运行状态 0-低功耗 1-永在线
    public static final String SetWorkMode = "SetWorkMode ";

    // 拍照方式 0-定时拍照 1-加报拍照
    public static final String SetTakePhotoMode = "SetTakePhotoMode ";

    // 运行状态 0-低功耗 1-永在线
    public static final String SetXIUZHENG = "SetXIUZHENG ";

    //串口输出模式设置
    public static final String SetOutPut = "SetOutPut ";
    // 采集要素设置
    public static final String SetScadaFactor = "SetScadaFactor ";

    // 定时报间隔SetPacketInterval XX SetPacketInterval 01
    // 1-5分钟、2-10分钟、3-15分钟、4-30分钟、5-1小时、6-2小时、7-3小时、8-4小时、9-6小时、10-8小时、11-12小时、12-24小时,不足两位补零
    public static final String SetPacketInterval = "SetPacketInterval ";
    public static final String PicSendInterval = "PicSendInterval ";
    public static final String elecrelay = "elecrelay ";
    public static final String elecrelay1 = "elecrelay1 ";
    public static final String elecrelay2 = "elecrelay2 ";
    public static final String elecrelay3 = "elecrelay3 ";
    public static final String elecrelay4 = "elecrelay4 ";
    public static final String SetVideoInterval = "SetVideoInterval ";
    public static final String SetRS232_1_ADD_Channel = "SetRS232_1_ADD_Channel ";

    //采集间隔
    public static final String SetCollectionInterval = "SetCollectionInterval ";

    // 通信协议类型，(20个) 01-水文 02-协议1 03-协议2 ,…, 09-协议8  10-协议9  11-协议10，  12-协议11 最大99
    public static final String SetprotocolType = "SetprotocolType ";
    // 0 召测；1 不召测
    public static final String SetAcquisitionMode = "SetAcquisitionMode ";
    // 0 中国移动；1 中国联通 ；2：中国电信
    public static final String SetSIMType = "SetSIMType ";
    // 北斗厂家，00H-神州天鸿01H-成都国星
    public static final String SetBeidouType = "SetBeidouType ";
    // 中心站主信道
    public static final String SetCenterType = "SetCenterType ";
    // 中心站备用信道
    public static final String SetReserveType = "SetReserveType ";

    // 中心站IP
    public static final String SetIP = "SetIP ";
    public static final String setPort = " PORT ";
    public static final String SetNetIP = "SetNetIP";
    public static final String SetNetPort = "SetNetPort";
    // 中心站短信 十一位
    public static final String SetSMS = "SetSMS ";
    // 中心站北斗 号码不足六位，补零
    public static final String SetBeiDou = "SetBeiDou ";

    // 雨量计分辨力
    public static final String SetRainType = "SetRainType ";
    // 雨量计类型
    public static final String SetRainMeterPara = "SetRainMeterPara ";
    // 雨量加报阈值
    public static final String SetRainFlowChangeMax = "SetRainFlowChangeMax ";
    // 水位计分辨力 0-1厘米，1-0.5厘米，2-0.1厘米
    public static final String SetWaterMeterPara = "SetWaterMeterPara ";
    // 加报水位
    public static final String SetWaterLeverMax = "SetWaterLeverMax ";
    // 加报水位上加报阈值
    public static final String SetWaterLeverChangeUP = "SetWaterLeverChangeUP ";
    // 加报水位下加报阈值
    public static final String SetWaterLeverChangeDW = "SetWaterLeverChangeDW ";
    //水位拍照上阈值
    public static final String SetWaterPicUp = "SetWaterPicUp ";
    //水位拍照下阀值
    public static final String SetWaterPicDown = "SetWaterPicDown ";
    // 加报时间间隔
    public static final String SetAddReportInterval = "SetAddReportInterval ";
    // 水位修正值
    public static final String SetAnaWaterCal = "SetAnaWaterCal ";
    // 水位基值
    public static final String SetAnaWaterBac = "SetAnaWaterBac ";

    // 模拟量水位计选择 SetAddReportInterval
    // 0-0~5v，1-0~10v，2-0~20v，3-4~20mA
    public static final String SetAnaWaterType = "SetAnaWaterType ";
    // 模拟量滤波系数 SetWaterLeverChangeUP XX(0-999)
    public static final String SetWaterLvBo = "SetWaterLvBo ";
    // 水位计个数,1-9
    public static final String SetWater_Num = "SetWater_Num ";
    // 水位计1-4地址，0-256
    public static final String SetWater_Addr1 = "SetWater_Addr 1 ";
    public static final String SetWater_Addr2 = "SetWater_Addr 2 ";
    public static final String SetWater_Addr3 = "SetWater_Addr 3 ";
    public static final String SetWater_Addr4 = "SetWater_Addr 4 ";
    // 模拟量水位计量程 SetAnaWaterRange XX(0-999)
    public static final String SetAnaWaterRange = "SetAnaWaterRange ";
    // 水位计类型 SetAnaWaterType X
    // 1为模拟量，2为485，3为无
    public static final String SetWaterType = "SetWaterType ";
    //设置水位采集方式，1-水深,2-空高
    public static final String SetWater_caiji_Type = "SetWater_caiji_Type ";

    // 485水位计选择 SetWater485Type XX
    // 0-亿力能水位计 1-麦克水位计 2-古大水位计 3-伟思水位计

    public static final String SetWater485Type = "SetWater485Type ";

    // 摄像头类型类型 SetCameraType X
    // 1-白天型 2-夜视型 3-无
    public static final String SetCameraType = "SetCameraType ";
    // 摄像头厂家，01H-尚鑫航 02H-海康
    public static final String SetCameraManuf = "SetCameraManuf ";
    // 摄像头个数，1-9
    public static final String SetCamNum = "SetCamNum ";
    // 摄像头分辨率 SetPIC_Resolution X
    // 0为320*240，1为640*480，2为1280*960

    //图片发送模式 1-单包回复 2-多包回复
    public static final String SetPicSendMode = "SetPicSendMode ";
    public static final String SetPIC_Resolution = "SetPIC_Resolution ";
    public static final String SetFramesInterval = "SetFramesInterval ";

    public static final String SETTIME = "SETTIME ";
    public static final String RESETALL = "RESETALL ";
    public static final String RESETUNIT = "RESETUNIT ";
    public static final String RESETALL10 ="RESETALL10 ";
    public static final String GPRSTEST = "GPRSTEST ";
    public static final String SetRTC = "SetRTC ";

    /**
     * 查询参数
     */
    public static final String ReadNomalPara = "ReadNomalPara";
    public static final String ReadCollectPara = "ReadCollectPara";
    // 系统参数
    public static final String ReadSystemPara = "ReadSystemPara";
    public static final String ReadSystemPara6 = "Readlvalve_SensorPara";

    public static final String ReadCommPara = "ReadCommPara";
    public static final String ReadCommPara1 = "ReadCommPara1";
    public static final String ReadCommPara2 = "ReadCommPara2";
    public static final String ReadCommPara3 = "ReadCommPara3";
    public static final String ReadCommPara4 = "ReadCommPara4";
    public static final String ReadNetCfg = "ReadNetCfg";
    public static final String READYUN = "READ_YUN";

    //模拟量设置
    public static final String ReadAna_SensorPara = "ReadAna_SensorPara";
    //模拟量采集
    public static final String SetAna_element0 = "SetAna_element0 ";
    public static final String SetAna_element1 = "SetAna_element1 ";
    public static final String SetAna_element2 = "SetAna_element2 ";
    public static final String SetAna_element3 = "SetAna_element3 ";
    public static final String SetAna_element4 = "SetAna_element4 ";
    public static final String SetAna_element5 = "SetAna_element5 ";
    public static final String SetAna_element6 = "SetAna_element6 ";

    //模拟量量程
    public static final String SetAnaRange0 = "SetAnaRange0 ";
    public static final String SetAnaRange1 = "SetAnaRange1 ";
    public static final String SetAnaRange2 = "SetAnaRange2 ";
    public static final String SetAnaRange3 = "SetAnaRange3 ";
    public static final String SetAnaRange4 = "SetAnaRange4 ";
    public static final String SetAnaRange5 = "SetAnaRange5 ";
    public static final String SetAnaRange6 = "SetAnaRange6 ";


    public static final String SetAnaRange_low0 = "SetAnaRange_low0 ";

    // 传感器状态
    public static final String ReadSensorPara = "ReadSensorPara";
    public static final String ReadSensorPara1 = "ReadSensorPara1";
    public static final String ReadSensorPara2 = "ReadSensorPara2";
    public static final String Readliuliang_SensorPara = "Readliuliang_SensorPara";


    //温度
    public static final String ReadTemp_SensorPara = "ReadTemp_SensorPara";

    // 读取状态
    public static final String ReadRunStatus = "ReadRunStatus";
    public static final String ReadRunStatus1 = "ReadRunStatus1";

    public  static final String Read_turang_data = "Read_turang_data ";
    public  static final String Read_SHUIZHI_data = "Read_SHUIZHI_data ";
    // GPRS_CSQ %d\r\n信号强度
    public static final String GPRS_CSQ = "GPRS_CSQ";
    // GPRS_Status %d\r\n GPRS状态，见显示屏协议
    public static final String Gate_position = "GatePosition ";
    // GPRS_SMS_Handle_Status_display %d\r\n 短信状态,见显示屏协议
    public static final String GPRS_SMS_Handle_Status_display = "GPRS_SMS_Handle_Status_display";
    // my_socks[0].GPRS_Handle_Status_display %d\r\n Socket1状态，见显示屏协议
    public static final String SOCKET_STATUS_1 = "my_socks0GPRS_Handle_Status_display";
    // my_socks[1].GPRS_Handle_Status_display %d\r\n Socket2状态，见显示屏协议
    public static final String SOCKET_STATUS_2 = "my_socks1GPRS_Handle_Status_display";
    // my_socks[2].GPRS_Handle_Status_display %d\r\n Socket3状态，见显示屏协议
    public static final String SOCKET_STATUS_3 = "my_socks2GPRS_Handle_Status_display";
    // my_socks[3].GPRS_Handle_Status_display %d\r\n Socket4状态，见显示屏协议
    public static final String SOCKET_STATUS_4 = "my_socks3GPRS_Handle_Status_display";

    public static final String GPRS_Status = "GPRS_Status";
    //北斗信号强度
    public static final String BEIDOU_CSQ = "BEIDOU_CSQ";
    public static final String Send_informa_time_tm1 = "Send_informa_time_tm1";
    public static final String Send_informa_time_tm2 = "Send_informa_time_tm2";
    public static final String Send_informa_time_tm3 = "Send_informa_time_tm3";
    public static final String Send_informa_time_tm4 = "Send_informa_time_tm4";
    public static final String Send_inf_chanel1 = "Send_inf_chanel1";
    public static final String Send_inf_chanel2 = "Send_inf_chanel2";
    public static final String Send_inf_chanel3 = "Send_inf_chanel3";
    public static final String Send_inf_chanel4 = "Send_inf_chanel4";

    public static final String ReadRunStatus2 = "ReadRunStatus2";
    public static final String READAD = "READAD";

    public static final String READADV1 = "READADV1 ";
    public static final String READADV2 = "READADV2 ";
    public static final String READADV3 = "READADV3 ";
    public static final String READADV4 = "READADV4 ";
    public static final String READADV5 = "READADV5 ";
    public static final String READADV6 = "READADV6 ";
    public static final String READADV7 = "READADV7 ";
    public static final String READADV8 = "READADV8 ";

    public static final String READADA1 = "READADA1 ";
    public static final String READADA2 = "READADA2 ";
    public static final String READADA3 = "READADA3 ";
    public static final String READADA4 = "READADA4 ";
    public static final String READADA5 = "READADA5 ";
    public static final String READADA6 = "READADA6 ";
    public static final String READADA7 = "READADA7 ";
    public static final String READADA8 = "READADA8 ";

    public static final String Temperature_2G = "Temperature_2G ";
    public static final String Temperature_3G = "Temperature_3G ";

    public static final String CDN2R = "CDN2R ";
    public static final String CDN3R = "CDN3R ";
    public static final String CDN4R = "CDN4R ";


    // PIC_Sta %d\r\n 图片读取状态
    public static final String PIC_Sta = "PIC_Sta";
    // PIC_Send_Sta1 %d\r\n 信道1图片发送状态
    public static final String PIC_Send_Sta1 = "PIC_Send_Sta1";
    // PIC_Send_Sta2 %d\r\n 信道2图片发送状态
    public static final String PIC_Send_Sta2 = "PIC_Send_Sta2";
    // PIC_Send_Sta3 %d\r\n 信道3图片发送状态
    public static final String PIC_Send_Sta3 = "PIC_Send_Sta3";
    // PIC_Send_Sta4 %d\r\n\信道4图片发送状态
    public static final String PIC_Send_Sta4 = "PIC_Send_Sta4";
    // PIC_Co1 %d\r\n
    public static final String PIC_Co1 = "PIC_Co1";
    // PIC_Co2 %d\r\n
    public static final String PIC_Co2 = "PIC_Co2";
    // PIC_Co3 %d\r\n
    public static final String PIC_Co3 = "PIC_Co3";
    // PIC_Co4 %d\r\n\ 信道1-4图片发送总帧数
    public static final String PIC_Co4 = "PIC_Co4";

    // PIC_Fr1 %d\r\n
    public static final String PIC_Fr1 = "PIC_Fr1";
    // PIC_Fr2 %d\r\n
    public static final String PIC_Fr2 = "PIC_Fr2";
    // PIC_Fr3 %d\r\n
    public static final String PIC_Fr3 = "PIC_Fr3";
    // PIC_Fr4 %d\r\n 信道1-4图片发送当前帧数
    public static final String PIC_Fr4 = "PIC_Fr4";
    // 当前采集摄像头编号
    public static final String Camerapercent = "Camerapercent";

    public static final String ReadRunStatus3 = "ReadRunStatus3";
    // Equipment_Status %x\r\n 设备状态
    public static final String Equipment_Status = "Equipment_Status";
    // EQUIP_Reicv_count %d 485传感器接收数据个数
    public static final String EQUIP_Reicv_count = "EQUIP_Reicv_count";
    // EQUIP_Reicv %x%x%x%x%x%x%x%x%x%x%x%x\r\n 485传感器接收数据1-12,界面需要以16进制显示出来
    public static final String EQUIP_Reicv = "EQUIP_Reicv ";

    // 读取数据
    public static final String Readdata = "Readdata";
    public static final String ReadData = "ReadData";
    public static final String ReadFunctionData = "ReadFunctionData";
    public static final String CustomerSelect = "CustomerSelect ";
    public static final String ReadParameter = "ReadParameter";
    public static final String SoftVer = "SoftVer ";
    public static final String ReadNetParam = "ReadNetParam";
    public static final String ReadYUNStatus = "ReadYUNStatus";
    // 当前电压数值
    public static final String ReadBattery = "ReadBattery";
//    public static final String BatteryVolts = "BatteryVolts";

    public static final String IP_REGEX = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-4]|[1-9]\\d|[1-9])$";
    public static final String IP_ADDRESS_REGEX = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-4]|[1-9]\\d|[1-9])" + "|^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}" + "|^[\\s|\\S]{0}$";

    // 发送图片
    public static final String SENDPIC = "SENDPIC";
    //发送图片2
    public static final String SEND2PIC = "SEND2PIC";
    // 采集等待时
    public static final String SetSample_Delay_Time = "SetSample_Delay_Time ";
    // 低电压报警值 范围是800到2000
    public static final String SetBatteryVoltLow = "SetBatteryVoltLow ";
    // 日起始时间
    public static final String SetSendBeginTime = "SetSendBeginTime ";
    // 日起始时间
    public static final String SetAPN = "SetAPN ";

    public static final String SetLoraChannel = "SetLoraChannel ";
    // 日起始时间
    public static final String SetAPNUser = "SetAPNUser ";
    // 日起始时间
    public static final String SetAP_N_secret = "SetAP_N_secret ";
    // 日起始时间
    public static final String SetComPassword = "SetComPassword ";
    // 中心站1-4地址
    public static final String Setcenternumber = "Setcenternumber ";
    public static final String SendManualDataRain = "sendManual_Data_rain ";
    public static final String SendManualDataWater = "sendManual_Data_water ";
    // 设置第N路AD电流校准大值，N为1-8
    public static final String SETHA = "SETHA ";
    // 设置AD电流校准小值
    public static final String SETLA = "SETLA ";
    // 设置AD电压校准大值
    public static final String SETHV = "SETHV ";
    // 设置AD电压校准小值
    public static final String SETLV = "SETLV ";

    public static final String SetBatteryHigh = "SetBatteryHigh";
    public static final String SetBatteryLow = "SetBatteryLow";
    public static final String ReadBatteryHighStatus = "ReadBatteryHighStatus";
    public static final String ReadBatteryLowStatus = "ReadBatteryLowStatus";

    /**
     * 河道瞬时流量：Water_Flow
     * 河道累积流量：Cumulative_Flow
     * 正累积流量（2）：Cumulative_Flow1
     * 负累积流量（3）：Cumulative_Flow2
     * 河道流速：Flow_Speed
     * 累计流量(21): Cumulative_Flow6
     */
    public static final String Water_Flow = "Water_Flow";
    public static final String Waterb_Flowb = "Waterb_Flowb ";
    public static final String Waterc_Flowc = "Waterc_Flowc ";
    public static final String Cumulative_Flowa = "Cumulative_Flowa ";
    public static final String Cumulative_Flowb = "Cumulative_Flowb";
    public static final String Cumulative_Flowc = "Cumulative_Flowc";
    public static final String Flow_Speed = "Flow_Speed";
    public static final String Cumulative_Flowg = "Cumulative_Flowg";

    //北京尚水
    public static final String TRB = "TRB";
    public static final String Temperature_Water = "Temperature_Water";
    public static final String CDNR = "CDNR";
    public static final String PH = "PH";
    public static final String DO = "DO";
    public static final String CHLA = "CHLA";
    public static final String Phycocyanin = "Phycocyanin";
    public static final String COD = "COD";
    public static final String NH4N = "NH4N";
    public static final String ANGEL_1 = "ANGEL_1 ";
    public static final String ANGEL_2 = "ANGEL_2 ";
    public static final String ANGEL_3 = "ANGEL_3 ";

    //SetAnaWaterSignal X，设置模拟量水位计输出信号：1为正比例，2为反比例。(水位计)
    public static final String SetAnaWaterSignal = "SetAnaWaterSignal ";

    //电表查询
    public static final String Read_DIANBIAO_data = "Read_DIANBIAO_data";
    public static final String ZXZYG  = "ZXZYG ";
    public static final String JL_VOLTAGE_A = "JL_VOLTAGE_A ";
    public static final String JL_VOLTAGE_B = "JL_VOLTAGE_B ";
    public static final String JL_VOLTAGE_C = "JL_VOLTAGE_C ";
    public static final String JL_I_A = "JL_I_A ";
    public static final String JL_I_B = "JL_I_B ";
    public static final String JL_I_C = "JL_I_C ";

    // TotalRainVal %0.1f\r\n 累计雨量值
    public static final String TotalRainVal = "TotalRainVal ";
    // PrecentRainVal %0.1f\r\n 当前雨量值
    public static final String PrecentRainVal = "PrecentRainVal ";
    // WaterLevel_R %0.3f\r\n相对水位
    public static final String WaterLevel_R = "WaterLevel_R ";
    // WaterLevel_A %0.3f\r\n\绝对水位
    public static final String WaterLevel_A = "WaterLevel_A ";
    public static final String WaterLevel_2R = "WaterLevel_2R ";
    // WaterLevel_A %0.3f\r\n\绝对水位
    public static final String WaterLevel_2A = "WaterLevel_2A ";
    public static final String WaterLevel_3R = "WaterLevel_3R ";
    // WaterLevel_A %0.3f\r\n\绝对水位
    public static final String WaterLevel_3A = "WaterLevel_3A ";
    public static final String WaterLevel_4R = "WaterLevel_4R ";
    // WaterLevel_A %0.3f\r\n\绝对水位
    public static final String WaterLevel_4A = "WaterLevel_4A ";
    // Temperature_G %0.1f\r\n 地温
    public static final String Temperature_G = "Temperature_G ";
    // Temperature %0.1f\r\n rtu温度
    public static final String Temperature = "Temperature ";
    // BatteryVolts %0.2f\r\n 电池电压
    public static final String BatteryVolts = "BatteryVolts ";
    // BatteryVolts %0.2f\r\n 市电电压
    public static final String SHIDIANBatteryVolts = "SHIDIANBatteryVolts ";
    //    风速：WindSpeed 风向：WindDirection 气温：Temperature_A 湿度：humidity
    public static final String WindSpeed = "WindSpeed ";
    public static final String WindDirection = "WindDirection ";
    public static final String Temperature_A = "Temperature_A ";
    public static final String humidity = "humidity ";
    public static final String Press = "Press ";
    public static final String radiation = "radiation ";

    public static final String RSSI = "RSSI ";
    public static final String GModuleStatus = "4GModuleStatus ";
    public static final String ConnectStatus1 = "ConnectStatus1 ";
    public static final String ConnectStatus2 = "ConnectStatus2 ";
    public static final String ConnectStatus3 = "ConnectStatus3 ";
    public static final String ConnectStatus4 = "ConnectStatus4 ";
    public static final String NetID = "NetID ";


    /**
     * Moisture1  %0.1f\r\n            墒情1
     * Moisture2  %0.1f\r\n            墒情2
     * Moisture3  %0.1f\r\n            墒情3
     * M_Volt1 %0.2f\r\n               墒情1电压
     * M_Volt2 %0.2f\r\n               墒情2电压
     * M_Volt3 %0.2f\r\n               墒情3电压
     */

    //读取墒情参数
    public static final String ReadMoisture_SensorPara = "ReadMoisture_SensorPara";
    public static final String Moisture = "Moisture ";
    public static final String Moisture1 = "Moisture1 ";
    public static final String Moisture2 = "Moisture2 ";
    public static final String Moisture3 = "Moisture3 ";
    public static final String Moisture4 = "Moisture4 ";
    public static final String Moisture5 = "Moisture5 ";
    public static final String Moisture6 = "Moisture6 ";
    public static final String M_Volt1 = "M_Volt1 ";
    public static final String M_Volt2 = "M_Volt2 ";
    public static final String M_Volt3 = "M_Volt3 ";


    // MoistureType; //墒情选择，1为模拟量，2为485，3为无
    public static final String SetMoistureType = "SetMoistureType ";
    // Moisture485Type; //485墒情选择，1-银河
    public static final String SetMoisture485Type = "SetMoisture485Type ";
    // Moisture_Factor_1; //墒情3次方系数，范围（-100000至100000）（原值*10000）
    public static final String SetMoisture_Factor_4 = "SetMoisture_Factor_4 ";
    // Moisture_Factor_2; //墒情2次方系数，范围（-100000至100000）（原值*10000）
    public static final String SetMoisture_Factor_3 = "SetMoisture_Factor_3 ";
    // Moisture_Factor_3; //墒情1次方系数，范围（-100000至100000）（原值*10000）
    public static final String SetMoisture_Factor_2 = "SetMoisture_Factor_2 ";
    // Moisture_Factor_4; //墒情0次方系数，范围（-100000至100000）（原值*10000）
    public static final String SetMoisture_Factor_1 = "SetMoisture_Factor_1 ";
    // Moisture_Style[1]; //墒情的类型，0-无 1-10厘米 2-20厘米 3-30厘米 4-40厘米 5-50厘米 6-60厘米
    // 7-80厘米 8-100厘米
    public static final String SetMoisture_Style1 = "SetMoisture_Style1 ";
    // Moisture_Style[2]; //墒情的类型，0-无 1-10厘米 2-20厘米 3-30厘米 4-40厘米 5-50厘米 6-60厘米
    // 7-80厘米 8-100厘米
    public static final String SetMoisture_Style2 = "SetMoisture_Style2 ";
    // Moisture_Style[3]; //墒情的类型，0-无 1-10厘米 2-20厘米 3-30厘米 4-40厘米 5-50厘米 6-60厘米
    // 7-80厘米 8-100厘米
    public static final String SetMoisture_Style3 = "SetMoisture_Style3 ";
    public static final String SetMoisture_Style4 = "SetMoisture_Style4 ";
    public static final String SetMoisture_Style5 = "SetMoisture_Style5 ";
    public static final String SetMoisture_Style6 = "SetMoisture_Style6 ";

    public static final String SetAnaMoistureType = "SetAnaMoistureType ";

    // FlowType; //流量计选择，1为485，2为无
    public static final String SetFlowType = "SetFlowType ";
    // Flow485Type; //485流量计选择，1-大连海峰 2-大连明锐 3-唐山汇中 4-唐山美伦 5-连利水表
    public static final String SetFlow485Type = "SetFlow485Type ";
    public static final String SetWeir_Code_Number = "SetWeir_Code_Number ";
    // liusujiabao; //流速加报
    public static final String Setliusujiabao = "Setliusujiabao ";
    // liuliangjiabao; //流量加报
    public static final String Setliuliangjiabao = "Setliuliangjiabao ";
//    Setliuliang_Num X 流量计个数
     public static final String Setliuliang_Num = "Setliuliang_Num ";
    //Setliuliang_Factor_0，Setliuliang_Factor_1，Setliuliang_Factor_2，Setliuliang_Factor_3 流量次方系数
    public static final String Setliuliang_Factor_0 = "Setliuliang_Factor_0 ";
    public static final String Setliuliang_Factor_1 = "Setliuliang_Factor_1 ";
    public static final String Setliuliang_Factor_2 = "Setliuliang_Factor_2 ";
    public static final String Setliuliang_Factor_3 = "Setliuliang_Factor_3 ";

    // 压力计类型
    public static final String SetPressType = "SetPressType ";
    //SetPress485Type  xx 压力计厂家 1-星仪 2-昆仑中大
//    public static final String SetPress485Type = "SetPress485Type ";
    //温度计
    public static final String SetWaterTempType = "SetWaterTempType ";
    // 486压力计
    public static final String SetPress485Type = "SetPress485Type ";
    // 模拟量量程
    public static final String SetAnaPressRange = "SetAnaPressRange ";

    // zhaweijiabao; //闸位加报
    public static final String Setzhaweijiabao = "Setzhaweijiabao ";
    // AnaGatePositionRange; //模拟量闸位计量程（单位：厘米）
    public static final String SetAnaGatePositionRange = "SetAnaGatePositionRange ";
    // GatePosition_Type; //闸位计选择，1为模拟量，2为485，3为无
    public static final String SetGatePosition_Type = "SetGatePosition_Type ";
    // AnaGatePositionType; //模拟量闸位计选择，0为0~5v，1为0~10v，2为0~20v，3为4~20mA
    public static final String SetAnaGatePositionType = "SetAnaGatePositionType ";
    // GatePosition485Type; //485闸位计选择，1-伟思
    public static final String SetGatePosition485Type = "SetGatePosition485Type ";


    public static final String Ver = "Ver ";


//    命令格式：
//    SetConfig0 Sque XXX Delay XX Addr XXX Start XXXX Len XXXX
//    说明：Sque XXX 传感器序号，最大值不超过第0路传感器总个数
//    Delay XX  传感器上电延时时间，为0-99秒
//    Addr XXX  目标设备的地址，范围为1-255
//    Start XXXX 要读取的传感器起始地址，高字节在前，低在后
//    Len XXXX  要读取的数据长度，高字节在前，低字节在后。

    public static final String SetConfig0 = "SetConfig0 ";
    public static final String SetConfig1 = "SetConfig1 ";
    public static final String Sque = "Sque ";
    public static final String Delay = "Delay ";
    public static final String Addr = "Addr ";
    public static final String Start = "Start ";
    public static final String Len = "Len ";


    //    Set485Num X XXX
//    X-取值为0或1  XXX-传感器个数，不足3位用0补齐
    public static final String Set485Num = "Set485Num ";


    /**
     * 阀控APP  协议
     * 应答包：数据域 0x11为成功，非0x11为失败
     */

    //设置物理地址0x55，	数据域4个字节，低字节在先
    public static final String SetPhysicalAddress = "SetPhysicalAddress ";
    //    StandbyInterval XXXX  待机时间间隔
    public static final String StandbyInterval = "StandbyInterval ";
    //    LoraOverTime XX 超时时间
    public static final String LoraOverTime = "LoraOverTime ";
    //    LoRaAddress XX  ID
    public static final String LoRaAddress = "LoRaAddress ";
    //    TransparentAddress XX  透传地址
    public static final String TransparentAddress = "TransparentAddress ";
    //    PhyChannel XX   物理信道
    public static final String PhyChannel = "PhyChannel ";
    //    AirVelocity XX  空中速率
    public static final String AirVelocity = "AirVelocity ";
    //    ReadParameters  读取参数
    public static final String ReadParameters = "ReadParameters ";
    //    初始化  FactoryInitialization
    public static final String FactoryInitialization = "FactoryInitialization ";
    //    固件版本号
    public static final String ReadRTUVer = "ReadRTUVer ";
    public static final String ReadNetVer = "ReadNetVer ";
    public static final String NetVer = "NetVer ";
    public static final String ReadVer = "ReadVer ";


    //        设置IP通道6设置
    public static final String SetIPChannel = "SetIPChannel ";
    public static final String Port = "Port ";
    public static final String Space = " ";
    //        FTP地址端口设定
    public static final String SetFTPAddr = "SetFTPAddr ";
    //        关机指令
    public static final String ShutDown = "ShutDown";
    //        开机指令
    public static final String StartUp = "StartUp";
    //        开机指令
    public static final String ReadStatus = "ReadStatus";
    //    卡配置指令（1联通移动，2电信）
    public static final String SetSIMConfig = "SetSIMConfig ";
    public static final String YunFunction = "YunFunction ";
    //    拍照并保存上传（无参数）
    public static final String SetTakePhoto = "SetTakePhoto";
    //    拍照间隔(单位：min)
    public static final String SetTakePhotoInterval = "SetTakePhotoInterval ";
    //    录像保存到TF卡（1个参数单位秒）
    public static final String SetVideoSave = "SetVideoSave ";
    //    清空SD卡（无参数）
    public static final String SetClearSDCard = "SetClearSDCard";
    //    重启系统板(无参数)
    public static final String SetRestartSystemBoard = "SetRestartSystemBoard";
    public static final String Version = "Version ";
    //    NetWorkCardStatus 0
    public static final String NetWorkCardStatus = "NetWorkCardStatus ";
    //    TFCardStatus 0
    public static final String TFCardStatus = "TFCardStatus ";

    public static final String SetCameraAPN = "SetCameraAPN ";

    //    阀门状态
    public static final String valve_status = "valve_status ";

    //  阀门设置 1-蝶阀 2-脉冲电磁阀
    public static final String SetvalveType = "SetvalveType ";

    //3000 阀1  阀2
    public static final String valve1ctl = "valve1ctl ";
    public static final String valve2ctl = "valve2ctl ";

    public static final String PulsePolar = "PulsePolar ";
    public static final String SensorPolar = "SensorPolar ";
    public static final String Channel = "Channel ";
    public static final String NetInfo = "NetInfo ";
    public static final String PulseTime = "PulseTime ";
    public static final String Serial = "Serial ";
    public static final String Battery = "Battery ";
    public static final String Flows = "Flows ";
    public static final String Pressure = "Pressure ";

    public static final String SetAllConfigInfo = "SetAllConfigInfo ";
    //    下发bin文件
    public static final String DOWNLOAD = "DOWNLOAD ";
    public static final String DOWNLOADEND = "BB";
    public static final String RN = "\r\n";



    // 1、控制云台向左       SetYUNLeft
    public static final String SetYUNLeft = "SetYUNLeft ";
    //2、控制云台向右 SetYUNRight
    public static final String SetYUNRight = "SetYUNRight ";
    //3、控制云台向上  SetYUNUp
    public static final String SetYUNUp = "SetYUNUp ";
    //4、控制云台向下  SetYUNDown
    public static final String SetYUNDown = "SetYUNDown ";
    //5、设置云台转动幅度  X = 3表示20°/s X = 2表示12°/s X = 1表示6°/s
    public static final String SetYUNCorner = "SetYUNCorner ";
    //6、设置当前位置    1 左限位  2 右限位
    public static final String SetYUNLimit = "SetYUNLimit";
    public static final String SetYUNTurnOn = "SetYUNTurnOn";
    public static final String SetYUNTurnOff = "SetYUNTurnOff";

    public static final String YUNMainType = "YUNMainType ";
    public static final String YUNMinorType = "YUNMinorType ";
    public static final String YUNStatus = "YUNStatus ";
    public static final String SetMainSurveyStatus = "SetMainSurveyStatus";
    public static final String DelMainSurveyStatus = "DelMainSurveyStatus ";
    public static final String RegulateMainSurvey = "RegulateMainSurvey";
    public static final String AddMinorSurvey = "AddMinorSurvey";
    public static final String SetMinorSurveyStatus = "SetMinorSurveyStatus ";
    public static final String DelMinorSurvey = "DelMinorSurvey";
    public static final String RegulateMinorSurvey = "RegulateMinorSurvey ";
    public static final String MainSurveyStatus = "MainSurveyStatus ";
    public static final String MinorSurveyStatus = "MinorSurveyStatus";
    public static final String MinorSurveyStatus1 = "MinorSurveyStatus1 ";
    public static final String CameraTakePicture = "CameraTakePicture";
    // 删除单条设备
    public static final String DelDeviceID = "DelDeviceID ";
    // 设备列表
    public static final String ReadDeviceList = "ReadDeviceList ";
    //
    public static final String DeviceID = "DeviceID";
//    public static final String SetYUNRightLimit = "SetYUNRightLimit";


}
