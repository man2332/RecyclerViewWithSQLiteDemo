package com.example.johnl.recyclerviewwithsqlitedemo;

import android.provider.BaseColumns;

public class ItemContract {
    //empty ctor
    //  -we just need an instant of the class to get access to it's inner class's contants
    public ItemContract() {
    }

    public static final class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "ItemList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        //timestamp to order the items in the list

    }



}
