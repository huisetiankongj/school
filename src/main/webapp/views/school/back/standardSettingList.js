$(function() {
	var standardSettingSvc = {
			url: {
				findStandardSettingList: rootPath + "/back/standardSetting/list?t="+new Date().getTime(),
				findStandardSettingForm: rootPath + "/back/standardSetting/form?t="+new Date().getTime(),
				findStandardSettingdelete: rootPath + "/back/standardSetting/delete?t="+new Date().getTime()
			},
			fnSave: function(){
				standardSettingSvc.fnchange('创建信息');
			},
			fnDetail: function(info){
				
				standardSettingSvc.fnchange('查看信息',info);
			},
			fnUpdate: function(info){
				
				standardSettingSvc.fnchange('修改信息',info);
			},
			fnDelete: function(standardSettings){
				Svc.AjaxJson.post(standardSettingSvc.url.findStandardSettingdelete,standardSettings,function(response){
					if(response.data=true){
						layer.msg('删除成功！');
						standardSettingTable.fnDraw();
					}else{
						layer.alert(response.join('<br/>'));
					}
				});
			},
			fnchange: function(title,info){
				API.fnShowForm({
					id: 'standardSettingLayerForm',
					url: standardSettingSvc.url.findStandardSettingForm,
					title: title,
					area: ['700px', '550px'],
					params: info?{id:info.id}:{}
				});
			},
			fnRegisterEvent: function(){
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#standardSettingSearchForm"));
					standardSettingTable.reDrawParams = params;
					standardSettingTable.fnDraw();
				});
			}
	}
	//---------------------------------------列表------------------------------------------------
	var standardSettingTable = $('#datatables_StandardSetting').dataTable({
		sAjaxSource: standardSettingSvc.url.findStandardSettingList,
		fnServerData: fnServer(),
		oDTCheckbox: {
	        iColIndex:0
	    },
		aoColumnDefs: [
				{ aTargets: [0], mData: "id", sClass: "text-center", sTitle: "<input type='checkbox' class='TableCheckall'>",bSortable: false, sWidth: "20px"},
				 { aTargets: [1], mDataProp: "orgId", sTitle: "org_id", mRender: function(v){
				      if(v)
							return '<a class="Item-Detail" href="javascript:;">'+v+'</a>';
						else
							return '<a class="Item-Detail" href="javascript:;">--</a>';
					}},
				 { aTargets: [2], mDataProp: "subjectId", sTitle: "subject_id", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [3], mDataProp: "passScore", sTitle: "pass_score", mRender: function(v){
						return v||'-';
					}},
				 { aTargets: [4], mDataProp: "excellentScore", sTitle: "excellent_score", mRender: function(v){
						return v||'-';
					}},
				{ aTargets: [5], sClass: "opperColumn", sTitle: "操作", mData: function(data){
					var buttons = [];
					$.each(standardSettingTable.permission,function(i,perm){
						switch(perm){
						case 'back:standardSetting:edit':
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
				$.each(standardSettingTable.permission,function(i,perm){
					switch(perm){
					case 'back:standardSetting:edit':
					case 'permission:all':
					     //新增信息
					  $('#addStandardSetting').click(function(){
						  standardSettingSvc.fnSave();
					   });
					
						$('#batchDeleteStandardSetting').click(function(){
							var info = DTCheckbox.fnGetInstance("datatables_StandardSetting").selectData;
				        	if($.isEmptyObject(info)){
				        		layer.msg('请选择要删除的记录。。');
				        		return;
				        	}
							layer.confirm('确定删除已勾选的信息吗？', function(index){
								layer.close(index);
								var standardSettings = [];
					        	$.each(info,function(key,v){
					        		standardSettings.push({id: v.id});
					        	});
					        	standardSettingSvc.fnDelete(standardSettings);
							});
						});
					}
				});
			},
			drawCallback: function( settings ){
				// 查看按钮
				$('.Item-Detail').click(function(){
					var info = standardSettingTable.fnGetData($(this).parents("tr"));
					standardSettingSvc.fnDetail(info);
				});
				
				// 修改按钮
				$('.Item-Update').click(function(){
					var info = standardSettingTable.fnGetData($(this).parents("tr"));
					standardSettingSvc.fnUpdate(info);
				});
			}
		});
	//---------------------------------------界面初始化------------------------------------------------
	standardSettingSvc.fnRegisterEvent();
});