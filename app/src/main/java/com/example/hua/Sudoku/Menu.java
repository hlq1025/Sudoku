package com.example.hua.Sudoku;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {


    private Button continue_btn;

    private Button homepage_btn;

    private TextView easy_time_record;

    private TextView middle_time_record;

    private TextView hard_time_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Density.setCustomDensity(this,getApplication());
        setContentView(R.layout.menu);
        init();
        SetListeners();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "返回键已被屏蔽", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event); }
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

        SQLiteDatabase sqLiteDatabase =  RecordTable.Get_Instance(this).getWritableDatabase();

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

}

