package com.heroan.operation.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.heroan.operation.model.RtuItem;
import com.heroan.operation.view.RtuItemView;

import zuo.biao.library.base.BaseAdapter;

public class RtuListAdapter extends BaseAdapter<RtuItem, RtuItemView> {
    public RtuListAdapter(Activity context) {
        super(context);
    }

    @Override
    public RtuItemView createView(int viewType, ViewGroup parent) {
        return new RtuItemView(context, parent);
    }


}
