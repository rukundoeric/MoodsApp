package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.component;

import android.content.Context;
import android.os.Environment;

import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.adapter.DocumentDetails;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;

import java.io.File;
import java.util.ArrayList;

public class DocumentFilesController {
	private  Context context;
	private  ArrayList<DocumentDetails> documentList;

    public DocumentFilesController(Context context) {
        this.context = context;
        documentList=new ArrayList<>();
    }
    public ArrayList<DocumentDetails> load(File path, String FileExtension){

        File FileList[]= path.listFiles();
        if (FileList!=null){
            for (File aFileList : FileList) {
                if (aFileList.isDirectory()) {
                    load(aFileList, FileExtension);
                } else {
                    if (FileExtension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP.toLowerCase())){
                        if (aFileList.getName().toLowerCase().endsWith(ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP.toLowerCase())
                                || aFileList.getName().toLowerCase().endsWith(ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP_RAR.toLowerCase())
                                ||aFileList.getName().toLowerCase().endsWith(ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP_7z.toLowerCase())
                                ) {
                            DocumentDetails documentDetails = new DocumentDetails();
                            documentDetails.fileName = aFileList.getName();
                            documentDetails.filePath = aFileList.getPath();
                            documentDetails.fileSize = FileInputOutPutStream.getDocumentSize(new File(documentDetails.filePath));
                            documentList.add(documentDetails);
                        }
                    }else {
                        if (aFileList.getName().toLowerCase().endsWith(FileExtension.toLowerCase())) {
                            DocumentDetails documentDetails = new DocumentDetails();
                            documentDetails.fileName = aFileList.getName();
                            documentDetails.filePath = aFileList.getPath();
                            documentDetails.fileSize = FileInputOutPutStream.getDocumentSize(new File(documentDetails.filePath));
                            documentList.add(documentDetails);
                        }
                    }
                }
            }
        }
        return documentList;
	}
	public ArrayList<DocumentDetails> loadedDocument(final String FileExtension){

		final ArrayList<DocumentDetails>[] documentListDetails = new ArrayList[]{new ArrayList<>()};
		new Thread(new Runnable() {
			@Override
			public void run() {
				documentListDetails[0] =load(Environment.getExternalStorageDirectory(),FileExtension);
			}
		}).start();
		return documentListDetails[0];
	}
}
