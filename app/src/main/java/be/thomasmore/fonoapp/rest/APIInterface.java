package be.thomasmore.fonoapp.rest;

import java.util.ArrayList;

import be.thomasmore.fonoapp.Classes.AgeRange;
import be.thomasmore.fonoapp.Classes.DisorderType;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("agerange")
    Call<ArrayList<AgeRange>> getAgeRanges();

    @GET("disordertype")
    Call<ArrayList<DisorderType>> getDisorderTypes();

}

