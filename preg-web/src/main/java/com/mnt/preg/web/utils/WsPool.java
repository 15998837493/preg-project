
package com.mnt.preg.web.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.java_websocket.WebSocket;

import com.mnt.health.utils.json.NetJsonUtils;

/**
 * websocket
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-4 zcq 初版
 */
public class WsPool {

    private static final Map<WebSocket, String> wsUserMap = new HashMap<WebSocket, String>();

    /**
     * 通过websocket连接获取其对应的用户
     * 
     * @author zcq
     * @param conn
     * @return
     */
    public static String getUserByWs(WebSocket conn) {
        return wsUserMap.get(conn);
    }

    /**
     * 根据userName获取WebSocket,这是一个list,此处取第一个
     * 因为有可能多个websocket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的websocket连接去除了）
     * 
     * @author zcq
     * @param userName
     * @return
     */
    public static Set<WebSocket> getWsByUser(String userName) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        Set<WebSocket> resultSet = new HashSet<WebSocket>();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String cuser = wsUserMap.get(conn);
                if (cuser.equals(userName)) {
                    resultSet.add(conn);
                }
            }
        }
        return resultSet;
    }

    /**
     * 向连接池中添加连接
     * 
     * @author zcq
     * @param userName
     * @param conn
     */
    public static void addUser(String userName, WebSocket conn) {
        wsUserMap.put(conn, userName); // 添加连接
    }

    /**
     * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
     * 
     * @author zcq
     * @return
     */
    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<String>();
        Collection<String> setUser = wsUserMap.values();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }

    /**
     * 移除连接池中的连接
     * 
     * @author zcq
     * @param conn
     * @return
     */
    public static boolean removeUser(WebSocket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据
     * 
     * @author zcq
     * @param conn
     * @param message
     */
    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn && null != wsUserMap.get(conn)) {
            conn.send(message);
        }
    }

    /**
     * 向特定用户发送数据
     * 
     * @author zcq
     * @param userName
     * @param message
     */
    public static void sendMessageToUser(String userName, String message) {
        if (StringUtils.isNotBlank(userName)) {
            Set<WebSocket> keySet = WsPool.getWsByUser(userName);
            if (!keySet.isEmpty()) {
                synchronized (keySet) {
                    for (WebSocket conn : keySet) {
                        if (conn != null) {
                            conn.send(message);
                        }
                    }
                }
            } else {
                System.out.println("websocket服务，用户：【" + userName + "】为空！");
            }
        }
    }

    /**
     * 向特定用户发送数据
     * 
     * @author zcq
     * @param userNameList
     * @param message
     */
    public static void sendMessageToUsers(List<String> userNameList, String message) {
        if (CollectionUtils.isNotEmpty(userNameList)) {
            for (String userName : userNameList) {
                WsPool.sendMessageToUser(userName, message);
            }
        }
    }

    /**
     * 向所有的用户发送消息
     * 
     * @author zcq
     * @param message
     */
    public static void sendMessageToAll(String message) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }

    /**
     * 返回json数据
     * 
     * @author zcq
     * @param message
     * @param type
     * @return
     */
    public static String getMessageJson(String message, String type) {
        WsMessage wsm = new WsMessage(message, type);
        return NetJsonUtils.objectToJson(wsm);
    }
}
