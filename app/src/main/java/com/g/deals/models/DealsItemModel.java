package com.g.deals.models;

/**
 * Created by ganesh on 26-06-2017.
 */

public class DealsItemModel {

    private String DealTitle;
    private String DealDesc;
    private String DealImage;

    public DealsItemModel(String t,String d,String i)
    {
        this.DealTitle = t;
        this.DealDesc = d;
        this.DealImage = i;
    }

    public String getDealTitle() {
        return DealTitle;
    }

    public String getDealDesc() {
        return DealDesc;
    }

    public String getDealImage() {
        return DealImage;
    }
}
