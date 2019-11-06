package com.example.hua.Sudoku;
import android.text.InputFilter;
import android.text.Spanned;
public class DigitFilter  implements InputFilter
{
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        if(source.length() <= 0){return "";}

        char [] newChar = new char[source.length()];

        //去掉source中不是数字的字符
        for(int i=source.length();--i>=0;){
            int chr=source.charAt(i);
            if(chr > 0 && chr < 10){
                newChar[i] = (char) chr;
            }

        }

        //newChar数组中没有赋值的项，在转换为String会被丢弃
        return new String(newChar);
    }


}
