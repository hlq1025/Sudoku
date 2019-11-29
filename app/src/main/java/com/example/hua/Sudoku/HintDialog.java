package com.example.hua.Sudoku;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class HintDialog extends Dialog {
    private Button confirmbtn;
    private Button cancelbtn;

    public static  boolean load_last_game;
    //    style引用style样式
    public HintDialog(Context context, int width, int height, int style) {

        super(context, style);

        setContentView(R.layout.hintdialog);
        final Button confirmbtn=findViewById(R.id.confirm_btn);

        final Button cancelbtn=findViewById(R.id.cancel_btn);


        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             load_last_game=true;

               cancel();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              load_last_game=false;

              cancel();
            }
        });



        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        window.setAttributes(params);


    }

}
