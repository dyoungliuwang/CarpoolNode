package com.dyoung.carpool.node.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dyoung.carpool.node.util.LogUtil;
import com.dyoung.carpool.node.util.ToastUtil;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

public class MiPushMessageReceiver extends PushMessageReceiver {
    /**
     * 小米推送的广播接收类
     */
    private  final  static  String TAG=MiPushMessageReceiver.class.getSimpleName();
    private String mRegId;
    /**
     * 接收客户端向服务器发送命令消息后返回的响应
     * @param context
     * @param miPushCommandMessage
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage);
        LogUtil.d(TAG,"onCommandResult"+miPushCommandMessage.toString());
    }

    /**
     * 接收服务器发送的透传消息
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
        LogUtil.d(TAG, "onReceivePassThroughMessage" + miPushMessage.toString());
    }

    /**
     * 接收服务器发来的通知栏消息（用户点击通知栏时触发）
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);
        LogUtil.d(TAG, "onNotificationMessageClicked"+miPushMessage.toString());
    }

    /**
     * 接收服务器发来的通知栏消息（消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
        LogUtil.d(TAG, "onNotificationMessageArrived"+miPushMessage.toString());
    }

    /**
     * 接收服务器推送的消息。
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceiveMessage(context, miPushMessage);
        LogUtil.d(TAG, "onReceiveMessage" + miPushMessage.toString());
    }

    /**
     * 接受客户端向服务器发送注册命令消息后返回的响应。
     * @param context
     * @param miPushCommandMessage
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
        LogUtil.d(TAG, "onReceiveRegisterResult" + miPushCommandMessage.toString());

        String command = miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                LogUtil.d(TAG, "onReceiveRegisterResult:注册成功: mRegId:" +mRegId);
                mRegId=cmdArg1;
                ToastUtil.show("注册成功");
            } else {
                LogUtil.d(TAG, "onReceiveRegisterResult:注册失败");
                ToastUtil.show("注册失败");
            }
        } else {
            String errorReason = miPushCommandMessage.getReason();
            LogUtil.d(TAG, "onReceiveRegisterResult: errorReason" +errorReason);
        }
    }
}
