/*
 * ItemDAOTest.java    1.0  2017-11-15
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.dao.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.entity.items.Item;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * ItemDAOTest
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-15 scd 初版
 */
public class ItemDAOTest extends BaseJunit {

    private ItemCondition condition;

    private Item item;

    private List<String> itemCodes;

    private List<String> inspectIds;

    private String insId = "1100002";

    private String maxCode = "000017";

    private List<String> itemIds;

    @Before
    public void setUp() {
        condition = new ItemCondition();

        item = new Item();
        item.setItemId("402880ea5fb82af1015fb8316e0c0000");
        item.setItemName("test");
        item.setItemCode("1100002000007");

        itemCodes = new ArrayList<String>();
        itemCodes.add("1100002000004");
        itemCodes.add("1100002000005");
        itemCodes.add("1100002000007");

        inspectIds = new ArrayList<String>();
        inspectIds.add("402880ea5fb80e76015fb80f6ae60000");
        inspectIds.add("402880ea5fb80e76015fb818fb2e0004");
        inspectIds.add("402880ea5fb80e76015fb8192fc50005");

        itemIds = new ArrayList<String>();
        itemIds.add("402880ea5fb40c0b015fb448b5a30001");
        itemIds.add("402880ea5fb40c0b015fb44e12d20002");
        itemIds.add("402880ea5fb82af1015fb8316e0c0000");

    }

    /**
     * 根据条件查询所有
     * 
     * @author scd
     * @param condition
     * @return
     */
    @Test
    public void testQueryItemByCondition() {
        List<ItemPojo> list1 = itemDAO.queryItemByCondition(condition);
        assert (list1.size() > 0);

        condition.setItemName(item.getItemName());
        List<ItemPojo> list3 = itemDAO.queryItemByCondition(condition);
        assert (list3.size() > 0);
        condition.setItemName(null);

    }

    /**
     * 根据指标编码查询指标异常
     * 
     * @author scd
     * @param itemCodeList
     * @return
     */
    @Test
    public void testQueryItemAbnomalByItemCodes() {
        List<String> list = itemDAO.queryItemAbnomalByItemCodes(itemCodes);
        assert (list.size() > 0);
    }

    /**
     * 
     * 更新体检项目字典表
     * 
     * @author scd
     * @param item
     * @param id
     *            主键
     */
    @Test
    public void testUpdateItem() {
        boolean flag = true;
        try {
            itemDAO.updateItem(item, item.getItemId());
        } catch (Exception e) {
            flag = false;
        }
        assertTrue(flag);
    }

    /**
     * 
     * 逻辑删除体检项目字典记录
     * 
     * @author scd
     * @param id
     *            主键
     */
    @Test
    public void testDeleteItem() {
        itemDAO.deleteItem(item.getItemId());
    }

    /**
     * 根据查询条件查询体检项目字典表记录
     * 
     * @author scd
     * @param condition
     *            查询条件
     * @return List<ItemPojo> 体检项目字典信息列表
     */
    @Test
    public void testQueryItem() {
        List<ItemPojo> list = itemDAO.queryItem(condition);
        assert (list.size() > 0);
    }

    /**
     * 根据查询条件查询系统体检项目字典表记录
     * 
     * @author scd
     * @param itemCode
     *            机构项目编码
     * @return ItemPojo 系统体检项目字典信息
     */
    @Test
    public void testGetItemByItemCode() {
        ItemPojo itemPojo = itemDAO.getItemByItemCode(item.getItemCode());
        assertEquals(itemPojo.getItemCode(), item.getItemCode());
    }

    /**
     * 根据查询条件查询系统体检项目字典表记录
     * 
     * @author scd
     * @param itemCode
     *            机构项目编码
     * @return ItemPojo 系统体检项目字典信息
     */
    @Test
    public void testGetItemByItemId() {
        ItemPojo itemPojo = itemDAO.getItemByItemId(item.getItemId());
        assertEquals(itemPojo.getItemId(), item.getItemId());
    }

    /**
     * 生成编码
     * 
     * @author scd
     * @return
     */
    @Test
    public void testGetMaxItemCode() {
        String code = itemDAO.getMaxItemCode(insId);
        assertEquals(code, maxCode);
    }

    /**
     * 
     * 批量删除指标
     * 
     * @author scd
     * @param itemIds
     *            主键
     */
    @Test
    public void testRemoveItems() {
        int result = itemDAO.removeItems(itemIds);
        assertEquals(result, 3);
    }

    @Test
    public void testQueryItemType() {
        itemDAO.queryItemType();
    }
}
