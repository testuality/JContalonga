package com.testuality.contalonga.beans;

import java.util.ArrayList;
import java.util.List;

public class ReportForYear {

    private int year;
    private List<ReportItem> byTypeList;
    private List<ReportItem> byTypeSubtypeList;
    private double totalAmount;

    public ReportForYear(int year, double totalAmount) {
        this.year = year;
        this.byTypeList = new ArrayList<>();
        this.byTypeSubtypeList = new ArrayList<>();
        this.totalAmount = totalAmount;
    }

    public List<ReportItem> getByTypeList() {
        return byTypeList;
    }

    public List<ReportItem> getByTypeSubtypeList() {
        return byTypeSubtypeList;
    }
}
