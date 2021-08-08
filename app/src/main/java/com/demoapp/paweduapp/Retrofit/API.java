package com.demoapp.paweduapp.Retrofit;

import com.demoapp.paweduapp.APIModels.AuthResponse;
import com.demoapp.paweduapp.APIModels.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("login")
    Call<AuthResponse> doLogin(@Body UserModel user);


    @POST("register")
    Call<AuthResponse> registerUser(@Body UserModel user);


    //-------------------------------------------------

    //GET ALL USERS


    //GET USER BY ID



}
