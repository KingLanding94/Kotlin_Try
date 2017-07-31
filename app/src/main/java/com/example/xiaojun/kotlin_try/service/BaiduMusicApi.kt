package com.example.xiaojun.kotlin_try.service

import com.example.xiaojun.kotlin_try.data.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
interface BaiduMusicApi {

    companion object{
        val RadionFrom = "qianqian"
        val Version = "2.1.0"

        val MusicFrom = "webapp_music"
        val Format = "json"
        val MusicBase : String
            get() = "http://tingapi.ting.baidu.com/v1/restserver/"
    }

    /**
     * 关键字搜索建议提示
     * 例：method=baidu.ting.search.catalogSug&query=“海阔天空”
     * 参数：query = '' //搜索关键字
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getSearchSug(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                        @Query("query")query:String):Observable<List<SongSearchSugBean>>


    /**
     * 根据songId得到歌曲的歌词
     * 例：method=baidu.ting.song.lry&songid=877578
     * 参数：songid = 877578 //歌曲id
     */

    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getSongLrc(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                     @Query("songid") songId:String):Observable<SongBean.SongLrc>


    /**
     * 专辑详情
     * 例：method=baidu.ting.album.getAlbumInfo&album_id=67909
     * 参数：album_id
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getAlbumDetail(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                   @Query("album_id") albumId:String):Observable<MusicAlbum>

    //获取电台列表和电台内容时不可以从web_app获取，需要更换别的方式
    /**
     * 获取音乐电台列表
     *http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.radio.getCategoryList&format=json
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getRadioStations(@Query("from") from: String,@Query("version") version:String,@Query("format") format: String,
                         @Query("method") method: String): Observable<MusicRadioStationResponseBean>


    /**
     * 获取电台列表下面的所有歌曲
     * http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.radio.getChannelSong&format=json&pn=0&rn=20&channelname=public_tuijian_ktv
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getStationSongs(@Query("from") from:String,@Query("version") version:String,@Query("format") format: String,
                        @Query("method") method: String,@Query("pn") pn:Int,@Query("rn") rn:Int,
                        @Query("channelname") channelname:String) : Observable<MusicRadioSongResponseBean>


    /**
     * 获取当前的音乐榜单
     * http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&format=json&method=baidu.ting.billboard.billCategory
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getRankList(@Query("from") from:String,@Query("format") format: String,
                        @Query("method") method: String,@Query("kflag") kflag:Int) : Observable<MusicRankResponseBean>

    /**
     * 获取某一排行榜中的歌曲列表
     * http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp&method=baidu.ting.billboard.billList&format=json&type=1&offset=0&size=50
     * method=baidu.ting.billboard.billList&type=1&size=10&offset=0
     * 参数:type = 1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
     * size = 10 //返回条目数量
     * offset = 0 //获取偏移
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getRankSongList(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                        @Query("type") type:Int,@Query("size") size:Int,@Query("offset")offset:Int):Observable<MusicRankSongResponseBean>


    /**
     * 获取当前全部歌单
     * http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&format=json&method=baidu.ting.diy.gedan&page_size=40&page_no=1
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getSheetList(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                     @Query("page_size") page_size:Int,@Query("page_no") page_no:Int):Observable<MusicSheetResponseBean>


    /**
     * 获取歌单的具体内容
     * http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&format=json&method=baidu.ting.diy.gedanInfo&listid=382052154
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getSheetSongList(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                         @Query("listid") listId:Int):Observable<MusicSheetSongResponseBean>

    /**
     * 根据歌曲id获得歌词文件
     * http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&format=json&method=baidu.ting.song.lry&songid=213508 //歌词文件
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getSongLry(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                   @Query("songid") songId:Int):Observable<SongBean.SongLrc>


    /**
     * 根据歌曲id获得可播放文件
     * http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid=877578 //播放
     */
    @GET("ting")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getSongLinkInfo(@Query("from") from:String,@Query("format")format: String,@Query("method")method: String,
                   @Query("songid") songId:Int):Observable<MusicPlayResponseBean>







}