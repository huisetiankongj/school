$(function(){
	function ajaxPost(testJson,callback,errorcallback){
		$.ajax({
			url : testJson.urlPrefix+testJson.url,
			type : testJson.type,
			dataType: testJson.dataType,
			contentType:testJson.contentType,
			//data : JSON.stringify(param),
			data : testJson.params,
			success : callback,
			error : errorcallback
		});
	}
	$("#urlPrefix").val(rootPath);
	//淇濆瓨鎸夐挳
	$("#commitBtn").click(function(){
		var testJson = Svc.formToJson($("#testForm"));
		console.log(testJson);
		ajaxPost(testJson,function(data) {
			$("#result").val(JSON.stringify(data));
		},function(data) {
			$("#result").val(JSON.stringify(data));
		});
	});
});