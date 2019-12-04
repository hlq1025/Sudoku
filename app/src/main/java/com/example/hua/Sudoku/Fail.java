package com.example.hua.Sudoku;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Fail extends AppCompatActivity {

    private Button back_to_homepage_btn_fail;
    private Button con_game_btn_fail;
    private TextView this_hard_level_fail;

    private TextView record_fastest_time_fail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Density.setCustomDensity(this,getApplication());
        setContentView(R.layout.fail);
        init();
        SetListeners();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        return true;//消费掉后退键
                }
              return super.onKeyDown(keyCode, event); }



    private void init()
    {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        back_to_homepage_btn_fail=findViewById(R.id.back_to_homepage_btn_fail);
        con_game_btn_fail=findViewById(R.id.con_game_btn_fail);
        this_hard_level_fail=findViewById(R.id.this_hard_level_fail);

        record_fastest_time_fail=findViewById(R.id.record_fastest_time_fail);
        this_hard_level_fail.setText(getIntent().getExtras().get("this_hard_level").toString());

        SQLiteDatabase writableDatabase = RecordTable.Get_Instance(this).getWritableDatabase();
        //修改
        ContentValues cv=new ContentValues();

        Cursor cursor = writableDatabase.query("record",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String s2 = cursor.getString(cursor.getColumnIndex("hard_level"));
                String s3=this_hard_level_fail.getText().toString();
                Log.d("----------", cursor.getString(cursor.getColumnIndex("hard_level")));
                Log.d("----------", cursor.getString(cursor.getColumnIndex("time")));
                if(s2.equals(s3))
                {   String s4=cursor.getString(cursor.getColumnIndex("time"));
                    record_fastest_time_fail.setText(s4);
                    break;
                }
            }while (cursor.moveToNext());
        }
        else
        {
            Log.d("----------", "onClick: 数据库为空");
        }
        //关闭连接
        writableDatabase.close();



    }
    private void SetListeners()
    {
        back_to_homepage_btn_fail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Fail.this , Homepage.class);

                startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
            }
        });
        con_game_btn_fail.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                Intent intent = new Intent(Fail.this , MainActivity.class);

                                                double difficulty_index;
                                                String hard_level=this_hard_level_fail.getText().toString();
                                                if(hard_level.equals("简单"))
                                                    difficulty_index=0.3;
                                                else if(hard_level.equals("中等"))
                                                    difficulty_index=0.5;
                                                else
                                                    difficulty_index=0.7;
                                                intent.putExtra("difficulty_index",difficulty_index);
                                                intent.putExtra("hard_level",hard_level);
                                                startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                                            }
                                        }
        );
    }
}
