package com.example.pettracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void deleteAnimalFile(Context context) {
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
            writer.append( entry.getNote() + ";" + entry.getFood() + ";" + entry.getVomit() + ";" + entry.getStool() + ";" + entry.getVomit() + ";" + entry.getDate() + ";" + entry.getName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEntryFile(Context context) {
        // Pfad zur Datei
        File file = new File(context.getFilesDir(), "entry.csv");

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



    //APPOINTMENT
    public static ArrayList<AppointmentData> getAppointmentsFromCSV(Context context) {
        ArrayList<AppointmentData> appointments = new ArrayList<>();
        File csvFile = new File(context.getFilesDir(), "appointment.csv");

        if (!csvFile.exists()) {
            return appointments;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] animalData = line.split(";");
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                if (animalData.length == 5) {
                    String title = animalData[0];
                    LocalDateTime apointmentTime = null;
                    try {
                        apointmentTime = LocalDateTime.parse(animalData[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    LocalDateTime reminderTime = null;
                    try {
                        reminderTime = LocalDateTime.parse(animalData[2]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String note = animalData[3];
                    String name = animalData[4];

                    appointments.add(new AppointmentData(title, apointmentTime, reminderTime, note, name));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appointments;
    }
    public void writeAppointmentCSV(Context context, AppointmentData appointment){
        String csvFileName = "appointment.csv";
        File csvFile = new File(context.getFilesDir(), csvFileName);

        File csvAnimals = new File(csvFileName);
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            // Daten in die CSV-Datei schreiben
            writer.append(appointment.getTitel() + ";" + appointment.getDate()+ ";" + appointment.getRemeberDate() + ";" + appointment.getNote() + ";" + appointment.getCatName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteAppointmentFile(Context context) {
        // Pfad zur Datei
        File file = new File(context.getFilesDir(), "appointment.csv");

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
    public void saveAppointmentsToCSV(Context context, ArrayList<AppointmentData> appointments) {
        File csvFile = new File(context.getFilesDir(), "appointment.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            for (AppointmentData appointment : appointments) {
                String line = appointment.getTitel() + ";" +
                        appointment.getDate().toString() + ";" +
                        appointment.getRemeberDate().toString() + ";" +
                        appointment.getNote() + ";" +
                        appointment.getTitel();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<EntryData> getEntriesFromCSV(Context context) {
        ArrayList<EntryData> entries = new ArrayList<>();
        File csvFile = new File(context.getFilesDir(), "entry.csv");

        if (!csvFile.exists()) {
            return entries; // leeren Liste rückgeben, wenn keine Datei vorhanden
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] entryData = line.split(";");
                if (entryData.length == 7) {
                    String note = entryData[0];
                    String food = entryData[1];
                    String vomit = entryData[2];
                    String stool = entryData[3];
                    LocalDate date = LocalDate.parse(entryData[4]);
                    String name = entryData[5];
                    entries.add(new EntryData(note, food, vomit, stool, date, name));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
