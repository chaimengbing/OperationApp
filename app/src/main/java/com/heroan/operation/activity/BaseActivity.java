package com.heroan.operation.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.heroan.operation.utils.FgManager;


/**
 * 说明：父类的Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements OnClickListener, Toolbar.OnMenuItemClickListener {
    private static final String TAG = BaseActivity.class.getName();
    /*Toolbar*/
    private Toolbar toolBar;
    /*一级标题*/
    private TextView titleMain;


    /*二级标题*/
    private TextView titleName;
    /*右边操作*/
    public TextView titleRight;

    public TextView getTitleRight() {
        return titleRight;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(getLayoutView());
            initComponentViews();
            initViewData();

        } catch (Exception e) {
        }
    }

    protected <T extends View> T getView(int resourcesId) {
        return (T) findViewById(resourcesId);
    }


    public void hideToolbar() {
        if (toolBar != null) {
            toolBar.setVisibility(View.GONE);
        }
    }


    public Toolbar getToolbar() {
        if (toolBar != null) {
            return toolBar;
        }
        return null;
    }

    public void showToolbar() {
        if (toolBar != null) {
            toolBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置title
     *
     * @param title ：title
     */
    public void setTitleMain(String title) {
        if (titleMain != null) {
            titleMain.setText(title);
            titleMain.setOnClickListener(this);
        }
    }

    public void setMainVisible(int visibile) {
        if (titleMain != null) {
            titleMain.setVisibility(visibile);
        }
    }

    public void setTitleName(String title) {
        if (titleName != null) {
            titleName.setText(title);
        }
    }

    public void setTitleRight(String title) {
        if (titleRight != null) {
            titleRight.setText(title);
            titleRight.setOnClickListener(this);
        }
    }

    /**
     * 设置右标题的显隐
     *
     * @param visibile
     */
    public void setTitleRightVisible(int visibile) {
        if (titleRight != null) {
            titleRight.setVisibility(visibile);
        }
    }

    public void setTitleftVisible(int visibile) {
        if (titleMain != null) {
            titleMain.setVisibility(visibile);
        }
    }



    public static abstract class GrantedResult implements Runnable {
        private boolean mGranted;

        public abstract void onResult(boolean granted);

        @Override
        public void run() {
            onResult(mGranted);
        }
    }


    /*---------------------------------------------------------------------------以下是android6.0动态授权的封装十分好用---------------------------------------------------------------------------*/
    private int mPermissionIdx = 0x10;//请求权限索引
    private SparseArray<GrantedResult> mPermissions = new SparseArray<>();//请求权限运行列表

    @SuppressLint("Override")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        GrantedResult runnable = mPermissions.get(requestCode);
        if (runnable == null) {
            return;
        }
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            runnable.mGranted = true;
        }
        runOnUiThread(runnable);
    }


    /**
     * 方法说明 : 初始化页面的布局
     *
     * @return void
     */
    public abstract int getLayoutView();

    /**
     * 方法说明 : 初始化页面显示数据
     *
     * @return void
     */
    public abstract void initViewData();

    /**
     * 方法说明 : 初始化页面控件的布局
     *
     * @return void
     */
    public abstract void initComponentViews();

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public void onBackPressed()
//    {
//        return;
//    }


    public boolean turnToFragmentStack(int containerViewId, Class<? extends Fragment> toFragmentClass, Bundle args) {
        return FgManager.turnToFragmentStack(getFragmentManager(), containerViewId, toFragmentClass, args);
    }

    public boolean turnToFragmentStack(int containerViewId, Class<? extends Fragment> toFragmentClass) {
        return FgManager.turnToFragmentStack(getFragmentManager(), containerViewId, toFragmentClass, null);
    }

    public void clearFragmentStack(int containerViewId) {
        FgManager.clearFragmentStack(getFragmentManager(), containerViewId);
        FrameLayout vg = (FrameLayout) findViewById(containerViewId);
        if (vg != null) {
            vg.removeAllViews();
        }
    }

    public void clearFragmentStack(int[] containerViewIds) {
        clearFragmentStack(containerViewIds, null);
    }

    public void clearFragmentStack(int[] containerViewIds, FgManager.Callback callback) {
        FgManager.clearFragmentStack(getFragmentManager(), containerViewIds, callback);
        FrameLayout vg = null;
        for (int containerViewId : containerViewIds) {
            vg = (FrameLayout) findViewById(containerViewId);
            if (vg != null) {
                vg.removeAllViews();
            }
        }
    }

    public void backToPreFragment(int containerViewId) {
        backToPreFragment(containerViewId, null, 1);
    }

    public void backToPreFragment(int containerViewId, int backCount) {
        backToPreFragment(containerViewId, null, backCount);
    }

    private void backToPreFragment(int containerViewId, Bundle args, int backCount) {
        try {
            FgManager.backToPreFragment(getFragmentManager(), containerViewId, args, backCount);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void removeFragmentFromBackStack(int containerViewId, Class<? extends Fragment>... removeFragmentClass) {
        try {
            FgManager.removeFragmentFromBackStack(getFragmentManager(), containerViewId, removeFragmentClass);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

}
