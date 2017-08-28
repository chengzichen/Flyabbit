package com.dhc.library.data.dao;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.dhc.library.data.bean.Image;

public class ImageDao extends BaseDao<Image> {

    public ImageDao(OrmLiteSqliteOpenHelper sqliteOpenHelper) {
        super(sqliteOpenHelper);
    }
}
