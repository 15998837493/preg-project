<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<title></title>
<script type="text/javascript">
var arechives = ${pregPojo};
var diagnosisData  = ${customerPojo};

document.title = "孕期建档信息--"+diagnosisData.custName;
var codeMap=new Map();
codeMap.set("custJob","CUSTJOB");//职业
codeMap.set("custSex","SEX");//性别
codeMap.set("custEducation","CUSTEDUCATION");//学历
codeMap.set("custIncome","CUSTINCOME");//家庭收入

var unitMap=new Map();
unitMap.set("diagnosisCustHeight","cm");
unitMap.set("diagnosisCustWaistline","cm");
unitMap.set("diagnosisCustWeight","kg");
unitMap.set("custBirthWeight","kg");
unitMap.set("custWaistline","cm");
unitMap.set("custHeight","cm");
unitMap.set("custWeight","kg");
unitMap.set("height","cm");
unitMap.set("weight","kg");

$().ready(function() {
	pregnancyArchivesModel();
});

function pregnancyArchivesModel(){
		//给基本信息赋值
		if(!common.isEmpty(diagnosisData)){
			$.each(diagnosisData, function(key, val) {
				if(common.isEmpty(val)){
					val="无";
				}else if(unitMap.get(key)){
					val=val+unitMap.get(key);
				}
				if(codeMap.get(key)){
					val=CODE.transCode(codeMap.get(key),val);
					if(common.isEmpty(val)){
						val="无";
					}
				}
				$("#diagnosis_"+key).html(val);
			});	
		}
		//给孕期信息赋值
		if(!common.isEmpty(arechives)){
			$.each(arechives, function(key, val) {
				if(common.isEmpty(val)){
					val="无";
				}else if(unitMap.get(key)){
					val=val+unitMap.get(key);
				}
				$("#arechives_"+key).html(val);
			});	
		}
};
</script>
<body>
<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-body form-horizontal">
					<div class="panel panel-lightblue">
                        <div class="panel-heading text-left">
                           	<i class="fa fa-tag fa-fw"></i>基本信息
                        </div>
                        <div class="table-responsive">
                            <table summary=" 一、基本信息" class="table table-bordered">
                                <tr>
                                    <td class='text-right active' style="width: 15%">病案号</td>
                                    <td class='text-left' id="diagnosis_custSikeId"></td>
                                    <td  class='text-right active' style="width: 10%">姓名</td>
                                    <td class='text-left' id="diagnosis_custName" style="width: 10%"></td>
                                    <td class='text-right active' style="width: 10%">性别</td>
                                    <td class='text-left' id="diagnosis_custSex" style="width: 10%"></td>
                                    <td class='text-right active' style="width: 10%">年龄</td>
                                    <td class='text-left' id="diagnosis_custAge" style="width: 10%"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">身份证号</td>
                                    <td colspan="3" id="diagnosis_custIcard"></td>
                                    <td class='text-right active' style="width: 10%">职业</td>
                                    <td class='text-left' id="diagnosis_custJob"></td>
                                    <td class='text-right active' style="width: 10%">文化程度</td>
                                    <td class='text-left' id="diagnosis_custEducation"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">家庭收入</td>
                                    <td class='text-left'id="diagnosis_custIncome"></td>
                                    <td class='text-right active' style="width: 10%">手机号</td>
                                    <td class='text-left' id="diagnosis_custPhone"></td>
                                    <td class='text-right active' style="width: 10%">出生体重</td>
                                    <td class='text-left' id="diagnosis_custBirthWeight"></td>
                                    <td class='text-right active' style="width: 10%">孕前体重</td>
                                    <td class='text-left' id="arechives_weight"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">身高</td>
                                    <td class='text-left'id="arechives_height"></td>
                                    <td class='text-right active' style="width: 10%">孕期腰围</td>
                                    <td class='text-left' id="arechives_waistline"></td>
                                    <td class='text-right active' style="width: 10%">预产期</td>
                                    <td class='text-left' id="arechives_pregnancyDueDate"></td>
                                    <td class='text-right active' style="width: 10%">建档日期</td>
                                    <td class='text-left' id="arechives_createDatetime"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left">
                           	<i class="fa fa-tag fa-fw"></i>妊娠信息
                        </div>
                        <div class="table-responsive">
                            <table summary="二、妊娠信息"  class="table table-bordered">
                                <tr>
                                    <td class='text-right active' style="width: 15%">妊娠情况</td>
                                    <td class='text-left' id="arechives_encyesisSituation"></td>
                                    <td class='text-right active' style="width: 15%">妊娠胚胎数</td>
                                    <td class='text-left' id="arechives_embryoNum"></td>
                                </tr>
<!--                                 <tr> -->
<!--                                     <td class='text-right active' style="width: 15%">孕前叶酸服用情况</td> -->
<!--                                     <td colspan="3" class='text-left' id="arechives_folicSituation"></td> -->
<!--                                 </tr> -->
                                <tr>
                                    <td class='text-right active' style="width: 15%">孕早期存在情况</td>
                                    <td colspan="3" class='text-left' id="arechives_firstTrimesterSituation"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left">
                           	<i class="fa fa-tag fa-fw"></i>月经情况
                        </div>
                        <div class="table-responsive">
                            <table summary="三、月经情况"  class="table table-bordered">
                                <tr>
                                    <td class='text-right active' style="width: 15%">初潮年龄</td>
                                    <td class='text-left' id="arechives_menarcheAge"></td>
                                    <td class='text-right active' style="width: 15%">月经持续平均天数</td>
                                    <td class='text-left' id="arechives_mensesDays"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">月经周期</td>
                                    <td class='text-left' id="arechives_mensesCycle"></td>
                                    <td class='text-right active' style="width: 15%">每次的月经量</td>
                                    <td class='text-left' id="arechives_mensesVolume"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">痛经的程度</td>
                                    <td colspan="3" class='text-left' id="arechives_dysmenorrheaExtent"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left">
                           	<i class="fa fa-tag fa-fw"></i>病史信息
                        </div>
                        <div class="table-responsive">
                            <table summary="四、病史信息"  class="table table-bordered">
                                <tr>
                                    <td class='text-right active' style="width: 15%">病史</td>
                                    <td class='text-left' id="arechives_diseaseHistory"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">家族病史</td>
                                    <td class='text-left' id="arechives_familyHistory"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">用药史</td>
                                    <td class='text-left' id="arechives_medicineHistory"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">过敏史</td>
                                    <td class='text-left' id="arechives_allergyHistory"></td>
                                </tr>                                
                                <tr>
                                    <td class='text-right active' style="width: 15%">手术等治疗历史</td>
                                    <td class='text-left' id="arechives_treatmentHistory"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">高碘或造影剂接触史</td>
                                    <td class='text-left' id="arechives_iodineNum"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">糖尿病病史或存在糖尿病前期状态</td>
                                    <td class='text-left' id="arechives_diabetesRelevant"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left">
                           	<i class="fa fa-tag fa-fw"></i>既往分娩史
                        </div>
                        <div class="table-responsive">
                            <table summary="五、既往分娩史"  class="table table-bordered">
                                <tr >
                                    <td class='text-right active' style="width: 15%">孕次</td>
                                    <td class='text-left' id="arechives_pregnancyNum"></td>
                                    <td class='text-right active' style="width: 15%">产次</td>
                                    <td class='text-left' id="arechives_birthNum"></td>
                                    <td class='text-right active' style="width: 15%">自然流产</td>
                                    <td class='text-left' id="arechives_abortionNum"></td>
                                    <td class='text-right active' style="width: 15%">胎停育</td>
                                    <td class='text-left' id="arechives_childStopNum"></td>
                                </tr>
                                <tr>
                                    <td class='text-right active' style="width: 15%">早产</td>
                                    <td class='text-left' id="arechives_pretermNum"></td>
                                    <td class='text-right active' style="width: 15%">中期引产</td>
                                    <td class='text-left' id="arechives_odinopoeiaNum"></td>
                                    <td class='text-right active' style="width: 15%">足月分娩</td>
                                    <td class='text-left' id="arechives_childbirthNum"></td>
                                    <td class='text-right active' style="width: 15%">巨大儿分娩</td>
                                    <td class='text-left' id="arechives_giantBabyNum"></td>
                                </tr>
                                <tr>
                               		<td class='text-right active' style="width: 15%">剖宫产</td>
                                    <td class='text-left' id="arechives_planePalaceNum"></td>
                                    <td class='text-right active' style="width: 15%">畸形</td>
                                    <td class='text-left' id="arechives_malformationNum"></td>
                                    <td class='text-right active' style="width: 15%">既往妊娠并发症</td>
                                    <td colspan="5" class='text-left' id="arechives_pregnancyComplications"></td>
                                </tr>
                            </table>
                        </div>
					</div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
</body>
</html>