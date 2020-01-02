<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="studentAchievementForm" name="studentAchievementForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${studentAchievement.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">任务：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="taskName" value="${studentAchievement.taskName}" class="form-control input-text" readonly/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学生：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="stuName" value="${studentAchievement.stuName}" datatype="*" class="form-control input-text" readonly/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">学科：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="subjectName" value="${studentAchievement.subjectName}" placeholder="请输入学科id" class="form-control input-text" readonly/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">班级：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="orgName" value="${studentAchievement.orgName}" placeholder="请输入班级id" class="form-control input-text" readonly/>
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