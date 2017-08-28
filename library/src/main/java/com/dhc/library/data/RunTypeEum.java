package com.dhc.library.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bsw
 * @version 1.0.0
 * @Description TODO(学校办学性质枚举)
 * @Date 2017年5月10日
 */
public enum RunTypeEum {

    PUBLIC("公办", "G"),
    PRIVATE("民办", "M");

    //类型名称
    private String name;

    //类型值
    private String value;


    RunTypeEum(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据枚举Value 值获取枚举对象
     *
     * @param val
     * @return
     */

    public static RunTypeEum getRunTypeByValue(String value) {
        RunTypeEum[] arry = RunTypeEum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].getValue().equalsIgnoreCase(value)) {
                return arry[i];
            }
        }
        return null;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        RunTypeEum[] enums = RunTypeEum.values();
        StringBuilder jsonStr = new StringBuilder("[");
        for (RunTypeEum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum.getValue()).append("',name:'").append(senum.getName()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }

    public static List<Map<Object, Object>> getJson() {
        RunTypeEum[] enums = RunTypeEum.values();
        List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>(enums.length);
        for (RunTypeEum senum : enums) {
            Map<Object, Object> map = new HashMap<Object, Object>(2);
            list.add(map);
            map.put("id", senum.getValue());
            map.put("name", senum.getName());
        }
        return list;
    }

}
