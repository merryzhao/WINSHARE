	function visible(targetid) {
		$("#" + targetid).show();
	}
	function invisible(targetid) {
		$("#" + targetid).hide();
	}
	function ajaxget(url, date, id) {
		$("#requestInfo").html('正在请求,请稍后......');
		$("#requestInfo").dialog("open");
		$.ajax({
			type : 'get', //可选get
			url : url, //这里是接收数据的程序
			data : date, //传给PHP的数据，多个参数用&连接
			dataType : 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
			success : function(msg) {
				//这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
				$('#' + id).html(msg);
				$("#requestInfo").dialog("close");
			},
			error : function() {
				$("#requestInfo").html('请求失败');
			}
		})
	}
	function ajaxpost(url, form, id,flag) {
             if(flag=="add"){
             	if(!/^[0-9]{1,4}$/.test($("#index").val())||$("#index").val().length==0){
            		alert("顺序只能是数字");
            		return;
            	}
            }
            if(flag=="edit"){
            	if(!/^[0-9]{1,4}$/.test($("#editIndex").val())||$("#editIndex").val().length==0){
            		alert("顺序只能是数字");
            		return;
		    	}
            }
			$("#requestInfo").html('正在请求,请稍后......');
			$("#requestInfo").dialog("open");
			$.ajax({
				type : 'post', //可选get
				url : url, //这里是接收数据的程序
				data : $("#"+form).serialize(), //传给PHP的数据，多个参数用&连接
				dataType : 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
				success : function(msg) {
					//这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
					$('#' + id).html(msg);
					$("#requestInfo").dialog("close");
				},
				error : function() {
					$("#requestInfo").html('请求失败');
				}
			})
	}
	function deleteproductMetaEnum(url,div){
		if(confirm('确定要删除记录吗?')){
			ajaxget(url,'',div);
		}
	}
	function gotoproductMetaEnum(id){
		$("#productMetaEnumdiv").dialog("open");
		ajaxget('/product/'+id+'/productMetaEnumList','','productMetaEnumdiv');
	}
	$("#requestInfo").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 250,
		height : 80
	});
	$("#productMetaEnumdiv").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 600
	});