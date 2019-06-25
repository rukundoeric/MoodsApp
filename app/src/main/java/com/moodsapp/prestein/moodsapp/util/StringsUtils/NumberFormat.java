package com.moodsapp.prestein.moodsapp.util.StringsUtils;

/**
 * Created by Danny on 1/12/2018.
 */

public class NumberFormat {

    public String getTenDigits(String phoneNumber)
    {
        StringBuilder stringBuilder=new StringBuilder(phoneNumber);
        StringBuilder sb;
        String revS=null;
        if(phoneNumber.length()>=9)
        {revS=stringBuilder.reverse().substring(0,9).toString();
            sb=new StringBuilder(revS);
            return sb.reverse().toString();}

        return null;
    }
    public String removeChar(String phoneNo )
    {
        StringBuilder stringBuilder = new StringBuilder(phoneNo);

        String resultNo=null;
        char sCharacter;
        for (int i=0;i<stringBuilder.length();i++)
        {
            sCharacter=stringBuilder.charAt(i);
            switch (sCharacter)
            {
                case '-':
                    stringBuilder.deleteCharAt(i);
                    continue;
                case ' ':
                    stringBuilder.deleteCharAt(i);
                    continue;
                case '(':
                    stringBuilder.deleteCharAt(i);
                    continue;
                case ')':
                    stringBuilder.deleteCharAt(i);
                    continue;
                case '+':
                    stringBuilder.deleteCharAt(i);
                    continue;
                case '*':
                    stringBuilder.deleteCharAt(i);
                    continue;
                case '.':
                    stringBuilder.deleteCharAt(i);
                    continue;
                case '#':
                    stringBuilder.deleteCharAt(i);
                default:
                    break;
            }
        }

        resultNo = stringBuilder.toString().trim();

        return resultNo;
    }
}
