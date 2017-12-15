package be.thomasmore.fonoapp.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by robyd on 30/11/2017.
 */

public class APIClient {
        private static Retrofit retrofit = null;

    public static Retrofit getClient() {


        retrofit = new Retrofit.Builder()
                .baseUrl("https://fonoappapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        return retrofit;
    }
}
