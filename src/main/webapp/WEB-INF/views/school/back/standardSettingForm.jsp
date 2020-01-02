<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="decorator" content="pageform"/>
<body>
	<form id="standardSettingForm" name="standardSettingForm" action="javascript:void(0);" class="form form-horizontal">
	   <input type="hidden"  name="id" value="${standardSetting.id}" />	
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">org_id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="orgId" value="${standardSetting.orgId}" placeholder="请输入org_id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">subject_id：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="subjectId" value="${standardSetting.subjectId}" placeholder="请输入subject_id" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">pass_score：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="passScore" value="${standardSetting.passScore}" placeholder="请输入pass_score" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label  pl-0 pr-5">excellent_score：</label>
			<div class="col-xs-12 col-sm-7 pl-0">
			    <input type="text" name="excellentScore" value="${standardSetting.excellentScore}" placeholder="请输入excellent_score" datatype="*" class="form-control input-text"/>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
		</form>
		<div class="form-group normal-form">
			<label class="col-xs-12 col-sm-2 control-label pl-0 pr-5"></label>
			<div class="col-xs-12 col-sm-7 pl-0 col-md-offset-2">
				<shiro:hasPermission name="back:standardSetting:edit">
					<button id="formSaveBtn" class="btn btn-primary">保存内容</button>
					<input id="formCancelBtn" class="btn btn-white" type="button" value="取消">
				</shiro:hasPermission>
			</div>
			<div class="col-xs-12 col-sm-3 valid-msg"></div>
		</div>
	<script type="text/javascript">
		$(function(){
			standardSettingSvc = {
				url: {
					saveStandardSetting : rootPath + "/back/standardSetting/save?t="+new Date().getTime()
				},
				fnCommit: function(){
					var saveJson = Svc.formToJson($("#standardSettingForm"));
		        	Svc.AjaxJson.post(standardSettingSvc.url.saveStandardSetting,saveJson,function(response){
		        		if(response == true){
		        			layer.alert('保存成功！',function(index){
		        				$('#datatables_StandardSetting').dataTable().fnDraw();
		        				layer.close(index);
		        				API.fnHideForm('standardSettingLayerForm');
		        			});
		        		}
		        	});
				},
				fnRegisterEvent: function(){
					// 取消按钮
					$('#formCancelBtn').click(function(){
						API.fnHideForm('standardSettingLayerForm');
					});
					// 保存按钮
					$('#formSaveBtn').click(function(){
						standardSettingSvc.fnCommit();
				    });
				}
			};
			standardSettingSvc.fnRegisterEvent();
		});
	</script>
</body>