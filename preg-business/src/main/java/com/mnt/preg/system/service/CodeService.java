
package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.pojo.ChinaPojo;
import com.mnt.preg.system.pojo.CodePojo;

/**
 * 代码表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-5 zcq 初版
 */
@Validated
public interface CodeService {

    /**
     * 查询代码表
     * 
     * @author zcq
     * @return
     */
    List<CodePojo> queryCode(CodeInfo condition);

    /**
     * 查询代码表
     * 
     * @author zcq
     * @return
     */
    List<CodePojo> queryCodeView(CodeInfo condition);

    /**
     * 获取代码信息--根据【主键】
     * 
     * @author zcq
     * @param codeId
     * @return
     */
    CodePojo getCode(String codeId);

    /**
     * 添加代码表信息
     * 
     * @author zcq
     * @param code
     * @return
     */
    CodePojo addCode(CodeInfo code);

    /**
     * 修改代码表信息
     * 
     * @author zcq
     * @param code
     */
    void updateCode(CodeInfo code);

    /**
     * 删除代码表信息
     * 
     * @author zcq
     * @param codeIdList
     */
    void deleteCode(List<String> codeIdList);

    /**
     * 校验代码表是否可用
     * 
     * @author zcq
     * @param condition
     * @return
     */
    Integer checkCodeValid(@Valid @NotNull CodeInfo condition);

    /**
     * 修改排序
     * 
     * @author zcq
     * @param codeIdList
     */
    void updateCodeOrder(List<String> codeIdList);

    /**
     * 查询省市信息
     * 
     * @author zcq
     * @param pId
     * @return 省市信息
     */
    List<ChinaPojo> queryChina(Integer pId, String type);

    /**
     * 
     * 根据父节点类型查询最大排序号
     * 
     * @author gss
     * @param parentCodeKind
     * @return
     */
    Integer getCodeMaxOrderByParentCodeKind(String parentCodeKind);

    /**
     * 删除代码表
     * 
     * @author zcq
     * @param codeId
     */
    void removeCode(String codeId);

    /**
     * 修改排序
     * 
     * @author zcq
     * @param codeIdList
     */
    void editCodeOrder(List<String> codeIdList);

    /**
     * 查询所有代码表信息
     * 
     * @author zcq
     * @return
     */
    List<CodePojo> queryCodeAll();
}
