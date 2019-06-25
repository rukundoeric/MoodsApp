package com.moodsapp.prestein.moodsapp.ui.Adapters;

/**
 * Created by Prestein on 7/26/2017.
 */

public class GroupAdapter {
    private String group_name;
    private String group_image;
    private String member_list_names;

    public GroupAdapter() {
    }

    public GroupAdapter(String group_name, String group_image, String member_list_names) {
        this.group_name = group_name;
        this.group_image = group_image;
        this.member_list_names = member_list_names;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_image() {
        return group_image;
    }

    public void setGroup_image(String group_image) {
        this.group_image = group_image;
    }

    public String getMember_list_names() {
        return member_list_names;
    }

    public void setMember_list_names(String member_list_names) {
        this.member_list_names = member_list_names;
    }
}
