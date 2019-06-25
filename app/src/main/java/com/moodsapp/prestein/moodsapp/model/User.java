package com.moodsapp.prestein.moodsapp.model;



public class User {
    public String name;
    public String country;
    public String image;
    public String status;
    public Status statas;
    public Message message;


    public User(){
        statas = new Status();
        message = new Message();
        statas.isOnline = false;
        statas.timestamp = 0;
        message.idReceiver = "0";
        message.idSender = "0";
        message.text = "";
        message.timestamp =0;
    }
}
