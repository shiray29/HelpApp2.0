package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WaitforRequest extends AppCompatActivity implements View.OnClickListener {
private Button reqChange;

    public WaitforRequest(Button reqChange) {
       reqChange= findViewById(R.id.req_change);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitfor_request);


    }

    @Override
    public void onClick(View v) {

    }
}
