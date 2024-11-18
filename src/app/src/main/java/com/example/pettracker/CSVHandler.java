package com.example.pettracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVHandler {

    //ADD ANIMAL
    //Add animal to List
public void writeAnimalCSV(Context context, Animal animal){
    String csvFileName = "animal.csv";
    File csvFile = new File(context.getFilesDir(), csvFileName);

    File csvAnimals = new File(csvFileName);
    try (FileWriter writer = new FileWriter(csvFile, true)) {
        // Daten in die CSV-Datei schreiben
        writer.append(animal.getName() + ";" + animal.getAnimalType()+ ";" + animal.getAge() + ";" + animal.getImageResId() + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}


//Get Aniamls from list
    public static ArrayList<Animal> getAnimalsFromCSV(Context context) {
        ArrayList<Animal> animals = new ArrayList<>();
        File csvFile = new File(context.getFilesDir(), "animal.csv");

        // Überprüfen, ob die Datei existiert
        if (!csvFile.exists()) {
            return animals; // Rückgabe einer leeren Liste, wenn keine Datei vorhanden ist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Jede Zeile in die Teile aufsplitten, basierend auf dem Trennzeichen ";"
                String[] animalData = line.split(";");

                // Sicherstellen, dass die Zeile korrekt formatiert ist
                if (animalData.length == 4) {
                    String name = animalData[0];
                    String animalType = animalData[1];
                    int age = Integer.parseInt(animalData[2]);
                    int imageResId = Integer.parseInt(animalData[3]);

                    // Ein neues Animal-Objekt erstellen und zur Liste hinzufügen
                    animals.add(new Animal(name, age, imageResId, animalType));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return animals;
    }

    //Delete all Cats
    public void deleteFile(Context context) {
        // Pfad zur Datei
        File file = new File(context.getFilesDir(), "animal.csv");

        // Überprüfen, ob die Datei existiert
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                // Erfolgreich gelöscht
                Toast.makeText(context, "Datei wurde gelöscht", Toast.LENGTH_SHORT).show();
            } else {
                // Fehler beim Löschen
                Toast.makeText(context, "Fehler beim Löschen der Datei", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Datei existiert nicht
            Toast.makeText(context, "Datei existiert nicht", Toast.LENGTH_SHORT).show();
        }
    }

    //ENTRY
    public void writeEntryCSV(Context context, EntryData entry){
        String csvFileName = "entry.csv";
        File csvFile = new File(context.getFilesDir(), csvFileName);

        File csvAnimals = new File(csvFileName);
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            // Daten in die CSV-Datei schreiben
            writer.append( entry.getNote() + ";" + entry.getFood() + ";" + entry.getVomit() + ";" + entry.getStool() + ";" + entry.getVomit() + ";" + entry.getDate() +"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
