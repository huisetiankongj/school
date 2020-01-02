$(function(){
	var sysIndexSvc = {
			url: {
				fnMemberCash: 			rootPath + "/cgs/back/memberPayOrder/membePayByCashIndexPage?t="+new Date().getTime(),
				memberRecharge: 			rootPath + "/cgs/back/memberRechargeInfo/memberRecharge?t="+new Date().getTime(),
				membePayByMember: 			rootPath + "/cgs/back/memberPayOrder/membePayByMember?t="+new Date().getTime(),
				updatePwdForm:	 			rootPath + "/sys/home/updatePwdForm?t="+new Date().getTime(),
				updateOperatePwdForm:	 	rootPath + "/sys/home/updateOperatePwdForm?t="+new Date().getTime()
			},
			fnMemberRecharge: function(){
				API.fnShowForm({
					id: 'memberRechargeLayerForm',
					url: sysIndexSvc.url.memberRecharge,
					title: "储值卡充值",
					area: ['800px', '400px'],
					params: {gasStationId: currentStation.ids}
				})
			},
			fnMemberCash: function(){
				API.fnShowForm({
					id: 'memberPayCashLayerForm',
					url: sysIndexSvc.url.fnMemberCash,
					title: "现金支付",
					area: ['800px', '550px'],
					params: {gasStationId: currentStation.ids}
				})
			},
			fnRmbPay: function(){
				API.fnShowForm({
					id: 'memberPayOrderLayerForm',
					url: sysIndexSvc.url.membePayByMember,
					title: "储值卡支付",
					area: ['800px', '550px'],
					params: {gasStationId: currentStation.ids}
				})
			},
			fnPwdModal: function(id,title,params,url){
				API.fnShowForm({
					id: id,
					url: url,
					area: ["644px", "410px"],
					title: title,
					params: params
				});
			},
			fnPageInit: function(){
				$("#printBtn").printPreview();
				
				// 储值卡充值
				$("#memberRecharge").click(function(){
					sysIndexSvc.fnMemberRecharge();
				});
				
				// 储值卡支付
				$("#rmbPay").click(function(){
					sysIndexSvc.fnRmbPay();
				});
				
				// 现金支付
				$("#cashPay").click(function(){
					sysIndexSvc.fnMemberCash();
				});
				
				// 修改登录密码按钮
				$('#updatePwd').click(function(){
					var loginName=$(".loginName").html();
					var params={loginName:loginName};
					var url=sysIndexSvc.url.updatePwdForm;
					sysIndexSvc.fnPwdModal('updatePwdForm','修改登录密码',params,url);
				});
				
				// 修改操作密码按钮
				$('#updateOperatePwd').click(function(){
					var loginName=$(".loginName").html();
					var params={loginName:loginName};
					var url=sysIndexSvc.url.updateOperatePwdForm;
					sysIndexSvc.fnPwdModal('updateOperatePwdForm','修改操作密码',params,url);
				});
			}
	}
	sysIndexSvc.fnPageInit();
});