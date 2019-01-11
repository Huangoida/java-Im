package com.example.a6175.fangwechat.view.Fragments;

import android.app.Activity;
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
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.view.Activity.addFriendList;
import com.example.a6175.fangwechat.view.Activity.detailInformation;
import com.example.a6175.fangwechat.Adapter.ContactsAdapter;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.FriendUser;
import com.example.a6175.fangwechat.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContactsFragment extends Fragment implements View.OnClickListener {

    private Activity ctx;
    private  String mTitle;
    private User user;
    private RecyclerView recyclerView;
    private Context context ;
    private  ContactsAdapter adapter;
    static List<User>friendUserList;
    private View layout, layoutHead;


    public static ContactsFragment getInstance(String mTitle)
    {
        ContactsFragment CF = new ContactsFragment();
        CF.mTitle = mTitle;
        return CF;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layout == null){
            layout = inflater.inflate(R.layout.fragment_contacts,null);
            ctx = this.getActivity();
            layoutHead = ctx.getLayoutInflater().inflate(R.layout.layout_head_friend,null);
            setLisnener();
            recyclerView = layout.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);
            queryFriends();
        }else{
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null){
                parent.removeView(layout);
            }
        }
        return layout;
    }

    @Override
    public void onStart() {
        Log.d("bmob","1");
        super.onStart();
    }

    public void setLisnener(){
      layoutHead.findViewById(R.id.layout_addfriend).setOnClickListener(this);
      layoutHead.findViewById(R.id.layout_group).setOnClickListener(this);
      layoutHead.findViewById(R.id.layout_public).setOnClickListener(this);
    }

    /**
     * 查询好友
     */
    public void queryFriends(){

        BmobQuery<FriendUser> query = new BmobQuery<>();
        user = BmobUser.getCurrentUser(User.class);
        //添加查询条件
        query.addWhereEqualTo("user",user);
       // query.include("user");  //关联查询
        query.include("friendUser");
        query.findObjects(new FindListener<FriendUser>() {
            @Override
            public void done(List<FriendUser> list, BmobException e) {
                if (e == null){
                    getList(list);
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
    private void getList(List<FriendUser> list){
        //将list中friend提取出来
        List<User> userinfo = new ArrayList<>();
        for (FriendUser user :list) {
             userinfo.add(user.getFriendUser());
        }
        friendUserList =  userinfo;
        List<BmobIMUserInfo> bmobIMUserInfoList=new ArrayList<>();
        for (int i=0;i< userinfo.size();i++) {
            BmobIMUserInfo userInfo =new BmobIMUserInfo( userinfo.get(i).getObjectId(), userinfo.get(i).getNickname(), userinfo.get(i).getAvater().getFileUrl());
            bmobIMUserInfoList.add(userInfo);
        }
        BmobIM.getInstance().updateBatchUserInfo(bmobIMUserInfoList);
        //设置recyclerView
        adapter = new ContactsAdapter(friendUserList);
        adapter.setHeaderViewAsFlow(true);
        recyclerView.setAdapter(adapter);

        //TODO 完成新朋友的设置
        adapter.setHeaderView(layoutHead);//设置头部
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                User friend = friendUserList.get(position);
                Intent intent = new Intent(ctx,detailInformation.class);
                intent.putExtra("User_data",friend);
                intent.putExtra("CODE",1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_addfriend:
                Intent intent =new Intent(getActivity(),addFriendList.class);
                startActivity(intent);
                break;
            case R.id.layout_group:
                break;
            default:
                    break;
        }
    }
}
