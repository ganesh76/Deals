package com.g.deals.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganesh on 22-06-2017.
 */

public class DealsInfoDAO {

    public DataBaseHelper helper;
    Context mContext;
    protected SQLiteDatabase database;

    public DealsInfoDAO(Context mContetx){
        this.mContext = mContetx;
        open();
    }

    public void open() throws android.database.SQLException
    {
        if(helper == null)
            helper = DataBaseHelper.getHelper(mContext);
        database = helper.getWritableDatabase();
    }

    public int insertOrUpdateDeal(DealsInfoModel dealsInfoModel)
    {
        int i = 0 ;
        RuntimeExceptionDao<DealsInfoModel, String> simpleDao = helper.getDealsInfoModelDAO();
        try
        {
            if(getDealDataByName(dealsInfoModel.getDealTitle()).size()>0)
            {
                i =  updateDealDataByName(dealsInfoModel.getDealTitle(),dealsInfoModel.getDealResponse());
            }
            else
            {
                i =   simpleDao.create(dealsInfoModel);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            i=0;
        }
        return i;
    }

    public void deleteAll()
    {
        try
        {
            RuntimeExceptionDao<DealsInfoModel, String> dao = helper.getDealsInfoModelDAO();
            List<DealsInfoModel> list = dao.queryForAll();
            if(list.size()>0)
            {
                dao.deleteBuilder().delete();
            }
            else
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<DealsInfoModel> getDealDataByName(String DealTitle)
    {
        List<DealsInfoModel> list = new ArrayList<>();
        RuntimeExceptionDao<DealsInfoModel, String> simpleDao = helper.getDealsInfoModelDAO();
        try
        {
            list = simpleDao.queryForEq("DealTitle",DealTitle);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public int updateDealDataByName(String DealTitle,String DealResponse)
    {
        int i = 0;
        RuntimeExceptionDao<DealsInfoModel, String> simpleDao = helper.getDealsInfoModelDAO();
        UpdateBuilder<DealsInfoModel, String> updateBuilder = simpleDao.updateBuilder();
        try
        {
            updateBuilder.updateColumnValue("DealResponse", DealResponse);
            updateBuilder.where().eq("DealTitle", DealTitle);
            i= simpleDao.update(updateBuilder.prepare());
        }
        catch (Exception e)
        {
            i=0;
            e.printStackTrace();
        }
        return i;
    }
}
