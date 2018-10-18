/**
 * 
 */

package com.mnt.preg.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnt.preg.web.mapping.LoginMapping;

/**
 * 
 * 登陆页
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-2-3 zy 初版
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = LoginMapping.DLYM)
    public String dlym() {
        return "/login";
    }

}
