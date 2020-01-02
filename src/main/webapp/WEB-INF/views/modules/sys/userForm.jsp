<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
<form name="userForm" action="javascript:void(0);" id="userForm" class="form form-horizontal">
	<input type="hidden" id="id" name="id" value="${user.id }">
	<input type="hidden" id="userType" name="userType" value="3">
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">组织机构：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<sys:treeselect id="formorg" name="org.id" value="${user.org.id}" datatype="*" nullmsg="部门不能为空！" labelName="org.name" labelValue="${user.org.name}"
					title="部门" url="/sys/org/treeData?type=2" cssClass="required" notAllowSelectParent="false"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">姓名：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="name" id="name" value="${user.name }" placeholder="用户姓名" class="form-control input-text" datatype="*1-50" nullmsg="姓名不能为空！"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">登录名：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
			<input type="text" name="loginName" id="loginName" value="${user.loginName }" placeholder="4~16个字符，字母/中文/数字/下划线" class="form-control input-text" datatype="*4-16" nullmsg="登录名不能为空！"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">密码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<input type="hidden" name="password" value="${user.password }">
    		<input type="password" name="newPassword" id="password" 
    		<c:if test="${empty user.id}">placeholder="密码"  datatype="*6-18" nullmsg="请输入密码！" </c:if>
    		<c:if test="${not empty user.id}"> placeholder="如果不修改密码，请留空！" </c:if>
    		autocomplete="off" class="form-control">
    	</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">确认密码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="password" errormsg="您两次输入的密码不一致！" id="confirmpassword" placeholder="确认密码" recheck="newPassword" ignore="ignore" datatype="*" class="form-control">
    	</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">操作密码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<input type="password" name="operatePwd" id="operatePwd"  autocomplete="off" class="form-control">
    	</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">手机：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="mobile" id="mobile" value="${user.mobile }" placeholder="手机号码" class="form-control input-text"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">是否允许登录：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<div class="radio radio-inline">
				<input id="radio1" type="radio" value="1" name="loginFlag" <c:if test="${user.loginFlag == 1 || user.id == null }">checked="true"</c:if>>
				<label for="radio1"> 是 </label>
			</div>
			<div class="radio radio-inline">
				<input id="radio0" type="radio" value="0" name="loginFlag" <c:if test="${user.loginFlag == 0 }">checked="true"</c:if>>
				<label for="radio0"> 否 </label>
			</div>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">用户角色：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<input type="hidden" id="oldUserRoleIds" value="${user.roleIdList}"> 
    		<c:forEach items="${allRoles}" var="role">
				<div class="checkbox checkbox-inline">
					<input type="checkbox" name="roleIdList" id="roleIdList${role.id }" value="${role.id }" datatype="*" nullmsg="用户角色不能为空！"/>
					<label for="roleIdList${role.id }">${role.name }</label>
				</div>
			</c:forEach>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
		<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">描述：</label>
		<div class="col-xs-12 col-sm-7 pl-0">
			<textarea class="form-control" row="10" placeholder="说点什么..." name="remarks">${user.remarks }</textarea>
		</div>
		<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<c:if test="${not empty user.id}">
		<div class="form-group normal-form">
			<div class="col-xs-12 col-sm-7 pl-0 col-md-offset-2">
				<div class="alert alert-success">
					<p>创建时间：<fmt:formatDate value="${user.createTime}" type="both" dateStyle="full"/></p>
					<p>最后登陆：IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></p>
				</div>
			</div>
		</div>
	</c:if>
	<div class="form-group normal-form">
		<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
		<div class="col-xs-12 col-sm-7 pl-0">
			<shiro:hasPermission name="sys:user:edit">
				<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
				<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
			</shiro:hasPermission>
		</div>
	<div class="col-xs-12 col-sm-3 valid-msg"></div>
</div>
</form>
<script type="text/javascript" src="${rootPath}/views/modules/sys/userForm.js"></script>
</body>