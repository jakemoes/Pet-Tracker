//Authors: Jakob Mösenbacher, Franziska Kaßler

package com.example.pettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // Define button add
        Button addButton = findViewById(R.id.btn_btn_add);

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
}