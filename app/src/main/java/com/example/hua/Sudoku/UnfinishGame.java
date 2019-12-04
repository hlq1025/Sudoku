package com.example.hua.Sudoku;

import android.content.Context;
import android.content.SharedPreferences;

public class UnfinishGame {
    private Context ctx;
    public UnfinishGame(Context context) {
       ctx=context;
    }
    public void SaveInformation(long base_time, String hard_level, String[] numbers, boolean flag) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(hard_level, Context.MODE_PRIVATE);

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

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(hard_level, 0);

        boolean CanCon = sharedPreferences.getBoolean("可继续", false);

        if (CanCon) ans[0] = "true";

        else ans[0] = "false";

        ans[1] =String.valueOf(sharedPreferences.getLong("用时", 0));

        ans[2] = sharedPreferences.getString("数字表", "");

        return ans;

    }
    public void ClearInformation(String hard_level) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(hard_level, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.commit();
    }
}

