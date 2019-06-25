package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_contact;

/**
 * Created by Danny on 9/24/2017.
 */

public class ListItem {

    private String name;
    private String number;

    public ListItem(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
