package com.example.pettracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddAppointment extends AppCompatActivity {

    private  String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton cancelButton = findViewById(R.id.btn_cancel);
        MaterialButton saveButton = findViewById(R.id.btn_save);
        MaterialButton btn_date = findViewById(R.id.btn_chooseDate);
        MaterialButton btn_remember = findViewById(R.id.btn_chooseRememberDate);
        EditText et_date = findViewById(R.id.et_datum);
        EditText et_rememberMe = findViewById(R.id.et_remember);

        name = getIntent().getStringExtra("NAME");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAppointment = new Intent(AddAppointment.this, Appointments.class);
                startAppointment.putExtra("NAME", name);
                startActivity(startAppointment);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAppointmentData();
                Intent startAppointments = new Intent (AddAppointment.this, Appointments.class);
                startAppointments.putExtra("NAME", name);
                startActivity(startAppointments);
            }
        });

        //DateTiem Picker Date
        btn_date.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            // Date Picker Dialog
            new DatePickerDialog(AddAppointment.this, (view, year, month, dayOfMonth) -> {
                LocalDateTime pickedDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0);

                // Time Picker Dialog
                new TimePickerDialog(AddAppointment.this, (timeView, hourOfDay, minute) -> {
                    LocalDateTime selectedDateTime = pickedDate.withHour(hourOfDay).withMinute(minute);

                    // Display the selected DateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    et_date.setText(selectedDateTime.format(formatter));

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        //DateTime Picker Remember
        btn_remember.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            // Date Picker Dialog
            new DatePickerDialog(AddAppointment.this, (view, year, month, dayOfMonth) -> {
                LocalDateTime pickedDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0);

                // Time Picker Dialog
                new TimePickerDialog(AddAppointment.this, (timeView, hourOfDay, minute) -> {
                    LocalDateTime selectedDateTime = pickedDate.withHour(hourOfDay).withMinute(minute);

                    // Display the selected DateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    et_rememberMe.setText(selectedDateTime.format(formatter));

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Hide the keyboard when the user taps the "Done" button
        EditText editTextNote = findViewById(R.id.et_Note);
        editTextNote.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editTextNote.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    public void saveAppointmentData(){
        boolean isValid = true;

        //Variables
        EditText et_title = findViewById(R.id.et_title);
        EditText et_date = findViewById(R.id.et_datum);
        EditText et_rememberMe = findViewById(R.id.et_remember);
        EditText et_note = findViewById(R.id.et_Note);

        //Get values
        String title = et_title.getText().toString();
        String note = et_note.getText().toString();
        String date = et_date.getText().toString();
        String rememberMe = et_rememberMe.getText().toString();

        if(title.isEmpty()){
            showMessageBox("Please enter a title");
            isValid = false;
        }
        if(date.isEmpty()){
            showMessageBox("Please enter a date");
            isValid = false;
        }
        if(rememberMe.isEmpty()){
            showMessageBox("Please enter a valid date for the reminder");
            isValid = false;
        }
        if(note.isEmpty()){
            showMessageBox("Please enter a note");
            isValid = false;
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");        LocalDateTime dateDateTime = null;
        try{
           dateDateTime = LocalDateTime.parse(date, formatter);
        }
        catch (Exception e){
            showMessageBox("Please enter a valid date (dd-MM-yyyy HH:mm)");
            isValid = false;
        }

        LocalDateTime rememberMeDateTime = null;
        try{
             rememberMeDateTime= LocalDateTime.parse(rememberMe, formatter);
        }
        catch (Exception e){
            showMessageBox("Please enter a valid date for the reminder (dd-MM-yyyy HH:mm)");
            isValid = false;
        };

        if(isValid){
        AppointmentData appointmentData = new AppointmentData(title, dateDateTime, rememberMeDateTime, note, name);
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.writeAppointmentCSV(this, appointmentData);
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