package com.xianjiu.www.togithubproject.activity.utile;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import com.xianjiu.www.togithubproject.activity.base.BaseApplication;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
    private static String SDPATH;
    static {
        SDPATH = getSDCardBasePath();
    }

    // private static final String LOG_BASE_PATH = SDPATH + "evalet/temp/chezhu/logs";

    private static String getLogBasePath() {
        if (SDPATH == null) {
            SDPATH = getSDCardBasePath();
            LogUtil.i("日志路径:" + SDPATH + "/youapp/temp/logs");
        }
        return SDPATH + "/youapp/temp/logs";
    }

    /**
     * 获得当日的log路径
     * 
     * @return
     */
    public static String getTodayLogFilePath() {
        String dateStr = (String) android.text.format.DateFormat.format("yyyy-MM-dd",
            new Date().getTime());
        return FileUtils.getLogFilePath("log_" + dateStr);
    }

    /**
     * 获得当日的错误log路径
     * 
     * @return
     */
    public static String getTodayErrorFilePath() {
        String dateStr = (String) android.text.format.DateFormat.format("yyyy-MM-dd",
            new Date().getTime());
        return FileUtils.getLogFilePath("log_error_" + dateStr);
    }

    /**
     * 日志文件目录
     * 
     * @param fileName
     * @return
     */
    private static String getLogFilePath(String fileName) {
        if (fileName.contains(getLogBasePath())) {
            return fileName;
        }
        if (fileName.endsWith(".txt")) {
            return getLogBasePath() + "/" + fileName;
        }
        return getLogBasePath() + "/" + fileName + ".txt";
    }

    /**
     * 向文件中写入指定字符串
     * 
     * @param data
     * @param filePath
     */
    public static void writeStringToFile(String data, String filePath) {
        if (data != null) {
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    LogUtil.i("SYS", e);
                }
            }
            BufferedWriter bf = null;
            try {
                bf = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(filePath, true)));
                bf.write(data);
                bf.close();
            } catch (FileNotFoundException e) {
                LogUtil.i("SYS", e);
            } catch (IOException e) {
                LogUtil.i("SYS", e);
            }
        }
    }

    /**
     * 判断SD卡是否挂载
     * 
     * @return
     */
    private static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * // 获取sdcard的路径：外置和内置,返回这个外置SD卡目录 否则：返回内置SD卡目录
     * 
     * @return
     */
    public static String getSDCardBasePath() {
        if (isSDCardMounted()) {
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sdCardFile.getAbsolutePath();
        }
        StorageManager sm = (StorageManager) BaseApplication.getContext()
            .getSystemService(Context.STORAGE_SERVICE);
        // 获取sdcard的路径：外置和内置
        try {
            String path = null;
            File sdCardFile = null;
            // 3.0以上可以通过反射获取
            String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths").invoke(sm);
            LogUtil.i("paths==" + paths);
            for (String p : paths) {
                File file = new File(p);
                if (file.isDirectory() && file.canWrite()) {
                    path = file.getAbsolutePath();
                    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                    File testWritable = new File(path, "test_" + timeStamp);
                    if (testWritable.mkdirs()) {
                        testWritable.delete();
                        break;
                    } else {
                        path = null;
                    }
                }
            }
            if (path != null) {
                sdCardFile = new File(path);
                return sdCardFile.getAbsolutePath();
            }
        } catch (Exception e1) {
        }
        return null;
    }

    /**
     * 通过Uri获取真实路径
     * 
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                new String[] { ImageColumns.DATA }, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 获取保存文件的目录
     * 
     * @param context
     * @param hasCrop 是否有裁剪或缩略
     * @return
     */
    public static String SaveImageToFile(Context context, Bitmap bm, boolean hasCrop) {
        String fileUrl = "";
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String filename = timeStampFormat.format(System.currentTimeMillis());
        File outFile = new File(getSaveFileBasePath(context, hasCrop), filename + ".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outFile);
            boolean flag = bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);// 把数据写入文件
            if (flag) {
                fileUrl = outFile.getAbsolutePath();
            }
        } catch (FileNotFoundException e) {
        }
        return fileUrl;
    }

    /**
     * 获取保存文件的目录
     * 
     * @param context
     * @param hasCrop 是否有裁剪或缩略
     * @return
     */
    public static File getSaveFileBasePath(Context context, boolean hasCrop) {
        File outDir = null;
        String fileBaseurl = getSDCardBasePath();
        String basePath = "evalet/user/photo_album";
        if (hasCrop) {
            basePath = basePath + "/thumbnail";
        }
        if (TextUtils.isEmpty(fileBaseurl)) {
            outDir = new File(context.getFilesDir(), basePath);
        } else {
            outDir = new File(fileBaseurl, basePath);
        }
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        return outDir;
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     * 
     * @param imageUri
     * @author shanshancao
     * @date 2016-03-04
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
            && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
