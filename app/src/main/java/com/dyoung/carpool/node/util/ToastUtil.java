package com.dyoung.carpool.node.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dyoung.carpool.node.activity.MyApp;

/**
 * Created by admin on 2016/11/21.
 */
public class ToastUtil {
    /**
     * 用于给用户显示Toast
     */

    private static  Context context= MyApp.getInstance();

    public  static  void show(String text){
        LogUtil.i(ToastUtil.class.getSimpleName(),"show :text="+text);
        show(text, Toast.LENGTH_SHORT);
    }
    public  static  void show(String text,int duration ){
        Toast.makeText(context, text, duration).show();
    }

}
