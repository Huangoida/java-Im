package com.example.a6175.fangwechat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.db.User;


import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity   {

   BmobUser user;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        user= User.getCurrentUser();
        if (user != null)
        {
            //运行用户使用应用
            Toast.makeText(MainActivity.this,"存在用户",Toast.LENGTH_SHORT).show();

        }else {
           //缓存用户对象为空时，可打开用
            Intent intent = new Intent(MainActivity.this,First_use.class);
            startActivity(intent);
            finish();
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.logOut();
                Toast.makeText(MainActivity.this,"用户注销",Toast.LENGTH_SHORT).show();
            }
        });

//       btn_conect.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               if (TextUtils.isEmpty(et_connect_id.getText().toString()))
//               {
//                   Toast.makeText(MainActivity.this,"请填写连接id",Toast.LENGTH_SHORT).show();
//               }
//              // btn_conect.setClickable(false);
//               BmobIM.connect(et_connect_id.getText().toString(), new ConnectListener() {
//                   @Override
//                   public void done(String s, BmobException e) {
//                       if(e==null)
//                       {
//                           isConnect= true;
//                           Log.i("TAG","服务器连接成功");
//                       }else
//                       {
//                           Log.i("TAG","服务器连接失败");
//                       }
//                   }
//               });
//           }
//       });
//
//       btn_send.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               if (!isConnect)
//               {
//                   Toast.makeText(MainActivity.this,"未连接状态不能打开会话",LENGTH_SHORT).show();
//                   return;
//               }
//               if (!isOpenConversation)
//               {
//                   BmobIMUserInfo info = new BmobIMUserInfo();
//                   info.setAvatar("填写接受者的头像");
//                   info.setUserId(et_connect_id.getText().toString());
//                   info.setUserId("填写接受者的名字");
//                   BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
//                       @Override
//                       public void done(BmobIMConversation bmobIMConversation, BmobException e) {
//                           if (e==null)
//                           {
//                               isOpenConversation = true;
//                               mBmobIMCoversation = BmobIMConversation.obtain(BmobIMClient.getInstance(),bmobIMConversation);
//                               tv_message.append("发送者:"+et_message.getText().toString()+"\n");
//                               BmobIMTextMessage msg = new BmobIMTextMessage();
//                               msg.setContent(et_message.getText().toString());
//                               mBmobIMCoversation.sendMessage(msg, new MessageSendListener() {
//                                   @Override
//                                   public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                                       if (e != null)
//                                       {
//                                           et_message.setText("");
//                                       }else
//                                       {
//
//                                       }
//                                   }
//                               });
//                           }else {
//                               Toast.makeText(MainActivity.this,"开启会话出错",LENGTH_SHORT).show();
//                           }
//                       }
//                   });
//               }else{
//                   BmobIMTextMessage msg = new BmobIMTextMessage();
//                   msg.setContent(et_message.getText().toString());
//                   tv_message.append("发送者:"+et_message.getText().toString()+"\n");
//                   mBmobIMCoversation.sendMessage(msg, new MessageSendListener() {
//                       @Override
//                       public void done(BmobIMMessage bmobIMMessage, BmobException e) {
//                           if (e != null)
//                           {
//                               et_message.setText("");
//                           }else
//                           {
//
//                           }
//                       }
//                   });
//               }
//           }
//       });
    }

    private void init(){
    btn_logout = (Button) findViewById(R.id.btn_logout);
    }




}
