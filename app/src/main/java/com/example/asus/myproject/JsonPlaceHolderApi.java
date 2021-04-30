package com.example.asus.myproject;

import com.example.asus.myproject.Model.CompanyInfo;
import com.example.asus.myproject.Model.Profile;
import com.example.asus.myproject.Model.Result;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("/api.php")
    Call<JsonObject> getQuiz(@Query("amount") int amount,
                             @Query("category") int category,
                             @Query("difficulty") String difficulty,
                             @Query("type") String type);

    @POST("profile/")
    Call<Profile> signUpIt(@Body Profile userProfile);

    @FormUrlEncoded
    @POST("login/")
    Call<JsonObject> signInIt(@Field("username") String email_id , @Field("password") String password);

    @GET("vacancy_info/?ordering=-vacancy_count")
    Call<JsonArray> setCardVacancy();

    @GET("vacancy_info/")
    Call<JsonArray> setcardVacancyDetail(@Query("id") int id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("company_info/")
    Call<JsonObject> setCompanyInfo(
            @Header("Authorization") String token,
            @Body CompanyInfo companyInfo
                                    );

    @POST("result/")
    Call<JsonObject> setResultScore(@Body Result result);

    @GET("result/")
    Call<JsonArray> getExamResult();

    @GET("result/")
    Call<JsonArray> getCanExamResult(@Query("search") String  search);



}