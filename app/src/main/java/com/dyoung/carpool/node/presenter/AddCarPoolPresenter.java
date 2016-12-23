package com.dyoung.carpool.node.presenter;

import android.content.Context;

import com.dyoung.carpool.node.greendao.model.CarPoolNode;
import com.dyoung.carpool.node.greendao.model.Trip;
import com.dyoung.carpool.node.greendao.service.CarPoolNodeBiz;
import com.dyoung.carpool.node.greendao.service.TripNodeBiz;
import com.dyoung.carpool.node.mvpview.IAddCarNodeView;
import com.dyoung.carpool.node.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by admin on 2016/11/18.
 */
public class AddCarPoolPresenter extends BasePresenter<IAddCarNodeView> {

    private CarPoolNodeBiz carPoolBiz;
    private TripNodeBiz tripNodeBiz;

    public AddCarPoolPresenter(Context context,IAddCarNodeView mView) {
        super(context,mView);
        carPoolBiz=new CarPoolNodeBiz();
        tripNodeBiz=new TripNodeBiz();
        EventBus.getDefault().register(this);
    }
    /**
     * 添加拼车信息
     * @param node
     * @return
     */
    public  void addNode(CarPoolNode node){
        try{
            long id=carPoolBiz.insert(node);
            node.setId(id);
            mView.addSuccess();
            EventBus.getDefault().post(new SetCarNodeEvent(node));
        }catch (Exception e){
            LogUtil.e(TAG, "addNode:" + e.getMessage());
            mView.addFailed();
        }
    }

    /**
     * 更新数据
     */
    public  void update(CarPoolNode node){
        try{
            carPoolBiz.update(node);
            mView.addSuccess();
            EventBus.getDefault().post(new SetCarNodeEvent(node));
        }catch (Exception e){
            LogUtil.e(TAG, "update:" + e.getMessage());
            mView.addFailed();
        }
    }

    public void getTripList(){
        EventBus.getDefault().post(new GetTripEvent() { });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public  void getTripList(GetTripEvent getTripEvent){
        List<Trip> tripList= tripNodeBiz.queryAllData();
        LogUtil.d(TAG,"tripListr="+tripList);
        if(tripList.isEmpty()){
            Trip trip1=new Trip(null,"涿州","北京");
            Trip trip2=new Trip(null,"北京","涿州");
            tripNodeBiz.insert(trip1);
            tripNodeBiz.insert(trip2);

            tripList.add(trip1);
            tripList.add(trip2);
        }
        EventBus.getDefault().post(new SetTripListEvent(tripList));
    }

    /**
     *
     * @param setTripListInterface
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTripList(SetTripListEvent setTripListInterface){
        mView.setTripList(setTripListInterface.tripList);
    }

    /**
     * 获取行程数据列表
     */
    public class  GetTripEvent{}

    /**
     * 设置行程列表
     */
    public class SetTripListEvent {
        List<Trip> tripList;
        public SetTripListEvent(List<Trip> tripList){
            this.tripList=tripList;
        }
    }

    /**
     * 更新拼车信息
     */
    public  class  SetCarNodeEvent{
        CarPoolNode carPoolNode;
        public SetCarNodeEvent( CarPoolNode carPoolNode){
            this.carPoolNode=carPoolNode;
        }
    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }

}
