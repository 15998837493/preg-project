
package com.mnt.preg.master.service.basic;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.entity.basic.FoodTree;
import com.mnt.preg.master.pojo.basic.TreePojo;

/**
 * 树级表基础维护
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 xdc 初版
 */
@Validated
public interface TreeService {

    /**
     * 查询树级表
     * 
     * @author xdc
     * @return
     */
    List<TreePojo> queryTreeByCondition(FoodTree condition);

    /**
     * 获取树节点
     * 
     * @author xdc
     * @param treeId
     * @return
     */
    TreePojo getTree(String treeId);

    /**
     * 验证树节点
     * 
     * @author xdc
     * @param treeId
     * @return
     */
    Integer checkTreeIdValid(String treeId, String treeKind);

    /**
     * 验证名称
     * 
     * @author xdc
     * @param treeName
     * @return
     */
    Integer checkTreeNameValid(String treeName, String treeKind);

    /**
     * 添加阶段
     * 
     * @author xdc
     * @param tree
     * @return
     */
    TreePojo addTree(@Valid @NotNull FoodTree tree);

    /**
     * 为新的节点添加数据
     * 
     * @author xdc
     * @param tree
     * @return
     */
    String addTree_help(FoodTree tree);

    /**
     * 更新节点
     * 
     * @author xdc
     * @param productCatalog
     */
    void updateTree(FoodTree productCatalog);

    /**
     * 删除节点
     * 
     * @author xdc
     * @param catalogId
     */
    void deleteTree(@NotBlank String treeId, String treeKind);

    /**
     * 编辑节点
     * 
     * @author xdc
     * @param catalogIdList
     */
    void editTreeOrder(List<String> catalogIdList);

    /**
     * 返回树状结构
     * 
     * @author xdc
     * @param condition
     * @return
     */
    List<TreePojo> getTree_returnlist(FoodTree condition);
    
    /**
     * 
     * 兄弟层级判断类别重复
     *
     * @author zj
     * @param catalogName
     * @param treeKind
     * @return
     */
    Integer checkTreeNameValidFromBrother(String catalogName, String treeKind);
    
    /**
     * 
     * 判断是否有子结点
     *
     * @author zj
     * @param treeId
     * @param treeKind
     * @return
     */
    Integer checkTreeHaveSub(String treeId, String treeKind);

}
