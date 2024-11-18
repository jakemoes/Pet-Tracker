package com.example.pettracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Entry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        Button saveButton = findViewById(R.id.btn_save);
        Spinner s_stool = findViewById(R.id.s_stool);
        Spinner s_vomit = findViewById(R.id.s_vomit);

        //Spinner AnimalType
        ArrayAdapter<CharSequence> adapter_stool = ArrayAdapter.createFromResource(this,
                R.array.stool, android.R.layout.simple_spinner_item);
        adapter_stool.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_stool.setAdapter(adapter_stool);


        ArrayAdapter<CharSequence> adapter_vomit = ArrayAdapter.createFromResource(this,
                R.array.vomit, android.R.layout.simple_spinner_item);
        adapter_stool.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_vomit.setAdapter(adapter_stool);

        // On Click cancel Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    private void saveData(){
        boolean success = true;
        EditText et_food = findViewById(R.id.et_food);
        EditText et_note = findViewById(R.id.et_Note);
        Spinner s_stool = findViewById(R.id.s_stool);
        Spinner s_vomit = findViewById(R.id.s_vomit);

        String food = et_food.getText().toString();
        String note = et_note.getText().toString();
        String vomit = s_vomit.getSelectedItem().toString();
        String stool = s_stool.getSelectedItem().toString();

        if(food.isEmpty()){
            success = false;
            showMessageBox("Bitte fülle das Feld Futter aus");
        }
        if(success){
            Context context = this;
            LocalDate date = LocalDate.now();
            EntryData entryData = new EntryData(note, food, vomit, stool, date);
            CSVHandler csvHandler = new CSVHandler();
            csvHandler.writeEntryCSV(context, entryData);
            Toast.makeText(context, "Daten gespeichert!", Toast.LENGTH_SHORT).show();
            et_food.setText("");
            et_food.setText("");
        }
    }
    private void showMessageBox(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Error")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the OK button click here
                    }
                });
        // Create and show the AlertDialog
        AlertDialog alert = builder.create();
        alert.show();
    }
}