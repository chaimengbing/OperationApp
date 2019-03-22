package com.heroan.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.RtuListAdapter;
import com.heroan.operation.model.RtuItem;

import java.util.List;

import zuo.biao.library.base.BaseHttpListActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.JSON;

public class RtuListActivity extends BaseHttpListActivity<RtuItem, ListView, RtuListAdapter> implements View.OnClickListener {

    private TextView titleRight;
    private TextView title;
    private ImageView backImageView;

    private String userCode = "";


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
                adapter.refresh(list);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        title.setText(getString(R.string.rtu_list));
        titleRight.setVisibility(View.GONE);
    }

    @Override
    public void getListAsync(final int page) {

        onHttpResponse(0, "[{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140027\",\"projectAlias\":\"龙慧德鸿\"},{\"projectId\":\"119361810131214\",\"stationId\":\"120001808140027\",\"projectAlias\":\"龙慧德鸿\"}]", new Exception());

//        HttpRequest.getDevicesList(userCode, 0, new OnHttpResponseListener() {
//            @Override
//            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
//                onHttpResponse(requestCode, resultJson, e);
//            }
//        });
    }

    @Override
    public List<RtuItem> parseArray(String json) {
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
