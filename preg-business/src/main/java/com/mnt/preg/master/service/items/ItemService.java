
package com.mnt.preg.master.service.items;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * 
 * 体检项目字典接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Validated
public interface ItemService {

    /**
     * 
     * 增加指标
     * 
     * @author gss
     * @param itemVo
     * @return 主键
     */
    String saveItem(@Valid @NotNull ItemPojo itemVo);

    /**
     * 
     * 修改指标
     * 
     * @author gss
     * @param itemVo
     * @param id
     *            主键
     */
    void updateItem(@Valid @NotNull ItemPojo itemVo, @NotBlank String id);

    /**
     * 
     * 删除指标
     * 
     * @author gss
     * @param id
     */
    void removeItem(@NotBlank String id);

    /**
     * 
     * 条件检索指标
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    public List<ItemPojo> queryItem(ItemCondition condition);

    /**
     * 
     * 根据主键查询指标
     * 
     * @author gss
     * @param id
     *            主键
     */
    ItemPojo getItemByItemId(@NotBlank String ItemId);

    /**
     * 
     * 根据编码获取指标
     * 
     * @author gss
     * @param itemCode
     *            系统项目编码
     */
    public ItemPojo getItemByItemCode(@NotBlank String itemCode);

    /**
     * 检索所有指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ItemPojo> queryItemByCondition(ItemCondition condition);

    /**
     * 验证指标编码
     * 
     * @author xdc
     * @param itemCode
     * @return
     */
    Integer checkItemCode(@NotBlank String itemCode);

    /**
     * 根据指标查询异常
     * 
     * @author zcq
     * @param itemCodeList
     * @return
     */
    String getItemAbnomalsByItemCodes(List<String> itemCodeList);

    /**
     * 校验指标名称是否存在
     * 
     * @author zcq
     * @param itemCodeList
     * @return
     */
    Integer checkItemName(@NotBlank String itemName, String itemType, String itemClassify);

    /**
     * 自动生成编码
     * 
     * @author zcq
     * @param itemCodeList
     * @return
     */
    String getMaxItemCode(String insId);

    /**
     * 查询检验项目类别
     * 
     * @author xdc
     * @return
     */
    List<ItemPojo> queryItemType();

    /**
     * 指标排序
     * 
     * @author xdc
     * @param itemType
     * @param order
     */
    void editItemOrder(String itemIds);

    /**
     * 更新指标类别
     * 
     * @author xdc
     */
    void updateItemType(String oldType, String newType, String type, String pType);

    /**
     * 根据类别移除指标
     * 
     * @author xdc
     */
    void removeItemByType(String type, String itemType, String itemClassify);
}
