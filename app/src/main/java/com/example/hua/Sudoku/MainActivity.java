package com.example.hua.Sudoku;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    private RecordTable Rt;
    private Button[][] edittexts;
    private Button[]  number_buttons;
    private Button clear_button;
    private Button pause_button;
    private Button hint_button;
    private Button menu_button;

    private Chronometer chronometer;
    private Intent intent;
    private TextView sudoku;
    private TextView time_record;
    private TextView hard_level;
    private int num;
    private int last_pressed_row;
    private int last_pressed_col;
    private int pressed_row;
    private int pressed_col;
    private long recordingTime = 0;// 记录下来的总时间
    private double difficulty_index;
    private Sudoku suk;
    public static MainActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setCustomDensity(this,getApplication());
        setContentView(R.layout.activity_main);
        instance=this;
        init();



    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        long base_time= SystemClock.elapsedRealtime() - chronometer.getBase();
     String this_hard_level=hard_level.getText().toString();
     String []nums=new String[81];
     boolean full=true;
     boolean flag=true;
     for(int i=0;i<9;i++)
         for(int j=0;j<9;j++)
         {
            String temp=edittexts[i][j].getText().toString();
           if(temp.length()!=0)  nums[i*9+j]= temp;
           else nums[i*9+j]=".";
            if(temp.equals("")) full=false;
         }


        flag= suk.CanGetSolution() &&(!full);
     SaveInformation(base_time,this_hard_level,nums,flag);

    }
    private void init()
    {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        Rt=new RecordTable(this);
        suk=new Sudoku();
        SQLiteDatabase writableDatabase = Rt.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("hard_level","简单");
        values.put("time","无");
        writableDatabase.insert("record",null,values);
        values.clear();

        values.put("hard_level","中等");
        values.put("time","无");
        writableDatabase.insert("record",null,values);
        values.clear();

        values.put("hard_level","困难");
        values.put("time","无");
        writableDatabase.insert("record",null,values);
        values.clear();

/*
        values.put("hard_level","简单");
        values.put("time","??");
        writableDatabase.update("record",values,"hard_level=?",new String[]{"简单"});
        values.clear();

        values.put("hard_level","中等");
        values.put("time","??");
        writableDatabase.update("record",values,"hard_level=?",new String[]{"中等"});
        values.clear();

        values.put("hard_level","困难");
        values.put("time","??");
        writableDatabase.update("record",values,"hard_level=?",new String[]{"困难"});
        values.clear();
*/
        Object obj=getIntent().getExtras().get("difficulty_index");
        difficulty_index= Double.parseDouble(obj.toString());
        time_record=findViewById(R.id.time_record);

        hard_level=findViewById(R.id.hard_level);
        obj=getIntent().getExtras().get("hard_level");
        hard_level.setText(obj.toString());
        Cursor cursor = writableDatabase.query("record",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String s2 = cursor.getString(cursor.getColumnIndex("hard_level"));
                String s3=obj.toString();
                if(s2.equals(s3))
               {time_record.setText(cursor.getString(cursor.getColumnIndex("time")));
               break;
               }


            }while (cursor.moveToNext());
        }else {
            Log.d("----------", "onClick: 数据库为空");
        }
        TableLayout tablelayout=(TableLayout) findViewById(R.id.tablelayout);
        tablelayout.setGravity(100);
        tablelayout.setStretchAllColumns(true);
        last_pressed_row=-1;
        last_pressed_col=-1;
        pressed_row=-1;
        pressed_col=-1;
        edittexts=new Button[9][9];

        clear_button=findViewById(R.id.clear_button);
        pause_button=findViewById(R.id.pause_button);
        hint_button=findViewById(R.id.hint_button);
        menu_button=findViewById(R.id.menu_button);
        chronometer=   (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();

        for(int row=0;row<9;row++)
        {
            TableRow tableRow=new TableRow(MainActivity.this);
            for(int col=0;col<9;col++)
            {
                edittexts[row][col]=new Button(MainActivity.this);
                //edittexts[row][col].setLayoutParams();
                edittexts[row][col].setEnabled(true);
                edittexts[row][col].setGravity(Gravity.CENTER);
                edittexts[row][col].setTextSize(15);
                edittexts[row][col].setTextColor(Color.rgb(28,159,93));


                SetButtonStyle(edittexts,row,col);
                final Button b=edittexts[row][col];
                final int row_t=row;
                final int col_t=col;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 响应事件
                        pressed_row=row_t;
                        pressed_col=col_t;
                        if(last_pressed_row!=-1&&last_pressed_col!=-1&&(!(last_pressed_row==pressed_row&&last_pressed_col==pressed_col))) {
                            //set button style for the last pressed button , the same column buttons ,the same row buttons and the same region buttons
                            for(int i=0;i<9;i++)
                            {
                                SetButtonStyle(edittexts,i,last_pressed_col);
                                SetButtonStyle(edittexts,last_pressed_row,i);
                            }
                            int last_left_pos=last_pressed_row/3*3;
                            int last_up_pos=last_pressed_col/3*3;
                            for(int i=last_left_pos;i<last_left_pos+3;i++)
                                for(int j=last_up_pos;j<last_up_pos+3;j++)
                                    SetButtonStyle(edittexts,i,j);

                            if(edittexts[last_pressed_row][last_pressed_col].getText().length()==0)
                                edittexts[last_pressed_row][last_pressed_col].setEnabled(true);
                        }

                        //set button style for the pressed button , the same column buttons ,the same row buttons and the same region buttons
                        for(int i=0;i<9;i++)
                        {
                            SetButtonStyle_Ref(edittexts,i,pressed_col);
                            SetButtonStyle_Ref(edittexts,pressed_row,i);
                        }
                        int left_pos=pressed_row/3*3;
                        int up_pos=pressed_col/3*3;
                        for(int i=left_pos;i<left_pos+3;i++)
                            for(int j=up_pos;j<up_pos+3;j++)
                                SetButtonStyle_Ref(edittexts,i,j);

                        SetButtonStyle_Pressed(edittexts,pressed_row,pressed_col);

                        edittexts[pressed_row][pressed_col].setEnabled(false);

                        last_pressed_row=pressed_row;
                        last_pressed_col=pressed_col;


                    }
                });
                //
                int width= (int) (getApplication().getResources().getDisplayMetrics().density*40);

                int height=width;

             tableRow.addView(edittexts[row][col],new TableRow.LayoutParams(width,height));
            }
         tablelayout.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }
        String []ans=LoadInformation(hard_level.getText().toString());
        if(ans[0].equals("true"))
        {
           Object obj1=getIntent().getExtras().get("load_last_game");
           String s=obj1.toString();
           if (obj1!=null&&Integer.parseInt(s)==1)

          {  chronometer.setBase(SystemClock.elapsedRealtime() - Long.parseLong(ans[1]));// 跳过已经记录了的时间，起到继续计时的作用
            chronometer.start();
            String regularEx = "#";
            String[] str = null;

            str = ans[2].split(regularEx);

            for(int row=0;row<9;row++)
            {
                for(int col=0;col<9;col++)
                {
                   if (!str[row*9+col].equals(".")) edittexts[row][col].setText(str[row*9+col]);
                   else edittexts[row][col].setText("");

                   suk.board[row][col]=str[row*9+col].charAt(0);

                }
            }


        }

        else
      {
        ClearInformation();
        SetNumsOfGrids();
      }
      }
        else
            SetNumsOfGrids();
        TableLayout tablelayout2=(TableLayout) findViewById(R.id.tablelayout2);
        number_buttons=new Button[9];


        TableRow tableRow=new TableRow(MainActivity.this);
        for(int col=0;col<9;col++)
          {
            number_buttons[col]=new Button(this);
            number_buttons[col].setGravity(Gravity.CENTER);
            number_buttons[col].setText(String.valueOf(col+1));
            number_buttons[col].setTextSize(25);

            number_buttons[col].setBackgroundColor(000000);
            number_buttons[col].setTextColor(Color.rgb(28,159,93));
            number_buttons[col].setWidth(100);
            number_buttons[col].setHeight(100);
            tableRow.addView(number_buttons[col],new TableRow.LayoutParams(112,150));
            final Button b1=number_buttons[col];
            b1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // 响应事件
                      num=Integer.parseInt(b1.getText().toString());
                      if(pressed_row!=-1&&pressed_col!=-1) {
                          edittexts[pressed_row][pressed_col].setText(String.valueOf(num));
                          suk.board[pressed_row][pressed_col] = (char) (48 + num);
                          if (!suk.CanGetSolution()) {
                              Intent intent = new Intent(MainActivity.this , Fail.class);
                              intent.putExtra("this_hard_level",hard_level.getText().toString());
                              intent.putExtra("this_record_time",chronometer.getText().toString());
                              ClearInformation();
                              startActivity(intent);
                          }
                          boolean IsFull=true;
                          for(int i=0;i<9;i++)
                              for(int j=0;j<9;j++)
                              {
                                  if(edittexts[i][j].getText().length()==0)
                                      IsFull=false;
                              }
                              if(IsFull)
                              {
                                  SQLiteDatabase writableDatabase = Rt.getWritableDatabase();
                                  //修改
                                  ContentValues cv=new ContentValues();
                                  cv.put("time", chronometer.getText().toString());
                                  Cursor cursor = writableDatabase.query("record",null,null,null,null,null,null);
                                  if(cursor.moveToFirst()){
                                      do{
                                          String s2 = cursor.getString(cursor.getColumnIndex("hard_level"));
                                          String s3=hard_level.getText().toString();
                                          Log.d("----------", cursor.getString(cursor.getColumnIndex("hard_level")));
                                          Log.d("----------", cursor.getString(cursor.getColumnIndex("time")));
                                          if(s2.equals(s3))
                                          {   String s4=cursor.getString(cursor.getColumnIndex("time"));
                                              if(s4.equals("??")||s4.compareTo(chronometer.getText().toString())>0)
                                              {
                                                  writableDatabase.update("record",cv,"hard_level=?",new String[]{hard_level.getText().toString()});
                                              }
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
                                  Intent intent = new Intent(MainActivity.this , Success.class);
                                  intent.putExtra("this_hard_level",hard_level.getText().toString());
                                  intent.putExtra("this_record_time",chronometer.getText().toString());
                                  ClearInformation();
                                  startActivity(intent);
                              }
                      }
                      }
              });
          }
          tablelayout2.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        SetListeners();
        }



private void SetListeners()
{

    clear_button.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            if(pressed_row!=-1&&pressed_col!=-1)
                                            {
                                                edittexts[pressed_row][pressed_col].setText("");
                                                edittexts[pressed_row][pressed_col].setEnabled(true);

                                                for(int i=0;i<9;i++)
                                                {
                                                    SetButtonStyle(edittexts,i,pressed_col);
                                                    SetButtonStyle(edittexts,pressed_row,i);
                                                }
                                                int left_pos=pressed_row/3*3;
                                                int up_pos=pressed_col/3*3;
                                                for(int i=left_pos;i<left_pos+3;i++)
                                                    for(int j=up_pos;j<up_pos+3;j++)
                                                        SetButtonStyle(edittexts,i,j);

                                            }

                                        }
                                    }

    );
    pause_button.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            if(pause_button.getText()=="暂停") {
                                                chronometer.stop();
                                                recordingTime = SystemClock.elapsedRealtime() - chronometer.getBase();// 保存这次记录了的时间
                                                pause_button.setText("继续");
                                                for(int row=0;row<9;row++)
                                                    for(int col=0;col<9;col++)
                                                    {
                                                        edittexts[row][col].setEnabled(false);
                                                    }
                                                for(int col=0;col<9;col++)
                                                    number_buttons[col].setEnabled(false);
                                                clear_button.setEnabled(false);
                                                hint_button.setEnabled(false);
                                            }
                                            else
                                            {
                                                chronometer.setBase(SystemClock.elapsedRealtime() - recordingTime);// 跳过已经记录了的时间，起到继续计时的作用
                                                chronometer.start();
                                                pause_button.setText("暂停");
                                                for(int row=0;row<9;row++)
                                                    for(int col=0;col<9;col++)
                                                    {   if(edittexts[row][col].getText().length()==0)

                                                        edittexts[row][col].setEnabled(true);
                                                    }
                                                for(int col=0;col<9;col++)
                                                    number_buttons[col].setEnabled(true);
                                                clear_button.setEnabled(true);
                                                hint_button.setEnabled(true);
                                            }
                                        }
                                    }
    );
    hint_button.setOnClickListener(new View.OnClickListener()
                                   {
                                       @Override
                                       public void onClick(View v)
                                       {
                                          if(pressed_row!=-1&&pressed_col!=-1)
                                          {int ans=suk.GetHint(pressed_row,pressed_col);
                                           edittexts[pressed_row][pressed_col].setText(String.valueOf(ans));
                                           suk.board[pressed_row][pressed_col]=(char)(48+ans);
                                           edittexts[pressed_row][pressed_col].setEnabled(false);
                                              boolean IsFull=true;
                                              for(int i=0;i<9;i++)
                                                  for(int j=0;j<9;j++)
                                                  {
                                                      if(edittexts[i][j].getText().length()==0)
                                                          IsFull=false;
                                                  }
                                              if(IsFull)
                                              {
                                                  SQLiteDatabase writableDatabase = Rt.getWritableDatabase();
                                                  //修改
                                                  ContentValues cv=new ContentValues();
                                                  cv.put("time", chronometer.getText().toString());
                                                  Cursor cursor = writableDatabase.query("record",null,null,null,null,null,null);
                                                  if(cursor.moveToFirst()){
                                                      do{
                                                          String s2 = cursor.getString(cursor.getColumnIndex("hard_level"));
                                                          String s3=hard_level.getText().toString();
                                                          Log.d("----------", cursor.getString(cursor.getColumnIndex("hard_level")));
                                                          Log.d("----------", cursor.getString(cursor.getColumnIndex("time")));
                                                          if(s2.equals(s3))
                                                          {   String s4=cursor.getString(cursor.getColumnIndex("time"));
                                                              if(s4.equals("无")||s4.compareTo(chronometer.getText().toString())>0)
                                                              {
                                                                  writableDatabase.update("record",cv,"hard_level=?",new String[]{hard_level.getText().toString()});
                                                              }
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
                                                  Intent intent = new Intent(MainActivity.this , Success.class);
                                                  intent.putExtra("this_hard_level",hard_level.getText().toString());
                                                  intent.putExtra("this_record_time",chronometer.getText().toString());
                                                  ClearInformation();
                                                  startActivity(intent);
                                          }
                                       }
                                   }}
    );

    menu_button.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this , Menu.class);

            startActivity(intent);//然后调用Activity类提供的startAcitivity()方法，用于启动一个活动


        }
    }
    );
}
private void SetNumsOfGrids()
{
    suk.InitSudoku(difficulty_index);
    for(int row=0;row<9;row++)
    {

        for(int col=0;col<9;col++)
        {
            char c=suk.board[row][col];
            if(c>'0'&&c<='9') {
                edittexts[row][col].setText( String.valueOf(c));
                edittexts[row][col].setEnabled(false);
            }

        }

    }
}

private void SetButtonStyle(Button[][] buttons,int row,int col)
{
    int count=row%3*10+col%3;
    switch (count)
    {
        case 0:
        {
            if(row==0&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge0));
            else if (row==0&&col!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge1));
            else if(col==0&&row!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge2));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top));
            break;
        }
        case 1:
        {
            if(row==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_top_edge));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_top));break;
        }
        case 2:{
            if(row==0&&col!=8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge0));
            else if(row==0&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge1));
            else if(row!=0&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge2));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top));break;}
        case 10:{
            if(col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_edge));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left));break;}
        case 11:{buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_center));break;}
        case 12:{
            if(col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_edge));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right));break;
        }
        case 20:{
            if(row!=8&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge0));
            else if(row==8&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge1));
            else if(row==8&&col!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge2));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom));break;}
        case 21:{
            if(row==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_bottom_edge));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_bottom));break;
        }
        case 22:{
            if(row==8&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge0));
            else if(row==8&&col!=8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge1));
            else if(row!=8&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge2));
            else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom));break;
        }
    }
}



private void SetButtonStyle_Pressed(Button[][] buttons,int row,int col)
    {
        int count=row%3*10+col%3;
        switch (count)
        {
            case 0:
            {
                if(row==0&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge0_pressed));
                else if (row==0&&col!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge1_pressed));
                else if(col==0&&row!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge2_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_pressed));
                break;
            }
            case 1:
            {
                if(row==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_top_edge_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_top_pressed));break;
            }
            case 2:{
                if(row==0&&col!=8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge0_pressed));
                else if(row==0&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge1_pressed));
                else if(row!=0&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge2_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_pressed));break;}
            case 10:{
                if(col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_edge_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_pressed));break;}
            case 11:{buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_center_pressed));break;}
            case 12:{
                if(col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_edge_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_pressed));break;
            }
            case 20:{
                if(row!=8&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge0_pressed));
                else if(row==8&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge1_pressed));
                else if(row==8&&col!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge2_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_pressed));break;}
            case 21:{
                if(row==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_bottom_edge_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_bottom_pressed));break;
            }
            case 22:{
                if(row==8&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge0_pressed));
                else if(row==8&&col!=8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge1_pressed));
                else if(row!=8&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge2_pressed));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_pressed));break;
            }
        }
    }
    private void SetButtonStyle_Ref(Button[][] buttons,int row,int col)
    {
        int count=row%3*10+col%3;
        switch (count)
        {
            case 0:
            {
                if(row==0&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge0_ref));
                else if (row==0&&col!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge1_ref));
                else if(col==0&&row!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_edge2_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_top_ref));
                break;
            }
            case 1:
            {
                if(row==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_top_edge_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_top_ref));break;
            }
            case 2:{
                if(row==0&&col!=8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge0_ref));
                else if(row==0&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge1_ref));
                else if(row!=0&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_edge2_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_top_ref));break;}
            case 10:{
                if(col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_edge_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_ref));break;}
            case 11:{buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_center_ref));break;}
            case 12:{
                if(col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_edge_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_ref));break;
            }
            case 20:{
                if(row!=8&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge0_ref));
                else if(row==8&&col==0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge1_ref));
                else if(row==8&&col!=0) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_edge2_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_left_bottom_ref));break;}
            case 21:{
                if(row==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_bottom_edge_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_bottom_ref));break;
            }
            case 22:{
                if(row==8&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge0_ref));
                else if(row==8&&col!=8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge1_ref));
                else if(row!=8&&col==8) buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_edge2_ref));
                else buttons[row][col].setBackground(getResources().getDrawable(R.drawable.button_right_bottom_ref));break;
            }
        }
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
    public void SaveInformation(long base_time, String hard_level, String[] numbers, boolean flag) {

       SharedPreferences sharedPreferences = getSharedPreferences(hard_level, Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件
        editor.putBoolean("可继续", flag);
        editor.putLong("用时", base_time);


        String regularEx = "#";
        String str = "";

        if (numbers != null && numbers.length > 0) {
            for (String value : numbers) {
                str += value;
                str += regularEx;
            }

            editor.putString("数字表", str);
        }
        editor.commit();
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
public void ClearInformation()
{
    SharedPreferences sharedPreferences = getSharedPreferences(hard_level.getText().toString(), Context.MODE_PRIVATE);
    //步骤2： 实例化SharedPreferences.Editor对象
    SharedPreferences.Editor editor = sharedPreferences.edit();
   editor.clear();
    editor.commit();
}
}


