package com.heroan.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.RtuListAdapter;
import com.heroan.operation.model.RtuItem;
import com.heroan.operation.utils.HttpRequest;

import java.util.List;

import zuo.biao.library.base.BaseHttpListActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.SettingUtil;

public class RtuListActivity extends BaseHttpListActivity<RtuItem, ListView, RtuListAdapter> implements View.OnClickListener {

    private static final String TAG = RtuListActivity.class.getName();
    private TextView titleRight;
    private TextView title;
    private ImageView backImageView;

    private TextView noData;
    private TextView numText;

    private String userCode = "";
    private boolean isFirst = true;
    private String resultData = "[{\"projectId\":\"119361810131214\"," +
            "\"stationId\":\"120001808140027\",\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140028\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140029\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140030\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140031\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140032\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140033\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140034\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140035\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140036\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140037\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140038\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140039\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140040\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140041\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140042\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140043\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140044\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140045\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140046\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140047\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140048\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140049\"," +
            "\"projectAlias\":\"舟山市定海区\"}," +
            "{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140050\"," +
            "\"projectAlias\":\"舟山市定海区\"}]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtu_list);
        initView();
        initData();
        initEvent();

        srlBaseHttpList.autoRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        super.initView();

        backImageView = findViewById(R.id.title_back);
        titleRight = findViewById(R.id.title_right);
        title = findViewById(R.id.title);
        numText = findViewById(R.id.num_text);
        noData = findViewById(R.id.noData);
    }

    private void setNum() {
        if (adapter != null) {
            numText.setText("总台数：" + adapter.getCount());
        } else {
            numText.setText("总台数：0");

        }
    }


    @Override
    public void onStopRefresh() {
        super.onStopRefresh();
        Log.i(TAG,"onStopRefresh::");
        runUiThread(new Runnable() {
            @Override
            public void run() {
                String rtuListString = SettingUtil.getSaveValue(SettingUtil.RTU_LIST);
                if (TextUtils.isEmpty(rtuListString)) {
                    showShortToast("清先获取设备清单");
                    return;
                }
                final List<RtuItem> rtuItems = JSON.parseArray(rtuListString, RtuItem.class);
                setList(rtuItems);
            }
        });

    }

    private void notifyList(List<RtuItem> rtuItems ){
        if (adapter == null){
            return;
        }
        if (rtuItems != null && rtuItems.size() > 0){
            adapter.refresh(rtuItems);
            lvBaseList.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }else {
            noData.setVisibility(View.VISIBLE);
            lvBaseList.setVisibility(View.GONE);
        }
        setNum();
    }

    @Override
    public void setList(final List<RtuItem> list) {
        setList(new AdapterCallBack<RtuListAdapter>() {

            @Override
            public RtuListAdapter createAdapter() {
                return new RtuListAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                notifyList(list);

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        title.setText(getString(R.string.rtu_list));
        titleRight.setVisibility(View.GONE);
        userCode = SettingUtil.getSaveValue(SettingUtil.PHONE);
        setNum();
//        userCode = "110";
    }

    @Override
    public void getListAsync(final int page) {

        Log.d(TAG, "page:" + page);
        HttpRequest.getDevicesList(userCode, page, this);

    }

    @Override
    public List<RtuItem> parseArray(String json) {
        if (!TextUtils.isEmpty(json)){
            SettingUtil.setSaveValue(SettingUtil.RTU_LIST, json);
        }
        return JSON.parseArray(json, RtuItem.class);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
    }
}
