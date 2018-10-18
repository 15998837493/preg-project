<%--
  Created by IntelliJ IDEA.
  User: ligp
  Date: 2018/9/5
  Time: 下午3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo_main.css" />
<script type="text/javascript">
    //取消回车事件
    $(document).keydown(function(event){
        switch(event.keyCode){
            case 13:return false;
        }
    });

    // 分娩结局信息
    var birthEndingPojo = "${birthEndingPojo }";
    var customer = "${customerPojo }";
    var custId = "${customerPojo.custId }";
    var customerBirthday = "${customerPojo.custBirthday }";
    var insPojo = ${insPojo };
    var archivesList = "${archivesList }";
    //补充剂自动补全数组
    var productAllMap = ${diseaseList};
    var productListData = [];
    if(!_.isEmpty(productAllMap)){
        for(var key in productAllMap){
            var product = productAllMap[key];
            productListData.push({name:product.diseaseName, value:product});
        };
    }
    var hospitalMap = ${hospitalList };
    var hospitalListData = [];
    if(!_.isEmpty(hospitalMap)){
        for(var key in hospitalMap){
            var hospital = hospitalMap[key];
            hospitalListData.push({name:hospital.hospitalName, value:hospital});
        };
    }
    var birthDirectionMap = ${codeList}
    var birthDirectionListData = [];
    if(!_.isEmpty(birthDirectionMap)){
        for(var key in birthDirectionMap){
            var birthDirection = birthDirectionMap[key];
            birthDirectionListData.push({name:birthDirection.codeName, value:birthDirection});
        };
    }
</script>
<body>

<!-- 基础信息 -->
<div class="col-xs-1 mytitle-left"></div>
<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i><span>基础信息</span></div>
<div class="panel panel-lightblue col-xs-12 padding-zero" id="birthEnding_div">
    <div class="table-responsive panel-body" id="birthEndingInfo_div">
        <form id="birthEndingUpdateForm" class="no-bottom">
            <input type="hidden" name="archivesId" id="archivesId" value="${birthEnding.archivesId }" />
            <input type="hidden" name="custId" id="custId" value="${customerPojo.custId }" />
            <input type="hidden" name="birthId" id="birthId" value="${birthEnding.birthId }" />
            <input type="hidden" id="birthAge_hidden" name="birthAge" />
            
            <div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left"><span>出生日期</span></div>
			            <input id="birthBirthday" name="birthBirthday" type="text" class="intake-input form-control" maxlength="10" placeholder="请选择出生日期" readonly onclick="common.dateShow('birthBirthday')" />
			        </div>
			    </div>
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left"><span>住院日期</span></div>
			            <input id="birthHospitalDate" name="birthHospitalDate" type="text" class="intake-input form-control" maxlength="10" placeholder="请选择住院日期" readonly onclick="common.dateShow('birthHospitalDate')" />
			        </div>
			    </div>
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left"><span>住院号</span></div>
			            <input type="text" id="birthPatientId" name="birthPatientId" class="intake-sm" maxlength="20" />
			        </div>
			    </div>
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left"><span>身高</span></div>
			            <input type="text" id="birthHeight" name="birthHeight" value="" class="intake-input" maxlength="7" onkeyup="checkNum(this);"/><div class="input-group-addon">cm</div>
			        </div>
			    </div>
			</div>
			<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left"><span>孕前体重</span></div>
			            <input type="text" id="birthWeight" name="birthWeight" class="intake-input" maxlength="7" onkeyup="checkNumPoint(this);"/><div class="input-group-addon">kg</div>
			        </div>
			    </div>
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left redClass"><span>受孕方式</span></div>
			            <select id="birthTiresType" name="birthTiresType" class="intake-sm" onchange="checkBirthTiresType()">
			                <option value="">请选择</option>
			                <option value="1">自然受孕</option>
			                <option value="2">辅助生殖</option>
			            </select>
			            <div class="input-group-addon addon-center"></div>
			            <select id="birthTiresType2" name="birthTiresType2" class="intake-sm">
			                <option value="">请选择</option>
			                <option value="1">意外妊娠</option>
			                <option value="2">计划妊娠</option>
			            </select>
			        </div>
			    </div>
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left redClass"><span>胎数</span></div>
			            <input type="text" id="birthTiresNumber" name="birthTiresNumber" class="intake-sm" maxlength="10" onkeyup="checkNum(this);" />
			        </div>
			    </div>
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left redClass"><span>孕</span></div>
			            <input type="text" id="birthPregNumber" name="birthPregNumber" class="intake-sm" maxlength="10" onkeyup="checkNum(this);" onblur="checkPregBorn();" />
			            <div class="input-group-addon addon-left redClass" style="min-width: 28px;">产 </div>
			            <input type="text" id="birthBornNumber" name="birthBornNumber" class="intake-sm" maxlength="10" onkeyup="checkNum(this);" onblur="checkPregBorn();" />
			        </div>
			    </div>
			</div>
			<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left">产检医院</div>
			            <label class="input-group-addon addon-left" style="min-width: 57px;"><input type="checkbox" id="birthIsPregHospital" onchange="checkHospital()"/> 本院</label>
			            <div class="input-group-addon addon-center"></div>
			            <input type='hidden' id='birthPregHospital_hidden' name="birthIsPregHospital"/>
			            <input type="text" id="birthPregHospital" name="birthPregHospital" class='intake-sm' placeholder="请选择产检医院" onchange="checkHospital()" />
			        </div>
			    </div>
			    <div class="col-xs-3 padding-zero">
			        <div class="input-group">
			            <div class="input-group-addon addon-left"><span>分娩医院</span></div>
			            <label class="input-group-addon addon-left" style="min-width: 57px;"><input type="checkbox" id="birthIsThisHospital" onchange="checkHospital()"/> 本院</label>
			            <div class="input-group-addon addon-center"></div>
			            <input type='hidden' id='birthHospital_hidden' name="birthIsThisHospital"/>
			            <input type="text" id="birthHospital" name="birthHospital" class='intake-sm' placeholder="请选择分娩医院" onchange="checkHospital()" />
			        </div>
			    </div>
			</div>
			<div class="col-xs-12 padding-zero">
				<div class="input-group">
				    <div class="input-group-addon addon-left">备注</div>
				    <textarea id="birthBaseRemark" name="birthBaseRemark" class="form-control" maxlength="200" placeholder="点击输入内容" maxlength="100"></textarea>
				</div>
			</div>
        </form>
    </div>
</div>
<!-- 入院诊断 -->
<div class="col-xs-1 mytitle-left"></div>
<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i><span>入院诊断</span></div>
<div class="panel panel-lightblue col-xs-12 padding-zero" id="dischargeIn_div">
    <div class="table-responsive" id="birthDiagnosis_info_div">
        <form id="birthDiagnosisForm" class="no-bottom">
            <table id="bodyStatusTable" class="table table-bordered table-padding-4 no-bottom">
	            <tr>
	            	<td class="thirdTitle">入院诊断</td>
	            	<td>
	            		<div class="col-xs-12 form-inline padding-zero" id="diagnosis_div" name="diseases">
		                	<div class="col-xs-3 input-group padding-zero">
		                		<input type='hidden' id='birthDiagnosis_hidden' name="birthDiagnosis" class='input-sm' />
		                		<input id="birthDiagnosis" type="text" class="intake-sm" placeholder="点击输入内容" />
		                	</div>
		                </div>
	            	</td>
	            </tr>
	            <tr>
	            	<td class="thirdTitle">备注</td>
	            	<td><textarea id="birthDiagRemark" name="birthDiagRemark" class="form-control" maxlength="200" placeholder="点击输入内容" maxlength="100"></textarea></td>
	            </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>
