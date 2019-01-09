package com.example.a6175.fangwechat;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;

public class ImApplication extends Application {

    private static Context context;
    private static ImApplication INSTANCE;
    public static ImApplication INSTANCE(){
        return INSTANCE;
    }
    private void setInstance(ImApplication app){
        setImApplication(app);
    }
    private static void setImApplication(ImApplication a){
        ImApplication.INSTANCE = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        Log.d("bmob", "smile");
        Bmob.initialize(this,"d7ff90df3bc6230e86269a4a19921697");
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            BmobIM.init(this);
            LitePal.initialize(this);
            LitePal.getDatabase();
            BmobIM.registerDefaultMessageHandler(new ImMessageHandler(this));
        }
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 获取当前进程名
     * @return
     */

    public static String getMyProcessName(){
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
