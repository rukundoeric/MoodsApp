package com.moodsapp.prestein.moodsapp.util.DiviceActionUtils;

import com.moodsapp.prestein.moodsapp.Application.MoodsApp;

public class ScreenManager {
    public static int getItemWidth(int columnsCount,int paddingValue){
        int itemWidth = (MoodsApp.displaySize.x - ((columnsCount + 1) * MoodsApp.dp(paddingValue))) / columnsCount;
        return  itemWidth;
    }

}
