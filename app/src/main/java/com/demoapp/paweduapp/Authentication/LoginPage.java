package com.demoapp.paweduapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginPage extends AppCompatActivity {

    EditText userName, password;
    Button login;
    private API api;
    TextView register;
    SharedPreferences sharedPref;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        sharedPref= getSharedPreferences("pawedUPref", Context.MODE_PRIVATE);
        uid = sharedPref.getString("USERID", "");

        if (uid.length()>1){
            startActivity(new Intent(LoginPage.this,MainActivity.class));
            finish();

        }



        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.reg);




        Retrofit retrofit = RetrofitClient.getRetrofit();
        api = retrofit.create(API.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,RegisterPage.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userName.getText().toString().equals("")){
                    Toast.makeText(LoginPage.this, "Enter a username First!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals("")){
                    Toast.makeText(LoginPage.this, "Enter a password to login!", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser();


            }
        });



    }

    private void loginUser() {

        UserModel user = new UserModel(userName.getText().toString().trim(),password.getText().toString().trim());

        Call<AuthResponse> loginCall = api.doLogin(user);

        loginCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Success\nUserID: "+response.body().getId(), Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("TOKEN", response.body().getToken());
                    editor.putString("USERID", response.body().getId());
                    editor.commit();

                    startActivity(new Intent(LoginPage.this, MainActivity.class));

                }
                else{
                    if (response.code() == 401){
                        Toast.makeText(LoginPage.this, "Failed\nNo User Exists with this name", Toast.LENGTH_SHORT).show();

                    }

                }


            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

                Toast.makeText(LoginPage.this, "Failed\nError: "+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}