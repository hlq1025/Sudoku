package com.example.hua.Sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
public class Homepage extends AppCompatActivity {
    private Button easy_mode;
    private Button middle_mode;
    private Button hard_mode;
    private double difficulty_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.homepage);
        init();
        SetListeners();
    }

    private void init()
    {
easy_mode=   findViewById(R.id.easy_mode);
middle_mode=findViewById(R.id.middle_mode);
hard_mode=findViewById(R.id.hard_mode);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
    }

    private void SetListeners()
    {
     easy_mode.setOnClickListener(new View.OnClickListener()
                             {
                                 @Override
                                 public void onClick(View v)
                                 {
                                     Intent intent = new Intent(Homepage.this , MainActivity.class);
                                     difficulty_index=0.1;
                                     intent.putExtra("difficulty_index",difficulty_index);
                                     intent.putExtra("hard_level","简单");
                                     startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                             }
                             }
                             );
        middle_mode.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick(View v)
                                         {
                                             Intent intent = new Intent(Homepage.this , MainActivity.class);
                                             difficulty_index=0.5;
                                             intent.putExtra("difficulty_index",difficulty_index);
                                             intent.putExtra("hard_level","中等");
                                             startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                                         }
                                     }
        );
        hard_mode.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick(View v)
                                         {
                                             Intent intent = new Intent(Homepage.this , MainActivity.class);
                                             difficulty_index=0.7;
                                             intent.putExtra("difficulty_index",difficulty_index);
                                             intent.putExtra("hard_level","困难");
                                             startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                                         }
                                     }
        );

    }
}