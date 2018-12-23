package com.example.a6175.fangwechat.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a6175.fangwechat.R;

public class WechatFragment extends Fragment {
    private String mTitle;

    public static WechatFragment getInstance(String mTitle)
    {
        WechatFragment WF = new WechatFragment();
        WF.mTitle = mTitle;
        return WF;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wechatfragment,null);
        TextView card_title_tv = (TextView)v.findViewById(R.id.test1);

        return v;
    }
}
