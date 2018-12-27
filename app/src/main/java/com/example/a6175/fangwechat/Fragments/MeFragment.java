package com.example.a6175.fangwechat.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a6175.fangwechat.Activity.MainActivity;
import com.example.a6175.fangwechat.Activity.User_information;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.db.User;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;

public class MeFragment extends Fragment implements OnClickListener {

    private  String mTitle;
    private View v;
    private User user;
    private TextView tv_name;
    private ImageView imageView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mefragment,null);
        init();
        setOnListener();

        return v;
    }

    public static MeFragment getInstance(String mTitle)
    {
        MeFragment MF = new MeFragment();
        MF.mTitle = mTitle;
        return MF;
    }


    private void init(){
      user = BmobUser.getCurrentUser(User.class);
      tv_name =(TextView) v.findViewById(R.id.tvname);
      tv_name.setText(user.getNickname());
      imageView=(ImageView)v.findViewById(R.id.head) ;
      Picasso.with(getActivity()).load(user.getAvater().getFileUrl()).into(imageView);



    }
    private void setOnListener()
    {
        v.findViewById(R.id.view_user).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.view_user:
                Intent intent = new Intent(getActivity(),User_information.class);
                startActivity(intent);
                break;
        }
    }
}
