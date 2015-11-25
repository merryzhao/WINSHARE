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
