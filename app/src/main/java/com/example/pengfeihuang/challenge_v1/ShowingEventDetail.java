package com.example.pengfeihuang.challenge_v1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowingEventDetail extends AppCompatActivity {
    private TextView eventTittle,eventTime,eventDes,eventLocation,eventSpeaker;
    private ApiInterface apiInterface;
    private String SpeakerName = "";
    private ImageView imgEventPoster;
    private Toolbar toolbar;
    private String token;
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

                prefs.edit().putString("Token","").commit();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                Intent intent1 = new Intent(this,ShowingEvents.class);
                startActivity(intent1);
                return  true;
                default:
                    return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_event_detail);

        eventTittle = (TextView)findViewById(R.id.tv_eventTittle);
        eventTime = (TextView)findViewById(R.id.tv_eventTime);
        eventDes = (TextView)findViewById(R.id.tv_eventDes);
        eventLocation = (TextView)findViewById(R.id.tv_eventLocation);
        eventSpeaker = (TextView)findViewById(R.id.tv_eventSpeaker);
        imgEventPoster = (ImageView)findViewById(R.id.imgEventPost);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Events");
        setSupportActionBar(toolbar);

        //add Go back button in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        token = prefs.getString("Token","");
        EventsDetail eventsDetail = (EventsDetail) getIntent().getSerializableExtra("E");

        toolbar.setTitle(eventsDetail.getTitle());
        //Set Tittle
        eventTittle.setText(eventsDetail.getTitle());
       //Set Date
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hha - " );
        String formattedDate=dateFormat. format(eventsDetail.getStart_date_time()).replace("AM", "am").replace("PM","pm");
        DateFormat dateFormat2 = new SimpleDateFormat(" hh:mma" );
        String formattedDate2=dateFormat2. format(eventsDetail.getEnd_date_time());
        eventTime.setText(formattedDate+formattedDate2.replace("AM", "am").replace("PM","pm"));
        //set Description
        eventDes.setText(eventsDetail.getEvent_description());
        //set Location
        eventLocation.setText(eventsDetail.getLocation());
        //Display those speakers and thier bio
        for (SpeakerId speakerId : eventsDetail.getSpeakers()){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(" https://challenge.myriadapps.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = retrofit.create(ApiInterface.class);
            Call<Speaker> call  = apiInterface.getSpeakerDetail(token,speakerId.getId());
            call.enqueue(new Callback<Speaker>() {
                @Override
                public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                    SpeakerName += response.body().getFirst_name() + "\n" + "\n" + response.body().getBio() + "\n"+"\n";
                    eventSpeaker.setText(SpeakerName);
                }

                @Override
                public void onFailure(Call<Speaker> call, Throwable t) {
                    Toast.makeText(ShowingEventDetail.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }

        //showing the pictures
        Picasso.with(this).load(eventsDetail.getImage_url()).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgEventPoster, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


    }


}
