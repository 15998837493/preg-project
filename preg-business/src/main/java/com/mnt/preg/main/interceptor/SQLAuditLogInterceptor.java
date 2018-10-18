
package com.mnt.preg.main.interceptor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import com.mnt.health.core.logs.IAuditLog;
import com.mnt.health.utils.thread.TokenManager;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.service.LoginService;

/**
 * 数据库日志拦截器主要针对数据库的增、删、改操作记录日志
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 下午1:18:41 zy 初版
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SQLAuditLogInterceptor extends EmptyInterceptor {

    @Resource
    protected LoginService loginService;

    private static final long serialVersionUID = 2723788204258441665L;

    Session session;

    private Set inserts = new HashSet();

    // private Set updates = new HashSet();
    // private Set deletes = new HashSet();

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String onPrepareStatement(String sql) {
        return super.onPrepareStatement(sql);
    }

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {

        String token = TokenManager.getCurrHashMap().get(Thread.currentThread());

        if (entity instanceof IAuditLog) {
            inserts.add(entity);
        }
        // 无需token的操作-如果token为空则设置默认值
        if (StringUtils.isEmpty(token)) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("createInsId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = "C000000";
                }
                if ("createUserId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = "0";
                }
                if ("updateUserId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = "0";
                }
                if ("flag".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = 1;
                }
            }
            return true;
        }
        LoginUser loginVo = (LoginUser) loginService.getLogin(token);
        // 超时或token已不存在
        if (loginVo == null) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("createInsId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = "C000000";
                }
                if ("createUserId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = "0";
                }
                if ("updateUserId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = "0";
                }
                if ("flag".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = 1;
                }
            }
            return true;
        } else {
            // 后台用户
            for (int i = 0; i < propertyNames.length; i++) {
                if ("createInsId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = loginVo.getUserPojo().getCreateInsId();
                }
                if ("createUserId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = loginVo.getUserPojo().getUserId();
                }
                if ("updateUserId".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = loginVo.getUserPojo().getUserId();
                }
                if ("flag".equals(propertyNames[i]) && null == state[i]) {
                    state[i] = 1;
                }
            }
            return true;
        }
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) throws CallbackException {
        return false;
    }

    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        // if (entity instanceof IAuditLog) {
        // deletes.add(entity);
        // }
    }

    // called before commit into database
    public void preFlush(Iterator iterator) {
    }

    // called after committed into database
    public void postFlush(Iterator iterator) {
        /*
         * try {
         * String token = TokenManager.getCurrHashMap().get(Thread.currentThread());
         * for (Iterator it = inserts.iterator(); it.hasNext();) {
         * IAuditLog entity = (IAuditLog) it.next();
         * LogUtil.LogAuditIt("Saved", entity, token);
         * }
         * for (Iterator it = updates.iterator(); it.hasNext();) {
         * IAuditLog entity = (IAuditLog) it.next();
         * LogUtil.LogAuditIt("Updated", entity, token);
         * }
         * for (Iterator it = deletes.iterator(); it.hasNext();) {
         * IAuditLog entity = (IAuditLog) it.next();
         * LogUtil.LogAuditIt("Deleted", entity, token);
         * }
         * } finally {
         * inserts.clear();
         * updates.clear();
         * deletes.clear();
         * }
         */
    }

}
