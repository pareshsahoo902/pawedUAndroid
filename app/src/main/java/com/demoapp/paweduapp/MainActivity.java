package com.demoapp.paweduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demoapp.paweduapp.Authentication.LoginPage;

public class MainActivity extends AppCompatActivity {

    String token,Uid;
    Button logout;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref= getSharedPreferences("pawedUPref", Context.MODE_PRIVATE);


        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sharedPref.edit().clear().commit();
              startActivity(new Intent(MainActivity.this, LoginPage.class));
              finish();

            }
        });



    }
}