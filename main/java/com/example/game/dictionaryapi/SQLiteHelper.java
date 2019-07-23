package com.example.game.dictionaryapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DatabaseName = "Database.db";
    private static final int DatabaseVersion = 2;
    Context mContext;

    public SQLiteHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLDatabase.FeedEntry.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLDatabase.FeedEntry.DELETE_QUERY);
        onCreate(db);
    }

    public void InsertData(Word data){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLDatabase.FeedEntry.Word,data.getWord());
        contentValues.put(SQLDatabase.FeedEntry.Etymologies,data.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0));
        db.insert(SQLDatabase.FeedEntry.TableName,null,contentValues);
        Toast.makeText(mContext,"Added To History",Toast.LENGTH_SHORT).show();
        //db.execSQL("DELETE FROM "+ SQLDatabase.FeedEntry.TableName+" WHERE "+data.getWord()+" NOT IN ( SELECT MIN("+data.getWord()+") FROM "+SQLDatabase.FeedEntry.TableName+" GROUP BY HASH);");
    }

    public Cursor getHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(SQLDatabase.FeedEntry.GET_QUERY,null);
        return res;
    }

    public void Clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQLDatabase.FeedEntry.DELETE_QUERY);
        Toast.makeText(mContext,"Clearing History",Toast.LENGTH_SHORT).show();
        db.execSQL(SQLDatabase.FeedEntry.CREATE_QUERY);
    }

}