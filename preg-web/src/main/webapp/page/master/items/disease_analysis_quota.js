
//************************************* 诊断项目ztree树配置 *************************************
//当前选中结点
var selectNode;
//树对象
var treeObj;
//树配置
var zTree_1 = {
	beforeClick : function(treeId, treeNode) {
		selectNode = treeNode;
		treeObj.selectNode(treeNode);
		$("#diseaseId").val("");
		$("#itemId").val("");
		if (treeNode.level >= 1 && !treeNode.isParent) {
			$("#t_body").empty();// 清空已选检验项目列表
			$("button[name='diseaseItemPage']").attr("disabled", false);// 动作按钮权限打开
			$("#queryForm input[name='diseaseId']").val(treeNode.value.diseaseId);// 选中诊断项目id（查询条件）
			$("#diseaseId").val(treeNode.value.diseaseId);// 全局选中诊断项目id
			// 高度样式调整
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
			// 加载对应的指标信息
			quotaTable = datatable.table(quotaOptions);
		} else {
			$("button[name='diseaseItemPage']").attr("disabled", true);
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
			$("#t_body").empty();
			$("#t_body").append("<tr><td colspan='100' class='text-center'><h4>请先选择疾病</h4></td></tr>");
			$("#t_foot").hide();
		}
		setDH();
	},
	onCollapse : function() {
		setDH();
	},
	onExpand : function() {
		setDH();
	}
};
var zTree_setting = {
	view : {
		selectedMulti : false
	},
	edit : {
		showRenameBtn : false,
		showRemoveBtn : false
	}
};

//************************************* 疾病推断检验项目列表（主列表） *************************************
var quotaData;
var quotaRow;
var quotaTable;

var quotaOptions = {
	id:"quotaListTable",
	form:"queryForm",
	columns: [
		{"data":null,"sClass":"text-center",
			"render":function (data, type, row, meta) {
      		return "<input type='radio' name='tableRadio' value='"+data.itemId+"' />";
			}
		},
		{"data":"itemName","sClass":"text-left"},
		{"data":"itemClassify","sClass":"text-center"},
		{"data":"itemType","sClass":"text-center"},
		{"data":"itemUnit","sClass":"text-center"}
	],
	rowClick: function(data, row){
		quotaData = data;
		quotaRow = row;
		$("#itemId").val(data.itemId);
	},
	condition : "quotaItemCondition",
	async: false,
	loading: false
};

//************************************* 检验项目推断配置列表（增加列表） *************************************
var itemListTable;
var itemListDataOptions = {
	id : "itemListTable",
	data : itemList,
	columns: [
		{"data": "itemName","sClass": "text-left" },
		{"data": "itemClassify","sClass": "text-center" },
		{"data": "itemType","sClass": "text-center"},
		{"data": "itemUnit","sClass": "text-center"},
		{"data": null,"sClass": "text-center",
			"render":  function (data, type, row, meta) {
        		return "<a onclick='addInspectItemConfig(\""+data.itemId+"\");'>添加</>";
        	}
		}
	],
	condition : "itemCondition"
};

//************************************* 加载数据 *************************************

var deduceCount = 1;// 配置数
var islegal = true; // 数据输入是否合法
$(document).ready(function() {
	// 初始化生成树
	$.fn.zTree.init($("#zTree"), common.zTree(zTree_1, zTree_setting), zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandAll(false);
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	// ztree树拓展搜索框
	common.ztreeSearch(treeObj, zTree_1, "left_div");
	// 调节div高度
	$("[treenode_switch]").die("click").live('click', function() {
		setDH();
	});
	
	// 加载检验项目库数据（添加动作）
	itemListTable = datatable.table(itemListDataOptions);
	
	// 初始化推断组合标签插件（组合动作）
	initTagEditor();
	// 定义指标组合Modal隐藏后触发事件
	common.modal("itemGroupModal", common.modalType.hidden, function(){
		$("#groupContent_div").tagEditor("destroy");// 清空标签
		initTagEditor();// 初始化标签插件
	});

	
	// 按钮点击事件
	$("button[name='diseaseItemPage']").off("click").on("click", function(){
		var diseaseId = $("#diseaseId").val();
		var itemId = $("#itemId").val();
		if(this.id == 'addButton'){
			// 添加修改
			$("#itemName").val("");
			datatable.search(itemListTable, itemListDataOptions);
			$("#inspectItemModal").modal("show");
		} else if(this.id == 'configButton' && common.isChoose(itemId)){// 配置页面
			common.clearForm("itemConfigModal");
			var url = URL.get("item.INSEPCT_ITEM_DISEASE_QUERY");
			var params = "diseaseId=" + diseaseId + "&itemId=" + itemId;
			ajax.post(url, params, dataType.json, function(data){
				if(!common.isEmpty(data.data)){
					deduceCount = 1;
					$("#diseaseItemConfig").empty();
					
					// 1,检验项目名称显示
					$("#configItemName").val(quotaData.itemName);
					
					// 2,配置信息加载
					$.each(data.data,function(index,value){
						if(!_.isEmpty(value.suitableStages)){
							addDiseaseItemConfigHtml(value);
							deduceCount ++;
						}
					});
					$("#itemConfigModal").modal("show");
				}
			}, false, false);
		} else if(this.id == 'groupButton'){
			initItemGroupModal();// 获取所有推断指标并展示出来
		} else if(this.id == 'removeButton' && common.isChoose(itemId)){
			deleteDiseaseItem(diseaseId, itemId);// 删除推断指标
		} else if(this.id == 'searchButton'){
			$("#itemId").val("");
			quotaTable = datatable.table(quotaOptions);
		} else if(this.id == "saveDiseaseItemButton"){
			updateDiseaseItem();// 保存检验项目配置
		} else if(this.id == "saveGroupButton"){
			saveDiseaseItemGroup();
		};
	});
});

/**
 * 增加页面==》列表添加方法
 */
function addInspectItemConfig(itemId) {
	var itemConfigIds = '';
	$("input:radio[name='tableRadio']").each(function(index, obj) {
		itemConfigIds += $(obj).val();// 这里的value就是每一个input的value值~
	});
	// 判断指不存在的话，添加指标
	if (itemConfigIds.indexOf(itemId) < 0) {
		var url = URL.get("item.INSPECT_ITEM_DISEASE_ADD");
		var params = "itemId=" + itemId + "&diseaseId=" + $("#diseaseId").val();
		ajax.post(url, params, dataType.json, function(data) {
			datatable.add(quotaTable, data.value);// 添加
			setDH();
		}, false, false);
	} else {
		layer.msg('不能重复添加相同的指标！');
	}
}

/**
 * 配置页面==》配置html拼接
 */
function addDiseaseItemConfigHtml(deduce){
	if(common.isEmpty(deduce)){
		return;
	}
	// html拼接
	var html = `
		<div class="panel-body form-horizontal deduceClass" style="margin-top:-15px;" id="${deduce.id}">
			<div class="form-group" style="margin-bottom:4px;">
				<label class="col-xs-2 col-xs-offset-1 control-label" style="text-align: left;margin-top: 5px;font-weight: normal;" name="ordernum">配置${deduceCount}</label>
		        <div class="col-xs-2" style="float:right;width:16%;">
		        	<button id="configButton" name="diseaseItemPage" type="button" class="btn btn-primary" style="width:76%;" onclick="removededuce('${deduce.id}')">删  除</button>
		        </div>
			</div>
			<hr style="margin: 0px 32px 15px 70px;"/>
			<div class="form-group">
				<label class="col-xs-3 control-label" style="color:red;">适宜人群</label>
		        <div class="col-xs-7">
		            <select id="suitableStages_${deduce.id}" name="suitableStages" class="form-control">
		            	<option value="">==请选择适宜人群==</option>
		                <option value="0">备孕</option>
		                <option value="1">妊娠</option>
		                <option value="2">哺乳</option>
		            </select>
		        </div>
			</div>
			<div class="form-group suitableStages_div_${deduce.id}" name="suitableStages_div" style="display:none;">
				<label class="col-xs-3 control-label">孕周范围</label>
		        <div class="col-xs-7 form-inline">
		            <input class="form-control autoWidth" id="weekBorderOne_${deduce.id}" name="weekBorder_${deduce.id}" type="text" maxlength="2"/> 
		            - 
		            <input class="form-control autoWidth" id="weekBorderTwo_${deduce.id}" name="weekBorder_${deduce.id}" type="text" maxlength="2"/>
		        	<input type="hidden" id="weekBorder_${deduce.id}" name="weekBorder_${deduce.id}"/>
		        </div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label" style="color:red;">判断标准</label>
		        <div class="col-xs-7">
		            <select id="standard_${deduce.id}" name="standard" class="form-control">
		            <option value="">==请选择判断标准==</option>
		            <option value="0">超标</option>
		            <option value="1">低标</option>
		            <option value="2">范围</option>
		            <option value="3">异常结果</option>
		            </select>
		        </div>
			</div>
			<div class="form-group standard-div_${deduce.id}" name="standard-div" style="display:none;">
				<label class="col-xs-3 control-label" style="color:red;">超标</label>
		        <div class="col-xs-7">
		            <input id="content_GE_${deduce.id}" name="content" class="form-control" type="text" placeholder="请输入数值" maxlength="8" input-valid/>
		        </div>
			</div>
			<div class="form-group standard-div_${deduce.id}" name="standard-div" style="display:none;">
				<label class="col-xs-3 control-label" style="color:red;">低标</label>
		        <div class="col-xs-7">
		            <input id="content_LT_${deduce.id}" name="content" class="form-control" type="text" placeholder="请输入数值" maxlength="8" input-valid/>
		        </div>
			</div>
			<div class="form-group standard-div_${deduce.id}" name="standard-div" style="display:none;">
				<label class="col-xs-3 control-label" style="color:red;">范围</label>
		        <div class="col-xs-7 form-inline">
		            <input class="form-control autoWidth" id="fw_one_${deduce.id}" name="fw_${deduce.id}" type="text" placeholder="请输入下限" maxlength="8" input-valid/>
		            -
		            <input class="form-control autoWidth" id="fw_two_${deduce.id}" name="fw_${deduce.id}" type="text" placeholder="请输入上限" maxlength="8" input-valid/>
		            <input type="hidden" id="content_RANGE_${deduce.id}" name="content"/>
		        </div>
			</div>
			<div class="form-group standard-div_${deduce.id}" name="standard-div" style="display:none;">
				<label class="col-xs-3 control-label" style="color:red;">异常结果</label>
		        <div class="col-xs-7">
		            <input id="content_LIKE_${deduce.id}" name="content" class="form-control" type="text" placeholder="请输入异常结果" maxlength="500"/>
		        </div>
			</div>	
		</div>
	`
	$("#diseaseItemConfig").append(html);
	
	// 数据绑定
	if(deduce.id.indexOf("new") == -1){
		var id = deduce.id || "";
		var suitableStages = deduce.suitableStages;// 适宜阶段（0备孕，1妊娠，2哺乳）
		var weekBorder = deduce.weekBorder || ''; // 孕周范围（备孕时显示）
		var algorithm = deduce.algorithm;// 判断标准（结论类型）（0超标，1低标，2范围，3异常结果）
		var content = deduce.content;// 结论内容（依据判断标准联动）

		// 适宜阶段
		$("#suitableStages_"+id).val(suitableStages);
		if(suitableStages == 1 && !common.isEmpty(weekBorder) ) {// 显示孕周
			$("#weekBorderOne_"+id).val(parseInt(weekBorder.split("-")[0]));
			$("#weekBorderTwo_"+id).val(parseInt(weekBorder.split("-")[1]));
		}
		// 判断标准
		if(!common.isEmpty(algorithm)) {
			if(algorithm == "GE") {// 超标
				$("#standard_"+id).val("0");
				$("#content_GE_"+id).val(content);
			}else if(algorithm == "LT") {// 低标
				$("#standard_"+id).val("1");
				$("#content_LT_"+id).val(content);
			}else if(algorithm == "RANGE") {// 范围
				$("#standard_"+id).val("2");
				if(!common.isEmpty(content)) {
					$("#content_RANGE_"+id).val(content);
					$("#fw_one_"+id).val(content.split("#")[0]);
					$("#fw_two_"+id).val(content.split("#")[1]);
				}
			}else if(algorithm == "LIKE") {// 异常结果
				$("#standard_"+id).val("3");
				$("#content_LIKE_"+id).val(content);
			}
		}
	}
	// 校验配置
	initSuitableStages(deduce.id);
	initStandard(deduce.id);
	initItemConfig(deduce.id);
}

/**
 * 配置页面==》输入校验配置
 */
function initItemConfig(id){
	// 适宜人群联动
	$("#suitableStages_"+id).change(function() {
		// 判断是否重复（适宜人群+判断标准）
		if(validateRepeat(id)){
			layer.msg("已存在相同配置，请重新配置!");
			$("#suitableStages_"+id).val("");
		}
		// 清空输入框内容
		$("#"+id).find("div[name='suitableStages_div'] input").each(function(){
			$(this).val("");
		})
		
		// 数据联动
		initSuitableStages(id);
	});
	
	// 孕周范围
	validateWeekBorder(id);
	
	// 判断标准联动
	$("#standard_"+id).change(function() {
		// 判断是否重复（适宜人群+判断标准）
		if(validateRepeat(id)){
			layer.msg("已存在相同配置，请重新配置!");
			$("#standard_"+id).val("");
		}
		
		// 清空输入框内容
		$("#"+id).find("div[name='standard-div'] input").each(function(){
			$(this).val("");
		})
		
		// 数据联动
		initStandard(id);
	});
	
	// 判断标准==》范围
	validateRange(id);
	
	// 定义校验数值(实时)
	$("#"+id+" input[input-valid]").off("change").on("change", function(){
		this.value = common.checkInputNumber("reg7", this.value);
	});
}

/**
 * 配置页面==》适应人群联动
 */
function initSuitableStages(id) {
	var val = $("#suitableStages_"+id).val();
	if(common.isEmpty(val)){
		$("#"+id).find("div[name='suitableStages_div']").hide();
	}else if(val == 1){ 
		$(".suitableStages_div_"+id).show();
	}else{
		$(".suitableStages_div_"+id).hide();
	}
}

/**
 * 配置页面==》孕周范围
 */
function validateWeekBorder(id) {
	$("input[name='weekBorder_"+id+"']").blur(function() {
		var currentBorder = this.value;
		var reg = /^(\d{1}|[1-9]\d{1})$/g;
		var flag = reg.test(currentBorder);
		if(flag) {
			var borderOne = $("#weekBorderOne_"+id).val();
			var borderTwo = $("#weekBorderTwo_"+id).val();
			// 范围校验
			if(!common.isEmpty(currentBorder)) {
				if(currentBorder < 1 || currentBorder > 40) {
					this.value = "";
					this.focus();
					layer.msg("孕周范围不正确!");
				}
			}
			// 上下限比较
			if(!common.isEmpty(borderOne) && !common.isEmpty(borderTwo)) {
				if(parseInt(borderOne) > parseInt(borderTwo)) {
					this.value = "";
					this.focus();
					layer.msg("孕周范围不正确!");
				}
			}
		}else {
			this.value = "";
		}
	});
}

/**
 * 配置页面==》判断标准联动
 */
function initStandard(id) {
	var val = $("#standard_"+id).val();
	if(common.isEmpty(val)){
		$("#"+id).find("div[name='standard-div']").hide();
	}else{
		$(".standard-div_"+id+":not("+val+")").hide();
		$(".standard-div_"+id+":eq("+val+")").show();
	}
}

/**
 * 配置页面==》判断标准==》范围
 */
function validateRange(id) {
	$("input[name='fw_"+id+"']").blur(function() {
		islegal = true;
		var val_one = $("#fw_one_"+id).val();
		var val_two = $("#fw_two_"+id).val();
		if(this.id == "fw_one_"+id) {
			if(!common.isEmpty(val_two) && !common.isEmpty(val_one)) {
				if(parseFloat(val_one) >= parseFloat(val_two)) {
					$("#fw_one_"+id).val("");
					$("#fw_one_"+id).focus();
					layer.msg("下限值必须小于上限值!");
					islegal = false;
				}
			}
		}else if(this.id == "fw_two_"+id) {
			if(!common.isEmpty(val_two) && !common.isEmpty(val_one)) {
				if(parseFloat(val_two) <= parseFloat(val_one)) {
					$("#fw_two_"+id).val("");
					$("#fw_two_"+id).focus();
					layer.msg("上限值必须大于下限值!");
					islegal = false;
				}
			}
		}
	});
}

/**
 * 配置页面==》必填校验
 */
function validate(){
	var goback = true;
	
	// 遍历获取数据
	$("#diseaseItemConfig").find(".deduceClass").each(function(){
		var id = $(this).attr("id");
		// 适宜人群
		var suitableStages = $("#suitableStages_"+id).val();
		if(_.isEmpty(suitableStages)){
			layer.msg("请选择适宜人群!");
			$(this).find("#suitableStages_"+id).focus();
			goback = false;
			return;
		}
		// 判断标准
		var standard = $("#standard_"+id).val();
		if(_.isEmpty(standard)){
			layer.msg("请选择判断标准!");
			$(this).find("#standard_"+id).focus();
			goback = false;
			return;
		}
		var inputIndex = 0;// 非空焦点定位
		var content_id = "";
		if(standard == "0") {// 超标
			content_id = "content_GE_"+id;
		}else if(standard == "1") {// 低标
			content_id = "content_LT_"+id;
		}else if(standard == "2") {// 范围
			content_id = "content_RANGE_"+id;
			var fwOne = $("#fw_one_"+id).val();
			var fwTwo = $("#fw_two_"+id).val();
			if(!common.isEmpty(fwOne) && !common.isEmpty(fwTwo)) {
				$("#"+content_id).val(fwOne+"#"+fwTwo);// 上限#下限
			}
			
			if(common.isEmpty(fwOne)){
				inputIndex = 0;
			}else if(common.isEmpty(fwTwo)){
				inputIndex = 1;
			}
		}else if(standard == "3") {// 异常结果
			content_id = "content_LIKE_"+id;
		}
		var content = $("#"+content_id).val();// 附属条件
		if(_.isEmpty(content)) {
			layer.msg("请填写"+$("#standard_"+id).find("option:selected").text()+"信息!");
			$(".standard-div_"+id+":eq("+standard+") input:eq("+inputIndex+")").focus();
			goback = false;
			return;
		}
	});
	return goback;
}

/**
 * 配置页面==》重复校验
 */
function validateRepeat(id){
	// 存在相同的配置
	var goback = false;
	if(!common.isEmpty(id)){
		var vals = $("#suitableStages_"+id).val();
		var vala = $("#standard_"+id).val();
		
		// 数据比对
		$("#diseaseItemConfig").find(".deduceClass").each(function(){
			var myid = $(this).attr("id");
			if(id != myid){
				var val1 = $("#suitableStages_"+myid).val();
				var val2 = $("#standard_"+myid).val();
				if(!(_.isEmpty(vals) && _.isEmpty(val1)) && vals == val1 && !(_.isEmpty(vala) && _.isEmpty(val2)) && vala == val2){
					goback = true;
					return goback;
				}
			}
		});
	}
	return goback;
}

/**
 * 配置页面==》添加配置
 */
function addDiseaseItemConfig() {
	// 校验是否有未完善的数据，存在则不允许添加新的配置
	if(!validate()){
		return;
	}
	var deduce = {};
	deduce["id"] = deduceCount + "_new";// 构建新配置标识
	addDiseaseItemConfigHtml(deduce);
	deduceCount ++;// 新加配置计数
}

/**
 * 配置页面==》保存配置内容
 */
function updateDiseaseItem(){
	// 校验数据输入是否符合标准
	if(!islegal){
		return;
	}
	
	// 必填校验
	if(!validate()){
		return;
	}
	// 获取基础数据
	var diseaseId = $("#diseaseId").val();
	var itemId = $("#itemId").val();
	
	var items = [];
	// 遍历获取数据
	$("#diseaseItemConfig").find(".deduceClass").each(function(){
		var item = {};
		var id = $(this).attr("id");
		
		// 适宜人群
		var suitableStages = $("#suitableStages_"+id).val();
		// 孕周数
		if(suitableStages == 1){
			var weekBorderOne = $("#weekBorderOne_"+id).val();
			var weekBorderTwo = $("#weekBorderTwo_"+id).val();
			if(!_.isEmpty(weekBorderOne) && !_.isEmpty(weekBorderTwo)){
				$("#weekBorder_"+id).val((parseInt(weekBorderOne)) + "-" + (parseInt(weekBorderTwo)));// 范围1-范围2
			}
		}else{
			$("#weekBorder_"+id).val("");
		}
		var weekBorder = $("#weekBorder_"+id).val();
		
		// 判断标准
		var standard = $("#standard_"+id).val();
		var content_id = "";
		if(standard == "0") {// 超标
			content_id = "content_GE_"+id;
		}else if(standard == "1") {// 低标
			content_id = "content_LT_"+id;
		}else if(standard == "2") {// 范围
			content_id = "content_RANGE_"+id;
			var fwOne = $("#fw_one_"+id).val();
			var fwTwo = $("#fw_two_"+id).val();
			if(!common.isEmpty(fwOne) && !common.isEmpty(fwTwo)) {
				$("#"+content_id).val(fwOne+"#"+fwTwo);// 上限#下限
			}
		}else if(standard == "3") {// 异常结果
			content_id = "content_LIKE_"+id;
		}
		var algorithm = content_id.split("_")[1];// 判断标准（数据库存储 GE/LT/RANGE/LIKE）
		var content = $("#"+content_id).val();// 附属条件
		
		// 数据匹配
		item["diseaseId"]=diseaseId;
		item["itemId"]=itemId;
		item["suitableStages"]=suitableStages;
		item["weekBorder"]=weekBorder;
		item["algorithm"]=algorithm;
		item["content"]=content;
		items.push(JSON.stringify(item).replace(/%/g, "%25").replace(/\+/g, "%2B"));
	});
	
	var url = URL.get("item.DISEASE_ITEM_UPDATE");
	var params = "diseaseId=" + diseaseId + "&itemId="+ itemId + "&items=" + items.join("###");
	ajax.post(url, params, dataType.json, function(data){
		layer.alert("操作成功！");
		$("#itemConfigModal").modal("hide");
	}, false, false);
}

/**
 * 配置页面==》删除单个配置
 * @param id
 */
function removededuce(id){
	if(!common.isEmpty(id)){
		layer.confirm('确定要执行【删除】操作？', {btn: ['确定','取消']}, function(index){
			// 删除数据库已有数据
			if(id.indexOf("new") == -1){
				var url = URL.get("item.INSEPCT_ITEM_DISEASE_DELETE");
		    	var params = "diseaseId=" + $("#diseaseId").val() + "&itemId=" + $("#itemId").val() + "&id=" + id;
		    	ajax.post(url, params, dataType.json, function(data){
		    		// 删除页面html代码块
					$("#"+id).remove();
		    	}, false, false);
			}else{
				// 删除页面html代码块
				$("#"+id).remove();
			}
			layer.close(index);
			
			// 重新配置顺序标题
			deduceCount = 1;// 初始化计数
			$("#diseaseItemConfig").find(".deduceClass").each(function(){
				$(this).find('label[name="ordernum"]').html("配置"+deduceCount);
				deduceCount ++;
			});
		});
	}
}


/**
 * 组合页面==》初始化标签插件
 */
function initTagEditor(){
	// 初始化标签插件
	$("#groupContent_div").tagEditor({
		initialTags: [],
		onChange: function(field, editor, tags){},
		beforeTagSave: function(){},
		beforeTagDelete: function(field, editor, tags, val){
			var url = URL.get("item.DISEASE_ITEM_GROUP_DELETE");
			var params = "itemNames=" + val + "&diseaseId=" + $("#diseaseId").val();
			ajax.post(url, params, dataType.json, null, false, false);
		}
	});
	$(".ui-sortable-handle").remove();
}

/**
 * 组合页面==》初始化Modal
 */
function initItemGroupModal(){
	var allData = datatable.getAllData(quotaTable);
	if(!_.isEmpty(allData)){
		var cols = 12;
		if(!_.isEmpty(allData) && allData.length > 20){
			cols = 6;
		}
		var groupCheckbox = "";
		$(allData).each(function(index, obj){
			groupCheckbox += 
				"<div class='col-xs-"+cols+"'>" + 
				"	<label class='checkbox-inline'>"+
				"		<input type='checkbox' name='groupCheckbox' value='"+obj.itemId+"_"+obj.itemName+"'>"+obj.itemName+
				"	</label>" +
				"</div>";
		});
		$("#groupCheckbox_div").html(groupCheckbox);
		
		// 初始化标签列表
		ajax.post(URL.get("item.DISEASE_ITEM_GROUP_QUERY"), "diseaseId=" + $("#diseaseId").val(), dataType.json, function(data){
			$.each(data.value, function(index, group){
				$("#groupContent_div").tagEditor("addTagn", group.itemNames);
			});
		}, false, false);
		$(".tag-editor-spacer").parent("li").remove();
		
		// 弹出框
		$("#itemGroupModal").modal("show");
	} else{
		layer.alert("请先添加指标！");
	}
}

/**
 * 组合页面==》保存推断指标组合并生成标签
 */
function saveDiseaseItemGroup(){
	// 准备数据
	var itemIds = [];
	var itemNames = [];
	$("input:checkbox[name='groupCheckbox'][checked]").each(function(index, group){
		itemIds.push(group.value.split("_")[0]);
		itemNames.push(group.value.split("_")[1]);
	});
	
	if(itemIds.length > 0){
		// 保存数据并添加标签
		var url = URL.get("item.DISEASE_ITEM_GROUP_ADD");
		var params = "diseaseId=" + $("#diseaseId").val() + "&itemIds=" + itemIds.join(",") + "&itemNames=" + itemNames.join("、");
		ajax.post(url, params, dataType.json, function(data){
			$("#groupContent_div").tagEditor("addTagn", itemNames.join("、"));
		}, false, false);
		
		// 取消选中状态
		$("input:checkbox[name='groupCheckbox']").removeAttr("checked");
	}else{
		layer.alert("请先选择检验项目！");
	}
}

/**
 * 删除动作==》删除推断指标
 * @param id
 */
function deleteDiseaseItem(diseaseId, itemId){
	layer.confirm('确定要执行【删除】操作？', {btn: ['确定','取消']}, function(index){
		var url = URL.get("item.INSEPCT_ITEM_DISEASE_DELETE");
    	var params = "diseaseId=" + diseaseId + "&itemId=" + itemId;
    	ajax.post(url, params, dataType.json, function(data){
    		datatable.remove(quotaTable, quotaRow);
			$("#itemId").val("");
    	}, false, false);
    	layer.close(index);
	});
}

//*****************************************自定义开始****************************************

//设置目录高度
function setDH() {
	$("#left_div").css("height", "auto");
	$("#cust_div").css("height", "auto");
	var hLeft = $("#left_div").height();
	var hRight = $("#cust_div").height();
	var max = hLeft > hRight ? hLeft : hRight;
	$("#left_div").height(max+50);
	$("#cust_div").height(max+50);
}
