package com.dyoung.carpool.node.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dyoung.carpool.node.greendao.core.DaoMaster;
import com.dyoung.carpool.node.greendao.core.DaoSession;


/**
 * Created by liuwang on 2016/10/16.
 */
public class DBHelper {

    private volatile static   DBHelper instance;
    private final static String dbName="carpoolNode";
    private DaoMaster.DevOpenHelper  openHelper;
    private DaoSession daoSession;

    private DBHelper(){

    }

    /**
     * 获取
     * @return
     */
    public static  DBHelper getInstance(){
        if(instance==null){
            synchronized (DBHelper.class){
                instance=new DBHelper();
            }
        }
        return instance;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context){
        openHelper=new DaoMaster.DevOpenHelper(context,dbName);
        daoSession=new DaoMaster(getWritableDatabase()).newSession();
    }

    /**
     * 获取可读写的数据库
     * @return
     */
    public SQLiteDatabase getWritableDatabase(){
       return  openHelper.getWritableDatabase();
    }

    /**
     * 获取可读数据库
     * @return
     */
    public SQLiteDatabase getReadableDatabase(){
        return  openHelper.getReadableDatabase();
    }

    public DaoSession getDaoSession(){
        if(daoSession==null){
            throw  new RuntimeException("daoSession is null, please init DBHelper ");
        }
     return daoSession;
    }



}
