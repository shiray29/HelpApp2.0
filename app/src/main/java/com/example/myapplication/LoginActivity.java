package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLoginconfirm ;
    private EditText edittextLoginemail, edittextLoginpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLoginconfirm = findViewById(R.id.btn_loginconfirm);
        edittextLoginemail = findViewById(R.id.edittext_loginemail);
        edittextLoginpassword = findViewById(R.id.edittext_logingpassword);
        btnLoginconfirm.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        String email = edittextLoginemail.getText().toString().trim();
        String password = edittextLoginpassword.getText().toString().trim();
        // TODO: לעבור לאקטיביטי - קשיש או מתנדב

    }
}
