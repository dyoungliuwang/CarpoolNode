package com.dyoung.carpool.node.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.util.SizeUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2016/11/26.
 */
public class CommonPopupWindow extends PopupWindow {

    /**
     * 通用popupWindow
     * @param context
     * @param layoutId
     */

    private View conentView;
    private  Activity context;
    public  CommonPopupWindow(Activity context,int layoutId){
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(layoutId, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth((int)SizeUtil.Dp2Px(context,135));
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
//        backgroundAlpha(0.4f);
        this.setAnimationStyle(R.style.common_popupwindow);
    }
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            Logger.i("showPopupWindow conentView"+conentView.getWidth());
            this.showAsDropDown(parent, parent.getWidth()-(int)SizeUtil.Dp2Px(context,135)-(int)SizeUtil.Dp2Px(context,15), 0);
        } else {
            this.dismiss();
        }
    }
    public void showPopupWindow(View parent,int xoff,int yoff) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, xoff, yoff);
        } else {
            this.dismiss();
        }
    }

    /**
     * 设置监听事件
     */
    public  void setOnClickListener(int id,View.OnClickListener listener){
        conentView.findViewById(id).setOnClickListener(listener);
    }

}
