<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="examTaskDetailForm" name="examTaskDetailForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${examTaskDetail.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">task_id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="taskId" value="${examTaskDetail.taskId}" placeholder="请输入task_id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">org_id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="orgId" value="${examTaskDetail.orgId}" placeholder="请输入org_id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		</form>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
			<div class="col-xs-12 col-sm-7 pl-0 col-md-offset-2">
				<shiro:hasPermission name="back:examTaskDetail:edit">
					<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
					<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
				</shiro:hasPermission>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	<script type="text/javascript">
		$(function(){
			examTaskDetailSvc = {
				url: {
					saveExamTaskDetail : rootPath + "/back/examTaskDetail/save?t="+new Date().getTime()
				},
				fnCommit: function(){
					var saveJson = Svc.formToJson($("#examTaskDetailForm"));
		        	Svc.AjaxJson.post(examTaskDetailSvc.url.saveExamTaskDetail,saveJson,function(response){
		        		if(response == true){
		        			layer.alert('保存成功！',function(index){
		        				$('#datatables_ExamTaskDetail').dataTable().fnDraw();
		        				layer.close(index);
		        				API.fnHideForm('examTaskDetailLayerForm');
		        			});
		        		}
		        	});
				},
				fnRegisterEvent: function(){
					// 取消按钮
					$('#formCancelBtn').click(function(){
						API.fnHideForm('examTaskDetailLayerForm');
					});
					// 保存按钮
					$('#formSaveBtn').click(function(){
						examTaskDetailSvc.fnCommit();
				    });
				}
			};
			examTaskDetailSvc.fnRegisterEvent();
		});
	</script>
</body>