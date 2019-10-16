<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pagelist"/>
<body>
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox">
				<div class="ibox-title">
					<h5>所有学生成绩</h5>
				</div>
				<div class="ibox-content">
					<div class="row m-b-sm m-t-sm">
						<div class="col-md-3">
			           	 	<button id="addStudentAchievement" class="btn btn-primary btn-sm" >创建学生成绩</button>
			              	<button id="batchDeleteStudentAchievement" class="btn btn-primary btn-sm" >批量删除</button>
		                </div>
						<div class="col-md-9">
							<form:form id="studentAchievementSearchForm" action="javascript:void(0);" method="get" class="form-inline pull-right">
								<div class="input-group">
									<input type="text" name="id" class="input-sm form-control" placeholder="请输入ID">
										<span class="input-group-btn">
											<button id="searchBtn" class="btn btn-sm btn-primary" type="button"> 搜索</button>
						                </span>
						         </div>
							  </form:form>
						</div>
					</div>
					<div class="project-list">
						<table id="datatables_StudentAchievement" class="display table-bordered" cellspacing="0"></table>
					</div>
				</div> 
            </div>         
	   </div>
    </div>
    <script type="text/javascript" src="${rootPath}/views/school/back/studentAchievementList.js?v=1"></script>
</body>