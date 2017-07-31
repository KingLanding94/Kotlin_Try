package com.example.xiaojun.kotlin_try.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */
@Entity
public class SongSheetBean {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private int songNumber = 0;
    @Generated(hash = 19139307)
    public SongSheetBean(Long id, String title, int songNumber) {
        this.id = id;
        this.title = title;
        this.songNumber = songNumber;
    }
    @Generated(hash = 1896587268)
    public SongSheetBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getSongNumber() {
        return this.songNumber;
    }
    public void setSongNumber(int songNumber) {
        this.songNumber = songNumber;
    }
}
