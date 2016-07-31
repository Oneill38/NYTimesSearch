package com.example.meganoneill.nytimessearch.models;

import android.os.Bundle;

/**
 * Created by meganoneill on 7/30/16.
 */
public class SearchFilter {
    //values from fragment
    String date;
    String sort;
    Boolean sports = false;
    Boolean arts = false;
    Boolean fashion = false;

    public SearchFilter(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getSports() {
        return sports;
    }

    public void setSports(Boolean sports) {
        this.sports = sports;
    }

    public Boolean getArts() {
        return arts;
    }

    public void setArts(Boolean arts) {
        this.arts = arts;
    }

    public Boolean getFashion() {
        return fashion;
    }

    public void setFashion(Boolean fashion) {
        this.fashion = fashion;
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        if (this.date != null) {
            b.putString(date, this.date);
        }
        if (this.sort != null) {
            b.putString(sort, this.sort);
        }
        if (this.fashion != null) {
            b.putBoolean("fashion", this.fashion);
        }
        if (this.arts != null) {
            b.putBoolean("arts", this.arts);
        }
        if (this.sports != null) {
            b.putBoolean("sports", this.sports);
        }
        return b;
    }


}
