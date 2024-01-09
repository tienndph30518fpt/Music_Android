package com.example.thi20_mussic;

import java.io.Serializable;

public class Song implements Serializable {
    private String name;
    private String singer;
    private int resouce;
    private int durationsong;



    private Boolean isPlay;

    public Boolean getPlay() {
        return isPlay;
    }

    public void setPlay(Boolean play) {
        isPlay = play;
    }

    public int getDurationsong() {
        return durationsong;
    }

    public void setDurationsong(int durationsong) {
        this.durationsong = durationsong;
    }

    public Song() {
    }

    public Song(String name, String singer, int resouce) {
        this.name = name;
        this.singer = singer;
        this.resouce = resouce;
        this.isPlay = false;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getResouce() {
        return resouce;
    }

    public void setResouce(int resouce) {
        this.resouce = resouce;
    }


}
