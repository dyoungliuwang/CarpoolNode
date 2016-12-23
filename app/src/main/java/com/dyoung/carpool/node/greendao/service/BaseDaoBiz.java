package com.dyoung.carpool.node.greendao.service;

import com.dyoung.carpool.node.greendao.core.NodeDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 2016/10/10.
 */
public abstract class BaseDaoBiz<T> {


    public abstract  AbstractDao getDao();

    public T  getCurrentDao(){
        return (T)getDao();
    }

    /**
     * 插入数据
     */
    public  long insert(T t){
        AbstractDao dao= getDao();
       return dao.insert(t);
    }

    /**
     * 插入一组数据
     */
    public void insertList(List<T> lists){
        AbstractDao dao= getDao();
        dao.insertInTx(lists);
    }

    /**
     * 删除数据
     */
    public  void delete(T t){
        AbstractDao dao= getDao();
        dao.delete(t);
    }

    /**
     * 删除数据根据id
     * @param id
     */
    public void deleteByKey(Long id){
        AbstractDao dao= getDao();
        dao.deleteByKey(id);
    }
    /**
     * 批量删除
     */
    public  void deleteByKeys(List<Long> lists){
        AbstractDao dao= getDao();
        dao.deleteByKeyInTx(lists);
    }

    /**
     * 删除所有数据
     */
    public  void deleteAll(){
        AbstractDao dao= getDao();
        dao.deleteAll();
    }

    /**
     *更新
     * @param t
     */
    public  void update(T t){
        AbstractDao dao= getDao();
        dao.update(t);
    }

    /**
     * 批量更新
     * @param lists
     */
    public  void update(List<T> lists){
        AbstractDao dao= getDao();
        dao.updateInTx(lists);
    }


    /**
     * 查询数据列表
     * @return
     */
    public List<T>  queryAllData(){
        AbstractDao dao= getDao();
        QueryBuilder<T> qb = dao.queryBuilder();
        List<T> list = qb.list();
        return list;
    }


}
