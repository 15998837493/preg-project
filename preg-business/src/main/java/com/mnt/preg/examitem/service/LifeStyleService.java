
package com.mnt.preg.examitem.service;

import java.util.List;

import com.mnt.preg.examitem.entity.DietaryFrequencyItem;
import com.mnt.preg.examitem.entity.LifeStyleDetail;
import com.mnt.preg.examitem.entity.LifeStyleItem;
import com.mnt.preg.examitem.entity.LifeStyleRecord;
import com.mnt.preg.examitem.pojo.LifeStyleRecoedPojo;

/**
 * 
 * 膳食及生活方式评估
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-6 mnt_zhangjing 初版
 */
public interface LifeStyleService {

    /**
     * 
     * 添加膳食及生活方式评估记录
     * 
     * @author mnt_zhangjing
     * @param lifeStyleRecord
     * @return
     */
    String addLifeStyleRecord(LifeStyleRecord lifeStyleRecord);

    /**
     * 
     * 主键查询记录总表信息
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleRecoedVo
     */
    LifeStyleRecoedPojo getLifeStyleRecoed(String recordId);

    /**
     * 
     * 保存答题记录和分析结果
     * 
     * @author mnt_zhangjing
     * @param recordId
     *            记录主键
     * @param lifeStyleDetails
     *            答题记录
     * @param lifeStyleItems
     *            分析结果
     */
    void saveLifeStyleRecoedDetail(String recordId, List<LifeStyleDetail> lifeStyleDetails,
            List<LifeStyleItem> lifeStyleItems);

    /**
     * 保存答题结果和分析结果
     * 
     * @author xdc
     * @param recordId
     * @param lifeStyleDetails
     * @param lifeStyleItems
     */
    void saveDietaryRecoedDetail(String recordId, List<LifeStyleDetail> lifeStyleDetails,
            List<DietaryFrequencyItem> lifeStyleItems);
}
