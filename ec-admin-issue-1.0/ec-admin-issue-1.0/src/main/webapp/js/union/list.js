	
$(function(){
	$("#tree-table").treeTable({clickableNodeNames:true,initialState:"expanded"});
});

$("#new").dialog({
		autoOpen:false,
		bgiframe:false,
		modal:true
});
$("#edit").dialog({
	autoOpen:false,
	bgiframe:false,
	modal:true
});

function add(){
	 	$("#id").val("");
	    $("#name").val("");
	    $("#url").val("");
	    $("#rate").val("");
	    $("#createTime").val("");
	    $("#userName").val("");
	    $("#phone").val("");
	    $("#email").val("");	
		$("input[type='radio'][name='available']").eq(0).attr("checked", true);
		$("#unionNew").attr("action","/union/create");
		$("#new").dialog("open");
}

function edit(id,name,url,rate,createTime,userName,phone,email,available){
	$("label[for='id']").remove();
	$("#id").val(id);
	$("label[for='name']").remove();
	$("#name1").val(name);
	$("label[for='url']").remove();
	$("#url1").val(url);
	$("label[for='rate']").remove();
	$("#rate1").val(rate);
	$("label[for='createTime']").remove();
	$("#createTime1").val(createTime);
	$("label[for='userName']").remove();
	$("#userName1").val(userName);
	$("label[for='phone']").remove();
	$("#phone1").val(phone);
	$("label[for='email']").remove();
	$("#email1").val(email);
	if(available){
		$("input[type='radio'][name='available']").eq(0).attr("checked", true);
	}else{
		$("input[type='radio'][name='available']").eq(1).attr("checked", true);
	}	
	$("#edit").dialog("open");
	$("#unionEdit").attr("action","/union/"+id+"/edit");
}
$(document).ready(function() {
	$("#unionNew").validate({
		rules : {
			name:{
				required : true
			},
			rate :{
	    	    required : true ,
	    	    number : true
			},
			email : {
				email : true
			}
		},
		messages : {
			name:{
				required : "联盟名称必填"	
			},
		    rate:{
		    	required : "佣金比例必填",
		    	number : "必须是数字"	
		    },
			email:{
				email : "邮件格式错误 "
			}
		}
	});
	$("#unionEdit").validate({
		rules : {
			name:{
				required : true
			},
			rate :{
	    	    required : true ,
	    	    number : true
			},
			email : {
				email : true
			}
		},
		messages : {
			name:{
				required : "联盟名称必填"	
			},
		    rate:{
		    	required : "佣金比例必填",
		    	number : "必须是数字"	
		    },
			email:{
				email : "邮件格式错误 "
			}
		}
	});
});

function deleteUnion(id){
	if(!confirm("是否确定删除?")){
		return;
	}
	$.get("/union/"+id+"/delete",{format:"json"}, function(data){
		if (data.result == "1") {
			alert("删除成功！");
			window.location.reload(true);
		}else{
			alert("删除失败！")
		}
  	}); 
}

function query(isReport,isQueryProduct){
	var form = $("form[id='unionOrderList']");
		isProduct = $("input[name='isProduct']");
	if (isQueryProduct) {		
			$("input[name='isProduct']").attr("value", true);
		}else{
			$("input[name='isProduct']").attr("value", false);
		}
	if (isReport) {
		form.attr("action", "/union/unionOrderList?format=xls");
	}
	form.submit();
	form.attr("action","/union/unionOrderList");
}




