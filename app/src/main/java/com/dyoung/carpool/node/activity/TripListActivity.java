package com.dyoung.carpool.node.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.adapter.CommonAdapter;
import com.dyoung.carpool.node.adapter.ViewHolder;
import com.dyoung.carpool.node.greendao.model.Trip;
import com.dyoung.carpool.node.mvpview.ITripListView;
import com.dyoung.carpool.node.presenter.TripPresenter;
import com.dyoung.carpool.node.util.LogUtil;
import com.dyoung.carpool.node.util.ToastUtil;
import com.dyoung.carpool.node.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripListActivity extends BaseActivity<TripPresenter> implements ITripListView{

    private List<Trip> dataList=new ArrayList<Trip>();
    protected LinearLayoutManager mLayoutManager;
    private CommonAdapter adapter;
    @BindView(R.id.loading)
    TextView loadingView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        ButterKnife.bind(this);

        toolbar.setTitle("行程");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_back_selector);

        initData();
    }

    private  void initData(){
        mPresenter=new TripPresenter(this,this);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter=new CommonAdapter<Trip>(this,dataList,R.layout.activity_trip_list_item) {
            @Override
            public void convert(ViewHolder holder,  Trip trip) {
                holder.setText(R.id.item_title,trip.getSetOut()+"-->"+trip.getArriveCity());
                holder.setOnClickListener(R.id.item_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        mPresenter.getTripList();
    }

    @Override
    public void setTripList(List<Trip> list) {
        loadingView.setVisibility(View.GONE);
        dataList.clear();
        dataList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTripListFailed() {
        LogUtil.e(TAG,"getTripListFailed");
        ToastUtil.show("获取数据失败");
    }
}
