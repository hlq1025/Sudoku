package com.example.hua.Sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements TextWatcher{

    private EditText editText00;
    private EditText editText01;
    private EditText editText02;
    private EditText editText03;
    private EditText editText04;
    private EditText editText05;
    private EditText editText06;
    private EditText editText07;
    private EditText editText08;
    private EditText editText10;
    private EditText editText11;
    private EditText editText12;
    private EditText editText13;
    private EditText editText14;
    private EditText editText15;
    private EditText editText16;
    private EditText editText17;
    private EditText editText18;
    private EditText editText20;
    private EditText editText21;
    private EditText editText22;
    private EditText editText23;
    private EditText editText24;
    private EditText editText25;
    private EditText editText26;
    private EditText editText27;
    private EditText editText28;
    private EditText editText30;
    private EditText editText31;
    private EditText editText32;
    private EditText editText33;
    private EditText editText34;
    private EditText editText35;
    private EditText editText36;
    private EditText editText37;
    private EditText editText38;
    private EditText editText40;
    private EditText editText41;
    private EditText editText42;
    private EditText editText43;
    private EditText editText44;
    private EditText editText45;
    private EditText editText46;
    private EditText editText47;
    private EditText editText48;
    private EditText editText50;
    private EditText editText51;
    private EditText editText52;
    private EditText editText53;
    private EditText editText54;
    private EditText editText55;
    private EditText editText56;
    private EditText editText57;
    private EditText editText58;
    private EditText editText60;
    private EditText editText61;
    private EditText editText62;
    private EditText editText63;
    private EditText editText64;
    private EditText editText65;
    private EditText editText66;
    private EditText editText67;
    private EditText editText68;
    private EditText editText70;
    private EditText editText71;
    private EditText editText72;
    private EditText editText73;
    private EditText editText74;
    private EditText editText75;
    private EditText editText76;
    private EditText editText77;
    private EditText editText78;
    private EditText editText80;
    private EditText editText81;
    private EditText editText82;
    private EditText editText83;
    private EditText editText84;
    private EditText editText85;
    private EditText editText86;
    private EditText editText87;
    private EditText editText88;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        editText00 = (EditText) findViewById(R.id.editText00);
        editText01 = (EditText) findViewById(R.id.editText01);
        editText02 = (EditText) findViewById(R.id.editText02);
        editText03 = (EditText) findViewById(R.id.editText03);
        editText04 = (EditText) findViewById(R.id.editText04);
        editText05 = (EditText) findViewById(R.id.editText05);
        editText06 = (EditText) findViewById(R.id.editText06);
        editText07 = (EditText) findViewById(R.id.editText07);
        editText08 = (EditText) findViewById(R.id.editText08);
        editText10 = (EditText) findViewById(R.id.editText10);
        editText11 = (EditText) findViewById(R.id.editText11);
        editText12 = (EditText) findViewById(R.id.editText12);
        editText13 = (EditText) findViewById(R.id.editText13);
        editText14 = (EditText) findViewById(R.id.editText14);
        editText15 = (EditText) findViewById(R.id.editText15);
        editText16 = (EditText) findViewById(R.id.editText16);
        editText17 = (EditText) findViewById(R.id.editText17);
        editText18 = (EditText) findViewById(R.id.editText18);
        editText20 = (EditText) findViewById(R.id.editText20);
        editText21 = (EditText) findViewById(R.id.editText21);
        editText22 = (EditText) findViewById(R.id.editText22);
        editText23 = (EditText) findViewById(R.id.editText23);
        editText24 = (EditText) findViewById(R.id.editText24);
        editText25 = (EditText) findViewById(R.id.editText25);
        editText26 = (EditText) findViewById(R.id.editText26);
        editText27 = (EditText) findViewById(R.id.editText27);
        editText28 = (EditText) findViewById(R.id.editText28);
        editText30 = (EditText) findViewById(R.id.editText30);
        editText31 = (EditText) findViewById(R.id.editText31);
        editText32 = (EditText) findViewById(R.id.editText32);
        editText33 = (EditText) findViewById(R.id.editText33);
        editText34 = (EditText) findViewById(R.id.editText34);
        editText35 = (EditText) findViewById(R.id.editText35);
        editText36 = (EditText) findViewById(R.id.editText36);
        editText37 = (EditText) findViewById(R.id.editText37);
        editText38 = (EditText) findViewById(R.id.editText38);
        editText40 = (EditText) findViewById(R.id.editText40);
        editText41 = (EditText) findViewById(R.id.editText41);
        editText42 = (EditText) findViewById(R.id.editText42);
        editText43 = (EditText) findViewById(R.id.editText43);
        editText44 = (EditText) findViewById(R.id.editText44);
        editText45 = (EditText) findViewById(R.id.editText45);
        editText46 = (EditText) findViewById(R.id.editText46);
        editText47 = (EditText) findViewById(R.id.editText47);
        editText48 = (EditText) findViewById(R.id.editText48);
        editText50 = (EditText) findViewById(R.id.editText50);
        editText51 = (EditText) findViewById(R.id.editText51);
        editText52 = (EditText) findViewById(R.id.editText52);
        editText53 = (EditText) findViewById(R.id.editText53);
        editText54 = (EditText) findViewById(R.id.editText54);
        editText55 = (EditText) findViewById(R.id.editText55);
        editText56 = (EditText) findViewById(R.id.editText56);
        editText57 = (EditText) findViewById(R.id.editText57);
        editText58 = (EditText) findViewById(R.id.editText58);
        editText60 = (EditText) findViewById(R.id.editText60);
        editText61 = (EditText) findViewById(R.id.editText61);
        editText62 = (EditText) findViewById(R.id.editText62);
        editText63 = (EditText) findViewById(R.id.editText63);
        editText64 = (EditText) findViewById(R.id.editText64);
        editText65 = (EditText) findViewById(R.id.editText65);
        editText66 = (EditText) findViewById(R.id.editText66);
        editText67 = (EditText) findViewById(R.id.editText67);
        editText68 = (EditText) findViewById(R.id.editText68);
        editText70 = (EditText) findViewById(R.id.editText70);
        editText71 = (EditText) findViewById(R.id.editText71);
        editText72 = (EditText) findViewById(R.id.editText72);
        editText73 = (EditText) findViewById(R.id.editText73);
        editText74 = (EditText) findViewById(R.id.editText74);
        editText75 = (EditText) findViewById(R.id.editText75);
        editText76 = (EditText) findViewById(R.id.editText76);
        editText77 = (EditText) findViewById(R.id.editText77);
        editText78 = (EditText) findViewById(R.id.editText78);
        editText80 = (EditText) findViewById(R.id.editText80);
        editText81 = (EditText) findViewById(R.id.editText81);
        editText82 = (EditText) findViewById(R.id.editText82);
        editText83 = (EditText) findViewById(R.id.editText83);
        editText84 = (EditText) findViewById(R.id.editText84);
        editText85 = (EditText) findViewById(R.id.editText85);
        editText86 = (EditText) findViewById(R.id.editText86);
        editText87 = (EditText) findViewById(R.id.editText87);
        editText88 = (EditText) findViewById(R.id.editText88);
        editText00.addTextChangedListener(this);
        editText01.addTextChangedListener(this);
        editText02.addTextChangedListener(this);
        editText03.addTextChangedListener(this);
        editText04.addTextChangedListener(this);
        editText05.addTextChangedListener(this);
        editText06.addTextChangedListener(this);
        editText07.addTextChangedListener(this);
        editText08.addTextChangedListener(this);
        editText10.addTextChangedListener(this);
        editText11.addTextChangedListener(this);
        editText12.addTextChangedListener(this);
        editText13.addTextChangedListener(this);
        editText14.addTextChangedListener(this);
        editText15.addTextChangedListener(this);
        editText16.addTextChangedListener(this);
        editText17.addTextChangedListener(this);
        editText18.addTextChangedListener(this);
        editText20.addTextChangedListener(this);
        editText21.addTextChangedListener(this);
        editText22.addTextChangedListener(this);
        editText23.addTextChangedListener(this);
        editText24.addTextChangedListener(this);
        editText25.addTextChangedListener(this);
        editText26.addTextChangedListener(this);
        editText27.addTextChangedListener(this);
        editText28.addTextChangedListener(this);
        editText30.addTextChangedListener(this);
        editText31.addTextChangedListener(this);
        editText32.addTextChangedListener(this);
        editText33.addTextChangedListener(this);
        editText34.addTextChangedListener(this);
        editText35.addTextChangedListener(this);
        editText36.addTextChangedListener(this);
        editText37.addTextChangedListener(this);
        editText38.addTextChangedListener(this);
        editText40.addTextChangedListener(this);
        editText41.addTextChangedListener(this);
        editText42.addTextChangedListener(this);
        editText43.addTextChangedListener(this);
        editText44.addTextChangedListener(this);
        editText45.addTextChangedListener(this);
        editText46.addTextChangedListener(this);
        editText47.addTextChangedListener(this);
        editText48.addTextChangedListener(this);
        editText50.addTextChangedListener(this);
        editText51.addTextChangedListener(this);
        editText52.addTextChangedListener(this);
        editText53.addTextChangedListener(this);
        editText54.addTextChangedListener(this);
        editText55.addTextChangedListener(this);
        editText56.addTextChangedListener(this);
        editText57.addTextChangedListener(this);
        editText58.addTextChangedListener(this);
        editText60.addTextChangedListener(this);
        editText61.addTextChangedListener(this);
        editText62.addTextChangedListener(this);
        editText63.addTextChangedListener(this);
        editText64.addTextChangedListener(this);
        editText65.addTextChangedListener(this);
        editText66.addTextChangedListener(this);
        editText67.addTextChangedListener(this);
        editText68.addTextChangedListener(this);
        editText70.addTextChangedListener(this);
        editText71.addTextChangedListener(this);
        editText72.addTextChangedListener(this);
        editText73.addTextChangedListener(this);
        editText74.addTextChangedListener(this);
        editText75.addTextChangedListener(this);
        editText76.addTextChangedListener(this);
        editText77.addTextChangedListener(this);
        editText78.addTextChangedListener(this);
        editText80.addTextChangedListener(this);
        editText81.addTextChangedListener(this);
        editText82.addTextChangedListener(this);
        editText83.addTextChangedListener(this);
        editText84.addTextChangedListener(this);
        editText85.addTextChangedListener(this);
        editText86.addTextChangedListener(this);
        editText87.addTextChangedListener(this);
        editText88.addTextChangedListener(this);  

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void afterTextChanged(Editable editable) {
        Toast toast=Toast.makeText(MainActivity.this,"Toast提示消息",Toast.LENGTH_SHORT    );
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


    }
        /*LinearLayout linearlayout=(LinearLayout) findViewById(R.id.linearlayout);
        TextView editText1= new TextView(MainActivity.this);
        editText1.setText("分数");
        editText1.setBackground(getResources().getDrawable(R.drawable.rectangle));

        TextView editText2= new  TextView(MainActivity.this);
        editText2.setText("记录");
        editText2.setBackground(getResources().getDrawable(R.drawable.rectangle));
        linearlayout.addView(editText1,new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));

        linearlayout.addView(editText2,new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));*/
        /*TableLayout tablelayout=(TableLayout) findViewById(R.id.tablelayout);
        tablelayout.setGravity(100);
        tablelayout.setStretchAllColumns(true);

        for(int row=0;row<9;row++)
        {
            TableRow tableRow=new TableRow(MainActivity.this);
            for(int col=0;col<9;col++)
            {
                EditText editText=new EditText(MainActivity.this);
                editText.setGravity(Gravity.CENTER);
                editText.setBackground(getResources().getDrawable(R.drawable.rectangle));
                tableRow.addView(editText);
            }
         tablelayout.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }*/









    }


