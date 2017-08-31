package com.dhc.library.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class UploadUtil {


    /**
     * 将文件路径数组封装为{@link List<MultipartBody.Part>}
     * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     * 同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key,
                                                       String[] filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (int i = 0; i <filePaths.length ; i++) {
            File file = new File(filePaths[i]);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key+i, file.getName(), requestBody);

            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     * @param key 同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key,
                                                     String[] filePaths,
                                                     MediaType imageType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i <filePaths.length ; i++) {
            File file = new File(filePaths[i]);
            RequestBody requestBody = RequestBody.create(imageType, file);
            builder.addFormDataPart(key+i, file.getName(), requestBody);
        }
//         Multipart body must have at least one part.
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }


    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     * @param parts Part集合
     * @param key 参数名（name属性）
     * @param value 文本内容
     * @param position 插入的位置
     */
    public static void addTextPart(List<MultipartBody.Part> parts,
                                   String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    /**
     * 添加文本类型的Part到的MultipartBody.Builder中
     * @param builder 用于构建MultipartBody的Builder
     * @param key 参数名（name属性）
     * @param value 文本内容
     */
    public static MultipartBody.Builder addTextPart(MultipartBody.Builder builder,
                                                    String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        // MultipartBody.Builder的addFormDataPart()有一个直接添加key value的重载，但坑的是这个方法
        // 不会设置编码类型，会出乱码，所以可以使用3个参数的，将中间的filename置为null就可以了
        // builder.addFormDataPart(key, value);
        // 还有一个坑就是，后台取数据的时候有可能是有顺序的，比如必须先取文本后取文件，
        // 否则就取不到（真弱啊...），所以还要注意add的顺序
        builder.addFormDataPart(key, null, requestBody);
        return builder;
    }
}
