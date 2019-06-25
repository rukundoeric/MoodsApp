package com.moodsapp.prestein.moodsapp.ui.Adapters;

/**
 * Created by Prestein on 6/9/2017.
 */

public class PostAdapter {
    private String name,description,business_image;

    public PostAdapter(String name, String description, String business_image) {
        this.name = name;
        this.description = description;
        this.business_image = business_image;
    }

    public PostAdapter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
