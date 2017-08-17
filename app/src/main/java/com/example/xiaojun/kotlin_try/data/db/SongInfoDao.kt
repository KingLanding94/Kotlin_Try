package com.example.xiaojun.kotlin_try.data.db

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import com.example.xiaojun.kotlin_try.util.Config
import android.content.ContentValues
import com.example.xiaojun.kotlin_try.util.App


/**
 * Created by XiaoJun on 2017/7/29.
 * Version 1.0.0
 */

//原本打算用greendao，最后还是手写了，后期应该会把这些改掉，先把功能实现吧

class SongInfoDao {

    init {

    }

    var db:SQLiteDatabase? = null
    private fun openOrCreateDb(name:String = Config.DBNAME){
        db = SQLiteDatabase.openOrCreateDatabase( App.getContext().filesDir.absolutePath +"/"+name,null);//null表示用默认的cursor工厂
    }

    private fun closeDb(){
        db?.close()
    }


    fun createSongTable(table: String) {
        openOrCreateDb()
        db?.execSQL("CREATE TABLE IF NOT EXISTS " + table+" (" + //

                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: songLocalId

                "\"FROM\" INTEGER NOT NULL ," + // 1: from

                "\"TITLE\" TEXT," + // 2: title

                "\"ARTIST\" TEXT," + // 3: artist

                "\"ALBUM\" TEXT," + // 4: album

                "\"DURATION\" INTEGER NOT NULL ," + // 5: duration

                "\"COVER_PATH\" TEXT," + // 6: coverPath

                "\"LYR_PATH\" TEXT," + // 7: lyrPath

                "\"SONG_PATH\" TEXT," + // 8: songPath

                "\"COVER_LINK\" TEXT," + // 9: coverLink

                "\"LYR_LINK\" TEXT," + // 10: lyrLink

                "\"SONG_LINK\" TEXT," + // 11: songLink

                "\"SONG_ID\" TEXT," + // 12: songId

                "\"ALBUM_ID\" TEXT," + // 13: albumId

                "\"ARTIST_ID\" TEXT);") // 14: artistId
        closeDb()
    }

    fun getSongFromTable(table:String):ArrayList<SongInfoBean>{
        openOrCreateDb()
        val list = ArrayList<SongInfoBean>()

        @SuppressLint("Recycle")
        val cursor = db?.query(true, table, arrayOf("_id", "FROM", "TITLE", "ARTIST",
                "ALBUM", "DURATION", "COVER_PATH", "LYR_PATH",
                "SONG_PATH","COVER_LINK","LYR_LINK" ,"SONG_LINK","SONG_ID","ALBUM_ID","ARTIST_ID"), null, null, null, null, null, null) ?: return list

        for (i in 0..cursor.count - 1) {
            cursor.moveToNext()
            val song = SongInfoBean()
            var count = 0
            song.songLocalId = cursor.getInt(count++).toLong()
            song.from = cursor.getInt(count++)
            song.title = cursor.getString(count++)
            song.artist = cursor.getString(count++)
            song.album = cursor.getString(count++)
            song.duration = cursor.getInt(count++)
            song.coverPath = cursor.getString(count++)
            song.lyrPath = cursor.getString(count++)
            song.songPath = cursor.getString(count++)
            song.coverLink = cursor.getString(count++)
            song.lyrLink = cursor.getString(count++)
            song.songLink = cursor.getString(count++)
            song.songId = cursor.getString(count++)
            song.albumId = cursor.getString(count++)
            song.artistId = cursor.getString(count)
            list.add(song)
        }
        cursor.close()
        closeDb()
        return list
    }

    //总是出现没有创建表错误
    fun getSheetSize(table:String):Int{
        openOrCreateDb()
        val cursor = db?.query(true, table, arrayOf("_id"), null, null, null, null, null, null)
        if (cursor == null){
            closeDb()
            return 0
        }
        val num = cursor.count
        cursor.close()
        closeDb()
        return num
    }

    fun clearTable(table:String){
        openOrCreateDb()
        db?.execSQL("delete from $table")
        closeDb()
    }

    fun insertSongListToTable(list: ArrayList<SongInfoBean>,table: String){
        openOrCreateDb()
        for (song in list){
            val values = ContentValues()
            values.put("_id", song.songLocalId)
            values.put("FROM", song.from)
            values.put("TITLE", song.title)
            values.put("ARTIST", song.artist)
            values.put("ALBUM", song.album)
            values.put("DURATION", song.duration)
            values.put("COVER_PATH", song.coverPath)
            values.put("LYR_PATH", song.lyrPath)
            values.put("SONG_PATH", song.songPath)
            values.put("COVER_LINK", song.coverLink)
            values.put("LYR_LINK", song.lyrLink)
            values.put("SONG_LINK", song.songLink)
            values.put("SONG_ID", song.songId)
            values.put("ALBUM_ID", song.albumId)
            values.put("ARTIST_ID", song.artist)
            db?.insert(table, null, values)
        }
        closeDb()
    }
    fun insertSongIntoTable(song:SongInfoBean,table:String){
        openOrCreateDb()
        val values = ContentValues()
        values.put("_id", song.songLocalId)
        values.put("FROM", song.from)
        values.put("TITLE", song.title)
        values.put("ARTIST", song.artist)
        values.put("ALBUM", song.album)
        values.put("DURATION", song.duration)
        values.put("COVER_PATH", song.coverPath)
        values.put("LYR_PATH", song.lyrPath)
        values.put("SONG_PATH", song.songPath)
        values.put("COVER_LINK", song.coverLink)
        values.put("LYR_LINK", song.lyrLink)
        values.put("SONG_LINK", song.songLink)
        values.put("SONG_ID", song.songId)
        values.put("ALBUM_ID", song.albumId)
        values.put("ARTIST_ID", song.artist)
        db?.insert(table, null, values)
        closeDb()
    }

    fun deleteSongFromTable(song: SongInfoBean,table:String){
        openOrCreateDb()
        if (song.songLocalId != null){
            return deleteSongById(song.songLocalId.toInt(),table)
        }else{
            val title = song.title
            val artist = song.artist
            val album = song.album
            val sql = "DELETE FROM $table WHERE TITLE = '$title' AND ARTIST = '$artist' AND ALBUM = '$album'"
            db?.execSQL(sql)
        }
        closeDb()
    }

    fun deleteSongById(id:Int,table:String){
        openOrCreateDb()
        val sql = "DELETE FROM $table WHERE _id = $id"
        db?.execSQL(sql)
        closeDb()
    }

}