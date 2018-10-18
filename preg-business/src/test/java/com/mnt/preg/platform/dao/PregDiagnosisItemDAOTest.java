
package com.mnt.preg.platform.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;

/**
 * 诊疗检测项目测试类
 * 
 * @author dhs
 * @version 1.3.0
 * 
 */
public class PregDiagnosisItemDAOTest extends BaseJunit {

    /**
     * 查询接诊--检测项目
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @Test
    public void queryDiagnosisItem() {
        DiagnosisItemsCondition itemCondition = new DiagnosisItemsCondition();
        itemCondition.setDiagnosisId("402880b35f716486015f71a26ad80079");
        List<PregDiagnosisItemsVo> diagnosisItemList = pregDiagnosisItemDAO.queryDiagnosisItem(itemCondition);
        assertEquals(diagnosisItemList.size(), 5);
    }
}
