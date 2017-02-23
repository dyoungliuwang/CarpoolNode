package com.dyoung.carpool.node.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
import com.dyoung.carpool.node.view.CommonListDialog;
import com.dyoung.carpool.node.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripListActivity extends BaseActivity<TripPresenter> implements ITripListView{

    private  static  final  int START_FOR_RESULT_ADD_TRIP=1;

    private List<Trip> dataList=new ArrayList<Trip>();
    protected LinearLayoutManager mLayoutManager;
    private CommonAdapter adapter;
    @BindView(R.id.loading)
    TextView loadingView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private  Trip currentTrip;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add:
                startActivityForResult(new Intent(this,AddTripActivity.class),START_FOR_RESULT_ADD_TRIP);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private  void initData(){
        mPresenter=new TripPresenter(this,this);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter=new CommonAdapter<Trip>(this,dataList,R.layout.activity_trip_list_item) {
            @Override
            public void convert(ViewHolder holder, final Trip trip) {
                holder.setText(R.id.item_title,trip.getSetOut()+"-->"+trip.getArriveCity());
                holder.setOnItemClick( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(TripListActivity.this,AddTripActivity.class);
                        intent.putExtra("item",trip);
                        startActivityForResult(intent,START_FOR_RESULT_ADD_TRIP);
                    }
                });
                holder.setOnItemLongClick(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        currentTrip=trip;
                        LogUtil.i(TAG,"onLongClick");
                        List<String> list=new ArrayList<String>();
                        list.add("删除");
                        new CommonListDialog(TripListActivity.this,trip.getSetOut()+"-->"+trip.getArriveCity(), list, new CommonListDialog.OnCommonDialogItemListener() {
                            @Override
                            public void onItemClick(String content) {
                                mPresenter.delete(trip);
                            }
                        }).show();
                        return true;
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

    @Override
    public void success() {
        dataList.remove(currentTrip);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failed(String errorCode) {
        ToastUtil.show(errorCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case START_FOR_RESULT_ADD_TRIP:
                if(resultCode==RESULT_OK){
                    mPresenter.getTripList();
                }
            break;
        }
    }
}
