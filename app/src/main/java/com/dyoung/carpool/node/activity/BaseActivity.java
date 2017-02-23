package com.dyoung.carpool.node.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.presenter.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

public  class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected  static  String TAG;
    protected T mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        TAG=getClass().getSimpleName();
//        PushAgent.getInstance(this).onAppStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestory();
        }
    }


}
