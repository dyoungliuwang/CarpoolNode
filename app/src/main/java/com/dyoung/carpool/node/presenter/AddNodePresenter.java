package com.dyoung.carpool.node.presenter;

import android.content.Context;

import com.dyoung.carpool.node.greendao.model.Node;
import com.dyoung.carpool.node.greendao.service.NodeBiz;
import com.dyoung.carpool.node.mvpview.IAddNodeView;
import com.dyoung.carpool.node.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by admin on 2016/11/23.
 */
public class AddNodePresenter extends BasePresenter<IAddNodeView> {

    private NodeBiz nodeBiz;

    public AddNodePresenter(Context context, IAddNodeView mView) {
        super(context, mView);
        nodeBiz=new NodeBiz();
        EventBus.getDefault().register(this);
    }

    public void insert(Node node)
    {
        try {
            long id=nodeBiz.insert(node);
            node.setId(id);
            EventBus.getDefault().post(new AddNodeResultEvent(node));
        }catch (Exception e){
            LogUtil.d(TAG, "addNode:" + e.getMessage());
            mView.failed();
        }
    }

    /**
     * 更新数据
     */
    public  void update(Node node){
        try{
            nodeBiz.update(node);
            EventBus.getDefault().post(new AddNodeResultEvent(node));
        }catch (Exception e){
            LogUtil.d(TAG, "update:" + e.getMessage());
            mView.failed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  postAddNodeResult(AddNodeResultEvent event){
        mView.success();
    }
    public  class AddNodeResultEvent{
        Node node;
        public AddNodeResultEvent(Node node){
            this.node=node;
        }
    }
    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }


}
