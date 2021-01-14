package com.testuality.contalonga.beans;

import java.math.BigDecimal;

public class JsonExpense {
    private String id;
    private String md5;
    private String date;
    private String typeid;
    private String subtypeid;
    private String description;
    private BigDecimal amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getSubtypeid() {
        return subtypeid;
    }

    public void setSubtypeid(String subtypeid) {
        this.subtypeid = subtypeid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
