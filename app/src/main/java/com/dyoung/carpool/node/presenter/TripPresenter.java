package com.dyoung.carpool.node.presenter;

import android.content.Context;

import com.dyoung.carpool.node.greendao.model.CarPoolNode;
import com.dyoung.carpool.node.greendao.model.Trip;
import com.dyoung.carpool.node.greendao.service.CarPoolNodeBiz;
import com.dyoung.carpool.node.greendao.service.TripNodeBiz;
import com.dyoung.carpool.node.mvpview.ITripListView;
import com.dyoung.carpool.node.util.LogUtil;

import java.util.List;

import rx.Observable;
import rx.Observer;
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
    private CarPoolNodeBiz carPoolNodeBiz;
    public TripPresenter(Context context, ITripListView mView) {
        super(context, mView);
        tripNodeBiz=new TripNodeBiz();
        carPoolNodeBiz=new CarPoolNodeBiz();
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
     * 删除数据
     * @param trip
     */
    public  void  delete(Trip trip){
        try{
            List<CarPoolNode> list=carPoolNodeBiz.queryCarNodeListByTripId(trip.getId());
            if(list.isEmpty()){
                tripNodeBiz.delete(trip);
                mView.success();
            }else{
                mView.failed("无法删除仍然在使用的行程信息");
            }
        }catch (Exception e){
            mView.failed("删除失败");
        }
    }





}
