package com.heroan.operation.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.adapter.MessageAdapter;
import com.heroan.operation.manager.SQLHelper;
import com.heroan.operation.view.MessageItemView;

import java.util.List;

import zuo.biao.library.base.BaseRecyclerActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.SettingUtil;

public class MessageActivity extends BaseRecyclerActivity<ContentValues, MessageItemView,
        MessageAdapter> implements View.OnClickListener {


    private TextView titleRight;
    private TextView title;
    private ImageView backImageView;
    private TextView leftText, rightText, centerText;
    private View leftView, rightView, centerView;

    private TextView noData;

    private SQLHelper sqlHelper;
    private String currentType = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initData();
        initEvent();

        onRefresh();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        super.initView();

        backImageView = findViewById(R.id.title_back);
        titleRight = findViewById(R.id.title_right);
        title = findViewById(R.id.title);
        centerText = findView(R.id.center_text);
        leftView = findView(R.id.left_divider);
        rightView = findView(R.id.right_divider);
        leftText = findView(R.id.left_text);
        rightText = findView(R.id.right_text);
        noData = findView(R.id.noData);
        centerView = findView(R.id.center_divider);

    }

    @Override
    public void setList(final List<ContentValues> list) {
        setList(new AdapterCallBack<MessageAdapter>() {
            @Override
            public MessageAdapter createAdapter() {
                return new MessageAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                if (list != null && list.size() > 0) {
                    adapter.refresh(list);
                    rvBaseRecycler.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                } else {
                    noData.setVisibility(View.VISIBLE);
                    rvBaseRecycler.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        leftText.setSelected(true);
        rightText.setSelected(false);
        centerText.setSelected(false);

        title.setText(getString(R.string.message));
        titleRight.setVisibility(View.GONE);

        sqlHelper = new SQLHelper(context);

//        initTestMessage();

    }

    private void initTestMessage() {
        if (!"1".equals(SettingUtil.getSaveValue("isHaveMessage"))) {
            SettingUtil.setSaveValue("isHaveMessage", "1");
            for (int i = 0; i < 10; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLHelper.COLUMN_TITLE, "西湖0" + i + "，电池电压异常");
                contentValues.put(SQLHelper.COLUMN_CONTENT, "西湖0" + i + "，电池电压异常");
                contentValues.put(SQLHelper.COLUMN_TYPE, 1);
                contentValues.put(SQLHelper.COLUMN_TIME, "2019-03-26 14:2" + i);
                sqlHelper.insert(contentValues);
            }
        }
    }

    @Override
    public void getListAsync(int page) {
        showProgressDialog(R.string.loading);
        List<ContentValues> list = sqlHelper.getList(SQLHelper.COLUMN_TYPE, "1");
        onLoadSucceed(page, list);
    }


    @Override
    public void initEvent() {
        super.initEvent();
        backImageView.setOnClickListener(this);
        centerText.setOnClickListener(this);
        leftText.setOnClickListener(this);
        rightText.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        List<ContentValues> list = null;
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.left_text:
                selectTag(leftText, centerText, rightText);
                selectTagView(leftView, centerView, rightView);
                currentType = "1";
                list = sqlHelper.getList(SQLHelper.COLUMN_TYPE, "1");
                onLoadSucceed(0, list);
                break;
            case R.id.right_text:
                selectTag(rightText, centerText, leftText);
                selectTagView(rightView, centerView, leftView);
                currentType = "3";
                list = sqlHelper.getList(SQLHelper.COLUMN_TYPE, "3");
                onLoadSucceed(0, list);
                break;
            case R.id.center_text:
                selectTag(centerText, leftText, rightText);
                selectTagView(centerView, leftView, rightView);
                currentType = "2";
                list = sqlHelper.getList(SQLHelper.COLUMN_TYPE, "2");
                onLoadSucceed(0, list);
                break;
            default:
                break;
        }

        if (list != null) {
            onLoadSucceed(0, list);
        } else {
            noData.setVisibility(View.VISIBLE);
            rvBaseRecycler.setVisibility(View.GONE);
        }
    }

    private void selectTag(TextView selectText, TextView text1, TextView text2) {
        selectText.setSelected(true);
        text1.setSelected(false);
        text2.setSelected(false);
    }

    private void selectTagView(View selectView, View view1, View view2) {
        selectView.setVisibility(View.VISIBLE);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final ContentValues contentValues = adapter.getItem(position);
        new AlertDialog(context, "提示", "确定删除么？", true, 0, new AlertDialog.OnDialogButtonClickListener() {
            @Override
            public void onDialogButtonClick(int requestCode, boolean isPositive) {
                sqlHelper.delete(SQLHelper.COLUMN_ID, contentValues.get(SQLHelper.COLUMN_ID).toString());
                List<ContentValues> list = sqlHelper.getList(SQLHelper.COLUMN_TYPE, currentType);
                adapter.refresh(list);
            }
        }).show();
        return super.onItemLongClick(parent, view, position, id);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        ContentValues contentValues = adapter.getItem(position);
        Intent intent = new Intent(context, DisplayMessageActivity.class);
        intent.putExtra("contentValues", contentValues);
        toActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
    }
}
