// 初始
	var productDiv;
$(document).ready(function() {
	/**
	 * 新建选择退货商品信息
	 */
	$("input[name='selectAll']").click(function() {
				if ($("input[name='selectAll']").attr("checked")) {
					$("input[name='item']").attr("checked", "checked");
				} else {
					$("input[name='item']").removeAttr("checked");
				}
			});
	/**
	 * 查询退换货列表选择上
	 */
	$("input[name='selectA']").click(function() {
				if ($("input[name='selectA']").attr("checked")) {
					$("input[name='returnOrderIds']")
							.attr("checked", "checked");
				} else {
					$("input[name='returnOrderIds']").removeAttr("checked");
				}
			});
	/**
	 * 查询退换货列表选择上
	 */
	$("input[name='selectB']").click(function() {
				if ($("input[name='selectB']").attr("checked")) {
					$("input[name='returnOrderIds']")
							.attr("checked", "checked");
				} else {
					$("input[name='returnOrderIds']").removeAttr("checked");
				}
			});
	/**
	 * 退换货新建，初始确认根据退换货类型，显示或隐藏部分内容
	 */
	if (!($('#type').val() == 24003 || $('#type').val() == 24004) ) {
		$('#refundcompensating').hide();
		$('#refundcompensating_title').hide();
		$('#refundcouponvalue').hide();
		$('#refundcouponvalue_title').hide();
		$('#expressid_td').show();
		$('#expressid_title_td').show();
		$('#targetDc_td').show();
		$('#targetDc_title_td').show();
		$('#product').show();
//		if($('#type').val() == 24002){
//			$('.fhyf').hide();
//		}
	} else {
// 		$(".fhyf").hide();
		$('#product').hide();
	}
	/**
	 * 退换货新建，根据退换货类型，显示或隐藏部分内容
	 */
	$('#type').change(function() {
				var typeid = $('#type').val()
				if (typeid == 24003 || typeid == 24004) {
//					$(".fhyf").hide();
					$('#expressid_td').hide();
					$('#expressid_title_td').hide();
					$('#targetDc_td').hide();
					$('#targetDc_title_td').hide();
					$('#refundcompensating').show();
					$('#refundcompensating_title').show();
					$('#refundcouponvalue').show();
					$('#refundcouponvalue_title').show();
					productDiv = $('#product').detach();
				} else {
					$('#product').show();
					$(".fhyf").show();
//					if($('#type').val() == 24002){
//						$('.fhyf').hide();
//					}else{
//						$(".fhyf").show();
//					}
					$('#refundcompensating').hide();
					$('#refundcompensating_title').hide();
					$('#refundcouponvalue').hide();
					$('#refundcouponvalue_title').hide();
					$('#expressid_td').show();
					$('#expressid_title_td').show();
					$('#targetDc_td').show();
					$('#targetDc_title_td').show();
					$("#newreturn").after(productDiv);
				}

			});
	
	//***增加excel导入功能 add by zhangliang2014.4.14
	
	$("#sbm").bind("click",function(){
		var fn = $("#fileName").val();
		if(fn == ""){
			alert("请选择上传的Excel文件");
			return false;
		}
		if(fn.lastIndexOf(".xls") == -1 || fn.lastIndexOf(".xls") == -1){
			alert("上传的文件必须为Excel文件");
			return false;
		}
		$("#excelForm").submit();
	});
})
/**
 * 退换货新建填表确认表单，并提交表单
 */
function submitForm() {
	var productId = $("input[name=item]:checked");
	var productIdAll = $("input[name=item]");
	var deliveryQuantity = $("input[name=deliveryQuantity]");
	var itemcount = $("input[bind=itemcount]");
	//发货运费退款
 	var blag = true;
	var typeid = $('#type').val();
	if (productId.length < 1 && !(typeid == 24003 || typeid == 24004)) {
		alert("请选择退换货商品");
		return;
	} else {
		for (var i = 0; i < productIdAll.length; i++) {
			for (var j = 0; j < productId.length; j++) {
				var count =itemcount.eq(i).val();
				if (productIdAll.eq(i).val() == productId.eq(j).val()) {
					if(!/^[1-9][0-9]{0,5}$/.test(count)){
						alert("退换货数量输入错误");
						blag=false;
						return;
					}
					var obj=itemcount.eq(i);
					var trObj=obj.parent().parent();
					var dq=trObj.find("input[name=deliveryQuantity]").val();
					var rq=trObj.find("td[class='returnQuantity']").text();
					if((parseInt(rq)+parseInt(obj.val()))>parseInt(dq)){
						alert("商品:"+trObj.find("td[class='proId']").text()+"("+trObj.find("td[class='proName']").text()+")  "+"的退货数量不能大于该商品的发货数量减已退货数量");
						blag=false;
						return;
					}								
					$("<input type='hidden'  name='itemcount' value='"+itemcount.eq(i).val()+"'/>").appendTo($('#returnorderForm'));
				}
			}
		}		
		if (blag) {
			if ($('#responsible').val() != $('#holder').val()) {
				if (confirm("责任方与承担方不一致，你确定要进入下一步吗？")) {
					$('#returnorderForm').submit();
				}
			}
			else {
				$('#returnorderForm').submit();
			}
		}else{
			$("form input[name='itemcount']").remove();
		}
	}

}
/**
 * 根据参数1最近一天 2最近一周 3最近一月 设置开始日期和截止日期
 * 
 * @param {}type
 */
function getTime(type) {
	var date = new Date();
	if(type == 1){
		date.setDate(date.getDate()-1);    		
	}else if(type == 2){
		date.setDate(date.getDate()-7);      	
	}else if(type == 3){
		date.setMonth(date.getMonth()-1);      	
	}else if(type ==4){
		date.setMonth(date.getMonth()-3);  
	}
	$("#startDate").val(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()));  
	var date1 =new Date();
	$("#endDate").val(date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+(date1.getDate()));        	
}
/**
 * 取消最近一天 最近一周 最近一月的 选择
 */
function noChecked() {
	$("input[name='time']").removeAttr("checked");
}
/**
 * 审核不通过, 添加不通过原因
 * @param id
 * @param type
 */
function notAudit(id, type){
	var val = $("#cause").val();
	if (!confirm("确定要审核此记录吗?")) {
		return;
	}
	var returnOrderIds = $("input[name=returnOrderIds]:checked");
	if (id == 0 && returnOrderIds.length == 0) {
		alert('请选择需要审核的记录');
		return;
	}
	var url = "/returnorder/audit";
	var form = $('#returnorderForm');
	var date = "";
	if (id == 0) {
		date = form.serialize() + "&returnOrderIds=&type=1&format=json";
	} else {
		date = "returnOrderIds=" + id + "&cause=" +val + "&type=" + type + "&format=json";
	}
	$.ajax({
				type : 'POST',
				url : url,
				data : date,
				dataType : 'json',
				success : function(msg) {
					if (id == 0) {
						var message = "成功审核" + msg.succeed + "条记录！"
						if (msg.fail != 0) {
							message += msg.fail + "条记录未能成功审核：\n" + msg.message;
						}
						alert(message);
						$('#returnorder_query_form').submit();
					} else {
						if (msg.succeed == 0) {
							alert(msg.message);
						} else {
							alert('审核成功');
							window.location.href = '/returnorder/' + id
									+ '/detail';
						}
					}
				},
				error : function() {
					alert('请求失败');
				}
			});
}
/**
 * 审核
 * 
 * @param {}
 *            id 需审核的记录id,如果为0，则表示从returnorderForm表单中读取记录id集
 * @param {}
 *            type 0表示审核不通过 1表示审核通过
 */
function audit(id, type) {
	if (!confirm("确定要审核此记录吗?")) {
		return;
	}
	var returnOrderIds = $("input[name=returnOrderIds]:checked");
	if (id == 0 && returnOrderIds.length == 0) {
		alert('请选择需要审核的记录');
		return;
	}
	var url = "/returnorder/audit";
	var form = $('#returnorderForm');
	var date = "";
	if (id == 0) {
		date = form.serialize() + "&returnOrderIds=&type=1&format=json";
	} else {
		date = "returnOrderIds=" + id + "&type=" + type + "&format=json";
	}
	$.ajax({
				type : 'POST',
				url : url,
				data : date,
				dataType : 'json',
				success : function(msg) {
					if (id == 0) {
						var message = "成功审核" + msg.succeed + "条记录！"
						if (msg.fail != 0) {
							message += msg.fail + "条记录未能成功审核：\n" + msg.message;
						}
						alert(message);
						$('#returnorder_query_form').submit();
					} else { 
						if (msg.succeed == 0) {
							alert(msg.message);
						} else {
							alert('审核成功');
							window.location.href = '/returnorder/' + id
									+ '/detail';
						}
					}
				},
				error : function() {
					alert('请求失败');
				}
			});
}

/**
 * 不退款的处理
 * @param id
 */
function notRefund(id){
	if (!confirm("确定不退款吗?")) {
		return;
	}
	$.ajax({
		type:'POST',
		url:'/returnorder/notrefund',
		data:'returnOrderId='+id+'&format=json',
		dataType : 'json',
		success:function(msg){
			alert(msg.message);
			window.location.href = '/returnorder/' + id
			+ '/detail';
		},
		error:function(msg){
			alert("请求失败...");
		}
	});
}

/**
 * 退款
 * 
 * @param {}
 *            id 需退款的记录id,如果为0，则表示从returnorderForm表单中读取记录id集
 */
function refund(id) {
	if (!confirm("确定要标记退款吗?")) {
		return;
	}
	var returnOrderIds = $("input[name=returnOrderIds]:checked");
	if (id == 0 && returnOrderIds.length == 0) {
		alert('请选择需要退款的记录');
		return;
	}
	var url = "/returnorder/refund";
	var form = $('#returnorderForm');
	var date = "";
	if (id == 0) {
		date = form.serialize() + "&returnOrderIds=&format=json";
	} else {
		date = "returnOrderIds=" + id + "&format=json";
	}
	$.ajax({
				type : 'POST',
				url : url,
				data : date,
				dataType : 'json',
				success : function(msg) {
					if (id != 0) {
						if (msg.succeed == 0) {
							alert("退款失败");
						} else {
							alert("退款成功");
							window.location.href = '/returnorder/' + id
									+ '/detail';
						}
					} else {
						var message = "成功退款" + msg.succeed + "条记录！ "
						if (msg.fail != 0) {
							message += msg.fail + "条记录未能成功退款：" + msg.message;
						}
						alert(message);
						$('#returnorder_query_form').submit();
					}
				},
				error : function() {
					alert('请求失败');
				}
			});
}
/**
 * 下传中启
 * 
 * @param {}
 *            id
 */
function downloadZQ(id) {
	if (!confirm("确定要已收到货了吗?")) {
		return;
	}
	$("#requestInfo").html('正在请求....');
	$("#requestInfo").dialog("open");
	var url = "/returnorder/downloadZQ";
	var date = "returnOrderIds=" + id + "&format=json";
	$.ajax({
				type : 'POST',
				url : url,
				data : date,
				dataType : 'json',
				success : function(msg) {
					if (msg.succeed) {
						$("#requestInfo").dialog("close");
						window.location.href = '/returnorder/' + id + '/detail';
					} else {
						$("#requestInfo").html('下传失败');
					}
				},
				error : function() {
					$("#requestInfo").html('请求失败');
				}
			});
}
/**
 * 下回传到EC
 * 
 * @param {}
 *            id
 */
function passBackEC(id, form) {
	var realQuantityError="";
	var realQuantityFormatError="";
	var qe=false;
	var qfe=false;
	$("#dataTable").find("input[name='realQuantity']").each(function(){
		if(!/^[1-9][0-9]{0,5}$/.test($(this).val())){
			realQuantityFormatError=realQuantityFormatError+"商品:"+"("+$(this).parent().parent().find("td[class='proName']").text()+")"+" 实际退换货数量必须为正整数\n";
			qfe=true;
		}
		if(parseInt($(this).val())>parseInt($(this).parent().parent().find("td[class='appQuantity']").text())){
			realQuantityError=realQuantityError+"商品:"+"("+$(this).parent().parent().find("td[class='proName']").text()+")"+" 实际退换货数量必须小于申请数量\n";
			qe=true;
		}
	})
	if(qe){
		alert(realQuantityError);
		return;
	}
	if(qfe){
		alert(realQuantityFormatError);
		return;		
	}
	
	if (!confirm("确定入库吗?")) {
		return;
	}
	passBackECClose();
	$("#requestInfo").html('正在请求....');
	$("#requestInfo").dialog("open");
	var url = "/returnorder/passBackEC";
	form = $('#' + form);
	var form = form.serialize() + "&returnOrderIds=" + id + "&format=json";
	$.ajax({
				type : 'POST',
				url : url,
				data : form,
				dataType : 'json',
				success : function(msg) {
					if (msg.succeed) {
						$("#requestInfo").dialog("close");
						$("#requestInfo").dialog("close");
						window.location.href = '/returnorder/' + id + '/detail';

					} else {
						$("#requestInfo").html('入库失败');
					}
				},
				error : function() {
					$("#requestInfo").html('请求失败');
				}
			});
}
/**
 * 显示回传EC弹出框
 */
function passBackECShow() {
	$("#passBackEC_DIV").dialog("open");
}
/**
 * 关闭回传EC弹出框
 */
function passBackECClose() {
	$("#passBackEC_DIV").dialog("close");
}
/**
 * 回传EC弹出框
 */
$("#passBackEC_DIV").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 800,
			height : 300
		});
function showTrack() {
	$("#newTrack").dialog("open");
}
function newTrack(url, form, div) {
	$("#newTrack").dialog("close");
	$("#requestInfo").html('正在请求....');
	$("#requestInfo").dialog("open");
	form = $('#' + form).serialize();
	$.ajax({
				type : 'post', // 可选get
				url : url, // 这里是接收数据的程序
				data : form, // 传给PHP的数据，多个参数用&连接
				dataType : 'html', // 服务器返回的数据类型 可选XML ,Json jsonp script html
				// text等
				success : function(html) {
					$("#requestInfo").dialog("close");
					$('#' + div).html(html);
				},
				error : function() {
					$("#requestInfo").html('请求失败');
				}
			})
}
/**
 * 信息提示框
 */
$("#requestInfo").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 250,
			height : 80
		});
/**
 * 弹出对话框
 */
$("#newTrack").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 250,
			height : 150
		});

// ***************修改****************

/**
 * 呈现修改框
 * 
 * @param {}
 *            type
 * @param {}
 *            ChineseName
 * @param {}
 *            div
 * @param {}
 *            name
 * @param {}
 *            value
 */
function showUpdate(type, ChineseName, div, name, value) {
	// 克隆对象
	div = $('#' + div).clone();
	// 设置显示中文名
	div.children("label").html(ChineseName);
	// 设置提交name
	$('#updateName').children("input").attr("value", name);
	// input提交
	if (type == "input") {
		// 退换货数量特殊处理
		if (name == "appQuantity") {
			var values = value.split("-");
			div.children("input").attr("value", values[1]);
			div.append("<label id='appQuantityId' style='display: none;'>"
					+ values[0] + "</label>");
		    div.append("<label id='deliveryQuantity' style='display: none;'>"
					+ values[2] + "</label>");
		} else {
			div.children("input").attr("value", value);
		}
		div.children("input").attr("id", "inputBox");
		// select 下拉框提交
	} else if (type == "select") {
		div.children("select").attr("value", value);
	}
	$("#updateDivContent").html(div);
	$("#updateDiv").dialog("open");
}
/**
 * 关闭修改框
 */
function closeUpdate() {
	$("#updateDiv").dialog("close");
}

/**
 * 验证输入金额格式
 */
function checkUp() {
	//退换货商品信息id
	var inputBoxValue = $("#inputBox").val();
	//对应退换货商品的发货数量
	var deliveryQuantity = $("#deliveryQuantity").html();
	var name="";
	var value="";
	// 获取表单数据
	var form=document.getElementById("updateForm");
    var inputs=form.getElementsByTagName("input");
    for(i=0;i<inputs.length;i++){
           if(inputs[i].name=="value"){
              value=inputs[i].value;
           }
           if(inputs[i].name=="name"){
              name=inputs[i].value;
           }
    	}
    
    if(name=="appQuantity"){
    	if(deliveryQuantity<value||value<1){
    		$("#requestInfo").html('应退换货数量应小于等于发货数量，并大于0。');
    	 return false;
    	}
    }
    if(name=="refundDeliveryFee"){
    	//发货运费
        var deliveryFee = $('#deliveryFee').html();
	    deliveryFee = deliveryFee.substr(0,deliveryFee.length-2)
    	if(value>deliveryFee){
    		$("#requestInfo").html('发货运费退款不能大于发货运费。');
    	 return false;
    	}
    }
	// 如果数字为空 无此控件则通过，否则验证其值
	if (inputBoxValue != null) {
		var regex = /^(\d+|[1-9])\.?\d{0,2}$/;
		var bool = regex.test(inputBoxValue);
		if(bool){
		return bool;
		}else{
		 $("#requestInfo").html('请输入正确的金额值！');
		return false;
		}
	} else {
		return true;
	}
}

/**
 * 提交修改
 */
function update() {
	if (!checkUp()) {
		$("#requestInfo").dialog("open");
		return;
	}
	// 关闭修改框
	$("#updateDiv").dialog("close");
	// 弹出等待框
	$("#requestInfo").html('正在修改....');
	$("#requestInfo").dialog("open");
	// 特殊处理退换货数量
	var name = $("#updateName").children("input").attr("value");
	if (name == "appQuantity") {
		var value = $("#appQuantityId").html();
		$("#inputBox").attr("value", value + "-" + $("#inputBox").val())
	}
	// 获取表单数据
	var form = $('#updateForm').serialize();
	// 路径
	var url = "/returnorder/update";
	$.ajax({
				type : 'POST', // 可选get
				url : url, // 这里是接收数据的程序
				data : form, // 传给PHP的数据，多个参数用&连接
				dataType : 'Json', // 服务器返回的数据类型 可选XML ,Json jsonp script html
				success : function(data) {
					if (data.succeed) {
						$("#requestInfo").dialog("close");
						location.reload();
					} else {
						$("#requestInfo").html('修改失败');
					}
				},
				error : function() {
					$("#requestInfo").html('请求失败');
				}
			})

}
/**
 * 修改对话框
 */
$("#updateDiv").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 250,
			height : 150
		});
// ***************修改****************
/**
 * tabs
 */
$(function() {
	$("#tabs").tabs({
		ajaxOptions : {
			error : function(xhr, status, index, anchor) {
				$(anchor.hash)
						.html("Couldn't load this tab. We'll try to fix this as soon as possible. "
								+ "If this wouldn't be a demo.");
			},
			cash : true
		}
	});
});

//**********************



