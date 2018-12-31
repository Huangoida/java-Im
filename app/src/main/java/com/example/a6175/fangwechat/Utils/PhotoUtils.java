package com.example.a6175.fangwechat.Utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.example.a6175.fangwechat.ImApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoUtils {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    //解析Uri android 4.4以上
    @TargetApi(19)
    public String handleImageOnKitKat(Intent data){
       String path = null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(ImApplication.getContext(),uri))
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
        return path;
    }

    //解析Uri android 4.4以下
    public String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
       String path =getImagePath(uri,null);
       return path;
    }

    //从URI获得图像的路径
    public String getImagePath (Uri uri,String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor =ImApplication.getContext().getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //从图像的路径获得URI
    public Uri getImageContentUri(String path){
        Cursor cursor = ImApplication.getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
            return ImApplication.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }

    //bitmap转file文件
    public String  saveBitmapFile(Bitmap bitmap) {
        String path="storage/emulated/fanwechat/save.jpg";
        File file =new File(path);
        try {
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


}
