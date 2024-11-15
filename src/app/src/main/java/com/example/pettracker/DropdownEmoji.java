package com.example.pettracker;

public class DropdownEmoji {
    private int imageResId;
    private String text;

    public DropdownEmoji(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}
