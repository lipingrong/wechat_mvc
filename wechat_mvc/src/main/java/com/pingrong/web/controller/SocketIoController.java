package com.pingrong.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pingrong.web.service.impl.SocketIoService;

@Controller
public class SocketIoController {
	 @Autowired
	    private SocketIoService service;
	    
	    //启动socket 服务
	    @RequestMapping("startServer")
	    public void startServer(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        //Map params = request.getParameterMap();
	        try {
	            if(SocketIoService.getServer() == null){
	                new Thread(new Runnable() {
	                    @Override
	                    public void run() {
	                        try {
	                            service.startServer();
	                        } catch (InterruptedException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                }).start();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    //停止socket服务
	    @RequestMapping("stopServer")
	    public void stopServer(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        Map params = request.getParameterMap();
	        try {
	            if(SocketIoService.getServer() == null){
	                service.stopServer();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    //给指定的客户端推送消息
	    @RequestMapping("sendAdvertInfoMsg")
	    public void sendAdvertInfoMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        Map<String, String[]> params = request.getParameterMap();
	        String clientid = "1";
	        try {
	            if(!"".equals(clientid) && SocketIoService.getServer() != null){
	                service.sendMessageToOneClient(clientid, "advert_info", "推送的内容");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
