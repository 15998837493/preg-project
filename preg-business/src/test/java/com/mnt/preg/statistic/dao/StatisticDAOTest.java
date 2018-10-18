
package com.mnt.preg.statistic.dao;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.statistic.condition.StatisticForm;
import com.mnt.preg.statistic.pojo.StatisticCustomerPojo;

public class StatisticDAOTest extends BaseJunit {

    @Test
    public void testQueryCustomerList() {
        StatisticForm condition = new StatisticForm();
        // 必填项
        condition.setPregPeriod("2");
        condition.setStartDate("2017-01-01");
        condition.setEndDate("2018-08-01");
        // 选填项
        // condition.setUserSelect("10012,10011,10000");
        // condition.setBirthSeize("协和医院");// TODO:
        // condition.setBirthChild("协和医院");// TODO:
        // condition.setHeightFrom("150");
        // condition.setHeightTo("170");
        // condition.setAgeFrom("20");
        // condition.setAgeTo("42");
        // condition.setBmiScope("18.5-32");
        // condition.setBirthNum("单胎,双胎,三胎及以上");
        // condition.setDiseaseHistory("11000022018011200237,11000022018011200238");
        // condition.setFamilyHistory("11000022018011200237,11000022018011200238");// TODO:
        // condition.setPregComplications("210100700000240,210100700000241");
        // condition.setPregTimes("1,2,3,4");
        // condition.setBirthTimes("0,1,2,3");
        // condition.setBadPregHistory(
        // "402880ef5a91a2b1015a91e5f7c50027,402880b35eb6f014015eb72059cf0015,402880ef5a91a2b1015a91e66c450028");
        // condition.setBadBirthHistory("210100700000232,210100700000234,210100700000236,210100700000238");
        // condition.setNormalInspectItemIds("8a50537d-d44f-11e7-adf8-000c29724006");
        // condition.setAbnormalInspectItemIds("8a4df1e4-d44f-11e7-adf8-000c29724006");
        // condition.setWeightCondition("体重增长过快,体重增长过缓,正常");
        // condition.setMuscleReduce("1");
        // condition.setProteinReduce("1");
        // condition.setFuzhongCondition("轻微,浮肿");
        // condition.setXiangweiFrom("1");
        // condition.setXiangweiTo("100");
        // condition.setDeseaseIds("6e7f3d0715d311e7b6a100163e000c97,6e79577615d311e7b6a100163e000c97");
        // condition.setMenzhenNumFrom("1");
        // condition.setMenzhenNumTo("10");
        // condition.setMenzhenPregWeekFrom("12");
        // condition.setMenzhenPregWeekTo("36");
        // condition.setFirstLevel("绿色,黄色,橙色,红色,紫色");
        // condition.setLastLevel("绿色,黄色,橙色,红色,紫色");
        //
        // condition.setBirthPregWeekFrom("39");
        // condition.setBirthPregWeekTo("42");
        // condition.setChildbirthType("自然分娩,吸引,产钳,臀助产,剖宫产,其他");
        // condition.setBirthPlace("LOA");
        // condition.setMazuiType("局部麻醉,全身麻醉");
        // condition.setIsDangerPregWoman("是");
        // condition.setIsInspectBeforeBirth("是");
        // condition.setComplicationIds("");// TODO:
        // condition.setIsLiveOrDead("存活");
        // condition.setWhenDead("2018-05-01");
        //
        // condition.setNewBirthSex("男");
        // condition.setWeightFrom("1000");
        // condition.setWeightTo("4000");
        // condition.setBirthDefect("无");
        // condition.setIsRescue("否");
        // condition.setChildComplicationIds("");// TODO:
        // condition.setAshiOneMinuteFrom("1");
        // condition.setAshiOneMinuteTo("10");
        // condition.setAshiFiveMinuteFrom("1");
        // condition.setAshiFiveMinuteTo("10");
        // condition.setAshiTenMinuteFrom("1");
        // condition.setAshiTenMinuteTo("10");
        // condition.setIsChildLiveOrDead("存活");
        // condition.setAfv("中");
        // condition.setAfluid("清澈");

        List<StatisticCustomerPojo> customerList = statisticDAO.queryCustomerList(condition);
        System.out.println(customerList.size());
    }

}
