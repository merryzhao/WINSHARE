var zTree;
var setting;
setting = {
	addHoverDom: addHoverDom,
	removeHoverDom: removeHoverDom,
	checkable : true,
	editable: true,
	async : true,
	keepParent: true,
	edit_renameBtn : isRename,
	edit_removeBtn : false,
	keepLeaf: false,
	asyncUrl : "/category/tree?manage=1&type=1", // 获取节点数据的URL地址
	asyncParam : [ "id" ], // 获取节点数据时，必须的数据名称，例如：id、name
	callback : {
		beforeChange: zTreeBeforeChange,
		rename: zTreeOnRename,
		dblclick: zTreeClick
		},
	isSimpleData : true,
	treeNodeKey : "id",
	treeNodeParentKey : "pid"
};
$(document).ready(function() {
		refreshTree();
		$("#helpcontent").hide();
});
var zNodes = [];
function refreshTree(asyncUrl) {
	zTree = $("#category_tree").zTree(setting, zNodes);
}
function isRename(treeNode){
	if(treeNode.level<1){
		return false;
	}
	return true;
}
function zTreeBeforeChange(event,treeNode){
	var confirmResult =true;
	if(treeNode.checked&&treeNode.isParent){
		confirmResult = confirm("停用该分类会停用分类下的所有分类，确定执行?");
	}else if(!treeNode.checked&&treeNode.isParent){
		confirmResult = confirm("启用该分类会启用用分类下的所有分类，确定执行?");
	}
	if(confirmResult){
		var url = '/category/'+treeNode.id+'/changeStatus';
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			url : url
		});
	}else{
		return false;
	}
}
function zTreeOnRename(event, treeId,treeNode){
	if(treeNode.name!=""){
		var url = '/category/'+treeNode.id+'/update';
		$.ajax({
			async : false,
			cache : false,
			data:"name="+treeNode.name,
			type : 'POST',
			url : url
		});
	}else{
		alert('分类名必填!');
	}
}


function addHoverDom(treeId, treeNode) {
    var aObj = $("#" + treeNode.tId + "_a");
    if ($("#diyBtn_"+treeNode.id).length>0) return;
    var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' ></span>"
        + "<button id='diyBtn_" + treeNode.id +"'  class=add ></button>";
    aObj.append(editStr);
    var btn = $("#diyBtn_"+treeNode.id);
    if (btn) btn.bind("click", function(){var conf = confirm("确认添加节点?");
	if(conf){
		var url = '/category/new';
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			data:'parentId='+treeNode.id,
			url : url
		});
		if(treeNode.isParent){
			zTree.reAsyncChildNodes(treeNode, "refresh");
		}else{
			zTree.reAsyncChildNodes(treeNode.parentNode, "refresh");
		}
		}});
};
function removeHoverDom(treeId, treeNode) {
    $("#diyBtn_"+treeNode.id).unbind().remove();
    $("#diyBtn_space_" +treeNode.id).unbind().remove();
};

function zTreeClick(event,treeId, treeNode){
	window.open( "/category/"+treeNode.id+"/productMeta","_blank");
}