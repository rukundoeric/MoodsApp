package com.moodsapp.prestein.moodsapp.ui.Adapters;

/**
 * Created by Prestein on 7/15/2017.
 */

public class MyBusinessAdapter {
    private String business_name;
    private String description;
    private String business_image;

    public MyBusinessAdapter(String business_name, String description, String business_image) {
        this.business_name = business_name;
        this.description = description;
        this.business_image = business_image;
    }

    public MyBusinessAdapter() {
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }
}
