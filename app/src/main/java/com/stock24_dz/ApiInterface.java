package com.stock24_dz;

import com.stock24_dz.Model.CompanyModel;
import com.stock24_dz.Model.category_model;
import com.stock24_dz.Model.user_interest;
import com.stock24_dz.Model.user_model;

import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("getAllCategories")
    public Call<List<category_model>>  getAllCategories();

    @POST("CreateUser")
    public Call<user_model> CreateUser(@Body user_model user);

    @POST("addInterest")
    public Call<user_interest> addInterest(@Body user_interest user_interest);

    @GET("{firebase_id}/getUser")
    public Call<user_model> getUserById(@Path("firebase_id") String firebase_id);

    @GET("getCompanyAbousUs")
    public Call<CompanyModel> getAboutUs();

    @GET("getCompanyTerms")
    public Call<CompanyModel> getTermes();

    @GET("getCompanyEmail")
    public Call<CompanyModel> getEmail();








}
