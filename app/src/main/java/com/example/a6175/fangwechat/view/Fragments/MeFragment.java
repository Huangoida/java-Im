package com.example.a6175.fangwechat.view.Fragments;

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
import android.widget.TextView;

import com.example.a6175.fangwechat.view.Activity.Setting;
import com.example.a6175.fangwechat.view.Activity.User_information;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.User;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;

public class MeFragment extends Fragment implements OnClickListener {

    private  String mTitle;
    private View v;
    private User user;
    private TextView tv_name;
    private ImageView imageView;
    private TextView tv_id;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_me,null);
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

    @Override
    public void onStart() {
        super.onStart();
        user = BmobUser.getCurrentUser(User.class);

        Picasso.with(getActivity()).load(user.getAvater().getFileUrl()).into(imageView);
        tv_name.setText(user.getNickname());
        tv_id.setText("微信号:"+user.getId());
    }

    //初始化控件
    private void init(){
        user = BmobUser.getCurrentUser(User.class);
      tv_name = v.findViewById(R.id.tvname);
      tv_name.setText(user.getNickname());
      imageView= v.findViewById(R.id.head) ;
      tv_id =v.findViewById(R.id.tv_id);

    }
    private void setOnListener()
    {
        v.findViewById(R.id.view_user).setOnClickListener(this);
        v.findViewById(R.id.setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.view_user:
                Intent intent = new Intent(getActivity(),User_information.class);
                startActivity(intent);
                break;
            case R.id.setting:
                Intent intent1 = new Intent(getActivity(),Setting.class);
                startActivity(intent1);
                default:
                    break;
        }
    }
}
