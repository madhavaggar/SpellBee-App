package com.example.game.dictionaryapi;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DisplayHistoryWord extends AppCompatActivity {

    SQLiteHelper helper;
    Cursor res;
    List<Word> searchList = new ArrayList<>();
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_history_display);
        Toast.makeText(getApplicationContext(), "Fetching Data", Toast.LENGTH_SHORT).show();
        helper = new SQLiteHelper(this);
        res = helper.getHistory();

        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Nothing in History", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Loading History", Toast.LENGTH_SHORT).show();
            while (res.moveToNext()) {
                Word newWord = new Word();
                newWord.setWord(res.getString(0));
                newWord.setEtymology(res.getString(1));
                searchList.add(newWord);
            }
            Log.d("DisplayHistoryWord", "initialising Recycler View");
            recyclerView = findViewById(R.id.recyclerView);
            adapter = new RecyclerAdapter(this, searchList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void DatabaseDelete(View view) {
        helper.Clear();
        this.recreate();
    }
}