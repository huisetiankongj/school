<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="studentForm" name="studentForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${student.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学生班级：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="stuOrgId" value="${student.stuOrgId}" placeholder="请输入学生班级" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学生姓名：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="stuName" value="${student.stuName}" placeholder="请输入学生姓名" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学生学号：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="stuNo" value="${student.stuNo}" placeholder="请输入学生学号" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学生性别：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="stuSex" value="${student.stuSex}" placeholder="请输入学生性别" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学生年龄：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="stuAge" value="${student.stuAge}" placeholder="请输入学生年龄" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建时间：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${student.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">修改时间：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${student.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">状态：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="state" value="${student.state}" placeholder="请输入状态" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">备注：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
		          <textarea class="form-control"  name="remarks" style="height:100px;">${student.remarks}</textarea>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		</form>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
			<div class="col-xs-12 col-sm-7 pl-0 col-md-offset-2">
				<shiro:hasPermission name="back:student:edit">
					<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
					<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
				</shiro:hasPermission>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	<script type="text/javascript">
		$(function(){
			studentSvc = {
				url: {
					saveStudent : rootPath + "/back/student/save?t="+new Date().getTime()
				},
				fnCommit: function(){
					var saveJson = Svc.formToJson($("#studentForm"));
		        	Svc.AjaxJson.post(studentSvc.url.saveStudent,saveJson,function(response){
		        		if(response == true){
		        			layer.alert('保存成功！',function(index){
		        				$('#datatables_Student').dataTable().fnDraw();
		        				layer.close(index);
		        				API.fnHideForm('studentLayerForm');
		        			});
		        		}
		        	});
				},
				fnRegisterEvent: function(){
					// 取消按钮
					$('#formCancelBtn').click(function(){
						API.fnHideForm('studentLayerForm');
					});
					// 保存按钮
					$('#formSaveBtn').click(function(){
						studentSvc.fnCommit();
				    });
				}
			};
			studentSvc.fnRegisterEvent();
		});
	</script>
</body>