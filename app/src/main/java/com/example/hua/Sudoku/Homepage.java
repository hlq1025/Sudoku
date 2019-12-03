package com.example.hua.Sudoku;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {
    private Button easy_mode;
    private Button middle_mode;
    private Button hard_mode;
    private double difficulty_index;
    private   Intent intent;
    private HintDialog hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomDensity(this,getApplication());
        setContentView(R.layout.homepage);
        init();
        SetListeners();
    }

    private void init()
    {
       easy_mode=   findViewById(R.id.easy_mode);
       middle_mode=findViewById(R.id.middle_mode);
       hard_mode=findViewById(R.id.hard_mode);
       intent = new Intent(Homepage.this , MainActivity.class);
        hint = new HintDialog(this, 0, 0,  R.style.DialogTheme);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
    }

    private void SetListeners()
    {
     easy_mode.setOnClickListener(new View.OnClickListener()
                             {
                                 @Override
                                 public void onClick(View v)
                                 {

                                     difficulty_index=0.3;
                                     intent.putExtra("difficulty_index",difficulty_index);
                                     intent.putExtra("hard_level","简单");

                                     String[]ans=LoadInformation("简单");

                                     if(ans[0].equals("true"))
                                     {
                                         showAlterDialog();


                                     }
                                     else
                                     {

                                         startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                                     }



                             }
                             }
                             );
        middle_mode.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick(View v)
                                         {

                                             difficulty_index=0.5;
                                             intent.putExtra("difficulty_index",difficulty_index);
                                             intent.putExtra("hard_level","中等");
                                             String[]ans=LoadInformation("中等");

                                             if(ans[0].equals("true"))
                                             {
                                                 showAlterDialog();


                                             }
                                             else
                                             {

                                                 startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                                             }


                                         }
                                     }
        );
        hard_mode.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick(View v)
                                         {

                                             difficulty_index=0.7;
                                             intent.putExtra("difficulty_index",difficulty_index);
                                             intent.putExtra("hard_level","困难");
                                             String[]ans=LoadInformation("困难");

                                             if(ans[0].equals("true"))
                                             {
                                                 showAlterDialog();


                                             }
                                             else
                                             {

                                                 startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                                             }


                                         }
                                     }
        );
        hint.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(HintDialog.load_last_game==true)  intent.putExtra("load_last_game",1);
                else  intent.putExtra("load_last_game",0);
                startActivity(intent);
            }});

    }
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
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            // 监听字体切换，防止系统切换后不起作用
           /* application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });*/
        }

        // 目标密度 屏幕宽度(px) / 设计图的宽度(dp), 这里是以小米6X为例
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
    private void showAlterDialog(){

        hint.setCancelable(true);
       hint.show();

    }
    public String[] LoadInformation(String hard_level) {
        String ans[] = new String[3];


        SharedPreferences sharedPreferences =  getSharedPreferences(hard_level, 0);

        boolean CanCon = sharedPreferences.getBoolean("可继续", false);

        if (CanCon) ans[0] = "true";

        else ans[0] = "false";

        ans[1] =String.valueOf(sharedPreferences.getLong("用时", 0));



        ans[2] = sharedPreferences.getString("数字表", "");

        return ans;

    }

}