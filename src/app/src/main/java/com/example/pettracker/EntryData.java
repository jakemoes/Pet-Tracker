package com.example.pettracker;

import java.time.LocalDate;
import java.util.Date;

public class EntryData {
    private String note;
    private String food;
    private String vomit;
    private String stool;
    private LocalDate date;

    public EntryData() {
    }

    public EntryData(String note, String food, String vomit, String stool, LocalDate date) {
        this.note = note;
        this.food = food;
        this.vomit = vomit;
        this.stool = stool;
    }

    // Getter und Setter
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getVomit() {
        return vomit;
    }

    public void setVomit(String vomit) {
        this.vomit = vomit;
    }

    public String getStool() {
        return stool;
    }

    public void setStool(String stool) {
        this.stool = stool;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
