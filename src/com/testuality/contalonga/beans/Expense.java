package com.testuality.contalonga.beans;

import java.util.GregorianCalendar;

public class Expense {
    private String id;
    private String md5;
    private GregorianCalendar date;
    private String typeId;
    private String subtypeId;
    private String description;
    private double amount;

    public Expense(String id, String md5, GregorianCalendar date, String typeId, String subtypeId, String description, double amount) {
        this.id = id;
        this.md5 = md5;
        this.date = date;
        this.typeId = typeId;
        this.subtypeId = subtypeId;
        this.description = description;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getMd5() {
        return md5;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getSubtypeId() {
        return subtypeId;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}
