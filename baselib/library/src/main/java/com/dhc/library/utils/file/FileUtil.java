package com.dhc.library.utils.file;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.text.TextUtils;

import com.dhc.library.utils.string.StringUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * 文件工具类
 */
public class FileUtil {


    public static final String ASSETS_PREFIX = "file://android_assets/";
    public static final String ASSETS_PREFIX2 = "file://android_asset/";
    public static final String ASSETS_PREFIX3 = "assets://";
    public static final String ASSETS_PREFIX4 = "asset://";
    public static final String RAW_PREFIX = "file://android_raw/";
    public static final String RAW_PREFIX2 = "raw://";
    public static final String FILE_PREFIX = "file://";
    public static final String DRAWABLE_PREFIX = "drawable://";

    public enum SizeUnit {
        Byte,
        KB,
        MB,
        GB,
        TB,
        Auto,
    }

        private static final String TAG = "FileUtil";

    public static boolean hasExtentsion(String filename) {
        int dot = filename.lastIndexOf('.');
        if ((dot > -1) && (dot < (filename.length() - 1))) {
            return true;
        } else {
            return false;
        }
    }

    // 获取文件扩展名
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    // 获取文件名
    public static String getFileNameFromPath(String filepath) {
        if ((filepath != null) && (filepath.length() > 0)) {
            int sep = filepath.lastIndexOf('/');
            if ((sep > -1) && (sep < filepath.length() - 1)) {
                return filepath.substring(sep + 1);
            }
        }
        return filepath;
    }

    // 获取不带扩展名的文件名
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }



    /**
     * 读取Property文件
     */
    public static HashMap<String, String> simpleProperty2HashMap(Context context, String path) {
        try {
            InputStream is = getStream(context, path);
            return simpleProperty2HashMap(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<String, String>();
    }

    private static HashMap<String, String> simpleProperty2HashMap(InputStream in) throws IOException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        Set keyValue = properties.keySet();
        for (Iterator it = keyValue.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            hashMap.put(key, (String) properties.get(key));
        }

        return hashMap;
    }


    public static InputStream getStream(Context context, String url) throws IOException {
        String lowerUrl = url.toLowerCase();
        InputStream is;
        if (lowerUrl.startsWith(ASSETS_PREFIX)) {
            String assetPath = url.substring(ASSETS_PREFIX.length());
            is = getAssetsStream(context, assetPath);
        } else if (lowerUrl.startsWith(ASSETS_PREFIX2)) {
            String assetPath = url.substring(ASSETS_PREFIX2.length());
            is = getAssetsStream(context, assetPath);
        } else if (lowerUrl.startsWith(ASSETS_PREFIX3)) {
            String assetPath = url.substring(ASSETS_PREFIX3.length());
            is = getAssetsStream(context, assetPath);
        } else if (lowerUrl.startsWith(ASSETS_PREFIX4)) {
            String assetPath = url.substring(ASSETS_PREFIX4.length());
            is = getAssetsStream(context, assetPath);
        } else if (lowerUrl.startsWith(RAW_PREFIX)) {
            String rawName = url.substring(RAW_PREFIX.length());
            is = getRawStream(context, rawName);
        } else if (lowerUrl.startsWith(RAW_PREFIX2)) {
            String rawName = url.substring(RAW_PREFIX2.length());
            is = getRawStream(context, rawName);
        } else if (lowerUrl.startsWith(FILE_PREFIX)) {
            String filePath = url.substring(FILE_PREFIX.length());
            is = getFileStream(filePath);
        } else if (lowerUrl.startsWith(DRAWABLE_PREFIX)) {
            String drawableName = url.substring(DRAWABLE_PREFIX.length());
            is = getDrawableStream(context, drawableName);
        } else {
            throw new IllegalArgumentException(String.format("Unsupported url: %s \n" +
                    "Supported: \n%sxxx\n%sxxx\n%sxxx", url, ASSETS_PREFIX, RAW_PREFIX, FILE_PREFIX));
        }
        return is;
    }

    private static InputStream getAssetsStream(Context context, String path) throws IOException {
        return context.getAssets().open(path);
    }

    private static InputStream getFileStream(String path) throws IOException {
        return new FileInputStream(path);
    }

    private static InputStream getRawStream(Context context, String rawName) throws IOException {
        int id = context.getResources().getIdentifier(rawName, "raw", context.getPackageName());
        if (id != 0) {
            try {
                return context.getResources().openRawResource(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new IOException(String.format("raw of id: %s from %s not found", id, rawName));
    }

    private static InputStream getDrawableStream(Context context, String rawName) throws IOException {
        int id = context.getResources().getIdentifier(rawName, "drawable", context.getPackageName());
        if (id != 0) {
            BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(id);
            Bitmap bitmap = drawable.getBitmap();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, os);
            return new ByteArrayInputStream(os.toByteArray());
        }

        throw new IOException(String.format("bitmap of id: %s from %s not found", id, rawName));
    }

    public static String getString(Context context, String url) throws IOException {
        return getString(context, url, "UTF-8");
    }

    public static String getString(Context context, String url, String encoding) throws IOException {
        String result = readStreamString(getStream(context, url), encoding);
        if (result.startsWith("\ufeff")) {
            result = result.substring(1);
        }

        return result;
    }

    public static String readStreamString(InputStream is, String encoding) throws IOException {
        return new String(readStream(is), encoding);
    }

    public static byte[] readStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024 * 10];
        int readlen;
        while ((readlen = is.read(buf)) >= 0) {
            baos.write(buf, 0, readlen);
        }
        baos.close();

        return baos.toByteArray();
    }



    /**
     * 把字节数组保存为一个文件
     *
     * @param b
     * @param fileName
     * @return
     */
    public static File write(byte[] b, String fileName) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(fileName);
            stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 文件/目录是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean exist(String fileName) {
        return new File(fileName).exists();
    }

    /**
     * 创建目录
     *
     * @param path  目录
     * @param cover 是否覆盖
     * @return
     */
    public static void createFolder(String path, boolean cover) {
        try {
            File file = new File(path);
            if (file.exists()) {
                if (cover) {
                    FileUtil.deleteFile(path, true);
                    file.mkdirs();
                }
            } else {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建空的文件
     *
     * @param fileName 文件
     * @param cover    是否覆盖
     * @return
     */
    public static boolean createFile(String fileName, boolean cover) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        try {
            File file = new File(fileName);
            if (file.exists()) {
                if (cover) {
                    file.delete();
                    file.createNewFile();
                }
            } else {
                // 如果路径不存在，先创建路
                File mFile = file.getParentFile();
                if (!mFile.exists()) {
                    mFile.mkdirs();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @param deleteParent 是否删除父目录
     */
    public static void deleteFile(String filePath, boolean deleteParent) {
        if (filePath == null) {
            return;
        }
        try {
            File f = new File(filePath);
            if (f.exists() && f.isDirectory()) {
                File[] delFiles = f.listFiles();
                if (delFiles != null) {
                    for (File delFile : delFiles) {
                        deleteFile(delFile.getAbsolutePath(), deleteParent);
                    }
                }
            }
            if (deleteParent) {
                f.delete();
            } else if (f.isFile()) {
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
//            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
//                    bytesum += byteread; //字节数 文件大小
//                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
//            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
//            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 判断手机SDCard是否已安装并可读写
     *
     * @return
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState());
    }

    /**
     * 读取文件
     *
     * @param fileName
     * @return
     */
    public static String readFile(String fileName) {
        try {
            return readFromStream(new FileInputStream(new File(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取assets文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readAsset(Context context, String fileName) {
        try {
            AssetFileDescriptor descriptor = context.getApplicationContext().getAssets().openFd(fileName);
            return readFromStream(descriptor.createInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取raw文件
     *
     * @param context
     * @param resId
     * @return
     */
    public static String readRaw(Context context, int resId) {
        return readFromStream(context.getResources().openRawResource(resId));
    }

    private static String readFromStream(InputStream in) {
        if (in == null) {
            return null;
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
            return new String(out.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSavePath(Context context, String FileName) {
        return getCacheDirectory(context).getAbsolutePath() + File.separator
                + FileName;
    }

    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(
                Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(
                new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }

    public static File createFile(String path) {
        if (path != null) {
            File file = new File(path);
            if (!file.exists()) {
                File file2 = new File(file.getParent());
                file2.mkdir();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return file;
        }
        return null;

    }


    /**
     * 获取文件或文件夹大小
     *
     * @param fileOrDir 文件或文件夹对象
     * @return 字节数
     */
    public static long getFileOrDirSize(File fileOrDir) {
        if (fileOrDir == null || !fileOrDir.exists()) {
            return 0L;
        }

        if (fileOrDir.isDirectory()) {
            File[] children = fileOrDir.listFiles();
            long length = 0L;
            for (File child : children) {
                length += getFileOrDirSize(child);
            }
            return length;
        } else {
            return getFileSize(fileOrDir);
        }
    }

    /**
     * 获取文件大小
     *
     * @param file 文件对象
     * @return 字节数
     */
    private static long getFileSize(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return 0L;
        }
        return file.length();
    }


    /**
     * 获取文件后缀名
     *
     * @param fileNameOrPath 文件名或文件路径
     * @return 后缀名（含“.”）
     */
    public static String getSuffix(String fileNameOrPath) {
        if (StringUtil.isEmpty(fileNameOrPath)) {
            return "";
        }
        String dot = ".";
        if (!fileNameOrPath.contains(dot)) {
            return "";
        }
        return fileNameOrPath.substring(fileNameOrPath.lastIndexOf(dot)).toLowerCase();
    }

    /**
     * 返回byte的数据大小对应的文本
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size == 0){
            return "0KB";
        }else if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size: error";
        }
    }



    public static void cleanCache(Context context){
        deleteFile(getCacheDirectory(context).getPath(),false);
    }


    /**
     * 创建未存在的文件夹
     *
     * @param file
     * @return
     */
    public static File makeDirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


}
