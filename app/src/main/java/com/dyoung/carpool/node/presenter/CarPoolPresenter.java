package com.dyoung.carpool.node.presenter;

import android.content.Context;

import com.dyoung.carpool.node.greendao.model.CarPoolNode;
import com.dyoung.carpool.node.greendao.service.CarPoolNodeBiz;
import com.dyoung.carpool.node.mvpview.ICarPoolView;
import com.dyoung.carpool.node.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by admin on 2016/11/16.
 */
public class CarPoolPresenter extends  BasePresenter<ICarPoolView> {

    private CarPoolNodeBiz carPoolBiz;
    public CarPoolPresenter(Context context,ICarPoolView mView) {
        super(context,mView);
        carPoolBiz=new CarPoolNodeBiz();
        EventBus.getDefault().register(this);
    }
    public void queryNodeListLimit(int count){
       EventBus.getDefault().post(new Integer(count));
    }
    /**
     * 获取拼车列表
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public  void queryNodeList(Integer count){
        List<CarPoolNode> list=carPoolBiz.queryCarNodeListLimit(count);
        EventBus.getDefault().post(new SetNodeListEvent(list));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNodeList(SetNodeListEvent nodeListInterface){
        mView.setNodeList(nodeListInterface.list);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void  addCarNode(AddCarPoolPresenter.SetCarNodeEvent setCarNodeEvent){
        mView.addCarNode(setCarNodeEvent.carPoolNode);
    }

    /**
     * 标记已上车
     */
    public void updateHasAboard(CarPoolNode carPoolNode){
        int oldStauts=carPoolNode.getStatus();
        try{
            carPoolNode.setStatus(1-oldStauts);
            carPoolBiz.update(carPoolNode);
            mView.markSuccess(carPoolNode);
        }catch (Exception e){
            LogUtil.e(TAG, "markHasAboard:" + e.getMessage());
            carPoolNode.setStatus(oldStauts);
            mView.markSuccessFailed();

        }
    }

    /**
     * 删除
     * @param carPoolNode
     */
    public  void delete(CarPoolNode carPoolNode){
        try{
            carPoolBiz.delete(carPoolNode);
            mView.deleteSuccess(carPoolNode);
        }catch (Exception e){
            LogUtil.e(TAG,"delete:"+e.getMessage());
            mView.deleteFailed();
        }
    }

    /**
     * 设置拼车列表
     */
    public class SetNodeListEvent {
        public List<CarPoolNode> list;
        public SetNodeListEvent(List<CarPoolNode> list){
            this.list=list;
        }
    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }
}
