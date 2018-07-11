package com.csl.adi.model;

import java.util.Date;

public class Teste {
    private long id;
    private String str;
    private Date creation;

    public Teste(long id) {
        this.id = id;
        this.creation = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }
}
