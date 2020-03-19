package com.kensun.video4wx.service;
import com.kensun.video4wx.model.Users;
/**
 * 微信 用户 业务 接口
 *
 * @author KenSun
 * @since 2017年10月5日 上午11:53:33
 **/
public interface UserService {
    /**
     * 判断用户名是否存在
     * @param userName
     * @return
     */
    public boolean queryUsernameIsExist(String userName);

    /**
     * 保存用户(用户注册)
     * @param user
     */
    public void saveUser(Users user);

    public Users queryByUserName(String username);

    Users queryByUserId(String userId);
}