package com.kensun.video4wx.service;

import com.kensun.video4wx.model.BuyCourse;
import com.kensun.video4wx.model.VideoCourse;
import com.kensun.video4wx.model.Videos;

import java.util.List;

public interface BuyCourseService {
    List<BuyCourse> findAll();
    void save(BuyCourse buyCourse);
    BuyCourse queryByBuyVideosIdAndUserId(final long videosId,String userId);
    List<BuyCourse> queryByBuyUserId(String openId);
}