<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>食材增加</title>
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet" type="text/css"  href="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.css">
<link type=text/css rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />
<link rel="stylesheet" type="text/css"  href="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.css">

<script type="text/javascript" src="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript"  src="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
<script type="text/javascript">
/**
 * 验证参数设置
 */
var fmOperation = {
	rules : {
		fmName : {
			required : true,
			maxlength : 80
		},
		fsId : {
			required : true
		},
		fmType : {
			required : true
		},
		fmLevel : {
			required : true
		},
		fmEsculent : {
			required : true,
			number : true
		},
		fmEnergy : {
			required : true,
			number : true
		},
		fmProtid : {
			required : true,
			number : true
		},
		fmFat : {
			required : true,
			number : true
		},
		fmSfa : {
			required : true,
			number : true
		},
		fmMfa : {
			required : true,
			number : true
		},
		fmPfa : {
			required : true,
			number : true
		},
		fmCbr : {
			required : true,
			number : true
		},
		fmDf : {
			required : true,
			number : true
		},
		fmAshc : {
			required : true,
			number : true
		},
		fmVa : {
			required : true,
			number : true
		},
		fmCarotin : {
			required : true,
			number : true
		},
		fmCho : {
			required : true,
			number : true
		},
		fmVb1 : {
			required : true,
			number : true
		},
		fmVb2 : {
			required : true,
			number : true
		},
		fmAf : {
			required : true,
			number : true
		},
		fmVc : {
			required : true,
			number : true
		},
		fmVe : {
			required : true,
			number : true
		},
		fmEca : {
			required : true,
			number : true
		},
		fmEp : {
			required : true,
			number : true
		},
		fmEk : {
			required : true,
			number : true
		},
		fmEna : {
			required : true,
			number : true
		},
		fmEmg : {
			required : true,
			number : true
		},
		fmEfe : {
			required : true,
			number : true
		},
		fmEzn : {
			required : true,
			number : true
		},
		fmEse : {
			required : true,
			number : true
		},
		fmEcu : {
			required : true,
			number : true
		},
		fmEmn : {
			required : true,
			number : true
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function() {
		var picPath = $("#fmPic").val();
		if(picPath == "" || picPath == null){
			layer.alert("请上传图片");
			return;
		}
		layer.confirm("确定要执行【保存】操作？",function(data) {
			if (data) {$('#addFmForm').ajaxPost(dataType.json,function(data) {
					common.pageForward(PublicConstant.basePath+ 
							"/page/master/basic/food/food_material_view.jsp");
				});
			}
		});
	}
};

$().ready(function() {
		$("#addFmForm").validate(fmOperation);
	
		//标签插件初始化 请输入食品别名
		$("[name='fmAlias']").tagEditor({
	       initialTags: [], 
	       delimiter: ', ',
	       autocomplete: {
		         delay: 0,
		         position: { collision: 'flip' }, 
		         source: [] },
	       placeholder: '请输入别名' }).css('display', 'block').attr('readonly', true);
	    $(".tag-editor").addClass("form-control");
     
		
		//加入必填项提示
		common.requiredHint("addFmForm", fmOperation);
		//初始化食物种类
		common.initCodeSelect("FOOD_SPECIES", "FOOD_SPECIES", "fsId");
		//初始化食材类型
		common.initCodeSelect("FOOD_MATERIAL_TYPE","FOOD_MATERIAL_TYPE", "fmType");

		// 初始化 uploadifive 上传控件
		common.uploadifive("上传食材图片", function(data) {
// 			var food = JSON.parse(data);
			$('#fmPic').val(data);
			$('#showpic').attr("src", data);
		});

		$("#backButton").click(function() {
			common.pageForward(PublicConstant.basePath + "/page/food/food_material_query.action");
		});
});
</script>
<body>
	<form
		action="${common.basepath }/${applicationScope.URL.Master.FOOD_MATERIAL_ADD}"
		id="addFmForm" name="addFmForm" method="post" class="form-horizontal">
		<div class="container-fluid">
			<div class="row row-top">
				<div class="col-xs-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<i class="fa fa-filter fa-fw"></i>食材信息
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-condensed">
									<tbody>
										<tr>
											<td rowspan="4"><label class="control-label" for="图片">图片</label></td>
											<td rowspan="4">
												<img class="img-thumbnail" style="width:100px;height:100px;margin-bottom:5px;" id="showpic" />
												<input type="file" name="file_upload"id="file_upload" />
												<input type="hidden" name="fmPic" id="fmPic" />
											</td>
											<td><label class="control-label text-right" for="食物名称">食物名称</label></td>
											<td><input type="text" class="form-control" id="fmName"
												name="fmName" placeholder="请输入食物名称"  data-toggle="tags"/></td>
											<td><label class="control-label text-right" for="食品别名">食品别名</label></td>
											<td><input type="hidden" class="form-control"
												name="fmAlias" placeholder="请输入食品别名,号分割" /></td>
										</tr>
										<tr>
											<td><label class="control-label" for="食物种类">膳食结构</label></td>
											<td><select id="fsId" name="fsId" class="form-control"></select>
											</td>
											<td><label class="control-label" for="食物种类">食物种类</label></td>
											<td><select id="fmType" name="fmType"
												class="form-control"></select></td>
										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="是否属于优质蛋白质">是否属于优质蛋白质</label></td>
											<td><label class="radio-inline"><input
													type="radio" id="protid1" name="fmProtidFlag" value="yes"
													checked />是</label> <label class="radio-inline"><input
													type="radio" id="protid2" name="fmProtidFlag" value="no" />否</label>
											</td>
											<td><label class="control-label text-right" for="脂肪类型">脂肪类型</label></td>
											<td><label class="radio-inline"><input
													type="radio" id="fat1" name="fmFatType" value="plant"
													checked />植物型</label> <label class="radio-inline"><input
													type="radio" id="fat2" name="fmFatType" value="animal" />动物型</label>
												<label class="radio-inline"><input type="radio"
													id="fat3" name="fmFatType" value="no" />无</label></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="GI值">GI值</label></td>
											<td><input type="text" class="form-control" name="fmGi"
												value="100" placeholder="请输入GI值" /></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="简介">简介</label></td>
											<td><textarea class="form-control" name="fmDesc"
													placeholder="请输入简介"></textarea></td>
											<td><label class="control-label text-right" for="功效">功效</label></td>
											<td><textarea class="form-control" name="fmEfficacy"
													placeholder="请输入功效"></textarea></td>
											<td><label class="control-label" for="适用人群">适用人群</label></td>
											<td><textarea class="form-control" name="fmArea"
													placeholder="请输入适用人群"></textarea></td>
										</tr>
										<tr>

										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<i class="fa fa-table fa-fw"></i>食材元素
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-condensed">
									<tbody>
										<tr>
											<td><label class="control-label text-right" for="食物级别">食物级别</label></td>
											<td><select id="fmLevel" name="fmLevel"
												class="form-control">
													<option value="">==食物级别==</option>
													<option value="green">绿色</option>
													<option value="yellow">黄色</option>
													<option value="red">红色</option>
											</select></td>
											<td><label class="control-label text-right"
												for="可食用部分（%）">可食用部分（%）</label></td>
											<td><input type="text" class="form-control"
												id="fmEsculent" name="fmEsculent" value="100"
												placeholder="请输入可食用部分（%）" /></td>
											<td><label class="control-label" for="能量_kcal">能量_kcal</label></td>
											<td><input type="text" class="form-control"
												id="fmEnergy" name="fmEnergy" value="0"
												placeholder="请输入能量_kcal" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="蛋白质(g)">蛋白质(g)</label></td>
											<td><input type="text" class="form-control"
												id="fmProtid" name="fmProtid" value="0"
												placeholder="请输入蛋白质(g)" /></td>
											<td><label class="control-label text-right" for="脂肪(g)">脂肪(g)</label></td>
											<td><input type="text" class="form-control" id="fmFat"
												name="fmFat" value="0" placeholder="请输入脂肪(g)" /></td>
											<td><label class="control-label text-right"
												for="饱和脂肪酸(g)">饱和脂肪酸(g)</label></td>
											<td><input type="text" class="form-control" id="fmSfa"
												name="fmSfa" value="0" placeholder="请输入饱和脂肪酸(g)" /></td>

										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="单不饱和脂肪酸(g)">单不饱和脂肪酸(g)</label></td>
											<td><input type="text" class="form-control" id="fmMfa"
												name="fmMfa" value="0" placeholder="请输入单不饱和脂肪酸(g)" /></td>
											<td><label class="control-label" for="多不饱和脂肪酸(g)">多不饱和脂肪酸(g)</label></td>
											<td><input type="text" class="form-control" id="fmPfa"
												name="fmPfa" value="0" placeholder="请输入多不饱和脂肪酸(g)" /></td>
											<td><label class="control-label" for="碳水化合物(g)">碳水化合物(g)</label></td>
											<td><input type="text" class="form-control" id="fmCbr"
												name="fmCbr" value="0" placeholder="请输入碳水化合物(g)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="膳食纤维(g)">膳食纤维(g)</label></td>
											<td><input type="text" class="form-control" id="fmDf"
												name="fmDf" value="0" placeholder="请输入膳食纤维(g)" /></td>
											<td><label class="control-label" for="灰分(g)">灰分(g)</label></td>
											<td><input type="text" class="form-control" id="fmAshc"
												name="fmAshc" value="0" placeholder="请输入灰分(g)" /></td>
											<td><label class="control-label text-right"
												for="维生素A(ug)">维生素A(ug)</label></td>
											<td><input type="text" class="form-control" id="fmVa"
												name="fmVa" value="0" placeholder="请输入维生素A(ug)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="胡萝卜素(μg)">胡萝卜素(μg)</label></td>
											<td><input type="text" class="form-control"
												id="fmCarotin" name="fmCarotin" value="0"
												placeholder="请输入胡萝卜素(μg)" /></td>
											<td><label class="control-label text-right"
												for="胆固醇(mg)">胆固醇(mg)</label></td>
											<td><input type="text" class="form-control" id="fmCho"
												name="fmCho" value="0" placeholder="请输入胆固醇(mg)" /></td>
											<td><label class="control-label" for="维生素B1(mg)">维生素B1(mg)</label></td>
											<td><input type="text" class="form-control" id="fmVb1"
												name="fmVb1" value="0" placeholder="请输入维生素B1(mg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="维生素B2(mg)">维生素B2(mg)</label></td>
											<td><input type="text" class="form-control" id="fmVb2"
												name="fmVb2" value="0" placeholder="请输入维生素B2(mg)" /></td>
											<td><label class="control-label text-right" for="烟酸(mg)">烟酸(mg)</label></td>
											<td><input type="text" class="form-control" id="fmAf"
												name="fmAf" value="0" placeholder="请输入烟酸(mg)" /></td>
											<td><label class="control-label" for="维生素C">维生素C</label></td>
											<td><input type="text" class="form-control" id="fmVc"
												name="fmVc" value="0" placeholder="请输入维生素C" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="维生素E">维生素E</label></td>
											<td><input type="text" class="form-control" id="fmVe"
												name="fmVe" value="0" placeholder="请输入维生素E" /></td>
											<td><label class="control-label text-right" for="钙(mg)">钙(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEca"
												name="fmEca" value="0" placeholder="请输入钙(mg)" /></td>
											<td><label class="control-label" for="磷(mg)">磷(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEp"
												name="fmEp" value="0" placeholder="请输入磷(mg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="钾(mg)">钾(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEk"
												name="fmEk" value="0" placeholder="请输入钾(mg)" /></td>
											<td><label class="control-label text-right" for="钠(mg)">钠(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEna"
												name="fmEna" value="0" placeholder="请输入钠(mg)" /></td>
											<td><label class="control-label" for="镁(mg)">镁(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEmg"
												name="fmEmg" value="0" placeholder="请输入镁(mg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="铁(mg)">铁(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEfe"
												name="fmEfe" value="0" placeholder="请输入铁(mg)" /></td>
											<td><label class="control-label text-right" for="锌(mg)">锌(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEzn"
												name="fmEzn" value="0" placeholder="请输入锌(mg)" /></td>
											<td><label class="control-label" for="硒(μg)">硒(μg)</label></td>
											<td><input type="text" class="form-control" id="fmEse"
												name="fmEse" value="0" placeholder="请输入硒(μg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="铜(mg)">铜(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEcu"
												name="fmEcu" value="0" placeholder="请输入铜(mg)" /></td>
											<td><label class="control-label text-right" for="钠(mg)">锰(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEmn"
												name="fmEmn" value="0" placeholder="请输入锰(mg)" /></td>
											<td><label class="control-label text-right" for="胆固醇">胆固醇(mg)</label></td>
											<td><input type="text" class="form-control" id="fmCho"
												name="fmCho" value="0" placeholder="请输入胆固醇(mg)" /></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="form-group text-center">
			<button class="btn btn-primary" type="submit">
				<i class="fa fa-save fa-fw"></i>保存
			</button>
			<button class="btn btn-primary" type="button" id="backButton">
				<i class="fa fa-share-square-o fa-fw"></i>返回
			</button>
		</div>
	</form>
</body>
</html>
