package com.dhc.library.data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.HashMap;

/**
 * 创建者     邓浩宸
 * 创建时间   2018/3/27 16:38
     * 描述	      room数据库帮助类
 */

public class DBHelper {
    private HashMap<String, Object> mServiceMap;
    public Context context;

    public DBHelper(Context context) {
        //Map used to store db
        mServiceMap = new HashMap<>();
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public <S extends RoomDatabase> S getApi(Class<S> serviceClass, String dbName) {
        if (mServiceMap.containsKey(dbName)) {
            return (S) mServiceMap.get(dbName);
        } else {
            Object obj = Room.databaseBuilder(context, serviceClass, dbName).build();
            mServiceMap.put(dbName, obj);
            return (S) obj;
        }
    }

}
