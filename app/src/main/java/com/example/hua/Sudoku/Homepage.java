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
    }

    private void SetListeners()
    {
easy_mode.setOnClickListener(new View.OnClickListener()
                             {
                                 @Override
                                 public void onClick(View v)
                                 {
                                     Intent intent = new Intent(Homepage.this , MainActivity.class);
                                     difficulty_index=0.3;
                                     intent.putExtra("diffculty_index",difficulty_index);
                                     startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动
                             }
                             }
);
    }
}