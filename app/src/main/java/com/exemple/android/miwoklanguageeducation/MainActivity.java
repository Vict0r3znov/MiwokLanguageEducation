package com.exemple.android.miwoklanguageeducation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Views
        TextView numbers = findViewById(R.id.numbers);
        TextView colors = findViewById(R.id.colors);
        TextView family_members = findViewById(R.id.family);
        TextView phrases = findViewById(R.id.phrases);

        //set onClickListener to each View to be able to open the other Activities

        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openNumbersActivity = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(openNumbersActivity);
            }
        });

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openColorsActivity = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(openColorsActivity);
            }
        });

        family_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openFamilyMembersActivity = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(openFamilyMembersActivity);
            }
        });

        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPhrasesActivity = new Intent(MainActivity.this, Phrases.class);
                startActivity(openPhrasesActivity);
            }
        });
    }
    }
