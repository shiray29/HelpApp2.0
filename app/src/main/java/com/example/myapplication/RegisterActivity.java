package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edittextFullname, edittextCellnum, edittextIdnum, edittextAdress, edittextEmail, edittextEnterpassword;
    private CheckBox checkboxOldie, checkboxVolunteer;
    private Button btnConfirm;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edittextFullname = findViewById(R.id.edittext_fullname);
        edittextCellnum = findViewById(R.id.edittext_cellnum);
        edittextIdnum = findViewById(R.id.edittext_idnum);
        edittextAdress = findViewById(R.id.edittext_adress);
        edittextEmail = findViewById(R.id.edittext_email);
        edittextEnterpassword = findViewById(R.id.edittext_enterpassword);
        checkboxOldie = findViewById(R.id.checkbox_oldie);
        checkboxVolunteer = findViewById(R.id.checkbox_volunteer);
        btnConfirm = findViewById(R.id.btn_confirm);
    }


    @Override
    public void onClick(View v) {
        String fullname = edittextFullname.getText().toString().trim();
        String cellnum = edittextCellnum.getText().toString().trim();
        String idnum = edittextIdnum.getText().toString().trim();
        String adress = edittextAdress.getText().toString().trim();
        String email = edittextEmail.getText().toString().trim();
        String password = edittextEnterpassword.getText().toString().trim();

        profile = new Profile(String fullname , String cellnum , String idnum , String adress , String email , String password , boolean isOldie , boolean isVolunteer, false)
        if (btnConfirm == v) {
            if ((checkboxOldie.isChecked() && checkboxVolunteer.isChecked()) || (!checkboxOldie.isChecked() && !checkboxVolunteer.isChecked()))
            {
                Toast.makeText(this,"יש לסמן רק אחת מהאפשרויות - מתנדב או קשיש", Toast.LENGTH_SHORT).show();
                checkboxOldie.setChecked(false);
                checkboxVolunteer.setChecked(false);
            }
            if (checkboxOldie.isChecked()){
                profile.setOld(true);

            }
            if (checkboxVolunteer.isChecked()){
                profile.setOld(false);

            }


        }


    }
}
