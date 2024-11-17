//Authors: Jakob Mösenbacher, Franziska Kaßler

package com.example.pettracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Welcome extends AppCompatActivity {
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private LinearLayout ll_textContainer;
    private LinearLayout ll_buttonContainer;

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
                //Delet File
                CSVHandler csvHandler = new CSVHandler();
                csvHandler.deleteFile(context);
            }
        });

        // On Click Add Button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a toast message when the button is clicked
                Intent intent = new Intent(Welcome.this, AddAnimal.class);
                startActivity(intent);
            }
        });
    }
    public void addAnimal(Animal newAnimal){
        animals.add(newAnimal);
    }


    //AddButton (with ico and catname)
    private void addButtons(String text, int resId) {
        ll_textContainer.setVisibility(View.INVISIBLE);

        // Create a new button
        MaterialButton button = new MaterialButton(this);
        //Text style
        button.setText(text);
        button.setTextSize(20);
        button.setTextColor(getResources().getColor(R.color.white));

        // Set button icon
        button.setIconResource(resId);
        button.setIconTintResource(R.color.white);
        button.setIconSize(getResources().getDimensionPixelSize(R.dimen.icon_size));

        // Set button styling
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.main)); // Background color
        button.setStrokeColorResource(R.color.main); // Stroke color
        button.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.corner_radius)); // Corner radius
        button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL); // Align text and icon



        // Add the button to the container
        // Erstelle LayoutParams für den Button
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,  // Breite auf Match-Parent setzen
                ViewGroup.LayoutParams.WRAP_CONTENT  // Höhe wird basierend auf dem Inhalt angepasst
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
            for(Animal animal : animals) {
                addButtons(animal.getName(),animal.getImageResId());
            }
        }
    }
}