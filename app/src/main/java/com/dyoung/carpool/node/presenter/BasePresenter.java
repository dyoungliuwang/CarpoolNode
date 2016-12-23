package com.dyoung.carpool.node.presenter;

import android.content.Context;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;

public abstract class BasePresenter<V> {
	/**
	 * 
	 * Created by lw on 2016/8/26.
	 */

	protected V mView;
	protected Context mContext;
	protected  String TAG;

	public BasePresenter(Context context,V mView) {
		this.mContext = context;
		this.mView=mView;
		TAG=getClass().getSimpleName();
//		EventBus.getDefault().register(this);
	}
	public void onDestory(){
//		EventBus.getDefault().unregister(this);
	}


}
