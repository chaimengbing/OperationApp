package com.heroan.operation.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.model.RtuItem;

import zuo.biao.library.base.BaseView;

public class RtuItemView extends BaseView<RtuItem> {

    private TextView addrText;
    private TextView idText;

    public RtuItemView(Activity context, ViewGroup parent) {
        super(context, R.layout.item_rtu_view, parent);
    }

    @Override
    public View createView() {
        addrText = findView(R.id.addr_text);
        idText = findView(R.id.id_text);
        return super.createView();
    }

    @Override
    public void bindView(RtuItem data_) {
        super.bindView(data_);
        addrText.setText(data.getStation_addr());
        idText.setText(data.getStation_id());
    }
}
