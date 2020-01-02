<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title/></title>
	<meta charset="utf-8"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!-- CSS文件 -->
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/font-awesome.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/animate.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/plugins/steps/jquery.steps.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="${ctxStatic}/Hplus-v.4.1.0/css/style.css?v=20170909" rel="stylesheet">
    <script src="${ctxStatic}/Hplus-v.4.1.0/js/jquery.min.js?v=2.1.4"></script>
    <c:if test="${fns:getByConfigKey('backStyle') != null && fns:getByConfigKey('backStyle') != 'undefined'}">
		<link href="${ctxStatic}${fns:getByConfigKey('backStyle')}" rel="stylesheet">
	</c:if>
    <script type="text/javascript">
        var rootPath = "${ctx}",
        	contextPath = "${rootPath}",
        	frontPath = "${ctxFront}",
        	rootAdminPath = "${ctx}";
    </script>
	<sitemesh:head/>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    	<div class="postion-nav" style="position: relative">
			<ul class="breadcrumb"><li>所在位置： </li></ul>
        </div>
    	<sitemesh:body/>
    </div>
	
	<!-- 全局js -->
	<script src="${ctxStatic}/org/it313/common/js/jquery.heart.min.js?v=1.0.0"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/content.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/layer/3.1.0/layer.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/peity/jquery.peity.min.js"></script>
	<link href="${ctxStatic}/Hplus-v.4.1.0/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/org/it313/datatables/1.10.2/media/css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/org/it313/datatables/1.10.2/extensions/TableTools/css/dataTables.tableTools.css">
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/extensions/TableTools/js/dataTables.tableTools.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/media/js/jquery.ba-bbq.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/datatables/1.10.2/media/js/jquery.dataTables.settings.js?v=20180324"></script>
	
	<link rel="stylesheet" href="${ctxStatic}/org/tinygroup/zTree/css/awesomeStyle/awesome.css"> 
	<link rel="stylesheet" href="${ctxStatic}/org/tinygroup/zTree/css/zTreeStyle/zTreeStyle.css">
	<script src="${ctxStatic}/org/tinygroup/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script src="${ctxStatic}/org/tinygroup/zTree/js/jquery.ztree.exhide-3.5.js"></script>
	<script src="${ctxStatic}/org/tinygroup/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script src="${ctxStatic}/org/tinygroup/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	
	<!--<link rel="stylesheet" href="${ctxStatic}/org/tinygroup/select2/css/css.css">
	<script src="${ctxStatic}/org/tinygroup/select2/js/select2.min.js"></script>
	<script src="${ctxStatic}/org/tinygroup/select2/js/select2_locale_zh-CN.js"></script>-->
	
	<script src="${ctxStatic}/org/tinygroup/form/js/Validform_v5.3.2.js"></script>
	
	<link rel="stylesheet" href="${ctxStatic}/org/tinygroup/webupload/lib/webuploader.css">
	<script src="${ctxStatic}/org/tinygroup/webupload/lib/webuploader.js"></script>
	<script src="${ctxStatic}/org/tinygroup/webupload/lib/jquery.webuploader.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/org/it313/fancytree/css/fancytree.css">
	<script src="${ctxStatic}/org/tinygroup/jqueryUi/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/fancytree/js/fancytree.js"></script>
	
	<link href="${ctxStatic}/org/tinygroup/bootstrapDate/css/daterangepicker-bs3.css" rel="stylesheet">
	<script src="${ctxStatic}/org/tinygroup/moment/js/moment.js"></script>
	<script src="${ctxStatic}/org/tinygroup/bootstrapDate/js/WdatePicker.js"></script>
	<script src="${ctxStatic}/org/tinygroup/bootstrapDate/js/daterangepicker.js"></script>
	
	<script src="${ctxStatic}/org/it313/ueditor-1.4.3.3/ueditor.config.js"></script>
	<script src="${ctxStatic}/org/it313/ueditor-1.4.3.3/ueditor.all.js"></script>
	<script src="${ctxStatic}/org/it313/ueditor-1.4.3.3/lang/zh-cn/zh-cn.js"></script>
	
	<link rel="stylesheet" href="${ctxStatic}/org/it313/viewer/viewer.min.css">
	<script src="${ctxStatic}/org/it313/viewer/viewer.min.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/echarts3.85/echarts.min.js"></script>
    <script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/echarts/wonderland.js"></script>
	
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/staps/jquery.steps.min.js"></script>
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/fullcalendar/fullcalendar.min.js"></script>
	
	<script src="${ctxStatic}/Hplus-v.4.1.0/js/plugins/chosen/chosen.jquery.js"></script>
	
	<script type="text/javascript">
		var $currentIframe = $(top.document).find("iframe:visible");
		var currentMenuId = $currentIframe.data('treeid');
		var menulocation = $currentIframe.data('location');
		var gasShopFlag = $currentIframe.data('data-gas-shop-flag');
		if(menulocation){
			$(".postion-nav .breadcrumb").append("<li>"+menulocation.split(",").join("</li><li>")+"</li>");
		}
		var API = {
				fnShowForm: function(option){
					var setting = {
						type: 1,
				  		title: option.title,
				  		area: option.area||['70%', '90%']
					};
					option.zIndex && (setting.zIndex = option.zIndex);
					var index = layer.open(setting);
					$('#layui-layer'+index+' .layui-layer-content').attr('id', option.id).attr('data-index', index);
					Svc.AjaxPage(option.url, $('#'+option.id), option.params||{}, option.type||'GET', option.success);
				},
				fnHideForm: function(layerId){
					layer.close($('#'+layerId).data('index'));
				}
			};

	</script>
</body>
</html>