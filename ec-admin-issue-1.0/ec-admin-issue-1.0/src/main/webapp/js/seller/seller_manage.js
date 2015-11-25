$(function() {

	$("#freezenDialog").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"确定" : function() {
				var freezenType = $("#freezenType").val();
				var index = $("#index").val();
				submitOpt(1, freezenType,index);
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});
	$("#verifyLogout").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"确定" : function() {
				submitOpt(2, -1);
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#verifyFreezen").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"确定" : function() {
				submitOpt(1, 1);
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#verifyActive").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"确定" : function() {
				submitOpt(0, -1);
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});
	$("#success").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"确定" : function() {
				$(this).dialog("close");
			}
		}
	});
	$("#fail").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"确定" : function() {
				$(this).dialog("close");
			}
		}
	});
});

function update(sellerId,type, state,index) {
	$("#index").val(index);
	$("#currentSellerId").val(sellerId);
	switch (type) {
	// 激活
	case 0:
		$("#verifyActive").dialog('open');
		break;
	// 冻结
	case 1:
		if (state == 36002) {
			$("#freezenDialog").dialog('open');
		} else {
			$("#verifyFreezen").dialog('open');
		}
		break;
	// 注销
	case 2:
		$("#verifyLogout").dialog('open');
		break;
	}
}

// 提交
function submitOpt(type, freezenType) {
	var url = "/seller";
	var index = $("#index").val();
	var sellerId = $("#currentSellerId").val();
	switch (type) {
	// 激活
	case 0:
		url += "/updateState?updateType=1&sellerId="+sellerId+"&format=json";
		break;
	// 冻结
	case 1:
		if (freezenType == 0) {
			url += "/updateState?updateType=2&sellerId="+sellerId+"&format=json";// 屏蔽
		} else {
			url += "/updateState?updateType=3&sellerId="+sellerId+"&format=json";// 完全冻结
		}
		break;
	// 注销
	case 2:
		url += "/updateState?updateType=4&sellerId="+sellerId+"&format=json";
		break;
	}
	$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : url,
				error : function() {// 请求失败处理函数
					alert('请求失败');
				},
				success : function(data) { // 请求成功后回调函数。
					switch (data.result) {
					// 失败
					case 0:
						$("#fail").dialog('open');
						break;
					// 激活
					case 1:
						$("#stateTd"+index).html("已激活");
						$("#activedateTd"+index).html(data.activeDate);
						$("#optTd"+index).html("<a class='operate-link'  href='javascript:void(0);' onclick='update("+sellerId+",1,36002,"+index+");'>冻结</a><a class='operate-link'  href='javascript:void(0);' onclick='update("+sellerId+",2,36002,"+index+");'>注销</a>");
						$("#success").dialog('open');
						break;
					// 屏蔽
					case 2:
						$("#stateTd"+index).html("搜索引擎屏蔽");
						$("#optTd"+index).html("<a class='operate-link'  href='javascript:void(0);' onclick='update("+sellerId+",0,36003,"+index+");'>激活</a><a class='operate-link'  href='javascript:void(0);' onclick='update("+sellerId+",1,36003,"+index+");'>冻结</a><a class='operate-link'  href='javascript:void(0);'onclick='update("+sellerId+",2,36003,"+index+");'>注销</a>");
						$("#success").dialog('open');
						break;
					// 冻结
					case 3:
						$("#stateTd"+index).html("完全冻结");
						$("#optTd"+index).html("<a class='operate-link'  href='javascript:void(0);' onclick='update("+sellerId+",0,36004,"+index+");'>激活</a><a class='operate-link'  href='javascript:void(0);' onclick='update("+sellerId+",2,36004,"+index+");'>注销</a>");
						$("#success").dialog('open');
						break;
					// 注销
					case 4:
						$("#stateTd"+index).html("已注销");
						$("#optTd"+index).html("");
						$("#success").dialog('open');
						break;
					}

				}
			});
}
