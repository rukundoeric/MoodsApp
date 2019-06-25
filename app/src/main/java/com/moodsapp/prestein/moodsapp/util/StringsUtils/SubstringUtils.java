package com.moodsapp.prestein.moodsapp.util.StringsUtils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Danny B on 2/11/2018.
 */

public class SubstringUtils
{
    public static String getSubStringedUserIdFromTable(String s) {
        return s.substring(7,s.length());
    }
    public static String getSubString(String s,int from, int to){
        return s.substring(from,to);
    }
    public String getSubStringByChar(Character a,String string,boolean isAfter) {
        String finalString = null;
        if (isAfter) {
            for (int i = 0; i <=string.length(); i++) {
                if (string.charAt(i) == a) {
                    finalString = string.substring(i+1, string.length());
                    break;
                }
            }
        } else {
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == a) {
                    finalString = string.substring(0, i);
                    break;
                }
            }
        }
     return finalString;
    }
    public static String getSubStringFromLast(Context context,Character a, String string){
        String finalString=null;
        try {
            for (int i = string.length()-1; i > 0; i--) {
                if (string.charAt(i) == a) {
                    finalString = string.substring(string.length(), i);
                    break;
                }
            }
        }catch (Exception e){
            Toast.makeText(context, "in substring from last "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return finalString;
    }
}
