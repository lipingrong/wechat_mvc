package com.pingrong.web.socket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/hello")
public class WebsocketTest {
	 //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。  
    private static int onlineCount = 0;  
       
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识  
    private static CopyOnWriteArraySet<WebsocketTest> webSocketSet = new CopyOnWriteArraySet<WebsocketTest>();  
    
    private static Map<String,Session> mapSocket = new Hashtable<String,Session>();
       
    //与某个客户端的连接会话，需要通过它来给客户端发送数据  
    private Session session;  
	
    public WebsocketTest(){
        System.out.println("WebsocketTest..");
    }
    /** 
     * 连接建立成功调用的方法 
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据 
     */  
    @OnOpen
    public void onopen(Session session){
        this.session = session;  
        webSocketSet.add(this);     //加入set中  
        mapSocket.put(session.getId(), session);
        addOnlineCount();           //在线数加1  
        System.out.println("连接成功");
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());  
        try {
            session.getBasicRemote().sendText("hello client...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** 
     * 连接关闭调用的方法 
     */  
    @OnClose
    public void onclose(Session session){
    	webSocketSet.remove(this);  //从set中删除  
        subOnlineCount();           //在线数减1      
        System.out.println("close....");
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());  
        
    }
    /** 
     * 收到客户端消息后调用的方法 
     * @param message 客户端发送过来的消息 
     * @param session 可选的参数 
     */ 
     @OnMessage      
    public void onsend(String msg,Session session){
            try {
				session.getBasicRemote().sendText("client"+session.getId()+"say:"+msg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            System.out.println("来自客户端的消息:" + msg);  
            int i = 0;
            //群发消息  
            for(WebsocketTest item: webSocketSet){               
                try {  
                    item.sendMessage("hello" + ++i);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    continue;  
                }  
            } 
    } 
     /** 
      * 发生错误时调用 
      * @param session 
      * @param error 
      */  
     @OnError
     public void onError(Session session, Throwable error){  
         System.out.println("发生错误");  
         error.printStackTrace();  
     } 
     /** 
      * 向客户端推送消息
      * @param message 
      * @throws IOException 
      */  
     public void sendMessage(String message) throws IOException{  
         this.session.getBasicRemote().sendText(message);  
         //this.session.getAsyncRemote().sendText(message);  
     }  
     public static synchronized int getOnlineCount() {  
         return onlineCount;  
     }  
    
     public static synchronized void addOnlineCount() {  
    	 WebsocketTest.onlineCount++;  
     }  
        
     public static synchronized void subOnlineCount() {  
    	 WebsocketTest.onlineCount--;  
     } 
}
