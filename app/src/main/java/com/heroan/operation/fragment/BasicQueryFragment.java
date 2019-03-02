package com.heroan.operation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

public class BasicQueryFragment extends BaseFragment implements EventNotifyHelper.NotificationCenterDelegate{


    private static BasicQueryFragment instance;

    private TextView dianyaText, xinhaoText, netStateText, sendstateText, sendTimeText, wenduText, yuliangText, shuiweiText;

    public static BasicQueryFragment createInstance() {
        if (instance == null) {
            instance = new BasicQueryFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_params_query);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }

    @Override
    public void initView() {

        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        dianyaText = findView(R.id.dianya_text);
        xinhaoText = findView(R.id.xinhao_text);
        netStateText = findView(R.id.net_state_text);
        sendstateText = findView(R.id.send_state_text);
        sendTimeText = findView(R.id.send_time_text);
        wenduText = findView(R.id.wendu_text);
        yuliangText = findView(R.id.yuliang_text);
        shuiweiText = findView(R.id.shuiwei_text);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == UiEventEntry.READ_DATA){

        }
    }
}
