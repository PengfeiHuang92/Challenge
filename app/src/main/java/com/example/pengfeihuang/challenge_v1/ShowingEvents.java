package com.example.pengfeihuang.challenge_v1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowingEvents extends AppCompatActivity {
    private String token;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private Toolbar toolbar;
    public SharedPreferences prefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                prefs.edit().putString("Token","").commit();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_events);
        //recycler View
        recyclerView = (RecyclerView)findViewById(R.id.recycleEvents);
        //toolbar
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Events");
        setSupportActionBar(toolbar);
        //share preferences
        prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        token = prefs.getString("Token","");
        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://challenge.myriadapps.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        showEvents(token);

    }

    private void showEvents(String tmpToken) {
        Call<List<Events>> call = apiInterface.getEvents(tmpToken);
        final String token = tmpToken;
        call.enqueue(new Callback<List<Events>>() {
            @Override
            public void onResponse(Call<List<Events>> call, Response<List<Events>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(ShowingEvents.this, ":(", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Events> tmpEvent = response.body();
                eventAdapter = new EventAdapter(ShowingEvents.this,tmpEvent,token);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ShowingEvents.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(eventAdapter);
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Events>> call, Throwable t) {
              Toast.makeText(ShowingEvents.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
