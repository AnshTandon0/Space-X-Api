package com.example.spacexapi;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "SpaceX")
public class CrewModal {

    @PrimaryKey
    @NonNull
    private String name;

    private String agency;

    private String imageUrl;

    private String status;

    private String wikipedia;

    public CrewModal()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }
}
