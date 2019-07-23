package com.example.game.dictionaryapi;

import android.provider.BaseColumns;

public class SQLDatabase {
    private SQLDatabase() {}

    public static class FeedEntry implements BaseColumns {
        public static final String
                Word = "word",
                Etymologies ="etymologies";
        public static final String TableName = "History";

        public static final String CREATE_QUERY = "CREATE TABLE " + TableName + " ( " +
                Word + " TEXT, " +
                Etymologies + " TEXT);";


        public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TableName + ";";
        public static final String GET_QUERY = "SELECT DISTINCT WORD,ETYMOLOGIES FROM "+TableName+";";
    }
}