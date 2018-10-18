var recordData;// 选中项信息
var recordRow;// 选中行信息
var recordTable;// table，注意命名不要重复
var selectedDiagnosisId;

//配置datatable
var recordTableOptions = {
	id: "recordListTable",
	form: "recordQuery",
	columns: [
		{"data": null,"sClass": "text-center"},
		{"data": "diagnosisDate","sClass": "text-center" },
		{"data": "diagnosisGestationalWeeks","sClass": "text-center",
			render: function(data, type, row, meta) {
				return pregnancy.gestationalWeeksSupHtml(data);
			}
		},
		{"data": "diagnosisUserName","sClass": "text-center" },
		{"data": "diagnosisDiseases",
			render: function(data, type, row, meta) {
				if(!common.isEmpty(data)){
					data = data.replace("#", "、");
					if(common.endWith(data, "、")){
						data = data.substring(0, data.length-1);
					}
					if(common.startWith(data, "、")){
						data = data.substring(1);
					}
				}
				return data;
			}
		},
		{"data": null,"sClass": "text-center",
		 	render: function(data, type, row, meta) {
				return "<a onclick='getDiagnosisHistory(\""+data.diagnosisId+"\")'>查看</a>";
			}
		}
	],
	orderIndex: 0
};

/**
 * 查看诊断分析
 * @param diagnosisId
 */
function getDiagnosisHistory(diagnosisId){
	common.openWindow(PublicConstant.basePath + "/page/platform/tool/tool_disgnosis_summary_window.jsp?diagnosisId=" + diagnosisId);
}
