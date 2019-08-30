package com.example.orderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.orderingapp.Common.Common;

public class FirstTime extends AppCompatActivity {
private Button mDoneButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        mDoneButton=findViewById(R.id.doneButton);
        Common.firstTimeLogin=false;
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstTime.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
