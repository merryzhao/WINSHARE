/**
 *
 * Category 分类加载.
 *
 * @author Heyadong
 * @update cast911
 *
 */
var lastSelectNode = null;
var constants={
	category:null	
};

$(function(){
	var ztree = {
			tree: null,
			descriptionFrame: $("#category_description"),
			root: function(node){
				return node.parentNode ? ztree.root(node.parentNode) : node;
			},//当前节点的根节点, [图书,百货,音像]
			setting: {
				asyncUrl: "/category/tree",
				asyncParam: ["id"],
				checkable: true,
				editable: true,
				async: true,
				keepParent: true,
				edit_renameBtn: true,
				edit_removeBtn: true,
				keepLeaf: false,
				dragCopy: false,
				dragMove: true,//禁止拖放
				showLine: true,
				async: true,
				drag: {
					isCopy: false,
					isMove: false
				},
				isSimpleData: true,
				treeNodeKey: "id",
				callback: {
					beforeClick: function(treeId, treeNode){
						getCategory(treeNode.id);

					},
					beforeChange: function(treeId, node){
						var ok = node.parentNode == null ? false : window.confirm((!node.checked ? "显示" : "隐藏") + ":" + node.name + "??");
						if (ok) {
							$.post("/category/available", {
								id: node.id,
								available: !node.checked
							});
						};
						return ok;
					},
					beforeDblclick: function(treeId, node){
						ztree.descriptionFrame.attr("src", "");
						$("#categoryMoveDiv").dialog("close");
					},
					rename: function(event, treeId, node){
						$.post("/category/rename?format=json", {
							"id": node.id,
							"name": node.name
						}, function(d){
							getCategory(node.id);
						});
					},
					beforeDrop: function(treeId, node, targetNode, moveType){
						if (!(ztree.root(node) == ztree.root(targetNode))) {
							alert("需要在同一顶级父节点")
							return false;
						}
						if (moveType == "inner") {
							before(treeId, node, targetNode, moveType);
						} else if (moveType == "before") {
							before(treeId, node, targetNode, moveType);
						} else if (moveType == "after") {
							after(treeId, node, targetNode, moveType);
						};
						ztree.tree.reAsyncChildNodes(node.isParent ? node : node.parentNode, "refresh");

					},
					//该方法为,商品添加分类使用,
					rightClick: function(event, treeId, node){
						var regx = /^.*product=(\d+)$/;
						var url = document.location.href;
						if (regx.test(url)) {
							if (window.confirm("是否为商品添加[" + node.name + "]分类?")) {
								var pid = url.match(regx)[1];
								var nodeId = node.id
								$.post("/product/appendCategory", {
									productId: pid,
									categoryId: nodeId
								});
							}
						}
					},
					beforeRemove:function(treeId, treeNode){
						var result = false;
						if(treeNode.isParent){
							alert("父分类不能删除，请先删除子分类!");
							return false;
						}
						if (!window.confirm("是否删除此分类["+treeNode.name+"]?")) {
							return false;
						}
						$.ajax({
							type: "DELETE",
							async: false,
							url: "/category/"+treeNode.id+"?format=json",
							data: {
								_method:"DELETE"
							},
							success:function(mav){
								if(mav.result == 1){
									result = true;
								}else{
									alert(mav.message);
								}
							}
						});
						return result;
					}
				},
				//添加 add Button
				addHoverDom: function(treeId, treeNode){
					if ($("#diyBtn_" + treeNode.id).length != 0) 
						return;
					var tagA = $("a[id=" + treeNode.tId + "_a]");
					var addHTML = "<button type='button' class='add' id='diyBtn_" + treeNode.id + "' title='在" + treeNode.name + "下添加分类' onfocus='this.blur();'></button>";
					tagA.append(addHTML);
					var infoHTML = "<button type='button' class='info' id='diyBtn_info_" + treeNode.id + "' title='数据描述' onfocus='this.blur();'></button>";
					tagA.append(infoHTML);
					var moveHTML = "<button type='button' class='move' id='diyBtn_move_" + treeNode.id + "' title='移动分类下的商品' onfocus='this.blur();'></button>";
					tagA.append(moveHTML);
					var updateHTML = "<button type='button' class='update' id='diyBtn_update_" + treeNode.id + "' title='位置修正' onfocus='this.blur();'></button>";
					tagA.append(updateHTML);
					var addButton = $("#diyBtn_" + treeNode.id);
					var infoBtn = $("#diyBtn_info_" + treeNode.id);
					var movieBtn = $("#diyBtn_move_" + treeNode.id);
					var updateBtn = $("#diyBtn_update_" + treeNode.id);
					if (addButton) {
						addButton.bind("click", function(){
							if (window.confirm("[" + treeNode.name + "] 添加一个子结点")) {
								$.ajax({
									type: "POST",
									async: false,
									url: "/category/new",
									data: {
										parentId: treeNode.id
									}
								});
								ztree.tree.reAsyncChildNodes(treeNode.isParent ? treeNode : treeNode.parentNode, "refresh");
							}
						});
					};

					if (infoBtn) {
						infoBtn.click(function(){
							ztree.descriptionFrame.attr("src", "/category/{id}/productMeta".replace("{id}", treeNode.id));
							return false;
						});
					}
					if(movieBtn){
						movieBtn.click(function(){
							lastSelectNode = treeNode;
							$("#categoryMoveDiv").dialog("open");
						});
					}
					
					if(updateBtn){
						updateBtn.click(function(){
						var updatesort = 	$("div.updatesort");
						var nowsort = updatesort.find("span.nowsort");
						var tagsort = updatesort.find("input.sort");
						var cancel = updatesort.find("a.cancel");
						var submit = updatesort.find("a.submit");
						nowsort.html(constants.category.index);
						tagsort.val(nowsort.html());
						updatesort.fadeIn(300);
						cancel.unbind("click");
						cancel.click(function(){
							updatesort.fadeOut(300);
							
						});
						submit.unbind("click");
						submit.click(function(){
							var para = {
								id:treeNode.id,
								sort:tagsort.val()
							};
							
							$.post("/category/updatesort?format=json",para,function(d){
								if(d.result>0){
									updatesort.fadeOut(300);
								}
								
							});
							
						});

//alert(treeNode.id);
						});
						
						
					}
					

				},
				removeHoverDom: function(treeId, treeNode){
					$("#diyBtn_" + treeNode.id).unbind().remove();
					$("#diyBtn_info_" + treeNode.id).unbind().remove();
					$("#diyBtn_move_" + treeNode.id).unbind().remove();
					$("#diyBtn_update_" + treeNode.id).unbind().remove();
				}
			}
	};

	ztree.tree = $("#category_tree").zTree(ztree.setting, []);
});
//***********************************************************

function getCategory(id){
	$.get("/category/" +id + "?format=json", function(d){
		var category = d.category;
		var html = "名称:" + category.name + ",别名:" + category.alias + ",code:" + category.code + ",位置:" + category.index+"</br>";
		if(category.proShop!=null){
			var proShop = category.proShop;
			html+="专业店名称:"+proShop.name+",url:"+proShop.url+",id:"+proShop.id+",是否可用:"+proShop.available+",版型:"+proShop.template+","+proShop.description;
		};
		$("#nodeattr").html(html);
		constants.category = category;
	});
}



function inner(treeId, node, targetNode, moveType){
	alert("暂不支持节点插入操作");
	return false;
	if (!window.confirm("需要将[ " + node.name + " ] 移到 [ " + targetNode.name + " ]内吗?")) {
		return false;
	};
	$.ajax({
		type: "POST",
		async: false,
		url: "/category/reparent",
		data: {
			parentId: targetNode.id,
			id: node.id
		}
	});
};

function before(treeId, node, targetNode, moveType){
	if (node.parentNode.id !== targetNode.parentNode.id) {
		//和父节点进行对换的话,要更新子节点所有的父节点.暂不考虑
		alert("相同父节点才能进行对换");
		return false;

	};
	if (!window.confirm("需要将[ " + node.name + " ] 和 [ {" + targetNode.name + "} ]进行对换吗?")) {
		return false;
	};
	$.ajax({
		type: "POST",
		async: false,
		url: "/category/renode?format=json",
		data: {
			id: node.id,
			targetid: targetNode.id
		},
		success: function(d){
			if (d.state > 0) {

			}
		}
	})

};

function after(treeId, node, targetNode, moveType){
	before(treeId, node, targetNode, moveType);
};

function transferCategory(treeId, treeNode){
	var selectNode = ztree2.tree.getSelectedNode();
	if(!selectNode || treeNode.id != ztree2.tree.getSelectedNode().id){
		alert("为防止误操作，请先选中此分类，在点击图标!");
		return false;
	}
	if (!window.confirm("是否将["+lastSelectNode.name+"]商品移动到分类["+treeNode.name+"]?")) {
		return false;
	}
	$.ajax({
		type: "POST",
		async: false,
		url: "/product/transfer?format=json",
		data: {
			target:"category",
			categoryId:lastSelectNode.id,
			targetCategoryId:selectNode.id
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
