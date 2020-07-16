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
        int buildCount = 0;
        int callCount = 0;
        int cleanCount = 0;
        int companyCount = 0;
        int shopCount = 0;

        if (imageViewBuild==V) { buildCount++; }
        if (imageViewCall==V) { callCount++; }
        if (imageViewClean==V) { cleanCount++; }
        if (imageViewCompany==V) { companyCount++; }
        if (imageViewShop==V) { shopCount++; }


        if (btnconfirmicon==V){
            DatabaseReference  ref = FirebaseDatabase.getInstance().getReference().
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()); // להוסיף את השאר
            final int finalBuildCount = buildCount;
            final int finalShopCount = shopCount;
            final int finalCompanyCount = companyCount;
            final int finalCleanCount = cleanCount;
            final int finalCallCount = callCount;
            final int finalBuildCount1 = buildCount;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    if (finalBuildCount1 %2 == 1){ profile.setIsBuild(true);}
                    if (finalCallCount %2 == 1){ profile.setCall(true);}
                    if (finalCleanCount %2 == 1){ profile.setClean(true);}
                    if (finalCompanyCount %2 == 1){ profile.setCompany(true);}
                    if (finalShopCount %2 == 1){ profile.setShop(true);}

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
            Intent intent_waitForRequest = new Intent(this, WaitforRequest.class);
            startActivity(intent_waitForRequest);
        }
    }
}