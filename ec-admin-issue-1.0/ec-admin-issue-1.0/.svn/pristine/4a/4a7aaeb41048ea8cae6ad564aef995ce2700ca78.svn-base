var zTree;
var setting;
setting = {
	checkable : true,
	async : true,
	asyncUrl : "/area/tree", // 获取节点数据的URL地址
	asyncParam : [ "id" ], // 获取节点数据时，必须的数据名称，例如：id、name
	callback : {},
	checkType : {
		"Y" : "",
		"N" : ""
	},
	isSimpleData : true,
	treeNodeKey : "id",
	treeNodeParentKey : "pid"
};
$(document).ready(function() {
		refreshTree();
});
var zNodes = [];
function refreshTree(asyncUrl) {
	zTree = $("#area_tree").zTree(setting, zNodes);
}
function submitEdit() {
	if (!mainvalidate()) {
		return false;
	}
	insertAreaNodes();
	if(!validateDeliveryFee()){
		return false;
	}
	document.promotionform.submit();
}

function deleteDiv(index){
	$("#oldAreaDiv"+index).html("");
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
				+ "</div> "
		var seller_list = $("#seller_list");
		seller_list.append(div);
	}
}

$(document).ready(function() {
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
	// 删除此行
	$("#delete").live("click", function() {
		$(this).parent("label").parent("div").remove();
	});
});

//获得点击后的tree并且在页面上显示
function insertAreaNodes() {
	var nodes = zTree.getChangeCheckedNodes();
	insertArea(nodes);
}

// 将选中的节点添加到表格中
function insertArea(nodes) {
	$("#areaInput").html("");
	for ( var i = 0; i < nodes.length; i++) {
		var id = nodes[i].id;		
		$("#areaInput").append(
				"<input type='hidden' name='areas' value='" + id + "'/>");
	}
}

function validateDeliveryFee() {
	var delivery =$("form input[name='deliveryfee']").val();
	if(checkOrderPrice()){
		alert("订单金额不正确");
		return false;
	}else if(isNaN(delivery)||delivery<0){
		alert("减免金额不正确");
		return false;
	}else if($("form input[name='areas']").html()==null){
		alert("区域必须选择");	
		return false;
	}
	return true;
}
//判断订单金额
function checkOrderPrice(){
	var min=$("form input[name='minAmount']").val();
//	var max=$("form input[name='maxAmount']").val();
	if(isNaN(min) || min<0){
		return true;
	}
	return false;
}
function number() {
	var number = "onkeydown=\"if(!isMoney(event)){if(document.all){event.returnValue = false;}else{event.preventDefault();}}\"";
	return number;
}
function isMoney(event) {
	var isMoney = (event.keyCode == 8 || event.keyCode == 190)
			|| (event.keyCode > 47 && event.keyCode < 58);
	return isMoney;
}