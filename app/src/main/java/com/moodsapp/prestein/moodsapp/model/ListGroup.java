package com.moodsapp.prestein.moodsapp.model;

import java.util.ArrayList;


public class ListGroup {
    private  ArrayList<Group> listGroup;
    public  ArrayList<Group> getListGroup() {
        return listGroup;
    }
    public ListGroup(){
        listGroup = new ArrayList<>();
    }
    public void setListGroup(ArrayList<Group> listGroup) {
        this.listGroup = listGroup;
    }
}
