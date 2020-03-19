package com.kensun.video4wx.dao;

import com.kensun.video4wx.model.BuyCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface BuyCourseDao extends JpaSpecificationExecutor<BuyCourse>, JpaRepository<BuyCourse, Long> {
}