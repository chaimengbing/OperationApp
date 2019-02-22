package com.heroan.operation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.heroan.operation.R;
import com.heroan.operation.utils.ConfigParams;
import com.heroan.operation.utils.EventNotifyHelper;
import com.heroan.operation.utils.SocketUtil;
import com.heroan.operation.utils.ToastUtil;
import com.heroan.operation.utils.UiEventEntry;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by linxi on 2017/7/5.
 */

public class YUNFragment extends BaseFragment implements View.OnClickListener,
        EventNotifyHelper.NotificationCenterDelegate {

    private TextView yunStatus;
    private Button startUp;
    private LinearLayout layoutYun;
    private Button leftArrow;
    private Button rightArrow;
    private RadioGroup mainPicture;
    private RadioGroup auxiliaryPicture;

    private LinearLayout mainLayout;
    private TextView mainStatus;
    private Button setMainButton;
    private Button removeMainButton;
    private Button regulateMainSurvey;
    private Button addMinorSurvey1;

    private TextView auxiliaryText;

    private LinearLayout auxiliaryLayout;
    private TextView auxiliaryStatus;
    private Button setAuxiliaryButton1;
    private Button removeAuxiliaryButton1;
    private Button regulateAuxiliarySurvey1;
    private Button addMinorSurvey2;

    private LinearLayout auxiliaryLayout2;
    private TextView auxiliaryStatus2;
    private Button setAuxiliaryButton2;
    private Button removeAuxiliaryButton2;
    private Button regulateAuxiliarySurvey2;
    private Button addMinorSurvey3;

    private LinearLayout auxiliaryLayout3;
    private TextView auxiliaryStatus3;
    private Button setAuxiliaryButton3;
    private Button removeAuxiliaryButton3;
    private Button regulateAuxiliarySurvey3;
    private Button addMinorSurvey4;

    private LinearLayout auxiliaryLayout4;
    private TextView auxiliaryStatus4;
    private Button setAuxiliaryButton4;
    private Button removeAuxiliaryButton4;
    private Button regulateAuxiliarySurvey4;
    private Button addMinorSurvey5;

    private LinearLayout auxiliaryLayout5;
    private TextView auxiliaryStatus5;
    private Button setAuxiliaryButton5;
    private Button removeAuxiliaryButton5;
    private Button regulateAuxiliarySurvey5;
    private Button addMinorSurvey6;

    private LinearLayout auxiliaryLayout6;
    private TextView auxiliaryStatus6;
    private Button setAuxiliaryButton6;
    private Button removeAuxiliaryButton6;
    private Button regulateAuxiliarySurvey6;
    private Button addMinorSurvey7;

    private LinearLayout auxiliaryLayout7;
    private TextView auxiliaryStatus7;
    private Button setAuxiliaryButton7;
    private Button removeAuxiliaryButton7;
    private Button regulateAuxiliarySurvey7;
    private Button addMinorSurvey8;

    private LinearLayout auxiliaryLayout8;
    private TextView auxiliaryStatus8;
    private Button setAuxiliaryButton8;
    private Button removeAuxiliaryButton8;
    private Button regulateAuxiliarySurvey8;
    private Button addMinorSurvey9;

    private LinearLayout auxiliaryLayout9;
    private TextView auxiliaryStatus9;
    private Button setAuxiliaryButton9;
    private Button removeAuxiliaryButton9;
    private Button regulateAuxiliarySurvey9;
    private Button addMinorSurvey10;

    private LinearLayout auxiliaryLayout10;
    private TextView auxiliaryStatus10;
    private Button setAuxiliaryButton10;
    private Button removeAuxiliaryButton10;
    private Button regulateAuxiliarySurvey10;
    private Button addMinorSurvey11;

    private LinearLayout auxiliaryLayout11;
    private TextView auxiliaryStatus11;
    private Button setAuxiliaryButton11;
    private Button removeAuxiliaryButton11;
    private Button regulateAuxiliarySurvey11;
    private Button addMinorSurvey12;

    private LinearLayout auxiliaryLayout12;
    private TextView auxiliaryStatus12;
    private Button setAuxiliaryButton12;
    private Button removeAuxiliaryButton12;
    private Button regulateAuxiliarySurvey12;
    private Button addMinorSurvey13;

    private LinearLayout auxiliaryLayout13;
    private TextView auxiliaryStatus13;
    private Button setAuxiliaryButton13;
    private Button removeAuxiliaryButton13;
    private Button regulateAuxiliarySurvey13;
    private Button addMinorSurvey14;

    private LinearLayout auxiliaryLayout14;
    private TextView auxiliaryStatus14;
    private Button setAuxiliaryButton14;
    private Button removeAuxiliaryButton14;
    private Button regulateAuxiliarySurvey14;
    private Button addMinorSurvey15;

    private LinearLayout auxiliaryLayout15;
    private TextView auxiliaryStatus15;
    private Button setAuxiliaryButton15;
    private Button removeAuxiliaryButton15;
    private Button regulateAuxiliarySurvey15;
    private Button addMinorSurvey16;

    private LinearLayout auxiliaryLayout16;
    private TextView auxiliaryStatus16;
    private Button setAuxiliaryButton16;
    private Button removeAuxiliaryButton16;
    private Button regulateAuxiliarySurvey16;
    private Button addMinorSurvey17;

    private Button takePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_yun);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifyHelper.getInstance().removeObserver(this, UiEventEntry.READ_DATA);
    }


    private void initView(final View view) {
        mainPicture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.YUNMainType;
                String yun = "";
                switch (i) {
                    case R.id.main_picture_radiobtton:
                        yun = "0";
                        break;
                    case R.id.main_video_radiobtton:
                        yun = "1";
                        break;

                    default:
                        break;
                }
                if (TextUtils.isEmpty(yun)) {
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(content + yun);
            }
        });

        auxiliaryPicture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checkView = view.findViewById(i);
                if (!checkView.isPressed()) {
                    return;
                }
                String content = ConfigParams.YUNMinorType;
                String yun = "";
                switch (i) {
                    case R.id.auxiliary_picture_radiobutton:
                        yun = "0";
                        break;
                    case R.id.auxiliary_video_radiobutton:
                        yun = "1";
                        break;

                    default:
                        break;
                }
                if (TextUtils.isEmpty(yun)) {
                    return;
                }
                SocketUtil.getSocketUtil().sendContent(content + yun);
            }
        });

    }

    @Override
    public void initView() {
        EventNotifyHelper.getInstance().addObserver(this, UiEventEntry.READ_DATA);

        yunStatus = (TextView) view.findViewById(R.id.yun_status);
        startUp = (Button) view.findViewById(R.id.start_up);
        layoutYun = (LinearLayout) view.findViewById(R.id.layout_yun);
        leftArrow = (Button) view.findViewById(R.id.left_arrow);
        rightArrow = (Button) view.findViewById(R.id.right_arrow);
        mainPicture = (RadioGroup) view.findViewById(R.id.main_picture);
        auxiliaryPicture = (RadioGroup) view.findViewById(R.id.auxiliary_picture);

        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        mainStatus = (TextView) view.findViewById(R.id.main_status);
        setMainButton = (Button) view.findViewById(R.id.set_main_button);
        removeMainButton = (Button) view.findViewById(R.id.remove_main_button);
        regulateMainSurvey = (Button) view.findViewById(R.id.regulate_main_survey);
        addMinorSurvey1 = (Button) view.findViewById(R.id.add_minor_survey1);

        auxiliaryText = (TextView) view.findViewById(R.id.auxiliary_text);

        auxiliaryLayout = (LinearLayout) view.findViewById(R.id.auxiliary_layout);
        auxiliaryStatus = (TextView) view.findViewById(R.id.auxiliary_status);
        setAuxiliaryButton1 = (Button) view.findViewById(R.id.set_auxiliary_button1);
        removeAuxiliaryButton1 = (Button) view.findViewById(R.id.remove_auxiliary_button1);
        regulateAuxiliarySurvey1 = (Button) view.findViewById(R.id.regulate_auxiliary_survey1);
        addMinorSurvey2 = (Button) view.findViewById(R.id.add_minor_survey2);

        auxiliaryLayout2 = (LinearLayout) view.findViewById(R.id.auxiliary_layout2);
        auxiliaryStatus2 = (TextView) view.findViewById(R.id.auxiliary_status2);
        setAuxiliaryButton2 = (Button) view.findViewById(R.id.set_auxiliary_button2);
        removeAuxiliaryButton2 = (Button) view.findViewById(R.id.remove_auxiliary_button2);
        regulateAuxiliarySurvey2 = (Button) view.findViewById(R.id.regulate_auxiliary_survey2);
        addMinorSurvey3 = (Button) view.findViewById(R.id.add_minor_survey3);

        auxiliaryLayout3 = (LinearLayout) view.findViewById(R.id.auxiliary_layout3);
        auxiliaryStatus3 = (TextView) view.findViewById(R.id.auxiliary_status3);
        setAuxiliaryButton3 = (Button) view.findViewById(R.id.set_auxiliary_button3);
        removeAuxiliaryButton3 = (Button) view.findViewById(R.id.remove_auxiliary_button3);
        regulateAuxiliarySurvey3 = (Button) view.findViewById(R.id.regulate_auxiliary_survey3);
        addMinorSurvey4 = (Button) view.findViewById(R.id.add_minor_survey4);

        auxiliaryLayout4 = (LinearLayout) view.findViewById(R.id.auxiliary_layout4);
        auxiliaryStatus4 = (TextView) view.findViewById(R.id.auxiliary_status4);
        setAuxiliaryButton4 = (Button) view.findViewById(R.id.set_auxiliary_button4);
        removeAuxiliaryButton4 = (Button) view.findViewById(R.id.remove_auxiliary_button4);
        regulateAuxiliarySurvey4 = (Button) view.findViewById(R.id.regulate_auxiliary_survey4);
        addMinorSurvey5 = (Button) view.findViewById(R.id.add_minor_survey5);

        auxiliaryLayout5 = (LinearLayout) view.findViewById(R.id.auxiliary_layout5);
        auxiliaryStatus5 = (TextView) view.findViewById(R.id.auxiliary_status5);
        setAuxiliaryButton5 = (Button) view.findViewById(R.id.set_auxiliary_button5);
        removeAuxiliaryButton5 = (Button) view.findViewById(R.id.remove_auxiliary_button5);
        regulateAuxiliarySurvey5 = (Button) view.findViewById(R.id.regulate_auxiliary_survey5);
        addMinorSurvey6 = (Button) view.findViewById(R.id.add_minor_survey6);

        auxiliaryLayout6 = (LinearLayout) view.findViewById(R.id.auxiliary_layout6);
        auxiliaryStatus6 = (TextView) view.findViewById(R.id.auxiliary_status6);
        setAuxiliaryButton6 = (Button) view.findViewById(R.id.set_auxiliary_button6);
        removeAuxiliaryButton6 = (Button) view.findViewById(R.id.remove_auxiliary_button6);
        regulateAuxiliarySurvey6 = (Button) view.findViewById(R.id.regulate_auxiliary_survey6);
        addMinorSurvey7 = (Button) view.findViewById(R.id.add_minor_survey7);

        auxiliaryLayout7 = (LinearLayout) view.findViewById(R.id.auxiliary_layout7);
        auxiliaryStatus7 = (TextView) view.findViewById(R.id.auxiliary_status7);
        setAuxiliaryButton7 = (Button) view.findViewById(R.id.set_auxiliary_button7);
        removeAuxiliaryButton7 = (Button) view.findViewById(R.id.remove_auxiliary_button7);
        regulateAuxiliarySurvey7 = (Button) view.findViewById(R.id.regulate_auxiliary_survey7);
        addMinorSurvey8 = (Button) view.findViewById(R.id.add_minor_survey8);

        auxiliaryLayout8 = (LinearLayout) view.findViewById(R.id.auxiliary_layout8);
        auxiliaryStatus8 = (TextView) view.findViewById(R.id.auxiliary_status8);
        setAuxiliaryButton8 = (Button) view.findViewById(R.id.set_auxiliary_button8);
        removeAuxiliaryButton8 = (Button) view.findViewById(R.id.remove_auxiliary_button8);
        regulateAuxiliarySurvey8 = (Button) view.findViewById(R.id.regulate_auxiliary_survey8);
        addMinorSurvey9 = (Button) view.findViewById(R.id.add_minor_survey9);

        auxiliaryLayout9 = (LinearLayout) view.findViewById(R.id.auxiliary_layout9);
        auxiliaryStatus9 = (TextView) view.findViewById(R.id.auxiliary_status9);
        setAuxiliaryButton9 = (Button) view.findViewById(R.id.set_auxiliary_button9);
        removeAuxiliaryButton9 = (Button) view.findViewById(R.id.remove_auxiliary_button9);
        regulateAuxiliarySurvey9 = (Button) view.findViewById(R.id.regulate_auxiliary_survey9);
        addMinorSurvey10 = (Button) view.findViewById(R.id.add_minor_survey10);

        auxiliaryLayout10 = (LinearLayout) view.findViewById(R.id.auxiliary_layout10);
        auxiliaryStatus10 = (TextView) view.findViewById(R.id.auxiliary_status10);
        setAuxiliaryButton10 = (Button) view.findViewById(R.id.set_auxiliary_button10);
        removeAuxiliaryButton10 = (Button) view.findViewById(R.id.remove_auxiliary_button10);
        regulateAuxiliarySurvey10 = (Button) view.findViewById(R.id.regulate_auxiliary_survey10);
        addMinorSurvey11 = (Button) view.findViewById(R.id.add_minor_survey11);

        auxiliaryLayout11 = (LinearLayout) view.findViewById(R.id.auxiliary_layout11);
        auxiliaryStatus11 = (TextView) view.findViewById(R.id.auxiliary_status11);
        setAuxiliaryButton11 = (Button) view.findViewById(R.id.set_auxiliary_button11);
        removeAuxiliaryButton11 = (Button) view.findViewById(R.id.remove_auxiliary_button11);
        regulateAuxiliarySurvey11 = (Button) view.findViewById(R.id.regulate_auxiliary_survey11);
        addMinorSurvey12 = (Button) view.findViewById(R.id.add_minor_survey12);

        auxiliaryLayout12 = (LinearLayout) view.findViewById(R.id.auxiliary_layout12);
        auxiliaryStatus12 = (TextView) view.findViewById(R.id.auxiliary_status12);
        setAuxiliaryButton12 = (Button) view.findViewById(R.id.set_auxiliary_button12);
        removeAuxiliaryButton12 = (Button) view.findViewById(R.id.remove_auxiliary_button12);
        regulateAuxiliarySurvey12 = (Button) view.findViewById(R.id.regulate_auxiliary_survey12);
        addMinorSurvey13 = (Button) view.findViewById(R.id.add_minor_survey13);

        auxiliaryLayout13 = (LinearLayout) view.findViewById(R.id.auxiliary_layout13);
        auxiliaryStatus13 = (TextView) view.findViewById(R.id.auxiliary_status13);
        setAuxiliaryButton13 = (Button) view.findViewById(R.id.set_auxiliary_button13);
        removeAuxiliaryButton13 = (Button) view.findViewById(R.id.remove_auxiliary_button13);
        regulateAuxiliarySurvey13 = (Button) view.findViewById(R.id.regulate_auxiliary_survey13);
        addMinorSurvey14 = (Button) view.findViewById(R.id.add_minor_survey14);

        auxiliaryLayout14 = (LinearLayout) view.findViewById(R.id.auxiliary_layout14);
        auxiliaryStatus14 = (TextView) view.findViewById(R.id.auxiliary_status14);
        setAuxiliaryButton14 = (Button) view.findViewById(R.id.set_auxiliary_button14);
        removeAuxiliaryButton14 = (Button) view.findViewById(R.id.remove_auxiliary_button14);
        regulateAuxiliarySurvey14 = (Button) view.findViewById(R.id.regulate_auxiliary_survey14);
        addMinorSurvey15 = (Button) view.findViewById(R.id.add_minor_survey15);

        auxiliaryLayout15 = (LinearLayout) view.findViewById(R.id.auxiliary_layout15);
        auxiliaryStatus15 = (TextView) view.findViewById(R.id.auxiliary_status15);
        setAuxiliaryButton15 = (Button) view.findViewById(R.id.set_auxiliary_button15);
        removeAuxiliaryButton15 = (Button) view.findViewById(R.id.remove_auxiliary_button15);
        regulateAuxiliarySurvey15 = (Button) view.findViewById(R.id.regulate_auxiliary_survey15);
        addMinorSurvey16 = (Button) view.findViewById(R.id.add_minor_survey16);

        auxiliaryLayout16 = (LinearLayout) view.findViewById(R.id.auxiliary_layout16);
        auxiliaryStatus16 = (TextView) view.findViewById(R.id.auxiliary_status16);
        setAuxiliaryButton16 = (Button) view.findViewById(R.id.set_auxiliary_button16);
        removeAuxiliaryButton16 = (Button) view.findViewById(R.id.remove_auxiliary_button16);
        regulateAuxiliarySurvey16 = (Button) view.findViewById(R.id.regulate_auxiliary_survey16);
        addMinorSurvey17 = (Button) view.findViewById(R.id.add_minor_survey17);

        takePhoto = (Button) view.findViewById(R.id.take_photo);


        initView(view);
    }

    @Override
    public void initData() {
        SocketUtil.getSocketUtil().sendContent(ConfigParams.ReadYUNStatus);
    }

    @Override
    public void initEvent() {
        takePhoto.setOnClickListener(this);
        startUp.setOnClickListener(this);
        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);
        setMainButton.setOnClickListener(this);
        removeMainButton.setOnClickListener(this);
        regulateMainSurvey.setOnClickListener(this);
        addMinorSurvey1.setOnClickListener(this);
        setAuxiliaryButton1.setOnClickListener(this);
        removeAuxiliaryButton1.setOnClickListener(this);
        regulateAuxiliarySurvey1.setOnClickListener(this);
        addMinorSurvey2.setOnClickListener(this);

        setAuxiliaryButton1.setOnClickListener(this);
        removeAuxiliaryButton1.setOnClickListener(this);
        regulateAuxiliarySurvey1.setOnClickListener(this);
        addMinorSurvey2.setOnClickListener(this);

        setAuxiliaryButton2.setOnClickListener(this);
        removeAuxiliaryButton2.setOnClickListener(this);
        regulateAuxiliarySurvey2.setOnClickListener(this);
        addMinorSurvey3.setOnClickListener(this);

        setAuxiliaryButton3.setOnClickListener(this);
        removeAuxiliaryButton3.setOnClickListener(this);
        regulateAuxiliarySurvey3.setOnClickListener(this);
        addMinorSurvey4.setOnClickListener(this);

        setAuxiliaryButton4.setOnClickListener(this);
        removeAuxiliaryButton4.setOnClickListener(this);
        regulateAuxiliarySurvey4.setOnClickListener(this);
        addMinorSurvey5.setOnClickListener(this);

        setAuxiliaryButton5.setOnClickListener(this);
        removeAuxiliaryButton5.setOnClickListener(this);
        regulateAuxiliarySurvey5.setOnClickListener(this);
        addMinorSurvey6.setOnClickListener(this);

        setAuxiliaryButton6.setOnClickListener(this);
        removeAuxiliaryButton6.setOnClickListener(this);
        regulateAuxiliarySurvey6.setOnClickListener(this);
        addMinorSurvey7.setOnClickListener(this);

        setAuxiliaryButton7.setOnClickListener(this);
        removeAuxiliaryButton7.setOnClickListener(this);
        regulateAuxiliarySurvey7.setOnClickListener(this);
        addMinorSurvey8.setOnClickListener(this);

        setAuxiliaryButton8.setOnClickListener(this);
        removeAuxiliaryButton8.setOnClickListener(this);
        regulateAuxiliarySurvey8.setOnClickListener(this);
        addMinorSurvey9.setOnClickListener(this);

        setAuxiliaryButton9.setOnClickListener(this);
        removeAuxiliaryButton9.setOnClickListener(this);
        regulateAuxiliarySurvey9.setOnClickListener(this);
        addMinorSurvey10.setOnClickListener(this);

        setAuxiliaryButton10.setOnClickListener(this);
        removeAuxiliaryButton10.setOnClickListener(this);
        regulateAuxiliarySurvey10.setOnClickListener(this);
        addMinorSurvey11.setOnClickListener(this);

        setAuxiliaryButton11.setOnClickListener(this);
        removeAuxiliaryButton11.setOnClickListener(this);
        regulateAuxiliarySurvey11.setOnClickListener(this);
        addMinorSurvey12.setOnClickListener(this);

        setAuxiliaryButton12.setOnClickListener(this);
        removeAuxiliaryButton12.setOnClickListener(this);
        regulateAuxiliarySurvey12.setOnClickListener(this);
        addMinorSurvey13.setOnClickListener(this);

        setAuxiliaryButton13.setOnClickListener(this);
        removeAuxiliaryButton13.setOnClickListener(this);
        regulateAuxiliarySurvey13.setOnClickListener(this);
        addMinorSurvey14.setOnClickListener(this);

        setAuxiliaryButton14.setOnClickListener(this);
        removeAuxiliaryButton14.setOnClickListener(this);
        regulateAuxiliarySurvey14.setOnClickListener(this);
        addMinorSurvey15.setOnClickListener(this);

        setAuxiliaryButton15.setOnClickListener(this);
        removeAuxiliaryButton15.setOnClickListener(this);
        regulateAuxiliarySurvey15.setOnClickListener(this);
        addMinorSurvey16.setOnClickListener(this);

        setAuxiliaryButton16.setOnClickListener(this);
        removeAuxiliaryButton16.setOnClickListener(this);
        regulateAuxiliarySurvey16.setOnClickListener(this);
        addMinorSurvey17.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String content = "";
        switch (v.getId()) {
            case R.id.take_photo:
                content = ConfigParams.CameraTakePicture;
                break;
            case R.id.start_up:
                content = ConfigParams.YUNStatus + "0";
                break;
            case R.id.left_arrow:
                content = ConfigParams.SetYUNLeft;

                break;
            case R.id.right_arrow:
                content = ConfigParams.SetYUNRight;

                break;
            case R.id.set_main_button:
                content = ConfigParams.SetMainSurveyStatus;
                break;
            case R.id.remove_main_button:
                content = ConfigParams.DelMainSurveyStatus;
                break;
            case R.id.regulate_main_survey:
                content = ConfigParams.RegulateMainSurvey;
                break;
            case R.id.add_minor_survey1:
                content = ConfigParams.AddMinorSurvey + "1";
                auxiliaryLayout.setVisibility(View.VISIBLE);
                addMinorSurvey1.setEnabled(false);
                break;
            case R.id.set_auxiliary_button1:
                content = ConfigParams.SetMinorSurveyStatus + "1";
                break;
            case R.id.remove_auxiliary_button1:
                content = ConfigParams.DelMinorSurvey + "1";
                break;
            case R.id.regulate_auxiliary_survey1:
                content = ConfigParams.RegulateMinorSurvey + "1";
                break;
            case R.id.add_minor_survey2:
                content = ConfigParams.AddMinorSurvey + "2";
                auxiliaryLayout2.setVisibility(View.VISIBLE);
                addMinorSurvey2.setEnabled(false);
                break;
            case R.id.set_auxiliary_button2:
                content = ConfigParams.SetMinorSurveyStatus + "2";
                break;
            case R.id.remove_auxiliary_button2:
                content = ConfigParams.DelMinorSurvey + "2";
                break;
            case R.id.regulate_auxiliary_survey2:
                content = ConfigParams.RegulateMinorSurvey + "2";
                break;
            case R.id.add_minor_survey3:
                content = ConfigParams.AddMinorSurvey + "3";
                auxiliaryLayout3.setVisibility(View.VISIBLE);
                addMinorSurvey3.setEnabled(false);
                break;
            case R.id.set_auxiliary_button3:
                content = ConfigParams.SetMinorSurveyStatus + "3";

                break;
            case R.id.remove_auxiliary_button3:
                content = ConfigParams.DelMinorSurvey + "3";

                break;
            case R.id.regulate_auxiliary_survey3:
                content = ConfigParams.RegulateMinorSurvey + "3";
                break;
            case R.id.add_minor_survey4:
                content = ConfigParams.AddMinorSurvey + "4";
                auxiliaryLayout4.setVisibility(View.VISIBLE);
                addMinorSurvey4.setEnabled(false);
                break;
            case R.id.set_auxiliary_button4:
                content = ConfigParams.SetMinorSurveyStatus + "4";
                break;
            case R.id.remove_auxiliary_button4:
                content = ConfigParams.DelMinorSurvey + "4";
                break;
            case R.id.regulate_auxiliary_survey4:
                content = ConfigParams.RegulateMinorSurvey + "4";
                break;
            case R.id.add_minor_survey5:
                content = ConfigParams.AddMinorSurvey + "5";
                auxiliaryLayout5.setVisibility(View.VISIBLE);
                addMinorSurvey5.setEnabled(false);
                break;
            case R.id.set_auxiliary_button5:
                content = ConfigParams.SetMinorSurveyStatus + "5";
                break;
            case R.id.remove_auxiliary_button5:
                content = ConfigParams.DelMinorSurvey + "5";

                break;
            case R.id.regulate_auxiliary_survey5:
                content = ConfigParams.RegulateMinorSurvey + "5";
                break;
            case R.id.add_minor_survey6:
                content = ConfigParams.AddMinorSurvey + "6";
                auxiliaryLayout6.setVisibility(View.VISIBLE);
                addMinorSurvey6.setEnabled(false);
                break;
            case R.id.set_auxiliary_button6:
                content = ConfigParams.SetMinorSurveyStatus + "6";
                break;
            case R.id.remove_auxiliary_button6:
                content = ConfigParams.DelMinorSurvey + "6";

                break;
            case R.id.regulate_auxiliary_survey6:
                content = ConfigParams.RegulateMinorSurvey + "6";
                break;
            case R.id.add_minor_survey7:
                content = ConfigParams.AddMinorSurvey + "7";
                auxiliaryLayout7.setVisibility(View.VISIBLE);
                addMinorSurvey7.setEnabled(false);
                break;
            case R.id.set_auxiliary_button7:
                content = ConfigParams.SetMinorSurveyStatus + "7";
                break;
            case R.id.remove_auxiliary_button7:
                content = ConfigParams.DelMinorSurvey + "7";

                break;
            case R.id.regulate_auxiliary_survey7:
                content = ConfigParams.RegulateMinorSurvey + "7";
                break;
            case R.id.add_minor_survey8:
                content = ConfigParams.AddMinorSurvey + "8";
                auxiliaryLayout8.setVisibility(View.VISIBLE);
                addMinorSurvey8.setEnabled(false);
                break;
            case R.id.set_auxiliary_button8:
                content = ConfigParams.SetMinorSurveyStatus + "8";
                break;
            case R.id.remove_auxiliary_button8:
                content = ConfigParams.DelMinorSurvey + "8";
                break;
            case R.id.regulate_auxiliary_survey8:
                content = ConfigParams.RegulateMinorSurvey + "8";
                break;
            case R.id.add_minor_survey9:
                content = ConfigParams.AddMinorSurvey + "9";
                auxiliaryLayout9.setVisibility(View.VISIBLE);
                addMinorSurvey9.setEnabled(false);
                break;
            case R.id.set_auxiliary_button9:
                content = ConfigParams.SetMinorSurveyStatus + "9";
                break;
            case R.id.remove_auxiliary_button9:
                content = ConfigParams.DelMinorSurvey + "9";

                break;
            case R.id.regulate_auxiliary_survey9:
                content = ConfigParams.RegulateMinorSurvey + "9";
                break;
            case R.id.add_minor_survey10:
                content = ConfigParams.AddMinorSurvey + "10";
                auxiliaryLayout10.setVisibility(View.VISIBLE);
                addMinorSurvey10.setEnabled(false);
                break;
            case R.id.set_auxiliary_button10:
                content = ConfigParams.SetMinorSurveyStatus + "10";
                break;
            case R.id.remove_auxiliary_button10:
                content = ConfigParams.DelMinorSurvey + "10";

                break;
            case R.id.regulate_auxiliary_survey10:
                content = ConfigParams.RegulateMinorSurvey + "10";
                break;
            case R.id.add_minor_survey11:
                content = ConfigParams.AddMinorSurvey + "11";
                auxiliaryLayout11.setVisibility(View.VISIBLE);
                addMinorSurvey11.setEnabled(false);
                break;
            case R.id.set_auxiliary_button11:
                content = ConfigParams.SetMinorSurveyStatus + "11";
                break;
            case R.id.remove_auxiliary_button11:
                content = ConfigParams.DelMinorSurvey + "11";

                break;
            case R.id.regulate_auxiliary_survey11:
                content = ConfigParams.RegulateMinorSurvey + "11";
                break;
            case R.id.add_minor_survey12:
                content = ConfigParams.AddMinorSurvey + "12";
                auxiliaryLayout12.setVisibility(View.VISIBLE);
                addMinorSurvey12.setEnabled(false);
                break;
            case R.id.set_auxiliary_button12:
                content = ConfigParams.SetMinorSurveyStatus + "12";
                break;
            case R.id.remove_auxiliary_button12:
                content = ConfigParams.DelMinorSurvey + "12";

                break;
            case R.id.regulate_auxiliary_survey12:
                content = ConfigParams.RegulateMinorSurvey + "12";
                break;
            case R.id.add_minor_survey13:
                content = ConfigParams.AddMinorSurvey + "13";
                auxiliaryLayout13.setVisibility(View.VISIBLE);
                addMinorSurvey13.setEnabled(false);
                break;
            case R.id.set_auxiliary_button13:
                content = ConfigParams.SetMinorSurveyStatus + "13";
                break;
            case R.id.remove_auxiliary_button13:
                content = ConfigParams.DelMinorSurvey + "13";

                break;
            case R.id.regulate_auxiliary_survey13:
                content = ConfigParams.RegulateMinorSurvey + "13";
                break;
            case R.id.add_minor_survey14:
                content = ConfigParams.AddMinorSurvey + "14";
                auxiliaryLayout14.setVisibility(View.VISIBLE);
                addMinorSurvey14.setEnabled(false);
                break;
            case R.id.set_auxiliary_button14:
                content = ConfigParams.SetMinorSurveyStatus + "14";
                break;
            case R.id.remove_auxiliary_button14:
                content = ConfigParams.DelMinorSurvey + "14";

                break;
            case R.id.regulate_auxiliary_survey14:
                content = ConfigParams.RegulateMinorSurvey + "14";
                break;
            case R.id.add_minor_survey15:
                content = ConfigParams.AddMinorSurvey + "15";
                auxiliaryLayout15.setVisibility(View.VISIBLE);
                addMinorSurvey15.setEnabled(false);
                break;
            case R.id.set_auxiliary_button15:
                content = ConfigParams.SetMinorSurveyStatus + "15";

                break;
            case R.id.remove_auxiliary_button15:
                content = ConfigParams.DelMinorSurvey + "15";

                break;
            case R.id.regulate_auxiliary_survey15:
                content = ConfigParams.RegulateMinorSurvey + "15";
                break;
            case R.id.add_minor_survey16:
                content = ConfigParams.AddMinorSurvey + "16";
                auxiliaryLayout16.setVisibility(View.VISIBLE);
                addMinorSurvey16.setEnabled(false);
                break;
            case R.id.set_auxiliary_button16:
                content = ConfigParams.SetMinorSurveyStatus + "16";
                break;
            case R.id.remove_auxiliary_button16:
                content = ConfigParams.DelMinorSurvey + "16";

                break;
            case R.id.regulate_auxiliary_survey16:
                content = ConfigParams.RegulateMinorSurvey + "16";
                break;
            case R.id.add_minor_survey17:
//                content = ConfigParams.AddMinorSurvey + "17";
//                auxiliaryLayout2.setVisibility(View.VISIBLE);
                addMinorSurvey17.setEnabled(false);
                break;
            default:
                break;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        SocketUtil.getSocketUtil().sendContent(content);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {

        if (id == UiEventEntry.READ_DATA) {
            String result = (String) args[0];
            String content = (String) args[1];
            if (TextUtils.isEmpty(result) || TextUtils.isEmpty(content)) {
                return;
            }
            setData(result);
        }
    }

    private void setData(String result) {
        String data = "";
        if (result.contains(ConfigParams.YUNStatus.trim())) {
            data = result.replaceAll(ConfigParams.YUNStatus.trim(), "").trim();
            if ("0".equals(data)) {
                yunStatus.setText("已启动");
                layoutYun.setVisibility(View.VISIBLE);
                auxiliaryText.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.VISIBLE);

            } else if ("1".equals(data)) {
                yunStatus.setText("未启动");
                layoutYun.setVisibility(View.GONE);
                auxiliaryText.setVisibility(View.GONE);
                mainLayout.setVisibility(View.GONE);
                auxiliaryLayout.setVisibility(View.GONE);
                auxiliaryLayout2.setVisibility(View.GONE);
                auxiliaryLayout3.setVisibility(View.GONE);
                auxiliaryLayout4.setVisibility(View.GONE);
                auxiliaryLayout5.setVisibility(View.GONE);
                auxiliaryLayout6.setVisibility(View.GONE);
                auxiliaryLayout7.setVisibility(View.GONE);
                auxiliaryLayout8.setVisibility(View.GONE);
                auxiliaryLayout9.setVisibility(View.GONE);
                auxiliaryLayout10.setVisibility(View.GONE);
                auxiliaryLayout11.setVisibility(View.GONE);
                auxiliaryLayout12.setVisibility(View.GONE);
                auxiliaryLayout13.setVisibility(View.GONE);
                auxiliaryLayout14.setVisibility(View.GONE);
                auxiliaryLayout15.setVisibility(View.GONE);
                auxiliaryLayout16.setVisibility(View.GONE);
            } else if ("2".equals(data)) {
                yunStatus.setText("拍摄中，请稍等...");
                layoutYun.setVisibility(View.GONE);
                auxiliaryText.setVisibility(View.GONE);
                mainLayout.setVisibility(View.GONE);
                auxiliaryLayout.setVisibility(View.GONE);
                auxiliaryLayout2.setVisibility(View.GONE);
                auxiliaryLayout3.setVisibility(View.GONE);
                auxiliaryLayout4.setVisibility(View.GONE);
                auxiliaryLayout5.setVisibility(View.GONE);
                auxiliaryLayout6.setVisibility(View.GONE);
                auxiliaryLayout7.setVisibility(View.GONE);
                auxiliaryLayout8.setVisibility(View.GONE);
                auxiliaryLayout9.setVisibility(View.GONE);
                auxiliaryLayout10.setVisibility(View.GONE);
                auxiliaryLayout11.setVisibility(View.GONE);
                auxiliaryLayout12.setVisibility(View.GONE);
                auxiliaryLayout13.setVisibility(View.GONE);
                auxiliaryLayout14.setVisibility(View.GONE);
                auxiliaryLayout15.setVisibility(View.GONE);
                auxiliaryLayout16.setVisibility(View.GONE);

            }
        } else if (result.contains(ConfigParams.YUNMainType.trim())) {
            data = result.replaceAll(ConfigParams.YUNMainType.trim(), "").trim();
            if ("0".equals(data)) {
                mainPicture.check(R.id.main_picture_radiobtton);
            } else if ("1".equals(data)) {
                mainPicture.check(R.id.main_video_radiobtton);
            }
        } else if (result.contains(ConfigParams.YUNMinorType.trim())) {
            data = result.replaceAll(ConfigParams.YUNMinorType.trim(), "").trim();
            if ("0".equals(data)) {
                auxiliaryPicture.check(R.id.auxiliary_picture_radiobutton);
            } else if ("1".equals(data)) {
                auxiliaryPicture.check(R.id.auxiliary_video_radiobutton);
            }
        } else if (result.contains(ConfigParams.DelMainSurveyStatus.trim())) {
            data = result.replaceAll(ConfigParams.DelMainSurveyStatus.trim(), "").trim();
            if ("OK".equals(data)) {
                mainLayout.setVisibility(View.GONE);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("请先删除所有辅巡测点！");
            }
        } else if (result.contains(ConfigParams.MainSurveyStatus.trim())) {
            data = result.replaceAll(ConfigParams.MainSurveyStatus.trim(), "").trim();
            if ("0".equals(data)) {
                mainStatus.setText("已设置");
                setMainButton.setVisibility(View.GONE);
                removeMainButton.setVisibility(View.VISIBLE);
            }
            if ("1".equals(data)) {
                mainStatus.setText("未设置");
                setMainButton.setVisibility(View.VISIBLE);
                removeMainButton.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus1).trim())) {
            String content = ConfigParams.MinorSurveyStatus1;
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey1.setEnabled(false);
            auxiliaryLayout.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus.setText("已设置");
                setAuxiliaryButton1.setVisibility(View.GONE);
                removeAuxiliaryButton1.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus.setText("未设置");
                setAuxiliaryButton1.setVisibility(View.VISIBLE);
                removeAuxiliaryButton1.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "2").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "2 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey2.setEnabled(false);
            auxiliaryLayout2.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus2.setText("已设置");
                setAuxiliaryButton2.setVisibility(View.GONE);
                removeAuxiliaryButton2.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus2.setText("未设置");
                setAuxiliaryButton2.setVisibility(View.VISIBLE);
                removeAuxiliaryButton2.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "3").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "3 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey3.setEnabled(false);
            auxiliaryLayout3.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus3.setText("已设置");
                setAuxiliaryButton3.setVisibility(View.GONE);
                removeAuxiliaryButton3.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus3.setText("未设置");
                setAuxiliaryButton3.setVisibility(View.VISIBLE);
                removeAuxiliaryButton3.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "4").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "4 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey4.setEnabled(false);
            auxiliaryLayout4.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus4.setText("已设置");
                setAuxiliaryButton4.setVisibility(View.GONE);
                removeAuxiliaryButton4.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus4.setText("未设置");
                setAuxiliaryButton4.setVisibility(View.VISIBLE);
                removeAuxiliaryButton4.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "5").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "5 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey5.setEnabled(false);
            auxiliaryLayout5.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus5.setText("已设置");
                setAuxiliaryButton5.setVisibility(View.GONE);
                removeAuxiliaryButton5.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus5.setText("未设置");
                setAuxiliaryButton5.setVisibility(View.VISIBLE);
                removeAuxiliaryButton5.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "6").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "6 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey6.setEnabled(false);
            auxiliaryLayout6.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus6.setText("已设置");
                setAuxiliaryButton6.setVisibility(View.GONE);
                removeAuxiliaryButton6.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus6.setText("未设置");
                setAuxiliaryButton6.setVisibility(View.VISIBLE);
                removeAuxiliaryButton6.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "7").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "7 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey7.setEnabled(false);
            auxiliaryLayout7.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus7.setText("已设置");
                setAuxiliaryButton7.setVisibility(View.GONE);
                removeAuxiliaryButton7.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus7.setText("未设置");
                setAuxiliaryButton7.setVisibility(View.VISIBLE);
                removeAuxiliaryButton7.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "8").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "8 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey8.setEnabled(false);
            auxiliaryLayout8.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus8.setText("已设置");
                setAuxiliaryButton8.setVisibility(View.GONE);
                removeAuxiliaryButton8.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus8.setText("未设置");
                setAuxiliaryButton8.setVisibility(View.VISIBLE);
                removeAuxiliaryButton8.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "9").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "9 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey9.setEnabled(false);
            auxiliaryLayout9.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus9.setText("已设置");
                setAuxiliaryButton9.setVisibility(View.GONE);
                removeAuxiliaryButton9.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus9.setText("未设置");
                setAuxiliaryButton9.setVisibility(View.VISIBLE);
                removeAuxiliaryButton9.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "10").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "10" + " ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey10.setEnabled(false);
            auxiliaryLayout10.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus10.setText("已设置");
                setAuxiliaryButton10.setVisibility(View.GONE);
                removeAuxiliaryButton10.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus10.setText("未设置");
                setAuxiliaryButton10.setVisibility(View.VISIBLE);
                removeAuxiliaryButton10.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "11").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "11 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey11.setEnabled(false);
            auxiliaryLayout11.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus11.setText("已设置");
                setAuxiliaryButton11.setVisibility(View.GONE);
                removeAuxiliaryButton11.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus11.setText("未设置");
                setAuxiliaryButton11.setVisibility(View.VISIBLE);
                removeAuxiliaryButton11.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "12").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "12 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey12.setEnabled(false);
            auxiliaryLayout12.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus12.setText("已设置");
                setAuxiliaryButton12.setVisibility(View.GONE);
                removeAuxiliaryButton12.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus12.setText("未设置");
                setAuxiliaryButton12.setVisibility(View.VISIBLE);
                removeAuxiliaryButton12.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "13").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "13 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey13.setEnabled(false);
            auxiliaryLayout13.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus13.setText("已设置");
                setAuxiliaryButton13.setVisibility(View.GONE);
                removeAuxiliaryButton13.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus13.setText("未设置");
                setAuxiliaryButton13.setVisibility(View.VISIBLE);
                removeAuxiliaryButton13.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "14").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "14 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey14.setEnabled(false);
            auxiliaryLayout14.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus14.setText("已设置");
                setAuxiliaryButton14.setVisibility(View.GONE);
                removeAuxiliaryButton14.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus14.setText("未设置");
                setAuxiliaryButton14.setVisibility(View.VISIBLE);
                removeAuxiliaryButton14.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "15").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "15 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey15.setEnabled(false);
            auxiliaryLayout15.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus15.setText("已设置");
                setAuxiliaryButton15.setVisibility(View.GONE);
                removeAuxiliaryButton15.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus15.setText("未设置");
                setAuxiliaryButton15.setVisibility(View.VISIBLE);
                removeAuxiliaryButton15.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.MinorSurveyStatus + "16").trim())) {
            String content = ConfigParams.MinorSurveyStatus + "16 ";
            data = result.replaceAll(content.trim(), "").trim();
            addMinorSurvey16.setEnabled(false);
            auxiliaryLayout16.setVisibility(View.VISIBLE);
            if ("0".equals(data)) {
                auxiliaryStatus16.setText("已设置");
                setAuxiliaryButton16.setVisibility(View.GONE);
                removeAuxiliaryButton16.setVisibility(View.VISIBLE);
            } else if ("1".equals(data)) {
                auxiliaryStatus16.setText("未设置");
                setAuxiliaryButton16.setVisibility(View.VISIBLE);
                removeAuxiliaryButton16.setVisibility(View.GONE);
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "1").trim())) {
            String content = ConfigParams.DelMinorSurvey + "1 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout.setVisibility(View.GONE);
                addMinorSurvey1.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "2").trim())) {
            String content = ConfigParams.DelMinorSurvey + "2 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout2.setVisibility(View.GONE);
                addMinorSurvey2.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "3").trim())) {
            String content = ConfigParams.DelMinorSurvey + "3 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout3.setVisibility(View.GONE);
                addMinorSurvey3.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "4").trim())) {
            String content = ConfigParams.DelMinorSurvey + "4 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout4.setVisibility(View.GONE);
                addMinorSurvey4.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "5").trim())) {
            String content = ConfigParams.DelMinorSurvey + "5 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout5.setVisibility(View.GONE);
                addMinorSurvey5.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "6").trim())) {
            String content = ConfigParams.DelMinorSurvey + "6 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout6.setVisibility(View.GONE);
                addMinorSurvey6.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "7").trim())) {
            String content = ConfigParams.DelMinorSurvey + "7 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout7.setVisibility(View.GONE);
                addMinorSurvey7.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "8").trim())) {
            String content = ConfigParams.DelMinorSurvey + "8 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout8.setVisibility(View.GONE);
                addMinorSurvey8.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "9").trim())) {
            String content = ConfigParams.DelMinorSurvey + "9 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout9.setVisibility(View.GONE);
                addMinorSurvey9.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "10").trim())) {
            String content = ConfigParams.DelMinorSurvey + "10 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout10.setVisibility(View.GONE);
                addMinorSurvey10.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "11").trim())) {
            String content = ConfigParams.DelMinorSurvey + "11 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout11.setVisibility(View.GONE);
                addMinorSurvey11.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "12").trim())) {
            String content = ConfigParams.DelMinorSurvey + "12 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout12.setVisibility(View.GONE);
                addMinorSurvey12.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "13").trim())) {
            String content = ConfigParams.DelMinorSurvey + "13 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout13.setVisibility(View.GONE);
                addMinorSurvey13.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "14").trim())) {
            String content = ConfigParams.DelMinorSurvey + "14 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout14.setVisibility(View.GONE);
                addMinorSurvey14.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "15").trim())) {
            String content = ConfigParams.DelMinorSurvey + "15 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout15.setVisibility(View.GONE);
                addMinorSurvey15.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else if (result.contains((ConfigParams.DelMinorSurvey + "16").trim())) {
            String content = ConfigParams.DelMinorSurvey + "16 ";
            data = result.replaceAll(content.trim(), "").trim();
            if ("OK".equals(data)) {
                auxiliaryLayout16.setVisibility(View.GONE);
                addMinorSurvey16.setEnabled(true);
            } else if ("ERROR".equals(data)) {
                ToastUtil.showToastLong("删除失败！");
            }
        } else {

            addMinorSurvey1.setEnabled(true);
            addMinorSurvey2.setEnabled(true);
            addMinorSurvey3.setEnabled(true);
            addMinorSurvey4.setEnabled(true);
            addMinorSurvey5.setEnabled(true);
            addMinorSurvey6.setEnabled(true);
            addMinorSurvey7.setEnabled(true);
            addMinorSurvey8.setEnabled(true);
            addMinorSurvey9.setEnabled(true);
            addMinorSurvey10.setEnabled(true);
            addMinorSurvey11.setEnabled(true);
            addMinorSurvey12.setEnabled(true);
            addMinorSurvey13.setEnabled(true);
            addMinorSurvey14.setEnabled(true);
            addMinorSurvey15.setEnabled(true);
            addMinorSurvey16.setEnabled(true);
        }
    }
}
