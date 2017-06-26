package com.g.deals.util;

import android.app.Application;

import com.g.deals.database.DataBaseHelper;
import com.g.deals.database.DealsInfoDAO;

/**
 * Created by ganesh on 26-06-2017.
 */

public class MyApplication extends Application {
    DataBaseHelper dataBaseHelper;
    DealsInfoDAO dealsInfoDAO;
    @Override
    public void onCreate() {
        super.onCreate();
        dataBaseHelper = new DataBaseHelper(this);
        dealsInfoDAO = new DealsInfoDAO(this);
    }
}
