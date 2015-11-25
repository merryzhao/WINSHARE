//所有节点带有checkbox
var zTree;
var setting;
setting = {
	checkable : true,
	async : true,
	asyncUrl : "/category/tree?type=1", // 获取节点数据的URL地址
	asyncParam : [ "id" ], // 获取节点数据时，必须的数据名称，例如：id、name
	callback : {},
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
	zTree = $("#category_tree").zTree(setting, zNodes);
}
//叶子节点带有radio
var zTree2;
var setting2;
setting2 = {
	checkable : true,
	 checkStyle : "radio",
	 checkRadioType : "all",
	async : true,
	asyncUrl : "/category/tree?type=0", // 获取节点数据的URL地址
	asyncParam : [ "id" ], // 获取节点数据时，必须的数据名称，例如：id、name
	callback : {},
	checkType : {
		"Y" : "",
		"N" : "s"
	},
	isSimpleData : true,
	treeNodeKey : "id",
	treeNodeParentKey : "pid"
};
$(document).ready(function() {
		refreshTree2();
});
var zNodes2 = [];
function refreshTree2(asyncUrl) {
	zTree2 = $("#category_tree2").zTree(setting2, zNodes2);
}
//所有节点带有radio
var zTree3;
var setting3;
setting3 = {
	checkable : true,
	 checkStyle : "radio",
	 checkRadioType : "all",
	 checkedCol : "",
	async : true,
	asyncUrl : "/category/tree?type=1", // 获取节点数据的URL地址
	asyncParam : [ "id" ], // 获取节点数据时，必须的数据名称，例如：id、name
	callback : {},
	checkType : {
		"Y" : "",
		"N" : "s"
	},
	isSimpleData : true,
	treeNodeKey : "id",
	treeNodeParentKey : "pid"
};
$(document).ready(function() {
		refreshTree3();
});
var zNodes3 = [];
function refreshTree3(asyncUrl) {
	zTree3 = $("#category_tree3").zTree(setting3, zNodes3);
}