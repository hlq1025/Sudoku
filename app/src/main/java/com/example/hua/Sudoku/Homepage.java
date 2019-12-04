package com.example.hua.Sudoku;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
        Density.setCustomDensity(this,getApplication());
        setContentView(R.layout.homepage);
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