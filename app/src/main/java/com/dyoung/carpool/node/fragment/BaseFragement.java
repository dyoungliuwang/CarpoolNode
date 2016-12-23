package com.dyoung.carpool.node.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dyoung.carpool.node.presenter.BasePresenter;

/**
 * Created by liuwang on 2016/11/17.
 */
public abstract class BaseFragement<T extends BasePresenter> extends Fragment {

    protected Context mContext;
    protected  String TAG;

    protected T mPresenter;

    /**
     * 是否加载view
     */
    protected boolean isLoadView=false;
    /**
     * 是否可见
     */
    protected  boolean isVisible=false;
    /**
     * 是否加载数据
     */
    protected boolean isLoadData=false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
        TAG=getClass().getSimpleName();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestory();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible=true;
            onVisible();
        }else{
            onInvisible();
            isVisible=false;
        }
    }

    protected void onVisible(){

        lazyLoad();
    }

    protected  void lazyLoad(){};

    protected void onInvisible(){}

    /**
     * 返回按钮
     * @param view
     */
    public  void onBack(View view){
        ((Activity)mContext).finish();
    }

}