package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search extends AppCompatActivity implements OnMapReadyCallback {

    private DatabaseReference databaseReference;
    private ArrayList profileList;
    private GoogleMap googleMap;
    private double thisLon, thisLat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
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

        DatabaseReference  ref = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()); // get current user Lat & Lon
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                thisLat = profile.getLat();
                thisLon = Profile.getLon();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        while (profileList.size() > count) { // מעלה את כל הקשישים במרחק של עד 30 ק"מ מהמשתמש
            // TODO - רשימץ מרקרים עם קישור לקשיש
            if (profileList.get(count).isOld())
            {
             tempLat = profileList.get(count).getLat(); // hadas shall add
             tempLon = profileList.get(count).getLon();
             if (findDistance(tempLat, tempLon, thisLat, thisLon) <=30)
             {
                 if (profileList.get(count).getIsBuild()){
                     showMarker(tempLat, tempLon, R.id.imageView_build);
             }
                 if (profileList.get(count).getClean()){
                 showMarker(tempLat, tempLon, R.id.imageView_clean);
             }
                 if (profileList.get(count).getCompany());
                 {
                 showMarker(tempLat, tempLon, R.id.imageView_company);
             }
                 if (profileList.get(count).getShop());
                 {
                 showMarker(tempLat, tempLon, R.id.imageView_shop);
             }
                 if (profileList.get(count).getCall());
                 {
                 showMarker(tempLat, tempLon, R.id.imageView_call);
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng point = new LatLng(thisLat, thisLon);
        this.googleMap.addMarker(new MarkerOptions().position(point));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }


    // דוגמא

    public void onClick(View V){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popupvul);
        Button accept = dialog.findViewById(R.id.btn_accept);
        Button decline = dialog.findViewById(R.id.btn_decline);
        accept.setOnClickListener((View.OnClickListener) this);
        decline.setOnClickListener((View.OnClickListener) this);
        if (V==accept){
            dialog.cancel();
            // TODO - notification;
        }

        // dialog.show



    }
}
