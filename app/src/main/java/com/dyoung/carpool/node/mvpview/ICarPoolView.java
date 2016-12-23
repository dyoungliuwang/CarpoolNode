package com.dyoung.carpool.node.mvpview;

import com.dyoung.carpool.node.greendao.model.CarPoolNode;
import com.dyoung.carpool.node.greendao.model.CarPoolNode;

import java.util.List;

/**
 * Created by admin on 2016/11/16.
 */
public interface ICarPoolView {
    void  setNodeList(List<CarPoolNode> list);
    void addCarNode(CarPoolNode carPoolNode);
    void markSuccess(CarPoolNode carPoolNode);
    void markSuccessFailed();
    void deleteSuccess(CarPoolNode carPoolNode);
    void deleteFailed();
}
