package com.example.hua.Sudoku;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
public class MainActivity extends AppCompatActivity {

    private RecordTable Rt;
    private Button[][] edittexts;
    private Button[]  number_buttons;
    private Button clear_button;
    private Button pause_button;
    private Button hint_button;
    private Button menu_button;
    private MyDialog  dialog;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    private void init() {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        Rt=new RecordTable(this);
        SQLiteDatabase writableDatabase = Rt.getWritableDatabase();
        ContentValues values = new ContentValues();
/*
        values.put("hard_level","简单");
        values.put("time","00:01");
        writableDatabase.insert("record",null,values);
        values.clear();

        values.put("hard_level","中等");
        values.put("time","00:02");
        writableDatabase.insert("record",null,values);
        values.clear();

        values.put("hard_level","困难");
        values.put("time","00:03");
        writableDatabase.insert("record",null,values);
        values.clear();
        */
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
        dialog=new MyDialog(MainActivity.this);
        clear_button=findViewById(R.id.clear_button);
        pause_button=findViewById(R.id.pause_button);
        hint_button=findViewById(R.id.hint_button);
        menu_button=findViewById(R.id.menu_button);
        chronometer=   (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();
        Sudoku.InitSudoku(difficulty_index);
        for(int row=0;row<9;row++)
        {
            TableRow tableRow=new TableRow(MainActivity.this);
            for(int col=0;col<9;col++)
            {
                edittexts[row][col]=new Button(MainActivity.this);
                //edittexts[row][col].setLayoutParams();

                edittexts[row][col].setEnabled(true);
                edittexts[row][col].setGravity(Gravity.CENTER);
                edittexts[row][col].setTextSize(18);
                edittexts[row][col].setTextColor(Color.rgb(28,159,93));
                SetButtonStyle(edittexts,row,col);


                char c=Sudoku.board[row][col];
                if(c>'0'&&c<='9') {
                    edittexts[row][col].setText( String.valueOf(c));
                    edittexts[row][col].setEnabled(false);
                }
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


                tableRow.addView(edittexts[row][col],new TableRow.LayoutParams(110,110));
            }
         tablelayout.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }
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
                          Sudoku.board[pressed_row][pressed_col] = (char) (48 + num);
                          if (!Sudoku.CanGetSolution()) {
                              dialog.show();
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
    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

            Sudoku.InitSudoku(difficulty_index);
            last_pressed_row=-1;
            last_pressed_col=-1;
            pressed_row=-1;
            pressed_col=-1;
            for(int row=0;row<9;row++)
                for(int col=0;col<9;col++)
                {
                    char c=Sudoku.board[row][col];
                    SetButtonStyle(edittexts,row,col);
                    if(c>'0'&&c<='9') {
                        edittexts[row][col].setText( String.valueOf(c));
                        edittexts[row][col].setEnabled(false);

                    }
                    else
                    {edittexts[row][col].setText("");
                        edittexts[row][col].setEnabled(true);
                    }
                }
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        }});
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
                                          {int ans=Sudoku.GetHint(pressed_row,pressed_col);
                                           edittexts[pressed_row][pressed_col].setText(String.valueOf(ans));
                                           Sudoku.board[pressed_row][pressed_col]=(char)(48+ans);
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




}


