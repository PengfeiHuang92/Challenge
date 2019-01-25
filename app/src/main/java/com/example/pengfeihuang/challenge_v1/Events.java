package com.example.pengfeihuang.challenge_v1;

import java.io.Serializable;
import java.util.Date;

public class Events implements Serializable {

    private Integer id;

    private String title;

    private String image_url;

    private Date start_date_time;

    private Date end_date_time;

    private String location;

    private Boolean featured;

    public Events(Integer id, String title, String image_url, Date start_date_time, Date end_date_time, String location, Boolean featured) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.start_date_time = start_date_time;
        this.end_date_time = end_date_time;
        this.location = location;
        this.featured = featured;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public Date getStart_date_time() {
        return start_date_time;
    }

    public Date getEnd_date_time() {
        return end_date_time;
    }

    public String getLocation() {
        return location;
    }

    public Boolean getFeatured() {
        return featured;
    }




}
