package com.example.pettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Appointments extends AppCompatActivity {


    private LinearLayout ll_buttonContainer;
    private LinearLayout ll_textContainer;
    private ArrayList<AppointmentData> appointments= new ArrayList<AppointmentData>();
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointments);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = getIntent().getStringExtra("NAME");

        //Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //View indicator = findViewById(R.id.indicator);
        bottomNavigationView.setSelectedItemId(R.id.appointments);


        // Setze den Listener für das Klicken auf die einzelnen Tabs
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.main:
                    startActivity(new Intent(Appointments.this, Welcome.class));
                    return true;
                case R.id.course:
                    startActivity(new Intent(Appointments.this, Trend.class));
                    return true;
                case R.id.entry:
                    Intent startEntry = new Intent(Appointments.this, Entry.class);
                    startEntry.putExtra("NAME", name);
                    startActivity(startEntry);
                    return true;
            }
            return false;
        });

        MaterialButton addButton = findViewById(R.id.btn_add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddAppointment = new Intent(Appointments.this, AddAppointment.class);
                startAddAppointment.putExtra("NAME", name);
                startActivity(startAddAppointment);
            }
        });


        CSVHandler csvHandler = new CSVHandler();
        appointments = csvHandler.getAppointmentsFromCSV(this);
        ll_textContainer = findViewById(R.id.ll_textContainer);
        ll_buttonContainer = findViewById(R.id.ll_buttonContainer);
        ViewHandler();
    }
    //Add Termin
    private void addAppointment(String text, int number) {
        ll_textContainer.setVisibility(View.INVISIBLE);

        // Create a new button
        MaterialButton button = new MaterialButton(this);
        button.setId(number);

        //Text style
        button.setText(text);
        button.setTextSize(20);
        button.setTextColor(getResources().getColor(R.color.white));

        // Set button styling
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.main)); // Background color
        button.setStrokeColorResource(R.color.main); // Stroke color
        button.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.corner_radius)); // Corner radius
        //button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL); // Align text and icon

        // Add the button to the container
        // Erstelle LayoutParams für den Button
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,  // Breite auf Match-Parent setzen
                300  // HÃ¶he wird basierend auf dem Inhalt angepasst
        );

        // Setze die Margins (Abstand auf der linken und rechten Seite)
        int leftMargin = getResources().getDimensionPixelSize(R.dimen.margin_left); // Linker Abstand (in Pixel)
        int rightMargin = getResources().getDimensionPixelSize(R.dimen.margin_right); // Rechter Abstand (in Pixel)

        // Setze die Margins auf den LayoutParams
        layoutParams.setMargins(leftMargin, 0, rightMargin, 0); // (left, top, right, bottom)

        // Füge den Button mit den LayoutParams hinzu
        button.setLayoutParams(layoutParams);

        // Füge den Button zum Layout hinzu
        ll_buttonContainer.addView(button);


        //Wenn eine Termin
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = text;
                //Intent intent = new Intent(Appointment.this, Entry.class);
                //intent.putExtra("NAME", text);
                //startActivity(intent);
            }
        });
    }
    private void addTextMessage() {
        // Create Text View
        TextView textView = new TextView(this);
        //Style
        textView.setText("Keine anstehenden Termine. Füge mit dem + Button einen neuen Termin hinzu.");
        textView.setTextSize(30);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setVisibility(View.VISIBLE);
        textView.setPadding(100,25,100,25);


        // Add the text View
        ll_textContainer.addView(textView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
    }


    private void ViewHandler(){
        if(appointments.isEmpty()){
            addTextMessage();
        }
        else {
            int counter = 0;
            for(AppointmentData appointmentData : appointments) {
                addAppointment(appointmentData.getTitel(), counter);
                counter ++;
            }
        }
    }
}