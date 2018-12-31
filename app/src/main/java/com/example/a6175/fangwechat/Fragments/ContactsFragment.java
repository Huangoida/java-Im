package com.example.a6175.fangwechat.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a6175.fangwechat.Activity.detailInformation;
import com.example.a6175.fangwechat.Adapter.ContactsAdapter;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.FriendUser;
import com.example.a6175.fangwechat.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContactsFragment extends Fragment {

    private  String mTitle;
    private User user;
    private RecyclerView recyclerView;
    private Context context ;
    private  ContactsAdapter adapter;
    static List<User>friendUserList;


    public static ContactsFragment getInstance(String mTitle)
    {
        ContactsFragment CF = new ContactsFragment();
        CF.mTitle = mTitle;
        return CF;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactsfragment,null);
        recyclerView = v.findViewById(R.id.recycler_view);
        context= getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        queryFriends();
        return v;
    }

    @Override
    public void onStart() {
        Log.d("bmob","1");
        super.onStart();

    }

    /**
     * 查询好友
     */
    public void queryFriends(){

        BmobQuery<FriendUser> query = new BmobQuery<>();
        user = BmobUser.getCurrentUser(User.class);

        query.addWhereEqualTo("user",user); //添加查询条件
       // query.include("user");  //关联查询
        query.include("friendUser");
        query.findObjects(new FindListener<FriendUser>() {
            @Override
            public void done(List<FriendUser> list, BmobException e) {
                if (e == null){
                    GetList(list);
                }else {
                    Log.d("bmob","失败"+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 获得联系人列表
     * @param list
     */
    private void GetList(List<FriendUser> list){
        //将list中friend提取出来
        List<User>UserInfo = new ArrayList<>();
        for (FriendUser user :list) {
            UserInfo.add(user.getFriendUser());
        }
        friendUserList = UserInfo;

        //设置recyclerView
        adapter = new ContactsAdapter(friendUserList);
        recyclerView.setAdapter(adapter);
        //TODO 完成新朋友的设置
        adapter.setHeaderView(getLayoutInflater().inflate(R.layout.title_newfriend,null));//设置头部
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                User friend = friendUserList.get(position);
                Intent intent = new Intent(context,detailInformation.class);
                intent.putExtra("User_data",friend);
                intent.putExtra("CODE",1);
                startActivity(intent);
            }
        });
    }
}
