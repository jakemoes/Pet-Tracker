package com.example.pettracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trend extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnSwitch = findViewById(R.id.btn_switch);

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Wechseln zu Trend2
                Intent intent = new Intent(Trend.this, Trend2.class);
                startActivity(intent);
            }
        });


    }

    private Map<String, Integer> categorizeData(String animalName, Context context) {
        CSVHandler csvHandler = new CSVHandler();
        ArrayList<EntryData> entries = csvHandler.getEntriesFromCSV(context);

        // Zähle die verschiedenen Kategorien
        int stoolProblematic = 0;
        int noProblems = 0;
        int both = 0;
        int vomit = 0;

        for (EntryData entry : entries) {
            if (entry.getName().equals(animalName)) {
                boolean hasStoolProblem = "problematisch".equals(entry.getStool());
                boolean hasVomit = "ja".equals(entry.getVomit());

                if (hasStoolProblem && hasVomit) {
                    both++;
                } else if (hasStoolProblem) {
                    stoolProblematic++;
                } else if (hasVomit) {
                    vomit++;
                } else {
                    noProblems++;
                }
            }
        }

        // Erstelle und gib Map zurück, um Zählungen an Frontend zu übergeben
        Map<String, Integer> categoryCounts = new HashMap<>();
        categoryCounts.put("Stuhl problematisch", stoolProblematic);
        categoryCounts.put("Keine Probleme", noProblems);
        categoryCounts.put("Beides", both);
        categoryCounts.put("Erbrechen", vomit);

        return categoryCounts;
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
