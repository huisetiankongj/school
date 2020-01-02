$(function() {
	var examTaskSvc = {
			url: {
				findExamTaskList: rootPath + "/back/examTask/list?t="+new Date().getTime(),
				findExamTaskForm: rootPath + "/back/examTask/form?t="+new Date().getTime(),
				findExamTaskdelete: rootPath + "/back/examTask/delete?t="+new Date().getTime(),
				fnGenReport:rootPath + "/back/examTask/genReport?t="+new Date().getTime()
			},
			fnSave: function(){
				examTaskSvc.fnchange('创建任务');
			},
			fnDetail: function(info){
				
				examTaskSvc.fnchange('查看任务',info);
			},
			fnUpdate: function(info){
				
				examTaskSvc.fnchange('修改任务',info);
			},
			fnDelete: function(examTasks){
				Svc.AjaxJson.post(examTaskSvc.url.findExamTaskdelete,examTasks,function(response){
					if(response.data=true){
						layer.msg('删除成功！');
						examTaskTable.fnDraw();
					}else{
						layer.alert(response.join('<br/>'));
					}
				});
			},
			fnGenReport:function(info){
				Svc.AjaxJson.post(examTaskSvc.url.fnGenReport,info,function(response){
					if(response == true){
						layer.alert('生成成功！',function(index){
							$('#datatables_ExamTask').dataTable().fnDraw();
							layer.close(index);
						});
					}
				});
			},
			fnchange: function(title,info){
				API.fnShowForm({
					id: 'examTaskLayerForm',
					url: examTaskSvc.url.findExamTaskForm,
					title: title,
					area: ['700px', '550px'],
					params: info?{id:info.id}:{}
				});
			},
			fnRegisterEvent: function(){
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#examTaskSearchForm"));
					examTaskTable.reDrawParams = params;
					examTaskTable.fnDraw();
				});
			}
	}
	//---------------------------------------列表------------------------------------------------
	var typeJson = {"1":"平时","2":"期中","3":"期末"},
		etOrgTypeJson = {"1":"所有","2":"年级","3":"班级"};
	var examTaskTable = $('#datatables_ExamTask').dataTable({
		sAjaxSource: examTaskSvc.url.findExamTaskList,
		fnServerData: fnServer(),
		oDTCheckbox: {
	        iColIndex:0
	    },
		aoColumnDefs: [
				{ aTargets: [0], mData: "id", sClass: "text-center", sTitle: "<input type='checkbox' class='TableCheckall'>",bSortable: false, sWidth: "20px"},
				 { aTargets: [1], mDataProp: "etTitle", sTitle: "考试标题", mRender: function(v){
				      if(v)
							return '<a class="Item-Detail" href="javascript:;">'+v+'</a>';
						else
							return '<a class="Item-Detail" href="javascript:;">--</a>';
					}},
				{ aTargets: [2], mDataProp: "type", sTitle: "考试类型", mRender: function(v){
					return typeJson[v]||'-';
				}},
				 { aTargets: [3], mDataProp: "etSubjectNames", sTitle: "考试学科", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [4], mDataProp: "etOrgType", sTitle: "范围类型", mRender: function(v){
						 return etOrgTypeJson[v]||'-';
					}},
				 { aTargets: [5], mDataProp: "etOrgNames", sTitle: "考试班级", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [6], mDataProp: "createTime", sTitle: "创建时间", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [7], mDataProp: "createUserName", sTitle: "创建人", mRender: function(v){
						return v||'-';
				 }},
				 { aTargets: [8], mDataProp: "updateTime", sTitle: "修改时间", mRender: function(v){
						return v||'-';
					}},
				{ aTargets: [9], mDataProp: "updateUserName", sTitle: "修改人", mRender: function(v){
					return v||'-';
				}},
				{ aTargets: [10], sClass: "opperColumn", sTitle: "操作", mData: function(data){
					var buttons = [];
					$.each(examTaskTable.permission,function(i,perm){
						switch(perm){
						case 'back:examTask:edit':
								buttons.push('<a class="Item-Update" href="javascript:;"><i class="fa fa-edit"></i>修改</a>');
								break;
						case 'back:examTask:genReport':
							buttons.push('<a class="Item-GenReport" href="javascript:;"><i class="fa fa-edit"></i>生成报表</a>');
							break;
						case 'permission:all':
							buttons.push('<a class="Item-Update" href="javascript:;"><i class="fa fa-edit"></i>修改</a>');
							buttons.push('<a class="Item-GenReport" href="javascript:;"><i class="fa fa-edit"></i>生成报表</a>');
							break;
						}
					});
					return buttons.join('&nbsp;&nbsp;');
				}}
			],
			initComplete: function( settings ){
				$.each(examTaskTable.permission,function(i,perm){
					switch(perm){
					case 'back:examTask:edit':
					case 'permission:all':
						$('#addExamTask,#batchDeleteExamTask').removeClass('hide');
					     //新增信息
					  	$('#addExamTask').click(function(){
						  	examTaskSvc.fnSave();
					   	});
					
						$('#batchDeleteExamTask').click(function(){
							var info = DTCheckbox.fnGetInstance("datatables_ExamTask").selectData;
				        	if($.isEmptyObject(info)){
				        		layer.msg('请选择要删除的记录。。');
				        		return;
				        	}
							layer.confirm('确定删除已勾选的信息吗？', function(index){
								layer.close(index);
								var examTasks = [];
					        	$.each(info,function(key,v){
					        		examTasks.push({id: v.id});
					        	});
					        	examTaskSvc.fnDelete(examTasks);
							});
						});
					}
				});
			},
			drawCallback: function( settings ){
				// 查看按钮
				$('.Item-Detail').click(function(){
					var info = examTaskTable.fnGetData($(this).parents("tr"));
					examTaskSvc.fnDetail(info);
				});
				
				// 修改按钮
				$('.Item-Update').click(function(){
					var info = examTaskTable.fnGetData($(this).parents("tr"));
					examTaskSvc.fnUpdate(info);
				});

				// 修改按钮
				$('.Item-GenReport').click(function(){
					var info = examTaskTable.fnGetData($(this).parents("tr"));
					layer.confirm('确定生成报表吗？', function(index){
						layer.close(index);
						console.log(info)
						examTaskSvc.fnGenReport(info);
					});
				});

			}
		});
	//---------------------------------------界面初始化------------------------------------------------
	examTaskSvc.fnRegisterEvent();
});