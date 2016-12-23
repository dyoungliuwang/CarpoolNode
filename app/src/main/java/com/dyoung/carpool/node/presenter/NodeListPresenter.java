package com.dyoung.carpool.node.presenter;

import android.content.Context;

import com.dyoung.carpool.node.fragment.BaseFragement;
import com.dyoung.carpool.node.greendao.model.Node;
import com.dyoung.carpool.node.greendao.service.NodeBiz;
import com.dyoung.carpool.node.mvpview.INodeListView;
import com.dyoung.carpool.node.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by admin on 2016/11/24.
 */
public class NodeListPresenter extends BasePresenter<INodeListView> {
    private NodeBiz nodeBiz;

    public NodeListPresenter(Context context, INodeListView mView) {
        super(context, mView);
        nodeBiz=new NodeBiz();
        EventBus.getDefault().register(this);
    }

    public void getNodeList(){
        EventBus.getDefault().post(new QueryNodeListEvent());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void queryNodeListEvent(QueryNodeListEvent event){
        List<Node> dataList=nodeBiz.queryAllData();
        EventBus.getDefault().post(new SetNodeListEvent(dataList));

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNodeListEvent(SetNodeListEvent event){
        mView.setNodeList(event.list);
    }

    /**
     * 添加成功后更新列表数据
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void appendNodeEvent(AddNodePresenter.AddNodeResultEvent event ){
        mView.appendNode(event.node);
    }

    /**
     * 删除记录
     * @param node
     */
    public  void delete(Node node){
        try{
            nodeBiz.delete(node);
            mView.deleteSuccess(node);
        }catch (Exception e){
            LogUtil.e(TAG,"delete:"+e.getMessage());
            mView.deleteFailed();
        }
    }
    /**
     * 查询笔记列表数据
     */
    public class  QueryNodeListEvent{}
    /*
    设置nodelist数据
     */
    public class  SetNodeListEvent{
        List<Node> list;
        public SetNodeListEvent( List<Node> list){
            this.list=list;
        }
    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }


}
