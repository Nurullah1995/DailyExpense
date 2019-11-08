package com.example.dailyexpence;

public class Expense {
    private  int id;
    private int typeId;
    private String type;
    private Double amount;
    private String date;
    private String time;
    private String document;


    public Expense(int id,int typeId,String type,Double amount, String date, String time, String document) {
        this.id=id;
        this.typeId=typeId;
        this.type=type;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.document = document;
    }
    public int getId() {
        return id;
    }
    public int getTypeId() {
        return typeId;
    }
    public String getType() {
        return type;
    }
    public Double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDocument() {
        return document;
    }
}
