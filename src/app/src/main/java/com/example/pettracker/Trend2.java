package com.example.pettracker;

<<<<<<< HEAD
import android.content.Context;
=======
>>>>>>> 2c79acdf51ec419c9281ec64b664f0f6b1cb3c64
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Trend2 extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trend2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tiername von Trend Aktivität bekommen
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");

        Button btnBack = findViewById(R.id.btn_switch);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //zurück zur Trend-Seite wechseln
                Intent intent = new Intent(Trend2.this, Trend.class);
                startActivity(intent);
            }
        });

<<<<<<< HEAD
        Context context = this;
        CSVHandler csvHandler = new CSVHandler();
        List<EntryData> entries = csvHandler.getEntriesFromCSV(context);
=======
        CSVHandler csvHandler = new CSVHandler();
        List<EntryData> entries = csvHandler.getEntriesFromCSV(this);
>>>>>>> 2c79acdf51ec419c9281ec64b664f0f6b1cb3c64

        // Entries filtern
        List<EntryData> filteredEntries = filterEntriesByName(entries, name);

        // Entries in Scroll View anzeigen
        LinearLayout entryLayout = findViewById(R.id.entryLayout);
        for (EntryData entry : filteredEntries) {
            TextView entryTextView = new TextView(this);
            entryTextView.setText(formatEntryText(entry));
            entryTextView.setBackgroundResource(R.drawable.shape);
            entryLayout.addView(entryTextView);
        }
    }

    private List<EntryData> filterEntriesByName(List<EntryData> entries, String name) {
        List<EntryData> filteredEntries = new ArrayList<>();
        for (EntryData entry : entries) {
            if (entry.getName().equals(name)) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }
    private String formatEntryText(EntryData entry) {
        String date = entry.getDate().toString();  // Assuming date is in LocalDate format
        String stool = entry.getStool();
        String vomit = entry.getVomit();
        String food = entry.getFood();
        String note = entry.getNote();

        return String.format(
                "<b>%s</b>\nStuhl: %s\nErbrochen: %s\nFutter: %s\nNotizen: %s",
                date, stool, vomit, food, note
        );
    }
}