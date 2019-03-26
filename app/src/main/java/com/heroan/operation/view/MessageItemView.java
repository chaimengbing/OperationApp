package com.heroan.operation.view;

import android.app.Activity;
import android.content.ContentValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.manager.SQLHelper;

import zuo.biao.library.base.BaseView;

public class MessageItemView extends BaseView<ContentValues> {

    private TextView titleText;
    private TextView timeTextview;


    public MessageItemView(Activity context, ViewGroup parent) {
        super(context, R.layout.item_message, parent);
    }

    @Override
    public View createView() {
        titleText = findView(R.id.item_title);
        timeTextview = findView(R.id.time_textview);
        return super.createView();
    }

    @Override
    public void bindView(ContentValues data_) {
        super.bindView(data_);
        titleText.setText(data.get(SQLHelper.COLUMN_TITLE).toString());
        timeTextview.setText(data.get(SQLHelper.COLUMN_TIME).toString());
    }

}
