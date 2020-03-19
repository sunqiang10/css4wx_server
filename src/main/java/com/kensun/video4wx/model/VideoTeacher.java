package com.kensun.video4wx.model;

import javax.persistence.*;

@Entity
@Table(name = "video_teacher")
public class VideoTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="teacher_name")
    private String teacherName;

    @Column(name="teacher_nickname")
    private String teacherNickname;

    @Column(name="face_url")
    private String faceUrl;

    @Column(name="teacher_introduce")
    private String teacherIntroduce;

    public Long getId() {
        return id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherNickname() {
        return teacherNickname;
    }

    public void setTeacherNickname(String teacherNickname) {
        this.teacherNickname = teacherNickname;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getTeacherIntroduce() {
        return teacherIntroduce;
    }

    public void setTeacherIntroduce(String teacherIntroduce) {
        this.teacherIntroduce = teacherIntroduce;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
