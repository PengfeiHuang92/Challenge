package com.example.pengfeihuang.challenge_v1;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventsDetail extends Events implements Serializable {

    private String event_description;
    //private List<SpeakerId> speakers = null;
    private List<SpeakerId> speakers;

    public EventsDetail(Integer id, String title, String image_url, Date start_date_time, Date end_date_time, String location, Boolean featured, String event_description, List<SpeakerId> speakers) {
        super(id, title, image_url, start_date_time, end_date_time, location, featured);
        this.event_description = event_description;
        this.speakers = speakers;
    }

    public List<SpeakerId> getSpeakers() {
        return speakers;
    }


    public void setSpeakers(List<SpeakerId> speakers) {
        this.speakers = speakers;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_description() {
        return event_description;
    }
}