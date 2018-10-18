
package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.dao.PrintDAO;
import com.mnt.preg.system.pojo.PrintPojo;

/**
 * 打印选项事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-26 zcq 初版
 */
@Service
public class PrintServiceImpl implements PrintService {

    @Resource
    private PrintDAO printDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PrintPojo> queryPrint(PrintCondition condition) {
        return printDAO.queryPrint(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<PrintPojo>> getPrintListMap() {
        List<PrintPojo> list = printDAO.queryPrint(null);
        Map<String, List<PrintPojo>> map = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (PrintPojo print : list) {
                String category = print.getPrintCategory();
                if (!map.containsKey(category)) {
                    map.put(category, new ArrayList<PrintPojo>());
                }
                map.get(category).add(print);
            }
        }
        return map;
    }
}
