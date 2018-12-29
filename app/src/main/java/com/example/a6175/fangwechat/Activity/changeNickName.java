package com.example.a6175.fangwechat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.db.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class changeNickName extends AppCompatActivity {

    private User user;
    private EditText editText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nick_name);

        init();
    }

    private void init() {
        user = BmobUser.getCurrentUser(User.class);
        editText = (EditText)findViewById(R.id.User_nickname_changee);
        editText.setText(user.getNickname());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.fanhui);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人信息");

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
                   Toast.makeText(changeNickName.this,"更新成功:"+user.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                   finish();
                }else{
                    Toast.makeText(changeNickName.this,"更新失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
