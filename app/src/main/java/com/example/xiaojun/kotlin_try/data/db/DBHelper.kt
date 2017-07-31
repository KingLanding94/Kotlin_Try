package com.example.xiaojun.kotlin_try.data.db

import android.util.Log
import com.example.xiaojun.kotlin_try.util.App
import com.example.xiaojun.kotlin_try.util.Constant
import org.greenrobot.greendao.annotation.Id


/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */

/**
 *  歌曲数据库里面目前只保存本地音乐，现在还没有提供本地音乐的删除方法
 */
object DBHelper {

    /**
     * 添加歌曲到指定表
     */
    val songDao = App.getApplication().getDaoSession().songInfoBeanDao
    val sheetDao = App.getApplication().getDaoSession().songSheetBeanDao
    val songSheetDao = App.getApplication().getDaoSession().songAndSheetBeanDao

    /**
     * 最近播放的条目最多为100，这个函数返回一个list列表的原因是
     */

    fun updateRecent(list: ArrayList<SongInfoBean>){
        val dao = SongInfoDao()
        dao.clearTable(Constant.RECENTPLAY)
        dao.insertSongListToTable(list,Constant.RECENTPLAY)

    }

    fun saveListToTable(list: ArrayList<SongInfoBean>, table:String){
        val dao = SongInfoDao()
        dao.insertSongListToTable(list,table)
    }

    /**
     * 根据表名获取歌曲
     */
    fun getSongListFromTable(sheet:String):ArrayList<SongInfoBean>{
        val dao = SongInfoDao()
        return dao.getSongFromTable(sheet)
    }

    /**
     * 得到表中歌曲数目
     */
    fun getTableSize(table:String):Int{
        val dao = SongInfoDao()
        return dao.getSheetSize(table)
    }


    //////////////////////////////////////////////////////////////////////////////////////
//    下面的方法暂时废弃，后期把项目改作greendao时再使用下面的方法


    /**
     * 删除歌单
     */
    fun deleteSheet(sheet:Long){
        val songAndSheetList = songSheetDao.loadAll()
        val deleteList = songAndSheetList.filter {
            it.sheetId == sheet
        }
        songSheetDao.deleteInTx(deleteList)

        val sheetBean = sheetDao.load(sheet)
        sheetDao.delete(sheetBean)
    }


    /**
     * 从指定歌单里面删除某个歌曲，现在还没有考虑本地音乐的删除
     */

    fun deleteSongFromSheet(song:SongInfoBean,sheet:Long){
        val songId = songDao.getKey(song)
        val songAndSheet = SongAndSheetBean(null,songId,sheet)
        songSheetDao.delete(songAndSheet)
        val sheetBean = sheetDao.load(sheet)
        sheetBean.songNumber --
        sheetDao.update(sheetBean)
    }

    /**
     * 添加多首歌曲到指定歌单
     */
    fun addSongListToSheet(list:ArrayList<SongInfoBean>,sheet:Long){

        if (sheet == Constant.LOCAL_SONG_SHEET_ID){
            for (i in list) songDao.insert(i)
        }
        for (i in list){
            val songId = songDao.getKey(i)
            Log.e("Db"+sheet," songId"+i.songId)
            val songAndSheet = SongAndSheetBean(null,songId,sheet)
            if (songSheetDao.getKey(songAndSheet) == null){  //不知道这样写会不会有bug
                songSheetDao.insert(songAndSheet)
            }
        }
        val sheetBean = sheetDao.load(sheet)
        if (sheetBean == null){
            Log.e("add","error")
            return
        }
        if (sheetBean.songNumber == null) sheetBean.songNumber = 0
        sheetBean.songNumber += list.size
        sheetDao.update(sheetBean)
    }

    /**
     * 添加某首歌到指定歌单里
     */
    fun addSongToSheet(song:SongInfoBean,sheet: Long){
        if(sheet == Constant.LOCAL_SONG_SHEET_ID){
            songDao.insert(song)
            Log.e("Db"+sheet," songId"+song.toString())
        }
        val songId = songDao.getKey(song)
        Log.e("Db"+sheet," songId"+song.toString())
        val songAndSheet = SongAndSheetBean(null,songId,sheet)
        if (songSheetDao.getKey(songAndSheet) == null){  //不知道这样写会不会有bug
            songSheetDao.insert(songAndSheet)
        }
        val sheetBean = sheetDao.load(sheet)
        if (sheetBean == null){
            Log.e("add","error")
            return
        }
        if (sheetBean.songNumber == null) sheetBean.songNumber = 0
        sheetBean.songNumber ++
        sheetDao.update(sheetBean)
    }

    /**
     * 新建歌单
     */
    fun addSongSheet(title:String){
        val sheet = SongSheetBean(null,title,0)
        sheetDao.insert(sheet)

        Log.e("new sheet"," id "+ sheetDao.getKey(sheet))
    }

    /**
     * 读取指定歌单的歌曲数目
     */
    fun readSheetSongNum(sheet:Long):Int{
        val sheetBean = sheetDao.load(sheet)
        if (sheetBean != null)
            return  sheetBean.songNumber
        else
            return -1
    }
}