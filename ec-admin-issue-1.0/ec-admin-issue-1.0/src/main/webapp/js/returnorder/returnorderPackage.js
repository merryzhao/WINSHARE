function checkFile(){
	var fn = $("#productFile").val();
	if(fn == ""){
		alert("请选择上传的Excel文件");
		return false;
	}
	if(fn.lastIndexOf(".xls") == -1 || fn.lastIndexOf(".xls") == -1){
		alert("上传的文件必须为Excel文件");
		return false;
	}
	return true;
}

//当没有商品时
function noProduct(){
	$("#information").html("").append("商品不存在!");
}
//往table里添加数据
function addProduct(data){
	var position = $("#product tr").length-2;
	var exist = false;
	var error = "";
	$("#product input[name='productSale']").each(function(index){
		if($(this).val()==data.eccode){
			exist = true;
			if($("#information").find("#error"+data.eccode).html()==null){
				error = "<span id=error"+data.eccode+" > EC商品编号为"+data.eccode+"已添加! </span>";
			}
		}
	});
	
	if(!exist){
	 	var str = "<tr class=product_content>" +
	 			"<td class=index>"+(1+position)+"</td>" +
	 			"<td>"+data.eccode+"<input class=produtSale type=hidden name=productSale value="+data.eccode+" /></td>" +
	 			"<td>"+data.isbn+"</td>" +
	 			"<td>"+data.name+"</td>" +
	 			"<td>"+data.listprice+"</td>" +
	 			"<td><input id=num"+data.eccode+" class=num type=text name=quantity value="+data.quantity+" size=2 onkeypress=EnterPress("+data.eccode+",event) /></td>" +
	 			"<td><input id=loca"+data.eccode+" type=text name=locations onkeypress=EnterPress('search_ISBN',event) /></td>" +
	 			"<td><a href=javascript:void(0) class=cldelete>删除</a></td></tr>"; 
		$("#product tr:eq("+position+")").after(str);
		setTotal();
		document.getElementById("num"+data.eccode).focus();
	}else if(error!=''){
		$("#information").html($("#information").html()+"<br/>"+error);
	}
	if($(".product_content").length>0){
		$("#total").show();
	}
}

function EnterPress(id,event){
	var e;
    if(!event) {
    	event = window.event;
        e = event.keyCode;
    }
    else {//兼容Firefox
        e = event.which;
    }
    //var e = (event.which) ? event.which : event.keyCode;;  
    if(e == 13){ 
    	document.getElementById("search_ISBN").value = '';
    	document.getElementById("search_ISBN").focus();
    }   
    if(e == 9){
    	if(id == 'search_ISBN'){
    		document.getElementById(id).value = '';
        	document.getElementById(id).focus();
    	}else{    		
    		document.getElementById("loca"+id).focus();
    	}
    }
}

function getProduct(){
	var ISBN = $("input[name='search_ISBN']").val();
	if(checkAPF()){
		$.ajax({
			url:"/returnorder/addPackageProduct?search_ISBN="+ISBN,
			type:"post",
			dataType: "json",
			success: function(msg){
				console.log("success get product");
			}, 
			error: function(msg){
				console.log("fail to server");
			}
		});
	}
}

//添加之后删除数据
$("a[class='cldelete']").live('click',
		function() {
			var v = confirm("确定删除?");
			if(v==true){
				var thisvalue = $(this).parent().parent()
				.children(".index").html();
		$("#product").find("td[class='index']").each(function() {
			if ($(this).html() > thisvalue) {
				var temp = $(this).html() - 1;
				$(this).html(temp);
			}
		});
		$(this).parent().parent().remove();
		setTotal();
		var temp = "#error"+$(this).parent().parent().find(".produtSale").val();
		$(temp).remove();
			}
			if($(".product_content").length==0){
				$("#total").hide();
			}
		});

//清空商品
$("#clear").live('click',function(){
	if (confirm("确认清空商品?")) {
		$("#product").find("tr[class='product_content']").each(
				function() {
					$(this).remove();
				});
		$("#total_number").html("0");
		$("#total_money").html("0");
		$("#information").html("").css({"border":"solid 0px red"});
		$("#total").hide();
	}
});

//设置总数量，总码洋
function setTotal(){
	var totalNumber = 0;
	$("input[name='quantity']").each(function() {
		totalNumber += parseInt($(this).val());
	});
	$("#total_number").html(totalNumber);
	var totalMoney = 0;
	$("span[class='price']").each(
			function() {
				var number = $(this).parent().parent().find(
						"input[name='quantity']").val();
				var tempMoney = (number * $(this).html());
				totalMoney += parseFloat(tempMoney);
			});
	$("#total_money").html(parseFloat(totalMoney.toFixed(2)));
}

//商品搜索时如果不填写收缩条件，则不搜索
function checkAPF(){
	//var id =$("input[name='search_productSaleId']").val();
	//var outerid=$("input[name='search_outerId']").val();
	var ISBN = $("input[name='search_ISBN']").val();
	//var name = $("input[name='search_productName']").val();
	if(ISBN==""||ISBN==null){
		alert("请填写查询参数...");
		return false;
	}
	return true;
}

//包件查询，时间设置
function clickRadio(radio,time){
	var date = new Date();
	if(time == 1){
		date.setDate(date.getDate()-1);    		
	}else if(time == 2){
		date.setDate(date.getDate()-7);      	
	}else if(time == 3){
		date.setMonth(date.getMonth()-1);      	
	}else if(time == 4){
		date.setMonth(date.getMonth()-3);      	
	}
	$(" .starttime").attr("value",date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()));  
	var date1 =new Date();
	$(" .endtime").attr("value",date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+(date1.getDate()));        	
}

//点击“零售”时，清空供货数据
function clearSuppply(){
	$('#type').val("1");
	$('#sexpressid').val("");
	$('#sreturnid').val("");
	$('#ssigntime').val("");
	$('#scustomer').val("");
	$('#scarrier').val("");
	$('#ssignname').val("");
	$('#sphone').val("");
	$('#sexpresstime').val("");
	$('#sreturnType').val("");
	$('#saddress').val("");
	$('#sremark').val("");
	$('#sstoragelocation').val("");
	$('#sshouldquantity').val("");
	$('#srealquantity').val("");
}

//点击“供货”时，清空零售数据
function clearRetail(){
	$('#type').val("2");
	$('#expressid').val("");
	$('#signtime').val("");
	$('#customer').val("");
	$('#carrier').val("");
	$('#signname').val("");
	$('#phone').val("");
	$('#expresstime').val("");
	$('#returnType').val("");
	$('#address').val("");
	$('#remark').val("");
	$('#storagelocation').val("");
	$('#shouldquantity').val("");
	$('#realquantity').val("");
}

//包件信息填写完整之后，将值添加到form表单
function fillInForm(){
	var type = $("#type").val().trim();
	if(type == 1){
		var expressid = $("#expressid").val().trim();
		var signtime = $('#signtime').val().trim();
		var customer = $('#customer').val().trim();
		var carrier = $('#carrier').val().trim();
		var signname = $('#signname').val().trim();
		var phone = $("#phone").val().trim();
		var expresstime = $('#expresstime').val().trim();
		var returnType = $('#returnType').val().trim();
		var address = $("#address").val().trim();
		var remark = $('#remark').val().trim();
		var storagelocation = $('#storagelocation').val().trim();
		var shouldquantity = $('#shouldquantity').val().trim();
		var realquantity = $('#realquantity').val().trim();
		
		$("#expressid_form").val(expressid);
		$("#signtime_form").val(signtime);
		$("#customer_form").val(customer);
		$("#carrier_form").val(carrier);
		$("#signname_form").val(signname);
		$("#phone_form").val(phone);
		$("#expresstime_form").val(expresstime);
		$("#returnType_form").val(returnType);
		$("#address_form").val(address);
		$("#remark_form").val(remark);
		$('#storagelocation_form').val(storagelocation);
		$('#shouldquantity_form').val(shouldquantity);
		$('#realquantity_form').val(realquantity);
	}else{
		var expressid = $("#sexpressid").val().trim();
		var signtime = $('#ssigntime').val().trim();
		var customer = $('#scustomer').val().trim();
		var carrier = $('#scarrier').val().trim();
		var signname = $('#ssignname').val().trim();
		var phone = $("#sphone").val().trim();
		var expresstime = $('#sexpresstime').val().trim();
		var returnType = $('#sreturnType').val().trim();
		var address = $("#saddress").val().trim();
		var remark = $('#sremark').val().trim();
		var returnid = $('#sreturnid').val().trim();
		var storagelocation = $('#sstoragelocation').val().trim();
		var shouldquantity = $('#sshouldquantity').val().trim();
		var realquantity = $('#srealquantity').val().trim();
		
		$("#expressid_form").val(expressid);
		$("#signtime_form").val(signtime);
		$("#customer_form").val(customer);
		$("#carrier_form").val(carrier);
		$("#signname_form").val(signname);
		$("#phone_form").val(phone);
		$("#expresstime_form").val(expresstime);
		$("#returnType_form").val(returnType);
		$("#address_form").val(address);
		$("#remark_form").val(remark);
		$("#returnid_form").val(returnid);
		$('#storagelocation_form').val(storagelocation);
		$('#shouldquantity_form').val(shouldquantity);
		$('#realquantity_form').val(realquantity);
	}
}

//包件关联订单信息
function relateOrderOpen(packageid){
	$('#relateOrderDialog').dialog({width:650, title:"关联订单"});
	$('#temp_packageid').attr('value',packageid);
}

//提交关联的订单号
function relateOrder(){
	var ordersList = $("#ordersList").val();
	var packageid = $('#temp_packageid').val().trim();
	$.ajax({
		url:"/returnorder/ralatepackage/"+packageid,
		type:"post",
		data:"orders="+ordersList+"&format=json", 
		dataType:"json",
		success: function(msg){
			if(msg.succeed){	
				$('#relateOrderDialog').dialog('close');
				$("#queryPackageForm").submit();
			}else{
				$("#temp_msg").html(msg.message);
			}
		}, 
		error: function(){
			alert("请求失败！");
			console.log("fail to server");
		}
	});
};

//更改包件状态公用方法
function changePackageStatus(packageid,status){
	$.ajax({
		url:"/returnorder/dealpackage/"+packageid,
		type:"post",
		data:"status="+status+"&format=json", 
		dataType:"json",
		success: function(msg){
			if(msg.succeed){				
				$("#queryPackageForm").submit();
			}else{
				$("#span-smg").html(packageid+"号包件，请求处理失败，请核实后重试...");
			}
		}, 
		error: function(){
			console.log("fail to server");
		}
	});
}

//页面更改包件状态调用此方法
function changeStatus(packageid,status){
	if(confirm("确定要做此操作吗？")){
		changePackageStatus(packageid,status);
	}
}

//申请退换货
function applyReturn(packageid,expressid,orderid){
	if(confirm("确定申请退换货？")){
		window.location.href = "/returnorder/new?id="+orderid+"&expressid="+expressid+"&packageid="+packageid;
	}
}

//批量处理，清理
function checkCheckBox(status){
	var packageids = $("input[name='checkbox']");
	var count = 0;
	var array = new Array();
	var titles = new Array();
	for ( var i = 0; i < packageids.length; i++) {
		if (packageids[i].checked) {
			count++;
			array.push(packageids[i].value);
			titles.push(packageids[i].title);
		}
	}
	if(count==0){
		alert("请至少选择一条记录！！");
	}
	//循环调用清理挂起的公用方法
	for(var i in array){
		/*if(status == 'clear'){
			if(array[i] != 600121 && array[i] != 600122 && array[i] != 600123){
				$("#span-smg").html(titles[i]+"号包件，状态不对，不能清理！！");
				return;
			}
		}*/
		if(status == 'suspend'){
			if(array[i] != 600121 && array[i] != 600122){
				$("#span-smg").html(titles[i]+"号包件，状态不对，不能挂起！！");
				return;
			}
		}
		changePackageStatus(titles[i],status);
	}
}

//表单提交前，验证表单
function checkFormParam(){
	var type = $("#type").val().trim();
	if(type == 1){
		var expressid = $("#expressid").val().trim();
		var signtime = $('#signtime').val().trim();
		var signname = $('#signname').val().trim();
		var returnType = $('#returnType').val().trim();
		var customer = $('#customer').val().trim();
		if(expressid == null || expressid == "" || 
				signtime == null || signtime == "" ||
				customer == null || customer == "" ||
				signname == null || signname == "" ||
				returnType ==null || returnType == ""){
			alert("'*'号为必填项！");
			return false;
		}
	}else{
		var expressid = $("#sexpressid").val().trim();
		var signtime = $('#ssigntime').val().trim();
		var customer = $('#scustomer').val().trim();
		var signname = $('#ssignname').val().trim();
		var returnType = $('#sreturnType').val().trim();
		if(expressid == null || expressid == "" || 
				signtime == null || signtime == "" ||
				customer == null || customer == "" ||
				signname == null || signname == "" ||
				returnType ==null || returnType == ""){
			alert("'*'号为必填项！");
			return false;
		}
	}
	//将参数填入form
	fillInForm();
	
	var productSale_val = $("input[name=productSale]").val();
	if(productSale_val == null || productSale_val == undefined){
		alert("请至少添加一个商品!");
		return false;
	}
	var productquantity = $("input[name=quantity]").val();
	if(productquantity == null || productquantity == 0 || productquantity == undefined){
		alert("商品数量不能为0！");
		return false;
	}
	return true;
}

//添加备注功能
function doremark(packageid){
	$('#remarkDialog').dialog({width:650, title:"包件备注"});
	$('#temp_remark_packageid').attr('value',packageid);
}

//关闭添加备注信息对话框
function closeRemarkDialog(){
	var remark = $('#temp_remark').val();
	var packageid = $('#temp_remark_packageid').val();
	$('#remarkDialog').dialog('close');
	$.ajax({
		url:"/returnorder/packageremark",
		type:"post",
		data:"remark="+remark+"&packageid="+packageid+"&format=json", 
		dataType:"json",
		success: function(msg){
			if(!msg.succeed){				
				$("#span-smg").html("添加备注信息失败，请重试...");
			}
		}, 
		error: function(){
			console.log("fail to server");
		}
	});
}

//展示已添加的备注
function showremark(packageid){
	window.location.href = "/returnorder/packageremark?packageid="+packageid;
}

//展示对应的操作日志
function showlog(packageid){
	window.location.href = "/returnorder/packagelog?packageid="+packageid;
}

$(document).ready(function() {
	//隐藏关联订单对话框
	$('#relateOrderDialog').hide();
	$('#remarkDialog').hide();
	
	$("#accordion").accordion({
		collapsible : true,
		autoHeight: false
	});
	setTotal();
	$("#total").hide();
	
	//包件查询全选
	$("input[name='selectAll']").click(function() {
		if ($("input[name='selectAll']").attr("checked")) {
			$("input[name='checkbox']").attr("checked", "checked");
		} else {
			$("input[name='checkbox']").removeAttr("checked");
		}
	});
	
	$("#confirmtosubmit").click(function(){
		$("#returnorderPackageForm").submit();
	});
	
	/*$("#addProductButton").click(function(){
		$("#addProductForm").submit();
	});*/
});