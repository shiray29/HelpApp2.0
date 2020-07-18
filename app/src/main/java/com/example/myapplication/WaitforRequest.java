package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaitforRequest extends AppCompatActivity implements View.OnClickListener {
private Button reqChange, editProfile;
private TextView message, message1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitfor_request);
        reqChange= findViewById(R.id.req_change);
        editProfile= findViewById(R.id.ed_pro);
        message= findViewById(R.id.message);
        message1= findViewById(R.id.message1);



    }

    @Override
    public void onClick(View v) {
        if (editProfile==v){
            startActivity(new Intent(getApplicationContext(),ChooseiconsActivity.class));
        }
        if (reqChange==v){

        }
    }
}
