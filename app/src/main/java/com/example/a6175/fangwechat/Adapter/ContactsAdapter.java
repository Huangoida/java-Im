package com.example.a6175.fangwechat.Adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.PingYinUtil;
import com.example.a6175.fangwechat.Utils.PinyinComparator;
import com.example.a6175.fangwechat.bean.User;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ContactsAdapter extends BaseQuickAdapter<User,BaseViewHolder> {

    private List<User>users;

    public ContactsAdapter(@Nullable List<User> data) {
        super(R.layout.item_contracts,data);
        Collections.sort(data, new PinyinComparator());
        users=data;
    }

    @Override
    public LinearLayout getHeaderLayout() {
        return super.getHeaderLayout();
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.textView,item.getNickname());
        Picasso.with(mContext).load(item.getAvater().getFileUrl()).into((ImageView) helper.getView(R.id.imageView));
        //获得联系人首字母
        String catalog = PingYinUtil.converterToFirstSpell(item.getNickname()).substring(0,1);

        if (helper.getLayoutPosition() == 1){
            helper.setVisible(R.id.contactitem_catalog, true);
            helper.setText(R.id.contactitem_catalog,catalog);
        }else {
            //检查上一个联系人首字母是否相同
            User user =users.get(helper.getLayoutPosition()-2);
            String lastCatalog = PingYinUtil.converterToFirstSpell(user.getNickname()).substring(0, 1);
            if (catalog.equals(lastCatalog)){
                helper.setGone(R.id.contactitem_catalog, false);

            }else {
                helper.setVisible(R.id.contactitem_catalog, true);
                helper.setText(R.id.contactitem_catalog,catalog);
            }

        }
    }
}
