
package com.mnt.preg.customer.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.pojo.CustomerPojo;

/**
 * 客户DAO测试类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
public class CustomerDAOTest extends BaseJunit {

    /**
     * 条件检索客户信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @Test
    public void queryCustomer() {
        @SuppressWarnings("unchecked")
        List<CustomerPojo> customerList = (List<CustomerPojo>) customerDAO.queryCustomerForDiagnosis(null).getData();
        assertEquals(customerList.size(), 10);
    }

}
