package com.dyoung.carpool.node.mvpview;

import com.dyoung.carpool.node.greendao.model.Trip;

import java.util.List;

/**
 * Created by lw on 2016/11/29.
 */
public interface ITripListView {
    void setTripList(List<Trip> list);
    void getTripListFailed();

}
