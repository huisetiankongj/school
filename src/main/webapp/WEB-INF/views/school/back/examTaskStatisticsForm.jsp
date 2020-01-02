<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="examTaskStatisticsForm" name="examTaskStatisticsForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${examTaskStatistics.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">task_id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="taskId" value="${examTaskStatistics.taskId}" placeholder="请输入task_id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试类型（1平时2期中3期末）：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="examType" value="${examTaskStatistics.examType}" placeholder="请输入考试类型（1平时2期中3期末）" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">班级：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="orgId" value="${examTaskStatistics.orgId}" placeholder="请输入班级" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">subject_id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="subjectId" value="${examTaskStatistics.subjectId}" placeholder="请输入subject_id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">subject_name：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="subjectName" value="${examTaskStatistics.subjectName}" placeholder="请输入subject_name" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">avg_score：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="avgScore" value="${examTaskStatistics.avgScore}" placeholder="请输入avg_score" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">不及格人数：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="badNum" value="${examTaskStatistics.badNum}" placeholder="请输入不及格人数" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">及格人数：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="passNum" value="${examTaskStatistics.passNum}" placeholder="请输入及格人数" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">优秀人数：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="excellentNum" value="${examTaskStatistics.excellentNum}" placeholder="请输入优秀人数" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">总人数：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="allNum" value="${examTaskStatistics.allNum}" placeholder="请输入总人数" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">创建时间：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${examTaskStatistics.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		</form>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
			<div class="col-xs-12 col-sm-7 pl-0 col-md-offset-2">
				<shiro:hasPermission name="back:examTaskStatistics:edit">
					<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
					<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
				</shiro:hasPermission>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	<script type="text/javascript">
		$(function(){
			examTaskStatisticsSvc = {
				url: {
					saveExamTaskStatistics : rootPath + "/back/examTaskStatistics/save?t="+new Date().getTime()
				},
				fnCommit: function(){
					var saveJson = Svc.formToJson($("#examTaskStatisticsForm"));
		        	Svc.AjaxJson.post(examTaskStatisticsSvc.url.saveExamTaskStatistics,saveJson,function(response){
		        		if(response == true){
		        			layer.alert('保存成功！',function(index){
		        				$('#datatables_ExamTaskStatistics').dataTable().fnDraw();
		        				layer.close(index);
		        				API.fnHideForm('examTaskStatisticsLayerForm');
		        			});
		        		}
		        	});
				},
				fnRegisterEvent: function(){
					// 取消按钮
					$('#formCancelBtn').click(function(){
						API.fnHideForm('examTaskStatisticsLayerForm');
					});
					// 保存按钮
					$('#formSaveBtn').click(function(){
						examTaskStatisticsSvc.fnCommit();
				    });
				}
			};
			examTaskStatisticsSvc.fnRegisterEvent();
		});
	</script>
</body>