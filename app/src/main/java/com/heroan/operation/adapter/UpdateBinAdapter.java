package com.heroan.operation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UpdateBinAdapter extends BaseAdapter
{

    protected static final String TAG = UpdateBinAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    private List<File> dataList = new ArrayList<>();
    private Context context;


    public UpdateBinAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        initData();
    }

    private void initData()
    {
        File f = new File(ConfigParams.BinPath);
        File[] files = f.listFiles();// 列出所有文件

        for (File file : files)
        {
            if (file.getName().endsWith(".bin"))
            {
                dataList.add(file);
            }
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
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTextView.setText(dataList.get(position).getName());
        return convertView;
    }



    class ViewHolder
    {
        TextView nameTextView;
    }

}
