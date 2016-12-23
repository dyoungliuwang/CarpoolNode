package com.dyoung.carpool.node.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.util.SizeUtil;

import java.util.List;

/**
 * Created by admin on 2016/11/25.
 */
public class CommonListDialog extends Dialog {
    /**
     * 通用dialog 选择器
     * @param context
     */

    private OnCommonDialogItemListener listener;
    private Context context;
    private  List<String> dataList;
    private LinearLayout linearLayout;
    private TextView titleTxt;
    private  String title;
    public CommonListDialog(Context context ,String title,List<String> dataList,final OnCommonDialogItemListener listener) {
        super(context, R.style.dialog_style);
        this.listener=listener;
        this.context=context;
        this.dataList=dataList;
        this.title=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.common_dialog_layout);
        linearLayout=(LinearLayout)super.findViewById(R.id.container);
        titleTxt=(TextView)findViewById(R.id.common_dialog_title);

        if(!TextUtils.isEmpty(title)){
            titleTxt.setVisibility(View.VISIBLE);
            titleTxt.setText(title);
        }

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
//        params.alpha = 1.0f;
//        params.dimAmount = 0.7f;
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        for(String item:dataList){
            TextView textView=createTextView(item);
            if(textView!=null){
                linearLayout.addView(textView);
            }
        }
    }
    private TextView createTextView(final String item){
        int padding=(int) SizeUtil.Dp2Px(context, 8);
        int minHeigt=(int)SizeUtil.Dp2Px(context, 40);
        TextView textView=new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, minHeigt));
        textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.textview_selector));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(padding, 0, padding, 0);
        textView.setTextSize(16);
        textView.setText(item);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonListDialog.this.dismiss();
                listener.onItemClick(item);
            }
        });
        return textView;
    }

    public interface  OnCommonDialogItemListener{
        void  onItemClick(String content);
    }


}
