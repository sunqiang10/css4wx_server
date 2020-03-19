package com.kensun.video4wx.service;
import com.kensun.video4wx.dao.CourseDao;
import com.kensun.video4wx.model.VideoCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao dao;
    @Override
    public List<VideoCourse> findAll() {
        return dao.findAll();
    }

    @Override
    public VideoCourse findOne(Long id) {
        return dao.findOne(id);
    }
}