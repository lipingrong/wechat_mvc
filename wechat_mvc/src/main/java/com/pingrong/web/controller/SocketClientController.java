package com.pingrong.web.controller;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SocketClientController {
	IO.Options options = new IO.Options();  
	static Socket socket;
	@RequestMapping("initClient")
	public void initClient() {
		try{
            options.forceNew = true;
            options.reconnection = true;
            final Socket sockets = IO.socket("http://localhost:9092?clientid=1", options);
            socket = sockets;
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("connect");
//                    socket.close();
                }
            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override 
                public void call(Object... args) {
                    System.out.println("connect timeout");
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override    
                public void call(Object... args) {
                    System.out.println("connect error");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {    
                    System.out.println("disconnect");
                }
            }).on("advert_info", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String data = (String)args[0];
                    System.out.println("服务端：************"+data.toString());
                    //给服务端发送信息
                    socket.emit("advert_info", "服务端你好，我是客户端，我有问题想咨询你！");
                    socket.emit("advert_info", "...是这样吗？");
                }
            }).on("notice_info", new Emitter.Listener(){
                @Override
                public void call(Object... args){
                    String data = (String)args[0];
                    System.out.println(data);
                }
            });
            socket.open();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	@RequestMapping("sendMsgToServlet")
	public void sendMsgToServlet() {
		socket.emit("advert_info", "哈哈，服务器有我的数据没");
	}
}
