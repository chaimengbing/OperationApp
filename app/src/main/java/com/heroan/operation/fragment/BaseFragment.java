package com.heroan.operation.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.lang.reflect.Field;

import zuo.biao.library.util.Log;

/**
 * 说明: 所有普通碎片类的基类
 * 
 */

public abstract class BaseFragment extends Fragment
{
    private static final String TAG = BaseFragment.class.getSimpleName();
    private View layoutView;

    public BaseFragment()
    {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        try
        {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        }
        catch (NoSuchFieldException e)
        {
            Log.e(TAG, e.toString());
        }
        catch (IllegalAccessException e)
        {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    public View getRootView()
    {
        return layoutView;
    }



    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        layoutView = inflater.inflate(getLayoutView(), null);
        initComponentViews(layoutView);
        initData();
        setListener();
        return layoutView;
    }

    public abstract int getLayoutView();

    public abstract void initComponentViews(View view);

    public abstract void initData();

    public abstract void setListener();


}
