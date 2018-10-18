
package com.mnt.preg.master.service.preg;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.items.IntervenePointsCondition;
import com.mnt.preg.master.entity.preg.PregInterveneDiseasePoints;
import com.mnt.preg.master.entity.preg.PregIntervenePoints;
import com.mnt.preg.master.pojo.preg.PregIntervenePointsPojo;

/**
 * 
 * 健康要点表接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Validated
public interface PregIntervenePointsService {

    /**
     * 
     * 健康要点记录查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>健康要点记录查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    List<PregIntervenePointsPojo> queryIntervenePoints(IntervenePointsCondition condition);

    /**
     * 
     * 增加健康要点记录
     * 
     * @author gss
     * @param intervenePoints
     *            疾病要点
     * @param interveneDiseasePoints
     *            疾病与要点关系
     * @return IntervenePointsVo
     */
    PregIntervenePointsPojo addIntervenePoints(@Valid @NotNull PregIntervenePoints intervenePoints,
            List<PregInterveneDiseasePoints> interveneDiseasePointses);

    /**
     * 
     * 修改健康要点记录
     * 
     * @author gss
     * @param intervenePoints
     * @param PregIntervenePointsVo
     */
    PregIntervenePointsPojo updateIntervenePoints(PregIntervenePoints intervenePoints,
            List<PregInterveneDiseasePoints> interveneDiseasePointses);

    /**
     * 
     * 根据主键删除健康要点记录
     * 
     * @author gss
     * @param id
     */
    void removeIntervenePoints(@NotBlank String id);

    /**
     * 
     * 根据主键查询健康要点表
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>根据主键查询健康要点表</dd>
     * </dl>
     * 
     * @author gss
     * @param id
     *            主键
     */
    PregIntervenePointsPojo getIntervenePoints(@NotBlank String id);

}
