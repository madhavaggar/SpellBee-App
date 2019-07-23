package com.example.game.dictionaryapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface API {
    public final String
            BASE_URL = "https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/",
            APP_ID="4e8d7dc5",
            APP_KEY="bdf6e9acb5f1a5d2af3341ad2d0feac2";

    @GET("{id}?fields=etymologies&strictMatch=false")
    Call<Word> getData(@Path("id") String word, @Header("app_id") String app_id, @Header("app_key") String app_key);
}
