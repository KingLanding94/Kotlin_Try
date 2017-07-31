package com.example.xiaojun.kotlin_try.util

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.xiaojun.kotlin_try.data.db.DaoMaster
import com.example.xiaojun.kotlin_try.data.db.DaoSession


/**
 * Created by XiaoJun on 2017/7/24.
 * Version 1.0.0
 */

/**
 * 这个类的目的是为了获得全局的
 */
class App:Application() {


    private var mHelper: DaoMaster.DevOpenHelper? = null
    private var db: SQLiteDatabase? = null
    private var mDaoMaster: DaoMaster? = null
    private var mDaoSession: DaoSession? = null

    companion object {
        var mContext:Context? = null
        var instance : App? = null

        fun getContext():Context{
            return mContext!!
        }

        fun getApplication(): App {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        instance = this
        setDatabase()
    }

    /**     * 设置greenDao     */

    private fun setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = DaoMaster.DevOpenHelper(this,"notes-db", null);
        db = mHelper?.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = DaoMaster(db);
        mDaoSession = mDaoMaster?.newSession();
    }


    fun getDaoSession():DaoSession{
        return mDaoSession!!;
    }

    fun getDb():SQLiteDatabase{
        return db!!
    }

}
