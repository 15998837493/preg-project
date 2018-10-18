/*
 * TestService.java    1.0  2016-8-16
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mnt.preg.system.pojo.UserPojo;
import com.mnt.preg.system.service.UserService;

/**
 * [关于类内容的描述]
 * 
 * @author Administrator
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-8-16 Administrator 初版
 */
public class TestService {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        String[] configLocations = {"classpath*:test-application-service.xml"};
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);

        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");
        UserPojo user = userService.getUserByUserCode("admin");

        System.out.println(user.getCreateInsId());

//        FoodService foodService = (FoodService) applicationContext.getBean("foodServiceImpl");
//        List<FoodPojo> list = foodService.queryFood(null);
//        System.out.println(list.get(0).getFoodAlias());
    }

}
