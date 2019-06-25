package com.moodsapp.prestein.moodsapp.service.RecentNotification;

import java.util.ArrayList;


public class RecentNotificationListClass {
    private ArrayList<ItemNotifictaion> listRecentNotification;
    public RecentNotificationListClass(){
        listRecentNotification = new ArrayList<>();
    }

    public ArrayList<ItemNotifictaion> getListRecentNotification() {
        return listRecentNotification;
    }
}
