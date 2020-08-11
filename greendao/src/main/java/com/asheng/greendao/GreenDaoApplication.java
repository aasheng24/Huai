package com.asheng.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.asheng.greendao.dao.DaoMaster;
import com.asheng.greendao.dao.DaoSession;

public class GreenDaoApplication extends Application {

    private DaoSession mDaoSession;
    private static GreenDaoApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initGreenDao();
    }

    public GreenDaoApplication getInstance() {
        return sInstance;
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "green_dao.db");
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession () {
        return mDaoSession;
    }
}
