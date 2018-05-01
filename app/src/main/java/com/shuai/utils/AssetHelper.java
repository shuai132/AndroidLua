package com.shuai.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liushuai on 18-5-1.
 */

public final class AssetHelper {
    private static final String TAG = AssetHelper.class.getSimpleName();

    private Context _context;

    @SuppressLint("StaticFieldLeak")
    private static AssetHelper assetHelper;

    public static AssetHelper getInstance(Context context) {
        if (assetHelper == null) {
            assetHelper = new AssetHelper(context);
        }
        return assetHelper;
    }

    private AssetHelper(Context context) {
        _context = context;
    }

    /**
     * 从资源中拷贝指定文件夹的文件到cache目录
     *
     * @param path 资源文件相对路径 不可以'/'结尾 不可使用'.'开始
     * @param endWith 需要拷贝的文件类型(文件名尾) 或 使用'*'匹配所有文件
     */
    public void copyFileFromAssets(String path, String endWith) {
        try {
            Log.d(TAG, "list: " + path);
            String[] paths = _context.getAssets().list(path);

            if (paths.length > 0) {
                //为目录，继续迭代查找
                for (String subPath : paths) {
                    Log.d(TAG, "foreach: " + subPath);
                    copyFileFromAssets(String.format("%s/%s", path, subPath), endWith);
                }
            }
            else {
                //为文件，拷贝到临时目录
                String fileName = path;//.substring(0, path.length() - 1);
                ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                if (endWith.equals("*") || fileName.toLowerCase().endsWith(endWith)) {
                    try {
                        String filePath = String.format("%s/%s", _context.getCacheDir(), fileName);
                        Log.d(TAG, "copying... " + filePath);
                        File file = new File(filePath);
                        File parentFile = file.getParentFile();
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        }

                        readAssetFileContent(fileName, dataStream);
                        //直接覆盖缓存路径中的文件，保证以assets中的内容为最新。
                        writeToFile(file, dataStream);
                        dataStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取资源文件内容
     * @param fileName  文件名称
     * @param outputStream  输出内容的二进制流
     */
    private void readAssetFileContent(String fileName, ByteArrayOutputStream outputStream) {
        try {
            InputStream stream = _context.getAssets().open(fileName);
            byte[] buffer = new byte[1024];

            int hasRead = 0;
            while ((hasRead = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, hasRead);
            }

            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     * @param file  目标文件
     * @param dataStream    二进制流
     */
    private void writeToFile(File file, ByteArrayOutputStream dataStream) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            dataStream.writeTo(stream);
            stream.flush();
            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
