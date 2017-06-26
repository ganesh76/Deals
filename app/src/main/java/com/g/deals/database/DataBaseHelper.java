package com.g.deals.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by ganesh on 22-06-2017.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private Context _context;
    private static final String DATABASE_NAME = "Deals.db";
    private static final int DATABASE_VERSION = 1;
    private RuntimeExceptionDao<DealsInfoModel, String> dealsInfoModelStringDao = null;
    private static DataBaseHelper instance;

    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        _context = context;
    }

    public static synchronized DataBaseHelper getHelper(Context context)
    {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    public RuntimeExceptionDao<DealsInfoModel, String> getDealsInfoModelDAO()
    {
        if (dealsInfoModelStringDao == null) {
            dealsInfoModelStringDao = getRuntimeExceptionDao(DealsInfoModel.class);
        }
        return dealsInfoModelStringDao;
    }

    @Override
    public void close() {
        super.close();
        dealsInfoModelStringDao = null;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, DealsInfoModel.class);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, DealsInfoModel.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
