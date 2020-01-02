(function($,window,undefined) {
	var uploaderFileSvc = {
		url : {
			delAttachment : rootAdminPath +  "/sys/attachment/delete?t=" + new Date().getTime(),
			findAttachment : rootAdminPath + "/sys/attachment/list?t=" + new Date().getTime(),
			saveAttachment : rootAdminPath + "/sys/attachment/save?t=" + new Date().getTime(),
			getAtta : rootAdminPath + "/sys/attachment/getAtta?t=" + new Date().getTime()
		},
		fnDelFile: function(atta) {
            Svc.AjaxJson.post(uploaderFileSvc.url.delAttachment, atta);
        }
	}
	!window.Svc && (window.Svc = {});
	Svc.ajaxUpload =function(option)
	{
		var settings ={
			auto:false,
			headers :{
    			accept:"*/*"
    		},
    		accept :{
    			extensions: "gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG,doc,xls,docx,xlsx,DOC,XLS,DOCX,XLSX,ppt,PPT,pptx,PPTX,java,txt,jar,rar,zip,chm"
    		},
    		multiple:false,
	        // swf文件路径
		    swf:'Uploader.swf',
		    // 文件接收服务端。
		    server:option.url?option.url:uploaderFileSvc.url.saveAttachment,
		    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		    resize: false,
	        fileVal: 'ajaxFile',
	        pick: option.pick,
	        fileSingleSizeLimit: 50* 1024 * 1024,    // 50 M
	        fileSizeLimit: 200 * 1024 * 1024,    // 200 M
	        compress:{
	        	quality: 50
	        }
	    };
		var uploader = WebUploader.create($.extend({}, settings, {
			                formData:option.formData
			            }));
		var fileArr =[]	            
        uploader.on( 'beforeFileQueued', function( file ) {
        	$.each(fileArr,function(i,v){
        		uploader.removeFile( v );
        	})
	    });
		uploader.on('uploadBeforeSend', function (obj, data, headers) {
			option.uploadBeforeSend&& option.uploadBeforeSend(data);
		});
        // 当有文件添加进来的时候
	    uploader.on( 'fileQueued', function( file ) {
	    	fileArr.push(file);
	    	option.onChange&& option.onChange(file);
	    });
	    // 文件上传过程中创建进度条实时显示。
		uploader.on( 'uploadProgress', function( file, percentage ) {
		});
	    uploader.on( 'uploadSuccess', function( file,response ) {
	    	option.onSuccess&& option.onSuccess( file,response );
	    });
	    uploader.on( 'uploadError', function( file ) {
	    });
		uploader.on( 'uploadComplete', function( file ) {
			option.onComplete&& option.onComplete(file);
		});
		uploader.onError = function(code) {
            switch (code) {
              case "F_DUPLICATE":
              	layer.msg("不能同时上传文件相同文件");
				break;
              case "Q_EXCEED_NUM_LIMIT":
              	layer.msg("已达允许上传文件数量");
				break;
              case "F_EXCEED_SIZE":
              	layer.msg("单个文件超出20M");
				break;
              case "Q_EXCEED_SIZE_LIMIT":
                layer.msg("上传文件超出200M");
				break;
              case "Q_TYPE_DENIED":
              	layer.msg("上传文件格式错误");
				break;

              default:
              	layer.msg("错误: " + code);
				break;
            }
        };     
		return uploader;
	};
})(jQuery,window);