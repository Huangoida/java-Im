package com.example.a6175.fangwechat.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.db.FriendUser;
import com.example.a6175.fangwechat.db.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContactsFragment extends Fragment {

    private  String mTitle;
    private User user;

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
        TextView card_title_tv =  v.findViewById(R.id.test2);
        queryFriends();
        return v;
    }

    /**
     * 查询好友
     */
    public void queryFriends(){
        BmobQuery<FriendUser> query = new BmobQuery<>();
        user = BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("user",user); //添加查询条件
        query.include("FriendUser");  //关联查询
        query.order("-updatedAt");  //排序
        query.findObjects(new FindListener<FriendUser>() {
            @Override
            public void done(List<FriendUser> list, BmobException e) {
                if (e == null){
                    for(FriendUser friendUser : list) {
                        friendUser.getFirendUser();
                    }
                }else {
                    Log.d("bmob","失败"+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
