<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
<form name="orgForm" action="javascript:void(0);" id="orgForm" class="form form-horizontal">
	<input type="hidden" id="id" name="id" value="${org.id }">
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">上级机构：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<c:if test="${org.id != ''&&org.id != null}">
    		<sys:treeselect id="org" name="parent.id" value="${org.parent.id}" labelName="parent.name" labelValue="${org.parent.name}"
							title="机构" url="/sys/org/treeData" extId="${org.id}" cssClass="required" allowClear="${org.currentUser.admin}" disabled="disabled"/>
			</c:if>
			<c:if test="${org.id == ''||org.id == null}">
				<sys:treeselect id="org" name="parent.id" value="${org.parent.id}" labelName="parent.name" labelValue="${org.parent.name}"
								title="机构" url="/sys/org/treeData" extId="${org.id}" cssClass="required" allowClear="${org.currentUser.admin}"/>
			</c:if>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<%--<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">归属区域：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<sys:treeselect id="area" name="area.id" value="${org.area.id}" labelName="area.name" labelValue="${org.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>--%>
	<input type="hidden" name="area.id" id="areaId" value="1" />
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">机构名称：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="name" id="name" value="${org.name }" placeholder="机构名称" class="form-control input-text" datatype="*1-50" nullmsg="机构名称不能为空！"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">机构编码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="code" id="code" value="${org.code }" placeholder="机构编码" class="form-control input-text"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">机构类型：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<select name="type" class="form-control select2" datatype="*" nullmsg="机构类型不能为空！">
				<c:forEach items="${fns:getDictList('sys_org_type')}" var="dict">
					<option value="${dict.value }" <c:if test="${dict.value == org.type}"> selected</c:if>>${dict.label }</option>
				</c:forEach>
			</select>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<%-- <div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">机构级别：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<select name="grade" class="form-control select2" datatype="*" nullmsg="机构级别不能为空！">
				<c:forEach items="${fns:getDictList('sys_org_grade')}" var="dict">
					<option value="${dict.value }" <c:if test="${dict.value == org.grade}"> selected</c:if>>${dict.label }</option>
				</c:forEach>
			</select>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div> --%>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">是否可用：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
    		<div class="radio radio-inline">
				<input id="radio1" type="radio" value="1" name="state" <c:if test="${org.state == 1 || org.id == null }">checked="true"</c:if>>
				<label for="radio1"> 是 </label>
			</div>
			<div class="radio radio-inline">
				<input id="radio0" type="radio" value="0" name="state" <c:if test="${org.state == 0 }">checked="true"</c:if>>
				<label for="radio0"> 否 </label>
			</div>
			<span class="help-block">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">联系地址：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="address" id="address" value="${org.address }" placeholder="联系地址" class="form-control input-text"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<%-- <div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">邮政编码：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="zipCode" id="zipCode" value="${org.zipCode }" placeholder="邮政编码" class="form-control input-text"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">负责人：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="master" id="master" value="${org.master }" placeholder="负责人" class="form-control input-text"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div> 

	<div class="form-group normal-form">
    	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">传真：</label>
    	<div class="col-xs-12 col-sm-7 pl-0">
			<input type="text" name="fax" id="fax" value="${org.fax }" placeholder="传真" class="form-control input-text"/>
		</div>
    	<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	--%>
	<div class="form-group normal-form">
		<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">描述：</label>
		<div class="col-xs-12 col-sm-7 pl-0">
			<textarea class="form-control" row="10" placeholder="说点什么..." name="remarks">${org.remarks }</textarea>
		</div>
		<div class="col-xs-12 col-sm-3 valid-msg"></div>
	</div>
	<c:if test="${empty org.id}">
		<div class="form-group normal-form">
  	  	<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">快速添加下级部门：</label>
    		<div class="col-xs-12 col-sm-7 pl-0">
				<c:forEach items="${fns:getDictList('sys_org_common')}" var="dict">
					<div class="checkbox checkbox-inline">
						<input type="checkbox" name="childDeptList" id="id${dict.id }" value="${dict.value }"/>
						<label for="id${dict.id }">${dict.label }</label>
					</div>
				</c:forEach>
			</div>
    		<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	</c:if>
	<div class="form-group normal-form">
		<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
		<div class="col-xs-12 col-sm-7 pl-0">
			<shiro:hasPermission name="sys:org:edit">
				<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
				<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
			</shiro:hasPermission>
		</div>
	<div class="col-xs-12 col-sm-3 valid-msg"></div>
</div>
</form>
<script type="text/javascript" src="${rootPath}/views/modules/sys/orgForm.js"></script>
</body>