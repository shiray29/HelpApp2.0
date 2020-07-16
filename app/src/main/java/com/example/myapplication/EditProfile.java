package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private EditText fullName, cellNum, adress,password;
    private Button  btnClose;
    private Uri imageUri;
    private TextView textViewProfile;
    private ProgressBar progressBar;
    private  boolean flag;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fullName = findViewById(R.id.full_name);
        cellNum = findViewById(R.id.cell_num);
        adress= findViewById(R.id.adress);
        password=findViewById(R.id.password);
        btnClose.findViewById(R.id.btn_close);
        storageReference = FirebaseStorage.getInstance().getReference("users"); // this line defines reference to Firebase Storage in order to store jpg files
        databaseReference= FirebaseDatabase.getInstance().getReference("users"); // this line defines reference to Firebase Database in order to store users data
        progressBar = findViewById(R.id.progress_bar);
        textViewProfile= findViewById(R.id.textview_insertprofile2);
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
                            profile.setUriProfile(uri.toString());



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
        String fullName = fullName.getText().toString().trim();
        String cellNum = cellNum.getText().toString().trim();
        String adress = adress.getText().toString().trim();
        String password = password.getText().toString().trim();

        profile.setName(fullName);
        profile.setCellnum(cellNum);
        profile.setAdress(adress);
        profile.setPassword(password);

    }
     if (textViewProfile==v) {
        flag= false;
        openFile();
        upload();
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Profile profile = dataSnapshot.getValue(Profile.class);
        profile.setName(fullName);
        profile.setUriProfile(imageUri);
        profile.setAdress(adress);
        profile.setCellnum(cellNum);
        profile.getPassword(password);
    }

        if (btnClose == v){
        startActivity(new Intent(getApplicationContext(), profile.isOld()==true?ChooseiconsActivity.class:Search.class)); // starts the suitable activity according to isOld
    }
}


