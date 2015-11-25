//订单修改
$(document).ready(function(){
			if($("#editShow").val()!='8001' && $("#editShow").val()!='8002'){
				$(".edit-img").css({display:"none"});
				$(".edit-needi").css({display:"none"});
				$(".edit-areaimg").css({display:"none"});
				$(".edit-areaimg").css({display:"none"});
				$(".edit-area").css({display:"none"});
				$(".edit-selectimg").css({display:"none"});
				$(".edit-transfer").css({display:"none"});
			}
		    $("#yes").live("click",function(){
		    	$("#theTitleType").show();
 		    	if($("#invoiceTitleType").val()==3461){
		    		$("#comName").show();
 		    	}
		    })
		    
		    $("#invoiceTitleType").live("change",function(){
 		    	if($("#invoiceTitleType").val()==3461){
		    		$("#comName").show();
		    	}else{
		    		$("#comName").hide();
		    	}
		    })
		    
		    $("#no").live("click",function(){
		    	$("#theTitleType").hide();
		    	$("#comName").hide();
		    })
});


$(document).ready(
		function() {
			$("#order-delivery").dialog({
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width:500
			});	
			
			$("#ori-dc-edit").dialog({
				autoOpen:false,
				bgiframe:false,
				modal:true,
				width:500				
			});
			
			$("#orderSupplyType-edit").dialog({
				autoOpen:false,
				bgiframe:false,
				modal:true,
				width:500				
			});
			
			$("#delivery-button").click(function(){
				openDeliveryDialog();
			});		
			
			$("#delivery-close").click(function(){
				closeDeliveryDialog();
			});	
			
			$("#update-text").dialog({ // 初始化订单修改的dialog
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width : 250,
				hight : 100

			});
			$(".edit-ori-dc").bind("click",function() {// 修改发货dc单击事件
				var defaultDc = $(this).parent().attr('id');
				if(defaultDc == '110001'){
					$("#dc").html("<option value='110002'>D801</option>");
				} else if(defaultDc == '110002'){
					$("#dc").html("<option value='110001'>D803</option>");
				} else if(defaultDc == '110003'){
					$("#dc").html("<option value='110004'>D818</option><option value='110007'>D819</option>");
				} else if(defaultDc == '110004'){
					$("#dc").html("<option value='110003'>8A17</option><option value='110007'>D819</option>");
				} else if(defaultDc == '110007'){
					$("#dc").html("<option value='110003'>8A17</option><option value='110004'>D818</option>");
				}else{
					alert("不能修改");
					return;
				}
				$("#ori-dc-edit").dialog();
				$("#ori-dc-edit").dialog("open");
			});
			$("#delivery-company-edit").dialog({
				autoOpen:false,
				bgiframe:false,
				modal:true,
				width:500				
			});
			$(".edit-delivery-company").click(function() {
				$("#delivery-company-edit").find(".deliveryCompany").val("");
				$("#delivery-company-edit").dialog();
				$("#delivery-company-edit").dialog("open");
			});
			
			$(".edit-orderSupplyType").bind("click",function() {// 修改订单类型单击事件
				$("#orderSupplyType-edit").dialog();
				$("#orderSupplyType-edit").dialog("open");
			});
			
			$(".edit-img")
					.bind(
							"click",
							function() {// 为修改图标绑定定单击事件
								$("#updateid").attr("value",$(this).parent().attr("id")); // 保存要更新的字段的名字到隐藏域
								$('#textname').html($(this).attr("name")); // 设置要修改的字段名称
								$("#updatetext").attr("value",$("#"+ $(this).parent().attr("id")+ ">span").text());// 把原来的值设置到dialog中
								$("#updatevalue").attr("value",
										$("#updatetext").val()); // 保存要修改的字段的值到隐藏域
								$("#update-text").dialog();
								$("#update-text").dialog("open");
							});

			$("#updateSubmit").bind(
					"click",
					function() {// 为订单信息修改的提交按钮绑定事件
						if ($("#updatevalue").val() == $("#updatetext").val()) {// 如果值没有更新
							// 直接关闭dialog
							$("#update-text").dialog("close");
							return;
						}
						if(!validate($("#updateid").val(),$("#updatetext").val())){
							return ;
						}
						// 异步修改
						var orderUpadateUrl = "/order/edit";
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							dataType : 'json',
							data : {
								"paramName" : $("#updateid").val(), // 要修改的字段名称
								"paramValue" : $("#updatetext").val(), // 新值
								"orderId" : $('#orderId').val(),
								"format" : 'json'
							},
							url : orderUpadateUrl,
							error : function() {// 请求失败处理函数
								alert('保存失败');
							},
							success : function(data) { // 请求成功后回调函数。
								$("#" + $("#updateid").val() + ">span").text(
										$("#updatetext").val());// 把新值设置到页面上
								$("#update-text").dialog("close");
							}
						});
					});
			$("#updateCancle").bind("click", function() {
				$("#update-text").dialog("close");
			});

		});

// 是否需要发票
$(document).ready(
		function() {
			$("#update-needinvoice").dialog({ // 初始化订单修改的dialog
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width : 250,
				hight : 100
			});

			$(".edit-needi").bind(
					"click",
					function() {// 为修改图标绑定定单击事件
						$("#updateid").attr("value",
								$(this).parent().attr("id")); // 保存要更新的字段的名字到隐藏域
						$('#textname').html($(this).attr("name")); // 设置要修改的字段名称
						$("#updatetext").attr(
								"value",
								$("#" + $(this).parent().attr("id") + ">span")
										.text());// 把原来的值设置到dialog中
						if ($("#" + $(this).parent().attr("id") + ">span")
								.attr("id") == "false") {
							$("input[name='needinvoice'][value=0]").attr(
									"checked", true);
					    	$("#theTitleType").hide();
					    	$("#comName").hide();
						}
						if ($("#" + $(this).parent().attr("id") + ">span")
								.attr("id") == "true") {
							$("input[name='needinvoice'][value=1]").attr(
									"checked", true);
							$("#comName").hide();
					    	$("#theTitleType").show();
 			 		    	if($("#invoiceTitleType").val()==3461){
 			 		    		$("#invoiceTitle").val("")
					    		$("#comName").show();
			 		    	}
 						}
						$("#updatevalue").attr("value",
								$("input[name='needinvoice']:checked").val()); // 保存要修改的字段的值到隐藏域
						$("#update-needinvoice").dialog();
						$("#update-needinvoice").dialog("open");
					});
			
			$("#needSubmit").bind(
					"click",
					function() {// 为订单信息修改的提交按钮绑定事件
 						// 异步修改
						var orderUpadateUrl = "/order/edit";
 						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							dataType : 'json',
							data : {
								"paramName" : $("#updateid").val(), // 要修改的字段名称
								"paramValue" : $("input[name='needinvoice']:checked").val(), // 新值
								"orderId" : $('#orderId').val(),
								"invoiceTitleType" : $("#invoiceTitleType").val(),
								"invoiceTitle" : $("#invoiceTitle").val(),
								"format" : 'json'
							},
							url : orderUpadateUrl,
							error : function() {// 请求失败处理函数
								alert('保存失败');
							},
							success : function(data) { // 请求成功后回调函数。
								if(data.message == null || data.message == ''){
									window.location.reload();
								}else{
									alert(data.message);
								}
							}
						});
					});
			$("#needCancle").bind("click", function() {
				$("#update-needinvoice").dialog("close");
			});
		});

//修改正传中启
$(function(){
	$("#update-transfer").dialog({ // 初始化订单修改的dialog
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 250,
		hight : 100
	});
	$(".edit-transfer").bind("click",function() {// 为修改图标绑定定单击事件
		var val = $("#transferLabel").data("val");
		if(val == '35003'){
			$("input[name=transfer][value=false]").attr("checked", true);
		} else if(val == '35002'){
			alert("已下传中启, 无法修改!");
			return;
		} else if(val == '35001'){
			$("input[name=transfer][value=true]").attr("checked", true);
		}
		$('#textname').html($(this).attr("name")); // 设置要修改的字段名称
		$("#update-transfer").dialog();
		$("#update-transfer").dialog("open");
	});
	
	$("#transferSubmit").bind("click",function() {// 为订单信息修改的提交按钮绑定事件
		// 异步修改
		var orderUpadateUrl = "/order/edit";
		var val = $("input[name=transfer]:checked").val();
		if($("#transferLabel").data("val") == '35003'){
			if(val == 'false'){
				$("#update-transfer").dialog("close");
				return;
			}
		} else {
			if(val == 'true'){
				$("#update-transfer").dialog("close");
				return;
			}
		}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			url : orderUpadateUrl,
			data : {
				"paramName" : "transfer", // 要修改的字段名称
				"paramValue" : val, // 新值
				"orderId" : $('#orderId').val(),
				"format" : 'json'
			},
			error : function() {// 请求失败处理函数
				alert('保存失败');
			},
			success : function(data) { // 请求成功后回调函数。
				if(val == 'false'){
					$("#transferLabel").data("val", "35003");
					$("#transferValue").html('否');
				}else if(val == 'true'){
					$("#transferLabel").data("val", "35001");
					$("#transferValue").html('是');
				}
				$("#update-transfer").dialog("close");
				alert('保存成功!');
				window.location.reload();
			}
		});
	});
	$("#transferCancle").bind("click", function() {
		$("#update-transfer").dialog("close");
	});
	$("#oriDcCancle").bind("click", function() {
		$("#ori-dc-edit").dialog("close");
	});
	$("#oriDcSubmit").bind("click", function(){
		var dcId = $("select[name=oriDc]").val();
		var imgDom = $(".edit-ori-dc");
		if(dcId == imgDom.parent().attr("id")){
			alert('请选择一个与当前不相同的dc');
			return;
		}
		var orderId = $("#orderId").val();
		$.ajax({
			dataType : 'json',
			url : '/order/changedc',
			type : 'POST',
			data : 'orderId='+ orderId+'&dcId='+dcId+'&format=json',
			success : function(msg){
				if(msg.result == 1){
					var currDc = imgDom.parent();
					currDc.attr('id', msg.dc.id);
					if(msg.order.processStatus.id == 8001 || msg.order.processStatus.id == 8002){
						html = '<img class="edit-ori-dc" style="cursor: pointer;" src="/imgs/orderinfo/edit.jpg" alt="编辑" name="发货DC">';
					}
					currDc.html(msg.dc.name + html);
					$("#ori-dc-edit").dialog("close");
					location.reload();
				}else{
					alert('修改失败,' + msg.message);
				}
			},
			error : function(msg){
				alert('修改失败,' + msg.message);
			}
		});
	});
	$("#orderSupplyTypeCancle").bind("click", function() {
		$("#orderSupplyType-edit").dialog("close");
	});
	$("#deliveryCompanySubmit").click(function() {
		var orderId = $("#orderId").val();
		var deliveryCompanyId = $("#delivery-company-edit").find(".deliveryCompany").val();
		var auditUrl = '/order/' + orderId + '/auditOrderShipper?format=json&deliveryCompanyId='+deliveryCompanyId;
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			url : auditUrl,
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				if(data.result == 1){
					$("#delivery-company-edit").dialog("close");
					location.reload();
				} else{
					alert('修改失败,' + data.message);
				}
			}
		});
	});
	$("#deliveryCompanyCancle").click(function() {
		$("#delivery-company-edit").dialog("close");
	});
	$("#orderSupplyTypeSubmit").bind("click", function(){
		$.get('/order/updatesupplytype?orderId='+$("#orderId").val()+'&format=json',function(){
			$("#orderSupplyType-edit").dialog("close");
			location.reload();
		}, 'json');
	})
});
// 备注留言和详细地址修改
$(document).ready(
		function() {
			$("#update-textarea").dialog({ // 初始化订单修改的dialog
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width : 550,
				hight : 250
			});
			$(".edit-areaimg").bind(
					"click",
					function() {// 为修改图标绑定定单击事件
						$("#updateid").attr("value",
								$(this).parent().attr("id")); // 保存要更新的字段的名字到隐藏域
						$('#textareaname').html($(this).attr("name")); // 设置要修改的字段名称
						$("#updatetextarea").attr(
								"value",
								$("#" + $(this).parent().attr("id") + ">span")
										.text());// 把原来的值设置到dialog中
						$("#updatevalue").attr("value",
								$("#updatetextarea").val()); // 保存要修改的字段的值到隐藏域
						$("#update-textarea").dialog();
						$("#update-textarea").dialog("open");
					});
			$("#areaSubmit").bind(
					"click",
					function() {// 为订单信息修改的提交按钮绑定事件
						if ($("#updatevalue").val() == $("#updatetextarea")
								.val()) {// 如果值没有更新 直接关闭dialog
							$("#update-textarea").dialog("close");
							return;
						}
						if(!validate($("#updateid").val(),$("#updatetextarea").val())){
							return ;
						}
						// 异步修改
						var orderUpadateUrl = "/order/edit";
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							dataType : 'json',
							data : {
								"paramName" : $("#updateid").val(), // 要修改的字段名称
								"paramValue" : $("#updatetextarea").val(), // 新值
								"orderId" : $('#orderId').val(),
								"format" : 'json'
							},
							url : orderUpadateUrl,
							error : function() {// 请求失败处理函数
								alert('保存失败');
							},
							success : function(data) { // 请求成功后回调函数。
								$("#" + $("#updateid").val() + ">span").text(
										$("#updatetextarea").val());// 把新值设置到页面上
								$("#update-textarea").dialog("close");
							}
						});
					});
			$("#areaCancle").bind("click", function() {
				$("#update-textarea").dialog("close");
			});

		});

// 修改所在区域
$(document).ready(
		function() {
			initArea($("#icountry").val(), $("#iprovince").val(), $("#icity")
					.val(), $("#idistrict").val(), $("#itown").val());
			$("#update-area").dialog({ // 初始化订单修改的dialog
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width : 550,
				hight : 250
			});

			$(".edit-area").bind("click", function() {// 为修改图标绑定定单击事件
				$("#update-area").dialog();
				$("#update-area").dialog("open");
			});

			$("#addSubmit").bind(
					"click",
					function() {// 为订单信息修改的提交按钮绑定事件
						var countryId = $("#country").val();
						var provinceId = $("#province").val();
						var cityId = $("#city").val();
						var districtId = $("#district").val();
						var townId = $("#town").val();
						if (provinceId == -1) {
							alert("请选择省份！");
							return;
						} else if (cityId == -1) {
							alert("请选择城市！");
							return;
						} else if (districtId == -1) {
							alert("请选择区县！");
							return;
						} else if(townId == -1){
							alert("请选择乡镇");
							return;
						}
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							dataType : 'json',
							data : {
								"paramName" : "consignee-area",
								"orderId" : $('#orderId').val(),
								"country" : countryId,
								"province" : provinceId,
								"city" : cityId,
								"district" : districtId,
								"town" : townId,
								"format" : 'json'
							},
							url : orderUpadateUrl,
							error : function(data) {// 请求失败处理函数
								alert(data.responseText);
							},
							success : function(data) { // 请求成功后回调函数。
								$('#countryText').html(
										$("#country").find('option:selected')
												.text());
								$('#provinceText').html(
										$("#province").find('option:selected')
												.text());
								$('#cityText').html(
										$("#city").find('option:selected')
												.text());
								$('#districtText').html(
										$("#district").find('option:selected')
												.text());
								$('#townText').html(
										$("#town").find('option:selected')
												.text());
								$('#areaId').val(townId);
								$("#update-area").dialog("close");
							}
						});

					});
			$("#addCancle").bind("click", function() {
				$("#update-area").dialog("close");
			});

		});

// 下拉列表数据修改
$(document)
		.ready(
				function() {
 					$("#update-select").dialog({ // 初始化订单修改的dialog
						autoOpen : false,
						bgiframe : false,
						modal : true,
						width : 300,
						hight : 250
					});
					$(".edit-selectimg")
							.bind(
									"click",
									function() {// 为修改图标绑定定单击事件
										$("#updateid").attr("value",
												$(this).parent().attr("id")); // 保存要更新的字段的名字到隐藏域
										$('#selectname').html(
												$(this).attr("name")); // 设置要修改的字段名称
										var plable = $("#updateselect")[0];
										var oldValue = $(
												"#"
														+ $(this).parent()
																.attr("id")
														+ ">span").text();
										$("#updatevalue").attr("value",
												oldValue); // 保存要修改的字段的值到隐藏域
										var url;
										if ($("#updateid").val() == "consignee-deliveryOption") {
											url = "deliveryOptionCode";
											showSelect(plable, url, oldValue);
										} else if ($("#updateid").val() == "consignee-invoiceType") {
											url = "findInvoiceTypeList";
											showSelect(plable, url, oldValue);
										} else if ($("#updateid").val() == "deliveryType") {
											url = "findDeliveryTypeByArea/" + $("#areaId").val();
											showSelect(plable, url, oldValue);
											deverflag=true;
 										} else {
 											
											if(!showPaymentSelect($(this).parent().find("span[class='payspan']").attr("id"),plable, $(
													"#updateid").val(), $(
													"#"
															+ $(this).parent()
																	.attr("id")
															+ ">span").attr(
													"id"))){
												alert("这种支付方式不运行修改");
												return;
											}
										}
										$("#updateselect").attr("value",
												oldValue);// 把原来的值设置到dialog中
										$("#update-select").dialog();
										$("#update-select").dialog("open");
									});

					$("#selectSubmit")
							.bind(
									"click",
									function() {// 为订单信息修改的提交按钮绑定事件
										if ($("#updatevalue").val() == $(
												"#receiveSelect").find(
												"option:selected").text()) {// 如果值没有更新
											// 直接关闭dialog
											$("#update-select").dialog("close");
											return;
										}
										if ($("#" + $("#updateid").val()).attr(
												"class") == "orderPayment") {
											var orderUpadateUrl = "/order/edit";
											$
													.ajax({
														async : false,
														cache : false,
														type : 'POST',
														dataType : 'json',
														data : {
															"paramName" : $(
																	"#"
																			+ $(
																					"#updateid")
																					.val())
																	.attr(
																			"class"), // 要修改的字段名称
															"orderPayment" : $(
																	"#"
																			+ $(
																					"#updateid")
																					.val())
																	.attr("id"),
															"payMent" : $(
																	"#receiveSelect")
																	.find(
																			"option:selected")
																	.val(), // 新值,
															"orderId" : $(
																	'#orderId')
																	.val(),
															"format" : 'json'
														},
														url : orderUpadateUrl,
														error : function() {// 请求失败处理函数
															alert('保存失败');
														},
														success : function(data) { // 请求成功后回调函数。
 															$(
																	"#"
																			+ $(
																					"#updateid")
																					.val()
																			+ ">span")
																	.text(
																			$(
																					"#receiveSelect")
																					.find(
																							"option:selected")
																					.text());// 把新值设置到页面上
															$("#update-select")
																	.dialog(
																			"close");
														}
													});
										} else {
											// 异步修改
											var orderUpadateUrl = "/order/edit";
											$
													.ajax({
														async : false,
														cache : false,
														type : 'POST',
														dataType : 'json',
														data : {
															"paramName" : $(
																	"#updateid")
																	.val(), // 要修改的字段名称
															"paramValue" : $(
																	"#receiveSelect")
																	.find(
																			"option:selected")
																	.val(), // 新值
															"orderId" : $(
																	'#orderId')
																	.val(),
															"format" : 'json'
														},
														url : orderUpadateUrl,
														error : function() {// 请求失败处理函数
															alert('保存失败');
														},
														success : function(data) { // 请求成功后回调函数。
															if(data.message!=null){
																alert(data.message);
																$("#update-select").dialog("close");
																return;
															}
 															$(
																	"#"
																			+ $(
																					"#updateid")
																					.val()
																			+ ">span")
																	.text(
																			$(
																					"#receiveSelect")
																					.find(
																							"option:selected")
																					.text());// 把新值设置到页面上
															$("#update-select")
																	.dialog(
																			"close");
														}
													});
										}
									});

					$("#selectCancle").bind("click", function() {
						$("#update-select").dialog("close");
					});

				});

$(document).ready(function() {

	$("#cancelOrder-editor").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true
	});

	$("button[binding='cancelOrderinfo']").click(function() {
		$("#cancelOrder-editor").dialog("open")
	});

	$("button[binding='newOrderTrack']").live("click", function() {
		$("#orderTrack-editor").dialog();
		$("#orderTrack-editor").dialog("open");
	});
});

$("#ordertrackButton").live(
		"click",
		function() {
			$("#orderTrack-editor").val();
			if ($("input[name='content']").val() == "") {
				alert("请输入备注!")

			} else {
				var url = "/order/orderTrack?format=json";
				var typeId = $("#cltypeId").val();
				var orderid = $("#clorderid").val();
				var content = $("#clcontent").val();
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					dataType : "json",
					data : {
						"orderid" : orderid,
						"typeId" : typeId,
						"content" : content
					},
					url : url,
					error : function() {// 请求失败处理函数
						alert('请求失败');
					},
					success : function(data) { // 请求成功后回调函数。
						if (data.message != 0) {
							$("#order_track").show();
							$("#orderTrack-editor").dialog("close");
							var str = "<tr><td>" + data.time + "</td><td>"
									+ data.employee + "</td><td>" + data.type
									+ "</td><td>" + content + "</td></tr>";
							$("#order_track>tbody>tr:first").after(str);
							$("#trackSize").html(
									parseInt($("#trackSize").html()) + 1);
							$("#clcontent").val('');
						}
					}
				});
			}
		});

(function($) {
	$(function() {
		var editable = $("div[editable='1']");
		editable.mouseover(function() {
			$(this).addClass("tip");
		}).mouseout(function() {
			$(this).removeClass("tip");
		});
		editable.find("a[name='mask']").click(function(e) {
			$(this.parentNode).addClass("edit");
			$(this.parentNode).find("input").focus();
			e.preventDefault();
		});
		editable.find("button[name='cancel']").click(function() {
			$(this.parentNode.parentNode).removeClass("edit");
		});
	});
})(jQuery)

var obig = document.getElementById("big");

function selectTag(showContent, selfObj) {
	var tag = document.getElementById("tags").getElementsByTagName("li");
	var taglength = tag.length;
	for (i = 0; i < taglength; i++) {
		tag[i].className = "";
	}
	selfObj.className = "selectTag";
	for (i = 0; j = document.getElementById("tagContent" + i); i++) {
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";
}

var receiveList;
var saveFalse = true;
function showSelect(element, controllerStr, oldhtml) {

	var oldhtml = $("#updatevalue").val();
	var s = document.createElement("SELECT");
	s.id = "receiveSelect";
	var orderControllerUrl = "/order/" + controllerStr + "?format=json";
	loadInvoiceList(orderControllerUrl);
	var opt;
	for ( var i = 0; i < receiveList.length; i++) {
		opt = new Option(receiveList[i].name, receiveList[i].id);
		s.options.add(opt);
	}
	for ( var j = 0; j < s.length; j++) {
		if (s.options[j].text == oldhtml) {
			s.selectedIndex = j;
			break;
		}
	}
	element.innerHTML = '';
	element.appendChild(s);

}

function loadInvoiceList(invoiceUrl) {
	//
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : invoiceUrl,
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			if(data.change){
				receiveList = data.children;
			}else{
				receiveList=null;
			}
			return receiveList;
		}
	});
}
// 订单审核
function checkOrder(orderId) {
	if (confirm("是否确认审核？")) {
		var ordercCheckUrl = '/order/' + orderId + '/audit?flag=1&&format=json';
		$.ajax({
			async: false,
			cache: false,
			type: 'GET',
			url: ordercCheckUrl,
			error: function(){// 请求失败处理函数
				alert('请求失败');
			},
			success: function(data){ // 请求成功后回调函数。
				if (data.result == 1) {
					$('#checkBtn').attr("disabled", "disabled");
					$("#orderStatusLabel").html("等待配货");
					alert('审核成功');					
				}
				else {
					alert('审核失败');
				}
			}
		});
	}
}

//配货
function picking(orderId){
	if(confirm("是否确认配货?")){
		var url = "/order/" + orderId + "/picking?format=json";
		$.ajax({
			async: false
			,cache: false
			,type: "GET"
			,url: url
			,error: function(){
				alert('请求失败!');
			}
			,success: function(data){
				if(data.result == 1){
					$('#picking').attr("disabled", "disabled");
					$('#delivery-button').removeAttr("disabled");
					$("#orderStatusLabel").html("正在配货");
					alert("配货成功!");
					window.location.reload();
				} else {
					alert("配货失败!");
				}
			}
		});
	}
}

function nullifyOrder(orderId){
	if(confirm("是否确认作废该订单？")){
		var url = '/order/'+orderId+'/nullify?format=json';
		$.ajax({
			async: false,
			cache: false,
			type: 'GET',
			url: url,
			error: function(){// 请求失败处理函数
				alert('请求失败');
			},
			success: function(data){ // 请求成功后回调函数。
				if (data.result == 1) {
					$('#nullifyBtn').attr("disabled", "disabled");
					$("#orderStatusLabel").html("归档");
					alert('订单已作废');	
					window.location.reload();
				}else if (data.result == 0) {
					alert('只有综合和专业渠道订单才能作废!');
					window.location.reload();
				}else{
					alert('订单作废失败!');
					window.location.reload();
				}
			}
		});
	}
}

function copyOrder(orderId){
	if(confirm("是否确认作复制订单？")){
		var url = '/order/'+orderId+'/copyOrder?format=json';
		$.ajax({
			async: false,
			cache: false,
			type: 'GET',
			url: url,
			error: function(){// 请求失败处理函数
				alert('请求失败');
			},
			success: function(data){ // 请求成功后回调函数。
				if (data.result == 1) {
					$('#copyBtn').attr("disabled", "disabled");
					alert('订单复制成功，新订单号为：'+data.message);	
				}else if (data.result == 0) {
					alert('订单不满足复制规则，复制失败!');
				}else{
					alert('订单复制失败!');
				}
			}
		});
	}
}

function erpIntercept(orderId,dcCode){
	if(dcCode != 110003){
		alert("订单非8A17发出，不能做拦截！！");
		return;
	}
	if(confirm("确认拦截此订单？")){
		var url = '/order/'+orderId+'/erpIntercept?format=json';
		$.ajax({
			async: false,
			cache: false,
			type: 'GET',
			url: url,
			error: function(){// 请求失败处理函数
				alert('请求失败');
			},
			success: function(data){ // 请求成功后回调函数。
				if (data.result == 1) {
					$('#erpInterceptBtn').attr("disabled", "disabled");
					$("#orderStatusLabel").html("等待拦截");
					alert('订单已标记拦截');	
					window.location.reload();
				}else{
					alert('订单拦截失败!');
					window.location.reload();
				}
			}
		});
	}
}

function reorder(orderId){
	if(confirm("确认该订单已做拦截操作？")){
		var url = '/order/'+orderId+'/reorder?format=json';
		$.ajax({
			async: false,
			cache: false,
			type: 'GET',
			url: url,
			error: function(){// 请求失败处理函数
				alert('请求失败');
			},
			success: function(data){ // 请求成功后回调函数。
				if (data.result == 1) {
					$('#reOrderBtn').attr("disabled", "disabled");
					$("#orderStatusLabel").html("等待转单");
					alert('操作成功!');	
					window.location.reload();
				}else{
					alert('操作失败!');
					window.location.reload();
				}
			}
		});
	}
}

// 保存order的url
var orderUpadateUrl = "/order/edit";
function updateOrder(name, value, countryId, provinceId, cityId, districtId,
		orderPaymentId, payMentId) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : 'json',
		data : {
			"paramName" : name,
			"paramValue" : value,
			"orderId" : $('#orderId').val(),
			"country" : countryId,
			"province" : provinceId,
			"city" : cityId,
			"district" : districtId,
			"orderPayment" : orderPaymentId,
			"payMent" : payMentId,
			"format" : 'json'
		},
		url : orderUpadateUrl,
		error : function() {// 请求失败处理函数
			alert('保存失败');
			saveFalse = true;
		},
		success : function(data) { // 请求成功后回调函数。
			saveFalse = false;
		}
	});
}

// 修改支付方式
function showPaymentSelect(payment,element, orderPaymentId, orderId) {
 	var oldhtml = $("#updatevalue").val();
	var s = document.createElement("SELECT");
	s.id = "receiveSelect";
	var invoiceUrl = "/order/findPaymentList/"+payment+"?format=json";
	loadInvoiceList(invoiceUrl);
	if(receiveList == null){
		return false;
	}
	var opt;
	for ( var i = 0; i < receiveList.length; i++) {
		opt = new Option(receiveList[i].name, receiveList[i].id);
		s.options.add(opt);
	}
	for ( var j = 0; j < s.length; j++) {
		if (s.options[j].text == oldhtml) {
			s.selectedIndex = j;
		}

	}
	element.innerHTML = '';
	element.appendChild(s);

	receiveList = null;
	return true;
}

function mouseoverElement(id) {
	var orignVlue = document.getElementById(id).innerText;
	document.getElementById(id).style.display = 'none';

	document.getElementById(id + 'hidden').value = orignVlue;
	document.getElementById(id + 'hidden').style.display = 'block';
	document.getElementById(id + 'hidden').focus();
}

function blurElement(id, name, element) {
	var newValue = document.getElementById(id + 'hidden').value;
	var orignValue = document.getElementById(id).innerText;
	if (newValue == orignValue) {
		// 不需要保存
	} else {
		updateOrder(name, newValue, null, null, null, null, null, null);
		if (!saveFalse) {
			// 保存成功
			document.getElementById(id).innerText = newValue;
			$(element.parentNode.parentNode).removeClass("edit");
		}

	}

}

function selectArea() {

	var obj = document.getElementById("areatd");
	obj.style.display = "block";
}

function cansal() {
	var obj = document.getElementById("areatd");
	obj.style.display = "none";
}

function visible(targetid) {

	if (document.getElementById) {
		document.getElementById(targetid).style.display = "block";
	}
}
function invisible(targetid) {
	if (document.getElementById) {

		document.getElementById(targetid).style.display = "none";
	}
}

function openDeliveryDialog(){
	$("#order-delivery").dialog('open');
}
function closeDeliveryDialog(){
	$("#order-delivery").dialog('close');
}
function deliverySubmit(orderId){	
	var deliveryUrl='/order/delivery';
	var deliveryCompanyId = $("#deliveryCompany").val();
	var productIds = $("input[name='productId']");
	var orderDeliveryCode = $("#orderDeliveryCode").val();
	var idLen = productIds.length;
	var proIdsStr="";	
	for(var i=0;i<idLen;i++){
		proIdsStr+=productIds[i].value+",";
	}
	var productQuantities = $("input[name='productQuantity']");
	var quLen = productQuantities.length; 
	var quantitiesStr="";
	for(var i=0;i<quLen;i++){
		quantitiesStr+=productQuantities[i].value+",";
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : 'json',
		data : {
			"orderId" : orderId,
			"deliveryCompanyId" : deliveryCompanyId,
			"proIds":proIdsStr,
			"proQuantities":quantitiesStr,
			"orderDeliveryCode":orderDeliveryCode,
			"format" : 'json'
		},
		url : deliveryUrl,
		error : function() {// 请求失败处理函数
			alert('请求失败!');
		},
		success : function(data) { // 请求成功后回调函数。
			if(data.result==1){
				alert('发货成功!');
			}else{
				alert('发货失败!');
			}
			location.reload(true);
		}
	});
}

function validate(name,value){
  	if(name=="consignee-consignee"&&$.trim(value).length==0){
		alert("收货人不能为空");
		return false;
	}
	else if(name=="consignee-phone"&&!/^(\d{3,4}\-)?[1-9]\d{6,8}$/.test(value)&&$.trim(value).length!=0){
		    alert("电话号码格式错误");
			return false;
		}
	else if(name=="consignee-mobile"&&!/^([1-9]{1})\d{10}$/.test(value)&&$.trim(value).length!=0){
	    alert("手机号码格式错误");
		return false;
	}
	else if(name=="consignee-email"){
		if(value.length==0){
			alert("邮箱不能为空");
			return false;
		}
		if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value)){
			alert("邮箱格式错误");
			return false;
		}
		
	}else if(name=="consignee-zipCode"){
		if(value.length==0){
			alert("邮编不能为空");
			return false;
		}
		if(! /^[0-9]{6}$/.test(value)){
			alert("邮编格式错误");
			return false;
		}
	}else if(name=="consignee-invoiceTitle"&&$.trim(value).length==0){
		alert("发票抬头不能为空");
		return false;
	}
	else if(name=="consignee-address"&&$.trim(value).length==0){
		alert("详细地址不能为空");
		return false;
	}
    return true;
}