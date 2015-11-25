	
$(function(){
	$("#tree-table").treeTable({clickableNodeNames:true,initialState:"expanded"});
});

$("#editCommission").dialog({
		autoOpen:false,
		bgiframe:false,
		modal:true
});
$("#editPay").dialog({
	autoOpen:false,
	bgiframe:false,
	modal:true
});

function showCommission(id,commission){
	$("#unionCommissionId").attr("value",id);
	$("#oldCommission").attr("value",commission);	
	$("#editCommission").dialog("open");
}

function editCommission() {
	var unionCommissionId = $("#unionCommissionId").attr("value");
	var newCommission = $("#newCommission").attr("value");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : 'newCommission='+newCommission,
		url : '/union/'+unionCommissionId+'/editCommission?format=json',
		error : function() {//请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { //请求成功后回调函数。  
			if(data.result==1){
				$("#commission"+unionCommissionId).text(newCommission);
				$("#editCommission").dialog("close");
			}else{
				alert('更新失败');
			}
		}
	});
}
function showPay(id,isPay){
	$("#unionCommissionId2").attr("value",id);
	if(isPay){
		$("input[type='radio'][name='isPay']").eq(0).attr("checked", true);
	}else{
		$("input[type='radio'][name='isPay']").eq(1).attr("checked", true);
	}
	$("#editPay").dialog("open");
}

function editPay() {
	var unionCommissionId = $("#unionCommissionId2").attr("value");	
	var isPay = $("input[type='radio'][name='isPay'][checked='checked']").attr("value");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : 'isPay='+isPay,
		url : '/union/'+unionCommissionId+'/editPay?format=json',
		error : function() {//请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { //请求成功后回调函数。  
			if(data.result == 1){
				if(isPay == 1){
					$("#pay"+unionCommissionId).text("已支付");
				}else{
					$("#pay"+unionCommissionId).text("未支付");
				}
				$("#editPay").dialog("close");
				alert(data.message);
			}else{
				alert('更新失败');
			}
		}
	});
}

$(document).ready(
	function(){
	   $("#isPay").bind("click",function(){
					   $(this).attr("checked",true);
					   $("#isNoPay").attr("checked",false);
	          });
	   $("#isNoPay").bind("click",function(){
					   $(this).attr("checked",true);
					   $("#isPay").attr("checked",false);
					   });		
});
	
function checkTime(){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	var startYear = $(".startYear").attr("value");
	var startMonth = $(".startMonth").attr("value");
	var endYear = $(".endYear").attr("value");
	var endMonth = $(".endMonth").attr("value");
	if(startYear > endYear){
		alert("只能选择可结算的月份");
		return false;
	}	
	if(startYear >= endYear && startMonth>endMonth){
		alert("只能选择可结算的月份");
		return false;
	}
	if(endYear == year && endMonth >month){
		alert("只能选择可结算的月份");
		return false;
	}
	return true;
}
function exportUnionCommission(){
	var form = $("form[id='unionCommissionList']");	
	form.attr("action","/union/unionCommissionList?format=xls");
	form.submit();
	form.attr("action","/union/unionCommissionList");
}





