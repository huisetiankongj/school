$(function() {
	var examTaskStatisticsSvc = {
			url: {
				findExamTaskStatisticsList: rootPath + "/back/examTaskStatistics/list?t="+new Date().getTime(),
				findExamTaskStatisticsForm: rootPath + "/back/examTaskStatistics/form?t="+new Date().getTime(),
				findExamTaskStatisticsdelete: rootPath + "/back/examTaskStatistics/delete?t="+new Date().getTime(),
				fnScoreDetail:rootPath + "/back/studentAchievement/scoreDetail?t="+new Date().getTime()
			},
			fnSave: function(){
				examTaskStatisticsSvc.fnchange('创建信息');
			},
			fnDetail: function(info){
				examTaskStatisticsSvc.fnchange('查看信息',info);
			},
			fnUpdate: function(info){
				examTaskStatisticsSvc.fnchange('修改信息',info);
			},
			fnScoreDetail:function(info){
				window.scoreDetail = info;
				API.fnShowForm({
					id: 'examTaskStatisticsLayerForm',
					url: examTaskStatisticsSvc.url.fnScoreDetail,
					title: "成绩明细",
					area: ['700px', '550px'],
					params: info?{id:info.id}:{}
				});
			},
			fnDelete: function(examTaskStatisticss){
				Svc.AjaxJson.post(examTaskStatisticsSvc.url.findExamTaskStatisticsdelete,examTaskStatisticss,function(response){
					if(response.data=true){
						layer.msg('删除成功！');
						examTaskStatisticsTable.fnDraw();
					}else{
						layer.alert(response.join('<br/>'));
					}
				});
			},
			fnchange: function(title,info){
				API.fnShowForm({
					id: 'examTaskStatisticsLayerForm',
					url: examTaskStatisticsSvc.url.findExamTaskStatisticsForm,
					title: title,
					area: ['700px', '550px'],
					params: info?{id:info.id}:{}
				});
			},
			fnRegisterEvent: function(){
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#examTaskStatisticsSearchForm"));
					/*params.subjectId&&(params.subjectId=params.subjectId.join(","));*/
					examTaskStatisticsTable.reDrawParams = params;
					examTaskStatisticsTable.fnDraw();
				});

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
	//---------------------------------------列表------------------------------------------------
	var examTypeObj = {"1":"平时","2":"期中","3":"期末"};
	var examTaskStatisticsTable = $('#datatables_ExamTaskStatistics').dataTable({
		sAjaxSource: examTaskStatisticsSvc.url.findExamTaskStatisticsList,
		fnServerData: fnServer(),
		oDTCheckbox: {
	        iColIndex:0
	    },
		aoColumnDefs: [
				 { aTargets: [0], mData: "id", sClass: "text-center", sTitle: "<input type='checkbox' class='TableCheckall'>",bSortable: false, sWidth: "20px"},
			     { aTargets: [1], mDataProp: "taskName", sTitle: "考试任务"},
				 { aTargets: [2], mDataProp: "examType", sTitle: "考试类型", mRender: function(v){
						return examTypeObj[v]||'-';
					}},
				 { aTargets: [3], mDataProp: "orgName", sTitle: "班级"},
				 { aTargets: [4], mDataProp: "subjectName", sTitle: "学科", mRender: function(v){
						return v||'-';
					}},
				{ aTargets: [5], mDataProp: "avgScore", sTitle: "总人数"},
				{ aTargets: [6], mDataProp: "allNum", sTitle: "与考人数",mData:function(data){
					return ['<a class="detailinfo" style="color:red;width:50px;" data-type="all" data-subjectId="',data.subjectId,'" data-orgId="',data.orgId,'"  data-taskId="',data.taskId,'">',data.allNum,'</a>'].join("");
				}},
				{ aTargets: [7], mDataProp: "totalScore", sTitle: "总分"},
				 { aTargets: [8], mDataProp: "avgScore", sTitle: "平均分"},
				 { aTargets: [9], mDataProp: "badNum", sTitle: "不及格人数",mData:function(data){
					 return ['<a class="detailinfo" style="color:red;width:50px;" data-type="bad" data-subjectId="',data.subjectId,'" data-orgId="',data.orgId,'"  data-taskId="',data.taskId,'">',data.badNum,'</a>'].join("");
				 }},
				 { aTargets: [10], mDataProp: "passNum", sTitle: "及格人数",mData:function(data){
						 return ['<a class="detailinfo" style="color:red;width:50px;" data-type="pass" data-subjectId="',data.subjectId,'" data-orgId="',data.orgId,'"  data-taskId="',data.taskId,'">',data.passNum,'</a>'].join("");
				 }},
				 { aTargets: [11],  sTitle: "及格率",mData:function(data){
					return Math.floor(parseFloat(data.passNum)/parseFloat(data.allNum)*100)/100;
				}},
				 { aTargets: [12], mDataProp: "excellentNum", sTitle: "优秀人数",mData:function(data){
						 return ['<a class="detailinfo" style="color:red;width:50px;" data-type="excellent" data-subjectId="',data.subjectId,'" data-orgId="',data.orgId,'"  data-taskId="',data.taskId,'">',data.excellentNum,'</a>'].join("");
				 }},
				{ aTargets: [13],  sTitle: "优秀率",mData:function(data){
					return Math.floor(parseFloat(data.excellentNum)/parseFloat(data.allNum)*100)/100;
				}},
				{ aTargets: [14], mDataProp: "maxScore", sTitle: "最高分"},
				{ aTargets: [15], mDataProp: "minScore", sTitle: "最低分"},
				 { aTargets: [16], mDataProp: "allNum", sTitle: "总人数",mData:function(data){
						 return ['<a class="detailinfo" style="color:red;width:50px;" data-type="all" data-subjectId="',data.subjectId,'" data-orgId="',data.orgId,'"  data-taskId="',data.taskId,'">',data.allNum,'</a>'].join("");
				 }},
				 { aTargets: [17], mDataProp: "createTime", sTitle: "时间", mRender: function(v){
						return v||'-';
				 }}
			],
			initComplete: function( settings ){
				$.each(examTaskStatisticsTable.permission,function(i,perm){
					switch(perm){
					case 'back:examTaskStatistics:edit':
					case 'permission:all':
					     //新增信息
					  $('#addExamTaskStatistics').click(function(){
						  examTaskStatisticsSvc.fnSave();
					   });
					
						$('#batchDeleteExamTaskStatistics').click(function(){
							var info = DTCheckbox.fnGetInstance("datatables_ExamTaskStatistics").selectData;
				        	if($.isEmptyObject(info)){
				        		layer.msg('请选择要删除的记录。。');
				        		return;
				        	}
							layer.confirm('确定删除已勾选的信息吗？', function(index){
								layer.close(index);
								var examTaskStatisticss = [];
					        	$.each(info,function(key,v){
					        		examTaskStatisticss.push({id: v.id});
					        	});
					        	examTaskStatisticsSvc.fnDelete(examTaskStatisticss);
							});
						});
					}
				});
			},
			drawCallback: function( settings ){
				// 查看按钮
				$('.Item-Detail').click(function(){
					var info = examTaskStatisticsTable.fnGetData($(this).parents("tr"));
					examTaskStatisticsSvc.fnDetail(info);
				});
				
				// 修改按钮
				$('.Item-Update').click(function(){
					var info = examTaskStatisticsTable.fnGetData($(this).parents("tr"));
					examTaskStatisticsSvc.fnUpdate(info);
				});

				$('.detailinfo').click(function(){
					var info = {},
						$this = $(this);
					info.type = $this.attr("data-type");
					info.taskId = $this.attr("data-taskId");
					info.subjectId = $this.attr("data-subjectId");
					info.orgId = $this.attr("data-orgId");

					examTaskStatisticsSvc.fnScoreDetail(info);
				});
			}
		});
	//---------------------------------------界面初始化------------------------------------------------
	examTaskStatisticsSvc.fnRegisterEvent();
});