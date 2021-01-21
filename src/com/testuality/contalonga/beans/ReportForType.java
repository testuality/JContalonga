package com.testuality.contalonga.beans;

import java.util.ArrayList;
import java.util.List;

public class ReportForType {
    private Type type;

    private List<ReportItem> typeList;
    private List<List<ReportItem>> subtypeLists;
    private double totalAmount;

    public ReportForType(Type type, double totalAmount) {
        this.type = type;
        this.typeList = new ArrayList<>();
        this.subtypeLists = new ArrayList<>();
        this.totalAmount = totalAmount;
    }

    public Type getType() {
        return type;
    }

    public List<ReportItem> getByTypeList() {
        return typeList;
    }

    public List<List<ReportItem>> getBySubtypeLists() {
        return subtypeLists;
    }
}
