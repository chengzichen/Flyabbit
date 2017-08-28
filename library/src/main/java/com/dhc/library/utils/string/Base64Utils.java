package com.dhc.library.utils.string;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 邓浩宸
 * @author Administrator
 *
 */
public class Base64Utils {
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    /**
     * Base64 encode the given data
     */
    public static String encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    private static int decode(char c) {
        if (c >= 'A' && c <= 'Z')
            return ((int) c) - 65;
        else if (c >= 'a' && c <= 'z')
            return ((int) c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return ((int) c) - 48 + 26 + 26;
        else
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
    }

    /**
     * Decodes the given Base64 encoded String to a new byte array. The byte
     * array holding the decoded data is returned.
     */

    public static byte[] decode(String s) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }
    				/**
    				 * 将流转为base64
    				 * @param s
    				 * @param os
    				 * @throws IOException
    				 */
    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;

        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;

            if (i == len)
                break;

            int tri = (decode(s.charAt(i)) << 18)
                    + (decode(s.charAt(i + 1)) << 12)
                    + (decode(s.charAt(i + 2)) << 6)
                    + (decode(s.charAt(i + 3)));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);

            i += 4;
        }
    }
    	/**
    	 * 讲bitmap转为base64字符串
    	 * @param clientBitmap   图片
    	 * @return
    	 */
    public static  String  encodeBase64Bitmap(Bitmap  clientBitmap){
    	
    	/* Bitmap clientBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.messi);*/
         //2.bitmap->byte[]
         ByteArrayOutputStream streem = new ByteArrayOutputStream();
         clientBitmap.compress(Bitmap.CompressFormat.JPEG, 100, streem);
         byte[] clientBytes = streem.toByteArray();
         //3.byte[] - > String
         String clientResult = Base64.encodeToString(clientBytes, Base64.DEFAULT);
    	
    	return clientResult;
    }
    /**
     * 讲bitmap转为base64字符串
     * @param clientBitmap   图片
     * op  为压缩比例
     * @return
     */
    public static  String  encodeBase64Bitmap(Bitmap  clientBitmap,int op){
    	
    	/* Bitmap clientBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.messi);*/
    	//2.bitmap->byte[]
    	ByteArrayOutputStream streem = new ByteArrayOutputStream();
    	clientBitmap.compress(Bitmap.CompressFormat.JPEG, op, streem);
    	byte[] clientBytes = streem.toByteArray();
    	//3.byte[] - > String
    	String clientResult = Base64.encodeToString(clientBytes, Base64.DEFAULT);
    	
    	return clientResult;
    }
    /**
     * 转换为base64字符串
     * @param path 图片路径
     * @return base64 string
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception{
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }
}
