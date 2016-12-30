package com.dyoung.carpool.node.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.activity.AddNodeActivity;
import com.dyoung.carpool.node.adapter.CommonAdapter;
import com.dyoung.carpool.node.adapter.ViewHolder;
import com.dyoung.carpool.node.greendao.model.Node;
import com.dyoung.carpool.node.mvpview.INodeListView;
import com.dyoung.carpool.node.presenter.NodeListPresenter;
import com.dyoung.carpool.node.util.DateUtil;
import com.dyoung.carpool.node.util.LogUtil;
import com.dyoung.carpool.node.view.CommonListDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/11/23.
 */
public class NodeListFragment extends BaseFragement<NodeListPresenter> implements INodeListView{

    private List<Node> dataList=new ArrayList<Node>();
    protected LinearLayoutManager mLayoutManager;
    private CommonAdapter adapter;
    @BindView(R.id.loading)
    TextView loadingView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common_recyclerview_layout, container, false);
        ButterKnife.bind(this, view);
        isLoadView=true;
        lazyLoad();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mPresenter=new NodeListPresenter(mContext,this);

        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        adapter=new CommonAdapter<Node>(mContext,dataList,R.layout.fragment_node_list_item) {
            @Override
            public void convert(ViewHolder holder, final Node node) {
                holder.setText(R.id.item_title,node.getTitle());
                holder.setText(R.id.item_content,node.getContent());
                holder.setText(R.id.item_date,DateUtil.formatToDay(node.getDate()));
                holder.setOnItemClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openNodeDetail(node);
                    }
                });
                holder.setOnItemLongClick(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        setOnLongClick(node);
                        return true;
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        mPresenter.getNodeList();
    }

    /**
     * 设置长按事件
     * @param node
     */
    private  void setOnLongClick(final Node node){
        final List list=new ArrayList();
        list.add("编辑");
        list.add("删除");
        new CommonListDialog(mContext,node.getTitle(), list, new CommonListDialog.OnCommonDialogItemListener() {
            @Override
            public void onItemClick(String content) {
                LogUtil.i(TAG,content);
                if(content.equals("编辑")){
                    openNodeDetail(node);
                }else if(content.equals("删除")){
                    mPresenter.delete(node);
                }
            }
        }).show();
    }

    private  void openNodeDetail(Node node){
        Intent intent = new Intent(mContext, AddNodeActivity.class);
        intent.putExtra("node", node);
        startActivity(intent);
    }
    @Override
    protected void lazyLoad() {
        if(!isLoadView || !isVisible || isLoadData){
            return;
        }
        isLoadData=true;
        initData();
        LogUtil.i(TAG, "lazyLoad");
    }


    @Override
    public void setNodeList(List<Node> list) {
        dataList.clear();
        dataList.addAll(list);
        adapter.notifyDataSetChanged();
        isHaveData();
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
    public void appendNode(Node node) {
        for(int x=0;x<dataList.size();x++){
            Node item=dataList.get(x);
            if(node.getId()==item.getId()){
                dataList.set(x,node);
                adapter.notifyDataSetChanged();
                return;
            }
        }
        dataList.add(0, node);
        adapter.notifyDataSetChanged();

        isHaveData();
    }

    @Override
    public void deleteSuccess(Node node) {
        LogUtil.i(TAG,"deleteSuccess");
        Iterator<Node> iterator=dataList.iterator();
        while (iterator.hasNext()){
            Node item=iterator.next();
            if(node.getId()==item.getId()){
                iterator.remove();
                break;
            }
        }
        adapter.notifyDataSetChanged();

        isHaveData();
    }

    @Override
    public void deleteFailed() {
        LogUtil.i(TAG,"deleteFailed");
    }
}
