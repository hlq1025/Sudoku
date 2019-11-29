package com.example.hua.Sudoku;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
public class Menu extends AppCompatActivity {


    private Button continue_btn;

    private Button homepage_btn;

    private TextView easy_time_record;

    private TextView middle_time_record;

    private TextView hard_time_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setCustomDensity(this,getApplication());
        setContentView(R.layout.menu);
        init();
        SetListeners();
    }
    private void init()
    {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        continue_btn=findViewById(R.id.continue_btn);

     homepage_btn=findViewById(R.id.homepage_btn);

     easy_time_record=findViewById(R.id.easy_time_record);

     middle_time_record=findViewById(R.id.middle_time_record);

     hard_time_record=findViewById(R.id.hard_time_record);
    }
    private void SetListeners()
    {
      continue_btn.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v)
                                          {
                                              finish();
                                          }
                                      }

      );

      homepage_btn.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View v)
          {
              Intent intent = new Intent(Menu.this , Homepage.class);
              MainActivity.instance.finish();
              startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
          }
      });

        SQLiteDatabase sqLiteDatabase = new RecordTable(this).getWritableDatabase();

        //搜索全表字段，null代表搜索条件
        Cursor cursor = sqLiteDatabase.query("record", null, null, null, null, null, null);

        //查询到的结果条数c.getCount();
        //遍历结果
        while (cursor.moveToNext()) {
            String s2 = cursor.getString(cursor.getColumnIndex("hard_level"));

            if(s2.equals("简单"))
            {easy_time_record.setText(cursor.getString(cursor.getColumnIndex("time")));

            }
            else if(s2.equals("中等"))
            {
                middle_time_record.setText(cursor.getString(cursor.getColumnIndex("time")));

            }
            else
            {
                hard_time_record.setText(cursor.getString(cursor.getColumnIndex("time")));

            }


        }

        //关闭连接
        sqLiteDatabase.close();
        cursor.close();

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
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        // 目标密度 屏幕宽度(px) / 设计图的宽度(dp), 这里是以小米6X为例
        float targetDensity = appDisplayMetrics.heightPixels/711;
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

