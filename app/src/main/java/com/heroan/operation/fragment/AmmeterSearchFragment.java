package com.heroan.operation.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.ServiceUtils;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.util.Log;

/**
 * Created by linxi on 2018/4/19.
 */

public class AmmeterSearchFragment extends BaseFragment implements View.OnClickListener,EventNotifyHelper.NotificationCenterDelegate
{
    private static final String TAG = AmmeterSearchFragment.class.getSimpleName();

    private TextView soilText;
    Context context;

    private StringBuffer currentSB = new StringBuffer();
    private String string = "";

    private String Positive_total_effort ;
    private String AC_voltage ;
    private String BC_voltage ;
    private String CC_voltage ;
    private String AC_current ;
    private String BC_current ;
    private String CC_current ;

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public int getLayoutView()
    {
        return R.layout.wq_search_fragment;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);

    }
    @Override
    public void initComponentViews(View view)
    {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.NOTIFY_BUNDLE);


        soilText = (TextView) view.findViewById(R.id.wq_search);

        setData();
    }

    @Override
    public void initData()
    {
    }

    @Override
    public void setListener()
    {

    }

    @Override
    public void didReceivedNotification(int id, Object... args)
    {

        if (id == UiEventEntry.NOTIFY_BUNDLE)
        {
            setData();
        }
        else if (id == UiEventEntry.READ_DATA)
        {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content))
            {
                return;
            }
            readData(result, content);
        }
    }

    private void readData(String result, String content)
    {

        String data = "";

        if (result.contains(ConfigParams.ZXZYG))
        {
            currentSB.insert(currentSB.indexOf(Positive_total_effort) + Positive_total_effort.length(), result.replaceAll(ConfigParams.ZXZYG, "").trim());
        }
        else if (result.contains(ConfigParams.JL_VOLTAGE_A))
        {
            currentSB.insert(currentSB.indexOf(AC_voltage) + AC_voltage.length(), result.replaceAll(ConfigParams.JL_VOLTAGE_A, "").trim());
        }
        else if (result.contains(ConfigParams.JL_VOLTAGE_B))
        {
            currentSB.insert(currentSB.indexOf(BC_voltage) + BC_voltage.length(), result.replaceAll(ConfigParams.JL_VOLTAGE_B, "").trim());
        }
        else if (result.contains(ConfigParams.JL_VOLTAGE_C))
        {
            currentSB.insert(currentSB.indexOf(CC_voltage) + CC_voltage.length(), result.replaceAll(ConfigParams.JL_VOLTAGE_C, "").trim());
        }
        else if (result.contains(ConfigParams.JL_I_A))
        {
            currentSB.insert(currentSB.indexOf(AC_current) + AC_current.length(), result.replaceAll(ConfigParams.JL_I_A, "").trim());
        }
        else if (result.contains(ConfigParams.JL_I_B))
        {
            currentSB.insert(currentSB.indexOf(BC_current) + BC_current.length(), result.replaceAll(ConfigParams.JL_I_B, "").trim());
        }
        else if (result.contains(ConfigParams.JL_I_C))
        {
            currentSB.insert(currentSB.indexOf(CC_current) + CC_current.length(), result.replaceAll(ConfigParams.JL_I_C, "").trim());
        }

        soilText.setText(currentSB.toString());
    }


    public void setData()
    {

        Positive_total_effort = getString(R.string.Positive_total_effort);
        AC_voltage = getString(R.string.AC_voltage);
        BC_voltage = getString(R.string.BC_voltage);
        CC_voltage = getString(R.string.CC_voltage);
        AC_current = getString(R.string.AC_current);
        BC_current = getString(R.string.BC_current);
        CC_current = getString(R.string.CC_current);

        String content = ConfigParams.Read_DIANBIAO_data;
        SocketUtil.getSocketUtil().sendContent(content);
        currentSB.delete(0, currentSB.length());

        currentSB.append(Positive_total_effort);
        currentSB.append("\n");
        currentSB.append(AC_voltage);
        currentSB.append("\n");
        currentSB.append(BC_voltage);
        currentSB.append("\n");
        currentSB.append(CC_voltage);
        currentSB.append("\n");
        currentSB.append(AC_current);
        currentSB.append("\n");
        currentSB.append(BC_current);
        currentSB.append("\n");
        currentSB.append(CC_current);
        currentSB.append("\n");

        if (soilText != null && currentSB.length() > 0)
        {
            soilText.setText(currentSB.toString());
        }
    }
}
