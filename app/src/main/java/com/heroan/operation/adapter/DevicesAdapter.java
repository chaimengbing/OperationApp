package com.heroan.operation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.SocketUtil;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.util.Log;

public class DevicesAdapter extends BaseAdapter
{

    protected static final String TAG = DevicesAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    private List<String> dataList = new ArrayList<>();
    private Context context;
    ViewHolder holder;


    public DevicesAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void updateData(List<String> list)
    {
        if (list != null)
        {
            this.dataList.clear();
            this.dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position)
    {
        if (dataList != null)
        {
            dataList.remove(position);
            this.notifyDataSetChanged();
        }
    }


    @Override
    public int getCount()
    {
        if (dataList == null || dataList.size() == 0)
        {
            return 0;
        }
        else
        {
            return dataList.size();
        }
    }

    @Override
    public Object getItem(int position)
    {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_device, null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.name);
            holder.delButton = (Button) convertView.findViewById(R.id.del_button);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTextView.setText(dataList.get(position));
        holder.delButton.setOnClickListener(new DelButtonListener(position));
        return convertView;
    }


    class ViewHolder
    {
        TextView nameTextView;
        Button delButton;
    }

    class DelButtonListener implements View.OnClickListener
    {
        private int position;

        DelButtonListener(int pos)
        {
            position = pos;
        }

        @Override
        public void onClick(View v)
        {
            int vid = v.getId();
            if (vid == holder.delButton.getId())
            {
                String deviceId = dataList.get(position).replaceAll("设备", "").trim();
                deviceId = deviceId.substring(2);
                Log.i(TAG, "deviceid:" + deviceId);
                SocketUtil.getSocketUtil().sendContent(ConfigParams.DelDeviceID + deviceId);
                removeItem(position);
            }

        }
    }

}
