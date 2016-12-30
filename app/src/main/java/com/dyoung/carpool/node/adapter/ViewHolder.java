package com.dyoung.carpool.node.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 2016/11/18.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private  View mView;
    public ViewHolder(View itemView) {
        super(itemView);
        mView=itemView;
        mViews=new SparseArray<View>();
    }

    public  static ViewHolder createHolder(Context context,ViewGroup parent,int layoutId){
        View view= LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder  holder=new ViewHolder(view);
        return  holder;
    }

    /**
     * 设置字体
     * @param viewId
     * @param typeFace
     * @return
     */
    public ViewHolder setTextTypeface(int viewId,Typeface typeFace){
        TextView textView=getView(viewId);
        if( !textView.getTypeface().equals(typeFace)){
            textView.setTypeface(typeFace);
        }
        return  this;
    }
    public ViewHolder setText(int viewId,String text){
        TextView textView=getView(viewId);
        textView.setText(text);
        return  this;
    }

    public ViewHolder setOnItemClick(View.OnClickListener listener){
        mView.setOnClickListener(listener);
        return  this;
    }
    public ViewHolder setOnItemLongClick(View.OnLongClickListener listener){
        mView.setOnLongClickListener(listener);
        return  this;
    }
    public ViewHolder setTextViewRightDrawable(int viewId,int drawId){
        TextView view =getView(viewId);
        view.setCompoundDrawablesWithIntrinsicBounds(null,null,view.getContext().getResources().getDrawable(drawId),null);
        return  this;
    }

    public ViewHolder setTextViewLeftDrawable(int viewId,int drawId){
        TextView view =getView(viewId);
        view.setCompoundDrawablesWithIntrinsicBounds(view.getContext().getResources().getDrawable(drawId),null,null,null);
        return  this;
    }

    public ViewHolder setOnClickListener(int viewId,View.OnClickListener listener){
        View view=getView(viewId);
        view.setOnClickListener(listener);
        return  this;
    }

    public  <T extends  View> T getView(int id){
        View view=mViews.get(id);
        if(view==null){
            view=mView.findViewById(id);
            mViews.put(id,view);
        }
        return (T)view;

    }

}
