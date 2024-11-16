package com.example.pettracker;

import androidx.lifecycle.viewmodel.CreationExtras;

public class Animal {
private String name;
private int age;
private int imageResId;
private String animalType;

    //Empty Constructor
    public Animal(){

    }

    //Constructor
    public Animal(String name, int age, int imageResId, String animalType){
        this.name = name;
        this.age = age;
        this.imageResId = imageResId;
        this.animalType = animalType;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
    this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
            this.animalType = animalType;
    }
}
