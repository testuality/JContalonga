package com.testuality.contalonga.beans;

public class ReportItem {
    private Type type;
    private Subtype subtype;
    private double amount;
    private double percent;

    public ReportItem(Type type, Subtype subtype, double amount, double percent) {
        this.type = type;
        this.subtype = subtype;
        this.amount = amount;
        this.percent = percent;
    }

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
