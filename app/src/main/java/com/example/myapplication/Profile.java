package com.example.myapplication;

public class Profile {
    private String name, adress, id, cellnum, password, email, uriId, uriProfile;
    private boolean isOld, isBuild, isClean, isCompany, isShop, isCall;




    public Profile(String name, String adress, String id, String cellnum, String email, String passsword,
                   String uri, boolean isOld, boolean isBuild, boolean isClean, boolean isCompany, boolean isShop, boolean isCall) {
        this.name = name;
        this.adress = adress;
        this.id = id;
        this.cellnum = cellnum;
        this.password= passsword;
        this.email= email;
        this.uriId= uriId;
        this.uriProfile= uriProfile;
        this.isOld = isOld;
        this.isBuild = isBuild;
        this.isClean = isClean;
        this.isCompany = isCompany;
        this.isShop = isShop;
        this.isCall = isCall;



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

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public boolean isShop() {
        return isShop;
    }

    public void setShop(boolean shop) {
        isShop = shop;
    }

    public boolean isCall() {
        return isCall;
    }

    public void setCall(boolean call) {
        isCall = call;
    }


}
