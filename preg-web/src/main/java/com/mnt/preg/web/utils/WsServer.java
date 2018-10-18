
package com.mnt.preg.web.utils;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * websocket服务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-4 zcq 初版
 */
public class WsServer extends WebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsServer.class);

    public WsServer(int port) {
        super(new InetSocketAddress(port));
    }

    public WsServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // ws连接的时候触发的代码，onOpen中我们不做任何操作

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // 断开连接时候触发代码
        userLeave(conn);
        LOGGER.info("websocket服务断开连接！");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // LOGGER.info("websocket服务，发送消息：【" + message + "】");
        if (null != message && message.startsWith("online")) {
            String userName = message.replaceFirst("online", "");// 用户名
            userJoin(conn, userName);// 用户加入
        } else if (null != message && message.startsWith("offline")) {
            userLeave(conn);
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (ex instanceof java.net.BindException) {
            LOGGER.error("websocket服务出错，端口号被占用，请更换端口号！");
        } else {
            LOGGER.error("websocket服务出错！ " + ex.getMessage());
        }
        ex.printStackTrace();
    }

    /**
     * 去除掉失效的websocket链接
     * 
     * @author zcq
     * @param conn
     */
    private void userLeave(WebSocket conn) {
        WsPool.removeUser(conn);
    }

    /**
     * 将websocket加入用户池
     * 
     * @author zcq
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn, String userName) {
        WsPool.addUser(userName, conn);
    }

    public static void main(String args[]) {
        WebSocketImpl.DEBUG = false;
        int port = 8090; // 端口
        WsServer s = new WsServer(port);
        s.start();
    }

}
