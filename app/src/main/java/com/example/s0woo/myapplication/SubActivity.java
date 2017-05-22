package com.example.s0woo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by s0woo on 2017-04-20.
 */

public class SubActivity extends AppCompatActivity {

    public EditText viewName0;
    public EditText viewName1;
    public EditText viewName2;
    public EditText viewName3;
    public EditText viewName4;
    public EditText viewName5;
    public EditText viewName6;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        viewName0 = (EditText)findViewById(R.id.name0);;
        viewName1 = (EditText)findViewById(R.id.name1);
        viewName2 = (EditText)findViewById(R.id.name2);
        viewName3 = (EditText)findViewById(R.id.name3);
        viewName4 = (EditText)findViewById(R.id.name4);
        viewName5 = (EditText)findViewById(R.id.name5);
        viewName6 = (EditText)findViewById(R.id.name6);

        Intent intent = getIntent();
        viewName0.setText(intent.getStringExtra("Name0"));
        viewName1.setText(intent.getStringExtra("Name1"));
        viewName2.setText(intent.getStringExtra("Name2"));
        viewName3.setText(intent.getStringExtra("Name3"));
        viewName4.setText(intent.getStringExtra("Name4"));
        viewName5.setText(intent.getStringExtra("Name5"));
        viewName6.setText(intent.getStringExtra("Name6"));

        viewName0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("OutName",viewName0.getText().toString());
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        viewName1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("OutName",viewName1.getText().toString());
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        viewName2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("OutName",viewName2.getText().toString());
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        viewName3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("OutName",viewName3.getText().toString());
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        viewName4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("OutName",viewName4.getText().toString());
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        viewName5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("OutName",viewName5.getText().toString());
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        viewName6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("OutName",viewName6.getText().toString());
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });


    }
}
