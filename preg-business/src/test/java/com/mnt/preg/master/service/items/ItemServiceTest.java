/*
 * ItemServiceTest.java    1.0  2017-11-15
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.service.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.BaseJunit;
import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.entity.items.Item;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * ItemServiceTest
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-15 scd 初版
 */

public class ItemServiceTest extends BaseJunit {

    private ItemCondition condition;

    private Item item;

    private List<String> itemCodes;

    private List<String> inspectIds;

    private List<String> itemIds;

    @Before
    public void setUp() {
        Date date = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDate.format(date);

        condition = new ItemCondition();

        item = new Item();
        item.setItemId("402880ea5fb82af1015fb8316e0c0000");
        item.setItemName("test");
        item.setItemCode("1100002000007");
        item.setUpdateTime(date);
        item.setCreateTime(date);
        item.setCreateUserId("10000");
        item.setUpdateUserId("10000");
        item.setCreateInsId("1100002");

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
     * 
     * 增加体检项目字典记录
     * 
     * @author gss
     * @param itemVo
     * @return 主键
     */
    @Test
    public void testSaveItem() {
        boolean flag = true;
        try {
            ItemPojo itemPojo = TransformerUtils.transformerProperties(ItemPojo.class, item);
            itemService.saveItem(itemPojo);
        } catch (Exception e) {
            flag = false;
        }
        assertTrue(flag);
    }

    /**
     * 
     * 修改体检项目字典记录
     * 
     * @author gss
     * @param itemVo
     * @param id
     *            主键
     */
    @Test
    public void testUpdateItem() {
        boolean flag = true;
        ItemPojo itemPojo = TransformerUtils.transformerProperties(ItemPojo.class, item);
        try {
            itemService.updateItem(itemPojo, item.getItemId());
        } catch (Exception e) {
            flag = false;
        }
        assertTrue(flag);
    }

    /**
     * 
     * 根据主键删除体检项目字典记录
     * 
     * @author gss
     * @param id
     */
    @Test
    public void testRemoveItem() {
        boolean flag = true;
        try {
            itemService.removeItem(item.getItemId());
        } catch (Exception e) {
            flag = false;
        }
        assertTrue(flag);
    }

    /**
     * 
     * 体检项目字典表查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>体检项目字典表查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    @Test
    public void testQueryItem() {
        List<ItemPojo> list1 = itemService.queryItem(condition);
        assert (list1.size() > 0);

        List<ItemPojo> list2 = itemService.queryItem(condition);
        assert (list2.size() > 0);

        condition.setItemName(item.getItemName());
        List<ItemPojo> list3 = itemService.queryItem(condition);
        assert (list3.size() > 0);
        condition.setItemName(null);
    }

    /**
     * 
     * 根据主键查询体检项目字典表
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>根据主键查询体检项目字典表</dd>
     * </dl>
     * 
     * @author gss
     * @param id
     *            主键
     */
    @Test
    public void testGetItemByItemId() {
        ItemPojo itemPojo = itemService.getItemByItemId(item.getItemId());
        assertEquals(itemPojo.getItemId(), item.getItemId());
    }

    /**
     * 
     * 根据itemCode查询系统项目基本信息
     * 
     * @author gss
     * @param itemCode
     *            系统项目编码
     */
    @Test
    public void testGetItemByItemCode() {
        ItemPojo itemPojo = itemService.getItemByItemCode(item.getItemCode());
        assertEquals(itemPojo.getItemCode(), item.getItemCode());
    }

    /**
     * 检索所有指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @Test
    public void testQueryItemByCondition() {
        List<ItemPojo> list1 = itemService.queryItemByCondition(condition);
        assert (list1.size() > 0);

        List<ItemPojo> list2 = itemService.queryItemByCondition(condition);
        assert (list2.size() > 0);

        condition.setItemName(item.getItemName());
        List<ItemPojo> list3 = itemService.queryItemByCondition(condition);
        assert (list3.size() > 0);
        condition.setItemName(null);
    }

    /**
     * 验证指标编码
     * 
     * @author xdc
     * @param itemCode
     * @return
     */
    @Test
    public void testCheckItemCode() {
        Integer result = itemService.checkItemCode(item.getItemCode());
        assert (result > 0);
    }

    /**
     * 根据指标查询异常
     * 
     * @author zcq
     * @param itemCodeList
     * @return
     */
    @Test
    public void testGetItemAbnomalsByItemCodes() {
        String code = itemService.getItemAbnomalsByItemCodes(itemCodes);
        assertNotNull(code);
    }

    /**
     * 校验指标名称是否存在
     * 
     * @author zcq
     * @param itemCodeList
     * @return
     */
    @Test
    public void testCheckItemName() {
    }

    /**
     * 自动生成编码
     * 
     * @author zcq
     * @param itemCodeList
     * @return
     */
    @Test
    public void testGetMaxItemCode() {
        boolean flag = true;
        try {
            itemService.getMaxItemCode(item.getCreateInsId());
        } catch (Exception e) {
            flag = false;
        }
        assertTrue(flag);
    }

}
