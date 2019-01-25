package com.example.pengfeihuang.challenge_v1;

import java.io.Serializable;

public class SpeakerId implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public SpeakerId(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
