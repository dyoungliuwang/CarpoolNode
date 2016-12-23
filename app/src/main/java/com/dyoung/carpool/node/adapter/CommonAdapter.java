package com.dyoung.carpool.node.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyoung.carpool.node.greendao.model.CarPoolNode;

import java.util.List;

/**
 * Created by liuwang on 2016/11/17.
 */
public abstract  class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private List<T> dataList;
    private  Context context;
    private  int layoutId;

    public CommonAdapter(Context context, List<T> dataList, int layoutId) {
        this.dataList = dataList;
        this.context=context;
        this.layoutId=layoutId;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder,dataList.get(position));
    }

    public  abstract void  convert(ViewHolder holder,T t);



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createHolder(context,parent,layoutId);
    }

}
