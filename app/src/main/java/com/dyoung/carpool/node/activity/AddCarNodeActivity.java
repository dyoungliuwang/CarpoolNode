package com.dyoung.carpool.node.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.greendao.DBHelper;
import com.dyoung.carpool.node.greendao.model.CarPoolNode;
import com.dyoung.carpool.node.greendao.model.NodeType;
import com.dyoung.carpool.node.greendao.model.Trip;
import com.dyoung.carpool.node.mvpview.IAddCarNodeView;
import com.dyoung.carpool.node.presenter.AddCarPoolPresenter;
import com.dyoung.carpool.node.util.DateUtil;
import com.dyoung.carpool.node.util.LogUtil;
import com.dyoung.carpool.node.util.SpinnerDialog;
import com.dyoung.carpool.node.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCarNodeActivity extends BaseActivity<AddCarPoolPresenter> implements IAddCarNodeView{

    private CarPoolNode editNodeCar;

    //  @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.date) TextView dateSelect;
    @BindView(R.id.trip) TextView tripSelect;
    @BindView(R.id.numType) TextView numType;
    @BindView(R.id.number) EditText numberEdt;
    @BindView(R.id.mark) EditText markEdt;
    @BindView(R.id.num) TextView numEdt;

    @BindView(R.id.minus) ImageView minusImg;
    @BindView(R.id.plus) ImageView plusImg;

    private  List<Trip> tripList=new ArrayList<Trip>();
    private  List<String> tripListStr=new ArrayList<String>();
    private  Trip currentTrip;
    private  Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_node);
        ButterKnife.bind(this);

        toolbar.setTitle("拼车信息");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_back_selector);

        mPresenter=new AddCarPoolPresenter(this,this);
        initDatas();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.save:
                CarPoolNode carNode=createCarNode();
                if(editNodeCar!=null && carNode!=null){
                    carNode.setId(editNodeCar.getId());
                    mPresenter.update(carNode);
                    return super.onOptionsItemSelected(item);
                }

                if(carNode!=null){
                    mPresenter.addNode(carNode);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //清空数据
        if(editNodeCar!=null){
            editNodeCar.__setDaoSession(null);
        }
    }

    public void initDatas(){
         editNodeCar=getIntent().getParcelableExtra("node");
         mPresenter.getTripList();
         if(editNodeCar!=null){
             calendar=Calendar.getInstance();
             calendar.setTimeInMillis(editNodeCar.getRideTime());

             editNodeCar.__setDaoSession(DBHelper.getInstance().getDaoSession());
             currentTrip=editNodeCar.getTrip();
             dateSelect.setText("发车时间:  "+DateUtil.formatToDay(editNodeCar.getRideTime()));
             tripSelect.setText("行程区间  "+currentTrip.getSetOut()+"-->"+currentTrip.getArriveCity());
             numType.setText("号码类型:  "+editNodeCar.getNodeType().getName());
             markEdt.setText(editNodeCar.getMark());
             numEdt.setText(editNodeCar.getNum()+"");
             numberEdt.setText(editNodeCar.getNumber());
         }else{
             calendar=Calendar.getInstance();
             dateSelect.setText("发车时间:  "+DateUtil.formatToDay(calendar.getTimeInMillis()));
         }
    }
    @Override
    public void addSuccess() {
//        ToastUtil.show("保存成功");
        finish();
    }
    @Override
    public void addFailed() {
        ToastUtil.show("保存失败");
    }

    @Override
    public void setTripList(List<Trip> tripList) {
        LogUtil.i(TAG,"setTripList");
        if(tripList.isEmpty()){
            LogUtil.i(TAG,"setTripList: tripList is Empty");
            return ;
        }

        if(editNodeCar==null){
            currentTrip=tripList.get(0);
            tripSelect.setText("行程区间:  "+currentTrip.getSetOut() + "--" + currentTrip.getArriveCity());
        }
        this.tripList.addAll(tripList);
        for (Trip item:tripList){
            tripListStr.add(item.getSetOut()+"--"+item.getArriveCity());
        }
    }

    @OnClick({R.id.date,R.id.trip,R.id.numType,R.id.minus,R.id.plus})
    public void viewOnClick(View view) {
        if(view==dateSelect){
            dateSelect();
        }else if(view==tripSelect){
            SpinnerDialog.showSpinner(this, tripListStr, new SpinnerDialog.SpinnerClickCallBack() {
                @Override
                public void onClick(int position) {
                    currentTrip = tripList.get(position);
                    tripSelect.setText("行程区间:  "+currentTrip.getSetOut() + "--" + currentTrip.getArriveCity());
                }
            });
        }else if(view==numType){
           List<String> list = Arrays.asList(getResources().getStringArray(R.array.carNodeType));
           SpinnerDialog.showSpinner(this, list, new SpinnerDialog.SpinnerClickCallBack() {
                @Override
                public void onClick(int position) {
                    LogUtil.i(TAG, "position=" + position);
                    numType.setText("号码类型:  "+getResources().getStringArray(R.array.carNodeType)[position]);
                }
            });
        }else if(view==minusImg){
            int num=Integer.parseInt(numEdt.getText().toString());
            numEdt.setText(--num+"");
        }else if(view==plusImg){
            int num=Integer.parseInt(numEdt.getText().toString());
            numEdt.setText(++num+"");

        }
    }

    /**
     * 日期选择
     */
    private  void dateSelect(){
        new DatePickerDialog(this,  new DatePickerDialog.OnDateSetListener() {  // 绑定监听器
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        LogUtil.i(TAG,"year="+year+"monthOfYear="+monthOfYear+"dayOfMonth="+dayOfMonth);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH,monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        dateSelect.setText("发车时间:  "+DateUtil.formatToDay(calendar.getTimeInMillis()));
                        timeSelect();
                    }
                }
                , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     */
    private  void timeSelect(){
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                LogUtil.i(TAG,"hourOfDay="+hourOfDay+"minute="+minute);
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                dateSelect.setText("发车时间:  "+DateUtil.formatToDay(calendar.getTimeInMillis()));
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true).show();
    }


    private  CarPoolNode createCarNode(){
        String dateStr=dateSelect.getText().toString().trim();
//        String tripStr=tripSelect.getText().toString().trim();
        String numTypeStr=numType.getText().toString().trim();
        String numberStr= numberEdt.getText().toString().trim();
        String markStr=markEdt.getText().toString().trim();
        int num=Integer.parseInt(numEdt.getText().toString().trim());

        if(TextUtils.isEmpty(numberStr)){
            ToastUtil.show("号码不能为空");
            return null;
        }
        CarPoolNode carNode=new CarPoolNode();
        if(numTypeStr.contains("手机号")){
            carNode.setNodeType(NodeType.PHONE);
        }else if(numTypeStr.contains("微信")){
            carNode.setNodeType(NodeType.WX);
        }else if(numTypeStr.contains("QQ")){
            carNode.setNodeType(NodeType.QQ);
        }
        carNode.setNum(num);
        carNode.setTrip(currentTrip);
        carNode.setNumber(numberStr);
        carNode.setMark(markStr);
        carNode.setStatus(0);
        carNode.setRideTime(calendar.getTimeInMillis());
        carNode.setDate(System.currentTimeMillis());
        return carNode;
    }






}
