package com.example.a6175.fangwechat.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactsAdapter extends BaseQuickAdapter<User,BaseViewHolder> {
    private Context mcontext;

    public ContactsAdapter(@Nullable List<User> data) {
        super(R.layout.contracts_items,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

        helper.setText(R.id.textView,item.getNickname());
        Picasso.with(mContext).load(item.getAvater().getFileUrl()).into((ImageView) helper.getView(R.id.imageView));
    }
}
