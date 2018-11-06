package com.ccee.videotool.greendao;

import android.content.Context;

import com.ccee.videotool.model.dbdao.DaoMaster;
import com.ccee.videotool.model.dbdao.DaoSession;
import com.sunsh.baselibrary.utils.sp.SpUtil;


/**
 * Created by sunsh
 */

public class GreenDaoManager {
    //初始化上下文
    private Context context;
    //多线程中要被共享的使用volatile关键字修饰  GreenDao管理类
    private volatile static GreenDaoManager mInstance;
    //它里边实际上是保存数据库的对象
    private static DaoMaster mDaoMaster, localDaoMaster;
    //创建数据库的工具
    private static SLOpenHelper mHelper;
    //管理gen里生成的所有的Dao对象里边带有基本的增删改查的方法
    private static DaoSession mDaoSession, localDaoSession;


    public static void clearInstance() {
        mInstance = null;
        mDaoMaster = null;
        closeConnection();
    }

    /**
     * 单例模式获得操作数据库对象
     *
     * @return
     */
    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化上下文创建数据库的时候使用
     */
    public void init(Context context) {
        this.context = context;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            synchronized (GreenDaoManager.class) {
                if (mDaoMaster == null) {
                    mHelper = new SLOpenHelper(context, getDbName(SpUtil.getInstance().getUserId()), null);
                    mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
                }
            }

        }
        return mDaoMaster;
    }

    private String getDbName(int id) {
        return id + "CCEEVideo.db";
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            synchronized (GreenDaoManager.class) {
                if (mDaoMaster == null) {
                    mDaoMaster = getDaoMaster();
                }
                mDaoSession = mDaoMaster.newSession();
            }

        }
        return mDaoSession;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public static void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    public static void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    public static void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    /**
     * 用于操作本地数据库使用
     */
   /* private void initGreenDao()
    {
        //临时数据使用
        SQLdm s = new SQLdm();
        SQLiteDatabase database = s.openDatabase(context);
        localDaoMaster = new DaoMaster(database);
        localDaoSession = localDaoMaster.newSession();

    }

    public DaoSession getLocalDaoSession()
    {
        if (localDaoMaster == null)
        {
            initGreenDao();
        }
        return localDaoSession;
    }
*/


}
