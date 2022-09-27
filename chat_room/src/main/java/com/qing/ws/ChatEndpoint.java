package com.qing.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qing.pojo.Message;
import com.qing.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lianggq
 * @date 2022/9/22 15:50
 */
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
@Slf4j
public class ChatEndpoint {

    // 用来存储每一个用户登陆时产生的客户端对象
    private static Map<String, ChatEndpoint> onlineUsers = new HashMap<String, ChatEndpoint>();

    // websocket-session
    private Session session;

    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        // 存储对象
        onlineUsers.put((String) httpSession.getAttribute("user"), this);

        //上线通知
        broadcastAllUser();
    }

    public void broadcastAllUser() {
        try {
            String message = MessageUtil.getMessage(true, null, onlineUsers.keySet());
            log.info("==========>系统消息："+ message);
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                ChatEndpoint endpoint = onlineUsers.get(name);
                endpoint.session.getBasicRemote().sendText(message);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(String message, Session session){
        try {
            Message msg = new ObjectMapper().readValue(message, Message.class);
            String toName = msg.getToName();
            String data = msg.getMessage();
            String username = (String) httpSession.getAttribute("user");
            String messageResult = MessageUtil.getMessage(false, username, data);
            log.info("============> 用户消息："+ messageResult);
            onlineUsers.get(toName).session.getBasicRemote().sendText(messageResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClose
    public void onClose(Session session) {

    }

}
