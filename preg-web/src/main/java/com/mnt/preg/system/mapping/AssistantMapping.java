
package com.mnt.preg.system.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 
 * 用户请求映射
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：
 *          v1.5 2018-04-08 dhs 初版
 */
@FrontCache(space = "Assistant")
public class AssistantMapping {

    /** 添加助理 */
    public static final String USER_ADD_ASSISSTANT = "/page/user/assistant/user_add_assisstant.action";

    /** 添加医生 */
    public static final String USER_ADD_DOCTOR = "/page/user/assistant/user_add_doctor.action";

    /** 查询 */
    public static final String USER_QUERY = "/page/user/assistant/user_query.action";

    /** 查询 医师/助理 */
    public static final String USER_ASSISTANT_QUERY = "/page/user/assistant/user_assistant_query.action";
}
