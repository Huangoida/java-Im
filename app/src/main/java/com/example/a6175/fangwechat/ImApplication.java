package com.example.a6175.fangwechat;

import android.app.Application;
import android.graphics.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.v3.Bmob;

public class ImApplication extends Application {
    @Override
    public void onCreate() {

        super.onCreate();

        Bmob.initialize(this,"d7ff90df3bc6230e86269a4a19921697");
//        if (getApplicationInfo().packageName.equals(getMyProcessName())){
//            BmobIM.init(this);
//            BmobIM.registerDefaultMessageHandler(new ImMessageHandler());
//        }
    }

//    public static String getMyProcessName(){
//        try {
//            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
//            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
//            String processName = mBufferedReader.readLine().trim();
//            mBufferedReader.close();
//            return processName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}