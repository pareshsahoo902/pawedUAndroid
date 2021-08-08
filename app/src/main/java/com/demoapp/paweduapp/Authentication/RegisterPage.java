package com.demoapp.paweduapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demoapp.paweduapp.APIModels.AuthResponse;
import com.demoapp.paweduapp.APIModels.UserModel;
import com.demoapp.paweduapp.MainActivity;
import com.demoapp.paweduapp.R;
import com.demoapp.paweduapp.Retrofit.API;
import com.demoapp.paweduapp.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterPage extends AppCompatActivity {

    EditText userName, password, name, breed, imageUrl;
    Button register;
    private API api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        breed = findViewById(R.id.breed);
        imageUrl = findViewById(R.id.imageUrl);
        register = findViewById(R.id.register);

        Retrofit retrofit = RetrofitClient.getRetrofit();
        api = retrofit.create(API.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userName.getText().toString().equals("")) {
                    Toast.makeText(RegisterPage.this, "Enter a username First!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals("")) {
                    Toast.makeText(RegisterPage.this, "Enter a password to login!", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser();


            }
        });


    }

    private void registerUser() {

        UserModel user = new UserModel(userName.getText().toString().trim(), name.getText().toString(),
                password.getText().toString().trim(), imageUrl.getText().toString(),breed.getText().toString());

        Call<AuthResponse> registerCall = api.registerUser(user);

        registerCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(RegisterPage.this, "User Registered!\nPlease Login!", Toast.LENGTH_SHORT).show();
                    finish();



                } else {
                    if (response.code() == 409) {
                        Toast.makeText(RegisterPage.this, "Failed\nUser Already exists .", Toast.LENGTH_SHORT).show();

                    }

                }


            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

                Toast.makeText(RegisterPage.this, "Failed\nError: " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}