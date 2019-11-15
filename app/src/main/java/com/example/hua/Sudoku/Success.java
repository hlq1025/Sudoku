package com.example.hua.Sudoku;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Success extends AppCompatActivity {

    private Button back_to_homepage_btn;
    private Button con_game_btn;
    private TextView this_hard_level;
    private TextView this_record_time;
    private TextView record_fastest_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        init();
        SetListeners();

    }
    private void init()
    {
        back_to_homepage_btn=findViewById(R.id.back_to_homepage_btn);
        con_game_btn=findViewById(R.id.con_game_btn);
        this_hard_level=findViewById(R.id.this_hard_level);
        this_record_time=findViewById(R.id.this_record_time);
        record_fastest_time=findViewById(R.id.record_fastest_time);
        this_hard_level.setText(getIntent().getExtras().get("this_hard_level").toString());
        this_record_time.setText(getIntent().getExtras().get("this_record_time").toString());
        SQLiteDatabase writableDatabase = new RecordTable(this).getWritableDatabase();
        //修改
        ContentValues cv=new ContentValues();

        Cursor cursor = writableDatabase.query("record",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String s2 = cursor.getString(cursor.getColumnIndex("hard_level"));
                String s3=this_hard_level.getText().toString();
                Log.d("----------", cursor.getString(cursor.getColumnIndex("hard_level")));
                Log.d("----------", cursor.getString(cursor.getColumnIndex("time")));
                if(s2.equals(s3))
                {   String s4=cursor.getString(cursor.getColumnIndex("time"));
                    record_fastest_time.setText(s4);
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
        back_to_homepage_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Success.this , Homepage.class);

                startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
            }
        });
        con_game_btn.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick(View v)
                                         {
                                             Intent intent = new Intent(Success.this , MainActivity.class);

                                             double difficulty_index;
                                             String hard_level=this_hard_level.getText().toString();
                                             if(hard_level.equals("简单"))
                                                 difficulty_index=0.1;
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
