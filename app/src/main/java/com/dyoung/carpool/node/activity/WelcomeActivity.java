package com.dyoung.carpool.node.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dyoung.carpool.node.R;

public class WelcomeActivity extends AppCompatActivity {
    /**
     * 欢迎页
     */
    private static Handler handler;
    private final static  int GOTO_MAINACTIVIT=1;
    private  final static int TIME=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();

            }
        };
        handler.sendEmptyMessageDelayed(GOTO_MAINACTIVIT, TIME);
    }
}
