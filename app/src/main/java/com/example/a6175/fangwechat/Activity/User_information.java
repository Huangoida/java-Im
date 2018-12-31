package com.example.a6175.fangwechat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6175.fangwechat.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.Utils.PhotoUtils;
import com.example.a6175.fangwechat.bean.User;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

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
    private PhotoUtils photoUtils;

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
        photoUtils= new PhotoUtils();
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
                ActivityUtils.start_Activity(User_information.this,changeNickName.class);
                break;
            case R.id.User_more:
                ActivityUtils.start_Activity(User_information.this,User_more.class);
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
        String title = "选择获取图片方式";
        String[] items = new String[]{"拍照","相册"};

        new AlertDialog.Builder(User_information.this)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which)
                        {
                            case 0:
                                pickImageFromCamera();
                                break;
                            case 1:
                                pickImageFromAlbum();
                                break;
                        }
                    }
                }).show();
    }

    //调用相机
    public void pickImageFromCamera()
    {
        //创建一个File对象，放在sd卡的关联缓存目录下
        File outPutImage = new File(getExternalCacheDir(),user.getNickname()+".jpg");
        try{
            if (outPutImage.exists())
            {
                outPutImage.delete();
            }
            outPutImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //如果低于android 7.0，调用fromeFile将File转化成uri对象
        if (Build.VERSION.SDK_INT>= 24)
        {
            imageUri = FileProvider.getUriForFile(User_information.this,"com.example.cameraalbumtest.fileprovider",outPutImage);
        }else
        {
            imageUri = Uri.fromFile(outPutImage);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    //调用相册
    public void pickImageFromAlbum()
    {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }


    //返回回来的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (resultCode == RESULT_OK) {
           switch (requestCode) {
               case TAKE_PHOTO:
                   imageUri=photoUtils.getImageContentUri(path);
                   beginCrop();
                   break;
               case CHOOSE_PHOTO:
                   //判断系统版本，调用不同方法
                    if(Build.VERSION.SDK_INT>= 19){
                        path=photoUtils.handleImageOnKitKat(data);
                    }else {
                        path=photoUtils.handleImageBeforeKitKat(data);
                    }
                    imageUri=photoUtils.getImageContentUri(path);
                    beginCrop();
                    break;
               case Crop.REQUEST_CROP:
                    Bundle bundle = data.getExtras();
                    if (bundle !=null)
                    {
                        String uploadPath=photoUtils.getImagePath(Crop.getOutput(data),null);//获得裁剪后的图片绝对路径
                        final BmobFile file = new BmobFile(new File(uploadPath));
                        file.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    SaveAvater(file);
                                }
                            }
                        });
                    }
                   break;
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

                }else {
                    Toast.makeText(User_information.this,"上传失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Picasso.with(this).load(user.getAvater().getFileUrl()).into(User_avater);
    }
    //裁剪图片
    private void beginCrop() {
        outputUri= photoUtils.getImageContentUri(path+"crop.jpg");
        Crop.of(imageUri,outputUri).asSquare().start(User_information.this);
    }

}
