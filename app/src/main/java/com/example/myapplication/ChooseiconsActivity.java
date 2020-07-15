package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChooseiconsActivity extends AppCompatActivity {

    private ImageView imageViewBuild, imageViewCall, imageViewClean, imageViewCompany, imageViewShop;
    private TextView textViewPick, textViewBuild, textViewCall, textViewClean, textViewCompany, textViewShop;
    private Button btnconfirmicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseicons);
        imageViewBuild = findViewById(R.id.imageView_build);
        imageViewCall = findViewById(R.id.imageView_call);
        imageViewClean = findViewById(R.id.imageView_clean);
        imageViewCompany = findViewById(R.id.imageView_company);
        imageViewShop = findViewById(R.id.imageView_shop);
        textViewBuild = findViewById(R.id.textView_build);
        textViewCall = findViewById(R.id.textView_call);
        textViewClean = findViewById(R.id.textView_clean);
        textViewCompany = findViewById(R.id.textView_company);
        textViewShop = findViewById(R.id.textView_shop);
        textViewPick = findViewById(R.id.textView_pick);
        btnconfirmicon = findViewById(R.id.btn_confirmicon);
    }

    public void onClick(View V){
        if (imageViewBuild==V) // TODO - and so on for all buttons. my question - how can we cancel?
            // TODO - counter - ךעדכן רק בסוף
        {   imageViewBuild.setClickable(false);
            DatabaseReference  ref = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    profile.setIsBuild(true);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }

        if (btnconfirmicon==V){ // TODO - add "are you sure?" pop-up
            Intent intent_waitForRequest = new Intent(this, WaitforRequest.class);
            startActivity(intent_waitForRequest);
        }
    }
}
