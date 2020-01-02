$(function(){
	var orgFormSvc = {
			url: {
				saveOrg : rootPath + "/sys/org/save?t="+new Date().getTime()
			},
			fnCommit: function(){
				var orgJson = Svc.formToJson($("#orgForm"));
	        	Svc.AjaxForm.post(orgFormSvc.url.saveOrg,orgJson,function(response){
	        		if(response == true){
	        			layer.alert('保存机构'+orgJson.name+'成功！',function(index){
	        				$('#datatables_org').dataTable().fnDraw();
	        				window.fnOrgReloadTree();
	        				layer.close(index);
	        				API.fnHideForm('orgLayerForm');
	        			});
	        		}
	        	});
			},
			fnRegisterEvent: function(){
				// 取消按钮
				$('#formCancelBtn').click(function(){
					API.fnHideForm('orgLayerForm'); 
				});
			    // 表单验证
			    $("form[name='orgForm']").Validform({
			        tiptype: function (msg, o, cssctl) {
			            var objtip = $(o.obj).closest(".form-group").children(".valid-msg");
			            cssctl(objtip, o.type);
			            objtip.text(msg);
			        },
			        beforeSubmit: function (curform) {
			            // TODO 保存方法
			        	orgFormSvc.fnCommit();
			            return false;
			        }
			    });
			}
	}
	// init
	orgFormSvc.fnRegisterEvent();
})