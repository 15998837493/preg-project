var questionnaire = {
		contants:{
			imgBaseUrl:"/resource/template/image/cover/life/",
			submitUrl:URL.get("Question.SAVE_LIFE_STYLE_QUESTION")
		},
		problem:{
			index:0,
			titleindex:0,//显示在问题前的编号
			removePid:[],//保存删除的问题编号
			initSlider:function(id){
				$("#ex"+id).slider();
				$("#ex"+id).on("change", function(e) {
					$("#exSliderVal"+id).text(e.value.newValue);
					$("#ratio"+id).val(e.value.newValue+"%");
				});
			},
			current:{},
			getProblems:function(removepids){
				questionnaire.actualData = questionnaire.data;
				if(removepids.length>0){
					for(var i=0;i<removepids.length;i++){
						var index = _.findIndex(questionnaire.data, {
							problemId: removepids[i]
							});
						questionnaire.actualData =  _.without(questionnaire.actualData,questionnaire.data[index]);
					}
				}
			},
			show:function(){
				questionnaire.problem.current = questionnaire.actualData[questionnaire.problem.index];
				var modelId = questionnaire.problem.current.model;
				var gettpl = document.getElementById(modelId).innerHTML;
				laytpl(gettpl).render(questionnaire.problem.current, function(html){
				    document.getElementById('problem_div').innerHTML = html;
				    setDH();
				});
				for(var i=0;i<questionnaire.problem.current.hidden.length;i++){
					questionnaire.problem.initSlider(i);
				}
			},
			largepic:function(){
				if(!common.isEmpty($("img").attr("src"))){
					$("img:not([nolarge])").click(function(){
						msg = "<img src='"+$(this).attr("src")+"' height='500' width='500'>";
						layer.open({
							type: 1,
						    title: false,
						    closeBtn: 0,
						    area: '500',
							shadeClose: true,
						    content: msg
						});
					});
				}
			}
		},
		result:{
			data:new Array(),
			push:function(){
				//获取当前问题
				var question = questionnaire.problem.current ;
				//查询当前问题在结果中的保存位置,可能有多个选项
				var findIndex = -1;
				do{
				        findIndex = _.findIndex(questionnaire.result.data, {
						problemId: question.problemId
						});
					if(findIndex!=-1){
						//如果当前问题的信息已经保存过则删除
						questionnaire.result.data = _.without(questionnaire.result.data, questionnaire.result.data[findIndex]) ;
					};
				}while(findIndex!=-1);
				
				var options = $("input[pid='"+question.problemId+"'],select[pid='"+question.problemId+"']");
				$.each(options,function(key,ele){
					if($(ele).attr("checked")=="checked"){
						if($(ele).attr("ptype")=="2"){
							questionnaire.result.data.push({"problemId":question.problemId,"optionId":$(ele).attr("id"),"optionType":$(ele).attr("ptype"),"optionContent":"","frequency":"","addvalue":""});
						}
						//处理问题3的checkbox
						if($(ele).attr("ptype")=="1"){
							questionnaire.result.data.push({"problemId":question.problemId,"optionId":$(ele).attr("oid"),"optionType":$(ele).attr("ptype"),"optionContent":"","frequency":"","addvalue":$(ele).val()});
						}
					}
					if($(ele).attr("type")=="text"&&!common.isEmpty($(ele).val())){
						//纯填空题
						if(common.isEmpty($(ele).attr("oid"))){
							questionnaire.result.data.push({"problemId":question.problemId,"optionId":"","optionType":$(ele).attr("ptype"),"optionContent":"","frequency":"","addvalue":$(ele).val()});
						}else{
							//处理24题这种填空
							questionnaire.result.data.push({"problemId":question.problemId,"optionId":$(ele).attr("oid"),"optionType":$(ele).attr("ptype"),"optionContent":"","frequency":$(ele).attr("frequency"),"addvalue":$(ele).val()});
						}
						
					}
					//处理问题3的select
					if(ele.tagName=="SELECT"){
						questionnaire.result.data.push({"problemId":question.problemId,"optionId":$(ele).attr("oid"),"optionType":$(ele).attr("ptype"),"optionContent":"","frequency":"","addvalue":$(ele).val()});
					}
				});
			}
		},
		actualData:{},//存放要显示的问题数据，data中的数据可能存在不需要显示的
		data:[{"problemId":"p101","order":"1","problemContent":"您的膳食类型","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10101","optionName":"肉食型","sort":"1","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10102","optionName":"偏肉食型","sort":"2","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10103","optionName":"荤素搭配型","sort":"3","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10104","optionName":"荤素搭配型（不吃鸡肉）","sort":"4","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10105","optionName":"荤素搭配型（不吃牛羊肉）","sort":"5","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10106","optionName":"荤素搭配型（不吃猪肉）","sort":"6","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10107","optionName":"荤素搭配型（不吃鱼虾等水产品）","sort":"7","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10108","optionName":"全素食型","sort":"8","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10109","optionName":"偏素食型（很少吃肉）","sort":"9","validate":"","imgUrl":"","model":""},
									                                                                                                       												 {"optionId":"O10110","optionName":"蛋奶素型（荤类食品中只吃鸡蛋和牛奶）","sort":"10","validate":"","imgUrl":"","model":""}
									                                                                                                       											   ],"hidden":""},
				{"problemId":"p102","order":"2","problemContent":"您在孕期的用餐频率为","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10201","optionName":"一日三餐规律","sort":"1","validate":"","imgUrl":"","model":""},
																								                                                   												 {"optionId":"O10202","optionName":"三餐不规律","sort":"2","validate":"","imgUrl":"","model":""},
																								                                                   												 {"optionId":"O10203","optionName":"少食多餐","sort":"3","validate":"","imgUrl":"","model":""}
																								                                                   											   ],"hidden":""},
				{"problemId":"p103","order":"3","problemContent":"您在孕期的用餐时间","model":"p3","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10301","optionName":"早餐","sort":"1","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10302","optionName":"上午加餐","sort":"2","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10303","optionName":"中餐","sort":"3","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10304","optionName":"下午加餐","sort":"4","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10305","optionName":"晚餐","sort":"5","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10306","optionName":"夜宵","sort":"6","validate":"","imgUrl":"","model":""}
												                  																								                               ],"hidden":""},
				{"problemId":"p104","order":"4","problemContent":"您在孕期的平均用餐时间为","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10401","optionName":"5分钟之内","sort":"1","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10402","optionName":"5-10分钟","sort":"2","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10403","optionName":"10-20分钟","sort":"3","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10404","optionName":"20-30分钟","sort":"4","validate":"","imgUrl":"","model":""},
												                  																								                               {"optionId":"O10405","optionName":"30分钟以上","sort":"5","validate":"","imgUrl":"","model":""}
												                  																								                               ],"hidden":""},
				{"problemId":"p105","order":"5","problemContent":"您在孕期的在外用餐频率为","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10501","optionName":"从不在外用餐","sort":"1","validate":"","imgUrl":"","model":""},
																																									                           {"optionId":"O10502","optionName":"每周1-3次","sort":"2","validate":"","imgUrl":"","model":""},
																																									                           {"optionId":"O10503","optionName":"每周4-6次","sort":"3","validate":"","imgUrl":"","model":""},
																																									                           {"optionId":"O10504","optionName":"每周7次以上","sort":"4","validate":"","imgUrl":"","model":""}
																																									                           ],"hidden":""},
				{"problemId":"p106","order":"6","problemContent":"您在孕期的进餐感受通常为","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10601","optionName":"五分饱：没吃饱很难撑到下一餐","sort":"1","validate":"","imgUrl":"","model":""},
										                                                                                                                                                        {"optionId":"O10602","optionName":"六分饱：不太饱也不太饿，第二餐之前饿得很明显","sort":"2","validate":"","imgUrl":"","model":""},
										                                                                                                                                                        {"optionId":"O10603","optionName":"七分饱：胃里不满，但进食速度明显变慢，不再继续吃不会觉得饿，第二餐之前不会饿","sort":"3","validate":"","imgUrl":"","model":""},
										                                                                                                                                                        {"optionId":"O10604","optionName":"八分饱：胃里满了，但是还能再吃几口","sort":"4","validate":"","imgUrl":"","model":""},
										                                                                                                                                                        {"optionId":"O10605","optionName":"九分饱：能勉强吃几口，每口都是负担","sort":"5","validate":"","imgUrl":"","model":""},
										                                                                                                                                                        {"optionId":"O10606","optionName":"十分饱：一口都吃不进去了","sort":"6","validate":"","imgUrl":"","model":""}
										                                                                                                                                                        ],"hidden":""},
				{"problemId":"p107","order":"7","problemContent":"您在孕期的平均每天都用的烹饪用油为(可多选)","model":"p2","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10701","optionName":"大豆调和油","sort":"1","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10702","optionName":"葵花橄榄调和油","sort":"2","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10703","optionName":"豆油","sort":"3","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10704","optionName":"葵花籽油","sort":"4","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10705","optionName":"橄榄油","sort":"5","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10706","optionName":"玉米油","sort":"6","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10707","optionName":"花生油 ","sort":"7","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10708","optionName":"葡萄籽油 ","sort":"8","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10709","optionName":"亚麻籽油/紫苏籽油","sort":"9","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10710","optionName":"核桃油","sort":"10","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10711","optionName":"金龙鱼1:1:1调和油","sort":"11","validate":"","imgUrl":"","model":""}
														                                                                                                                                        ],"hidden":""},
				{"problemId":"p108","order":"8","problemContent":"您在孕期常用的烹饪方式有（可多选）","model":"p2","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10801","optionName":"炒菜","sort":"1","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10802","optionName":"蒸煮","sort":"2","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10803","optionName":"煲炖","sort":"3","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10804","optionName":"煎炸","sort":"4","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10805","optionName":"烤","sort":"5","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10806","optionName":"凉拌","sort":"6","validate":"","imgUrl":"","model":""}
														                                                                                                                                        ],"hidden":""},
				{"problemId":"p109","order":"9","problemContent":"您在孕期常用的烹饪习惯有（可多选）","model":"p10","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O10901","optionName":"无特殊烹饪习惯","sort":"1","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10902","optionName":"勾芡","sort":"2","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10903","optionName":"几乎不放油","sort":"3","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10904","optionName":"清淡（少油少盐）","sort":"4","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10905","optionName":"高油（用餐时盘底有明显剩余油脂、或经常食用煎炸类食物）","sort":"5","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10906","optionName":"高糖（烹饪食物时习惯额外添加糖）","sort":"6","validate":"","imgUrl":"","model":""},
														                                                                                                                                        {"optionId":"O10907","optionName":"高盐（过量的含钠调味品如酱油、味精等）","sort":"7","validate":"","imgUrl":"","model":""}
														                                                                                                                                        ],"hidden":""}	,		
				{"problemId":"p110","order":"10","problemContent":"您在孕期的碘摄入情况","model":"p1","hasOther":false,"hint":"备注：富含碘的食物如海带、紫菜、淡菜、海鱼、虾贝类尤其是紫菜和海带","ratio":"","options":[{"optionId":"O11001","optionName":"不使用加碘盐或不能保证每周都能食用富含碘的食物","sort":"1","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                              {"optionId":"O11002","optionName":"使用加碘盐并且每周都能够食用富含碘的食物","sort":"2","validate":"","imgUrl":"","model":""},
																																															  {"optionId":"O11003","optionName":"吃碘盐不吃海产品","sort":"2","validate":"","imgUrl":"","model":""},
																																															  {"optionId":"O11004","optionName":"不吃碘盐吃海产品","sort":"2","validate":"","imgUrl":"","model":""}
                                                                                                                                                                                              ],"hidden":""},			
				{"problemId":"p201","order":"11","problemContent":"您的运动频率为","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O20101","optionName":"不参加","sort":"1","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20102","optionName":"1～2次/周","sort":"2","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20103","optionName":"3～5次/周","sort":"3","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20104","optionName":"＞5次/周","sort":"4","validate":"","imgUrl":"","model":""}
                                                                                                                                                                                               ],"hidden":""},
                {"problemId":"p202","order":"12","problemContent":"您常采用的运动锻炼方式：（可多选）","model":"p2","hasOther":true,"hint":"","ratio":"","options":[{"optionId":"O20201","optionName":"散步","sort":"1","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20202","optionName":"慢跑","sort":"2","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20203","optionName":"游泳","sort":"3","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20204","optionName":"爬楼梯","sort":"4","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20205","optionName":"舞蹈","sort":"5","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20206","optionName":"瑜伽/普拉提","sort":"6","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20207","optionName":"健身操","sort":"7","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20208","optionName":"力量锻炼","sort":"8","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O20209","optionName":"太极拳","sort":"9","validate":"","imgUrl":"","model":""}
                                                                                                                                                                                               ],"hidden":""},
               {"problemId":"p204","order":"13","problemContent":"您每次锻炼多次时间","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O20401","optionName":"＜30分钟","sort":"1","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O20402","optionName":"30～60分钟","sort":"2","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O20403","optionName":"＞60分钟","sort":"3","validate":"","imgUrl":"","model":""}
													                                                                                                                                          ],"hidden":""},
			   {"problemId":"p205","order":"14","problemContent":"运动时间","model":"p2","hasOther":true,"hint":"","ratio":"","options":[{"optionId":"O20501","optionName":"晨起","sort":"1","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O20502","optionName":"早餐后","sort":"2","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O20503","optionName":"上午两餐之间","sort":"3","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O20504","optionName":"午餐后","sort":"4","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O20505","optionName":"下午两餐之间","sort":"5","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O20506","optionName":"晚餐后","sort":"6","validate":"","imgUrl":"","model":""},
													                                                                                                                                          ],"hidden":""},
			   {"problemId":"p301","order":"15","problemContent":"近期睡眠如何","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O30101","optionName":"好","sort":"1","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O30102","optionName":"一般","sort":"2","validate":"","imgUrl":"","model":""},
													                                                                                                                                          {"optionId":"O30103","optionName":"差","sort":"3","validate":"","imgUrl":"","model":""}
													                                                                                                                                          ],"hidden":""},
			   {"problemId":"p302","order":"16","problemContent":"您睡眠是否存在这些表现：(可多选)","model":"p2","hasOther":true,"hint":"","ratio":"","options":[{"optionId":"O30201","optionName":"入睡困难","sort":"1","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30202","optionName":"夜间易醒或早醒","sort":"2","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30203","optionName":"夜起","sort":"3","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30204","optionName":"多梦或噩梦中惊醒","sort":"4","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30205","optionName":"熟睡时间短","sort":"5","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30206","optionName":"夜间去厕所","sort":"6","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30207","optionName":"呼吸不畅","sort":"7","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30208","optionName":"感觉冷","sort":"8","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30209","optionName":"感觉热","sort":"9","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O30210","optionName":"疼痛不适","sort":"10","validate":"","imgUrl":"","model":""}
														                                                                                                                                      ],"hidden":""},		
				{"problemId":"p303","order":"17","problemContent":"您每天平均睡眠时间：（不等于卧床时间）","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O30301","optionName":"＜5小时","sort":"1","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O30302","optionName":"5～7小时","sort":"2","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O30303","optionName":"7～9小时","sort":"3","validate":"","imgUrl":"","model":""},
                                                                                                                                                                                               {"optionId":"O30304","optionName":"＞9小时","sort":"4","validate":"","imgUrl":"","model":""}
                                                                                                                                                                                               ],"hidden":""}	,
                {"problemId":"p401","order":"18","problemContent":"您最近两周是否经常几种情绪（可多选）？ ","model":"p10","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O40101","optionName":"情绪低落，压抑或沮丧","sort":"1","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O40102","optionName":"对人对事缺乏热情","sort":"2","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O40103","optionName":"注意力集中有困难","sort":"3","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O40104","optionName":"情绪激动或生气","sort":"4","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O40105","optionName":"精神紧张，很难放松","sort":"5","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O40106","optionName":"发脾气，没有耐性","sort":"6","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O40107","optionName":"焦虑不安、心烦意乱","sort":"7","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O40108","optionName":"心态很好，没有任何不良情绪","sort":"8","validate":"","imgUrl":"","model":""}
														                                                                                                                                      ],"hidden":""}	,
               {"problemId":"p501","order":"19","problemContent":"关于饮酒 ","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O50101","optionName":"孕前经常喝，怀孕后不喝","sort":"1","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O50102","optionName":"孕前偶尔喝，怀孕后不喝","sort":"2","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O50103","optionName":"一直不喝","sort":"3","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O50104","optionName":"少量红酒","sort":"4","validate":"","imgUrl":"","model":""},
														                                                                                                                                      {"optionId":"O50105","optionName":"一直喝","sort":"5","validate":"","imgUrl":"","model":""}
														                                                                                                                                      ],"hidden":""},
              {"problemId":"p502","order":"20","problemContent":"关于主被动吸烟 ","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O50201","optionName":"不吸","sort":"1","validate":"","imgUrl":"","model":""},
 														                                                                                                                                      {"optionId":"O50202","optionName":"吸烟","sort":"2","validate":"","imgUrl":"","model":""},
 														                                                                                                                                      {"optionId":"O50203","optionName":"吸烟，已戒","sort":"3","validate":"","imgUrl":"","model":""},
 														                                                                                                                                      {"optionId":"O50204","optionName":"被动吸烟（每天累计15分钟以上，且每周1天以上）","sort":"4","validate":"","imgUrl":"","model":""}
 														                                                                                                                                      ],"hidden":""},
  			   {"problemId":"p601","order":"21","problemContent":"您的工作/生活场所经常会接触到哪些有害物质：","model":"p10","hasOther":true,"hint":"","ratio":"","options":[{"optionId":"O60101","optionName":"无或很少","sort":"1","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60102","optionName":"噪音、震动","sort":"2","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60103","optionName":"电磁辐射","sort":"3","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60104","optionName":"二手烟","sort":"4","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60105","optionName":"粉尘","sort":"5","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60106","optionName":"杀虫剂","sort":"6","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60107","optionName":"干洗衣物","sort":"7","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60108","optionName":"化学污染（毒素环境、重金属）","sort":"8","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60109","optionName":"空气污染","sort":"9","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60110","optionName":"建筑装修污染","sort":"10","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60111","optionName":"烹饪油烟","sort":"11","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60112","optionName":"饮用非过滤水","sort":"12","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60113","optionName":"通风不良","sort":"13","validate":"","imgUrl":"","model":""},
															                                                                                                                                  {"optionId":"O60114","optionName":"牙齿金属填充物（银汞合金、镍铬合金）","sort":"14","validate":"","imgUrl":"","model":""}
															                                                                                                                                  ],"hidden":""},
	             {"problemId":"p602","order":"22","problemContent":"您在孕期平时晒太阳的时间：","model":"p1","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O60201","optionName":"几乎没有","sort":"1","validate":"","imgUrl":"","model":""},
	                                                                                                                                                                                           {"optionId":"O60202","optionName":"很少、每天＜30分钟","sort":"2","validate":"","imgUrl":"","model":""},
	                                                                                                                                                                                           {"optionId":"O60203","optionName":"每天晒0.5-2小时太阳","sort":"3","validate":"","imgUrl":"","model":""}
	                                                                                                                                                                                           ],"hidden":""},
                {"problemId":"p000","order":"","problemContent":" ","model":"p11","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O00001","optionName":"","sort":"1","validate":"","imgUrl":"resource/template/image/cover/life/stander.jpg","model":""}],"hidden":""}	,
                {"problemId":"p701","order":"23","problemContent":"您平均饮水量（不包含糖饮料）约为@#$ml? ","model":"p4","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O70101","optionName":"","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/water.png","model":""}],"hidden":""}	,
                {"problemId":"p801","order":"24","problemContent":"您平均每天摄入多少主食?（以最平常的一天为例，只填写摄入的食物，若未摄入某类食物，则无需填写。） ","model":"p5","hasOther":false,"hint":"","ratio":["主食中粗粮占比约为："],"options":[{"optionId":"O80101","optionName":"米饭(图示为100g)","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/mifan.png","model":""},
                																																												{"optionId":"O80102","optionName":"粥(图示为100g)","sort":"2","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/damizhou.png","model":""},
																																												                {"optionId":"O80103","optionName":"面条(图示为100g)","sort":"3","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/miaotiao.png","model":""},
                																																												{"optionId":"O80104","optionName":"馒头(图示为80g)","sort":"4","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/mantou.png","model":""},
                																																												{"optionId":"O80105","optionName":"面包(图示为50g)","sort":"5","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/mianbao.png","model":""},
                																																												{"optionId":"O80106","optionName":"烙饼(图示为100g)","sort":"6","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/laobing.png","model":""},
                																																												{"optionId":"O80107","optionName":"土豆(图示为100g)","sort":"7","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/malingshu.png","model":""},
                																																												{"optionId":"O80108","optionName":"玉米(图示为200g)","sort":"8","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/yumi.png","model":""},
                																																												{"optionId":"O80109","optionName":"包子(肉)(图示为100g)","sort":"9","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/baozi.png","model":""},
                																																												{"optionId":"O80110","optionName":"饺子(素)(图示为100g)","sort":"10","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/jiaozi.png","model":""},
                																																												{"optionId":"O80111","optionName":"汉堡(图示为150g)","sort":"11","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/hanbao.png","model":""},
                																																												{"optionId":"O80112","optionName":"披萨(图示为100g)","sort":"12","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/pisha.png","model":""}
                																																											 ],"hidden":["O80113"]}	,
			     {"problemId":"p802","order":"25","problemContent":"根据如下图片，回顾一下您平均一天大约能吃@#$g畜禽类肉 ","model":"p6","hasOther":false,"hint":"(备注：①包含荤素混合菜品种的肉类，如芹菜炒肉中的肉  ②不包括鱼虾海鲜、内脏及蛋类 ③图中的重量均为该食物的生重可食部分的重量)","ratio":["禽畜肉类菜品中瘦肉占比约为："],"options":[{"optionId":"O80201","optionName":"羊肉片(图示为50g)","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/yangroupian.png","model":""},
                																																												{"optionId":"O80202","optionName":"猪肉丝(图示为50g)","sort":"2","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/zhurousi.png","model":""},
																																												                {"optionId":"O80203","optionName":"鸡腿(图示为150g)","sort":"3","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/jitui.png","model":""},
                																																												{"optionId":"O80204","optionName":"鸡翅(图示为80g)","sort":"4","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/jichi.png","model":""},
                																																												{"optionId":"O80205","optionName":"排骨(图示为50g)","sort":"5","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/paigu.png","model":""},
                																																												{"optionId":"O80206","optionName":"牛肉(图示为50g)","sort":"6","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/niurou.png","model":""},
                																																												{"optionId":"O80207","optionName":"叉烧肉(图示为50g)","sort":"7","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/chashaorou.png","model":""},
                																																												{"optionId":"O80208","optionName":"火腿肠(图示为50g)","sort":"8","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/huotuichang.png","model":""}
                																																												],"hidden":["O80209"]},
				{"problemId":"p901","order":"29","problemContent":"请根据你的实际情况填写下方的食物(若未摄入某类食物，则无需填写。) ","model":"p8","hasOther":false,"hint":"","ratio":["鱼类中深水鱼的比例（如三文鱼、鲅鱼、沙丁鱼等）："],"options":[{"optionId":"O90101","optionName":"蛋类","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/901-1.png","model":""},
		          																																												{"optionId":"O90102","optionName":"全脂奶及奶制品","sort":"2","validate":"^\\d*(.\\d{1})?$","imgUrl":["resource/template/image/cover/life/niunai.png","resource/template/image/cover/life/nailao.png","resource/template/image/cover/life/suannai.png","resource/template/image/cover/life/naifen.png"],"model":["一份牛奶200ml","一份奶酪25g","一份酸奶125ml*2","一份奶粉10g"]},
																																												                {"optionId":"O90103","optionName":"脱脂奶及奶制品","sort":"3","validate":"^\\d*(.\\d{1})?$","imgUrl":"","model":""},
		          																																												{"optionId":"O90104","optionName":"大豆类","sort":"4","validate":"^\\d*(.\\d{1})?$","imgUrl":["resource/template/image/cover/life/huangdou.png","resource/template/image/cover/life/doujiang.png","resource/template/image/cover/life/dougan.png","resource/template/image/cover/life/beidoufu.png"],"model":["一份黄豆20g","一份豆浆360ml","一份豆干45g","一份北豆腐60g"]},
		          																																												{"optionId":"O90105","optionName":"鱼虾类","sort":"5","validate":"^\\d*(.\\d{1})?$","imgUrl":["resource/template/image/cover/life/sanwenyu.png","resource/template/image/cover/life/daiyu.png","resource/template/image/cover/life/xia.png","resource/template/image/cover/life/caoyu.png"],"model":["一份三文鱼50g","一份带鱼65g可食部分50g","一份草虾85g可食部分50g","一份草鱼90g可食部分50g"]},
		          																																												{"optionId":"O90106","optionName":"坚果（非油炸）","sort":"6","validate":"^\\d*(.\\d{1})?$","imgUrl":["resource/template/image/cover/life/guaziren.png","resource/template/image/cover/life/guazi.png","resource/template/image/cover/life/huashengmi.png","resource/template/image/cover/life/huasheng.png"],"model":["一份瓜子仁10g","一份瓜子24g","一份花生米10g","一份花生28g"]},
		          																																												{"optionId":"O90107","optionName":"动物内脏","sort":"7","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/901-7.png","model":"一份鸡肝50g"}
		          																																												],"hidden":["O90108"]},
                {"problemId":"p803","order":"26","problemContent":"根据如下图片，回顾一下您平均一天大约能吃@#$g蔬菜 ","model":"p6","hasOther":false,"hint":"(备注：①包含荤素混合菜品种的蔬菜  ，如芹菜炒肉中的芹菜 ②不包括薯类：如地瓜、土豆、山药等)","ratio":["蔬菜类菜品中深色蔬菜如西蓝花、菠菜、胡萝卜等占比约为：","蔬菜类菜品中菌类如香菇、木耳、猴头等占比约为：","蔬菜类菜品中藻类如海带、裙带菜等占比约为："],"options":[{"optionId":"O80301","optionName":"西红柿(图示为250g)","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/xihongshi.png","model":""},
                																																												{"optionId":"O80302","optionName":"黄瓜(图示为100g)","sort":"2","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/huanggua.png","model":""},
																																												                {"optionId":"O80303","optionName":"芹菜(图示为100g)","sort":"3","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/qincai.png","model":""},
                																																												{"optionId":"O80304","optionName":"茄子(图示为100g)","sort":"4","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/qiezi.png","model":""},
                																																												{"optionId":"O80305","optionName":"豆芽(图示为100g)","sort":"5","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/douya.png","model":""},
                																																												{"optionId":"O80306","optionName":"平菇(图示为100g)","sort":"6","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/pinggu.png","model":""},
                																																												{"optionId":"O80307","optionName":"花菜(图示为100g)","sort":"7","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/huacai.png","model":""},
                																																												{"optionId":"O80308","optionName":"大白菜(图示为100g)","sort":"8","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/dabaicai.png","model":""},
                																																												{"optionId":"O80309","optionName":"菠菜(图示为100g)","sort":"9","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/bocai.png","model":""},
                																																												{"optionId":"O80310","optionName":"白萝卜(图示为100g)","sort":"10","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/bailuobo.png","model":""}
                																																												],"hidden":["O80311","O80312","O80313"]},
	            {"problemId":"p804","order":"27","problemContent":"您平均每天摄入多少水果？(只填写摄入的食物，若未摄入某类食物，则无需填写。) ","model":"p7","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O80401","optionName":"苹果(图示为200g)","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/pingguo.png","model":""},
	              																																												{"optionId":"O80402","optionName":"香蕉(图示为160g)","sort":"2","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/xiangjiao.png","model":""},
																																												                {"optionId":"O80403","optionName":"橘子(图示为100g)","sort":"3","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/juzi.png","model":""},
	              																																												{"optionId":"O80404","optionName":"西瓜(图示为100g)","sort":"4","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/xigua.png","model":""},
	              																																												{"optionId":"O80405","optionName":"葡萄(图示为100g)","sort":"5","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/putao.png","model":""},
	              																																												{"optionId":"O80406","optionName":"草莓(图示为100g)","sort":"6","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/caimei.png","model":""},
	              																																												{"optionId":"O80407","optionName":"桑葚(图示为100g)","sort":"7","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/shangshen.png","model":""},
	              																																												{"optionId":"O80408","optionName":"其他","sort":"8","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/other.png","model":""}
	              																																												],"hidden":""},
				{"problemId":"p902","order":"30","problemContent":"请根据你的实际情况填写下方常见零食摄入情况(若未摄入某类食物，则无需填写。) ","model":"p9","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O90201","optionName":"饼干(图示为25g)","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/binggan.png","model":"g"},
		          																																												{"optionId":"O90202","optionName":"蛋糕(图示为40g)","sort":"2","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/dangao.png","model":"g"},
																																												                {"optionId":"O90203","optionName":"巧克力(图示为50g)","sort":"3","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/qiaokeli.png","model":"g"},
		          																																												{"optionId":"O90204","optionName":"蜜饯(图示为50g)","sort":"4","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/mijian.png","model":"g"},
		          																																												{"optionId":"O90205","optionName":"糖果(图示为20g)","sort":"5","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/tangguo.png","model":"g"},
		          																																												{"optionId":"O90206","optionName":"高油高糖面包(图示为100g)","sort":"6","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/gaoyougaotangmiaobao.png","model":"g"},
		          																																												{"optionId":"O90207","optionName":"海苔(图示为2g)","sort":"7","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/haitai.png","model":"g"},
		          																																												{"optionId":"O90208","optionName":"膨化食品(图示为45g)","sort":"8","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/penghua.png","model":"g"},
		          																																												{"optionId":"O90209","optionName":"雪糕(图示为75g)","sort":"9","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/xuegao.png","model":"g"},
		          																																												{"optionId":"O90210","optionName":"饮料(图示为500g)","sort":"10","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/yinliao.png","model":"g"}
		          																																												],"hidden":""},
				{"problemId":"p903","order":"31","problemContent":"请根据你的实际情况填写下方常见营养食品的摄入情况(若未摄入某类食物，则无需填写。) ","model":"p9","hasOther":false,"hint":"","ratio":"","options":[{"optionId":"O90301","optionName":"燕窝(图示为35g)","sort":"1","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/yanwo.png","model":"g"},
		          																																												{"optionId":"O90302","optionName":"海参(图示为80g)","sort":"2","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/haisheng.png","model":"g"},
																																																{"optionId":"O90303","optionName":"蜂蜜(图示为5g)","sort":"3","validate":"^\\d*(.\\d{1})?$","imgUrl":"resource/template/image/cover/life/fengmi.png","model":"g"}
		          																																												],"hidden":""}
		          																																												
		],
		init:function(){
			questionnaire.actualData = questionnaire.data;
			questionnaire.problem.show();
		},
		next:function(){
			//记录答题结果
			questionnaire.result.push();
			questionnaire.problem.index++;
			if(questionnaire.actualData[questionnaire.problem.index]!="p000"){
				questionnaire.problem.titleindex++;
			}
			//判断是否还有下一题
			if(questionnaire.problem.index<questionnaire.actualData.length){
				//显示当前问题
				questionnaire.problem.show();
			}else{
				layer.confirm("已经到最后一题，确认提交问卷？",function(data){
					if(data){
						questionnaire.submit();
					}else{
						questionnaire.problem.index--;
					}
				});
			}
			questionnaire.problem.largepic();
		},
		previous:function(){
			if(questionnaire.problem.index==0){
				layer.msg("没有上一道题了");
				return ;
			}
			questionnaire.problem.index--;
			if(questionnaire.actualData[questionnaire.problem.index]!="p000"){
				questionnaire.problem.titleindex--;
			}
			questionnaire.problem.show();
			questionnaire.problem.largepic();
		},
		submit:function(){
			box.load("submit");
			$.ajax({
				url : questionnaire.contants.submitUrl,
				async : true,
				type : 'post',
				data : {
					recordId : $("#recordId").val(),
					diagnosisId : $("#diagnosisId").val(),
					questionAnswers:JSON.stringify(questionnaire.result.data)
				},
				dataType : dataType.json,
				success :function(data){ // 完成
					box.ready("submit");
					if(data.result){
						//更新检查项目状态
						opener.updateInspectStatus($("#inspectId").val(), $("#recordId").val());
						var url = URL.get("Question.LIFE_STYLE_REPOER");
						var params = "id=" + $("#inspectId").val();
						ajax.post(url, params, dataType.json, function(data){
							window.location.href = PublicConstant.basePath +"/"+ data.value;
						});
					}
					
				},
				error:function(data,textstatus){
		    		question.layer.ready("submit");	
		    		question.layer.msg(data.responseText);
			    }
			});
		}
};

var commonInfo = {
	//早餐时间
	mealsTime:[["05:00-06:00","06:00-07:00","07:00-08:00","08:00-09:00"],["09:00-10:00","10:00-11:00"],["11:00-12:00","12:00-13:00","13:00-14:00"],["14:00-15:00","15:00-16:00"],["16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00"],["20:00-21:00","21:00-22:00","22:00-23:00","23:00-24:00"]],
	frequency:[{"val":"0","name":"不吃"},{"val":"1","name":"1次/月"},{"val":"2","name":"1~2次/月"},{"val":"3","name":"2~3次/月"},{"val":"4","name":"1~2次/周"},{"val":"5","name":"3~4次/周"},{"val":"6","name":"5~6次/周"},{"val":"7","name":"1次/日"},{"val":"8","name":"2次/日"},{"val":"9","name":"3次/日及以上"}]
};

function setDH(){
	var height = $("#problem_div").height();
    var $uppro = $("#uppro");
    var $downpro = $("#downpro");
    $uppro.css("height","auto");
    $downpro.css("height","auto");
    $uppro.height(height);
    $downpro.height(height);
    restBodyHeight();
}

function restBodyHeight(){
	$("body").css("height", "auto");
	H = $(window).height()-30;
	if($("body").height() < H){
    	$("body").height(H);
	}
}