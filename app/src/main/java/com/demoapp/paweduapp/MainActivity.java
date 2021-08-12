package com.demoapp.paweduapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demoapp.paweduapp.APIModels.UserModel;
import com.demoapp.paweduapp.Authentication.LoginPage;
import com.demoapp.paweduapp.Retrofit.API;
import com.demoapp.paweduapp.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    String token,Uid;
    Button logout;
    SharedPreferences sharedPref;
    RecyclerView recyclerView;
    API api;
    UserAdapter adapter;
    List<UserModel> userModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref= getSharedPreferences("pawedUPref", Context.MODE_PRIVATE);
        token = sharedPref.getString("TOKEN","");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.hasFixedSize();

        userModelList = new ArrayList<>();
        adapter = new UserAdapter(userModelList,this);
        recyclerView.setAdapter(adapter);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sharedPref.edit().clear().commit();
              startActivity(new Intent(MainActivity.this, LoginPage.class));
              finish();

            }
        });
        Retrofit retrofit = RetrofitClient.getRetrofit();
        api = retrofit.create(API.class);


        getUserListData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserListData();
    }

    private void getUserListData() {

        Call<List<UserModel>> getUserCall = api.getAllUsers("Bearer " +token);

        getUserCall.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()){
                    Log.v("paresh",response.body().toString());
                    if (response.body().size()>0){
                        adapter.updateUserList(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

                Log.v("paresh",t.toString());

            }
        });



    }
}