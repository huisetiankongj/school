<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<base href="<%=basePath%>">

<title></title>
<script type="text/javascript">
        var rootPath = "${ctx}",
        	contextPath = "${rootPath}",
        	frontPath = "${ctxFront}",
        	rootAdminPath = "${ctx}",
        	tinyTplContextPath = "${ctxStatic}/org/tinygroup/home/";
    </script>
<link href="${ctxStatic}/org/min/css/app.plugin.min.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctxStatic}/org/min/js/app.plugin.min.js"></script>
<script type="text/javascript" src="${rootPath}/views/test/test.js"></script>
</head>

<body>
<div style="width: 80%;margin: 50px auto;">
	<form name="testForm" action="javascript:void(0);" id="testForm" class="form form-horizontal">
	    <div class="form-group normal-form">
		      <label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">项目地址前缀：</label>
		      <div class="col-xs-12 col-sm-10 pl-0">
		          	<input type="text" id="urlPrefix" name="urlPrefix" placeholder="" class="form-control input-text"/>
		      </div>
			  <div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	    <div class="form-group normal-form">
		      <label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">URL：</label>
		      <div class="col-xs-12 col-sm-10 pl-0">
		          	<input type="text" id="url" name="url" placeholder="" class="form-control input-text"/>
		      </div>
			  <div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	    <div class="form-group normal-form">
		      <label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">提交类型：</label>
		      <div class="col-xs-12 col-sm-2 pl-0">
		          	<select class="form-control select2" size="1" name="type">
			          <option value="post">post</option>
			          <option value="get">get</option>
			        </select>
		      </div>
		      <label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">提交数据类型：</label>
		      <div class="col-xs-12 col-sm-2 pl-0">
		          	<select class="form-control select2" size="1" name="contentType">
			          <option value="application/json;charset=UTF-8">json</option>
			          <option value="text/plain;charset=UTF-8">text文本字符串</option>
			          <option value="text/HTML;charset=UTF-8">HTML</option>
			          <option value="text/xml;charset=UTF-8">xml</option>
			          <option value="image/GIF;charset=UTF-8">GIF</option>
			          <option value="image/JPEG;charset=UTF-8">JPEG</option>
			        </select>
		      </div>
		      <label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">返回结果类型：</label>
		      <div class="col-xs-12 col-sm-2 pl-0">
		          	<select class="form-control select2" size="1" name="dataType">
			          <option value="json">json格式</option>
			          <option value="text">纯文本字符串</option>
			          <option value="html">html格式</option>
			          <option value="xml">xml格式</option>
			          <option value="jsonp">jsonp格式</option>
			        </select>
		      </div>
		</div>
		<div class="form-group normal-form">
		      <label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">参数：</label>
		      <div class="col-xs-12 col-sm-10 pl-0">
		          	<input type="text" id="params" name="params" placeholder="" class="form-control input-text"/>
		      </div>
			  <div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
		      <label class="col-xs-12 col-sm-2 control-label pl-0 pr-5">返回结果：</label>
		      <div class="col-xs-12 col-sm-10 pl-0">
		          	<textarea type="text" id="result" placeholder="" class="form-control input-text" style="height: 300px;"></textarea>
		      </div>
			  <div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
			<div class="col-xs-12 col-sm-7 pl-0">
				<button id="commitBtn" class="btn btn-primary">提交</button>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	</form>
</div>
</body>
</html>
