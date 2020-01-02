$(function(){
	var fileUploader,qrUploader;
	var userFormSvc = {
			url: {
				saveUser : rootPath + "/sys/user/save?t="+new Date().getTime()
			},
			fnCommit: function(){
				var userJson = Svc.formToJson($("#userForm"));
	        	Svc.AjaxForm.post(userFormSvc.url.saveUser,userJson,function(response){
	        		if(response == true){
	        			layer.alert('用户保存成功！',function(index){
	        				$('#datatables_user').dataTable().fnDraw();
	        				layer.close(index);
	        				API.fnHideForm('userLayerForm');
	        			});
	        		}
	        	});
			},
			fnRegisterEvent: function(){
				// 取消按钮
				$('#formCancelBtn').click(function(){
					API.fnHideForm('userLayerForm');
				});
			    // 表单验证
			    $("form[name='userForm']").Validform({
			        tiptype: function (msg, o, cssctl) {
			            var objtip = $(o.obj).closest(".form-group").children(".valid-msg");
			            cssctl(objtip, o.type);
			            objtip.text(msg);
			        },
			        beforeSubmit: function (curform) {
			            // TODO 保存方法
			        	userFormSvc.fnCommit();
			            return false;
			        }
			    });
			}
	}
	// 选中已有的角色
	var oldUserRoleIds = $("#oldUserRoleIds").val().replace(/[\[\]]/g,"");
	$.each(oldUserRoleIds.split(','),function(i,v){
		$('#roleIdList'+$.trim(v)).prop('checked',true);
	});
	userFormSvc.fnRegisterEvent();
})