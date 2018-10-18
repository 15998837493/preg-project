var questionnaire = {
		contants:{
			imgBaseUrl:"/resource/template/image/cover/life/",
			submitUrl:URL.get("Question.SAVE_DIETARY_FREQUENCY_QUESTION")
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
		data:[{
			"problemId": "p701",
			"order": "23",
			"problemContent": "您平均饮水量（不包含糖饮料）约为@#$ml? ",
			"model": "p4",
			"hasOther": false,
			"hint": "",
			"ratio": "",
			"options": [{
				"optionId": "O70101",
				"optionName": "",
				"sort": "1",
				"validate": "^\\d*(\\.\\d{1})?$",
				"imgUrl": "resource/template/image/cover/life/water.png",
				"model": ""
			}],
			"hidden": ""
		},{
    		"problemId": "p801",
    		"order": "24",
    		"problemContent": "您平均每天摄入多少主食?（以最平常的一天为例，只填写摄入的食物，若未摄入某类食物，则无需填写。） ",
    		"model": "p5",
    		"hasOther": false,
    		"hint": "",
    		"ratio": ["主食中粗粮占比约为："],
    		"options": [{
    				"optionId": "O80101",
    				"optionName": "米饭(图示为100g)",
    				"sort": "1",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/mifan.png",
    				"model": ""
    			},{
    				"optionId": "O80102",
    				"optionName": "粥(图示为100g)",
    				"sort": "2",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/damizhou.png",
    				"model": ""
    			},{
    				"optionId": "O80103",
    				"optionName": "面条(图示为100g)",
    				"sort": "3",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/miaotiao.png",
    				"model": ""
    			},{
    				"optionId": "O80104",
    				"optionName": "馒头(图示为80g)",
    				"sort": "4",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/mantou.png",
    				"model": ""
    			},{
    				"optionId": "O80105",
    				"optionName": "面包(图示为50g)",
    				"sort": "5",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/mianbao.png",
    				"model": ""
    			},{
    				"optionId": "O80106",
    				"optionName": "烙饼(图示为100g)",
    				"sort": "6",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/laobing.png",
    				"model": ""
    			},{
    				"optionId": "O80107",
    				"optionName": "土豆(图示为100g)",
    				"sort": "7",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/malingshu.png",
    				"model": ""
    			},{
    				"optionId": "O80108",
    				"optionName": "玉米(图示为200g)",
    				"sort": "8",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/yumi.png",
    				"model": ""
    			},{
    				"optionId": "O80109",
    				"optionName": "包子(肉)(图示为100g)",
    				"sort": "9",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/baozi.png",
    				"model": ""
    			},{
    				"optionId": "O80110",
    				"optionName": "饺子(素)(图示为100g)",
    				"sort": "10",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/jiaozi.png",
    				"model": ""
    			},{
    				"optionId": "O80111",
    				"optionName": "汉堡(图示为150g)",
    				"sort": "11",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/hanbao.png",
    				"model": ""
    			},{
    				"optionId": "O80112",
    				"optionName": "披萨(图示为100g)",
    				"sort": "12",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/pisha.png",
    				"model": ""
    			}
    		],
    		"hidden": ["O80113"]
    	},{
    		"problemId": "p802",
    		"order": "25",
    		"problemContent": "根据如下图片，回顾一下您平均一天大约能吃@#$g畜禽类肉 ",
    		"model": "p6",
    		"hasOther": false,
    		"hint": "(备注：①包含荤素混合菜品种的肉类，如芹菜炒肉中的肉  ②不包括鱼虾海鲜、内脏及蛋类 ③图中的重量均为该食物的生重可食部分的重量)",
    		"ratio": ["禽畜肉类菜品中瘦肉占比约为："],
    		"options": [{
    				"optionId": "O80201",
    				"optionName": "羊肉片(图示为50g)",
    				"sort": "1",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/yangroupian.png",
    				"model": ""
    			},{
    				"optionId": "O80202",
    				"optionName": "猪肉丝(图示为50g)",
    				"sort": "2",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/zhurousi.png",
    				"model": ""
    			},{
    				"optionId": "O80203",
    				"optionName": "鸡腿(图示为150g)",
    				"sort": "3",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/jitui.png",
    				"model": ""
    			},{
    				"optionId": "O80204",
    				"optionName": "鸡翅(图示为80g)",
    				"sort": "4",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/jichi.png",
    				"model": ""
    			},{
    				"optionId": "O80205",
    				"optionName": "排骨(图示为50g)",
    				"sort": "5",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/paigu.png",
    				"model": ""
    			},{
    				"optionId": "O80206",
    				"optionName": "牛肉(图示为50g)",
    				"sort": "6",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/niurou.png",
    				"model": ""
    			},{
    				"optionId": "O80207",
    				"optionName": "叉烧肉(图示为50g)",
    				"sort": "7",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/chashaorou.png",
    				"model": ""
    			},{
    				"optionId": "O80208",
    				"optionName": "火腿肠(图示为50g)",
    				"sort": "8",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/huotuichang.png",
    				"model": ""
    			}
    		],
    		"hidden": ["O80209"]
       	},{
    		"problemId": "p803",
    		"order": "26",
    		"problemContent": "根据如下图片，回顾一下您平均一天大约能吃@#$g蔬菜 ",
    		"model": "p6",
    		"hasOther": false,
    		"hint": "(备注：①包含荤素混合菜品种的蔬菜  ，如芹菜炒肉中的芹菜 ②不包括薯类：如地瓜、土豆、山药等)",
    		"ratio": ["蔬菜类菜品中深色蔬菜如西蓝花、菠菜、胡萝卜等占比约为：" 
//    		          "蔬菜类菜品中菌类如香菇、木耳、猴头等占比约为：",
//    		          "蔬菜类菜品中藻类如海带、裙带菜等占比约为："
    		          ],
    		"options": [{
    				"optionId": "O80301",
    				"optionName": "西红柿(图示为250g)",
    				"sort": "1",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/xihongshi.png",
    				"model": ""
    			},{
    				"optionId": "O80302",
    				"optionName": "黄瓜(图示为100g)",
    				"sort": "2",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/huanggua.png",
    				"model": ""
    			},{
    				"optionId": "O80303",
    				"optionName": "芹菜(图示为100g)",
    				"sort": "3",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/qincai.png",
    				"model": ""
    			},{
    				"optionId": "O80304",
    				"optionName": "茄子(图示为100g)",
    				"sort": "4",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/qiezi.png",
    				"model": ""
    			},{
    				"optionId": "O80305",
    				"optionName": "豆芽(图示为100g)",
    				"sort": "5",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/douya.png",
    				"model": ""
    			},{
    				"optionId": "O80306",
    				"optionName": "平菇(图示为100g)",
    				"sort": "6",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/pinggu.png",
    				"model": ""
    			},{
    				"optionId": "O80307",
    				"optionName": "花菜(图示为100g)",
    				"sort": "7",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/huacai.png",
    				"model": ""
    			},{
    				"optionId": "O80308",
    				"optionName": "大白菜(图示为100g)",
    				"sort": "8",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/dabaicai.png",
    				"model": ""
    			},{
    				"optionId": "O80309",
    				"optionName": "菠菜(图示为100g)",
    				"sort": "9",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/bocai.png",
    				"model": ""
    			},{
    				"optionId": "O80310",
    				"optionName": "白萝卜(图示为100g)",
    				"sort": "10",
    				"validate": "^\\d*(\\.\\d{1})?$",
    				"imgUrl": "resource/template/image/cover/life/bailuobo.png",
    				"model": ""
    			}
    		],
    		"hidden": ["O80311"]
    	},{
       	    "problemId": "p804",
       	    "order": "27",
       	    "problemContent": "您平均每天摄入多少水果？(只填写摄入的食物，若未摄入某类食物，则无需填写。) ",
       	    "model": "p7",
       	    "hasOther": false,
       	    "hint": "",
       	    "ratio": "",
       	    "options": [
       	        {
       	            "optionId": "O80401",
       	            "optionName": "苹果(图示为200g)",
       	            "sort": "1",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/pingguo.png",
       	            "model": ""
       	        },{
       	            "optionId": "O80402",
       	            "optionName": "香蕉(图示为160g)",
       	            "sort": "2",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/xiangjiao.png",
       	            "model": ""
       	        },{
       	            "optionId": "O80403",
       	            "optionName": "橘子(图示为100g)",
       	            "sort": "3",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/juzi.png",
       	            "model": ""
       	        },{
       	            "optionId": "O80404",
       	            "optionName": "西瓜(图示为100g)",
       	            "sort": "4",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/xigua.png",
       	            "model": ""
       	        },{
       	            "optionId": "O80405",
       	            "optionName": "葡萄(图示为100g)",
       	            "sort": "5",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/putao.png",
       	            "model": ""
       	        },{
       	            "optionId": "O80406",
       	            "optionName": "草莓(图示为100g)",
       	            "sort": "6",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/caimei.png",
       	            "model": ""
       	        },{
       	            "optionId": "O80407",
       	            "optionName": "桑葚(图示为100g)",
       	            "sort": "7",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/shangshen.png",
       	            "model": ""
       	        },{
       	            "optionId": "O80408",
       	            "optionName": "其他",
       	            "sort": "8",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/other.png",
       	            "model": ""
       	        }
       	    ],
       	    "hidden": ""
       	},{
    	    "problemId": "p901",
    	    "order": "29",
    	    "problemContent": "请根据你的实际情况填写下方的食物(若未摄入某类食物，则无需填写。) ",
    	    "model": "p8",
    	    "hasOther": false,
    	    "hint": "",
    	    "ratio": [
    	        "鱼类中深水鱼的比例（如三文鱼、鲅鱼、沙丁鱼等）："
    	    ],
    	    "options": [
    	        {
    	            "optionId": "O90101",
    	            "optionName": "蛋类",
    	            "sort": "1",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": "resource/template/image/cover/life/901-1.png",
    	            "model": ""
    	        },{
    	            "optionId": "O90102",
    	            "optionName": "全脂奶及奶制品",
    	            "sort": "2",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": [
    	                "resource/template/image/cover/life/niunai.png",
    	                "resource/template/image/cover/life/nailao.png",
    	                "resource/template/image/cover/life/suannai.png",
    	                "resource/template/image/cover/life/naifen.png"
    	            ],
    	            "model": [
    	                "一份牛奶200ml",
    	                "一份奶酪25g",
    	                "一份酸奶125ml*2",
    	                "一份奶粉10g"
    	            ]
    	        },{
    	            "optionId": "O90103",
    	            "optionName": "脱脂奶及奶制品",
    	            "sort": "3",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": "",
    	            "model": ""
    	        },{
    	            "optionId": "O90104",
    	            "optionName": "大豆类",
    	            "sort": "4",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": [
    	                "resource/template/image/cover/life/huangdou.png",
    	                "resource/template/image/cover/life/doujiang.png",
    	                "resource/template/image/cover/life/dougan.png",
    	                "resource/template/image/cover/life/beidoufu.png"
    	            ],
    	            "model": [
    	                "一份黄豆20g",
    	                "一份豆浆360ml",
    	                "一份豆干45g",
    	                "一份北豆腐60g"
    	            ]
    	        },{
    	            "optionId": "O90105",
    	            "optionName": "鱼虾类",
    	            "sort": "5",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": [
    	                "resource/template/image/cover/life/sanwenyu.png",
    	                "resource/template/image/cover/life/daiyu.png",
    	                "resource/template/image/cover/life/xia.png",
    	                "resource/template/image/cover/life/caoyu.png"
    	            ],
    	            "model": [
    	                "一份三文鱼50g",
    	                "一份带鱼65g可食部分50g",
    	                "一份草虾85g可食部分50g",
    	                "一份草鱼90g可食部分50g"
    	            ]
    	        },{
    	            "optionId": "O90106",
    	            "optionName": "坚果（非油炸）",
    	            "sort": "6",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": [
    	                "resource/template/image/cover/life/guaziren.png",
    	                "resource/template/image/cover/life/guazi.png",
    	                "resource/template/image/cover/life/huashengmi.png",
    	                "resource/template/image/cover/life/huasheng.png"
    	            ],
    	            "model": [
    	                "一份瓜子仁10g",
    	                "一份瓜子24g",
    	                "一份花生米10g",
    	                "一份花生28g"
    	            ]
    	        },{
    	            "optionId": "O90107",
    	            "optionName": "动物内脏",
    	            "sort": "7",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": "resource/template/image/cover/life/901-7.png",
    	            "model": "一份鸡肝50g"
    	        }
    	    ],
    	    "hidden": ["O90108"]
    	},{
       	    "problemId": "p902",
       	    "order": "30",
       	    "problemContent": "请根据你的实际情况填写下方常见零食摄入情况(若未摄入某类食物，则无需填写。) ",
       	    "model": "p9",
       	    "hasOther": false,
       	    "hint": "",
       	    "ratio": "",
       	    "options": [
       	        {
       	            "optionId": "O90201",
       	            "optionName": "饼干(图示为25g)",
       	            "sort": "1",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/binggan.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90202",
       	            "optionName": "蛋糕(图示为40g)",
       	            "sort": "2",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/dangao.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90203",
       	            "optionName": "巧克力(图示为50g)",
       	            "sort": "3",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/qiaokeli.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90204",
       	            "optionName": "蜜饯(图示为50g)",
       	            "sort": "4",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/mijian.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90205",
       	            "optionName": "糖果(图示为20g)",
       	            "sort": "5",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/tangguo.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90207",
       	            "optionName": "海苔(图示为2g)",
       	            "sort": "7",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/haitai.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90208",
       	            "optionName": "膨化食品(图示为45g)",
       	            "sort": "8",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/penghua.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90209",
       	            "optionName": "雪糕(图示为75g)",
       	            "sort": "9",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/xuegao.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90210",
       	            "optionName": "饮料(图示为500g)",
       	            "sort": "10",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/yinliao.png",
       	            "model": "g"
       	        },{
       	            "optionId": "O90206",
       	            "optionName": "高油高糖面包(图示为100g)",
       	            "sort": "6",
       	            "validate": "^\\d*(\\.\\d{1})?$",
       	            "imgUrl": "resource/template/image/cover/life/gaoyougaotangmiaobao.png",
       	            "model": "g"
       	        }
       	    ],
       	    "hidden": ""
       	},{
    	    "problemId": "p903",
    	    "order": "31",
    	    "problemContent": "请根据你的实际情况填写下方常见营养食品的摄入情况(若未摄入某类食物，则无需填写。) ",
    	    "model": "p9",
    	    "hasOther": false,
    	    "hint": "",
    	    "ratio": "",
    	    "options": [
    	        {
    	            "optionId": "O90301",
    	            "optionName": "燕窝(图示为35g)",
    	            "sort": "1",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": "resource/template/image/cover/life/yanwo.png",
    	            "model": "g"
    	        },{
    	            "optionId": "O90302",
    	            "optionName": "海参(图示为80g)",
    	            "sort": "2",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": "resource/template/image/cover/life/haisheng.png",
    	            "model": "g"
    	        },{
    	            "optionId": "O90303",
    	            "optionName": "蜂蜜(图示为5g)",
    	            "sort": "3",
    	            "validate": "^\\d*(\\.\\d{1})?$",
    	            "imgUrl": "resource/template/image/cover/life/fengmi.png",
    	            "model": "g"
    	        }
    	    ],
    	    "hidden": ""
    	},{
    	    "problemId": "p904",
    	    "order": "32",
    	    "problemContent": "平均每天总用油量",
    	    "model": "p1",
    	    "hasOther": false,
    	    "hint": "",
    	    "ratio": "",
    	    "options": [
    	        {
    	            "optionId": "O90401",
    	            "optionName": "每日用油＜10g：几乎无油饮食，全天烹饪用油不超过一羹匙",
    	            "sort": "1",
    	            "validate": "",
    	            "imgUrl": "",
    	            "model": ""
    	        },{
    	            "optionId": "O90402",
    	            "optionName": "每日用油＜20g：饮食清淡，烹饪时很少放油",
    	            "sort": "2",
    	            "validate": "",
    	            "imgUrl": "",
    	            "model": ""
    	        },{
    	            "optionId": "O90403",
    	            "optionName": "每日用油约为25g：很少摄入煎炸制品，并且日常饮食盘底无明显油渍",
    	            "sort": "3",
    	            "validate": "",
    	            "imgUrl": "",
    	            "model": ""
    	        },{
    	            "optionId": "O90404",
    	            "optionName": "每日用油约为35g：偶尔摄入煎炸制品，或日常饮食盘底有少量油渍",
    	            "sort": "4",
    	            "validate": "",
    	            "imgUrl": "",
    	            "model": ""
    	        },{
    	            "optionId": "O90405",
    	            "optionName": "每日用油＞40g：经常摄入煎炸制品，或日常饮食盘底有明显油渍",
    	            "sort": "5",
    	            "validate": "",
    	            "imgUrl": "",
    	            "model": ""
    	        }
    	    ],
    	    "hidden": ""
    	},{
    	    "problemId": "p110",
    	    "order": "33",
    	    "problemContent": "您在孕期的碘摄入情况",
    	    "model": "p1",
    	    "hasOther": false,
    	    "hint": "备注：富含碘的食物如海带、紫菜、淡菜、海鱼、虾贝类尤其是紫菜和海带",
    	    "ratio": "",
    	    "options": [
    	        {
    	            "optionId": "O11001",
    	            "optionName": "不使用加碘盐或不能保证每周都能食用富含碘的食物",
    	            "sort": "1",
    	            "validate": "",
    	            "imgUrl": "",
    	            "model": ""
    	        },{
    	            "optionId": "O11002",
    	            "optionName": "使用加碘盐并且每周都能够食用富含碘的食物",
    	            "sort": "2",
    	            "validate": "",
    	            "imgUrl": "",
    	            "model": ""
    	        }
    	    ],
    	    "hidden": ""
    	},{
			"problemId": "p111",
			"order": "34",
			"problemContent": "您在孕期还有哪些经常摄入的营养食品/医学食品/特膳食品：@#$ ",
			"model": "p12",
			"hasOther": false,
			"hint": "",
			"ratio": "",
			"options":"",
			"hidden": ""
		}
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
						console.log(data);
						opener.updateInspectStatus($("#inspectId").val(), data.value);
						var url = URL.get("Question.DIETARY_FREQUENCY_REPOER");
						var params = "id=" + $("#inspectId").val();
						ajax.post(url, params, dataType.json, function(data){
							window.location.href = PublicConstant.basePath +"/"+ data.value;
						});
					}
					
				}
			});
		}
};

var commonInfo = {
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