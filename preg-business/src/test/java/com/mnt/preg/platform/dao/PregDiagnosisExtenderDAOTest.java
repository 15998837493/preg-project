
package com.mnt.preg.platform.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;

/**
 * 诊疗补充剂测试类
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.0 2017-2-18 dhs 初版
 */

public class PregDiagnosisExtenderDAOTest extends BaseJunit {

    /**
     * 营养处方--检索补充剂信息
     * 
     * @author dhs
     * @param planId
     * @return
     */
    @Test
    public void queryDiagnosisPrescriptionByDiagnosisId() {
        List<DiagnosisPrescriptionPojo> list = pregDiagnosisExtenderDao
                .queryDiagnosisPrescriptionByDiagnosisId("402880f45fe7d05c015fe7d8b5490000");
        assertEquals(list.size(), 0);
    }

}
