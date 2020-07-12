package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

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
    FusedLocationProviderClient fusedLocationProviderClient;



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
        storageReference = FirebaseStorage.getInstance().getReference("users"); // this line defines reference to Firebase Storage in order to store jpg files
        databaseReference= FirebaseDatabase.getInstance().getReference("users"); // this line defines reference to Firebase Database in order to store users data
        profile= new Profile();
        textViewId= findViewById(R.id.textview_insertidphoto);
        textViewProfile= findViewById(R.id.textview_insertprofile);
    }
    private void openFile(){ //this function opens the cellphone's gallery using intents
        Intent intent= new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){ //this function re-defines the imageUri
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==1)&&(resultCode==RESULT_OK)&&(data!=null)&&(data.getData()!=null)){
            imageUri= data.getData();

        }
    }
    private void upload(){
        if (imageUri!= null){
            String temp= System.currentTimeMillis()+"."+getFileExtention(imageUri); //defines a file name to the uploaded image
            final StorageReference fileReference= storageReference.child(temp); // inserts the new image to database storage
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() { // presents a progress bar of the download time
                                    progressBar.setProgress(0);
                                }
                            },500);
                            progressBar.setProgress(0);
                            if(flag){ // inserts the information to the suitable variable using the flag (flag is true if it's ID image)
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
    public void onCreate(Bundle saveInstancestate ) {
        super.onCreate(saveInstancestate);
        setContentView(R.layout.activity_register);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onClick(View v) {
        String fullname = edittextFullname.getText().toString().trim();
        String cellnum = edittextCellnum.getText().toString().trim();
        String idnum = edittextIdnum.getText().toString().trim();
        String adress = edittextAdress.getText().toString().trim();
        String email = edittextEmail.getText().toString().trim();
        String password = edittextEnterpassword.getText().toString().trim();

        profile.setName(fullname);
        profile.setCellnum(cellnum);
        profile.setId(idnum);
        profile.setAdress(adress);
        profile.setEmail(email);
        profile.setPassword(password);


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
        if (btnConfirm == v)
            if (ActivityCompat.checkSelfPermission((RegisterActivity.this(), Manifest.permission.ACCESS_FINE_LOCATION) == (PackageManager.PERMISSION_GRANTED)){
                getLocation();
            }else {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

            }

            if ((checkboxOldie.isChecked() && checkboxVolunteer.isChecked()) || (!checkboxOldie.isChecked() && !checkboxVolunteer.isChecked())) // checks whether the user chose 2 or 0 options
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
            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile); // creates a new user in Firebase Database
            startActivity(new Intent(getApplicationContext(), profile.isOld()==true?ChooseiconsActivity.class:Search.class)); // starts the suitable activity according to isOld

        }


}


