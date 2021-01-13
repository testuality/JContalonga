package com.testuality.contalonga.beans;

import java.util.GregorianCalendar;

public class BankMovement {
    private String id;
    private GregorianCalendar date;
    private String concept;
    private double amount;

    public BankMovement(String id, GregorianCalendar date, String concept, double amount) {
        this.id = id;
        this.date = date;
        this.concept = concept;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getConcept() {
        return concept;
    }

    public double getAmount() {
        return amount;
    }
}
