package com.moodsapp.prestein.moodsapp.ui.Adapters;

/**
 * Created by Prestein on 7/15/2017.
 */

public class ChatHistoryAdapter {
    private String name;
    private String last_message;
    private long last_message_timeStamp;
    private String small_profile_image;
    private String id;
    private String idRoom;
    private String status;

    public ChatHistoryAdapter(String name, String last_message, long last_message_timeStamp, String small_profile_image, String id, String idRoom, String status) {
        this.name = name;
        this.last_message = last_message;
        this.last_message_timeStamp = last_message_timeStamp;
        this.small_profile_image = small_profile_image;
        this.id = id;
        this.idRoom = idRoom;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getLast_message() {
        return last_message;
    }

    public long getLast_message_timeStamp() {
        return last_message_timeStamp;
    }

    public String getSmall_profile_image() {
        return small_profile_image;
    }

    public String getId() {
        return id;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public String getStatus() {
        return status;
    }
}

