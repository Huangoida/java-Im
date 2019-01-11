package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.User;
import com.lqr.optionitemview.OptionItemView;
import com.luck.picture.lib.dialog.CustomDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;


public class Setting extends AppCompatActivity {

    private View mExitView;
   private OptionItemView optionItemView;
   private CustomDialog mExitDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        optionItemView= findViewById(R.id.oivExit);
        optionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QMUIBottomSheet.BottomListSheetBuilder(Setting.this)
                        .addItem("注销")
                        .addItem("退出")
                        .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                                if (position == 0){
                                    User.logOut();
                                    Intent intent = new Intent(Setting.this,FirstUseActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (position == 1){
                                    Intent intent = new Intent(Setting.this,MainActivity.class);
                                    intent.putExtra(MainActivity.TAG_EXIT,true);
                                    startActivity(intent);
                                }
                            }

                        }).build().show();
            }
        });
    }
}
