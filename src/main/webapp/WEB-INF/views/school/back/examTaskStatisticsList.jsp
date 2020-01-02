<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pagelist"/>
<body>
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox">
				<div class="ibox-title">
					<h5>所有任务统计</h5>
				</div>
				<div class="ibox-content">
					<div class="row m-b-sm m-t-sm">
						<div class="col-md-12">
							<form id="examTaskStatisticsSearchForm" action="javascript:void(0);" method="get" class="form form-inline">
								<div class="well well-sm" style="margin-top: 10px;">
									<div id="datepicker" class="input-daterange input-group" style="padding-top: 5px; padding-left: 10px; width:370px;">
										<input type="text" id="beginTime" name="beginTime" placeholder="开始时间" class="input-sm form-control">
										<span class="input-group-addon" style="background-color: #eee;">到</span>
										<input type="text" id="endTime" name="endTime" placeholder="结束时间" class="input-sm form-control">
									</div>&nbsp;
									<select id="taskIdImport" name="taskId" class="form-control select2" datatype="*" nullmsg="考试类型不能为空！">
										<option value="">考试任务</option>
										<c:forEach items="${examTaskList}" var="examTask" varStatus="status">
											<option value="${examTask.id }">${examTask.etTitle }</option>
										</c:forEach>
									</select>
									<select name="examType" class="form-control select2" datatype="*" nullmsg="考试类型不能为空！">
										<option value="">考试类型</option>
										<c:forEach items="${fns:getDictList('tb_exam_type')}" var="dict">
											<option value="${dict.value }">${dict.label }</option>
										</c:forEach>
									</select>
									<%--<div style="height:20px;"></div>--%>
									<select name="subjectId" class="form-control select2" datatype="*" nullmsg="考试类型不能为空！">
										<c:forEach items="${fns:getDictList('tb_subject_type')}" var="dict" varStatus="status">
											<option value="${dict.value }">${dict.label }</option>
										</c:forEach>
									</select>
									<%--
									<c:forEach items="${fns:getDictList('tb_subject_type')}" var="dict" varStatus="status">
										<div class="checkbox checkbox-inline" style="margin-right: 0px; padding-left: 10px;">
											<input id="subjectId${dict.value }" type="checkbox" value="${dict.value }" name="subjectId">
											<label for="subjectId${dict.value }">${dict.label }</label>
										</div>
									</c:forEach>&nbsp;--%>
									<sys:treeselect id="formorgImport" name="orgId" value="" datatype="*" nullmsg="班级不能为空！" labelName="etOrgNames" labelValue=""
													title="班级" url="/sys/org/treeData?type=2" cssClass="required"/>
									&nbsp;<input type="button" id="searchBtn" class="btn btn-primary btn-sm" value="搜索">
								</div>
							  </form>
						</div>
					</div>
					<div class="project-list">
						<table id="datatables_ExamTaskStatistics" class="display table-bordered" cellspacing="0"></table>
					</div>
				</div> 
            </div>         
	   </div>
    </div>
    <script type="text/javascript" src="${rootPath}/views/school/back/examTaskStatisticsList.js?v=12"></script>
</body>