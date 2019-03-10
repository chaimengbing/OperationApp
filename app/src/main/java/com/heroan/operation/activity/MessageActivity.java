package com.heroan.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heroan.operation.R;

import zuo.biao.library.base.BaseActivity;

public class MessageActivity extends BaseActivity implements View.OnClickListener {


    private TextView titleRight;
    private TextView title;
    private ImageView backImageView;
    private TextView leftText, rightText, centerText;
    private View leftView, rightView, centerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {

        backImageView = findViewById(R.id.title_back);
        titleRight = findViewById(R.id.title_right);
        title = findViewById(R.id.title);
        centerText = findView(R.id.center_text);
        leftView = findView(R.id.left_divider);
        rightView = findView(R.id.right_divider);
        leftText = findView(R.id.left_text);
        rightText = findView(R.id.right_text);
        centerView = findView(R.id.center_divider);
    }

    @Override
    public void initData() {
        leftText.setSelected(true);
        rightText.setSelected(false);
        centerText.setSelected(false);

        title.setText(getString(R.string.message));
        titleRight.setVisibility(View.GONE);

    }

    @Override
    public void initEvent() {
        backImageView.setOnClickListener(this);
        centerText.setOnClickListener(this);
        leftText.setOnClickListener(this);
        rightText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.left_text:
                selectTag(leftText, centerText, rightText);
                selectTagView(leftView, centerView, rightView);
                break;
            case R.id.right_text:
                selectTag(rightText, centerText, leftText);
                selectTagView(rightView, centerView, leftView);
                break;
            case R.id.center_text:
                selectTag(centerText, leftText, rightText);
                selectTagView(centerView, leftView, rightView);
                break;
            default:
                break;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
    }
}
