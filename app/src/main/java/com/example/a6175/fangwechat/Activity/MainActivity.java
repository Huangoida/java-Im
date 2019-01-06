package com.example.a6175.fangwechat.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a6175.fangwechat.Adapter.FragmentAdapter;
import com.example.a6175.fangwechat.BaseActivity;
import com.example.a6175.fangwechat.Fragments.ContactsFragment;
import com.example.a6175.fangwechat.Fragments.MeFragment;

import com.example.a6175.fangwechat.Fragments.WechatFragment;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.User;
import com.lzy.widget.AlphaIndicator;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;


public class MainActivity extends BaseActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback  {

    private BmobUser user;
    private Button btn_logout;
    private List<Fragment> mFragments ;
    final private  int REQUEST_READ_PHONE_STATE =1;
    private Toolbar toolbar;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        requestReadPhonePermission();
        testUser();
    }

    private void requestReadPhonePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
        }
    }


    @Override
    protected void initControl() {
        btn_logout =  findViewById(R.id.btn_logout);
        toolbar =  findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);


    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initData() {
        mFragments=new ArrayList<>();
        mFragments.add(new  WechatFragment());
        mFragments.add(new  ContactsFragment());
        mFragments.add(new MeFragment());
        FragmentAdapter adapter =new FragmentAdapter(getSupportFragmentManager(),mFragments);
        viewPager.setAdapter(adapter);
        AlphaIndicator alphaIndicator = findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);
    }


    /**
     * 设置监听对象
     */
    @Override
    protected void setListener() {
        btn_logout.setOnClickListener(this);
    }

    /**
     * 在onCreateOptionsMenu方法中加载toobar.xml文件
     * @param menu Toolbar Item布局文件
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_toolbar,menu);
        return  true;
    }

    /**
     * 在onOptionSItemSelected中添加处理Toolbar上各个按键的点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_addfriend:
                ActivityUtils.start_Activity(MainActivity.this,AddFriend.class);
                break;
            case R.id.menu_saoyisao:
                Toast.makeText(this,"扫一扫",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
        return  true;
    }

    /**
     * 请求权限的回调结果处理函数
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:

                break;

            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //用户注销按钮
            case R.id.btn_logout:
                User.logOut();
                Toast.makeText(MainActivity.this,"用户注销",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,FirstUseActivity.class);
                //清空全部会话，防止出现bug
                BmobIM.getInstance().clearAllConversation();
                startActivity(intent);
                finish();

        }
    }

    private void testUser() {
        //User访问本地缓存，看是否有缓存的用户对象
        user= User.getCurrentUser();
        if (user != null)
        {
            //允许用户使用应用
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                        ActivityUtils.showShortToast(MainActivity.this,"连接成功");
                        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                            @Override
                            public void onChange(ConnectionStatus connectionStatus) {
                                ActivityUtils.showShortToast(MainActivity.this,connectionStatus.getMsg());
                            }
                        });
                    }else {
                        ActivityUtils.showShortToast(MainActivity.this,"连接失败");
                    }
                }
            });
        }else {
            //缓存用户对象为空时，打开用户注册界面
            Intent intent = new Intent(MainActivity.this,FirstUseActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
