
package com.mnt.preg.customer.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.condition.CustomerCondition;
import com.mnt.preg.customer.pojo.CustomerPojo;

/**
 * 患者管理Service测试类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-10 zcq 初版
 */
public class CustomerServiceTest extends BaseJunit {

    /**
     * 条件检索客户信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @Test
    public void queryCustomer() {
        CustomerCondition condition = new CustomerCondition();
        List<CustomerPojo> customerList = customerDAO.queryCustomer(condition);
        assertEquals(customerList.size(), 35);
    }
}
