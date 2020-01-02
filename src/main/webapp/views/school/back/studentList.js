$(function() {
	var studentSvc = {
			url: {
				findStudentList: rootPath + "/back/student/list?t="+new Date().getTime(),
				findStudentForm: rootPath + "/back/student/form?t="+new Date().getTime(),
				findStudentdelete: rootPath + "/back/student/delete?t="+new Date().getTime(),
				import: rootPath + "/back/student/importPage?t="+new Date().getTime()
			},
			fnSave: function(){
				studentSvc.fnchange('创建信息');
			},
			fnDetail: function(info){
				
				studentSvc.fnchange('查看信息',info);
			},
			fnUpdate: function(info){
				
				studentSvc.fnchange('修改信息',info);
			},
			fnDelete: function(students){
				Svc.AjaxJson.post(studentSvc.url.findStudentdelete,students,function(response){
					if(response.data=true){
						layer.msg('删除成功！');
						studentTable.fnDraw();
					}else{
						layer.alert(response.join('<br/>'));
					}
				});
			},
			fnchange: function(title,info){
				API.fnShowForm({
					id: 'studentLayerForm',
					url: studentSvc.url.findStudentForm,
					title: title,
					area: ['700px', '550px'],
					params: info?{id:info.id}:{}
				});
			},
			fnRegisterEvent: function(){
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#studentSearchForm"));
					console.log(params);
					studentTable.reDrawParams = params;
					studentTable.fnDraw();
				});

				//导入按钮
				$("#importBtn").click(function(){
					API.fnShowForm({
						id: 'studentImportForm',
						url: studentSvc.url.import,
						title: "批量导入",
						area: ['500px', '400px'],
						zIndex: 200,
						params: {stuOrgId:""}
					});
				})
			}
	}
	//---------------------------------------列表------------------------------------------------
	var studentTable = $('#datatables_Student').dataTable({
		sAjaxSource: studentSvc.url.findStudentList,
		fnServerData: fnServer(),
		oDTCheckbox: {
	        iColIndex:0
	    },
		aoColumnDefs: [
				{ aTargets: [0], mData: "id", sClass: "text-center", sTitle: "<input type='checkbox' class='TableCheckall'>",bSortable: false, sWidth: "20px"},
				 { aTargets: [1], mDataProp: "stuOrgName", sTitle: "学生班级"},
				 { aTargets: [2], mDataProp: "stuName", sTitle: "学生姓名", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [3], mDataProp: "stuNo", sTitle: "学生学号", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [4], mDataProp: "stuSex", sTitle: "学生性别", mRender: function(v){
						return v==1?'男':'女';
					}},
				 { aTargets: [5], mDataProp: "stuAge", sTitle: "学生年龄", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [6], mDataProp: "createTime", sTitle: "创建时间", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [7], mDataProp: "state", sTitle: "状态", mRender: function(v){
						return v==1?"正常":'禁用';
					}},
				{ aTargets: [8], sClass: "opperColumn", sTitle: "操作", mData: function(data){
					var buttons = [];
					$.each(studentTable.permission,function(i,perm){
						switch(perm){
						case 'back:student:edit':
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
				$.each(studentTable.permission,function(i,perm){
					switch(perm){
					case 'back:student:edit':
					case 'permission:all':
						$('#addStudent,#batchDeleteStudent').removeClass('hide');
					     //新增信息
					  $('#addStudent').click(function(){
						  studentSvc.fnSave();
					   });
					
						$('#batchDeleteStudent').click(function(){
							var info = DTCheckbox.fnGetInstance("datatables_Student").selectData;
				        	if($.isEmptyObject(info)){
				        		layer.msg('请选择要删除的记录。。');
				        		return;
				        	}
							layer.confirm('确定删除已勾选的信息吗？', function(index){
								layer.close(index);
								var students = [];
					        	$.each(info,function(key,v){
					        		students.push({id: v.id});
					        	});
					        	studentSvc.fnDelete(students);
							});
						});
					}
				});
			},
			drawCallback: function( settings ){
				// 查看按钮
				$('.Item-Detail').click(function(){
					var info = studentTable.fnGetData($(this).parents("tr"));
					studentSvc.fnDetail(info);
				});
				
				// 修改按钮
				$('.Item-Update').click(function(){
					var info = studentTable.fnGetData($(this).parents("tr"));
					studentSvc.fnUpdate(info);
				});
			}
		});
	//---------------------------------------左侧组织机构树------------------------------------------------
	var organTree;
	function fnTreeClick(event, treeId, treeNode){
		$("#stuOrgId").val(treeNode.id);
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
	studentSvc.fnRegisterEvent();
});