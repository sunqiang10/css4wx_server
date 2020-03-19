package com.kensun.video4wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.kensun.video4wx.model.ConfigBean;
import com.kensun.video4wx.model.Users;
import com.kensun.video4wx.service.UserService;
import com.kensun.video4wx.utils.CodeUtil;
import com.kensun.video4wx.utils.IMoocJSONResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.util.HashMap;
import java.util.*;
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private ConfigBean configBean;
    @Resource
    private UserService userService;
    @RequestMapping(value = "loginPage")
    public String loginPage(HttpServletRequest request) {
        return "/admin/login";
    }
    @RequestMapping("/getCode")
    public void getAuthCode(HttpServletRequest request,
                            HttpServletResponse response, HttpSession session){
        try{
            //创建文件输出流对象
            Map<String,Object> map = CodeUtil.generateCodeAndPic(configBean.getFontName());
            ImageIO.write((RenderedImage) map.get("codePic"),
                    "jpeg",
                    response.getOutputStream());
            session.setAttribute("code",map.get("code"));
            response.getOutputStream().flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping(value="login", method= RequestMethod.POST)
    @ResponseBody
    public Map login(@RequestBody Users user,HttpServletRequest request) {
        Map map = new HashMap();
        map.put("info", "登陆失败");
        map.put("ok", 0);
        try{
            String username = user.getUserName();
            String password = user.getPassWord();
            if(password==null || password.length()<=0){
                map.put("info", "密码不能为空");
                return map;
            }
            if(username!=null&& username.length()>0){
                Users findUser =  userService.queryByUserName(username);
                if(findUser!=null){
                    password = DigestUtils.md5Hex(password);
                    if(findUser.getPassWord().equals(password)){
                        request.getSession().setAttribute("ht_user",findUser.getUserName());
                        map.put("info", JSONObject.toJSONString(user));
                        map.put("ok", 1);
                        return map;
                    }else{
                        map.put("info", "用户名或者密码错误");
                        return map;
                    }

                }else{
                    map.put("info", "用户名不存在");
                }
            }else{
                map.put("info", "用户名不能为空");
                return map;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping(value="logOut", method= RequestMethod.POST)
    @ResponseBody
    public Map logOut(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("info", "退出登陆失败");
        map.put("ok", 0);
        try{
            request.getSession().setAttribute("ht_user",null);
            map.put("info", "退出登陆");
            map.put("ok", 1);
            return map;

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping(value="getUserName", method= RequestMethod.POST)
    @ResponseBody
    public Map getUserName(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("info", "获取用户名失败");
        map.put("ok", 0);
        try{
            map.put("info", request.getSession().getAttribute("ht_user").toString());
            map.put("ok", 1);
            return map;

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping(value="regist", method= RequestMethod.POST)
    @ResponseBody
    public IMoocJSONResult regist(@RequestBody Users user) throws Exception {

        //1、判断用户名和密码必须不为空
        if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassWord())) {
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }

        //2、判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUserName());

        //3、保存用户，注册信息
        if(!usernameIsExist) {
            if(StringUtils.isEmpty(user.getUserId())){
                user.setUserId(new Date().getTime()+"");
            }
            user.setNickName(user.getUserName());
            user.setPassWord(DigestUtils.md5Hex(user.getPassWord()));
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);
            user.setCreateTime(new Date());
            userService.saveUser(user);

        }else {
            return IMoocJSONResult.errorMsg("用户名已经存在，请换一个试试");
        }

        return IMoocJSONResult.ok();
    }

}

