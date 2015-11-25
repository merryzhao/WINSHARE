function channelChange(channelId,checkid) {
	var channelUrl = '/channel/'+channelId+'/change?format=json';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : channelUrl,
		error : function() {//请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { //请求成功后回调函数。  
			if(data.result==1){
				if($("#checkid"+checkid).attr("checked") == "checked"){
					$("#checkspan"+checkid).html("已启用");
				}else{
					$("#checkspan"+checkid).html("未启用");
				}
			}else{
				alert('更新失败');
			}
		}
	});
}

	
$(function(){
	$("#tree-table").treeTable({clickableNodeNames:true,initialState:"expanded"});
});

$("#newChild").dialog({
		autoOpen:false,
		bgiframe:false,
		modal:true
});
function addChild(parent){
	$("label[for='clname']").remove();
		$("#channelForm").attr("action","/channel/create");
		$("#selecttype").val("startvalue");
		$("input[name='parent']").val(parent);
		$("input[type='radio'][name='available']").eq(0).attr("checked", true);
		$("input[type='radio'][name='usingapi']").eq(1).attr("checked", true);
		$("#newChild").dialog("open");
}
function edit(id,name,index,available,usingApi,issettle){
	$("label[for='clname']").remove();
	$("#clname").val(name);
	$("input[name='parent']").val(id);
	
	var typename = $("#typename"+index).html();
	$("#selecttype option").each(function(){
		if($(this).html() == typename){
			$(this).attr("SELECTED","SELECTED");
		}
	});
	if(available){
		$("input[type='radio'][name='available']").eq(0).attr("checked", true);
	}else{
		$("input[type='radio'][name='available']").eq(1).attr("checked", true);
	}
	if(usingApi){
		$("input[type='radio'][name='usingapi']").eq(0).attr("checked", true);
	}else{
		$("input[type='radio'][name='usingapi']").eq(1).attr("checked", true);
	}
	if(issettle){
		$("input[type='radio'][name='issettle']").eq(0).attr("checked", true);
	}else{
		 $("input[type='radio'][name='issettle']").eq(1).attr("checked", true);
	}
	$("#newChild").dialog("open");
	$("#channelForm").attr("action","/channel/edit");
}

$().ready(function() {
	$("#channelForm").validate({
		rules : {
			name:{
				required : true
			}
		},
		messages : {
			name:{
				required : "渠道必填"
			}
		}
	});
});