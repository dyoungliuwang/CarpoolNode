package com.dyoung.carpool.node.util;

import com.dyoung.carpool.node.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2016/11/21.
 */
public class LogUtil {

    private final  static  boolean isDebug= BuildConfig.LOG_DEBUG;

    public static void v(String tag,String info){
        if(isDebug){
            Logger.v(info,tag);
        }

    }

    public static void d(String tag,String info){
        if(isDebug){
            Logger.d(info,tag);
        }

    }
    public static void i(String tag,String info){
        if(isDebug){
            Logger.i(info,tag);
        }

    }
    public static void e(String tag,String info){
        if(isDebug){
            Logger.e(info,tag);
        }
    }

}
