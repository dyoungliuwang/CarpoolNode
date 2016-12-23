package com.dyoung.carpool.node.mvpview;

import com.dyoung.carpool.node.greendao.model.CarPoolNode;
import com.dyoung.carpool.node.greendao.model.Trip;

import java.util.List;

/**
 * Created by admin on 2016/11/16.
 */
public interface IAddCarNodeView {
    /*
    插入数据成功
     */
    void addSuccess();

    /**
     * 插入数据失败
     */
    void addFailed();

    void setTripList(List<Trip> tripList);

}
