package com.kensun.video4wx.service;
import com.kensun.video4wx.model.VideoCourse;
import java.util.List;

/**
 * 微信 用户 业务 接口
 *
 * @author KenSun
 * @since 2017年10月5日 上午11:53:33
 **/
public interface CourseService {
    List<VideoCourse> findAll();

    VideoCourse findOne(Long aLong);
}