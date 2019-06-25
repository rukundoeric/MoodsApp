package com.moodsapp.prestein.moodsapp.ui.Adapters;

/**
 * Created by Prestein on 6/5/2017.
 */

public class UserListingAdapter {

    private String name, status, small_profile_image;

    public UserListingAdapter() {
    }

    public UserListingAdapter(String name, String status, String small_profile_image) {
        this.name = name;
        this.status = status;
        this.small_profile_image = small_profile_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSmall_profile_image() {
        return small_profile_image;
    }

    public void setSmall_profile_image(String small_profile_image) {
        this.small_profile_image = small_profile_image;
    }
}