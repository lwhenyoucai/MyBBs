package com.example.lwhenyoucai.mysamplebbs;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by lwhenyoucai on 2018/5/8.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;
    public static Context mContext;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = this;
        initScreenSize();
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                /*.setErrorImage(R.mipmap.define_error)
                .setEmptyImage(R.mipmap.define_empty)
                .setNoNetworkImage(R.mipmap.define_nonetwork)
                .setAllTipTextColor(R.color.gray)*/
                .setAllTipTextSize(14)
                .setReloadButtonText("点击重试")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.word_gray)
                /*.setReloadButtonWidthAndHeight(150, 40)
                .setAllPageBackgroundColor(R.color.background);*/
                .setLoadingPageLayout(R.layout.loading_layout);
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
}
