<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="examTaskForm" name="examTaskForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${examTask.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试标题：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="etTitle" value="${examTask.etTitle}" placeholder="请输入考试标题" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试学科ids：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="etSubjectIds" value="${examTask.etSubjectIds}" placeholder="请输入考试学科ids" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试学科names：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="etSubjectNames" value="${examTask.etSubjectNames}" placeholder="请输入考试学科names" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试类型（1所有2年级3班级）：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="etOrgType" value="${examTask.etOrgType}" placeholder="请输入考试类型（1所有2年级3班级）" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试班级ids：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="etOrgIds" value="${examTask.etOrgIds}" placeholder="请输入考试班级ids" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试班级names：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="etOrgNames" value="${examTask.etOrgNames}" placeholder="请输入考试班级names" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建时间：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${examTask.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建人：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="createUser" value="${examTask.createUser}" placeholder="请输入创建人" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">修改时间：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${examTask.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">备注：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
		          <textarea class="form-control"  name="remarks" style="height:100px;">${examTask.remarks}</textarea>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">状态：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="state" value="${examTask.state}" placeholder="请输入状态" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建人：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="updateUser" value="${examTask.updateUser}" placeholder="请输入创建人" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		</form>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
			<div class="col-xs-12 col-sm-7 pl-0 col-md-offset-2">
				<shiro:hasPermission name="back:examTask:edit">
					<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
					<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
				</shiro:hasPermission>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	<script type="text/javascript">
		$(function(){
			examTaskSvc = {
				url: {
					saveExamTask : rootPath + "/back/examTask/save?t="+new Date().getTime()
				},
				fnCommit: function(){
					var saveJson = Svc.formToJson($("#examTaskForm"));
		        	Svc.AjaxJson.post(examTaskSvc.url.saveExamTask,saveJson,function(response){
		        		if(response == true){
		        			layer.alert('保存成功！',function(index){
		        				$('#datatables_ExamTask').dataTable().fnDraw();
		        				layer.close(index);
		        				API.fnHideForm('examTaskLayerForm');
		        			});
		        		}
		        	});
				},
				fnRegisterEvent: function(){
					// 取消按钮
					$('#formCancelBtn').click(function(){
						API.fnHideForm('examTaskLayerForm');
					});
					// 保存按钮
					$('#formSaveBtn').click(function(){
						examTaskSvc.fnCommit();
				    });
				}
			};
			examTaskSvc.fnRegisterEvent();
		});
	</script>
</body>