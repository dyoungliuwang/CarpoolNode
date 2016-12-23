package com.dyoung.carpool.node.presenter;

import android.content.Context;

import com.dyoung.carpool.node.greendao.model.Trip;
import com.dyoung.carpool.node.greendao.service.TripNodeBiz;
import com.dyoung.carpool.node.mvpview.ITripListView;
import com.dyoung.carpool.node.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by admin on 2016/11/29.
 */
public class TripPresenter extends  BasePresenter<ITripListView> {

    /**
     * 行程列表
     */
    private TripNodeBiz tripNodeBiz;
    public TripPresenter(Context context, ITripListView mView) {
        super(context, mView);
        tripNodeBiz=new TripNodeBiz();
    }

    /**
     * 获取行程列表
     */
    public  void getTripList(){
        Observable.just(tripNodeBiz.queryAllData())
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<List<Trip>>() {
              @Override
              public void onCompleted() {
                  LogUtil.e(TAG, "onCompleted");
              }
              @Override
              public void onError(Throwable e) {
                  LogUtil.e(TAG, e.getMessage());
                  mView.getTripListFailed();
              }
              @Override
              public void onNext(List<Trip> trips) {
                  LogUtil.e(TAG, "onNext");
                  mView.setTripList(trips);
              }
          });
    }

    /**
     * 插入数据
     * @param trip
     */
    public  void  insert(Trip trip){

    }
    /**
     * 删除数据
     * @param trip
     */
    public  void  delete(Trip trip){

    }





}
