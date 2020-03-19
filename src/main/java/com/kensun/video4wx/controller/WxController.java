package com.kensun.video4wx.controller;

import com.kensun.video4wx.dao.CourseDao;
import com.kensun.video4wx.dao.UserDao;
import com.kensun.video4wx.dao.VideosDao;
import com.kensun.video4wx.model.*;
import com.kensun.video4wx.service.BuyCourseService;
import com.kensun.video4wx.service.CourseService;
import com.kensun.video4wx.service.UserService;
import com.kensun.video4wx.service.VideosService;
import com.kensun.video4wx.utils.VideoEncodeUtil;
import com.kensun.video4wx.utils.WeiXinUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.*;

@Controller
@RequestMapping(value = "/wx")
public class WxController {
    protected Logger log = Logger.getLogger(WxController.class);
    @Resource
    private ConfigBean configBean;
    @Resource
    private VideosService videosService;
    @Resource
    private CourseService courseService;
    @Resource
    private UserService userServivce;
    @Resource
    private BuyCourseService buyCourseService;
    @RequestMapping(value="getVidoesList")
    @ResponseBody
    public Map getVidoesList(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("info", "获取列表失败！");
        map.put("ok", 0);
        try{
           List<Videos> videos=  videosService.findAll();
            map.put("info", videos);
            map.put("ok", 1);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return map;
    }
    @RequestMapping(value="getVidoesById")
    @ResponseBody
    public Map getVidoesById(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("info", "获取视频失败！");
        map.put("ok", 0);
        try{
            String id = request.getParameter("id");
            if(!StringUtils.isEmpty(id)){
                Videos videos=  videosService.findOne(new Long(id));
                map.put("info", videos);
                map.put("ok", 1);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return map;
    }
    @RequestMapping(value="getVidoesListByCourseId")
    @ResponseBody
    public Map getVidoesListByCourseID(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("info", "获取列表失败！");
        map.put("ok", 0);
        try{
            String id = request.getParameter("id");
            String openId = request.getParameter("openId");
            if(!StringUtils.isEmpty(id)) {
                VideoCourse vc =  courseService.findOne(new Long(id));
                List<Videos> videos = videosService.findByVideoCourse(vc);
                List<BuyCourse> buyCourseList = null;
                if(!StringUtils.isEmpty(openId)){
                    buyCourseList =  buyCourseService.queryByBuyUserId(openId);
                }
                map.put("buyCourseList", buyCourseList);
                map.put("info", videos);
                map.put("ok", 1);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return map;
    }
    @RequestMapping(value="getVidoesListByVideosID")
    @ResponseBody
    public Map getVidoesListByVideosID(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("info", "获取列表失败！");
        map.put("ok", 0);
        try{
            String id = request.getParameter("id");
            if(!StringUtils.isEmpty(id)) {
                Videos videos = videosService.findOne(new Long(id));
                List<BuyCourse> buyCourseList = null;
                map.put("info", videos);
                map.put("ok", 1);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return map;
    }
    /**
     * 获取OPENID
     */
    @RequestMapping("/wechat")
    @ResponseBody
    public Map wechat(HttpServletRequest request){
        Map map = new HashMap();
        map.put("info", "获取用户出错！");
        map.put("ok", 0);
        try{
            String appId = "";
            String appSecret = "";
            String code = request.getParameter("code");
            String url ="https://api.weixin.qq.com/sns/jscode2session";
            String param = "appid="+ appId + "&secret=" + appSecret + "&grant_type=authorization_code&js_code=" + code;
            String resp = WeiXinUtil.sendGet(url,param);
            map.put("info", resp);
            map.put("ok", 1);
        }catch (Exception e){
            log.error(e.toString());
        }
        return map;
    }

    @RequestMapping("/buyCourse")
    @ResponseBody
    public Map buyCourse(HttpServletRequest request){
        Map map = new HashMap();
        map.put("info", "订阅课程失败！");
        map.put("ok", 0);
        try{
            String openId = request.getParameter("openId");
            String courseId = request.getParameter("courseId");
            String videosIds = request.getParameter("videosIds");
            String nickName = request.getParameter("nickName");
            String avatarUrl = request.getParameter("avatarUrl");
            if(StringUtils.isEmpty(openId) || "undefined".equals(openId)){
                map.put("info", "订阅课程失败，用户未登录！");
                return map;
            }
            if(StringUtils.isEmpty(courseId) && StringUtils.isEmpty(videosIds)){
                map.put("info", "订阅课程失败，课程ID错误！");
                return map;
            }
            Users users = userServivce.queryByUserId(openId);
            if(users==null){
                users = new Users();
                users.setUserId(openId);
                Date d = new Date();
                users.setCreateTime(d);
                users.setUserName(WeiXinUtil.getUUID());
                users.setPassWord(DigestUtils.md5Hex(d.getTime()+""));
                users.setNickName(nickName);
                users.setFaceImage(avatarUrl);
                userServivce.saveUser(users);
            }
            int buyType = 0;
            if(!StringUtils.isEmpty(videosIds)){
                String[] videosIdArray = videosIds.split(",");
                for (int i = 0; i < videosIdArray.length; i++) {
                    Videos videos = videosService.findOne(new Long(videosIdArray[i]));
                    BuyCourse buyCourse =  buyCourseService.queryByBuyVideosIdAndUserId(videos.getId(),openId);
                    if(buyCourse==null){
                        BuyCourse bc = new BuyCourse();
                        bc.setBuyType(buyType);
                        bc.setCreateTime(new Date());
                        bc.setUserId(openId);
                        bc.setVideoCourse(videos.getVideoCourse());
                        bc.setVideosId(videos.getId());
                        buyCourseService.save(bc);
                    }
                }
            }else{
                buyType = 1;
                VideoCourse videoCourse = courseService.findOne(new Long(courseId));
                List<Videos> videosList = videosService.findByVideoCourse(videoCourse);
                for (int i = 0; i < videosList.size(); i++) {
                    BuyCourse buyCourse =  buyCourseService.queryByBuyVideosIdAndUserId(videosList.get(i).getId(),openId);
                    if(buyCourse==null) {
                        BuyCourse bc = new BuyCourse();
                        bc.setBuyType(buyType);
                        bc.setCreateTime(new Date());
                        bc.setUserId(openId);
                        bc.setVideoCourse(videosList.get(i).getVideoCourse());
                        bc.setVideosId(videosList.get(i).getId());
                        buyCourseService.save(bc);
                    }
                }
            }
            List<BuyCourse> buyCourseList =  buyCourseService.queryByBuyUserId(openId);
            map.put("info", buyCourseList);
            map.put("ok", 1);
        }catch (Exception e){
            log.error(e.toString());
        }
        return map;
    }
    @RequestMapping("/getBuyCourse")
    @ResponseBody
    public Map getBuyCourse(HttpServletRequest request){
        Map map = new HashMap();
        map.put("info", "查询订阅失败！");
        map.put("ok", 0);
        try{
            String openId = request.getParameter("openId");
            if(StringUtils.isEmpty(openId)){
                map.put("info", "查询订阅失败，用户未登录！");
                return map;
            }
            List<BuyCourse> buyCourseList =  buyCourseService.queryByBuyUserId(openId);
            map.put("info", buyCourseList);
            map.put("ok", 1);
        }catch (Exception e){
            log.error(e.toString());
        }
        return map;
    }
}