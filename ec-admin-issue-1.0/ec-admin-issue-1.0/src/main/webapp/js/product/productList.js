	//初始 
    var aobj;
    var lastSelectNode = null;
    var cancelPreSaleHtmlTemplate = '<input type="button" onclick="cancelPresaleProduct({productSaleId}, this);" value="取消预售"/>';
	var addPreSaleHtmlTemplate = ''
    $(document).ready(function() {
		$("#productionStartDate").datepicker({changeYear:true});
		$("#productionEndDate").datepicker({changeYear:true});
		$("#category").val("");
  		$("input[bind='datepicker']").datepicker("option","minDate",new Date());
		$("input[name='selectAll']").click(function() {
			if ($("input[name='selectAll']").attr("checked")) {
				$("input[name='export']").attr("checked", "checked");
			} else {
				$("input[name='export']").removeAttr("checked");
			}
		});
		$("input[name='selectAll2']").click(function() {
			if ($("input[name='selectAll2']").attr("checked")) {
				$("input[name='export']").attr("checked", "checked");
			} else {
				$("input[name='export']").removeAttr("checked");
			}
		});
		
		$("#productBookingDialog").dialog({
			autoOpen : false,
			bgiframe : true,
			modal : true,
			width : 500,
			height : 400,
			buttons: {
				"确定": function() {
					setup();					
				},
				"取消": function() {
					$("#stockQuantity").val("");
					$("#bookStartDate").val("");
					$("#bookEndDate").val("");
					$("textarea[name=bookDescription]").val("");
					$( this ).dialog( "close" );
				}
			}
		});
		$("#success").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true
		});
		$("#failure").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true
		});
		$("input:submit,button,.jButton").button();
	})
	
 
	function checkBooking(){
		var count=0;
		var quantity=$("#stockQuantity").val();
		var ignore=$("#ignore").val();
		var sd = $("#bookStartDate").val();
		var ed = $("#bookEndDate").val();
		if(quantity.match(/^[0-9]+$/) == null){ 
			$("#quantityError").html("库存量只能为正整数");
			count++;
		}else{
			$("#quantityError").html("");
		}
		if(ignore.match(/^[0-9]+$/) == null){ 
			$("#ignoreError").html("忽略库存量只能大于等于0");
			count++;
		}else{
			$("#ignoreError").html("");
		}
		if(sd==""||ed==""){
			$("#dateError").html("日期不能为空");
			count++;
		}else if(!comptime(sd,ed)){
			$("#dateError").html("");
			$("#dateError").html("日期先后顺序错误");
			count++;
		}else{
			$("#dateError").html("");
		}		
		return count;
	}
	
	function setup(){
		if(checkBooking()==0){
			setupBooking($("#currentPsId").val(),$("#currentIndex").val());	
			$("#productBookingDialog").dialog("close");
		}
	}
	
	function setBook(psId,index,stock,e){
		var obj=$(e);
		aobj=obj;
		var stockQuantity=$.trim(obj.parent().parent().find("td[class='stockQuantityCss']").text());
		stock = stock < 0 ? 0 : stock;
		$("#ignore").val(stock);
		$("#saleStatusOff").attr("checked",true);
		$("#productBookingDialog").dialog("open");
		$("#currentPsId").val(psId);
		$("#currentIndex").val(index);
	}
	
	function setupBooking(saleId, index) {
	var url = "/product/setBooking";
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,
		dataType : 'json',
		data : {
			"saleId" : saleId,
			"stockQuantity" : $("#stockQuantity").val(),
			"ignore" : $("#ignore").val(),
			"bookStartDate" : $("#bookStartDate").val(),
			"bookEndDate" : $("#bookEndDate").val(),
			"saleStatus" : 13002,
			"bookDescription" : $("textarea[name='bookDescription']").val(),
			"dc" : $("input[name='dc']:checked").val(),
			"format" : 'json'
		},
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			if (data.result == 1) {
  				$("#success").dialog("open");	
 				aobj.parent().parent().find("td[class='statuscss']").text(data.status);
 				aobj.parent().parent().find("td[class='saleStatuscss']").text(data.saleStatus);
 				var cancelPreSaleHtml = cancelPreSaleHtmlTemplate;
 				cancelPreSaleHtml = cancelPreSaleHtml.replace(/\{productSaleId\}/g, saleId);
 				aobj.parent().append(cancelPreSaleHtml);
 				aobj.remove();
				}else{
 					$("#returnMessage").html(data.message);
					$("#failure").dialog("open");
				}
			}
		});
	}
	function cls() {
		// 捕获触发事件的对象，并设置为以下语句的默认对象
		with (event.srcElement)
			// 如果当前值为默认值，则清空
			if (value == defaultValue)
				value = ""
	}

	function res() {
		// 捕获触发事件的对象，并设置为以下语句的默认对象
		with (event.srcElement)
			// 如果当前值为空，则重置为默认值
			if (value == "")
				value = defaultValue
	}

	function inorvisible(targetid) {
		target = document.getElementById(targetid);
		var ismore = document.getElementById("ismore");
		if ("block" == target.style.display) {
			target.style.display = "none";
			cleanData();
			ismore.value = "0"
		} else {
			target.style.display = "block";
			ismore.value = "1"
		}
	}
	function cleanData() {
		var sellerName = document.getElementById("sellerName");
		sellerName.value = ""
		var productAuthor = document.getElementById("productAuthor");
		productAuthor.value = ""
		var productMcCategory = document.getElementById("productMcCategory");
		productMcCategory.value = ""
		var status = document.getElementById("status");
		status.value = "0"
	}
	// 到处excel 表单提交
	function submitExcel() {
		var form = $("#xlsform");
		var exports = $("input[name='export']");
		var noId = true;
		$("#otherinfo").html("");
		for (i = 0; i < exports.length; i++) {
			if (exports[i].checked) {
				var hidden = $("<input type='hidden' name='export' value='"+exports[i].value+"' />");
				$("#otherinfo").append(hidden);
				noId=false;
			}
		}
		if(noId){
			alert("请至少选择一个商品导出！");
			return;
		}
		$("#otherinfo").append(getFormVal("productform"));
		form.submit();
	}
	//移动分类
	function changeCategory(){
		if(lastSelectNode == null){
			alert("所选择的批次移动商品需要为同一起始分类，请在检索条件中选择移动的起始分类!");
			return;
		}
		var exports = $("input[name='export']:checked");
		if(exports.length == 0){
			alert("请选择需要移动分类的商品!");
			return;
		}
		$("#categoryMoveDiv").dialog("open");
	}
	
	function transferCategory(treeId, treeNode){
		var selectNode = ztree2.tree.getSelectedNode();
		if(!selectNode || treeNode.id != ztree2.tree.getSelectedNode().id){
			alert("为防止误操作，请先选中此分类，在点击图标!");
			return false;
		}
		if (!window.confirm("是否将["+lastSelectNode.name+"]商品移动到分类["+treeNode.name+"]?")) {
			return false;
		}
		var exports = $("input[name='export']:checked");
		var flag = "";
		var productIds = "";
		for (i = 0; i < exports.length; i++) {
				productIds = productIds + flag + $(exports[i]).val();
				flag = ",";
		}
		$.ajax({
			type: "POST",
			async: false,
			url: "/product/transfer?format=json",
			data: {
				target:"category",
				categoryId:lastSelectNode.id,
				targetCategoryId:selectNode.id,
				productIds:productIds
			},
			success:function(mav){
				if(mav.result == 1){
					result = true;
				}else{
					alert(mav.message);
				}
			}
		});
		$("#categoryMoveDiv").dialog("close");
	}
	// 提取表单值
	function getFormVal(form) {
		var datevaule = "";
		var thisform = document.getElementById(form);
		// 处理input 内容
		var inputs = thisform.getElementsByTagName("input");
		for (i = 0; i < inputs.length; i++) {// inputs.length
			datevaule += "<input type='hidden' name='"+inputs[i].name+"' value='"+inputs[i].value+"' />"
		}
		// 处理select 内容
		var selects = thisform.getElementsByTagName("select");
		for (i = 0; i < selects.length; i++) {
			datevaule += "<input type='hidden' name='"+selects[i].name+"' value='"+selects[i].value+"' />"
		}
		return datevaule;
	}
	$("#categoryDiv").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 350
	});
	$("#chooseCategory").click(function(){
		$("#categoryDiv").dialog("open");
	});
	
	$("#getChecktree").click(function(){
 		var node = zTree3.getCheckedNodes(true);
 		lastSelectNode = node[0];
		if(node!=null){
			$("#categoryName").text(node[0].name);
			$("input[name='category']").val(node[0].id+"");
		}else{
			$("#categoryName").text("");
			$("input[name='category']").val("");
		}
		$("#categoryDiv").dialog("close");
	});
	
	$("#saleStatusOn").click(function(){		//预售商品的上架判断
		$.ajax({
			async:false,
			cache:false,
			type:'POST',
			dataType:'json',
			data:{
				"proId":$("#currentPsId").val()
			},
			url:"/product/canbeonshelf?format=json",
			success:function(data){
				if(!data.result){
					alert("商品不满足上架条件");
					$("#saleStatusOff").attr("checked",true);
				}
			},
			error:function(){
				alert("请求服务器失败!");
				$("#saleStatusOff").attr("checked",true);
			}
			
		});
	});
	
	
	function comptime(beginTime, endTime) {
	     var beginTimes = beginTime.substring(0, 10).split('-');
	     var endTimes = endTime.substring(0, 10).split('-');
	     beginTime = beginTimes[1] + '/' + beginTimes[2] + '/' + beginTimes[0] + '/ ' + beginTime.substring(10, 19);
	     endTime = endTimes[1] + '/' + endTimes[2] + '/' + endTimes[0] + '/ ' + endTime.substring(10, 19);
	     var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
	     if (a < 0) {
	         return false;
	     } else {
	      return true;
	     }
	 }
	  $("#successButton").click(function(){
	 	$("#success").dialog("close");
	 });
	function cancelPresaleProduct(productSaleId, btn) {
	    if (window.confirm("确认取预售商品?")) {
	        $.post("/product/cancelPresaleProduct/"+productSaleId,{format:"json"},function(msg){
	            if (msg.result == 1) {
	            	
	                $(btn).remove();
	            }
	            alert(msg.message);
	        });
	    };
	};