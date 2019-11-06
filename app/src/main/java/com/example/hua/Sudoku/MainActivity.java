package com.example.hua.Sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.DialogInterface;
public class MainActivity extends AppCompatActivity {

    private Button[][] edittexts;
    private Button[][]   buttons;

    private MyDialog  dialog;
    private Intent intent;
    private Boolean flag;
    private int num;
    private TextView score_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    private void init() {
        TableLayout tablelayout=(TableLayout) findViewById(R.id.tablelayout);
        tablelayout.setGravity(100);
        tablelayout.setStretchAllColumns(true);
        flag=true;
        score_tv=findViewById(R.id.textView7);
        edittexts=new Button[9][9];

        dialog=new MyDialog(MainActivity.this);


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                score_tv.setText("0");
                Sudoku.InitSudoku();
                for(int row=0;row<9;row++)
                    for(int col=0;col<9;col++)
                    {
                        char c=Sudoku.board[row][col];
                        if(c>'0'&&c<='9') {
                            edittexts[row][col].setText( String.valueOf(c));

                        }
                        else
                            edittexts[row][col].setText("");
                    }
            }});


        for(int row=0;row<9;row++)
        {
            TableRow tableRow=new TableRow(MainActivity.this);
            for(int col=0;col<9;col++)
            {
                edittexts[row][col]=new Button(MainActivity.this);
                //edittexts[row][col].setLayoutParams();

                edittexts[row][col].setEnabled(true);
                edittexts[row][col].setGravity(Gravity.CENTER);
                edittexts[row][col].setBackground(getResources().getDrawable(R.drawable.button_normal));

                Sudoku.InitSudoku();
                char c=Sudoku.board[row][col];
                if(c>'0'&&c<='9') {
                    edittexts[row][col].setText( String.valueOf(c));

                }
                final Button b=edittexts[row][col];
                final int row_t=row;
                final int col_t=col;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 响应事件

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(MainActivity.this,"请输入数字",Toast.LENGTH_SHORT).show();
                                b.setBackground(getResources().getDrawable(R.drawable.button_pressed));
                                while(flag);
                                b.setText(String.valueOf(num));
                                b.setBackground(getResources().getDrawable(R.drawable.button_normal));
                                Sudoku.board[row_t][col_t]=(char) (48+num);
                                flag=true;

                                refreshMSG();


                            }
                        }).start();

                    }
                });


                tableRow.addView(edittexts[row][col],new TableRow.LayoutParams(120,120));
            }
         tablelayout.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }
        TableLayout tablelayout2=(TableLayout) findViewById(R.id.tablelayout2);
        buttons=new Button[3][3];
        for(int row=0;row<3;row++)
        {
            TableRow tableRow=new TableRow(MainActivity.this);
            for(int col=0;col<3;col++)
          {
            buttons[row][col]=new Button(this);
            buttons[row][col].setGravity(Gravity.CENTER);
            buttons[row][col].setText(String.valueOf(row*3+col+1));
            buttons[row][col].setWidth(100);
            buttons[row][col].setHeight(100);
            tableRow.addView(buttons[row][col],new TableRow.LayoutParams(150,150));
              final Button b1=buttons[row][col];
              b1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // 响应事件

                      new Thread(new Runnable() {
                          @Override
                          public void run() {
                              num=Integer.parseInt(b1.getText().toString());
                              flag=false;

                          }
                      }).start();


                  }
              });
          }
          tablelayout2.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }


    }
    public void refreshMSG() {
        Message msg = Message.obtain();

        if(Sudoku.isValidSudoku())
        {

            String s=score_tv.getText().toString();

            int score= Integer.parseInt(s);

            score=score+5;
            msg.arg1 = score;//Message类有属性字段arg1、arg2、what...


        //消息要先传进Message中，再由Message传递给Handler处理


    }
    else
        {
            msg.arg1=-1;


        }
        mHandler.sendMessage(msg);//sendMessage()用来传送Message类的值到mHandler

    }


    Handler mHandler = new Handler()
    {

        //handleMessage为处理消息的方法
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
                if(msg.arg1==-1)
                {


                    dialog.show();
                }
                else

                {
                    score_tv.setText(msg.arg1 + "");
                }
        }
    };








    }


