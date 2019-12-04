package com.example.hua.Sudoku;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

public class Density {
    /**
     * 今日头条适配方案  修改设备密度
     * 支持以宽或者高一个维度去适配，保持该维度上和设计图一致
     * 当前设备总宽度(px) / 设计图总宽度(dp) = density
     * density: 1dp 占当前设备的多少像素
     * 在BaseActivity的onCreate()中引用即可
     * @param activity
     * @param application
     */

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {

        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            // 系统显示器的逻辑密度
            sNoncompatDensity = appDisplayMetrics.density;
            // 字体的缩放系数，与density相同
            sNoncompatScaledDensity = appDisplayMetrics.density;

        }

        // 目标密度 屏幕宽度(px) / 设计图的宽度(dp).
        float targetDensity = appDisplayMetrics.heightPixels/ (float) 776;
        // 目标缩放密度
        float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        // 目标DPI
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}





