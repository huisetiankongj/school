<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="studentAchievementForm" name="studentAchievementForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${studentAchievement.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">任务id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="taskId" value="${studentAchievement.taskId}" placeholder="请输入任务id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学生id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="stuId" value="${studentAchievement.stuId}" placeholder="请输入学生id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学科id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="subjectId" value="${studentAchievement.subjectId}" placeholder="请输入学科id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">班级id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="orgId" value="${studentAchievement.orgId}" placeholder="请输入班级id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">班级名称：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="orgName" value="${studentAchievement.orgName}" placeholder="请输入班级名称" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">成绩：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="score" value="${studentAchievement.score}" placeholder="请输入成绩" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建时间：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${studentAchievement.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建人：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="createUser" value="${studentAchievement.createUser}" placeholder="请输入创建人" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">修改时间：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${studentAchievement.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建人：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="updateUser" value="${studentAchievement.updateUser}" placeholder="请输入创建人" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		</form>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
			<div class="col-xs-12 col-sm-7 pl-0 col-md-offset-2">
				<shiro:hasPermission name="back:studentAchievement:edit">
					<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
					<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
				</shiro:hasPermission>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	<script type="text/javascript">
		$(function(){
			studentAchievementSvc = {
				url: {
					saveStudentAchievement : rootPath + "/back/studentAchievement/save?t="+new Date().getTime()
				},
				fnCommit: function(){
					var saveJson = Svc.formToJson($("#studentAchievementForm"));
		        	Svc.AjaxJson.post(studentAchievementSvc.url.saveStudentAchievement,saveJson,function(response){
		        		if(response == true){
		        			layer.alert('保存成功！',function(index){
		        				$('#datatables_StudentAchievement').dataTable().fnDraw();
		        				layer.close(index);
		        				API.fnHideForm('studentAchievementLayerForm');
		        			});
		        		}
		        	});
				},
				fnRegisterEvent: function(){
					// 取消按钮
					$('#formCancelBtn').click(function(){
						API.fnHideForm('studentAchievementLayerForm');
					});
					// 保存按钮
					$('#formSaveBtn').click(function(){
						studentAchievementSvc.fnCommit();
				    });
				}
			};
			studentAchievementSvc.fnRegisterEvent();
		});
	</script>
</body>