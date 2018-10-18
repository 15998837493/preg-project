/*
 * TreeDAOTest.java    1.0  2018年8月30日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.dao.basic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mnt.preg.BaseJunit;


/**
 * [关于类内容的描述]
 *
 * @author zj
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月30日 zj 初版
 */
public class TreeDAOTest extends BaseJunit {
    
    @Autowired
    private TreeDAO treeDAO;

    @Test
    public void testQueryTreeId() {
        String treeName = "蚕豆";
        String treeid = treeDAO.queryTreeId(treeName, "", "");
        assertEquals(treeid, "005001");
    }

}
