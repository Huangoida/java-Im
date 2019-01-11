package com.example.a6175.fangwechat.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;



public class ActivityUtils {
    public static void showLongToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭 Activity
     * @param activity 活动
     */
    public static void finish(Activity activity) {
        activity.finish();
    }



    /**
     * 判断是否有网络
     * @param context 这个活动
     * @return 返回是否存在活动
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Network []networks = connectivity.getAllNetworks();
                NetworkInfo networkInfo;
                for(Network mNetwork : networks){
                    networkInfo = connectivity.getNetworkInfo(mNetwork);
                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)){
                        return true;
                    }
                }
            }else{
                if (connectivity == null) {
                    Log.w("Utility", "couldn't get connectivity manager");
                } else {
                    NetworkInfo[] info = connectivity.getAllNetworkInfo();
                    if (info != null) {
                        for (int i = 0; i < info.length; i++) {
                            if (info[i].isAvailable()) {
                                Log.d("Utility", "network is available");
                                return true;
                            }
                        }
                    }
                }
            }

        }
        Log.d("Utility", "network is not available");
        return false;
    }

    /**
     * 打开Activity
     *
     * @param activity 当前活动
     * @param cls  要跳跃的活动
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);


    }
}


