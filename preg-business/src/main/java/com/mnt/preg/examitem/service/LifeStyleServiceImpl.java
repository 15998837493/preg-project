
package com.mnt.preg.examitem.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.examitem.dao.LifeStyleDAO;
import com.mnt.preg.examitem.entity.DietaryFrequencyItem;
import com.mnt.preg.examitem.entity.LifeStyleDetail;
import com.mnt.preg.examitem.entity.LifeStyleItem;
import com.mnt.preg.examitem.entity.LifeStyleRecord;
import com.mnt.preg.examitem.pojo.LifeStyleRecoedPojo;
import com.mnt.preg.main.service.BaseService;

@Service
public class LifeStyleServiceImpl extends BaseService implements LifeStyleService {

    @Resource
    private LifeStyleDAO lifeStyleDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addLifeStyleRecord(LifeStyleRecord lifeStyleRecord) {
        return (String) lifeStyleDAO.save(lifeStyleRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public LifeStyleRecoedPojo getLifeStyleRecoed(String recordId) {
        return lifeStyleDAO.getTransformerBean(recordId, LifeStyleRecord.class, LifeStyleRecoedPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveLifeStyleRecoedDetail(String recordId, List<LifeStyleDetail> lifeStyleDetails,
            List<LifeStyleItem> lifeStyleItems) {

        // 保存答题记录
        for (LifeStyleDetail lifeStyleDetail : lifeStyleDetails) {
            lifeStyleDetail.setRecordId(recordId);
            lifeStyleDAO.save(lifeStyleDetail);
        }

        // 保存分析结果
        for (LifeStyleItem lifeStyleItem : lifeStyleItems) {
            lifeStyleDAO.save(lifeStyleItem);
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveDietaryRecoedDetail(String recordId, List<LifeStyleDetail> lifeStyleDetails,
            List<DietaryFrequencyItem> lifeStyleItems) {
        // 保存答题记录
        for (LifeStyleDetail lifeStyleDetail : lifeStyleDetails) {
            lifeStyleDetail.setRecordId(recordId);
            lifeStyleDAO.save(lifeStyleDetail);
        }

        // 保存分析结果
        for (DietaryFrequencyItem lifeStyleItem : lifeStyleItems) {
            lifeStyleDAO.save(lifeStyleItem);
        }
    }
}
