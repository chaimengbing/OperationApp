package com.heroan.operation.adapter;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends BaseAdapter
{

    private static final int COUNT = 12;
    private static final int TYPE_GPRS = 0;
    private static final int TYPE_SMS = 1;
    private static final int TYPE_BEIDOU = 2;
    private static final int TYPE_CENTER = 3;
    protected static final String TAG = ChannelAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    private List<String> centerList = new ArrayList<String>();
    private List<String> dataList = new ArrayList<String>();
    private Context context;
    private ListView listView;


    public ChannelAdapter(Context context, ListView listView)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listView = listView;
        initData();
    }

    private void initData()
    {
        centerList.add("中心站1");
        centerList.add("中心站2");
        centerList.add("中心站3");
        centerList.add("中心站4");
    }

    public void setData(List<String> dataList)
    {
        if (dataList != null && dataList.size() > 0)
        {
            Log.i(TAG, "setData::dataList.size():" + dataList.size());
            this.dataList.clear();
            this.dataList.addAll(dataList);
        }
    }

    @Override
    public int getCount()
    {
        return COUNT;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        int type = getItemViewType(position);
        if (convertView == null)
        {
            holder = new ViewHolder();
            switch (type)
            {
                case TYPE_GPRS:
                    convertView = inflater.inflate(R.layout.item_gprs, null);
                    holder.gprsButton = (Button) convertView.findViewById(R.id.gprs_button);
                    holder.gprsTextView = (TextView) convertView.findViewById(R.id.gprs_textview);
                    holder.ipEditText = (EditText) convertView.findViewById(R.id.ip_edittext);
                    holder.portEditText = (EditText) convertView.findViewById(R.id.port_edittext);
                    holder.gprsTextView.setText(centerList.get(position % 4));

                    break;
                case TYPE_SMS:
                    convertView = inflater.inflate(R.layout.item_sms, null);
                    holder.smsButton = (Button) convertView.findViewById(R.id.sms_button);
                    holder.smsTextView = (TextView) convertView.findViewById(R.id.sms_textview);
                    holder.smsEditText = (EditText) convertView.findViewById(R.id.sms_edittext);
                    holder.smsTextView.setText(centerList.get(position % 4));

                    break;
                case TYPE_BEIDOU:
                    convertView = inflater.inflate(R.layout.item_beidou, null);
                    holder.beiButton = (Button) convertView.findViewById(R.id.bei_button);
                    holder.beiTextView = (TextView) convertView.findViewById(R.id.bei_textview);
                    holder.beiEditText = (EditText) convertView.findViewById(R.id.bei_edittext);
                    holder.beiTextView.setText(centerList.get(position % 4));

                    break;
                case TYPE_CENTER:
                    convertView = inflater.inflate(R.layout.item_center, null);
                    holder.centerButton = (Button) convertView.findViewById(R.id.center_button);
                    holder.centerTextView = (TextView) convertView.findViewById(R.id.center_textview);
                    holder.centerEditText = (EditText) convertView.findViewById(R.id.center_edittext);
                    holder.centerTextView.setText(centerList.get(position % 4));
                    break;

                default:
                    break;
            }
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
//        Log.i(TAG, "position:" + position);

        switch (type)
        {
            case TYPE_GPRS:
                holder.pos = position;

//                holder.ipEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
//                {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus)
//                    {
//                        if (!hasFocus)
//                        {
//                            EditText etxt = (EditText) v;
//                            holder.ipEditText.setText(etxt.getText().toString());
//                        }
//                    }
//                });
//
//                holder.portEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
//                {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus)
//                    {
//                        if (!hasFocus)
//                        {
//                            EditText etxt = (EditText) v;
//                            holder.portEditText.setText(etxt.getText().toString());
//                        }
//                    }
//                });


                holder.ipEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.gprsButton.setOnClickListener(new OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        String ip = holder.ipEditText.getText().toString().trim();
                        String port = holder.portEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(ip))
                        {
                            ToastUtil.showToastLong(context.getString(R.string.The_IP_address_cannot_be_empty));
                            return;
                        }
                        // portValuesEdit[holder.pos]
                        if (TextUtils.isEmpty(port))
                        {
                            ToastUtil.showToastLong(context.getString(R.string.The_port_number_cannot_be_empty));
                            return;
                        }
                        Log.i(TAG, "ip:" + ip + ",port" + port);

                        int channel = holder.pos;
                        // 设置状态参数
                        String content = ConfigParams.SetIP + (channel + 1) + " " + ServiceUtils.getRegxIp(ip) + ConfigParams.setPort + ServiceUtils.getStr(port + "", 5);
                        String content1 = ConfigParams.SetIP + (channel + 1) + " " + ip + ConfigParams.setPort + port;
                        SocketUtil.getSocketUtil().sendContent(content);
                        if (dataList.size() > 0)
                        {
                            dataList.set(holder.pos, content1);
                        }

                    }
                });

                if (dataList.size() > 0)
                {
                    // 读取参数，更新适配器
                    String[] ipArray = null;
                    if (dataList.size() > 0 && position == 0)
                    {
                        ipArray = dataList.get(position).split(ConfigParams.setPort.trim());
                    }
                    else if (dataList.size() > 1 && position == 1)
                    {
                        ipArray = dataList.get(position).split(ConfigParams.setPort.trim());
                    }
                    else if (dataList.size() > 2 && position == 2)
                    {
                        ipArray = dataList.get(position).split(ConfigParams.setPort.trim());
                    }
                    else if (dataList.size() > 3 && position == 3)
                    {
                        ipArray = dataList.get(position).split(ConfigParams.setPort.trim());
                    }
                    if (ipArray != null)
                    {
                        holder.ipEditText.setText(ServiceUtils.getRemoteIp(ipArray[0].replaceAll(ConfigParams.SetIP + (position + 1), "").trim()));
                        if (ipArray.length > 1)
                        {
                            holder.portEditText.setText(ipArray[1].trim());
                        }
                    }
                }
                break;
            case TYPE_SMS:
                holder.pos = position;

//                holder.smsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
//                {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus)
//                    {
//                        if (!hasFocus)
//                        {
//                            EditText etxt = (EditText) v;
//                            holder.smsEditText.setText(etxt.getText().toString());
//                        }
//                    }
//                });

                holder.smsButton.setOnClickListener(new OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        String sms = holder.smsEditText.getText().toString().trim();
                        if (TextUtils.isEmpty(sms))
                        {
                            ToastUtil.showToastLong(context.getString(R.string.SMS_number_cannot_be_empty));
                            return;
                        }
                        Log.i(TAG, sms);
                        if (sms.length() != 11)
                        {
                            ToastUtil.showToastLong(context.getString(R.string.correct_cell_phone_number));
                            return;
                        }
                        Log.i(TAG, sms);
                        String content = "";
                        if (holder.pos == 4)
                        {
                            content = ConfigParams.SetSMS + "1" + " " + sms;
                        }
                        else if (holder.pos == 5)
                        {
                            content = ConfigParams.SetSMS + "2" + " " + sms;

                        }
                        else if (holder.pos == 6)
                        {
                            content = ConfigParams.SetSMS + "3" + " " + sms;

                        }
                        else if (holder.pos == 7)
                        {
                            content = ConfigParams.SetSMS + "4" + " " + sms;
                        }
                        dataList.set(holder.pos, content);
                        SocketUtil.getSocketUtil().sendContent(content);
                    }
                });
                if (dataList.size() > 0)
                {
                    if (position == 4 && dataList.size() > 4)
                    {
                        holder.smsEditText.setText(dataList.get(position).replaceAll(ConfigParams.SetSMS + "1", "").trim());
                    }
                    else if (position == 5 && dataList.size() > 5)
                    {
                        holder.smsEditText.setText(dataList.get(position).replaceAll(ConfigParams.SetSMS + "2", "").trim());

                    }
                    else if (position == 6 && dataList.size() > 6)
                    {
                        holder.smsEditText.setText(dataList.get(position).replaceAll(ConfigParams.SetSMS + "3", "").trim());

                    }
                    else if (position == 7 && dataList.size() > 7)
                    {
                        holder.smsEditText.setText(dataList.get(position).replaceAll(ConfigParams.SetSMS + "4", "").trim());
                    }
                }
                break;
            case TYPE_BEIDOU:
                holder.pos = position;

//                holder.beiEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
//                {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus)
//                    {
//                        if (!hasFocus)
//                        {
//                            EditText etxt = (EditText) v;
//                            holder.beiEditText.setText(etxt.getText().toString());
//                        }
//                    }
//                });

                holder.beiButton.setOnClickListener(new OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        String bei = holder.beiEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(bei))
                        {
                            ToastUtil.showToastLong(context.getString(R.string.compass_number_cannot_be_empty));
                            return;
                        }

                        String content = "";
                        if (holder.pos == 8)
                        {
                            content = ConfigParams.SetBeiDou + "1" + " " + ServiceUtils.getStr(bei, 6);
                        }
                        else if (holder.pos == 9)
                        {
                            content = ConfigParams.SetBeiDou + "2" + " " + ServiceUtils.getStr(bei, 6);
                        }
                        else if (holder.pos == 10)
                        {
                            content = ConfigParams.SetBeiDou + "3" + " " + ServiceUtils.getStr(bei, 6);
                        }
                        else if (holder.pos == 11)
                        {
                            content = ConfigParams.SetBeiDou + "4" + " " + ServiceUtils.getStr(bei, 6);
                        }
                        SocketUtil.getSocketUtil().sendContent(content);
                        dataList.set(holder.pos, content);
                    }
                });

                if (dataList.size() > 0)
                {


                    if (position == 8 && dataList.size() > 8)
                    {
                        String beidou = dataList.get(position);
                        if (!TextUtils.isEmpty(beidou) && beidou.contains(ConfigParams.SetBeiDou))
                        {
                            holder.beiEditText.setText(beidou.replaceAll(ConfigParams.SetBeiDou + "1", "").trim());
                        }
                    }
                    else if (position == 9 && dataList.size() > 9)
                    {
                        String beidou = dataList.get(position);
                        if (!TextUtils.isEmpty(beidou) && beidou.contains(ConfigParams.SetBeiDou))
                        {
                            holder.beiEditText.setText(beidou.replaceAll(ConfigParams.SetBeiDou + "2", "").trim());
                        }

                    }
                    else if (position == 10 && dataList.size() > 10)
                    {
                        String beidou = dataList.get(position);
                        if (!TextUtils.isEmpty(beidou) && beidou.contains(ConfigParams.SetBeiDou))
                        {
                            holder.beiEditText.setText(beidou.replaceAll(ConfigParams.SetBeiDou + "3", "").trim());
                        }

                    }
                    else if (position == 11 && dataList.size() > 11)
                    {
                        String beidou = dataList.get(position);
                        if (!TextUtils.isEmpty(beidou) && beidou.contains(ConfigParams.SetBeiDou))
                        {
                            holder.beiEditText.setText(beidou.replaceAll(ConfigParams.SetBeiDou + "4", "").trim());
                        }
                    }
                }
                break;
            case TYPE_CENTER:
                holder.pos = position;

//                holder.smsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
//                {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus)
//                    {
//                        if (!hasFocus)
//                        {
//                            EditText etxt = (EditText) v;
//                            holder.smsEditText.setText(etxt.getText().toString());
//                        }
//                    }
//                });

                holder.centerButton.setOnClickListener(new OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        String center = holder.centerEditText.getText().toString().trim();
                        if (TextUtils.isEmpty(center))
                        {
                            ToastUtil.showToastLong(context.getString(R.string.Address_cannot_be_empty));
                            return;
                        }
                        Log.i(TAG, center);
                        if (center.length() < 0 && center.length() > 255)
                        {
                            ToastUtil.showToastLong(context.getString(R.string.Address_range));
                            return;
                        }
                        Log.i(TAG, center);
                        String content = "";
                        if (holder.pos == 12)
                        {
                            content = ConfigParams.Setcenternumber + "1" + " " + ServiceUtils.getStr(center,3);
                        }
                        else if (holder.pos == 13)
                        {
                            content = ConfigParams.Setcenternumber + "2" + " " + ServiceUtils.getStr(center,3);

                        }
                        else if (holder.pos == 14)
                        {
                            content = ConfigParams.Setcenternumber + "3" + " " + ServiceUtils.getStr(center,3);

                        }
                        else if (holder.pos == 15)
                        {
                            content = ConfigParams.Setcenternumber + "4" + " " + ServiceUtils.getStr(center,3);
                        }
                        dataList.set(holder.pos, content);
                        SocketUtil.getSocketUtil().sendContent(content);
                    }
                });
                if (dataList.size() > 0)
                {
                    if (position == 12 && dataList.size() > 12)
                    {
                        String center = dataList.get(position);
                        if (!TextUtils.isEmpty(center) && center.contains(ConfigParams.Setcenternumber))
                        {
                            holder.centerEditText.setText(dataList.get(position).replaceAll(ConfigParams.Setcenternumber + "1", "").trim());
                        }
                    }
                    else if (position == 13 && dataList.size() > 13)
                    {
                        String center = dataList.get(position);
                        if (!TextUtils.isEmpty(center) && center.contains(ConfigParams.Setcenternumber))
                        {
                            holder.centerEditText.setText(dataList.get(position).replaceAll(ConfigParams.Setcenternumber + "2", "").trim());
                        }

                    }
                    else if (position == 14 && dataList.size() > 14)
                    {
                        String center = dataList.get(position);
                        if (!TextUtils.isEmpty(center) && center.contains(ConfigParams.Setcenternumber))
                        {
                            holder.centerEditText.setText(dataList.get(position).replaceAll(ConfigParams.Setcenternumber + "3", "").trim());
                        }

                    }
                    else if (position == 15 && dataList.size() > 15)
                    {
                        String center = dataList.get(position);
                        if (!TextUtils.isEmpty(center) && center.contains(ConfigParams.Setcenternumber))
                        {
                            holder.centerEditText.setText(dataList.get(position).replaceAll(ConfigParams.Setcenternumber + "4", "").trim());
                        }
                    }
                }
                break;
            default:
                break;
        }

        return convertView;
    }


    @Override
    public int getViewTypeCount()
    {
        return 3;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position < 4)
            return TYPE_GPRS;
        else if (position > 3 && position < 8)
            return TYPE_SMS;
        else if (position > 8 && position < 12)
            return TYPE_BEIDOU;
        else if (position < 16)
            return TYPE_CENTER;
        else
            return TYPE_GPRS;
    }

    class ViewHolder
    {

        TextView gprsTextView;
        EditText ipEditText, portEditText;
        TextView smsTextView;
        EditText smsEditText;
        TextView beiTextView;
        EditText beiEditText;
        TextView centerTextView;
        EditText centerEditText;
        Button gprsButton;
        Button smsButton;
        Button beiButton;
        Button centerButton;


        int pos;

    }

}
