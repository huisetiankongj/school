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
					<h5>用户列表</h5>
					<div class="ibox-tools">
						<a id="addUser" class="btn btn-primary btn-xs hide" href="javascript:void(0);">创建新用户</a>
					</div>
				</div>
                <div class="ibox-content">
                	<div class="row m-b-sm m-t-sm">
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-9">
                          	<form id="userSearchForm" action="javascript:void(0);" method="get" class="form-inline pull-right">
                            	<div class="input-group">
                                	<input type="text" name="name" class="input-sm form-control" placeholder="请输入用户名"> <span class="input-group-btn">
                                	<button id="searchBtn" class="btn btn-sm btn-primary" type="button"> 搜索</button> </span>
                            	</div>
                            </form>
                        </div>
                    </div>
                    <div class="project-list">
                    	<table id="datatables_user" class="display table-bordered" cellspacing="0"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<script type="text/javascript" src="${rootPath}/views/modules/sys/userList.js"></script>
</body>