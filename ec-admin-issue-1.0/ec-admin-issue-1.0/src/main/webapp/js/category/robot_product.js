/**
 *
 * Category 分类加载.
 *
 * @author heshuai
 *
 */
var ztree;
$(function(){
	ztree = {
			tree: null,
			descriptionFrame: $("#category_description"),
			root: function(node){
				return node.parentNode ? ztree.root(node.parentNode) : node;
			},//当前节点的根节点, [图书,百货,音像]
			setting: {
				asyncUrl: "/category/"+$("#productId").val()+"/tree_product",
				asyncParam: ["id"],
				checkable: true,
				checkType : {"Y": "p", "N": "ps" },
				async: true,
				dragCopy: false,
				dragMove: true,//禁止拖放
				showLine: true,
				isSimpleData: true,
				treeNodeKey: "id",
				callback: {
					change: zTreeOnChange,
					asyncSuccess: zTreeOnAsyncSuccess
				}
			}
			
	};
	
	ztree.tree = $("#category_tree").zTree(ztree.setting, []);
	
	

});
//***********************************************************
var sId;
function zTreeOnChange(event, treeId, treeNode) {

    // $("#checkedNum").val(treeNode.id + ", " + treeNode.name);
     var tmp =ztree.tree.getCheckedNodes();
     var nid ="";
     var html1="";
    for(var i=0;i<tmp.length;i++){
    	  var node = tmp[i];
    	  nid = nid+":"+node.id;
    	  html1 = html1+"<input type='hidden' name='categories' value='"+node.id+"' />"; 
    }
    $("#categorysDiv").html(html1);
    $("#isUpdate").val(1);
    sId=nid;
//    alert(sId);
}
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
//	jQuery.parseJSON(msg).each(function(i){
//		alert(i.checked);
//	});
}

function updateCategoryA(){
	 var tmp =ztree.tree.getCheckedNodes();
	 if($("#isUpdate").val()==0){
		 alert("你尚未对分类做出修改");
		 return;
	 }
	 if(tmp.length<1){
		 alert("你还未选择分类");
		 return;
	 }
	$("#proCaEditForm").submit();
}

