package com.example.a6175.fangwechat.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.a6175.fangwechat.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.db.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class changeNickName extends BaseActivity {

    private User user;
    private EditText editText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_nick_name);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initControl() {
        editText = findViewById(R.id.User_nickname_changee);
        toolbar=findViewById(R.id.toolbar);
    }

    @Override
    protected void initView() {
        editText.setText(user.getNickname());

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人信息");
    }

    @Override
    protected void initData() {
        user = BmobUser.getCurrentUser(User.class);
    }

    @Override
    protected void setListener() {

    }

    //添加对应的menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                changeName();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void changeName(){
        user.setNickname(editText.getText().toString());
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ActivityUtils.showLongToast(changeNickName.this,"更新成功:"+user.getUpdatedAt());
                    ActivityUtils.finish(changeNickName.this);
                }else{
                    ActivityUtils.showLongToast(changeNickName.this,"更新失败：" + e.getMessage());
                }
            }
        });
    }
}
