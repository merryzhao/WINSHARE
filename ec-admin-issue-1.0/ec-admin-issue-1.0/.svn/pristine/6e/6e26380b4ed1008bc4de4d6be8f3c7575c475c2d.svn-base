/**
 * @author HideHai
 */
var ztree2 = null;
$(function(){
	ztree2 = {
			tree:null,
			setting :{
				checkable : false,
				checkStyle : "radio",
				checkRadioType : "all",
				async : true,
				editable: true,
				edit_removeBtn : false,
				edit_renameBtn : false,
				asyncUrl : "/category/tree?type=0", // 获取节点数据的URL地址
				asyncParam : [ "id" ], // 获取节点数据时，必须的数据名称，例如：id、name
				callback : {
					beforeDblclick: function(treeId, node){
						ztree2.tree.reAsyncChildNodes(node.isParent ? node : node.parentNode, "refresh");
					}
				},
				checkType : {
					"Y" : "",
					"N" : "s"
				},
				isSimpleData : true,
				treeNodeKey : "id",
				treeNodeParentKey : "pid",
				addHoverDom: function(treeId, treeNode){
					if ($("#diyBtn_movie_ok_" + treeNode.id).length != 0) 
						return;
					var tagA = $("a[id=" + treeNode.tId + "_a]");
					var movieToHTML = "<button type='button' class='move' id='diyBtn_movie_ok_" + treeNode.id + "' title='将商品移动到此分类:" + treeNode.name + "' onfocus='this.blur();'></button>";
					if(!treeNode.isParent){	
						tagA.append(movieToHTML);
					}
					var movieToBtn = $("#diyBtn_movie_ok_" + treeNode.id);
					if(movieToBtn){
						movieToBtn.bind("click", function(){
							transferCategory(treeId, treeNode);
						});
					}
				},
				removeHoverDom: function(treeId, treeNode){
					$("#diyBtn_movie_ok_" + treeNode.id).unbind().remove();
				}
			}
	};
	
	ztree2.tree = $("#categoryMoveDiv_tree").zTree(ztree2.setting, []);
	
	$("#categoryMoveDiv").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 350,
		title:"操作分类"
	});

});

