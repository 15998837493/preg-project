//URL缓存-------------------------------------------------------------------------------------
var URL = {};
if(_.isEmpty(URL)){
	//加载缓存
	$.ajax({
		url : PublicConstant.basePath + "/page/params/system_url_code.action?s="+Math.random(),
		async : false,
		dataType:'json',
		success : function(data){
			URL = data;
		}
	});
}
URL.get = function(param){
	var strs = param.split(".");
	if(!strs){
		return "";
	}else if(strs.length == 1){
		return PublicConstant.basePath + URL[strs[0]];
	}else if(strs.length == 2){
		return PublicConstant.basePath + URL[strs[0]][strs[1]];
	}else if(strs.length == 3){
		var fu = strs[0]+"."+strs[1];
		return URL[fu][strs[2]];
	}
	return "";
};


//CODE缓存-------------------------------------------------------------------------------------
var CODE = {};
if(_.isEmpty(CODE) || _.isEmpty(CODE.data)){
	//加载缓存
	$.ajax({
		url : PublicConstant.basePath + "/page/code/code_cache_all.action?s="+Math.random(),
		async : false,
		dataType:'json',
		success : function(data){
			CODE.data = data;
		}
	});
}
CODE.transCode = function(codeKind, codeValue){
	var key = codeKind.toUpperCase() + codeValue;
	if(!key || common.isEmpty(CODE.data[key])){
		return "";
	}
	return CODE.data[key].codeName;
};

function getCodeCache(){
	//加载缓存
	$.ajax({
		url : PublicConstant.basePath + "/page/code/code_cache_all.action?s="+Math.random(),
		async : false,
		dataType:'json',
		success : function(data){
			if(data){
				CODE.data = data;
			}
		}
	});
}


//定义菜单点击事件-------------------------------------------------------------------------------------
function menuClick(url, pagename){
	ajax.getHtml(url, "content", function(data){
		var nameArray = pagename.split('_');
		var html = "&nbsp;&nbsp;<span class='fa fa-desktop'></span> &nbsp;";
		for (var i=0 ; i< nameArray.length ; i++){
			html = html + nameArray[i]+" &nbsp;<span class='fa fa-angle-right'></span> &nbsp;";
		}
		if(nameArray.length>0){
			$(".tab-header").html(html);
		}
	});
};


$(function(){
	$.scrollUp({
		scrollImg: true
	});
	
	$(".menu-second").on('hide.bs.collapse', function () {
		$("#"+this.id+"_icon").removeClass();
		$("#"+this.id+"_icon").addClass("fa fa-angle-left fa-fw");
		//激活选中样式
		//$(menuObject).addClass('active');
	});
	
	$(".menu-second").on('show.bs.collapse', function () {
		$("#"+this.id+"_icon").removeClass();
		$("#"+this.id+"_icon").addClass("fa fa-angle-down fa-fw");
	});
	
	$("a").each(function(){
		//过滤,只记录target=frame_content目标的连接
		if(this.target == 'frame_content'){
			$(this).bind("click", function(){
				var url = this.href;
				var urlIndex = url.indexOf('?');
				if( urlIndex > 0 ){
					this.href = url.substring(0,urlIndex)+'?random='+Math.random();
				}else{
					this.href = this.href + '?random='+Math.random();
				}
			});
		}
	});
   	
	$('[data-toggle="tooltip"]').tooltip();
	
	// 机构logo图片路径
	if(!common.isEmpty($("#insLogoPath").val())){
		$("#mainInsLogo").attr("src",$("#insLogoPath").val());
	}
	
	// 菜单隐藏
	$('#menu').metisMenu();
	$(".sidebar-menu").click(function(){
		if($(".sidebar").width() > 35){//
			$(".sidebar-nav").hide();
			$(".sidebar-title strong").hide();
			$(".sidebar").animate({width:"35px"},400,function(){
				$("#sidebar_prompt").show();
			});
	  		$(".main-page").animate({left: "35px"});
	  		$(".tab-header").animate({left: "35px"});
		}else{
			$("#sidebar_prompt").hide();
	  		$(".main-page").animate({left: "220px"});
	  		$(".tab-header").animate({left: "220px"});
			$(".sidebar").animate({width:"220px"},400,function(){
				$(".sidebar-nav").show();
				$(".sidebar-title strong").show();
			});
		}
	});	
});

/**
 * 退出操作事件
 */
function logoutEvent(msg,insId){
	layer.confirm(msg, function (index) {
		ajax.post("logout.action", {"logout":'exit'}, dataType.json, function(data){
			window.location = "dlym.action";
		});
		layer.close(index);
    });
};


function updatePsw() {
	pswUpdateModel.openPswUpdateModel();
};

function myInfo(){
	myInfoUpdateModel.openMyInfoUpdateModel(loginUserData);
}
