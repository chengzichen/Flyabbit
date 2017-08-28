package com.dhc.library.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bsw
 * @version 1.0.0
 * @Description TODO(学校类型枚举)
 * @Date 2017年5月10日
 */
public enum SchoolTypeEum {

    PRIMARYSCHOOL("小学", "P"),
    MIDDLESCHOOL("初中", "M"),
    PANDMSCHOOL("小学加初中", "P,M");

    //类型名称
    private String name;

    //类型值
    private String value;


    SchoolTypeEum(String name, String value) {
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
     * @param value
     * @return
     */

    public static SchoolTypeEum getSchoolTypeByValue(String value) {
        SchoolTypeEum[] arry = SchoolTypeEum.values();
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
        SchoolTypeEum[] enums = SchoolTypeEum.values();
        StringBuilder jsonStr = new StringBuilder("[");
        for (SchoolTypeEum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum.getValue()).append("',name:'").append(senum.getName()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }

    public static List<Map<Object, Object>> getJson() {
        SchoolTypeEum[] enums = SchoolTypeEum.values();
        List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>(enums.length);
        for (SchoolTypeEum senum : enums) {
            Map<Object, Object> map = new HashMap<Object, Object>(2);
            list.add(map);
            map.put("id", senum.getValue());
            map.put("name", senum.getName());
        }
        return list;
    }

}
