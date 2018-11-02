package com.ccee.videotool.greendao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.ccee.videotool.model.dbdao.DaoMaster;
import com.sunsh.baselibrary.utils.LogUtil;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/**
 * Created by sunsh on 2018/7/18.
 */

public class SLOpenHelper extends DaoMaster.OpenHelper {


    public SLOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        LogUtil.e("SLOpenHelper---version:" + oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion < newVersion) {
            GreenTable[] values = GreenTable.values();
            Class<? extends AbstractDao<?, ?>>[] classes = new Class[values.length];
            for (int i = 0; i < values.length; i++) {
                classes[i] = values[i].getaClass();
            }
            MigrationHelper.getInstance().migrate(db, classes);
            LogUtil.e("SLOpenHelper-----更新完成");
        }
    }
}
