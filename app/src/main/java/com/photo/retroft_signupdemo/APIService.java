/*
 * Created by Shaon on 8/15/16 8:06 PM
 * Copyright (c) 2016. This is free to use in any software.
 * You must provide developer name on your project.
 */

package com.photo.retroft_signupdemo;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Shaon on 8/15/2016.
 */
public interface APIService {


    @FormUrlEncoded
    @POST("login/views/signup.php")
    Call<MSG> userSignUp(@Field("name") String name,
                         @Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("login/views/login.php")
    Call<MSG> userLogIn(@Field("email") String email,
                        @Field("password") String password);



}