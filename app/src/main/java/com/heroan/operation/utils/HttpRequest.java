/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.heroan.operation.utils;

import com.heroan.operation.model.OperationOrder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.manager.HttpManager;
import zuo.biao.library.model.Parameter;
import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;

/**
 * HTTP请求工具类
 *
 * @author Lemon
 * @use 添加请求方法xxxMethod >> HttpRequest.xxxMethod(...)
 * @must 所有请求的url、请求方法(GET, POST等)、请求参数(key-value方式，必要key一定要加，没提供的key不要加，value要符合指定范围)
 * 都要符合后端给的接口文档
 */
public class HttpRequest {
    //	private static final String TAG = "HttpRequest";


    /**
     * 基础URL，这里服务器设置可切换
     */
    public static final String URL_BASE = SettingUtil.getCurrentServerAddress();
    public static final String PAGE_NUM = "pageNum";


    //示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //user<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String RANGE = "range";

    public static final String ID = "id";
    public static final String USER_CODE = "user_code";
    public static final String ORDER_ID = "order_id";
    public static final String COMPANY_ID = "company_id";
    public static final String CREATE_TIME = "create_time";
    public static final String SUMMARY = "summary";
    public static final String IMAGEFILES0 = "Imgfiles0";
    public static final String IMAGEFILES1 = "Imgfiles1";
    public static final String IMAGEFILES2 = "Imgfiles2";
    public static final String ISCHANGE_BATTERY = "ischangeBattery";
    public static final String ISCHANGE_SOLAR_BATTERY = "ischangeSolarBattery";
    public static final String ISCHANGE_WIRE = "ischangeWire";
    public static final String ISCHANGE_RTU = "ischangeRtu";
    public static final String SCH_ID = "schId";
    public static final String REGISTER_ID = "registerId";
    public static final String USER_ID = "userId";
    public static final String CURRENT_USER_ID = "currentUserId";

    /**
     * code
     * register_id
     */

    public static final String CODE = "code";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";


    /**
     * 翻译，根据有道翻译API文档请求
     * http://fanyi.youdao.com/openapi?path=data-mode
     * <br > 本Demo中只有这个是真正可用，其它需要自己根据接口文档新增或修改
     *
     * @param word
     * @param requestCode
     * @param listener
     */
    public static void translate(String word, final int requestCode,
                                 final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("q", word);
        request.put("keyfrom", "ZBLibrary");
        request.put("key", 1430082675);
        request.put("type", "data");
        request.put("doctype", "json");
        request.put("version", "1.1");

        HttpManager.getInstance().get(request, "http://fanyi.youdao.com/openapi.do", requestCode,
                listener);
    }


    //account<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param listener
     */
    public static void register(final String phone, final String password, final String infoDes,
                                final String registerId,
                                final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put(PHONE, phone);
        request.put(PASSWORD, password);
        request.put(SUMMARY, infoDes);
        request.put(REGISTER_ID, registerId);

        HttpManager.getInstance().post(request, URL_BASE + "/registerAjax", requestCode, listener);
    }

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @param listener
     */
    public static void login(final String phone, final String password, String registerId,
                             final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put(PHONE, phone);
        request.put(CODE, phone);
        request.put(PASSWORD, password);
        request.put(REGISTER_ID, registerId);

        HttpManager.getInstance().post(request, URL_BASE + "/loginAjax", requestCode, listener);
    }

    //account>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**
     * 同步设备清单
     *
     * @param phone
     * @param requestCode
     * @param listener
     */
    public static void getDevicesList(String phone, final int requestCode,
                                      final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put(PHONE, phone);

        HttpManager.getInstance().get(request, URL_BASE + "/equipmentListAjax", requestCode,
                listener);
    }

    public static final int USER_LIST_RANGE_ALL = 0;
    public static final int USER_LIST_RANGE_RECOMMEND = 1;
    //1.创建对应的MediaType
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 运维打卡
     * String longitude = request.getParameter("longitude");// 经度
     * * 		String latitude = request.getParameter("latitude");// 维度
     *
     * @param phone
     * @param requestCode
     * @param listener
     */
    public static void addProduct(String phone, String longitude, String latitude,
                                  OperationOrder operationOrder, String operaInfo,
                                  String changeBattery, String changeSolar, String changeWire,
                                  String changeRTU, String finishOrder, File envFile,
                                  File beforeFile, File afterFile, final int requestCode,
                                  final OnHttpResponseListener listener) {

        //2.创建RequestBody
        MultipartBody.Builder fileBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        fileBody.addFormDataPart(IMAGEFILES0, envFile.getName(),
                RequestBody.create(MEDIA_TYPE_PNG, envFile));
        fileBody.addFormDataPart(IMAGEFILES1, beforeFile.getName(),
                RequestBody.create(MEDIA_TYPE_PNG, beforeFile));
        fileBody.addFormDataPart(IMAGEFILES2, afterFile.getName(),
                RequestBody.create(MEDIA_TYPE_PNG, afterFile));
        fileBody.addFormDataPart(PHONE, phone);
        fileBody.addFormDataPart(LONGITUDE, longitude);
        fileBody.addFormDataPart(LATITUDE, latitude);
        fileBody.addFormDataPart(ORDER_ID, operationOrder.getOrder_id());
        fileBody.addFormDataPart(SUMMARY, operaInfo);
        fileBody.addFormDataPart(ISCHANGE_BATTERY, changeBattery);
        fileBody.addFormDataPart(ISCHANGE_SOLAR_BATTERY, changeSolar);
        fileBody.addFormDataPart(ISCHANGE_WIRE, changeWire);
        fileBody.addFormDataPart(ISCHANGE_RTU, changeRTU);
        fileBody.addFormDataPart(SCH_ID, finishOrder);
        fileBody.addFormDataPart(CREATE_TIME, operationOrder.getCreate_time());
        fileBody.addFormDataPart(COMPANY_ID, operationOrder.getCompany_id());
        //3.构建MultipartBody
        RequestBody requestBody = fileBody.build();

        String url = URL_BASE + "/addProductAjax";
        //4.构建请求
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        final OkHttpClient okHttpClient = HttpManager.getInstance().getHttpClient(url);
        //5.发送请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    listener.onHttpResponse(requestCode, response.body().string(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


//        HttpManager.getInstance().get(request, URL_BASE + "/addProductAjax", requestCode,
//                listener);
    }


    /**
     * 工单接单接口
     *
     * @param phone
     * @param orderId
     * @param requestCode
     * @param listener
     */
    public static void singleOrders(String phone, String orderId, final int requestCode,
                                    final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put(PHONE, phone);
        request.put(ORDER_ID, orderId);


        HttpManager.getInstance().get(request, URL_BASE + "/singleOrdersAjax", requestCode,
                listener);
    }

    /**
     * 运维工单同步
     *
     * @param phone
     * @param requestCode
     * @param listener
     */
    public static void operational(String phone, final int requestCode,
                                   final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put(PHONE, phone);
        HttpManager.getInstance().get(request, URL_BASE + "/operationalAjax", requestCode,
                listener);
    }


    //user>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**
     * 添加请求参数，value为空时不添加，最快在 19.0 删除
     *
     * @param list
     * @param key
     * @param value
     */
    @Deprecated
    public static void addExistParameter(List<Parameter> list, String key, Object value) {
        if (list == null) {
            list = new ArrayList<Parameter>();
        }
        if (StringUtil.isNotEmpty(key, true) && StringUtil.isNotEmpty(value, true)) {
            list.add(new Parameter(key, value));
        }
    }


}