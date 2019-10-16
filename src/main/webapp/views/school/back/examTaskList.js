$(function() {
	var examTaskSvc = {
			url: {
				findExamTaskList: rootPath + "/back/examTask/list?t="+new Date().getTime(),
				findExamTaskForm: rootPath + "/back/examTask/form?t="+new Date().getTime(),
				findExamTaskdelete: rootPath + "/back/examTask/delete?t="+new Date().getTime()
			},
			fnSave: function(){
				examTaskSvc.fnchange('创建信息');
			},
			fnDetail: function(info){
				
				examTaskSvc.fnchange('查看信息',info);
			},
			fnUpdate: function(info){
				
				examTaskSvc.fnchange('修改信息',info);
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
				 { aTargets: [2], mDataProp: "etSubjectIds", sTitle: "考试学科ids", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [3], mDataProp: "etSubjectNames", sTitle: "考试学科names", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [4], mDataProp: "etOrgType", sTitle: "考试类型（1所有2年级3班级）", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [5], mDataProp: "etOrgIds", sTitle: "考试班级ids", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [6], mDataProp: "etOrgNames", sTitle: "考试班级names", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [7], mDataProp: "createTime", sTitle: "创建时间", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [8], mDataProp: "createUser", sTitle: "创建人", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [9], mDataProp: "updateTime", sTitle: "修改时间", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [10], mDataProp: "remarks", sTitle: "备注", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [11], mDataProp: "state", sTitle: "状态", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [12], mDataProp: "updateUser", sTitle: "创建人", mRender: function(v){
						return v||'-';
					}},
				{ aTargets: [13], sClass: "opperColumn", sTitle: "操作", mData: function(data){
					var buttons = [];
					$.each(examTaskTable.permission,function(i,perm){
						switch(perm){
						case 'back:examTask:edit':
								buttons.push('<a class="Item-Update" href="javascript:;"><i class="fa fa-edit"></i>修改</a>');
								break;
						case 'permission:all':
							buttons.push('<a class="Item-Update" href="javascript:;"><i class="fa fa-edit"></i>修改</a>');
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
			}
		});
	//---------------------------------------界面初始化------------------------------------------------
	examTaskSvc.fnRegisterEvent();
});