
package com.mnt.preg.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.pojo.PrintPojo;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 打印选项
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-26 zcq 初版
 */
@Controller
public class PrintController extends BaseController {

    /**
     * 检索打印选项
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.PRINT_MAPLIST_GET)
    public @ResponseBody
    WebResult<Map<String, List<PrintPojo>>> getPrintListMap(PrintCondition condition) {
        WebResult<Map<String, List<PrintPojo>>> webResult = new WebResult<Map<String, List<PrintPojo>>>();
        webResult.setSuc(printService.getPrintListMap());
        return webResult;
    }

}
