package com.dyoung.carpool.node.greendao.service;

import com.dyoung.carpool.node.greendao.DBHelper;
import com.dyoung.carpool.node.greendao.core.NodeDao;
import com.dyoung.carpool.node.greendao.model.Node;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by admin on 2016/11/23.
 */
public class NodeBiz extends BaseDaoBiz<Node> {
    @Override
    public AbstractDao getDao() {
        return DBHelper.getInstance().getDaoSession().getNodeDao();
    }
}
