//Authors: Jakob Mösenbacher, Franziska Kaßler

package com.example.pettracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Welcome extends AppCompatActivity {
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private LinearLayout ll_textContainer;
    private LinearLayout ll_buttonContainer;
    //private MaterialButton button = new MaterialButton(this);
    int result = 0;

    private ScrollView sv_buttons;
    private ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ll_textContainer = findViewById(R.id.ll_textContainer);
        ll_buttonContainer = findViewById(R.id.ll_buttonContainer);
        sv_buttons = findViewById(R.id.sv_buttons);

        //Get animal list
        Context context = this;
        CSVHandler csvHandler = new CSVHandler();
        animals = csvHandler.getAnimalsFromCSV(context);

        ViewHandler();
        // Define button add
        Button addButton = findViewById(R.id.btn_add);
        Button deleteButton = findViewById(R.id.btn_delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteAll("Möchtest du wirklich alle Tiere löschen?", new DialogResultListener() {
                    @Override
                    public void onDialogResult(int result) {
                        if (result == 1) {
                            //Delete File
                            CSVHandler csvHandler = new CSVHandler();
                            csvHandler.deleteAnimalFile(context);
                            csvHandler.deleteEntryFile(context);
                            csvHandler.deleteAppointmentFile(context);
                            Intent intent = new Intent(Welcome.this, Welcome.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Welcome.this, "Abbruch: Tiere wurden nicht gelöscht.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // On Click Add Button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, AddAnimal.class);
                startActivity(intent);
            }
        });
    }
    public void addAnimal(Animal newAnimal){
        animals.add(newAnimal);
    }


    //AddButton (with icon and catname)
    private void addButtons(String text, int resId, int number) {
        ll_textContainer.setVisibility(View.INVISIBLE);

        // Create a new button
        MaterialButton button = new MaterialButton(this);

        button.setId(number);

        //Text style
        button.setText(text);
        button.setTextSize(25);
        button.setTextColor(getResources().getColor(R.color.white));

        // Set button icon
        button.setIconResource(resId);
        button.setIconSize(60);
        button.setIconTintResource(R.color.white);
        button.setIconSize(getResources().getDimensionPixelSize(R.dimen.icon_size));

        // Set button styling
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.main)); // Background color
        button.setStrokeColorResource(R.color.main);
        button.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.corner_radius)); // Corner radius
        button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL); // Align text and icon

        // Add the button to the container
        // Erstelle LayoutParams für den Button
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                250
        );

        int leftMargin = getResources().getDimensionPixelSize(R.dimen.margin_left);
        int rightMargin = getResources().getDimensionPixelSize(R.dimen.margin_right);

        // Setze die Margins auf den LayoutParams
        layoutParams.setMargins(leftMargin, 0, rightMargin, 0);

        // Füge den Button mit den LayoutParams hinzu
        button.setLayoutParams(layoutParams);

        // Füge den Button zum Layout hinzu
        ll_buttonContainer.addView(button);


        //Wenn eine Katze angeklickt wird
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = text;
                Intent intent = new Intent(Welcome.this, Entry.class);
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });
    }

    //Add button
    private void addTextMessage() {
        // Create Text View
        TextView textView = new TextView(this);
        //Style
        textView.setText("Bitte füge eine Katze oder einen Hund mit dem + Button hinzu.");
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
        if(animals.isEmpty()){
            addTextMessage();
        }
        else {
            int counter = 0;
            for(Animal animal : animals) {
                addButtons(animal.getName(),animal.getImageResId(), counter);
                counter ++;
            }
        }
    }
    private void showDialogDeleteAll(String text, DialogResultListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogResult(1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogResult(0);
                    }
                });
        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();
    }
    public interface DialogResultListener {
        void onDialogResult(int result);
    }
}