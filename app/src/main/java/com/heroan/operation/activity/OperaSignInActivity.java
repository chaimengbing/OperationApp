package com.heroan.operation.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heroan.operation.R;
import com.heroan.operation.utils.GpsUtils;

import java.text.DecimalFormat;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.CutPictureActivity;
import zuo.biao.library.ui.SelectPictureActivity;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.StringUtil;

public class OperaSignInActivity extends BaseActivity implements View.OnClickListener, GpsUtils.OnLocationResultListener {
    private final static String TAG = OperaSignInActivity.class.getName();

    private static final int REQUEST_TO_SELECT_PICTURE = 20;
    private static final int REQUEST_TO_CUT_PICTURE = 21;

    private static final int SEL_ENV = 1;
    private static final int SEL_BEFORE_OPERA = 2;
    private static final int SEL_AFTER_OPERA = 3;

    private TextView titleRight;
    private TextView title;
    private ImageView backImageView;

    private ImageView updateImage;
    private TextView signInText;
    private ImageView envImage, beforeOperaImage, afterOperaImage;
    private TextView longitudeText, latitudeText, altitudeText;


    private int currentSelImage = SEL_ENV;
    private String picturePath = "";
    private DecimalFormat decimalFormat = new DecimalFormat("###.000000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opera_sign_in);
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
        updateImage = findViewById(R.id.update_operation_imageview);
        signInText = findViewById(R.id.sign_in_text);
        envImage = findViewById(R.id.env_image);
        beforeOperaImage = findViewById(R.id.before_opera_image);
        afterOperaImage = findViewById(R.id.after_opera_image);
        longitudeText = findViewById(R.id.longitude_text);
        latitudeText = findViewById(R.id.latitude_text);
        altitudeText = findViewById(R.id.altitude_text);
    }

    @Override
    public void initData() {
        title.setText(getString(R.string.operation_sign_in));
        titleRight.setVisibility(View.GONE);

        GpsUtils.getInstance(getApplicationContext()).getLngAndLat(this);

    }

    @Override
    public void initEvent() {
        backImageView.setOnClickListener(this);
        updateImage.setOnClickListener(this);
        signInText.setOnClickListener(this);
        envImage.setOnClickListener(this);
        beforeOperaImage.setOnClickListener(this);
        afterOperaImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.update_operation_imageview:
                //刷新工单列表
                break;
            case R.id.sign_in_text:
                //运维任务选择
                break;
            case R.id.env_image:
                currentSelImage = SEL_ENV;
                selectPicture();
                break;
            case R.id.before_opera_image:
                currentSelImage = SEL_BEFORE_OPERA;
                selectPicture();
                break;
            case R.id.after_opera_image:
                currentSelImage = SEL_AFTER_OPERA;
                selectPicture();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 选择图片
     */
    private void selectPicture() {
        toActivity(SelectPictureActivity.createIntent(context), REQUEST_TO_SELECT_PICTURE, false);
    }

    /**
     * 显示图片
     *
     * @param path
     */
    private void setPicture(String path) {
        if (StringUtil.isFilePath(path) == false) {
            Log.e(TAG, "setPicture  StringUtil.isFilePath(path) == false >> showShortToast(找不到图片);return;");
            showShortToast("找不到图片");
            return;
        }
        this.picturePath = path;

        if (currentSelImage == SEL_ENV) {
            Glide.with(context).load(path).into(envImage);
        } else if (currentSelImage == SEL_BEFORE_OPERA) {
            Glide.with(context).load(path).into(beforeOperaImage);
        } else if (currentSelImage == SEL_AFTER_OPERA) {
            Glide.with(context).load(path).into(afterOperaImage);
        }
    }


    /**
     * 裁剪图片
     *
     * @param path
     */
    private void cutPicture(String path) {
        if (StringUtil.isFilePath(path) == false) {
            Log.e(TAG, "cutPicture  StringUtil.isFilePath(path) == false >> showShortToast(找不到图片);return;");
            showShortToast("找不到图片");
            return;
        }
        this.picturePath = path;

        toActivity(CutPictureActivity.createIntent(context, path
                , DataKeeper.imagePath, "photo" + System.currentTimeMillis(), 200)
                , REQUEST_TO_CUT_PICTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_TO_SELECT_PICTURE:
                if (data != null) {
                    cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH));
                }
                break;
            case REQUEST_TO_CUT_PICTURE:
                if (data != null) {
                    setPicture(data.getStringExtra(CutPictureActivity.RESULT_PICTURE_PATH));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLocationResult(Location location) {
        if (location != null) {
            longitudeText.setText(decimalFormat.format(location.getLongitude()));
            latitudeText.setText(decimalFormat.format(location.getLatitude()));
            altitudeText.setText(decimalFormat.format(location.getAltitude()));
        }
    }

    @Override
    public void OnLocationChange(Location location) {

    }
}
