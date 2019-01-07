package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddFriend extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initControl() {
        toolbar=findViewById(R.id.toolbar);
        editText= findViewById(R.id.edit_query);
        imageView= findViewById(R.id.search_button);

    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        imageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button:
                FindUser();
                break;
        }
    }

    private void FindUser(){
        BmobQuery<User> userBmobQuery = new BmobQuery<>("User");
        final String searchName = editText.getText().toString();
       // userBmobQuery.addWhereContains("mobilePhoneNumber","13979211617");
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e==null){
                    for (User user : list) {
                        if (user.getMobilePhoneNumber().equals(searchName)){
                            ActivityUtils.showShortToast(AddFriend.this,"查询成功");
                            Intent intent = new Intent(AddFriend.this,detailInformation.class);
                            intent.putExtra("User_data",user);
                            intent.putExtra("CODE",0);
                            startActivity(intent);
                            return;
                        }
                    }
                    ActivityUtils.showShortToast(AddFriend.this,"用户不存在");
                }else{
                    ActivityUtils.showShortToast(AddFriend.this,"查询失败"+e.getMessage());
                }
            }
        });

    }
}
