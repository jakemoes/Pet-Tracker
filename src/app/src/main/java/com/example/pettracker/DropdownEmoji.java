package com.example.pettracker;

public class DropdownEmoji {
    private Integer imageResId;
    private String text;

    public DropdownEmoji(Integer imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}
