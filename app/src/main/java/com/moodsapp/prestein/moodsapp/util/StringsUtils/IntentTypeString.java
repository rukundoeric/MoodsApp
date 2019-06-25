package com.moodsapp.prestein.moodsapp.util.StringsUtils;

import java.util.ArrayList;

public class IntentTypeString
{
    public ArrayList<IntentTypeItem> getIntentType(){
        ArrayList<IntentTypeItem> intentTypeItems=new ArrayList<>();
        IntentTypeItem intentTypeItem=new IntentTypeItem("Android Application ",".apk","application/vnd.android.package-archive");
        IntentTypeItem intentTypeItem1=new IntentTypeItem("Text",".txt","text/plain");
        IntentTypeItem intentTypeItem2=new IntentTypeItem("Text",".csv","text/csv");
        IntentTypeItem intentTypeItem3=new IntentTypeItem("Package",".zip","application/zip");
        IntentTypeItem intentTypeItem4=new IntentTypeItem("Package",".rar","application/x-rar-compressed");
        IntentTypeItem intentTypeItem5=new IntentTypeItem("Package",".gz","application/gzip");
        intentTypeItems.add(intentTypeItem);
        intentTypeItems.add(intentTypeItem1);
        intentTypeItems.add(intentTypeItem2);
        intentTypeItems.add(intentTypeItem3);
        intentTypeItems.add(intentTypeItem4);
        intentTypeItems.add(intentTypeItem5);
        return intentTypeItems;
    }
    public String getIntentTypeByExtension(ArrayList<IntentTypeItem> intentTypeList, String extension){
        for (IntentTypeItem intentTypeItemArrayList:intentTypeList){
            if (intentTypeItemArrayList.getExtension().equals("."+extension)){
                return intentTypeItemArrayList.getIntentType();
            }
        }
        return null;
    }
    private class IntentTypeItem {
        private String category;
        private String extension;
        private String IntentType;

        public IntentTypeItem(String category, String extension, String intentType) {
            this.category = category;
            this.extension = extension;
            IntentType = intentType;
        }

        public String getCategory() {
            return category;
        }

        public String getExtension() {
            return extension;
        }

        public String getIntentType() {
            return IntentType;
        }
    }
}
