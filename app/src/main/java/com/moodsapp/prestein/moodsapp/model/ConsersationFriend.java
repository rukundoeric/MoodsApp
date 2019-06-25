package com.moodsapp.prestein.moodsapp.model;

import java.util.ArrayList;


public class ConsersationFriend {
    private ArrayList<ResentChats> listMessageData;
    public ConsersationFriend(){
        listMessageData = new ArrayList<>();
    }

    public ArrayList<ResentChats> getListMessageData() {
        return listMessageData;
    }
}
