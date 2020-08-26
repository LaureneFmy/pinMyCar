package com.example.pinmycar;

import android.widget.EditText;

import java.util.Date;

public class Pin {
    EditText label;
    Date date;
    Double latitude;

    public EditText getLabel() {
        return label;
    }

    public void setLabel(EditText label) {
        this.label = label;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    Double longitude;
}