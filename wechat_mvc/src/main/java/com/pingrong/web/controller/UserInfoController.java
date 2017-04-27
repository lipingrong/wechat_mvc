package com.pingrong.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pingrong.web.entity.UserInfo;
import com.pingrong.web.service.UserInfoService;
/**
 * 测试类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("index")
public class UserInfoController{
    private static Logger logger = Logger.getLogger(UserInfoController.class);
    @Autowired
    private UserInfoService userService;
    
    @RequestMapping(value = "/in")
    public ModelAndView inIndex() throws Exception{
        ModelAndView mv = new ModelAndView("index");
        logger.info("进入in请求");
        return mv;
    }
    @RequestMapping(value="/getUser",method=RequestMethod.GET)
    @ResponseBody
    public List<UserInfo> getUser()throws Exception{
    	logger.info("进入getUser请求");
    	return userService.getUser();
    }
    @RequestMapping(value="/testSocket",method=RequestMethod.GET)
    public ModelAndView testSocket()throws Exception{
    	logger.info("进入testSocket请求");
    	 ModelAndView mv = new ModelAndView("socket-test");
    	return mv;
    }
}
