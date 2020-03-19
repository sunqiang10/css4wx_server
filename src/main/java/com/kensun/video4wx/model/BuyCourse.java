package com.kensun.video4wx.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "buy_course")
public class BuyCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private String userId;

    @Column(name = "videos_id")
    private long videosId;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private VideoCourse videoCourse;			//外键关联 课程

    @Column(name="buy_type")
    private int buyType;

    @Column(name="create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getVideosId() {
        return videosId;
    }

    public void setVideosId(long videosId) {
        this.videosId = videosId;
    }

    public VideoCourse getVideoCourse() {
        return videoCourse;
    }

    public void setVideoCourse(VideoCourse videoCourse) {
        this.videoCourse = videoCourse;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
