
package com.mnt.preg.master.service.preg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.items.IntervenePointsCondition;
import com.mnt.preg.master.dao.items.InterveneDiseaseDAO;
import com.mnt.preg.master.dao.preg.PregIntervenePointsDAO;
import com.mnt.preg.master.entity.preg.PregInterveneDiseasePoints;
import com.mnt.preg.master.entity.preg.PregIntervenePoints;
import com.mnt.preg.master.pojo.items.InterveneDiseasePointsPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.pojo.preg.PregIntervenePointsPojo;

/**
 * 健康要点表接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Service
public class PregIntervenePointsServiceImpl extends BaseService implements PregIntervenePointsService {

    @Resource
    private PregIntervenePointsDAO pregIntervenePointsDAO;

    @Resource
    private InterveneDiseaseDAO interveneDiseaseDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PregIntervenePointsPojo> queryIntervenePoints(IntervenePointsCondition condition) {
        if (condition == null) {
            condition = new IntervenePointsCondition();
        }
        List<PregIntervenePointsPojo> intervenePointss = pregIntervenePointsDAO.queryIntervenePoints(condition);
        if (CollectionUtils.isNotEmpty(intervenePointss)) {
            for (PregIntervenePointsPojo intervenePointsVo : intervenePointss) {
                // 根据pointId查询疾病Id
                List<InterveneDiseasePointsPojo> diseasePointsVos = pregIntervenePointsDAO
                        .queryInterveneDiseasePointsByPointId(intervenePointsVo.getPointId());
                if (CollectionUtils.isNotEmpty(diseasePointsVos)) {
                    List<String> diseaseIdList = new ArrayList<String>();
                    List<String> diseaseNameList = new ArrayList<String>();
                    for (InterveneDiseasePointsPojo interveneDiseasePointsVo : diseasePointsVos) {
                        diseaseIdList.add(interveneDiseasePointsVo.getDiseaseId());
                        diseaseNameList.add(interveneDiseasePointsVo.getDiseaseName());
                    }
                    intervenePointsVo.setInterveneDiseaseIds(StringUtils.join(diseaseIdList, ","));
                    intervenePointsVo.setInterveneDiseaseNames(StringUtils.join(diseaseNameList, ","));
                }
            }
        }
        return intervenePointss;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregIntervenePointsPojo addIntervenePoints(PregIntervenePoints intervenePoints,
            List<PregInterveneDiseasePoints> interveneDiseasePointses) {
        String id = (String) pregIntervenePointsDAO.save(intervenePoints);
        if (StringUtils.isNotEmpty(id)) {
            List<String> diseaseNameList = null;
            if (CollectionUtils.isNotEmpty(interveneDiseasePointses)) {
                diseaseNameList = new ArrayList<String>();
                for (PregInterveneDiseasePoints interveneDiseasePoints : interveneDiseasePointses) {
                    interveneDiseasePoints.setPointId(id);
                    pregIntervenePointsDAO.save(interveneDiseasePoints);
                    List<InterveneDiseasePojo> interveneDiseaseVos = interveneDiseaseDAO
                            .queryInterveneDiseaseByCode(interveneDiseasePoints.getDiseaseId());
                    if (CollectionUtils.isNotEmpty(interveneDiseaseVos)) {
                        InterveneDiseasePojo interveneDiseaseVo = interveneDiseaseVos.get(0);
                        diseaseNameList.add(interveneDiseaseVo.getDiseaseName());
                    }
                }
            }
            PregIntervenePointsPojo intervenePointsVo = pregIntervenePointsDAO.getTransformerBean(id,
                    PregIntervenePoints.class,
                    PregIntervenePointsPojo.class);
            intervenePointsVo.setInterveneDiseaseIds(StringUtils.join(diseaseNameList, ","));
            return intervenePointsVo;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregIntervenePointsPojo updateIntervenePoints(PregIntervenePoints intervenePoints,
            List<PregInterveneDiseasePoints> interveneDiseasePointses) {
        if (intervenePoints == null || StringUtils.isEmpty(intervenePoints.getPointId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        pregIntervenePointsDAO.deleteInterveneDiseasePoints(intervenePoints.getPointId());

        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("point_id", HQLSymbol.EQ.toString(), intervenePoints.getPointId()));
        int count = pregIntervenePointsDAO.updateHQL(intervenePoints, updateParams);
        if (count != 1) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }

        List<String> diseaseNameList = null;
        if (CollectionUtils.isNotEmpty(interveneDiseasePointses)) {
            diseaseNameList = new ArrayList<String>();
            for (PregInterveneDiseasePoints interveneDiseasePoints : interveneDiseasePointses) {
                pregIntervenePointsDAO.save(interveneDiseasePoints);
                List<InterveneDiseasePojo> interveneDiseaseVos = interveneDiseaseDAO
                        .queryInterveneDiseaseByCode(interveneDiseasePoints.getDiseaseId());
                if (CollectionUtils.isNotEmpty(interveneDiseaseVos)) {
                    InterveneDiseasePojo interveneDiseaseVo = interveneDiseaseVos.get(0);
                    diseaseNameList.add(interveneDiseaseVo.getDiseaseName());
                }
            }
        }

        PregIntervenePointsPojo intervenePointsVo = pregIntervenePointsDAO.getTransformerBean(
                intervenePoints.getPointId(),
                PregIntervenePoints.class, PregIntervenePointsPojo.class);
        intervenePointsVo.setInterveneDiseaseIds(StringUtils.join(diseaseNameList, ","));
        return intervenePointsVo;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeIntervenePoints(String id) {
        PregIntervenePoints intervenePoints = (PregIntervenePoints) pregIntervenePointsDAO.get(
                PregIntervenePoints.class, id);
        intervenePoints.setFlag(0);
        List<PregInterveneDiseasePoints> interveneDiseasePointses = null;
        // 根据pointId查询疾病基本信息
        List<InterveneDiseasePointsPojo> diseasePointsVos = pregIntervenePointsDAO
                .queryInterveneDiseasePointsByPointId(id);
        if (CollectionUtils.isNotEmpty(diseasePointsVos)) {
            interveneDiseasePointses = new ArrayList<PregInterveneDiseasePoints>();
            PregInterveneDiseasePoints interveneDiseasePoints = null;
            for (InterveneDiseasePointsPojo interveneDiseasePointsVo : diseasePointsVos) {
                interveneDiseasePoints = TransformerUtils.transformerProperties(PregInterveneDiseasePoints.class,
                        interveneDiseasePointsVo);
                interveneDiseasePoints.setFlag(0);
                interveneDiseasePointses.add(interveneDiseasePoints);
            }
        }
        updateIntervenePoints(intervenePoints, interveneDiseasePointses);
    }

    @Override
    @Transactional(readOnly = true)
    public PregIntervenePointsPojo getIntervenePoints(String id) {
        PregIntervenePointsPojo intervenePointsVo = pregIntervenePointsDAO.getTransformerBean(id,
                PregIntervenePoints.class,
                PregIntervenePointsPojo.class);
        // 根据pointId查询疾病基本信息
        List<InterveneDiseasePointsPojo> diseasePointsVos = pregIntervenePointsDAO
                .queryInterveneDiseasePointsByPointId(intervenePointsVo.getPointId());
        if (CollectionUtils.isNotEmpty(diseasePointsVos)) {
            String interveneDiseaseIds = "";
            for (InterveneDiseasePointsPojo interveneDiseasePointsVo : diseasePointsVos) {
                if (StringUtils.isEmpty(interveneDiseasePointsVo.getDiseaseId())) {
                    continue;
                }
                interveneDiseaseIds = interveneDiseaseIds + interveneDiseasePointsVo.getDiseaseId() + ",";
            }
            if (StringUtils.isNotEmpty(interveneDiseaseIds)) {
                interveneDiseaseIds = interveneDiseaseIds.substring(0, interveneDiseaseIds.length() - 1);
            }
            intervenePointsVo.setInterveneDiseaseIds(interveneDiseaseIds);
        }
        return intervenePointsVo;
    }

}
