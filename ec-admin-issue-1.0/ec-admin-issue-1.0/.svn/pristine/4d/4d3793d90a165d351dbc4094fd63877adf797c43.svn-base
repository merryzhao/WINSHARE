var obj;


$().ready(function(){
	$(".selectAll").bind("click",function(){
		if($(this).attr("checked")){
			$("#datatable").find("input[name='orderid']").each(function(){
				$(this).attr("checked",true);
			})
			$(".selectAll").attr("checked",true)
		}else{
			$("#datatable").find("input[name='orderid']").each(function(){
				$(this).attr("checked",false);
			}) 
			$(".selectAll").attr("checked",false)
		}
	})

	$("#exButton").live("click",function(){
		if($("#datatable").find(":checked:checked[name='orderid']").length!=0){
			var ids="";
			$("#datatable").find(":checked:checked[name='orderid']").each(function(){
				ids=ids+$(this).val()+",";
			})
			$("#exids").val(ids);
			$("#actionType").val("select");
		}
		$("#exForm").submit();
	})

})


$().ready(function(){
	$("#setAgent").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width:300
	});	

	$("#statusLog").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width:400
	});


	$(".setAgent").live("click",function(){
		obj=$(this);
		$("#cusid").val($(this).parent().find("span[class='cid']").text());
		$("#uName").text($(this).parent().parent().find("td[class='usernametd']").text());
		$("#setAgent").dialog('open');
	})

	$(".cancelAgent").live("click",function(){
		if(!confirm("确认取消代理商资格")){
			return;
		}
		obj=$(this);
		var id=$(this).parent().find("span[class='cid']").text();
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
				"id" : id,
				"format" : 'json'
			},
			url : "/agent/cancelagent",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				alert("取消成功");
				obj.parent().append("<a href='javascript:void(0)' class='setAgent'>设置为代理商</a>");
				obj.parent().parent().find("td[class='discountcss']").text("");
				obj.parent().parent().find("td[class='isagentcss']").text("非代理商");
				obj.remove();
			}
		});
	})


	$(".findLog").live("click",function(){
		obj=$("#statusLog").find("tbody");
		var id=$(this).parent().find("span[class='cid']").text();
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			dataType : 'json',
			data : {
				"format" : 'json'
			},
			url : "/customer/"+id+"/statuslog",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				var result = data["result"];
				var str = "";
				if(result == 1){
					obj.empty();
					var logs = data["logs"]
					for(var i=0 ; i<logs.length ; i ++){
						var log = logs[i];
						str = str + "<tr><td>" ;
						str = str + log.channel.name;
						str = str + "</td><td>";
						str = str + log.discount;
						str = str + "</td><td>";
						str = str + log.operator.name;
						str = str + "</td><td>";
						str = str + log.updateTime;
						str = str + "</td></tr>";
					}
				}else{
					str = str + "<tr><td colspan=3>" ;
					str = str + "没有日志信息！";
					str = str + "</td></tr>";
				}
				obj.append(str);
				$("#statusLog").dialog('open');
			}
		});
	});

	$("#sbm").bind("click",function(){
		var id=$("#cusid").val();
		var discount=$("#discount").val();
		if(!/^0.[0-9]{3}$/.test(discount)&&discount!==1){
			alert("折扣信息输入错误，折扣为不大于1的3位小数组成或者为1(如：0.989；1)");
			return;
		}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
				"id" :id,
				"discount" :discount,
				"format" : 'json'
			},
			url : "/agent/setagent",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				obj.parent().append("<a href='javascript:void(0)' class='cancelAgent'>取消代理商资格</a>");
				obj.parent().parent().find("td[class='discountcss']").text(data.discount);
				obj.parent().parent().find("td[class='isagentcss']").text("代理商");
				obj.remove();
				$("#setAgent").dialog('close');
			}
		});
	})
})