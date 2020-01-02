<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
<form name="updatePwdForm" action="javascript:void(0);" id="userForm" class="form form-horizontal">
	<input type="hidden" id="id" name="id" value="${user.id }">
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">姓名：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="name" id="name"  value="${user.name }" readonly="readonly" placeholder="用户姓名" class="form-control input-text" datatype="*1-50" nullmsg="姓名不能为空！"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">登录名：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
			<input type="text" name="loginName" id="loginName" value="${user.loginName }" readonly="readonly" placeholder="4~16个字符，字母/中文/数字/下划线" class="form-control input-text" datatype="*4-16" nullmsg="登录名不能为空！"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">原密码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<input type="password" name="oldPassword" placeholder="请输入原密码！" id="oldPassword" datatype="*" nullmsg="请输入原密码！"
    		autocomplete="off" class="form-control">
    	</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">新密码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<input type="password" name="newPassword" id="newPassword" datatype="*6-18" placeholder="请输入新密码！" nullmsg="请输入新密码！"
    		autocomplete="off" class="form-control">
    	</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">确认密码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="password" errormsg="您两次输入的密码不一致！"  id="confirmpassword" placeholder="确认密码" 
			recheck="newPassword" ignore="ignore" datatype="*" class="form-control">
    	</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
		<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
		<div class="col-xs-12 col-sm-7 pl-0">
				<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
				<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
		</div>
	<div class="col-xs-12 col-sm-3 valid-msg"></div>
</div>
</form>
<script type="text/javascript">
var updatePwdFormSvc = {
		url: {
			updatePwd : rootPath + "/sys/home/updatePwd?t="+new Date().getTime()
		},
		fnCommit: function(){
			var updatePwdJson = Svc.formToJson($("#userForm"));
        	Svc.AjaxForm.post(updatePwdFormSvc.url.updatePwd,updatePwdJson,function(response){
        		if(response == true){
        			layer.alert('密码修改成功！',function(index){
        				layer.close(index);
        				API.fnHideForm("updatePwdForm");
        			});
        		}else{
        			layer.alert("原密码错误！！请重新输入...");
        		}
        	});
		},
		fnRegisterEvent: function(){
			// 取消按钮
			$('#formCancelBtn').click(function(){
				API.fnHideForm("updatePwdForm");
			});
		    // 表单验证
		    $("form[name='updatePwdForm']").Validform({
		        tiptype: function (msg, o, cssctl) {
		            var objtip = $(o.obj).closest(".form-group").children(".valid-msg");
		            cssctl(objtip, o.type);
		            objtip.text(msg);
		        },
		        beforeSubmit: function (curform) {
		            // TODO 保存方法
		        	if($('#newPassword').val()===$('#oldPassword').val()){
		        		layer.alert("原密码不能与新密码一致！");
		        		return false;
		        	}
		        	updatePwdFormSvc.fnCommit();
		            return false;
		        }
		    });
		}
}
updatePwdFormSvc.fnRegisterEvent();
</script>
</body>