/*
 * ImportDataServiceTest.java    1.0  2018年8月30日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.service.basic.importdata;


import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.master.service.basic.importdata.ImportDataService;


/**
 * 导入食材测试类
 *
 * @author zj
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月30日 zj 初版
 */
public class ImportDataServiceTest extends BaseJunit {
    
    @Autowired
    private ImportDataService importDataService;

    @Test
    @Rollback(false)
    public void testOpratorDate() throws IOException {
        
        importDataService.opratorDate();

    }

}
