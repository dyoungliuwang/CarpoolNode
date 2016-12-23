package com.dyoung.carpool.node.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.*;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by admin on 2016/11/23.
 */
public class ProcessUtil {
    /**
     * 进程管理
     */

    /**
     * 判断是不是主进程
     * @param context
     * @return
     */
    public static  boolean isMainProcess(Context context) {
        return  isCurrentProcess(context,null);
    }

    /**
     * 判断是不是当前进程
     * @param context
     * @param processName
     * @return
     */
    public static  boolean isCurrentProcess(Context context,String processName) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        LogUtil.i("ProcessUtil","mainProcessName:"+mainProcessName);
        if(!TextUtils.isEmpty(processName)){
            mainProcessName+=processName;
        }
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
