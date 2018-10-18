<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
#guide_top { /* 	top: calc(50%/2-200px); */
	display: block;
	position: fixed;
	_position: absolute;
	top: 0px;
	height: 80px;
	width: 100%;
	z-index: 999;
	margin-left: -15px;
	padding: 20px 10px 0 15px;
	background-color: #eaf1fb;
}

#dropdown-div div {
	float: left;
}
</style>
<!-- 接诊页面工具条 -->
<div class="row">
	<div id="guide_top">
		<div class="button-group" id="dropdown-div">
			<!-- <div style="margin: -7px 18px 0 2px;">
				<img id="guide_page_inslogo" class="logo-image" id="mainInsLogo" src="" style="width: 55px;height: 55px;"/>
			</div> -->
			<div>
				<button type="button" id="interrogation" class="button button-primary button-rounded button-action button-raised"><i class="fa fa-stethoscope"></i> 问诊</button>
			</div>
<!-- 			<div>
				<button type="button" id="doctor" class="button button-primary button-rounded button-raised"><i class="fa fa-suitcase"></i> 医嘱</button>
			</div> -->
			<div>
				<button type="button" id="diagnosisRecordShow" class="button button-primary button-rounded button-raised"><i class="fa fa-folder-open-o"></i> 营养病历</button>
			</div>
<!-- 			<div class="dropdown">
				<button type="button" class="button button-primary button-rounded button-raised" data-toggle="dropdown">
					<i class="fa fa-cog"></i> 工具 <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li role="presentation"><a id="compare" role="menuitem">宫高腹围对照表</a></li>
					<li role="presentation" class="divider"></li>
					<li role="presentation"><a id="calculator" role="menuitem">胎龄计算器（B超）</a></li>
					<li role="presentation" class="divider"></li>
					<li role="presentation"><a id="weightTool" role="menuitem">体重计算器</a></li>
				</ul>
			</div> -->
<!-- 			<div class="dropdown">
				<button type="button" class="button button-primary button-rounded button-raised" data-toggle="dropdown">
					<i class="fa fa-folder-o"></i> 查询历史记录 <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li role="presentation"><a id="diagnosisItem" role="menuitem">查询系统营养评价</a></li>
					<li role="presentation" class="divider"></li>
					<li role="presentation"><a id="diagnosisHistory" role="menuitem">查询历次就诊记录</a></li>
				</ul>
			</div> -->
		</div>
	</div>
</div>