<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pagelist"/>
<body>
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox">
				<div class="ibox-title">
					<h5>所有任务明细</h5>
				</div>
				<div class="ibox-content">
					<div class="row m-b-sm m-t-sm">
						<div class="col-md-3">
			           	 	<button id="addExamTaskDetail" class="btn btn-primary btn-sm" >创建任务明细</button>
			              	<button id="batchDeleteExamTaskDetail" class="btn btn-primary btn-sm" >批量删除</button>
		                </div>
						<div class="col-md-9">
							<form:form id="examTaskDetailSearchForm" action="javascript:void(0);" method="get" class="form-inline pull-right">
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
						<table id="datatables_ExamTaskDetail" class="display table-bordered" cellspacing="0"></table>
					</div>
				</div> 
            </div>         
	   </div>
    </div>
    <script type="text/javascript" src="${rootPath}/views/elm/back/examTaskDetailList.js?v=1"></script>
</body>