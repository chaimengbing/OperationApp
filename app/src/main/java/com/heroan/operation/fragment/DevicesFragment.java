package com.heroan.operation.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.DevicesAdapter;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vcontrol on 2016/11/23.
 */

public class DevicesFragment extends BaseFragment implements View.OnClickListener, EventNotifyHelper.NotificationCenterDelegate
{

    private ListView deviceListView;
    private DevicesAdapter adapter;
    private List<String> deviceList = new ArrayList<>();

    @Override
    public int getLayoutView()
    {
        return R.layout.fragment_device_list;
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
    public void initComponentViews(View view)
    {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DEL_DEVICE_ERROR);
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DEL_DEVICE_OK);

        deviceListView = (ListView) view.findViewById(R.id.device_listview);
        initView(view);
    }

    private void initView(final View view)
    {
    }

    @Override
    public void initData()
    {
        adapter = new DevicesAdapter(getActivity());
        deviceListView.setAdapter(adapter);
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadDeviceList);
    }

    @Override
    public void setListener()
    {
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
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadDeviceList);
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
