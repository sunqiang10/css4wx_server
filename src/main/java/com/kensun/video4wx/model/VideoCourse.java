package com.kensun.video4wx.model;

import javax.persistence.*;

@Entity
@Table(name = "video_course")
public class VideoCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private String userId;

    @ManyToOne()
    @JoinColumn(name = "teacher_id")
    private VideoTeacher videoTeacher;

    @Column(name = "course_name")
    private String courseName;

    @Column(columnDefinition="TEXT")
    private String content;

    @Column(name="cover_path")
    private String coverPath;
    @Column(name="course_price",columnDefinition="double default 0")
    private double coursePrice;

    public double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

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

    public VideoTeacher getVideoTeacher() {
        return videoTeacher;
    }

    public void setVideoTeacher(VideoTeacher videoTeacher) {
        this.videoTeacher = videoTeacher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
