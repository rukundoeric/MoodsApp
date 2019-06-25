package com.moodsapp.prestein.moodsapp.util.InputOutputStream;

/**
 * Created by USER on 3/17/2018.
 */

public class ProfileFile {
    public String fileName;
    public String fileContent;

    public ProfileFile(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileContent() {
        return fileContent;
    }
}
