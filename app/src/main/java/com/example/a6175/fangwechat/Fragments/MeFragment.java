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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a6175.fangwechat.Activity.MainActivity;
import com.example.a6175.fangwechat.Activity.User_information;
import com.example.a6175.fangwechat.R;

public class MeFragment extends Fragment implements OnClickListener {

    private  String mTitle;
    private View v;



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
