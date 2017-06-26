package com.g.deals.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ganesh on 22-06-2017.
 */

@DatabaseTable(tableName = "DealsInfoModel")

public class DealsInfoModel
{
    @DatabaseField(generatedId = true)
    private Integer sno;

    @DatabaseField(columnName = "DealTitle")
    private String DealTitle;

    @DatabaseField(columnName = "DealResponse")
    private String DealResponse;

    public DealsInfoModel()
    {

    }

    public DealsInfoModel(String DealTitle,String DealResponse)
    {
        this.DealTitle = DealTitle;
        this.DealResponse = DealResponse;
    }

    public String getDealTitle() {
        return DealTitle;
    }

    public String getDealResponse() {
        return DealResponse;
    }
}
