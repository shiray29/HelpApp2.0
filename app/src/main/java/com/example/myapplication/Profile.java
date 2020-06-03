package com.example.myapplication;

public class Profile {
    private String name, adress, id, cellnum;
    private boolean isOld;

    public Profile(String name, String adress, String id, String cellnum, boolean isOld) {
        this.name = name;
        this.adress = adress;
        this.id = id;
        this.cellnum = cellnum;
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
}
