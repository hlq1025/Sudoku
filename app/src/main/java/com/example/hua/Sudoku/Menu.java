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
        Cursor cursor = sqLiteDatabase.query("record", null, "hard_level=?", new String[]{"简单"}, null, null, null);
        if(cursor.moveToNext())
        {
            easy_time_record.setText(cursor.getString(cursor.getColumnIndex("time")));
        }
        cursor=sqLiteDatabase.query("record", null, "hard_level=?", new String[]{"中等"}, null, null, null);
        if(cursor.moveToNext())
        {
            middle_time_record.setText(cursor.getString(cursor.getColumnIndex("time")));
        }
        cursor=sqLiteDatabase.query("record", null, "hard_level=?", new String[]{"困难"}, null, null, null);
        if(cursor.moveToNext())
        {
            hard_time_record.setText(cursor.getString(cursor.getColumnIndex("time")));
        }
        cursor.close();
        sqLiteDatabase.close();

        }
}

