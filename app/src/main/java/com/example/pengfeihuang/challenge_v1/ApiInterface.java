package com.example.pengfeihuang.challenge_v1;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
//    @POST("login")
//    Call<Token> LoginUser(@Body Login login);
    @POST("login")
    Call<Token> LoginUser(@Query("Username") String userName,
                           @Query("Password") String password);

    @GET("events")
    Call<List<Events>> getEvents(@Header("Authorization") String authToken);

    @GET("events/{id}")
    Call<EventsDetail> getEventsDetail(@Header("Authorization") String authToken, @Path("id") int eventId);

    @GET("speakers/{id}")
    Call<Speaker> getSpeakerDetail(@Header("Authorization") String autoToken, @Path("id") int speakerId);


}
