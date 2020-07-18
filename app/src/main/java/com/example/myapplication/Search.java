package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
    private ArrayList markerList;
    private GoogleMap googleMap;
    private double thisLon, thisLat;
    private Profile thisUser;
    private Button btnSearchedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        final ArrayList<Profile> profileList = new ArrayList<Profile>();
        final ArrayList<Marker> markerList = new ArrayList<Marker>();
        btnSearchedit = findViewById(R.id.btn_searchedit);
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

        DatabaseReference  ref = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()); // gets current user Lat & Lon
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                thisLat = profile.getLatitude();
                thisLon = profile.getLongitude();
                thisUser = profile;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        while (profileList.size() > count) { // מעלה את כל הקשישים במרחק של עד 30 ק"מ מהמשתמש
            if (profileList.get(count).isOld())
            {
             tempLat = profileList.get(count).getLatitude(); // hadas shall add
             tempLon = profileList.get(count).getLongitude();
             if (findDistance(tempLat, tempLon, thisLat, thisLon) <=30)
             {
                 String title;
                 if (profileList.get(count).getIsBuild()){
                     title = "סיוע בשיפוץ";
                     showMarker(tempLat, tempLon, R.id.imageView_build, title);
             }
                 if (profileList.get(count).getClean()){
                     title = "סיוע בניקיון";
                     showMarker(tempLat, tempLon, R.id.imageView_clean, title);
             }
                 if (profileList.get(count).getCompany());
                 {
                     title = "אירוח חברה";
                     showMarker(tempLat, tempLon, R.id.imageView_company, title);
             }
                 if (profileList.get(count).getShop());
                 {
                     title = "סיוע בקניות";
                     showMarker(tempLat, tempLon, R.id.imageView_shop, title);
             }
                 if (profileList.get(count).getCall());
                 {
                     title = "קשר טלפוני";
                     showMarker(tempLat, tempLon, R.id.imageView_call, title);
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

    public void showMarker(double lat, double lon, int imageID, String title) { // מעלה את האייקון על המפה ומוסיפה את המרקר לרשימה
        Marker m1 = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .anchor(0.5f, 0.5f).title(title).snippet("").icon(BitmapDescriptorFactory.fromResource(imageID)));
        markerList.add(m1);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng point = new LatLng(thisLat, thisLon);
        this.googleMap.addMarker(new MarkerOptions().position(point));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }


    public void sendSmsOld(Profile currentUser, Profile wantedOldie, String helpType)
    // מקבלת - פרופיל המתנדב והקשיש וסוג העזרה, שולחת לקשיש הודעה בהתאם
    {
        String phoneNo = wantedOldie.getCellnum();
        String message = "שלום, קוראים לי " + currentUser.getName() + ", הכתובת שלי היא " + currentUser.getAdress() + "ואני רוצה לעזור לך ב"
                + helpType + ". כדי ליצור איתי קשר ולקבל את עזרתי, אנא התקשר למספר הטלפון שלי - " + currentUser.getCellnum();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(), "ההודעה נשלחה לקשיש",
                Toast.LENGTH_LONG).show();
    }

    public Profile oldUserFromLocation(LatLng location) // מקבלת מיקום ומחזירה את הקשיש שנמצא בו
    {
        for (int j = 0; j<profileList.size(); j++){
            Profile tempProf = (Profile) profileList.get(j);
            if ((tempProf.isOld()) && (tempProf.getLatitude() == location.latitude) &&
                    (tempProf.getLongitude() == location.longitude))
            {
                return tempProf;
            }
        }
        return null;

    }

    public void showPopup ()
    {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popupsearch, null, false),
                400,600, true);
        pw.showAtLocation(this.findViewById(R.id.mapView), Gravity.CENTER, 0, 0); // האם להראות על המפה? TODO -
    }

    public void onClick(View V){
        if (V == btnSearchedit) {
            Intent intent_login = new Intent(this, EditProfile.class);
            startActivity(intent_login);
        }
        for (int i = 0; i < markerList.size(); i++)
        {
            if (markerList.get(i) ==V)
            {
                Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popupsearch);
                Button accept = dialog.findViewById(R.id.btn_accept);
                Button decline = dialog.findViewById(R.id.btn_decline);
                accept.setOnClickListener((View.OnClickListener) this);
                decline.setOnClickListener((View.OnClickListener) this);
                if (V == accept)
                {
                    Marker mar = (Marker) markerList.get(i);
                    LatLng posi = mar.getPosition();
                    String helpType = mar.getTitle();
                    Profile wantedOld = oldUserFromLocation(posi);
                    sendSmsOld(thisUser, wantedOld, helpType);
                    dialog.cancel();
                    dialog.show();
                }

                if (V == decline)
                {
                    dialog.cancel();
                    dialog.show();
                }
                }
            }
            }
        }


