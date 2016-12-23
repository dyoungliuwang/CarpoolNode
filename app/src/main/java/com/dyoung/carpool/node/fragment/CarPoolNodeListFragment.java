package com.dyoung.carpool.node.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.activity.AddCarNodeActivity;
import com.dyoung.carpool.node.adapter.CommonAdapter;
import com.dyoung.carpool.node.adapter.ViewHolder;
import com.dyoung.carpool.node.greendao.model.CarPoolNode;
import com.dyoung.carpool.node.mvpview.ICarPoolView;
import com.dyoung.carpool.node.presenter.CarPoolPresenter;
import com.dyoung.carpool.node.util.DateUtil;
import com.dyoung.carpool.node.util.LogUtil;
import com.dyoung.carpool.node.util.ToastUtil;
import com.dyoung.carpool.node.view.CommonListDialog;
import com.dyoung.carpool.node.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 拼车列表
 */
public class CarPoolNodeListFragment extends BaseFragement<CarPoolPresenter> implements ICarPoolView  ,Comparator<CarPoolNode> {
    private List<CarPoolNode> dataList=new ArrayList<CarPoolNode>();;
    protected LinearLayoutManager mLayoutManager;
    private CommonAdapter<CarPoolNode> adapter;
    private final static int    DEFAUL_LOAD_COUNT=100;

    @BindView(R.id.loading)
    TextView loadingView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common_recyclerview_layout, container, false);
        ButterKnife.bind(this, view);
        isLoadView=true;
        lazyLoad();
        return  view;
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mPresenter=new CarPoolPresenter(mContext,this);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        adapter=new CommonAdapter<CarPoolNode>(mContext,dataList,R.layout.fragment_car_pool_node_list_item) {
            @Override
            public void convert(ViewHolder holder, final CarPoolNode carPoolNode) {
                holder.setText(R.id.item_title,carPoolNode.getTrip().getSetOut()+"--"+carPoolNode.getTrip().getArriveCity()+"  "+ DateUtil.formatToDay(carPoolNode.getRideTime()));
                String content=carPoolNode.getNodeType().getName()+"  ";
                content+=carPoolNode.getNumber()+"  "+(+carPoolNode.getStatus()==1?"已上车":"未上车")+"\n";
                if(!TextUtils.isEmpty(carPoolNode.getMark())){
                    content+="备注:"+carPoolNode.getMark()+"\n";
                }
                content+=DateUtil.formatWholeDate(carPoolNode.getDate());
                holder.setText(R.id.item_content,content);
                holder.setOnItemClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goEditCarNode(carPoolNode);
                    }
                });
                holder.setOnItemLongClick(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        setOnLongClick(carPoolNode);
                        return true;
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
        mPresenter.queryNodeListLimit(DEFAUL_LOAD_COUNT);

    }

    /**
     * 设置长按事件
     */
    private  void setOnLongClick(final CarPoolNode carPoolNode){
        final List list=new ArrayList();
        switch (carPoolNode.getNodeType()){
            case PHONE:
                list.add("拔打电话");
                list.add("发送短信");
                break;
            case WX:
                break;
            case QQ:
                break;

        }
        if(carPoolNode.getStatus()==1){
            list.add("标记未上车");
        }else{
            list.add("标记已上车");
        }
        list.add("编辑");
        list.add("删除");
        new CommonListDialog(mContext,carPoolNode.getNumber(), list, new CommonListDialog.OnCommonDialogItemListener() {
            @Override
            public void onItemClick(String content) {
                LogUtil.i(TAG,content);
                if(content.equals("拔打电话")){
                    callPhone(carPoolNode.getNumber());
                }else if(content.equals("编辑")){
                    goEditCarNode(carPoolNode);
                }else if(content.equals("标记已上车")){
                    mPresenter.updateHasAboard(carPoolNode);
                }else if(content.equals("标记未上车")){
                    mPresenter.updateHasAboard(carPoolNode);
                }else if(content.equals("删除")){
                    mPresenter.delete(carPoolNode);
                }
            }
        }).show();
    }

    /**
     * 拔打电话
     * @param phone
     */
    private  void callPhone(String phone){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        this.startActivity(intent);
    }

    private  void  goEditCarNode(CarPoolNode carPoolNode){
        Intent intent = new Intent(mContext, AddCarNodeActivity.class);
        intent.putExtra("node", carPoolNode);
        startActivity(intent);
    }

    /**
     * 判断是否还有数据列表
     * @return
     */
    private void isHaveData(){
        if(dataList.isEmpty()){
            loadingView.setVisibility(View.VISIBLE);
            loadingView.setText("暂无数据");
        }else{
            loadingView.setVisibility(View.GONE);
        }
    }
    @Override
    public void setNodeList(List<CarPoolNode> list) {
        loadingView.setVisibility(View.GONE);
        dataList.clear();
        dataList.addAll(list);
        adapter.notifyDataSetChanged();
        isHaveData();
    }

    @Override
    public void markSuccess(CarPoolNode carPoolNode) {
        LogUtil.e(TAG, "标记成功");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void markSuccessFailed() {
        LogUtil.e(TAG,"标记失败");
        ToastUtil.show("标记失败");

    }

    @Override
    public void deleteSuccess(CarPoolNode carPoolNode) {
        LogUtil.e(TAG, "删除成功");
        Iterator<CarPoolNode> iterator=dataList.iterator();
        while (iterator.hasNext()){
            CarPoolNode item=iterator.next();
            if(carPoolNode.getId()==carPoolNode.getId()){
                iterator.remove();
                break;
            }
        }
        adapter.notifyDataSetChanged();
        isHaveData();

    }

    @Override
    public void deleteFailed() {
        LogUtil.e(TAG,"删除失败");
        ToastUtil.show("删除失败");
    }


    @Override
    public void addCarNode(CarPoolNode carPoolNode) {
        if(loadingView.getVisibility()==View.VISIBLE){
            loadingView.setVisibility(View.GONE);
        }
        Iterator<CarPoolNode> iterator=dataList.iterator();
        while (iterator.hasNext()){
            CarPoolNode item=iterator.next();
            if(carPoolNode.getId()==item.getId()){
                iterator.remove();
                break;
            }
        }

        dataList.add(carPoolNode);
        Collections.sort(dataList,this);
        adapter.notifyDataSetChanged();
        isHaveData();
    }

    @Override
    protected void lazyLoad() {
        if(!isLoadView || !isVisible || isLoadData){
            return;
        }
        isLoadData=true;
        initData();
        LogUtil.i(TAG,"lazyLoad");
    }

    @Override
    public int compare(CarPoolNode carPoolNode1, CarPoolNode carPoolNode2) {
        if(carPoolNode2.getRideTime()==carPoolNode1.getRideTime()){
            if(carPoolNode2.getTripId()==carPoolNode1.getTripId()){
                return   carPoolNode2.getDate().compareTo(carPoolNode1.getDate());
            }else{
                return  carPoolNode2.getTripId().compareTo(carPoolNode1.getTripId());
            }
        }else{
            return  carPoolNode2.getRideTime().compareTo(carPoolNode1.getRideTime());
        }
    }
}
