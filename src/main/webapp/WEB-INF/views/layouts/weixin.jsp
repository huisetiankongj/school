<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<meta charset="utf-8">
	<meta content="width=device-width,initial-scale=1,user-scalable=0" name="viewport">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta name="renderer" content="webkit">
	<title><sitemesh:title/></title>
	<script type="text/javascript">
       var rootPath = "${ctx}",
        	contextPath = "${rootPath}",
        	frontPath = "${ctxFront}/f",
        	rootAdminPath = "${ctx}",
        	tinyTplContextPath = "${ctxStatic}/org/tinygroup/home/";
	</script>
	
	<link rel="stylesheet" href="${ctxStatic}/org/tinygroup/layer_mobile/need/layer.css">
	<link rel="stylesheet" href="${ctxStatic}/weui/weui.css?v=20170625">
	<link rel="stylesheet" href="${ctxStatic}/weui/jquery-weui/css/jquery-weui.min.css?v=20170625">
	<link rel="stylesheet" href="${ctxStatic}/weui-sms/20171124/css/style.css">
	<link href="${ctxStatic}/carclub/mobile/css/index.css?v=80" rel="stylesheet" type="text/css">
	<style> 
		.weui-picker-container, .weui-picker-overlay{
		    background: rgba(0,0,0,0.4);
		    height: 100%;
		}
		.swiper-pagination-bullet-active{
			background: #fff;
		}
	</style>
	<script src="${ctxStatic}/org/tinygroup/jquery/js/jquery.1.11.3.min.js"></script>
	<sitemesh:head/> 
</head>
<body>
	<sitemesh:body/>
	<script src="${ctxStatic}/org/tinygroup/layer_mobile/layer.js"></script>
	<script type="text/javascript" src="${ctxStatic}/org/it313/common/js/jquery.heart.mobile.min.js?v=20171114"></script>
	<script src="${ctxStatic}/org/tinygroup/layer_mobile/layer.js"></script>
	<script src="${ctxStatic}/weui/js/weui.js"></script>
	<script src="${ctxStatic}/weui/jquery-weui/js/jquery-weui.min.js"></script>
	<script src="${ctxStatic}/weui-sms/20171124/lib/fastclick.js"></script>
	<script src="${ctxStatic}/app/tool.js?v=20190524"></script>
	<script type="text/javascript">
		// 判断设备类型
		function getDeviceType() {
		  var u = navigator.userAgent;
		  var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		  var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		  if (isAndroid)
		    return 'android';
		  if (isiOS)
		    return 'ios';
		}
		
		var isReset = true;//是否归位
		if (getDeviceType() === 'ios') {
		  document.body.addEventListener('focusin', function(){
		    //软键盘弹出的事件处理
			isReset = false;
		  });
		  document.body.addEventListener('focusout', function(){
		    //软键盘收起的事件处理
		    isReset = true;
		    setTimeout(function(){
		    	//当焦点在弹出层的输入框之间切换时先不归位
		      if (isReset) {
		        window.scroll(0, 0);//失焦后强制让页面归位
		      };
		    }, 300);
		  });
		} else if (getDeviceType() === 'android') {
		  window.onresize = function () {
		    //键盘弹起与隐藏都会引起窗口的高度发生变化
		    let resizeHeight = document.documentElement.clientHeight || document.body.clientHeight;
		    if (resizeHeight < h) {
		      //当软键盘弹起，在此处操作
		      isReset = false;
		    } else {
		      //当软键盘收起，在此处操作
		      isReset = true;
		      setTimeout(function(){
		        if (isReset) {
		          window.scroll(0, 0);//失焦后强制让页面归位
		        }
		      }, 300);
		    };
		  };
		};
	</script>
</body>
</html>