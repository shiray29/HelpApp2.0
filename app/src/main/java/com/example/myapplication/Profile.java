package com.example.myapplication;

public class Profile {
    private String name, adress, id, cellnum, password, email, uriId, uriProfile;
    private boolean isOld;

    public Profile(){

    }

    public Profile(String name, String adress, String id, String cellnum, String email, String passsword,String uri, boolean isOld) {
        this.name = name;
        this.adress = adress;
        this.id = id;
        this.cellnum = cellnum;
        this.password= passsword;
        this.email= email;
        this.uriId= uriId;
        this.uriProfile= uriProfile;
        this.isOld = isOld;
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
}
