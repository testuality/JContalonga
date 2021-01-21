package com.testuality.contalonga.beans;

public class ReportItem {
    private int year;
    private Type type;
    private Subtype subtype;
    private double amount;
    private double percent;

    public ReportItem(int year, Type type, Subtype subtype, double amount, double percent) {
        this.year = year;
        this.type = type;
        this.subtype = subtype;
        this.amount = amount;
        this.percent = percent;
    }

    public int getYear() {return year;}

    public Type getType() {
        return type;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    public double getAmount() {
        return amount;
    }

    public double getPercent() {
        return percent;
    }
}
