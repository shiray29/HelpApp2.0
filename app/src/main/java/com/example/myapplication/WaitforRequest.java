package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaitforRequest extends AppCompatActivity implements View.OnClickListener
{
    private Button btnReqChange;
    private TextView textViewExplain;
    private TextView textViewWaitforReq;
    private ImageView imageViewWait;
    private Button btnEditWait;
    private Profile profile;

    public WaitforRequest(Button reqChange) {
       reqChange= findViewById(R.id.btn_reqchange);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitfor_request);
        btnReqChange = findViewById(R.id.btn_reqchange);
        btnEditWait = findViewById(R.id.btn_editwait);
        textViewWaitforReq = findViewById(R.id.textView_waitforreq);
        textViewExplain = findViewById(R.id.textView_explain);
        imageViewWait = findViewById(R.id.imageViewWait);
    }

    @Override
    public void onClick(View V)
    {
        if (btnEditWait == V)
        {
            Intent intent_editprofile = new Intent(this, EditProfile.class);
            startActivity(intent_editprofile);
        }

        if (btnReqChange == V)
        {
            Intent intent_waitForRequest = new Intent(this, WaitforRequest.class);
            startActivity(intent_waitForRequest);
        }
    }
}
