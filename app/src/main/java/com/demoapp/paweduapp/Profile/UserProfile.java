package com.demoapp.paweduapp.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.paweduapp.APIModels.UserModel;
import com.demoapp.paweduapp.R;
import com.demoapp.paweduapp.Retrofit.API;
import com.demoapp.paweduapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfile extends AppCompatActivity {
    String uid, token;
    ImageView image;
    SharedPreferences sharedPref;

    TextView username, name, breed;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sharedPref = getSharedPreferences("pawedUPref", Context.MODE_PRIVATE);

        uid = getIntent().getStringExtra("UserId");
        token = sharedPref.getString("TOKEN", "");

        Toast.makeText(this, "user:" + uid, Toast.LENGTH_SHORT).show();

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        breed = findViewById(R.id.breed);

        Retrofit retrofit = RetrofitClient.getRetrofit();
        api = retrofit.create(API.class);
        getUserDetails();


    }

    private void getUserDetails() {
        Call<UserModel> getDetails = api.getUserById("Bearer "+token,uid);

        getDetails.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    name.setText(response.body().getName());
                    username.setText("@"+response.body().getUsername());
                    breed.setText(response.body().getBreed());

                    Picasso.get().load(response.body().getImageUrl()).error(R.drawable.dogdemo).into(image);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
}