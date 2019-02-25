package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.DevicesAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class DevicesFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{

    private ListView deviceListView;
    private DevicesAdapter adapter;
    private List<String> deviceList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_device_list);
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
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DEL_DEVICE_OK);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DEL_DEVICE_ERROR);
    }



    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DEL_DEVICE_ERROR);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DEL_DEVICE_OK);

        deviceListView = (ListView) view.findViewById(R.id.device_listview);
    }

    @Override
    public void initData()
    {
        adapter = new DevicesAdapter(getActivity());
        deviceListView.setAdapter(adapter);
        ServiceUtils.sendData(ConfigParams.ReadDeviceList);
    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onClick(View view)
    {

    }

    @Override
    public void didReceivedNotification(int id, Object... args)
    {
        if (id == UiEventEntry.READ_DEL_DEVICE_OK)
        {
            ToastUtil.showToastLong(getString(R.string.successfully_deleted1));
        }
        else if (id == UiEventEntry.READ_DEL_DEVICE_ERROR)
        {
            ToastUtil.showToastLong(getString(R.string.Delet_eerror));
        }
        else if (id == UiEventEntry.READ_DATA)
        {
            String result = (String) args[0];
            if (TextUtils.isEmpty(result))
            {
                return;
            }
            setData(result);
        }
    }

    public void updateData()
    {
        this.deviceList.clear();
        adapter.updateData(deviceList);
        ServiceUtils.sendData(ConfigParams.ReadDeviceList);
    }


    public void setData(String data)
    {
        if (data.contains(ConfigParams.DeviceID))
        {
            String res = (data.replaceAll(ConfigParams.DeviceID, getString(R.string.equipment)).trim());
            deviceList.add(res);
        }
        adapter.updateData(deviceList);
    }
}
