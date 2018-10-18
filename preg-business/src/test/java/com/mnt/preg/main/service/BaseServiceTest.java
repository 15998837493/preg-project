
package com.mnt.preg.main.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.entity.Customer;

public class BaseServiceTest extends BaseJunit {

    @Test
    public void testValidName() {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("custName", "张传强");
        int count = primaryKeyDao.validName(conditionMap, Customer.class);
        assertEquals(count, 1);
    }

}
