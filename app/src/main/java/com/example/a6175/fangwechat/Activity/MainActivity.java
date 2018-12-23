package com.example.a6175.fangwechat.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.a6175.fangwechat.Fragments.ContactsFragment;
import com.example.a6175.fangwechat.Fragments.MeFragment;
import com.example.a6175.fangwechat.Fragments.WechatFragment;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.TabEntity;
import com.example.a6175.fangwechat.db.User;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;


import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity   {

    private BmobUser user;
    private Button btn_logout;
    private ArrayList<Fragment> mFragments ;
    private ArrayList<CustomTabEntity> customTabEntities ;
    private String[] mTitle = {"微信","通讯录","我"};
    private int[] mIconUnselectIds = {R.drawable.wechat_defult,R.drawable.tongxunlu_defult,R.drawable.my_defult};
    private int[] mIconSelectIds = {R.drawable.wechat_selected,R.drawable.tongxunlu_selected,R.drawable.my_selected};
    private CommonTabLayout commonTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //User访问本地缓存，看是否有缓存的用户对象
        user= User.getCurrentUser();
        if (user != null)
        {
            //允许用户使用应用
            Toast.makeText(MainActivity.this,"存在用户",Toast.LENGTH_SHORT).show();

        }else {
           //缓存用户对象为空时，可打开用户注册界面
            Intent intent = new Intent(MainActivity.this,FirstuseActivity.class);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //配置TabLayout
        commonTabLayout.setTabData(customTabEntities,this,R.id.fl_change,mFragments);
        //设置小圆点
        commonTabLayout.showDot(0);

        //用户注销按钮
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.logOut();
                Toast.makeText(MainActivity.this,"用户注销",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //在onCreateOptionsMenu方法中加载toobar.xml文件
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return  true;
    }


    //在onOptionSItemSelected中添加处理各个按键的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_addfriend:
                Toast.makeText(this,"添加朋友",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_saoyisao:
                Toast.makeText(this,"扫一扫",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
        return  true;
    }

    //初始化控件和对象
    private void init(){
    btn_logout = (Button) findViewById(R.id.btn_logout);
    mFragments = new ArrayList<>();
    customTabEntities = new ArrayList<>();
    commonTabLayout = (CommonTabLayout)findViewById(R.id.tl_commen);

    mFragments.add(WechatFragment.getInstance(mTitle[0]));
    mFragments.add(ContactsFragment.getInstance(mTitle[1]));
    mFragments.add(MeFragment.getInstance(mTitle[2]));

        for (int i = 0; i < mTitle.length; i++) {
            customTabEntities.add(new TabEntity(mTitle[i],mIconSelectIds[i],mIconUnselectIds[i]));
        }
    }








}
