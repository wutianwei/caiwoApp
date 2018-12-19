package com.caiwo.caiwoapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;


import com.caiwo.caiwoapp.app.MyApplication;
import com.caiwo.caiwoapp.utils.picture.Bimp;
import com.caiwo.caiwoapp.utils.picture.ImageItems;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件工具类
 */
public class FileUtil {

    public static List<String> photoaslist = new ArrayList<String>();
    public static List<String> photos = new ArrayList<String>();
    public static Map<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();


    //    public static String path = Environment.getExternalStorageDirectory() + "/rongba/catch/img2";
    private static final String TAG = "FileUtil";
    private static String pathDiv = "/";
    private static File cacheDir = !isExternalStorageWritable() ? MyApplication.getInstance().getFilesDir() : MyApplication.getInstance().getExternalCacheDir();
    public static List<String> quanziPath = new ArrayList<String>();

    private FileUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 创建临时文件
     *
     * @param type 文件类型
     */
    public static File getTempFile(FileType type) {
        try {
            File file = File.createTempFile(type.toString(), null, cacheDir);
            file.deleteOnExit();
            return file;
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * 获取缓存文件地址
     */
    public static String getCacheFilePath(String fileName) {
        return cacheDir.getAbsolutePath() + pathDiv + fileName;
    }

    /**
     * 获取缓存文件地址
     */
    public static String getCacheFilePath2(String fileName) {
        return cacheDir.getAbsolutePath() + pathDiv + fileName;
    }

    public static String path = Environment.getExternalStorageDirectory() + "/rongba/融吧客户端/";

    public static String loadIamge(String fimename) {
//        String galleryPath= Environment.getExternalStorageDirectory()
//                + File.separator + Environment.DIRECTORY_DCIM
//                +File.separator+"Camera"+File.separator;

        File file = new File(path);

        String fileName = path + fimename;

        if (!file.exists()) {
            file.mkdirs();// 创建文件夹

        } else {
            file.delete();
            file.mkdirs();
        }
        return fileName;
    }

    /**
     * 判断缓存文件是否存在
     */
    public static boolean isCacheFileExist(String fileName) {
        File file = new File(getCacheFilePath(fileName));
        return file.exists();
    }

    /**
     * 判断缓存文件是否存在
     */
    public static boolean isCacheFileExist2(String fileName) {
        File file = new File(getCacheFilePath(fileName));
        return file.exists();
    }

    /**
     * 将图片存储为文件
     *
     * @param bitmap 图片
     */
    public static String createFile(Bitmap bitmap, String filename) {
        File f = new File(cacheDir, filename);
        try {
            if (f.createNewFile()) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "create bitmap file error" + e);
        }
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        return null;
    }

    /**
     * 将数据存储为文件
     *
     * @param data 数据
     */
    public static void createFile(byte[] data, String filename) {
        File f = new File(cacheDir, filename);
        try {
            if (f.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(data);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "create file error" + e);
        }
    }


    /**
     * 判断缓存文件是否存在
     */
    public static boolean isFileExist(String fileName, String type) {
        if (isExternalStorageWritable()) {
            File dir = MyApplication.getInstance().getExternalFilesDir(type);
            if (dir != null) {
                File f = new File(dir, fileName);
                return f.exists();
            }
        }
        return false;
    }


    /**
     * 将数据存储为文件
     *
     * @param data     数据
     * @param fileName 文件名
     * @param type     文件类型
     */
    public static File createFile(byte[] data, String fileName, String type) {
        if (isExternalStorageWritable()) {
            File dir = MyApplication.getInstance().getExternalFilesDir(type);
            if (dir != null) {
                File f = new File(dir, fileName);
                try {
                    if (f.createNewFile()) {
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(data);
                        fos.flush();
                        fos.close();
                        return f;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "create file error" + e);
                    return null;
                }
            }
        }
        return null;
    }


    /**
     * 从URI获取图片文件地址
     *
     * @param context 上下文
     * @param uri     文件uri
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getImageFilePath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String path = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            if (!isMediaDocument(uri)) {
                try {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                } catch (IllegalArgumentException e) {
                    path = null;
                }
            }
        }
        if (path == null) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }

            path = null;
        }
        return path;
    }


    /**
     * 从URI获取文件地址
     *
     * @param context 上下文
     * @param uri     文件uri
     */
    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * 判断外部存储是否可用
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        Log.e(TAG, "ExternalStorage not mounted");
        return false;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public enum FileType {
        IMG,
        AUDIO,
        VIDEO,
        FILE,
    }

    /**
     * 拍照，选择图片，根据图片获取文件的 Uri
     *
     * @return
     */
    public static Uri getOutputMediaFileUri(Context context) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
        }
        String sdsta = Environment.getExternalStorageDirectory() + "/rongba/catch/img2/2/";
        File file = new File(sdsta);
        if (!file.exists()) {
            file.mkdirs();// 创建文件
        }
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile = new File(file.getPath() + File.separator + "/rongba" + timeStamp + ".jpg");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                String authority = "com.rongba.xindai.fileprovider";
                return FileProvider.getUriForFile(context.getApplicationContext(), authority, mediaFile);
            } else {
                return Uri.fromFile(mediaFile);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static void saveBitmap(Bitmap bm, String picName, Context context) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.getInstance(context).show("SD卡不可用");
            return;
        }
        String mkdirs = path;// 图片名字

        File file = new File(mkdirs);

        String fileName = path + picName + ".jpg";
        if (!file.exists()) {

            file.mkdirs();// 创建文件夹

        } else {
            file.delete();
            file.mkdirs();
        }
        // 写入SD卡
        FileOutputStream out;
        try {
            out = new FileOutputStream(fileName);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //fileName
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存数据
     */
    public static String saveBitmap(Bitmap bm) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        String mkdirs = path;// 图片名字
        File file = new File(mkdirs);
        String fileName = path + "head.jpg";
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        } else {
            file.delete();
            file.mkdirs();
        }
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(fileName);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

//    /**
//     * 拍照
//     */
//    public static  void photoGraph(CirclePublishActivity mContext) {
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        try {
//            String state = Environment.getExternalStorageState();
//            if (Environment.MEDIA_MOUNTED.equals(state)) {
////                AppConstants.mImageCaptureUri  = getTmpPhotoUri(mContext);
//                AppConstants.mImageCaptureUri = getTmpPhotoUri2(mContext);
//            } else {
//                AppConstants.mImageCaptureUri  = InternalStorageContentProvider.CONTENT_URI;
//            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, AppConstants.mImageCaptureUri );
//            intent.putExtra("return-data", true);
//             mContext.startActivityForResult(intent, 5);
//        } catch (ActivityNotFoundException e) {
//            L.d("cannot take picture", e);
//        }
//    }
//    public static Uri getTmpPhotoUri(Context context) {
//        File file = getTmpPhotoFile(context);
//        if (file != null) {
//            return Uri.fromFile(file);
//        }
//        return null;
//    }
//    public static Uri getTmpPhotoUri2(Context context) {
//        File file = getpPhotoFile(context);
//        if (file != null) {
//            return Uri.fromFile(file);
//        }
//        return null;
//    }
    private static Uri imguri;

    public static void quanzi(Context mContext, Uri uploadUri) {
        String str = "";
        if (null == uploadUri) {
            uploadUri = imguri;
            if (uploadUri.toString().contains("content://")) {
                String[] proj = {MediaStore.Images.Media.DATA};
                CursorLoader loader = new CursorLoader(mContext, uploadUri, proj, null, null, null);
                Cursor cursor = loader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                str = cursor.getString(column_index);
            } else {
                str = uploadUri.getPath();
            }
        } else {
            if (uploadUri.toString().contains("content://")) {
                String[] proj = {MediaStore.Images.Media.DATA};
                CursorLoader loader = new CursorLoader(mContext, uploadUri, proj, null, null, null);
                Cursor cursor = loader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                str = cursor.getString(column_index);
            } else {
                str = uploadUri.getPath();
            }
        }
        try {
            str = compressImageUpload(str);
            photoaslist.add(str);
            Log.e("aaa","=-=-=-=photoaslist="+photoaslist.toString());
        } catch (Exception e) {
//            upLoadHeadImg(str);
        }
    }


    public static String compressImageUpload(String path) {
        String filename = path.substring(path.lastIndexOf("/") + 1);
        Bitmap image = getImage(path);

        ImageItems toke = new ImageItems();
//        CirclePublishActivity.fun();
        int degree = readPictureDegree(path);
        if(degree!=0){
            image =  rotaingImageView(degree,image);
        }
        toke.setBitmap(image);
        Bimp.tempSelectBitmap.add(toke);
        return saveMyBitmap(filename, image);
    }
    /**
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Log.e("aaa","angle2="+angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
    public static String saveMyBitmap(String filename, Bitmap bit) {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rongba/";
        String filePath = baseDir + filename;
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File f = new File(filePath);
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bit.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return filePath;
    }

    public static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    }
