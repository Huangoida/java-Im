package com.example.a6175.fangwechat.model;

import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.view.Activity.User_regiseter;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户的MODEL
 */
public class UserModel implements Imodel {
    @Override
    public void login(String username, String password, final OnLoginListener onLoginListener) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    onLoginListener.loginSuccess();
                }else {
                    onLoginListener.loginFailed(e.getMessage());
                }
            }
        });
    }

    /**
     * 注册
     * @param NickName 昵称
     * @param MobilePhone 手机号
     * @param Username 用户名
     * @param password 密码
     * @param onRegisetereListener 注册事件监听器
     */
    @Override
    public void regiseter(String NickName, String MobilePhone, String Username, String password, final OnRegisetereListener onRegisetereListener) {
        User user = new User();
        BmobFile bmobFile = new BmobFile("default.png","","http://bmob-cdn-23206.b0.upaiyun.com/2018/12/29/0cad45e4401054b280f5556c05395e17.png");
        user.setNickname(NickName);
        user.setMobilePhoneNumber(MobilePhone);
        user.setUsername(Username);
        user.setPassword(password);
        user.setAvater(bmobFile); // 设置默认头像
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null)
                {
                    //设置wxid
                    user.setId("wxid_"+user.getObjectId());
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                onRegisetereListener.RegisetereSuccess();
                            }else {
                                onRegisetereListener.RegisetereFailed(e.getMessage());
                            }
                        }
                    });
                }else
                {
                    onRegisetereListener.RegisetereFailed(e.getMessage());
                }
            }
        });


    }




}
