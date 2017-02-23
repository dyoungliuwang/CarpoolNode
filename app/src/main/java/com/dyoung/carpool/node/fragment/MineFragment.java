package com.dyoung.carpool.node.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.activity.AboutActivity;
import com.dyoung.carpool.node.activity.FeedBookActivity;
import com.dyoung.carpool.node.activity.TripListActivity;
import com.dyoung.carpool.node.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragement {
    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @BindView(R.id.trip_set)
    TextView tripSet;
    @BindView(R.id.trip_carpool)
    TextView carPoolSet;
    @BindView(R.id.trip_feedbook)
    TextView feedBookSet;
    @BindView(R.id.update)
    TextView versionUpdate;
    @BindView(R.id.about)
    TextView aboutSet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.trip_set,R.id.trip_carpool,R.id.trip_feedbook,R.id.update,R.id.about})
    public void onClickView(View view){
        LogUtil.i(TAG,"onCLick: view="+view);
        if(view==tripSet){
            Intent intent=new Intent(mContext,TripListActivity.class);
            startActivity(intent);
        }else if(view==feedBookSet){
            Intent intent=new Intent(mContext,FeedBookActivity.class);
            startActivity(intent);
        }else if(view==aboutSet){
            Intent intent=new Intent(mContext,AboutActivity.class);
            startActivity(intent);
        }
    }

}
