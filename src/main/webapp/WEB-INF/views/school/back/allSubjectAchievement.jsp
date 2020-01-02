<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pagelist"/>
<body>
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox">
				<div class="ibox-title">
					<h5>成绩报表</h5>
				</div>
				<div class="ibox-content">
					<div class="row m-b-sm m-t-sm">
						<div class="col-md-12">
							<form id="allSubjectAchievementForm" action="javascript:void(0);" method="get" class="form form-inline">
								<div class="well well-sm" style="margin-top: 10px;">
									<%--<div id="datepicker" class="input-daterange input-group" style="padding-top: 5px; padding-left: 10px; width:370px;">
										<input type="text" id="beginTime" name="beginTime" placeholder="开始时间" class="input-sm form-control">
										<span class="input-group-addon" style="background-color: #eee;">到</span>
										<input type="text" id="endTime" name="endTime" placeholder="结束时间" class="input-sm form-control">
									</div>&nbsp;--%>
									<select id="taskIdImport" name="taskId" class="form-control select2" datatype="*" nullmsg="考试类型不能为空！">
										<c:forEach items="${examTaskList}" var="examTask" varStatus="status">
											<option value="${examTask.id }">${examTask.etTitle }</option>
										</c:forEach>
									</select>
									&nbsp;<input type="button" id="searchBtn" class="btn btn-primary btn-sm" value="搜索">
									 <button id="exportBtn" class="btn btn-primary btn-sm">
										<i class="fa fa-send-o"></i> 导出
									</button>
								</div>
							  </form>
						</div>
					</div>
					<div class="project-list" id="datatable1">
						<table id="datatables_allSubjectAchievement" class="display table-bordered" cellspacing="0"></table>
					</div>
				</div> 
            </div>         
	   </div>
    </div>
    <script type="text/javascript" src="${rootPath}/views/school/back/allSubjectAchievement.js?v=20"></script>
</body>