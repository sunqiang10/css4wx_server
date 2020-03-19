package com.kensun.video4wx.dao;

import com.kensun.video4wx.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends JpaSpecificationExecutor<Users>, JpaRepository<Users, Long> {
}