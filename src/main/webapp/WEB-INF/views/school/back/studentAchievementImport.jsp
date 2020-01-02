<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<style>
		.input-group{width:80%;}
	</style>
	<div>
		<p>
			<strong>操作步骤：</strong><br/>
			<span style="display:block;padding:5px !important;">1、请下载EXCEL模板文件</span>
			<span style="display:block;padding:5px !important;">2、按照文件中的格式填写内容,并保存。不能修改文件中的格式，否则会出错！</span>
			<span style="display:block;padding:5px !important;">3、点击导入模板文件！</span>
			&nbsp;<a id="downA" href="${ctx}/back/student/downExcel?fileName=成绩导入模板.xlsx" ></a>
		</p>
		<p>
			<select id="etSubjectIdImport" name="etSubjectId" class="form-control select2" style="width: 80%;" datatype="*" nullmsg="考试类型不能为空！">
				<c:forEach items="${fns:getDictList('tb_subject_type')}" var="dict" varStatus="status">
					<option value="${dict.value }">${dict.label }</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<sys:treeselect id="formorgImport" name="org.id" value="${user.org.id}" datatype="*" nullmsg="班级不能为空！" labelName="org.name" labelValue="${user.org.name}"
							title="班级" url="/sys/org/treeData?type=2" cssClass="required" notAllowSelectParent="true"/>
		</p>
		<p>
			<select id="taskIdImport" name="taskId" class="form-control select2" style="width: 80%;" datatype="*" nullmsg="考试类型不能为空！">
				<c:forEach items="${examTaskList}" var="examTask" varStatus="status">
					<option value="${examTask.id }">${examTask.etTitle }</option>
				</c:forEach>
			</select>
		</p>
		<p style="color: saddlebrown;">
			<input type="text" id="fileName" class="form-control input-text" style="width: 70%;display: inline-block;" readOnly>
            <a href="javascript:;;;" id="picker" class="fr tiaojian_button1" style="display: inline-block;padding: 7px;background-color: #2626e2;color: #fff; vertical-align: top;">选择</a>
		</p>
	</div>
	<div class="form-group normal-form" style="text-align: center;">
		<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
		<div class="col-xs-12 col-sm-7 pl-0">
			<button id="downBtn" class="btn btn-primary">下载模板</button>
			<button id="formSaveBtn" class="btn btn-primary">开始导入</button>
		</div>
	</div>
<script src="${ctxStatic}/org/tinygroup/webupload/lib/jquery.uploader.js"></script>	
<script>
$(function(){
	var uploader = Svc.ajaxUpload({
						"pick":"#picker",
						"url":rootPath + "/back/studentAchievement/batchSaveByExcel?t="+new Date().getTime(),
						"formData":{},
						"uploadBeforeSend":function(data){
							data.subjectId = $("#etSubjectIdImport").val();
							data.orgId = $("#formorgImportId").val();
							data.taskId= $("#taskIdImport").val();
							data.taskName = $("#taskIdImport option:selected").text();
							data.subjectName = $("#etSubjectIdImport option:selected").text();
							data.orgName = $("#formorgImportName").val();
						},
						"onChange":function(file){
										var fileName = file.name,
											fileType=(fileName.substr(fileName.lastIndexOf("."))).toLowerCase();
									 	if(fileType==".xls"||fileType==".xlsx"){
									 		$("#fileName").val(fileName);
									 	}else{
									 		layer.msg("只能上传excel格式文件");
									 		return false;
									 	}
									},
						"onSuccess"	:function(file,response){
										if(response.data==true){
											layer.msg("导入成功");
											$('#datatables_StudentAchievement').dataTable().fnDraw();
					        				API.fnHideForm('studentAchImportForm');
										}else{
											layer.alert(response.data.msg);
										}
										$("#formSaveBtn").text("开始导入");
									}		
					});
	//点击事件
	$("#picker").click(function(){
		$("#fileName").val("");
	})		
	//表单提交事件		
	$("#formSaveBtn").click(function(){
		if("开始导入"!=$("#formSaveBtn").text()){
			return false;
		}
		if(!$("#fileName").val()){
			layer.msg("请先上传数据文件！");
			return false;
		}
		$("#formSaveBtn").text("提交中...");
		uploader.upload();
	});

	$("#downBtn").click(function(){
		var etSubjectId = $("#etSubjectIdImport").val(),
			orgId = $("#formorgImportId").val(),
			taskId= $("#taskIdImport").val();
		if(!etSubjectId){
			layer.msg("选择要导出的科目");
			return false;
		}
		if(!orgId){
			layer.msg("选择要导出的班级");
			return false;
		}
		var url = "${ctx}/back/studentAchievement/downExcel?etSubjectId="+etSubjectId+"&orgId="+orgId+"&taskId="+taskId;
		$("#downA").attr("href", url);
		document.getElementById("downA").click();
	})
})
</script>
</body>