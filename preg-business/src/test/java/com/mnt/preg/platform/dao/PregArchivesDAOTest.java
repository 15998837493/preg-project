
package com.mnt.preg.platform.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.pojo.PregArchivesPojo;

public class PregArchivesDAOTest extends BaseJunit {

    /**
     * 测试获取最后一次的建档信息
     * 
     * @author xdc
     */
    @Test
    public void testGetLastPregnancyArchive() {
        PregArchivesPojo arch = archivesDAO.getLastPregnancyArchive("402880f45f9ff012015f9ff4f1120004");
        assertNotNull(arch);
    }

    /**
     * 删除建档信息
     * 
     * @author xdc
     */
    @Test
    public void testDeletePregnancyArchives() {
        archivesDAO.deletePregnancyArchives("0e98c7d5-e4ef-4428-9bb0-451b0f476e35");
    }

}
