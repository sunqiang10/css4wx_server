package com.kensun.video4wx.model;

import javax.persistence.*;

@Entity
@Table(name = "videos")
public class Videos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private String userId;

    @Column(name="vedio_id")
    private String vedioId;

    @Column(name="video_path")
    private String videoPath;

    @Column(name="video_title")
    private String videoTitle;

    @Column(name="video_desc")
    private String videoDesc;

    @ManyToOne()
    @JoinColumn(name = "teacher_id")
    private VideoTeacher videoTeacher;

    @Column(name="video_seconds",columnDefinition="int default 1")
    private int videoSeconds;

    @Column(name="cover_path")
    private String coverPath;

    @Column(name="like_counts")
    private String likeCounts;

    @Column(name="status")
    private String status;

    @Column(name="create_time")
    private String createTime;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private VideoCourse videoCourse;			//外键关联 课程

    @Column(name="video_price",columnDefinition="double default 0")
    private double videoPrice;

    public VideoTeacher getVideoTeacher() {
        return videoTeacher;
    }

    public void setVideoTeacher(VideoTeacher videoTeacher) {
        this.videoTeacher = videoTeacher;
    }

    public VideoCourse getVideoCourse() {
        return videoCourse;
    }

    public void setVideoCourse(VideoCourse videoCourse) {
        this.videoCourse = videoCourse;
    }

    public double getVideoPrice() {
        return videoPrice;
    }

    public void setVideoPrice(double videoPrice) {
        this.videoPrice = videoPrice;
    }

    public Long getId() {
        return id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
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

    public String getVedioId() {
        return vedioId;
    }

    public void setVedioId(String vedioId) {
        this.vedioId = vedioId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public int getVideoSeconds() {
        return videoSeconds;
    }

    public void setVideoSeconds(int videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(String likeCounts) {
        this.likeCounts = likeCounts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
