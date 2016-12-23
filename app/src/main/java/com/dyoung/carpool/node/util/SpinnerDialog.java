package com.dyoung.carpool.node.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.activity.AddCarNodeActivity;

import java.util.List;

/**
 * Created by lw on 2016/11/21.
 */
public class SpinnerDialog {

    public static Spinner showSpinner(Context context,final List<String> list,final SpinnerClickCallBack callBack){
        Spinner spinner=new Spinner(context,Spinner.MODE_DIALOG){
            @Override
            public void setSelection(int position) {
                super.setSelection(position);
                LogUtil.i("SPinnerDialog","setSelection; position="+position);
                callBack.onClick(position);
            }
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.performClick();
        return spinner;
    }

    public  interface  SpinnerClickCallBack{
        void  onClick(int position);
    }

}
