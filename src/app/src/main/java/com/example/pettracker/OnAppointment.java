package com.example.pettracker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class OnAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_appointment);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Daten aus Intent
        String title = getIntent().getStringExtra("TITLE");
        String date = getIntent().getStringExtra("DATE");
        String reminder = getIntent().getStringExtra("REMINDER");
        String note = getIntent().getStringExtra("NOTE");

        TextView titleView = findViewById(R.id.text_title);
        TextView dateView = findViewById(R.id.text_date);
        TextView reminderView = findViewById(R.id.text_reminder);
        TextView noteView = findViewById(R.id.text_note);

        titleView.setText("Titel: " + title);
        dateView.setText("Datum: " + date);
        reminderView.setText("Erinnerung: " + reminder);
        noteView.setText("Notizen: " + note);


        // Zurück-Button
        MaterialButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> {
            // Zurück zur Appointments Activity
            Intent intent = new Intent(OnAppointment.this, Appointments.class);
            startActivity(intent);
            finish();
        });



        // Löschen-Button
        MaterialButton deleteButton = findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(v -> {
            // Termin aus CSV löschen
            deleteAppointment();

            // Zurück zur Appointments Activity nach dem Löschen
            Intent intent = new Intent(OnAppointment.this, Appointments.class);
            startActivity(intent);
            finish();


        });

    }
    private void deleteAppointment() {
        CSVHandler csvHandler = new CSVHandler();
        ArrayList<AppointmentData> appointments = csvHandler.getAppointmentsFromCSV(this);

        //Daten von Intent abrufen
        Intent intent = getIntent();
        String titleToDelete = intent.getStringExtra("TITLE");

        ArrayList<AppointmentData> updatedAppointments = new ArrayList<>();
        for (AppointmentData appointment : appointments) {
            if (!appointment.getTitel().equals(titleToDelete)) {
                updatedAppointments.add(appointment);
            }
        }

        //aktualisierte Liste zurück in CSV-Datei speichern
        csvHandler.saveAppointmentsToCSV(this, updatedAppointments);

        Toast.makeText(this, "Termin erfolgreich gelöscht", Toast.LENGTH_SHORT).show();
    }

}