package com.example.xiaojun.kotlin_try.data.db;

/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * greanDao注解：@Id :主键 Long型，可以通过@Id(autoincrement = true)设置自增长
 *@Property：设置一个非默认关系映射所对应的列名，默认是的使用字段名举例：@Property (nameInDb="name")
 *@NotNul：设置数据库表当前列不能为空
 *@Transient：添加次标记之后不会生成数据库表的列
 */

@Entity
public class SongInfoBean {

    @Id(autoincrement = true)
    private Long songLocalId;
    private int from;
    private String title;
    private String artist;
    private String album;
    private int duration;

    private String coverPath;
    private String lyrPath;
    private String songPath;

    private String coverLink;
    private String lyrLink;
    private String songLink;
    //服务器的歌曲数据ID
    private String songId;
    private String albumId;
    private String artistId;
    @Generated(hash = 1588137946)
    public SongInfoBean(Long songLocalId, int from, String title, String artist,
            String album, int duration, String coverPath, String lyrPath,
            String songPath, String coverLink, String lyrLink, String songLink,
            String songId, String albumId, String artistId) {
        this.songLocalId = songLocalId;
        this.from = from;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.coverPath = coverPath;
        this.lyrPath = lyrPath;
        this.songPath = songPath;
        this.coverLink = coverLink;
        this.lyrLink = lyrLink;
        this.songLink = songLink;
        this.songId = songId;
        this.albumId = albumId;
        this.artistId = artistId;
    }
    @Generated(hash = 427206265)
    public SongInfoBean() {
    }
    public Long getSongLocalId() {
        return this.songLocalId;
    }
    public void setSongLocalId(Long songLocalId) {
        this.songLocalId = songLocalId;
    }
    public int getFrom() {
        return this.from;
    }
    public void setFrom(int from) {
        this.from = from;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getAlbum() {
        return this.album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getCoverPath() {
        return this.coverPath;
    }
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
    public String getLyrPath() {
        return this.lyrPath;
    }
    public void setLyrPath(String lyrPath) {
        this.lyrPath = lyrPath;
    }
    public String getSongPath() {
        return this.songPath;
    }
    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
    public String getCoverLink() {
        return this.coverLink;
    }
    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }
    public String getLyrLink() {
        return this.lyrLink;
    }
    public void setLyrLink(String lyrLink) {
        this.lyrLink = lyrLink;
    }
    public String getSongLink() {
        return this.songLink;
    }
    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }
    public String getSongId() {
        return this.songId;
    }
    public void setSongId(String songId) {
        this.songId = songId;
    }
    public String getAlbumId() {
        return this.albumId;
    }
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
    public String getArtistId() {
        return this.artistId;
    }
    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
}

