package com.example.natan.sqllitetodo.Database;

import android.provider.BaseColumns;

/**
 * Created by natan on 2/1/2018.
 */

public class MyContract {


    public static class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DATE_TIME = "datetime";
        //public static final String COLUMN_TIME = "time";

    }


}
