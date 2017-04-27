package com.pingrong.web.socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIo_Client {
	 public static void main(String[] args) {
	        try{
	            IO.Options options = new IO.Options();    
	            options.forceNew = true;
	            options.reconnection = true;
	            final Socket socket = IO.socket("http://103.44.145.245:22592?clientid=1", options);
	            //final Socket socket = IO.socket("http://localhost:9092?clientid=1", options);

	            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
	                @Override
	                public void call(Object... args) {
	                    System.out.println("connect");
//	                    socket.close();
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
	            
	        }
	    }
}
