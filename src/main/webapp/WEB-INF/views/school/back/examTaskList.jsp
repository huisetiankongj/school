<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pagelist"/>
<body>
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox">
				<div class="ibox-title">
					<h5>考试任务</h5>
				</div>
				<div class="ibox-content">
					<div class="row m-b-sm m-t-sm">
						<div class="col-md-12">
							<button id="addExamTask" class="btn btn-primary btn-sm hide" >创建任务</button>
							<button id="batchDeleteExamTask" class="btn btn-primary btn-sm hide" >批量删除</button>
						</div>
					</div>
					<div class="row m-b-sm m-t-sm">
						<div class="col-md-12">
							<form id="examTaskSearchForm" action="javascript:void(0);" method="get" class="form form-inline">
								<div class="well well-sm" style="margin-top: 10px;">
									<div id="datepicker" class="input-daterange input-group" style="padding-top: 5px; padding-left: 10px; width:370px;">
										<input type="text" id="beginTime" name="beginTime" placeholder="开始时间" class="input-sm form-control">
										<span class="input-group-addon" style="background-color: #eee;">到</span>
										<input type="text" id="endTime" name="endTime" placeholder="结束时间" class="input-sm form-control">
									</div>
									<sys:treeselect id="formorgImport" name="etOrgIds" value="" datatype="*" nullmsg="班级不能为空！" labelName="etOrgNames" labelValue=""
													title="班级" url="/sys/org/treeData?type=2" cssClass="required"/>
									&nbsp;<input type="button" id="searchBtn" class="btn btn-primary btn-sm" value="搜索">
								</div>
							  </form>
						</div>
					</div>
					<div class="project-list">
						<table id="datatables_ExamTask" class="display table-bordered" cellspacing="0"></table>
					</div>
				</div> 
            </div>         
	   </div>
    </div>
    <script type="text/javascript" src="${rootPath}/views/school/back/examTaskList.js?v=13"></script>
</body>