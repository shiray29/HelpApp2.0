package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLoginconfirm ;
    private EditText edittextLoginemail, edittextLoginpassword;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth= FirebaseAuth.getInstance();
        btnLoginconfirm = findViewById(R.id.btn_loginconfirm);
        edittextLoginemail = findViewById(R.id.edittext_loginemail);
        edittextLoginpassword = findViewById(R.id.edittext_logingpassword);
        btnLoginconfirm.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        String email = edittextLoginemail.getText().toString().trim();
        String password = edittextLoginpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            edittextLoginemail.setError("אימייל חובה");
            return;

        }

        if (TextUtils.isEmpty(password)) {
            edittextLoginpassword.setError("סיסמה חובה");
            return;
        }
        if (password.length()<6){
            edittextLoginpassword.setError("הסיסמה חייבת להיות מעל 5 תווים");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                 //   startActivity();

                }
                else {
                    Toast.makeText(LoginActivity.this,"שגיאה שם משתמש וסיסמה אינם נכונים",Toast.LENGTH_SHORT).show();
                }
            }
        })
}
