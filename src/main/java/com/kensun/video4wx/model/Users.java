package com.kensun.video4wx.model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_id")
    private String userId;

    @Column(name="username")
    private String userName;

    @Column(name="password")
    private String passWord;

    @Column(name="face_image")
    private String faceImage;

    @Column(name="nickname")
    private String nickName;
    @Column(name="create_time")
    private Date createTime;

    @Column(name="fans_counts" ,columnDefinition="int default 1")
    private int fansCounts;

    @Column(name="follow_counts" ,columnDefinition="int default 1")
    private int followCounts;

    @Column(name="receive_like_counts" ,columnDefinition="int default 1")
    private int receiveLikeCounts;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(int fansCounts) {
        this.fansCounts = fansCounts;
    }

    public int getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(int followCounts) {
        this.followCounts = followCounts;
    }

    public int getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    public void setReceiveLikeCounts(int receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }
}
