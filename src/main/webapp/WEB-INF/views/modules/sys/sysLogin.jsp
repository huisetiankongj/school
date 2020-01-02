<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>${fns:getByConfigKey('sysName')} 登录</title>
	<meta charset="utf-8"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!-- CSS文件 -->
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/font-awesome.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/animate.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/style.css" rel="stylesheet">
    <script src="${ctxStatic}/Hplus-v.4.1.0/js/jquery.min.js?v=2.1.4"></script>
    <style type="text/css">
      .header{height:80px;padding-top:20px;position: absolute;width: 100%;top: 0;} 
      .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
      .validateCode {color: #000; text-align: left;}
    </style> 
    <c:if test="${fns:getByConfigKey('backStyle') != null && fns:getByConfigKey('backStyle') != 'undefined'}">
		<link href="${ctxStatic}${fns:getByConfigKey('backStyle')}" rel="stylesheet">
	</c:if>
    <script type="text/javascript">
        var rootPath = "${ctx}",
        	contextPath = "${rootPath}",
        	frontPath = "${ctxFront}",
        	rootAdminPath = "${ctx}";
    </script>
</head>
<body class="gray-bg">
	<div class="header">
		<div id="messageBox" class="alert alert-warning alert-dismissible ${empty message ? 'hide' : ''}" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  			<strong id="loginError">提示! </strong>${message}
		</div>
	</div>
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div style="margin-bottom: 10px;">
                <h1 class="logo-name"><img src="${ctxStatic}${fns:getByConfigKey('loginLogo')}" style="width: 100px;"/></h1>
            </div>
            <h3>欢迎使用 ${fns:getByConfigKey('sysName')}</h3>
            <form class="m-t" role="form" action="${ctx}/login" method="post">
                <div class="form-group">
                    <input type="text" name="username" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="密码" required="">
                </div>
                <div class="form-group">
                    <c:if test="${isValidateCodeLogin}">
                    	<div class="validateCode">
							<label class="input-label mid" for="validateCode">验证码</label>
							<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
						</div>
					</c:if>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
            </form>
        </div>
    </div>

    <!-- 全局js -->
	<script src="${ctxStatic}/org/it313/common/js/jquery.heart.min.js?v=1.0.0"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/content.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/layer/layer.min.js"></script>
	
	<link href="${ctxStatic}/Hplus-v.4.1.0/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/org/it313/datatables/1.10.2/media/css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/org/it313/datatables/1.10.2/extensions/TableTools/css/dataTables.tableTools.css">
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/extensions/TableTools/js/dataTables.tableTools.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/media/js/jquery.ba-bbq.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/media/js/jquery.dataTables.settings.js"></script>
	
	<script src="${ctxStatic}/org/tinygroup/form/js/Validform_v5.3.2.js"></script>
	<script src="${ctxStatic}/org/tinygroup/bootstrapDate/js/WdatePicker.js"></script>
	
    <script type="text/javascript">
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</body>
</html>