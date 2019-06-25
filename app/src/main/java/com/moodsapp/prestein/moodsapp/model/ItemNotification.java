package com.moodsapp.prestein.moodsapp.model;

import java.io.Serializable;

/**
 * Created by Eric prestein on 1/3/2018.
 */

public class ItemNotification implements Serializable{
    public String title;
    public String notId;
    public String iconUrl;
    public String msgId;
    public String msgType;
    public String idRoom;
    public String idReceiver;
    public String idSender;
    public String text;
    public String PhotoDeviceUrl;
    public String PhotoStringBase64;
    public String VoiceDeviceUrl;
    public String VideoDeviceUrl;
    public String DocumentDeviceUrl;
    public String AnyMediaUrl;
    public String AnyMediaStatus;
    public String timestamp;
    public String msg_reprayed_id;
    public String msg_reprayed_message;

}
