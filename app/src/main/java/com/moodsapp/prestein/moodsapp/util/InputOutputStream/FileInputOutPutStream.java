package com.moodsapp.prestein.moodsapp.util.InputOutputStream;

import java.io.File;

public class FileInputOutPutStream {
    public static double getFileSize(File file){
        long size = 0;
        size = getFileFolderSize(file);

        double sizeMB = (double) size / 1024 / 1024;
        //String s = " MB";
        /*if (sizeMB < 1) {
            sizeMB = (double) size / 1024;
            s = " KB";
        }*/
        return sizeMB;
    }
    public static String getDocumentSize(File file){
        long size = 0;
        size = getFileFolderSize(file);

        double sizeMB = (double) size / 1024 / 1024;
        String s = " MB";
        if (sizeMB < 1) {
            sizeMB = (double) size / 1024;
            s = " KB";
        }
        String re=String.valueOf(sizeMB);
        String res=re.length()<5?re:String.valueOf(re.substring(0,5)+s);
        return res;
    }
    public static String getImageSize(File file){
        long size = 0;
        size = getFileFolderSize(file);

        double sizeMB = (double) size / 1024 / 1024;
        String s = " MB";
        if (sizeMB < 1) {
            sizeMB = (double) size / 1024;
            s = " KB";
        }
        String re=String.valueOf(sizeMB);
        String res=re.length()<3?re:String.valueOf(re.substring(0,3)+s);
        return res;
    }
    public static String getApkSize(File file){
        long size = 0;
        size = getFileFolderSize(file);

        double sizeMB = (double) size / 1024 / 1024;
       String s = " MB";
         if (sizeMB < 1) {
            sizeMB = (double) size / 1024;
            s = " KB";
        }
        String re=String.valueOf(sizeMB);
        String res=re.length()<5?re:String.valueOf(re.substring(0,5)+s);
        return res;
    }
    private static long getFileFolderSize(File dir) {
        long size = 0;
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    size += file.length();
                } else
                    size += getFileFolderSize(file);
            }
        } else if (dir.isFile()) {
            size += dir.length();
        }
        return size;
    }
}
