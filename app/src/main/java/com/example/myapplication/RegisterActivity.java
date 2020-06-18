package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ActivityResult {

    private EditText edittextFullname, edittextCellnum, edittextIdnum, edittextAdress, edittextEmail, edittextEnterpassword;
    private CheckBox checkboxOldie, checkboxVolunteer;
    private Button btnConfirm;
    private Profile profile;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private Uri imageUri;
    private TextView textViewProfile, textViewId;
    private  boolean flag;


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
        progressBar = findViewById(R.id.progress_bar);
        storageReference = FirebaseStorage.getInstance().getReference("users");
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        profile= new Profile();
        textViewId= findViewById(R.id.textview_insertidphoto);
        textViewProfile= findViewById(R.id.textview_insertprofile);
    }
    private void openFile(){
        Intent intent= new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==1)&&(resultCode==RESULT_OK)&&(data!=null)&&(data.getData()!=null)){
            imageUri= data.getData();

        }
    }
    private void upload(){
        if (imageUri!= null){
            String temp= System.currentTimeMillis()+"."+getFileExtention(imageUri);
            final StorageReference fileReference= storageReference.child(temp);
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            },500);
                            progressBar.setProgress(0);
                            if(flag){
                                profile.setUriId(uri.toString());
                            }
                            else {
                                profile.setUriProfile(uri.toString());
                            }


                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int )progress);
                }
            });
        }
    }
    private String getFileExtention(Uri uri){
        ContentResolver cr= getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    @Override
    public void onClick(View v) {
        String fullname = edittextFullname.getText().toString().trim();
        String cellnum = edittextCellnum.getText().toString().trim();
        String idnum = edittextIdnum.getText().toString().trim();
        String adress = edittextAdress.getText().toString().trim();
        String email = edittextEmail.getText().toString().trim();
        String password = edittextEnterpassword.getText().toString().trim();
        public String getFullnane() {return fullname};
        public void setFullname(String fullname){this.edittextFullname = edittextFullname;}

        public void setCellnum(String cellnum){this.edittextCellnum= edittextCellnum;}

        public void setIdnum(String idnum){this.edittextIdnum= edittextIdnum;}


        if (textViewProfile==v) {
            flag= false;
            openFile();
            upload();
        }
        if (textViewId==v){
            flag=true;
            openFile();
            upload();
        }
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
            String id= databaseReference.push().getKey();
            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile);

        }


    }
}
