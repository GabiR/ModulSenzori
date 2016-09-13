package com.example.modulsenzori.objects;

/**
 * Created by GabiRotaru on 11/07/16.
 */
public class Sensor {
    String title; // Temperatura, Umiditate, Lumina, Presiune, PresiuneRel, TemperaturaAmbient
    String status;
    String icon_blue;
    String icon_gray;
    String last_reading;

    public Sensor(String title, String status, String icon_blue, String icon_gray, String last_reading) {
        this.title = title;
        this.status = status;
        this.icon_blue = icon_blue;
        this.icon_gray = icon_gray;
        this.last_reading = last_reading;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon_blue() {
        return icon_blue;
    }

    public void setIcon_blue(String icon_blue) {
        this.icon_blue = icon_blue;
    }

    public String getIcon_gray() {
        return icon_gray;
    }

    public void setIcon_gray(String icon_gray) {
        this.icon_gray = icon_gray;
    }

    public String getLast_reading() {
        return last_reading;
    }

    public void setLast_reading(String last_reading) {
        this.last_reading = last_reading;
    }


}

