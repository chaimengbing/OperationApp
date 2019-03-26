package com.heroan.operation.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.view.ViewGroup;

import com.heroan.operation.view.MessageItemView;

import zuo.biao.library.base.BaseAdapter;

public class MessageAdapter extends BaseAdapter<ContentValues, MessageItemView> {
    public MessageAdapter(Activity context) {
        super(context);
    }

    @Override
    public MessageItemView createView(int viewType, ViewGroup parent) {
        return new MessageItemView(context, parent);
    }


}
