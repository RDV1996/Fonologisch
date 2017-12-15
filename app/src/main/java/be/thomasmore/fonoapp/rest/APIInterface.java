package be.thomasmore.fonoapp.rest;

import java.util.ArrayList;

import be.thomasmore.fonoapp.Classes.AgeRange;
import be.thomasmore.fonoapp.Classes.DisorderType;
import be.thomasmore.fonoapp.Classes.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @GET("/agerange")
    Call<ArrayList<AgeRange>> getAgeRanges();

    @GET("/disordertype")
    Call<ArrayList<DisorderType>> getDisorderTypes();

    @POST("/auth/signin")
    Call<User> login(@Body User user);

}

