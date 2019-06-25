package com.moodsapp.prestein.moodsapp.util.InputOutputStream;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by USER on 3/17/2018.
 */

public class myProfile {

    public static void UpdateProfile(Context context, ArrayList<ProfileFile> fileData){
        try {
            for (ProfileFile profile:fileData){
                String[] fileContent=profile.getFileContent().split(System.getProperty("line.separator"));
                Save(new File(loopFileName(context,profile.getFileName())),fileContent);
            }
        }catch (Exception r){
            Toast.makeText(context, "in updtate "+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public static String getProfileInfo(Context context,String fileName){
        String resultInfo="";
        try{
            String loadedContent[]=Load(new File(loopFileName(context,fileName)));
            for (int i=0;i<=loadedContent.length-1;i++){
                resultInfo+=loadedContent[i];
            }

        }catch (Exception e){
            Toast.makeText(context, "in Load data "+fileName+"  "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return resultInfo;
     }

    public static void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
    private static String loopFileName(Context context, String fname)
    {
        String FINAL_FILE_NAME="";
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.MY_PROFILE_STORAGE_PATH;
            if (new File(filePath).exists())
            {
                new File(filePath).delete();
                new File(filePath).mkdirs();
                String FILE_NAME=fname+".txt";
                FINAL_FILE_NAME=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                String FILE_NAME=fname+".txt";
                FINAL_FILE_NAME=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(context, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return FINAL_FILE_NAME;
    }
    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }
}
