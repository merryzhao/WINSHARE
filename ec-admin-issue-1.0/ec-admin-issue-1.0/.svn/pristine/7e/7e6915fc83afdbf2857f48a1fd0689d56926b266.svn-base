$(document).ready(function() {
	$('#Condition_type').change(function() {
		var id = $('#Condition_type').val();
		if (id == '8') {
			$('#Condition_enumeration').show();
		} else {
			$('#Condition_enumeration').hide();
		}
	});
	
	//报表服务
	window.report = {
		//查询报表.
		query : function(fromID){
			var _from = $("#" + fromID);
			//所有条件
			var conditions = _from.find("div[tag=div_condition]");
			for (var i =0 ; i < conditions.length ;i++) {
				var allowNull = $(conditions[i]).find("input[id=allowNull]").val();
				if(allowNull == "false"){
					var text = $(conditions[i]).find("lable[id=name]").html();
					var control = $(conditions[i]).find("*[name]");
					var tagName = control[0].tagName;
					if (tagName == "INPUT") {
						var type = control.attr("type")
						type = type ? type.toLowerCase() : null;
						if (type == "text") {
							if (control.val() == "") {
								alert("[" + text + "],不能为空!");
								return false;
							}
						}
						else if (type == "checkbox" || type == "radio") {
								var _valid = false;
								$(control).each(function(i, v){
									if ($(v).attr("checked")) {
										_valid = true;
									}
								});
								if (!_valid) {
									alert("请选中,[" + text + "]!");
									return false;
								}
						}
					} else if (tagName == "SELECT"){
						if (control.val() == 0){
							alert("请选择,[" + text + "]!");return false;
						}
					} else {
						if (control.val() == ""){
							alert("[" + text + "],不能为空!");return false;
						}
					}
				}
			}
			
			_from.submit();
		}
	};
	//document.getElementById('tablesContent').style.display = "none";
});
function newCondition() {
	$("#ConditionDivTitle").html("新建条件");
	// --------------------------------------
	$("#Condition_id").val("");
	// 字段名
	$("#Condition_name").val("");
	// 参数名
	$("#Condition_parameterName").val("");
	// 类型
	$("#Condition_type").attr("value", '0');
	// 枚举
	$("#Condition_enumeration").attr("value", '0');
	// 默认值
	$("#Condition_defaultValue").val("");
	// 控件
	$("#Condition_control").attr("value", '0');
	// 允许空
	$("#Condition_allowNull").attr("checked", false);
	// --------------------------------------
	$("#ConditionDiv").dialog("open");
}
function updateCondition(id) {
	$("#ConditionDivTitle").html("修改条件");
	// --------------------------------------
	// id
	$("#Condition_id").val(id);
	// 字段名
	$("#Condition_name").val($("#" + id + "_name").html());
	// 参数名
	$("#Condition_parameterName").val($("#" + id + "_parameterName").html());
	// 类型
	$("#Condition_type").attr("value", $("#" + id + "_type").html());
	// 枚举
	$("#Condition_enumeration").attr("value",
			$("#" + id + "_enumeration").html());
	// 默认值
	$("#Condition_defaultValue").val($("#" + id + "_defaultValue").html());
	// 控件
	$("#Condition_control").attr("value", $("#" + id + "_control").html());
	// 允许空
	$("#Condition_allowNull").attr("checked",
			$("#" + id + "_allowNull").html() == "true" ? true : false);
	if ($("#" + id + "_type").html() == 8) {
		$("#Condition_enumeration").show();
	}
	// --------------------------------------
	$("#ConditionDiv").dialog("open");
}
function newColumn() {
	$("#ColumnDivTitle").html("新建显示字段");
	// --------------------------------------
	$("#Column_id").val("");
	// 列名
	$("#Column_name").val("");
	// 值
	$("#Column_value").val("");
	// 列宽度
	$("#Column_width").val("");
	// 是否可排序
	$("#Column_order").attr("checked", false);
	// 是否合计
	$("#Column_aggregated").attr("checked", false);
	// 升序SQL
	$("#Column_ascSql").val("");
	// 降序SQL
	$("#Column_descSql").val("");
	// --------------------------------------
	$("#ColumnDiv").dialog("open");
}
function updateColumn(id) {
	$("#ColumnDivTitle").html("修改显示字段");
	// --------------------------------------
	// id
	$("#Column_id").val(id);
	// 列名
	$("#Column_name").val($("#" + id + "_name").html());
	// 值
	$("#Column_value").val($("#" + id + "_value").html());
	// 列宽度
	$("#Column_width").val($("#" + id + "_width").html());
	// 是否可排序
	$("#Column_order").attr("checked",
			$("#" + id + "_order").html() == "true" ? true : false);
	// 是否合计
	$("#Column_aggregated").attr("checked",
			$("#" + id + "_aggregated").html() == "true" ? true : false);
	// 升序SQL
	$("#Column_ascSql").val($("#" + id + "_ascSql").html());
	// 降序SQL
	$("#Column_descSql").val($("#" + id + "_descSql").html());
	// --------------------------------------
	$("#ColumnDiv").dialog("open");
}
function closed(id) {
	$("#" + id).dialog("close");
}
$("#ConditionDiv").dialog({
	autoOpen : false,
	bgiframe : false,
	modal : true,
	width : 600
});
$("#ColumnDiv").dialog({
	autoOpen : false,
	bgiframe : false,
	modal : true,
	width : 600
});
$("#requestInfo").dialog({
	autoOpen : false,
	bgiframe : false,
	modal : true,
	width : 250,
	height : 80
});
// ajax请求
function ajaxForm(url, form, id) {
	$("#requestInfo").html('正在请求,请稍后......');
	$("#requestInfo").dialog("open");
	form = $("#" + form).serialize();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : 'html',
		data : form,
		url : url,
		error : function() {// 请求失败处理函数
			$("#requestInfo").html('请求失败');
		},
		success : function(msg) { // 请求成功后回调函数。
			$("#" + id).html(msg);
			 $("#requestInfo").dialog("close");
		}
	});
}
function ajaxData(url, form, id) {
	$("#requestInfo").html('正在请求,请稍后......');
	$("#requestInfo").dialog("open");
	$.ajax({
		type : 'post',
		dataType : 'html',
		data : form,
		url : url,
		error : function() {// 请求失败处理函数
			$("#requestInfo").html('请求失败');
		},
		success : function(msg) { // 请求成功后回调函数。
			$("#" + id).html(msg);
			 $("#requestInfo").dialog("close");
		}
	});
}
function submitform(flg) {
	if (flg == 1) {
		if(!Condition_form()){
		 	return;
		}
		$("#ConditionDiv").dialog("close");
		var id = $("#Condition_id").val();
		if ("" == id) {// new
			ajaxForm('/report/condition/create', 'Condition_form',
					'condition_div');
		} else {// update
			ajaxForm('/report/condition/update', 'Condition_form',
					'condition_div');
		}
	}
	if (flg == 2) {	
		if(!Column_formValidate()){
		 	return;
		}
		$("#ColumnDiv").dialog("close");
		var id = $("#Column_id").val();
		if ("" == id) {// new
			ajaxForm('/report/column/create', 'Column_form', 'column_div');
		} else {// update
			ajaxForm('/report/column/update', 'Column_form', 'column_div');
		}
	}

}
function deleterow(flg, id) {
	if (confirm('确定要删除此记录吗?')) {
		if (flg == 1) {
			ajaxData('/report/condition/delete', 'id=' + id, 'condition_div');
		}
		if (flg == 2) {
			ajaxData('/report/column/delete', 'id=' + id, 'column_div');
		}
	}
}
function upordown(upordowm, flg, id) {
	if (flg == 1) {
		ajaxData('/report/condition/index', 'id=' + id + '&upordown='
				+ upordowm, 'condition_div');
	}
	if (flg == 2) {
		ajaxData('/report/column/index', 'id=' + id + '&upordown=' + upordowm,
				'column_div');
	}
}
function gridFormSubmit(id) {
	var gridForm = $("#grid_form");
	if (id == "") {// new
		gridForm.attr("action", "/report/grid/create");
	} else {// update
		gridForm.attr("action", "/report/grid/update");
	}
	gridForm.submit();
}

function showTable(url) {
	var target = document.getElementById('tablesContent');
	var img = document.getElementById('tableTreeImg');
	if (target.style.display == "none") {
		visiable(target, img, url);
	} else {
		disvisiable(target, img, url);
	}
}
function visiable(target, img, url) {
	target.style.display = "block";
	img.src = url + "/imgs/area/open.jpg";
}
function disvisiable(target, img, url) {
	target.style.display = "none";
	img.src = url + "/imgs/area/close.jpg";
}

//$(function() {
//	$("#dataSource").change(function() {
//		getTables($("#dataSource option:selected").val());
//	});
//});
//function getTables(dataSourceId) {
//	
//	var tableUrl = "/report/grid/listTable/" + dataSourceId;
//	$.ajax({
//		async : false,
//		cache : false,
//		type : 'GET',
//		url : tableUrl,
//		dataType : 'html',
//		error : function() {// 请求失败处理函数
//			$("#tableTree").html('无数据');
//		},
//		success : function(data) { // 请求成功后回调函数。
//			$("#tableTree").html(data);
//		}
//	});
//}
function submitGridForm(columnIndex,isAsc,format) {	
	
	var parentDocument = parent.document;
	parentDocument.getElementById("orderColumnIndex").value = columnIndex;
	parentDocument.getElementById("isAsc").value = isAsc;
	parentDocument.getElementById("format").value = format;
	parentDocument.getElementById("conditionForm").submit();
	parentDocument.getElementById("orderColumnIndex").value = "-1";
	parentDocument.getElementById("format").value = "";
	parentDocument.getElementById("page").value = "1";
}

function setPage(selectObj){
	parent.document.getElementById("page").value = selectObj.options[selectObj.selectedIndex].value
}
