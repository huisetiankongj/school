$(function() {
	var orgSvc = {
			url: {
				findOrg: rootPath + "/sys/org/list?t="+new Date().getTime(),
				orgForm: rootPath + "/sys/org/form?t="+new Date().getTime(),
				deleteOrg: rootPath + "/sys/org/delete?t="+new Date().getTime()
			},
			fnSave: function(){
				orgSvc.fnOrgModal('机构信息');
			},
			fnDelete: function(list){
				Svc.AjaxJson.post(orgSvc.url.deleteOrg,list,function(response){
					if(response == true){
						layer.msg('删除成功！');
						orgTable.fnDraw();
						fnReloadTree();
					}
				});
			},
			fnUpdate: function(info){
				orgSvc.fnOrgModal('机构信息',info);
			},
			fnDetail: function(info){
				orgSvc.fnOrgModal('机构信息',info);
			},
			fnOrgModal: function(title,info){
				API.fnShowForm({
					id: 'orgLayerForm',
					url: orgSvc.url.orgForm,
					title: title,
					params: (info && info.id)? {id: info.id}:{}
				});
			},
			fnRegisterEvent: function(){
				// 刷新机构树
				$("#changeE").click(function() {
					fnReloadTree();
				});
				
				//搜索按钮
				$("#searchBtn").click(function(){
					var params = Svc.formToJson($("#orgSearchForm"));
					params.name && (params.name = '%' + params.name.trim() + '%');
					if(organTree && organTree.getSelectedNodes().length>0 && organTree.getSelectedNodes()[0].id != 'allcontent'){
						var treeNode = organTree.getSelectedNodes()[0];
						params.leftValue = treeNode.leftValue;
						params.rightValue = treeNode.rightValue;
					}
					orgTable.reDrawParams = params;
					orgTable.fnDraw();
				});
			}
	}

	//---------------------------------------机构列表------------------------------------------------
	var orgTable = $('#datatables_org').dataTable({
			oDTCheckbox: {
		        iColIndex:0
		    },
			sAjaxSource: orgSvc.url.findOrg,
			fnServerData: fnServer({parent: {id: 0}}),
			aoColumnDefs: [
					{ aTargets: [ 0 ], mData: "id", sClass: "text-center", sTitle: "<input type='checkbox' class='TableCheckall'/>",bSortable: false, sWidth: "20px"},
					{ aTargets: [ 1 ], mDataProp: "name", sTitle: "机构名称", mRender: function(v){
						return '<a class="Item-Detail" href="javascript:;">'+v+'</a>';
					}},
					{ aTargets: [ 2 ], mDataProp: "area", sTitle: "归属区域", mRender: function(v){
						return v.name;
					}},
					{ aTargets: [ 3 ], mDataProp: "code", sTitle: "机构编码", mRender: function(v){
						return v||'-';
					}},
					{ aTargets: [ 4 ], mDataProp: "type", sTitle: "机构类型", mRender: function(v){
						return sys_org_type[v]||'-';
					}},
					{ aTargets: [ 5 ], mDataProp: "wechatSynFlag", sTitle: "是否同步", mRender: function(v){
						return v=='1'?'是':'否';
					}},
					{ aTargets: [ 6 ], mDataProp: "remarks", sTitle: "备注", mRender: function(v){
						return v||'-';
					}},
					{ aTargets: [ 7 ], sClass: "opperColumn", sTitle: "操作", mData: function(data){
						var buttons = [];
						$.each(orgTable.permission,function(i,perm){
							switch(perm){
							case 'sys:org:edit':
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
				$.each(orgTable.permission,function(i,perm){
					switch(perm){
					case 'sys:org:edit':
					case 'permission:all':
						$('#addOrg, #batchDeleteOrg').removeClass('hide');
						$('#addOrg').click(function(){
							orgSvc.fnSave();
						});
						$('#batchDeleteOrg').click(function(){
							var info = DTCheckbox.fnGetInstance("datatables_org").selectData;
				        	if($.isEmptyObject(info)){
				        		layer.msg('请选择要删除的记录。。');
				        		return;
				        	}
							layer.confirm('确定删除已勾选的机构及所有子机构项吗？', function(index){
								layer.close(index);
								var orgs = [];
					        	$.each(info,function(key,v){
					        		orgs.push({id: v.id});
					        	});
					        	orgSvc.fnDelete(orgs);
							});
						});
						break;
					}
				});
			},
			drawCallback: function( settings ){
				// 查看按钮
				$('.Item-Detail').click(function(){
					var info = orgTable.fnGetData($(this).parents("tr"));
					orgSvc.fnDetail(info);
				});
				
				// 修改按钮
				$('.Item-Update').click(function(){
					var info = orgTable.fnGetData($(this).parents("tr"));
					orgSvc.fnUpdate(info);
				});
				
				// 删除按钮
				$('.Item-Delete').click(function(){
					var info = orgTable.fnGetData($(this).parents("tr"));
					layer.confirm('确定删除机构【'+info.name+'】及所有子机构项吗？', function(index){
						layer.close(index);
						orgSvc.fnDelete([{id:info.id}]);
					});
				});
			}
		});
	//---------------------------------------左侧机构树------------------------------------------------
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
	window.fnOrgReloadTree = fnReloadTree;
	//---------------------------------------界面初始化------------------------------------------------
	fnReloadTree();
	orgSvc.fnRegisterEvent();
});