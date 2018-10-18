<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="/common/common.jsp" %>
    <title>数据统计平台</title>
    <style type="text/css">
        .tdfont {
            color: blue
        }

        #birthResultTable tbody tr td {
            width: 5.5%
        }

        .intake-sm {
            height: 30px;
            padding: 4px;
            margin: 0;
        }
    </style>
</head>
<script type="text/javascript">
    var userList = ${userList};// 医师列表

    //BMI范围
    var bmiList = ${bmiList};
    //胎数
    var birthNumList = ${birthNumList};
    //体重增长情况
    var weightConditionList = ${weightConditionList};
    //浮肿情况
    var fuzhongConditionList = ${fuzhongConditionList};
    //首次产检妊娠风险级别
    var firstLevelList = ${levelList};
    //末次产检妊娠风险级别
    var lastLevelList = ${levelList};
    //分娩方式
    var childbirthTypeList = ${childbirthTypeList};
    //麻醉
    var mazuiTypeList = ${mazuiTypeList};
    //孕次
    var pregTimesList = ${pregTimesList};
    //产次
    var birthTimesList = ${birthTimesList};
    //不良孕史
    var badPregHistoryList = ${badPregHistoryList};
    //不良产史
    var badBirthHistoryList = ${badBirthHistoryList};
    //产妇结局何时死亡
    var whenDeadList = ${whenDeadList};
    //新生儿性别
    var newBirthSexList = ${newBirthSexList};
    //羊水量
    var afvList = ${afvList};
    //羊水性状
    var afluidList = ${afluidList};
    //病史
    var diseaseHistoryList = ${diseaseHistoryList};
    //家族史
    var familyHistoryList = ${familyHistoryList};
    //妊娠并发症
    var pregComplicationsList = ${pregComplicationsList};
    //疾病列表
    var diseaseList = ${diseaseList};
    //自动补全功能使用的集合（诊断名称）
    var diseaseListData = [];
    // 遍历所有诊断疾病
    $.each(diseaseList, function (index, obj) {
        diseaseListData.push({name: obj.diseaseName, val: obj});
    });
    //所有检验项目列表
    var inspectAllList = ${inspectAllList};
    var inspectAllListData = [];
    //遍历所有检验项目
    $.each(inspectAllList, function (index, obj) {
        inspectAllListData.push({name: obj.itemName, val: obj});
    });
    //所有分娩方位列表
    var birthPlaceList = ${birthPlaceList};
    var birthPlaceListData = [];
    //遍历所有分娩方位
    $.each(birthPlaceList, function (index, obj) {
        birthPlaceListData.push({name: obj.codeName, val: obj});
    });

</script>
<script type="text/javascript" charset="utf-8"
        src="${common.basepath}/page/statistic/statistic.js"></script>
<body>
<div class="panel panel-lightblue" style="margin-top: 15px;">
    <div class="panel-heading text-left">
        <i class="fa fa-tag fa-fw"></i>
        查询条件汇总
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-condensed">
            <thead>
            <tr>
                <th class="text-center" style="width:15%">查询分类</th>
                <th class="text-center">查询条件</th>
            </tr>
            </thead>
            <tbody id="queryCondition">
            <tr style="display:none;">
                <td class="text-left" style="width:15%">基础条件</td>
                <td class="text-left">
                    <div class="form-inline">
                        <div class="form-group">
                            <!-- <label class="control-label">阶段：</label>
                            <span id="condition00_pregPeriod" class="control-label"></span> -->
                            <label id="condition00_timeType" class="control-label"></label>
                        </div>
                        <div class="form-group" style="display:none;">
                            <!-- <label class="control-label">时间段：</label> -->
                            <span id="condition00_startDate" class="control-label"></span> <span class="control-label">至</span>
                            <span id="condition00_endDate" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <!-- <label class="control-label">时间段：</label> -->
                            <span id="condition000_startDate" class="control-label"></span><span class="control-label">之后</span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <!-- <label class="control-label">时间段：</label> -->
                            <span id="condition000_endDate" class="control-label"></span><span class="control-label">之前</span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <!-- <label class="control-label">时间段：</label>
                            <span class="control-label">=</span> -->
                            <span id="condition0000_endDate" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">营养医师：</label>
                            <span id="condition00_userSelect" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">产检医院：</label>
                            <span id="condition00_birthSeize" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">分娩医院：</label>
                            <span id="condition00_birthChild" class="control-label"></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr style="display:none;">
                <td class="text-left" style="width:15%">基本信息</td>
                <td class="text-left">
                    <div class="form-inline">
                        <div class="form-group" style="display:none;">
                            <label class="control-label">身高：</label>
                            <span id="condition11_heightFrom" class="control-label"></span><span class="control-label"> cm </span><span class="control-label">至</span>
                            <span id="condition11_heightTo" class="control-label"></span><span class="control-label"> cm </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">身高：</label>
                            <span class="control-label">>=</span>
                            <span id="condition111_heightFrom" class="control-label"></span>
                            <span class="control-label"> cm </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">身高：</label>
                            <span class="control-label"><=</span>
                            <span id="condition111_heightTo" class="control-label"></span>
                            <span class="control-label"> cm </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">身高：</label>
                            <span class="control-label">=</span>
                            <span id="condition1111_heightTo" class="control-label"></span>
                            <span class="control-label"> cm </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">年龄：</label>
                            <span id="condition11_ageFrom" class="control-label"></span><span class="control-label"> 岁 </span><span class="control-label">至</span>
                            <span id="condition11_ageTo" class="control-label"></span><span class="control-label"> 岁 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">年龄：</label>
                            <span class="control-label">>=</span>
                            <span id="condition111_ageFrom" class="control-label"></span>
                            <span class="control-label"> 岁 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">年龄：</label>
                            <span class="control-label"><=</span>
                            <span id="condition111_ageTo" class="control-label"></span>
                            <span class="control-label"> 岁 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">年龄：</label>
                            <span class="control-label">=</span>
                            <span id="condition1111_ageTo" class="control-label"></span>
                            <span class="control-label"> 岁 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">BMI范围：</label>
                            <span id="condition11_bmiScope" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">胎数：</label>
                            <span id="condition11_birthNum" class="control-label"></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr style="display:none;">
                <td class="text-left" style="width:15%">孕前病史及孕育史</td>
                <td class="text-left">
                    <div class="form-inline">
                        <div class="form-group" style="display:none;">
                            <label class="control-label">病史：</label>
                            <span id="condition22_diseaseHistory" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">家族史：</label>
                            <span id="condition22_familyHistory" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">妊娠并发症：</label>
                            <span id="condition22_pregComplications" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">孕次：</label>
                            <span id="condition22_pregTimes" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">产次：</label>
                            <span id="condition22_birthTimes" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">不良孕史：</label>
                            <span id="condition22_badPregHistory" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">不良产史：</label>
                            <span id="condition22_badBirthHistory" class="control-label"></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr style="display:none;">
                <td class="text-left" style="width:15%">孕期检验检测信息</td>
                <td class="text-left">
                    <div class="form-inline">
                        <div class="form-group" style="display:none;">
                            <label class="control-label">检验项目：</label>
                            <span id="condition33_normalInspectItemIdsNames" class="control-label"></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr style="display:none;">
                <td class="text-left" style="width:15%">人体成分</td>
                <td class="text-left">
                    <div class="form-inline">
                        <div class="form-group" style="display:none;">
                            <label class="control-label">体重增长情况：</label>
                            <span id="condition44_weightCondition" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label id="condition44_proteinReduce" class="control-label"></label>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label id="condition44_muscleReduce" class="control-label"></label>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">浮肿情况：</label>
                            <span id="condition44_fuzhongCondition" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">相位角：</label>
                            <span id="condition44_xiangweiFrom" class="control-label"></span> <span class="control-label">至</span>
                            <span id="condition44_xiangweiTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">相位角：</label>
                            <span class="control-label">>=</span>
                            <span id="condition444_xiangweiFrom" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">相位角：</label>
                            <span class="control-label"><=</span>
                            <span id="condition444_xiangweiTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">相位角：</label>
                            <span class="control-label">=</span>
                            <span id="condition4444_xiangweiTo" class="control-label"></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr style="display:none;">
                <td class="text-left" style="width:15%">孕期诊疗信息</td>
                <td class="text-left">
                    <div class="form-inline">
                        <div class="form-group" style="display:none;">
                            <label class="control-label">诊断：</label>
                            <span id="condition55_diseaseIdsNames" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">营养门诊就诊次数：</label>
                            <span id="condition55_menzhenNumFrom" class="control-label"></span> <span class="control-label">至</span>
                            <span id="condition55_menzhenNumTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">营养门诊就诊次数：</label>
                            <span class="control-label">>=</span>
                            <span id="condition555_menzhenNumFrom" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">营养门诊就诊次数：</label>
                            <span class="control-label"><=</span>
                            <span id="condition555_menzhenNumTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">营养门诊就诊次数：</label>
                            <span class="control-label">=</span>
                            <span id="condition5555_menzhenNumTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">首次孕期营养门诊就诊孕周数：</label>
                            <span id="condition55_menzhenPregWeekFrom" class="control-label"></span> <span class="control-label">至</span>
                            <span id="condition55_menzhenPregWeekTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">首次孕期营养门诊就诊孕周数：</label>
                            <span class="control-label">>=</span>
                            <span id="condition555_menzhenPregWeekFrom" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">首次孕期营养门诊就诊孕周数：</label>
                            <span class="control-label"><=</span>
                            <span id="condition555_menzhenPregWeekTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">首次孕期营养门诊就诊孕周数：</label>
                            <span class="control-label">=</span>
                            <span id="condition5555_menzhenPregWeekTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">首次产检妊娠风险级别：</label>
                            <span id="condition55_firstLevel" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">末次产检妊娠风险级别：</label>
                            <span id="condition55_lastLevel" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">是否一日门诊：</label>
                            <span id="condition55_isOneday" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">是否MDT门诊：</label>
                            <span id="condition55_isMDT" class="control-label"></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr id="birthInfo" style="display:none;">
                <td class="text-left" style="width:15%">分娩信息</td>
                <td class="text-left">
                    <div class="form-inline">
                        <div class="form-group" style="display:none;">
                            <label class="control-label">分娩时孕周数：</label>
                            <span id="condition66_birthPregWeekFrom" class="control-label"></span> <span class="control-label">至</span>
                            <span id="condition66_birthPregWeekTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">分娩时孕周数：</label>
                            <span class="control-label">>=</span>
                            <span id="condition666_birthPregWeekFrom" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">分娩时孕周数：</label>
                            <span class="control-label"><=</span>
                            <span id="condition666_birthPregWeekTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">分娩时孕周数：</label>
                            <span class="control-label">=</span>
                            <span id="condition6666_birthPregWeekTo" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">分娩方式：</label>
                            <span id="condition66_childbirthType" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">分娩方位：</label>
                            <span id="condition66_birthPlace" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">麻醉类型：</label>
                            <span id="condition66_mazuiType" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">是否危重产妇：</label>
                            <span id="condition66_isDangerPregWoman" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">是否产前检查：</label>
                            <span id="condition66_isInspectBeforeBirth" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">产妇并发症：</label>
                            <span id="condition66_complicationIdsNames" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">产妇结局：</label>
                            <span id="condition66_whenDead" class="control-label"></span>
                            <span id="condition66_isLiveOrDead" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿性别：</label>
                            <span id="condition66_newBirthSex" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿体重：</label>
                            <span id="condition66_weightFrom" class="control-label"></span><span class="control-label"> g </span><span class="control-label">至</span>
                            <span id="condition66_weightTo" class="control-label"></span><span class="control-label"> g </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿体重：</label>
                            <span class="control-label">>=</span>
                            <span id="condition666_weightFrom" class="control-label"></span>
                            <span class="control-label"> g </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿体重：</label>
                            <span class="control-label"><=</span>
                            <span id="condition666_weightTo" class="control-label"></span>
                            <span class="control-label"> g </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿体重：</label>
                            <span class="control-label">=</span>
                            <span id="condition6666_weightTo" class="control-label"></span>
                            <span class="control-label"> g </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿：</label>
                            <span id="condition66_birthDefect" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿是否抢救：</label>
                            <span id="condition66_isRescue" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿并发症：</label>
                            <span id="condition66_childComplicationIdsNames" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 1分钟：</label>
                            <span id="condition66_ashiOneMinuteFrom" class="control-label"></span><span class="control-label"> 分 </span><span class="control-label">至</span>
                            <span id="condition66_ashiOneMinuteTo" class="control-label"></span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 1分钟：</label>
                            <span class="control-label">>=</span>
                            <span id="condition666_ashiOneMinuteFrom" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 1分钟：</label>
                            <span class="control-label"><=</span>
                            <span id="condition666_ashiOneMinuteTo" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 1分钟：</label>
                            <span class="control-label">=</span>
                            <span id="condition6666_ashiOneMinuteTo" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 5分钟：</label>
                            <span id="condition66_ashiFiveMinuteFrom" class="control-label"></span><span class="control-label"> 分 </span><span class="control-label">至</span>
                            <span id="condition66_ashiFiveMinuteTo" class="control-label"></span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 5分钟：</label>
                            <span class="control-label">>=</span>
                            <span id="condition666_ashiFiveMinuteFrom" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 5分钟：</label>
                            <span class="control-label"><=</span>
                            <span id="condition666_ashiFiveMinuteTo" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 5分钟：</label>
                            <span class="control-label">=</span>
                            <span id="condition6666_ashiFiveMinuteTo" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 10分钟：</label>
                            <span id="condition66_ashiTenMinuteFrom" class="control-label"></span><span class="control-label"> 分 </span><span class="control-label">至</span>
                            <span id="condition66_ashiTenMinuteTo" class="control-label"></span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 10分钟：</label>
                            <span class="control-label">>=</span>
                            <span id="condition666_ashiTenMinuteFrom" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 10分钟：</label>
                            <span class="control-label"><=</span>
                            <span id="condition666_ashiTenMinuteTo" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">阿氏评分 10分钟：</label>
                            <span class="control-label">=</span>
                            <span id="condition6666_ashiTenMinuteTo" class="control-label"></span>
                            </span><span class="control-label"> 分 </span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">新生儿是否死亡：</label>
                            <span id="condition66_isChildLiveOrDead" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">羊水量：</label>
                            <span id="condition66_afv" class="control-label"></span>
                        </div>
                        <div class="form-group" style="display:none;">
                            <label class="control-label">羊水性状：</label>
                            <span id="condition66_afluid" class="control-label"></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr id="queryButton">
                <td class="text-right" colspan="2">
                    <button class="btn btn-primary" type="button" onclick="queryForm()"><i class="fa fa-search fa-fw"></i>查询</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<form id="queryForm" action="${common.basepath}/${applicationScope.URL.Statistic.STATISTIC_QUERY}">
    <div class="panel panel-lightblue" style="margin-top: 10px;">
        <div class="panel-heading text-left">
            <i class="fa fa-tag fa-fw"></i>
            查询条件
        </div>
        <div style="padding: 3px;">
            <div class="form-inline">
                <select id="condition0_pregPeriod" class="form-control input-sm" name="pregPeriod" onchange="onPregPeriodChange('condition0_pregPeriod')">
                    <option value="">请选择阶段</option>
                    <option value="1">妊娠期</option>
                    <option value="2">产后</option>
                </select>
                <select id="condition0_timeType" name="timeType" class="form-control input-sm" disabled>
                    <option value="">请选择日期分类</option>
                    <option value="1">预产期</option>
                    <option value="2">分娩时间</option>
                </select>
                <div class="input-group">
                    <input id="condition0_startDate" class="form-control form_date input-sm" name="startDate" placeholder="请选择开始日期" readonly type="text"/>
                    <span class="input-group-btn"> <button class="btn btn-primary input-sm" style="padding:4px;" type="button" onclick="common.dateShow('condition0_startDate')"> <i
                            class="fa fa-calendar fa-fw"></i>选择 </button> </span>
                </div>
                至
                <div class="input-group">
                    <input id="condition0_endDate" class="form-control form_date input-sm" name="endDate" placeholder="请选择结束日期" readonly type="text"/>
                    <span class="input-group-btn"> <button class="btn btn-primary input-sm" style="padding:4px;" type="button" onclick="common.dateShow('condition0_endDate')"> <i
                            class="fa fa-calendar fa-fw"></i>选择 </button> </span>
                </div>
                <select id="condition0_userSelect" class="form-control" name="userSelect" multiple="multiple" style="height: 34px; width: 210px;"></select>
            </div>
            <div class="form-inline" style="padding: 8px 8px 8px 3px;">
                <div class="form-group">
                    <label class="control-label">产检医院</label>
                    <label class="radio-inline" style="margin-left: 10px;" onmousedown="onHospitalClick('condition0_birthSeize','1','condition0_birthSeizeMy')">
                        <input id="condition0_birthSeizeMy" name="birthSeize" value="1" type="radio" onclick="return false;"/>本院
                    </label>
                    <label class="radio-inline" style="margin-left: 10px;" onmousedown="onHospitalClick('condition0_birthSeize','2','condition0_birthSeizeOther')">
                        <input id="condition0_birthSeizeOther" name="birthSeize" value="2" type="radio" onclick="return false;"/>其他医院
                    </label>
                </div>
                <div id="condition0_childbirthHospital" class="form-group" style="display: none;">
                    <label class="control-label" style="margin-left: 20px;">分娩医院</label>
                    <label class="radio-inline" style="margin-left: 10px;" onmousedown="onHospitalClick('condition0_birthChild','1','condition0_birthChildMy')">
                        <input id="condition0_birthChildMy" name="birthChild" value="1" type="radio" onclick="return false;"/>本院
                    </label>
                    <label class="radio-inline" style="margin-left: 10px;" onmousedown="onHospitalClick('condition0_birthChild','2','condition0_birthChildOther')">
                        <input id="condition0_birthChildOther" name="birthChild" value="2" type="radio" onclick="return false;"/>其他医院
                    </label>
                </div>
            </div>
        </div>
        <hr style="margin-top:0px;margin-bottom:0px;"/>
        <div class="panel-body" style="padding: 0px;">
            <ul id="myTab" class="nav nav-tabs">
                <li id="navTab1" class="active"><a id="navTabA1" href="#tab1" data-toggle="tab">基本信息</a></li>
                <li><a href="#tab2" data-toggle="tab">孕前病史及孕育史</a></li>
                <li><a href="#tab3" data-toggle="tab">孕期检验检测信息</a></li>
                <li><a href="#tab4" data-toggle="tab">人体成分</a></li>
                <li><a href="#tab5" data-toggle="tab">孕期诊疗信息</a></li>
                <li id="navTab6" style="display:none;"><a href="#tab6" data-toggle="tab">分娩信息</a></li>
            </ul>
            <div id="myTabContent" class="tab-content">
                <div class="tab-pane active" id="tab1" style="padding: 3px;">
                    <div class="form-inline" style="margin-left: 3px;">
                        <div class="form-group">
                            <label class="control-label">身高</label>
                            <div class="input-group">
                                <input id="condition1_heightFrom" name="heightFrom" class="form-control input-sm text-center" style="width:50px;" type="text" value=""
                                       onchange="checkInteger('condition1_heightFrom','condition1_heightTo','身高',this)"/>
                                <span class="input-group-addon input-sm">cm</span>
                            </div>
                            至
                            <div class="input-group">
                                <input id="condition1_heightTo" name="heightTo" class="form-control input-sm text-center" style="width:50px;" type="text" value=""
                                       onchange="checkInteger('condition1_heightFrom','condition1_heightTo','身高',this)"/>
                                <span class="input-group-addon input-sm">cm</span>
                            </div>
                        </div>
                        <div class="form-group" style="margin-left: 10px;">
                            <label class="control-label">年龄</label>
                            <div class="input-group">
                                <input id="condition1_ageFrom" name="ageFrom" class="form-control input-sm text-center" style="width:50px;" type="text"
                                       onchange="checkInteger('condition1_ageFrom','condition1_ageTo','年龄',this)"/>
                                <span class="input-group-addon input-sm">岁</span>
                            </div>
                            至
                            <div class="input-group">
                                <input id="condition1_ageTo" name="ageTo" class="form-control input-sm text-center" style="width:50px;" type="text"
                                       onchange="checkInteger('condition1_ageFrom','condition1_ageTo','年龄',this)"/>
                                <span class="input-group-addon input-sm">岁</span>
                            </div>
                        </div>
                        <select class="form-control" id="condition1_bmiScope" name="bmiScope" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <select class="form-control" id="condition1_birthNum" name="birthNum" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                    </div>
                </div>
                <div class="tab-pane fade" id="tab2" style="padding: 3px;">
                    <div class="form-inline">
                        <select class="form-control" id="condition2_diseaseHistory" name="diseaseHistory" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <select class="form-control" id="condition2_familyHistory" name="familyHistory" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <select class="form-control" id="condition2_pregComplications" name="pregComplications" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                    </div>
                    <div class="form-inline" style="margin-top:3px;">
                        <select class="form-control" id="condition2_pregTimes" name="pregTimes" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <select class="form-control" id="condition2_birthTimes" name="birthTimes" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <select class="form-control" id="condition2_badPregHistory" name="badPregHistory" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <select class="form-control" id="condition2_badBirthHistory" name="badBirthHistory" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                    </div>
                </div>
                <div class="tab-pane fade" id="tab3" style="padding: 3px;">
                    <div id="normalInspectList" class="form-inline">
                        <div class="div-table-cell">
                            <label class="control-label">检验项目</label>
                            <input id="condition3_normalInspectItemIds" class="input-sm" name="normalInspectItemIds" type="hidden"/>
                            <input id="condition3_normalInspectItemIdsNames" type="hidden" onchange="setConditionTable('condition3_normalInspectItemIdsNames','input')"/>
                            <input type="text" id="normalInspectItemName" class="form-control input-sm" placeholder="请输入检验项目" style="margin-right: 10px;"/>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="tab4" style="padding: 3px;">
                    <div class="form-inline">
                        <select class="form-control" id="condition4_weightCondition" name="weightCondition" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <label class="checkbox-inline" style="margin-left: 10px;">
                            <input id="condition4_proteinReduce" name="proteinReduce" value="1" type="checkbox"
                                   onchange="setConditionTable('condition4_proteinReduce','checkbox','蛋白质降低',null, null,this)"/>蛋白质降低
                        </label>
                        <label class="checkbox-inline" style="margin-left: 10px;margin-right: 10px;">
                            <input id="condition4_muscleReduce" name="muscleReduce" value="1" type="checkbox"
                                   onchange="setConditionTable('condition4_muscleReduce','checkbox','骨骼肌降低',null, null,this)"/>骨骼肌降低
                        </label>
                        <select class="form-control" id="condition4_fuzhongCondition" name="fuzhongCondition" multiple="multiple" style="margin-left: 10px;height: 34px; width: 210px;">
                        </select>
                        <label class="control-label" style="margin-left: 10px;">相位角</label>
                        <input id="condition4_xiangweiFrom" name="xiangweiFrom" class="form-control text-center input-sm" type="text" style="width: 50px;"
                               onblur="checkFloat('condition4_xiangweiFrom','condition4_xiangweiTo','相位角',this,1)"/> 至
                        <input id="condition4_xiangweiTo" name="xiangweiTo" class="form-control text-center input-sm" type="text" style="width: 50px;"
                               onblur="checkFloat('condition4_xiangweiFrom','condition4_xiangweiTo','相位角',this,1)"/>
                    </div>
                </div>
                <div class="tab-pane fade" id="tab5" style="padding: 3px;">
                    <div id="diseaseList" class="form-inline">
                        <input id="condition5_diseaseIds" class="input-sm" name="deseaseIds" type="hidden"/>
                        <input id="condition5_diseaseIdsNames" type="hidden" onchange="setConditionTable('condition5_diseaseIdsNames','input')"/>
                        <input type="text" id="diseaseName" class="form-control input-sm" placeholder="请输入诊断名称"/>
                    </div>
                    <div class="form-inline" style="margin-top:3px;">
                        <label class="control-label">营养门诊就诊次数</label>
                        <input id="condition5_menzhenNumFrom" name="menzhenNumFrom" class="form-control text-center input-sm" type="text" style="width: 50px;"
                               onchange="checkInteger('condition5_menzhenNumFrom','condition5_menzhenNumTo','营养门诊就诊次数',this)"/> 至
                        <input id="condition5_menzhenNumTo" name="menzhenNumTo" class="form-control text-center input-sm" type="text" style="width: 50px;"
                               onchange="checkInteger('condition5_menzhenNumFrom','condition5_menzhenNumTo','营养门诊就诊次数',this)"/>
                        <label class="control-label" style="margin-left: 10px;">首次孕期营养门诊就诊孕周数</label>
                        <input id="condition5_menzhenPregWeekFrom" name="menzhenPregWeekFrom" class="form-control text-center input-sm" type="text" style="width: 50px;"
                               onchange="checkInteger('condition5_menzhenPregWeekFrom','condition5_menzhenPregWeekTo','首次孕期营养门诊就诊孕周数',this)"/> 至
                        <input id="condition5_menzhenPregWeekTo" name="menzhenPregWeekTo" class="form-control text-center input-sm" type="text" style="width: 50px;"
                               onchange="checkInteger('condition5_menzhenPregWeekFrom','condition5_menzhenPregWeekTo','首次孕期营养门诊就诊孕周数', this)"/>
                    </div>
                    <div class="form-inline" style="margin-top:3px;">
                        <select class="form-control" id="condition5_firstLevel" name="firstLevel" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                        <select class="form-control" id="condition5_lastLevel" name="lastLevel" multiple="multiple" style="height: 34px; width: 210px;">
                        </select>
                    </div>
                    <div class="form-inline" style="margin-top:3px;">
                        <select id="condition5_isOneday" name="isOneday" class="form-control input-sm" disabled onchange="setConditionTable('condition5_isOneday','select')">
                            <option value="">是否一日门诊</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                        <select id="condition5_isMDT" name="isMDT" class="form-control input-sm" disabled onchange="setConditionTable('condition5_isMDT','select')">
                            <option value="">是否MDT门诊</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                </div>
                <div class="tab-pane fade" id="tab6">
                    <table class="table table-bordered table-padding-4 no-bottom">
                        <tbody>
                        <tr>
                            <td class="text-center" style="width: 15%">基本情况</td>
                            <td class="text-left">
                                <div class="form-inline">
                                    <label class="control-label" style="margin-top:3px;">分娩时孕周数</label> <input id="condition6_birthPregWeekFrom" name="birthPregWeekFrom"
                                                                                                               class="form-control text-center input-sm" type="text" style="width: 50px;"
                                                                                                               onchange="checkInteger('condition6_birthPregWeekFrom','condition6_birthPregWeekTo','分娩时孕周数',this)"/>
                                    至
                                    <input id="condition6_birthPregWeekTo" name="birthPregWeekTo"
                                           class="form-control text-center input-sm" type="text" style="width: 50px;"
                                           onchange="checkInteger('condition6_birthPregWeekFrom','condition6_birthPregWeekTo','分娩时孕周数',this)"/>
                                    <select
                                            class="form-control" id="condition6_childbirthType" name="childbirthType"
                                            multiple="multiple"
                                            style="margin-left: 10px; height: 34px; width: 210px;">
                                    </select>
                                    <input id="condition6_birthPlaceId" class="input-sm" name="birthPlace" type="hidden"/>
                                    <input id="condition6_birthPlace"
                                           class="form-control input-sm" type="text" placeholder="请输入分娩方位" onchange="onBirthPlaceChange('condition6_birthPlace','input')"/>
                                    <select class="form-control" id="condition6_mazuiType" name="mazuiType"
                                            multiple="multiple"
                                            style="margin-left: 10px; height: 34px; width: 210px;">
                                    </select>
                                    <select id="condition6_isDangerPregWoman" name="isDangerPregWoman" class="form-control input-sm"
                                            onchange="setConditionTable('condition6_isDangerPregWoman','select')">
                                        <option value="">是否为危重产妇</option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-center" style="width: 15%">并发症</td>
                            <td class="text-left">
                                <div id="complicationList" class="form-inline" style="margin-top:3px;">
                                    <select id="condition6_isInspectBeforeBirth" name="isInspectBeforeBirth" class="form-control input-sm"
                                            onchange="setConditionTable('condition6_isInspectBeforeBirth','select')">
                                        <option value="">是否产前检查</option>
                                        <option value="1">有</option>
                                        <option value="0">无</option>
                                    </select>
                                    <label class="control-label" style="margin-left: 10px;">并发症情况</label>
                                    <input id="condition6_complicationIds" class="input-sm" name="complicationIds" type="hidden"/>
                                    <input id="condition6_complicationIdsNames" type="hidden" onchange="setConditionTable('condition6_complicationIdsNames','input')"/>
                                    <input id="complicationName" class="form-control input-sm" placeholder="请输入并发症名称"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-center" style="width: 15%">产妇结局</td>
                            <td class="text-left">
                                <div class="form-inline">
                                    <label class="radio-inline" style="margin-left: 3px;" onmousedown="onIsLiveOrDeadClick('1','condition6_isLive')">
                                        <input id="condition6_isLive" name="isLiveOrDead" value="1" type="radio" onclick="return false;"/>存活
                                    </label>
                                    <label class="radio-inline" style="margin-left: 10px;" onmousedown="onIsLiveOrDeadClick('2','condition6_isDead')">
                                        <input id="condition6_isDead" name="isLiveOrDead" value="2" type="radio" onclick="return false;"/>死亡
                                    </label>
                                    <select id="condition6_whenDead" class="form-control" name="whenDead"
                                            multiple="multiple">
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-center" style="width: 15%">新生儿</td>
                            <td class="text-left">
                                <div class="form-inline" style="margin-left: 3px;margin-top:3px;">
                                    <select
                                            class="form-control" id="condition6_newBirthSex" name="newBirthSex"
                                            multiple="multiple"
                                            style="height: 34px; width: 210px;">
                                    </select>
                                    <label class="control-label" style="margin-left: 10px;">体重</label>
                                    <div class="input-group">
                                        <input id="condition6_weightFrom" name="weightFrom" class="form-control text-center input-sm" style="width: 70px;"
                                               type="text" onblur="checkInteger('condition6_weightFrom','condition6_weightTo','体重',this)"/> <span class="input-group-addon">g</span>
                                    </div>
                                    至
                                    <div class="input-group">
                                        <input id="condition6_weightTo" name="weightTo" class="form-control text-center input-sm" style="width: 70px;"
                                               type="text" onblur="checkInteger('condition6_weightFrom','condition6_weightTo','体重',this)"/> <span class="input-group-addon">g</span>
                                    </div>
                                    <label class="checkbox-inline" style="margin-left: 10px;">
                                        <input id="condition6_birthDefect" name="birthDefect" value="1" type="checkbox"
                                               onchange="setConditionTable('condition6_birthDefect','checkbox','出生缺陷',null, null,this)"/>出生缺陷
                                    </label>
                                    <select id="condition6_isRescue" name="isRescue" class="form-control input-sm" style="margin-left: 10px;"
                                            onchange="setConditionTable('condition6_isRescue','select')">
                                        <option value="">是否抢救</option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
                                </div>
                                <div id="childComplicationList" class="form-inline" style="margin-left: 3px;margin-top:3px;">
                                    <label class="control-label">并发症</label>
                                    <input id="condition6_childComplicationIds" class="input-sm" name="childComplicationIds" type="hidden"/>
                                    <input id="condition6_childComplicationIdsNames" type="hidden" onchange="setConditionTable('condition6_childComplicationIdsNames','input')"/>
                                    <input id="childComplicationName" class="form-control input-sm" placeholder="请输入并发症名称"/>
                                </div>
                                <div class="form-inline" style="margin-left: 3px;margin-top:3px;">
                                    <label class="control-label">阿氏评分 1分钟</label>
                                    <div class="input-group">
                                        <input id="condition6_ashiOneMinuteFrom" name="ashiOneMinuteFrom" class="form-control text-center input-sm" style="width: 50px;"
                                               type="text" onchange="checkInteger('condition6_ashiOneMinuteFrom','condition6_ashiOneMinuteTo','1分钟',this)"/> <span class="input-group-addon">分</span>
                                    </div>
                                    至
                                    <div class="input-group">
                                        <input id="condition6_ashiOneMinuteTo" name="ashiOneMinuteTo" class="form-control text-center input-sm" style="width: 50px;"
                                               type="text" onchange="checkInteger('condition6_ashiOneMinuteFrom','condition6_ashiOneMinuteTo','1分钟',this)"/> <span class="input-group-addon">分</span>
                                    </div>
                                    <label class="control-label" style="margin-left: 10px;"> 5分钟</label>
                                    <div class="input-group">
                                        <input id="condition6_ashiFiveMinuteFrom" name="ashiFiveMinuteFrom" class="form-control text-center input-sm" style="width: 50px;"
                                               type="text" onchange="checkInteger('condition6_ashiFiveMinuteFrom','condition6_ashiFiveMinuteTo','5分钟',this)"/> <span class="input-group-addon">分</span>
                                    </div>
                                    至
                                    <div class="input-group">
                                        <input id="condition6_ashiFiveMinuteTo" name="ashiFiveMinuteTo" class="form-control text-center input-sm" style="width: 50px;"
                                               type="text" onchange="checkInteger('condition6_ashiFiveMinuteFrom','condition6_ashiFiveMinuteTo','5分钟',this)"/> <span class="input-group-addon">分</span>
                                    </div>
                                    <label class="control-label" style="margin-left: 10px;"> 10分钟</label>
                                    <div class="input-group">
                                        <input id="condition6_ashiTenMinuteFrom" name="ashiTenMinuteFrom" class="form-control text-center input-sm" style="width: 50px;"
                                               type="text" onchange="checkInteger('condition6_ashiTenMinuteFrom','condition6_ashiTenMinuteTo','10分钟',this)"/> <span class="input-group-addon">分</span>
                                    </div>
                                    至
                                    <div class="input-group">
                                        <input id="condition6_ashiTenMinuteTo" name="ashiTenMinuteTo" class="form-control text-center input-sm" style="width: 50px;"
                                               type="text" onchange="checkInteger('condition6_ashiTenMinuteFrom','condition6_ashiTenMinuteTo','10分钟',this)"/> <span class="input-group-addon">分</span>
                                    </div>
                                    <select id="condition6_isChildLiveOrDead" name="isChildLiveOrDead" class="form-control input-sm" style="margin-left: 10px;"
                                            onchange="setConditionTable('condition6_isChildLiveOrDead','select')">
                                        <option value="">新生儿是否死亡</option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-center" style="width: 15%">胎盘羊水情况</td>
                            <td class="text-left">
                                <div class="form-inline">
                                    <select id="condition6_afv" name="afv" class="form-control" multiple="multiple" style="margin-left: 3px;"></select>
                                    <select id="condition6_afluid" name="afluid" class="form-control" multiple="multiple" style="margin-left: 10px;"></select>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="panel panel-lightblue" style="margin-top: 10px;">
    <div class="panel-heading text-left">
        <i class="fa fa-tag fa-fw"></i>
        查询结果
    </div>
    <div class="panel-body" style="padding: 0px;">
        <ul id="myTab2" class="nav nav-tabs">
            <li class="active"><a href="#tabResult1" data-toggle="tab">查询人员列表</a></li>
            <li><a href="#tabResult2" data-toggle="tab">查询条件导出表</a></li>
            <li><a href="#tabResult3" data-toggle="tab">分娩结局统计报表</a></li>
            <li><a href="#tabResult4" data-toggle="tab">孕期营养门诊考核报表</a></li>
        </ul>
        <div id="myTabContent2" class="tab-content">
            <div class="tab-pane active" id="tabResult1">
                <div id="pregTable" class="table-responsive">
                    <table id="personPregTable" class="table table-bordered table-hover no-bottom">
                        <thead>
                        <tr class="active">
                            <th class="text-center">编号</th>
                            <th class="text-center">姓名</th>
                            <th class="text-center">病案号</th>
                            <th class="text-center">ID</th>
                            <th class="text-center">年龄/岁</th>
                            <!-- <th class="text-center">就诊营养科</th> -->
                            <th class="text-center">营养科医生</th>
                            <th class="text-center">营养就诊次数</th>
                            <th class="text-center">妊娠次数</th>
                            <th class="text-center">分娩次数</th>
                            <th class="text-center">受孕方式</th>
                            <th class="text-center">预产期</th>
                            <th class="text-center">营养病历</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="odd">
                            <td valign="top" colspan="13" align="center" class="dataTables_empty">
                                <h4>没有数据！</h4>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div id="birthTable" class="table-responsive" style="display:none;">
                    <table id="personBirthTable" class="table table-bordered table-hover no-bottom">
                        <thead>
                        <tr class="active">
                            <th class="text-center">编号</th>
                            <th class="text-center">姓名</th>
                            <th class="text-center">病案号</th>
                            <th class="text-center">ID</th>
                            <th class="text-center">年龄/岁</th>
                            <!-- <th class="text-center">就诊营养科</th> -->
                            <th class="text-center">营养科医生</th>
                            <th class="text-center">营养就诊次数</th>
                            <th class="text-center">妊娠次数</th>
                            <th class="text-center">分娩次数</th>
                            <th class="text-center">受孕方式</th>
                            <th class="text-center">分娩日期</th>
                            <th class="text-center">营养病历</th>
                            <th class="text-center">分娩结局</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="odd active">
                            <td valign="top" colspan="14" align="center" class="dataTables_empty">
                                <h4>没有数据！</h4>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="tab-pane fade" id="tabResult2">
                <form id="exportForm" action="">
                    <table class="table table-bordered table-padding-4 no-bottom">
                        <tbody>
                        <tr>
                            <td style="border-right:none;">
                                <div class="form-inline">
                                    <label class="checkbox-inline" style="margin-left: 3px;">
                                        <input id="allExcel" name="allExcel" value="1" type="checkbox" onchange="onCheckExcelChange('allExcel',this)"/>总导出表
                                    </label>
                                    <label class="checkbox-inline" style="margin-left: 10px;display: none;">
                                        <input id="bodyExcel" name="bodyExcel" value="2" type="checkbox" onchange="onCheckExcelChange('bodyExcel',this)"/>人体成分导出表
                                    </label>
                                    <label class="checkbox-inline" style="margin-left: 10px;display: none;">
                                        <input id="pregInspectExcel" name="pregInspectExcel" value="3" type="checkbox" onchange="onCheckExcelChange('pregInspectExcel',this)"/>孕期检验记录导出表
                                    </label>
                                    <label class="checkbox-inline" style="margin-left: 10px;">
                                        <input id="nutritionExcel" name="nutritionExcel" value="4" type="checkbox" onchange="onCheckExcelChange('nutritionExcel',this)"/>营养制剂导出表
                                    </label>
                                    <label id="birthResultExcelLabel" class="checkbox-inline" style="margin-left: 10px;">
                                        <input id="birthResultExcel" name="birthResultExcel" value="5" type="checkbox" onchange="onCheckExcelChange('birthResultExcel',this)"/>分娩结局导出表
                                    </label>
                                </div>
                            </td>
                            <td class="text-right" style="border-left:none;">
                                <button id="exportButton" class="btn btn-primary" type="button" onclick="exportResultForm()">导出excel表格</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div id="inspectList" class="form-inline" style="padding: 3px;display:none;">
                                    <label class="control-label">检验项目</label>
                                    <input id="pregInspectIds" name="inspectIds" type="hidden"/>
                                    <input type="text" id="pregInspectNameExcel" class="form-control" placeholder="请输入检验项目"/>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="tab-pane fade" id="tabResult3">
                <form id="birthResultQueryForm" action="${common.basepath}/${applicationScope.URL.Statistic.STATISTIC_BIRTHRESULT}">
                    <div class="form-inline" style="padding: 7px;">
                        <label class="control-label">分娩时间</label>
                        <div class="input-group">
                            <input id="conditionResult_resultStartDate" class="form-control form_date" name="resultStartDate" placeholder="请选择开始日期" readonly type="text"/>
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button" onclick="common.dateShow('conditionResult_resultStartDate')">
                                    <i class="fa fa-calendar fa-fw"></i>选择
                                </button>
                            </span>
                        </div>
                        至
                        <div class="input-group">
                            <input id="conditionResult_resultEndDate" class="form-control form_date" name="resultEndDate" placeholder="请选择结束日期" readonly type="text"/>
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button" onclick="common.dateShow('conditionResult_resultEndDate')">
                                    <i class="fa fa-calendar fa-fw"></i>选择
                                </button>
                            </span>
                        </div>
                        <button class="btn btn-primary" type="button" onclick="queryBirthResultForm()">
                            <i class="fa fa-search fa-fw"></i>查询
                        </button>
                    </div>
                </form>
                <table id="birthResultTable"
                       class="table table-bordered table-padding-4 no-bottom">
                    <tbody>
                    <tr class="active">
                        <td class="text-center" rowspan="2">入院总数</td>
                        <td class="text-center" colspan="2">分娩总数</td>
                        <td class="text-center" rowspan="2">危重孕妇</td>
                        <td class="text-center" rowspan="2">死亡孕妇</td>
                        <td class="text-center" colspan="4">分娩孕周数</td>
                        <td class="text-center" colspan="4">产妇来源</td>
                        <td class="text-center" colspan="5">会阴情况</td>
                    </tr>
                    <tr class="active">
                        <td class="text-center">初产</td>
                        <td class="text-center">经产</td>
                        <td class="text-center"><28周</td>
                        <td class="text-center">28-36</td>
                        <td class="text-center">37-42</td>
                        <td class="text-center">>42周</td>
                        <td class="text-center">本院</td>
                        <td class="text-center">外院转诊</td>
                        <td class="text-center" style="color:red;">高危转诊</td>
                        <td class="text-center">无产前</td>
                        <td class="text-center">完整</td>
                        <td class="text-center">Ⅰ°裂伤</td>
                        <td class="text-center">Ⅱ°裂伤</td>
                        <td class="text-center">Ⅲ°裂伤</td>
                        <td class="text-center">切开</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numRuYuan" class="text-center">-</td><!-- 入院总数 -->

                        <td id="numBirthChuChan" class="text-center">-</td><!-- 分娩总数-出产 -->
                        <td id="numBirthJingChan" class="text-center">-</td><!-- 经产 -->

                        <td id="numDangerPreg" class="text-center">-</td><!-- 危重孕妇 -->
                        <td id="numDeadPreg" class="text-center">-</td><!-- 死亡孕妇 -->

                        <td id="numPregWeek28" class="text-center">-</td><!-- 分娩时孕周-<28周 -->
                        <td id="numPregWeek2836" class="text-center">-</td><!-- 28-36 -->
                        <td id="numPregWeek3741" class="text-center">-</td><!-- 37-42 -->
                        <td id="numPregWeek42" class="text-center">-</td><!-- >42 -->

                        <td id="numPregFromMy" class="text-center">-</td><!-- 产妇来源-本院 -->
                        <td id="numPregFromOther" class="text-center">-</td><!-- 外院 -->
                        <td id="numPregFromGaowei" class="text-center">-</td><!-- 外院 -->
                        <td id="numPregFromNone" class="text-center">-</td><!-- 无产前 -->

                        <td id="numPerinaeumFull" class="text-center">-</td><!-- 会阴情况-完整 -->
                        <td id="numPerinaeumOne" class="text-center">-</td><!-- Ⅰ°裂伤 -->
                        <td id="numPerinaeumTwo" class="text-center">-</td><!-- Ⅱ°裂伤 -->
                        <td id="numPerinaeumThree" class="text-center">-</td><!-- Ⅲ°裂伤 -->
                        <td id="numPerinaeumCut" class="text-center">-</td><!-- 切开 -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" colspan="6">分娩方式</td>
                        <td class="text-center" colspan="6">引产方式</td>
                        <td class="text-center" colspan="2">助产操作</td>
                        <td class="text-center" colspan="3">胎盘情况</td>
                    </tr>
                    <tr class="active">
                        <td class="text-center">自然分娩</td>
                        <td class="text-center">吸引</td>
                        <td class="text-center">产钳</td>
                        <td class="text-center">臀助产</td>
                        <td class="text-center">剖宫产</td>
                        <td class="text-center">其他</td>
                        <td class="text-center">无</td>
                        <td class="text-center">改良药物</td>
                        <td class="text-center">剥膜</td>
                        <td class="text-center">点滴</td>
                        <td class="text-center">破膜</td>
                        <td class="text-center">其他</td>
                        <td class="text-center">产后刮宫</td>
                        <td class="text-center">手转胎头</td>
                        <td class="text-center">手剥</td>
                        <td class="text-center">沾水</td>
                        <td class="text-center">自然脱落</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numBirthTypeAuto" class="text-center">-</td><!-- 分娩方式-自然分娩 -->
                        <td id="numBirthTypePull" class="text-center">-</td><!-- 吸引 -->
                        <td id="numBirthTypeForceps" class="text-center">-</td><!-- 产钳 -->
                        <td id="numBirthTypeHip" class="text-center">-</td><!-- 臀助 -->
                        <td id="numBirthTypeDissect" class="text-center">-</td><!-- 剖宫产 -->
                        <td id="numBirthTypeOther" class="text-center">-</td><!-- 其他 -->

                        <td id="numPullTypeNone" class="text-center">-</td><!-- 引产方式-无 -->
                        <td id="numPullTypeDrug" class="text-center">-</td><!-- 改良药物 -->
                        <td id="numPullTypeMembrane" class="text-center">-</td><!-- 剥膜 -->
                        <td id="numPullTypeBottle" class="text-center">-</td><!-- 点滴 -->
                        <td id="numPullTypeMembraneHarm" class="text-center">-</td><!-- 破膜 -->
                        <td id="numPullTypeOther" class="text-center">-</td><!-- 其他 -->

                        <td id="numHelpOprationPostpartumCurettage" class="text-center">-</td><!-- 助产操作-产后刮宫 -->
                        <td id="numHelpOprationTurnHead" class="text-center">-</td><!-- 手转胎头 -->

                        <td id="numPlacentaHand" class="text-center">-</td><!-- 胎盘情况-手剥 -->
                        <td id="numPlacentaWater" class="text-center">-</td><!-- 沾水 -->
                        <td id="numPlacentaAuto" class="text-center">-</td><!-- 自然脱落 -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" rowspan="5">产前合并症</td>
                        <td class="text-center" colspan="4">妊高症</td>
                        <td class="text-center" colspan="5">糖耐量异常</td>
                        <td class="text-center" colspan="3">血型不合</td>
                        <td class="text-center" colspan="4">骨盆异常</td>
                    </tr>
                    <tr class="active">
                        <td class="text-center">轻度</td>
                        <td class="text-center">中度</td>
                        <td class="text-center">重度</td>
                        <td class="text-center">子痫</td>
                        <td class="text-center" colspan="2" style="color:grey">50g糖筛异常</td>
                        <td class="text-center">糖耐量异常</td>
                        <td class="text-center">妊娠期糖尿病</td>
                        <td class="text-center">糖尿病合并妊娠</td>
                        <td class="text-center">ABO血型不合</td>
                        <td class="text-center">Rh血型不合</td>
                        <td class="text-center" style="color:grey">其他</td>
                        <td class="text-center">出口</td>
                        <td class="text-center" style="color:grey">中骨盆</td>
                        <td class="text-center">入口</td>
                        <td class="text-center" style="color:grey">其他</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numPregHighLittle" class="text-center">-</td><!-- 妊高症-轻度 -->
                        <td id="numPregHighMiddle" class="text-center">-</td><!-- 中度 -->
                        <td id="numPregHighSerious" class="text-center">-</td><!-- 重度 -->
                        <td id="numPregHighEclampsia" class="text-center">-</td><!-- 子痫 -->

                        <td id="numSugarAbnormal50g" class="text-center" colspan="2">-</td><!-- 糖耐量异常-50g糖筛异常 -->
                        <td id="numSugarAbnormalPatience" class="text-center">-</td><!-- 糖耐量异常 -->
                        <td id="numSugarAbnormalPregDiabetes" class="text-center">-</td><!-- 妊娠期糖尿病 -->
                        <td id="numSugarAbnormalDiabetesPreg" class="text-center">-</td><!-- 糖尿病合并妊娠 -->

                        <td id="numBloodABO" class="text-center">-</td><!-- 血型不合-ABO血型不合 -->
                        <td id="numBloodRH" class="text-center">-</td><!-- Rh血型不合 -->
                        <td id="numBloodOther" class="text-center">-</td><!-- 其他 -->

                        <td id="numPelvicExit" class="text-center">-</td><!-- 骨盆异常-出口 -->
                        <td id="numPelvicMiddle" class="text-center">-</td><!-- 中骨盆 -->
                        <td id="numPelvicEntrance" class="text-center">-</td><!-- 入口 -->
                        <td id="numPelvicOther" class="text-center">-</td><!-- 其他 -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" style="color:grey">畸形</td>
                        <td class="text-center" style="color:grey">妇科肿瘤</td>
                        <td class="text-center">孕史不良</td>
                        <td class="text-center">产史不良</td>
                        <td class="text-center">剖宫产史</td>
                        <td class="text-center">初产头浮</td>
                        <td class="text-center">高龄初产</td>
                        <td class="text-center">胎膜早破</td>
                        <td class="text-center">双胎多胎</td>
                        <td class="text-center">早产</td>
                        <td class="text-center">过期妊娠</td>
                        <td class="text-center" style="color:grey">手术史</td>
                        <td class="text-center">ITP</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numDeformity" class="text-center">-</td><!-- 畸形 -->
                        <td id="numTumor" class="text-center">-</td><!-- 妇科肿瘤 -->
                        <td id="numPregHistoryBad" class="text-center">-</td><!-- 孕史不良 -->
                        <td id="numBirthHistoryBad" class="text-center">-</td><!-- 产史不良 -->
                        <td id="numCSHistory" class="text-center">-</td><!-- 剖宫产史 -->
                        <td id="numFloatHead" class="text-center">-</td><!-- 初产头浮 -->
                        <td id="numBigAgePreg" class="text-center">-</td><!-- 高龄初产 -->
                        <td id="numCaulEarlyHarm" class="text-center">-</td><!-- 胎膜早破 -->
                        <td id="numTwoManyBirth" class="text-center">-</td><!-- 双胎多胎 -->
                        <td id="numEarlyBirth" class="text-center">-</td><!-- 早产 -->
                        <td id="numTimeoutPreg" class="text-center">-</td><!-- 过期妊娠 -->
                        <td id="numOperationHistory" class="text-center">-</td><!-- 手术史 -->
                        <td id="numITP" class="text-center">-</td><!-- ITP -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" rowspan="6">产时并发症</td>
                        <td class="text-center" colspan="3">胎儿宫内窘迫</td>
                        <td class="text-center" colspan="3">胎位异常</td>
                        <td class="text-center" rowspan="2">脐带缠绕</td>
                        <td class="text-center" colspan="4">脐带</td>
                        <td class="text-center" colspan="2">羊水</td>
                        <td class="text-center" colspan="2">胎盘</td>
                        <td class="text-center" rowspan="2">胎盘早剥</td>
                    </tr>
                    <tr class="active">
                        <td class="text-center">胎心</td>
                        <td class="text-center">羊水</td>
                        <td class="text-center">胎心+羊水</td>
                        <td class="text-center">臀位</td>
                        <td class="text-center">枕横位</td>
                        <td class="text-center">枕后位</td>
                        <td class="text-center">过长</td>
                        <td class="text-center">过短</td>
                        <td class="text-center">脱垂</td>
                        <td class="text-center">先露</td>
                        <td class="text-center">过多</td>
                        <td class="text-center">过少</td>
                        <td class="text-center">低置</td>
                        <td class="text-center">前置</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numFetalDistressHeart" class="text-center">-</td><!-- 胎儿宫内窘迫-胎心 -->
                        <td id="numFetalDistressWater" class="text-center">-</td><!-- 羊水 -->
                        <td id="numFetalDistressHeartWater" class="text-center">-</td><!-- 胎心+羊水 -->


                        <td id="numAbnormalPositionHip" class="text-center">-</td><!-- 胎位异常-臀位 -->
                        <td id="numAbnormalPositionOccipitotransverse" class="text-center">-</td><!-- 枕横位 -->
                        <td id="numAbnormalPositionOccipitoposterior" class="text-center">-</td><!-- 枕后位 -->

                        <td id="numCordEntanglement" class="text-center">-</td><!-- 脐带缠绕 -->

                        <td id="numCordLong" class="text-center">-</td><!-- 脐带-过长 -->
                        <td id="numCordShort" class="text-center">-</td><!-- 过短 -->
                        <td id="numCordProlapse" class="text-center">-</td><!-- 脱垂 -->
                        <td id="numCordPresent" class="text-center">-</td><!-- 先露 -->

                        <td id="numWaterMany" class="text-center">-</td><!-- 羊水-过多 -->
                        <td id="numWaterLittle" class="text-center">-</td><!-- 过少 -->

                        <td id="numPlacentaLow" class="text-center">-</td><!-- 胎盘-低置 -->
                        <td id="numPlacentaPreposition" class="text-center">-</td><!-- 前置 -->

                        <td id="numPlacentaEarlyPeeling" class="text-center">-</td><!-- 胎盘早剥 -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" rowspan="2">胎盘胎膜残留</td>
                        <td class="text-center" rowspan="2">胎盘植入</td>
                        <td class="text-center" rowspan="2">子宫破裂</td>
                        <td class="text-center" rowspan="2">产间发热</td>
                        <td class="text-center" rowspan="2">羊水栓塞</td>
                        <td class="text-center" rowspan="2" style="color:grey">胎盘异常</td>
                        <td class="text-center" colspan="2">产程</td>
                        <td class="text-center" colspan="2" style="color:grey">产后出血</td>
                    </tr>
                    <tr class="active">
                        <td class="text-center">滞产</td>
                        <td class="text-center">二产程</td>
                        <td class="text-center" style="color:grey">VD</td>
                        <td class="text-center" style="color:grey">CS</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numPlacentaCaulRemain" class="text-center">-</td><!-- 胎盘胎膜残留 -->
                        <td id="numPlacentaImplantation" class="text-center">-</td><!-- 胎盘植入 -->
                        <td id="numHysterorrhexis" class="text-center">-</td><!-- 子宫破裂 -->
                        <td id="numBirthFever" class="text-center">-</td><!-- 产间发热 -->
                        <td id="numWaterEmbolism" class="text-center">-</td><!-- 羊水栓塞 -->
                        <td id="numPlacentaAbnormal" class="text-center">-</td><!-- 胎盘异常 -->

                        <td id="numBirthBradytoia" class="text-center">-</td><!-- 产程-滞产 -->
                        <td id="numBirthSecond" class="text-center">-</td><!-- 二产程 -->

                        <td id="numFloodingVD" class="text-center">-</td><!-- 产后出血-VD -->
                        <td id="numFloodingCS" class="text-center">-</td><!-- CS -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" rowspan="2">产后并发症</td>
                        <td class="text-center">产后感染</td>
                        <td class="text-center">产后低热</td>
                        <td class="text-center">尿潴留</td>
                        <td class="text-center">伤口感染</td>
                        <td class="text-center" style="color:grey">伤口水肿</td>
                        <td class="text-center" style="color:grey">晚期出血</td>
                        <td class="text-center">伤口脂肪液化</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numBirthInfection" class="text-center">-</td><!-- 产后感染 -->
                        <td id="numBirthLowHeat" class="text-center">-</td><!-- 产后低热 -->
                        <td id="numUroschesis" class="text-center">-</td><!-- 尿潴留 -->
                        <td id="numWoundInfection" class="text-center">-</td><!-- 伤口感染 -->
                        <td id="numWoundEdema" class="text-center">-</td><!-- 伤口水肿 -->
                        <td id="numLateBlood" class="text-center">-</td><!-- 晚期出血 -->
                        <td id="numWoundFatLiquefaction" class="text-center">-</td><!-- 伤口脂肪液化 -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" rowspan="2">内科并发症</td>
                        <td class="text-center" style="color:grey">哮喘</td>
                        <td class="text-center" style="color:grey">肾病</td>
                        <td class="text-center" style="color:grey">神经系统疾病</td>
                        <td class="text-center" style="color:grey">心脏病</td>
                        <td class="text-center" style="color:grey">心功能异常</td>
                        <td class="text-center" style="color:grey">血液病</td>
                        <td class="text-center">贫血</td>
                        <td class="text-center" style="color:grey">肝炎</td>
                        <td class="text-center">澳抗阳性</td>
                        <td class="text-center" style="color:grey">病毒感染</td>
                        <td class="text-center" style="color:grey">甲状腺</td>
                        <td class="text-center">SLE</td>
                        <td class="text-center" style="color:grey">其他内科并发症</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numAsthma" class="text-center">-</td><!-- 哮喘 -->
                        <td id="numNephropathy" class="text-center">-</td><!-- 肾病 -->
                        <td id="numNervousDisease" class="text-center">-</td><!-- 神经系统疾病 -->
                        <td id="numHeartDisease" class="text-center">-</td><!-- 心脏病 -->
                        <td id="numHeartAbnormal" class="text-center">-</td><!-- 心功能异常 -->
                        <td id="numHematopathy" class="text-center">-</td><!-- 血液病 -->
                        <td id="numAnemia" class="text-center">-</td><!-- 贫血 -->
                        <td id="numHepatitis" class="text-center">-</td><!-- 肝炎 -->
                        <td id="numAusAntiPositive" class="text-center">-</td><!-- 澳抗阳性 -->
                        <td id="numViralInfection" class="text-center">-</td><!-- 病毒感染 -->
                        <td id="numThyroid" class="text-center">-</td><!-- 甲状腺 -->
                        <td id="numSLE" class="text-center">-</td><!-- SLE -->
                        <td id="numOtherInternalComplication" class="text-center">-</td><!-- 其他内科并发症 -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" rowspan="3">新生儿情况</td>
                        <td class="text-center" colspan="3">新生儿性别</td>
                        <td class="text-center" colspan="9">新生儿体重</td>
                    </tr>
                    <tr class="active">
                        <td class="text-center">男</td>
                        <td class="text-center">女</td>
                        <td class="text-center">不明</td>
                        <td class="text-center"><1000</td>
                        <td class="text-center">1000-1499</td>
                        <td class="text-center">1500-1999</td>
                        <td class="text-center">2000-2499</td>
                        <td class="text-center">2500-2999</td>
                        <td class="text-center">3000-3499</td>
                        <td class="text-center">3500-3999</td>
                        <td class="text-center">4000-4499</td>
                        <td class="text-center">>=4500</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numNewBirthSexMale" class="text-center">-</td><!-- 新生儿性别-男 -->
                        <td id="numNewBirthSexFemale" class="text-center">-</td><!-- 女 -->
                        <td id="numUnknow" class="text-center">-</td><!-- 不明 -->

                        <td id="numNewBirthWeight1000" class="text-center">-</td><!-- 新生儿体重-<1000 -->
                        <td id="numNewBirthWeight10001499" class="text-center">-</td><!-- 1000-1499 -->
                        <td id="numNewBirthWeight15001999" class="text-center">-</td><!-- 1500-1999 -->
                        <td id="numNewBirthWeight20002499" class="text-center">-</td><!-- 2000-2499 -->
                        <td id="numNewBirthWeight25002999" class="text-center">-</td><!-- 2500-2999 -->
                        <td id="numNewBirthWeight30003499" class="text-center">-</td><!-- 3000-3499 -->
                        <td id="numNewBirthWeight35003999" class="text-center">-</td><!-- 3500-3999 -->
                        <td id="numNewBirthWeight40004499" class="text-center">-</td><!-- 4000-4499 -->
                        <td id="numNewBirthWeight4500" class="text-center">-</td><!-- >4500 -->
                    </tr>
                    <tr class="active">
                        <td class="text-center" rowspan="3">新生儿并发症</td>
                        <td class="text-center" colspan="2">低体重儿</td>
                        <td class="text-center" colspan="2">窒息</td>
                        <td class="text-center" rowspan="2">SGA</td>
                        <td class="text-center" rowspan="2">RDS</td>
                        <td class="text-center" rowspan="2">肺炎</td>
                        <td class="text-center" rowspan="2" style="color:grey">畸形</td>
                        <td class="text-center" rowspan="2" style="color:grey">感染</td>
                        <td class="text-center" rowspan="2" style="color:grey">产伤</td>
                        <td class="text-center" rowspan="2">颅内出血</td>
                        <td class="text-center" rowspan="2" style="color:grey">遗传病</td>
                        <td class="text-center" rowspan="2">死胎</td>
                        <td class="text-center" rowspan="2">死产</td>
                        <td class="text-center" rowspan="2">新生儿死亡</td>
                    </tr>
                    <tr class="active">
                        <td class="text-center"><2500</td>
                        <td class="text-center"><1500</td>
                        <td class="text-center">轻度</td>
                        <td class="text-center">重度</td>
                    </tr>
                    <tr class="tdfont">
                        <td id="numLowWeight2500" class="text-center">-</td><!-- 低体重儿-<2500 -->
                        <td id="numLowWeight1500" class="text-center">-</td><!-- <1500 -->

                        <td id="numAsphyxiaLight" class="text-center">-</td><!-- 窒息-轻度 -->
                        <td id="numAsphyxiaSerious" class="text-center">-</td><!-- 重度 -->

                        <td id="numSGA" class="text-center">-</td><!-- SGA -->
                        <td id="numRDS" class="text-center">-</td><!-- RDS -->
                        <td id="numPneumonia" class="text-center">-</td><!-- 肺炎 -->
                        <td id="numMalformation" class="text-center">-</td><!-- 畸形 -->
                        <td id="numInfection" class="text-center">-</td><!-- 感染 -->
                        <td id="numBirthHarm" class="text-center">-</td><!-- 产伤 -->
                        <td id="numBrainBlood" class="text-center">-</td><!-- 颅内出血 -->
                        <td id="numHereditaryDisease" class="text-center">-</td><!-- 遗传病 -->
                        <td id="numDeadFetus" class="text-center">-</td><!-- 死胎 -->
                        <td id="numDeadBirth" class="text-center">-</td><!-- 死产 -->
                        <td id="numNewBirthDead" class="text-center">-</td><!-- 新生儿死亡 -->
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane fade" id="tabResult4">
                <div class="form-inline" style="margin-top:7px;margin-left:7px;">
                    <select class="form-control" style="padding: 3px;" disabled>
                        <option>选择统计年份（多选）</option>
                    </select>
                    <button class="btn btn-primary" disabled>统计</button>
                </div>
                <table class="table table-bordered no-bottom" style="margin-top:7px;">
                    <thead>
                    <tr class="active">
                        <th class="text-center" width="30%">门诊建设指标</th>
                        <th class="text-center">达标标准</th>
                        <th class="text-center">2017年 院内实际指标</th>
                        <th class="text-center">2018年 院内实际指标</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="text-center">孕期营养课程覆盖率</td>
                        <td class="text-center">>=80%</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    <tr>
                        <td class="text-center">孕妇营养课堂满意率</td>
                        <td class="text-center">>=85%</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    <tr>
                        <td class="text-center">孕期营养门诊出诊情况</td>
                        <td class="text-center">>=2个单元/周</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    <tr>
                        <td class="text-center">营养高危因素孕妇管理率</td>
                        <td class="text-center">>80%</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    </tbody>
                </table>
                <div class="form-inline" style="margin-top:7px; margin-left: 7px;">
                    <select class="form-control" disabled>
                        <option>选择起始年份</option>
                    </select>
                    <select class="form-control" disabled>
                        <option>选择终止年份</option>
                    </select>
                    <select class="form-control" disabled>
                        <option>选择统计营养相关妊娠结局</option>
                    </select>
                    <button class="btn btn-primary" disabled>统计</button>
                </div>
                <table class="table table-bordered no-bottom" style="margin-top:7px;">
                    <tbody>
                    <tr class="active">
                        <th class="text-center" rowspan="2" width="30%">营养相关妊娠结局发生率</th>
                        <th class="text-center" colspan="4">发生率</th>
                    </tr>
                    <tr class="active">
                        <th class="text-center">2015年</th>
                        <th class="text-center">2016年</th>
                        <th class="text-center">2017年</th>
                        <th class="text-center">2018年</th>
                    </tr>
                    <tr>
                        <td class="text-center">巨大儿发生率</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    <tr>
                        <td class="text-center">贫血发生率</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    <tr>
                        <td class="text-center">小于胎龄儿发生率</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    <tr>
                        <td class="text-center">营养因素剖宫产率</td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!--  分娩结局modal-->
<div id="birthEndingModal" class="modal fade bs-example-modal">
    <div class="modal-dialog" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-body">
                <form id="queryBirthEndingForm" action="${common.basepath }/${applicationScope.URL.BirthEnding.BIRTHENDING_GETLIST}">
                    <input type="hidden" name="custId"/>
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left"><i class="fa fa-search"></i>分娩记录<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                        <div class="panel panel-default" style="margin-bottom: 0px;">
                            <div class="table-responsive">
                                <table id="birthEndingTable" class="table table-bordered table-hover">
                                    <thead>
                                    <tr class="active">
                                        <th class="text-center">序号</th>
                                        <th class="text-center">分娩日期</th>
                                        <th class="text-center">分娩孕周</th>
                                        <th class="text-center">分娩医院</th>
                                        <th class="text-center">产检医院</th>
                                        <th class="text-center">登记时间</th>
                                        <th class="text-center">登记人员</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>
