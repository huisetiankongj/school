$(function() {
	var oTable;
	var allSubjectAchievementSvc = {
			url: {
				getTaskSubject: rootPath + "/back/examTask/getTaskSubject?t="+new Date().getTime(),
				allSubjectAchievement: rootPath + "/back/studentAchievement/allSubjectAchievement?t="+new Date().getTime(),
				exportAllSubject: rootPath + "/back/studentAchievement/exportAllSubject?t="+new Date().getTime()
			},
			fnRegisterEvent: function(){
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#allSubjectAchievementForm"));
					var dynAllColumn = [];
					for(var i=0,len=dynColum1.length;i<len;i++){
						dynAllColumn.push(dynColum1[i])
					}
					Svc.AjaxJson.post(allSubjectAchievementSvc.url.getTaskSubject,{"id":params.taskId},function (data){
						$.each(data,function (i,v) {
							var colObj = {aTargets:[i+3],mData: "subject"+v.value, sClass: "text-center", sTitle: v.label,bSortable: false};
							dynAllColumn.push(colObj);
						})
						if(oTable){
							oTable.fnClearTable();
							oTable.fnDestroy();
							oTable = null;
							$('#datatable1').html("");
							$('#datatable1').append('<table id="datatables_allSubjectAchievement" class="display table-bordered" cellspacing="0"></table>');
						}

						initTable(params,dynAllColumn);

					})
				});
				//导出
				$("#exportBtn").click(function(){
					var params = Svc.formToJson($("#allSubjectAchievementForm"));
					$("body").append(['<form id="exportAllAchievenmentForm1" style="display:none;" action="',allSubjectAchievementSvc.url.exportAllSubject,'" >',
									  ' <input type="hidden" name="taskId" value="',params.taskId,'" />',
						              ' <input type="hidden" name="taskName" value="',$("#taskIdImport option:selected").text(),'" />',
									  '</form>'].join(""))
					$("#exportAllAchievenmentForm1").submit().remove();

				})

				//时间区间选择
				$("#beginTime").click(function(){
					var date = new Date();
					date.setMonth( date.getMonth()-3 );
					date.setDate(1);
					WdatePicker({
						dateFmt:'yyyy-MM-dd HH:mm:ss',
						maxDate:'#F{$dp.$D(\'endTime\')}',
						minDate: date.format('yyyy-MM-dd') + ' 00:00:00',
						hmsMenuCfg: {
							H: [1, 6],
							m: [1, 8],
							s: [1, 8]
						}
					});
				});
				$("#endTime").click(function(){
					WdatePicker({
						dateFmt:'yyyy-MM-dd HH:mm:ss',
						minDate:'#F{$dp.$D(\'beginTime\')}',
						hmsMenuCfg: {
							H: [1, 6],
							m: [1, 8],
							s: [1, 8]
						}
					});
				});
				var dateStr = new Date().format('yyyy-MM-dd');
				$('#beginTime').val(dateStr + ' 00:00:00');
				$('#endTime').val(dateStr + ' 23:59:59');
			}
	}

	var dynColum1 = [
		{ aTargets: [0], mDataProp: "stuOrgName", sTitle: "学生班级"},
		{ aTargets: [1], mDataProp: "stuNo", sTitle: "学生学号", mRender: function(v){
			return v||'-';
		}},
		{ aTargets: [2], mDataProp: "stuName", sTitle: "学生姓名", mRender: function(v){
			return v||'-';
		}}
	];

	function initTable(params,dyncolmn){
		var tableConfig={
			sAjaxSource : allSubjectAchievementSvc.url.allSubjectAchievement,
			fnServerData : fnServer(params),
			oTableTools : {
				aButtons:[]
			},
			aoColumnDefs :dyncolmn
		}
		oTable = $('#datatables_allSubjectAchievement').dataTable(tableConfig);
	}


	allSubjectAchievementSvc.fnRegisterEvent();
	$("#searchBtn").trigger('click');

	function reOrder(obj){
		for(var i=0;i<obj.length;i++)
		{
			obj[i].aTargets=[i];
		}
	}
});