package com.ccee.videotool.greendao;

import com.ccee.videotool.model.dbdao.DBVideoDao;

import org.greenrobot.greendao.AbstractDao;

/**
 * 注册 表dao
 */
public enum GreenTable {
    PRESENT(DBVideoDao.class);




    private Class<? extends AbstractDao<?, ?>> aClass;

    public Class<? extends AbstractDao<?, ?>> getaClass() {
        return aClass;
    }

    GreenTable(Class<? extends AbstractDao<?, ?>> aClass) {
        this.aClass = aClass;
    }
}
