
$(function() {
	var dates = $( "#promotionStartDate, #promotionEndDate" ).datepicker({
			onSelect: function( selectedDate ) {
				var option = this.id == "promotionStartDate" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );
			}
		});
			
 			$('#promotionStartTime').timepicker({});
			$('#promotionEndTime').timepicker({});
	$("#type").bind("change", function() {
		getSelecttype();
	});
	$("#promotion_error").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 350
	});
});
function success(){
	$("#content").html("<center><h4>新建促销活动成功!</h4></center>");
}
function fail(information){
	$("#promotion_error").html(information);
	$("#promotion_error").dialog("open");
}
function getSelecttype() {
	var selectedtype = $("#type").find('option:selected').val();
	if (selectedtype == 20001) {
		// 选择单品价格优惠活动
		changecontent('productpreferential');
	} else if (selectedtype == 20003) {
		// 买商品赠商品活动
		changecontent('productgive');
	} else if (selectedtype == 20002) {
		// 分类价格优惠活动
		changecontent('category_price');
	} else if (selectedtype == 20004) {
		// 订单价格优惠活动
		changecontent('orderpreferential');
	} else if (selectedtype == 20005) {
		// 订单送礼券活动
		changecontent('orderpresent');
	} else if (selectedtype == 20006) {
		// 订单减运费活动
		changecontent('deliveryfee');
	} else if (selectedtype == 20008) {
		// 指定商品满省活动
		changecontent('specifyproductpreferential');
	} else if(selectedtype == 20009){
		//注册送礼券活动
		changecontent('presentfornewcustomer');
	} else {
		
	}
}

function submitto() {
	var selectedtype = $("#type").find('option:selected').val();
	document.promotionform.action = getcontroller(selectedtype);
	if (!mainvalidate()) {
		return false;
	}
	if ($("#type").val() == "20005") {
		if (!orderPresentValidate()) {
			return false;
		}
	}
	if ($("#type").val() == "20001") {
		if (!validateSingleProduct()) {
			return false;
		}
	}
	if ($("#type").val() == "20002") {
		if (!validateCategoryPrice()) {
			return false;
		}
	}
	if ($("#type").val() == "20003") {
		if(!validateProduct()){
			return false;
		}
	}
	if ($("#type").val() == "20004") {
		if (!validateOrderPrice()) {
			return false;
		}
	}
	if (selectedtype == 20006) {
		insertAreaNodes();
		if(!validateDeliveryFee()){
			return false;
		}
	}
	if ($("#type").val() == "20008") {
		if (!validatespecifyProductPreferential()) {
			return false;
		}
	}
	if(selectedtype == 20009){
		if(!registerPresentValidate()){
			return false;
		}
	}
	document.promotionform.submit();
}
function validateOrderPrice(){
	var sellers = $("input[name=orderPricesellers]");
	if(sellers.length<=0){
		alert('请选择商家');
		return false;
	}
	var manner = $("input:radio[name=orderPricemanner][checked]").val();
	var generalPriceSpends = $("input[name=generalPriceSpends]").val();
	var generalPriceSpares = $("input[name=generalPriceSpares]").val();
	var gradsPriceSpends = $("input[name=gradsPriceSpends]").val();
	var gradsPriceSpares = $("input[name=gradsPriceSpares]").val();
	// 普通优惠
	if((manner==0&&(generalPriceSpends==""||generalPriceSpares==""))){
       alert('请填写促销规则');
       return false;
	}
	// 梯度优惠
	if((manner==1&&(gradsPriceSpends==""||gradsPriceSpares==""))){
       alert('请填写促销规则');
       return false;
	}
	return true;
}
function getcontroller(selectedtype) {
	var url = "";
	if (selectedtype == 20001) {
		// 选择单品价格优惠活动
		url = '/promotion/saveSingleProduct';
	} else if (selectedtype == 20003) {
		// 买商品赠商品活动
		url = '/promotion/saveProduct';
	} else if (selectedtype == 20002) {
		// 分类价格优惠活动
		url = '/promotion/saveCategoryPrice';
	} else if (selectedtype == 20004) {
		// 订单价格优惠活动
		url = '/promotion/orderPrice';
	} else if (selectedtype == 20005) {
		// 订单送礼券活动
		url = '/promotion/presentfororder';
	} else if (selectedtype == 20006) {
		// 订单减运费活动
		url = '/promotion/saveDeliveryFee';
	} else if (selectedtype == 20008) {
		// 指定商品满省活动
		url = '/promotion/saveSpecifyProductPreferential';
	} else if (selectedtype == 20009){
		//注册送礼券
		url = '/promotion/savePresentForNewCustomer';
	} else {
		alert('do not have done');
	}
	return url;
}

function changecontent(contentstr) {
	$("#activitycontent").html('');
	$("#activitycontent").append($('#' + contentstr).html());
	$('#activitytype').val(contentstr);
	if(contentstr=='productpreferential' 
		|| contentstr=='category_price'){
		$("#productTips").show();
	}else{
		$("#productTips").hide();
	}
	if(contentstr=='productpreferential'){
		$("#time2").attr("value","23:30");
	}else{
		$("#time2").attr("value","23:59");
	}
}





$(document).ready(function() {
	//初始化dialog
	$("#present_send").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width:600,
		height:350
	});	
	
	generalPromotion('','');
	getSelecttype();// 刷新或后退显示选择类型下的活动
	$("#general_price_promotion").show();
	$("#grads_price_promotion").hide();
	// 删除此行
	$("#delete").live("click", function() {
		$(this).parent("label").parent("div").remove();
	});
	// 切换普通优惠 和 梯度优惠
	$("input[name=orderPricemanner]").live("change", function() {
		var item = $(this).val();
		if (item == 0) {
			generalPromotion('','');
			$("#general_price_promotion").show();
			$("#grads_price_promotion").hide();
		} else {
			gradsPromotionOne('','');
			$("#general_price_promotion").hide();
			$("#grads_price_promotion").show();
		}
	});

});
function number() {
	var number = "onkeydown=\"if(!isMoney(event)){if(document.all){event.returnValue = false;}else{event.preventDefault();}}\"";
	return number;
}
function isMoney(event) {
	var isMoney = (event.keyCode == 8 || event.keyCode == 190)
			|| (event.keyCode > 47 && event.keyCode < 58)
			|| (event.keyCode > 95 && event.keyCode < 106);
	return isMoney;
}

/**
 * 普通 条件第一条
 */
function generalPromotion(minamount,amount) {
	var general_promotion = $("#general_price_promotion");
	var text = general_promotion.html();
	if (text == "") {
		var div = "<div class='seller'>"
				+ "<label class='text'>满</label>"
				+ "<input class='update_price' name='generalPriceSpends' type='text' "
				+ number()
				+ " value='"+minamount+"'>"
				+ "<label class='text'>元</label>"
				+ "<label class='text'>省</label>"
				+ "<input class='update_price' name='generalPriceSpares' type='text' "
				+ number() + " value='"+amount+"'>" + "<label class='text'>元</label>"
				+ "<label class='red' >优惠成倍数增长</label>" + "</div>";
		general_promotion.html(div);
	}
}
/**
 * 梯度条件第一条
 */
function gradsPromotionOne(minamount,amount) {
	var grads_promotion = $("#grads_price_promotion");
	var text = grads_promotion.html();
	if (text == "") {
		var div = "<div class='seller'>"
				+ "<label class='text'>满</label>"
				+ "<input class='update_price' name='gradsPriceSpends' type='text' "
				+ number()
				+ " value='"+minamount+"'>"
				+ "<label class='text'>元</label>"
				+ "<label class='text'>省</label>"
				+ "<input class='update_price' name='gradsPriceSpares' type='text' "
				+ number()
				+ "  value='"+amount+"'>"
				+ "<label class='text'>元</label>"
				+ "<label><a class='delete' href='javascript:void();' onclick='gradsPromotion(\"\",\"\")'>添加</a></label>"
				+ "</div>";
		grads_promotion.html(div);
	}
}
/**
 * 添加梯度条件
 */
function gradsPromotion(minamount,amount) {
	var gradsStartPrice = $("input[name=gradsPriceSpends]");
	var leng = gradsStartPrice.length;
	var gradsStartPriceValue = gradsStartPrice.eq(leng - 1).val();
	if (gradsStartPriceValue != "") {
		var div = "<div class='seller'>"
				+ "<label class='text'>满</label>"
				+ "<input class='update_price' name='gradsPriceSpends' type='text' "
				+ number()
				+ "  value='"+minamount+"'>"
				+ "<label class='text'>元</label>"
				+ "<label class='text'>省</label>"
				+ "<input class='update_price' name='gradsPriceSpares' type='text' "
				+ number()
				+ "  value='"+amount+"'>"
				+ "<label class='text'>元</label>"
				+ "<label><a id='delete' class='delete' href='javascript:void();'>删除</a></label>"
				+ "</div>";
		var grads_promotion = $("#grads_price_promotion");
		grads_promotion.append(div);
	}
}
/**
 * 添加商家
 */
function addSellers() {
	// 下拉列表选中项
	var sellers = $("#seller_select option:selected");
	var value = sellers.val();
	var name = sellers.text();
	// 卖家隐藏id
	var seller_values = $("input[name='sellers']");
	var isSame = true;
	// 如果卖家中已有即将要添加的卖家 则isSame = false;
	if (jQuery.support.opacity) {
		for ( var i = 0; i < seller_values.length; i++) {
			if (value == seller_values.eq(i).val()) {
				isSame = false;
			}
		}
	} else {
		if (seller_values.length != 0) {
			for ( var i = 0; i < seller_values.length; i++) {
				if (value == seller_values.eq(i).val()) {
					isSame = false;
				}
			}
		}
	}
	// 如果isSame = true;则添加此卖家
	if (isSame) {
		var div = "<div class='seller'>"
				+ "<label class='text'>"
				+ name
				+ "</label>"
				+ "<label><a id='delete' class='delete' href='javascript:void(0);'>删除</a></label>"
				+ "<input type='hidden' name='sellers' value='" + value + "'>"
				+ "</div> ";
		var seller_list = $("#seller_list");
		seller_list.append(div);
	}
}
/**
 * 订单满送 -- 添加商家
 */
function orderPriceAddSeller() {
	// 下拉列表选中项
	var sellers = $("#seller_select option:selected");
	var value = sellers.val();
	var name = sellers.text();
	// 卖家隐藏id
	var seller_values = $("input[name='orderPricesellers']");
	var isSame = true;
	// 如果卖家中已有即将要添加的卖家 则isSame = false;
	if (jQuery.support.opacity) {
		for ( var i = 0; i < seller_values.length; i++) {
			if (value == seller_values.eq(i).val()) {
				isSame = false;
			}
		}
	} else {
		if (seller_values.length != 0) {
			for ( var i = 0; i < seller_values.length; i++) {
				if (value == seller_values.eq(i).val()) {
					isSame = false;
				}
			}
		}
	}
	// 如果isSame = true;则添加此卖家
	if (isSame) {
		var div = "<div class='seller'>"
				+ "<label class='text'>"
				+ name
				+ "</label>"
				+ "<label><a id='delete' class='delete' href='javascript:void(0);'>删除</a></label>"
				+ "<input type='hidden' name='orderPricesellers' value='" + value + "'>"
				+ "</div> ";
		var seller_list = $("#seller_list");
		seller_list.append(div);
	}
}



/**
 * 修改数生成促销规则部分的代码
 * @param {} manner
 * @param {} minamount
 * @param {} amount
 */
function loadingSelect(manner,minamount,amount){
	    minamount = minamount.split("_");
	    amount = amount.split("_");
		if (manner == 21001) {
			$("input[name='orderPricemanner'][value=0]").attr("checked",true); 
			$("input[name='generalPriceSpends']").val(minamount[0]);
			$("input[name='generalPriceSpares']").val(amount[0]);
			$("#general_price_promotion").show();
			$("#grads_price_promotion").hide();
		} else {
			$("input[name='orderPricemanner'][value=1]").attr("checked",true); 
			gradsPromotionOne(minamount[0],amount[0]);
			$("#general_price_promotion").hide();
			$("#grads_price_promotion").show();
			for (var i = 1; i < minamount.length; i++) {
				if(minamount[i]!=null && minamount[i].length>0){
					gradsPromotion(minamount[i],amount[i]);
				}
			}
		}
}
function orderPriceUpdateSumbit(){
		if (!mainvalidate()) {
		   return false;
	    }
		if (!validateOrderPrice()) {
		   return false;
		}
		$('#promotionOPUpdateform').submit();
}
/**
 * 验证文件格式
 * regex:需要验证的文件名称如demo.doc
 * suffix:后缀名doc
 * message:提示信息
 */
function isPattern(regex,suffix,message){
	for(var i=0;i<suffix.length;i++){
		var r = regex.substring(regex.length-suffix[i].length);
		if(r==suffix[i]){  
	        return true;  
	    }
	}
	alert(message);
    return false;  
}  