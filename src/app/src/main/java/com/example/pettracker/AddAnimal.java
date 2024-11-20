package com.example.pettracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AddAnimal extends AppCompatActivity {

    private  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_animal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });



        // Define elements
        Button cancelButton = findViewById(R.id.btn_btn_cancel);
        Button saveButton = findViewById(R.id.btn_save);
        EditText editTextName = findViewById(R.id.et_catName);
        EditText editTextAge = findViewById(R.id.et_catAge);
        Spinner spinnerEmoji = findViewById(R.id.s_emoji);
        Spinner spinnerAnimal = findViewById(R.id.s_Animal);

        context = this;

        //Spinner AnimalType
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.animal_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimal.setAdapter(adapter);


        //Spinner Emoji
        List<DropdownEmoji> items = new ArrayList<>();
        items.add(new DropdownEmoji(R.drawable.dog_cat, "Select an Emoji"));
        items.add(new DropdownEmoji(R.drawable.cat_01, "Standard"));
        items.add(new DropdownEmoji(R.drawable.cat_02, "Liegend"));
        items.add(new DropdownEmoji(R.drawable.cat_03, "Süß"));
        items.add(new DropdownEmoji(R.drawable.cat_04, "Smiling"));
        items.add(new DropdownEmoji(R.drawable.cat_05, "Sitting"));
        items.add(new DropdownEmoji(R.drawable.dog_01, "Sitting"));
        items.add(new DropdownEmoji(R.drawable.dog_02, "Standard"));
        items.add(new DropdownEmoji(R.drawable.dog_03, "Sitting"));
        items.add(new DropdownEmoji(R.drawable.dog_04, "Laughing"));
        DropdownEmojiAdapter emojiAdapter = new DropdownEmojiAdapter(this, items);
        spinnerEmoji.setAdapter(emojiAdapter);




        // On Click cancel Button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAnimal.this, Welcome.class);
                startActivity(intent);
            }
        });

        // On Click save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = true;
                String name = editTextName.getText().toString();
                if (name.isEmpty()){
                    success = false;
                    showMessageBox("Please enter a name.");
                }


                int age = 0;
                try{
                    age = Integer.parseInt(editTextAge.getText().toString());
                }catch(NumberFormatException e){
                    success = false;
                    editTextAge.setText("");
                    showMessageBox("Please Enter a number in Age.");
                }


                String animalKind = spinnerAnimal.getSelectedItem().toString();

                DropdownEmoji selectedItem = (DropdownEmoji) spinnerEmoji.getSelectedItem();
                int imageId = selectedItem.getImageResId();
                String text = selectedItem.getText();

                if(imageId == R.drawable.dog_cat){
                    success = false;
                    showMessageBox("Please select an Emoji.");
                }



                if(success) {
                    Animal animal = new Animal(name, age,imageId,animalKind);
                    CSVHandler csvHandler = new CSVHandler();
                    csvHandler.writeAnimalCSV(context , animal);

                    Intent intent = new Intent(AddAnimal.this, Welcome.class);
                    startActivity(intent);
                }
            }
        });

        // Hide the keyboard when the user taps the "Done" button
        editTextAge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editTextAge.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private void showMessageBox(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Error")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create and show the AlertDialog
        AlertDialog alert = builder.create();
        alert.show();
    }
}