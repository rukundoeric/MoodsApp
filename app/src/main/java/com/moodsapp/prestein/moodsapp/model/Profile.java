package com.moodsapp.prestein.moodsapp.model;

/**
 * Created by Eric prestein on 12/5/2017.
 */

public class Profile extends profileInfo{
    public String DeviceId;
    public String DeviceToken;
    public String UserId;
    public String name;
    public String status;
    public String phone;
    public String country;
    public String profile_image;
    public String small_profile_image;
}
class profileInfo{
public static final String DeviceIdInfo="DeviceId";
public static final String DeviceTokenInfo="DeviceToken";
public static final String UserIdInfo="UserId";
public static final String nameInfo="name";
public static final String statusInfo="status";
public static final String phoneInfo="phone";
public static final String countryInfo="country";
public static final String profile_imageInfo="profile_image";
public static final String small_profile_imageInfo="small_profile_image";
        }
