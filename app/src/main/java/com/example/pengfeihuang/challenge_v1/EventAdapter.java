package com.example.pengfeihuang.challenge_v1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<Events> resultList;
    private final Activity context;
    private String token;
    private int eventId=0;
    public TextView title, date;
    public ImageView poster;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvTitle);
            date = (TextView) view.findViewById(R.id.tvDate);
            poster = (ImageView) view.findViewById(R.id.imgEvent);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final View view = v;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(" https://challenge.myriadapps.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            eventId = getAdapterPosition() +1;
         //   Toast.makeText(view.getContext(),":(" +Integer.toString(eventId),Toast.LENGTH_LONG).show();

           // Call<List<EventsDetail>> call = apiInterface.getEventsDetail(eventId,token);
            Call<EventsDetail> call = apiInterface.getEventsDetail(token,eventId);
            call.enqueue(new Callback<EventsDetail>() {
                @Override
                public void onResponse(Call<EventsDetail> call, Response<EventsDetail> response) {
                 //   Toast.makeText(view.getContext(),response.body().getTitle(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(),ShowingEventDetail.class);
                    intent.putExtra("E",response.body());
                    intent.putExtra("Token",token);
                    view.getContext().startActivity(intent);

                }

                @Override
                public void onFailure(Call<EventsDetail> call, Throwable t) {
                    Toast.makeText(view.getContext(),t.getMessage()+":(",Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public EventAdapter(Activity context, List<Events> resultList,String token){
        this.context = context;
        this.resultList = resultList;
        this.token = token;

    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventAdapter.MyViewHolder myViewHolder, int position) {
        Events event = resultList.get(position);
        title.setText(event.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hha - " );
        String formattedDate=dateFormat. format(event.getStart_date_time()).replace("AM", "am").replace("PM","pm");
        DateFormat dateFormat2 = new SimpleDateFormat(" hh:mma" );
        String formattedDate2=dateFormat2. format(event.getEnd_date_time());
        date.setText(formattedDate+formattedDate2.replace("AM", "am").replace("PM","pm"));
       // date.setText(event.getStart_date_time().toString());
        Picasso.with(context).load(event.getImage_url()).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(poster, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


    }

    @Override
    public int getItemCount()
    {
        return resultList.size();
    }
}
