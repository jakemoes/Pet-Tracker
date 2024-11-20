package com.example.pettracker;

import java.time.LocalDateTime;

public class AppointmentData {
    private  String titel;
    private LocalDateTime date;
    private LocalDateTime remeberDate;
    private String note;
    private String catName;


    // Full constructor
    public AppointmentData(String titel, LocalDateTime date, LocalDateTime remeberDate, String note, String catName) {
        this.titel = titel;
        this.date = date;
        this.remeberDate = remeberDate;
        this.note = note;
        this.catName = catName;
    }

    //Empty constructor
    public AppointmentData() {
    }


    // Getter and Setter for titel
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    // Getter and Setter for date
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Getter and Setter for remeberDate
    public LocalDateTime getRemeberDate() {
        return remeberDate;
    }

    public void setRemeberDate(LocalDateTime remeberDate) {
        this.remeberDate = remeberDate;
    }

    // Getter and Setter for note
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

}
