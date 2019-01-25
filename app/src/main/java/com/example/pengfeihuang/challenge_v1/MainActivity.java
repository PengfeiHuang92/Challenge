package com.example.pengfeihuang.challenge_v1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView txResult;
    private EditText userName, password;
    private Button submit;
    private ApiInterface apiInterface;
    private String token;
    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.editUserName);
        password = (EditText) findViewById(R.id.editPassword);
        submit = (Button) findViewById(R.id.btSubmit);

        prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        if(!prefs.getString("Token","").toString().isEmpty()){
            startActivity(new Intent(MainActivity.this,ShowingEvents.class));
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://challenge.myriadapps.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        Toast.makeText(this,prefs.getString("Token",""),Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startlogin();
            }
        });


    }

    private void startlogin() {

        if (!userName.getText().toString().isEmpty() || !password.getText().toString().isEmpty()) {

            Call<Token> call = apiInterface.LoginUser(userName.getText().toString(),password.getText().toString());

            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (!response.isSuccessful()) {

                        Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    token = response.body().getToken();
                    prefs.edit().putString("Token",token).commit();
                    Intent intentToShowingEvents = new Intent(getApplication(), ShowingEvents.class);
                    startActivity(intentToShowingEvents);
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "User name or Password can not be empty! ", Toast.LENGTH_SHORT).show();
        }
    }




}
