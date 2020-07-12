package com.example.myapplication;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Profile {
    private String name, adress, id, cellnum, password, email, uriId, uriProfile;
    private boolean isOld, isBuild, isClean, isCompany, isShop, isCall;
    private Double longitude, latitude;
    private FusedLocationProviderClient fusedLocationProviderClient;


    public Profile(){
        this.name = name;
        this.adress = adress;
        this.id = id;
        this.cellnum = cellnum;
        this.password= password;
        this.email= email;
        this.uriId= uriId;
        this.uriProfile= uriProfile;
        this.isOld = isOld;
        this.isBuild = isBuild;
        this.isClean = isClean;
        this.isCompany = isCompany;
        this.isShop = isShop;
        this.isCall = isCall;
        this.longitude= longitude;
        this.latitude= latitude;

    }


    public Profile(String name, String adress, String id, String cellnum, String email, String passsword,
                   String uri, boolean isOld, boolean isBuild, boolean isClean, boolean isCompany, boolean isShop, boolean isCall, Double longitude, Double latitude) {
        this.name = name;
        this.adress = adress;
        this.id = id;
        this.cellnum = cellnum;
        this.password= password;
        this.email= email;
        this.uriId= uriId;
        this.uriProfile= uriProfile;
        this.isOld = isOld;
        this.isBuild = isBuild;
        this.isClean = isClean;
        this.isCompany = isCompany;
        this.isShop = isShop;
        this.isCall = isCall;
        this.longitude= longitude;
        this.latitude= latitude;

    }

    void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                }
            }
        });
}

    public Profile() {

    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCellnum() {
        return cellnum;
    }

    public void setCellnum(String cellnum) {
        this.cellnum = cellnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOld() {
        return isOld;
    }

    public void setOld(boolean old) {
        isOld = old;
    }

    public void setUriId(String uriId){this.uriId= uriId;}

    public void setUriProfile(String uriProfile){this.uriProfile=uriProfile;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email= email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password= password; }

    public boolean getIsBuild() {
        return isBuild;
    }

    public void setIsBuild(boolean isBuild) { this.isBuild= isBuild; }

    public boolean getClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public boolean getCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public boolean getShop() {
        return isShop;
    }

    public void setShop(boolean shop) {
        isShop = shop;
    }

    public boolean getCall() {
        return isCall;
    }

    public void setCall(boolean call) {
        isCall = call;
    }


}
