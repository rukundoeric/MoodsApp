package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_group;

/**
 * Created by Prestein on 9/29/2017.
 */

public class Group_list_item {
    String group_name;
    String group_image;

    public Group_list_item(String group_name, String group_image) {
        this.group_name = group_name;
        this.group_image = group_image;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_image() {
        return group_image;
    }
}

