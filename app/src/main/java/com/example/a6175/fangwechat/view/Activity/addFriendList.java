package com.example.a6175.fangwechat.view.Activity;



import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a6175.fangwechat.Adapter.NewFriendAdapter;
import com.example.a6175.fangwechat.Listener.BaseListener;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.model.AgreeAddFriendMessage;
import com.example.a6175.fangwechat.bean.NewFriend;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.view.BaseActivity;

import org.litepal.LitePal;

import java.util.List;


import cn.bmob.v3.BmobUser;


public class addFriendList extends BaseActivity {

    private RecyclerView recyclerView;
    private NewFriendAdapter newFriendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initControl() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        List<NewFriend>newFriendList = LitePal.where("userId = ?",user.getObjectId())
                .find(NewFriend.class);
        if (newFriendList != null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            newFriendAdapter = new NewFriendAdapter(newFriendList);
            recyclerView.setAdapter(newFriendAdapter);
        }
    }

    @Override
    protected void setListener() {
        //设置好友界面的监听者
    newFriendAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
            List<NewFriend> newFriendList = adapter.getData();
            final NewFriend newFriend = newFriendList.get(position);
            AgreeAddFriendMessage.snedAgreeAddFriendMEssage(newFriend, new BaseListener() {
                @Override
                public void Success() {
                    ActivityUtils.showShortToast(addFriendList.this,"添加成功");
                    LitePal.delete(NewFriend.class,newFriend.getId());
                    adapter.remove(position);
                }

                @Override
                public void Failure(String e) {
                    ActivityUtils.showShortToast(addFriendList.this,e);
                }
            });
        }
    });
    }
}
