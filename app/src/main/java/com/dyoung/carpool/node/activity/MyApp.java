package com.dyoung.carpool.node.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.StrictMode;
import android.util.Log;

import com.dyoung.carpool.node.BuildConfig;
import com.dyoung.carpool.node.greendao.DBHelper;
import com.dyoung.carpool.node.util.LogUtil;
import com.dyoung.carpool.node.util.ProcessUtil;
import com.orhanobut.logger.LogLevel;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import butterknife.ButterKnife;

/**
 * Created by liuwang on 2016/11/16.
 */
public class MyApp  extends DefaultApplicationLike {
    /**
     * the application
     */
    // user your appid the key.
    private static final String APP_ID = "1000270";
    // user your appid the key.
    private static final String APP_KEY = "670100056270";

    public static final String TAG = MyApp.class.getSimpleName();

    private static  Application instance;

    public MyApp(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent,
                                 Resources[] resources, ClassLoader[] classLoader, AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        //初始化greenDao
        DBHelper.getInstance().init(getApplication());
//        initMiPush();
        initUmengPush();
        //初始化logger
        initLogger();
        //初始化内存分析工具
        initStrictMode();
    }

    /**
     * 严苛模式
     */
    private  void initStrictMode(){
        if (BuildConfig.LOG_DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    /**
     *初始化logger工具
     */
    private  void initLogger() {
        if (BuildConfig.LOG_DEBUG) {
            com.orhanobut.logger.Logger.init("CarPoolNode").logLevel(LogLevel.FULL);
        } else {
            com.orhanobut.logger.Logger.init("CarPoolNode").logLevel(LogLevel.NONE);
        }
    }


    /**
     * 初始化友盟推送 无需做进程判断
     */
    private  void initUmengPush(){
        PushAgent mPushAgent = PushAgent.getInstance(getApplication());
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.i(TAG,"onSuccess:"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.i(TAG,"onFailure:s="+s+"s1="+s1);
            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                LogUtil.i(TAG,"dealWithCustomAction"+msg.custom.toString());
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    /**
     * 初始化小米push
     */
    private void initMiPush(){
        LogUtil.i(TAG,"initMiPush");
        //初始化push推送服务
        if(ProcessUtil.isMainProcess(getApplication())) {
            LogUtil.i(TAG,"registerPush");
            MiPushClient.registerPush(getApplication(), APP_ID, APP_KEY);
        }
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(getApplication(), newLogger);
    }



    /**
     * 获取单例
     * @return
     */
    public static  Application getInstance(){
        return  instance;
    }
}
