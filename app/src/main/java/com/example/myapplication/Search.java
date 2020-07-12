package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList profileList;
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        googleMap = findViewById(R.id.mapView); // TODO - linking XML and java maps. this is probably wrong
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        final ArrayList<Profile> profileList = new ArrayList<Profile>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // creates all profiles lists
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Profile profile = snapshot.getValue(Profile.class);
                    profileList.add(profile);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        int count = 0;
        double tempLat;
        double tempLon;

        while (profileList.size() > count) { // מעלה את כל הקשישים במרחק של עד 30 ק"מ מהמשתמש
            if (profileList.get(count).isOld())
            {
             tempLat = profileList.get(count).getLat(); // hadas shall add
             tempLon = profileList.get(count).getLon();
             if (findDistance(tempLat, tempLon, thisLat, thisLon) <=30) // TODO - ask how i access this user
             {
                 if profileList.get(count).getIsBuild(){
                     showMarker(tempLat, tempLon, buildID);
             }
                 if profileList.get(count).getClean(){
                 showMarker(tempLat, tempLon, cleanID);
             }
                 if profileList.get(count).getCompany();
                 {
                 showMarker(tempLat, tempLon, companyID);
             }
                 if profileList.get(count).getShop();
                 {
                 showMarker(tempLat, tempLon, shopID);
             }
                 if profileList.get(count).getCall();
                 {
                 showMarker(tempLat, tempLon, callID);
             }
             }
            }
            count++;
        }
    }

    public double findDistance(double lat1, double lon1, double lat2, double lon2) // מחשבת מרחק בין 2 מיקומים בקורדינאטות
    {
        int R = 6371; // km
        double x = (lon2 - lon1) * Math.cos((lat1 + lat2) / 2);
        double y = (lat2 - lat1);
        double distance = Math.sqrt(x * x + y * y) * R;
        return distance;
    }

    public void showMarker(double lat, double lon, int imageID) { // מעלה את האייקון על המפה
        Marker m1 = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .anchor(0.5f, 0.5f).title("").snippet("").icon(BitmapDescriptorFactory.fromResource(imageID)));
    }



}
