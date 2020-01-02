<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
<button id="expBtn" class="btn btn-primary btn-sm hide"  style="margin-bottom: 10px;"><i class="fa fa-send-o"></i> 导出数据</button>
<table id="datatables_scoreDetail" class="display table-bordered" cellspacing="0"></table>
<script type="text/javascript">
	$(function(){
		var scoreDetail= window.scoreDetail;
		window.scoreDetail = undefined;
		var	scoreDetailSvc = {
			url: {
				findScoreDetail: rootPath + "/back/studentAchievement/scoreDetailList?t="+new Date().getTime()
			}
		}
		var memberOrgIntegralTable = $('#datatables_scoreDetail').dataTable({
			sAjaxSource: scoreDetailSvc.url.findScoreDetail,
			fnServerData: fnServer(scoreDetail),
			aoColumnDefs: [
				{ aTargets: [0], mDataProp: "taskName", sTitle: "任务"},
				{ aTargets: [1], mDataProp: "orgName", sTitle: "班级名称", mRender: function(v){
						return v||'-';
					}},
				{ aTargets: [2], mDataProp: "subjectName", sTitle: "学科", mRender: function(v){
						return v||'-';
					}},
				{ aTargets: [3], mDataProp: "stuName", sTitle: "学生", mRender: function(v){
						return v||'-';
					}},

				{ aTargets: [4], mDataProp: "score", sTitle: "成绩", mRender: function(v){
						return v||'-';
				}},
			],
			initComplete: function( settings ){
				// 表格头部按钮权限
				$.each(memberOrgIntegralTable.permission,function(i,perm){
					switch(perm){
						case 'oa:employee:edit':
						case 'permission:all':
							$('#expBtn').removeClass('hide');
							/*$("#expBtn").click(function(){
								location.href=rootPath + "/cgs/oa/employee/expPay?t="+new Date().getTime()+"&recommender="+recommender+"&gasStationId="+top.currentStation.id+"&recommenderName="+recommenderName;
							});*/
							break;
					}
				});
			}
		});
	});
</script>
</body>