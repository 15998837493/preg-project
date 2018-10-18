/*
 * ImportFoodServiceTest.java    1.0  2018年9月7日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.service.basic.importdata;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.mnt.preg.BaseJunit;


/**
 * 导入菜谱测试类
 *
 * @author zj
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年9月7日 zj 初版
 */
public class ImportFoodServiceTest extends BaseJunit {
    
    @Autowired
    private ImportFoodService importFoodService;

    @Test
    @Rollback(false)
    public void testOpratorDate() throws IOException {
        importFoodService.opratorDate();
    }

}
