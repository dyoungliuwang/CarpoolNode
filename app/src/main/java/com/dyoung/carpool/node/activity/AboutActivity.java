package com.dyoung.carpool.node.activity;
import android.os.Bundle;

import com.dyoung.carpool.node.R;

import butterknife.ButterKnife;


public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        ButterKnife.bind(this);

        toolbar.setTitle("关于");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_back_selector);
    }


}
