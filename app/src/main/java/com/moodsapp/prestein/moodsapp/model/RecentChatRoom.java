package com.moodsapp.prestein.moodsapp.model;

import java.util.ArrayList;


public class RecentChatRoom {
    private ArrayList<ResentChats> listRecentChatData;
    public RecentChatRoom(){
        listRecentChatData = new ArrayList<>();
    }

    public ArrayList<ResentChats> getListRecentChatDataData() {
        return listRecentChatData;
    }
}
