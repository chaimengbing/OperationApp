package com.heroan.operation.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.manager.SQLHelper;

import zuo.biao.library.base.BaseActivity;

/**
 * Created by linxi on 2018/2/5.
 */

public class DisplayMessageActivity extends BaseActivity implements View.OnClickListener {


    public TextView title;
    public TextView messageTitle;
    public TextView time;
    public TextView atv;

    private ContentValues contentValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_message);
        initView();
        initData();
        initEvent();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void initView() {
        atv = findViewById(R.id.display_message);
        messageTitle = findViewById(R.id.title_display);
        time = findViewById(R.id.time_display);
        findView(R.id.title_right).setVisibility(View.GONE);
        title = findView(R.id.title);


    }

    @Override
    public void initData() {
        contentValues = getIntent().getParcelableExtra("contentValues");

        title.setText(R.string.Message_display);
        if (contentValues != null) {
            messageTitle.setText(contentValues.get(SQLHelper.COLUMN_TITLE).toString());
            time.setText(contentValues.get(SQLHelper.COLUMN_TIME).toString());
            atv.setText(contentValues.get(SQLHelper.COLUMN_CONTENT).toString());
        }
    }

    @Override
    public void initEvent() {
        findViewById(R.id.title_back).setOnClickListener(this);
    }
}
