package com.demoapp.paweduapp.Retrofit;

import com.demoapp.paweduapp.APIModels.AuthResponse;
import com.demoapp.paweduapp.APIModels.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    @POST("login")
    Call<AuthResponse> doLogin(@Body UserModel user);


    @POST("register")
    Call<AuthResponse> registerUser(@Body UserModel user);


    //-------------------------------------------------

    //GET ALL USERS

    @GET("getUsers")
    Call<List<UserModel>> getAllUsers(
      @Header("Authorization") String token
    );


    //GET USER BY ID

    @GET("getUsers/{id}")
    Call<UserModel> getUserById(
            @Header("Authorization") String token,
            @Path("id") String Id
    );



}
