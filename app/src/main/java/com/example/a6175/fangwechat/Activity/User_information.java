package com.example.a6175.fangwechat.Activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6175.fangwechat.BuildConfig;
import com.example.a6175.fangwechat.R;
//import com.example.a6175.fangwechat.Utils.PicassoImageLoader;
import com.example.a6175.fangwechat.db.User;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
//import com.lzy.imagepicker.ImagePicker;
//import com.lzy.imagepicker.bean.ImageItem;
//import com.lzy.imagepicker.ui.ImageGridActivity;
//import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
//import me.iwf.photopicker.PhotoPicker;


public class User_information extends AppCompatActivity implements View.OnClickListener {


    private  RelativeLayout avater;   //头像布局
    private  RelativeLayout nick_change;   //昵称布局
    private  RelativeLayout more;   //更多布局
    private  ImageView User_avater;  //头像
    private TextView nickname;  //昵称
    private TextView wxid;  //id
    private User user;
    private Uri imageUri;
    private Uri outputUri;
    private Bitmap mbitmap;
    private File mfile;

    String path="";

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int CUT_PHOTO = 3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);


        init();
        setListener();
        getInformation();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.User_avater_change:
                UploadAvater();

                break;
            case R.id.User_nickname_change:
                break;
            case R.id.User_more:
                break;
        }

    }

    public void init()
    {
        avater = (RelativeLayout) findViewById(R.id.User_avater_change);
        nick_change = (RelativeLayout)findViewById(R.id.User_nickname_change);
        more = (RelativeLayout) findViewById(R.id.User_more);
        User_avater = (ImageView) findViewById(R.id.User_avater);
        nickname = (TextView) findViewById(R.id.User_nickname);
        wxid = (TextView)findViewById(R.id.wxid);
        user = BmobUser.getCurrentUser(User.class);




    }
    public void setListener()
    {
      nick_change.setOnClickListener(this);
      more.setOnClickListener(this);
      avater.setOnClickListener(this);
    }

    public void getInformation()//获得帐号信息
    {
        nickname.setText(user.getNickname());
        wxid.setText(user.getObjectId());
        BmobFile file = user.getAvater();
        Picasso.with(this).load(user.getAvater().getFileUrl()).into(User_avater);

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
                   imageUri=getImageContentUri(path);

                   break;
               case CHOOSE_PHOTO:
                    if(Build.VERSION.SDK_INT>= 19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                    imageUri=getImageContentUri(path);
                    beginCrop();
                    break;
               case Crop.REQUEST_CROP:
                    Bundle bundle = data.getExtras();
                    if (bundle !=null)
                    {
                        String uploadPath=getImagePath(Crop.getOutput(data),null);//获得裁剪后的图片绝对路径
                        final BmobFile file = new BmobFile(new File(uploadPath));
                        file.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    SaveFile(file);
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

    private void SaveFile(BmobFile file) {
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

    //解析Uri
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        path = null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri))
        {
            //如果是document类型的uri，通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id =docId.split(":")[1];//解析出数字id
                String selection = MediaStore.Images.Media._ID + "="+id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                path = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            path = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
    }

    //解析Uri android 4.4以下
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        path =getImagePath(uri,null);
    }

    private String getImagePath (Uri uri,String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor =getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
           cursor.close();
        }
        return path;
    }

    private Uri getImageContentUri(String path){
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, ""+id);
        }else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, path);
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }

    private void beginCrop() {
        outputUri= getImageContentUri(path+"crop.jpg");
        Crop.of(imageUri,outputUri).asSquare().start(User_information.this);
    }

}
