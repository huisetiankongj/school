$(function() {
	var userSvc = {
			url: {
				findUser: rootPath + "/sys/user/list?t="+new Date().getTime(),
				userForm: rootPath + "/sys/user/form?t="+new Date().getTime(),
				deleteUser: rootPath + "/sys/user/delete?t="+new Date().getTime(),
				sendOneReport: rootPath + "/cgs/back/common/sendOneReport?t="+new Date().getTime()
			},
			fnSave: function(){
				userSvc.fnUserModal('用户信息');
			},
			fnDelete: function(list){
				Svc.AjaxJson.post(userSvc.url.deleteUser,list,function(response){
					if(response && response.length==0){
						layer.msg('删除成功！');
						userTable.fnDraw();
					}else{
						layer.alert(response.join('<br/>'));
					}
				});
			},
			fnSendOneReport: function(list){
				Svc.AjaxJson.post(userSvc.url.sendOneReport,list,function(response){
					if(response){
						layer.msg('发送成功！');
					}else{
						layer.alert(response.join('<br/>'));
					}
				});
			},
			fnUpdate: function(info){
				userSvc.fnUserModal('用户信息',info);
			},
			fnDetail: function(info){
				userSvc.fnUserModal('用户信息',info);
			},
			fnUserModal: function(title,info){
				API.fnShowForm({
					id: 'userLayerForm',
					url: userSvc.url.userForm,
					title: title,
					params: (info && info.id)? {id: info.id}:{}
				});
			},
			fnRegisterEvent: function(){
				// 刷新组织机构树
				$("#changeE").click(function() {
					fnReloadTree();
				});
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#userSearchForm"));
					params.name && (params.name = '%' + params.name.trim() + '%');
					if(organTree && organTree.getSelectedNodes().length>0 && organTree.getSelectedNodes()[0].id != 'allcontent'){
						var treeNode = organTree.getSelectedNodes()[0];
						params.org = {leftValue: treeNode.leftValue, rightValue: treeNode.rightValue};
					}
					userTable.reDrawParams = params;
					userTable.fnDraw();
				});
				
				$("#sendOneReportBtn").click(function(){
					var info = DTCheckbox.fnGetInstance("datatables_user").selectData;
					var users = [];
		        	$.each(info,function(key,v){
		        		users.push({id: v.id});
		        	});
		        	userSvc.fnSendOneReport(users);
				})
			}
	}

	//---------------------------------------用户列表------------------------------------------------
	var userTable = $('#datatables_user').dataTable({
			sAjaxSource: userSvc.url.findUser,
			fnServerData: fnServer(),
			oDTCheckbox: {
		        iColIndex:0
		    },
			aoColumnDefs: [
					{ aTargets: [ 0 ], mData: "id", sClass: "text-center", sTitle: "<input type='checkbox' class='TableCheckall'>",bSortable: false, sWidth: "20px"},
					{ aTargets: [ 1 ], mDataProp: "org", sTitle: "组织机构", mRender: function(v){
						return v.name;
					}},
					{ aTargets: [ 2 ], mDataProp: "loginName", sTitle: "帐号", mRender: function(v){
						return '<a class="Item-Detail" href="javascript:;">'+v+'</a>';
					}},
					{ aTargets: [ 3 ], mDataProp: "name", sTitle: "用户名"},
					{ aTargets: [ 4 ], mDataProp: "mobile", sTitle: "手机", mRender: function(v){
						return v||'-';
					}},
					{ aTargets: [ 5 ], mDataProp: "email", sTitle: "邮箱", mRender: function(v){
						return v||'-';
					}},
					{ aTargets: [ 6 ], mDataProp: "wechatSynFlag", sTitle: "是否同步", mRender: function(v){
						return v=='1'?'是':'否';
					}},
					{ aTargets: [ 7 ], sClass: "opperColumn", sTitle: "操作", mData: function(data){
						var buttons = [];
						$.each(userTable.permission,function(i,perm){
							switch(perm){
							case 'sys:user:edit':
  								buttons.push('<a class="Item-Update" href="javascript:;"><i class="fa fa-edit"></i>修改</a>','<a class="Item-Delete" href="javascript:;"><i class="fa fa-trash"></i>删除</a>');
  								break;
							case 'permission:all':
								buttons.push('<a class="Item-Update" href="javascript:;"><i class="fa fa-edit"></i>修改</a>','<a class="Item-Delete" href="javascript:;"><i class="fa fa-trash"></i>删除</a>');
								break;
							}
						});
						return buttons.join('&nbsp;&nbsp;');
					}}
				],
			initComplete: function( settings ){
				// 表格头部按钮权限
				$.each(userTable.permission,function(i,perm){
					switch(perm){
					case 'sys:user:edit':
					case 'permission:all':
						$('#addUser, #batchDeleteUser,#sendOneReportBtn').removeClass('hide');
						$('#addUser').click(function(){
							userSvc.fnSave();
						});
						$('#batchDeleteUser').click(function(){
							var info = DTCheckbox.fnGetInstance("datatables_user").selectData;
				        	if($.isEmptyObject(info)){
				        		layer.msg('请选择要删除的记录。。');
				        		return;
				        	}
							layer.confirm('确定删除已勾选的用户吗？', function(index){
								layer.close(index);
								var users = [];
					        	$.each(info,function(key,v){
					        		users.push({id: v.id});
					        	});
					        	userSvc.fnDelete(users);
							});
						});
					}
				});
			},
			drawCallback: function( settings ){
				// 查看按钮
				$('.Item-Detail').click(function(){
					var info = userTable.fnGetData($(this).parents("tr"));
					userSvc.fnDetail(info);
				});
				
				// 修改按钮
				$('.Item-Update').click(function(){
					var info = userTable.fnGetData($(this).parents("tr"));
					userSvc.fnUpdate(info);
				});
				
				// 删除按钮
				$('.Item-Delete').click(function(){
					var info = userTable.fnGetData($(this).parents("tr"));
					layer.confirm('确定删除用户【'+info.loginName+'】吗？', function(index){
						layer.close(index);
						userSvc.fnDelete([{id:info.id,loginName:info.loginName}]);
					});
				});
			}
		});
	//---------------------------------------左侧组织机构树------------------------------------------------
	var organTree;
	function fnTreeClick(event, treeId, treeNode){
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
	userSvc.fnRegisterEvent();
});