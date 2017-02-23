package com.dyoung.carpool.node.greendao.service;

import com.dyoung.carpool.node.greendao.DBHelper;
import com.dyoung.carpool.node.greendao.core.TripDao;
import com.dyoung.carpool.node.greendao.model.Trip;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 2016/11/16.
 */
public class TripNodeBiz extends  BaseDaoBiz<Trip> {
    @Override
    public AbstractDao getDao() {
        return DBHelper.getInstance().getDaoSession().getTripDao();
    }


    /**
     * 查询数据列表
     * @return
     */
    public List<Trip>  queryAllData(){
        AbstractDao dao= getDao();
        QueryBuilder<Trip> qb = dao.queryBuilder().orderDesc(TripDao.Properties.Id);
        List<Trip> list = qb.list();
        return list;
    }


}
