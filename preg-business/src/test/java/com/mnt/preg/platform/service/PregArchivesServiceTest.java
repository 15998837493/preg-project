
package com.mnt.preg.platform.service;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.entity.PregArchives;

public class PregArchivesServiceTest extends BaseJunit {

    /**
     * 获取最后一次建档信息
     * 
     * @author xdc
     */
    @Test
    public void testGetLastPregnancyArchive() {
        archivesService.getLastPregnancyArchive("402880f45f9ff012015f9ff4f1120004");
    }

    /**
     * 删除建档信息
     * 
     * @author xdc
     */
    @Test
    public void testDeleteAndSavePregnancyArchives() {
        PregArchives arch = new PregArchives();
        arch.setId("0e98c7d5-e4ef-4428-9bb0-451b0f476e35");
        archivesService.deleteAndSavePregnancyArchives(arch);
    }
}
