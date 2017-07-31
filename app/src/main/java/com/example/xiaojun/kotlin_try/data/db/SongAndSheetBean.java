package com.example.xiaojun.kotlin_try.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */
@Entity
public class SongAndSheetBean {
    @Id
    private Long Id;
    private Long songId;
    private Long sheetId;
    @Generated(hash = 744616005)
    public SongAndSheetBean(Long Id, Long songId, Long sheetId) {
        this.Id = Id;
        this.songId = songId;
        this.sheetId = sheetId;
    }
    @Generated(hash = 808919539)
    public SongAndSheetBean() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public Long getSongId() {
        return this.songId;
    }
    public void setSongId(Long songId) {
        this.songId = songId;
    }
    public Long getSheetId() {
        return this.sheetId;
    }
    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }
}
