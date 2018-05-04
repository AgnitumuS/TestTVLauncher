package com.explame.testtvlauncher.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.util.DisplayMetrics;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.utils.LogUtils;
import com.explame.testtvlauncher.utils.ScreenUtils;

public class MainActivity extends Activity {

    private Context mContext;

    protected BrowseFragment mBrowseFragment;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private ArrayObjectAdapter rowsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        mContext = this;
        initData();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        LogUtils.i("手机屏幕宽度(像素)----->" + ScreenUtils.getScreenWidth() + "|" + "手机屏幕高度(像素)----->" + ScreenUtils.getScreenHeight());
        LogUtils.i("手机屏幕密度----->" + ScreenUtils.getScreenDensity(mContext) + "|" + "手机屏幕densityDpi----->"
                + ScreenUtils.getScreendensityDpi(mContext));
        LogUtils.i("手机屏幕宽度(dp)----->" + ScreenUtils.getScreenWidthDP(mContext) + "|" + "手机屏幕高度(dp)----->" + ScreenUtils.getScreenHeightDP(mContext));
    }

    /**
     * 初始化监听
     */
    public void initListener() {

    }


}
