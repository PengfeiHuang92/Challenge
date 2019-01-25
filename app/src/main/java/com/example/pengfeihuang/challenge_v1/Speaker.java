package com.example.pengfeihuang.challenge_v1;

import java.io.Serializable;

public class Speaker implements Serializable {
    private int id;
    private String first_name;
    private String last_name;
    private String bio;
    private String image_rul;

    public Speaker(int id, String first_name, String last_name, String bio, String image_rul) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.bio = bio;
        this.image_rul = image_rul;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setImage_rul(String image_rul) {
        this.image_rul = image_rul;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBio() {
        return bio;
    }

    public String getImage_rul() {
        return image_rul;
    }
}
