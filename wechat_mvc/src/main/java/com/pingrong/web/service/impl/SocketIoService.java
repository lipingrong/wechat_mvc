package com.pingrong.web.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;


@Service("socketIoService")
public class SocketIoService {
	static SocketIOServer server;
    static Map<String, SocketIOClient> clientsMap = new HashMap<String, SocketIOClient>();
    
    public void startServer() throws InterruptedException{
        Configuration config = new Configuration();
        //服务器主机ip    
        config.setHostname("192.168.209.55");
        //端口
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
        //监听广告推送事件，advert_info为事件名称，自定义
        server.addEventListener("advert_info", String.class, new DataListener<String>(){
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws ClassNotFoundException {
                //客户端推送advert_info事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型
                
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1,sa.indexOf(":"));//获取客户端连接的ip
                //Map<String, List<String>> params = client.getHandshakeData().getUrlParams();//获取客户端url参数
                
                System.out.println(clientIp+"：客户端：************"+data);
            }
        });
        //监听通知事件
        server.addEventListener("notice_info", String.class, new DataListener<String>() {
            @Override    
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
            	String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1,sa.indexOf(":"));//获取客户端连接的ip
                //Map<String, List<String>> params = client.getHandshakeData().getUrlParams();//获取客户端url参数
                
                System.out.println(clientIp+"：客户端：************"+data);
            }
        });
        
        /**
         * 监听其他事件
         */
        
        //添加客户端连接事件
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
                System.out.println(clientIp+"-------------------------"+"客户端已连接");
                Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
                //获取客户端连接的clientid参数
                List<String> object = params.get("clientid");
                String clientid = "";
                if(object != null){
                	clientid = object.get(0);
                    //将uuid和连接客户端对象进行绑定
                    clientsMap.put(clientid,client);
                }
                //给客户端发送消息
                client.sendEvent("advert_info",clientIp+"客户端你好，我是服务端，有什么能帮助你的？");
            }
        });
        //添加客户端断开连接事件
        server.addDisconnectListener(new DisconnectListener(){
            @Override
            public void onDisconnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
                System.out.println(clientIp+"-------------------------"+"客户端已断开连接");
                
                Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
                //获取客户端连接的clientid参数
                List<String> object = params.get("clientid");
                String clientid = "";
                if(object != null){
                	clientid = object.get(0);
                    //将uuid和连接客户端对象进行绑定
                    clientsMap.remove(clientid);
                }
                //给客户端发送消息
                client.sendEvent("advert_info",clientIp+"客户端你好，我是服务端，期待下次和你见面");
            }
        });
          server.start();
          
        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
    public void stopServer(){
        if(server != null){
            server.stop();
            server = null;
        }
    }
    /**
     *  给所有连接客户端推送消息
     * @param eventType 推送的事件类型
     * @param message  推送的内容
     */
    public void sendMessageToAllClient(String eventType,String message){
        Collection<SocketIOClient> clients = server.getAllClients();
        for(SocketIOClient client: clients){
            client.sendEvent(eventType,message);
        }
    }
    /**
     * 给具体的客户端推送消息
     * @param deviceId 设备类型
     * @param eventType推送事件类型
     * @param message 推送的消息内容
     */
    public void sendMessageToOneClient(String clientid,String eventType,String message){
        try {
            if(clientid != null && !"".equals(clientid)){
                SocketIOClient client = (SocketIOClient)clientsMap.get(clientid);
                if(client != null){
                    client.sendEvent(eventType,message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public static SocketIOServer getServer() {
        return server;
    }
}
