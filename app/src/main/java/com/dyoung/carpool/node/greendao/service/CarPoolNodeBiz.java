package com.dyoung.carpool.node.greendao.service;

import com.dyoung.carpool.node.greendao.DBHelper;
import com.dyoung.carpool.node.greendao.core.CarPoolNodeDao;
import com.dyoung.carpool.node.greendao.model.CarPoolNode;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 2016/11/16.
 */
public class CarPoolNodeBiz extends  BaseDaoBiz<CarPoolNode> {
    @Override
    public AbstractDao getDao() {
        return DBHelper.getInstance().getDaoSession().getCarPoolNodeDao();
    }

    /**
     * 获取拼车列表
     * @param count
     * @return
     */
    public List<CarPoolNode> queryCarNodeListLimit(int count){
        QueryBuilder<CarPoolNode> qb = getDao().queryBuilder();
        qb.limit(count).orderDesc(CarPoolNodeDao.Properties.RideTime,CarPoolNodeDao.Properties.TripId,CarPoolNodeDao.Properties.Date);
        return qb.list();
    }



}
