package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.User;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;



public class User_information extends BaseActivity implements View.OnClickListener {


    private  RelativeLayout avater;   //头像布局
    private  RelativeLayout nick_change;   //昵称布局
    private  RelativeLayout more;   //更多布局
    private  ImageView User_avater;  //头像
    private TextView nickname;  //昵称
    private TextView wxid;  //id
    private User user;
    private Uri imageUri;
    private Uri outputUri;

    String path="";

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_information);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initControl() {
        avater =  findViewById(R.id.User_avater_change);
        nick_change = findViewById(R.id.User_nickname_change);
        more =  findViewById(R.id.User_more);
        User_avater = findViewById(R.id.User_avater);
        nickname =  findViewById(R.id.User_nickname);
        wxid = findViewById(R.id.wxid);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人信息");

    }

    @Override
    protected void initView() {

        nickname.setText(user.getNickname());
        wxid.setText(user.getId());
        BmobFile file = user.getAvater();
        Picasso.with(this).load(user.getAvater().getFileUrl()).into(User_avater);
    }

    @Override
    protected void initData() {
        user = BmobUser.getCurrentUser(User.class);

    }

    @Override
    protected void setListener() {
        nick_change.setOnClickListener(this);
        more.setOnClickListener(this);
        avater.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.User_avater_change:
                UploadAvater();
                break;
            case R.id.User_nickname_change:
                ActivityUtils.startActivity(User_information.this,changeNickName.class);
                break;
            case R.id.User_more:
                ActivityUtils.startActivity(User_information.this,userMore.class);
                break;

        }

    }

    //返回的事件响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        nickname.setText(user.getNickname());
        super.onStart();
    }

    //上传图片
    private void UploadAvater()
    {
        PictureSelector.create(User_information.this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)//单选
                .isCamera(true)//是否显示拍照
                .enableCrop(true)//是否裁剪
                .compress(true)//是否压缩
                .previewImage(false)
                .withAspectRatio(1,1)//裁剪比例
                .freeStyleCropEnabled(true)//裁剪框可以拖拽
                .isDragFrame(true)//是否可以拖动裁剪框
                .showCropFrame(true)//是否显示边框
                .forResult(PictureConfig.CHOOSE_REQUEST);

    }




    //返回回来的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (resultCode == RESULT_OK) {
           switch (requestCode) {
               case PictureConfig.CHOOSE_REQUEST:
                   List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                   String upload =list.get(0).getCutPath();
                   final BmobFile file = new BmobFile(new File(upload));
                   file.upload(new UploadFileListener() {
                       @Override
                       public void done(BmobException e) {
                           if (e == null){
                               SaveAvater(file);
                           }else {

                           }
                       }
                   });
                default:
                    break;
           }
       }
    }
    //保存头像
    private void SaveAvater(BmobFile file) {
        user.setAvater(file);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Toast.makeText(User_information.this,"上传成功",Toast.LENGTH_SHORT).show();
                    PictureFileUtils.deleteCacheDirFile(User_information.this);//清理图片缓存

                }else {
                    Toast.makeText(User_information.this,"上传失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        Picasso.with(this).load(user.getAvater().getFileUrl()).into(User_avater);
    }


}
