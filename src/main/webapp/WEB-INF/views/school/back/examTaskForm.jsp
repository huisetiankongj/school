<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="examTaskForm" name="examTaskForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${examTask.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试标题：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="etTitle" value="${examTask.etTitle}" placeholder="请输入考试标题" datatype="*1-100" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">考试类型：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<select name="type" class="form-control select2" datatype="*" nullmsg="考试类型不能为空！">
					<c:forEach items="${fns:getDictList('tb_task_type')}" var="dict">
						<option value="${dict.value }" <c:if test="${dict.value == examTask.type}"> selected</c:if>>${dict.label }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form" id="joinMemberCardIds-wrap">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试学科：</label>
			<div class="col-xs-12 col-sm-8 pl-0">
				<input type="hidden" id="etSubjectIds" value="${examTask.etSubjectIds}">
				<div class="checkbox checkbox-inline">
					<input type="checkbox" name="etSubjectIds" id="etSubjectIds0" value="0" datatype="*1-500" nullmsg="学科不能为空"/>
					<label for="etSubjectIds0">所有</label>
				</div>
				<c:forEach items="${fns:getDictList('tb_subject_type')}" var="dict" varStatus="status">
					<div class="checkbox checkbox-inline">
						<input type="checkbox" name="etSubjectIds" id="etSubjectIds${dict.value }" value="${dict.value }" datatype="*1-500" nullmsg="学科不能为空"/>
						<label for="etSubjectIds${dict.value }">${dict.label }</label>
					</div>
				</c:forEach>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">范围类型：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<select name="etOrgType" class="form-control select2" datatype="*" nullmsg="考试类型不能为空！">
					<c:forEach items="${fns:getDictList('tb_exam_type')}" var="dict">
						<option value="${dict.value }" <c:if test="${dict.value == examTask.etOrgType}"> selected</c:if>>${dict.label }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">考试班级：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<sys:treeselect id="orgForm" name="etOrgIds" value="${examTask.etOrgIds}" datatype="*1-50" nullmsg="班级不能为空！" labelName="etOrgNames" labelValue="${examTask.etOrgNames}"
								title="班级" url="/sys/org/treeData?type=2" cssClass="required" notAllowSelectParent="true" checked="true"/>
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
			var examTaskSvc = {
				url: {
					saveExamTask : rootPath + "/back/examTask/save?t="+new Date().getTime()
				},
				fnCommit: function(){
					var saveJson = Svc.formToJson($("#examTaskForm"));
					if($("#etSubjectIds0").is(':checked')){
						saveJson.etSubjectIds = '0';
						saveJson.etSubjectNames = '所有';
					}else{
						var ids = [], vals = [];
						$("input[name='etSubjectIds']:checked").each(function(i,v){
							var that = $(this), $label = that.parent().find("label");
							ids.push(that.val());
							vals.push($label.text());
						})
						saveJson.etSubjectIds = ids.join(',');
						saveJson.etSubjectNames = vals.join(',');
					}
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
						if(validate.check()){
							examTaskSvc.fnCommit();
						}
				    });
				}
			};
			// 表单验证
			var validate = $("#examTaskForm").Validform({
				ignoreHidden:true,
				tiptype: function (msg, o, cssctl) {
					var objtip = $(o.obj).closest(".form-group").children(".valid-msg");
					cssctl(objtip, o.type);
					objtip.text(msg);
				},
			});
			examTaskSvc.fnRegisterEvent();
			if($("#etSubjectIds").val()){
				var etSubjectIds = $("#etSubjectIds").val().replace(/[\[\]]/g,"");
				$.each(etSubjectIds.split(','),function(i,v){
					$('#etSubjectIds'+$.trim(v)).prop('checked',true);
				});
			}else{
				$('#etSubjectIds0').prop('checked',true);
			}
		});
	</script>
</body>