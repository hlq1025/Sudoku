package com.example.hua.Sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyDialog extends Dialog {

    private Button ExitBn;
    private Button RestartBn;

    public MyDialog( Context context) {
        super(context);

    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_dialog);
        ExitBn=findViewById(R.id.exitBtn);
        RestartBn=findViewById(R.id.restartBtn);
        ExitBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 响应事件
                android.os.Process.killProcess(android.os.Process.myPid());

            }});

        RestartBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 响应事件
                dismiss();
                Sudoku.InitSudoku();


            }});
        }

    }



