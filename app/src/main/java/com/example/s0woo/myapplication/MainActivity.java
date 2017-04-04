package com.example.s0woo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.skp.Tmap.TMapTapi;


public class MainActivity extends AppCompatActivity {



    public EditText editStart;
    public ImageButton btnStart;

    public EditText editFinish;
    public ImageButton btnFinish;

    public EditText editStop1;
    public ImageButton btnStop1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editStart = (EditText)findViewById(R.id.startEdit);
        btnStart = (ImageButton)findViewById(R.id.startBtn);

        editFinish = (EditText)findViewById(R.id.finishEdit);
        btnFinish = (ImageButton)findViewById(R.id.finishBtn);

        editStop1 = (EditText)findViewById(R.id.stop1Edit);
        btnStop1 = (ImageButton)findViewById(R.id.stop1Btn);


        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStart.getText().toString();

            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editFinish.getText().toString();

            }
        });

        btnStop1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStop1.getText().toString();

            }
        });


    }
}
