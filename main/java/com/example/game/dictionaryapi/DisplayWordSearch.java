package com.example.game.dictionaryapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DisplayWordSearch extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{

        SearchView searchBox;
        TextView wordView;
        Retrofit retrofit;
        API api;
        ProgressBar bar;
        Word wordData;
        SQLiteHelper helper;
        ScrollView scroller;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_word_search);
            bar = findViewById(R.id.bar);
            searchBox = findViewById(R.id.searchWord);
            scroller = findViewById(R.id.wordController);
            searchBox.setOnQueryTextListener(this);
            searchBox.setIconifiedByDefault(false);
            retrofit = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(API.class);
            wordView = findViewById(R.id.words);
            helper = new SQLiteHelper(this);
        }

        @Override
        public boolean onQueryTextSubmit(String Query) {
            bar.setVisibility(View.VISIBLE);
            findWord(Query);
            return true;
        }

        private void findWord(final String word) {
            Call<Word> callWords = api.getData(word,API.APP_ID,API.APP_KEY);
            callWords.enqueue(new Callback<Word>() {
                @Override
                public void onResponse(Call<Word> call, Response<Word> response) {
                    wordData = response.body();
                    bar.setVisibility(View.GONE);
                    if(wordData!=null && wordData.getError()==null && wordData.getResults().get(0).getLexicalEntries().get(0).getEntries()!=null && wordData.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0)!=null) {
                        scroller.setVisibility(View.VISIBLE);
                        wordView.setText(wordData.getWord()+"\n\nEtymology -\n\t"+wordData.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0));
                        helper.InsertData(wordData);
                    }
                    else{
                        wordView.setText("Invalid Input Sir.");
                        Toast.makeText(getApplicationContext(), "No matches Found. Try Again with a different Term", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Word> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("DisplayWordSearch",t.getMessage());
                }
            });
        }

        @Override
        public boolean onQueryTextChange(String Query) {
            scroller.setVisibility(View.INVISIBLE);
            wordView.setText("");
            return false;
        }

        public void outToHistory(View view) {
            Intent intent = new Intent(DisplayWordSearch.this,DisplayHistoryWord.class);
            startActivity(intent);
        }
    }