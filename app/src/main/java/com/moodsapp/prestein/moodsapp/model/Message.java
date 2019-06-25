package com.moodsapp.prestein.moodsapp.model;


import java.io.Serializable;

public class Message implements Serializable{
    //Identification of Message
    public String msgId;
    public String msgType;
    public String idRoom;
    public String idReceiver;
    public String idSender;
    //for Text Message
    public String text;
    //********************
    //for Photo Message
    public String PhotoDeviceUrl;
    public String PhotoStringBase64;
    //for Voice Message
    public String VoiceDeviceUrl;
    //for Video Message
    public String VideoDeviceUrl;
    //for Document Message
    public String DocumentDeviceUrl;
    public String AnyMediaUrl;
    public String AnyMediaStatus;
    public long timestamp;
    public String msg_reprayed_id;
    public String msg_reprayed_message;
    public String messageStatus;
}