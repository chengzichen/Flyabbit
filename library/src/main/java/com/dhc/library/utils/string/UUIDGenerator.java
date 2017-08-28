package com.dhc.library.utils.string;

import java.util.UUID;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/11/25 14:43
 * 描述	      ${生成UUID}
 */
public class UUIDGenerator {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return str+","+temp;
    }
    //获得指定数量的UUID
    public static String[] getUUID(int number) {
        if (number < 10||number>40) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

}
