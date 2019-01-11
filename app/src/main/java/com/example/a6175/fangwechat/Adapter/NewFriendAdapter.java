package com.example.a6175.fangwechat.Adapter;




import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.NewFriend;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewFriendAdapter extends BaseQuickAdapter<NewFriend,BaseViewHolder> {


    public NewFriendAdapter( List<NewFriend> data) {
        super(R.layout.item_newfriend,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewFriend item) {
        helper.setText(R.id.txt_name,item.getName());
        helper.setText(R.id.txt_msg,item.getMsg());
        Picasso.with(mContext).load(item.getAvatar()).into((ImageView) helper.getView(R.id.img_photo));
        helper.addOnClickListener(R.id.txt_add);
    }
}
