package be.thomasmore.fonoapp.rest;

import java.util.ArrayList;

import be.thomasmore.fonoapp.Classes.AgeRange;
import be.thomasmore.fonoapp.Classes.DisorderType;
import be.thomasmore.fonoapp.Classes.User;
import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.Classes.WordPair;
import be.thomasmore.fonoapp.Classes.WordPairType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/agerange")
    Call<ArrayList<AgeRange>> getAgeRanges();

    @GET("/disordertype")
    Call<ArrayList<DisorderType>> getDisorderTypes();

    @POST("/auth/signin")
    Call<User> login(@Body User user);

    @GET("/wordPairType")
    Call<ArrayList<WordPairType>> getWordPairTypes();

    @GET("/wordpair/filters")
    Call<ArrayList<WordPair>> getWordPaires(@Query("age")String age, @Query("type")String pairType);

    @GET("/word/{id}")
    Call<Word> getWord(@Path("id") String id);
}

