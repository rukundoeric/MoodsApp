package com.moodsapp.prestein.moodsapp.ui.Adapters;

/**
 * Created by Prestein on 7/7/2017.
 */

public class PostingAdapter {
public String business_owner_image,business_name,post_desc,business_image_posted;

    public PostingAdapter() {
    }

    public PostingAdapter(String business_owner_image, String business_name, String post_desc, String business_image) {
        this.business_owner_image = business_owner_image;
        this.business_name = business_name;
        this.post_desc = post_desc;
        this.business_image_posted = business_image;
    }

    public String getBusiness_owner_image() {
        return business_owner_image;
    }

    public void setBusiness_owner_image(String business_owner_image) {
        this.business_owner_image = business_owner_image;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getPost_desc() {
        return post_desc;
    }

    public void setPost_desc(String post_desc) {
        this.post_desc = post_desc;
    }

    public String getBusiness_image_posted() {
        return business_image_posted;
    }

    public void setBusiness_image_posted(String business_image_posted) {
        this.business_image_posted = business_image_posted;
    }
}
