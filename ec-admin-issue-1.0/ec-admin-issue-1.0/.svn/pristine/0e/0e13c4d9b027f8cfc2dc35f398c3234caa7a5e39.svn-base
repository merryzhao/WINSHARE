var zTree;
var setting;
setting = {
	checkable: true,
	checkStyle: "radio",
	checkRadioType: "all",
	async : true,
	asyncUrl : "/channel/tree?type=0", // 获取节点数据的URL地址
	asyncParam : [ "id" ], // 获取节点数据时，必须的数据名称，例如：id、name
	callback : { },
	checkType : {
		"Y" : "",
		"N" : "s"
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
	zTree = $("#channel_tree").zTree(setting, zNodes);
}
function insertNodes(){
	var node = zTree.getCheckedNodes(true);
	var n = node[0];
	if(n !=null && !n.isParent){
		$("#channelChecked").html("<input type=hidden name=channel value="+n.id+" /> ("+n.name+")");
		$("#channelDiv").dialog("close");
	}else{
		alert("请选择一个最末级渠道");
	}
	
}

