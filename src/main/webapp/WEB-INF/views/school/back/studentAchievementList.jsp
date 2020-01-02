<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pagelist"/>
<body>
	<div class="row">
		<div class="col-sm-3">
			<div class="ibox">
				<div class="ibox-title">
					<h5><i class="fa fa-sitemap"></i> 组织机构树</h5>
					<div class="ibox-tools">
						<a id="changeE" class="btn btn-primary btn-xs" href="javascript:void(0);">刷新树</a>
					</div>
				</div>
				<div class="ibox-content">
					<ul id="organTreeUl" class="fa-ztree left-ztree" data-url="${ctx}/sys/org/treeData?isUseable=true"></ul>
				</div>
			</div>
		</div>
		<div class="col-sm-9">
			<div class="ibox">
				<div class="ibox-title">
					<h5>学生成绩</h5>
				</div>
				<div class="ibox-content">
					<div class="row m-b-sm m-t-sm">
						<div class="col-md-2">
							<button id="importBtn" class="btn btn-primary btn-sm">
								<i class="fa fa-send-o"></i> 批量导入
							</button>
			              	<button id="batchDeleteStudentAchievement" class="btn btn-primary btn-sm" >批量删除</button>
		                </div>
						<div class="col-md-10">
							<form id="studentAchievementSearchForm" action="javascript:void(0);" method="get" class="form-inline pull-right">
								<input type="hidden" name="orgId" id="achOrgId" />
								<div class="input-group">
									<select id="etSubjectIdAch" name="subjectId" class="form-control select2"  datatype="*" nullmsg="考试类型不能为空！">
										<option value="">所有</option>
										<c:forEach items="${fns:getDictList('tb_subject_type')}" var="dict" varStatus="status">
											<option value="${dict.value }">${dict.label }</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-group">
									<select id="taskIdAch" name="taskId" class="form-control select2"  datatype="*" nullmsg="考试类型不能为空！">
										<option value="">所有</option>
										<c:forEach items="${examTaskList}" var="examTask" varStatus="status">
											<option value="${examTask.id }">${examTask.etTitle }</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-group">
									<input type="text" name="stuName" class="input-sm form-control" placeholder="学生名称">
										<span class="input-group-btn">
											<button id="searchBtn" class="btn btn-sm btn-primary" type="button"> 搜索</button>
						                </span>
						         </div>
							  </form>
						</div>
					</div>
					<div class="project-list">
						<table id="datatables_StudentAchievement" class="display table-bordered" cellspacing="0"></table>
					</div>
				</div> 
            </div>         
	   </div>
    </div>
    <script type="text/javascript" src="${rootPath}/views/school/back/studentAchievementList.js?v=5"></script>
</body>