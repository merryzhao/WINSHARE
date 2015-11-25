//叶子节点带有radio
var zTree2;
var setting2;
setting2 = {
	checkable : true,
	checkStyle : "checkbox",
	checkedCol : "",
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

// 插入分类节点,分类ID为nodes[0].id
function insertNodes() {
	var nodes = zTree2.getCheckedNodes(true);
	for ( var i in nodes) {
		var item = nodes[i];
		makeCategoryItem({
			"allName" : nodePath(item),
			"id" : item.id
		});
	}
	$("#categoryDiv").dialog("close");
}
function makeCategoryItem(item){
	var html = "";
	html = '<div>\
				<span class="category">'+item.allName+'</span>\
				<input type="hidden" value="'+item.id+'" class="newCategory" name="newCategory"/>\
				<a href="javascript:;" class="closeCategory">×</a>\
			</div>';
	$("#categorier").append(html);
}
$(".closeCategory").live("click", function(){
	if(!confirm("确认要删除?")){
		 return false;
	 }
	var self = $(this);
	self.parent("div").remove();
});
function nodePath(node) {
	if (node && node.parentNode) {
		return nodePath(node.parentNode) + "-<a href='http://list.winxuan.com/"
				+ node.id + "'>" + node.name + "</a>";
	}
	return node.name;
}


/**
 * 合并mate信息
 * @param ps
 * @returns {Array}
 */
function fetchMeta(ps){
	var mates = new Array();
	if(ps && ps.product && ps.product.categories){
		var me=ps.product.categories[0].productMetaList;
		for(var i in me){
			var a = exist(mates, function(item){return me.id == item.id;});
			if(!exist(mates, function(item){return me.id == item.id;})){
				mates.push(me[i]);
			}
		}
	}
	return mates;
}
function exist(items, handle){
	for(var i in items){
		if(handle(items[i])){
			return true;
		}
	}
	return false;
}


function fetchCategory(){
	var cas = $(".newCategory");
	var item = "[";
	var t = true;
	cas.each(function(i){
		item += i==0 ? "" : ","; 
		item += $(this).val().replace(new RegExp('(["\"])', 'g'),"\\\"");
	});
	item += "]";
	return item;
}

function creatJsonData(){
 	var jsonStr="";
	jsonStr=jsonStr+"{\"name\":\""+$("#name").val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\"," +
 			         "\"barcode\":\""+$("#barcode").val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\","+
			         "\"manuFacturer\":\""+$("#manuFacturer").val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\","+
			         "\"author\":\""+$("#author").val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\","+
 			         "\"productionDate\":\""+$("#productionDate").val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\","+
			         "\"categories\":\""+fetchCategory()+"\","+ 
			         "\"promValue\":\""+$("#promValue").val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\","+
			         "\"exList\":[";

	$("#ex_table").find(".ex").each(function(){
		if($.trim($(this).val()).length!=0){
			jsonStr=jsonStr+"{\"name\":\""+$(this).attr("exname")+"\"," +
		         "\"value\":\""+$(this).val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\","+
		         "\"mataId\":\""+$(this).attr("id")+"\"},";
		}else{
			jsonStr=jsonStr+"{\"name\":\""+$(this).attr("exname")+"\"," +
			"\"value\":\""+"  "+"\","+
			"\"mataId\":\""+$(this).attr("id")+"\"},";
		}
	})
	if($("#exdec_table").find(".exdec").length!=0){
			jsonStr=jsonStr+"],\"descriptionList\":["
			$("#exdec_table").find(".exdec").each(function(){
				if($.trim($(this).val()).length!=0){
 					jsonStr=jsonStr+"{\"name\":\""+$(this).attr("exname")+"\"," +
					"\"value\":\""+$(this).val().replace(new RegExp('(["\"])', 'g'),"\\\"")+"\","+
					"\"mataId\":\""+$(this).attr("id")+"\"},";
				}else{
					jsonStr=jsonStr+"{\"name\":\""+$(this).attr("exname")+"\"," +
					"\"value\":\""+"  "+"\","+
					"\"mataId\":\""+$(this).attr("id")+"\"},";
				}
			})
			
	}
	jsonStr=jsonStr+"]}"
 	$("#jsonData").val(jsonStr);
 }
