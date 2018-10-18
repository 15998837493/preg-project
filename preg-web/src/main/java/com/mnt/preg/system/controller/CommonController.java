
package com.mnt.preg.system.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 公共的接口的方法
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-13 xdc 初版
 */
@Controller
public class CommonController extends BaseController {

    /**
     * common.js使用的接口，用于查询指定字段已有的内容，方便选择
     * 
     * @author xdc
     * @param tableName
     * @param fieldName
     * @return
     */
    @RequestMapping(value = SystemMapping.QUERY_DISTINCT_STRING)
    public @ResponseBody
    WebResult<List<String>> queryDistinctByFieldName(String tableName, String fieldName) {
        WebResult<List<String>> webResult = new WebResult<List<String>>();
        webResult.setData(this.queryDistinctByField(tableName, fieldName));
        return webResult;
    }

}
