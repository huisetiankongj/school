$(function() {
	var studentAchievementSvc = {
			url: {
				findStudentAchievementList: rootPath + "/back/studentAchievement/list?t="+new Date().getTime(),
				findStudentAchievementForm: rootPath + "/back/studentAchievement/form?t="+new Date().getTime(),
				findStudentAchievementdelete: rootPath + "/back/studentAchievement/delete?t="+new Date().getTime(),
				import: rootPath + "/back/studentAchievement/importPage?t="+new Date().getTime()
			},
			fnSave: function(){
				studentAchievementSvc.fnchange('创建信息');
			},
			fnDetail: function(info){
				
				studentAchievementSvc.fnchange('查看信息',info);
			},
			fnUpdate: function(info){
				studentAchievementSvc.fnchange('修改信息',info);
			},
			fnDelete: function(studentAchievements){
				Svc.AjaxJson.post(studentAchievementSvc.url.findStudentAchievementdelete,studentAchievements,function(response){
					if(response.data=true){
						layer.msg('删除成功！');
						studentAchievementTable.fnDraw();
					}else{
						layer.alert(response.join('<br/>'));
					}
				});
			},
			fnchange: function(title,info){
				API.fnShowForm({
					id: 'studentAchievementLayerForm',
					url: studentAchievementSvc.url.findStudentAchievementForm,
					title: title,
					area: ['700px', '550px'],
					params: info?{id:info.id}:{}
				});
			},
			fnRegisterEvent: function(){
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#studentAchievementSearchForm"));
					studentAchievementTable.reDrawParams = params;
					studentAchievementTable.fnDraw();
				});
				//导入按钮
				$("#importBtn").click(function(){
					API.fnShowForm({
						id: 'studentAchImportForm',
						url: studentAchievementSvc.url.import,
						title: "批量导入",
						area: ['560px', '500px'],
						zIndex: 200,
						params: {stuOrgId:""}
					});
				})
			}
	}
	//---------------------------------------列表------------------------------------------------
	var studentAchievementTable = $('#datatables_StudentAchievement').dataTable({
		sAjaxSource: studentAchievementSvc.url.findStudentAchievementList,
		fnServerData: fnServer(),
		oDTCheckbox: {
	        iColIndex:0
	    },
		aoColumnDefs: [
				{ aTargets: [0], mData: "id", sClass: "text-center", sTitle: "<input type='checkbox' class='TableCheckall'>",bSortable: false, sWidth: "20px"},
				{ aTargets: [1], mDataProp: "taskName", sTitle: "任务"},
				{ aTargets: [2], mDataProp: "orgName", sTitle: "班级名称", mRender: function(v){
						return v||'-';
				}},
				{ aTargets: [3], mDataProp: "subjectName", sTitle: "学科", mRender: function(v){
					return v||'-';
				}},
				 { aTargets: [4], mDataProp: "stuName", sTitle: "学生", mRender: function(v){
						return v||'-';
					}},

				 { aTargets: [5], mDataProp: "score", sTitle: "成绩", mRender: function(v){
						return v||'-';
					}},
				{ aTargets: [6], sClass: "opperColumn", sTitle: "操作", mData: function(data){
					var buttons = [];
					$.each(studentAchievementTable.permission,function(i,perm){
						switch(perm){
						case 'back:studentAchievement:edit':
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
				$.each(studentAchievementTable.permission,function(i,perm){
					switch(perm){
					case 'back:studentAchievement:edit':
					case 'permission:all':
					     //新增信息
					  $('#addStudentAchievement').click(function(){
						  studentAchievementSvc.fnSave();
					   });
					
						$('#batchDeleteStudentAchievement').click(function(){
							var info = DTCheckbox.fnGetInstance("datatables_StudentAchievement").selectData;
				        	if($.isEmptyObject(info)){
				        		layer.msg('请选择要删除的记录。。');
				        		return;
				        	}
							layer.confirm('确定删除已勾选的信息吗？', function(index){
								layer.close(index);
								var studentAchievements = [];
					        	$.each(info,function(key,v){
					        		studentAchievements.push({id: v.id});
					        	});
					        	studentAchievementSvc.fnDelete(studentAchievements);
							});
						});
					}
				});
			},
			drawCallback: function( settings ){
				// 查看按钮
				$('.Item-Detail').click(function(){
					var info = studentAchievementTable.fnGetData($(this).parents("tr"));
					studentAchievementSvc.fnDetail(info);
				});
				
				// 修改按钮
				$('.Item-Update').click(function(){
					var info = studentAchievementTable.fnGetData($(this).parents("tr"));
					studentAchievementSvc.fnUpdate(info);
				});
			}
		});
	//---------------------------------------左侧组织机构树------------------------------------------------
	var organTree;
	function fnTreeClick(event, treeId, treeNode){
		$("#achOrgId").val(treeNode.id);
		$("#searchBtn").trigger('click');
	};
	var treeSetting= {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: fnTreeClick,
		}
	};
	function fnReloadTree(callback){
		Svc.AjaxForm.get($("#organTreeUl").attr("data-url"),{},function(data){
			data.splice(0, 0, {id: "allcontent",pId: 0, name: "全部", add: true });
			try {
				organTree.destroy();
			} catch (e) {
			}
			organTree = $.fn.zTree.init($("#organTreeUl"), treeSetting, data);
			callback && callback();
		});
	};
	//---------------------------------------界面初始化------------------------------------------------
	fnReloadTree();
	studentAchievementSvc.fnRegisterEvent();
});