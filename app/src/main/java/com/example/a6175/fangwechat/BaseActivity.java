package com.example.a6175.fangwechat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.a6175.fangwechat.Utils.ActivityUtils;


public abstract class BaseActivity extends AppCompatActivity {
    private Activity context;
    private static final int REQUEST_FINE_LOCATION=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        initControl();
        initData();
        initView();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            ActivityUtils.finish(this);
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 绑定控件
     */
    protected abstract void initControl();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected  abstract void initData();

    /**
     * 设置监听
     */
    protected  abstract void setListener();

    /**
     * 打开Activity
     */
    public void start_Activity(Activity activity,Class<?> cls) {
        ActivityUtils.start_Activity(activity,cls);
    }

    public void finish(Activity activity){
        ActivityUtils.finish(activity);
    }

    public boolean isNetWorkAvailable(Context context){
        return ActivityUtils.isNetworkAvailable(context);
    }

}
